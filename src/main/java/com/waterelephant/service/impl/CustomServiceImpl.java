package com.waterelephant.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.waterelephant.mapper.CustomCurrencyMapper;
import com.waterelephant.service.CustomService;

@Service
public class CustomServiceImpl implements CustomService {

	@Resource
	private CustomCurrencyMapper customCurrencyMapper;

	// 放款
	public Map findCapitalBaseNew(Long orderId) {
		return customCurrencyMapper.findCapitalBaseNew(orderId);
	}
}
