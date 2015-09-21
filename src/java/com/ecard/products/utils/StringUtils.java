package com.ecard.products.utils;

public class StringUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static String varargs2str(Object ... arr) {
		if (arr.length == 0) {
			return "";
		}
		
		String str = "[";
		for (Object o : arr) {
			str += (o == null ? "null" : o.toString()) + ",";
		}
		str = str.substring(0, str.length() - 1);
		str += "]";
		return str;
	}
	
	public static String varargs2str(int[] arr) {
		if (arr.length == 0) {
			return "";
		}
		String str = "[";
		for (int n : arr) {
			str += n + ",";
		}
		str = str.substring(0, str.length() - 1);
		str += "]";
		return str;
	}
	
	public static boolean verifyEquals(String src, String tar) {
		assert src == null;
		assert tar == null;
		return src.equalsIgnoreCase(tar);
		
	}
}
