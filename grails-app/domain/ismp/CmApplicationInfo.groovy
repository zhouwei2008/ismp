package ismp

class CmApplicationInfo {

    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator', params: [sequence_name: 'seq_cm_application', initial_value: 1])
        tablePerHierarchy false
    }

    String companyWebsite      //公司接入网址
    String registrationName    //客户名称
    String registrationType    //客户类型
    String belongToArea        //商户所在地区
    String belongToBusiness   //所属行业
    String bizMan              //业务联系人
    String bizMPhone          //业务联系人手机
    String bizPhone           //业务联系人座机 传真
    String bizEmail           //业务联系人邮箱
    String officeLocation    //地址
    String zipCode            //邮编
    String status             //签约状态
    Date dateCreated
    Date lastUpdated

    static constraints = {
        companyWebsite(maxSize:200,nullable:false)
        registrationName(maxSize:64,nullable: false)
        registrationType(maxSize:1,inList:['P','C'],nullable: false)
        belongToArea(maxSize:200, nullable: false)
        belongToBusiness(size: 1..20, nullable: false)
        bizMan(maxSize:50,nullable:false)
        bizMPhone(maxSize:20,nullable:false)
        bizPhone(maxSize:20,nullable:true)
        bizEmail(maxSize:200,nullable:false)
        officeLocation(maxSize:200,nullable:false)
        zipCode(maxSize:10,nullable:false)
        status(maxSize:1,inList:['0','1'],nullable:false)
        dateCreated(nullable:false)
        lastUpdated(nullable:false)
    }

    def static statusMap = ['0': '未签约','1': '已签约']
    def static typeMap = ['C': '企业', 'P': '个人']
    def static businessMap = ['数字点卡': '数字点卡', '教育培训': '教育培训', '网络游戏': '网络游戏', '旅游票务': '旅游票务', '鲜花礼品': '鲜花礼品', '电子产品': '电子产品', '图书音像': '图书音像', '会员论坛': '会员论坛', '网站建设': '网站建设', '软件产品': '软件产品', '运动休闲': '运动休闲', '影视娱乐': '影视娱乐', '日常用品': '日常用品', '医疗保健': '医疗保健', '虚拟主机': '虚拟主机', '彩票': '彩票', '其他': '其他']
}
