package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgSpecialCate;
import com.waterelephant.rongCarrier.service.XgSpecialCateService;
import com.waterelephant.service.BaseNewTabService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:16
 */
@Service
public class XgSpecialCateServiceImpl extends BaseNewTabService<XgSpecialCate, Long> implements XgSpecialCateService {
    @Override
    public boolean save(XgSpecialCate xgSpecialCate) {
        return mapper.insert(xgSpecialCate)>0;
    }

    @Override
    public boolean saveToNewTab(XgSpecialCate xgSpecialCate) {
    	return insertNewTab(xgSpecialCate)>0;
    }

    @Override
    public boolean deleteZ(XgSpecialCate xgSpecialCate) {
        return mapper.delete(xgSpecialCate) > 0;
    }

	@Override
	public boolean deleteToNewTab(XgSpecialCate xgSpecialCate) {
		return deleteNewTab(xgSpecialCate)>0;
	}
}
