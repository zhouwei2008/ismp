package ismp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MD5Tool {

    public static String md5(byte[] data) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        md5.update(data);

        byte[] encoded = md5.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encoded.length; i++) {
            if ((encoded[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(encoded[i] & 0xff, 16));
        }

        return buf.toString();
    }

    public static String md5(String str, String charset) {
        if (str == null) return null;
        try {
            return md5( str.getBytes(charset) );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalStateException("System doesn't support Charset '" + charset + "'");
        }
    }

    public static void main(String[] args) {
        String hello = "支付平台+grails+"+new Date();
        try {
            System.out.println("hello = " + hello);
            System.out.println("md5(hello.getBytes()) = " + md5(hello.getBytes()));
            System.out.println("md5(hello, \"GBK\") = " + md5(hello, "GBK"));
            System.out.println("md5(hello, \"UTF8\") = " + md5(hello, "UTF8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
