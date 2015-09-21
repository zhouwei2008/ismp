package ismp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FormFunction {
    public static String BuildForm(Map<Object, Object> params, String key) {
        Map paramsNew = compressMap(params);
        StringBuffer form = new StringBuffer();
        return null;
    }

    public static boolean verifyMD5Sign(Map<Object, Object> params, String key) {
        String charset = (String) params.get("_input_charset");
        String sign = (String) params.get("sign");
        String sign_type = (String) params.get("sign_type");
        if (!"MD5".equals(params.get("sign_type")) &&
                (params.get("sign")==null || "".equals(params.get("sign")))) {}
        return sign.equals( createMD5Sign(params, key, charset) );
    }

	/**
	 * 功能：生成签名结果
	 * @param params 要签名的数组
	 * @param key 安全校验码
     * @param charset 字符编码
	 * @return 签名结果字符串
	 */
	public static String createMD5Sign(Map<Object, Object> params, String key, String charset) {
        charset = (charset==null || "".equals(charset)) ? "gbk" : charset;
		String param_str = params2string(params);  //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        System.out.println("*****************  param_str  " + param_str);
		param_str = param_str + key;                     //把拼接后的字符串再与安全校验码直接连接起来
        String sign = null;
        try {
            sign = MD5Tool.md5(param_str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
	}

    /**
     * 压缩参数表
     * @param params 参数
     * @return 压缩后的参数
     */
    public static Map<Object, Object> compressMap(Map<Object, Object> params) {
        Map<Object, Object> paramsNew = new HashMap<Object, Object>();
        for(Object key : params.keySet()) {
            Object value = params.get( key );
            if (value==null || "".equals(value) ||
                    "sign".equals(key) || "sign_type".equals(key)) {
                continue;
            }
            paramsNew.put(key, value);
        }
        return paramsNew;
    }

	/**
	 * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String params2string(Map<Object, Object> params){
        StringBuffer buffer = new StringBuffer();
        TreeMap<Object, Object> paramsSort = new TreeMap<Object, Object>(params);
        boolean first = true;
        for(Object key : paramsSort.keySet()) {
            Object value = paramsSort.get( key );
            if (first) { first = false; }
            else       { buffer.append('&'); }
            buffer.append(key).append('=').append(value);
        }
		return buffer.toString();
	}
}
