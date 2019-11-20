package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwRateDictionary;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwRateDictionaryService;

@Service
public class BwRateDictionaryService extends BaseService<BwRateDictionary, Long> implements IBwRateDictionaryService{

	@Override
	public BwRateDictionary findBwRateDictionaryByAttr(BwRateDictionary bwRateDictionary) {

		return mapper.selectOne(bwRateDictionary);
	}

}
