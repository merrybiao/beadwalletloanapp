package com.waterelephant.authentication.service;

import java.util.Map;

/**
 * 系统权鉴中心-签名、验签、加密、解密
 * @author dinglinhao
 * @date
 *
 */
public interface SystemAuthenticationService {
	
	String getToken();
	
	String getAppKey();
	
	String getSignKey();

	String getSignKey(String appKey);
	
	String getPublicKey();

	String getPublicKey(String appKey);
	
	String getPrivateKey(String appKey);
	
	boolean verifyToken(String appKey,String appToken);

	boolean verifyToken(Map<String, String> paramsMap);

	boolean verifySign(Map<String, String> paramsMap,String signKey);

	

}
