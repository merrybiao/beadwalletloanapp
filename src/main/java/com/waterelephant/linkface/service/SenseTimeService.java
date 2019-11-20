package com.waterelephant.linkface.service;

import java.util.Map;

public interface SenseTimeService {
	
	Map<String,Object> ocrIdcard(String imageUrl,String imageData,String side) throws Exception;
	
	Map<String,Object> ocrBankcard(String imageUrl,String imageData) throws Exception;
	
	Map<String,Object> imageVerification(String firstImg,String secondImg) throws Exception;
	
	Map<String,Object> imageVerificationUrl(String firstImgUrl,String secondImgUrl) throws Exception;

	boolean saveIdentityCardInfo(Map<String, Object> resultMap) throws Exception;

	Map<String, Object> idnumberVerification(String name, String idcardNumber, String imageUrl,String imageData) throws Exception;

	Map<String, Object> livenessIdnumberVerification(String name, String idcardNumber,String livenessUrl, String livenessData) throws Exception;


}
