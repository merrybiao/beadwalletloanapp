///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.BindCardRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.CheckUserRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.CheckUserRequestNew;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.ConfirmOrderRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RtyResponse;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RtyResponseNew;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RytPushOrderVo;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RytRequest;
//import com.waterelephant.sxyDrainage.service.RongYiTuiService;
//import com.waterelephant.sxyDrainage.utils.rongYiTuiUtil.DecryptUtil;
//import com.waterelephant.sxyDrainage.utils.rongYiTuiUtil.RongYiTuiConstant;
//import com.waterelephant.utils.MD5Util;
//
///**
// * Module: 
// * RongyituiController.java 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Controller
//public class RongYiTuiController {
//	private Logger logger = LoggerFactory.getLogger(RongYiTuiController.class);
//	@Autowired
//	private RongYiTuiService rongYiTuiService;
//
//	/**
//	 * 用户过滤接口 单推手机号码
//	 * 
//	 * @param CheckUserRequestNew
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/ryt/checkUser.do")
//	public RtyResponseNew CheckUserNew(@RequestBody CheckUserRequestNew checkUserRequestNew, HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponseNew rtyResponseNew = new RtyResponseNew();
//		logger.info(sessionId + ">开始融易推 过滤接口(新)：>>>>" + JSON.toJSONString(checkUserRequestNew));
//		String token = request.getHeader("token");
//		try {
//			if (null == checkUserRequestNew) {
//				rtyResponseNew.setStatus("500");
//				rtyResponseNew.setMsg("请求参数为空");
//			} else if (StringUtils.isBlank(checkUserRequestNew.getPhone())) {
//				rtyResponseNew.setStatus("500");
//				rtyResponseNew.setMsg("手机号码为空");
//			} else if (StringUtils.isBlank(checkUserRequestNew.getRegister())) {
//				rtyResponseNew.setStatus("500");
//				rtyResponseNew.setMsg("请求来源为空");
//			} else if (StringUtils.isBlank(checkUserRequestNew.getSign())) {
//				rtyResponseNew.setStatus("500");
//				rtyResponseNew.setMsg("签名为空");
//			} else if (StringUtils.isBlank(checkUserRequestNew.getEncode())
//					|| !"1".equals(checkUserRequestNew.getEncode())) {
//				rtyResponseNew.setStatus("500");
//				rtyResponseNew.setMsg("failure");
//			} else {
//				String phone = checkUserRequestNew.getPhone();
//				String id_card = checkUserRequestNew.getCard_id();
//				String name = checkUserRequestNew.getName();
//				String sign_ = "";
//				DecryptUtil des = new DecryptUtil(RongYiTuiConstant.RYT_PWD);
//				phone = des.decrypt(phone);
//				if (StringUtils.isBlank(id_card) && StringUtils.isBlank(name)) {
//					sign_ = DigestUtils.sha1Hex(phone + RongYiTuiConstant.RYT_KEY);
//				} else {
//					id_card = des.decrypt(phone);
//					name = des.decrypt(phone);
//					sign_ = DigestUtils.sha1Hex(phone + id_card + name + RongYiTuiConstant.RYT_KEY);
//				}
//				logger.info(sessionId + ">融易推 过滤接口(新)：>>>>" + sign_);
//				if (!(sign_).equals(checkUserRequestNew.getSign()) || !(regex(phone))) {
//					rtyResponseNew.setStatus("500");
//					rtyResponseNew.setMsg("failure");
//				} else {
//					rtyResponseNew = rongYiTuiService.addCheckUser(sessionId, phone, id_card, name);
//				}
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 过滤接口(新)：>>>> 异常：", e);
//			rtyResponseNew.setStatus("500");
//			rtyResponseNew.setMsg("请求异常");
//		}
//		rtyResponseNew.setToken(token);
//		logger.info(sessionId + ">结束融易推 过滤接口(新)：>>>>" + JSON.toJSONString(rtyResponseNew));
//		return rtyResponseNew;
//	}
//
//	/**
//	 * 用户过滤接口
//	 * 
//	 * @param checkUserRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/checkUser.do")
//	public RtyResponse checkUser(@RequestBody CheckUserRequest checkUserRequest) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 过滤接口：>>>>");
//		try {
////			String ip = request.getHeader("x-forwarded-for");
////			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
////				ip = request.getHeader("Proxy-Client-IP");
////			}
////			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
////				ip = request.getHeader("WL-Proxy-Client-IP");
////			}
////			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
////				ip = request.getRemoteAddr();
////			}
////			System.out.println(ip);
//			 if (null == checkUserRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				rtyResponse = rongYiTuiService.checkUser(sessionId, checkUserRequest);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 过滤接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推 过滤接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	/**
//	 * 用户信息推送接口
//	 * 
//	 * @param rytPushOrderVo
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/pushOrder.do")
//	public RtyResponse pushOrder(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 推单接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
////			System.out.println(params);
//			RytPushOrderVo rytPushOrderVo = JSON.parseObject(params, RytPushOrderVo.class);
//			if (null == rytPushOrderVo) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				rtyResponse = rongYiTuiService.savePushOrder(sessionId, rytPushOrderVo);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 推单接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推  推单接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	/**
//	 * 
//	 * 获取订单状态
//	 * 
//	 * @param rytRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/getOrderStatus.do")
//	// public RtyResponse getOrderStatus(@RequestBody RytRequest rytRequest) {
//	public RtyResponse getOrderStatus(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 获取订单状态接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//			System.out.println(params);
//			RytRequest rytRequest = JSON.parseObject(params, RytRequest.class);
//			if (null == rytRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				rtyResponse = rongYiTuiService.getOrderStatus(sessionId, rytRequest);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 获取订单状态接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推  获取订单状态接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	/**
//	 * 获取审核状态接口
//	 * 
//	 * @param rytRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/getApprovalResult.do")
//	// public RtyResponse getApprovalResult(@RequestBody RytRequest rytRequest) {
//	public RtyResponse getApprovalResult(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 获取订单状态接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
////			System.out.println(params);
//			RytRequest rytRequest = JSON.parseObject(params, RytRequest.class);
//			if (null == rytRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				rtyResponse = rongYiTuiService.getApprovalResult(sessionId, rytRequest);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 获取订单状态接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推  获取订单状态接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	/**
//	 * 绑卡接口
//	 * 
//	 * @param bindCardRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/bindCard.do")
//	// public RtyResponse bindCard(@RequestBody BindCardRequest bindCardRequest) {
//	public RtyResponse bindCard(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 绑卡接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
////			System.out.println(params);
//			BindCardRequest bindCardRequest = JSON.parseObject(params, BindCardRequest.class);
//			if (null == bindCardRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				rtyResponse = rongYiTuiService.savesBindCard(sessionId, bindCardRequest);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 绑卡接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推  绑卡接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//	
//	/**
//	 * 绑卡接口
//	 * 
//	 * @param bindCardRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/ryt/bindCardReady.do")
//	// public RtyResponse bindCard(@RequestBody BindCardRequest bindCardRequest) {
//	public RtyResponse bindCard_Ready(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 绑卡接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//			// System.out.println(params);
//			BindCardRequest bindCardRequest = JSON.parseObject(params, BindCardRequest.class);
//			if (null == bindCardRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				String sign = bindCardRequest.getSign();
//				String sign_ = MD5Util.md5(bindCardRequest.getOrder_no() + "||" + bindCardRequest.getCard_no());
//				logger.info(sessionId + ">融易推:>>>>" + JSON.toJSONString(bindCardRequest));
//				if (!sign.equals(sign_)) {
//					rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//					rtyResponse.setMsg("验签失败");
//				} else {
//					rtyResponse = rongYiTuiService.savesBindCardReady(sessionId, bindCardRequest);
//				}
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 绑卡接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推  绑卡接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	/**
//	 * 绑卡接口
//	 * 
//	 * @param bindCardRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/ryt/bindCardSure.do")
//	// public RtyResponse bindCard(@RequestBody BindCardRequest bindCardRequest) {
//	public RtyResponse bindCard_sure(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 绑卡接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//			// System.out.println(params);
//			BindCardRequest bindCardRequest = JSON.parseObject(params, BindCardRequest.class);
//			if (null == bindCardRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				String sign = bindCardRequest.getSign();
//				String sign_ = MD5Util.md5(bindCardRequest.getOrder_no() + "||" + bindCardRequest.getVerifyCode());
//				logger.info(sessionId + ">融易推:>>>>" + JSON.toJSONString(bindCardRequest));
//				if (!sign.equals(sign_)) {
//					rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//					rtyResponse.setMsg("验签失败");
//				} else {
//					rtyResponse = rongYiTuiService.savesBindCardSure(sessionId, bindCardRequest);
//				}
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 绑卡接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推  绑卡接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	/**
//	 * 确认贷款接口
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/confirmOrder.do")
//	// public RtyResponse confirmOrder(@RequestBody ConfirmOrderRequest confirmOrderRequest) {
//	public RtyResponse confirmOrder(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 贷款确认接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
////			System.out.println(params);
//			ConfirmOrderRequest confirmOrderRequest = JSON.parseObject(params, ConfirmOrderRequest.class);
//			if (null == confirmOrderRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				rtyResponse = rongYiTuiService.updateConfirmOrder(sessionId, confirmOrderRequest);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 贷款确认接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推 贷款确认接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	// /**
//	// * 提现接口 // 签约接口
//	// *
//	// * @param signContractRequest
//	// * @return
//	// */
//	// @ResponseBody
//	// @RequestMapping("/cloud/third/SignContract.do")
//	// // public RtyResponse updateSignContract(@RequestBody RytRequest rytRequest) {
//	// public RtyResponse updateSignContract(HttpServletRequest request) {
//	// long sessionId = System.currentTimeMillis();
//	// RtyResponse rtyResponse = new RtyResponse();
//	// logger.info(sessionId + ">开始融易推 提现接口：>>>>");
//	// try {
//	// String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//	// System.out.println(params);
//	// RytRequest rytRequest = JSON.parseObject(params, RytRequest.class);
//	// if (null == rytRequest) {
//	// rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//	// rtyResponse.setMsg("请求参数为空");
//	// } else {
//	// rtyResponse = rongYiTuiService.updateSignContract(sessionId, rytRequest);
//	// }
//	// } catch (Exception e) {
//	// logger.error(sessionId + ">结束融易推 提现接口：>>>> 异常：", e);
//	// rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//	// rtyResponse.setMsg("请求失败");
//	// }
//	// logger.info(sessionId + ">结束融易推 提现接口：>>>>" + JSON.toJSONString(rtyResponse));
//	// return rtyResponse;
//	// }
//
//	/**
//	 * 获取还款计划
//	 * 
//	 * @param rytRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/getRepayPlan.do")
//	// public RtyResponse getRepayPlan(@RequestBody RytRequest rytRequest) {
//	public RtyResponse getRepayPlan(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 获取还款计划接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
////			System.out.println(params);
//			RytRequest rytRequest = JSON.parseObject(params, RytRequest.class);
//			if (null == rytRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				rtyResponse = rongYiTuiService.getRepayPlan(sessionId, rytRequest);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 获取还款计划接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推 获取还款计划接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	/**
//	 * 主动还款接口
//	 * 
//	 * @param rytRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cloud/third/repayment.do")
//	// public RtyResponse repayment(@RequestBody RytRequest rytRequest) {
//	public RtyResponse repayment(HttpServletRequest request) {
//		long sessionId = System.currentTimeMillis();
//		RtyResponse rtyResponse = new RtyResponse();
//		logger.info(sessionId + ">开始融易推 主动还款接口：>>>>");
//		try {
//			String params = IOUtils.toString(request.getInputStream(), "UTF-8");
////			System.out.println(params);
//			RytRequest rytRequest = JSON.parseObject(params, RytRequest.class);
//			if (null == rytRequest) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//			} else {
//				rtyResponse = rongYiTuiService.updateRepayment(sessionId, rytRequest);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束融易推 主动还款接口：>>>> 异常：", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + ">结束融易推 主动还款接口：>>>>" + JSON.toJSONString(rtyResponse));
//		return rtyResponse;
//	}
//
//	private boolean regex(String str) {
//		String regEx = "^1[0-9]{10}$";
//		Pattern pattern = Pattern.compile(regEx);
//		Matcher matcher = pattern.matcher(str);
//		return matcher.matches();
//	}
//}
