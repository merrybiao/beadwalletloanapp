package com.waterelephant.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCreditCloudExternal;
import com.waterelephant.mapper.BwCreditCloudExternalMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCreditCloudExternalService;

@Service
public class BwCreditCloudExternalServiceImpl extends BaseService<BwCreditCloudExternal, Long>
		implements BwCreditCloudExternalService {

	@Resource
	private BwCreditCloudExternalMapper bwCreditCloudExternalMapper;

	@Override
	public void saveBwCreditCloudExternal(BwCreditCloudExternal bwCreditCloudExternal) {
		mapper.insert(bwCreditCloudExternal);
	}

	@Override
	public void updateBwCreditCloudExternal(BwCreditCloudExternal bwCreditCloudExternal) {
		mapper.updateByPrimaryKey(bwCreditCloudExternal);
	}

	@Override
	public BwCreditCloudExternal findBwCreditCloudExternalByCreditId(Long creditId, int source) {
		return bwCreditCloudExternalMapper.findBwCreditCloudExternalByCreditId(creditId, source);
	}

	@Override
	public BwCreditCloudExternal findBwCreditCloudExternalByExternalNo(String externalNo, int source) {
		return bwCreditCloudExternalMapper.findBwCreditCloudExternalByExternalNo(externalNo, source);

	}

}
