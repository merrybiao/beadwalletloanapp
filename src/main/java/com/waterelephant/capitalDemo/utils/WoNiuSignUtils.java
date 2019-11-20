package com.waterelephant.capitalDemo.utils;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import com.waterelephant.utils.CommUtils;

/**
 * 蜗牛聚财 woNiu001
 * 
 * 
 * Module:
 * 
 * WoNiuSignUtils.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class WoNiuSignUtils {

	/**
	 * 蜗牛聚财 - 生成签名字符串
	 * 
	 * @param reqMap
	 * @param key
	 * @return
	 */
	public static String generateSign(String json, String key) {
		String signStr = json + key;
		String sign = new String(org.apache.commons.codec.binary.Base64.encodeBase64(signStr.getBytes()));
		return sign;
	}

	/**
	 * 蜗牛聚财 - 向指定 URL 发送POST方法的请求
	 * 
	 * @param requestUrl 发送请求的 URL
	 * @param param 请求参数，请求参数是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostRequestBody(String requestUrl, String param) throws Exception {
		OutputStreamWriter out = null;
		InputStream inputStream = null;
		String result = "";
		try {
			URL url = new URL(requestUrl);
			URLConnection urlConnection = url.openConnection();
			// 设置doOutput属性为true表示将使用此urlConnection写入数据
			urlConnection.setDoOutput(true);
			//x-www-form-urlencoded
			urlConnection.setRequestProperty("content-type", "x-www-form-urlencoded; charset=UTF-8");
			// 得到请求的输出流对象
			out = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
			// 把数据写入请求的Body
			out.write(param);
			out.flush();
			// 从服务器读取响应
			inputStream = urlConnection.getInputStream();
			result = IOUtils.toString(inputStream, "utf-8");
			// System.out.println(result);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}
		return result;
	}

	/**
	 * 蜗牛聚财 - 验签
	 * 
	 * @param source
	 * @param securtyKey
	 * @param sign
	 * @return
	 */
	public static boolean verifySign(String json, String securtyKey, String sign) {
		if (CommUtils.isNull(sign) == true) {
			return false;
		}

		String verifySign = generateSign(json, securtyKey);
		if (sign.equals(verifySign) == true) {
			return true;
		} else {
			return false;
		}
	}
}
