package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwJdBankcards;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwJdBankcardsService;

@Service
public class BwJdBankcardsServiceImpl extends BaseService<BwJdBankcards, Long> implements BwJdBankcardsService {

	/**
	 * 
	 * @see com.waterelephant.service.BwJdBankcardsService#findListByAttr(com.waterelephant.entity.BwJdBankcards)
	 */
	@Override
	public List<BwJdBankcards> findListByAttr(BwJdBankcards bwJdBankcards) {
		return mapper.select(bwJdBankcards);
	}

}