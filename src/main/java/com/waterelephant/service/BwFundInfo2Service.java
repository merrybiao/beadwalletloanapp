package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwFundInfo;

public interface BwFundInfo2Service {

	List<BwFundInfo> findListByAttr(BwFundInfo bwFundInfo);

	BwFundInfo findByAttr(BwFundInfo bwFundInfo);

}