package com.waterelephant.service;

import java.util.List;

import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entity.response.RongAddInfo;
import com.beadwallet.service.entity.response.RongOrder;
import com.beadwallet.service.entity.response.RongOrderDetail;
import com.waterelephant.dto.RongOrderDto;

public interface BwRongOrderService {

	//引流订单列表
	Response<List<RongOrder>> getOrderList(Long startTime, Long endTime); 
	
	//订单详细信息
	Response<RongOrderDetail> getOrderDetai(String orderNo);
	
	//订单补充信息
	Response<RongAddInfo> getOrderAddInfo(String orderNo);
	
	//反馈订单状态
	Response<Object> orderfeedback(String orderNo,int status);
	
	//订单审批结果反馈状态
	Response<Object> approvefeedback(String orderNo,int status); 
	
	//还款反馈状态
	Response<Object> repaymentfeedback(String orderNo,int status);
	
	
}
