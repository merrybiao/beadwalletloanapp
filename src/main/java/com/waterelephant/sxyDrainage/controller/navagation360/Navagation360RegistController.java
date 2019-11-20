///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller.navagation360;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.beadwallet.service.utils.CommUtils;
//import com.waterelephant.sxyDrainage.entity.navagation360.Navigation360RegistReponse;
//import com.waterelephant.sxyDrainage.service.Navigation360RegistService;
//import com.waterelephant.utils.MyDateUtils;
//
///**
// * 
// * 
// * Module:
// * 
// * Navagation360RegistController.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: qihu360注册通知(qihu360请求我方接口，返回数据)
// */
//@Controller
//public class Navagation360RegistController {
//
//	private Logger logger = Logger.getLogger(Navagation360RegistController.class);
//	private final static String QIHU_CHANNEL = "686";
//
//	@Autowired
//	private Navigation360RegistService navigation360RegistService;
//
//	@RequestMapping("/sxy/qihu360/registerNotify.do")
//	@ResponseBody
//
//	public Navigation360RegistReponse registerNotify(HttpServletRequest request, HttpServletResponse reponse) {
//
//		logger.info("开始进入访问接口");
//		Navigation360RegistReponse navigation360RegistReponse = new Navigation360RegistReponse();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			String startTime = request.getParameter("startTime");
//			String endTime = request.getParameter("endTime");
//			if (CommUtils.isNull(startTime) || CommUtils.isNull(endTime)) {
//				navigation360RegistReponse.setCode("111");
//				navigation360RegistReponse.setMsg("请确定输入的参数是否为空");
//				return navigation360RegistReponse;
//			}
//			Date startTimeDate = sdf.parse(startTime);
//			Date endTimeDate = sdf.parse(endTime);
//			if (MyDateUtils.getDaySpace(startTimeDate, endTimeDate) > 7) {
//				navigation360RegistReponse.setCode("111");
//				navigation360RegistReponse.setMsg("查询间隔时间过大,请重新输入查询时间");
//				return navigation360RegistReponse;
//			}
//
//			Map<String, Object> data = new HashMap<>();
//			data = navigation360RegistService.findBorrower(startTime, endTime, QIHU_CHANNEL);
//			// logger.info("data-->>" + data);
//
//			// logger.info("JSON-->> " + JSONObject.toJSONString(data));
//			navigation360RegistReponse.setData(data);
//			navigation360RegistReponse.setMsg("通知成功");
//			navigation360RegistReponse.setCode("000");
//
//		} catch (Exception e) {
//			navigation360RegistReponse.setCode("111");
//			navigation360RegistReponse.setMsg("注册通知异常");
//			logger.info("qihu360注册通知异常", e);
//		}
//		return navigation360RegistReponse;
//
//	}
//
//}
