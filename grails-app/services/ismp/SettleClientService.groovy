package ismp

import groovyx.net.http.HTTPBuilder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST

class SettleClientService {

  static transactional = true
  /**
   * 清结算交易请求同步调用
   * @param srvCode 业务编码
   * @param tradeCode 交易类型编码
   * @param customerNo 客户号
   * @param amount 交易金额，整数
   * @param seqNo 交易流水号
   * @param tradeDate 交易时间，格式为：yyyy-MM-dd HH:mm:ss.SSS
   * @param billDate 入账时间，格式为：yyyy-MM-dd HH:mm:ss.SSS
   * @return { result : 'true or false', errorMsg: ''}
   * result: true为成功， false 为失败,
   * errorMsg: 当result为false时，返回误原因
   * @throws Exception
   */
  def trade(srvCode, tradeCode, customerNo, amount, seqNo, tradeDate, billDate) throws Exception {
    def http = new HTTPBuilder(ConfigurationHolder.config.settle.serverUrl)
    http.request(POST, JSON) { req ->
      uri.path = 'rpc/trade'
      body = [srvCode: srvCode, tradeCode: tradeCode, customerNo: customerNo, amount: amount, seqNo: seqNo, tradeDate: tradeDate, billDate: billDate]
      response.success = { resp, json ->
        return json
      }
      response.failure = { resp ->
        throw new Exception('request error')
      }
    }
  }
}
