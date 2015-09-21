package dsf

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import ismp.CmCustomer
import ismp.TradeBase
import dsf.TbAgentpayDetailsInfo
import ismp.BalanceOfAccountLog
import com.ecard.products.utils.DateUtils
import com.ecard.products.utils.security.EasypayFunction
import java.util.concurrent.atomic.AtomicInteger
import com.ecard.products.pay.data.analyse.details.Remark2ResponsibilityChain
import com.ecard.products.pay.data.analyse.details.RemarkResponsibilityChain
import com.ecard.products.pay.data.analyse.details.AmountResponsibilityChain
import com.ecard.products.pay.data.analyse.details.BranchResponsibilityChain
import com.ecard.products.pay.data.analyse.details.SubBranchResponsibilityChain
import com.ecard.products.pay.data.analyse.details.BankNameResponsibilityChain
import com.ecard.products.pay.data.analyse.details.AccountTypeResponsibilityChain
import com.ecard.products.pay.data.analyse.details.AccNameResponsibilityChain
import com.ecard.products.pay.data.analyse.details.AccountResponsibilityChain
import com.ecard.products.pay.data.analyse.details.RepeatVerifyResponsibilityChain
import com.ecard.products.pay.data.analyse.details.AbsDetailResponsibilityChain
import com.ecard.products.pay.data.analyse.details.CompleteVerifyResponsibilityChain
import boss.BoAgentPayServiceParams
import com.ecard.products.constants.Constants
import account.AcAccount
import boss.BoCustomerService
import grails.converters.deep.XML
import grails.converters.deep.JSON
import javax.crypto.Cipher
import java.security.PrivateKey
import java.security.KeyStore
import sun.misc.BASE64Decoder
import org.apache.commons.lang.ArrayUtils
import java.security.PublicKey
import java.security.cert.Certificate
import sun.misc.BASE64Encoder
import java.security.cert.CertificateFactory
import java.text.SimpleDateFormat

/**
 * @deprecated 吉高代收、代付商户接口核心类
 *
 * @author tkZhu
 * @version 1.0
 */
class AgentpayController {

    def SERVER_TYPE_PAY = 'F'      //代付业务标记
    def SERVER_TYPE_COLL = 'S'     //代收业务标记
    def SERVER_TYPE_ONLINE = 'O'     //代收业务标记
    def RESP_RESULT_SUCC = 'succ' //成功
    def RESP_RESULT_FAIL = 'fail' //失败
    def agentLoaderService        //服务类
    def entrustClientService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = { }


     /*****************************************************************
      *  #代付服务接口                                                *
      *  1、商户代付送盘处理接口                                      *
      *  描述：                                                       *
      *  2、商户代付批次回盘接口                                      *
      *  描述：                                                       *
      *  3、商户代付单笔查询接口                                      *
      *  描述                                                         *
      *****************************************************************/

    /**
     * 代付单笔查询接口
     */
    def formatDate(String batchDate){ //传入数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        return sdfd.format(sdf.parse(batchDate));
    }
    def formatDateBak(String batchDate){//传出数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
        return sdfd.format(sdf.parse(batchDate));
    }


