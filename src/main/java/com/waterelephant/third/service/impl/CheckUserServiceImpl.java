/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.third.service.CheckUserService;
import com.waterelephant.utils.CommUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 统一对外接口 -判断新老用户（code0091）
 * 
 * Module:
 * 
 * CheckUserServiceImpl.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class CheckUserServiceImpl implements CheckUserService {
	private Logger logger = Logger.getLogger(CheckUserServiceImpl.class);
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;

	/**
	 * 公共方法 - 判断是否是老用户
	 * 
	 * @param idCard
	 * @param name
	 * @return boolean
	 * @author zhangchong
	 * 
	 */
	@Override
	public boolean isOldUser(String idCard, String name, String mobile) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter2(name, mobile, idCard);
		if (borrower != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 公共方法 - 判断是否黑名单
	 * 
	 * @param idCard
	 * @param name
	 * @return boolean
	 * @author zhangchong
	 * 
	 */
	@Override
	public boolean isBlackUser(String idCard, String name, String mobile) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter2(name, mobile, idCard);
		if (CommUtils.isNull(borrower)) {
			return false;
		}

		Example example = new Example(BwBlacklist.class);
		example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card", idCard.toUpperCase());
		List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
		if (CommUtils.isNull(desList) == false) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 公共方法 - 判断是否有进行中的订单
	 * 
	 * @param idCard
	 * @return boolean
	 * @author zhangchong
	 * 
	 */
	@Override
	public boolean isPocessingOrder(String idCard, String name, String mobile) {
		BwBorrower bw = new BwBorrower();
		bw.setIdCard(idCard);
		bw.setName(name);
		bw.setPhone(mobile);
		bw = bwBorrowerService.findBwBorrowerByAttr(bw);
		if (bw != null) {
			logger.info("开始考拉征信查询进行中的订单,borrowerId=" + bw.getId());
			Long count = bwOrderService.findProOrder(String.valueOf(bw.getId()));
			logger.info("结束考拉征信查询进行中的订单,count=" + count);
			if (count != null && count.intValue() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 公共方法 - 是否被拒（使用该方法得确保用户已存在）
	 * 
	 * @param idCard
	 * @param name
	 * @return boolean
	 * @author zhangchong
	 */
	@Override
	public boolean isRejectRecord(String idCard, String name, String mobile) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter2(name, mobile, idCard);
		if (CommUtils.isNull(borrower)) {
			return false;
		}
		// 查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
		BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
		if (!CommUtils.isNull(record)) {
			// 永久拒绝
			if ("0".equals(String.valueOf(record.getRejectType()))) {
				return true;
			} else {
				// 判断临时拒绝是否到期，如果没到期则返回400，否则返回200+is_reloan=1
				Date rejectDate = record.getCreateTime();
				long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime()) / (24 * 60 * 60 * 1000);
				if (day <= 7) {
					return true;
				}
			}
		}
		return false;
	}
}
