package com.waterelephant.alipay.util;

import java.util.ResourceBundle;

public class AliPayConstant {
	public static String CREATEMETHOD = "";
	public static String MCH_ID = "";
	public static String APPID = "";
	public static String KEY ="";
	public static String COMMON_URL="";
	public static String QUERYMETHOD="";

	static {
		ResourceBundle woNiu_bundle = ResourceBundle.getBundle("ali");
		if (woNiu_bundle == null) {
			throw new IllegalArgumentException("[ali.properties] is not found!");
		}

		AliPayConstant.CREATEMETHOD = woNiu_bundle.getString("method.create");
		AliPayConstant.MCH_ID = woNiu_bundle.getString("mch_id");
		AliPayConstant.APPID = woNiu_bundle.getString("appid");
		AliPayConstant.KEY = woNiu_bundle.getString("testKey");
		AliPayConstant.COMMON_URL = woNiu_bundle.getString("common_url");
		AliPayConstant.COMMON_URL = woNiu_bundle.getString("method.query");
	}
}
