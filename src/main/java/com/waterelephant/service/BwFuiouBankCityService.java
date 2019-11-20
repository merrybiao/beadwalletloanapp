package com.waterelephant.service;

import com.waterelephant.entity.BwFuiouBankCity;

public interface BwFuiouBankCityService {
 
	//根据城市名称查询富友对应的code 6214 8327 0701 0646
	BwFuiouBankCity findCityByName(String cityName);
	
}
