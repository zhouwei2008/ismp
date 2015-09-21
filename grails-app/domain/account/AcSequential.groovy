package account

class AcSequential {
  //账户
  AcAccount account
  //账户号
  String accountNo
  //对端账户
  AcAccount oppositeAcc
  //事务
  AcTransaction transaction
  //借记金额
  Long debitAmount = 0
  //贷记金额
  Long creditAmount = 0
  //上期余额
  Long preBalance = 0
  //本期余额
  Long balance = 0
  //创建时间
  Date dateCreated
  
  static constraints = {
    accountNo(size: 1..24, blank: false)
  }

  static mapping = {
   // id generator: 'sequence', params: [sequence: 'seq_ac_sequential']
  }
}
