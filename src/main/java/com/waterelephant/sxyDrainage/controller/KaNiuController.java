//package com.waterelephant.sxyDrainage.controller;
//
//import java.io.IOException;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuBindCardReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuCheckUserReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuCommonReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuPushUserReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuResponse;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupplyInfoReq;
//import com.waterelephant.sxyDrainage.service.KaNiuService;
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.BASE64Decoder;
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.KaNiuConstant;
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.KaNiuUtil;
//
///**
// * 
// * Module: KaNiuController.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description:
// */
//@Controller
//public class KaNiuController {
//	private Logger logger = LoggerFactory.getLogger(KaNiuController.class);
//	@Autowired
//	private KaNiuService kaNiuService;
//
//	/**
//	 * 用户准入判断接口
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/third/cloud/cardNiu/checkUser.do")
//	public KaNiuResponse checkUser(HttpServletRequest request, @RequestParam Map<String, String> params) {
//		long sessionId = System.currentTimeMillis();
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		logger.info(sessionId + ">开始卡牛 过滤接口：>>>>");
//		try {
//			String data = request.getParameter("data");
//			// logger.info(sessionId + ">用户准入判断接口：DATA >>>>" + data);
//			if (StringUtils.isBlank(data)) {
//				logger.info(sessionId + ">结束卡牛：>>>> 参数为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("参数为空");
//				return kaNiuResponse;
//			}
//			BASE64Decoder base64de = new BASE64Decoder();
//			@SuppressWarnings("restriction")
//			byte[] dataByte = base64de.decodeBuffer(data);
//			String reqjson = new String(dataByte, "UTF-8");
//			logger.info(sessionId + ">用户准入判断接口：json >>>>" + reqjson);
//			kaNiuResponse = checkRequest(sessionId, request, params);
//			if (null == kaNiuResponse) {
//				kaNiuResponse = new KaNiuResponse();
//				KaNiuCheckUserReq kaNiuCheckUserReq = JSON.parseObject(reqjson, KaNiuCheckUserReq.class);
//				if (null == kaNiuCheckUserReq) {
//					logger.info(sessionId + ">结束卡牛：>>>> 参数为空");
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("参数为空");
//					return kaNiuResponse;
//				}
//				kaNiuResponse = kaNiuService.checkUser(sessionId, kaNiuCheckUserReq);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 过滤接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("请求异常");
//		}
//		logger.info(sessionId + ">结束卡牛 过滤接口：>>>>" + JSON.toJSONString(kaNiuResponse));
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 进件接口
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("restriction")
//	@ResponseBody
//	@RequestMapping("/third/cloud/cardNiu/pushUser.do")
//	public KaNiuResponse pushUser(HttpServletRequest request, @RequestParam Map<String, String> params) {
//		long sessionId = System.currentTimeMillis();
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		logger.info(sessionId + ">开始卡牛 进件接口：>>>>");
//		try {
//			String data = request.getParameter("data");
//			// logger.info(sessionId + ">进件接口：DATA >>>>" + data);
//			if (StringUtils.isBlank(data)) {
//				logger.info(sessionId + ">结束卡牛进件接口：>>>> 参数为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("参数为空");
//				return kaNiuResponse;
//			}
//			kaNiuResponse = checkRequest(sessionId, request, params);
//			if (null == kaNiuResponse) {
//				kaNiuResponse = new KaNiuResponse();
//				BASE64Decoder base64de = new BASE64Decoder();
//				byte[] dataByte = base64de.decodeBuffer(data);
//				String reqjson = new String(dataByte, "UTF-8");
//				// logger.info(sessionId + ">用户进件接口：json >>>>" + reqjson);
//				KaNiuPushUserReq kaNiuPushUserReq = JSON.parseObject(reqjson, KaNiuPushUserReq.class);
//				if (null == kaNiuPushUserReq) {
//					logger.info(sessionId + ">结束卡牛进件接口：>>>> 参数为空");
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("参数为空");
//					return kaNiuResponse;
//				}
//				kaNiuResponse = kaNiuService.savePushUser(sessionId, kaNiuPushUserReq);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 进件接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("请求异常");
//		}
//		logger.info(sessionId + ">结束卡牛 进件接口：>>>>" + JSON.toJSONString(kaNiuResponse));
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 绑卡接口
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("restriction")
//	@ResponseBody
//	@RequestMapping("/third/cloud/cardNiu/bandCard.do")
//	public KaNiuResponse bandCard(HttpServletRequest request, @RequestParam Map<String, String> params) {
//		long sessionId = System.currentTimeMillis();
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		logger.info(sessionId + ">开始卡牛 绑卡接口：>>>>");
//		try {
//			String data = request.getParameter("data");
//			// logger.info(sessionId + ">绑卡接口：DATA >>>>" + data);
//			if (StringUtils.isBlank(data)) {
//				logger.info(sessionId + ">结束卡牛绑卡接口：>>>> 参数为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("参数为空");
//				return kaNiuResponse;
//			}
//			kaNiuResponse = checkRequest(sessionId, request, params);
//			if (null == kaNiuResponse) {
//				BASE64Decoder base64de = new BASE64Decoder();
//				byte[] dataByte = base64de.decodeBuffer(data);
//				String reqjson = new String(dataByte, "UTF-8");
//				logger.info(sessionId + ">用户绑卡接口：json >>>>" + reqjson);
//				kaNiuResponse = new KaNiuResponse();
//				KaNiuBindCardReq kaNiuBindCardReq = JSON.parseObject(reqjson, KaNiuBindCardReq.class);
//				if (null == kaNiuBindCardReq) {
//					logger.info(sessionId + ">结束卡牛绑卡接口：>>>> 参数为空");
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("参数为空");
//					return kaNiuResponse;
//				}
//				kaNiuResponse = kaNiuService.saveBindCard(sessionId, kaNiuBindCardReq);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 进件接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("请求异常");
//		}
//		logger.info(sessionId + ">结束卡牛 过滤接口：>>>>" + JSON.toJSONString(kaNiuResponse));
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 订单状态查询接口
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("restriction")
//	@ResponseBody
//	@RequestMapping("/third/cloud/cardNiu/getOrderState.do")
//	public KaNiuResponse getOrderState(HttpServletRequest request, @RequestParam Map<String, String> params) {
//		long sessionId = System.currentTimeMillis();
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		logger.info(sessionId + ">开始卡牛 订单状态查询接口：>>>>");
//		try {
//			String data = request.getParameter("data");
//			// logger.info(sessionId + ">订单状态查询接口：DATA >>>>" + data);
//			if (StringUtils.isBlank(data)) {
//				logger.info(sessionId + ">结束卡牛订单状态查询接口：>>>> 参数为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("参数为空");
//				return kaNiuResponse;
//			}
//			BASE64Decoder base64de = new BASE64Decoder();
//			byte[] dataByte = base64de.decodeBuffer(data);
//			String reqjson = new String(dataByte, "UTF-8");
//			logger.info(sessionId + ">用户状态查询接口：json >>>>" + reqjson);
//			kaNiuResponse = checkRequest(sessionId, request, params);
//			if (null == kaNiuResponse) {
//				kaNiuResponse = new KaNiuResponse();
//				KaNiuCommonReq kaNiuCommonReq = JSON.parseObject(reqjson, KaNiuCommonReq.class);
//				if (null == kaNiuCommonReq) {
//					logger.info(sessionId + ">结束卡牛订单状态查询接口：>>>> 参数为空");
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("参数为空");
//					return kaNiuResponse;
//				}
//				kaNiuResponse = kaNiuService.getOrderState(sessionId, kaNiuCommonReq);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 订单状态查询接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("请求异常");
//		}
//		logger.info(sessionId + ">结束卡牛 订单状态查询接口：>>>>" + JSON.toJSONString(kaNiuResponse));
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 获取签约/绑卡地址接口
//	 *
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("restriction")
//	@ResponseBody
//	@RequestMapping("/third/cloud/cardNiu/getNextReqUrl.do")
//	public KaNiuResponse getNextReqUrl(HttpServletRequest request, @RequestParam Map<String, String> params) {
//		long sessionId = System.currentTimeMillis();
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		logger.info(sessionId + ">开始卡牛 获取签约/绑卡地址接口：>>>>");
//		try {
//			String data = request.getParameter("data");
//			if (StringUtils.isBlank(data)) {
//				logger.info(sessionId + ">结束卡牛获取签约/绑卡地址接口：>>>> 参数为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("参数为空");
//				return kaNiuResponse;
//			}
//			BASE64Decoder base64de = new BASE64Decoder();
//			byte[] dataByte = base64de.decodeBuffer(data);
//			String reqjson = new String(dataByte, "UTF-8");
//			logger.info(sessionId + ">获取签约/绑卡地址接口：json >>>>" + reqjson);
//			kaNiuResponse = checkRequest(sessionId, request, params);
//			if (null == kaNiuResponse) {
//				kaNiuResponse = new KaNiuResponse();
//				KaNiuBindCardReq kaNiuBindCardReq = JSON.parseObject(reqjson, KaNiuBindCardReq.class);
//				if (null == kaNiuBindCardReq) {
//					logger.info(sessionId + ">结束卡牛获取签约/绑卡地接口：>>>> 参数为空");
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("参数为空");
//					return kaNiuResponse;
//				}
//				kaNiuResponse = kaNiuService.getNextReqUrl(sessionId, kaNiuBindCardReq);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 获取签约/绑卡地址接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("请求异常");
//		}
//		logger.info(sessionId + ">结束卡牛 获取签约/绑卡地址接口：>>>>" + JSON.toJSONString(kaNiuResponse));
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 推送进件补充信息
//	 *
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("restriction")
//	@ResponseBody
//	@RequestMapping("/third/cloud/cardNiu/pushSupplyInfo.do")
//	public KaNiuResponse pushSupplyInfo(HttpServletRequest request, @RequestParam Map<String, String> params) {
//		long sessionId = System.currentTimeMillis();
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		logger.info(sessionId + ">开始卡牛 推送补充信息接口：>>>>");
//		try {
//			String data = request.getParameter("data");
//			if (StringUtils.isBlank(data)) {
//				logger.info(sessionId + ">结束卡牛推送补充信息接口：>>>> 参数为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("参数为空");
//				return kaNiuResponse;
//			}
//			BASE64Decoder base64de = new BASE64Decoder();
//			byte[] dataByte = base64de.decodeBuffer(data);
//			String reqjson = new String(dataByte, "UTF-8");
//			// logger.info(sessionId + ">推送补充信息接口：json >>>>" + reqjson);
//			kaNiuResponse = checkRequest(sessionId, request, params);
//			if (null == kaNiuResponse) {
//				kaNiuResponse = new KaNiuResponse();
//				SupplyInfoReq supplyInfoReq = JSON.parseObject(reqjson, SupplyInfoReq.class);
//				if (null == supplyInfoReq) {
//					logger.info(sessionId + ">结束卡牛推送补充信息：>>>> 参数为空");
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("参数为空");
//					return kaNiuResponse;
//				}
//				kaNiuResponse = kaNiuService.saveSupplyInfo(sessionId, supplyInfoReq);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 推送补充信息：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("请求异常");
//		}
//		logger.info(sessionId + ">结束卡牛 推送补充信息：>>>>" + JSON.toJSONString(kaNiuResponse));
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 还款试算接口
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("restriction")
//	@ResponseBody
//	@RequestMapping("/third/cloud/cardNiu/calculate.do")
//	public KaNiuResponse calculate(HttpServletRequest request, @RequestParam Map<String, String> params) {
//		long sessionId = System.currentTimeMillis();
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		logger.info(sessionId + ">开始卡牛 还款试算接口：>>>>");
//		try {
//			String data = request.getParameter("data");
//			logger.info(sessionId + ">还款试算接口：DATA >>>>" + data);
//			if (StringUtils.isBlank(data)) {
//				logger.info(sessionId + ">结束还款试算接口：>>>> 参数为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("参数为空");
//				return kaNiuResponse;
//			}
//			BASE64Decoder base64de = new BASE64Decoder();
//			byte[] dataByte = base64de.decodeBuffer(data);
//			String reqjson = new String(dataByte, "UTF-8");
//			logger.info(sessionId + ">还款试算接口：json >>>>" + reqjson);
//			kaNiuResponse = checkRequest(sessionId, request, params);
//			if (null == kaNiuResponse) {
//				kaNiuResponse = new KaNiuResponse();
//				KaNiuCommonReq kaNiuCommonReq = JSON.parseObject(reqjson, KaNiuCommonReq.class);
//				if (null == kaNiuCommonReq) {
//					logger.info(sessionId + ">结束还款试算接口：>>>> 参数为空");
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("参数为空");
//					return kaNiuResponse;
//				}
//				kaNiuResponse = kaNiuService.calculate(sessionId, kaNiuCommonReq);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束还款试算查询接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("请求异常");
//		}
//		logger.info(sessionId + ">结束卡牛 还款试算接口：>>>>" + JSON.toJSONString(kaNiuResponse));
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 还款申请
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("restriction")
//	@ResponseBody
//	@RequestMapping("/third/cloud/cardNiu/repayment.do")
//	public KaNiuResponse repayment(HttpServletRequest request, @RequestParam Map<String, String> params) {
//		long sessionId = System.currentTimeMillis();
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		logger.info(sessionId + ">开始卡牛 还款申请接口：>>>>");
//		try {
//			String data = request.getParameter("data");
//			logger.info(sessionId + ">还款申请：DATA >>>>" + data);
//			if (StringUtils.isBlank(data)) {
//				logger.info(sessionId + ">结束还款申请接口：>>>> 参数为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("参数为空");
//				return kaNiuResponse;
//			}
//			BASE64Decoder base64de = new BASE64Decoder();
//			byte[] dataByte = base64de.decodeBuffer(data);
//			String reqjson = new String(dataByte, "UTF-8");
//			logger.info(sessionId + ">还款申请接口：json >>>>" + reqjson);
//			kaNiuResponse = checkRequest(sessionId, request, params);
//			if (null == kaNiuResponse) {
//				kaNiuResponse = new KaNiuResponse();
//				KaNiuCommonReq kaNiuCommonReq = JSON.parseObject(reqjson, KaNiuCommonReq.class);
//				if (null == kaNiuCommonReq) {
//					logger.info(sessionId + ">结束还款申请接口：>>>> 参数为空");
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("参数为空");
//					return kaNiuResponse;
//				}
//				kaNiuResponse = kaNiuService.updateRepayment(sessionId, kaNiuCommonReq);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束还款申请接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("请求异常");
//		}
//		logger.info(sessionId + ">结束卡牛 还款申请接口：>>>>" + JSON.toJSONString(kaNiuResponse));
//		return kaNiuResponse;
//	}
//
//	// --------------------------------------参数校验--------------------------------------//
//	private KaNiuResponse checkRequest(Long sessionId, HttpServletRequest request, Map<String, String> map)
//			throws IOException {
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		String accessId = request.getHeader("accessId");
//		String accessToken = request.getHeader("accessToken");
//		if (StringUtils.isBlank(accessId) || StringUtils.isBlank(accessToken)) {
//			logger.info(sessionId + ">结束卡牛：>>>> 参数为空");
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.ERR_NO_ACCESS);
//			kaNiuResponse.setResultCodeDescription("accessToken或accessId不存在");
//			return kaNiuResponse;
//		}
//		if (!accessId.equals(KaNiuConstant.ACCESSID)) {
//			logger.info(sessionId + ">结束卡牛>>>> 验签失败");
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.ERR_SIGN);
//			kaNiuResponse.setResultCodeDescription("鉴权失败");
//			return kaNiuResponse;
//		}
//		String key = KaNiuUtil.getToken(map, KaNiuConstant.ACCESSTOKEN);
//		if (accessToken.equals(key)) {
//			return null;
//		} else {
//			logger.info(sessionId + ">结束卡牛>>>> 验签失败");
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//			kaNiuResponse.setResultCode(KaNiuResponse.ERR_SIGN);
//			kaNiuResponse.setResultCodeDescription("鉴权失败");
//			return kaNiuResponse;
//		}
//	}
//}
