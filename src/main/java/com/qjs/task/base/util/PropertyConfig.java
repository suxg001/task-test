package com.qjs.task.base.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
/**
 * 
 * @description 加载配置文件
 *
 */
public class PropertyConfig {

	private static final Logger logger = LoggerFactory.getLogger(PropertyConfig.class);
	
	public static Properties prop = new Properties();
	
	public static void init(String filePath){
		InputStream is = null;
		try {
			ClassPathResource cpr = new ClassPathResource(filePath);
			is = cpr.getInputStream();
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("-------------> Load Properties "+filePath+" failer!");
		}finally{
			if(null != is){
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Close inputStream failer");
				}
			}
		}
	}
	
	/**
	 * 由KEY返回VALUE，如果VALUE为空，则返回DEFAULTVALUE
	 * @param key
	 * @param defaultValue
	 * @return String
	 */
	public static String getProperty(String key,String defaultValue){
		return prop.getProperty(key,defaultValue);
	}
	public static void setProperty(String key,String defaultValue){
		prop.setProperty(key,defaultValue);
	}
	
	
	
	public static void main(String[] args) {
		PropertyConfig.init("phone.properties");
		String phones = PropertyConfig.getProperty("phone", "");
		System.out.println(phones);
		PropertyConfig.setProperty("home", "bb");
		System.out.println(PropertyConfig.getProperty("ss", ""));
	}
}
