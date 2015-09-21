package ismp

import grails.validation.ValidationException
import role.Role

class OperatorController extends BaseController{
    static allowedMethods = [save: "POST",update:'POST',saveBindMobile:'POST',savechangeloginpass:'POST',savechangepaypass:'POST',sendDynamicKey:'POST',savechangeapikey:'POST']
    def operatorService

    protected checkMobile={
        if(!session.cmCustomerOperator.defaultMobile){
            render "您还没有绑定手机，不能修改支付密码。"
            return
        }
    }
     protected checkMobileMethod={
        if(!session.cmCustomerOperator.defaultMobile){
            return "您还没有绑定手机，不能修改支付密码。"
        }
    }

    def index = {
        redirect(action: "list", params: params)
    }

    /**
     * 取得当前商户下所拥有的所有可用角色，将这些角色整合到roleSetMap中，以便其他地方读取；
     */
    def queryRoleByCustomer = {
//        def roleList = Role.findAllByCustomerId(session.cmCustomer.id)
        def query = {
            or {
                eq("customerId", String.valueOf(session.cmCustomer.id))
                eq("customerId", String.valueOf(0))
            }
            order("id", "asc")
        }
        def roleList = Role.createCriteria().list(params, query)
        CmCustomerOperator.roleSetMap.clear()
        for(role in roleList){
            if(role.roleStatus == 'normal'){
                CmCustomerOperator.roleSetMap.put('' + role.id,role.roleName)
            }
        }
    }

    def list = {
        queryRoleByCustomer()
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = {
            eq("customer.id", session.cmCustomer.id)
            ne("status", "deleted")
    }

        [cmCustomerOperatorInstanceList: CmCustomerOperator.createCriteria().list(params, query), cmCustomerOperatorInstanceTotal: CmCustomerOperator.createCriteria().count(query)]
    }

    def create = {
        def cmCustomerOperatorInstance = new CmCustomerOperator()
        cmCustomerOperatorInstance.properties = params
        return [cmCustomerOperatorInstance: cmCustomerOperatorInstance]
    }

    def checkEmailInfo = {
        def cmLoginCertificate=CmLoginCertificate.findByLoginCertificate(params.defaultEmail)
        if(cmLoginCertificate) render "该邮件已被注册了，请换一个再试"
    }

    def sendMobileCaptcha = {
        if(!session.cmCustomerOperator.defaultMobile){
            render "您还没有绑定手机，不能添加操作员。"
            return
        }
        def mobile_captcha=KeyUtils.getRandNumberKey(6)
        def content=new Date().format("MM月dd日")+',您新建['+ session.cmCustomer.name +']商户后台操作员。手机校验码：'+mobile_captcha+'。【吉高】'
        def cmCustomer=session.cmCustomer
        def cmCustomerOperator=session.cmCustomerOperator
        operatorService.sendMobileCaptcha(cmCustomer,cmCustomerOperator,cmCustomerOperator.defaultMobile,content,mobile_captcha,'add_operator')
        render "ok"
    }

    def save = {
        def cmCustomerOperatorInstance = new CmCustomerOperator(params)

        //检查手机校验码
        def query={
            eq('useType','add_operator')
            eq('parameter',session.cmCustomerOperator.id as String)
            eq('sendType','mobile')
            eq('isUsed',false)
            ge("timeExpired",new Date())
        }
        def cmDynamicKeyList=CmDynamicKey.createCriteria().list([sort:"id",order:'desc'],query)
        def cmDynamicKey=(cmDynamicKeyList&&cmDynamicKeyList.size()>0)?cmDynamicKeyList.first():null
        if(grailsApplication.config.verifyCaptcha!='false'&&((!cmDynamicKey)||(params.mobile_captcha!=cmDynamicKey.verification))){
            flash.message = "手机验证码错误，请重新输入"
            def role = Role.findByCustomerId(session.cmCustomer.id)
            render(view: "create", model: [cmCustomerOperatorInstance: cmCustomerOperatorInstance,roleList:role])
            return
        }

        try{
            cmCustomerOperatorInstance.defaultEmail=cmCustomerOperatorInstance.defaultEmail.toLowerCase();//将邮箱统一转换成小写以防止重复
            cmCustomerOperatorInstance.customer=session.cmCustomer
            cmCustomerOperatorInstance.status='normal'
            operatorService.addOperator(session.cmCustomer,cmCustomerOperatorInstance)
            flash.message = "添加操作员${cmCustomerOperatorInstance.name}成功，请通知该操作员登录Email然后根据邮件提示设置登录密码"
            redirect(action: "list")
//        }catch(ValidationException e){
//            e.printStackTrace()
//            render(view: "create", model: [cmCustomerOperatorInstance: cmCustomerOperatorInstance])
        }catch(Exception e){
            def role = Role.findByCustomerId(session.cmCustomer.id)
            e.printStackTrace()
            render(view: "create", model: [cmCustomerOperatorInstance: cmCustomerOperatorInstance,roleList:role])
        }
    }

