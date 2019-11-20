package com.waterelephant.drainage.util.xianjincard;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * Module:
 * 
 * XianJinCardUtil.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class XianJinCardUtils {
	/**
	 * 根据名称获取银行编码
	 * 
	 * @param openBank 银行名称
	 * @return
	 */
	public static String convertToBankCode(String openBank) {
		if (StringUtils.isBlank(openBank)) {
			return "";
		} else if (openBank.contains("工商")) {
			return "ICBC";
		} else if (openBank.contains("农业")) {
			return "ABC";
		} else if (openBank.contains("中国银行")) {
			return "BOC";
		} else if (openBank.contains("建设")) {
			return "CCB";
		} else if (openBank.contains("交通")) {
			return "BCOM";
		} else if (openBank.contains("民生")) {
			return "CMBC";
		} else if (openBank.contains("招商")) {
			return "CMB";
		} else if (openBank.contains("邮储")) {
			return "POST";
		} else if (openBank.contains("平安")) {
			return "PAB";
		} else if (openBank.contains("中信")) {
			return "CITIC";
		} else if (openBank.contains("光大")) {
			return "CEB";
		} else if (openBank.contains("兴业")) {
			return "CIB";
		} else if (openBank.contains("广发")) {
			return "GDB";
		} else if (openBank.contains("华夏")) {
			return "HXB";
		} else if (openBank.contains("南京")) {
			return "NJCB";
		} else if (openBank.contains("浦发")) {
			return "SPDB";
		} else if (openBank.contains("北京")) {
			return "BOB";
		} else if (openBank.contains("杭州")) {
			return "HZB";
		} else if (openBank.contains("宁波")) {
			return "NBCB";
		} else if (openBank.contains("浙商")) {
			return "CZB";
		} else if (openBank.contains("徽商")) {
			return "HSB";
		} else if (openBank.contains("渤海")) {
			return "CBHB";
		} else if (openBank.contains("汉口")) {
			return "HKBANK";
		} else {
			return "";
		}
	}

	/**
	 * 获取现金白卡工作年限
	 * 
	 * @param work_age
	 * @return
	 */
	public static String getWorkAge(String work_age) {
		if ("1".equals(work_age)) {
			return "0-5个月";
		} else if ("2".equals(work_age)) {
			return "6-11个月";
		} else if ("3".equals(work_age)) {
			return "1-3年";
		} else if ("4".equals(work_age)) {
			return "3-7年";
		} else if ("5".equals(work_age)) {
			return "7年以上";
		} else {
			return "";
		}
	}

	/**
	 * 获取现金白卡工作类型
	 * 
	 * @param type
	 * @return
	 */
	public static String getWorkType(String type) {
		if ("1".equals(type)) {
			return "政府或企事业单位";
		} else if ("2".equals(type)) {
			return "央企国企";
		} else if ("3".equals(type)) {
			return "外资企业";
		} else if ("4".equals(type)) {
			return "上市公司";
		} else if ("5".equals(type)) {
			return "民营企业";
		} else if ("6".equals(type)) {
			return "个体工商户";
		} else {
			return "";
		}
	}

}
