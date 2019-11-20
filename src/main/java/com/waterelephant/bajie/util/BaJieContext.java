package com.waterelephant.bajie.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

public class BaJieContext {
	public static Map<String, String> KEY_MAP = new HashMap<String, String>();
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("bajie");
		if (bundle == null) {
			throw new IllegalArgumentException("[bajie.properties] is not found!");
		}

		Enumeration<String> keys = bundle.getKeys();
		String key = null;
		while (keys.hasMoreElements()) {
			key = keys.nextElement();
			KEY_MAP.put(key, bundle.getString(key));
		}
	}
	
	public static String get(String key){
		if (StringUtils.isBlank(key)) {
			return null;
		}
		
		return KEY_MAP.get(key);
	}
	
	public static void set(String key, String value){
		KEY_MAP.put(key, value);
	}
}