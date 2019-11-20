package com.waterelephant.capital.service;

import java.util.TreeMap;

import com.waterelephant.utils.AppResponseResult;

/**
 * 蜗牛聚财 woNiu001
 * 
 * 
 * Module:
 * 
 * WoNiuService.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface WoNiuService {

	/**
	 * 蜗牛聚财 - 成功回调
	 * 
	 * @param TreeMap<String, String> tm
	 */

	AppResponseResult saveCallBackSuccess(TreeMap<String, String> tm);

	/**
	 * 蜗牛聚财 - 失败回调
	 * @param TreeMap<String, String> tm
	 * @return
	 */
	AppResponseResult saveCallBackFail(TreeMap<String, String> tm);


}
