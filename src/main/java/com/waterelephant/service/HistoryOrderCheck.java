package com.waterelephant.service;

import java.util.List;

import com.waterelephant.dto.HistorOrderDto;
import com.waterelephant.entity.BwRejectRecord;

public interface HistoryOrderCheck {

	//根据手机号查询历史工单记录
	public List<HistorOrderDto> getOrders(String mobile);
	//根据工单号查询历史被拒记录
	public List<BwRejectRecord> getRecords(Long orderId);
}
