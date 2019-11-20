package com.waterelephant.zlgxb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.zlgxb.ZlgxbSDK;

public class ZlgxbdealSdk {
	
	private Logger logger = LoggerFactory.getLogger(ZlgxbdealSdk.class);
	
	/**
	 * 获取token
	 * @param name
	 * @param idcard
	 * @param phone
	 * @param timestamp
	 * @param authItem
	 * @param sequenceNo
	 * @return
	 */
	public String getToken(String name,String idcard,String phone,String timestamp,String authItem,String sequenceNo) throws Exception{
		
		String tokenData = ZlgxbSDK.getToken(name, phone, idcard, authItem, sequenceNo, timestamp);
		
		JSONObject jsonToken = JSONObject.parseObject(tokenData);
		
		String resp = null;
		
		if("0000".equals(jsonToken.getString("retCode"))){
			
			JSONObject json = jsonToken.getJSONObject("retData");
			
			 resp = json.getString("token");
			
		} 
		logger.info("获取token返回数据为："+resp);
		return resp;

	}
	
	/**
	 * 返回授权链接
	 * @param token
	 * @param returnUrl
	 * @return
	 */
	public JSONObject getAutuData(String token,String returnUrl) throws Exception{
		
		String autudata = ZlgxbSDK.getAuth(token, returnUrl);
		
		JSONObject jsonToken = JSONObject.parseObject(autudata);
		
		JSONObject resp = null;
		
		if("0000".equals(jsonToken.getString("retCode"))){
			
			resp = jsonToken.getJSONObject("retData");
			
		}
		logger.info("返回授权链接返回数据为："+resp);
		return resp;
		
	}
	
	/**
	 * 获取信用分
	 * @param token
	 * @return
	 */
	public String getScore(String token) throws Exception{
		
		String score = ZlgxbSDK.getRawdata(token);
		
		JSONObject jsonToken = JSONObject.parseObject(score);
		
		String resp = null;
		
		if("0000".equals(jsonToken.getString("retCode"))){
			
			resp = jsonToken.getString("retData");
			
		}
		logger.info("获取信用分返回数据为："+resp);
		return resp;
	}

}
