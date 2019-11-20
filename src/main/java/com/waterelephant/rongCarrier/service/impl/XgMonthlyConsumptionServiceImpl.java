package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgMonthlyConsumption;
import com.waterelephant.rongCarrier.service.XgMonthlyConsumptionService;
import com.waterelephant.service.BaseNewTabService;

@Service
public class XgMonthlyConsumptionServiceImpl extends BaseNewTabService<XgMonthlyConsumption, Long> implements XgMonthlyConsumptionService {

	@Override
	public boolean save(XgMonthlyConsumption xgMonthlyConsumption) {
		return mapper.insert(xgMonthlyConsumption)>0;
	}
	
	@Override
	public boolean saveToNewTab(XgMonthlyConsumption xgMonthlyConsumption) {
		return insertNewTab(xgMonthlyConsumption)>0;
	}

	@Override
	public boolean deleteZ(XgMonthlyConsumption xgMonthlyConsumption) {
		return mapper.delete(xgMonthlyConsumption) > 0;
	}

	@Override
	public boolean deleteToNewTab(XgMonthlyConsumption xgMonthlyConsumption) {
		return deleteNewTab(xgMonthlyConsumption)>0;
	}

}
