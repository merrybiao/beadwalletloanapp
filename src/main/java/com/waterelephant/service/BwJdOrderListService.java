package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwJdOrderList;

public interface BwJdOrderListService {

	List<BwJdOrderList> findListByAttr(BwJdOrderList bwJdOrderList);

}