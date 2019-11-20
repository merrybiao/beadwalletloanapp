package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditDhbRiskBlacklist;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditDhbRiskBlacklistService;

@Service
public class BwCreditDhbRiskBlacklistServiceImpl extends BaseService<BwCreditDhbRiskBlacklist, Long>
		implements BwCreditDhbRiskBlacklistService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditDhbRiskBlacklistService#findListByAttr(com.waterelephant.entity.BwCreditDhbRiskBlacklist)
	 */
	@Override
	public List<BwCreditDhbRiskBlacklist> findListByAttr(BwCreditDhbRiskBlacklist bwCreditDhbRiskBlacklist) {
		return mapper.select(bwCreditDhbRiskBlacklist);
	}

}