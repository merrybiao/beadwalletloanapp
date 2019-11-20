package com.waterelephant.service;

import com.waterelephant.entity.BwMoheAuthRecord;

public interface BwMoheAuthRecordService {
	
	boolean saveRecord(String productNo,String authType,String realName,String idcardNum,String userMobile,String reqId,String returnUrl,String notifyUrl);
	
	BwMoheAuthRecord updateRecord(String reqId,String taskId,String notifyEvent,String notifyType,String notifyTime);

	BwMoheAuthRecord queryAuthRecordByTaskId(String taskId);
	

}
