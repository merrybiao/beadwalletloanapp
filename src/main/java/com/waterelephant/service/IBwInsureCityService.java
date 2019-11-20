package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwInsureCity;

public interface IBwInsureCityService {

	//批量添加城市
	public boolean create();
	//获取社保可支持城市
	public List<BwInsureCity> getCitys();
	//获取城市
	public BwInsureCity getCity(Long id);
}
