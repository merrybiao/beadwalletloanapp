package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwFundInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwFundInfo2Service;

@Service
public class BwFundInfo2ServiceImpl extends BaseService<BwFundInfo, Long> implements BwFundInfo2Service {

	/**
	 * 
	 * @see com.waterelephant.service.BwFundInfo2Service#findListByAttr(com.waterelephant.entity.BwFundInfo)
	 */
	@Override
	public List<BwFundInfo> findListByAttr(BwFundInfo bwFundInfo) {
		return mapper.select(bwFundInfo);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwFundInfo2Service#findByAttr(com.waterelephant.entity.BwFundInfo)
	 */
	@Override
	public BwFundInfo findByAttr(BwFundInfo bwFundInfo) {
		return mapper.selectOne(bwFundInfo);
	}

}