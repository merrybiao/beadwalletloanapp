package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgCallLog;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgCallLogService;

@Service
public class BwXgCallLogServiceImpl extends BaseService<BwXgCallLog, Long> implements BwXgCallLogService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgCallLogService#findListByAttr(com.waterelephant.entity.BwXgCallLog)
	 */
	@Override
	public List<BwXgCallLog> findListByAttr(BwXgCallLog bwXgCallLog) {
		return mapper.select(bwXgCallLog);
	}

}
