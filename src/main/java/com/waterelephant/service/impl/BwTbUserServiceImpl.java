package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwTbUser;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwTbUserService;

@Service
public class BwTbUserServiceImpl extends BaseService<BwTbUser, Long> implements BwTbUserService {

	/**
	 * 
	 * @see com.waterelephant.service.BwTbUserService#findListByAttr(com.waterelephant.entity.BwTbUser)
	 */
	@Override
	public List<BwTbUser> findListByAttr(BwTbUser bwTbUser) {
		return mapper.select(bwTbUser);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwTbUserService#findByAttr(com.waterelephant.entity.BwTbUser)
	 */
	@Override
	public BwTbUser findByAttr(BwTbUser bwTbUser) {
		return mapper.selectOne(bwTbUser);
	}

}