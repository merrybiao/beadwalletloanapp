/**
 * 
 */
package com.waterelephant.fudata.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.fudata.service.FudataSDKService;
import com.waterelephant.fudata.service.FudataService;

/**
 * @author dinglinhao
 *
 */
@Service
public class FudataServiceImpl extends FudataBaseService implements FudataService {
	
	private static final Logger logger = LoggerFactory.getLogger(FudataServiceImpl.class);
	
	@Override
	public String register(String token,String uid) throws Exception {
		
		String result = FudataSDKService.register(token, uid);
		
		return result;
	}

	@Override
	public String getOrganizationList(String token, String orgType) {
		
		String meghodName = getClass().getName().concat("getOrganizationList(),参数token:{},orgType:{}");
		
		String result = null;
		try {
			result = FudataSDKService.organizationList(token, orgType);
			
			if(!StringUtils.isEmpty(result)) {
				
				return JSONObject.parseObject(result).getString("data");
			}
		} catch (Exception e) {
			
			logger.error(meghodName+"请求SDKService失败或解析结果失败，return:{}",token,orgType,result);
			
		}
		
		return null;
	}
	

	@Override
	public String entryInfo(String token,String organizationId) throws Exception {
		
		String result = FudataSDKService.entryInfo(token, organizationId);
		
		return result;
	}

	

	@Override
	public String postUserInfo(String token,String openId, String userName, String idCard, String mobile) throws Exception {
		
		String result = FudataSDKService.postUserInfo(token, openId, userName, idCard, mobile);
		
		return result;
	}

	@Override
	public String crawler(String token,String openId,String organizationId,String entryId,String version,String custParams,Map<String,String> accountParams)throws Exception {
		
		String result = FudataSDKService.crawler(token, openId, organizationId, entryId, version, custParams, accountParams);
		
		return result;
	}

	@Override
	public String crawlerProgress(String token,String taskId, String refresh, String captchaMobile, String captchaPicture)
			throws Exception {
		
		String result = FudataSDKService.crawlerProgress(token, taskId, refresh, captchaMobile, captchaPicture);
		
		return result;
	}

	@Override
	public String crawlerStatus(String token,String taskId) throws Exception {
		
		String result = FudataSDKService.crawlerStatus(token, taskId);
		
		return result;
	}

	
	@Override
	public String rawData(String token,String openId, String taskId,String version) throws Exception {
		
		String result = FudataSDKService.rawData(token, openId, taskId, version);
		
		return result;
	}

	@Override
	public String getReport(String token,String openId, String taskId) throws Exception {
		
		String result = FudataSDKService.getReport(token, openId, taskId);
		
		return result;
	}

	
	@Override
	public String pageConfig(String token,String organizationId) throws Exception {
		
		String result = FudataSDKService.pageConfig(token, organizationId);
		
		return result;
	}

//	@Override
//	public String crawlerCallback(String token, String returnUrl) throws Exception {
//		
//		String result = FudataSDKService.crawlerCallback(token, returnUrl);
//		
//		return result;
//	}

}
