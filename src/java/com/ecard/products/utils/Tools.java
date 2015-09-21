package com.ecard.products.utils;

public class Tools {

	public static Long String2Long(String s){
		return StringUtils.isEmpty(s) ? null : Long.valueOf(s);
	}
}
