package boss

class BoAcquirerAccount {

  static mapping = {
    id generator: 'sequence', params: [sequence: 'seq_bo_acquireraccount']
  }

  //开户银行
  BoBankDic bank
  //分行支行名称
  String branchName
  //银行行号
  String bankNo
  //银行账户
  String bankAccountNo
  //银行账户名称
  String bankAccountName
  //账户类型
  String bankAccountType
  //账户别名
  String aliasName
  //系统账户号
  String innerAcountNo
  //状态
  String status
  //创建时间
  Date dateCreated
  //更新时间
  Date lastUpdated

  static hasMany = {merchant: BoMerchant}

  static constraints = {
    branchName(size: 1..64, blank: false)
    bankNo(size: 1..16, blank: false)
    bankAccountNo(size: 1..32, blank: false)
    bankAccountName(size: 1..40, blank: false)
    bankAccountType(size: 1..16, blank: false)
    aliasName(size: 1..40, blank: false)
    innerAcountNo(size: 1..24, blank: false)
    status(size: 1..10, blank: false)
  }

  def static typeMap = ['person': '个人', 'company': '公司']
  def static statusMap = ['normal': '正常', 'disabled': '停用']
}
