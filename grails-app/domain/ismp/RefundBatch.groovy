package ismp

class RefundBatch {

      static mapping = {
          id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator', params: [sequence_name: 'seq_refund_batch', initial_value: 1])
      }

          String   BatchNo
          Long     TotalAmount
          Integer  TotalCount
          boolean flag = false   //有效标识
          static hasMany = [ refundAuth : RefundAuth ]
//          static mappedBy = [refundAuth:"batch"]

//      static constraints = {
//          BatchNo(unique: true)
//          TotalAmount()
//          TotalCount()
//      }
}
