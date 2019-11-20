package com.waterelephant.service;

import com.waterelephant.entity.BwSctxBaseinfo;


public interface IBwSctxBaseinfoService {
	
	public Long saveBaseInfo(String name,String mobile,String idCard,String onlyId);
	
	public BwSctxBaseinfo queryBaseInfoByonlyId(String onlyId); 
	
}
