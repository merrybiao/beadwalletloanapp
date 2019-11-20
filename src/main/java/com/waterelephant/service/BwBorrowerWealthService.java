package com.waterelephant.service;

import com.waterelephant.entity.BwBorrowerWealth;

/**
 * 财富值
 * 
 * Module:
 * 
 * BwBorrowerWealthService.java
 * 
 * @author 胡林浩
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface BwBorrowerWealthService {

	/** 查询用户等级 */
	Integer getUserRankByBorrowerId(String borrowerId);

	/**
	 * 统计财富值
	 */
	void saveCountWealth(Integer borrowerId);

	/**
	 * 根据借款人Id查询财富值记录
	 *
	 * @param borrowerId
	 * @return
	 */
	BwBorrowerWealth selectBwBorrowerWealth(Integer borrowerId);

	/**
	 * 保存或更新借款人财富值记录
	 *
	 * @param entity
	 */
	void saveBorrowerWealth(BwBorrowerWealth entity);

	/**
	 * 查询本金加利息产生的财富值（归还本金的金额 每1元 = 0.1 财富值 ，借贷的利息 每1元 = 0.5 财富值）
	 *
	 * @return
	 */
	BwBorrowerWealth selectWealthForPrincipal(Integer BorrowerId);

	/**
	 * 查询续贷产生的财富值（归还利息+展期的金额 每1元 = 0.5 财富值）
	 *
	 * @return
	 */
	BwBorrowerWealth selectWealthForXudai(Integer BorrowerId);

	/**
	 * 查询逾期天数（逾期1-10天：每天减2财富值 逾期11-20天：每天减5财富值 逾期21-30天：每天减8财富值 逾期31+：每天减10财富值 ）
	 */
	BwBorrowerWealth selectWealthForOverdue(Integer BorrowerId);

	/**
	 * 查询邀请好友并成功借贷的利息所产生的财富值，每1元 = 0.1财富值
	 */
	BwBorrowerWealth selectWealthForInvite(Integer BorrowerId);

}
