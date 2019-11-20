package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwCreditDhbInfo;

public interface BwCreditDhbInfoService {

	List<BwCreditDhbInfo> findListByAttr(BwCreditDhbInfo bwCreditDhbInfo);

}