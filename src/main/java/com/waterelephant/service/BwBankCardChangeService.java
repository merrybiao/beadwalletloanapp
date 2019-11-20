package com.waterelephant.service;

import com.waterelephant.entity.BwBankCardChange;
import com.waterelephant.entity.BwBorrower;

/**
 * Module:银行卡重绑记录表service
 * 
 * BwBankCardChangeService.java
 * 
 * @author wangkun
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface BwBankCardChangeService {

	/**
	 * 通过借款人id查询当月是否有修改记录
	 * 
	 * @param parseLong
	 * @return
	 */
	int findBwBankCardChangeByBorrowid(Long borrower_id);

	/**
	 * 修改借款人个人信息，修改银行卡提现卡，添加银行卡绑定记录
	 * 
	 * @param bw
	 * @param bankCode
	 * @param bankName
	 * @param cardNo
	 */
	void saveOrUpdBorrowerAndBankCardAndBankCardChange(BwBorrower bw, String bankCode, String bankName, String cardNo);

	Integer insertByAtt(BwBankCardChange bwBankCardChange);

}
