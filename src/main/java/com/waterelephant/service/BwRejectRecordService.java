package com.waterelephant.service;

import java.util.Date;
import java.util.List;

import com.waterelephant.entity.BwReconsider;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.exception.BusException;


/**
 * 认证被拒记录
 * 
 * @author huwei
 *
 */
public interface BwRejectRecordService {
	
	BwRejectRecord findBwRejectRecordByAtta(BwRejectRecord record);
	
	int save(BwRejectRecord record);
	
	int saveBwRejectRecord(BwRejectRecord record,Long borrow_id);
	
	/**
	 * 根据手机号查询黑名单用户
	 */
	int findRejectRecordByPhone(String phone);



	List<BwRejectRecord> queryRecord(String valueOf);

	void add(BwRejectRecord bwRejectRecord);

	int insert(BwRejectRecord bwRejectRecord);
	List<BwReconsider> reconsider(String orderId) throws BusException;
	
	
	Date findCreateTimeByOrderId(Long orderId);
	
	BwRejectRecord findBwRejectRecordByBid(Long borrowerId);

	/**
	 * 根据工单ID查询最新被拒记录
	 * 
	 * @param orderId
	 * @return
	 */
	BwRejectRecord findBwRejectRecordByOrderId(Long orderId);
}
