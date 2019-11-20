package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgAreaAnalysis;
import com.waterelephant.rongCarrier.service.XgAreaAnalysisService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:28
 */
@Service
public class XgAreaAnalysisServiceImpl extends BaseNewTabService<XgAreaAnalysis, Long> implements XgAreaAnalysisService {
    @Override
    public boolean save(XgAreaAnalysis xgAreaAnalysis) {
        return mapper.insert(xgAreaAnalysis)>0;
    }
    @Override
    public boolean saveToNewTab(XgAreaAnalysis xgAreaAnalysis) {
    	return insertNewTab(xgAreaAnalysis)>0;
    }

    @Override
    public boolean deleteZ(XgAreaAnalysis xgAreaAnalysis) {
        return mapper.delete(xgAreaAnalysis) > 0;
    }

	@Override
	public boolean deleteToNewTab(XgAreaAnalysis xgAreaAnalysis) {
		return deleteNewTab(xgAreaAnalysis)>0;
	}


}
