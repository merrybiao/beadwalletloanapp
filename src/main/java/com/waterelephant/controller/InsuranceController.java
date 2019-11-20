/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.insuranceApi.InsuranceApiSDK;
import com.waterelephant.utils.AppResponseResult;

/**
 * Module: 
 * InsuranceController.java 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
public class InsuranceController {
	private Logger logger = Logger.getLogger(InsuranceController.class);

	/**
	 * 获取token
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/insuranceApi/getToken.do")
	@ResponseBody
	public AppResponseResult getToken(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			Response<Object> resp = InsuranceApiSDK.getToken();
			String msg = "请求失败";
			if (null != resp) {
				if ("000000".equals(resp.getRequestCode())) {
					appResponseResult.setCode("000");
					appResponseResult.setMsg("success");
					appResponseResult.setResult(resp.getObj() + "");
					logger.info("承保接口：>>>获取token" + JSON.toJSONString(appResponseResult));
					return appResponseResult;
				}
				msg = resp.getRequestMsg();
			}
			appResponseResult.setCode("100");
			appResponseResult.setMsg(msg);
		} catch (Exception e) {
			appResponseResult.setCode("999");
			appResponseResult.setMsg("系统异常");
			logger.error("承保接口：>>>获取token处理异常" + e);
		}
		logger.info("承保接口：>>>获取token" + JSON.toJSONString(appResponseResult));
		return appResponseResult;
	}

	/**
	 * 获取token
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/insuranceApi/createInsuranceOrder.do")
	@ResponseBody
	public AppResponseResult createInsuranceOrder(HttpServletRequest request) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			String insuranceOrderInfo = request.getParameter("insuranceOrderInfo");
			if (StringUtils.isBlank(insuranceOrderInfo)) {
				appResponseResult.setCode("100");
				appResponseResult.setMsg("请求参数为空");
				return appResponseResult;
			}
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("insuranceOrderInfo", insuranceOrderInfo);
			Response<Object> resp = InsuranceApiSDK.createInsuranceOrder(paramMap);
			String msg = "请求失败";
			if (null != resp) {
				if ("000000".equals(resp.getRequestCode())) {
					appResponseResult.setCode("000");
					appResponseResult.setMsg("success");
					appResponseResult.setResult(resp.getObj() + "");
					logger.info("承保接口：>>> 创建保单" + JSON.toJSONString(appResponseResult));
					return appResponseResult;
				}
				msg = resp.getRequestMsg();
			}
			appResponseResult.setCode("100");
			appResponseResult.setMsg(msg);
		} catch (Exception e) {
			appResponseResult.setCode("999");
			appResponseResult.setMsg("系统异常");
			logger.error("承保接口：>>> 创建保单异常" + e);
		}
		logger.info("承保接口：>>> 创建保单" + JSON.toJSONString(appResponseResult));
		return appResponseResult;
	}
}
