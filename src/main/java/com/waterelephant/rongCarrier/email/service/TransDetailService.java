package com.waterelephant.rongCarrier.email.service;

import com.waterelephant.rongCarrier.email.entity.TransDetail;

public interface TransDetailService {
	
	public boolean saveTransDetail(TransDetail transDetail);
	
	public boolean updateTransDetail(TransDetail transDetail);
	
	public TransDetail queryTransDetail(Long borrowerId, long transDate);
}
