/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.drainage.entity.haoxiangdai.HaoXiangDaiRequest;
import com.waterelephant.drainage.entity.haoxiangdai.HaoXiangDaiResponse;
import com.waterelephant.drainage.service.HaoXiangDaiService;
import com.waterelephant.third.entity.ThirdResponse;

/**
 * Module: 
 * HaoXiangDaiController.java 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
public class HaoXiangDaiController {
	private Logger logger = LoggerFactory.getLogger(HaoXiangDaiController.class);
	@Autowired
	private HaoXiangDaiService haoXiangDaiService;
	
	/**
	 * 好享贷--进件接口
	 * 
	 */
	@ResponseBody
	@RequestMapping("/third/cloud/interface/pushUser.do")
	public HaoXiangDaiResponse pushUser(HaoXiangDaiRequest haoXiangDaiRequest) {
		long sessionId = System.currentTimeMillis();
		HaoXiangDaiResponse haoXiangDaiResponse = new HaoXiangDaiResponse();
		logger.info(sessionId + "：开始controller层进件接口");
		try {
			// 检查参数
			if (haoXiangDaiRequest == null) {
				haoXiangDaiResponse.setCode(ThirdResponse.CODE_PARAMETER);
				haoXiangDaiResponse.setMsg("请求参数为空");
				logger.info(sessionId + "：结束controller层进件接口：" + JSON.toJSONString(haoXiangDaiResponse));
				return haoXiangDaiResponse;
			}
			String request = haoXiangDaiRequest.getRequest();
			String appId = haoXiangDaiRequest.getAppId();
			String sign = haoXiangDaiRequest.getSign();
			if (StringUtils.isBlank(request) || StringUtils.isBlank(appId) || StringUtils.isBlank(sign)) {
				haoXiangDaiResponse.setCode(ThirdResponse.CODE_PARAMETER);
				haoXiangDaiResponse.setMsg("请求参数为空");
				logger.info(sessionId + "结束水象云进件接口" + JSON.toJSONString(haoXiangDaiResponse));
				return haoXiangDaiResponse;
			}
			haoXiangDaiResponse = haoXiangDaiService.savePushUser(sessionId, haoXiangDaiRequest);
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层进件接口异常:", e);
			haoXiangDaiResponse.setCode(ThirdResponse.CODE_NETERROR);
			haoXiangDaiResponse.setMsg("系统异常");
		}
		logger.info(sessionId + "结束controller层进件接口：" + JSON.toJSONString(haoXiangDaiResponse));
		return haoXiangDaiResponse;
	}
}
