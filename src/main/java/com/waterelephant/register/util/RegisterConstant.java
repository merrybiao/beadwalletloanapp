package com.waterelephant.register.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RegisterConstant {

	public static Map<String, String> KEY_MAP = new HashMap<String, String>();
	   
	static {
		ResourceBundle loanWallet_bundle = ResourceBundle.getBundle("register");
		if (loanWallet_bundle == null) {
			throw new IllegalArgumentException("[register.properties] is not found!");
		}

		Enumeration<String> keys = loanWallet_bundle.getKeys();
		String key = null;
		while (keys.hasMoreElements()) {
			key = keys.nextElement();
			KEY_MAP.put(key, loanWallet_bundle.getString(key));
		}
	}
	
	public static void main(String[] args) {
		ResourceBundle loanWallet_bundle = ResourceBundle.getBundle("register");
		Enumeration<String> keys = loanWallet_bundle.getKeys();
		String key = null;
		while (keys.hasMoreElements()) {
			key = keys.nextElement();
//			DES_KEY_MAP.put(key, loanWallet_bundle.getString(key));
			System.out.println("------------------------------------------------------------key="+key);
			System.out.println("------------------------------------------------------------value="+loanWallet_bundle.getString(key));
		}
	}
}