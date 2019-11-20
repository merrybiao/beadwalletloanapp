package com.waterelephant.fudata.service.impl;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.fudata.service.FudataSDKService;
import com.waterelephant.fudata.entity.PublickeyPostResponse;
import com.waterelephant.fudata.entity.TokenPostResponse;
import com.waterelephant.utils.RedisUtils;

public abstract class FudataBaseService {
    //富数授权令牌
	private static final String FUDATA_TOKEN ="zhengxin:fudata:token";
	//富数加密公钥
	private static final String FUDATA_PUBLICKEY = "zhengxin:fudata:publickey";
	//富数加密公钥ID
	private static final String FUDATA_PUBLICKEY_ID = "zhengxin:fudata:publickey_id";
	//富数机构列表（根据类型缓存2~10分钟）
	private static final String FUDATA_ORGANIZATION_KEY = "zhengxin:fudata:";
	//富数机构列表失效时间
	private static final int FUDATA_ORGANIZATION_EXPTIME = 600;//十分钟
	
	public String getToken() throws Exception {
		
		String token = null;
		
		token = RedisUtils.get(FUDATA_TOKEN);
		
		if(StringUtils.isEmpty(token)) {
			
			String resJson = FudataSDKService.getToken();
			
			TokenPostResponse response = JSONObject.parseObject(resJson, TokenPostResponse.class);
			
			token = response.getToken();
			
			if(!StringUtils.isEmpty(token)) {
				
				long tokenExpiry = response.getTokenExpiry().getTime();
				
				long curTime = System.currentTimeMillis();
				
				int expTime = (int)((tokenExpiry - curTime)/1000);//失效时间（秒）
				
				RedisUtils.setex(FUDATA_TOKEN, token,expTime);//设置token,及其失效时间
				
			}else {
				
				throw new Exception(response.getMsg()+"["+response.getCode()+"]");
			}
		}
		
		return token;
	}
	
	public String getPublickey(String token) throws Exception {
		
		String publickey = null;
		
		publickey = RedisUtils.get(FUDATA_PUBLICKEY);
		
		if(StringUtils.isEmpty(publickey)) {
			
			String resJson = FudataSDKService.getPublickey(token);
			
			PublickeyPostResponse response = JSONObject.parseObject(resJson, PublickeyPostResponse.class);
			
			publickey = response.getPublickey();
			
			if(!StringUtils.isEmpty(publickey)) {
				
				String publickeyId = response.getPublickeyId();
				
				long publickeyExpiry = response.getPublickeyExpiry().getTime();
				
				long curTime = System.currentTimeMillis();
				
				int expTime = (int)((publickeyExpiry - curTime)/1000);//失效时间（秒）

				RedisUtils.setex(FUDATA_PUBLICKEY, publickey, expTime);//设置publickey,及失效时间
				
				RedisUtils.setex(FUDATA_PUBLICKEY_ID, publickeyId, expTime);//设置publickeyId,及失效时间
				
			} else {
				
				throw new Exception(response.getMsg() +"["+response.getCode()+"]");
			}
		}
		
		return publickey;
	}
	
	public String getPublickeyId(String token) throws Exception {
		
		String publickeyId = null;
		
		publickeyId = RedisUtils.get(FUDATA_PUBLICKEY_ID);
		
		if(StringUtils.isEmpty(publickeyId)) {
			
			String resJson = FudataSDKService.getPublickey(token);
			
			PublickeyPostResponse response = JSONObject.parseObject(resJson, PublickeyPostResponse.class);
			
			publickeyId = response.getPublickeyId();
			
			if(!StringUtils.isEmpty(publickeyId)) {
				
				String publickey = response.getPublickey();
				
				long publickeyExpiry = response.getPublickeyExpiry().getTime();
				
				long curTime = System.currentTimeMillis();
				
				int expTime = (int)((publickeyExpiry - curTime)/1000);//失效时间（秒）

				RedisUtils.setex(FUDATA_PUBLICKEY, publickey, expTime);//设置publickey,及失效时间
				
				RedisUtils.setex(FUDATA_PUBLICKEY_ID, publickeyId, expTime);//设置publickeyId,及失效时间
				
			} else {
				
				throw new Exception(response.getMsg() +"["+response.getCode()+"]");
			}
		}
		
		return publickeyId;
	}
	
	public String organizationList(String token,String orgType) throws Exception {
		
		if(StringUtils.isEmpty(orgType)) return null;
		
		String key = FUDATA_ORGANIZATION_KEY.concat(orgType);
		
		String value = RedisUtils.get(key);
		
		if(StringUtils.isEmpty(value)) {
			
			value = getOrganizationList(token,orgType);
			
			if(!StringUtils.isEmpty(value)) {
			
				RedisUtils.setex(key, value, FUDATA_ORGANIZATION_EXPTIME);//设置KEY值及其失效时间
				
			}else {
				
				throw new Exception("调用FudataBaseService.getOrganizationList()获取富数机构列表信息失败,机构类型:"+orgType);
			}
		}
		
		return value;
	}
	
	public abstract String getOrganizationList(String token,String orgType);

}
