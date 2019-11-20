package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgMonthlyConsumption;

public interface BwXgMonthlyConsumptionService {

	List<BwXgMonthlyConsumption> findListByAttr(BwXgMonthlyConsumption bwXgMonthlyConsumption);
}
