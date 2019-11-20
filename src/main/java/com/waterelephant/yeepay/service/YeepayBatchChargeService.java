package com.waterelephant.yeepay.service;


import com.waterelephant.yeepay.entity.YeepayBatchCharge;

/**
 * Service接口
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-22 10:21:09
 */
public interface YeepayBatchChargeService {

	
	/**
	 * 保存
	 * @param yeepayBatchCharge
	 */
	boolean saveYeepayBatchCharge(YeepayBatchCharge yeepayBatchCharge);
	
	/**
	 * 修改？这尼玛是修改？
	 * @param yeepayBatchCharge
	 */
	boolean deleteYeepayBatchCharge(YeepayBatchCharge yeepayBatchCharge);

	/**
	 * 根据商户批次号查找到批次信息
	 * @param merchatntBatchNo
	 * @return
	 */
	YeepayBatchCharge findByMerchantBatchNo(String merchatntBatchNo);
	
	/**
	 * 更新批次状态
	 * @param yeepayBatchCharge
	 * @return
	 */
	boolean updateYeepayBatchCharge(YeepayBatchCharge yeepayBatchCharge);
	
}
