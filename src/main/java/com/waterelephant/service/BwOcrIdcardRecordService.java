package com.waterelephant.service;

import java.util.Map;

public interface BwOcrIdcardRecordService {
	
	boolean saveFront(String productNo, String ocrSource, Map<String, Object> resultMap,Object object,String imageUrl,String imageData) throws Exception;
	
	boolean saveBack(String productNo,String ocrSource,String idcardNumber,Map<String,Object> resultMap,Object object,String imageUrl,String imageData) throws Exception;
}
