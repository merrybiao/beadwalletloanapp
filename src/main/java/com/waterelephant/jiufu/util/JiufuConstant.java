package com.waterelephant.jiufu.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class JiufuConstant {
	public static Map<String, String> KEY_MAP = new HashMap<String, String>();
	static {
		ResourceBundle loanWallet_bundle = ResourceBundle.getBundle("jiufu");
		if (loanWallet_bundle == null) {
			throw new IllegalArgumentException("[jiufu.properties] is not found!");
		}

		Enumeration<String> keys = loanWallet_bundle.getKeys();
		String key = null;
		while (keys.hasMoreElements()) {
			key = keys.nextElement();
			KEY_MAP.put(key, loanWallet_bundle.getString(key));
		}
	}
}
