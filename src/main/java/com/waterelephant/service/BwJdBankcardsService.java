package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwJdBankcards;

public interface BwJdBankcardsService {
	List<BwJdBankcards> findListByAttr(BwJdBankcards bwJdBankcards);

}