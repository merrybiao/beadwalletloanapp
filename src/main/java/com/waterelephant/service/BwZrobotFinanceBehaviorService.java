package com.waterelephant.service;

import com.waterelephant.entity.BwZrobotFinanceBehavior;

public interface BwZrobotFinanceBehaviorService {
	
	boolean update() throws Exception;
	
	BwZrobotFinanceBehavior queryByTransationId(String transationId) throws Exception;
	
	boolean save(BwZrobotFinanceBehavior record) throws Exception;
	
	BwZrobotFinanceBehavior queryFinanceBehavior(String name, String idCardNum, String cellPhoneNum) throws Exception;

}
