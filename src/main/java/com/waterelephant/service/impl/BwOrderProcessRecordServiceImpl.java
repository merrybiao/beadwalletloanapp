/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.mapper.BwOrderProcessRecordMapper;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 
 * Module:
 * 
 * BwOrderProcessRecordServiceImpl.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwOrderProcessRecordServiceImpl extends BaseCommonServiceImpl<BwOrderProcessRecord, Long>
		implements BwOrderProcessRecordService {
	private Logger logger = Logger.getLogger(BwOrderProcessRecordServiceImpl.class);
	@Autowired
	private BwOrderProcessRecordMapper bwOrderProcessRecordMapper;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;

	@Override
	public BwOrderProcessRecord selectByOrderId(Long orderId) {
		BwOrderProcessRecord queryBwOrderProcessRecord = null;
		if (orderId != null && orderId >= 0) {
			Example example = new Example(BwOrderProcessRecord.class);
			example.setOrderByClause("id DESC");
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("orderId", orderId);
			List<BwOrderProcessRecord> list = bwOrderProcessRecordMapper.selectByExample(example);
			if (list != null && !list.isEmpty()) {
				queryBwOrderProcessRecord = list.get(0);
			}
		}
		return queryBwOrderProcessRecord;
	}

	@Override
	public BwOrderProcessRecord saveOrUpdateByOrderId(BwOrderProcessRecord bwOrderProcessRecord) {
		if (bwOrderProcessRecord == null) {
			return null;
		}
		Long orderId = bwOrderProcessRecord.getOrderId();
		BwOrder bwOrder = null;
		if (orderId != null) {
			bwOrder = bwOrderService.findBwOrderById(orderId.toString());
		}
		Long borrowerId = null;
		BwBorrower bwBorrower = null;
		if (bwOrder != null) {
			borrowerId = bwOrder.getBorrowerId();
			if (borrowerId != null) {
				bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
			}

			// 设置值
			Date submitTime = bwOrder.getSubmitTime();
			Integer productType = bwOrder.getProductType();
			Integer orderChannel = bwOrder.getChannel();
			Integer borrowerChannel = bwBorrower.getChannel();
			// 已借款次数
			Integer borrowCount = sqlMapper.selectOne("select count(*) from bw_order where status_id=6 and borrower_id="
					+ borrowerId + " and id<>" + orderId, Integer.class);
			bwOrderProcessRecord.setBorrowCount(borrowCount);
			if (orderChannel != null) {
				bwOrderProcessRecord.setOrderChannel(orderChannel);
			}
			if (borrowerChannel != null) {
				bwOrderProcessRecord.setRegisterChannel(borrowerChannel);
			}
			if (submitTime != null) {
				bwOrderProcessRecord.setSubmitTime(submitTime);
			}
			if (productType != null) {
				bwOrderProcessRecord.setProductType(productType);
			}
		}

		BwOrderProcessRecord queryBwOrderProcessRecord = selectByOrderId(orderId);
		Date nowDate = new Date();
		bwOrderProcessRecord.setUpdateTime(nowDate);
		if (queryBwOrderProcessRecord != null) {// 修改
			bwOrderProcessRecord.setId(queryBwOrderProcessRecord.getId()).setUpdateTime(nowDate);
			bwOrderProcessRecordMapper.updateByPrimaryKeySelective(bwOrderProcessRecord);
		} else {// 新增
			bwOrderProcessRecord.setId(null).setCreateTime(nowDate).setUpdateTime(nowDate);
			if (borrowerId != null && borrowerId > 0L) {
				bwOrderProcessRecord.setBorrowerId(borrowerId);
			}
			if (bwOrder != null) {
				bwOrderProcessRecord.setDraftTime(bwOrder.getCreateTime());
			}
			if (bwBorrower != null) {
				bwOrderProcessRecord.setRegisterTime(bwBorrower.getCreateTime());
			}
			bwOrderProcessRecordMapper.insertSelective(bwOrderProcessRecord);
			logger.info("【BwOrderProcessRecordServiceImpl.saveOrUpdateByOrderId】orderId:" + orderId + ",borrowerId:"
					+ bwOrderProcessRecord.getBorrowerId() + ",bwOrderProcessRecord="
					+ JSON.toJSONString(bwOrderProcessRecord) + ",新增工单处理记录");
		}
		return bwOrderProcessRecord;
	}

	// 放款成功
	@Override
	public BwOrderProcessRecord saveOrUpdateByOrderIdRepay(Long orderId, Date loanDate) {
		BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
		bwOrderProcessRecord.setOrderId(orderId);
		bwOrderProcessRecord.setLoanTime(loanDate);
		return saveOrUpdateByOrderId(bwOrderProcessRecord);
	}
}