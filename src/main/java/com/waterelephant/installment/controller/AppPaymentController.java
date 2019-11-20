/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.utils.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.entity.lianlian.NotifyResult;
import com.waterelephant.annotation.LockAndSyncRequest;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.installment.service.AppPaymentService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.impl.BwOrderService;

/**
 * Module:
 * 
 * AppPaymentController.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/app/payment")
public class AppPaymentController {

	private Logger logger = Logger.getLogger(AppPaymentController.class);

	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private AppPaymentService appPaymentService;
	@Autowired
	private ProductService productService;
	@Autowired
	private IBwOrderPushInfoService bwOrderPushInfoService;

	@RequestMapping("/appCheckLogin/pay.do")
	@ResponseBody
	@LockAndSyncRequest
	public AppResponseResult pay(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		Long orderId = NumberUtil.parseLong(paramMap.get("orderId"), null);
		logger.info("【AppPaymentController.pay】orderId：" + orderId + "，支付请求参数：" + paramMap);
		try {
			// 支付类型，1正常还款 2续贷
			Integer payType = NumberUtil.parseInteger(paramMap.get("payType"), null);
			// 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
			Integer terminalType = NumberUtil.parseInteger(paramMap.get("terminalType"), null);
			// 分批还款金额
			String batchMoneyStr = paramMap.get("batchMoney");
			Double batchMoney = NumberUtil.parseDouble(batchMoneyStr, null);
			if (orderId == null || payType == null || terminalType == null || batchMoney == null || batchMoney <= 0.0) {
				result.setCode("001");
				result.setMsg("参数错误");
				return result;
			}
			if (!batchMoneyStr.matches("^\\d+(\\.\\d{1,2})?$")) {
				result.setCode("011");
				result.setMsg("金额格式不对");
				logger.info("【AppResponseResult.pay】orderId：" + orderId + "，金额batchMoney：" + batchMoneyStr + "格式不对");
				return result;
			}
			if (payType != 1 && payType != 2) {
				result.setCode("002");
				result.setMsg("支付类型错误");
				return result;
			}
			BwOrder queryOrder = bwOrderService.selectByPrimaryKey(orderId);
			if (queryOrder == null) {
				result.setCode("003");
				result.setMsg("查询不到工单");
				return result;
			}
			Long borrowerId = queryOrder.getBorrowerId();
			BwBankCard queryBankCard = null;
			if (borrowerId != null && borrowerId > 0L) {
				queryBankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
			}
			if (queryBankCard == null) {
				result.setCode("004");
				result.setMsg("没有绑定银行卡");
				return result;
			}
			Integer productType = queryOrder.getProductType();// 产品类型，1.单期 2.多期
			if (productType == 2 || payType == 2) {// 分期或续贷不能使用优惠券
				paramMap.put("isUseCoupon", "0");
			}

			Long statusId = queryOrder.getStatusId();
			if (statusId != 9 && statusId != 13) {// 还款中 逾期
				result.setCode("106");
				result.setMsg("此工单不是还款状态..");// 提示信息方便前端显示 让用户去手动刷新界面
				// 实际提示信息(工单只有还款中和逾期中才可还款 )
				return result;
			}

			// 处理中
			if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId.toString())
					|| RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId.toString())
					|| RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId.toString())) {
				result.setCode("104");
				if (payType == 1) {
					result.setMsg("此工单还款正在处理中..");
				} else {
					result.setMsg("此工单展期正在处理中..");
				}
				return result;
			}

			// 判断是否口袋用户，是：调用口袋代扣
			BwOrderPushInfo koudaiPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 2);
			if (koudaiPushInfo != null) {// 口袋用户
				RepaymentDto repaymentDto = new RepaymentDto();
				repaymentDto.setOrderId(orderId);
				repaymentDto.setTerminalType(terminalType);
				repaymentDto.setUseCoupon(false);
				repaymentDto.setAmount(batchMoney);
				repaymentDto.setType(payType);
				result = appPaymentService.updateAndKouDaiWithhold(repaymentDto);
				return result;
			}

			// TODO Redis保存不能继续续贷用户ID，还款后清除(预留)
			if (payType == 2) {// 续贷
				if (RedisUtils.hexists(RedisKeyConstant.NOT_CAN_XUDAI, borrowerId.toString())) {
					result.setCode("005");
					result.setMsg("不能续贷");
					return result;
				}
				if (productType == 2) {// 分期不能续贷
					result.setCode("006");
					result.setMsg("分期不能续贷");
					return result;
				}
				// 判断是否可以续期
				AppResponseResult canXuDaiResult = productService.canXuDai(orderId);
				if (!"000".equals(canXuDaiResult.getCode())) {
					RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
					return canXuDaiResult;
				}
			}

			paramMap.put("bwOrderJson", JSON.toJSONString(queryOrder));
			paramMap.put("bwBankCardJson", JSON.toJSONString(queryBankCard));
			int repaymentChannel = getRepaymentChannel(payType, queryBankCard.getSignStatus());
			paramMap.put("repaymentChannel", repaymentChannel + "");
			if (repaymentChannel == 2) {// 续贷并且连连签约用连连支付
				// 存入redis,有效期10分钟
				RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId, orderId.toString(), 600);
			} else {// 还款或签约宝付用宝付
				RedisUtils.hset(SystemConstant.NOTIFY_BAOFU, orderId.toString(),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
			result = appPaymentService.updateAndPay(paramMap);
			String code = result.getCode();
			if (!"000".equals(code)) {
				logger.info("调用支付接口失败，删除redis开始，orderId=" + orderId);
				RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
				RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId.toString());
				logger.info("调用支付接口失败，删除redis结束，orderId=" + orderId);
			}
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统异常");
			logger.error("调用支付接口系统异常，orderId=" + orderId, e);
			logger.info("调用支付接口系统异常，orderId=" + orderId);
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
			RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId.toString());
		}
		return result;
	}

	/**
	 * 连连还款回调
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/lianlianRepaymentNotify.do")
	public NotifyNotice lianlianRepaymentNotify(HttpServletRequest request) {

		logger.info("=====================进入连连还款异步回调===========================");
		try {
			String receiveJsonStr = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("==================连连还款回调接收参数=========" + receiveJsonStr);

			NotifyResult notifyResult = JSON.parseObject(receiveJsonStr, NotifyResult.class);
			logger.info("【AppPaymentController.lianlianRepaymentNotify】no_order：" + notifyResult.getNo_order()
					+ "，NotifyResult：" + JSON.toJSONString(notifyResult));

			logger.info("=====================还款异步通知返回工单号===============" + notifyResult.getNo_order());
			logger.info("=====================还款异步通知返回状态码===============" + notifyResult.getResult_pay());

			NotifyNotice notice = appPaymentService.updateForLianlianPaymentNotify(notifyResult);
			return notice;
		} catch (Exception e) {
			NotifyNotice notice = new NotifyNotice();
			logger.error(e, e);
			e.printStackTrace();
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
			return notice;
		}

	}

	private int getRepaymentChannel(Integer payType, Integer signStatus) {
		int repaymentChannel = 1;
		if (payType == 2 && signStatus == 2) {// 续贷并且连连签约用连连支付
			repaymentChannel = 2;
		} else {// 还款或签约宝付用宝付
			repaymentChannel = 1;
		}
		return repaymentChannel;
	}

}