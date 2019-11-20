package com.waterelephant.rongCarrier.jd.service;

import com.waterelephant.rongCarrier.jd.entity.ShippingAddrs;

public interface ShippingAddrsService {

	public boolean saveShippingAddrs(ShippingAddrs shippingAddrs);
	
	public boolean updateShippingAddrs(ShippingAddrs shippingAddrs);
	
	public ShippingAddrs queryShippingAddrs(Long borrowerId, String addrId);
}
