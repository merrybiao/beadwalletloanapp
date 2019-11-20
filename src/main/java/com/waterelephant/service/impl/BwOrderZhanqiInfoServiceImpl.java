package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrderZhanqiInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwOrderZhanqiInfoService;

@Service
public class BwOrderZhanqiInfoServiceImpl extends BaseService<BwOrderZhanqiInfo, Long> implements IBwOrderZhanqiInfoService {

	@Override
	public void saveOrderZhanqiInfo(BwOrderZhanqiInfo bwOrderZhanqiInfo) {
		mapper.insertSelective(bwOrderZhanqiInfo);
	}

}
