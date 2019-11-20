/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.activity.service.ActivityService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.entity.ActivityDiscountDistribute;
import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;

/**
 * 新手活动管理
 * 
 * Module:
 * 
 * ActivityController.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

@Controller
@RequestMapping("/app/activity")
public class AppActivityController {

	private Logger logger = Logger.getLogger(AppActivityController.class);

	@Autowired
	private ActivityService activityService;

	/**
	 * 根据活动类型查询活动
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getActivity.do")
	public AppResponseResult getActivity(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			ActivityInfo entity = new ActivityInfo();
			String activityType = request.getParameter("activityType");
			logger.info("接收到的参数:activityType=" + activityType);
			if (!CommUtils.isNull(activityType)) {
				entity.setActivityType(activityType);
			} else {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("activityType不能为空");
				result.setResult(false);// 处理失败
				logger.info("activityType不能为空");
				return result;
			}
			// 根据活动类型查询活动信息
			Object object = new Object();
			object = activityService.getActivity(entity);
			// 指定活动不存在
			if (CommUtils.isNull(object)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.ACTIVITY_NOT_EXIST);
				result.setResult(false);// 处理失败
				logger.info("活动不存在");
				return result;
			} else {
				result.setCode(ActivityConstant.ErrorCode.SUCCESS);
				result.setResult(true);// 处理成功
				result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
				result.setResult(object);
				logger.info("操作成功");
				return result;
			}
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.ACTIVITY_NOT_EXIST);
			result.setResult(false);// 处理失败
			logger.info("参数错误");
			return result;
		}
	}

	/**
	 * 我的邀请
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMyInvitation.do")
	public AppResponseResult getMyInvitation(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			ActivityDiscountDistribute entity = new ActivityDiscountDistribute();
			String borrowId = request.getParameter("borrowId");// 借款人Id

			logger.info("接收到的参数:borrowId=" + borrowId);
			if (!CommUtils.isNull(borrowId)) {
				entity.setBorrowId(Integer.parseInt(borrowId));
			} else {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
				result.setResult(false);// 处理失败
				logger.info("borrowId不能为空");
				return result;
			}

			// 查询我的邀请
			Object object = activityService.getMyInvitation(entity);
			if (null == object) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
				result.setResult(false);// 处理失败
				logger.info("查询我的邀请失败");
				return result;
			} else {
				result.setCode(ActivityConstant.ErrorCode.SUCCESS);
				result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
				result.setResult(true);// 处理成功
				result.setResult(object);
				logger.info("处理成功");
				return result;
			}
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("参数错误", e);
			return result;
		}

	}

	/**
	 * 查询用户所有劵
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCoupon.do")
	public AppResponseResult getCoupon(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			ActivityDiscountDistribute entity = new ActivityDiscountDistribute();
			String borrowId = request.getParameter("borrowId");// 借款人Id
			String activityType = request.getParameter("activityType");// 活动类型
			logger.info("接收到的参数:borrowId=" + borrowId + ",activityType=" + activityType);
			if (!CommUtils.isNull(borrowId)) {
				entity.setBorrowId(Integer.parseInt(borrowId));
			} else {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("borrowId不能为空");
				result.setResult(false);// 处理失败
				logger.error("borrowId不能为空");
				return result;
			}
			if (!CommUtils.isNull(activityType)) {
				entity.setDistributeType(activityType);
			} else {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("activityType不能为空");
				result.setResult(false);// 处理失败
				logger.error("activityType不能为空");
				return result;
			}
			// 查询用户所有劵
			Object object = activityService.getCoupon(entity);
			if (null == object) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
				result.setResult(false);// 处理失败
				logger.info("查询用户所有劵失败");
				return result;
			} else {
				logger.info("查询用户所有劵：" + object.toString());
				result.setCode(ActivityConstant.ErrorCode.SUCCESS);
				result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
				result.setResult(true);// 处理成功
				result.setResult(object);
				logger.info("处理成功");
				return result;
			}

		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("参数错误", e);
			return result;
		}
	}

	/**
	 * 查询最大能使用券
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCouponMax.do")
	public AppResponseResult getCouponMax(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			ActivityDiscountDistribute entity = new ActivityDiscountDistribute();
			String borrowId = request.getParameter("borrowId");// 借款人Id
			logger.info("接收到的参数:borrowId=" + borrowId);
			if (CommUtils.isNull(borrowId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
				result.setResult(false);// 处理失败
				logger.info("borrowId不能为空");
				return result;
			}
			entity.setBorrowId(Integer.parseInt(borrowId));
			// 查询最大能使用券失败
			Object object = activityService.getMaxCoupon(entity);
			logger.info("----------查询最大能使用券" + object);
			if (null == object) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("没有可以使用的优惠券");
				result.setResult(true);// 处理失败
				logger.info("查询最大能使用券失败");
				return result;
			} else {
				result.setCode(ActivityConstant.ErrorCode.SUCCESS);
				result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
				result.setResult(true);// 处理成功
				result.setResult(object);
				return result;
			}
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("参数错误", e);
			return result;
		}

	}

}
