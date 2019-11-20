package com.waterelephant.service;

import com.waterelephant.entity.BwUserEquipment;

public interface BwUserEquipmentService {

	//保存第三方传过来用户设备信息
	int save(BwUserEquipment bwUserEquipment);
	
}
