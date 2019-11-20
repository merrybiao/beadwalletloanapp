package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditDhbRiskSocialNetwork;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditDhbRiskSocialNetworkService;

@Service
public class BwCreditDhbRiskSocialNetworkServiceImpl extends BaseService<BwCreditDhbRiskSocialNetwork, Long>
		implements BwCreditDhbRiskSocialNetworkService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditDhbRiskSocialNetworkService#findListByAttr(com.waterelephant.entity.BwCreditDhbRiskSocialNetwork)
	 */
	@Override
	public List<BwCreditDhbRiskSocialNetwork> findListByAttr(
			BwCreditDhbRiskSocialNetwork bwCreditDhbRiskSocialNetwork) {
		return mapper.select(bwCreditDhbRiskSocialNetwork);
	}

}