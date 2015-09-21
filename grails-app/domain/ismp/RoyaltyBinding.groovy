package ismp

class RoyaltyBinding {
    Long partnerId
    Long customerId
    String  outCustomerCode
    String  nopassRefundFlag
    String  status
    Date    dateCreated
//    //业务代码，用于区分是分润绑定还是自助签约
    String bizType

    static constraints = {
        outCustomerCode nullable: true
        nopassRefundFlag inList: ['T', 'F']
        status inList: ['bind', 'del','sign']
    }

    static mapping = {
        table 'cm_royalty_binding'
        tablePerHierarchy false
    }
}
