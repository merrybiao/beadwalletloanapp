package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgUserSpecialCallInfo;
import com.waterelephant.rongCarrier.service.XgUserSpecialCallInfoService;
import com.waterelephant.service.BaseNewTabService;

/**
 * asdf
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/18 11:11
 */
@Service
public class XgUserSpecialCallInfoServiceImpl extends BaseNewTabService<XgUserSpecialCallInfo, Long>
		implements XgUserSpecialCallInfoService {
    @Override
    public boolean save(XgUserSpecialCallInfo xgUserSpecialCallInfo) {
        return mapper.insert(xgUserSpecialCallInfo)>0;
    }

    @Override
    public boolean deleteZ(XgUserSpecialCallInfo xgUserSpecialCallInfo) {
        return  mapper.delete(xgUserSpecialCallInfo) > 0;
    }

	@Override
	public boolean saveToNewTab(XgUserSpecialCallInfo xgUserSpecialCallInfo) {
		return insertNewTab(xgUserSpecialCallInfo)>0;
	}

	@Override
	public boolean deleteToNewTab(XgUserSpecialCallInfo xgUserSpecialCallInfo) {
		return deleteNewTab(xgUserSpecialCallInfo)>0;
	}
}
