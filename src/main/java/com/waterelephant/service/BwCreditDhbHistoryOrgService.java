package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwCreditDhbHistoryOrg;

public interface BwCreditDhbHistoryOrgService {

	List<BwCreditDhbHistoryOrg> findListByAttr(BwCreditDhbHistoryOrg bwCreditDhbHistoryOrg);

}