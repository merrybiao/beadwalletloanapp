package com.waterelephant.service;

import com.waterelephant.entity.BwZrobotPanguScore;

public interface BwZrobotPanguScoreService {
	
	Long save(String name,String idCardNum,String cellPhoneNum,String bankCardNum) throws Exception;
	
	boolean update(BwZrobotPanguScore entity) throws Exception;
	
	BwZrobotPanguScore queryPangu(String name,String idCardNum,String cellPhoneNum,String bankCardNum)throws Exception;

	BwZrobotPanguScore queryPanguRecord(String name, String idCardNum, String cellPhoneNum, String bankCardNum) throws Exception;

}