	def updateStatus={
        def cmCustomerOperatorInstance = CmCustomerOperator.get(params.id)
        if(cmCustomerOperatorInstance.customer.id==session.cmCustomer.id&&cmCustomerOperatorInstance.id!=session.cmCustomerOperator.id){
            operatorService.updateStatus(cmCustomerOperatorInstance)
            redirect(action: "list")
        }else{
            writeInfoPage "没找到该操作员"
        }
    }

    def edit={
        queryRoleByCustomer()
        def cmCustomerOperatorInstance = CmCustomerOperator.get(params.id)
        if (!cmCustomerOperatorInstance||cmCustomerOperatorInstance.customer.id!=session.cmCustomer.id) {
            writeInfoPage "没找到该操作员"
        }else {
            if(cmCustomerOperatorInstance.id==session.cmCustomerOperator.id){
                writeInfoPage "不能修改该操作员信息"
            }else{
                [cmCustomerOperatorInstance: cmCustomerOperatorInstance]
            }
        }
    }

    def update={
        def cmCustomerOperatorInstance = CmCustomerOperator.get(params.id)
        if(!params.name){writeInfoPage "请输入操作员姓名";return}
        if(!params.roleSet){writeInfoPage "请选择操作员角色";return}
        if (!cmCustomerOperatorInstance||cmCustomerOperatorInstance.customer.id!=session.cmCustomer.id) {
            writeInfoPage "没找到该操作员"
        }else {
            if(cmCustomerOperatorInstance.id==session.cmCustomerOperator.id){
                writeInfoPage "不能修改该操作员信息"
            }else{
                cmCustomerOperatorInstance.roleSet=params.roleSet
                cmCustomerOperatorInstance.name=params.name
                cmCustomerOperatorInstance.save(flush:true)
                redirect(action: "list")
            }
        }
    }

    def resetLoginPass={
        def cmCustomerOperatorInstance = CmCustomerOperator.get(params.id)
        if(cmCustomerOperatorInstance.customer.id==session.cmCustomer.id&&cmCustomerOperatorInstance.id!=session.cmCustomerOperator.id){
            operatorService.resetLoginPassByEmail(cmCustomerOperatorInstance.customer,cmCustomerOperatorInstance,cmCustomerOperatorInstance.defaultEmail)
            writeInfoPage "系统已发送一封重置登录密码的邮件到该操作员的默认邮箱里了，请通知该操作员登录邮箱重新设置登录密码。","ok"
        }else{
            writeInfoPage "没找到该操作员"
        }
    }

    def bindMobile={
        if(session.cmCustomerOperator.defaultMobile){
           render '您已经绑定了一个手机号码'+session.cmCustomerOperator.defaultMobile
        }
    }

    def bindMobile2={
        if(session.cmCustomerOperator.defaultMobile){
            render '您已经绑定了一个手机号码'+session.cmCustomerOperator.defaultMobile
            return
        }
        if(!params.mobile){
            render '请输入您要绑定的手机号'
            return
        }else{
            def mobile=params.mobile as String
            //if(mobile.length()!=11||!(mobile.startsWith('13')||mobile.startsWith('15')||mobile.startsWith('18')))
            //手机号码格式，必须是11位数字，且以13/14/15/18开头。
            if(!(mobile==~/^1[3458]\d{9}$/)){
                render '请输入正确的手机号码'
                return
            }
            //检查是否该手机号已经被绑定
//            def cmLoginCertificate=CmLoginCertificate.findByLoginCertificate(mobile)
//            if(cmLoginCertificate){
//                render '该手机号'+mobile+'已经被绑定'
//                return
//            }
            //发送手机短信
            operatorService.sendBindMobileSMS(session.cmCustomer,session.cmCustomerOperator,mobile)
            render "ok"
        }
    }

