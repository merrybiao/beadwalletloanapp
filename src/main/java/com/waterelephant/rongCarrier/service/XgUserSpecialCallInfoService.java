package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgUserSpecialCallInfo;

/**
 * sdaf
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/18 11:10
 */
public interface XgUserSpecialCallInfoService {
    public boolean save(XgUserSpecialCallInfo xgUserSpecialCallInfo);
    public boolean saveToNewTab(XgUserSpecialCallInfo xgUserSpecialCallInfo);

    public boolean deleteZ(XgUserSpecialCallInfo xgUserSpecialCallInfo);
    public boolean deleteToNewTab(XgUserSpecialCallInfo xgUserSpecialCallInfo);


}
