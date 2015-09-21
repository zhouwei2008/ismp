package ismp

class CmLoginCertificate {

    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator',params: [sequence_name: 'seq_cm_login_certificate', initial_value: 1])
    }
    CmCustomerOperator customerOperator
    String loginCertificate
    String certificateType
    Date dateCreated
    Date lastUpdated
    Boolean isVerify

    static constraints = {
        customerOperator()
        loginCertificate(maxSize: 64,blank: false,unique: true)
        certificateType(maxSize: 8,blank:false)
    }
}
