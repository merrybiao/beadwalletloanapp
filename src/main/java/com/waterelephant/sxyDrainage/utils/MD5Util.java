//package com.waterelephant.sxyDrainage.utils;
//
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SignatureException;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.SortedMap;
//import java.util.TreeMap;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.beadwallet.utils.MyBeanUtils;
//import com.waterelephant.utils.DateUtil;
//
//public class MD5Util {
//
//	private static String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
//			"f" };
//
//	private MD5Util() {
//	}
//
//	public static String md5(byte b[]) throws NoSuchAlgorithmException {
//		MessageDigest md5 = MessageDigest.getInstance("MD5");
//		md5.update(b, 0, b.length);
//		return byteArrayToHexString(md5.digest());
//	}
//
//	public static String md5(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//		MessageDigest md5 = MessageDigest.getInstance("MD5");
//		byte b[] = data.getBytes("UTF-8");
//		md5.update(b, 0, b.length);
//		return byteArrayToHexString(md5.digest());
//	}
//
//	private static String byteArrayToHexString(byte b[]) {
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < b.length; i++)
//			sb.append(byteToHexString(b[i]));
//
//		return sb.toString();
//	}
//
//	private static String byteToHexString(byte b) {
//		int n = b;
//		if (n < 0)
//			n = 256 + n;
//		int d1 = n / 16;
//		int d2 = n % 16;
//		return hexDigits[d1] + hexDigits[d2];
//	}
//
//	public static String getMd5Value(String sSecret) {
//		try {
//			MessageDigest bmd5 = MessageDigest.getInstance("MD5");
//			bmd5.update(sSecret.getBytes());
//			int i;
//			StringBuffer buf = new StringBuffer();
//			byte[] b = bmd5.digest();
//			for (int offset = 0; offset < b.length; offset++) {
//				i = b[offset];
//				if (i < 0)
//					i += 256;
//				if (i < 16)
//					buf.append("0");
//				buf.append(Integer.toHexString(i));
//			}
//			return buf.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	/**
//	 * 获取MD5加密签名
//	 *
//	 * @param params 对象
//	 * @param key 加密key
//	 * @param notSignFieldName 不加密字段名称
//	 * @param valueEmptySign value为空是否参与签名，true：参与签名，false：value不为空才参与签名
//	 * @param <T>
//	 * @return
//	 */
//	public static <T> String getMd5Sign(Map<String, String> params, String key, List<String> notSignFieldName,
//			boolean valueEmptySign) {
//		return MD5Util.getMd5Value(getSignOrigStr(params, key, notSignFieldName, valueEmptySign));
//	}
//
//	/**
//	 * 获取MD5加密签名原串
//	 *
//	 * @param t 对象
//	 * @param key 加密key
//	 * @param notSignFieldName 不加密字段名称
//	 * @param valueEmptySign value为空是否参与签名，true：参与签名，false：value不为空才参与签名
//	 * @param <T>
//	 * @return
//	 */
//	public static <T> String getSignOrigStr(T t, String key, List<String> notSignFieldName, boolean valueEmptySign) {
//		TreeMap<String, Object> treeMap = new TreeMap<>();
//		List<String> fieldNameList = MyBeanUtils.getFieldNameList(t.getClass(), Arrays.asList("serialVersionUID"));
//		if (fieldNameList != null && !fieldNameList.isEmpty()) {
//			for (String fieldName : fieldNameList) {
//				treeMap.put(fieldName, MyBeanUtils.getProperty(t, fieldName));
//			}
//		}
//		StringBuilder signOrigSB = new StringBuilder();
//		for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
//			String name = entry.getKey();
//			if (notSignFieldName != null && notSignFieldName.contains(name)) {
//				continue;
//			}
//			Object value = entry.getValue();
//			if (valueEmptySign || (!valueEmptySign && value != null)) {
//				signOrigSB.append(name);
//				signOrigSB.append("=");
//				if (value != null && !"".equals(value)) {
//					signOrigSB.append(value);
//				}
//				signOrigSB.append("&");
//			}
//		}
//		if (StringUtils.isNotEmpty(signOrigSB)) {
//			signOrigSB.replace(signOrigSB.lastIndexOf("&"), signOrigSB.length(), "");
//		}
//		if (StringUtils.isNotEmpty(key)) {
//			signOrigSB.append("&key=");
//			signOrigSB.append(key);
//		}
//		return signOrigSB.toString();
//	}
//
//	/**
//	 * 获取MD5加密签名原串
//	 *
//	 * @param paramMap 对象
//	 * @param key 加密key
//	 * @param notSignFieldName 不加密字段名称
//	 * @param valueEmptySign value为空是否参与签名，true：参与签名，false：value不为空才参与签名
//	 * @param <T>
//	 * @return
//	 */
//	public static <T> String getSignOrigStr(Map<String, String> paramMap, String key, List<String> notSignFieldName,
//			boolean valueEmptySign) {
//		if (paramMap == null || paramMap.isEmpty()) {
//			return "";
//		}
//		if (!(paramMap instanceof SortedMap)) {
//			paramMap = new TreeMap<>(paramMap);
//		}
//		TreeMap<String, Object> treeMap = new TreeMap<>();
//		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
//			String name = entry.getKey();
//			String value = entry.getValue();
//			treeMap.put(name, value);
//		}
//		StringBuilder signOrigSB = new StringBuilder();
//		for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
//			String name = entry.getKey();
//			if (notSignFieldName != null && notSignFieldName.contains(name)) {
//				continue;
//			}
//			Object value = entry.getValue();
//			if (valueEmptySign || (!valueEmptySign && value != null)) {
//				signOrigSB.append(name);
//				signOrigSB.append("=");
//				if (value != null && !"".equals(value)) {
//					signOrigSB.append(value);
//				}
//				signOrigSB.append("&");
//			}
//		}
//		if (StringUtils.isNotEmpty(signOrigSB)) {
//			signOrigSB.replace(signOrigSB.lastIndexOf("&"), signOrigSB.length(), "");
//		}
//		if (StringUtils.isNotEmpty(key)) {
//			signOrigSB.append("&key=");
//			signOrigSB.append(key);
//		}
//		// System.out.println();
//		// System.out.println("signOrigSB.toString >> " + signOrigSB.toString() + ".");
//		// System.out.println();
//		return signOrigSB.toString();
//	}
//
//	/**
//	 * 获取加密原串
//	 *
//	 * @param paramMap 需要加密的map
//	 * @param key 加密秘钥，传空则不拼接
//	 * @param valueEmptySign value为空是否参与签名，true：参与签名，false：value不为空才参与签名
//	 * @return
//	 */
//	public static String getSignOrigStr(Map<String, String> paramMap, String key, boolean valueEmptySign) {
//		if (paramMap == null || paramMap.isEmpty()) {
//			return "";
//		}
//		StringBuilder signOrigSB = new StringBuilder();
//		if (!(paramMap instanceof SortedMap)) {
//			paramMap = new TreeMap<>(paramMap);
//		}
//		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
//			String value = entry.getValue();
//			if (valueEmptySign || (!valueEmptySign && StringUtils.isNotEmpty(value))) {
//				signOrigSB.append(entry.getKey());
//				signOrigSB.append("=");
//				if (StringUtils.isNotEmpty(value)) {
//					signOrigSB.append(value);
//				}
//				signOrigSB.append("&");
//			}
//		}
//		if (signOrigSB.length() > 0) {
//			int lastIndex = signOrigSB.lastIndexOf("&");
//			signOrigSB.replace(lastIndex, lastIndex + 1, "");
//		}
//		if (StringUtils.isNotEmpty(key)) {
//			signOrigSB.append("&key=");
//			signOrigSB.append(key);
//		}
//		return signOrigSB.toString();
//	}
//
//	/***
//	 * MD5加密 生成32位md5码
//	 * 
//	 * @param 待加密字符串
//	 * @return 返回32位md5码
//	 */
//	public static String md5Encode(String inStr) throws Exception {
//		MessageDigest md5 = null;
//		try {
//			md5 = MessageDigest.getInstance("MD5");
//		} catch (Exception e) {
//			System.out.println(e.toString());
//			e.printStackTrace();
//			return "";
//		}
//
//		byte[] byteArray = inStr.getBytes("UTF-8");
//		byte[] md5Bytes = md5.digest(byteArray);
//		StringBuffer hexValue = new StringBuffer();
//		for (int i = 0; i < md5Bytes.length; i++) {
//			int val = (md5Bytes[i]) & 0xff;
//			if (val < 16) {
//				hexValue.append("0");
//			}
//			hexValue.append(Integer.toHexString(val));
//		}
//		return hexValue.toString();
//	}
//
//	private static MessageDigest md;
//
//	/**
//	 * 签名字符串
//	 *
//	 * @param text 需要签名的字符串
//	 * @param key 密钥
//	 * @param input_charset 编码格式
//	 * @return 签名结果
//	 */
//	public static String sign(String text, String key, String charset) throws Exception {
//		text = text + key;
//		return DigestUtils.md5Hex(getContentBytes(text, charset));
//
//	}
//
//	/**
//	 * 签名字符串
//	 *
//	 * @param text 需要签名的字符串
//	 * @param sign 签名结果
//	 * @param key 密钥
//	 * @param input_charset 编码格式
//	 * @return 签名结果
//	 */
//	public static boolean verify(String text, String sign, String key, String charset) throws Exception {
//		text = text + key;
//		String mysign = DigestUtils.md5Hex(getContentBytes(text, charset));
//		if (mysign.equals(sign)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * @param content
//	 * @param charset
//	 * @return
//	 * @throws SignatureException
//	 * @throws UnsupportedEncodingException
//	 */
//	private static byte[] getContentBytes(String content, String charset) {
//		if (charset == null || "".equals(charset)) {
//			return content.getBytes();
//		}
//		try {
//			return content.getBytes(charset);
//		} catch (UnsupportedEncodingException e) {
//			throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
//		}
//	}
//
//	/**
//	 * 通联签名
//	 * 
//	 * @param b
//	 * @return
//	 */
//	public static String computeDigest(byte[] b) {
//		try {
//			md = MessageDigest.getInstance("MD5");
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			return new String(b);
//		}
//		md.reset();
//		md.update(b);
//		byte[] hash = md.digest();
//		StringBuffer outStrBuf = new StringBuffer(32);
//		for (int i = 0; i < hash.length; i++) {
//			int v = hash[i] & 0xFF;
//			if (v < 16) {
//				outStrBuf.append('0');
//			}
//			outStrBuf.append(Integer.toString(v, 16).toLowerCase());
//		}
//		return outStrBuf.toString();
//	}
//
//	public static void main(String[] args) {
//		try {
//			String url = "http://106.14.99.159:8080/loanapp-api-web/v3/app/order/a56/grantMallCoupon.do";
//			Map<String, String> map = new HashMap<>();
//			map.put("orderId", "1510052222477");
//			map.put("thirdNo", "1510052222477-0");
//			map.put("billNo", "1510052222477-0");
//			map.put("tradAmount", "0");
//			map.put("tradCreateTime", DateUtil.getDateString(new Date(), DateUtil.yyyyMMddHHmmss));
//			map.put("tradFinishTime", DateUtil.getDateString(new Date(), DateUtil.yyyyMMddHHmmss));
//			String key = "8PgJukbgmia7FMJk3FCvbd8C4fd4HtD3t2qA3GwajRY4e658Zfw9JDLQ8z0TFaJa";
//			String currentSecretKey = getMd5Sign(map, key, Arrays.asList("sign"), true);
//			map.put("sign", currentSecretKey);
//			String json = HttpClientHelper.post(url, "UTF-8", map);
//			System.out.println(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
