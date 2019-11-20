package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwCapitalInsurance;

public interface BwCapitalInsuranceService {
	List<BwCapitalInsurance> findListByAttr(BwCapitalInsurance bwCapitalInsurance);
}