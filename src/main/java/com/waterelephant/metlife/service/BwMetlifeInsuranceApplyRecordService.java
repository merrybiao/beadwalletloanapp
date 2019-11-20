package com.waterelephant.metlife.service;

import java.util.List;

import com.waterelephant.entity.BwMetlifeInsuranceApplyRecord;
import com.waterelephant.metlife.vo.MetLifeInsuredVo;

public interface BwMetlifeInsuranceApplyRecordService {
	
	Long save(MetLifeInsuredVo vo,Integer applyState)  throws Exception;
	
	int queryApplyRecordCount(String orderNo,List<Object> applyState);
	
	List<BwMetlifeInsuranceApplyRecord> queryApplyRecord(String productNo,String trimDate,String orderNo,String insuredIdNo,String insuredName,String insuredMobile);

	boolean updateApplyRecord(Long id, int applyState,String message);

	BwMetlifeInsuranceApplyRecord queryApplyRecordByOrderNo(String orderNo,String productNo);


}
