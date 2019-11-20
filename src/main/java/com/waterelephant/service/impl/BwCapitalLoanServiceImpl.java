/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCapitalLoan;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCapitalLoanService;

/**
 * 
 * Module:
 * 
 * BwCapitalLoanServiceImpl.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2017年9月19日
 */
@Service("bwCapitalLoanService")
public class BwCapitalLoanServiceImpl extends BaseService<BwCapitalLoan, Long> implements BwCapitalLoanService {

	@Override
	public void save(BwCapitalLoan bwCapitalLoan) {
		mapper.insert(bwCapitalLoan);
	}

	@Override
	public BwCapitalLoan queryBwCapitalLoan(Long orderId, int capitalId) {
		String sql = "select a.* from bw_capital_loan a where  a.order_id = " + orderId + " and capital_id = "
				+ capitalId + " order by a.create_time desc limit 1";
		return sqlMapper.selectOne(sql, BwCapitalLoan.class);
	}

	@Override
	public int updateBwCapitalLoan(BwCapitalLoan bwCapitalLoan) {

		return mapper.updateByPrimaryKey(bwCapitalLoan);
	}

	@Override
	public int updateBwCapitalLoanByorderId(Long orderId, int capitalId, int pushStatus) {
		String sql = "update bw_capital_loan a set a.push_status=" + pushStatus + ",capital_id=" + capitalId
				+ " where order_id=" + orderId;
		return sqlMapper.update(sql);
	}

	@Override
	public BwCapitalLoan queryBwCapitalLoanCapitalNo(Long orderId, int capitalId, String capitalNo) {
		String sql = "select a.* from bw_capital_loan a where  a.order_id = " + orderId + " and capital_id = "
				+ capitalId + " and capital_no='" + capitalNo + "' order by a.create_time desc limit 1";
		return sqlMapper.selectOne(sql, BwCapitalLoan.class);
	}

}
