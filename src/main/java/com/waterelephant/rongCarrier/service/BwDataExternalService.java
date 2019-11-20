package com.waterelephant.rongCarrier.service;

import java.util.Map;

import com.waterelephant.rongCarrier.entity.BwDataExternal;

public interface BwDataExternalService {
	
	boolean save(Map<String,String> params,String platform) throws Exception;
	//保存
	boolean save(BwDataExternal record) throws Exception;
	//更新状态
	BwDataExternal updateState(String userId,String outUniqueId,String state) throws Exception;
	//更新查询信息
	BwDataExternal updateSearchInfo(String userId,String outUniqueId,String searchId,String state,String account,String accountType,String errorReasonDetail) throws Exception;
	
	BwDataExternal selectRecord(String userId,String outUniqueId) throws Exception;
	
	BwDataExternal selectOneBySearchId(String searchId);
	
}
