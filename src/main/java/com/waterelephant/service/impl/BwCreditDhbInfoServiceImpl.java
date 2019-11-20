package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditDhbInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditDhbInfoService;

@Service
public class BwCreditDhbInfoServiceImpl extends BaseService<BwCreditDhbInfo, Long> implements BwCreditDhbInfoService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditDhbInfoService#findListByAttr(com.waterelephant.entity.BwCreditDhbInfo)
	 */
	@Override
	public List<BwCreditDhbInfo> findListByAttr(BwCreditDhbInfo bwCreditDhbInfo) {
		return mapper.select(bwCreditDhbInfo);
	}

}