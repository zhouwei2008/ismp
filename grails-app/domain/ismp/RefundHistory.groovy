package ismp

class RefundHistory {

   static mapping = {
       id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator', params: [sequence_name: 'seq_refund_history', initial_value: 1])
       version:false
   }

    Date batchDate
    String batchNo
    String customerNo
    Long succItems
    double succAmt
    Long failItems
    double failAmt
    String exportPath
    String note
    String note1
    String note2

    static constraints = {
        batchDate(nullable: false)
        batchNo(nullable: false)
        customerNo(nullable: false)
        succItems(nullable: true)
        succAmt(nullable: false)
        failItems(nullable: true)
        failAmt(nullable: false)
        exportPath(nullable: true)
        note(nullable: true)
        note1(nullable: true)
        note2(nullable: true)
    }
}
