package com.waterelephant.service;

import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.*;
import com.waterelephant.utils.AppResponseResult;

/**
 * Created by liven on 2017/1/17.
 */
public interface IBwRepaymentService {

	/**
	 * 催单还款更新工单表，还款计划表，逾期记录表状态
	 * 
	 * @param orderId
	 */
	void updateOrderStatus(Long orderId) throws Exception;

	/**
	 * 根据交易金额更新工单、还款计划，并记录流水、支付明细、分批信息等
	 * 
	 * @param repaymentDto orderId:工单ID；tradeNo:交易流水号；terminalType:终端类型；tradeMoney:交易金额；type:1.还款 2.展期；payChannel:支付渠道
	 *            1.宝付 2.连连 3.支付宝 4.微信；tradeTime:交易时间
	 * @return
	 * @throws Exception
	 */
	AppResponseResult updateOrderByTradeMoney(RepaymentDto repaymentDto) throws Exception;

	/**
	 * 工单是否正在处理中
	 * 
	 * @param orderId
	 * @return
	 */
	boolean isProcessing(Long orderId);

	/**
	 * 三方还款代扣
	 *
	 * @param orderId
	 * @return code=000还款申请成功
	 */
    AppResponseResult updateAndPaymentThirdByOrderId(Long orderId);

	/**
	 * 三方还款代扣
	 *
	 * @param orderId
	 * @return code=000还款申请成功
	 */
    AppResponseResult updateAndPaymentThirdByOrderIdNew(Long orderId);

	/**
	 * 分批还款或展期保存拆分还款记录
	 *
	 * @param repaymentDto
	 * @param bwOrder
	 * @param bwRepaymentPlan
	 * @param bwOrderRepaymentBatchDetail 还款必传
	 * @param bwPaymentDetail 展期必传
	 * @param bwPlatformRecord
	 * @param repayPlanMoney
	 * @param realOverdueAmount
	 * @return
	 */
	BwPaymentRecord saveBwPaymentRecordByPlan(RepaymentDto repaymentDto, BwOrder bwOrder, BwRepaymentPlan bwRepaymentPlan,
											  BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail, BwPaymentDetail bwPaymentDetail,
											  BwPlatformRecord bwPlatformRecord, Double repayPlanMoney, Double realOverdueAmount);
}
