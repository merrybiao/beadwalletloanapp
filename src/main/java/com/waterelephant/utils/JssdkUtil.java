/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.waterelephant.vo.JssdkSignature;

/**
 * 微信Jssdk工具类
 * 
 * @author HWang
 */
public class JssdkUtil {

	private static Logger logger = Logger.getLogger(JssdkUtil.class);

	/**
	 * 获取JssdkSignature
	 * 
	 * @param url
	 * @return
	 */
	public static JssdkSignature getJssdkSignature(String url) {
		JssdkSignature jssdkSignature = new JssdkSignature();
		String appId = SystemConstant.APPID;
		String nonceStr = create_nonce_str();
		String jsapi_ticket = WeixinUtil.getAccessTokenFromRedis().getTicket();
		String timestamp = create_timestamp();
		String signature = getSignature(jsapi_ticket, nonceStr, timestamp, url);
		jssdkSignature.setAppId(appId);
		jssdkSignature.setNonceStr(nonceStr);
		jssdkSignature.setSignature(signature);
		jssdkSignature.setTimestamp(timestamp);
		return jssdkSignature;
	}

	/**
	 * 获取签名
	 */
	private static String getSignature(String jsapi_ticket, String nonceStr,
			String timestamp, String url) {
		// 注意这里参数名必须全部小写，且必须有序
		String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
				+ nonceStr + "&timestamp=" + timestamp + "&url=" + url;
		String signature = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.error("{}", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("{}", e);
		}
		return signature;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
