package com.ecard.products.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ResourceLoad {

	private static Logger logger = Logger.getLogger(ResourceLoad.class);
	
	public static Properties load(String config){
		Properties p = new Properties();
		try {
			InputStream in = ResourceLoad.class.getClassLoader().getResourceAsStream(config);
			p.load(in);
		} catch (IOException e) {
			logger.error("加载属性文件" + config + "出现异常", e);
		}
		return p;
	}
	
	public static void main(String[] args) {
		Properties config = ResourceLoad.load("cfg_dsf.properties");
		System.out.println(config.getProperty("root"));
	}
}
