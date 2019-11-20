/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.drainage.entity.xianJinCard.XianJinCardBindCardRequest;
import com.waterelephant.drainage.entity.xianJinCard.XianJinCardCommonRequest;
import com.waterelephant.drainage.entity.xianJinCard.XianJinCardRequest;
import com.waterelephant.drainage.entity.xianJinCard.XianJinCardResponse;
import com.waterelephant.drainage.service.XianJinCardService;
import com.waterelephant.drainage.util.xianjincard.XianJinCardConstant;
import com.waterelephant.drainage.util.xianjincard.XianJinCardUtils;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.MD5Util;
import com.waterelephant.utils.RedisUtils;

/**
 * 
 * Module:现金白卡 接口
 * 
 * XianJinCardController.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
public class XianJinCardController {
	private Logger logger = LoggerFactory.getLogger(XianJinCardController.class);
	@Autowired
	private XianJinCardService xianJinCardService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwOrderRongService bwOrderRongService;

	@ResponseBody
	@RequestMapping("/third/xianjincard.do")
	public XianJinCardResponse xianjincard(XianJinCardRequest xianjinCardRequest) {
		long sessionId = System.currentTimeMillis();
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		try {
			logger.info(sessionId + "：开始xianjincard method：");
			// 获取业务参数
			String args = xianjinCardRequest.getArgs();
			String call = xianjinCardRequest.getCall();
			XianJinCardCommonRequest xianJinCardCommonRequest = JSON.parseObject(args, XianJinCardCommonRequest.class);
			if (xianJinCardCommonRequest == null || StringUtils.isBlank(call)) {
				xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
				xianJinCardResponse.setMessage("入参不合法");
				logger.info(sessionId + "：结束xianjincard：" + JSON.toJSONString(xianJinCardResponse));
				return xianJinCardResponse;
			}
			boolean flag = checkSign(sessionId, xianjinCardRequest);
			if (!flag) {
				xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
				xianJinCardResponse.setMessage("sign错误");
				logger.info(sessionId + "：结束xianjincard：" + JSON.toJSONString(xianJinCardResponse));
				return xianJinCardResponse;
			}
			// 处理业务
			if ("User.isUserAccept".equals(call)) {
				// 用户过滤
				xianJinCardResponse = xianJinCardService.checkUser(sessionId, xianJinCardCommonRequest);
			} else if ("Order.pushOrderInfo".equals(call)) {
				// 推送订单信息
				xianJinCardResponse = xianJinCardService.savePushOrder(sessionId, xianJinCardCommonRequest);
			} else if ("BindCard.getUserBindBankCardList".equals(call)) {
				// 获取已经绑定银行卡列表
				xianJinCardResponse = xianJinCardService.getBindCard(sessionId, xianJinCardCommonRequest);
			} else if ("Order.confirmLoan".equals(call)) {
				// 审批签约
				xianJinCardResponse = xianJinCardService.updateSignContract(sessionId, xianJinCardCommonRequest);
			} else if ("Order.getRepayplan".equals(call)) {
				// 获取还款计划
				xianJinCardResponse = xianJinCardService.getRepayplan(sessionId, xianJinCardCommonRequest);
			} else if ("Order.getOrderStatus".equals(call)) {
				// 获取订单状态
				xianJinCardResponse = xianJinCardService.getOrderStatus(sessionId, xianJinCardCommonRequest);
			} else if ("Order.loanCalculate".equals(call)) {
				// 试算接口
				xianJinCardResponse = xianJinCardService.loanCalculate(sessionId, xianJinCardCommonRequest);
			} else if ("Order.getContracts".equals(call)) {
				// 合同接口
				xianJinCardResponse = xianJinCardService.getContracts(sessionId, xianJinCardCommonRequest);
			} else {
				xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
				xianJinCardResponse.setMessage("请求失败");
			}
		} catch (Exception e) {
			logger.error(sessionId + "：执行xianjincard method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
		}
		logger.info(sessionId + "：结束xianjincard：" + JSON.toJSONString(xianJinCardResponse));
		return xianJinCardResponse;
	}

	/**
	 * 绑卡处理
	 * 
	 * @param xianjinCardRequest
	 * @return
	 */
	@RequestMapping("/third/xianjincard/bindcard.do")
	public String bindcard(XianJinCardBindCardRequest xianJinCardBindCardRequest) {
		long sessionId = System.currentTimeMillis();
		try {
			logger.info(sessionId + "：开始bindcard method" + xianJinCardBindCardRequest.toString());
			// 获取业务参数
			Map<String, String> treeMap = new TreeMap<>();

			if (null != xianJinCardBindCardRequest.getOrder_sn()) {
				treeMap.put("order_sn", xianJinCardBindCardRequest.getOrder_sn());
			}
			if (null != xianJinCardBindCardRequest.getUser_name()) {
				treeMap.put("user_name", xianJinCardBindCardRequest.getUser_name());
			}
			if (null != xianJinCardBindCardRequest.getUser_phone()) {
				treeMap.put("user_phone", xianJinCardBindCardRequest.getUser_phone());
			}
			if (null != xianJinCardBindCardRequest.getUser_idcard()) {
				treeMap.put("user_idcard", xianJinCardBindCardRequest.getUser_idcard());
			}
			if (null != xianJinCardBindCardRequest.getEcho_data()) {
				treeMap.put("echo_data", xianJinCardBindCardRequest.getEcho_data());
			}
			if (null != xianJinCardBindCardRequest.getLast_card_number()) {
				treeMap.put("last_card_number", xianJinCardBindCardRequest.getLast_card_number());
			}
			if (null != xianJinCardBindCardRequest.getLast_card_phone()) {
				treeMap.put("last_card_phone", xianJinCardBindCardRequest.getLast_card_phone());
			}
			if (null != xianJinCardBindCardRequest.getReturn_url()) {
				treeMap.put("return_url", xianJinCardBindCardRequest.getReturn_url());
			}
			if (null != xianJinCardBindCardRequest.getSign()) {
				treeMap.put("sign", xianJinCardBindCardRequest.getSign());
			}
			logger.info(sessionId + "：执行bindcard method:" + treeMap.toString());
			// 验签
			if (null == treeMap || !CheckH5Sign(sessionId, treeMap)) {
				return "sign_fail_third";// 入参
			}
			// 处理业务
			Map<String, String> result = xianJinCardService.saveBindCard(sessionId, treeMap);
			logger.info(sessionId + "：结束bindcard：");
			if ("1".equals(result.get("resCode"))) {
				return "redirect:" + result.get("resMsg"); // 银行卡信息填写URL
			} else {
				logger.info(sessionId + "：结束bindcard：");
				return "sign_fail_third";
			}
		} catch (Exception e) {
			logger.error(sessionId + "：执行bindcard method 异常：", e);
			logger.info(sessionId + "：结束bindcard：");
			return "sign_fail_third";
		}
	}

	@RequestMapping("/third/xianjincard/bindcardDirect.do")
	public String bindcardDirect(HttpServletRequest request, HttpServletResponse response) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始bindcardDirect method");
		try {
			// 第一步：请求参数
			String orderNO = request.getParameter("orderNO"); // 订单号
			String channelId = request.getParameter("channelId"); // 渠道ID
			String echo_data = request.getParameter("echo_data"); // echo_data

			// 第二步：成功/失败跳转URL
			String successReturnUrl = RedisUtils.hget("third:bindCard:successReturnUrl:" + channelId,
					"orderNO_" + orderNO); // 成功后的跳转URL
			if (!CommUtils.isNull(successReturnUrl)) {
				successReturnUrl = "redirect:" + successReturnUrl;
				RedisUtils.hdel("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + orderNO);
			} else {
				successReturnUrl = "sign_success_third";
			}

			String failReturnUrl = RedisUtils.hget("third:bindCard:failReturnUrl:" + channelId, "orderNO_" + orderNO);
			if (!CommUtils.isNull(failReturnUrl)) {
				failReturnUrl = "redirect:" + failReturnUrl;
				RedisUtils.hdel("third:bindCard:failReturnUrl:" + channelId, "orderNO_" + orderNO);
			} else {
				failReturnUrl = "sign_fail_third";
			}

			// 第三步：判断并跳转
			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNO);
			Map<String, String> treeMap = new TreeMap<>();
			if (null != bwOrder) {
				BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
				BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(bwOrder.getBorrowerId());
				BwOrderRong bwOrderRong = bwOrderRongService.findBwOrderRongByOrderId(bwOrder.getId());
				if (null != bwOrderRong) {
					treeMap.put("order_sn", bwOrderRong.getThirdOrderNo());// 借款订单唯一编号, 推单前绑卡则为空
				}
				treeMap.put("user_name", bwBorrower.getName());// 用户真实姓名
				treeMap.put("user_phone", bwBorrower.getPhone());// 用户手机号
				treeMap.put("user_idcard", bwBorrower.getIdCard());// 用户身份证号码
				treeMap.put("echo_data", echo_data);// 回显数据字段, 此字段的数据来源于机构绑卡跳转地址的请求参数中，原样回传即可
				if (!CommUtils.isNull(bwBankCard) && 2 == bwBankCard.getSignStatus()) {
					treeMap.put("bind_status", "200");// 绑卡结果状态; 200 成功; 505 失败
					treeMap.put("bank_code", XianJinCardUtils.convertToBankCode(bwBankCard.getBankName()));// 绑卡银行编码
					treeMap.put("card_number", bwBankCard.getCardNo());// 银行卡号
					treeMap.put("card_phone", bwBankCard.getPhone());// 银行预留手机号
					String sign = getH5Sign(sessionId, treeMap);
					treeMap.put("sign", sign);// 签名
					logger.info(sessionId + "bindcardDirect>>URL:" + successReturnUrl + "?"
							+ toResParams(sessionId, treeMap));
					return successReturnUrl + "?" + toResParams(sessionId, treeMap);
				}
				treeMap.put("bind_status", "505");
				String sign = getH5Sign(sessionId, treeMap);
				treeMap.put("sign", sign);// 签名
				logger.info(sessionId + "bindcardDirect>>URL:" + failReturnUrl + "?" + toResParams(sessionId, treeMap));
				return failReturnUrl + "?" + toResParams(sessionId, treeMap);
			}
		} catch (Exception e) {
			logger.error(sessionId + "：执行bindcardDirect method 异常：", e);
			logger.info(sessionId + "：结束bindcardDirect：");
		}
		return "sign_fail_third";
	}

	/*********************************************************************************************************/
	// API 验签
	private boolean checkSign(long sessionId, XianJinCardRequest xianjinCardRequest) {
		try {
			ResourceBundle xianjinCard = ResourceBundle.getBundle("xianjincard"); // 配置常量
			if (xianjinCard == null) {
				logger.info(sessionId + "：结束XianJinCardController.getSign：" + xianjinCard);
				return false;
			}
			String call = xianjinCardRequest.getCall();
			String args = xianjinCardRequest.getArgs();
			String ua = XianJinCardConstant.UA_REQUEST;
			String signkey = XianJinCardConstant.SIGNKEY_REQUEST;
			String key = ua + signkey + ua;
			String sign = "";
			sign = MD5Util.md5(key + call + key + args + key);
			if (sign.equals(xianjinCardRequest.getSign())) {
				return true;
			}
		} catch (Exception e) {
			logger.error(sessionId + "：结束XianJinCardController.getSign：" + xianjinCardRequest.toString());
		}
		return false;
	}

	// H5验签
	private boolean CheckH5Sign(long sessionId, Map<String, String> treeMap) {
		try {
			String sign = treeMap.get("sign");
			treeMap.remove("sign");
			String str = "";
			if (null != treeMap) {
				for (Entry<String, String> string : treeMap.entrySet()) {
					if (StringUtils.isBlank(str)) {
						str = string.getKey() + string.getValue();
					} else {
						str += string.getKey() + string.getValue();
					}
				}
			}
			String ua = XianJinCardConstant.UA_REQUEST;
			String signkey = XianJinCardConstant.SIGNKEY_REQUEST;
			String key = ua + signkey + ua;
			String sign_ = MD5Util.md5(key + str + key);
			if (sign_.equals(sign)) {
				return true;
			}
		} catch (Exception e) {
			logger.error(sessionId + "：结束XianJinCardController.CheckH5Sign：");
		}
		return false;
	}

	// H5 生成签名
	private String getH5Sign(long sessionId, Map<String, String> treeMap) {
		String sign = "";
		try {
			String str = "";
			if (null != treeMap) {
				for (Entry<String, String> string : treeMap.entrySet()) {
					if (StringUtils.isBlank(str)) {
						str = string.getKey() + string.getValue();
					} else {
						str += string.getKey() + string.getValue();
					}
				}
			}
			String ua = XianJinCardConstant.UA_RESPONSE;
			String signkey = XianJinCardConstant.SIGNKEY_RESPONSE;
			String key = ua + signkey + ua;
			sign = MD5Util.md5(key + str + key);
		} catch (Exception e) {
			logger.error(sessionId + "：结束XianJinCardController.getH5Sign：");
		}
		return sign;
	}

	//
	private String toResParams(long sessionId, Map<String, String> treeMap) {
		String str = "";
		try {
			if (null != treeMap) {
				for (Entry<String, String> string : treeMap.entrySet()) {
					if (StringUtils.isBlank(str)) {
						str = string.getKey() + "=" + string.getValue();
					} else {
						str += "&" + string.getKey() + "=" + string.getValue();
					}
				}
			}
		} catch (Exception e) {
			logger.error(sessionId + "：结束XianJinCardController.toResParams：");
		}
		return str;
	}
}
