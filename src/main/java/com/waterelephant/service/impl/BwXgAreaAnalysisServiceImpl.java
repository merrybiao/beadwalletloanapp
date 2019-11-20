package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgAreaAnalysis;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgAreaAnalysisService;

@Service
public class BwXgAreaAnalysisServiceImpl extends BaseService<BwXgAreaAnalysis, Long>
		implements BwXgAreaAnalysisService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgAreaAnalysisService#findListByAttr(com.waterelephant.entity.BwXgAreaAnalysis)
	 */
	@Override
	public List<BwXgAreaAnalysis> findListByAttr(BwXgAreaAnalysis bwXgAreaAnalysis) {
		return mapper.select(bwXgAreaAnalysis);
	}

}
