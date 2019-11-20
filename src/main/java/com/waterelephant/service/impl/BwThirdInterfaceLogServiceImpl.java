package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwThirdInterfaceLog;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwThirdInterfaceLogService;

@Service
public class BwThirdInterfaceLogServiceImpl extends BaseService<BwThirdInterfaceLog, Long>
		implements BwThirdInterfaceLogService {
	@Override
	public int save(BwThirdInterfaceLog bwThirdInterfaceLog) {
		return mapper.insert(bwThirdInterfaceLog);
	}

}
