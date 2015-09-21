package com.ecard.products.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfTime = new SimpleDateFormat("HHmm");
	
	private static SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
	
	
	public static String getDefaultDateBySDF8(){
		return sdf8.format(new Date());
	}
	

	/**
	 * 截取日期字符串
	 * 当参数为空时，默认获取当前日期
	 * 格式：YYYY-MM-DD
	 */
	public static String getDate(Date d){
		if(d == null){
			return sdf.format(new Date());
		}
		return sdf.format(d);
	}
	
	public static String getDefaultDate(){
		return sdf.format(new Date());
	}
	
	/**
	 * 获取时间字符串
	 * 当参数为空时，默认获取当前时间
	 * 格式：HHmm
	 */
	public static String  getTime(Date d){
		if(d == null){
			return sdfTime.format(new Date());
		}
		return sdfTime.format(d);
	}
	
	public static String  getDefaultTime(){
		return sdfTime.format(new Date());
	}
	
	
	public static Date string2date(String ds){
		String fmt = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			return sdf.parse(ds);
		} catch (ParseException e) {
			e.printStackTrace();
			return null; 
		}
	}
	
	public static Date addDateByOne(Date ds){
		//
		return new Date(ds.getTime()+24*3600*1000-1);     
	}

	
	public static void main(String[] args) throws ParseException {
		String str = "2011-02-24";
		String fmt = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date date = sdf.parse(str);//转换成功的Date对象
		System.out.println(date);
		
		
	}
}
