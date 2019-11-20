package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgCallLog;
import com.waterelephant.rongCarrier.service.XgCallLogService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:28
 */
@Service
public class XgCallLogServiceImpl extends BaseNewTabService<XgCallLog, Long> implements XgCallLogService {
    @Override
    public boolean save(XgCallLog xgCallLog) {
        return mapper.insert(xgCallLog)>0;
    }
    @Override
    public boolean saveToNewTab(XgCallLog xgCallLog) {
    	return insertNewTab(xgCallLog)>0;
    }

    @Override
    public boolean deleteZ(XgCallLog xgCallLog) {
        return mapper.delete(xgCallLog) > 0;
    }

	@Override
	public boolean deleteToNewTab(XgCallLog xgCallLog) {
		return deleteNewTab(xgCallLog)>0;
	}

}
