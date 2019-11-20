/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCapitalPush;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCapitalPushService;

/**
 * @author 崔雄健
 * @date 2017年4月6日
 * @description
 */
@Service("bwCapitalPushService")
public class BwCapitalPushServiceImpl extends BaseService<BwCapitalPush, Long> implements BwCapitalPushService {

	@Override
	public void save(BwCapitalPush bwCapitalPush) {
		mapper.insert(bwCapitalPush);
	}

	@Override
	public BwCapitalPush queryBwCapitalPush(Long orderId) {
		String sql = "select a.* from bw_capital_push a where  a.order_id = " + orderId
				+ " order by a.create_time desc limit 1";
		return sqlMapper.selectOne(sql, BwCapitalPush.class);
	}

	@Override
	public BwCapitalPush queryBwCapitalPush(Long orderId, int capitalId) {
		String sql = "select a.* from bw_capital_push a where  a.order_id = " + orderId + " and capital_id = "
				+ capitalId + " order by a.create_time desc limit 1";
		return sqlMapper.selectOne(sql, BwCapitalPush.class);
	}

	@Override
	public int updateBwCapitalPush(BwCapitalPush bwCapitalPush) {

		return mapper.updateByPrimaryKey(bwCapitalPush);
	}
}
