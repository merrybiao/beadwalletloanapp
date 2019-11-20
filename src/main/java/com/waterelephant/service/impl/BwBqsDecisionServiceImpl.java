package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBqsDecision;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBqsDecisionService;

@Service
public class BwBqsDecisionServiceImpl extends BaseService<BwBqsDecision, Long> implements BwBqsDecisionService {

	/**
	 * 
	 * @see com.waterelephant.service.BwBqsDecisionService#findListByAttr(com.waterelephant.entity.BwBqsDecision)
	 */
	@Override
	public List<BwBqsDecision> findListByAttr(BwBqsDecision bwBqsDecision) {
		return mapper.select(bwBqsDecision);
	}

}