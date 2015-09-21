package ismp

import account.AcAccount
import grails.converters.JSON

import java.text.SimpleDateFormat

class WithdrawController extends BaseController{
    def beforeInterceptor = [action:this.&checkMobile]
    def withdrawService
    def operatorService

    protected checkMobile ={
        def cmCustomer=CmCustomer.get(session.cmCustomer.id)
        if(cmCustomer.status=='init'){
            writeInfoPage "用户状态错误，不能做提现操作，请联系吉高客服。",'warn'
            return
        }
        if(cmCustomer.status in ['disabled','deleted']){
            writeInfoPage "用户状态错误，不能做提现操作，请联系吉高客服。",'warn'
            session.cmCustomer=null
            return
        }
        def cmCustomerBankAccount=CmCustomerBankAccount.findByCustomerAndIsDefault(session.cmCustomer,true)
        if(!cmCustomerBankAccount){
            writeInfoPage "您没有指定默认账户，系统无法做提现操作，请联系吉高客服。"
            return
        }
        if(!session.cmCustomerOperator.defaultMobile){
            writeInfoPage "您还没有绑定手机，不能进行提现操作，请先绑定手机。"
            return
        }
    }

    protected checkParams(){
        def res=[result:true,msg:'ok']
        def amount=0
        try{
            BigDecimal strAmount=new BigDecimal(params.amount)
            if(strAmount>20000000){
                res.result=false
                res.msg= "单笔提现金额不能超过20000000，请重新输入"
                return res
            }
            strAmount=strAmount.multiply(new BigDecimal(100))
            log.info "strAmount="+strAmount
            amount=strAmount as Long
            params._amount=amount
        }catch(Exception e){
            res.result=false
            res.msg= "无效金额，请重新输入"
            return res
        }
        log.info "amount="+amount
        return res
    }

    def index = {
        if(!session.cmCustomer){
            writeInfoPage "用户状态错误，不能做提现操作，请联系吉高支付客服。",'warn'
            return
        }

        def cmCustomer=CmCustomer.get(session.cmCustomer.id)
        if(cmCustomer.status=='init'){
            writeInfoPage "用户状态错误，不能做提现操作，请联系吉高支付客服。",'warn'
            return
        }
        if(cmCustomer.status in ['disabled','deleted']){
            writeInfoPage "用户状态错误，不能做提现操作，请联系吉高支付客服。",'warn'
            session.cmCustomer=null
            return
        }

        def accountNo=session.cmCustomer.accountNo
        def acAccount_Main=AcAccount.findByAccountNo(accountNo)
        def cmCustomerBankAccount=CmCustomerBankAccount.findByCustomerAndIsDefault(session.cmCustomer,true)
        [acAccount_Main:acAccount_Main,cmCustomerBankAccount:cmCustomerBankAccount]
    }

    def step2={
        //if (!withForm {true}.invalidToken {false}) {writeInfoPage "请不要重复提交数据" ;return}
        //检查页面验证码
        if(grailsApplication.config.verifyCaptcha!='false'&&!session.captcha?.isCorrect(params.captcha.toUpperCase())){
            session.captcha=null
            writeInfoPage '验证码不正确，请重新输入'
            render(text:"验证码不正确，请重新输入",contentType:'text/plaintext',encoding:"UTF-8")
            return
        }
        session.captcha=null
        def res=checkParams()
        if(res.result==false){
            writeInfoPage res.msg
            render(text:res.msg,contentType:'text/plaintext',encoding:"UTF-8")
            return
        }
        render(text:'ok',contentType:'text/plaintext',encoding:"UTF-8")
        return

        //发送手机验证码
//        def mobile_captcha=KeyUtils.getRandNumberKey(6)
//        def content='提现确认：您本次提现操作的验证码是'+mobile_captcha+'。'
//        def cmCustomer_payer=session.cmCustomer
//        def cmCustomerOperator_payer=session.cmCustomerOperator
//        operatorService.sendMobileCaptcha(cmCustomer_payer,cmCustomerOperator_payer,cmCustomerOperator_payer.defaultMobile,content,mobile_captcha,'withdrawal')

//        def accountNo=session.cmCustomer.accountNo
//        def acAccount_Main=AcAccount.findByAccountNo(accountNo)
//        def cmCustomerBankAccount=CmCustomerBankAccount.findByCustomerAndIsDefault(session.cmCustomer,true)
//        [acAccount_Main:acAccount_Main,cmCustomerBankAccount:cmCustomerBankAccount]
    }

