package com.waterelephant.sctx.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.BwSctxBaseinfo;


public interface IBigsFintechServiceV1 {
	
	public List<Map<String,String>> saveApplyInfo(Long id,String name,String mobile,String idCard,String onlyId) throws Exception;
	
	public Long saveBaseInfo(String name, String mobile, String idCard,String onlyId)  throws Exception;
	
	public List<Map<String,String>> querydataBaseInfoByonlyId(String onlyId)  throws Exception;
	
	public BwSctxBaseinfo queryBaseinfoByonlyId(String onlyId)   throws Exception;


}
