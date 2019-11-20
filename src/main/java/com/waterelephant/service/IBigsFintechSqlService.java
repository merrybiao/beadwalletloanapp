package com.waterelephant.service;

import com.waterelephant.entity.BwBigsFintech;

public interface IBigsFintechSqlService {
	
	public Integer saveApplyInfo(BwBigsFintech bwbigsfintech) throws Exception;
	
	public BwBigsFintech selectPhoneQueryTotal(String mobile,String orderId,String borrowerId) throws Exception;

}
