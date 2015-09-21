package boss

class BoOperator {

  static mapping = {
    id generator: 'sequence', params: [sequence: 'seq_bo_operator']
  }

  String account
  String password
  String name
  String status = 'normal'
  String roleSet = 'admin'
  int loginErrorTime = 0
  Date lastLoginTime
  Date dateCreated
  Date lastUpdated

  static constraints = {
    lastLoginTime(nullable: true)
  }

  def static statusMap = ['normal': '正常', 'disabled': '停用', 'deleted': '删除']
}
