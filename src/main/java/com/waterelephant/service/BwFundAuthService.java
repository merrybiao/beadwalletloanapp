package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwFundAuth;

public interface BwFundAuthService {

	List<BwFundAuth> findListByAttr(BwFundAuth bwFundAuth);

}