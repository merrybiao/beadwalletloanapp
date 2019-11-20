package com.waterelephant.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditCloudReason;
import com.waterelephant.mapper.BwCreditCloudReasonMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditCloudReasonService;

@Service
public class BwCreditCloudReasonServiceImpl extends BaseService<BwCreditCloudReason, Long>
		implements BwCreditCloudReasonService {

	@Resource
	private BwCreditCloudReasonMapper bwCreditCloudReasonMapper;

	@Override
	public void saveBwCreditCloudReason(BwCreditCloudReason bwCreditCloudReason) {
		mapper.insert(bwCreditCloudReason);
	}

	@Override
	public void updateBwCreditCloudReason(BwCreditCloudReason bwCreditCloudReason) {
		mapper.updateByPrimaryKey(bwCreditCloudReason);
	}

	@Override
	public BwCreditCloudReason findBwCreditCloudReason(String creditPushNo) {
		return bwCreditCloudReasonMapper.findBwCreditCloudReason(creditPushNo);
	}

}
