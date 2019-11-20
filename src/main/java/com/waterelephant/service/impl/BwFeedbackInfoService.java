package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwFeedbackInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwFeedbackInfoService;

@Service
public class BwFeedbackInfoService extends BaseService<BwFeedbackInfo, Long> implements IBwFeedbackInfoService{

	@Override
	public int addBwFeedbackInfo(BwFeedbackInfo bwFeedbackInfo) {

		return mapper.insert(bwFeedbackInfo);
	}

}
