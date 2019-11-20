package com.waterelephant.service;

import java.util.List;

import com.beadwallet.service.rong360.entity.response.SheBaoFlow;
import com.waterelephant.entity.BwInsureRecord;

public interface BwInsureRecordService {

	// 保存到数据库
	public Long save(BwInsureRecord bwInsureRecord);

	// 查询最近三个月的社保缴费记录
	public List<BwInsureRecord> getRecords();

	/**
	 * 融360 - 社保 - 删除社保记录（code0084）
	 * 
	 * @param bwInsureRecord
	 * @return
	 */
	public boolean deleteBwInsureRecord(BwInsureRecord bwInsureRecord);

	/**
	 * 融360 - 社保 - 批量保存（code0084）
	 * 
	 * @param List<SheBaoFlow>
	 * @return
	 */
	public void saveList(List<SheBaoFlow> flowList, long insureInfoId, long orderId);

	/**
	 * (code:s2s)
	 * 
	 * @param bwInsureRecord
	 * @return
	 */
	List<BwInsureRecord> findListByAttr(BwInsureRecord bwInsureRecord);
}
