package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwZmxyScore;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwZmxyScoreService;

@Service
public class BwZmxyScoreServiceImpl extends BaseService<BwZmxyScore, Long> implements BwZmxyScoreService{

	@Override
	public int save(BwZmxyScore bwZmxyScore) {
		return mapper.insert(bwZmxyScore);
	}

	@Override
	public int update(BwZmxyScore bwZmxyScore) {
		return mapper.updateByPrimaryKey(bwZmxyScore);
	}
	
	@Override
	public BwZmxyScore findByAttr(BwZmxyScore bwZmxyScore) {
		return mapper.selectOne(bwZmxyScore);
	}


}
