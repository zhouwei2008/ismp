package ismp

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import groovyx.net.http.ContentType

class SendService {

    static transactional = true
    def grailsTemplateEngineService

    def sendSMS(String target,String content,String userID,String userPWD) throws Exception {
        def sign = target + content + userID + userPWD
        println sign
        sign = MD5Tool.md5(sign, "UTF-8")
        def http = new HTTPBuilder(ConfigurationHolder.config.ismp.eims)
        def params=[target: target,content:URLEncoder.encode(content,"UTF-8"),userid:userID,sign:sign,mode:1,charset:"UTF-8"]
        log.info("http:"+ConfigurationHolder.config.ismp.eims)
        log.info("params:"+params)
        http.request(POST,JSON) { req ->
          requestContentType = ContentType.URLENC
          uri.path="inAccess/sms"
          body = params
          req.getParams().setParameter("http.connection.timeout", new Integer(50000));
          req.getParams().setParameter("http.socket.timeout", new Integer(50000));
          response.success = { resp, json ->
            return json
          }
          response.failure = { resp ->
            log.error resp.statusLine
            throw new Exception('request error')
          }
        }
      }

      def sendEmail(String mailtemplate,String mailTitle,String target,model) throws Exception {
          def http = new HTTPBuilder(ConfigurationHolder.config.ismp.eims)
          String txt = grailsTemplateEngineService.renderView(mailtemplate, model)
          mailTitle = URLEncoder.encode(mailTitle, 'GBK')
          def args = [to: target, subject: mailTitle, body: URLEncoder.encode(txt, 'GBK'), charset: 'GBK']
          log.info args
          http.request(POST,JSON) {req ->
              requestContentType = ContentType.URLENC
              uri.path="inAccess/email"
              body = args
              req.getParams().setParameter("http.connection.timeout", new Integer(60000));
              req.getParams().setParameter("http.socket.timeout", new Integer(60000));
              response.success = { resp, reader ->
                  return reader
              }
              response.failure = { resp ->
                  log.error resp.statusLine
                  throw new Exception('request error')
              }
          }
      }
}
