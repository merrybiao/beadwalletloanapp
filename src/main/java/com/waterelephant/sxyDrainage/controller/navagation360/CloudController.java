///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller.navagation360;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.sxyDrainage.service.CloudService;
//
///**
// * (code:saas)
// * 
// * Module:把beadwalletloan库的订单状态为1和7的用户数据导入到saas库中
// * 
// * CloudController.java
// * 
// * @author zhangchong
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Controller
//public class CloudController {
//	private Logger logger = Logger.getLogger(CloudController.class);
//	@Autowired
//	private CloudService cloudSercide;
//	@Autowired
//	private IBwOrderService bwOrderService;
//
//	@ResponseBody
//	@RequestMapping("/saas/getData.do")
//	public Map<String, Object> getData(String startTime, String endTime, int pageSize, int pageNum) {
//		Map<String, Object> data = new HashMap<>();
//		try {
//			List<BwBorrower> bwBorrowers = cloudSercide.findBwBorrower(startTime, endTime, pageSize, pageNum);
//			if (bwBorrowers != null && bwBorrowers.size() > 0) {
//				data.put("code", 200);
//				data.put("msg", bwBorrowers);
//			} else {
//				data.put("code", 100);
//				data.put("msg", null);
//			}
//
//		} catch (Exception e) {
//			logger.error("获取数据异常", e);
//			data.put("code", 400);
//			data.put("msg", null);
//		}
//		return data;
//	}
//
//	/**
//	 * 把beadwalletloan库的订单状态为6，商户id不为0的用户数据导入到saas库中
//	 * 
//	 * @param merchantId
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/saas/findData.do")
//	public Map<String, Object> findData(Long merchantId) {
//		Map<String, Object> data = new HashMap<>();
//		try {
//			List<BwBorrower> bwBorrowers = cloudSercide.findBwBorrowerList(merchantId);
//			if (bwBorrowers != null && bwBorrowers.size() > 0) {
//				data.put("code", 200);
//				data.put("msg", bwBorrowers);
//			} else {
//				data.put("code", 100);
//				data.put("msg", null);
//			}
//		} catch (Exception e) {
//			logger.error("查询数据异常", e);
//			data.put("code", 400);
//			data.put("msg", null);
//		}
//		return data;
//
//	}
//
//	/**
//	 * 获取用户信息
//	 * 
//	 * @param startTime
//	 * @param endTime
//	 * @param pageSize
//	 * @param pageNum
//	 * @param orderStatus
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/saas/getBorrowerInfo.do")
//	public Map<String, Object> getBorrowerInfo(String startTime, String endTime, int pageSize, int pageNum, int orderStatus) {
//		Map<String, Object> data = new HashMap<>();
//		try {
//			Integer totalNumber = bwOrderService.findCountByParams(startTime, endTime, orderStatus);
//
//			List<Map<String, String>> list = cloudSercide.findBorrowerInfoList(startTime, endTime, pageSize, pageNum,
//					orderStatus);
//			if (list != null && list.size() > 0) {
//				data.put("code", 200);
//				data.put("message", "success");
//				data.put("totalNumber", totalNumber);
//				data.put("data", JSON.toJSONString(list));
//			} else {
//				data.put("code", 200);
//				data.put("message", "success");
//				data.put("totalNumber", totalNumber);
//				data.put("data", null);
//			}
//		} catch (Exception e) {
//			logger.error("查询数据异常", e);
//			data.put("code", 400);
//			data.put("message", "fail");
//		}
//		return data;
//	}
//
//	/**
//	 * 获取订单为7、8的用户信息
//	 * 
//	 * @param startTime
//	 * @param endTime
//	 * @param pageSize
//	 * @param pageNum
//	 * @param orderStatus
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/saas/getBorrowerInfo2.do")
//	public Map<String, Object> getBorrowerInfo2(String startTime, String endTime, int pageSize, int pageNum, int orderStatus) {
//		Map<String, Object> data = new HashMap<>();
//		try {
//				Integer totalNumber = bwOrderService.findCountByParams2(startTime, endTime, orderStatus);
//
//			List<Map<String, String>> list = cloudSercide.findBorrowerInfoList(startTime, endTime, pageSize, pageNum,
//					orderStatus);
//			if (list != null && list.size() > 0) {
//				data.put("code", 200);
//				data.put("message", "success");
//				data.put("totalNumber", totalNumber);
//				data.put("data", JSON.toJSONString(list));
//			} else {
//				data.put("code", 200);
//				data.put("message", "success");
//				data.put("totalNumber", totalNumber);
//				data.put("data", null);
//			}
//		} catch (Exception e) {
//			logger.error("查询数据2异常", e);
//			data.put("code", 400);
//			data.put("message", "fail");
//		}
//		return data;
//	}
//
//	/**
//	 * 获取水象云贷用户信息
//	 * 
//	 * @param startTime
//	 * @param endTime
//	 * @param pageSize
//	 * @param pageNum
//	 * @param orderStatus
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/saas/getBorrowerInfoByYD.do")
//	public Map<String, Object> getBorrowerInfoByYD(String startTime, String endTime, int pageSize, int pageNum, int orderStatus) {
//		Map<String, Object> data = new HashMap<>();
//		try {
//			String url = "http://openapi.51sxyd.com/user/getUserList";
//			Map<String, Object> params = new HashMap<>();
//			params.put("pageNum", pageNum);
//			params.put("pageSize", pageSize);
//			params.put("beginDate", startTime);
//			params.put("endDate", endTime);
//			params.put("orderStatus", orderStatus);
//			Map<String, String> map = new HashMap<>();
//			map.put("jsonData", JSON.toJSONString(params));
//
//			String json = HttpClientHelper.post(url, "utf-8", map);
//			JSONObject jsonObject = JSON.parseObject(json);
//			String code = jsonObject.getString("code");
//			String message = jsonObject.getString("message");
//			String totalNumber = jsonObject.getString("totalNumber");
//			String da = jsonObject.getString("data");
//
//			if (code == "200") {
//				data.put("code", code);
//				data.put("message", message);
//				data.put("totalNumber", totalNumber);
//				data.put("data", da);
//			} else {
//				data.put("code", code);
//				data.put("message", message);
//				data.put("totalNumber", totalNumber);
//				data.put("data", da);
//			}
//		} catch (Exception e) {
//			logger.error("查询数据云贷异常", e);
//			data.put("code", 400);
//			data.put("message", "fail");
//		}
//		return data;
//	}
//}
