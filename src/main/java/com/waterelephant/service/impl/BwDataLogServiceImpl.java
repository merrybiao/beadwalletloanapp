package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwDataLog;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwDataLogService;

@Service
public class BwDataLogServiceImpl extends BaseService<BwDataLog, Long> implements BwDataLogService {

	@Override
	public int save(BwDataLog bwDataLog) {

		return mapper.insert(bwDataLog);
	}

}
