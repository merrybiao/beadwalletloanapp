package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwYxafScore;

import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwYxafScoreService;
@Service
public class BwYxafScoreServiceImpl extends BaseService<BwYxafScore, Long> implements BwYxafScoreService{

	@Override
	public int save(BwYxafScore bwYxafScore) {
		return mapper.insert(bwYxafScore);
	}

	@Override
	public BwYxafScore findByNameAndIdCard(String name, String idCard) {
		BwYxafScore bwYxafScore = new BwYxafScore();
		bwYxafScore.setName(name);
		bwYxafScore.setIdCard(idCard);
		return mapper.selectOne(bwYxafScore);
	}

	@Override
	public int update(BwYxafScore bwYxafScore) {
		return mapper.updateByPrimaryKey(bwYxafScore);
	}

	
}
