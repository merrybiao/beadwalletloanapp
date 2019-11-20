package com.waterelephant.service;

import com.waterelephant.dto.RongOrderDto;

public interface BwRongOrderDetailService {

	//录入订单详细信息
	int save(RongOrderDto rongOrderDto) throws Exception ;
	//录入订单详细信息
	int saveNew(String biz_data) throws Exception ;
	
	
}
