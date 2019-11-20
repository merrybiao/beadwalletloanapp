/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.rongtaobao.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.rong360.service.BeadWalletRongCarrierService;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.rongtaobao.entity.TaoBaoReturn;
import com.waterelephant.rongtaobao.service.RongTaoBaoService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;

/**
 * 融 淘宝
 *
 * @author 郭坤
 * @date 2017年6月6日
 */
@Controller
@RequestMapping("/app/rong")
public class RongTaoBaoController {

	private Logger logger = Logger.getLogger(RongTaoBaoController.class);

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private RongTaoBaoService rongTaoBaoService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;

	/**
	 * 郭坤
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/taobao/backH5.do")
	public AppResponseResult operateBackH5(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult rDto = new AppResponseResult();
		final String status_id = request.getParameter("status_id");
		final String module = request.getParameter("module");
		final String status = request.getParameter("status");
		final String session = request.getParameter("session");
		final String notice_attached_param = request.getParameter("notice_attached_param");
		if (CommUtils.isNull(notice_attached_param)) {
			rDto.setCode("103");
			rDto.setMsg("数据有误");
			return rDto;
		}

		try {
			JSONObject jsonObject = JSON.parseObject(notice_attached_param);
			if (CommUtils.isNull(jsonObject)) {
				rDto.setCode("103");
				rDto.setMsg("数据有误");
				return rDto;
			}
			String userId = jsonObject.getString("userId");
			String orderId = jsonObject.getString("orderId").split("_")[0];

			String authChannel = jsonObject.getString("authChannel");
			logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~融360淘宝回调认证记录：status_id：" + status_id + ",module：" + module
					+ ",status:" + status + ",session:  " + session + "；  notice_attached_param: "
					+ notice_attached_param + "userId: " + userId + ",orderId:" + orderId);

			// orderId = orderId.split("_")[0];

			if (CommUtils.isNull(status)) {
				rDto.setCode("101");
				rDto.setMsg("session不可为空");
				return rDto;
			}
			if ("2".equals(status) || "4".equals(status)) {
				Thread task = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Map map = new HashMap();
							map.put("userId", session);
							String returnSdk = BeadWalletRongCarrierService.rongTaoBaoSaas(map);
							// logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~ 淘宝返回结果：" + returnSdk);
							JSONObject jsonObject1 = JSON.parseObject(returnSdk);
							String error = jsonObject1.getString("error");
							if ("200".equals(error)) {
								JSONObject wd_api_taobao_getData_response = jsonObject1
										.getJSONObject("wd_api_taobao_getData_response");
								String data = wd_api_taobao_getData_response.getString("data");
								TaoBaoReturn opReport = JSONObject.parseObject(data, TaoBaoReturn.class);
								// userId为null报异常,修改如下
								if (!CommUtils.isNull(userId)) {
									rongTaoBaoService.saveTaoBaoData(opReport, Long.parseLong(userId),
											Long.parseLong(orderId), authChannel);
								} else {
									rongTaoBaoService.saveTaoBaoData(opReport, null, Long.parseLong(orderId),
											authChannel);
								}
							}
						} catch (Exception e) {
							logger.error(e);
						}
					}
				});
				taskExecutor.execute(task);
				rDto.setCode("200");
				rDto.setMsg("成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rDto.setCode("101");
			rDto.setMsg("系统异常");
		}
		return rDto;
	}

	/**
	 * 郭坤
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/taobao/authenticationTaoBao.do")
	public AppResponseResult editOrder(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		Map map = new HashMap();
		String orderId = request.getParameter("orderId");
		String authChannel = request.getParameter("authChannel");
		if (CommUtils.isNull(orderId)) {
			appResponseResult.setCode("101");
			appResponseResult.setMsg("orderId不可为空");
			return appResponseResult;
		}
		if (CommUtils.isNull(authChannel)) {
			appResponseResult.setCode("102");
			appResponseResult.setMsg("authChannel不可为空");
			return appResponseResult;
		}
		try {
			BwOrderAuth bwOrderAuthF = bwOrderAuthService.findBwOrderAuth(Long.parseLong(orderId), 8);
			if (bwOrderAuthF != null) {
				appResponseResult.setCode("000");
				appResponseResult.setMsg("添加成功");
				return appResponseResult;
			}
			BwOrderAuth bwOrderAuth = new BwOrderAuth();
			bwOrderAuth.setOrderId(Long.parseLong(orderId));
			bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
			bwOrderAuth.setAuth_type(8);
			bwOrderAuth.setCreateTime(new Date());
			bwOrderAuth.setUpdateTime(new Date());
			bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
		} catch (Exception e) {
			logger.error("~~~~~~~~~~~~~~~~~~~添加订单认证失败， orderId：" + orderId);
			appResponseResult.setCode("103");
			appResponseResult.setMsg("添加淘宝认证记录失败，请重新提交");
			return appResponseResult;
		}
		appResponseResult.setCode("000");
		appResponseResult.setMsg("添加成功");
		return appResponseResult;
	}

	/**
	 * 郭坤
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/taobao/test.do")
	public AppResponseResult test(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		Map map = new HashMap();
		String userId = request.getParameter("userId");
		String orderId = request.getParameter("orderId");
		map.put("userId", userId);
		String result = BeadWalletRongCarrierService.rongTaoBao(map);
		// logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~ 淘宝返回结果：" + result);
		JSONObject jsonObject1 = JSON.parseObject(result);
		String error = jsonObject1.getString("error");
		if ("200".equals(error)) {
			try {
				JSONObject wd_api_taobao_getData_response = jsonObject1.getJSONObject("wd_api_taobao_getData_response");
				String data = wd_api_taobao_getData_response.getString("data");
				TaoBaoReturn opReport = JSONObject.parseObject(data, TaoBaoReturn.class);
				rongTaoBaoService.saveTaoBaoData(opReport, 12L, Long.parseLong(orderId), "2");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		appResponseResult.setResult(result);
		return appResponseResult;
	}

}