package ismp

class TradeRefund extends TradeBase{

    String  submitBatch
    String  submitType
    Long    customerOperId
    String  submitter
    Long    backFee
    Long    realRefundAmount
    String  refundType
    String  refundParams
    String  royaltyRefundStatus
    String  acquirerCode
    String  acquirerMerchantNo
    Long    acquirer_account_id
    String  checkStatus
    Long    checkOperatorId
    Date    checkDate
    Long    handleOperId
    String  handleOperName
    String  handleBatch
    String  handleCommandNo
    String  handleStatus
    Date    handleTime
    Long  refundBatch
    String refundFlag
    String refundBankType
    String channel

    static constraints = {
        submitBatch(maxSize: 16,blank: false)
        submitType(maxSize: 32,inList: ['manual','automatic'])
        customerOperId(nullable: true)
        submitter(maxSize: 32)
        refundType(maxSize: 16,inList: ['normal','royalty'])
        refundParams(maxSize: 512,nullable:true)
        royaltyRefundStatus(maxSize: 16,inList: ['starting','processing','successed','failed'])
        acquirerCode(maxSize: 16,nullable: true)
        acquirerMerchantNo(maxSize: 20,nullable:true)
        checkStatus(maxSize: 16)
        checkOperatorId(nullable: true)
        checkDate(nullable: true)
        handleOperId(nullable: true)
        handleOperName(maxSize: 16,nullable: true)
        handleBatch(maxSize: 16,nullable: true)
        handleCommandNo(maxSize: 40,nullable: true)
        handleStatus(maxSize: 16,inList: ['waiting','checked','submited','completed'])
        handleTime(nullable: true)
        refundBatch(nullable: true)
        refundFlag nullable:true
        refundBankType(nullable: true)
        channel(nullable: true)
    }

    def static submitTypeMap = ['manual': '人工提交', 'automatic': '接口提交']
    def static refundTypeMap = ['normal': '普通退款', 'royalty': '分润退款']
    def static royaltyRefundStatusMap = ['starting': '开始', 'processing': '处理中','successed':'成功','failed':'失败']
    def static handleStatusMap = ['waiting': '待处理', 'checked': '已审核','submited':'已提交','completed':'完毕']
    def static refundBankTypeMap = ['0':'B2C线上退款','1':'B2B线上退款','2':'B2B线下退款']
}
