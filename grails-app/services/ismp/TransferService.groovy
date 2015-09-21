package ismp

import trade.AccountCommandPackage
import account.AcAccount
import boss.BoRefundModel
import boss.BoCustomerService

class TransferService {

    static transactional = true

    def accountClientService
    def tradeNoService

    def transfer(CmDynamicKey cmDynamicKey, CmLoginCertificate payer, CmCustomerOperator cmCustomerOperator_payer, CmLoginCertificate payee, Long amount, String transferType, String subject, String note, String IP) {
        def acPackage = new AccountCommandPackage()
        //更改动态口令表信息
        cmDynamicKey.timeUsed = new Date()
        cmDynamicKey.isUsed = true
        cmDynamicKey.save(flush: true)

        //取出付款人和收款人的操作员和客户信息
        def cmCustomer_payer = cmCustomerOperator_payer.customer
        def cmCustomerOperator_payee = payee.customerOperator
        def cmCustomer_payee = cmCustomerOperator_payee.customer
        //1.检查付款人的账户余额是否足够付款
        def payerAccount = AcAccount.findByAccountNo(cmCustomer_payer.accountNo)
        if (payerAccount.balance < amount) return "余额不足"
        def boRefundModel = new BoRefundModel()
        //判断付款人的转账是否需要审批
        def customerServer = BoCustomerService.findByCustomerIdAndServiceCode(cmCustomer_payer.id, 'online')
        if (customerServer) {
            boRefundModel = BoRefundModel.findByCustomerServerId(customerServer.id)
        }
        //2.写转账交易表
        def today = new Date()
        def tradeTransfer = new TradeTransfer()
        tradeTransfer.rootId = null
        tradeTransfer.originalId = null
        tradeTransfer.tradeNo = tradeNoService.createTradeNo('transfer', today)
        tradeTransfer.tradeType = 'transfer'
        tradeTransfer.partnerId = null
        tradeTransfer.payerId = cmCustomer_payer.id
        tradeTransfer.payerName = cmCustomer_payer.name
        tradeTransfer.payerCode = payer.loginCertificate
        tradeTransfer.payerAccountNo = cmCustomer_payer.accountNo
        tradeTransfer.payeeId = cmCustomer_payee.id
        tradeTransfer.payeeName = cmCustomer_payee.name
        tradeTransfer.payeeCode = payee.loginCertificate
        tradeTransfer.payeeAccountNo = cmCustomer_payee.accountNo
        tradeTransfer.outTradeNo = null
        tradeTransfer.amount = amount
        tradeTransfer.currency = 'CNY'
        tradeTransfer.subject = subject
        tradeTransfer.status = 'processing'
        tradeTransfer.tradeDate = new java.text.SimpleDateFormat('yyyyMMdd').format(today) as Integer
        tradeTransfer.note = note
        //转账表属性
        tradeTransfer.submitType = 'manual'
        tradeTransfer.submitter = cmCustomerOperator_payer.name
        tradeTransfer.feeAmount = 0
        tradeTransfer.submitIp = IP
        tradeTransfer.customerOperId = cmCustomerOperator_payer.id
        //open表示需要审批、close表示不需要审批
        if (boRefundModel?.transferModel == 'open') {
            tradeTransfer.transferModel = 'open'
            tradeTransfer.status = 'starting'
        } else {
            tradeTransfer.transferModel = 'close'
        }
        tradeTransfer.save(flush: true, failOnError: true)
        //tradeTransfer.rootId=tradeTransfer.id
        acPackage.append tradeTransfer
        acPackage.save()

        // 发送指令, 接受指令结果

        def res = null
        //不需要审批的直接走账务系统，进行转账
        if (tradeTransfer.transferModel == 'close') {
            try {
                res = accountClientService.executeCommands(acPackage)
            } catch (e) {
                e.printStackTrace()
            }
            log.info "res=" + res
            if (res)//远程调用成功
            {
                acPackage.update res
                tradeTransfer.status = res.result == 'true' ? 'completed' : 'closed'
                tradeTransfer.save(failOnError: true)
                return res.result == 'true' ? 'true' : res.errorMsg
            } else {//远程调用失败
                log.info "账务系统连接异常"
                return "转账失败"
            }
        } else if (tradeTransfer.transferModel == 'open') {
            //保存转账交易状态
            tradeTransfer.save(flash: true,flush:true)
            //修改 TradeAccountCommandSaf表的自动提交标识，对需要审批的要不能自动转账
            def tradeAccountCommandSaf = TradeAccountCommandSaf.findByTradeId(tradeTransfer.id)
            if (tradeAccountCommandSaf) {
                tradeAccountCommandSaf.syncFlag = 'S'
                tradeAccountCommandSaf.save(flush: true, flash: true)
            }
            return res = 'open'
        }


    }

