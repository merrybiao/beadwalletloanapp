package com.waterelephant.capital.koudai;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright (C), 2011-2018 温州贷 FileName: com.wzdai.web.core.utils.KdaiSignUtils.java Author: wangyingjie Email:
 * wangyingjie@wzdai.com Date: 2017/6/21 14:43 Description: History: <Author> <Time> <version> <desc> wangyingjie 14:43
 * 1.0 Create
 */
public class KdaiSignUtils {

	/**
	 * 生成签名字符串
	 * 
	 * @param reqMap
	 * @param key
	 * @return
	 */
	public static Map<String, String> createSign(Map<String, String> reqMap, String key) {

		// 加签原串
		String str = "";
		try {
			// 对请求参数进行urlencode加密
			for (Map.Entry<String, String> entry : reqMap.entrySet()) {
				reqMap.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 对参数进行升序排序
		List<Map.Entry<String, String>> sortMap = KdaiSignUtils.argSort(reqMap);

		for (int i = 0; i < sortMap.size(); i++) {
			str += sortMap.get(i).getKey() + "=" + sortMap.get(i).getValue() + "&";
		}
		// 清除最后一个&
		if (str.length() > 1) {
			str = str.substring(0, str.length() - 1);
		}
		Map<String, String> map = new HashMap<>();
		String params = str;
		map.put("params", params);

		String signStr = str + key;

		String sign = new String(org.apache.commons.codec.binary.Base64.encodeBase64(signStr.getBytes()));
		map.put("sign", sign);
		// return new String(org.apache.commons.codec.binary.Base64.encodeBase64(str.getBytes()));
		return map;
	}

	/**
	 * 对请求参数进行处理
	 * 
	 * @param reqMap
	 * @return
	 */
	public static String reqStrUtil(Map<String, String> reqMap) {
		String reqStr = "";
		for (Map.Entry<String, String> entry : reqMap.entrySet()) {
			reqStr += entry.getKey() + "=" + entry.getValue() + "&";
		}
		return reqStr.substring(0, reqStr.length() - 1);
	}

	/**
	 * 排序 a-z 根据键排序
	 * 
	 * @param map
	 * @return
	 */
	public static List<Map.Entry<String, String>> argSort(Map<String, String> map) {
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
		// 然后通过比较器来实现排序
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			// 升序排序
			@Override
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}

		});

		return list;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
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
			urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
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
	 * 口袋回调验签
	 * 
	 * @param params
	 * @param securtyKey
	 * @return
	 */
	public static boolean verifySign(JSONObject params, String securtyKey) {
		if (params.getString("sign") == null) {
			return false;
		}
		String sign = getSign(params, securtyKey);
		return params.getString("sign").equals(sign);

	}

	/**
	 * 签名
	 *
	 * @param params 待签名数据
	 * @param securtyKey 密钥
	 * @return
	 */
	public static String getSign(JSONObject params, String securtyKey) {

		StringBuffer content = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		// 待签名数组，去除sign
		keys.remove("sign");
		// 按key排序
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.getString(key);
			try {
				content.append((i == 0 ? "" : "&") + key + "=" + URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		String signSrc = content.append(securtyKey).toString();
		return getBASE64(signSrc);
	}

	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return org.apache.commons.codec.binary.Base64.encodeBase64String(s.getBytes());

	}

	public static Map<String, String> getNotityMap(String params) throws Exception {
		String[] paramsStrs = params.split("&");
		Map<String, String> map = new HashMap<>();
		if (paramsStrs.length > 0) {
			for (String str : paramsStrs) {

				if (2 == str.split("=").length) {
					map.put(str.split("=")[0], URLDecoder.decode(str.split("=")[1], "utf-8"));
				} else if (1 == str.split("=").length) {
					map.put(str.split("=")[0], "");
				}

			}
		}
		return map;

	}

}
