package settle
/**
 * 交易费率设置
 */
class FtTradeFee {
  //所属客户号
  String customerNo
  //所属业务类型
  settle.FtSrvType srv
  //所属交易类型
  settle.FtSrvTradeType tradeType
  //交易类型权重，0为负值，1为正值
  Integer tradeWeight
  //收费类型，0为即收，1为后返
  Integer fetchType
  //费率类型，0为按比收，1为按比率收
  Integer feeType
  //费率设置，费率类型为按笔收的话为每笔交易多少钱；费率类型为按比例收的话为交易金额收取费率的比例；
  Double feeValue

   

  static mapping = {
    id generator: 'sequence', params: [sequence: 'seq_trade_fee']
  }

  static constraints = {
    customerNo(maxSize: 24, nullable: false)
    tradeWeight(nullable: false)
    fetchType(nullable: false)
    feeType(nullable: false)
    feeValue(nullable: false)
  }
   
   
     def static fetchtypeMap = ['0': '即收', '1': '后返']
     def static feetypeMap = ['0': '按笔收(元)', '1': '按比率收(%)']
     def static settleMap = ['0': '固定']
     def static tradeWeightMap = [1: '收取', 0: '返还']
//     def static srvcodeMap = ['onlinepay': '在线支付', 'agentreceive': '代收']
}
