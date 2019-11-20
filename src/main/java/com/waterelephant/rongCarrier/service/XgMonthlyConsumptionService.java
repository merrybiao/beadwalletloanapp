package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgMonthlyConsumption;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 16:45
 */
public interface XgMonthlyConsumptionService {

	public boolean save(XgMonthlyConsumption xgMonthlyConsumption);
	public boolean saveToNewTab(XgMonthlyConsumption xgMonthlyConsumption);

	public boolean deleteZ(XgMonthlyConsumption xgMonthlyConsumption);
	public boolean deleteToNewTab(XgMonthlyConsumption xgMonthlyConsumption);



}
