package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwCreditDhbRiskBlacklist;

public interface BwCreditDhbRiskBlacklistService {

	List<BwCreditDhbRiskBlacklist> findListByAttr(BwCreditDhbRiskBlacklist bwCreditDhbRiskBlacklist);

}