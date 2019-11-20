package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwBqsStrategy;

public interface BwBqsStrategyService {

	List<BwBqsStrategy> findListByAttr(BwBqsStrategy bwBqsStrategy);
}