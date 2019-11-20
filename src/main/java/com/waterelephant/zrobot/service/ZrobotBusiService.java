package com.waterelephant.zrobot.service;

import java.util.Map;

import com.waterelephant.zrobot.dto.ZrobotPanguScoreDto;

public interface ZrobotBusiService {

	String getToken() throws Exception;

	Map<String,Object> queryfb(String token, String name, String idCardNum, String cellPhoneNum)
			throws Exception;

	boolean savefb(String name, String idCardNum, String cellPhoneNum, Map<String, Object> resultMap) throws Exception;
	
	ZrobotPanguScoreDto queryPangu(String name,String idCardNum,String cellPhoneNum,String bankCardNum) throws Exception;
	
	Long savePangu(String name,String idCardNum,String cellPhoneNum,String bankCardNum) throws Exception;
	
	boolean updatePangu(Long id,ZrobotPanguScoreDto dto) throws Exception;

}
