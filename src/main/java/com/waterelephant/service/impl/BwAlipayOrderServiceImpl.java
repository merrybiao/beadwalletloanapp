/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwAlipayOrder;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwAlipayOrderService;

/**
 * @author 崔雄健
 * @date 2017年5月10日
 * @description
 */
@Service("bwAlipayOrderService")
public class BwAlipayOrderServiceImpl extends BaseService<BwAlipayOrder, Long> implements BwAlipayOrderService {

	private Logger logger = Logger.getLogger(BwAlipayOrderServiceImpl.class);

	@Override
	public int update(BwAlipayOrder bwAlipayOrder) {
		return mapper.updateByPrimaryKey(bwAlipayOrder);
	}

	/**
	 * 查询
	 */
	@Override
	public BwAlipayOrder checkBwAlipayOrder(String alipayNo) {
		String sql = "select * from bw_alipay_order a where a.alipay_no ='" + alipayNo + "' limit 1";

		BwAlipayOrder bwAlipayOrder = sqlMapper.selectOne(sql, BwAlipayOrder.class);
		return bwAlipayOrder;
	}

	/**
	 * 查询
	 */
	@Override
	public BwAlipayOrder queryBwAlipayOrder(Long orderId) {
		String sql = "select * from bw_alipay_order a where a.order_id =" + orderId + " order by update_time limit 1";

		return sqlMapper.selectOne(sql, BwAlipayOrder.class);
	}

	@Override
	public BwAlipayOrder queryBwAlipayOrderById(Long id) {
		String sql = "select * from bw_alipay_order a where a.id =" + id + " limit 1";

		BwAlipayOrder bwAlipayOrder = sqlMapper.selectOne(sql, BwAlipayOrder.class);
		return bwAlipayOrder;
	}

	@Override
	public BwAlipayOrder queryBwAlipayOrderById(Long id, int type) {
		String sql = "select * from bw_alipay_order a where a.id =" + id + " and type = " + type + "  limit 1";

		BwAlipayOrder bwAlipayOrder = sqlMapper.selectOne(sql, BwAlipayOrder.class);
		return bwAlipayOrder;
	}

	/**
	 * 查询
	 */
	@Override
	public BwAlipayOrder queryBwAlipayOrderType(Long orderId, int type, String money) {
		String sql = "select * from bw_alipay_order a where a.order_id =" + orderId + " and type = " + type
				+ " and money=" + money + "  order by update_time limit 1";

		return sqlMapper.selectOne(sql, BwAlipayOrder.class);
	}

	@Override
	public void save(BwAlipayOrder bwAlipayOrder) {
		mapper.insert(bwAlipayOrder);

	}

	@Override
	public String getMaxTime() {
		String sql = "select max(a.transfer_time) from bw_alipay_order a  limit 1";
		String maxTime = sqlMapper.selectOne(sql, String.class);
		return maxTime;
	}

	@Override
	public void updateType(BwAlipayOrder bwAlipayOrder) {
		String sql = "update bw_alipay_order b set b.type=1,message=#{message} where b.id=#{id}";
		sqlMapper.update(sql, bwAlipayOrder);

	}

}
