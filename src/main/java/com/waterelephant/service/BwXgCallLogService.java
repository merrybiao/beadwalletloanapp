package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgCallLog;

public interface BwXgCallLogService {

	List<BwXgCallLog> findListByAttr(BwXgCallLog bwXgCallLog);
}
