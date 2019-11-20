package com.waterelephant.service;

import com.waterelephant.entity.BwZmxyIvs;

public interface BwZmxyIvsService {

	//保存
	int save(BwZmxyIvs bwZmxyIvs);
	
	//根据姓名和身份证号查询
	BwZmxyIvs findByNameAndIdCard(String name,String idCard);
	
	//更新
	int update(BwZmxyIvs bwZmxyIvs);
}
