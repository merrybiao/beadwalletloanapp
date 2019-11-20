package com.waterelephant.rongCarrier.jd.service;

import java.util.List;

import com.waterelephant.rongCarrier.jd.entity.OrderList;

public interface OrderListService {

	void saveJdOrderList(List<OrderList> list);
	
	boolean updateOrderList(OrderList orderList);
	
	int queryOrderList(Long borrowerId);
	
	OrderList queryBuyTime(Long borrowerId, String orderId);

	boolean deleteOrderList(Long borrowerId);
}
