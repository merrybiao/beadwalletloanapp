package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwInsureInfo;

public interface BwInsureInfoService {

	// 保存社保信息
	public Long save(BwInsureInfo bwInsureInfo);

	// 保存社保信息
	public BwInsureInfo getByIdCard(String idCard);

	public List<BwInsureInfo> queryInfo(Long orderId);

	public void add(BwInsureInfo bwInsureInfo);

	/**
	 * 融360 - 社保 - 删除社保账户（code0084）
	 * 
	 * @param bwInsureInfo
	 */
	public boolean deleteBwInsureInfo(BwInsureInfo bwInsureInfo);

}
