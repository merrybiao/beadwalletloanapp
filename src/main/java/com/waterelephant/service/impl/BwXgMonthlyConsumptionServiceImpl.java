package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgMonthlyConsumption;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgMonthlyConsumptionService;

@Service
public class BwXgMonthlyConsumptionServiceImpl extends BaseService<BwXgMonthlyConsumption, Long>
		implements BwXgMonthlyConsumptionService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgMonthlyConsumptionService#findListByAttr(com.waterelephant.entity.BwXgMonthlyConsumption)
	 */
	@Override
	public List<BwXgMonthlyConsumption> findListByAttr(BwXgMonthlyConsumption bwXgMonthlyConsumption) {

		return mapper.select(bwXgMonthlyConsumption);
	}

}
