package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgSpecialCatePhoneDetail;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgSpecialCatePhoneDetailService;

@Service
public class BwXgSpecialCatePhoneDetailServiceImpl extends BaseService<BwXgSpecialCatePhoneDetail, Long>
		implements BwXgSpecialCatePhoneDetailService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgSpecialCatePhoneDetailService#findListByAttr(com.waterelephant.entity.BwXgSpecialCatePhoneDetail)
	 */
	@Override
	public List<BwXgSpecialCatePhoneDetail> findListByAttr(BwXgSpecialCatePhoneDetail bwXgSpecialCatePhoneDetail) {
		return mapper.select(bwXgSpecialCatePhoneDetail);
	}

}