    def paysinglequery = {
         log.info '代付单笔查询接口'
         /****************** 验证商户  *********************/
        def bizId = params.batchBizid
        if(!bizId){
            respXml(RESP_RESULT_FAIL, '商户号不能为空')
            return
        }
        def cmCustomer = CmCustomer.findByCustomerNo(bizId)
        if(!cmCustomer){
            respXml(RESP_RESULT_FAIL, bizId + '商户号不存在')
            return
        }

        /****************** 验证服务  *********************/
        def boCustomerServices
        try{
            boCustomerServices = BoCustomerService.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.PAY_SERVICE,isCurrent:true,enable:true)
            if(boCustomerServices == null){
                log.warn '您没有开通代付服务'
                respXml(RESP_RESULT_FAIL, '您没有开通代付服务')
                return
            }
            log.info  ' daylimitTrans is ' + boCustomerServices.dayLimitTrans +' daylimitMon is ' + boCustomerServices.dayLimitMoney
           if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||!boCustomerServices.interfaceChannel){
                log.warn '没有设置代付服务参数'
                respXml(RESP_RESULT_FAIL, '没有设置代付服务参数，请联系管理员！')
                return
           }
           if(!boCustomerServices.certName || !boCustomerServices.certPath ){
               log.warn '没有安全证书'
               respXml(RESP_RESULT_FAIL, '没有可用的安全证书，请联系管理员！')
               return
           }
        }catch (NullPointerException e){
            e.printStackTrace()
            respXml(RESP_RESULT_FAIL, '您没有开通代付服务，请确认')
            return
        }

         /****************** 验证数据域  *********************/
        String errMsg = validateQueryParams(params, boCustomerServices, cmCustomer, SERVER_TYPE_PAY)
        if(errMsg){
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }
        if(!params.tradenum || !params.tradenum.matches('^\\d{1,6}$')){
            errMsg = '交易序号为空或格式错误'
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }

        /****************** 加载代付对象  *********************/
        /*def pays = TbAgentpayInfo.createCriteria().list {//sqlRestriction()
            eq('batchBizid', params.batchBizid)
            eq('batchType', SERVER_TYPE_PAY)
            eq('batchDate', params.batchDate)
            eq('batchCurrnum', params.batchCurrnum)
        }
        if(!pays){
            log.warn 'WARN MESSAGE : '
            respXml(RESP_RESULT_FAIL, '没有发现该批次的信息')
            return
        }*/
        def batch = new TbAgentpayInfo()
        batch.batchBizid =  params.batchBizid
        batch.batchType = SERVER_TYPE_PAY
        batch.batchDate = formatDate(params.batchDate)
        batch.batchFilename = params.batchCurrnum
        def pay = TbAgentpayInfo.find(batch)
        def detail = TbAgentpayDetailsInfo.findByBatchAndTradeNum(pay, params.tradenum)
        if(!detail){
            errMsg = '不存在该笔交易'
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }
        //log.info 'detail is ' + detail
        String _input_charset = params._input_charset? params._input_charset : 'GBK'
        def xmlResult = respDetail2Xml(detail, pay,_input_charset, 'MD5', cmCustomer.apiKey)
        println '***************************************************** \n'
        println xmlResult
        def result = encrypt(xmlResult, _input_charset, boCustomerServices.certPath)
        println 'render detail result is ' + result

       /* println '*****************************************************'
         byte[] dataReturn_r = new BASE64Decoder().decodeBuffer(result);
        final String KEYSTORE_FILE = "D:\\ssl\\client\\client.p12";
	    final String KEYSTORE_PASSWORD = "client";
        final String KEYSTORE_ALIAS = "client";
        KeyStore ks = KeyStore.getInstance("PKCS12");
	    FileInputStream fis = new FileInputStream(KEYSTORE_FILE);

	    char[] nPassword = null;
	    if((KEYSTORE_PASSWORD == null)||KEYSTORE_PASSWORD.trim().equals("")){
	    	nPassword = null;
	    }else{
	    	nPassword = KEYSTORE_PASSWORD.toCharArray();
	    }
	    ks.load(fis,nPassword);
	    fis.close();
        PrivateKey prikey = (PrivateKey)ks.getKey(KEYSTORE_ALIAS, nPassword);
        Cipher rc2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    rc2.init(Cipher.DECRYPT_MODE, prikey);
	    //byte[] rmsg2 = rc2.doFinal(dataReturn_r); // 解密后的数据
	    StringBuilder bf_r = new StringBuilder();
        for (int i = 0; i < dataReturn_r.length; i += 128) {
            byte[] doFinal = rc2.doFinal(ArrayUtils.subarray(dataReturn_r, i,i + 128));
            bf_r.append(new String(doFinal,"GBK"));
        }

	    // 打印解密字符串 - 应显示 犯大汉天威者，虽远必诛！
        System.out.println("End is ----------------------------- is \n " + bf_r.toString());*/
        render result
    }

    /**
     * 代付批次查询接口
     */
    def payquery = {
        log.info '代付批次查询接口'
         /****************** 验证商户  *********************/
        def bizId = params.batchBizid
        if(!bizId){
            respXml(RESP_RESULT_FAIL, '商户号不能为空')
            return
        }
        def cmCustomer = CmCustomer.findByCustomerNo(bizId)
        if(!cmCustomer){
            respXml(RESP_RESULT_FAIL, bizId + '商户号不存在')
            return
        }

        /****************** 验证服务  *********************/
        def boCustomerServices
        try{
            boCustomerServices = BoCustomerService.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.PAY_SERVICE,isCurrent:true,enable:true)
            if(boCustomerServices == null){
                log.warn '您没有开通代付服务'
                respXml(RESP_RESULT_FAIL, '您没有开通代付服务')
                return
            }
            log.info ' daylimitTrans is ' + boCustomerServices.dayLimitTrans +' daylimitMon is ' + boCustomerServices.dayLimitMoney
           if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||!boCustomerServices.interfaceChannel){
                log.warn '没有设置代付服务参数'
                respXml(RESP_RESULT_FAIL, '没有设置代付服务参数，请联系管理员！')
                return
           }
           if(!boCustomerServices.certName || !boCustomerServices.certPath){
               log.warn '没有安全证书'
               respXml(RESP_RESULT_FAIL, '没有可用的安全证书，请联系管理员！')
               return
           }
        }catch (NullPointerException e){
            e.printStackTrace()
            respXml(RESP_RESULT_FAIL, '您没有开通代付服务，请确认')
            return
        }

         /****************** 验证数据域  *********************/
        String errMsg = validateQueryParams(params, boCustomerServices, cmCustomer, SERVER_TYPE_PAY)
        if(errMsg){
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }

        /****************** 加载代付对象  *********************/
        def pays = TbAgentpayInfo.createCriteria().list {//sqlRestriction()
            eq('batchBizid', params.batchBizid)
            eq('batchType', SERVER_TYPE_PAY)
            eq('batchDate', formatDate(params.batchDate))
            eq('batchFilename', params.batchCurrnum)
        }
        if(!pays){
            errMsg = '没有发现该批次的信息'
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }
        String _input_charset = params._input_charset? params._input_charset : 'GBK'
        def xmlResult = respPay2Xml(pays.get(0),_input_charset,'MD5',cmCustomer.apiKey)
        def result = encrypt(xmlResult, _input_charset, boCustomerServices.certPath)
        println 'render result is ' + result
        render result
    }

    public static void main(String [] args){
        def sw = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(sw)
         xml.langs(type:"current", count:3, mainstream:true){
          language(flavor:"static", version:"1.5", "Java")
          language(flavor:"dynamic", version:"1.6.0", "Groovy")
          language(flavor:"dynamic", version:"1.9", "JavaScript")
        }
        println sw


    }
    /**
     *  代付批次处理接口
     */
    def pay = {
        log.info '代付批次提交接口'
        /****************** 验证商户  *********************/
        def bizId = params.batchBizid
        if(!bizId){
            respXml(RESP_RESULT_FAIL, '商户号不能为空')
            return
        }
        def cmCustomer = CmCustomer.findByCustomerNo(bizId)
        if(!cmCustomer){
            respXml(RESP_RESULT_FAIL, bizId + '商户号不存在')
            return
        }

        /****************** 验证服务  *********************/
        def boCustomerServices
        try{
            boCustomerServices = BoCustomerService.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.PAY_SERVICE,isCurrent:true,enable:true)
            if(boCustomerServices == null){
                log.warn '您没有开通代付服务'
                respXml(RESP_RESULT_FAIL, '您没有开通代付服务')
                return
            }
            log.info  ' daylimitTrans is ' + boCustomerServices.dayLimitTrans +' daylimitMon is ' + boCustomerServices.dayLimitMoney
           if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||!boCustomerServices.interfaceChannel){
                log.warn '没有设置代收服务参数'
                respXml(RESP_RESULT_FAIL, '没有设置代付服务参数，请联系管理员！')
                return
           }
           if(!boCustomerServices.certName || !boCustomerServices.certPath){
               log.warn '没有安全证书'
               respXml(RESP_RESULT_FAIL, '没有可用的安全证书，请联系管理员！')
               return
           }
        }catch (NullPointerException e){
            e.printStackTrace()
            respXml(RESP_RESULT_FAIL, '您没有开通代付服务，请确认')
            return
        }

        /****************** 验证数据域  *********************/
        String errMsg = validateParams(params, boCustomerServices, cmCustomer, SERVER_TYPE_PAY)
        if(errMsg){
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }

       // /****************** 构造代付对象  *********************/
       // def aPay = builderDomain(params, boCustomerServices, SERVER_TYPE_PAY)
        def msg=builderDomainnew(params, boCustomerServices, SERVER_TYPE_PAY)
        /****************** 验证账户余额  *********************/
        //double requiredAmount = aPay.batchAmount.doubleValue()
        //if(boCustomerServices.settWay.equals('0')){
        //    requiredAmount = requiredAmount + aPay.batchFee.doubleValue()
        //}
        //double balance = (AcAccount.findByAccountNo(cmCustomer.accountNo).balance/100).doubleValue()
        //if(balance < requiredAmount){
        //    log.info '现金账户余额不足'
        //    respXml(RESP_RESULT_FAIL, '您的现金账户余额不足，请充值')
        //    return
        //}
        def rtr
        /****************** 同步数据库  *********************/
        try{
             //respXml(RESP_RESULT_FAIL, '您没有开通代付服务，请确认')
             //return
            rtr=entrustClientService.openprocess("agentpay","interface",cmCustomer.customerNo,msg,params.batchCurrnum);
            if(rtr.result == 'true'){
                    println '保存成功,数据上传到系统！'
                }else if(rtr.result == 'false'){
                     println '保存失败！'
                     log.error rtr.errorMsg
                     respXml(RESP_RESULT_FAIL, rtr.errorMsg)
                     return
                }
           //agentLoaderService.saveAgentBatch(aPay, boCustomerServices, cmCustomer)
        }catch(Exception ex){
           ex.printStackTrace()
           log.warn '系统异常'
           respXml(RESP_RESULT_FAIL, '系统异常，请联系管理员')
           return
        }
        log.info 'success'
        respXml(RESP_RESULT_SUCC, null)
        return
    }


    /******************************************************************
     *  #代收服务接口                                                 *
     *  1、商户代收送盘处理接口                                       *
     *  描述：                                                        *
     *  2、商户代收批次回盘接口                                       *
     *  描述：                                                        *
     *  3、商户代收单笔查询接口                                       *
     *  描述：                                                        *
     ******************************************************************/

    /**
     * 代收单笔查询接口
     */
    def collsinglequery = {
        log.info '代收单笔查询接口'
         /****************** 验证商户  *********************/
        def bizId = params.batchBizid
        if(!bizId){
            respXml(RESP_RESULT_FAIL, '商户号不能为空')
            return
        }
        def cmCustomer = CmCustomer.findByCustomerNo(bizId)
        if(!cmCustomer){
            respXml(RESP_RESULT_FAIL, bizId + '商户号不存在')
            return
        }

        /****************** 验证服务  *********************/
        def boCustomerServices
        try{
            boCustomerServices = BoCustomerService.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.COLLECT_SERVICE,isCurrent:true,enable:true)
            if(boCustomerServices == null){
                log.warn '您没有开通代收服务'
                respXml(RESP_RESULT_FAIL, '您没有开通代收服务')
                return
            }
            log.info  ' daylimitTrans is ' + boCustomerServices.dayLimitTrans +' daylimitMon is ' + boCustomerServices.dayLimitMoney
           if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||!boCustomerServices.interfaceChannel){
                log.warn '没有设置代收服务参数'
                respXml(RESP_RESULT_FAIL, '没有设置代收服务参数，请联系管理员！')
                return
           }
           if(!boCustomerServices.certName || !boCustomerServices.certPath){
               log.warn '没有安全证书'
               respXml(RESP_RESULT_FAIL, '没有可用的安全证书，请联系管理员！')
               return
           }
        }catch (NullPointerException e){
            e.printStackTrace()
            respXml(RESP_RESULT_FAIL, '您没有开通代收服务，请确认')
            return
        }

         /****************** 验证数据域  *********************/
        String errMsg = validateQueryParams(params, boCustomerServices, cmCustomer, SERVER_TYPE_COLL)
        if(errMsg){
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }
        if(!params.tradenum || !params.tradenum.matches('^\\d{1,6}$')){
            errMsg = '交易序号为空或格式错误'
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }

        /****************** 加载代付对象  *********************/
        /*def pays = TbAgentpayInfo.createCriteria().list {//sqlRestriction()
            eq('batchBizid', params.batchBizid)
            eq('batchType', SERVER_TYPE_PAY)
            eq('batchDate', params.batchDate)
            eq('batchCurrnum', params.batchCurrnum)
        }
        if(!pays){
            log.warn 'WARN MESSAGE : '
            respXml(RESP_RESULT_FAIL, '没有发现该批次的信息')
            return
        }*/
        def batch = new TbAgentpayInfo()
        batch.batchBizid =  params.batchBizid
        batch.batchType = SERVER_TYPE_COLL
        batch.batchDate = formatDate(params.batchDate)
        batch.batchFilename = params.batchCurrnum

        def pay = TbAgentpayInfo.find(batch)
        def detail = TbAgentpayDetailsInfo.findByBatchAndTradeNum(pay, params.tradenum)
        if(!detail){
            errMsg = '不存在该笔交易'
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }
        //log.info 'detail is ' + detail
        String _input_charset = params._input_charset? params._input_charset : 'GBK'
        def xmlResult = respDetail2Xml(detail, pay,_input_charset, 'MD5', cmCustomer.apiKey)
        def result = encrypt(xmlResult, _input_charset, boCustomerServices.certPath)
        println 'render detail result is ' + result
        render result
    }

    /**
     * 代收批次查询接口
     */
    def collquery = {
        log.info '代收批次查询接口'
         /****************** 验证商户  *********************/
        def bizId = params.batchBizid
        if(!bizId){
            respXml(RESP_RESULT_FAIL, '商户号不能为空')
            return
        }
        def cmCustomer = CmCustomer.findByCustomerNo(bizId)
        if(!cmCustomer){
            respXml(RESP_RESULT_FAIL, bizId + '商户号不存在')
            return
        }

        /****************** 验证服务  *********************/
        def boCustomerServices
        try{
            boCustomerServices = BoCustomerService.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.COLLECT_SERVICE,isCurrent:true,enable:true)
            if(boCustomerServices == null){
                log.warn '您没有开通代收服务'
                respXml(RESP_RESULT_FAIL, '您没有开通代收服务')
                return
            }
            log.info  ' daylimitTrans is ' + boCustomerServices.dayLimitTrans +' daylimitMon is ' + boCustomerServices.dayLimitMoney
           if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||!boCustomerServices.interfaceChannel){
                log.warn '没有设置代收服务参数'
                respXml(RESP_RESULT_FAIL, '没有设置代收服务参数，请联系管理员！')
                return
           }
           if(!boCustomerServices.certName || !boCustomerServices.certPath ){
               log.warn '没有安全证书'
               respXml(RESP_RESULT_FAIL, '没有可用的安全证书，请联系管理员！')
               return
           }
        }catch (NullPointerException e){
            e.printStackTrace()
            respXml(RESP_RESULT_FAIL, '您没有开通代收服务，请确认')
            return
        }

         /****************** 验证数据域  *********************/
        String errMsg = validateQueryParams(params, boCustomerServices, cmCustomer, SERVER_TYPE_COLL)
        if(errMsg){
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }

        /****************** 加载代付对象  *********************/
        def pays = TbAgentpayInfo.createCriteria().list {//sqlRestriction()
            eq('batchBizid', params.batchBizid)
            eq('batchType', SERVER_TYPE_COLL)
            eq('batchDate', formatDate(params.batchDate))
            eq('batchFilename', params.batchCurrnum)
        }
        if(!pays){
            errMsg = '没有发现该批次的信息'
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }
        log.info 'ready render xml '
        String _input_charset = params._input_charset? params._input_charset : 'GBK'
        def xmlResult = respPay2Xml(pays.get(0),_input_charset,'MD5',cmCustomer.apiKey)
        def result = encrypt(xmlResult, _input_charset, boCustomerServices.certPath)
        println 'render result is \n ' + result
        render result
    }

    /**
     * 代收批次处理接口
     */
    def coll = {
        log.info '代收批次提交接口'
        /****************** 验证商户  *********************/
        def bizId = params.batchBizid
        if(!bizId){
            respXml(RESP_RESULT_FAIL, '商户号不能为空')
            return
        }
        def cmCustomer = CmCustomer.findByCustomerNo(bizId)
        if(!cmCustomer){
            respXml(RESP_RESULT_FAIL, bizId + '商户号不存在')
            return
        }

        /****************** 验证服务  *********************/
        def boCustomerServices
        try{
            boCustomerServices = BoCustomerService.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.COLLECT_SERVICE,isCurrent:true,enable:true)
            if(boCustomerServices == null){
                log.warn '您没有开通代收服务'
                respXml(RESP_RESULT_FAIL, '您没有开通代收服务')
                return
            }
            log.info  ' daylimitTrans is ' + boCustomerServices.dayLimitTrans +' daylimitMon is ' + boCustomerServices.dayLimitMoney
           if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||!boCustomerServices.interfaceChannel){
                log.warn '没有设置代收服务参数'
                respXml(RESP_RESULT_FAIL, '没有设置代收服务参数，请联系管理员！')
                return
           }
           if(!boCustomerServices.certName || !boCustomerServices.certPath){
               log.warn '没有安全证书'
               respXml(RESP_RESULT_FAIL, '没有可用的安全证书，请联系管理员！')
               return
           }
        }catch (NullPointerException e){
            e.printStackTrace()
            respXml(RESP_RESULT_FAIL, '您没有开通代收服务，请确认')
            return
        }

        /****************** 验证数据域  *********************/
        String errMsg = validateParams(params, boCustomerServices, cmCustomer, SERVER_TYPE_COLL)
        if(errMsg){
            log.warn 'WARN MESSAGE : ' + errMsg
            respXml(RESP_RESULT_FAIL, errMsg)
            return
        }
        def rtr
        /****************** 构造并同步代付对象  *********************/
        //def aPay = builderDomain(params, boCustomerServices, SERVER_TYPE_COLL)
        def msg=builderDomainnew(params, boCustomerServices, SERVER_TYPE_COLL)
        try{
            rtr=entrustClientService.openprocess("agentcoll","interface",cmCustomer.customerNo,msg,params.batchCurrnum);
             if(rtr.result == 'true'){
                    println '保存成功,数据上传到系统！'
                }else if(rtr.result == 'false'){
                     println '保存失败！'
                     log.error rtr.errorMsg
                     respXml(RESP_RESULT_FAIL, rtr.errorMsg)
                     return
                }
           //agentLoaderService.saveAgentBatch(aPay, boCustomerServices, cmCustomer)
        }catch(Exception ex){
           ex.printStackTrace()
           log.warn '系统异常'
           respXml(RESP_RESULT_FAIL, '系统异常，请联系管理员')
           return
        }
        log.info 'success'
        respXml(RESP_RESULT_SUCC, null)
        return
    }


    /******************************************************************
     *  #代收付服务辅助接口                                           *
     *  1、构造对象服务                                               *
     *  描述：                                                        *
     *  2、响应对象服务                                               *
     *  描述：                                                        *
     *  3、数据验证服务                                               *
     *  描述：                                                        *
     ******************************************************************/
    //生成数据
    def builderDomainnew(GrailsParameterMap params, BoAgentPayServiceParams service, String type){
           String content
        String _input_charset   =   params._input_charset ? params._input_charset : 'GBK'
        try{
            content = decrypt(params.batchContent, _input_charset)
         }catch(Exception ue){
            ue.printStackTrace()
            return '批量明细加密错误'
        }

        return content
    }
    /**
     * 代收、代付对象构造服务
     * @param params
     * @param service
     * @param type
     * @return
     */
    def builderDomain(GrailsParameterMap params, BoAgentPayServiceParams service, String type){
        def ai = new TbAgentpayInfo()
        ai.batchBizid    =  params.batchBizid
        ai.batchType     =  type
        ai.batchVersion  =  params.batchVersion
        ai.batchDate     =  params.batchDate
        //ai.batchCurrnum  =  params.batchCurrnum
        ai.batchCount    =  params.batchCount as Long
        ai.batchAmount   =  params.batchAmount as Double
        ai.batchBiztype  =  params.batchBiztype ? params.batchBiztype : '00000'
        ai.batchFeetype  =  service.settWay
        ai.batchFilename =  params.batchCurrnum
        String _input_charset   =   params._input_charset ? params._input_charset : 'GBK'
        //String content          =   java.net.URLDecoder.decode(params.batchContent, _input_charset)
        String content
        try{
            content = decrypt(params.batchContent, _input_charset)
         }catch(Exception ue){
            ue.printStackTrace()
            return '批量明细加密错误'
        }

        StringTokenizer st = new StringTokenizer(content, '|')
        log.info 'ccount is ' + st.countTokens()
        while(st.hasMoreTokens()){
            String s = st.nextToken()
            agentLoaderService.analyseDetail(s, ai, service)
        }
        return ai
    }

    def encrypt(String content, String input_charset, String KEYSTORE_FILE){

        byte [] msg = content.getBytes(input_charset)
		CertificateFactory cfff = CertificateFactory.getInstance("X.509");
		FileInputStream fin = new FileInputStream(KEYSTORE_FILE); // 证书文件
		Certificate cf1 = cfff.generateCertificate(fin);
		PublicKey pk5 = cf1.getPublicKey();           // 得到证书文件携带的公钥
		Cipher cc = Cipher.getInstance("RSA/ECB/PKCS1Padding");      // 定义算法：RSA
		cc.init(Cipher.PUBLIC_KEY, pk5);
		byte [] news = null;
		// 加密时超过117字节就报错。为此采用分段加密的办法来加密
        for (int i = 0; i < msg.length; i += 100) {
            byte[] doFinal = cc.doFinal(ArrayUtils.subarray(msg, i, i + 100));
            news = ArrayUtils.addAll(news, doFinal);
        }

        BASE64Encoder encoder = new sun.misc.BASE64Encoder()
        def result = encoder.encode(news)
        // 打印加密字符串
        log.info '加密后密文信息：\n ' +  result
        return result
    }

    def decrypt(String content, String input_charset) throws Exception{

        // Base64解密
        BASE64Decoder decoder = new sun.misc.BASE64Decoder()
        byte [] bs = decoder.decodeBuffer(content)

        // 用证书的私钥解密 - 该私钥存在生成该证书的密钥库中
        def keyPath = request.getRealPath("/")+"cert"+ File.separator +"server" + File.separator + Constants.ServerCert.CERTIFICATE_STORE_NAME
		FileInputStream fin = new FileInputStream(keyPath)  //d:\\ssl\\server\\tomcat.keystore
		KeyStore ks = KeyStore.getInstance("JKS")                                               // 加载证书库
		char[] ksPwd = Constants.ServerCert.CERTIFICATE_STORE_PASS.toCharArray()             // 证书库密码
		char[] keyPwd =  Constants.ServerCert.CERTIFICATE_KEY_PASS.toCharArray()             // 证书密码
		ks.load(fin, ksPwd)                                                                      // 加载证书
		PrivateKey pk = (PrivateKey)ks.getKey(Constants.ServerCert.CERTIFICATE_ALIAS, keyPwd) // 获取证书私钥
		fin.close()

        // RSA解密
		Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding")
		c.init(Cipher.DECRYPT_MODE, pk)
        // 解密时超过118字节就报错。为此采用分段解密的办法来解密
        StringBuilder buffer = new StringBuilder();
        byte [] bsAll = null
         for (int i = 0; i < bs.length; i += 128) {
             byte[] doFinal = c.doFinal(ArrayUtils.subarray(bs, i,i + 128));   // 解密后的数据
             bsAll = ArrayUtils.addAll(bsAll, doFinal)
         }
        buffer.append(new String(bsAll, input_charset));
		// 打印解密字符串
		log.info '解密后明文信息：\n ' + buffer.toString()        // 将解密数据转为字符串
        return buffer.toString()
    }

    /**
     * 构造检测链
     * @return
     */
    def buildDetailResponsibilityChain() {
        Remark2ResponsibilityChain r2rc = new Remark2ResponsibilityChain(null);
		RemarkResponsibilityChain rrc = new RemarkResponsibilityChain(r2rc);
		AmountResponsibilityChain amrc = new AmountResponsibilityChain(rrc);
		AccountTypeResponsibilityChain atrc = new AccountTypeResponsibilityChain(amrc);
		BankNameResponsibilityChain bnrc = new BankNameResponsibilityChain(atrc);
		SubBranchResponsibilityChain sbrc = new SubBranchResponsibilityChain(bnrc);
		BranchResponsibilityChain brc = new BranchResponsibilityChain(sbrc);
		AccNameResponsibilityChain anrc = new AccNameResponsibilityChain(brc);
		AccountResponsibilityChain arc = new AccountResponsibilityChain(anrc);
		RepeatVerifyResponsibilityChain rvr = new RepeatVerifyResponsibilityChain(arc);
		AbsDetailResponsibilityChain detailChain = new CompleteVerifyResponsibilityChain(rvr);
        return detailChain
	}

    /**
     * 响应
     * @param result 响应结果 成功/失败
     * @param errMsg 原因
     * @return
     */
    def respXml(String result, String errMsg){
        render(contentType:"text/xml"){
            Resp{
                if(result){
                    status(result)
                }
                if(errMsg){
                    reason(errMsg)
                }
            }
        }
        //return
    }

    /**
     * 对象转化为XML响应
     * @param o  待响应对象
     * @return
     */
    def respObj2Xml(Object o){
        def xml
        XML.use("deep"){
            xml = o as XML
            log.info 'Result is ' + xml
        }
        render xml
    }

    /**
     * 代收付批次对象转化为XML格式响应
     * @param pay 待响应对象
     * @return
     */
    def respPay2Xml(TbAgentpayInfo pay,String input_charset,String type, String key){
        def sw = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(sw)
                xml.Resp(){
                _input_charset(input_charset)
                batchBizid(pay.batchBizid)
                batchVersion(pay.batchVersion)
                batchDate(formatDateBak(pay.batchDate))
                batchCurrnum(pay.batchFilename)
                def content = new StringBuffer()
                batchContent{
                    for(TbAgentpayDetailsInfo item : pay.tbAgentpayDetailsInfos){
                        def d = item.tradeNum + ',' + item.tradeCardnum + ',' + item.tradeCardname + ',' + item.tradeBranchbank + ',' + item.tradeSubbranchbank + ',' + item.tradeAccountname + ',' + item.tradeAccounttype + ',' + item.tradeAmount + ',' +
                        item.tradeAmounttype + ',' + item.tradeRemark + ',' + (item.contractUsercode? item.contractUsercode : '')  + ',' + item.tradeRemark2 + ',' + (item.tradeFeedbackcode?item.tradeFeedbackcode : '') + ',' + (item.tradeReason? item.tradeReason : '')
                        content.append(d)
                        content.append('|')
                        detailInfo(d)
                    }
                }
                Map signMap = new HashMap()
                signMap.put('_input_charset', input_charset)
                signMap.put('batchBizid', pay.batchBizid)
                signMap.put('batchVersion', pay.batchVersion)
                signMap.put('batchDate', formatDateBak(pay.batchDate))
                signMap.put('batchCurrnum', pay.batchFilename)
                signMap.put('batchContent', content.toString())

                String mySign = EasypayFunction.BuildMysign(signMap, key, input_charset);//生成签名结果
                log.info '签名结果 ' + mySign
                sign(mySign)
                signType(type)
            }
        log.info '响应输出的批次反馈的明文信息：\n ' + sw
        return sw.toString()
    }

    /**
     * 代收付明细对象转化为XML格式响应
     * @param item 待响应对象
     * @return
     */
    def respDetail2Xml(TbAgentpayDetailsInfo item, TbAgentpayInfo pay,String input_charset, String type, String key){
        def sw = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(sw)
        xml.Resp(){
                _input_charset(input_charset)
                batchBizid(pay.batchBizid)
                batchVersion(pay.batchVersion)
                batchDate(formatDateBak(pay.batchDate))
                batchCurrnum(pay.batchFilename)
                def d = item.tradeNum + ',' + item.tradeCardnum + ',' + item.tradeCardname + ',' + item.tradeBranchbank + ',' + item.tradeSubbranchbank + ',' + item.tradeAccountname + ',' + item.tradeAccounttype + ',' + item.tradeAmount + ',' +
                        item.tradeAmounttype + ',' + item.tradeRemark + ',' + (item.contractUsercode? item.contractUsercode : '')  + ',' + item.tradeRemark2 + ',' + (item.tradeFeedbackcode?item.tradeFeedbackcode : '') + ',' + (item.tradeReason? item.tradeReason : '')
                detailInfo(d)

               Map signMap = new HashMap()
                signMap.put('_input_charset', input_charset)
                signMap.put('batchBizid', pay.batchBizid)
                signMap.put('batchVersion', pay.batchVersion)
                signMap.put('batchDate', formatDateBak(pay.batchDate))
                signMap.put('batchCurrnum', pay.batchFilename)
                signMap.put('detailInfo', d)

                String mySign = EasypayFunction.BuildMysign(signMap, key, input_charset);//生成签名结果
                log.info '签名结果 ' + mySign
                sign(mySign)
                signType(type)
        }
        log.info '响应输出的单笔反馈的明文信息 ' + sw
        return sw.toString()
    }

     /**
     * 单笔限额验证服务
     * @param s 批次明细
     * @param limit 单笔限额
     * @param c 明细所在条目数
     * @return
     */
    def analyseAmtLimit(String s, Double limit, int c){
        double amount = new BigDecimal(s.split(",")[7]).doubleValue()
        if(limit && amount > limit.doubleValue()){
            return '第' + c + "条明细单笔金额超限"
        }
        return null
    }

    /**
     * 验证数据域
     * @param params  待验证域集合
     * @param service 商户业务服务
     * @param customer 商户
     * @param type    业务类型 代收 S、代付 F
     * @return
     */
    def validateParams(GrailsParameterMap params, BoAgentPayServiceParams service, CmCustomer customer, String type){

        //版本为空
        if(!params.batchVersion){
            return  '批量版本号不能为空'
        }
        if(!params.batchVersion.matches('^\\d{2}$')){
            return '批量版本号格式错误'
        }
        //日期为空
        def d = params.batchDate
        if(!d){
            return '交易日期不能为空'
        }
        //日期格式,格式yyyyMMdd
        if(!DateUtils.getDefaultDateBySDF8().equals(d)){
            return '交易日期格式错误';
        }
        //业务类型为不为空时是否5个长度整数
        if(params.batchBiztype && !params.batchBiztype.matches('^\\d{5}$')){
            return '业务类型格式错误'
        }
        //批次号为空
        //if(!params.batchCurrnum || !params.batchCurrnum.matches('^\\d{5}$')){
        //    return '批次号不能为空或格式错误'
        //}
        //验证重复批次
        def l = TbAgentpayInfo.createCriteria().list {
            eq('batchBizid', params.batchBizid)
            eq('batchType', type)
            eq('batchDate', formatDate(d))
            eq('batchFilename', params.batchCurrnum)
        }
        if(l){
            return '重复提交批次'
        }

        //验证批量明细
        if(!params.batchContent){
            return '批量明细不能为空'
        }
         log.info 'batch old is ' + params.batchContent
        String _input_charset = params._input_charset? params._input_charset : 'GBK'
        String batchContent
        try {
            batchContent = decrypt(params.batchContent, _input_charset)
        }catch(Exception ue){
            ue.printStackTrace()
            return '批量明细尚未Encoding'
        }
        //log.info 'batch content is ' + batchContent
        AtomicInteger totalCount = new AtomicInteger(0)     //统计数量
        double totalAmount = 0                              //统计金额
        //def detailChain = buildDetailResponsibilityChain()  //验证链
        //StringTokenizer st = new StringTokenizer(batchContent, "|")
       /* while(st.hasMoreTokens()){
            String s = st.nextToken()
            // 验证明细
            String errMsg = detailChain.process(s, totalCount.incrementAndGet())
            if(errMsg){
               return errMsg
            }
            // 单笔限额
            errMsg = analyseAmtLimit(s, service.limitMoney, totalCount.get());
            if(errMsg){
                return errMsg;
            }
            totalAmount = BigDecimal.valueOf(totalAmount).add(BigDecimal.valueOf(Double.valueOf(s.split(",")[7]))).doubleValue()
        }

        //总数为空
        if(!params.batchCount || !params.batchCount.matches('^(0|[1-9][0-9]*)$')){
            return '交易总数为空或格式错误'
        }
        //总数不匹配
        if(Integer.parseInt(params.batchCount) != totalCount.get()){
            return '交易总数与明细数量不符'
        }
        //总金额为空
        if(!params.batchAmount || !params.batchAmount.matches('^\\d{1,}(\\.\\d{1,2}){0,1}$')){
            return '交易总金额为空或格式错误'
        }
        //总金额不匹配
        if(BigDecimal.valueOf(Double.valueOf(params.batchAmount)).doubleValue()!=totalAmount){
            return '交易总金额与明细总额不符'
        }

        //验证当日限制 交易数量、交易金额
        def dayCount = TbAgentpayInfo.createCriteria().get {
            projections {
                sum "batchCount"
                sum "batchAmount"
            }
            eq('batchBizid',customer.customerNo)
            eq('batchType', type)
            eq('batchDate', DateUtils.getDefaultDateBySDF8())
        }
        log.info "batchCount ${dayCount[0]} ${dayCount[1]}"
        long   currDayCount     =  dayCount[0] != null ? dayCount[0] : 0;
        double currDayAmount    =  dayCount[1] != null ? dayCount[1] : 0;
        if(currDayCount + Long.valueOf(totalCount.get()).longValue() > service.getDayLimitTrans().longValue()){
            return Constants.Verify.DETAIL_DAYCOUNTOVER_ERRMSG;
        }
        if(currDayAmount + totalAmount.doubleValue() > service.getDayLimitMoney().longValue()){
            return Constants.Verify.DETAIL_DAYAMOUNTOVER_ERRMSG;
        }*/

        // 验证签名
        /*Map reNew = params.clone()
        reNew.put('batchContent',batchContent)
        Map sParaNew = EasypayFunction.ParaFilter(reNew); //除去数组中的空值和签名参数
		String mySign = EasypayFunction.BuildMysign(sParaNew, customer.apiKey, _input_charset);//生成签名结果
        log.info "request sign is " + params.sign + " & calculate sign is " + mySign
        if(!params.sign || !params.sign.equals(mySign)){
            return '签名失败'
        }*/
        return null
    }

    /**
     * 验证查询接口数据域
     * @param params  待验证域集合
     * @param service 商户业务服务
     * @param customer 商户
     * @param type    业务类型 代收 S、代付 F
     * @return
     */
    def validateQueryParams(GrailsParameterMap params, BoAgentPayServiceParams service, CmCustomer customer, String type){
        //版本为空
        if(!params.batchVersion || !params.batchVersion.matches('^\\d{2}$')){
            return  '批量版本号为空或格式错误'
        }
        //日期为空
        def d = params.batchDate
        if(!d){
            return '交易日期为空'
        }
        //日期格式,格式yyyyMMdd
        if(!d.matches('^[1,2][0-9]{3}([0][1-9]|[1][0-2])([0][1-9]|[1-2][0-9]|[3][0-1])$')){
            return '交易日期格式错误'
        }

        //批次号为空
        if(!params.batchCurrnum){
            return '批次号不能为空或格式错误'
        }

        // 验证签名
        String _input_charset = params._input_charset? params._input_charset : 'GBK'
        Map sParaNew = EasypayFunction.ParaFilter(params); //除去数组中的空值和签名参数
		String mySign = EasypayFunction.BuildMysign(sParaNew, customer.apiKey, _input_charset);//生成签名结果
        log.info "query request param sign is " + params.sign + " & query calculate sign is " + mySign
        if(!params.sign || !params.sign.equals(mySign)){
            return '签名失败'
        }
        return null
    }

    def getCertServices(){
        def cmCustomer = session.cmCustomer
        def bo = BoAgentPayServiceParams.createCriteria()
        def serviceList = bo.list {
            eq('customerId',cmCustomer.id)
            'in'('serviceCode',[Constants.ServiceType.COLLECT_SERVICE,Constants.ServiceType.PAY_SERVICE])
            eq('isCurrent', true)
            eq('enable', true)
        }
        def serviceMap = new HashMap()
        if(serviceList)
            for(BoAgentPayServiceParams boService : serviceList){
                if(boService.serviceCode==Constants.ServiceType.COLLECT_SERVICE){
                    serviceMap.put('S', '代收证书')
                }else if(boService.serviceCode==Constants.ServiceType.PAY_SERVICE){
                    serviceMap.put('F', '代付证书')
                }else if(boService.serviceCode==Constants.ServiceType.PAY_ONLINE){
                    serviceMap.put('O', '互联网支付证书')
                }
            }
        return serviceMap
    }

    def certedit = {
        def bizId = params.id
        def serviceMap = getCertServices()
        if(!bizId){
            render(view: 'certupload',model: [serviceMap:serviceMap])
        }else{
            render(view: 'certreupload',model: [serviceMap:serviceMap,bizId:bizId])
        }
    }

    def certlist = {
        def cmCustomer = session.cmCustomer
        def bo = BoAgentPayServiceParams.createCriteria()
        def result = bo.list {
            eq('customerId',cmCustomer.id)
            'in'('serviceCode',[Constants.ServiceType.COLLECT_SERVICE,Constants.ServiceType.PAY_SERVICE])
            eq('isCurrent', true)
            eq('enable', true)
            isNotNull('certName')
            isNotNull('certPath')
        }
        [certList : result]
    }
    /**
     * 商户证书更新
     */
    def certupload = {
        def f = request.getFile('certFile')
        def type = params.type

        // 验证类型
        if(!type){
            flash.message = '请选择证书的类型'
            def serviceMap = getCertServices()
            render(view:'certupload', model: [serviceMap:serviceMap])
            return
        }

        // 验证文件
        if(f.empty){
            flash.message = '请选择证书文件'
            def serviceMap = getCertServices()
            render(view:'certupload', model: [serviceMap:serviceMap])
            return
        }
        if(!f.getOriginalFilename().endsWith('.cer')){
            flash.message = '证书文件格式不正确'
            def serviceMap = getCertServices()
            render(view:'certupload', model: [serviceMap:serviceMap])
            return
        }

        // 验证服务
        def cmCustomer = session.cmCustomer
        def boCustomerServices
        def errMsg
        try{
            def code =null;

            switch(type){

                case "F":
                    code= Constants.ServiceType.PAY_SERVICE;
                case "S":
                    code= Constants.ServiceType.COLLECT_SERVICE;
                case "O":
                    code= Constants.ServiceType.PAY_ONLINE;
            }

//           def code = type == 'F' ? Constants.ServiceType.PAY_SERVICE : Constants.ServiceType.COLLECT_SERVICE
             boCustomerServices = BoAgentPayServiceParams.findWhere(customerId:cmCustomer.id,serviceCode:code,isCurrent:true,enable:true)
             if(boCustomerServices == null){
                 switch(type){
                     case "F":
                         errMsg= '您没有开通代付服务，请确认';
                     case "S":
                         errMsg= '您没有开通代收服务，请确认';
                     case "O":
                         errMsg= '您没有开通网关支付服务，请确认';
                 }
//              errMsg = type == 'F' ? '您没有开通代付服务，请确认' : '您没有开通代收服务，请确认'
                println errMsg
                flash.message = errMsg
                def serviceMap = getCertServices()
                render(view:'certupload', model: [serviceMap:serviceMap])
                return
             }
            if(!boCustomerServices.interfaceChannel){
                switch(type){
                    case "F":
                        errMsg= '您没有开通代付服务，请确认';
                    case "S":
                        errMsg= '您没有开通代收服务，请确认';
//                    case "O":
//                        errMsg= '您没有开通网关支付服务，请确认';
                }
                //errMsg = type == 'F' ? '您没有开通代付接口服务，请确认' : '您没有开通代收接口服务，请确认'
                println errMsg
                flash.message = errMsg
                def serviceMap = getCertServices()
                render(view:'certupload', model: [serviceMap:serviceMap])
                return
            }
        }catch (NullPointerException e){
            errMsg = type == 'F' ? '您没有开通代付服务，请确认' : '您没有开通代收服务，请确认'
            println errMsg + e
            flash.message = errMsg
            def serviceMap = getCertServices()
            render(view:'certupload', model: [serviceMap:serviceMap])
            return
        }

        if(boCustomerServices.certName && boCustomerServices.certPath ){
            errMsg = type == 'F' ? '您已经提交过代付安全证书' : '您已经提交过代收安全证书'
            flash.message = errMsg
            def serviceMap = getCertServices()
            render(view:'certupload', model: [serviceMap:serviceMap])
            return
        }
        // 下载文件
        String prefix = cmCustomer.customerNo + type
        String s = request.getRealPath("/")+"cert"+ File.separator +"client" + File.separator + prefix + '.cer'
        f.transferTo(new File(s))
        // 同步 boCustomerServices
        switch(type){
            case "F":
                boCustomerServices.certName= '代付证书';
            case "S":
                boCustomerServices.certName= '代收证书';
            case "O":
                boCustomerServices.certName= '网关证书';
        }
//        boCustomerServices.certName = (type=='F' ? '代付证书' : '代收证书')
        boCustomerServices.certPath = s
        boCustomerServices.certDate = new Date()
        boCustomerServices.save(flush:true,failOnError:true)
        log.info 'save customer pay service ok'
        flash.message = '证书提交成功'
        redirect(action: certlist)
    }

    def certreupload = {
        def f = request.getFile('certFile')
        def bizId = params.bizId

        // 验证文件
        if(f.empty){
            flash.message = '请选择证书文件'
            def serviceMap = getCertServices()
            render(view:'certreupload', model: [serviceMap:serviceMap,bizId:bizId])
            return
        }
        if(!f.getOriginalFilename().endsWith('.cer')){
            flash.message = '证书文件格式不正确'
            def serviceMap = getCertServices()
            render(view:'certreupload', model: [serviceMap:serviceMap,bizId:bizId])
            return
        }

        def boService = BoAgentPayServiceParams.get(bizId as Long)
         // 下载文件
        String type = null;
        switch(boService.serviceCode) {

            case Constants.ServiceType.COLLECT_SERVICE:
                type = SERVER_TYPE_COLL;
            case Constants.ServiceType.PAY_SERVICE:
                type = SERVER_TYPE_PAY;
            case Constants.ServiceType.PAY_ONLINE:
                type = SERVER_TYPE_ONLINE;
        }

//      String type = boService.serviceCode==Constants.ServiceType.COLLECT_SERVICE ? SERVER_TYPE_COLL : SERVER_TYPE_PAY
        String prefix = session.cmCustomer.customerNo + type
        String s = request.getRealPath("/")+"cert"+ File.separator +"client" + File.separator + prefix + '.cer'
        log.info 'name all path is ' + s
        f.transferTo(new File(s))

        // 同步 boCustomerServices
        boService.certName = (type==SERVER_TYPE_COLL ? '代收证书' : '代付证书')
        boService.certPath = s
        boService.certDate = new Date()
        boService.save(flush:true,failOnError:true)
        flash.message = '证书更新成功'
        redirect(action: certlist)
    }

    /**
     *  证书下载
     */
    def downcert = {
        def filepath = request.getRealPath("/")+"cert"+ File.separator +"server" + File.separator + Constants.ServerCert.CERTIFICATE_NAME
        downfile(filepath, Constants.ServerCert.CERTIFICATE_NAME)
    }

    def downfile(String filepath, String fn){
       OutputStream out=response.getOutputStream()
       byte [] bs = new byte[500]
       File fileLoad=new File(filepath)
       response.setContentType("application/octet-stream");
       response.setHeader("content-disposition","attachment; filename=" + java.net.URLEncoder.encode(fn, "UTF-8"));
       long fileLength=fileLoad.length();
       String length1=String.valueOf(fileLength);
       response.setHeader("Content_Length",length1)
       InputStream inputStream = new FileInputStream(fileLoad)
       int len
       while((len = inputStream.read(bs))!=-1){
            out.write(bs,0,len)
        }
       inputStream.close()
       out.flush()
       out.close()
    }

    // 代付入金出金对账
    def billset = {

        try {

            log.info '代付入金出金对账单'
             /****************** 验证商户  *********************/
            def bizId = params.batchBizid
            if(!bizId){
                respXml(RESP_RESULT_FAIL, '商户号不能为空')
                return
            }
            def cmCustomer = CmCustomer.findByCustomerNo(bizId)
            if(!cmCustomer){
                respXml(RESP_RESULT_FAIL, bizId + '商户号不存在')
                return
            }

            /****************** 验证服务  *********************/
            def boCustomerServices
            try{
                boCustomerServices = BoCustomerService.findWhere(customerId:cmCustomer.id,serviceCode:Constants.ServiceType.PAY_SERVICE,isCurrent:true,enable:true)
                if(boCustomerServices == null){
                    log.warn '您没有开通代付服务'
                    respXml(RESP_RESULT_FAIL, '您没有开通代付服务')
                    return
                }
                log.info  ' daylimitTrans is ' + boCustomerServices.dayLimitTrans +' daylimitMon is ' + boCustomerServices.dayLimitMoney
               if(boCustomerServices.dayLimitTrans==null||boCustomerServices.dayLimitMoney==null||!boCustomerServices.interfaceChannel){
                    log.warn '没有设置代付服务参数'
                    respXml(RESP_RESULT_FAIL, '没有设置代付服务参数，请联系管理员！')
                    return
               }
               if(!boCustomerServices.certName || !boCustomerServices.certPath ){
                   log.warn '没有安全证书'
                   respXml(RESP_RESULT_FAIL, '没有可用的安全证书，请联系管理员！')
                   return
               }
            }catch (NullPointerException e){
                e.printStackTrace()
                respXml(RESP_RESULT_FAIL, '您没有开通代付服务，请确认')
                return
            }

             /****************** 验证数据域  *********************/
            String errMsg = ''
            //版本为空
            if(!params.batchVersion || !params.batchVersion.matches('^\\d{2}$')){
                errMsg =  '批量版本号为空或格式错误'
            }
            //日期为空
            def d = params.batchDate
            if(!d){
                errMsg = '交易日期为空'
            }
            //日期格式,格式yyyyMMdd
            if(!d.matches('^[1,2][0-9]{3}([0][1-9]|[1][0-2])([0][1-9]|[1-2][0-9]|[3][0-1])$')){
                errMsg = '交易日期格式错误'
            }

            // 验证签名
            String _input_charset = params._input_charset? params._input_charset : 'GBK'
            Map sParaNew = EasypayFunction.ParaFilter(params); //除去数组中的空值和签名参数
            String mySign = EasypayFunction.BuildMysign(sParaNew, cmCustomer.apiKey, _input_charset);//生成签名结果
            log.info "query request param sign is " + params.sign + " & query calculate sign is " + mySign
            if(!params.sign || !params.sign.equals(mySign)){
                errMsg = '签名失败'
            }

            if(!errMsg.isEmpty()){
                log.warn 'WARN MESSAGE : ' + errMsg
                respXml(RESP_RESULT_FAIL, errMsg)
                return
            }

            /***************** 记录日志 ******************/
            def balanceOfAccountLog = BalanceOfAccountLog.findByCustomNoAndRequestDate(bizId, d)
            if (balanceOfAccountLog) {
                log.warn 'WARN MESSAGE : 对账单重复请求'
                respXml(RESP_RESULT_FAIL, "当日对账单请求已发送，不可重复申请")
                return
            } else {
                balanceOfAccountLog = new BalanceOfAccountLog()
                balanceOfAccountLog.customNo = bizId
                balanceOfAccountLog.requestDate = d
                balanceOfAccountLog.requestIP = request.getHeader('X-Real-IP') ? request.getHeader('X-Real-IP') : request.getRemoteAddr()
                balanceOfAccountLog.save()
            }

//            def pa = request.getSession().getServletContext().getRealPath("/")
            def pa = grailsApplication.config.balanceOfAccountPath

            /****************** 入金对账单  *********************/
            params.sort = "dateCreated"
            params.order = "asc"

            def saleQuery = {
                eq('payeeId', cmCustomer.id)
                or {
                    eq('tradeType', 'payment')
                    eq('tradeType', 'transfer')
                }

                int year = Integer.valueOf(d.substring(0, 4))
                int month = Integer.valueOf(d.substring(4, 6)) - 1
                int day = Integer.valueOf(d.substring(6, 8))
                int hour = 0
                int minute = 0
                int second = 0
                Calendar calendar = Calendar.getInstance()
                calendar.set(year, month, day, hour, minute, second)
                ge('dateCreated', calendar.getTime())
                calendar.add(Calendar.DATE, 1)
                le('dateCreated', calendar.getTime())
            }

            def saleList = TradeBase.createCriteria().list(params, saleQuery)
            def saleCount = saleList.size()
            def saleAmount = new BigDecimal("0")
            def saleDetailInfo = new StringBuffer()
            saleList.each {
                saleAmount = saleAmount.add(new BigDecimal(String.valueOf(it.amount)))
                def outTradeNo = ''
                if (it.outTradeNo != null) {
                    outTradeNo = it.outTradeNo
                }
                saleDetailInfo.append(it.tradeNo).append("|")
                          .append(outTradeNo).append("|")
                          .append(it.dateCreated.format("yyyy-MM-dd HH:mm:ss")).append("|")
                          .append((new BigDecimal(String.valueOf(it.amount))).divide(new BigDecimal("100"))).append("\n")
            }
            saleAmount = saleAmount.divide(new BigDecimal("100"))

            File incomeFile = new File(pa + "/" + bizId + "/income/" )
            if (!incomeFile.isFile()) {
                incomeFile.mkdirs()
            }
            String saleFile = incomeFile.path + "/" + d + ".txt"
            PrintWriter salePw = new PrintWriter(new BufferedWriter(new FileWriter(saleFile)))

            salePw.print(bizId + "|" + d + "|" + saleCount + "|" + saleAmount + "\n")
            salePw.print(saleDetailInfo.toString())

            salePw.close()

            /****************** 出金对账单  *********************/
            params.sort = "tradeSubdate"
            params.order = "asc"

            def payQuery = {
                eq('batchBizid',bizId)
                eq("tradeType", "F")

                int year = Integer.valueOf(d.substring(0, 4))
                int month = Integer.valueOf(d.substring(4, 6)) - 1
                int day = Integer.valueOf(d.substring(6, 8))
                int hour = 0
                int minute = 0
                int second = 0
                Calendar calendar = Calendar.getInstance()
                calendar.set(year, month, day, hour, minute, second)
                ge('tradeSubdate', calendar.getTime())
                calendar.add(Calendar.DATE, 1)
                le('tradeSubdate', calendar.getTime())
            }

            def payList = TbAgentpayDetailsInfo.createCriteria().list(params, payQuery)
            def payCount = payList.size()
            def payAmount = new BigDecimal("0")
            def payDetailInfo = new StringBuffer()
            payList.each {
                payAmount = payAmount.add(new BigDecimal(String.valueOf(it.tradeAmount)))
//                def tradeReason = ""
//                if (it.tradeReason != null) {
//                    tradeReason = it.tradeReason
//                }
//                def tradeDonedate = ""
//                if (it.tradeDonedate != null) {
//                    tradeDonedate = it.tradeDonedate.format("yyyy-MM-dd HH:mm:ss")
//                }
                payDetailInfo.append(it.batch.batchFilename).append("|")
                          .append(it.tradeNum).append("|")
                          .append(it.tradeCardnum).append("|")
                          .append(it.tradeCardname).append("|")
                          .append(it.tradeAccountname).append("|")
                          .append(it.tradeBranchbank).append("|")
                          .append(it.tradeSubbranchbank).append("|")
                          .append(TbAgentpayDetailsInfo.accountTypeMap[it.tradeAccounttype]).append("|")
                          .append(new BigDecimal(String.valueOf(it.tradeAmount))).append("|")
                          .append(it.tradeAmounttype).append("|")
//                          .append(TbAgentpayDetailsInfo.tradeStatusMap[it.tradeStatus]).append("|")
//                          .append(tradeReason).append("|")
//                          .append(it.tradeSubdate.format("yyyy-MM-dd HH:mm:ss")).append("|")
                          .append(it.tradeSubdate.format("yyyy-MM-dd HH:mm:ss")).append("\n")
//                          .append(tradeDonedate).append("\n")
            }

            File payoutFile = new File(pa + "/" + bizId + "/payout/" )
            if (!payoutFile.isFile()) {
                payoutFile.mkdirs()
            }
            String payFile = payoutFile.path + "/" + d + ".txt"
            PrintWriter payPw = new PrintWriter(new BufferedWriter(new FileWriter(payFile)))

            payPw.print(bizId + "|" + d + "|" + payCount + "|" + payAmount + "\n")
            payPw.print(payDetailInfo.toString())

            payPw.close()

            log.warn 'SUCCESS'
            respXml(RESP_RESULT_SUCC, "验证通过，对账单生成")
            return

        } catch (Exception e) {
            log.warn 'ERROR MESSAGE : ' + e.toString()
            respXml(RESP_RESULT_FAIL, '系统异常')
            return
        }
    }

}
