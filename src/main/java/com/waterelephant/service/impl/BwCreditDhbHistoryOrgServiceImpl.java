package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditDhbHistoryOrg;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditDhbHistoryOrgService;

@Service
public class BwCreditDhbHistoryOrgServiceImpl extends BaseService<BwCreditDhbHistoryOrg, Long>
		implements BwCreditDhbHistoryOrgService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditDhbHistoryOrgService#findListByAttr(com.waterelephant.entity.BwCreditDhbHistoryOrg)
	 */
	@Override
	public List<BwCreditDhbHistoryOrg> findListByAttr(BwCreditDhbHistoryOrg bwCreditDhbHistoryOrg) {
		return mapper.select(bwCreditDhbHistoryOrg);
	}

}