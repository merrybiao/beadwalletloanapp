/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.entity.BwPaymentDetail;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwPaymentDetailService;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;

/**
 * Module:
 * 
 * BwPaymentDetailServiceImpl.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: 还款或续贷支付明细service实现
 */
@Service
public class BwPaymentDetailServiceImpl extends BaseService<BwPaymentDetail, Long> implements BwPaymentDetailService {

	private Logger logger = Logger.getLogger(BwPaymentDetailServiceImpl.class);

	@Override
	public BwPaymentDetail query(Long repayId) {
		BwPaymentDetail bwPaymentDetail = null;
		if (repayId != null) {
			BwPaymentDetail param = new BwPaymentDetail();
			param.setRepayId(repayId);
			List<BwPaymentDetail> list = mapper.select(param);
			if (list != null && !list.isEmpty()) {
				bwPaymentDetail = list.get(list.size() - 1);
			}
		}
		return bwPaymentDetail;
	}

	/**
	 * @see com.waterelephant.target.service.BwPaymentDetailService#saveOrUpdateByOrderIdAndType(com.waterelephant.common.entity.BwPaymentDetail)
	 */
	@Override
	public BwPaymentDetail saveOrUpdateByRepayId(BwPaymentDetail bwPaymentDetail) {
		if (bwPaymentDetail == null) {
			return null;
		}
		Long repayId = bwPaymentDetail.getRepayId();
		Date nowDate = new Date();
		BwPaymentDetail queryPaymentDetail = null;
		if (repayId != null && repayId > 0L) {
			queryPaymentDetail = query(repayId);
		}
		bwPaymentDetail.setUpdateTime(nowDate);
		if (queryPaymentDetail == null) {
			bwPaymentDetail.setCreateTime(nowDate);
			mapper.insertSelective(bwPaymentDetail);
		} else {
			bwPaymentDetail.setId(queryPaymentDetail.getId());
			mapper.updateByPrimaryKeySelective(bwPaymentDetail);
		}
		return bwPaymentDetail;
	}

	/**
	 * @see com.waterelephant.target.service.BwPaymentDetailService#saveOrUpdateByRedis(java.lang.Long,
	 *      java.lang.Integer)
	 */
	@Override
	public BwPaymentDetail saveOrUpdateByRedis(Long repayId, boolean isDelRedis) {
		BwPaymentDetail bwPaymentDetail = null;
		if (repayId == null) {
			return bwPaymentDetail;
		}
		// payment_detail:order_id:1(还款)和payment_detail:order_id:2(续贷)
		String paymentDetailRedisKey = RedisKeyConstant.PAYMENT_DETAIL;
		// 获取redis数据并保存，保存后删除
		String payDetailStr = RedisUtils.hget(paymentDetailRedisKey, repayId.toString());
		logger.info("repayId：" + repayId + "，保存支付明细信息：" + payDetailStr);
		if (!StringUtil.isEmpty(payDetailStr)) {
			BwPaymentDetail redisPaymentDetail = JSON.parseObject(payDetailStr, BwPaymentDetail.class);
			redisPaymentDetail.setPayStatus(1);
			redisPaymentDetail.setTradeTime(new Date());
			bwPaymentDetail = saveOrUpdateByRepayId(redisPaymentDetail);
			if (isDelRedis) {
				deleteRedis(repayId);
				logger.info("保存后删除redis支付明细：" + payDetailStr);
			}
		}
		return bwPaymentDetail;
	}

	@Override
	public void deleteRedis(Long repayId) {
		RedisUtils.hdel(RedisKeyConstant.PAYMENT_DETAIL, String.valueOf(repayId));
	}

	@Override
	public BwPaymentDetail queryByRedisOrDB(Long repayId) {
		BwPaymentDetail bwPaymentDetail = null;
		if (repayId != null) {
			String paymentDetailRedisKey = RedisKeyConstant.PAYMENT_DETAIL;
			String payDetailStr = RedisUtils.hget(paymentDetailRedisKey, repayId.toString());
			if (!StringUtil.isEmpty(payDetailStr)) {
				bwPaymentDetail = JSON.parseObject(payDetailStr, BwPaymentDetail.class);
			}
			// redis没有从数据库查
			if (bwPaymentDetail == null) {
				bwPaymentDetail = query(repayId);
			}
		}
		return bwPaymentDetail;
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwPaymentDetailService#deleteBwPaymentDetail(java.lang.Long)
	 */
	@Override
	public void deleteBwPaymentDetail(Long repayId) {
		sqlMapper.delete("delete from bw_payment_detail where repay_id=" + repayId);
	}
}