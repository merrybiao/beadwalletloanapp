package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgSpecialMonthDetail;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgSpecialMonthDetailService;

@Service
public class BwXgSpecialMonthDetailServiceImpl extends BaseService<BwXgSpecialMonthDetail, Long>
		implements BwXgSpecialMonthDetailService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgSpecialMonthDetailService#findListByAttr(com.waterelephant.entity.BwXgSpecialMonthDetail)
	 */
	@Override
	public List<BwXgSpecialMonthDetail> findListByAttr(BwXgSpecialMonthDetail bwXgSpecialMonthDetail) {
		return mapper.select(bwXgSpecialMonthDetail);
	}

}
