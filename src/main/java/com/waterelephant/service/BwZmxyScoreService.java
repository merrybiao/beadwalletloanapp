package com.waterelephant.service;

import com.waterelephant.entity.BwZmxyScore;

public interface BwZmxyScoreService {

	//保存
	int save(BwZmxyScore bwZmxyScore);
	
	//更新
	int update(BwZmxyScore bwZmxyScore);
	
	//查询
	BwZmxyScore findByAttr(BwZmxyScore bwZmxyScore);
}
