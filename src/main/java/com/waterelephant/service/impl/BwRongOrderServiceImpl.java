package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.entity.response.RongAddInfo;
import com.beadwallet.service.entity.response.RongOrder;
import com.beadwallet.service.entity.response.RongOrderDetail;
import com.beadwallet.service.serve.BeadWalletRongOrderService;
import com.waterelephant.service.BwRongOrderService;


@Service
public class BwRongOrderServiceImpl implements BwRongOrderService{

	@Override
	public Response<List<RongOrder>> getOrderList(Long startTime, Long endTime) {
		return BeadWalletRongOrderService.getOrderList(startTime, endTime);
	}

	@Override
	public Response<Object> orderfeedback(String orderNo, int status) {
		return BeadWalletRongOrderService.orderfeedback(orderNo, status);
	}

	@Override
	public Response<Object> approvefeedback(String orderNo, int status) {
		return BeadWalletRongOrderService.orderfeedback(orderNo, status);
	}

	@Override
	public Response<Object> repaymentfeedback(String orderNo, int status) {
		return BeadWalletRongOrderService.orderfeedback(orderNo, status);
	}

	@Override
	public Response<RongOrderDetail> getOrderDetai(String orderNo) {
		return BeadWalletRongOrderService.getOrderDetail(orderNo);
	}

	@Override
	public Response<RongAddInfo> getOrderAddInfo(String orderNo) {
		return BeadWalletRongOrderService.getOrderAddInfo(orderNo);
		
	}


}
