package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgCallLogDetail;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 16:45
 */
public interface XgCallLogDetailService {

	public boolean save(XgCallLogDetail xgCallLogDetail);

	public boolean deleteZ(XgCallLogDetail xgCallLogDetail);

	public boolean deleteToNewTab(XgCallLogDetail xgCallLogDetail);

	public boolean saveToNewTab(XgCallLogDetail xgCallLogDetail);

}
