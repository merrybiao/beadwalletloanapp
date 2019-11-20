package com.waterelephant.service;

import com.waterelephant.entity.BwYxafScore;

public interface BwYxafScoreService {

	//保存
	int save(BwYxafScore bwYxafScore);
	
	//根据姓名和身份证号查询
	BwYxafScore findByNameAndIdCard(String name,String idCard);
	
	//更新
	int update(BwYxafScore bwYxafScore);
}
