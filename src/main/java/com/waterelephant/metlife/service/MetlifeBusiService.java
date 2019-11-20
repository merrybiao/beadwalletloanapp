package com.waterelephant.metlife.service;

import java.util.List;

import com.waterelephant.entity.BwMetlifeInsuranceApplyRecord;
import com.waterelephant.metlife.dto.MetlifeInsuranceOrderDto;
import com.waterelephant.metlife.vo.MetLifeInsuredVo;

public interface MetlifeBusiService {
	
	boolean checkApplyInsurance(MetLifeInsuredVo vo) throws Exception;

	boolean checkCancelInsurance(String productNo,String orderNo,String remark) throws Exception;
	
	BwMetlifeInsuranceApplyRecord queryApplyState(String orderNo,String productNo) throws Exception;
	
//	BwMetlifeInsuranceDetail queryInsuranceDetail(String orderNo) throws Exception;
	
	List<MetlifeInsuranceOrderDto> queryInsuranceOrderList(String orderNo, String productNo) throws Exception;

	List<String> queryPolicyNoByTrimDate(String productNo, String trimDate) throws Exception;

	List<String> queryPolicyNoByOrderNo(String productNo, String orderNo) throws Exception;

	List<MetlifeInsuranceOrderDto> queryInsuranceListByPolicyNo(String policyNo,String productNo) throws Exception;

	List<MetlifeInsuranceOrderDto> queryOrderApplyState(String orderNo);

	int updatePolicyNo(String batchNo, String policyNo);

}
