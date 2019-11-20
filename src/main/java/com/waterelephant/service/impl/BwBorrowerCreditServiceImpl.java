package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBorrowerCredit;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBorrowerCreditService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
/**
 * 授信单
 * @author dinglinhao
 *
 */
@Service
public class BwBorrowerCreditServiceImpl extends BaseService<BwBorrowerCredit, Long> implements BwBorrowerCreditService {

	@Override
	public BwBorrowerCredit queryCreditOrderById(Long creditId) {
		return selectByPrimaryKey(creditId);
	}

	@Override
	public BwBorrowerCredit queryCreditOrderByBorrowerId(Long borrowerId) {
		Example example = Example.builder(BwBorrowerCredit.class)
				.andWhere(Sqls.custom().andEqualTo("borrowerId", borrowerId))
				.orderByDesc("createTime")
				.build();
		List<BwBorrowerCredit> list = selectByExample(example);
		return list.isEmpty() ? null: list.get(0);
	}

	@Override
	public BwBorrowerCredit queryCreditOrderByCreditNo(String creditNo) {
		Example example = Example.builder(BwBorrowerCredit.class)
				.andWhere(Sqls.custom().andEqualTo("creditNo", creditNo))
				.orderByDesc("createTime")
				.build();
		List<BwBorrowerCredit> list = selectByExample(example);
		return list.isEmpty() ? null: list.get(0);
	}
	
	

}
