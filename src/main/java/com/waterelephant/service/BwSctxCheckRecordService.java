package com.waterelephant.service;

import com.waterelephant.entity.BwSctxCheckRecord;

public interface BwSctxCheckRecordService {
	
	BwSctxCheckRecord saveRecord(String name,String mobile,String idCard,String checkResult);

}
