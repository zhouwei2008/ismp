package ismp

import groovy.sql.Sql
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils
import account.AcAccount
import boss.BoCustomerService
import boss.BoRefundModel
import gateway.GwTrxs

class RefundController extends BaseController{
    def refundService;
    def dataSource;
    def gwTrxsService;
    /**
     * 退款申请
     */
    def index = {
        String terminal = ""
        String channelType = ""
        def trade=TradeBase.get(params.id)
        def gwTrxs = gwTrxsService.searchGwtrxs(trade.tradeNo)
        if(!trade||(trade.payerId!=session.cmCustomer.id&&(trade.payeeId!=session.cmCustomer.id))){
            writeInfoPage "没找到该交易"
            return
        }
        def db=new Sql(DatasourcesUtils.getDataSource('ismp'))
        def payment=null;
        payment=db.firstRow("select * from gworders where id=?",[trade.tradeNo])
        if(payment?.ROYALTY_TYPE=='10') {
            writeInfoPage "分润交易不能发起退款"
            return
        }
        def waitamount = db.firstRow("select sum(amount) from refund_auth where out_trade_no=? and trade_no=? and flag=1 and status='pass'",[trade.outTradeNo,trade.tradeNo])
        long wamount = 0
        if(waitamount[0]==null){
            wamount = 0
        }else{
            wamount = waitamount[0]
        }

        // 修正
        def bcs = BoCustomerService.findWhere(customerId:session.cmCustomer.id,serviceCode:'online',isCurrent:true,enable:true)
        if(!bcs){
           writeInfoPage "没有退款服务"
           return
        }

        if(gwTrxs.size()>0){
            if(gwTrxs.get(0).get("acquirer_id")!=null  &&  !"".equals(gwTrxs.get(0).get("acquirer_id")) &&  !"null".equals(gwTrxs.get(0).get("acquirer_id"))){
                def channelList = gwTrxsService.searchGwChannel(gwTrxs.get(0).get("acquirer_id"))
                if(channelList.size()>0){
                    terminal = channelList.get(0).get("terminal")
                    channelType = channelList.get(0).get("channel_type")
                }
            }
        }
            // 加载模式
        def brm = BoRefundModel.findByCustomerServerId(bcs.id)
        def model
        if(!brm){
            model = 'recheck'
        }else{
            model = brm.refundModel
        }
        [trade:trade,order:payment,wamount:wamount,model:model,amount:params.amount,description:params.description,terminal:terminal,channelType:channelType]
    }

    /**
     * 处理退款申请
     */
    def create={
        if(!withForm{true}.invalidToken {false}){
            writeInfoPage  "请勿重复提交数据"
            return;
        }
        def trade = TradeBase.get(params.id)
        if (!trade || (trade.payerId != session.cmCustomer.id && (trade.payeeId != session.cmCustomer.id))){
            writeInfoPage "没找到该交易"
            return
        }
        def db=new Sql(DatasourcesUtils.getDataSource('ismp'))
        def payment=null;
        payment=db.firstRow("select * from gworders where id=?",[trade.tradeNo])
        if(payment?.ROYALTY_TYPE=='10') {
            writeInfoPage "分润交易不能发起退款"
            return
        }
        def msg="";
        def waitamount
        if(!params.amount) {
           msg="金额不能空";
        }else{
            if(!(params.amount==~/^(\d{0,8}+)(\.\d{1,2})?$/)){
                msg="无效金额格式"
            }else{
                def xamount=(params.amount as double)
                if(xamount<0.01) {
                    msg="金额不能小于0.01元"
                }
                waitamount = db.firstRow("select sum(amount) from refund_auth where out_trade_no=? and trade_no=? and flag=1 and status='pass'",[trade.outTradeNo,trade.tradeNo])
                long wamount = 0
                if(waitamount[0]==null){
                    wamount = 0
                }else{
                    wamount = waitamount[0]
                }
                def ableamount=(trade.amount-trade.refundAmount) as long
                xamount=(new BigDecimal(params.amount).multiply(new BigDecimal(100))) as long

                if(xamount + wamount > ableamount){
                    msg="金额 ${(xamount+wamount)/100}元不能大于可退金额 ${ableamount/100} 元"
                }
//                def payerAccount=AcAccount.findByAccountNo(session.cmCustomer.accountNo)
                def payerAccount=AcAccount.findByAccountNo(trade.payeeAccountNo)
//                 println  session.cmCustomer.accountNo
//                println trade.payeeAccountNo
                def a1= 0
                def a2 =0
                if(trade.refundAmount>0){
                    a1 =  trade.refundAmount/100
                }else{
                    a1 = 0
                }
                if(payerAccount.balance && payerAccount.balance!=''){
                    a2 = payerAccount.balance/100
                }else{
                    a2 = 0
                }
                if(a2<xamount/100){
                    //msg = "账户${trade.payeeAccountNo}余额不足"
                    //bug writeInfoPage msg
                    //return
                }
            }
            if(params.description?.length()>64){
                msg="备注字符长度超出64"
            }
        }
        if(!msg){
            if(params.model=='payPassword'){
                render (view: 'confirm', model:[trade:trade,order:payment,model:params.model,amount:params.amount,description:params.description])
                return
            }

            def amounts=new BigDecimal(params.amount).movePointRight(2)

            def refundAuth = new RefundAuth()
                refundAuth.outTradeNo = trade.outTradeNo
                refundAuth.tradeNo = trade.tradeNo
                refundAuth.amount = amounts as long
                refundAuth.customerNo = session.cmCustomer.customerNo
                refundAuth.operatorId = session.cmCustomerOperator.id
                refundAuth.uploadTime = new Date()
                refundAuth.note = params.description
                refundAuth.flag = true
                refundAuth.status = 'pass'
                refundAuth.type = 'starting'
                println "refundAccName :"+params.refundAccName+"refundAccNo :"+params.refundAccNo+"bankName :"+ params.bankName
                refundAuth.refundAccName=params.refundAccName==null?"":params.refundAccName
                refundAuth.refundAccNo=params.refundAccNo==null?"":params.refundAccNo
                refundAuth.bankName=params.bankName==null?"":params.bankName
                refundAuth.save(flush:true,failOnError: true)

                redirect(controller: "trade", action: "sale")
        }else{

            waitamount = db.firstRow("select sum(amount) from refund_auth where out_trade_no=? and trade_no=? and flag=1 and status='starting'",[trade.outTradeNo,trade.tradeNo])
            long wamount = 0
            if(waitamount[0]==null){
                wamount = 0
            }else{
                wamount = waitamount[0]
            }
            flash.message =msg;
            log.info ' msg is ' + msg
//            render(view:"index",model:[trade:trade,order:payment]);
            render(view:"index", model:[trade:trade,order:payment,wamount:wamount,model:params.model]);
        }
    }

