package com.waterelephant.capitalDemo.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author 张博
 * 
 * 读取资源文件
 *
 */
public class WoNiuContext {

	public static Map<String, String> KEY_MAP = new HashMap<String, String>();
	
	static {
		ResourceBundle loanWallet_bundle = ResourceBundle.getBundle("woniu");	
		if (loanWallet_bundle == null) {
			throw new IllegalArgumentException("[woniu.properties] is not found!");
		}

		Enumeration<String> keys = loanWallet_bundle.getKeys();		//获取配置文件中所有的key
		String key = null;
		while (keys.hasMoreElements()) {							
			key = keys.nextElement();
			KEY_MAP.put(key, loanWallet_bundle.getString(key));		//把键和值封装到map中
		}
	}
	
	public static String get(String key){
		return KEY_MAP.get(key);
	}
}
