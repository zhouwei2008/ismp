package ismp;

import groovy.util.ConfigObject;
import org.apache.axis.client.Service;
import com.hnas.esb.entity.GenerateReqMsg;
import com.hnas.esb.entity.MessageHeader;
import com.hnas.esb.entity.MessageRequest;
import com.hnas.esb.entity.Parameter;
import com.hnas.event.*;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;

public class HnaSMS implements ISMSTool{
    /*
    * 短信调用
    */
    public String SendSms(String mobile,String content) throws Exception {
        ConfigObject config= ConfigurationHolder.getConfig();
        //以下参数测试与正式不一样，全部需配置化以便更改
        // ESB接口地址
        String soapUrl = config.getProperty("soapUrl").toString();
        // 路由编号
        String messageType = config.getProperty("messageType").toString();
        // 用户名
        String userId = config.getProperty("userId").toString();
        // 用户密码
        String userPwd = config.getProperty("userPwd").toString();
        // 用户私钥文件路径
        String privateKeyPath = config.getProperty("privateKeyPath").toString();

        // 业务接口参数,接口没有参数时设置为null
        Parameter[] parameter = new Parameter[6];

        //具体参数数组的长度根据《EAI短信接口入口参数要求.pdf》设定，根据查询条件实例化参数个数
        //参数实例化的构造函数为：Parameter(参数名, 参数值)
        Parameter parameter0 = new Parameter("AppName", config.getProperty("AppName").toString());
        Parameter parameter1 = new Parameter("SignString", config.getProperty("SignString").toString());
        Parameter parameter2 = new Parameter("SubServiceCode",config.getProperty("SubServiceCode").toString());
        Parameter parameter3 = new Parameter("UserName", config.getProperty("UserName").toString());
        Parameter parameter4 = new Parameter("Mobile", mobile);
        Parameter parameter5 = new Parameter("Content",content);
//        System.out.println("soapUrl="+soapUrl);
//        System.out.println("messageType="+messageType);
//        System.out.println("userId="+userId);
//        System.out.println("userPwd="+userPwd);
//        System.out.println("privateKeyPath="+privateKeyPath);
        parameter[0] = parameter0;
        parameter[1] = parameter1;
        parameter[2] = parameter2;
        parameter[3] = parameter3;
        parameter[4] = parameter4;
        parameter[5] = parameter5;

        //使用EAI的JAR生成Webservice请求消息的消息头对象
        MessageHeader messageHeader = new MessageHeader(messageType, userId,userPwd);
        //使用EAI的JAR生成Webservice请求消息对象
        MessageRequest messageRequest = new MessageRequest(messageHeader,parameter);
        try {
            //使用EAI的JAR生成Webservice请求消息
            String message = GenerateReqMsg.generate(messageRequest,privateKeyPath);

            try {
                //定义axis客户端调用服务
                Service service = new Service();
                //实例化ESB服务,ESBServiceSoapStub为接口代理类名
                ESBServiceSoapStub saopObject = new ESBServiceSoapStub(new java.net.URL(soapUrl),service);

                //调用ESB接口
                String response = saopObject.getDataFromInnerESB(message);
                return response; //response就是接口调用的返回值，再根据返回值格式及具体业务数据进行数据解析及业务操作
            }catch (Exception ex) {
                throw ex;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
