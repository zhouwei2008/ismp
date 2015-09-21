package ismp

class TradeCharge extends TradeBase {

    static mapping = {
    }

    Long    paymentRequestId
    String  addedMethod
    Long    backAmount
    String  fundingSource
    Boolean isCreditCard
    String  paymentIp

    static constraints = {
        addedMethod(maxSize: 16)
        fundingSource(maxSize: 16)
        paymentIp(maxSize: 20)
    }
}
