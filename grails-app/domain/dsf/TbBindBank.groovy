package dsf

class TbBindBank {
       static mapping = {
        table 'TB_BIND_BANK'
        version false
        id generator: 'assigned', column:'ID'
    }
    String dsfFlag   // F:代付 S:代收
    String bankAccountno//银行帐号
    String motes     //说明


    static constraints = {
        dsfFlag(size:1..1,nullable:true)
        bankAccountno(size:1..32,nullable:true)
        motes(size:1..100,nullable:true)
    }
    def static dsfFlagMap = ['S':'代收','F': '代付']
}
