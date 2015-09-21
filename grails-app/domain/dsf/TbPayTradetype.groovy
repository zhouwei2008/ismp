package dsf

class TbPayTradetype {
       static mapping = {
        table 'TB_PAY_TRADETYPE'
        version false
        id generator: 'assigned', column:'ID'
    }
    String payCode   // 交易code
    String payName   //交易名称
    String payType     //交易类型： 收款s 付款f
    String note     //备注

    static constraints = {
        payCode(size:1..10,nullable:true)
        payName(size:1..50,nullable:true)
        payType(size:1..1,nullable:true)
        note(size:1..100,nullable:true)
    }
    def static payTypeMap = ['S':'代收','F': '代付']
}
