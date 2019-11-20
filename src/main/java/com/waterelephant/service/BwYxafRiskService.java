package com.waterelephant.service;


import com.waterelephant.entity.BwYxafRisk;

public interface BwYxafRiskService {

	//保存
	int save(BwYxafRisk bwYxafRisk);
	
	//根据姓名和身份证号查询
	BwYxafRisk findByNameAndIdCard(String name,String idCard);
	
	//更新
	int update(BwYxafRisk bwYxafRisk);
}
