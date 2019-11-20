package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgAreaAnalysis;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 16:45
 */
public interface XgAreaAnalysisService {

    public boolean save(XgAreaAnalysis xgAreaAnalysis);
    public boolean deleteZ(XgAreaAnalysis xgAreaAnalysis);
	public boolean deleteToNewTab(XgAreaAnalysis xgAreaAnalysis);
	public boolean saveToNewTab(XgAreaAnalysis xgAreaAnalysis);

}
