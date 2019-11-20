package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgSpecialCatePhoneDetail;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
public interface XgSpecialCatePhoneDetailService {
    public boolean save(XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail);
    public boolean saveToNewTab(XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail);
    public boolean deleteZ(XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail);
	public boolean deleteToNewTab(XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail);
}
