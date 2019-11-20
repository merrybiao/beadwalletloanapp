package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgEmergencyAnalysis;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
public interface XgEmergencyAnalysisService {
    public boolean save(XgEmergencyAnalysis xgEmergencyAnalysis);
    public boolean saveToNewTab(XgEmergencyAnalysis xgEmergencyAnalysis);
    public boolean deleteZ(XgEmergencyAnalysis xgEmergencyAnalysis);
	public boolean deleteToNewTab(XgEmergencyAnalysis xgEmergencyAnalysis);
}
