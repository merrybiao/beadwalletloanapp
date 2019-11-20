package com.waterelephant.service;

import com.waterelephant.entity.BwFuiouBankProvince;

public interface BwFuiouBankProvinceService {

	//根据省份名称查询富友对应的code
	BwFuiouBankProvince findProvinceByName(String provinceName);
	
}
