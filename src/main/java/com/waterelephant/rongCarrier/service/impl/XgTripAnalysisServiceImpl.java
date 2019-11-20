package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgTripAnalysisDetail;
import com.waterelephant.rongCarrier.service.XgTripAnalysisDetailService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
@Service
public class XgTripAnalysisServiceImpl extends BaseNewTabService<XgTripAnalysisDetail, Long>
		implements XgTripAnalysisDetailService {

	@Override
	public boolean save(XgTripAnalysisDetail xgTripAnalysisDetail) {
		return mapper.insert(xgTripAnalysisDetail)>0;
	}

	@Override
	public boolean deleteZ(XgTripAnalysisDetail xgTripAnalysisDetail) {
		return mapper.delete(xgTripAnalysisDetail) > 0;
	}

	@Override
	public boolean saveToNewTab(XgTripAnalysisDetail xgTripAnalysisDetail) {
		return insertNewTab(xgTripAnalysisDetail)>0;
	}

	@Override
	public boolean deleteToNewTab(XgTripAnalysisDetail xgTripAnalysisDetail) {
		return insertNewTab(xgTripAnalysisDetail)>0;
	}

}