    def saveBindMobile={
        if(session.cmCustomerOperator.defaultMobile){
           render '您已经绑定了一个手机号码'+session.cmCustomerOperator.defaultMobile
           return
        }
        if(!params.mobile_captcha){
           render '请输入您手机上收到的验证码'
           return
        }
        def query={
            eq('useType','bindmobile')
            eq('parameter',session.cmCustomerOperator.id as String)
            eq('sendType','mobile')
            eq('isUsed',false)
            ge("timeExpired",new Date())
        }
        def cmDynamicKeyList=CmDynamicKey.createCriteria().list([sort:"id",order:'desc'],query)
        def cmDynamicKey=(cmDynamicKeyList&&cmDynamicKeyList.size()>0)?cmDynamicKeyList.first():null
        if(grailsApplication.config.verifyCaptcha!='false'&&((!cmDynamicKey)||(params.mobile_captcha!=cmDynamicKey.verification))){
            render "手机验证码错误，请重新输入"
            return
        }
        //验证码验证通过
        try{
            operatorService.saveBindMobile(cmDynamicKey,session.cmCustomerOperator)
            render "ok"
        }catch (Exception e){
            e.printStackTrace();
            render "绑定手机失败"
        }
    }

    def changeloginpass={}

    def savechangeloginpass={
        def password=params.password as String
        def newpassword=params.newpassword as String
        def confirm_newpassword=params.confirm_newpassword as String
        log.info newpassword
        if(!password){
            render "请输入当前登录密码"
            return
        }

        if(!newpassword){
            render  "请输入新的登录密码"
            return
        }
        if (newpassword != confirm_newpassword) {
            render "您输入的新登录密码和确认密码不一致，请重新输入"
            return
        }
        //检查密码强度
        if(!KeyUtils.checkPassStrength(newpassword)){
            render "密码长度必须大于8位，且必须是数字、字母和特殊字符组合而成"
            return
        }
        //判断当前密码是否正确
        def cmCustomerOperator=session.cmCustomerOperator
        def shapass=(cmCustomerOperator.id+params.password).encodeAsSHA1()
        if(shapass!=cmCustomerOperator.loginPassword)
        {
            render "您输入的当前登录密码不正确，请重新输入"
            return
        }

        if((cmCustomerOperator.id+'p'+params.newpassword).encodeAsSHA1()==cmCustomerOperator.payPassword){
            render "登陆密码不能与支付密码相同，请重新输入"
            return
        }

        cmCustomerOperator.loginPassword=(cmCustomerOperator.id+params.newpassword).encodeAsSHA1()
        cmCustomerOperator.lastPWChangeTime = new Date();
        cmCustomerOperator.save(flush: true)
        flash.message= "修改登录密码成功"
        render "ok"
    }

    def changepaypass={
        checkMobile
    }

