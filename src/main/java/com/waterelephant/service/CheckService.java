package com.waterelephant.service;

import com.waterelephant.dto.CheckInfoDto;
import com.waterelephant.entity.CheckResult;

public interface CheckService {

	/**
	 * 身份认证
	 */
	public CheckResult identity(String idNo,String name,String phone,String cardNo);
	/**
	 * 黑名单
	 */
	public CheckResult blackList(String idNo,String name);
	/**
	 * 社保
	 */
	public CheckResult inSure(CheckInfoDto cInfoDto);
	/**
	 * 历史工单
	 */
	public CheckResult historyOrder(CheckInfoDto cInfoDto);
	
	
	/**
	 * 认证总流程
	 */
	public boolean check(Long borrowerId, Long orderId, boolean includeAddressBook) throws Exception;
}
