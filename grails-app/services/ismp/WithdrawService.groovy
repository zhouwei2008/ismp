package ismp

import trade.AccountCommandPackage
import account.AcAccount
import boss.BoInnerAccount

class WithdrawService {

    static transactional = true
    def accountClientService
    def tradeNoService

    def withdrawal(CmDynamicKey cmDynamicKey,CmLoginCertificate submitter,Long amount)
    {
        def acPackage = new AccountCommandPackage()
        //更改动态口令表信息
        cmDynamicKey.timeUsed=new Date()
        cmDynamicKey.isUsed=true
        cmDynamicKey.save(flush: true)

        //取出操作员和客户信息
        def cmCustomerOperator=submitter.customerOperator
        def cmCustomer=cmCustomerOperator.customer
        def cmCustomerBankAccount=CmCustomerBankAccount.findByCustomerAndIsDefault(cmCustomer,true)
		if(!cmCustomerBankAccount) return "您还没有设定提现银行，无法进行提现操作。"
        //1.检查账户余额是否足够提现
        def payerAccount=AcAccount.findByAccountNo(cmCustomer.accountNo)
        if(payerAccount.balance<amount) return "余额不足"
        //2.写提现交易表
        def boInnerAccount=BoInnerAccount.findByKey("middleAcc")
		log.info "boInnerAccount="+boInnerAccount
        def today=new Date()
        def trade=new TradeWithdrawn()
        trade.rootId=null
        trade.originalId=null
        trade.tradeNo=tradeNoService.createTradeNo('withdrawn',today)
        trade.tradeType='withdrawn'
        trade.partnerId=null
        trade.payerId=cmCustomer.id
        trade.payerName=cmCustomer.name
        trade.payerCode=submitter.loginCertificate
        trade.payerAccountNo=cmCustomer.accountNo
        //trade.payeeId=0
        trade.payeeName=boInnerAccount.note
        trade.payeeCode=boInnerAccount.key
        trade.payeeAccountNo=boInnerAccount.accountNo
        trade.outTradeNo=null
        trade.amount=amount
        trade.currency='CNY'
        trade.subject=null
        trade.status='processing'
        trade.tradeDate=new java.text.SimpleDateFormat('yyyyMMdd').format(today) as Integer
        trade.note=null
        //提现表属性
        trade.submitType='manual'
        trade.customerOperId=cmCustomerOperator.id
        trade.submitter=cmCustomerOperator.name
        trade.transferFee=0
        trade.realTransferAmount=0
        trade.customerBankAccountId=cmCustomerBankAccount.id
        trade.customerBankCode=cmCustomerBankAccount.bankCode
        trade.customerBankNo=cmCustomerBankAccount.bankNo
        trade.customerBankAccountName=cmCustomerBankAccount.bankAccountName
        trade.customerBankAccountNo=cmCustomerBankAccount.bankAccountNo
        trade.isCorporate=cmCustomerBankAccount.isCorporate
        trade.handleStatus='waiting'
        trade.accountProvince= cmCustomerBankAccount.accountProvince
        trade.accountCity=cmCustomerBankAccount.accountCity
        trade.save flush:true,failOnError: true

        acPackage.append trade
        acPackage.save()

        // 发送指令, 接受指令结果
        def res=null
        try{
            res=accountClientService.executeCommands(acPackage)
        }catch (e){
            e.printStackTrace()
        }
        log.info  "res="+res
        if(res)//远程调用成功
        {
            acPackage.update res
            trade.status=res.result=='true'?'processing':'closed'
            trade.save(failOnError: true)
            return res.result=='true'?'true':res.errorMsg
        }else{//远程调用失败
            log.info  "账务系统连接异常"
            return "提现操作失败"
        }
    }
}
