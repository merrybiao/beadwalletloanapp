/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.baidu.util;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * 
 * Module:
 * 
 * Md5Util.java
 * 
 * @author 甘立思
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Md5Util {
	private static String signKey = Constant.KEY;

	/**
	 * 获取自己接口的签名
	 * 
	 * @param paramMap
	 * @return
	 */
	public static String getSign(Map<String, Object> paramMap) {
		Iterator<String> iter = paramMap.keySet().iterator();
		String[] paramArray = new String[paramMap.size() - 1];
		String paramData = null, key = null;
		Object value = null;
		int i = 0;
		while (iter.hasNext()) {
			key = iter.next();
			if (!"sign".equals(key)) {
				value = paramMap.get(key);
				paramArray[i] = key + "=" + value;
				i++;
			}
		}
		Arrays.sort(paramArray);
		// 遍历出数组中的数据并拼接成字符传
		StringBuilder strB = new StringBuilder();
		for (int j = 0; j < paramArray.length; j++) {
			String str = paramArray[j];
			strB.append(str);
		}
		String paramStr = strB.toString();
		// String paramStrEncode = URLEncoder.encode(paramStr, "UTF-8");
		paramData = paramStr + signKey;
		String sign = MD5(paramData);
		return sign;
	}

	/***
	 * MD5 加密
	 */
	public static String MD5(String str) {
		if (str == null)
			return null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("UTF-8"));
			byte[] digest = md5.digest();
			StringBuffer hexString = new StringBuffer();
			String strTemp;
			for (int i = 0; i < digest.length; i++) {
				strTemp = Integer.toHexString((digest[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
				hexString.append(strTemp);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
