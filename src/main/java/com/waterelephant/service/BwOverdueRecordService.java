package com.waterelephant.service;

import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOverdueRecord;

public interface BwOverdueRecordService {

	public int updateBwOverdueRecord(BwOverdueRecord bwOverdueRecord);

	public BwOverdueRecord queryBwOverdueRecord(BwOverdueRecord bwOverdueRecord);

	public BwOverdueRecord findBwOverdueRecordByAttr(BwOverdueRecord bo);

	/**
	 * 还款回调，支付成功则清零逾期记录金额
	 * 
	 * @param bwOrder
	 */
	void updateBwOverdueRecordMoney(BwOrder bwOrder);

	/**
	 * 还款回调，支付成功则清零逾期记录金额
	 * 
	 * @param orderId
	 * @param repayId
	 */
	void updateBwOverdueRecordMoney(Long orderId, Long repayId);

	/**
	 * 根据还款计划Id查询逾期记录
	 * 
	 * @param repayId
	 * @return
	 */
	BwOverdueRecord queryBwOverdueByRepayId(Long repayId);

	void saveBwOverdueRecord(BwOverdueRecord record);

	void deleteBwOverdueRecord(Long repayId);
}
