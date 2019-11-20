package com.waterelephant.service;

import java.util.Date;

import com.waterelephant.entity.BwGxbAuthRecord;
import com.waterelephant.gxb.dto.AuthInfoDto;

public interface BwGxbAuthRecordService {
	
	Long save(String name,String idCard,String phone,String authItem,String sequenceNo,String returnUrl,String notifyUrl,String timestamp) throws Exception;
	
	boolean update(Long id,String appId,String authStatus,Date authTime) throws Exception;
	
	BwGxbAuthRecord queryBySequenceNo(String sequenceNo)  throws Exception ;

	boolean updateStatus(AuthInfoDto authInfoDto)  throws Exception ;

	boolean update(BwGxbAuthRecord record)  throws Exception ;

}
