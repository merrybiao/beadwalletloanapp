/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.annotation.LockAndSyncRequest;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.installment.service.AppBorrowService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * 
 * 
 * Module:
 * 
 * AppBorrowController.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: 借款controller
 */
@Controller
@RequestMapping("/app/borrow/")
public class AppBorrowController {
	private Logger logger = Logger.getLogger(AppBorrowController.class);
	@Autowired
	private AppBorrowService appBorrowService;

	/**
	 * 我要借款-->提交我要借款信息
	 * 
	 * @return
	 */
	@RequestMapping("/appCheckLogin/commitBorrowInfo.do")
	@ResponseBody
	@LockAndSyncRequest
	public AppResponseResult commitBorrowInfo(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
			Integer channel = (Integer) request.getSession().getAttribute(SystemConstant.ORDER_CHENNEL_TOKEN);
			if (channel == null) {
				channel = NumberUtil.parseInteger(request.getParameter("channel"), null);
			}
			paramMap.put("channel", channel != null ? channel.toString() : null);
			logger.info("【AppBorrowController.commitBorrowInfo】borrowerId：" + request.getParameter("borrowerId")
					+ "，我要借款-->提交我要借款信息请求参数：" + paramMap);
			result = appBorrowService.updateAndCommitBorrowInfo(paramMap);
		} catch (Exception e) {
			result = new AppResponseResult();
			result.setCode("111");
			result.setMsg("系统异常");
			logger.error("【AppBorrowController.commitBorrowInfo】系统异常", e);
		}
		return result;
	}

	/**
	 * 确认拿钱页面信息
	 * 
	 * @return
	 */
	@RequestMapping("/appCheckLogin/getBorrowInfo.do")
	@ResponseBody
	public AppResponseResult getBorrowInfo(HttpServletRequest request) {
		AppResponseResult result = null;
		try {
			Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
			logger.info("【AppBorrowController.getBorrowInfo】orderId：" + request.getParameter("orderId")
					+ "，确认拿钱页面信息查询请求参数paramMap：" + paramMap);
			result = appBorrowService.queryBorrowInfo(paramMap);
		} catch (Exception e) {
			result = new AppResponseResult();
			result.setCode("111");
			result.setMsg("系统异常");
			logger.error("【AppBorrowController.getBorrowInfo】系统异常", e);
		}
		return result;
	}

	/**
	 * 确认点击仲裁提示
	 * 
	 * @return
	 */
	@RequestMapping("/appCheckLogin/conformClick.do")
	@ResponseBody
	public AppResponseResult conformClick(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");
			logger.info("【AppBorrowController.conformClick】orderId：" + StringUtil.toString(orderId));
			if (StringUtil.isEmpty(orderId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("orderId不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			RedisUtils.hset("arbitration:isFirst", orderId, DateUtil.getCurrentDateString(DateUtil.yyyy_MM_dd_HHmmss));
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg("");
			result.setResult(true);
		} catch (Exception e) {
			result = new AppResponseResult();
			result.setCode("111");
			result.setMsg("系统异常");
			logger.error("【AppBorrowController.conformClick】系统异常", e);
		}
		return result;
	}

}