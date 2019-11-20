/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBorrowerDetail;
import com.waterelephant.service.BwBorrowerDetailService;

/**
 * 
 * Module:
 * 
 * BwBorrowerDetailServiceImpl.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: 借款人详情service
 */
@Service("bwBorrowerDetailService")
public class BwBorrowerDetailServiceImpl extends BaseCommonServiceImpl<BwBorrowerDetail, Long>
		implements BwBorrowerDetailService {

	@Override
	public BwBorrowerDetail selectByBorrowerId(Long borrowerId) {
		if (borrowerId != null && borrowerId > 0L) {
			BwBorrowerDetail paramDetail = new BwBorrowerDetail();
			paramDetail.setBorrowerId(borrowerId);
			List<BwBorrowerDetail> list = select(paramDetail);
			if (list != null && !list.isEmpty()) {
				return list.get(list.size() - 1);
			}
		}
		return null;
	}
}