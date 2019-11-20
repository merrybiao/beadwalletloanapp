package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgCallLog;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 16:45
 */
public interface XgCallLogService {

    public boolean save(XgCallLog xgCallLog);
    public boolean saveToNewTab(XgCallLog xgCallLog);


    public boolean deleteZ(XgCallLog xgCallLog);
    public boolean deleteToNewTab(XgCallLog xgCallLog);





}
