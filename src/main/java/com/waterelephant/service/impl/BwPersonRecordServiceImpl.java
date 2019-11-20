package com.waterelephant.service.impl;


import org.springframework.stereotype.Service;
import com.waterelephant.entity.BwPersonRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwPersonRecordService;

@Service
public class BwPersonRecordServiceImpl extends BaseService<BwPersonRecord, Long> implements BwPersonRecordService {

	
	@Override
	public int saveBwPersonRecord(BwPersonRecord bwPersonRecord) {
		return mapper.insert(bwPersonRecord);
	}


}
