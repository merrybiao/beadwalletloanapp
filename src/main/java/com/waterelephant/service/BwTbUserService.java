package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwTbUser;

public interface BwTbUserService {

	List<BwTbUser> findListByAttr(BwTbUser bwTbUser);

	BwTbUser findByAttr(BwTbUser bwTbUser);

}