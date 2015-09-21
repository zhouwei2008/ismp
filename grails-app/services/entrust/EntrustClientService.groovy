package entrust

import groovyx.net.http.HTTPBuilder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST

/**
 * 委托结算系统调用客户端
 * 配置：
 * 1. 安装grails插件rest: grails InstallPlugin rest
 * 2. 在Config.groovy中增加一行
 * account.serverUrl = "http://www.testpay.org:8000/Entrust/"
 */
class EntrustClientService {

  static transactional = true

  def http = new HTTPBuilder(ConfigurationHolder.config.entrust.serverUrl)

  /**
   * 委托结算提交订单接口调用
   * @param srvCode 业务类型,必须参数
   * @param srvChan 渠道类型,必须参数
   * @param cmrNo 商户号,必须参数
   * @param msg 订单域,必须参数
   * @return {result: 'true or false', errorMsg: ''}
   * result: true为成功， false 为失败,
   * errorMsg: 当result为false时，返回误原因,
   * @throws Exception
   */
  def openprocess(mysrvCode,mysrvChan,mycmrNo,mymsg,myfilename) throws Exception {
    http.request(POST, JSON) { req ->
      uri.path = 'enTrust/process'
      body = [srvCode: mysrvCode, srvChan:mysrvChan,cmrNo:mycmrNo,msg:mymsg,filename:myfilename]
      response.success = { resp, json ->
        return json
      }
      response.failure = { resp ->
          println(resp.toString())
          throw new Exception('连接不到委托结算系统,请重试!')

      }
    }
  }
  /**
   * 委托结算封箱订单接口调用
   * @param batchOrder 订单批次号,必须参数
   * @return {result: 'true or false', errorMsg: ''}
   * result: true为成功， false 为失败,
   * errorMsg: 当result为false时，返回误原因,
   * @throws Exception
   */
  def openpackages(mybatchOrder) throws Exception {
    http.request(POST, JSON) { req ->
      uri.path = 'enTrust/packages'
      body = [batchOrder: mybatchOrder]
      response.success = { resp, json ->
        return json
      }
      response.failure = { resp ->
        throw new Exception('连接不到委托结算系统')
      }
    }
  }
 /**
   * 委托结算审核订单接口调用
   * @param batchOrder 订单批次号,必须参数
   * @param procCmd 审核命令：pass 通过 refuse 拒绝,必须参数
   * @param batchNotes 审核意见  必须参数
   * @return {result: 'true or false', errorMsg: ''}
   * result: true为成功， false 为失败,
   * errorMsg: 当result为false时，返回误原因,
   * @throws Exception
   */
  def openverify(mybatchOrder,myprocCmd,mybatchNotes) throws Exception {
    http.request(POST, JSON) { req ->
      uri.path = 'enTrust/verify'
      body = [batchOrder:mybatchOrder,procCmd:myprocCmd,batchNotes:mybatchNotes]
      response.success = { resp, json ->
        return json
      }
      response.failure = { resp ->
        throw new Exception('连接不到委托结算系统')
      }
    }
  }
  /**
   * 委托结算修改订单接口调用
   * @param order 订单号,必须参数
   * @param msg 订单明细字段 必须参数
   * @return {result: 'true or false', errorMsg: ''}
   * result: true为成功， false 为失败,
   * errorMsg: 当result为false时，返回误原因,
   * @throws Exception
   */
  def openrenew(myorder,mycmrNo,mymsg) throws Exception {
    http.request(POST, JSON) { req ->
      uri.path = 'enTrust/renew'
      body = [order:myorder,cmrNo:mycmrNo,msg:mymsg]
      response.success = { resp, json ->
        return json
      }
      response.failure = { resp ->
        throw new Exception('连接不到委托结算系统')
      }
    }
  }

  /**
   * 委托结算删除订单接口调用
   * @param order 订单号,必须参数
   * @return {result: 'true or false', errorMsg: ''}
   * result: true为成功， false 为失败,
   * errorMsg: 当result为false时，返回误原因,
   * @throws Exception
   */
  def openremove(myorder,mycmrNo) throws Exception {
    http.request(POST, JSON) { req ->
      uri.path = 'enTrust/remove'
      body = [order:myorder,cmrNo:mycmrNo]
      response.success = { resp, json ->
        return json
      }
      response.failure = { resp ->
        throw new Exception('连接不到委托结算系统')
      }
    }
  }




}
