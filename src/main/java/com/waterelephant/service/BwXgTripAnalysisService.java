package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgTripAnalysis;

public interface BwXgTripAnalysisService {
	List<BwXgTripAnalysis> findListByAttr(BwXgTripAnalysis bwXgTripAnalysis);
}
