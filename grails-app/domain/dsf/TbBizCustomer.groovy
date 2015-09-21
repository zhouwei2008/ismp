package dsf

class TbBizCustomer {

    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator', params: [sequence_name: 'SEQ_BIZCUSTOMER', initial_value: 1])
        version false
        sort id:"desc"
    }
    String bizId
    String bizType
    String cardName
    String cardNum
    String bank
    String branchBank
    String subbranchBank
    String accountType
    String contractCode
    String province
    String city
    Date lastTime
    String remark
    String remark1
    String remark2
    String remark3
    //增加证件类型字段 wz2012-02-13
    String certificateType
    //增加证件号字段 wz2012-02-13
    String certificateNum
    //增加手机号字段 wz2012-02-13
    String tradeMobile


    static constraints = {
        bizId(nullable:true)
        bizType(size:1..1,nullable:true)
        cardName(size:1..50,nullable:true)
        cardNum(size:1..32,nullable:true)
        bank(size:1..50,nullable:true)
        branchBank(size:1..50,nullable:true)
        subbranchBank(size:1..50,nullable:true)
        accountType(size:1..1,nullable:true)
        contractCode(size:1..30,nullable:true)
        province(size:1..50,nullable:true)
        city(size:1..50,nullable:true)
        lastTime(nullable:true)
        remark(size:1..50,nullable:true)
        remark1(size:1..50,nullable:true)
        remark2(size:1..50,nullable:true)
        remark3(size:1..50,nullable:true)
        certificateType (size:1..50,nullable:true)
        certificateNum (size:1..50,nullable:true)
        tradeMobile (size:1..13,nullable:true)
    }

    def static bizTypeMap = ['S':'代收','F':'代付']
    def static accountTypeMap = ['0': '个人', '1': '企业']
    def static requiredMap = ['cardName':'客户名称','bank':'开户行','branchBank':'开户行分行','subbranchBank':'开户行支行','province':'开户行所在省','city':'开户行所在市']

    def static certificateTypeMap = ['0':'身份证','1':'户口簿','2':'护照','3':'军官证','4':'士兵证','5':'台胞证']
}
