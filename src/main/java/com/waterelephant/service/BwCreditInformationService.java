package com.waterelephant.service;

import com.waterelephant.entity.BwCreditImformation;

public interface BwCreditInformationService{

	//添加
	int save(BwCreditImformation bwCreditImformation);
	
	//修改
	int update(BwCreditImformation bwCreditImformation);
	
	//根据姓名和身份证号查询
	BwCreditImformation findByNameAndIdCard(String name,String idCard);
	
	//根据姓名和身份证号查询
	BwCreditImformation findByIdCard(String idCard);
	
	
}
