package com.waterelephant.service.impl;




import org.springframework.stereotype.Service;


import com.waterelephant.entity.BwScoreCard;

import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwScoreCardService;


@Service
public class BwScoreCardServiceImpl extends BaseService<BwScoreCard, Long> implements BwScoreCardService{

	@Override
	public int saveScoreCard(BwScoreCard bwScoreCard) {
		 return   mapper.insert(bwScoreCard);
		
	}

	

	
}
