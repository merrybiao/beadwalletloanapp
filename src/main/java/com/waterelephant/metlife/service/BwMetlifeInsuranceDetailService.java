package com.waterelephant.metlife.service;

import java.util.List;

import com.waterelephant.entity.BwMetlifeInsuranceDetail;

public interface BwMetlifeInsuranceDetailService {
	
	List<BwMetlifeInsuranceDetail> queryInsuranceDetailListByBatchNo(String batchNo);
	
	boolean updatePolicyNo(Long id,String policyNo);

}
