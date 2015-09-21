package ismp

import trade.AccountCommandPackage
import groovy.sql.Sql
import account.AcAccount
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils
import boss.BoInnerAccount
import java.text.SimpleDateFormat
import boss.BoCustomerService

/**
 * Created by IntelliJ IDEA.
 * User: Kitian-XIE
 * Date: 11-3-26
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
class RefundService {

    static transactional = true
    def accountClientService
    def settleClientService
    def tradeNoService
    def dataSource;
    //def jmsService
    def gwTrxsService;

    def refund(CmLoginCertificate submitter,TradeBase trade,long amount,String refunddesc,Long batch) throws Exception{

        String terminal = ""
        String channelType = ""
        def trade_payment=TradeBase.findByTradeNo(trade.tradeNo);
        log.info trade_payment.tradeNo
        //取出操作员和客户信息
        def cmCustomerOperator=submitter.customerOperator
        def cmCustomer=cmCustomerOperator.customer
        def cmCustomerBankAccount=CmCustomerBankAccount.findByCustomerAndIsDefault(cmCustomer,true)
        //1.检查账户余额是否足够退款
        if(trade_payment.refundAmount+amount>trade_payment.amount) {
              throw new Exception("refund amount is over the max")
        }
        //def payerAccount=AcAccount.findByAccountNo(cmCustomer.accountNo)
        def payerAccount=AcAccount.findByAccountNo(trade.payeeAccountNo)
        //0920SETTLE-BUG  if(payerAccount.balance<amount) return [result:-1,msg:"账户可用余额不足"]


        def gwTrxs = gwTrxsService.searchGwtrxs(trade.tradeNo)

        def refund=new TradeRefund()
        refund.rootId=trade_payment.rootId
        refund.originalId=trade_payment.id
        refund.tradeNo=tradeNoService.createTradeNo('refund',new Date())
        refund.tradeType='refund'
        refund.partnerId=trade_payment.partnerId

        refund.payerId=trade_payment.payeeId
        refund.payerName=trade_payment.payeeName
        refund.payerCode=trade_payment.payeeCode
        if(batch!=null){refund.refundBatch = batch}
        //refund.payerAccountNo=trade_payment.payeeAccountNo
        def payeesrvacct=BoCustomerService.createCriteria().list {
                                    eq("customerId",trade_payment.payeeId)
                                    eq("serviceCode","online")
                                }?.get(0)?.srvAccNo
        refund.payerAccountNo=!payeesrvacct?trade_payment.payeeAccountNo:payeesrvacct

        def guestAcc=BoInnerAccount.findByKey("guestAcc")
        def innerAcc=BoInnerAccount.findByKey("middleAcc")


        if(trade_payment.payerAccountNo.equals(guestAcc.accountNo)){
            //refund.payeeId=innerAcc.id
            refund.payeeCode=innerAcc.key
            refund.payeeName=trade_payment.payerName //innerAcc.note
            refund.payeeAccountNo=innerAcc.accountNo
        }else{
            refund.payeeId=trade_payment.payerId
            refund.payeeName=trade_payment.payerName
            refund.payeeCode=trade_payment.payerCode
            refund.payeeAccountNo=trade_payment.payerAccountNo
        }
        def random = new Random().nextInt(100)
        refund.outTradeNo= refund.tradeNo + random.toString().padLeft(2, '0')
        refund.amount=amount
        refund.currency=trade_payment.currency
        refund.subject=trade_payment.tradeNo +"退款"
        refund.status='starting'
        refund.tradeDate=new java.text.SimpleDateFormat('yyyyMMdd').format(new Date()) as Integer
        refund.refundApply = refunddesc
        refund.backFee= 0;//(trade_payment.feeAmount*amount/trade_payment.amount)   as Long

        if(gwTrxs.size()>0){
            if(gwTrxs.get(0).get("acquirer_id")!=null  &&  !"".equals(gwTrxs.get(0).get("acquirer_id")) &&  !"null".equals(gwTrxs.get(0).get("acquirer_id"))){
                def channelList = gwTrxsService.searchGwChannel(gwTrxs.get(0).get("acquirer_id"))
                if(channelList.size()>0){
                    terminal = channelList.get(0).get("terminal")
                    channelType = channelList.get(0).get("channel_type")
                }
            }
        }
        if("1".equals(channelType)){
            refund.channel='1'
            refund.refundBankType='0'
        }
        if("2".equals(channelType) && !"999999".equals(terminal)){
            refund.channel='2'
            refund.refundBankType='1'
        }
        if("2".equals(channelType) && "999999".equals(terminal)){
            refund.channel='2'
            refund.refundBankType='2'
        }
        if("3".equals(channelType)){
            refund.channel='3'
        }

        def db=new Sql(DatasourcesUtils.getDataSource('ismp'));

        db.eachRow("select t.* from gwtrxs t inner join gworders g on t.gworders_id=g.id where g.id=? and trxsts=1",[trade_payment.tradeNo]){trade_charge->
            refund.acquirerCode=trade_charge?.ACQUIRER_CODE
            refund.acquirerMerchantNo=trade_charge?.ACQUIRER_MERCHANT
            refund.acquirer_account_id=(trade_charge?.FROMACCTNUM==null?refund.payeeAccountNo:trade_charge?.FROMACCTNUM) as long
        }
        if(!refund.acquirer_account_id)   refund.acquirer_account_id=(refund.payeeAccountNo as long);
        //退款表属性        trade.id=
        refund.checkDate=null
        refund.checkOperatorId= null
        if(trade_payment.payerAccountNo.equals(guestAcc.accountNo)){
            refund.checkStatus="waiting"
        }else{
            refund.checkStatus="completed"
        }
        refund.customerOperId=cmCustomerOperator.id
        refund.handleBatch=  null
        refund.handleCommandNo=  null
        refund.handleOperId=  null
        refund.handleOperName= null
        if(trade_payment.payerAccountNo.equals(guestAcc.accountNo)){
            refund.handleStatus="waiting"
        }else{
            refund.handleStatus="completed"
        }
        refund.handleTime= new Date()
        refund.realRefundAmount=0
        refund.refundParams=  "null"
        refund.refundType= "normal"
        refund.royaltyRefundStatus=  "starting"
        refund.submitBatch= new SimpleDateFormat("mmssZ").format(new Date())

        refund.submitType= 'manual'
        refund.submitter=cmCustomerOperator.name
        refund.save failOnError: true

        // 发送指令, 接受指令结果
        def res=null
        try{
            if(accountClientService.queryAcc(refund.payeeAccountNo)?.result=='false')  //FOR TEST ACCOUNT NETWORK
               throw new Exception("账务查询出现故障,请确认后操作")
            res=settleClientService.trade("online", "refund", submitter.customerOperator.customer.customerNo,
                    refund.amount, refund.tradeNo,new Date().format('yyyy-MM-dd HH:mm:ss.SSS'), refund.handleTime.format('yyyy-MM-dd HH:mm:ss.SSS'))
        }catch (Exception e){
            e.printStackTrace()
            refund.status='closed'
            refund.save(failOnError: true)
            throw new Exception("账务预处理出现故障,请确认后操作")
        }
        log.info  "res="+res
        if(res?.result=='true')//远程调用成功
        {
           def acPackage = new AccountCommandPackage()
           acPackage.append refund
           acPackage.save()
           def res_acct=null
           try{
              res_acct=accountClientService.executeCommands(acPackage)
           }catch(e){
              throw new Exception("退款账务处理出现故障,请确认后操作")
           }
           acPackage.update res_acct
           log.info "res_acct="+res_acct

            if(res_acct?.result=='true'){
                trade_payment.refundAmount+=(refund.amount as long)
                if(trade_payment.refundAmount==trade_payment.amount){
                    trade_payment.status="closed"
                }
                trade_payment.save failOnError:true
                if(trade_payment.payerAccountNo.equals(guestAcc.accountNo))
                    refund.status='processing'
                else{
                    refund.status="completed"
                }

                if(refund.save(failOnError: true)){
                    log.info("save success");
                }
                else{
                   refund.errors.each {
                       println refund.tradeNo+"退款交易创建失败:"+it
                   }
                   throw new Exception("refund amount save failure")
                }
                return [result:0,msg:"退款申请处理成功，退款状态:"+TradeBase.statusMap[refund.status]]
            }else {
                refund.status='closed'
                refund.note=res_acct.errorMsg
                if(!refund.save(failOnError: true)){
                   refund.errors.each {
                       println refund.tradeNo+"退款交易创建失败:"+it
                   }
                   throw new Exception("refund amount save failure")
                }
                return [result:-1,msg:'退款操作失败' + ( res_acct.errorMsg==null ? '':',' + res_acct.errorMsg)]
                //throw new RuntimeException(res.errorMsg)
            }

        }else{//远程调用失败
            refund.status='closed'
            refund.note=res.errorMsg
            refund.save(failOnError: true)
            return [result:-1,msg:'退款操作失败' + (res.errorMsg==null?'':','+res.errorMsg)]
        }

    }
}
