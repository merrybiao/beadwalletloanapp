package com.waterelephant.service;

import com.waterelephant.entity.BwBorrowerCredit;

/**
 * 授信单
 * @author dinglinhao
 *
 */
public interface BwBorrowerCreditService {
	/**
	 * 根据Id查询授信单信息
	 * @param creditId
	 * @return
	 */
	BwBorrowerCredit queryCreditOrderById(Long creditId);
	/**
	 * 根据借款人ID查询授信单信息
	 * @param borrowerId
	 * @return
	 */
	BwBorrowerCredit queryCreditOrderByBorrowerId(Long borrowerId);
	
	BwBorrowerCredit queryCreditOrderByCreditNo(String creditNo);
}
