package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgTripAnalysis;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgTripAnalysisService;

@Service
public class BwXgTripAnalysisServiceImpl extends BaseService<BwXgTripAnalysis, Long>
		implements BwXgTripAnalysisService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgTripAnalysisService#findListByAttr(com.waterelephant.entity.BwXgTripAnalysis)
	 */
	@Override
	public List<BwXgTripAnalysis> findListByAttr(BwXgTripAnalysis bwXgTripAnalysis) {
		return mapper.select(bwXgTripAnalysis);
	}

}
