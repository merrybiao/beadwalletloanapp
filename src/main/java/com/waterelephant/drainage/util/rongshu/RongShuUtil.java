package com.waterelephant.drainage.util.rongshu;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author xiaoXingWu
 * @time 2017年8月8日
 * @description
 * @since JDK1.8
 */
public class RongShuUtil {
	private static Logger logger = Logger.getLogger(RongShuUtil.class);
	// 审核状态映射
	private static Map<String, String> APPROVE_STATUS_MAP = new HashMap<String, String>();
	// 账单状态映射
	private static Map<String, String> BILL_STATUS_MAP = new HashMap<String, String>();

	static {

		BILL_STATUS_MAP.put("9", "1");
		BILL_STATUS_MAP.put("6", "2");
		BILL_STATUS_MAP.put("13", "3");

		APPROVE_STATUS_MAP.put("7", "40");
		APPROVE_STATUS_MAP.put("8", "40");
		APPROVE_STATUS_MAP.put("4", "10");
		APPROVE_STATUS_MAP.put("11", "10");
		APPROVE_STATUS_MAP.put("12", "10");
		APPROVE_STATUS_MAP.put("14", "10");
		APPROVE_STATUS_MAP.put("9", "10");
		APPROVE_STATUS_MAP.put("5", "10");
		APPROVE_STATUS_MAP.put("6", "10");
		APPROVE_STATUS_MAP.put("13", "10");

	}

	public static String convertBillStatus(Long oriStatus) {
		if (oriStatus == null) {
			return null;
		}

		return BILL_STATUS_MAP.get(String.valueOf(oriStatus));
	}

	public static String convertApproveStatus(Long oriStatus) {
		if (oriStatus == null) {
			return null;
		}

		return APPROVE_STATUS_MAP.get(String.valueOf(oriStatus));
	}

	/***
	 * 验签判断
	 * 
	 * @param checkSign
	 * @param checkContent
	 * @return
	 */
	public static boolean checkSign(String checkSign, Map<String, String> paramMap) {
		boolean flag = false;
		logger.info("~~~~榕树请求的checkSign="+checkSign);
		String  private_key=RongShuConstant.DEFAULT_PRIVATE_KEY;
		logger.info("加签的key:"+private_key);
		String sign = SignUtil.rsaSign(paramMap,private_key , "utf-8");
		logger.info("~~~~本地加签的sign="+sign);
		if (checkSign.equals(sign)) {
			flag = true;
		}

		return flag;
	}
	/**
	 * 
	 * @param idCard
	 * @return
	 */
	public static int getSexByIdCard(String idCard) {
		int sex = 0;
		String sexNum = idCard.substring(idCard.length() - 2, idCard.length() - 1);
		if ((Integer.parseInt(sexNum)) % 2 == 0) {
			sex = 0;
		} else {
			sex = 1;
		}
		return sex;
	}

	/**
	 * 
	 * @param idCard
	 * @return
	 */
	public static int getAgeByIdCard(String idCard) {
		Calendar c = Calendar.getInstance();
		int age = 0;
		int year = c.get(Calendar.YEAR);
		String ageNum = idCard.substring(6, 10);
		age = year - Integer.parseInt(ageNum);

		return age;
	}
}
