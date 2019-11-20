package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgAreaAnalysisDetail;
import com.waterelephant.rongCarrier.service.XgAreaAnalysisDetailService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:28
 */
@Service
public class XgAreaAnalysisDetailServiceImpl extends BaseNewTabService<XgAreaAnalysisDetail, Long> implements XgAreaAnalysisDetailService {
    @Override
    public boolean save(XgAreaAnalysisDetail xgAreaAnalysisDetail) {
        return mapper.insert(xgAreaAnalysisDetail)>0;
    }

    @Override
    public boolean saveToNewTab(XgAreaAnalysisDetail xgTripAnalysisDetail) {
    	return insertNewTab(xgTripAnalysisDetail) >0;
    }

    @Override
    public boolean deleteZ(XgAreaAnalysisDetail xgAreaAnalysisDetail) {
        return mapper.delete(xgAreaAnalysisDetail) > 0;
    }

	@Override
	public boolean deleteToNewTab(XgAreaAnalysisDetail xgAreaAnalysisDetail) {
		return deleteNewTab(xgAreaAnalysisDetail)>0;
	}


}
