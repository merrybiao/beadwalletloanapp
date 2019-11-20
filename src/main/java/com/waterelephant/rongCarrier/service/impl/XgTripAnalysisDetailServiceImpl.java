package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgTripAnalysis;
import com.waterelephant.rongCarrier.service.XgTripAnalysisService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
@Service
public class XgTripAnalysisDetailServiceImpl extends BaseNewTabService<XgTripAnalysis, Long> implements XgTripAnalysisService {

	@Override
	public boolean save(XgTripAnalysis xgTripAnalysis) {
		return mapper.insert(xgTripAnalysis)>0;
	}

	@Override
	public boolean deleteZ(XgTripAnalysis xgTripAnalysis) {
		return mapper.delete(xgTripAnalysis) > 0;
	}

	@Override
	public boolean saveToNewTab(XgTripAnalysis xgtripAnalysis) {
		return insertNewTab(xgtripAnalysis)>0;
	}

	@Override
	public boolean deleteToNewTab(XgTripAnalysis xgTripAnalysis) {
		return deleteNewTab(xgTripAnalysis)>0;
	}

}
