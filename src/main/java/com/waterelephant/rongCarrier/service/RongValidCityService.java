package com.waterelephant.rongCarrier.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.rongCarrier.entity.RongValidCity;

/**
 * 
 * Module: 融360 - 可用城市（code0084）
 * 
 * RongValidCityService.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <融360 - 可用城市>
 */
public interface RongValidCityService {

	public boolean save(RongValidCity rongValidCity);

	public boolean saveBatchOfSheBao() throws Exception;

	public boolean deleteZ(RongValidCity rongValidCity);

	/**
	 * 融360 - 根据城市名判断是否存在
	 * 
	 * @param cityName
	 * @return
	 */
	public boolean findByCityNameOfLike(String cityName, int type);

	/**
	 * 融360 - 查询可用省份
	 * 
	 * @param rongValidCity
	 * @return
	 */
	List<Map<String, Object>> findProvinceList(int type);

	/**
	 * 融360 - 查询可用城市
	 * 
	 * @param rongValidCity
	 * @return
	 */
	public List<Map<String, Object>> findCityList(int type, String province);

}
