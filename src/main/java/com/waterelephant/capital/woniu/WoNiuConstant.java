package com.waterelephant.capital.woniu;

import java.util.ResourceBundle;

/**
 * woNiu001
 * 
 * @author 张博
 * 
 *         读取资源文件
 *
 */
public class WoNiuConstant {

	public static String Key = "";
	public static String CAPTITALID = "";
	public static String CAPTIALORDERPUSHID = "";
	public static String CAPITALFAIL ="";

	static {
		ResourceBundle woNiu_bundle = ResourceBundle.getBundle("woniu");
		if (woNiu_bundle == null) {
			throw new IllegalArgumentException("[woniu.properties] is not found!");
		}

		WoNiuConstant.Key = woNiu_bundle.getString("woNiu.signKey");
		WoNiuConstant.CAPTITALID = woNiu_bundle.getString("woniu.capitalId");
		WoNiuConstant.CAPTIALORDERPUSHID = woNiu_bundle.getString("woniu.capitalOrderPushId");
		WoNiuConstant.CAPITALFAIL=woNiu_bundle.getString("woniu.capitalFail");
	}
}
