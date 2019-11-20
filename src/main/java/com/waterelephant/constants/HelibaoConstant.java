package com.waterelephant.constants;

import java.util.ResourceBundle;

/**
 * 合利宝常量
 * 
 * @author maoenqi
 */
public class HelibaoConstant {
	private static ResourceBundle configBundle = ResourceBundle.getBundle("helibao");
	public static String SIGNKEY_MD5;
	static {
		SIGNKEY_MD5 = configBundle.getString("signkey_MD5");
	}
}