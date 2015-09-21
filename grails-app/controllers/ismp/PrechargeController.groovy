package ismp

import account.AcAccount
import boss.BoCustomerService
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.text.SimpleDateFormat

class PrechargeController extends BaseController{
    def beforeInterceptor = [action:this.&checkMobile]
    def index = {
        def accountNo=session.cmCustomer.accountNo
        def acAccount_Main=AcAccount.findByAccountNo(accountNo)
        def acAccount_Frozen=AcAccount.findByParentIdAndAccountType(acAccount_Main?.id,'freeze')

        [acAccount_Main:acAccount_Main,acAccount_Frozen:acAccount_Frozen]
    }

    def confirm={

          if(grailsApplication.config.verifyCaptcha!='false'&&!session.captcha?.isCorrect(params.captcha.toUpperCase())){
            session.captcha=null
            writeInfoPage '验证码不正确，请重新输入'
            return
        }
        session.captcha=null
         render(view:"confirm",model:[cardName:params.cardName,cardNo:params.cardNo,amount:params.amount,note:params.note]);
    }

    def recharge={
        if (!withForm {true}.invalidToken {false}) {
            writeInfoPage "请不要重复提交数据,请确认后重新提交！" ;
            return
        }
        def amount=params.amount
        if(!params.paypass){
            writeInfoPage "请输入支付密码"
            return
        }
        def payer=session.cmCustomerOperator
        def str_pass=(payer.id+'p'+params.paypass).encodeAsSHA1()
        if(payer.payPassword!=str_pass){
            writeInfoPage "支付密码错误"
            return
        }
        if(session.prechargeService==null){
            writeInfoPage "您的账户暂未开通预付费卡充值服务，请联系客服开通！"
            return
        }
            //收款方账号
          def reviceEmail =  session.prechargeService.serviceParams
        if(reviceEmail==null||"".equals(reviceEmail)){
            writeInfoPage "您账户开通的预付费卡充值服务中没有配置收款账号，无法充值，请联系客服开通！"
            return
        }
//          def reviceEmail = "1050001043@qq.com"
         //查找收款方partner
          def loginCertificate = CmLoginCertificate.findByLoginCertificate(reviceEmail.toLowerCase())
          if(loginCertificate==null){
              writeInfoPage "无此收款方账号"
              return
          }
          def cmCustomerOperator = loginCertificate.customerOperator
          def reviceCustomer = cmCustomerOperator.customer
          def key =  reviceCustomer.apiKey
          def partner = reviceCustomer.id
          def clientNo =  reviceCustomer.customerNo
         //判断当前用户是否开通自助签约，如果没有开通，则开通
           def signService = RoyaltyBinding.findWhere(partnerId:partner,customerId:session.cmCustomer.id,outCustomerCode:session.cmLoginCertificate.loginCertificate,nopassRefundFlag:'T',status:'sign',bizType:'11')
           if(signService==null){  //沒有簽約，需要重新簽約
               def royaltyBinding = new BindingMoney()
               royaltyBinding.partnerId= partner    //收款方partner
               royaltyBinding.customerId=session.cmCustomer.id    //付款方customer
               royaltyBinding.outCustomerCode= session.cmLoginCertificate.loginCertificate  //付款方帳號
               royaltyBinding.nopassRefundFlag='T'
               royaltyBinding.status= 'sign'
               royaltyBinding.bizType='11'
               royaltyBinding.dateCreated= new Date()
               royaltyBinding.amount=500000000
               royaltyBinding.totalAmount=500000000
               if(!royaltyBinding.save(flush:true,flash:true)){
                      royaltyBinding.errors.each{ee->
                             println ee
                      }
               }
            }
           //调用预付费卡充值网关
            def urlStr =  grailsApplication.config.precharge.targetUrl
            def payEmail =   session.cmLoginCertificate.loginCertificate
            StringBuilder sb = new StringBuilder();
            sb = this.getPerMap(urlStr,clientNo,payEmail,reviceEmail,key)
            HttpURLConnection httpconn = null;
            String result="-20";
            BufferedReader rd = null;
             try{
                    URL url = new URL(sb.toString());
                    println "url:"+url
                    httpconn = (HttpURLConnection) url.openConnection();
                     rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
                    result = rd.readLine();
                    rd.close();
             }catch (MalformedURLException e) {
                e.printStackTrace();
                 writeInfoPage "充值失败，链接预付费卡接口超时，请稍后重试！"
                 return
             } catch (IOException e) {
                e.printStackTrace();
                 writeInfoPage "充值失败，链接预付费卡接口超时，请稍后重试！"
                 return
             } finally{
                 if(httpconn!=null)
               {
                 httpconn.disconnect();
                  httpconn=null;
                 }
              }
            if(result==null||"-20".equals(result))
            {
                    writeInfoPage "充值失败，链接预付费卡接口超时，请稍后重试！"
                    return
            }
             String[] arr = result.split("&");
            if(arr==null&&arr.length<=0){
                  writeInfoPage "充值失败，链接预付费卡接口超时，请稍后重试！"
                  return
            }
            String[] ar1 = arr[0].split("=");   //交易状态
            String[] ar2 = arr[1].split("=");   //交易代码t
            if("F".equals(ar1[1])||!"TRADE_SUCCESS".equals(ar2[1])){
                 writeInfoPage "充值失败，错误信息："+this.getMsg(ar2[1])
                 return
            }
            String[] ar3 = arr[3].split("=");    //sign
            String[] ar4 = arr[8].split("=");   //金额
            String[] ar5 = arr[11].split("=");    //充值卡号
            //验证页签
           if(verifySign(result,key,ar3[1])){
               writeInfoPage "充值成功！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;充值金额："+ar5[1]+"元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;充值卡号："+ar4[1] , 'ok'
               return
           }else{
               writeInfoPage "充值返回页签验证失败!" , 'warn'
               return
           }

    }
      protected checkMobile ={
        def cmCustomer=CmCustomer.get(session.cmCustomer.id)
        if(cmCustomer.status=='init'){
            writeInfoPage "用户状态错误，不能做预付费卡充值操作，请联系吉高客服。",'warn'
            return
        }
        if(cmCustomer.status in ['disabled','deleted']){
            writeInfoPage "用户状态错误，不能做预付费卡充值操作，请联系吉高客服。",'warn'
            session.cmCustomer=null
            return
        }
    }


