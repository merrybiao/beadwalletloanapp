package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.BwFundInfo;

/**
 * 融360 - 公积金 - 账户信息（code0085）
 * 
 * Module:
 * 
 * BwFundInfoService.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <融360 - 公积金 - 账户信息>
 */
public interface BwFundInfoService {

	/**
	 * 融360 - 公积金 - 保存
	 * 
	 * @param bwFundInfo
	 * @return
	 */
	public Long save(BwFundInfo bwFundInfo);

	/**
	 * 融360 - 公积金 - 删除
	 * 
	 * @param bwFundInfo
	 * @return
	 */
	public boolean deleteBwFundInfo(BwFundInfo bwFundInfo);

}
