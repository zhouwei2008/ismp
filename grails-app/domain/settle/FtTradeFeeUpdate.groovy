package settle
/**
 * 交易费率设置
 */
class FtTradeFeeUpdate {
  //所属客户号
  String customerNo
  //所属业务类型
  FtSrvType srv
  //所属交易类型
  FtSrvTradeType tradeType
  //交易类型权重，0为负值，1为正值
  Integer tradeWeight
  //收费类型，0为即收，1为后返
  Integer fetchType
  //费率类型，0为按比收，1为按比率收
  Integer feeType
  //费率设置，费率类型为按笔收的话为每笔交易多少钱；费率类型为按比例收的话为交易金额收取费率的比例；
  Double feeValue

  static mapping = {
    id generator: 'sequence', params: [sequence: 'seq_trade_fee'],table('ft_trade_fee')
  }

  static constraints = {
    customerNo(maxSize: 24, nullable: false)
    tradeWeight(nullable: false)
    fetchType(nullable: false)
    feeType(nullable: false)
    feeValue(nullable: false)
  }
}
