package com.waterelephant.rongCarrier.service;

import java.util.Map;

public interface Rong360BussinessService {
	
	Map<String,Object> queryData(String searchId,String type,String appId,boolean gzip);
}
