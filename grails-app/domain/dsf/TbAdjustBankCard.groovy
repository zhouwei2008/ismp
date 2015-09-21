package dsf

class TbAdjustBankCard {
       static mapping = {
        table 'TB_ADJUST_BANK_CARD'
        version false
        id generator: 'assigned', column:'ID'
    }
    String bankCode    //银行代码
    String bankNames //模糊银行名称
    String note //银行名称
    String bankOrgCode //机构代码


    static constraints = {
        bankCode(nullable:true)
        bankNames(nullable:true)
        note(nullable:true)
        bankOrgCode(nullable:true)
    }
}
