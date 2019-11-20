package com.waterelephant.operatorData.service;

import java.util.Map;

@Deprecated
public interface OperatorsDataService {

	Map<String, Object> packageOperatorsData(Long borrowerId,Long orderId);
	
	Map<String, Object> packageOperatorsData(Long borrowerId,Long orderId,int channel);

}
