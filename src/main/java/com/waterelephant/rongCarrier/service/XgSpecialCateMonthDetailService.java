package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgSpecialCateMonthDetail;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
public interface XgSpecialCateMonthDetailService {
    public boolean save(XgSpecialCateMonthDetail xgSpecialCateMonthDetail);
    public boolean saveToNewTab(XgSpecialCateMonthDetail xgSpecialCateMonthDetail);
    public boolean deleteZ(XgSpecialCateMonthDetail xgSpecialCateMonthDetail);
	public boolean deleteToNewTab(XgSpecialCateMonthDetail xgSpecialCateMonthDetail);
}
