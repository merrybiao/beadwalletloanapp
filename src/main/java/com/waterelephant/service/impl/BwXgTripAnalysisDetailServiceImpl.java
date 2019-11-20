package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgTripAnalysisDetail;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgTripAnalysisDetailService;

@Service
public class BwXgTripAnalysisDetailServiceImpl extends BaseService<BwXgTripAnalysisDetail, Long>
		implements BwXgTripAnalysisDetailService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgTripAnalysisDetailService#findListByAttr(com.waterelephant.entity.BwXgTripAnalysisDetail)
	 */
	@Override
	public List<BwXgTripAnalysisDetail> findListByAttr(BwXgTripAnalysisDetail bwXgTripAnalysisDetail) {
		return mapper.select(bwXgTripAnalysisDetail);
	}

}
