package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditDhbBindingIdcards;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditDhbBindingIdcardsService;

@Service
public class BwCreditDhbBindingIdcardsServiceImpl extends BaseService<BwCreditDhbBindingIdcards, Long>
		implements BwCreditDhbBindingIdcardsService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditDhbBindingIdcardsService#findListByAttr(com.waterelephant.entity.BwCreditDhbBindingIdcards)
	 */
	@Override
	public List<BwCreditDhbBindingIdcards> findListByAttr(BwCreditDhbBindingIdcards bwCreditDhbBindingIdcards) {
		return mapper.select(bwCreditDhbBindingIdcards);
	}

}