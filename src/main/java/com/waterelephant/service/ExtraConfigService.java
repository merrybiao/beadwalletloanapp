package com.waterelephant.service;

/**
 *
 * Module: 银行绑定次数配置类
 * 
 * ExtraConfigService.java
 * 
 * @author wangkun
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ExtraConfigService {

	/**
	 * 通过code查询出配置信息
	 * 
	 * @param code
	 * @return
	 */
	String findCountExtraConfigByCode(String code);
}
