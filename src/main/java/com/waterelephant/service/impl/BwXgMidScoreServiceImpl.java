package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgMidScore;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgMidScoreService;

@Service
public class BwXgMidScoreServiceImpl extends BaseService<BwXgMidScore, Long> implements BwXgMidScoreService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgMidScoreService#findByAttr(com.waterelephant.entity.BwXgMidScore)
	 */
	@Override
	public BwXgMidScore findByAttr(BwXgMidScore bwXgMidScore) {

		return mapper.selectOne(bwXgMidScore);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwXgMidScoreService#findListByAttr(com.waterelephant.entity.BwXgMidScore)
	 */
	@Override
	public List<BwXgMidScore> findListByAttr(BwXgMidScore bwXgMidScore) {
		return mapper.select(bwXgMidScore);
	}

}
