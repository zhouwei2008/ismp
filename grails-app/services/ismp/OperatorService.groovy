package ismp

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class OperatorService {

    static transactional = true

    //def asynchronousMailService
    def grailsTemplateEngineService
    def sendService

    def addOperator(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperatorInstance) {
//        cmCustomerOperatorInstance.loginPassword='111111'
//        cmCustomerOperatorInstance.payPassword='111111'
//        if(!cmCustomerOperatorInstance.defaultEmail)throw new RuntimeException("请输入邮箱")
        if (cmCustomerOperatorInstance.save()){
            //产生随机的登录密码和支付密码
            def loginpass=cmCustomerOperatorInstance.id+KeyUtils.getRandKey(8)
            def paypass=cmCustomerOperatorInstance.id+KeyUtils.getRandKey(8)
            cmCustomerOperatorInstance.loginPassword=loginpass.encodeAsSHA1()
//            cmCustomerOperatorInstance.payPassword=paypass.encodeAsSHA1()
            cmCustomerOperatorInstance.save()

            def cmLoginCertificate=new CmLoginCertificate()
            cmLoginCertificate.customerOperator=cmCustomerOperatorInstance
            cmLoginCertificate.loginCertificate=cmCustomerOperatorInstance.defaultEmail
            cmLoginCertificate.certificateType='email'
            cmLoginCertificate.isVerify=false
            if(!cmLoginCertificate.hasErrors()&&cmLoginCertificate.save()){
                //1.发送重置密码的邮件
                sendEmailCaptcha(cmCustomer,cmCustomerOperatorInstance,cmCustomerOperatorInstance.defaultEmail,'获取登录密码')
            }else{
                cmCustomerOperatorInstance.errors.rejectValue("defaultEmail","该邮件已被注册了，请换一个再试")
                throw new RuntimeException("该邮件${cmCustomerOperatorInstance.defaultEmail}已被注册，cmLoginCertificate表不允许重复Email")
            }
        }else{
            throw new RuntimeException("添加操作员失败")
        }
    }

    def updateStatus(CmCustomerOperator cmCustomerOperator){
        if(cmCustomerOperator.status=='normal')
        {
            cmCustomerOperator.status='disabled'
        } else if(cmCustomerOperator.status=='disabled' || cmCustomerOperator.status=='locked'){
            cmCustomerOperator.status='normal'
        }
    }

    def resetLoginPassByEmail(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperator,String sendTo){
        //产生随机的登录密码
//        def loginpass=cmCustomerOperator.id+KeyUtils.getRandKey(8)
//        cmCustomerOperator.loginPassword=loginpass.encodeAsSHA1()
//        cmCustomerOperator.save(flush: true)
        sendEmailCaptcha(cmCustomer,cmCustomerOperator,sendTo,'重置登录密码')
    }

    def resetLoginPassByMobile(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperator,String sendTo){
        //产生随机的登录密码
//        def loginpass=cmCustomerOperator.id+KeyUtils.getRandKey(8)
//        cmCustomerOperator.loginPassword=loginpass.encodeAsSHA1()
//        cmCustomerOperator.save(flush: true)
        //1随机产生6位数字手机验证码
        def mobile_captcha=KeyUtils.getRandNumberKey(6)
        def content='重置登录密码确认：您本次重置登录密码操作的验证码是'+mobile_captcha+'。【吉高】'
        sendMobileCaptcha(cmCustomer,cmCustomerOperator,sendTo,content,mobile_captcha,'reset_login_pass')
    }

    def sendEmailCaptcha(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperator,String sendTo,String mailTitle)
    {
        //1.发送重置密码的邮件
        //1.1写动态口令表
        def cmDynamicKey=new CmDynamicKey()
        cmDynamicKey.customer=cmCustomer
        cmDynamicKey.sendType='email'
        cmDynamicKey.sendTo=sendTo
        cmDynamicKey.parameter=cmCustomerOperator.id                                                                //运算参数
        cmDynamicKey.key=KeyUtils.getRandKey(8)                                                                      //动态口令
        cmDynamicKey.procMethod='md5'                                                                              //运算方法
        cmDynamicKey.verification=(cmDynamicKey.parameter+cmDynamicKey.key).encodeAsMD5()                        //验证值，等于运算参数加动态口令的md5值
        cmDynamicKey.timeExpired=new Date(System.currentTimeMillis()+3600000)                                      //过期时间,设为1小时后
        //cmDynamicKey.timeUsed=''                                                                                   //使用时间
        cmDynamicKey.isUsed=false                                                                                  //是否使用过
        cmDynamicKey.useType='reset_login_pass'                                                                  //使用类型
        cmDynamicKey.save(flush: true,failOnError:true)
        log.info "reset_login_pass verification=${cmDynamicKey.verification}"
        // 开发期间不用发，上生产后再打开
        //1.2异步发送邮件
        /*asynchronousMailService.sendAsynchronousMail{
            to cmDynamicKey.sendTo
            subject mailTitle
            //html '<u>Test</u>'
            body(view: "/mail/template_resetloginpass", model: [cmDynamicKey:cmDynamicKey])
            maxAttemptsCount 3
        }*/
        sendService.sendEmail("/mail/template_resetloginpass",mailTitle,cmDynamicKey.sendTo,[cmDynamicKey:cmDynamicKey])
    }

    def sendMobileCaptcha(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperator,String sendTo,String content,String mobile_captcha,String useType)
    {
        //1.1写动态口令表
        def cmDynamicKey=new CmDynamicKey()
        cmDynamicKey.customer=cmCustomer
        cmDynamicKey.sendType='mobile'
        cmDynamicKey.sendTo=sendTo
        cmDynamicKey.parameter=cmCustomerOperator.id                                                                //运算参数
        cmDynamicKey.key=KeyUtils.getRandKey(8)                                                                      //动态口令
        cmDynamicKey.procMethod='none'                                                                              //运算方法
        cmDynamicKey.verification=mobile_captcha                                                                    //验证值，等于手机验证码
        cmDynamicKey.timeExpired=new Date(System.currentTimeMillis()+180000)                                       //过期时间,设为3分钟后
        //cmDynamicKey.timeUsed=''                                                                                   //使用时间
        cmDynamicKey.isUsed=false                                                                                  //是否使用过
        cmDynamicKey.useType=useType                                                                                //使用类型
        cmDynamicKey.save(flush: true,failOnError:true)
        log.info "${useType} mobile_captcha=${mobile_captcha}"

        def usrID = ConfigurationHolder.config.userId
        def usrPWD= ConfigurationHolder.config.userPwd
        def result = sendService.sendSMS(String.valueOf(cmDynamicKey.sendTo),content,String.valueOf(usrID),String.valueOf(usrPWD))
        if(result instanceof Map){
          log.info(result)
          if (result.rescode == '00') {
//                 writeInfoPage "发送成功"
          }else if(result.rescode == '03'){
//                 writeInfoPage "发送成功"
          }else if(result.rescode == '01'){
                 throw new RuntimeException(result.resmsg)
          }else if(result.rescode == '02'){
                 throw new RuntimeException("用户未授权")
          } else {
            throw new RuntimeException("open account faile, error code:${result.errorCode}, ${result.errorMsg}")
          }
        }else{
            throw new RuntimeException("暂时中断，稍后再试");
        }

        // 开发期间不用发短信，上生产后再打开
        //1.2发送手机短信
//        ISMSTool smstool=SMSFactory.getSMSTool();
//        println smstool.class.canonicalName
//        try {
//            smstool.SendSms(cmDynamicKey.sendTo,content);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }

    def updateLoginPass(CmDynamicKey cmDynamicKey,String loginpass){
        //更改动态口令表信息
        cmDynamicKey.timeUsed=new Date()
        cmDynamicKey.isUsed=true
        cmDynamicKey.save(flush: true,failOnError:true)

        //更改操作员登录密码
        def cmCustomerOperator=CmCustomerOperator.get(cmDynamicKey.parameter as Long)
        cmCustomerOperator.loginPassword=(cmCustomerOperator.id+loginpass).encodeAsSHA1()
        cmCustomerOperator.save(flush: true,failOnError:true)

        //更改登录凭证表的isVerify
        def cmLoginCertificate=CmLoginCertificate.findByLoginCertificate(cmDynamicKey.sendTo)
        if(cmLoginCertificate&&(cmLoginCertificate.isVerify==false))
        {
            cmLoginCertificate.isVerify=true
            cmLoginCertificate.save(flush: true,failOnError:true)
        }
    }

    def sendBindMobileSMS(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperator,String sendTo){
        //1随机产生6位数字手机验证码
        def mobile_captcha=KeyUtils.getRandNumberKey(6)
        def content=new Date().format("MM月dd日")+',您申请绑定手机号。手机校验码：'+mobile_captcha+'。【吉高】'
        sendMobileCaptcha(cmCustomer,cmCustomerOperator,sendTo,content,mobile_captcha,'bindmobile')
    }

    def saveBindMobile(CmDynamicKey cmDynamicKey,CmCustomerOperator cmCustomerOperator)
    {
        //更改动态口令表信息
        cmDynamicKey.timeUsed=new Date()
        cmDynamicKey.isUsed=true
        cmDynamicKey.save(flush: true,failOnError:true)

        //更改操作员表的defaultMobile字段
        cmCustomerOperator.defaultMobile=cmDynamicKey.sendTo
        cmCustomerOperator.save(flush: true,failOnError:true)

        //判断手机号是否已经被绑定
         //添加登录凭证表
        /*
        def cmLoginCertificate=new CmLoginCertificate()
        cmLoginCertificate.customerOperator=cmCustomerOperator
        cmLoginCertificate.loginCertificate=cmDynamicKey.sendTo
        cmLoginCertificate.certificateType='mobile'
        cmLoginCertificate.isVerify=true
        */
        /*
        if(cmLoginCertificate.hasErrors()||!cmLoginCertificate.save(flush: true))
        {
            cmCustomerOperator.defaultMobile=null
            throw new RuntimeException("该手机号${cmDynamicKey.sendTo}已经被绑定")
        }
        */
    }

    def resetPayPassByMobile(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperator,String sendTo){
        //1随机产生6位数字手机验证码
        def mobile_captcha=KeyUtils.getRandNumberKey(6)
        def content='修改支付密码确认：您本次修改支付密码操作的验证码是'+mobile_captcha+'。【吉高】'
        sendMobileCaptcha(cmCustomer,cmCustomerOperator,sendTo,content,mobile_captcha,'reset_pay_pass')
    }
   def updateMobile(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperator,String sendTo){
        //1随机产生6位数字手机验证码
        def mobile_captcha=KeyUtils.getRandNumberKey(6)
        def content=new Date().format("MM月dd日")+',您申请修改绑定手机号。手机校验码：'+mobile_captcha+'。【吉高】'
        sendMobileCaptcha(cmCustomer,cmCustomerOperator,sendTo,content,mobile_captcha,'update_mobile')
    }
   def newMobile(CmCustomer cmCustomer,CmCustomerOperator cmCustomerOperator,String sendTo){
        //1随机产生6位数字手机验证码
        def mobile_captcha=KeyUtils.getRandNumberKey(6)
        def content=new Date().format("MM月dd日")+',您绑定新的手机号。手机校验码：'+mobile_captcha+'。【吉高】'
        sendMobileCaptcha(cmCustomer,cmCustomerOperator,sendTo,content,mobile_captcha,'new_mobile')
    }
   def saveUpdateMobile(CmDynamicKey cmDynamicKey,CmCustomerOperator cmCustomerOperator){
        //更改动态口令表信息
        cmDynamicKey.timeUsed=new Date()
        cmDynamicKey.isUsed=true
        cmDynamicKey.save(flush: true,failOnError:true)

        //更改操作员表的defaultMobile字段
        cmCustomerOperator.defaultMobile=cmDynamicKey.sendTo
        cmCustomerOperator.save(flush: true,failOnError:true)
   }

    def updatePayPass(CmDynamicKey cmDynamicKey,String paypass){
        //更改动态口令表信息
        cmDynamicKey.timeUsed=new Date()
        cmDynamicKey.isUsed=true
        cmDynamicKey.save(flush: true,failOnError:true)

        //更改操作员登录密码
        def cmCustomerOperator=CmCustomerOperator.get(cmDynamicKey.parameter as Long)
        def shapass=(cmCustomerOperator.id+paypass).encodeAsSHA1()

        if(shapass==cmCustomerOperator.loginPassword){
            return  '支付密码不能与登陆密码相同！'
        }
        cmCustomerOperator.payPassword=(cmCustomerOperator.id+'p'+paypass).encodeAsSHA1()
        cmCustomerOperator.save(flush: true,failOnError:true)

    }
}
