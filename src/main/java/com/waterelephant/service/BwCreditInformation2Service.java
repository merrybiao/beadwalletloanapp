package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwCreditInformation;

public interface BwCreditInformation2Service {

	List<BwCreditInformation> findListByAttr(BwCreditInformation bwCreditInformation);

}