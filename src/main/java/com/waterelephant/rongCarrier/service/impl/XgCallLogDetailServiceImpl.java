package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgCallLogDetail;
import com.waterelephant.rongCarrier.service.XgCallLogDetailService;
import com.waterelephant.service.BaseNewTabService;

@Service
public class XgCallLogDetailServiceImpl extends BaseNewTabService<XgCallLogDetail, Long> implements XgCallLogDetailService {

	@Override
	public boolean save(XgCallLogDetail xgCallLogDetail) {
		return mapper.insert(xgCallLogDetail)>0;
	}
	@Override
	public boolean saveToNewTab(XgCallLogDetail xgCallLogDetail) {
		return insertNewTab(xgCallLogDetail)>0;
	}

	@Override
	public boolean deleteZ(XgCallLogDetail xgCallLogDetail) {
		return mapper.delete(xgCallLogDetail) > 0;
	}

	@Override
	public boolean deleteToNewTab(XgCallLogDetail xgCallLogDetail) {
		return deleteNewTab(xgCallLogDetail)>0;
	}


}
