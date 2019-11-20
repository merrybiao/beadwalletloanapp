package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditDhbHistorySearch;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditDhbHistorySearchService;

@Service
public class BwCreditDhbHistorySearchServiceImpl extends BaseService<BwCreditDhbHistorySearch, Long>
		implements BwCreditDhbHistorySearchService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditDhbHistorySearchService#findListByAttr(com.waterelephant.entity.BwCreditDhbHistorySearch)
	 */
	@Override
	public List<BwCreditDhbHistorySearch> findListByAttr(BwCreditDhbHistorySearch bwCreditDhbHistorySearch) {
		return mapper.select(bwCreditDhbHistorySearch);
	}

}