package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgTripAnalysisDetail;

public interface BwXgTripAnalysisDetailService {
	List<BwXgTripAnalysisDetail> findListByAttr(BwXgTripAnalysisDetail bwXgTripAnalysisDetail);
}
