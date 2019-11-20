package com.waterelephant.utils;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
	private ValidateUtil() {
	}

	/**
	 * 判断是否是银行卡号
	 *
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;

	}

	private static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/***
	 * 真实姓名验证
	 *
	 * @param trueName
	 * @return
	 */
	public static boolean checkTrueName(String trueName) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String check = "^[\u4E00-\u9FA5]{2,8}(?:·[\u4E00-\u9FA5]{2,8})*$";
		p = Pattern.compile(check); // 验证真实姓名
		m = p.matcher(trueName);
		b = m.matches();
		return b;
	}

	/**
	 * 身份证号码验证
	 *
	 * @param card
	 * @return
	 */
	public static boolean checkIdentityCard(String card) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String check = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		p = Pattern.compile(check);
		m = p.matcher(card);
		b = m.matches();
		return b;
	}

	private static final String JS = "建设";
	private static final String GD = "光大";
	private static final String ZS = "招商";
	private static final String LY = "农业";
	private static final String JT = "交通";
	private static final String PA = "平安";
	private static final String PD = "浦东";
	private static final String YZ = "邮政储蓄";
	private static final String HX = "华夏银行";
	private static final String MS = "民生银行";
	private static final String ZG = "中国银行";
	private static final String GS = "工商银行";
	private static final String GF = "广发银行";
	private static final String XY = "兴业";
	private static final String ZX = "中信银行";
	private static final String SH = "上海银行";
	private static Map<String, String> bankMap = new HashedMap();
	static {
		bankMap.put(JS, "0105");
		bankMap.put(GD, "0303");
		bankMap.put(ZS, "0308");
		bankMap.put(LY, "0103");
		bankMap.put(JT, "0301");
		bankMap.put(PA, "0307");
		bankMap.put(PD, "0310");
		bankMap.put(YZ, "0403");
		bankMap.put(HX, "0304");
		bankMap.put(MS, "0305");
		bankMap.put(ZG, "0104");
		bankMap.put(GS, "0102");
		bankMap.put(GF, "0306");
		bankMap.put(XY, "0309");
		bankMap.put(ZX, "0302");
		bankMap.put(SH, "0401");
	}

	/**
	 * 验证银行卡名字和卡号是否一致
	 *
	 * @param bankCode
	 * @param cardNo
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean checkBankNameAndCode(String bankCode, String cardNo) {
		String bankName = BankInfoUtils.getNameOfBank(cardNo.substring(0, 6));
		String code = getBankCode(bankName);
		if (code == null || !code.equals(bankCode)){
			return false;
		}
		return true;
	}

	public static String getBankCode(String bankName) {
		String code = "";
		if (bankMap != null && bankName != null) {
			for (Map.Entry<String, String> bankEntry : bankMap.entrySet()) {
				if (bankName.contains(bankEntry.getKey())) {
					code = bankEntry.getValue();
					break;
				}
			}
		}
		return code;
	}
}