package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCapitalInsurance;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCapitalInsuranceService;

@Service
public class BwCapitalInsuranceServiceImpl extends BaseService<BwCapitalInsurance, Long>
		implements BwCapitalInsuranceService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCapitalInsuranceService#findListByAttr(com.waterelephant.entity.BwCapitalInsurance)
	 */
	@Override
	public List<BwCapitalInsurance> findListByAttr(BwCapitalInsurance bwCapitalInsurance) {
		return mapper.select(bwCapitalInsurance);
	}

}