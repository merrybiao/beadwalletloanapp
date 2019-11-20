package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgTripAnalysis;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 16:45
 */
public interface XgTripAnalysisService {

	public boolean save(XgTripAnalysis xgTripAnalysis);
	public boolean saveToNewTab(XgTripAnalysis xgtripAnalysis);

	public boolean deleteZ(XgTripAnalysis xgTripAnalysis);
	public boolean deleteToNewTab(XgTripAnalysis xgTripAnalysis);


}
