///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.beijingDrainageUtil;
//
//import java.text.ParsePosition;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.commons.lang.StringUtils;
//
///**
// * Module: 
// * SxyThirdUtil.java 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class BeijingDrainageUtil {
//	/**
//	 * 将指定格式的字符串转为日期时间对象
//	 * 
//	 * @param strDate 时间
//	 * @param format 格式
//	 * @return 时间 date
//	 */
//	public static Date formatToDate(String strDate, String format) {
//		if (strDate == null) {
//			return null;
//		}
//		SimpleDateFormat formatter = new SimpleDateFormat(format);
//		ParsePosition pos = new ParsePosition(0);
//		Date strtodate = formatter.parse(strDate, pos);
//		return strtodate;
//	}
//
//	public static String getWorkYear(String type) {
//		if (StringUtils.isBlank(type)) {
//			return "";
//		} else if ("1".equals(type)) {
//			return "0-5个月 ";
//		} else if ("2".equals(type)) {
//			return "6-11个月 ";
//		} else if ("3".equals(type)) {
//			return "1-3年 ";
//		} else if ("4".equals(type)) {
//			return "3-7年 ";
//		} else if ("5".equals(type)) {
//			return "7年以上 ";
//		}
//		return "";
//	}
//
//	/**
//	 * 
//	 * @param industry
//	 * @return
//	 */
//	public static String getIndustry(String industry) {
//		if (StringUtils.isBlank(industry)) {
//			return "";
//		} else if ("A".equals(industry.toUpperCase())) {
//			return "农牧业 ";
//		} else if ("B".equals(industry.toUpperCase())) {
//			return "商贸建筑业";
//		} else if ("D".equals(industry.toUpperCase())) {
//			return "服务业 ";
//		} else if ("E".equals(industry.toUpperCase())) {
//			return "金融保险业 ";
//		} else if ("F".equals(industry.toUpperCase())) {
//			return "公务员 ";
//		} else if ("G".equals(industry.toUpperCase())) {
//			return "军队 公安 警察 等 ";
//		} else if ("H".equals(industry.toUpperCase())) {
//			return "学生 宗教 自由职业者 ";
//		} else if ("I".equals(industry.toUpperCase())) {
//			return "赌坊 KTV 其他娱乐业 ";
//		} else if ("J".equals(industry.toUpperCase())) {
//			return "无业 ";
//		} else if ("K".equals(industry.toUpperCase())) {
//			return "其他 ";
//		}
//		return "";
//	}
//
//	/**
//	 * 根据名称获取银行编码
//	 * 
//	 * @param openBank 银行名称
//	 * @return
//	 */
//	public static String convertToBankCode(String openBank) {
//		if (StringUtils.isBlank(openBank)) {
//			return "";
//		} else if (openBank.contains("工商")) {
//			return "ICBC";
//		} else if (openBank.contains("农业")) {
//			return "ABC";
//		} else if (openBank.contains("中国银行")) {
//			return "BOC";
//		} else if (openBank.contains("建设")) {
//			return "CCB";
//		} else if (openBank.contains("交通")) {
//			return "BCOM";
//		} else if (openBank.contains("民生")) {
//			return "CMBC";
//		} else if (openBank.contains("招商")) {
//			return "CMB";
//		} else if (openBank.contains("邮储")) {
//			return "POST";
//		} else if (openBank.contains("邮政")) {
//			return "PSBC";
//		} else if (openBank.contains("平安")) {
//			return "PAB";
//		} else if (openBank.contains("中信")) {
//			return "CITIC";
//		} else if (openBank.contains("光大")) {
//			return "CEB";
//		} else if (openBank.contains("兴业")) {
//			return "CIB";
//		} else if (openBank.contains("广发")) {
//			return "GDB";
//		} else if (openBank.contains("华夏")) {
//			return "HXB";
//		} else if (openBank.contains("南京")) {
//			return "NJCB";
//		} else if (openBank.contains("浦发")) {
//			return "SPDB";
//		} else if (openBank.contains("北京")) {
//			return "BOB";
//		} else if (openBank.contains("杭州")) {
//			return "HZB";
//		} else if (openBank.contains("宁波")) {
//			return "NBCB";
//		} else if (openBank.contains("浙商")) {
//			return "CZB";
//		} else if (openBank.contains("徽商")) {
//			return "HSB";
//		} else if (openBank.contains("渤海")) {
//			return "CBHB";
//		} else if (openBank.contains("汉口")) {
//			return "HKBANK";
//		} else if (openBank.contains("上海")) {
//			return "SHB";
//		} else if (openBank.contains("江苏")) {
//			return "JSB";
//		} else if (openBank.contains("浙商")) {
//			return "ZSB";
//		} else {
//			return "";
//		}
//	}
//
//}
