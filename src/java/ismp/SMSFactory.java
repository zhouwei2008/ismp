package ismp;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

public class SMSFactory {
    public static ISMSTool getSMSTool(){
        try{
            String clsName= ConfigurationHolder.getConfig().getProperty("SMSImpl").toString();
            ISMSTool sms=(ISMSTool)Class.forName(clsName).newInstance();
            return sms;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
