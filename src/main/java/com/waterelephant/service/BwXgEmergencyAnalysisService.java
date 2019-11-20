package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgEmergencyAnalysis;

public interface BwXgEmergencyAnalysisService {

	List<BwXgEmergencyAnalysis> findListByAttr(BwXgEmergencyAnalysis bwXgEmergencyAnalysis);
}
