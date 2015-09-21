package settle

/**
 * 清算单
 */
class FtLiquidate {

  //服务类型编码
  String srvCode
  //交易类型编码
  String tradeCode
  //清算金额 ，单位分
  Long amount
  //交易笔数
  Integer transNum
  //即收手续费金额
  Long preFee
  //后收手续费金额
  Long postFee
  //客户号
  String customerNo
  //清算发生时间
  Date liqDate
  //清算批次号
  String liquidateNo
  //结算批次号
  String footNo
  //手续费类型,0为即收手续费，1为后返手续费
  Integer feeType
  //结算状态：0未生成结算单，1已生成结算
  Integer status
  //后返结算批次号
  String postFootNo
  //后返结算状态：0未结算，1已结算
  Integer postFootStatus

  Date dateCreated

  //是否转账失败，true为失败，需重转账
  Boolean redo
  //结算类型：0 非实时，1 实时
  Integer settleType

  static mapping = {
    id generator: 'sequence', params: [sequence: 'seq_liquidate']
  }

  static constraints = {
    srvCode(maxSize: 20, nullable: false)
    tradeCode(maxSize: 20, nullable: false)
    customerNo(maxSize: 24, nullable: false)
    liqDate(nullable: false)
    liquidateNo(maxSize: 24, nullable: false)
    footNo(maxSize: 30, nullable: true)
    status(nullable: false)
    redo(size: 1, nullable: false)
    postFootNo(maxSize: 30, nullable: true)
    postFootStatus(nullable: false)
    settleType(nullable: true)
  }
}
