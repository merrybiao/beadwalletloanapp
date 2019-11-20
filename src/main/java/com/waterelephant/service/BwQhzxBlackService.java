package com.waterelephant.service;

import com.waterelephant.entity.BwQhzxBlack;

public interface BwQhzxBlackService {

	//保存
	int save(BwQhzxBlack bwQhzxBlack);
	
	//根据姓名和身份证号查询
	BwQhzxBlack findByNameAndIdCard(String name,String idCard);
	
	//更新
	int update(BwQhzxBlack bwQhzxBlack);
}
