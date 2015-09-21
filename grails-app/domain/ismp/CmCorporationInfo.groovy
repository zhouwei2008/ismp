package ismp

class CmCorporationInfo extends CmCustomer {

    static mapping = {
    }

    String  registrationName
    String businessLicenseCode
    String organizationCode
    String taxRegistrationNo
    Date registrationDate
    Date licenseExpires
    String businessScope
    Double registeredFunds
    String registeredPlace
    String numberOfStaff
    Double expectedTurnoverOfYear
    String checkStatus              //审核状态（未审核/审核中/审核通过/审核拒绝）
    Long checkOperatorId
    Date checkDate
    String corporate
    String companyPhone
    String officeLocation
    String contact
    String contactPhone
    String zipCode
    String note
    String belongToArea        //商户所在地区
    String belongToSale        //所属销售
    String belongToBusiness   //所属行业
    String branchCompany      //所属分公司
    String grade               //商户等级(分ABCD四个等级)
    String accessMode         //接入方式（协议/自助）
    String bizMan              //业务联系人
    String bizMPhone          //业务联系人手机
    String bizPhone           //业务联系人座机
    String bizEmail           //业务联系人邮箱
    String techMan            //技术联系人
    String techMPhone        //技术联系人手机
    String techPhone         //技术联系人座机
    String techEmail         //技术联系人邮箱
    String financeMan        //财务联系人
    String financeMPhone    //财务联系人手机
    String financePhone     //财务联系人座机
    String financeEmail     //财务联系人邮箱
    String companyWebsite   //公司接入网址
    String fraud_check       //是否校验公司接入网址（0，否；1，是）
    Boolean isViewDate = false
    //每日限额
    Double dayQutor
    //单笔限额
    Double qutor
    //每月交易
    Double monthQutor
    //交易笔数
    String qutorNum
    //每日限额累计
    Double dayQutorCount = 0
    //单笔限额累计
    Double qutorCount = 0
    //每月交易累计
    Double monthQutorCount = 0
    //交易笔数累计
    String qutorNumCount= "0"

    String controlling    //控股股东

    String  legal //法定代表人

    String identityType //证件类型

    String identityNo   //证件号码

    Date validTime  //有效期限

    Long chargeSingleLimit  //充值单笔限额

    static constraints = {
        registrationName(maxSize: 64,blank: false)
        businessLicenseCode(maxSize:20,blank:false)
        organizationCode(maxSize:20,blank:false)
        taxRegistrationNo(maxSize:20,blank:false)
        businessScope(maxSize:500,blank:false)
        registeredFunds(nullable:true)
        registeredPlace(maxSize:200,nullable:true)
        numberOfStaff(maxSize: 20,nullable: true)
        expectedTurnoverOfYear(maxSize: 20,nullable:true)
        checkStatus(maxSize:16,inList: ['waiting','processing','successed','refuse'],nullable:true)
        checkOperatorId(nullable:true)
        checkDate(nullable:true)
        corporate(maxSize:32,nullable:true)
        companyPhone(maxSize:20,nullable:true)
        officeLocation(maxSize:200,nullable:true)
        contact(maxSize:32,nullable:true)
        contactPhone(maxSize:20,nullable:true)
        zipCode(maxSize:10,nullable:true)
        note(maxSize:128,nullable:true)
        belongToBusiness(size: 1..20, blank: false)
        belongToSale(size: 1..20, blank: false)
        belongToArea(maxSize:200, blank: false)
        branchCompany(maxSize:200, blank: false)
        grade(maxSize: 16,inList: ['a','b','c','d','z'])
        accessMode(maxSize: 16,inList: ['protocol','self'],nullable:true)
        bizMan(maxSize:50,nullable:true)              //业务联系人
        bizMPhone(maxSize:20,nullable:true)          //业务联系人手机
        bizPhone(maxSize:20,nullable:true)           //业务联系人座机
        bizEmail(maxSize:200,nullable:true)           //业务联系人邮箱
        techMan(maxSize:50,nullable:true)            //技术联系人
        techMPhone(maxSize:20,nullable:true)        //技术联系人手机
        techPhone(maxSize:20,nullable:true)         //技术联系人座机
        techEmail(maxSize:200,nullable:true)         //技术联系人邮箱
        financeMan(maxSize:50,nullable:true)        //财务联系人
        financeMPhone(maxSize:20,nullable:true)    //财务联系人手机
        financePhone(maxSize:20,nullable:true)     //财务联系人座机
        financeEmail(maxSize:200,nullable:true)     //财务联系人邮箱
        companyWebsite(maxSize:200,nullable:true)   //公司接入网址
        fraud_check(maxSize:1,nullable:true)   //是否校验公司接入网址
        isViewDate(nullable:true)
        dayQutor(nullable:true)
        qutor(nullable:true)
        monthQutor(nullable:true)
        qutorNum(nullable:true)
        dayQutorCount(nullable:true)
        qutorCount(nullable:true)
        monthQutorCount(nullable:true)
        qutorNumCount(nullable:true)
        controlling(nullable:true)
        legal(nullable:true)
        identityType(maxSize: 8, blank: false)
        identityNo(nullable:true)
        validTime(nullable:true)
        chargeSingleLimit(nullable:true)
    }
}
