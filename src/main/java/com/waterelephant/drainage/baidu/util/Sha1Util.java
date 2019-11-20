package com.waterelephant.drainage.baidu.util;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.drainage.baidu.entity.RepaymentPlanData;

/**
 * sha1加密
 * 
 * @author DIY
 *
 */
public class Sha1Util {

	private static String signKey = Constant.KEY;

	/**
	 * 调用百度的接口验签
	 * 
	 * @param paramMap
	 * @return
	 * @throws DigestException
	 */
	public static String getParamStrSHA1(Map<String, String> paramMap) throws DigestException {
		Iterator<String> iter = paramMap.keySet().iterator();
		String[] paramArray = new String[paramMap.size()];
		String paramData = null, key = null;
		Object value = null;
		int i = 0;
		while (iter.hasNext()) {
			key = iter.next();
			value = paramMap.get(key);
			paramArray[i] = key + "=" + value;
			i++;
		}
		Arrays.sort(paramArray);
		// 遍历出数组中的数据并拼接成字符传
		StringBuilder strB = new StringBuilder();
		for (int j = 0; j < paramArray.length; j++) {
			String str = paramArray[j];
			strB.append(str + "&");
		}
		String paramStr = strB.toString().substring(0, strB.toString().length() - 1);
		// String paramStrEncode = URLEncoder.encode(paramStr, "UTF-8");
		paramData = paramStr + "&key=" + signKey;
		System.out.println(paramData);
		return sha1(paramData);
	}

	/**
	 * 对数据进行sha1加密
	 * 
	 * @param data
	 * @return
	 */
	public static String sha1(String data) throws DigestException {
		try {
			// 指定sha1加密
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(data.getBytes());
			// 获取字节数组
			byte[] digestBytes = messageDigest.digest();
			StringBuilder hexString = new StringBuilder();
			// 将字节组转换为十六进制数组
			for (int i = 0; i < digestBytes.length; i++) {
				String shaHex = Integer.toHexString(digestBytes[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}

			return hexString.toString().toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new DigestException("签名错误！");
		}
	}

	public static void main(String[] args) throws DigestException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timestamp", 1488854485);
		map.put("tp_code", "test");
		map.put("tp_code", "test");
		RepaymentPlanData repaymentPlanData = new RepaymentPlanData();
		repaymentPlanData.setAmount(2);
		repaymentPlanData.setDue_time(3);
		repaymentPlanData.setPlan_id("20");
		repaymentPlanData.setReal_repay_time(22);
		repaymentPlanData.setStatus(0);
		Map<String, String> paramMap = (Map<String, String>) JSONObject
				.parse(JSONObject.toJSONString(repaymentPlanData));
		System.out.println(paramMap);
		System.out.println(map);
		System.out.println(getParamStrSHA1(paramMap));
		System.out.println();
		System.out.println(sha1("amount=2&due_time=3&plan_id=20&real_repay_time=22&status=0&key=3FADAE9950B216AF"));
	}
}
