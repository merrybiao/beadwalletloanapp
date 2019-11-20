package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwCreditDhbHistorySearch;

public interface BwCreditDhbHistorySearchService {

	List<BwCreditDhbHistorySearch> findListByAttr(BwCreditDhbHistorySearch bwCreditDhbHistorySearch);

}