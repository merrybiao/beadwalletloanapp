package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwAuthRejectRecordService;

@Service
public class BwAuthRejectRecordServiceImpl extends BaseService<BwRejectRecord, Long> implements BwAuthRejectRecordService{

	@Override
	public Long save(BwRejectRecord bwAuthRejectRecord) {
		mapper.insert(bwAuthRejectRecord);
		return bwAuthRejectRecord.getId();
	}

}
