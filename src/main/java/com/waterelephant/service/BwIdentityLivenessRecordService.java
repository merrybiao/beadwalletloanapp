package com.waterelephant.service;

public interface BwIdentityLivenessRecordService {
	
	boolean saveLivenessRecord(String productNo, String livenessSource,String idcardNum,String name,String livenessUrl,String livenessData, Object resultMap) throws Exception;
	
}
