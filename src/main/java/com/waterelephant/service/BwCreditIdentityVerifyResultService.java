package com.waterelephant.service;

public interface BwCreditIdentityVerifyResultService {

	boolean saveIdentityVerifyResult(Long borrowerId,Long creditId, Integer verifySource, Integer verifytype, Integer verifyResult,
			String verifyScore);
}
