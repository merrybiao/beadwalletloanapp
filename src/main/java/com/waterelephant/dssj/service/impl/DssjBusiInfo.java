package com.waterelephant.dssj.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.dssj.DssjAfterSDK;
import com.waterelephant.utils.RedisUtils;

public class DssjBusiInfo {
	
	/**
	 * 获取token
	 * @return Token
	 */
	public String getToken() throws Exception{
		Map<String,String> map  = new HashMap<>();
		String token = null;
		if(RedisUtils.exists("dssj_token")){
			token  = RedisUtils.get("dssj_token");
			map.put("token", token);
		} else {
			JSONObject json  = JSONObject.parseObject(DssjAfterSDK.getToken());
			if("0000".equals(json.getString("retCode"))){
				JSONObject jsonObject = json.getJSONObject("retData");
				if(!jsonObject.isEmpty()){
					token = jsonObject.getString("token");
					map.put("token", token);
					String expire = jsonObject.getString("expire");
					map.put("expire", expire);
					long expirelong = Long.parseLong(expire);
					long nowTime = System.currentTimeMillis();
					String time = String.valueOf((expirelong-nowTime)/1000);
					RedisUtils.setex("dssj_token", token, Integer.parseInt(time));
				} else {
					throw new IllegalArgumentException("调用SDK[getoken]返回的[retData]数据为空");
				}
			} else {
				throw new IllegalArgumentException("调用SDK[getoken]返回的retCode为：["+json.getString("retCode")+"]返回的retData为："+json.getJSONObject("retData"));
			}
		}
		return JSON.toJSONString(map);
	};
	
	public String getAuth(String name,String idCard,String phone,String token,String tiNo) throws Exception{
		String auth = DssjAfterSDK.getAuth(name, idCard, phone, token,tiNo);
		JSONObject json = JSONObject.parseObject(auth);
		if("0000".equals(json.getString("retCode"))){
			return json.getString("retData");
		} else {
			throw new IllegalArgumentException("调用SDK[getAuthData]返回的retCode为：["+json.getString("retCode")+"]返回的retData为："+json.getJSONObject("retData"));
		}

	}
	
	/**
	 * 手动获取授权后的信用分
	 * @param name
	 * @param idCard
	 * @param phone
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String getScoreByQuery(String name,String idCard,String phone,String token) throws Exception{
		String authdata = DssjAfterSDK.getAuthData(name, idCard, phone, token);
		JSONObject json = JSONObject.parseObject(authdata);
		if("0000".equals(json.getString("retCode"))){
			return json.getString("retData");
		} else {
			throw new IllegalArgumentException("调用SDK[getAuthData]返回的retCode为：["+json.getString("retCode")+"]返回的retData为："+json.getJSONObject("retData"));
		}
	}
}
