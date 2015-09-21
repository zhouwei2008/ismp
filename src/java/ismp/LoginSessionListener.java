package ismp;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginSessionListener implements HttpSessionListener,HttpSessionAttributeListener {

    // 保存当前登录的所有用户
    public static Map<HttpSession,Long> loginUser= new HashMap<HttpSession, Long>();

    // 用这个作为session中的key
    public static String SESSION_LOGIN_NAME = "user_id_key";

    //session创建时调用这个方法
    public void sessionCreated(HttpSessionEvent arg0) {
        System.out.println("sessionCreated================:"+arg0);
    }

    //Session失效或者过期的时候调用的这个方法,
    public void sessionDestroyed(HttpSessionEvent se) {
        // 如果session超时, 则从map中移除这个用户
        try {
            System.out.println("sessionDestroyed================:"+se.getSession());
            loginUser.remove(se.getSession());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //执行setAttribute的时候, 当这个属性本来不存在于Session中时, 调用这个方法.
    public void attributeAdded(HttpSessionBindingEvent se) {
        // 如果添加的属性是用户名, 则加入map中
        System.out.println("attributeAdded================:"+se.getName());
        if (se.getName().equals(SESSION_LOGIN_NAME)) {
            loginUser.put(se.getSession(), Long.valueOf(se.getValue().toString()));
        }
    }

    //当执行removeAttribute时调用的方法
    public void attributeRemoved(HttpSessionBindingEvent se) {
        // 如果移除的属性是用户名, 则从map中移除
        System.out.println("attributeRemoved================:"+se.getName());
        if (se.getName().equals(SESSION_LOGIN_NAME)) {
            try {
                loginUser.remove(se.getSession());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //当执行setAttribute时 ,如果这个属性已经存在, 覆盖属性的时候, 调用这个方法
    public void attributeReplaced(HttpSessionBindingEvent se) {
        System.out.println("attributeReplaced================:"+se.getName());
        // 如果改变的属性是用户名, 则跟着改变map
        if (se.getName().equals(SESSION_LOGIN_NAME)) {
            loginUser.put(se.getSession(), Long.valueOf(se.getValue().toString()));
        }
    }
}
