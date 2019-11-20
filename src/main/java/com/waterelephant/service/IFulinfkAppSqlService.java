package com.waterelephant.service;

import com.waterelephant.entity.BwFullinkReport;

public interface IFulinfkAppSqlService {
	
	public Integer saveFullinkInfo(BwFullinkReport bwScoreCard);
	
	public BwFullinkReport selectScore(String phone, String idCard, String name);

}
