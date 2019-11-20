///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.kaola;
//
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
///**
// * 
// * 
// * Module:
// * 
// * KaoLaConstant.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class KaoLaConstant {
//
//	public static Map<String, String> KEY_MAP = new HashMap<String, String>();
//	public static String CHANNEL_ID = "";
//	public static String SX_PRIVATE_KEY = "";
//	public static String KL_PUBLIC_KEY = "";
//	public static String PRODUCT_ID = "";
//	public static String CONTRACT_URL = "";
//	public static String RETURN_URL = "";
//	static {
//		ResourceBundle loanWallet_bundle = ResourceBundle.getBundle("kaola");
//		if (loanWallet_bundle == null) {
//			throw new IllegalArgumentException("[kaola.properties] is not found!");
//		}
//
//		Enumeration<String> keys = loanWallet_bundle.getKeys(); // 获取配置文件中所有的key
//		String key = null;
//		while (keys.hasMoreElements()) {
//			key = keys.nextElement();
//			KEY_MAP.put(key, loanWallet_bundle.getString(key)); // 把键和值封装到map中
//		}
//		CHANNEL_ID = get("channel_id");
//		SX_PRIVATE_KEY = get("sx_private_key");
//		KL_PUBLIC_KEY = get("kl_public_key");
//		PRODUCT_ID = get("product_id");
//		CONTRACT_URL = get("contract_url");
//		RETURN_URL = get("return_url");
//	}
//
//	public static String get(String key) {
//		return KEY_MAP.get(key);
//	}
//
//	public static void main(String[] args) {
//		String value = get("channel_id");
//		System.out.println(value);
//	}
//
//}
