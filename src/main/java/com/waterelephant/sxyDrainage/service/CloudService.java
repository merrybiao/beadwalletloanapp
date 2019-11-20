///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import java.util.List;
//import java.util.Map;
//
//import com.waterelephant.entity.BwBorrower;
//
///**
// * 
// * 
// * Module:(code:saas)
// * 
// * CloudService.java
// * 
// * @author zhangchong
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public interface CloudService {
//	/**
//	 * 通过时间查询
//	 * 
//	 * @param startTime
//	 * @param endTime
//	 * @param pageSize
//	 * @param pageNum
//	 * @return
//	 */
//	List<BwBorrower> findBwBorrower(String startTime, String endTime, int pageSize, int pageNum);
//
//	/**
//	 * 查询订单状态为6，商户id不为0的用户
//	 * 
//	 * @return
//	 */
//	List<BwBorrower> findBwBorrowerList(Long merchantId);
//
//	/**
//	 * 查询用户信息
//	 * 
//	 * @param startTime
//	 * @param endTime
//	 * @param pageSize
//	 * @param pageNum
//	 * @param orderStatus
//	 * @return
//	 */
//	List<Map<String, String>> findBorrowerInfoList(String startTime, String endTime, int pageSize, int pageNum,
//			int orderStatus);
//}
