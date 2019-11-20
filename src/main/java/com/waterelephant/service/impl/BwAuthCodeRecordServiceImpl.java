/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.waterelephant.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwAuthCodeRecord;
import com.waterelephant.service.BwAuthCodeRecordService;
import com.waterelephant.utils.RedisUtils;

@Service("bwAuthCodeRecordService")
public class BwAuthCodeRecordServiceImpl implements BwAuthCodeRecordService {

	private Logger logger = Logger.getLogger(BwAuthCodeRecordServiceImpl.class);

	/**
	 * 请求一次验证码 往redis里面记录
	 * 
	 * @see com.waterelephant.service.BwAuthCodeRecordService#addDataToBwAuthCodeRecord(com.waterelephant.entity.BwAuthCodeRecord)
	 */
	@Override
	public void addDataToBwAuthCodeRecord(BwAuthCodeRecord bwAuthCodeRecord) {
		try {
			if (bwAuthCodeRecord.getType() == 0) {
				if (!RedisUtils.exists("BwAuthCodeRecord_app")) {
					RedisUtils.hset("BwAuthCodeRecord_app", "requestNumber",
							bwAuthCodeRecord.getRequestNumber().toString());
					RedisUtils.hset("BwAuthCodeRecord_app", "requestSucceedNumber",
							bwAuthCodeRecord.getRequestSucceedNumber().toString());
					RedisUtils.hset("BwAuthCodeRecord_app", "requestFailNumber",
							bwAuthCodeRecord.getRequestFailNumber().toString());
					RedisUtils.hset("BwAuthCodeRecord_app", "type", bwAuthCodeRecord.getType().toString());
					logger.info("验证码请求记录存入Redis成功！");

				} else {
					RedisUtils.hincrby("BwAuthCodeRecord_app", "requestNumber",
							Long.parseLong(bwAuthCodeRecord.getRequestNumber().toString()));
					RedisUtils.hincrby("BwAuthCodeRecord_app", "requestSucceedNumber",
							Long.parseLong(bwAuthCodeRecord.getRequestSucceedNumber().toString()));
					RedisUtils.hincrby("BwAuthCodeRecord_app", "requestFailNumber",
							Long.parseLong(bwAuthCodeRecord.getRequestFailNumber().toString()));
					logger.info("验证码请求记录存入Redis成功！");
				}
			}
			if (bwAuthCodeRecord.getType() == 1) {
				if (!RedisUtils.exists("BwAuthCodeRecord_H5")) {
					RedisUtils.hset("BwAuthCodeRecord_H5", "requestNumber",
							bwAuthCodeRecord.getRequestNumber().toString());
					RedisUtils.hset("BwAuthCodeRecord_H5", "requestSucceedNumber",
							bwAuthCodeRecord.getRequestSucceedNumber().toString());
					RedisUtils.hset("BwAuthCodeRecord_H5", "requestFailNumber",
							bwAuthCodeRecord.getRequestFailNumber().toString());
					RedisUtils.hset("BwAuthCodeRecord_H5", "type", bwAuthCodeRecord.getType().toString());
					logger.info("验证码请求记录存入Redis成功！");
				} else {
					RedisUtils.hincrby("BwAuthCodeRecord_H5", "requestNumber",
							Long.parseLong(bwAuthCodeRecord.getRequestNumber().toString()));
					RedisUtils.hincrby("BwAuthCodeRecord_H5", "requestSucceedNumber",
							Long.parseLong(bwAuthCodeRecord.getRequestSucceedNumber().toString()));
					RedisUtils.hincrby("BwAuthCodeRecord_H5", "requestFailNumber",
							Long.parseLong(bwAuthCodeRecord.getRequestFailNumber().toString()));
					logger.info("验证码请求记录存入Redis成功！");
				}
			}
		} catch (Exception e) {
			logger.info("验证码请求记录存入Redis失败！");
		}

	}

}
