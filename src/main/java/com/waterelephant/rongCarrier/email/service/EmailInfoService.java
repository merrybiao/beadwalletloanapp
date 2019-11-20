package com.waterelephant.rongCarrier.email.service;

import com.waterelephant.rongCarrier.email.entity.EmailInfo;

public interface EmailInfoService {

	public boolean saveEmailInfo(EmailInfo emailInfo);
	
	public boolean updateEmailInfo(EmailInfo emailInfo);
	
	public EmailInfo queryEmailInfo(Long borrowerId, String mail, Long paymentCurDate);
}
