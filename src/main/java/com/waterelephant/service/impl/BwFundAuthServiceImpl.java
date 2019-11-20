package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwFundAuth;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwFundAuthService;

@Service
public class BwFundAuthServiceImpl extends BaseService<BwFundAuth, Long> implements BwFundAuthService {

	/**
	 * 
	 * @see com.waterelephant.service.BwFundAuthService#findListByAttr(com.waterelephant.entity.BwFundAuth)
	 */
	@Override
	public List<BwFundAuth> findListByAttr(BwFundAuth bwFundAuth) {
		return mapper.select(bwFundAuth);
	}

}