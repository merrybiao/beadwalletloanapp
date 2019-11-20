/**
 * 
 */
package com.waterelephant.fudata.service;

import java.util.Map;

/**
 * @author dinglinhao
 *
 */
public interface FudataService {
	
	String getToken() throws Exception;
	
	String getPublickey(String token) throws Exception;
	
	String getPublickeyId(String token) throws Exception;
	
	String register(String token,String uid) throws Exception;

	String organizationList(String token,String organizationType)  throws Exception;

	String entryInfo(String token,String organizationId) throws Exception;
	
	String postUserInfo(String token,String openId,String userName,String idCard,String mobile) throws Exception;
	
	String crawler(String token,String openId,String organizationId,String entryId,String version,String custParams,Map<String,String> accountParams) throws Exception;
	
	String crawlerProgress(String token,String taskId,String refresh,String captchaMobile,String captchaPicture) throws Exception;
	
	String crawlerStatus(String token,String taskId) throws Exception;
	
	String rawData(String token,String openId,String taskId,String version) throws Exception;
	
	String getReport(String token,String openId,String taskId) throws Exception;
	
	String pageConfig(String token,String organizationId) throws Exception;

//	String crawlerCallback(String token, String returnUrl) throws Exception;

}

