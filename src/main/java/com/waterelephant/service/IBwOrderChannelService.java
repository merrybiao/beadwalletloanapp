package com.waterelephant.service;

import com.waterelephant.entity.BwOrderChannel;

public interface IBwOrderChannelService {

	BwOrderChannel getOrderChannelByCode(String code);
	
	boolean addOrderChannel(BwOrderChannel orderChannel);
	
	BwOrderChannel findBwOrderChannel(BwOrderChannel orderChannel);
}