    /**
     * 支付密码确定
     */
    def confirm = {
        if(!withForm{true}.invalidToken {false}){
            writeInfoPage  "请勿重复提交数据"
            return;
        }
        // 验证
        def msg
        if(!params.payPwd){
            msg = "请输入支付密码"
        }else{
            CmCustomerOperator cco = session.cmCustomerOperator
            log.info 'pwd is ' + (cco.id+'p'+params.payPwd)
            def pwd = (cco.id+'p'+params.payPwd).encodeAsSHA1()
            // 获取当前支付密码
            def currPwd = cco.payPassword
            log.info 'Curr pay password is ' + currPwd + " & params pay password is " + pwd
            if(!pwd.equals(currPwd)){
                msg = "支付密码不正确"
            }
        }

        def trade = TradeBase.get(params.id)
        def db=new Sql(DatasourcesUtils.getDataSource('ismp'))
        def payment=null;
        payment=db.firstRow("select * from gworders where id=?",[trade.tradeNo])
        if(!msg){
            def amounts=new BigDecimal(params.amount).movePointRight(2)
            def refundAuth = new RefundAuth()
                refundAuth.outTradeNo = trade.outTradeNo
                refundAuth.tradeNo = trade.tradeNo
                refundAuth.amount = amounts as long
                refundAuth.customerNo = session.cmCustomer.customerNo
                refundAuth.operatorId = session.cmCustomerOperator.id
                refundAuth.uploadTime = new Date()
                refundAuth.note = params.description
                refundAuth.flag = true

                def results = 1
                try{
                    results=refundService.refund(session.cmLoginCertificate,trade,refundAuth.amount as long,refundAuth.note, null)
                    refundAuth.authTime = new Date()
                    if(results.result==0){
                        refundAuth.status = 'closed'
                        refundAuth.type = 'completed'
                        refundAuth.save(flush:true,failOnError: true)
                    }else if(results.result==-1){
                        log.info 'error msg is ' + results.msg
                        flash.message = results.msg
                        render (view: 'confirm', model:[trade:trade,order:payment,model:params.model,amount:params.amount,description:params.description])
                        return
                        //[trade:trade,order:payment,model:params.model,amount:params.amount,description:params.description]
                    }
                }catch (e){
                    println e
                    writeInfoPage "平台交易号"+refundAuth.tradeNo+"退款失败"
                    return
                }
                redirect(controller: "trade", action: "refund")
        }else{
            flash.message =msg;
            log.info ' pay msg is ' + msg
//            render(view:"index",model:[trade:trade,order:payment]);
            [trade:trade,order:payment,model:params.model,amount:params.amount,description:params.description]
        }
    }

    // 返回
    def toindex = {
        redirect (actionName:"index",params:[id:params.id,amount:params.amount,description:params.description])
    }

     def refundHistory = {
        params.sort = params.sort ? params.sort : "batchDate"
        params.order = params.order ? params.order : "desc"
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.offset = params.offset ? params.int('offset') : 0
        def query = {
            eq('customerNo', session.cmCustomer.customerNo)
            if (params.batchNo) {
                eq('batchNo', params.batchNo)
            }
            if (params.startDate) {
                ge('batchDate', Date.parse("yyyy-MM-dd", params.startDate))
            }
            if (params.endDate) {
                le('batchDate', Date.parse("yyyy-MM-dd", params.endDate) + 1)
            }
        }
        def list = RefundHistory.createCriteria().list(params, query)
        def count = RefundHistory.createCriteria().count(query)
        [refundHisList: list, refundHisTotal: count]
    }

    def export = {
        def refund = RefundHistory.findById(params.id)
        def fn = request.getRealPath("/") + refund.exportPath
        log.info 'fn ' + fn
        downfile(fn)
    }

    def downfile(String filepath){
       OutputStream out=response.getOutputStream()
       byte [] bs = new byte[500]
       File fileLoad=new File(filepath)
        log.info 'path is ' + filepath + " & name is " + fileLoad.getName()
       response.setContentType("application/octet-stream;charset=gbk");
       response.setHeader("content-disposition","attachment; filename=" + fileLoad.getName());
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
}
