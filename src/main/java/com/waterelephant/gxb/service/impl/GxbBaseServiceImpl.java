package com.waterelephant.gxb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.gxb.GxbSDK;


public class GxbBaseServiceImpl {
	
	private Logger logger = LoggerFactory.getLogger(GxbBaseServiceImpl.class);
	
	protected String token(String name,String idCard,String phone,String authItem,String timestamp,String sequenceNo) throws Exception {
		String token = null;
		String result =GxbSDK.getToken(name, phone, idCard, authItem, sequenceNo, timestamp);
		logger.info("请求GxbSDK.getToken()方法，返回结果：{}",result);
		JSONObject json = JSON.parseObject(result);
		if(null == json || !"0000".equals(json.getString("retCode")))
			return null;
		token = json.getString("retData");
		return token;
	}
	
	public String auth(String token,String returnUrl,String username) throws Exception {
		
		String result = GxbSDK.getAuth(token, returnUrl,username);
		
		JSONObject json = JSONObject.parseObject(result);
		
		if(null == json || !"0000".equals(json.getString("retCode"))){
			logger.info(json.getString("retMsg"));
			return null;
		}
		result = json.getString("retData");
		
		return result;
	}
	
	public String getRawdata(String token) throws Exception {
		
		String result = GxbSDK.getRawdata(token);
			
//		String appId = "gxb245a4612a88be532";
//		
//		String appSecret ="7b074422d8244be68fe4085de48a4afa";
//		
//		String timestamp = String.valueOf(new Date().getTime());
//		
//		String sign =  DigestUtils.md5Hex(String.format("%s%s%s", appId, appSecret, timestamp));
//		
//		result = httpGet("https://prod.gxb.io/crawler/data/rawdata/"+token+"?appId="+appId+"&timestamp="+timestamp+"&sign="+sign);
//		
		logger.debug("请求GxbSDK.getRawdata()方法，返回结果：{}",result);
		Assert.hasText(result, "请求异常，请稍后再试~");
		JSONObject json = JSON.parseObject(result);
		
		Integer reCode = json.getInteger("retCode");
		
		if(null != reCode && reCode.intValue() ==1) {
			return json.getString("data");
		}else {
			throw new Exception(json.getString("retMsg")+"["+json.getString("retCode")+"]");
		}
	}

	public String getReport(String token) throws Exception {
		//TODO SDK 没提供该方法~
//		String result = GxbSDK.getReport();
//		logger.info("请求GxbSDK.getReport()方法，返回结果：{}",result);
		return null;
	}
	
	/*public static String postByJson(String requestUrl,String content) {
		
		OutputStreamWriter out = null;
		InputStream inputStream = null;
		String result = "";
		try {
			URL url = new URL(requestUrl);

			if (requestUrl.startsWith("https")) {
				SslUtils.ignoreSsl();
			}

			URLConnection urlConnection = url.openConnection();
			// 设置doOutput属性为true表示将使用此urlConnection写入数据
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("content-type", "application/json; charset=UTF-8");
			// 得到请求的输出流对象
			out = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
			// 把数据写入请求的Body
			out.write(content);
			out.flush();
			// 从服务器读取响应
			inputStream = urlConnection.getInputStream();
			result = IOUtils.toString(inputStream, "utf-8");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}

				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String httpGet(String requestUrl) {

		OutputStreamWriter out = null;
		InputStream inputStream = null;
		String result = "";
		try {
			URL url = new URL(requestUrl);
			if (requestUrl.startsWith("https")) {
				SslUtils.ignoreSsl();
			}
			URLConnection urlConnection = url.openConnection();
			// 从服务器读取响应
			inputStream = urlConnection.getInputStream();
			result = IOUtils.toString(inputStream, "utf-8");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}

				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}*/
	
}
