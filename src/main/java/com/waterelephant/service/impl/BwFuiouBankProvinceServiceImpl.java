package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwFuiouBankCity;
import com.waterelephant.entity.BwFuiouBankProvince;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwFuiouBankProvinceService;

@Service
public class BwFuiouBankProvinceServiceImpl extends BaseService<BwFuiouBankProvince, Long> implements BwFuiouBankProvinceService{

	@Override
	public BwFuiouBankProvince findProvinceByName(String provinceName) {
		String sql = "SELECT * FROM fuiou_bank_province WHERE province_name = #{provinceName}";
		return sqlMapper.selectOne(sql, provinceName, BwFuiouBankProvince.class);
	}


}
