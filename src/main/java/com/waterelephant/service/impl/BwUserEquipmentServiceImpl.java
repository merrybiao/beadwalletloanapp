package com.waterelephant.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwUserEquipment;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwUserEquipmentService;

@Service
public class BwUserEquipmentServiceImpl extends BaseService<BwUserEquipment, Long> implements BwUserEquipmentService {
	private Logger logger = Logger.getLogger(BwUserEquipmentServiceImpl.class);

	@Override
	public int save(BwUserEquipment bwUserEquipment) {
		return  mapper.insert(bwUserEquipment);
	}

}
