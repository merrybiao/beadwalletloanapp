package com.waterelephant.linkface.service;

import java.util.Map;

import com.waterelephant.utils.AppResponseResult;

/**
 * 商汤人脸识别（code0088）
 * 
 * 
 * Module:
 * 
 * LinkfaceService.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <商汤人脸识别>
 */
public interface LinkfaceService {

	/**
	 * 商汤人脸识别 - 身份证 - 正面
	 * 
	 * @param sessionId
	 * @param params
	 * @return AppResponseResult
	 */
	public AppResponseResult saveOcrIDCardFront(String sessionId, Map<String, String> params);

	/**
	 * 商汤人脸识别 - 身份证 - 反面
	 * 
	 * @param sessionId
	 * @param params
	 * @return AppResponseResult
	 */
	public AppResponseResult saveOcrIDCardBack(String sessionId, Map<String, String> params);

	/**
	 * 商汤人脸识别 - 身份证 - 保存身份证信息 - APP
	 * 
	 * @param sessionId
	 * @param paramMap
	 * @return
	 */
	public AppResponseResult saveIDCardInfoApp(String sessionId, Map<String, String> paramMap);

	/**
	 * 商汤人脸识别 - 身份证 - 保存身份证信息 - H5
	 * 
	 * @param sessionId
	 * @param paramMap
	 * @return
	 */
	public AppResponseResult saveIDCardInfoH5(String sessionId, Map<String, String> paramMap);

}