    def sendMobileCaptcha={
        def mobile_captcha=KeyUtils.getRandNumberKey(6)
        def content='提现确认：您本次提现操作的验证码是'+mobile_captcha+'。【吉高】'
        def cmCustomer_payer=session.cmCustomer
        def cmCustomerOperator_payer=session.cmCustomerOperator
        operatorService.sendMobileCaptcha(cmCustomer_payer,cmCustomerOperator_payer,cmCustomerOperator_payer.defaultMobile,content,mobile_captcha,'withdrawal')
        render "ok"
    }

    def save={
        //if (!withForm {true}.invalidToken {false}) {writeInfoPage "请不要重复提交数据" ;return}
        def res=checkParams()
        if(res.result==false){
            writeInfoPage res.msg
            render(text:res.msg,contentType:'text/plaintext',encoding:"UTF-8")
            return
        }
        def amount=params._amount
        //检查支付密码
        if(!params.paypass){
            writeInfoPage "请输入支付密码"
            render(text:"请输入支付密码",contentType:'text/plaintext',encoding:"UTF-8")
            return
        }
        def payer=session.cmCustomerOperator
        def str_pass=(payer.id+'p'+params.paypass).encodeAsSHA1()
        if(payer.payPassword!=str_pass){
            writeInfoPage "支付密码错误"
            render(text:"支付密码错误",contentType:'text/plaintext',encoding:"UTF-8")
            return
        }
        //检查手机校验码
        def query={
            eq('useType','withdrawal')
            eq('parameter',payer.id as String)
            eq('sendType','mobile')
            eq('isUsed',false)
            ge("timeExpired",new Date())
        }
        def cmDynamicKeyList=CmDynamicKey.createCriteria().list([sort:"id",order:'desc'],query)
        def cmDynamicKey=(cmDynamicKeyList&&cmDynamicKeyList.size()>0)?cmDynamicKeyList.first():null
        if(grailsApplication.config.verifyCaptcha!='false'&&((!cmDynamicKey)||(params.mobile_captcha!=cmDynamicKey.verification))){
            writeInfoPage "手机验证码错误，请重新输入"
            render(text:"手机验证码错误，请重新输入",contentType:'text/plaintext',encoding:"UTF-8")
            return
        }
        try{
            def msgreturn=withdrawService.withdrawal(cmDynamicKey,session.cmLoginCertificate,amount)
            if(msgreturn=='true')
            {
                writeInfoPage "提现操作成功",'ok'
                render(text:"ok",contentType:'text/plaintext',encoding:"UTF-8")
                return
            }else{
                log.info msgreturn
                writeInfoPage "提现操作失败"
                render(text:"fail",contentType:'text/plaintext',encoding:"UTF-8")
                return
            }
        }catch(Exception e){
            e.printStackTrace()
            writeInfoPage "提现操作失败"
            render(text:"fail",contentType:'text/plaintext',encoding:"UTF-8")
            return
        }
    }

    def list = {
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query = {
            eq('payerId', session.cmCustomer.id)
            eq('tradeType', 'withdrawn')

            if (!params.startDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                Calendar calendar = Calendar.getInstance()
                params.endDate = sdf.format(calendar.getTime())
                calendar.add(Calendar.MONTH, -3)
                params.startDate = sdf.format(calendar.getTime())
            }

            String startDate = String.valueOf(params.startDate)
            int year = Integer.valueOf(startDate.substring(0, 4))
            int month = Integer.valueOf(startDate.substring(5, 7)) - 1
            int day = Integer.valueOf(startDate.substring(8, 10))
            Calendar calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            ge('dateCreated', calendar.getTime())

            String endDate = String.valueOf(params.endDate)
            year = Integer.valueOf(endDate.substring(0, 4))
            month = Integer.valueOf(endDate.substring(5, 7)) - 1
            day = Integer.valueOf(endDate.substring(8, 10))
            calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            le('dateCreated', calendar.getTime())
//            if (params.startDate) {
//                ge('dateCreated', Date.parse("yyyy-MM-dd", params.startDate))
//                if (!params.endDate) {
//                    le('dateCreated',Date.parse("yyyy-MM-dd", params.startDate).updated(month:Date.parse("yyyy-MM-dd", params.startDate).month+3)+1)
//                }
//            }
//            if (params.endDate) {
//                if (!params.startDate) {
//                    ge('dateCreated',Date.parse("yyyy-MM-dd", params.endDate).updated(month:Date.parse("yyyy-MM-dd", params.endDate).month-3));
//                }
//                le('dateCreated', Date.parse("yyyy-MM-dd", params.endDate) + 1)
//            }
        }
        def list = TradeWithdrawn.createCriteria().list(params, query)
        def count = TradeWithdrawn.createCriteria().count(query)
        [tradeList: list, tradeListTotal: count]
    }
}
