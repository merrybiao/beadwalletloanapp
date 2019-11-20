/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCapitalExternalPush;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCapitalExternalPushService;

/**
 * @author 崔雄健
 * @date 2017年4月6日
 * @description
 */
@Service("bwCapitalExternalPushService")
public class BwCapitalExternalPushServiceImpl extends BaseService<BwCapitalExternalPush, Long>
		implements BwCapitalExternalPushService {

	@Override
	public void save(BwCapitalExternalPush bwCapitalExternalPush) {
		mapper.insert(bwCapitalExternalPush);
	}

	@Override
	public BwCapitalExternalPush queryBwCapitalExternalPush(String orderNo) {
		String sql = "select a.* from bw_capital_external_push a where  a.order_no = '" + orderNo
				+ "' order by a.create_time desc limit 1";
		return sqlMapper.selectOne(sql, BwCapitalExternalPush.class);
	}

	@Override
	public BwCapitalExternalPush queryBwCapitalExternalPush(String orderNo, int capitalId) {
		String sql = "select a.* from bw_capital_external_push a where  a.order_no = '" + orderNo
				+ "' and capital_id = " + capitalId + " order by a.create_time desc limit 1";
		return sqlMapper.selectOne(sql, BwCapitalExternalPush.class);
	}

	@Override
	public int updateBwCapitalExternalPush(BwCapitalExternalPush bwCapitalExternalPush) {

		return mapper.updateByPrimaryKey(bwCapitalExternalPush);
	}

	@Override
	public Integer queryBwCapitalExternalPushCount(String orderNo) {
		String sql = "select count(1) from bw_capital_external_push a where  a.order_no = '" + orderNo
				+ "' and a.push_status>0";
		return sqlMapper.selectOne(sql, Integer.class);
	}

}
