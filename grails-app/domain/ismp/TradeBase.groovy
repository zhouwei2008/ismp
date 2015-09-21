package ismp

class TradeBase {

    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator', params: [sequence_name: 'seq_trade', initial_value: 1])
        tablePerHierarchy false
    }
    Date dateCreated
    Date lastUpdated
    Long rootId
    Long originalId
    String tradeNo
    String tradeType
    Long partnerId
    Long payerId
    String  payerName
    String payerCode
    String payerAccountNo
    Long payeeId
    String  payeeName
    String payeeCode
    String payeeAccountNo
    String outTradeNo
    Long amount
    Long feeAmount=0
    String currency
    String subject
    String status
    Integer tradeDate
    String  note
    String  refundApply

    static constraints = {
        rootId(nullable: true)
        originalId(nullable: true)
        tradeNo(maxSize:36,blank: false)
        tradeType(maxSize: 16,blank:false,inList: ['payment','transfer','refund','charge','withdrawn','royalty','royalty_rfd','frozen','unfrozen','settle','void'])
        partnerId(nullable: true)
        payerId(nullable: true)
        payerName(maxSize: 64,nullable: true)
        payerCode(maxSize: 64,nullable: true)
        payerAccountNo(maxSize: 24,blank:false)
        payeeId(nullable: true)
        payeeName(maxSize: 64,nullable: true)
        payeeCode(maxSize: 64,nullable: true)
        payeeAccountNo(maxSize: 24,blank:false)
        outTradeNo(maxSize: 64,nullable: true)
        amount()
        feeAmount()
        currency(maxSize: 4,blank:false)
        subject(maxSize: 256,nullable: true)
        status(maxSize: 16,inList: ['starting','processing','completed','closed','fail'])
        note(maxSize: 64,nullable: true)
        refundApply(maxSize: 64,nullable: true)
    }

    def afterInsert(){
        if(!rootId)
        {
            rootId=id
            save()
        }
    }

//    def static tradeTypeMap = ['payment': '支付', 'transfer': '转账','refund':'退款','charge':'充值','withdrawn':'提现','royalty':'分润','royalty_rfd':'退分润','frozen':'冻结','unfrozen':'解冻结','agentpay':'代付','agentcoll':'代收','settle':"结算",'void':'撤销']
    def static tradeTypeMap = ['payment': '支付', 'transfer': '转账','refund':'退款','charge':'充值','withdrawn':'提现','agentpay':'代付','settle':"结算",'void':'撤销']
    def static statusMap = ['starting': '待处理', 'processing': '处理中','completed':'完成','closed':'关闭','fail':'失败']
    def static paymentTypeMap = ['in': '入金', 'out': '出金','fee':'手续费','pay':'支付','get':'收款']

}
