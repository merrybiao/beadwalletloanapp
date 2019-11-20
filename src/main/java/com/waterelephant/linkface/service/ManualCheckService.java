package com.waterelephant.linkface.service;

import java.util.Map;

import com.waterelephant.utils.AppResponseResult;

/**
 * 活体验证 - 人工审核（code0088）
 * 
 * 
 * Module:
 * 
 * ManualCheckService.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ManualCheckService {

	/**
	 * 活体验证 - 人工审核 - 正面（code0088）
	 * 
	 * @param sessionId
	 * @param params
	 * @return
	 */
	public AppResponseResult saveOcrIDCardFront(String sessionId, Map<String, String> params);

	/**
	 * 活体验证 - 人工审核 - 反面（code0088）
	 * 
	 * @param sessionId
	 * @param paramMap
	 * @return
	 */
	public AppResponseResult saveOcrIDCardBack(String sessionId, Map<String, String> paramMap);

	/**
	 * 活体验证 - 人工审核 - APP - 保存身份证信息（code0088）
	 * 
	 * @param sessionId
	 * @param paramMap
	 * @return
	 */
	public AppResponseResult saveIDCardInfoApp(String sessionId, Map<String, String> paramMap);

	/**
	 * 活体验证 - 人工审核 - H5 - 保存身份证信息（code0088）
	 * 
	 * @param sessionId
	 * @param paramMap
	 * @return
	 */
	public AppResponseResult saveIDCardInfoH5(String sessionId, Map<String, String> paramMap);

}
