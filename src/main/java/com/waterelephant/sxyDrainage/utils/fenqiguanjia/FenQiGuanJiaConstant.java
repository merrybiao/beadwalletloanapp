//package com.waterelephant.sxyDrainage.utils.fenqiguanjia;
//
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//public class FenQiGuanJiaConstant {
//
//	public static Map<String, String> KEY_MAP = new HashMap<String, String>();
//	// 订单状态映射
//	public static Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();
//	// 审核状态映射
//	public static Map<String, String> APPROVE_STATUS_MAP = new HashMap<String, String>();
//	// 账单状态映射
//	public static Map<String, String> BILL_STATUS_MAP = new HashMap<String, String>();
//
//	static {
//		ResourceBundle loanWallet_bundle = ResourceBundle.getBundle("fenqiguanjia");
//		if (loanWallet_bundle == null) {
//			throw new IllegalArgumentException("[fenqiguanjia.properties] is not found!");
//		}
//
//		Enumeration<String> keys = loanWallet_bundle.getKeys(); // 获取配置文件中所有的key
//		String key = null;
//		while (keys.hasMoreElements()) {
//			key = keys.nextElement();
//			KEY_MAP.put(key, loanWallet_bundle.getString(key)); // 把键和值封装到map中
//		}
//
//		ORDER_STATUS_MAP.put("9", "170");// 放款成功
//		ORDER_STATUS_MAP.put("13", "180");// 逾期
//		ORDER_STATUS_MAP.put("6", "200");// 贷款结清
//		ORDER_STATUS_MAP.put("7", "110");// 审批不通过
//		ORDER_STATUS_MAP.put("8", "110");
//		ORDER_STATUS_MAP.put("4", "100");// 审批通过
//		ORDER_STATUS_MAP.put("11", "210");
//		ORDER_STATUS_MAP.put("12", "210");// 待贷款 用户确认后进入待放款状态
//		ORDER_STATUS_MAP.put("14", "210");
//
//		APPROVE_STATUS_MAP.put("7", "40");
//		APPROVE_STATUS_MAP.put("8", "40");
//		APPROVE_STATUS_MAP.put("4", "10");
//		APPROVE_STATUS_MAP.put("11", "10");
//		APPROVE_STATUS_MAP.put("12", "10");
//		APPROVE_STATUS_MAP.put("14", "10");
//		APPROVE_STATUS_MAP.put("9", "10");
//		APPROVE_STATUS_MAP.put("6", "10");
//		APPROVE_STATUS_MAP.put("13", "10");
//
//		BILL_STATUS_MAP.put("9", "1");
//		BILL_STATUS_MAP.put("6", "2");
//		BILL_STATUS_MAP.put("13", "3");
//	}
//
//	public static String get(String key) {
//		return KEY_MAP.get(key);
//	}
//
//	public static void main(String[] args) {
//		String aString = FenQiGuanJiaConstant.get("priKey");
//		System.out.println(aString);
//
//	}
//}
