package com.waterelephant.service;

import com.waterelephant.dto.RongOrderDto;

public interface BwRongOrderAddInfoService {

	//录入订单补充信息
	int save(RongOrderDto rongOrderDto) throws Exception ;
	
	//录入订单补充信息
	int saveNew(String biz_data) throws Exception ;
}
