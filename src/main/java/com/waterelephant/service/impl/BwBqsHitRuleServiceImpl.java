package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBqsHitRule;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBqsHitRuleService;

@Service
public class BwBqsHitRuleServiceImpl extends BaseService<BwBqsHitRule, Long> implements BwBqsHitRuleService {

	/**
	 * 
	 * @see com.waterelephant.service.BwBqsHitRuleService#findListByAttr(com.waterelephant.entity.BwBqsHitRule)
	 */
	@Override
	public List<BwBqsHitRule> findListByAttr(BwBqsHitRule bwBqsHitRule) {
		return mapper.select(bwBqsHitRule);
	}

}