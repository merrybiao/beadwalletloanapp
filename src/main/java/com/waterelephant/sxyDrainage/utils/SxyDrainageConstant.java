///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils;
//
//import java.util.ResourceBundle;
//
///**
// * 
// * 
// * Module:
// * 
// * SxyDrainageConstant.java
// * 
// * @author zhangchong
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class SxyDrainageConstant {
//	public static ResourceBundle sxyConfig = null;
//	public static String productId = null;
//	public static String MALLCOUPON_URL = null;
//	public static String MALLCOUPON_KEY = null;
//
//	static {
//		sxyConfig = ResourceBundle.getBundle("sxyDrainage");
//		if (sxyConfig == null) {
//			throw new IllegalArgumentException("[sxyDrainage.properties] is not found!");
//		}
//
//		productId = sxyConfig.getString("product_id");
//		MALLCOUPON_URL = sxyConfig.getString("MALLCOUPON_URL");
//		MALLCOUPON_KEY = sxyConfig.getString("MALLCOUPON_KEY");
//	}
//
//	public static void main(String[] args) {
//		System.out.println(productId);
//	}
//}
