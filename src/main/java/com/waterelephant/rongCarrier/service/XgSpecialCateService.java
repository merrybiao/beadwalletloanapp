package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgSpecialCate;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
public interface XgSpecialCateService {
    public boolean save(XgSpecialCate xgSpecialCate);
    public boolean saveToNewTab(XgSpecialCate xgSpecialCate);
    public boolean deleteZ(XgSpecialCate xgSpecialCate);
	public boolean deleteToNewTab(XgSpecialCate xgSpecialCate);
}
