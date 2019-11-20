package com.waterelephant.metlife.service;

import java.util.List;

import com.waterelephant.metlife.dto.MetlifeInsuranceOrderDto;
import com.waterelephant.metlife.vo.MetLifeInsuredVo;

public interface BwMetlifeInsuranceOrderService {

	boolean createOrder(MetLifeInsuredVo vo, Long rId) throws Exception ;
	
	List<String> queryInsurancePolicyNo(String productNo,String trimDate,String orderNo);

	List<MetlifeInsuranceOrderDto> queryInsuranceOrderList(Long rId, String orderNo) throws Exception;

	List<MetlifeInsuranceOrderDto> queryInsuranceListByPolicyNo(String policyNo, String productNo) throws Exception;

	List<MetlifeInsuranceOrderDto> queryOrderApplyState(String orderNo);
	
	boolean updateStateByRid(Long rId,int state);

	boolean updatePolicyNoByUuid(String uuid, String policyNo);

}
