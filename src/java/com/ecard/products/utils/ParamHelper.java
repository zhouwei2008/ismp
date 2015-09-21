package com.ecard.products.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;


public class ParamHelper {
	private static final Logger logger = Logger.getLogger(ParamHelper.class);
	/**
	 * 把字符串转化成Double
	 * @param src
	 * @return
	 */
	public static Double str2Double(String src){
		if(src==null || src.trim().length()==0){
			return null;
		}
		try {
			Double d = Double.valueOf(src);
			return d;
		} catch (NumberFormatException e) {
			logger.error("参数字符串转成Double时转化异常", e);
			return null;
		}
	}
	/**
	 * 把字符串转化成Integer
	 * @param src
	 * @return
	 */
	public static Integer str2Integer(String src){
		if(src==null || src.trim().length()==0){
			return null;
		}
		try {
			Integer i = Integer.valueOf(src);
			return i;
		} catch (NumberFormatException e) {
			logger.error("参数字符串转成Integer时转化异常", e);
			return null;
		}
	}
	/**
	 * 把字符串转化成Long
	 * @param src
	 * @return
	 */
	public static Long str2Long(String src){
		if(src==null || src.trim().length()==0){
			return null;
		}
//		if(!src.matches("\\d+")){
//			return null;
//		}
		try {
			Long l = Long.valueOf(src);
			return l;
		} catch (NumberFormatException e) {
			logger.error("参数字符串转成Long时转化异常", e);
			return null;
		}
	}
	/**
	 * 把字符串转化成Short
	 * @param src
	 * @return
	 */
	public static Short str2Short(String src){
		if(src==null || src.trim().length()==0){
			return null;
		}
		try {
			Short s = Short.valueOf(src);
			return s;
		} catch (NumberFormatException e) {
			logger.error("参数字符串转成Short时转化异常", e);
			return null;
		}
	}
	/**
	 * 把字符串转化成Byte
	 * 是按字面值转成数字，不是取编码
	 * @param src
	 * @return
	 */
	public static Byte str2Byte(String src){
		if(src==null || src.trim().length()==0){
			return null;
		}
		try {
			Byte b = Byte.valueOf(src);
			return b;
		} catch (NumberFormatException e) {
			logger.error("参数字符串转成Byte时转化异常", e);
			return null;
		}
	}
	/**
	 * 
	 * @param src
	 * @return
	 */
	public static String null2Empty(String src){
		if(src==null || src.trim().length()==0){
			return "";
		}
		return src;
	}
	/**
	 * 
	 * @param src
	 * @return
	 */
	public static String empty2Null(String src){
		if(src==null || src.trim().length()==0){
			return null;
		}
		return src;
	}
	public static Date str2Date(String src, DateFormat format){
		if(src==null || src.trim().equals("")){
			return null;
		}
		try {
			return format.parse(src);
		} catch (ParseException e) {
			logger.error("日期时间格式不正确，无法解析", e);
			return null;
		}
	}
	public static Date str2Date(String src, String pattern){
		DateFormat format = new SimpleDateFormat(pattern);
		return str2Date(src, format);
	}
	public static int str2Seconds(String src){
		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date = str2Date(src, format);
		if(date==null){
			return -3000;	//Or 其他
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		return hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
	}
	
	public static String seconds2Str(int seconds){
		if(seconds==-3000){
			return null;
		}
		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MILLISECOND, seconds);
		Date date = calendar.getTime();
		return format.format(date);
	}
	
	public static Integer minute2Millisecond(Integer minute){
		if(minute==null){
			return null;
		}
		return minute * 60 * 1000;
	}
	public static Integer millisecond2Minute(Integer millisecond){
		if(millisecond==null){
			return null;
		}
		return millisecond / (60 * 1000);
	}
	public static Double percent2Double(Double percent){
		if(percent==null){
			return null;
		}
		return percent / 100d;
	}
	public static Double double2Percent(Double d){
		if(d==null){
			return null;
		}
		return d * 100.0;
	}
	public static String double2PercentStr(Double d){
		if(d==null){
			return null;
		}
		return ""+(double2Percent(d))+"%";
	}
		
	public static String md5(String passwd){
		try {
			return MD5.getMD5(passwd.getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error("error when generate md5 code", e);
			return null;
		}
	}
	public static void main(String[] args) {
		System.out.println(md5("123"));
	}
	public static String date2Str(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
}
