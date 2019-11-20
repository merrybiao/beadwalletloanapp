package com.waterelephant.utils;

import java.util.Date;

/**
 * 工单相关操作工具类
 * 
 * @author duxiaoyong
 *
 */
public class OrderUtil {
	/** 工单编号前缀 */
	private final static String PRE = "B";

	/** 分期工单编号前缀 */
	private final static String INATALLMENT_PRE = "F";

	/**
	 * 生成工单号
	 * 
	 * @return 工单号
	 */
	public static String generateOrderNo() {
		StringBuffer orderNo = new StringBuffer(PRE);
		orderNo.append(CommUtils.convertDateToString(new Date(), "yyyyMMddhhmmssSSS"));
		orderNo.append(CommUtils.getRandomNumber(3));
		return orderNo.toString();
	}

	/**
	 * 生成分期工单号
	 * 
	 * @return 工单号
	 */
	public static String generateInstallmentOrderNo() {
		StringBuffer orderNo = new StringBuffer(INATALLMENT_PRE);
		orderNo.append(CommUtils.convertDateToString(new Date(), "yyyyMMddhhmmssSSS"));
		orderNo.append(CommUtils.getRandomNumber(3));
		return orderNo.toString();
	}

}
