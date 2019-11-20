package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwZmxyRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwZmxyRecordService;

@Service
public class BwZmxyRecordServiceImpl extends BaseService<BwZmxyRecord, Long> implements BwZmxyRecordService {

	@Override
	public int saveBwZmxyRecord(BwZmxyRecord bwZmxyRecord) {
		return mapper.insert(bwZmxyRecord);
	}

}
