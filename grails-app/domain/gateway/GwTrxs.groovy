package gateway

class GwTrxs {

    static constraints = {
    }

    static mapping = {
        table 'gwtrxs'
        cache   usage: 'read-only'
        version false
    }

    static trxTypeMap = ['100':'消费']
    static channelMap = ['1':'网上银行B2C', '2':'网上银行B2B', '3':'预付费卡']
    static paymentTypeMap = ['0':'立即到账', '1':'担保']
    static payModeMap = ['0':'借记', '1':':贷记', '2':'未知']
    static trxStsMap = ['0':'等待支付', '1':'交易成功', '2':'交易失败', '3':'交易完成']
}