    def transfer2(CmLoginCertificate payer, CmCustomerOperator cmCustomerOperator_payer, CmLoginCertificate payee, Long amount, String transferType, String otNo, String subject, String note, String IP) {
        def acPackage = new AccountCommandPackage()
//        //更改动态口令表信息
//        cmDynamicKey.timeUsed=new Date()
//        cmDynamicKey.isUsed=true
//        cmDynamicKey.save(flush: true)

        //取出付款人和收款人的操作员和客户信息
        def cmCustomer_payer = cmCustomerOperator_payer.customer
        def cmCustomerOperator_payee = payee.customerOperator
        def cmCustomer_payee = cmCustomerOperator_payee.customer
        //1.检查付款人的账户余额是否足够付款
        def payerAccount = AcAccount.findByAccountNo(cmCustomer_payer.accountNo)
        if (payerAccount.balance < amount) return "余额不足"
        def boRefundModel = new BoRefundModel()
        //判断付款人的转账是否需要审批
        def customerServer = BoCustomerService.findByCustomerIdAndServiceCode(cmCustomer_payer.id, 'online')
        if (customerServer) {
            boRefundModel = BoRefundModel.findByCustomerServerId(customerServer.id)
        }
        //2.写转账交易表
        def today = new Date()
        def tradeTransfer = new TradeTransfer()
        tradeTransfer.rootId = null
        tradeTransfer.originalId = null
        tradeTransfer.tradeNo = tradeNoService.createTradeNo('transfer', today)
        tradeTransfer.tradeType = 'transfer'
        tradeTransfer.partnerId = null
        tradeTransfer.payerId = cmCustomer_payer.id
        tradeTransfer.payerName = cmCustomer_payer.name
        tradeTransfer.payerCode = payer.loginCertificate
        tradeTransfer.payerAccountNo = cmCustomer_payer.accountNo
        tradeTransfer.payeeId = cmCustomer_payee.id
        tradeTransfer.payeeName = cmCustomer_payee.name
        tradeTransfer.payeeCode = payee.loginCertificate
        tradeTransfer.payeeAccountNo = cmCustomer_payee.accountNo
        tradeTransfer.outTradeNo = otNo
        tradeTransfer.amount = amount
        tradeTransfer.currency = 'CNY'
        tradeTransfer.subject = subject
        tradeTransfer.status = 'processing'
        tradeTransfer.tradeDate = new java.text.SimpleDateFormat('yyyyMMdd').format(today) as Integer
        tradeTransfer.note = note
        //转账表属性
        tradeTransfer.submitType = 'manual'
        tradeTransfer.submitter = cmCustomerOperator_payer.name
        tradeTransfer.feeAmount = 0
        tradeTransfer.submitIp = IP
        tradeTransfer.customerOperId = cmCustomerOperator_payer.id
        if (boRefundModel?.transferModel == 'open') {
            tradeTransfer.transferModel = 'open'
            tradeTransfer.status = 'starting'
        } else {
            tradeTransfer.transferModel = 'close'
        }
        tradeTransfer.save(flush: true, failOnError: true)
        //tradeTransfer.rootId=tradeTransfer.id
        acPackage.append tradeTransfer
        acPackage.save()

        // 发送指令, 接受指令结果
        def res = null
        if (boRefundModel?.transferModel == 'close') {
            try {
                res = accountClientService.executeCommands(acPackage)
            } catch (e) {
                e.printStackTrace()
            }
            log.info "res=" + res
            if (res)//远程调用成功
            {
                acPackage.update res
                tradeTransfer.status = res.result == 'true' ? 'completed' : 'closed'
                tradeTransfer.save(failOnError: true)
                return res.result == 'true' ? 'true' : res.errorMsg
            } else {//远程调用失败
                log.info "账务系统连接异常"
                return "转账失败"
            }
        } else if (tradeTransfer.transferModel == 'open') {
            tradeTransfer.save(flush: true, flash: true)
            def tradeAccountCommandSaf = TradeAccountCommandSaf.findByTradeId(tradeTransfer.id)
            if (tradeAccountCommandSaf) {
                tradeAccountCommandSaf.syncFlag = 'S'
                tradeAccountCommandSaf.save(flush: true, flash: true)
            }
            return res = 'open'
        }
    }
}
