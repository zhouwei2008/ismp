package ismp

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Connection
import org.apache.activemq.ActiveMQConnection
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import javax.jms.Session
import javax.jms.DeliveryMode

class JmsService {

    static transactional = true

    static ActiveMQConnectionFactory connectionFactory = null
    static Connection connection = null
    static Session session = null;

   /* static {
        restartconn()
    }*/

    def static synchronized restartconn() {
        def flag = "s"
        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ConfigurationHolder.config.jms.url.toString())
            connection = connectionFactory.createConnection()
            connection.start()
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
        } catch (Exception e) {
            e.printStackTrace()
            flag = "f"
        }
        return flag
    }

    def synchronized send(msg) throws Exception {
        if (connection == null) {
            def flag ="f"// restartconn()
            if (flag == "f") {
                log.warn "结算系统暂连接不上!"
                throw new Exception("结算系统暂连接不上!")
            }
        }
        try {
            javax.jms.MessageProducer producer = session.createProducer(session.createQueue('settle'))
            producer.setDeliveryMode(DeliveryMode.PERSISTENT)
            producer.send(msg)
        } catch (Exception e) {
            connection.stop();
            connection = null;
            log.warn("send jms msg error.", e)
        }
    }

    def createMapMessage() throws Exception {
        if (connection == null) {
            def flag = "f"// restartconn()
            if (flag == "f") {
                log.warn "结算系统暂连接不上!"
                throw new Exception("结算系统暂连接不上!")
            }
        }
        return session.createMapMessage()
    }
}
