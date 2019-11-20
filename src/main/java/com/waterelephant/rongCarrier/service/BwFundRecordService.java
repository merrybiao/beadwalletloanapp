package com.waterelephant.rongCarrier.service;

import java.util.List;

import com.beadwallet.service.rong360.entity.response.FundFlow;
import com.waterelephant.rongCarrier.entity.BwFundInfo;
import com.waterelephant.rongCarrier.entity.BwFundRecord;

/**
 * 融360 - 公积金 - 缴费记录（code0085） 
 * 
 * Module: 
 * 
 * BwFundRecordService.java 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface BwFundRecordService {

	/**
	 * 融360 - 公积金- 删除缴费记录
	 * 
	 * @param bwFundRecord
	 * @return
	 */
	public boolean deleteBwFundRecord(BwFundRecord bwFundRecord) throws Exception;

	/**
	 * 融360 - 公积金 - 批量保存缴费记录
	 * 
	 * @param flowList
	 * @param fundInfoId
	 * @param orderId
	 */
	public void saveList(List<FundFlow> flowList, BwFundInfo bwFundInfo, long orderId) throws Exception;
}
