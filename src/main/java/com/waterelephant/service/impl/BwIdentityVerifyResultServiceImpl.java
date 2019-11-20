package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwIdentityVerifyResult;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwIdentityVerifyResultService;

@Service
public class BwIdentityVerifyResultServiceImpl extends BaseService<BwIdentityVerifyResult, Long> implements BwIdentityVerifyResultService {
	/**
	 * 保存验证记录
	 * @param orderId 工单ID 
	 * @param verifySource 验证来源 1：face++；2：商汤
	 * @param type 1:正面照和活体照片对比 2:三方预留照片和活体照片对比
	 * @param result 1:对比通过 2:对比通过,但分数过低, 3:对比失败
	 * @param score 比对分数
	 * @return 保存成功与否 true/false
	 */
	@Override
	public boolean saveIdentityVerifyResult(Long orderId, Integer verifySource, Integer type, Integer result, String score) {
		BwIdentityVerifyResult record = new BwIdentityVerifyResult();
		record.setOrderId(orderId);
		record.setType(type);
		record.setScore(score);
		record.setResult(result);
		record.setVerifySource(verifySource);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		return insert(record)>0;
	}

}
