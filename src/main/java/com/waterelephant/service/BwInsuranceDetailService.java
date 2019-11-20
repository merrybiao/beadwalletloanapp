package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwInsuranceDetail;

public interface BwInsuranceDetailService {

	List<BwInsuranceDetail> findListByAttr(BwInsuranceDetail bwInsuranceDetail);

	BwInsuranceDetail findByAttr(BwInsuranceDetail bwInsuranceDetail);

}