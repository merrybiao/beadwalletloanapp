package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwFuiouBankCity;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwFuiouBankCityService;

@Service
public class BwFuiouBankCityServiceImpl extends BaseService<BwFuiouBankCity, Long> implements BwFuiouBankCityService{

	@Override
	public BwFuiouBankCity findCityByName(String cityName) {
		String sql = "SELECT * FROM fuiou_bank_city WHERE city_name = #{cityName}";
		return sqlMapper.selectOne(sql, cityName, BwFuiouBankCity.class);
	}

}
