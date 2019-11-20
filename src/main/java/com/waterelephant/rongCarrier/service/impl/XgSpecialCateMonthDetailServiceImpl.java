package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgSpecialCateMonthDetail;
import com.waterelephant.rongCarrier.service.XgSpecialCateMonthDetailService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
@Service
public class XgSpecialCateMonthDetailServiceImpl extends BaseNewTabService<XgSpecialCateMonthDetail, Long> implements XgSpecialCateMonthDetailService {
    @Override
    public boolean save(XgSpecialCateMonthDetail xgSpecialCateMonthDetail) {
        return mapper.insert(xgSpecialCateMonthDetail)>0;
    }

    @Override
    public boolean deleteZ(XgSpecialCateMonthDetail xgSpecialCateMonthDetail) {
        return mapper.delete(xgSpecialCateMonthDetail) > 0;
    }

	@Override
	public boolean saveToNewTab(XgSpecialCateMonthDetail xgSpecialCateMonthDetail) {
		return insertNewTab(xgSpecialCateMonthDetail)>0;
	}

	@Override
	public boolean deleteToNewTab(XgSpecialCateMonthDetail xgSpecialCateMonthDetail) {
		return deleteNewTab(xgSpecialCateMonthDetail)>0;
	}
}
