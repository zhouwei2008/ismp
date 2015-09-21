package dsf

class TbBankcardInfo {
       static mapping = {
        table 'TB_BANKCARD_INFO'
        version false
        id generator: 'assigned', column:'ID'
    }
    String bankName    //发卡行名称
    String bankOrgCode //机构代码
    String bankCardName //卡名
    Integer bankCardLen //长度
    String bankCardVal   //取值
    String bankCardType  //卡种

    static constraints = {
        bankName(nullable:true)
        bankOrgCode(nullable:true)
        bankCardName(nullable:true)
        bankCardLen(nullable:true)
        bankCardVal(nullable:true)
        bankCardType(nullable:true)
    }
}
