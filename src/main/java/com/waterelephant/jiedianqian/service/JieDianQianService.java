package com.waterelephant.jiedianqian.service;


import java.util.Map;

import com.waterelephant.entity.BwOrder;
import com.waterelephant.jiedianqian.entity.JieDianQianResponse;

/**
 * 
 * 
 * Module: 
 * 
 * JieDianQianService.java 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <借点钱>
 */
public interface JieDianQianService {

	/**
	 * 借点钱用户检验接口
	 * @param sessionId
	 * @param phone
	 * @param id_number
	 * @return	JieDianQianResponse
	 */
	public JieDianQianResponse checkUserInfo(String sessionId, String name, String phone, String id_number);

	/**
	 * 还款试算
	 * @param sessionId
	 * @param amonut
	 * @return
	 */
	public JieDianQianResponse loanCalculate(String sessionId, String amonut,String orderNo);
	
	public JieDianQianResponse saveBwOrder(String sessionId,String bizData);
	
	public Map<String, Object> queryOrderInfo(String sessionId,BwOrder bwOrder);
	
}
