package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwJdShippingAddrs;

public interface BwJdShippingAddrsService {

	List<BwJdShippingAddrs> findListByAttr(BwJdShippingAddrs bwJdShippingAddrs);

}