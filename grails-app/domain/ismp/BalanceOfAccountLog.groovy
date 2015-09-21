package ismp

/**
 * Created by IntelliJ IDEA.
 * User: zhaoshuang
 * Date: 13-3-25
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
class BalanceOfAccountLog {

  static mapping = {
    id generator: 'sequence', params: [sequence: 'seq_balance_of_account']
  }

  String customNo
  String requestDate
  String requestIP


    static constraints = {
        customNo(maxSize:24,nullable: false)
        requestDate(maxSize:8,nullable: false)
        requestIP(maxSize:20,nullable:false)
    }
}
