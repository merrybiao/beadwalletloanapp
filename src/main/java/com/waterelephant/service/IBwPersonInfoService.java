package com.waterelephant.service;

import java.util.Map;

import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;

public interface IBwPersonInfoService {

	BwPersonInfo getbyAttr(BwPersonInfo bwPersonInfo);

	Long save(BwPersonInfo bwPersonInfo);

	int addBwPersonInfo(BwPersonInfo bwPersonInfo);

	BwPersonInfo getByIdAndOrderId(Long id, Long orderId);

	int update(BwPersonInfo bwPersonInfo);

	/**
	 * 根据工单号查找个人信息
	 * 
	 * @param orderId 工单号
	 * @return
	 */
	BwPersonInfo findBwPersonInfoByOrderId(Long orderId);

	/**
	 * 根据主键id查找个人信息
	 * 
	 * @param id 主键id
	 * @return
	 */
	BwPersonInfo findBwPersonInfoById(Long id);

	/**
	 * 统计亲属联系人电话号码被使用的次数
	 * 
	 * @param phone
	 * @return
	 */
	int countRelation(String phone, Long userId);

	/**
	 * 统计非亲属联系人电话号码被使用的次数
	 * 
	 * @param phone
	 * @return
	 */
	int countUnRelation(String phone, Long userId);

	/**
	 * 根据电话号码统计被作为联系人的次数
	 * 
	 * @param phone
	 * @return
	 */
	int count(String phone);

	/**
	 * 根据地区获得机构ID
	 * 
	 * @param cityName
	 * @return
	 */
	Map<String, Object> getOrgId(String cityName);

	/**
	 * 根据地区获得城市ID
	 * 
	 * @param cityName
	 * @return
	 */
	Map<String, Object> getCityId(String cityName);

	BwPersonInfo findBwPersonInfoByOrderId(String valueOf);

	void add(BwPersonInfo bwPersonInfo);

	BwWorkInfo queryWork(Long orderId);

	void addWork(BwWorkInfo bwWorkInfo);

	BwPersonInfo findBwPersonInfoByOrderIdNew(String orderId);

}
