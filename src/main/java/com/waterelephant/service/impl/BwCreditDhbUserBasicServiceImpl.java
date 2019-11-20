package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditDhbUserBasic;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditDhbUserBasicService;

@Service
public class BwCreditDhbUserBasicServiceImpl extends BaseService<BwCreditDhbUserBasic, Long>
		implements BwCreditDhbUserBasicService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditDhbUserBasicService#findListByAttr(com.waterelephant.entity.BwCreditDhbUserBasic)
	 */
	@Override
	public List<BwCreditDhbUserBasic> findListByAttr(BwCreditDhbUserBasic bwCreditDhbUserBasic) {
		return mapper.select(bwCreditDhbUserBasic);
	}

}