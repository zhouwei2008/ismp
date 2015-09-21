package ismp

class RefundAuth {
    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator', params: [sequence_name: 'seq_refund_auth', initial_value: 1])
    }

    String      outTradeNo
    String      tradeNo
    Long        amount
    String      customerNo
    String      operatorId
    Date        uploadTime
    String      note
    RefundBatch batch
    boolean    flag = false   //审核标识
    String      status
    String      type
    Date        authTime
    String      refundRefuse
    Integer     numberNo
    String      refundAccName  //退款账户名称
    String      refundAccNo    //退款账号
    String      bankName       //开户行名称

    static belongsTo = ismp.RefundBatch

    static constraints = {
            outTradeNo(maxSize: 64,nullable: false)
            tradeNo(maxSize:36,blank: false)
            amount()
            customerNo(maxSize:24,blank: false)
            operatorId(nullable: false)
            uploadTime(nullable: true)
            note(maxSize: 64,nullable: true)
            batch(nullable: true)
            flag()
            status(maxSize: 16,inList: ['starting','pass','closed'])
            type(maxSize: 16,inList: ['starting','completed','closed'])
            authTime(nullable: true)
            numberNo(nullable: true)
            refundRefuse(maxSize: 64,nullable: true)
            //B2B线下退款
            refundAccName(maxSize: 50,nullable: true)
            refundAccNo(maxSize: 30,nullable: true)
            bankName(maxSize: 50,nullable: true)
        }

       def static statusMap = ['starting': '未审核','pass':'审核通过','closed':'审核拒绝']
       def static typeMap = ['starting': '待审核','completed':'完成','closed':'关闭']
}
