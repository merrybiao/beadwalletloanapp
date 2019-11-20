package com.waterelephant.service;

import java.util.List;
import java.util.Map;

public interface BwOrderHourStatisticsService {
	
	Map<String,Object> currOrderApplyCountStatistics();
	
	Map<String,Object> currOrderApplyCountStatistics(String startTime,String endTime);

	Map<String, Object> currOrderAuditCountStatistics();
	
	Map<String, Object> currOrderAuditCountStatistics(String startTime, String endTime);
	
	Map<String, Object> currOrderLoansCountStatistics();
	
	Map<String, Object> currOrderLoansCountStatistics(String startTime, String endTime);
	
	List<Map<String,Object>> queryCurrOrderApplyCount(Integer[] channels);
	
	List<Map<String,Object>> queryCurrOrderAuditCount(Integer[] channels);
	
	List<Map<String,Object>> queryCurrOrderLoansCount(Integer[] channels);


}
