package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditInformation;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditInformation2Service;

@Service
public class BwCreditInformation2ServiceImpl extends BaseService<BwCreditInformation, Long>
		implements BwCreditInformation2Service {

	/**
	 * 
	 * @see com.waterelephant.service.BwCreditInformation2Service#findListByAttr(com.waterelephant.entity.BwCreditInformation)
	 */
	@Override
	public List<BwCreditInformation> findListByAttr(BwCreditInformation bwCreditInformation) {
		return mapper.select(bwCreditInformation);
	}

}