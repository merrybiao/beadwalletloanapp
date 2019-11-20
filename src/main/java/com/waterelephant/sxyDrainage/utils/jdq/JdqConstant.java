///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.jdq;
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
// * JdqConstant.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class JdqConstant {
//	private static Map<String, String> KEY_MAP = new HashMap<String, String>();
//	// 订单状态映射
//	public static Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();
//	// 审核状态映射
//	public static Map<String, String> REPAY_STATUS_MAP = new HashMap<String, String>();
//
//	static {
//		ResourceBundle jdq_bundle = ResourceBundle.getBundle("jdq");
//		if (jdq_bundle == null) {
//			throw new IllegalArgumentException("[jdq.properties] is not found!");
//		}
//
//		Enumeration<String> keys = jdq_bundle.getKeys(); // 获取配置文件中所有的key
//		String key = null;
//		while (keys.hasMoreElements()) {
//			key = keys.nextElement();
//			KEY_MAP.put(key, jdq_bundle.getString(key)); // 把键和值封装到map中
//		}
//		// 借点钱，0:待审核，1:已取消，2:审核失败，3:审核成功，4:待签约，5:签约失败，6:待放款，7:已放款，8:已还清，10:逾期还清，12:已坏账，13:已逾期，14:续期，15:放款失败（最终状态）
//		ORDER_STATUS_MAP.put("1", "0");
//		ORDER_STATUS_MAP.put("2", "0");
//		ORDER_STATUS_MAP.put("3", "0");
//		ORDER_STATUS_MAP.put("4", "4");
//		// ORDER_STATUS_MAP.put("5", "6");
//		ORDER_STATUS_MAP.put("6", "8");
//		ORDER_STATUS_MAP.put("7", "2");
//		ORDER_STATUS_MAP.put("8", "2");
//		ORDER_STATUS_MAP.put("9", "7");
//		ORDER_STATUS_MAP.put("11", "6");// TODO
//		ORDER_STATUS_MAP.put("12", "6");
//		ORDER_STATUS_MAP.put("14", "6");
//		ORDER_STATUS_MAP.put("13", "13");
//
//		// ORDER_STATUS_MAP.put("12", "6");
//		// ORDER_STATUS_MAP.put("14", "6");
//		ORDER_STATUS_MAP.put("13", "13");
//
//		// 我方 1 未还款 2 已还款 3垫付 4展期',
//		// 借点钱 0:未出账， 1:待还款，2:正常结清，3:逾期结清， 4:逾期， 5:部分还款（已逾期），6:部分还款（未逾期），7:还款中
//		REPAY_STATUS_MAP.put("1", "1");
//		REPAY_STATUS_MAP.put("2", "2");// 返回，2:正常结清，3:逾期结清
//		REPAY_STATUS_MAP.put("3", "4");
//
//		// REPAY_STATUS.put("4", "7");
//
//		// REPAY_STATUS.put("4", "7");
//
//	}
//
//	public static String get(String key) {
//		return KEY_MAP.get(key);
//	}
//
//	public static void main(String[] args) {
//		String aString = JdqConstant.get("jdqPrivateKey");
//		System.out.println(aString);
//	}
//
//}
