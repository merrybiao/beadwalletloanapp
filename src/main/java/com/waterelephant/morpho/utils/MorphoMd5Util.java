package com.waterelephant.morpho.utils;

import java.util.ResourceBundle;

import com.waterelephant.utils.MD5Util;
import com.yeepay.g3.utils.common.json.JSONException;
import com.yeepay.g3.utils.common.json.JSONObject;
/**
 * code:18005
 * @pram 请求json
 * @author Lion
 *
 */
public class MorphoMd5Util {
	public static String encoding(String data) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("morpho");
			String key = bundle.getString("key");
			return MD5Util.md5(key + data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static void main(String[] args) throws JSONException {
		String s = encoding("{\"name\":\"孟艳丽\",\"mobile\":\"15537826131\",\"pid\":\"410224198411111667\",\"loan_type\":\"1\"}");
		System.out.println(s);
	/*	JSONObject o = new JSONObject();
		o.put("pk", "111222");
		JSONObject o1 = new JSONObject();
		o1.put("name", "骆建明");
		o1.put("mobile", "13926516542");
		o1.put("pid", "440307198607061918");
		o1.put("loan_type", "1");
		o.put("data", o1);
		System.out.println(o.toString());*/
	}
}
