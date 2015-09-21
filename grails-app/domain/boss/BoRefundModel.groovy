package boss

class BoRefundModel {
    String refundModel
    String customerServerId
    //转账审核模式的开启、关闭
    String transferModel
    static mapping = {
        id generator: 'sequence', params: [sequence: 'seq_bo_refund_model']
    }
    static constraints = {
        refundModel(maxSize: 32, inList: ['recheck', 'payPassword'])
        transferModel(nullable:true)
    }

    def static refundModelMap = ['recheck': '二次审核', 'payPassword': '支付密码']
}
