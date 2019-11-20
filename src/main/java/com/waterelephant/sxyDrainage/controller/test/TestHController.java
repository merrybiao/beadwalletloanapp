///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller.test;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.waterelephant.sxyDrainage.service.FqgjOperatorService;
//
///**
// * 
// * 
// * Module:
// * 
// * TestController.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Controller
//public class TestHController {
//
//	private Logger logger = Logger.getLogger(TestHController.class);
//
//	@Autowired
//	private FqgjOperatorService fqgjOperatorService;
//
//	@RequestMapping("/test/name.do")
//	public void name(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			String data = request.getParameter("data");
//			String orderId = request.getParameter("orderId");
//			String borrowerId=request.getParameter("borrowerId");
//			fqgjOperatorService.saveOperator(data, Long.valueOf(orderId),Long.valueOf(borrowerId));
//			logger.info("ok");
//
//		} catch (Exception e) {
//			logger.error("出异常了");
//		}
//
//	}
//
//
//}
