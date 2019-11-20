package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwInsuranceDetail;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwInsuranceDetailService;

@Service
public class BwInsuranceDetailServiceImpl extends BaseService<BwInsuranceDetail, Long>
		implements BwInsuranceDetailService {

	/**
	 * 
	 * @see com.waterelephant.service.BwInsuranceDetailService#findListByAttr(com.waterelephant.entity.BwInsuranceDetail)
	 */
	@Override
	public List<BwInsuranceDetail> findListByAttr(BwInsuranceDetail bwInsuranceDetail) {
		return mapper.select(bwInsuranceDetail);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwInsuranceDetailService#findByAttr(com.waterelephant.entity.BwInsuranceDetail)
	 */
	@Override
	public BwInsuranceDetail findByAttr(BwInsuranceDetail bwInsuranceDetail) {
		return mapper.selectOne(bwInsuranceDetail);
	}

}