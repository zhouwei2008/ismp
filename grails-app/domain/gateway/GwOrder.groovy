package gateway

class GwOrder {
//    ID	VARCHAR2(22 BYTE)	No	1	 订单主键
    String  id
//    SERVICE	VARCHAR2(64 BYTE)	No	2	服务名 trade_create_by_buyer
    String  service
//    ORDERNUM	VARCHAR2(128 BYTE)	No	3	订单号
    String  outTradeNo
//    PARTNERID	VARCHAR2(22 BYTE)	No	4	合作商家客户号
    String  partnerCustomerNo
//    SELLER_NAME	VARCHAR2(100 BYTE)	No	5	卖家名称
    String  sellerCode
//    SELLER_ID	VARCHAR2(22 BYTE)	No	6	卖家(收款)ID
    String  sellerCustomerNo
//    BUYER_NAME	VARCHAR2(100 BYTE)	Yes	7	买家名称
    String  buyerCode
//    BUYER_ID	VARCHAR2(22 BYTE)	Yes	8	买家(付款)ID
    String  buyerCustomerNo
//    PRICE	NUMBER	No	9	商品单价
//    QUANTITY	NUMBER	No	10	商品数量
//    SIGN_TYPE	VARCHAR2(10 BYTE)	No	11	签名类型[MD5,RSA]
    String  signType
//    DISCOUNT	NUMBER	No	12	折扣
//    DISCOUNT_MODE	VARCHAR2(10 BYTE)	Yes	13	折扣方式(红包。。）
//    DISCOUNTDESC	VARCHAR2(20 BYTE)	Yes	14	折扣说明
//    CURRENCY	VARCHAR2(4 BYTE)	No	15	币种
    String  currency
//    ORDERDATE	VARCHAR2(8 BYTE)	No	16	商家订单日期
    String  orderdate
//    CREATEDATE	DATE	No	17	创建日期
    Date    dateCreated
//    QUERY_KEY	VARCHAR2(64 BYTE)	Yes	18	查询密钥
    String  queryKey
//    EXP_DATES	VARCHAR2(5 BYTE)	No	19	订单过期表达式
    String  expires
//    IPS	VARCHAR2(20 BYTE)	No	20	订单创建IP
    String  ip
//    ORDERSTS	VARCHAR2(64 BYTE)	No	21	订单状态[0WAIT_BUYER_PAY,1WAIT_SELLER_SEND_GOODS,2WAIT_BUYER_CONFIRM_GOODS,3TRADE_FINISHED,4TRADE_CLOSED]
    String  status
//    SELLER_REMARKS	VARCHAR2(512 BYTE)	Yes	22	卖家(收款)备注
    String  sellerRemarks
//    BUYER_REMARKS	VARCHAR2(512 BYTE)	Yes	23	买家(付款)备注
    String  buyerRemarks
//    RETURN_URL	VARCHAR2(512 BYTE)	Yes	24	返回URL
    String  returnUrl
//    NOTIFY_URL	VARCHAR2(512 BYTE)	Yes	25	通知URL
    String  notifyUrl
//    SUBJECT	VARCHAR2(256 BYTE)	No	26	订单主题
    String  subject
//    BODYS	VARCHAR2(512 BYTE)	No	27	订单说明
    String  body
//    ROYALTY_TYPE	VARCHAR2(2 BYTE)	Yes	28	分润类型
    String  royaltyType
//    ROYALTY_PARAMETERS	VARCHAR2(512 BYTE)	Yes	29	分润参数
    String  royaltyParams
//    AMOUNT	NUMBER	No	30	总金额
    Long    amount
//    GWLGOPTIONS_ID	VARCHAR2(22 BYTE)	Yes	31	物流ID
//    PRICECHANGED	NUMBER(1,0)	Yes	32	价格是否调整[0未调整 1 调整]
//    APIVERSION	VARCHAR2(20 BYTE)	Yes	33	API版本[aplipay,chinabank]
    String apiversion
//    LOCALE	VARCHAR2(10 BYTE)	No	34	本地语言
//    PREFERENCE	VARCHAR2(10 BYTE)	Yes	35	偏爱银行
//    REFUND_AMOUNT	NUMBER(19,0)	Yes	36	退款金额
//    REFUND_STS	VARCHAR2(10 BYTE)	Yes	37	退款状态
//    ORDER_TYPE	VARCHAR2(3 BYTE)	No	38	订单类型[1:商品购买 01:红包结算金预收款 02红包结算金 04 自动发货商品 2 充值]
    String  orderType
//    VERSION	NUMBER	No	39	版本
//    GWL_UPDATE	DATE	No	40	物流更新时间
//    CHARSETS	VARCHAR2(10 BYTE)	No	41	字符集
    String  charset
//    PAYMETHOD	VARCHAR2(10 BYTE)	Yes	42	支付访问
//    SERVICE_FEE	NUMBER	Yes	43	服务费
//    AGENTID	VARCHAR2(22 BYTE)	Yes	44	代理商户ID
//    CLOSEDATE	DATE	Yes	45
    Date    closeDate
//    PARTNER_ID	VARCHAR2(22 BYTE)	Yes	46	合作商家系统ID
//    SHOW_URL	VARCHAR2(512 BYTE)	Yes	47	显示URL
    String  showUrl
    String  buyerRealname
//    static hasMany = [transactions: GwTransaction]

    static constraints = {}

    static mapping = {
        table 'gworders'
        cache   usage: 'read-only'
        version false
        id  generator:'assigned', type: 'string'
        outTradeNo          column: 'ORDERNUM'
        partnerCustomerNo   column: 'PARTNERID'
        sellerCode          column: 'SELLER_NAME'
        sellerCustomerNo    column: 'SELLER_ID'
        buyerCode           column: 'BUYER_NAME'
        buyerCustomerNo     column: 'BUYER_ID'
        dateCreated         column: 'CREATEDATE'
        expires             column: 'EXP_DATES'
        ip                  column: 'IPS'
        status              column: 'ORDERSTS'
        body                column: 'BODYS'
        royaltyParams       column: 'ROYALTY_PARAMETERS'
        charset             column: 'CHARSETS'
        closeDate           column: 'CLOSEDATE'
//        transactions        column: 'GWORDERS_ID'
    }

    static statusMap = ['0':'等待买家付款', '1':'等待卖家发货', '2':'等待买家确认货品', '3':'交易完成', '4':'交易失败', '5':'支付失败']
    static orderTypeMap = ['1':'商品购买', '01':'红包结算金预收款', '02':'红包结算金', '04':'自动发货商品', '2':'在商户系统充值']
    static royaltyTypeMap = ['10':'分润', '12':'合单支付']
}
