package com.waterelephant.authentication.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.authentication.AuthenticationSDKService;
import com.waterelephant.authentication.ApiSignUtils;
import com.waterelephant.authentication.service.SystemAuthenticationService;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.utils.RedisUtils;
/**
 * 系统权鉴中心-签名、验签、加密、解密
 * @author dinglinhao
 * @date
 *
 */
@Service
public class SystemAuthenticationServiceImpl implements SystemAuthenticationService {
	
	private Logger logger = LoggerFactory.getLogger(SystemAuthenticationServiceImpl.class);
	private final static String REDIS_APP_KEY ="loanapp:appkey:";
	private final static String REDIS_APP_TOKEN ="loanapp:apptoken:";
	private final static String DEFAULT_USER_NAME = "default";

	public String getToken()  {
		String token = RedisUtils.get(REDIS_APP_TOKEN + DEFAULT_USER_NAME);
		if(StringUtils.isEmpty(token)) {
			String result = AuthenticationSDKService.getToken();
			logger.info("----【权鉴中心】---AuthenticationSDKService.getToken()返回结果：{}",result);
			if(StringUtils.isEmpty(result)) return "";
			token = parseResultAndSave(result);
		} else {
			long time = RedisUtils.ttl(REDIS_APP_TOKEN + DEFAULT_USER_NAME);
			//有效期小于5分钟，则刷新token,避免还没到失效时间token已过期的情况
			if(time < (5*60) && time >0) {
				String value = RedisUtils.get(REDIS_APP_KEY+ DEFAULT_USER_NAME);
				JSONObject load = JSON.parseObject(value);
				String refreshToken = load.getString("refreshToken");
				token = refreshToken(refreshToken);
			}
		}
		return token;
	}
	
	@Override
	public String getAppKey() {
		String appKey = "";
		//1、判断该appKey是否在redis中存在
		if(RedisUtils.exists(REDIS_APP_KEY + DEFAULT_USER_NAME)) {
			String value = RedisUtils.get(REDIS_APP_KEY+ DEFAULT_USER_NAME);
			JSONObject load = JSON.parseObject(value);
			appKey = load.getString("appKey");
		}
		return appKey;
	}
	
	@Override
	public String getSignKey() {
		String signKey = "";
		//1、判断该appKey是否在redis中存在
		if(RedisUtils.exists(REDIS_APP_KEY + DEFAULT_USER_NAME)) {
			String value = RedisUtils.get(REDIS_APP_KEY+ DEFAULT_USER_NAME);
			JSONObject load = JSON.parseObject(value);
			signKey = load.getString("signKey");
		}
		return signKey;
	}
	
