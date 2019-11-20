package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgSpecialCatePhoneDetail;
import com.waterelephant.rongCarrier.service.XgSpecialCatePhoneDetailService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
@Service
public class XgSpecialCatePhoneDetailServiceImpl extends BaseNewTabService<XgSpecialCatePhoneDetail, Long> implements XgSpecialCatePhoneDetailService {
    @Override
    public boolean save(XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail) {
        return mapper.insert(xgSpecialCatePhoneDetail)>0;
    }

    @Override
    public boolean deleteZ(XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail) {
        return mapper.delete(xgSpecialCatePhoneDetail) > 0;
    }

	@Override
	public boolean saveToNewTab(XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail) {
		return insertNewTab(xgSpecialCatePhoneDetail)>0;
	}

	@Override
	public boolean deleteToNewTab(XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail) {
		return deleteNewTab(xgSpecialCatePhoneDetail)>0;
	}
}
