package ismp

class CmLoginLog {

    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator',params: [sequence_name: 'seq_cm_login_log', initial_value: 1])
		sort id:"desc"
    }

    CmCustomer customer
    CmCustomerOperator customerOperator
    String loginCertificate
    Date dateCreated
    String loginIp
    String loginResult

    static constraints = {
        customer()
        customerOperator()
        loginCertificate(maxSize:64,blank: false)
        loginIp(maxSize:20,blank: false)
        loginResult(maxSize:8,blank: false)
    }
}
