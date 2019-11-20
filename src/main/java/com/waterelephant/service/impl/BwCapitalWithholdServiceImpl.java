/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCapitalWithhold;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCapitalWithholdService;

import java.util.List;

/**
 * Module:
 * 
 * BwWithholdPushServiceImpl.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service("bwCapitalWithholdService")
public class BwCapitalWithholdServiceImpl extends BaseService<BwCapitalWithhold, Long>
		implements BwCapitalWithholdService {

	@Override
	public void save(BwCapitalWithhold bwCapitalWithhold) {
		mapper.insertSelective(bwCapitalWithhold);
	}

	@Override
	public BwCapitalWithhold queryBwCapitalWithhold(Long id) {
		return selectByPrimaryKey(id);
	}

	@Override
	public BwCapitalWithhold queryBwCapitalWithhold(Long orderId, Long id) {
		return queryBwCapitalWithhold(id);
	}

	@Override
	public int updateBwCapitalWithhold(BwCapitalWithhold bwCapitalWithhold) {
		return mapper.updateByPrimaryKeySelective(bwCapitalWithhold);
	}

	@Override
	public BwCapitalWithhold queryBwCapitalWithhold(String otherOrderNo) {
		if (StringUtils.isEmpty(otherOrderNo)) {
			return null;
		}
		BwCapitalWithhold param = new BwCapitalWithhold();
		param.setOtherOrderNo(otherOrderNo);
		List<BwCapitalWithhold> list = select(param);
		if (list != null && !list.isEmpty()) {
			return list.get(list.size() - 1);
		}
		return null;
	}
}
