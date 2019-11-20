package com.waterelephant.rongCarrier.jd.service;

import com.waterelephant.rongCarrier.jd.entity.Bankcards;

public interface BankcardsService {

	public boolean saveBankcards(Bankcards bankcards);
	
	public boolean updateBankcards(Bankcards bankcards);
	
	public Bankcards queryBankcards(Long borrowerId, String cardId);
}