     def getPerMap(def url,def partner,def payEmail,def reviceEmail,def key){

            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyymmddHHmmss");
             def createTime=  sdfTime.format(new Date())
             def orderNo = sdfTime.format(new Date())
             Map par = new HashMap();
             par.put("partner",partner)
             par.put("cardNo", params.cardNo.toString().replaceAll(" ",""))
             par.put("payEmail",payEmail)
             par.put("reviceEmail",reviceEmail)
             par.put("orderNo", orderNo)
             par.put("amount", params.amount)
            if(params.holderName!=null&&!"".equals(params.holderName.toString().replaceAll(" ",""))){
                  par.put("holderName",params.holderName.toString().replaceAll(" ",""))
            }
            if(params.note!=null&&!"".equals(params.note.toString().replaceAll(" ",""))){
                  par.put("note",params.note.toString().replaceAll(" ",""))
            }
             par.put("subType", "2")
             par.put("subject", "ReCharge")
             par.put("createTime", createTime)

           def sign=FormFunction.createMD5Sign(par,key,'utf-8')

            StringBuilder sb = new StringBuilder();
            sb.append(url+"?");
            sb.append("partner=").append(partner);
            sb.append("&cardNo=").append(params.cardNo.toString().replaceAll(" ",""));
            sb.append("&payEmail=").append(payEmail);
            sb.append("&reviceEmail=").append(reviceEmail);
            sb.append("&orderNo=").append(orderNo);
            sb.append("&amount=").append(params.amount);
            sb.append("&holderName=").append(params.holderName.toString().replaceAll(" ",""));
            sb.append("&note=").append(params.note.toString().replaceAll(" ",""));
            sb.append("&subType=").append("2");
            sb.append("&returnUrl=").append("");
            sb.append("&createTime=").append(createTime);
            sb.append("&subject=").append("ReCharge");
            sb.append("&ext1=").append("");
            sb.append("&ext2=").append("");
            sb.append("&sign_type=").append("MD5");
            sb.append("&sign=").append(sign);
        return sb
   }

    def verifySign( def str,def key,def sign){
        boolean flag = false;
        Map par = new HashMap();
        if(str==null&&!"".equals(str)) return flag;
        String[] arr = str.split("&");
        for(int i=0;i<arr.length;i++){
            String [] aa = arr[i].split("=");
            if(aa==null||aa.length<=1)   continue;
            if(aa[1]!=null&&!"".equals(aa[1])){
                if(aa[0].equals("sign"))  continue;
                 par.put(aa[0],aa[1])
            }

        }
        def mysign=FormFunction.createMD5Sign(par,key,'utf-8')
        if(mysign.equals(sign)){
              flag = true;
        }
        return flag;
    }

    def getMsg(code){

         def msg = ""

        if (code == "RECHARGE_PARAMS_ERROR") {
            msg = "参数错误！"
        } else if (code == "RECHARGE_NO_REVICEEMAIL_KEY") {
            msg = "请联系客服在预付费卡配置收款方账号信息!"
        } else if (code == "SIGN_ERROR") {
            msg = "签名错误，请联系客服！"
        } else if (code == "RECHARGE_NO_CARD") {
            msg = "无此卡！"
        } else if (code == "RECHARGE_HOLDERNAME_TOOLONG") {
            msg = "持卡人姓名过长，只能输入20个汉字！"
        } else if (code == "RECHARGE_NOTE_TOOLONG") {
            msg = "描述太长，只能输入50个汉字！"
        } else if (code == "RECHARGE_STATUS_REEOR") {
            msg = "卡状态有误！"
        } else if (code == "TRADE_ENTRUST_OUTTIME") {
            msg = "链接代扣接口超时，请稍后重试！"
        } else if (code == "TRADE_ENTRUST_SIGN_REEOR") {
            msg = "链接代扣接口，返回页签验证失败，请联系客服！"
        } else if (code == "NOT_EXIST_CUSTOMER") {
            msg = "不存在该签约客户(不是吉高用户)！"
        } else if (code == "NOT_EXIST_CUST_SIGN") {
            msg = "客户不存在！"
        } else if (code == "STATUS_CUSTOMER_SIGN") {
            msg = "客户签约状态非正常！"
        } else if (code == "PARAMS_EXIST_KEYWORDS") {
            msg = "请不要输入特殊字符跟关键字！"
        } else if (code == "SERVICE_NOT_SUPPORT") {
            msg = "您所配置的收款方未开通自助签约服务，无法充值，请先开通自助签约服务！"
        } else {
            msg = code
        }
        return msg
    }

}
