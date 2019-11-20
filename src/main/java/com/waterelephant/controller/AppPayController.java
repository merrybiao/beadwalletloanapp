package com.waterelephant.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.utils.AppResponseResult;

/**
 * 支付控制层
 * 
 * 
 * Module:
 * 
 * AppPayController.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/pay/")
public class AppPayController {

	private Logger logger = Logger.getLogger(AppPayController.class);

	/**
	 * 扣款，1.还款涉及本金用宝付，2.续贷判断是否连连签约，连连签约走连连，否则走宝付
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("repayment.do")
	public AppResponseResult repayment(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		result.setCode("101");
		result.setMsg("请将App更新到最新版本");
		logger.info("=======================================请将App更新到最新版本");
		return result;
	}

}
