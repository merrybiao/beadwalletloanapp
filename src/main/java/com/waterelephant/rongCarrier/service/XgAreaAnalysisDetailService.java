package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgAreaAnalysisDetail;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 16:45
 */
public interface XgAreaAnalysisDetailService {

    public boolean save(XgAreaAnalysisDetail xgAreaAnalysisDetail);
    public boolean deleteZ(XgAreaAnalysisDetail xgAreaAnalysisDetail);
	public boolean deleteToNewTab(XgAreaAnalysisDetail xgAreaAnalysisDetail);
	public boolean saveToNewTab(XgAreaAnalysisDetail xgTripAnalysisDetail);

}