    def sendDynamicKey={
        def str=checkMobileMethod.call();
        if(str){
            render str
            return;
        }
        operatorService.resetPayPassByMobile(session.cmCustomer,session.cmCustomerOperator,session.cmCustomerOperator.defaultMobile)
        render 'ok'
    }
    def mobileDynamicKey={
        if(!session.cmCustomerOperator.defaultMobile){
            render "您还没有绑定手机，不能修改。"
            return
        }
        operatorService.updateMobile(session.cmCustomer,session.cmCustomerOperator,session.cmCustomerOperator.defaultMobile)
        render 'ok'
    }
    def updateMobileNew={
        if(!params.mobileCaptcha){
            render '请输入您收到的手机验证码'
            return
        }else{
            //检查手机动态口令是否正确
            def query={
                eq('useType','update_mobile')
                eq('parameter',session.cmCustomerOperator.id as String)
                eq('sendType','mobile')
                eq('isUsed',false)
                ge("timeExpired",new Date())
            }
            def cmDynamicKeyList=CmDynamicKey.createCriteria().list([sort:"id",order:'desc'],query)
            def cmDynamicKey=(cmDynamicKeyList&&cmDynamicKeyList.size()>0)?cmDynamicKeyList.first():null
            if(grailsApplication.config.verifyCaptcha!='false'&&((!cmDynamicKey)||(params.mobileCaptcha!=cmDynamicKey.verification))){
                render "手机验证码错误，请重新输入"
                return
            }
            //手机动态口令验证通过 更改动态口令表信息
            cmDynamicKey.timeUsed=new Date()
            cmDynamicKey.isUsed=true
            cmDynamicKey.save(flush: true,failOnError:true)
        }
        render "ok"
    }
    def checkMobileInfo = {
       //检查手机号是否与当前相同
        if(params.new_mobile == session.cmCustomerOperator.defaultMobile){
            render '该手机号'+params.new_mobile+'与当前用户的手机号相同，请换一个。'
        }else {
            render ''
        }
    }
    def newMobileDynamicKey={
        if(!params.new_mobile){
            return "请您输入新手机号码。"
        }
        operatorService.newMobile(session.cmCustomer,session.cmCustomerOperator,params.new_mobile)
        render 'ok'
    }
    def saveUpdateMobile={
        if(!params.new_mobile){
            return "请您输入新手机号码。"
        }
        //检查是否该手机号已经被绑定
        if(params.new_mobile == session.cmCustomerOperator.defaultMobile){
            render '该手机号'+params.new_mobile+'与当前用户的手机号相同，请换一个。'
            return
        }
        if(!params.mobile_captcha2){
            render '请您输入新绑定手机收到的验证码。'
            return
        }
        def query={
            eq('useType','new_mobile')
            eq('parameter',session.cmCustomerOperator.id as String)
            eq('sendType','mobile')
            eq('isUsed',false)
            ge("timeExpired",new Date())
        }
        def cmDynamicKeyList=CmDynamicKey.createCriteria().list([sort:"id",order:'desc'],query)
        def cmDynamicKey=(cmDynamicKeyList&&cmDynamicKeyList.size()>0)?cmDynamicKeyList.first():null
        if(grailsApplication.config.verifyCaptcha!='false'&&((!cmDynamicKey)||(params.mobile_captcha2!=cmDynamicKey.verification))){
            render "手机验证码错误，请重新输入"
            return
        }
        //验证码验证通过
        try{
            operatorService.saveUpdateMobile(cmDynamicKey,session.cmCustomerOperator)
            render "ok"
        }catch (Exception e){
            e.printStackTrace();
            render "修改手机失败"
        }
    }

    def savechangepaypass={
        checkMobile
        if(!params.mobile_captcha){
           render '请输入您手机上收到的动态口令码'
           return
        }
        def newpassword=params.newpassword as String
        def confirm_newpassword=params.confirm_newpassword as String
        if(!newpassword){
            render "请输入新的支付密码"
            return
        }
        if (newpassword != confirm_newpassword) {
            render "您输入的新支付密码和确认密码不一致，请重新输入"
            return
        }
        //检查密码强度
        if(!KeyUtils.checkPassStrength(newpassword)){
            render "密码长度必须大于8位，且必须是数字、字母和特殊字符组合而成"
            return
        }
        //检查手机动态口令是否正确
        def query={
            eq('useType','reset_pay_pass')
            eq('parameter',session.cmCustomerOperator.id as String)
            eq('sendType','mobile')
            eq('isUsed',false)
            ge("timeExpired",new Date())
        }
        def cmDynamicKeyList=CmDynamicKey.createCriteria().list([sort:"id",order:'desc'],query)
        def cmDynamicKey=(cmDynamicKeyList&&cmDynamicKeyList.size()>0)?cmDynamicKeyList.first():null
        if(grailsApplication.config.verifyCaptcha!='false'&&((!cmDynamicKey)||(params.mobile_captcha!=cmDynamicKey.verification))){
            render "手机验证码错误，请重新输入"
            return
        }
        //手机动态口令验证通过
        //手机动态口令验证通过
        def result = operatorService.updatePayPass(cmDynamicKey,newpassword)
        if(result != ''){
            render result
            return
        }
        render "ok"
    }

    // 修改安全验证码
    def savechangeapikey={
        def cmCustomer=CmCustomer.findById(session.cmCustomer.id)
        cmCustomer.apiKey = ismp.KeyUtils.getRandKey(64)

        cmCustomer.save(flush: true)
        session.cmCustomer.apiKey = cmCustomer.apiKey
        //flash.message= "修改安全验证码成功"
        render "ok"
    }

    // 修改个性化信息
    def savechangewelcomemsg={
        def newwelcomemsg=params.newwelcomemsg as String
//        if(!newwelcomemsg){
//            render  "请输入个性化信息"
//            return
//        }
        def cmCustomerOperator=CmCustomerOperator.findById(session.cmCustomerOperator.id)
        cmCustomerOperator.welcomeMsg=newwelcomemsg

        cmCustomerOperator.save(flush: true)
        session.cmCustomerOperator.welcomeMsg=newwelcomemsg
        //flash.message= "修改个性化信息成功"
        redirect(controller:"security", action:"index")
//        render "ok"
    }
}
