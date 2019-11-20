package com.waterelephant.service;

import com.waterelephant.entity.BwOfficialAccounts;

public interface IBwOfficialAccountsService {

	/**
	 * 添加公众号与用户关联信息
	 * 
	 * @param borrower
	 * @return
	 */
	Boolean addOfficialAccounts(BwOfficialAccounts bwOfficialAccounts);
	
	/**
	 * 获取公众号与用户关联信息
	 * 条件ID
	 * @param borrower
	 * @return
	 */
	BwOfficialAccounts findBwOfficialAccountsById(Object obj);
	
	/**
	 * 获取公众号与用户关联信息
	 * 条件属性
	 * @param borrower
	 * @return
	 */
	BwOfficialAccounts findBwOfficialAccountsByAttr(BwOfficialAccounts bwOfficialAccounts);
	
	/**
	 * 更新公众号与用户关联信息
	 * 条件属性
	 * @param borrower
	 * @return
	 */
	int updateOfficialAccounts(BwOfficialAccounts bwOfficialAccounts);
	
}
