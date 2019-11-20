package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwMarkRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMarkRecordService;

@Service
public class BwMarkRecordServiceImpl extends BaseService<BwMarkRecord, Long> implements BwMarkRecordService{

	@Override
	public int add(BwMarkRecord bwMarkRecord) {
		return mapper.insert(bwMarkRecord);
	}

}
