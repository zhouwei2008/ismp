package dsf

class TbAgentpayDetailsChildInfo extends TbAgentpayDetailsInfo{

    String cbusinessType
    String cpayCorname
    String cbudgetProname
    String ctradeCurrdate
    String cpayAccountcode
    String corgCode
    String cinterbankCode
    String cpeoplebankCode
    String cpayType
    String cNote
    String cNote1
    String cNote2

    static constraints = {
        cbusinessType(nullable: true)
        cpayCorname(nullable: true)
        cbudgetProname(nullable: true)
        ctradeCurrdate(nullable: true)
        cpayAccountcode(nullable: true)
        corgCode(nullable: true)
        cinterbankCode(nullable: true)
        cpeoplebankCode(nullable: true)
        cpayType(nullable: true)
        cNote(nullable: true)
        cNote1(nullable: true)
        cNote2(nullable: true)
    }

}
