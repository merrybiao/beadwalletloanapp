package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgSpecialCate;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgSpecialCateService;

@Service
public class BwXgSpecialCateServiceImpl extends BaseService<BwXgSpecialCate, Long> implements BwXgSpecialCateService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgSpecialCateService#findListByAttr(com.waterelephant.entity.BwXgSpecialCate)
	 */
	@Override
	public List<BwXgSpecialCate> findListByAttr(BwXgSpecialCate bwXgSpecialCate) {
		return mapper.select(bwXgSpecialCate);
	}

}
