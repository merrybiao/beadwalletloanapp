package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgTripAnalysisDetail;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 16:45
 */
public interface XgTripAnalysisDetailService {

	public boolean save(XgTripAnalysisDetail xgTripAnalysisDetail);
	public boolean saveToNewTab(XgTripAnalysisDetail xgTripAnalysisDetail);

	public boolean deleteZ(XgTripAnalysisDetail xgTripAnalysisDetail);
	public boolean deleteToNewTab(XgTripAnalysisDetail xgTripAnalysisDetail);



}
