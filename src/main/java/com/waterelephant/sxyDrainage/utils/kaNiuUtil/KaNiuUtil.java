///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.kaNiuUtil;
//
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.apache.commons.lang.StringUtils;
//
///**
// * Module: 
// * KaNiuUtil.java 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class KaNiuUtil {
//
//
//	/**
//	 * 
//	 * 转换成卡牛订单状态
//	 * 
//	 * @param type
//	 * @return
//	 */
//	// 当前工单状态 1草稿 2初审 3终审 4待签约 5待放款 6结束 7拒绝 8撤回 9还款中 11待生成合同 12待债匹 13逾期 14债匹中
//	// 3 审核中4 审核被退回5 审核不通过6 审核通过41 用户取消审核 71 待签约放款 8 放款中9 放款成功已放款10 放款失败11 客户放弃借款12 还款中13 已结清14 逾期中
//	public static String toKNState(int orderStatus) {
//		if (1 == orderStatus || 2 == orderStatus || 3 == orderStatus) {
//			return "3";
//		} else if (4 == orderStatus) {
//			return "71";
//		} else if (5 == orderStatus) {
//			return "71";
//		} else if (6 == orderStatus) {
//			return "13";
//		} else if (7 == orderStatus || 8 == orderStatus) {
//			return "5";
//		} else if (9 == orderStatus) {
//			return "12";// 放款通知中写9
//		} else if (11 == orderStatus || 12 == orderStatus || 14 == orderStatus) {
//			return "8";
//		} else if (13 == orderStatus) {
//			return "14";
//		}
//		return "0";
//	}
//
//	/*
//	 * 运营商转换 0:电信;1:移动;2:联通
//	 */
//	public static String toOperSou(int type) {
//		if ( 0 == type) {
//			return "电信";
//		} else if (1 == type) {
//			return "移动";
//		} else if (2 == type) {
//			return "联通";
//		} else {
//			return "未知";
//		}
//	}
//
//	/*
//	 * 1男 2女
//	 */
//	public static String toSex(String type) {
//		if ("1".equals(type)) {
//			return "男 ";
//		} else if ("2".equals(type)) {
//			return "女";
//		} else {
//			return "";
//		}
//	}
//	
//	/*
//	 * 未婚/已婚未育/已婚已育/离异/其他
//	 */
//	public static int toMarryStatus(String type) {
//		if (StringUtils.isBlank(type)) {
//			return 0;
//		}
//		if (type.contains("已婚")) {
//			return 1;
//		} else {
//			return 0;
//		}
//	}
//
//	/*
//	 * 身份证有效期
//	 */
//	public static String toValidType(String StartType, String EndType) {
//		if (StringUtils.isBlank(StartType) || StringUtils.isBlank(EndType)) {
//			return "";
//		}
//		return StartType.replaceAll("-", ".") + "-" + EndType.replaceAll("-", ".");
//	}
//	public static String getToken(Map<String, String> requestMap, String key) {
//		Set<String> sortedRequestKey = new TreeSet<>(requestMap.keySet());
//		StringBuilder sb = new StringBuilder();
//		for (String reqKey : sortedRequestKey) {
//			String value = requestMap.get(reqKey);
//			sb.append(reqKey).append("=").append(value).append("&");
//		}
//		sb.append("accessKey=").append(key);
//		try {
//			return EncoderByMd5(sb.toString());
//		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	@SuppressWarnings("restriction")
//	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//		// 确定计算方法
//		MessageDigest md5 = MessageDigest.getInstance("MD5");
//		BASE64Encoder base64en = new BASE64Encoder();
//		// 加密后的字符串
//		String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
//		return newstr;
//	}
//}
