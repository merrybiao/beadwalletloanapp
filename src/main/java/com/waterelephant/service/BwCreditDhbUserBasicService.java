package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwCreditDhbUserBasic;

public interface BwCreditDhbUserBasicService {

	List<BwCreditDhbUserBasic> findListByAttr(BwCreditDhbUserBasic bwCreditDhbUserBasic);

}