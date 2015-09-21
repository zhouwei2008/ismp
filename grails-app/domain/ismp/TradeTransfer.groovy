package ismp

class TradeTransfer extends TradeBase{

    String  submitType
    Long    customerOperId
    String  submitter
    String  submitIp
    //转账是否需要审批
    String  transferModel

    static constraints = {
        submitType(maxSize: 32,inList: ['manual','automatic'])
        submitter(maxSize: 32)
        submitIp(maxSize: 20)
        transferModel(nullable:true)
    }
    def static submitTypeMap = ['manual': '人工提交', 'automatic': '接口提交']
}
