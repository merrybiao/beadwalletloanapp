package com.waterelephant.service;

import java.util.Date;

import com.waterelephant.entity.BwOrder;

public interface CapitalBaseService {

	void pushOrderStatus(BwOrder bwOrder, Date repaymentTime);

}
