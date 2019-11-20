package com.waterelephant.otherPayment.controller;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.LoanInfo;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwAlipayOrder;
import com.waterelephant.entity.BwWinpayOrder;
import com.waterelephant.otherPayment.util.MD5;
import com.waterelephant.otherPayment.util.PayUtil;
import com.waterelephant.otherPayment.util.StreamUtils;
import com.waterelephant.service.BwAlipayOrderService;
import com.waterelephant.service.BwWinpayOrderService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * Module:
 * 
 * YbjAlipayController.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/ybjPayment")
public class YbjPaymentController {

	private Logger logger = Logger.getLogger(YbjPaymentController.class);

	@Resource
	private BwAlipayOrderService bwAlipayOrderService;
	@Resource
	private BwWinpayOrderService bwWinpayOrderService;
	@Resource
	private IBwRepaymentService iBwRepaymentService;
	@Resource
	private ProductService productService;
	// 测试
	private boolean testBool = false;

	@RequestMapping(value = "/payCompactIndex.do")
	public String payCompactIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// String agent = getRequest().getHeader("user-agent");
		String type = request.getParameter("type");
		logger.info("页面跳转:" + type);
		if ("0".equals(type)) {
			return "alipay_success";
		} else if ("1".equals(type)) {
			return "alipay_fail";
		} else if ("2".equals(type)) {
			return "alipay_overtime";
		} else if ("3".equals(type)) {
			return "alipay_success_zq";
		} else if ("4".equals(type)) {
			return "alipay_fail_zq";
		} else if ("5".equals(type)) {
			return "alipay_overtime";
		} else {
			return "alipay_fail";
		}

	}

	/**
	 * 微信支付请求
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/winChatPay.do")
	public String winChatPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nonceStr = UUID.randomUUID().toString().trim().replaceAll("-", "");

		String orderIdStr = request.getParameter("orderId");
		Long orderId = NumberUtil.parseLong("orderId", null);
		String isUseCoupon = request.getParameter("isUseCoupon");
		Integer payType = NumberUtil.parseInteger(request.getParameter("payType"), null);// 1 还款 2 展期
		String channel = request.getParameter("channel"); // 来源
		String money = request.getParameter("money");// 还款金额
		// String money = "0.01";

		logger.info("益倍嘉微信请求：orderIdStr:" + orderIdStr + ",payType:" + payType + "channel:" + channel + ",money:"
				+ money + ",isUseCoupon:" + isUseCoupon);

		if (CommUtils.isNull(orderIdStr) || CommUtils.isNull(orderId) || CommUtils.isNull(payType)
				|| (payType != 1 && payType != 2) || CommUtils.isNull(channel)
				|| (CommUtils.isNull(money) && payType == 1)) {
			return "alipay_fail";
		}

		AppResponseResult result = null;
		// 是否使用了优惠券
		boolean useCoupons = true;

		if (payType == 1) {// 还款
			result = productService.calcRepaymentCost(orderId, useCoupons, Double.parseDouble(money));
			Map<String, Object> resultMap = (Map<String, Object>) result.getResult();
			if (resultMap != null && resultMap.get("totalUseCouponsAmount") != null) {
				Double totalUseCouponsAmount = NumberUtil.parseDouble(resultMap.get("totalUseCouponsAmount") + "", 0.0);
				Double totalAmount = NumberUtil.parseDouble(resultMap.get("totalAmount") + "", 0.0);
				if (useCoupons && Double.parseDouble(money) >= totalAmount) {// 使用优惠券且全额还款
					money = totalUseCouponsAmount.toString();
					useCoupons = true;
				} else {
					useCoupons = false;
				}
			}
			logger.info("【YbjPaymentController.winChatPay】还款orderId:" + orderId + ",useCoupons=" + useCoupons
					+ ",money=" + money + "resultMap=" + resultMap);
		} else if (payType == 2) {// 展期
			result = productService.calcZhanQiCost(orderId);
			LoanInfo loanInfo = (LoanInfo) result.getResult();
			if (loanInfo != null) {
				money = loanInfo.getAmt();
			}
			logger.info("【YbjPaymentController.winChatPay】展期orderId:" + orderId + ",loanInfo="
					+ JSON.toJSONString(loanInfo));
		}
		if (!"000".equals(result.getCode())) {
			logger.info("【YbjPaymentController.winChatPay】orderId:" + orderId + ",payType:" + payType
					+ ",不满足支付条件,result:" + JSON.toJSONString(result));
			return "alipay_fail";
		}

		String merchantId = SystemConstant.YBJ_MER_ID;
		String key = SystemConstant.YBJ_KEY;
		String test = "false";

		BwWinpayOrder bwWinpayOrder = null;
		if (orderIdStr != null && orderIdStr.trim().length() > 0) {
			bwWinpayOrder = bwWinpayOrderService.queryBwWinpayOrderType(orderId, 0, money);
			int fz = 0;

			if (bwWinpayOrder != null) {
				fz = (int) (((new Date()).getTime() - bwWinpayOrder.getCreateTime().getTime()) / 60000);
				if (fz >= 5) {
					bwWinpayOrder.setType(-1);
					bwWinpayOrderService.update(bwWinpayOrder);
				}
				if (!("W" + payType + channel).equals(bwWinpayOrder.getRemark())) {
					bwWinpayOrder.setRemark("W" + payType + channel);
					bwWinpayOrderService.update(bwWinpayOrder);
				}
			}

			if (bwWinpayOrder == null || fz >= 5) {
				bwWinpayOrder = new BwWinpayOrder();
				bwWinpayOrder.setWinpayType(1);
				bwWinpayOrder.setMoney(new BigDecimal(money));
				bwWinpayOrder.setType(0);
				bwWinpayOrder.setOrderId(orderId);
				bwWinpayOrder.setOtherOrderNo("");
				bwWinpayOrder.setWinpayNo("");
				bwWinpayOrder.setRemark("W" + payType + channel);
				bwWinpayOrderService.save(bwWinpayOrder);
			}
		} else {
			return "alipay_fail";
		}

		if (testBool) {// 测试，回调取测试金额，不取1分钱
			bwWinpayOrder.setMoney(new BigDecimal(money));
			money = "0.01";
		}

		String merchantOutOrderNo = CommUtils.toString(bwWinpayOrder.getId()) + "W" + payType + channel;

		StringBuffer sb = new StringBuffer();
		sb.append("merchantOutOrderNo=").append(merchantOutOrderNo);
		sb.append("&merid=").append(merchantId);
		sb.append("&noncestr=").append(nonceStr);
		sb.append("&orderMoney=").append(money);
		sb.append("&orderTime=").append(PayUtil.getDefaultDateString());
		String sign = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
		sb.append("&sign=" + sign);

		if (test != null && test.trim().equals("true")) {
			return "redirect:http://pay.ebjfinance.com/payWeb/wechatcompactpay.php?" + sb.toString() + "&test=true";
		}
		System.out.println("redirect:http://pay.ebjfinance.com/wechatcompactpay.php?" + sb.toString());
		return "redirect:http://pay.ebjfinance.com/wechatcompactpay.php?" + sb.toString();
	}

	/**
	 * 支付宝支付请求
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/alipay.do")
	public String alipay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer payType = null;
		String channel = null;
		// return "alipay_stop";

		try {
			String nonceStr = UUID.randomUUID().toString().trim().replaceAll("-", "");

			String orderIdStr = request.getParameter("orderId");
			Long orderId = NumberUtil.parseLong(orderIdStr, null);
			payType = NumberUtil.parseInteger(request.getParameter("payType"), null);// 1 还款 2 展期
			channel = request.getParameter("channel"); // 来源
			String isUseCoupon = request.getParameter("isUseCoupon"); // 是否使用优惠券
			String money = request.getParameter("money");// 还款金额

			logger.info("益倍嘉支付宝请求：orderIdStr:" + orderIdStr + ",payType:" + payType + "channel:" + channel + ",money:"
					+ money + ",isUseCoupon:" + isUseCoupon);

			if (CommUtils.isNull(orderIdStr) || CommUtils.isNull(orderId) || CommUtils.isNull(payType)
					|| (payType != 1 && payType != 2) || CommUtils.isNull(channel)
					|| (CommUtils.isNull(money) && payType == 1)) {
				request.setAttribute("channel", channel);
				request.setAttribute("payType", payType);
				logger.info("支付宝支付：系统异常" + orderIdStr);
				if (payType == 2) {
					return "alipay_fail_zq";
				} else {
					return "alipay_fail";
				}
			}

			AppResponseResult result = null;
			// 是否使用了优惠券
			boolean useCoupons = true;
			if (payType == 1) {// 还款
				result = productService.calcRepaymentCost(orderId, useCoupons, Double.parseDouble(money));
				Map<String, Object> resultMap = (Map<String, Object>) result.getResult();
				if (resultMap != null) {
					Double totalUseCouponsAmount = NumberUtil.parseDouble(resultMap.get("totalUseCouponsAmount") + "",
							0.0);
					Double totalAmount = NumberUtil.parseDouble(resultMap.get("totalAmount") + "", 0.0);
					if (useCoupons && Double.parseDouble(money) >= totalAmount) {// 使用优惠券且全额还款
						money = totalUseCouponsAmount.toString();
						useCoupons = true;
					} else {
						useCoupons = false;
					}
				}
				logger.info("【YbjPaymentController.alipay】还款orderId:" + orderId + ",money=" + money + ",useCoupons="
						+ useCoupons + ",resultMap=" + resultMap);
			} else if (payType == 2) {// 展期
				result = productService.calcZhanQiCost(orderId);
				LoanInfo loanInfo = (LoanInfo) result.getResult();
				if (loanInfo != null) {
					money = loanInfo.getAmt();
				}
				logger.info("【YbjPaymentController.alipay】展期orderId:" + orderId + ",loanInfo="
						+ JSON.toJSONString(loanInfo));
			}
			if (!"000".equals(result.getCode())) {
				logger.info("【YbjPaymentController.alipay】orderId:" + orderId + ",payType:" + payType
						+ ",不满足支付条件,result:" + JSON.toJSONString(result));
				request.setAttribute("channel", channel);
				request.setAttribute("payType", payType);
				logger.info("支付宝支付：不满足展期条件," + orderIdStr);
				if (payType == 2) {
					return "alipay_fail_zq";
				} else {
					return "alipay_fail";
				}
			}

			String merchantId = SystemConstant.YBJ_MER_ID;
			String key = SystemConstant.YBJ_KEY;

			// String test = CookieUtil.extractCookie(request, "test");
			String test = "false";
			BigDecimal chargeMoney = null;
			BwAlipayOrder bwAlipayOrder = null;
			if (orderIdStr != null && orderIdStr.trim().length() > 0) {
				if (CommUtils.isNull(SystemConstant.YBJ_CHARGE)) {
					chargeMoney = new BigDecimal(0);
				} else {
					chargeMoney = new BigDecimal(money).multiply(new BigDecimal(SystemConstant.YBJ_CHARGE)).setScale(2,
							BigDecimal.ROUND_UP);
				}

				bwAlipayOrder = bwAlipayOrderService.queryBwAlipayOrderType(orderId, 0, money);
				int fz = 0;
				if (bwAlipayOrder != null) {
					fz = (int) (((new Date()).getTime() - bwAlipayOrder.getCreateTime().getTime()) / 60000);
					if (fz >= 5) {
						bwAlipayOrder.setType(-1);
						bwAlipayOrderService.update(bwAlipayOrder);
					}
					if (!("X" + payType + channel).equals(bwAlipayOrder.getRemark())) {
						bwAlipayOrder.setRemark("X" + payType + channel);
						bwAlipayOrderService.update(bwAlipayOrder);
					}
				}

				if (bwAlipayOrder == null || fz >= 5) {
					bwAlipayOrder = new BwAlipayOrder();
					bwAlipayOrder.setAlipayType(1);
					bwAlipayOrder.setMoney(new BigDecimal(money));
					bwAlipayOrder.setType(0);
					bwAlipayOrder.setOrderId(orderId);
					bwAlipayOrder.setOtherOrderNo("");
					bwAlipayOrder.setAlipayNo("");
					bwAlipayOrder.setCreateTime(new Date());
					bwAlipayOrder.setRemark("X" + payType + channel);
					bwAlipayOrder.setChargeMoney(chargeMoney);
					bwAlipayOrderService.save(bwAlipayOrder);
				}
			} else {
				request.setAttribute("channel", channel);
				request.setAttribute("payType", payType);
				logger.info("支付宝支付：系统异常," + orderIdStr);
				if (payType == 2) {
					return "alipay_fail_zq";
				} else {
					return "alipay_fail";
				}
			}
			if (testBool) {// 测试，回调取测试金额，不取1分钱
				bwAlipayOrder.setMoney(new BigDecimal(money));
				money = "0.01";
			}
			String merchantOutOrderNo = CommUtils.toString(bwAlipayOrder.getId()) + "X" + payType + channel;
			// logger.info("益倍嘉支付宝请求：merid:" + merchantId + ",key:" + key + "merchantOutOrderNo:" + merchantOutOrderNo);
			// if (merchantOutOrderNo.trim().length() == 0) {
			// merchantOutOrderNo = PayUtil.getDefaultDateString();
			// }

			StringBuffer sb = new StringBuffer();
			sb.append("merchantOutOrderNo=").append(merchantOutOrderNo);
			sb.append("&merid=").append(merchantId);
			sb.append("&noncestr=").append(nonceStr);
			sb.append("&orderMoney=").append(CommUtils.toString(new BigDecimal(money).add(chargeMoney)));
			sb.append("&orderTime=").append(PayUtil.getDefaultDateString());
			String sign = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			sb.append("&sign=" + sign);

			String returnURL = "";
			if (test != null && test.trim().equals("true")) {
				returnURL = "http://pay.ebjfinance.com/payWeb/alipay.php?" + sb.toString() + "&test=true";
			} else {
				returnURL = "http://pay.ebjfinance.com/alijspay.php?" + sb.toString();
			}
			logger.info("returnURL:" + returnURL);
			// 放入上下文
			request.setAttribute("returnURL", returnURL);
			request.setAttribute("merchantOutOrderNo", merchantOutOrderNo);
			request.setAttribute("channel", channel);
			request.setAttribute("payType", payType);
			request.setAttribute("money", money);
			request.setAttribute("merid", merchantId);
		} catch (Exception e) {
			logger.error("益倍嘉支付宝请求异常：", e);
			request.setAttribute("channel", channel);
			request.setAttribute("payType", payType);
			logger.info("支付宝支付：系统异常");
			if (payType == 2) {
				return "alipay_fail_zq";
			} else {
				return "alipay_fail";
			}
		}
		return "alijspayConfirm";
	}

	/**
	 * 支付宝支付查询
	 */
	@RequestMapping(value = "/alipayQuery.do")
	@ResponseBody
	public void alipayQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String merchantOutOrderNo = request.getParameter("merchantOutOrderNo");

		String paymentType = request.getParameter("paymentType");
		String nonceStr = UUID.randomUUID().toString().trim().replaceAll("-", "");
		String merchantId = SystemConstant.YBJ_MER_ID;
		String key = SystemConstant.YBJ_KEY;
		String test = "false";

		if ("0".equals(paymentType)) {
			String idStr = merchantOutOrderNo.split("X")[0];
			Long id = Long.parseLong(idStr);
			BwAlipayOrder bwAlipayOrder = bwAlipayOrderService.queryBwAlipayOrderById(id);
			if (bwAlipayOrder != null && 2 == bwAlipayOrder.getType()) {
				response.getWriter().write("{\"payResult\": true}");
				return;
			} else {
				int m = DateUtil.intervalMinute(bwAlipayOrder.getCreateTime(), new Date());
				if (m >= 3) {
					StringBuffer sb = new StringBuffer();
					sb.append("merchantOutOrderNo=").append(merchantOutOrderNo);
					sb.append("&merid=").append(merchantId);
					sb.append("&noncestr=").append(nonceStr);
					String sign = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
					sb.append("&sign=" + sign);

					CloseableHttpResponse response1 = null;
					CloseableHttpClient client = null;
					HttpPost httpPost = new HttpPost(
							"http://pay.ebjfinance.com/alipay/alipayquery.php?" + sb.toString());
					client = HttpClients.createDefault();
					response1 = client.execute(httpPost);

					// 得到返回内容，json格式
					HttpEntity entity = response1.getEntity();
					String content = EntityUtils.toString(entity, "utf-8");

					logger.info("支付结果查询反馈：" + content);
					JsonParser parser = new JsonParser();
					JsonObject object = (JsonObject) parser.parse(content);

					if (object.get("msg") != null) {
						logger.info("===============errorMsg===============：" + object.get("msg").getAsString());
						response.getWriter()
								.write("{\"payResult\": false, \"msg\": \"" + object.get("msg").getAsString() + "\"}");
						return;
					}

					if (bwAlipayOrder != null && "true".equals(object.get("payResult").getAsString())) {
						bwAlipayOrder.setAlipayType(1);
						// bwAlipayOrder.setMoney(new BigDecimal(object.get("payMoney").getAsString()));
						bwAlipayOrder.setOtherOrderNo(object.get("orderNo").getAsString());
						bwAlipayOrder.setAlipayNo(object.get("tradeNo").getAsString());
						bwAlipayOrder.setThirdNo(object.get("thirdNo").getAsString());
						bwAlipayOrder.setRemark(merchantOutOrderNo);
						bwAlipayOrder.setType(2);
						bwAlipayOrderService.update(bwAlipayOrder);

						response.getWriter().write("{\"payResult\": true}");
						return;
					}
				}
			}

		} else {

			StringBuffer sb = new StringBuffer();
			sb.append("merchantOutOrderNo=").append(merchantOutOrderNo);
			sb.append("&merid=").append(merchantId);
			sb.append("&noncestr=").append(nonceStr);
			String sign = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
			sb.append("&sign=" + sign);

			CloseableHttpResponse response1 = null;
			CloseableHttpClient client = null;
			HttpPost httpPost = new HttpPost("http://pay.ebjfinance.com/alipay/alipayquery.php?" + sb.toString());
			client = HttpClients.createDefault();
			response1 = client.execute(httpPost);

			// 得到返回内容，json格式
			HttpEntity entity = response1.getEntity();
			String content = EntityUtils.toString(entity, "utf-8");

			logger.info("支付结果查询反馈：" + content);

			if (!CommUtils.isNull(content)) {

				JsonParser parser = new JsonParser();
				JsonObject object = (JsonObject) parser.parse(content);

				String idStr = merchantOutOrderNo.split("X")[0];
				String payType = merchantOutOrderNo.split("X")[1].substring(0, 1);
				String appRequest = merchantOutOrderNo.split("X")[1].substring(1, 2);
				String payResult = "false";
				if (object.get("payResult") != null) {
					payResult = object.get("payResult").getAsString();
				}

				Long id = Long.parseLong(idStr);
				BwAlipayOrder bwAlipayOrder = bwAlipayOrderService.queryBwAlipayOrderById(id);
				if ("true".equals(payResult)) {

					if (bwAlipayOrder != null && 2 != bwAlipayOrder.getType() && "1".equals(paymentType)
							&& CommUtils.isNull(bwAlipayOrder.getAlipayNo())) {
						// 支付成功
						RepaymentDto repaymentDto = new RepaymentDto();
						repaymentDto.setOrderId(bwAlipayOrder.getOrderId());
						repaymentDto.setType(NumberUtil.parseInteger(payType, null));
						repaymentDto.setTerminalType(NumberUtil.parseInteger(appRequest, null));
						repaymentDto.setTradeNo(object.get("tradeNo").getAsString());
						repaymentDto.setUseCoupon(true);
						repaymentDto.setAmount(bwAlipayOrder.getMoney().doubleValue());
						repaymentDto.setPayChannel(5);
						repaymentDto.setTradeType(2);
						repaymentDto.setTradeCode(payResult);
						repaymentDto.setTradeTime(new Date());
						bwAlipayOrder.setUpdateTime(new Date());

						// 支付成功
						logger.info("支付宝查询还款开始idStr:" + idStr);
						AppResponseResult appResponseResult = iBwRepaymentService.updateOrderByTradeMoney(repaymentDto);

						if ("000".equals(appResponseResult.getCode())) {
							logger.info("支付宝查询还款成功idStr：" + idStr + appResponseResult.getMsg());
						} else {
							logger.info("支付宝查询还款失败idStr：" + idStr + appResponseResult.getMsg());
						}
						response.getWriter()
								.write("{\"payResult\": true, \"msg\": \"" + object.get("msg").getAsString() + "\"}");
						return;
					}
					if (bwAlipayOrder != null) {
						bwAlipayOrder.setAlipayType(1);
						// bwAlipayOrder.setMoney(new BigDecimal(object.get("payMoney").getAsString()));
						bwAlipayOrder.setOtherOrderNo(object.get("orderNo").getAsString());
						bwAlipayOrder.setAlipayNo(object.get("tradeNo").getAsString());
						bwAlipayOrder.setThirdNo(object.get("thirdNo").getAsString());
						bwAlipayOrder.setRemark(merchantOutOrderNo);
					}

					bwAlipayOrder.setType(2);
					bwAlipayOrderService.update(bwAlipayOrder);

				} else {
					bwAlipayOrder.setType(3);
					bwAlipayOrderService.update(bwAlipayOrder);
				}
				if (object.get("msg") != null) {
					logger.info("===============errorMsg===============：" + object.get("msg").getAsString());
					response.getWriter()
							.write("{\"payResult\": false, \"msg\": \"" + object.get("msg").getAsString() + "\"}");
					return;
				}
				response.getWriter().write(content);
			}
		}

	}

	/**
	 * 微信支付查询
	 */
	@RequestMapping(value = "/winPayQuery")
	@ResponseBody
	public void winPayQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String merchantOutOrderNo = request.getParameter("merchantOutOrderNo");
		String nonceStr = UUID.randomUUID().toString().trim().replaceAll("-", "");

		// 从cookie取出
		String merchantId = SystemConstant.YBJ_MER_ID;
		String key = SystemConstant.YBJ_KEY;
		String test = "false";

		StringBuffer sb = new StringBuffer();
		sb.append("merchantOutOrderNo=").append(merchantOutOrderNo);
		sb.append("&merid=").append(merchantId);
		sb.append("&noncestr=").append(nonceStr);
		String sign = MD5.sign(sb.toString(), "&key=" + key, "utf-8");
		sb.append("&sign=" + sign);

		CloseableHttpResponse response1 = null;
		CloseableHttpClient client = null;
		HttpPost httpPost = new HttpPost("http://pay.ebjfinance.com/weixin/wechatpayquery.php?" + sb.toString());
		client = HttpClients.createDefault();
		response1 = client.execute(httpPost);

		// 得到返回内容，json格式
		HttpEntity entity = response1.getEntity();
		String content = EntityUtils.toString(entity, "utf-8");

		logger.info("微信支付查询反馈：" + content);

		if (!CommUtils.isNull(content)) {
			JsonParser parser = new JsonParser();
			JsonObject object = (JsonObject) parser.parse(content);

			// 返回成功
			String idStr = merchantOutOrderNo.split("W")[0];
			String payType = merchantOutOrderNo.split("W")[1].substring(0, 1);
			String appRequest = merchantOutOrderNo.split("W")[1].substring(1, 2);

			String payResult = object.get("payResult").getAsString();

			Long id = Long.parseLong(idStr);
			BwWinpayOrder bwWinpayOrder = bwWinpayOrderService.queryBwWinpayOrderById(id);
			if (bwWinpayOrder != null) {
				bwWinpayOrder.setWinpayType(1);
				bwWinpayOrder.setMoney(new BigDecimal(object.get("payMoney").getAsString()));
				bwWinpayOrder.setOtherOrderNo(object.get("orderNo").getAsString());
				bwWinpayOrder.setWinpayNo(object.get("tradeNo").getAsString());
				bwWinpayOrder.setThirdNo(object.get("thirdNo").getAsString());
				bwWinpayOrder.setRemark(merchantOutOrderNo);
			} else {
				// 否则返回失败
				response.getOutputStream().write("fail".getBytes());
				return;
			}

			if ("true".equals(payResult)) {
				bwWinpayOrder.setType(2);
				bwWinpayOrder.setUpdateTime(new Date());
				bwWinpayOrderService.update(bwWinpayOrder);

				RepaymentDto repaymentDto = new RepaymentDto();
				repaymentDto.setOrderId(bwWinpayOrder.getOrderId());
				repaymentDto.setType(NumberUtil.parseInteger(payType, null));
				repaymentDto.setTerminalType(NumberUtil.parseInteger(appRequest, null));
				repaymentDto.setTradeNo(object.get("tradeNo").getAsString());
				repaymentDto.setAmount(bwWinpayOrder.getMoney().doubleValue());
				repaymentDto.setPayChannel(6);
				repaymentDto.setTradeTime(new Date());

				// 支付成功

			} else {
				bwWinpayOrder.setType(3);
				bwWinpayOrderService.update(bwWinpayOrder);
			}
			if (object.get("msg") != null) {
				logger.info("===============errorMsg===============：" + object.get("msg").getAsString());
				response.getWriter()
						.write("{\"payResult\": false, \"msg\": \"" + object.get("msg").getAsString() + "\"}");
				return;
			}
		}

		// 输出内容
		response.getWriter().write(content);
	}

	// 支付宝结果回调
	@RequestMapping(value = "/payNotify.do")
	@ResponseBody
	public void payNotify(HttpServletRequest request, HttpServletResponse response) {
		String idStr = null;
		try {
			String data = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
			data = java.net.URLDecoder.decode(data, "utf-8");
			logger.info("益倍嘉支付宝支付反馈：data:" + data);

			Map parameterMap = PayUtil.getParameterMap(data);
			String merid = (String) parameterMap.get("merid");
			String merchantOutOrderNo = (String) parameterMap.get("merchantOutOrderNo");
			String orderNo = (String) parameterMap.get("orderNo");
			// msg为json格式
			String msg = (String) parameterMap.get("msg");
			String payResult = CommUtils.toString(parameterMap.get("payResult"));
			JsonParser parser = new JsonParser();
			JsonObject msgObject = (JsonObject) parser.parse(msg);

			// logger.info("========payNotify:" + orderNo);
			// logger.info("========payNotify:" + merchantOutOrderNo);
			// logger.info("========payNotify:" + msgObject.get("tradeNo").getAsString());

			// 返回成功
			idStr = merchantOutOrderNo.split("X")[0];
			// 防重复请求锁
			boolean lockRequest = ControllerUtil.lockRequest(RedisKeyConstant.LOCK_KEY_PRE + idStr, 30);
			if (!lockRequest) {// 重复回调
				// 否则返回失败
				logger.info("========payNotify重复回调orderId:" + idStr);
				response.getOutputStream().write("fail".getBytes());
				return;
			}
			String payType = merchantOutOrderNo.split("X")[1].substring(0, 1);
			String appRequest = merchantOutOrderNo.split("X")[1].substring(1, 2);

			Long id = Long.parseLong(idStr);
			BwAlipayOrder bwAlipayOrder = bwAlipayOrderService.queryBwAlipayOrderById(id, 0);
			// logger.info("========payNotify:" + bwAlipayOrder);
			if (bwAlipayOrder != null) {
				bwAlipayOrder.setAlipayType(1);
				// if (!testBool) {
				// bwAlipayOrder.setMoney(new BigDecimal(msgObject.get("payMoney").getAsString()));
				// }
				bwAlipayOrder.setOtherOrderNo(orderNo);
				bwAlipayOrder.setAlipayNo(msgObject.get("tradeNo").getAsString());
				bwAlipayOrder.setThirdNo(msgObject.get("thirdNo").getAsString());
				bwAlipayOrder.setRemark(merchantOutOrderNo);
			} else {
				// 否则返回失败
				response.getOutputStream().write("fail".getBytes());
				return;
			}
			logger.info("========payNotify==idStr:" + idStr + "true".equals(payResult));
			if ("true".equals(payResult)) {
				bwAlipayOrder.setType(2);
				bwAlipayOrder.setUpdateTime(new Date());
				bwAlipayOrderService.update(bwAlipayOrder);

				RepaymentDto repaymentDto = new RepaymentDto();
				repaymentDto.setOrderId(bwAlipayOrder.getOrderId());
				repaymentDto.setType(NumberUtil.parseInteger(payType, null));
				repaymentDto.setTerminalType(NumberUtil.parseInteger(appRequest, null));
				repaymentDto.setTradeNo(msgObject.get("tradeNo").getAsString());
				repaymentDto.setUseCoupon(true);
				repaymentDto.setAmount(bwAlipayOrder.getMoney().doubleValue());
				repaymentDto.setPayChannel(5);
				repaymentDto.setTradeTime(new Date());
				repaymentDto.setTradeType(2);
				repaymentDto.setTradeCode(payResult);

				// 支付成功
				logger.info("支付宝还款开始idStr:" + idStr);
				AppResponseResult appResponseResult = iBwRepaymentService.updateOrderByTradeMoney(repaymentDto);

				if ("000".equals(appResponseResult.getCode())) {
					logger.info("支付宝还款成功idStr：" + idStr + appResponseResult.getMsg());
				} else {
					logger.info("支付宝还款失败idStr：" + idStr + appResponseResult.getMsg());
				}
				Map<String, String> transactionMap = new HashMap<String, String>();
				transactionMap.put(merchantOutOrderNo, data);
				response.getOutputStream().write("success".getBytes());
				return;
			} else {
				bwAlipayOrder.setType(3);
				bwAlipayOrderService.update(bwAlipayOrder);

				response.getOutputStream().write("fail".getBytes());
				return;
			}
			// 否则返回失败
			// response.getOutputStream().write("fail".getBytes());
		} catch (Exception ex) {
			logger.error("回调异常：", ex);
		} finally {
			if (!CommUtils.isNull(idStr)) {
				logger.info("========payNotify删除回调锁orderId:" + idStr);
				RedisUtils.del(RedisKeyConstant.LOCK_KEY_PRE + idStr);
			}
		}
	}

	// 微信结果回调
	@RequestMapping(value = "/payNotifyWin.do")
	@ResponseBody
	public void payNotifyWin(HttpServletRequest request, HttpServletResponse response) {
		try {

			String data = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
			data = java.net.URLDecoder.decode(data, "utf-8");
			logger.info("益倍嘉支付微信反馈：data:" + data);

			Map parameterMap = PayUtil.getParameterMap(data);
			String merid = (String) parameterMap.get("merid");
			String merchantOutOrderNo = (String) parameterMap.get("merchantOutOrderNo");
			String orderNo = (String) parameterMap.get("orderNo");
			// msg为json格式
			String msg = (String) parameterMap.get("msg");
			String payResult = CommUtils.toString(parameterMap.get("payResult"));
			JsonParser parser = new JsonParser();
			JsonObject msgObject = (JsonObject) parser.parse(msg);

			// logger.info("========:" + merid);
			// logger.info("========:" + orderNo);
			// logger.info("========:" + merchantOutOrderNo);
			// logger.info("========:" + msg);
			// logger.info("========:" + msgObject.get("tradeNo").getAsString());

			// 返回成功
			String idStr = merchantOutOrderNo.split("W")[0];
			String payType = merchantOutOrderNo.split("W")[1].substring(0, 1);
			String appRequest = merchantOutOrderNo.split("W")[1].substring(1, 2);

			Long id = Long.parseLong(idStr);
			BwWinpayOrder bwWinpayOrder = bwWinpayOrderService.queryBwWinpayOrderById(id);
			if (bwWinpayOrder != null) {
				bwWinpayOrder.setWinpayType(1);
				if (!testBool) {
					bwWinpayOrder.setMoney(new BigDecimal(msgObject.get("payMoney").getAsString()));
				}
				bwWinpayOrder.setOtherOrderNo(orderNo);
				bwWinpayOrder.setWinpayNo(msgObject.get("tradeNo").getAsString());
				bwWinpayOrder.setThirdNo(msgObject.get("thirdNo").getAsString());
				bwWinpayOrder.setRemark(merchantOutOrderNo);
			} else {
				// 否则返回失败
				response.getOutputStream().write("fail".getBytes());
				return;
			}

			if ("true".equals(payResult)) {
				bwWinpayOrder.setType(2);
				bwWinpayOrder.setUpdateTime(new Date());
				bwWinpayOrderService.update(bwWinpayOrder);

				RepaymentDto repaymentDto = new RepaymentDto();
				repaymentDto.setOrderId(bwWinpayOrder.getOrderId());
				repaymentDto.setType(NumberUtil.parseInteger(payType, null));
				repaymentDto.setTerminalType(NumberUtil.parseInteger(appRequest, null));
				repaymentDto.setTradeNo(msgObject.get("tradeNo").getAsString());
				repaymentDto.setAmount(bwWinpayOrder.getMoney().doubleValue());
				repaymentDto.setUseCoupon(true);
				repaymentDto.setPayChannel(6);
				repaymentDto.setTradeTime(new Date());
				repaymentDto.setTradeType(2);
				repaymentDto.setTradeCode(payResult);

				// 支付成功
				iBwRepaymentService.updateOrderByTradeMoney(repaymentDto);

				Map<String, String> transactionMap = new HashMap<String, String>();
				transactionMap.put(merchantOutOrderNo, data);
				response.getOutputStream().write("success".getBytes());
				return;
			} else {
				bwWinpayOrder.setType(3);
				bwWinpayOrderService.update(bwWinpayOrder);

				response.getOutputStream().write("fail".getBytes());
				return;
			}
			// 否则返回失败
			// response.getOutputStream().write("fail".getBytes());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/payReturnWin.do")
	public String payReturnWin(HttpServletRequest request, HttpServletResponse response) {

		try {

			String merchantOutOrderNo = request.getParameter("merchantOutOrderNo");
			System.out.println("益倍嘉支付宝支付回调：merchantOutOrderNo:" + merchantOutOrderNo);
			// 返回成功

			// String payType = merchantOutOrderNo.split("X")[1].substring(0, 1);
			// String appRequest = merchantOutOrderNo.split("X")[1].substring(1, 2);
			if (CommUtils.isNull(merchantOutOrderNo)) {
				return "alipay_fail";
			} else {
				String idStr = merchantOutOrderNo.split("X")[0];
				BwWinpayOrder bwWinpayOrder = bwWinpayOrderService.queryBwWinpayOrderById(Long.parseLong(idStr));
				if (2 == bwWinpayOrder.getType()) {
					// 成功
					return "alipay_fail";
				} else {
					return "alipay_fail";
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("益倍嘉支付宝支付回调异常");
		}
		return "alipay_fail";
	}

	public static void main(String[] args) {
		Long date = new Date().getTime();
		Double d = Double.parseDouble("111.00") * 0.005;
		BigDecimal b = new BigDecimal("111.00").multiply(new BigDecimal("0.005")).setScale(2, BigDecimal.ROUND_UP);
		System.out.println(b);
		System.out.println(date);
	}

}