	@Override
	public String getSignKey(String appKey) {
		String signKey = "";
		//1、判断该appKey是否在redis中存在
		try {
			if(RedisUtils.exists(REDIS_APP_KEY + appKey)) {
				String value = RedisUtils.get(REDIS_APP_KEY+ appKey);
				JSONObject load = JSON.parseObject(value);
				signKey = load.getString("signKey");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signKey;
	}
	
	@Override
	public String getPublicKey() {
		String publicKey = "";
		//1、判断该appKey是否在redis中存在
		if(RedisUtils.exists(REDIS_APP_KEY + DEFAULT_USER_NAME)) {
			String value = RedisUtils.get(REDIS_APP_KEY+ DEFAULT_USER_NAME);
			JSONObject load = JSON.parseObject(value);
			publicKey = load.getString("publicKey");
		}
		return publicKey;
	}
	
	@Override
	public String getPublicKey(String appKey) {
		String publicKey = "";
		//1、判断该appKey是否在redis中存在
		if(RedisUtils.exists(REDIS_APP_KEY + appKey)) {
			String value = RedisUtils.get(REDIS_APP_KEY+ appKey);
			JSONObject load = JSON.parseObject(value);
			publicKey = load.getString("publicKey");
		}
		return publicKey;
	}
	
	@Override
	public String getPrivateKey(String appKey) {
		String privateKey = "";
		//1、判断该appKey是否在redis中存在
		if(RedisUtils.exists(REDIS_APP_KEY + appKey)) {
			String value = RedisUtils.get(REDIS_APP_KEY+ appKey);
			JSONObject load = JSON.parseObject(value);
			privateKey = load.getString("privateKey");
		}
		return privateKey;
	}
	
	@Override
	public boolean verifyToken(String appKey,String appToken) {
		//1、判断该appKey是否在redis中存在
 		if(RedisUtils.exists(REDIS_APP_TOKEN + appKey)) {
			//2、redis中存在appKey，则通过appKey从redis中获取token
			String token  = RedisUtils.get(REDIS_APP_TOKEN + appKey);
			//3、判断token和该appToken是否相等
			boolean flag = appToken.equals(token);
			if(flag) return true;
			RedisUtils.del(REDIS_APP_TOKEN + appKey);//reids中校验失败则，清楚redis中的key
		}
 		
		//4、redis中不存在该appKey，则取权鉴中心校验
		String result = AuthenticationSDKService.verifyToken(getToken(),appKey,appToken, null, null);
		logger.info("----【权鉴中心】---AuthenticationSDKService.verifyToken()返回结果：{}",result);
		if(StringUtils.isEmpty(result)) return false;
		JSONObject jsonResult = JSON.parseObject(result);
		String sysCode = jsonResult.getString("sysCode");
		if(!"00000".equals(sysCode)){
			throw new BusinessException(sysCode, jsonResult.getString("sysMesg"));
		}
		//检验成功 解析并
		parseResultAndSave(appKey, appToken, result);
		return true;
		
	}
	
	private  String refreshToken(String refreshToken) {
		String result = AuthenticationSDKService.refreshToken(refreshToken, null);
		logger.info("----【权鉴中心】---AuthenticationSDKService.refreshToken()返回结果：{}",result);
		if(StringUtils.isEmpty(result)) return "";
		return parseResultAndSave(result);
	}
	
	private String parseResultAndSave(String result) {
		return parseResultAndSave(DEFAULT_USER_NAME, DEFAULT_USER_NAME, result);
	}
	
	private String parseResultAndSave(String appKey,String appToken,String result) {
		String token = "";
		JSONObject jsonResult = JSON.parseObject(result);
		String sysCode = jsonResult.getString("sysCode");
		JSONObject load = null;
		switch(sysCode){
			case "00000":
				load = jsonResult.getJSONObject("load");
				int expiresIn = load.getIntValue("expiresIn");
				token = StringUtils.isEmpty(load.getString("accessToken"))? appToken : load.getString("accessToken");
				RedisUtils.setex(REDIS_APP_TOKEN + appKey, token , (expiresIn-300));//通过appKey去token
				RedisUtils.set(REDIS_APP_KEY+ appKey, load.toJSONString());//通过appKey取秘钥等信息
				break;
			case "01003"://token过期
				String value = RedisUtils.get(REDIS_APP_KEY+ appKey);
				load = JSON.parseObject(value);
				String refreshToken = load.getString("refreshToken");
				token = refreshToken(refreshToken);
			case "9999":
			default:
				throw new BusinessException(sysCode, jsonResult.getString("sysMesg"));
		}
		return token;
	}
	

	@Override
	public boolean verifyToken(Map<String, String> paramsMap) {
		//获取appKey
		String appKey = null;
		if(!paramsMap.containsKey(ApiSignUtils.REQUEST_PARAM_APPKEY)) {
			//如果请求参数中没有appKey
			throw new IllegalArgumentException("参数错误~缺少appKey！");
		} else {
			appKey = paramsMap.get(ApiSignUtils.REQUEST_PARAM_APPKEY);
			Assert.hasText(appKey,"参数错误~appKey参数不能为空！");
		}
		
		//获取token字段，需验证token是否有效
		String appToken = null;
		if(!paramsMap.containsKey(ApiSignUtils.REQUEST_PARAM_TOKEN)) {
			throw new IllegalArgumentException("参数错误~缺少token！");
		} else {
			appToken = paramsMap.get(ApiSignUtils.REQUEST_PARAM_TOKEN);
			Assert.hasText(appToken,"参数错误~登录token不能为空！");
		}
		return verifyToken(appKey, appToken);
	}

	@Override
	public boolean verifySign(Map<String, String> paramsMap,String signKey) {
		
		String sign = null;
		if(!paramsMap.containsKey(ApiSignUtils.REQUEST_PARAM_SIGN)) {
			//如果请求参数中没有appKey
			throw new IllegalArgumentException("参数错误~缺少sign！");
		} else {
			sign = paramsMap.get(ApiSignUtils.REQUEST_PARAM_SIGN);
			Assert.hasText(sign,"参数错误~sign参数不能为空！");
		}
		
		String timestamp = null;
		long reqTime = 0l;
		if(!paramsMap.containsKey(ApiSignUtils.REQUEST_PARAM_TIMESTAMP)) {
			//如果请求参数中没有timestamp
			throw new IllegalArgumentException("参数错误~缺少timestamp！");
		}else {
			timestamp =String.valueOf(paramsMap.get(ApiSignUtils.REQUEST_PARAM_TIMESTAMP));
			Assert.hasText(timestamp,"参数错误~timestamp参数不能为空！");
			try {
				reqTime = Long.valueOf(timestamp);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		//验证请求时间是否超时
		if(Math.abs(System.currentTimeMillis() - reqTime) > ApiSignUtils._5_MINS) {
			throw new IllegalArgumentException("参数错误~签名超时！");
		}
		
		Assert.hasText(signKey, "验签失败，获取signKey失败~");
		//从参数中删除并返回sign字段的值
    	sign = paramsMap.remove("sign");
    	//通过参数生产签名
		String cSign = ApiSignUtils.createSign(paramsMap,signKey);
		//比较签名是否一致
		return cSign.equals(sign);
	}

}
