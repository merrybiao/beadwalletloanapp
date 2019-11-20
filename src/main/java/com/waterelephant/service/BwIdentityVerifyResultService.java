package com.waterelephant.service;

public interface BwIdentityVerifyResultService {
	
	boolean saveIdentityVerifyResult(Long orderId, Integer verifySource, Integer verifyType, Integer verifyResult, String verifyScore);

}
