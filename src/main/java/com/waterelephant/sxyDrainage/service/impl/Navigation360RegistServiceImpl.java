///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.sxyDrainage.service.Navigation360RegistService;
//
///**
// * 
// * 
// * Module:
// * 
// * Navigation360RegistServiceImpl.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: qihu360注册通知service实现类
// */
//@Service
//public class Navigation360RegistServiceImpl implements Navigation360RegistService {
//	private Logger logger = Logger.getLogger(Navigation360RegistServiceImpl.class);
//
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//
//	/**
//	 * 
//	 * @see com.waterelephant.sxyDrainageJob.service.Navigation360RegistService#findBorrower()
//	 */
//	@Override
//	public Map<String, Object> findBorrower(String startTime, String endTime, String channelId) {
//		logger.info("开始进入注册接口实现类方法");
//
//		List<BwBorrower> bwBorrowers = bwBorrowerService.findBorrowerByCreateTimeAndChannel(startTime, endTime,
//				channelId);
//
//		// 通知信息总数
//		String total = String.valueOf(bwBorrowers.size());
//
//		// 返回data数据
//		Map<String, Object> result = new HashMap<>();
//
//		// 对应的list字段
//		List<Map<String, String>> list = new ArrayList<>();
//
//		// 对应list字段中的phone,registerTime
//
//		for (BwBorrower borrower : bwBorrowers) {
//			Map<String, String> phoneAndTime = new HashMap<>();
//			String phone = borrower.getPhone();
//			phoneAndTime.put("phone", phone);
//			String registerTime = String.valueOf(borrower.getCreateTime().getTime() / 1000);
//			phoneAndTime.put("registerTime", registerTime);
//			list.add(phoneAndTime);
//
//		}
//
//		result.put("total", total);
//		result.put("list", list);
//		logger.info("成功执行注册通知实现类方法");
//
//		return result;
//	}
//
//}
