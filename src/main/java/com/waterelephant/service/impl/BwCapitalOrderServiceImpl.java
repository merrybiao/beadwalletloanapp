/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCapitalOrder;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCapitalOrderService;

/**
 * @author 崔雄健
 * @date 2017年4月6日
 * @description
 */
@Service("bwCapitalOrderService")
public class BwCapitalOrderServiceImpl extends BaseService<BwCapitalOrder, Long> implements BwCapitalOrderService {

	@Override
	public void save(BwCapitalOrder bwCapitalOrder) {
		mapper.insert(bwCapitalOrder);
	}

	@Override
	public void update(BwCapitalOrder bwCapitalOrder) {
		mapper.updateByPrimaryKey(bwCapitalOrder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.waterelephant.service.BwCapitalOrderService#queryBwCapitalOrder()
	 */
	@Override
	public BwCapitalOrder queryBwCapitalOrder(Long orderId, int capitalId) {
		String sql = "select a.* from bw_capital_order a where a.order_id = " + orderId + " and  a.capital_id="
				+ capitalId + " limit 0,1";

		return sqlMapper.selectOne(sql, BwCapitalOrder.class);

	}

	@Override
	public BwCapitalOrder queryBwCapitalOrder(String capitalNo, int capitalId) {
		String sql = "select a.* from bw_capital_order a where a.capital_no = '" + capitalNo + "' and  a.capital_id="
				+ capitalId + " limit 0,1";

		return sqlMapper.selectOne(sql, BwCapitalOrder.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.waterelephant.service.BwCapitalOrderService#updateBwCapitalOrder(java.lang.String)
	 */
	@Override
	public int updateBwCapitalOrder(int type, String status, String message, String orderNo) {
		String sql = "update bw_capital_order set type= " + type + ", order_status ='" + status + "'" + ", message ='"
				+ message + "'" + " where order_no='" + orderNo + "'";
		return sqlMapper.update(sql);

	}

	@Override
	public Map queryBwCapitalOrderOrderId(String orderId, int capitalId) {
		String sql = "select a.capital_no from bw_capital_order a where a.order_id = '" + orderId + "'and a.capital_id="
				+ capitalId + "limit 0,1";
		return sqlMapper.selectOne(sql);
	}

	@Override
	public BwCapitalOrder queryBwCapitalOrderByNo(String orderNo, int capitalId) {
		String sql = "select a.* from bw_capital_order a where a.order_no = '" + orderNo + "' and  a.capital_id="
				+ capitalId + " limit 0,1";
		return sqlMapper.selectOne(sql, BwCapitalOrder.class);

	}
}
