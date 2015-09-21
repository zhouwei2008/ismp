/*字符串 DESede(3DES) 加密*/
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64

class TripeDESCodec {
    private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
    private static key="passfordesnishiwode&0082".getBytes("UTF-8")

    static encode = { String target ->
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        return Base64.encode(encryptMode(key, target.getBytes()));
    }

    static decode = { String target ->
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        return new String(decryptMode(key, Base64.decode(target)));
    }

    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    private static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        }
        catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
        catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    private static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        }
        catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
        catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    //转换成十六进制字符串
    private static String byte2hex(byte[] b) {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++) {
            stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) hs=hs+"0"+stmp;
            else hs=hs+stmp;
            if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) throws Exception{

        //添加新安全算法,如果用JCE就要把它添加进去
        Security.addProvider(new com.sun.crypto.provider.SunJCE());


        String szSrc = "This is a 3DES test. 测试";
        System.out.println("加密前的字符串:" + szSrc);

        byte[] encoded = encryptMode(key, szSrc.getBytes());
        System.out.println("加密后的字符串:" + encoded.encodeBase64());

        byte[] srcBytes = decryptMode(key, encoded);
        System.out.println("解密后的字符串:" + (new String(srcBytes)));
    }
}