package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBqsStrategy;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBqsStrategyService;

@Service
public class BwBqsStrategyServiceImpl extends BaseService<BwBqsStrategy, Long> implements BwBqsStrategyService {

	/**
	 * 
	 * @see com.waterelephant.service.BwBqsStrategyService#findListByAttr(com.waterelephant.entity.BwBqsStrategy)
	 */
	@Override
	public List<BwBqsStrategy> findListByAttr(BwBqsStrategy bwBqsStrategy) {
		return mapper.select(bwBqsStrategy);
	}

}