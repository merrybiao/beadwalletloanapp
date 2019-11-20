package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwOrderChannelService;

@Service
public class BwOrderChannelServiceImpl extends BaseService<BwOrderChannel, Long> implements IBwOrderChannelService {

	@Override
	public BwOrderChannel getOrderChannelByCode(String code) {
		BwOrderChannel channel = new BwOrderChannel();
		channel.setChannelCode(code);
		return mapper.selectOne(channel);
	}
	
	@Override
	public boolean addOrderChannel(BwOrderChannel orderChannel) {
		 return mapper.insert(orderChannel) > 0;
	}

	@Override
	public BwOrderChannel findBwOrderChannel(BwOrderChannel orderChannel) {
		return mapper.selectOne(orderChannel);
	}
}
