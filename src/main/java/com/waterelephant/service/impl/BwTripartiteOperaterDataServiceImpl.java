/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwTripartiteOperaterData;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwTripartiteOperaterDataService;

/**
 * 
 * 
 * Module:
 * 
 * BwTripartiteOperaterDataServiceImpl.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwTripartiteOperaterDataServiceImpl extends BaseService<BwTripartiteOperaterData, Long>
		implements BwTripartiteOperaterDataService {

	/**
	 * 
	 * @see com.waterelephant.service.BwTripartiteOperaterDataService#save(com.waterelephant.entity.BwTripartiteOperaterData)
	 */
	@Override
	public Integer save(BwTripartiteOperaterData bwTripartiteOperaterData) {
		return mapper.insert(bwTripartiteOperaterData);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwTripartiteOperaterDataService#updateByOrderId(java.lang.Long)
	 */
	@Override
	public Integer updateByOrderId(BwTripartiteOperaterData bwTripartiteOperaterData) {
		return mapper.updateByPrimaryKey(bwTripartiteOperaterData);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwTripartiteOperaterDataService#findByAttr(com.waterelephant.entity.BwTripartiteOperaterData)
	 */
	@Override
	public BwTripartiteOperaterData findByAttr(BwTripartiteOperaterData bwTripartiteOperaterData) {
		return mapper.selectOne(bwTripartiteOperaterData);
	}

}
