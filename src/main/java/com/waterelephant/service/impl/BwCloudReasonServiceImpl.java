package com.waterelephant.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCloudReason;
import com.waterelephant.mapper.BwCloudReasonMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCloudReasonService;

@Service
public class BwCloudReasonServiceImpl extends BaseService<BwCloudReason, Long> implements BwCloudReasonService {

	@Resource
	private BwCloudReasonMapper bwCloudReasonMapper;

	@Override
	public void saveBwCloudReason(BwCloudReason bwCloudReason) {
		mapper.insert(bwCloudReason);
	}

	@Override
	public void updateBwCloudReason(BwCloudReason bwCloudReason) {
		mapper.updateByPrimaryKey(bwCloudReason);
	}

	@Override
	public BwCloudReason findBwCloudReason(String orderPushNo) {
		return bwCloudReasonMapper.findBwCloudReason(orderPushNo);
	}

}
