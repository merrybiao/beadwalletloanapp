package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwTbOrder;

public interface BwTbOrderService {

	List<BwTbOrder> findListByAttr(BwTbOrder bwTbOrder);

	BwTbOrder findByAttr(BwTbOrder bwTbOrder);

}