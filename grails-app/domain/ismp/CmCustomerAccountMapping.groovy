package ismp

class CmCustomerAccountMapping {

    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator',params: [sequence_name: 'seq_cm_customer_accountmapping', initial_value: 1])
    }

    CmCustomer customer
    String accountType
    String accountNo

    static constraints = {
        customer()
        accountType(maxSize:8)
        accountNo(maxSize:24)
    }
}
