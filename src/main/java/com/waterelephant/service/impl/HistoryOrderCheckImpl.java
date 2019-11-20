package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.dto.HistorOrderDto;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.HistoryOrderCheck;

@Service
public class HistoryOrderCheckImpl extends BaseService<HistorOrderDto, Long> implements HistoryOrderCheck {

	@Override
	public List<HistorOrderDto> getOrders(String mobile) {
		String sql = "SELECT o.id,o.status_id,b.`name` " + "FROM bw_order o " + "LEFT JOIN bw_borrower b "
				+ "ON o.borrower_id = b.id " + "WHERE b.phone = #{phone} and product_type = 1 ";
		return sqlMapper.selectList(sql, mobile, HistorOrderDto.class);
	}

	@Override
	public List<BwRejectRecord> getRecords(Long orderId) {
		String sql = "SELECT r.* FROM bw_reject_record r " + "LEFT JOIN bw_order o " + "ON r.order_id = o.id "
				+ "WHERE o.id = #{orderId}";
		return sqlMapper.selectList(sql, orderId, BwRejectRecord.class);
	}

}
