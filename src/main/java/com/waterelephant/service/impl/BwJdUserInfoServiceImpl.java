package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwJdUserInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwJdUserInfoService;

@Service
public class BwJdUserInfoServiceImpl extends BaseService<BwJdUserInfo, Long> implements BwJdUserInfoService {

	/**
	 * 
	 * @see com.waterelephant.service.BwJdUserInfoService#findListByAttr(com.waterelephant.entity.BwJdUserInfo)
	 */
	@Override
	public List<BwJdUserInfo> findListByAttr(BwJdUserInfo bwJdUserInfo) {
		return mapper.select(bwJdUserInfo);
	}

}