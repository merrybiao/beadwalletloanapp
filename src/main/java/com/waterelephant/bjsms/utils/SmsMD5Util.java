package com.waterelephant.bjsms.utils;

import java.util.ResourceBundle;

import com.waterelephant.utils.MD5Util;
/**
 * code:18020
 * @author Lion
 * 北京短信接口参数md5加密
 */
public class SmsMD5Util {
	public static String encoding(String data) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("bjsms");
			String key = bundle.getString("key");
			return MD5Util.md5(key + data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
