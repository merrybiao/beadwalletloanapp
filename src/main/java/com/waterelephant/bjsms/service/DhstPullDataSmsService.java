package com.waterelephant.bjsms.service;

import java.util.List;

import com.waterelephant.bjsms.entity.BwDhstreportSms;


public interface DhstPullDataSmsService {
		
	public boolean saveMongoSms(List<BwDhstreportSms> listReport) throws Exception;
	
}
