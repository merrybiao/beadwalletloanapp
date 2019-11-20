package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.BwFundInfo;
import com.waterelephant.rongCarrier.service.BwFundInfoService;
import com.waterelephant.service.BaseService;

/**
 * 
 * 融360 - 公积金- 账户信息（code0085）
 * 
 * Module:
 * 
 * BwFundInfoServiceImpl.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwFundInfoServiceImpl extends BaseService<BwFundInfo, Long> implements BwFundInfoService {

	/**
	 * 融360 - 公积金 - 保存账户信息
	 * 
	 * @see com.waterelephant.rongCarrier.service.BwFundInfoService#save(com.waterelephant.rongCarrier.entity.BwFundInfo)
	 */
	@Override
	public Long save(BwFundInfo bwFundInfo) {
		mapper.insert(bwFundInfo);
		return bwFundInfo.getId();
	}

	/**
	 * 融360 - 公积金 - 删除账户信息
	 * 
	 * @see com.waterelephant.rongCarrier.service.BwFundInfoService#deleteBwFundInfo(com.waterelephant.rongCarrier.entity.BwFundInfo)
	 */
	@Override
	public boolean deleteBwFundInfo(BwFundInfo bwFundInfo) {
		return mapper.delete(bwFundInfo) > 0;
	}

}
