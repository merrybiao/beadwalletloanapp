package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwJdUserInfo;

public interface BwJdUserInfoService {

	List<BwJdUserInfo> findListByAttr(BwJdUserInfo bwJdUserInfo);

}