package com.waterelephant.capital.yiqile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class ApiUtil {
	private static Logger log = Logger.getLogger(ApiUtil.class);

	public static String encodeToUtf8(String value) {
		try {
			value = URLEncoder.encode(value, "utf-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String decodeFromUtf8(String value) {
		try {
			value = URLDecoder.decode(value.replaceAll("%20", "+"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static StringBuffer generateSignOriginalStr(Map<String, Object> params, String signKey) {
		params.remove("sign");
		ArrayList<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer sb = new StringBuffer();
		String method = (String) params.getOrDefault("method", "null");
		for (String key : keys) {
			Object value = params.get(key);
			if ("user/syncCredit".equals(method) && "data".equals(key)) {
				continue;
			}
			if (value != null) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(key).append("=").append(encodeToUtf8(value.toString()));
			}
		}
		sb.append("&key=");
		sb.append(signKey);
		return sb;
	}

	/**
	 * 生成签名
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(Integer signType, String signKey, Map<String, Object> params) {
		StringBuffer sb = generateSignOriginalStr(params, signKey);
		System.out.println(sb.toString());
		String sign = DigestUtils.sha256Hex(sb.toString());

		return sign;
	}

	/**
	 * 校验客户端sign
	 * 
	 * @param parameters
	 * @return
	 */
	public static boolean checkSign(Integer signType, String signKey, Map<String, Object> parameters) {
		// return true;
		String sign = (String) parameters.get("sign");
		String method = (String) parameters.getOrDefault("method", "null");

		parameters.remove("sign");
		String sign1 = createSign(signType, signKey, parameters);
		if (!sign.equals(sign1)) {
			StringBuffer originalStr = generateSignOriginalStr(parameters, signKey);
			log.error(String.format("校验sign时不一致：实际值=%s 传入值=%s 签名原窜=%s", sign1, sign, originalStr.toString()));
			return false;
		}
		return true;
	}

}
