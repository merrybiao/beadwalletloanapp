package com.waterelephant.baiqishi.service;

import java.util.Map;

import com.waterelephant.baiqishi.json.DecisionJSON;
import com.waterelephant.utils.AppResponseResult;

/**
 * 白骑士
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/23 10:39
 */
public interface BaiQiShiService {
	/**
	 * 白骑士决策访问
	 * 
	 * @return
	 */
	AppResponseResult decision(Map params) throws Exception;

	/**
	 * 请求白骑士封装
	 * 
	 * @param borrowerId
	 * @param orderId
	 * @param eventType
	 * @param otherMap
	 * @return
	 * @throws Exception
	 */
	public boolean saveBaiQiShi(Long borrowerId, Long orderId, String eventType, String tokenKey,
			Map<String, String> otherMap) throws Exception;

	Long saveBaiqishiExternal(DecisionJSON decisionJSON, Long borrowerId, Long orderId, String eventType);
}
