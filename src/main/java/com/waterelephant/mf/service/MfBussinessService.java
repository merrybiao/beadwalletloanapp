package com.waterelephant.mf.service;

import java.util.Map;

public interface MfBussinessService {
	
	Map<String,Object> queryTaskReport(String taskId,boolean gzip);
	
	Map<String,Object> queryTaskReport(String taskId,String appId,boolean gizp);
	
	Map<String,Object> queryTaskData(String taskId,boolean gzip);
	
	Map<String,Object> queryTaskData(String taskId,String appId,boolean gizp);

	boolean saveTaskData(String taskId,Long orderId);

	boolean saveTaskReport(String taskId, Long orderId);
	

}
