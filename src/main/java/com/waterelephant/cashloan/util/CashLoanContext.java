package com.waterelephant.cashloan.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CashLoanContext {


	public static Map<String, String> KEY_MAP = new HashMap<String, String>();
	
	static {
		ResourceBundle loanWallet_bundle = ResourceBundle.getBundle("cashloan");	//读取资源属性文件
		if (loanWallet_bundle == null) {
			throw new IllegalArgumentException("[cashloan.properties] is not found!");
		}

		Enumeration<String> keys = loanWallet_bundle.getKeys();
		String key = null;
		while (keys.hasMoreElements()) {
			key = keys.nextElement();
			KEY_MAP.put(key, loanWallet_bundle.getString(key));
		}
	}
	
	public static String get(String key){
		return KEY_MAP.get(key);
	}
}