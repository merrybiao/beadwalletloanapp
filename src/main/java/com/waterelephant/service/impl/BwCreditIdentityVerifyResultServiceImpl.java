package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditIdentityVerifyResult;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditIdentityVerifyResultService;

/**
 * @author dinglinhao
 *
 */
@Service
public class BwCreditIdentityVerifyResultServiceImpl extends BaseService<BwCreditIdentityVerifyResult, Long> implements BwCreditIdentityVerifyResultService {

	@Override
	public boolean saveIdentityVerifyResult(Long borrowerId,Long creditId, Integer verifySource, Integer verifytype, Integer verifyResult,
			String verifyScore) {
		BwCreditIdentityVerifyResult record = new BwCreditIdentityVerifyResult();
		record.setBorrowerId(borrowerId);
		record.setRelationId(creditId);
		record.setRelationType(1);
		record.setType(verifytype);
		record.setScore(verifyScore);
		record.setResult(verifyResult);
		record.setVerifySource(verifySource);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		return insert(record)>0;
	}

}
