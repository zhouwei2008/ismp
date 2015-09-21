package com.ecard.products.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CollectionUtils {

	public static boolean isEmpty(Collection<?>  c){
		return c == null || c.size()== 0;
	}
	
	public static boolean isEmpty(Map<?,?> m){
		return m == null || m.size() == 0;
	}
	
	public static boolean isEmpty(Object []  c){
		return c == null || c.length== 0;
	}
	
	public static boolean isNotEmpty(Collection<?>  c){
		return c != null && c.size()!= 0;
	}
	
	public static boolean isNotEmpty(Map<?,?> m){
		return m != null && m.size() != 0;
	}
	
	public static boolean isNoEmpty(Object []  c){
		return c != null && c.length!= 0;
	}
	
	
	
	public static void main(String[] args) {
		
	}
}
