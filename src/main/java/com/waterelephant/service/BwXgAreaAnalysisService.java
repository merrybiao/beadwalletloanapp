package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgAreaAnalysis;

public interface BwXgAreaAnalysisService {

	List<BwXgAreaAnalysis> findListByAttr(BwXgAreaAnalysis bwXgAreaAnalysis);
}
