/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.installment.service.InstallmentService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;

/**
 * 分期信息控制器
 * 
 * Module:
 * 
 * InstallmentInfoController.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/app/installmentInfo")
public class InstallmentInfoController {

	private Logger logger = Logger.getLogger(OrderController.class);

	@Resource
	private InstallmentService installmentService;

	/**
	 * 查询分期信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInstallementInfo.do")
	public AppResponseResult getInstallementInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			// 借款人ID
			String borrowerId = request.getParameter("borrowerId");
			// 产品类型
			String productType = request.getParameter("productType");
			logger.info("【InstallmentInfoController.getInstallementInfo】borrowerId：" + borrowerId + "，productType："
					+ productType);
			if (CommUtils.isNull(borrowerId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("borrowerId不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			if (CommUtils.isNull(productType)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("productType不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			// 查询分期信息
			Map<String, Object> map = installmentService.getInstallementInfo(borrowerId, productType);
			logger.info("【InstallmentInfoController.getInstallementInfo】borrowerId：" + borrowerId + "，productType："
					+ productType + "，分期信息map：" + map);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			result.setResult(map);
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("系统异常", e);
			return result;
		}
	}

	/**
	 * 查询借款记录-现金分期-分期信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInstallmentList.do")
	public AppResponseResult getInstallmentList(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			// 工单id
			String orderId = request.getParameter("orderId");
			logger.info("[getInstallmentList接口]接收到的参数:orderId:" + orderId);
			if (CommUtils.isNull(orderId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("工单id不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			// 查询分期工单
			List<Map<String, Object>> list = installmentService.getInstallmentList(Long.parseLong(orderId));
			logger.info("[installmentService.getInstallmentList]查询orderId " + orderId + " 查询出的工单分期为 ： " + list);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			result.setResult(list);
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("系统异常", e);
			return result;
		}
	}

}
