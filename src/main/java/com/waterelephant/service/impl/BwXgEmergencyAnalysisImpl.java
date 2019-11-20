package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgEmergencyAnalysis;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgEmergencyAnalysisService;

@Service
public class BwXgEmergencyAnalysisImpl extends BaseService<BwXgEmergencyAnalysis, Long>
		implements BwXgEmergencyAnalysisService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgEmergencyAnalysisService#findListByAttr(com.waterelephant.entity.BwXgEmergencyAnalysis)
	 */
	@Override
	public List<BwXgEmergencyAnalysis> findListByAttr(BwXgEmergencyAnalysis bwXgEmergencyAnalysis) {
		return mapper.select(bwXgEmergencyAnalysis);
	}

}
