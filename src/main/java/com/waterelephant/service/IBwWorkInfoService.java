package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwWorkInfo;

public interface IBwWorkInfoService {
	
	//添加工作信息
	public int save(BwWorkInfo bwWorkInfo,Long bid);
	
	//修改工作信息
	public int update(BwWorkInfo bwWorkInfo);
	
	//根据借款人id查询借款人工作信息
	public BwWorkInfo findBwWorkInfoById(Object obj);
	
	//根据主键id和工单id查询工单工作信息
	public List<BwWorkInfo> findByIdAndOrderId(Long id,Long orderId);
	
	/**
	 * 根据条件查询单位信息
	 * @param workInfo 单位信息封装实体
	 * @return
	 */
	public BwWorkInfo findBwWorkInfoByAttr(BwWorkInfo workInfo);
	
	/**
	 * 根据工单id查询工作信息
	 * @param orderId
	 * @return
	 */
	BwWorkInfo findBwWorkInfoByOrderId(Long orderId);
	
	/**
	 * 添加工作信息
	 */
	int addBwWorkInfo(BwWorkInfo bwWorkInfo);
	
}
