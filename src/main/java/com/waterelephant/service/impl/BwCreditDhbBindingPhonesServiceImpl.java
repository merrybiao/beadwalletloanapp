package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditDhbBindingPhones;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditDhbBindingPhonesService;

@Service
public class BwCreditDhbBindingPhonesServiceImpl extends BaseService<BwCreditDhbBindingPhones, Long>
		implements BwCreditDhbBindingPhonesService {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditDhbBindingPhonesService#findListByAttr(com.waterelephant.entity.BwCreditDhbBindingPhones)
	 */
	@Override
	public List<BwCreditDhbBindingPhones> findListByAttr(BwCreditDhbBindingPhones bwCreditDhbBindingPhones) {
		return mapper.select(bwCreditDhbBindingPhones);
	}

}