/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwWinpayOrder;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwWinpayOrderService;

/**
 * @author 崔雄健
 * @date 2017年5月10日
 * @description
 */
@Service("bwWinpayOrderService")
public class BwWinpayOrderServiceImpl extends BaseService<BwWinpayOrder, Long> implements BwWinpayOrderService {

	private Logger logger = Logger.getLogger(BwWinpayOrderServiceImpl.class);

	@Override
	public BwWinpayOrder queryBwWinpayOrderById(Long id) {
		String sql = "select * from bw_winpay_order a where a.id =" + id + " limit 1";

		BwWinpayOrder bwWinpayOrder = sqlMapper.selectOne(sql, BwWinpayOrder.class);
		return bwWinpayOrder;
	}

	@Override
	public BwWinpayOrder queryBwWinpayOrderById(Long id, int type) {
		String sql = "select * from bw_winpay_order a where a.id =" + id + " and type = " + type + " limit 1";

		BwWinpayOrder bwWinpayOrder = sqlMapper.selectOne(sql, BwWinpayOrder.class);
		return bwWinpayOrder;
	}

	@Override
	public int update(BwWinpayOrder bwWinpayOrder) {
		return mapper.updateByPrimaryKey(bwWinpayOrder);
	}

	/**
	 * 查询
	 */
	@Override
	public BwWinpayOrder queryBwWinpayOrder(Long orderId) {
		String sql = "select * from bw_winpay_order a where a.order_id =" + orderId + " order by update_time limit 1";

		return sqlMapper.selectOne(sql, BwWinpayOrder.class);
	}

	@Override
	public void save(BwWinpayOrder bwWinpayOrder) {
		mapper.insert(bwWinpayOrder);

	}

	/**
	 * 查询
	 */
	@Override
	public BwWinpayOrder queryBwWinpayOrderType(Long orderId, int type, String money) {
		String sql = "select * from bw_winpay_order a where a.order_id =" + orderId + " and type = " + type
				+ " and money=" + money + "  order by update_time limit 1";

		return sqlMapper.selectOne(sql, BwWinpayOrder.class);
	}

}
