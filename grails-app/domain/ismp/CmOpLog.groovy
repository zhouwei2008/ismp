package ismp
/**
 * 记录操作日志对象，主要记录操作员，操作时间，ip地址，操作参数等
 */
class CmOpLog {

  String controller
  String action
  String params
  String customerNo
  String account
  String ip
  Date dateCreated


  static constraints = {
  }
  static mapping = {
    params(type: "text")
  }
}
