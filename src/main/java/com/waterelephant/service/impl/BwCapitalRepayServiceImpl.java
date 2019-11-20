/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCapitalRepay;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCapitalRepayService;

/**
 * @author 崔雄健
 * @date 2017年4月6日
 * @description
 */
@Service("bwCapitalRepayService")
public class BwCapitalRepayServiceImpl extends BaseService<BwCapitalRepay, Long> implements BwCapitalRepayService {

	@Override
	public void save(BwCapitalRepay bwCapitalRepay) {
		mapper.insert(bwCapitalRepay);
	}

	@Override
	public BwCapitalRepay queryBwCapitalRepay(Long orderId) {
		String sql = "select a.* from bw_capital_repay a where  a.order_id = " + orderId
				+ " order by a.create_time desc limit 1";
		return sqlMapper.selectOne(sql, BwCapitalRepay.class);
	}

	@Override
	public int updateBwCapitalRepay(BwCapitalRepay bwCapitalRepay) {
		return mapper.updateByPrimaryKey(bwCapitalRepay);
	}

	@Override
	public BwCapitalRepay queryBwCapitalRepay(Long orderId, int capitalId) {
		String sql = "select a.* from bw_capital_repay a where  a.order_id = " + orderId + " and a.capital_id = "
				+ capitalId + " order by a.create_time desc limit 1";
		return sqlMapper.selectOne(sql, BwCapitalRepay.class);
	}
}
