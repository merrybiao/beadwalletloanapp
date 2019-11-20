package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgEmergencyAnalysis;
import com.waterelephant.rongCarrier.service.XgEmergencyAnalysisService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:28
 */
@Service
public class XgEmergencyAnalysisServiceImpl extends BaseNewTabService<XgEmergencyAnalysis, Long> implements XgEmergencyAnalysisService{
    @Override
    public boolean save(XgEmergencyAnalysis xgEmergencyAnalysis) {
        return mapper.insert(xgEmergencyAnalysis)>0;
    }

    @Override
    public boolean saveToNewTab(XgEmergencyAnalysis xgEmergencyAnalysis) {
    	return insertNewTab(xgEmergencyAnalysis)>0;
    }

    @Override
    public boolean deleteZ(XgEmergencyAnalysis xgEmergencyAnalysis) {
        return mapper.delete(xgEmergencyAnalysis) > 0;
    }

	@Override
	public boolean deleteToNewTab(XgEmergencyAnalysis xgEmergencyAnalysis) {
		return deleteNewTab(xgEmergencyAnalysis)>0;
	}

}
