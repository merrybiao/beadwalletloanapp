package com.waterelephant.service;

import java.util.Map;

import com.waterelephant.entity.BwOrder;

public interface QueryOrderBusiService {
	
	Map<String,Object> queryOrder(String idcardNo,String name,String mobile) throws Exception;
	
	/**
	 * <p> 获取运营商数据
	 * @param bwOrder 
	 * @param appId
	 * @param gzip
	 * @return
	 */
	Map<String,Object> queryOperatorData(BwOrder bwOrder,String appId,boolean gzip) throws Exception;
	/**
	 * <p> 获取京东数据
	 * @param bwOrder
	 * @param appId
	 * @param gzip
	 * @return
	 */
	Map<String, Object> queryJdData(BwOrder bwOrder, String appId, boolean gzip) throws Exception;
	/**
	 * <p>获取淘宝数据
	 * @param bwOrder
	 * @param appId
	 * @param gzip
	 * @return
	 */
	Map<String, Object> queryTaobaoData(BwOrder bwOrder, String appId, boolean gzip) throws Exception;

}
