package com.waterelephant.linkface.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.linkface.entity.LinkfaceVerify;
import com.waterelephant.linkface.service.LinkfaceVerifyService;
import com.waterelephant.service.BaseService;

/**
 * linkface - 活体检测记录事物处理 code0088
 * 
 * 
 * Module:
 * 
 * LinkfaceVerifyServiceImpl.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class LinkfaceVerifyServiceImpl extends BaseService<LinkfaceVerify, Long> implements LinkfaceVerifyService {

	@Override
	public void saveLinkfaceVerify(LinkfaceVerify linkfaceVerify) {
		mapper.insert(linkfaceVerify);
	}
}
