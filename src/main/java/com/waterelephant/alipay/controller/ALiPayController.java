package com.waterelephant.alipay.controller;

import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.alipay.entity.ALiPreforkResponse;
import com.waterelephant.alipay.entity.AliQueryRequest;
import com.waterelephant.alipay.util.ALiPayUtils;
import com.waterelephant.alipay.util.AliPayConstant;
import com.waterelephant.alipay.util.CorefireHttpPost;
import com.waterelephant.alipay.util.MD5;
import com.waterelephant.alipay.util.SignUtils;
import com.waterelephant.alipay.util.XmlUtils;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.LoanInfo;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwAlipayOrder;
import com.waterelephant.otherPayment.util.StreamUtils;
import com.waterelephant.service.BwAlipayOrderService;
import com.waterelephant.service.BwWinpayOrderService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.RedisUtils;

import cn.jpush.api.utils.StringUtils;

/**
 * 
 * 
 * Module:
 * 
 * aLiPayController.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <支付宝>
 */
@Controller
@SuppressWarnings("all")
public class ALiPayController {

	private Logger logger = Logger.getLogger(ALiPayController.class);

	@Resource
	private BwAlipayOrderService bwAlipayOrderService;
	@Resource
	private BwWinpayOrderService bwWinpayOrderService;
	@Resource
	private IBwRepaymentService iBwRepaymentService;
	@Resource
	private ProductService productService;

	/**
	 * 订单预创建
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/alipay.do")
	@ResponseBody
	public String createOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SortedMap hm = new TreeMap(); // 填充参数
		Integer payType = null;
		String channel = null;
		try {
			String nonce_str = UUID.randomUUID().toString().replace("-", "");
			logger.info("开始支付宝预创建接口");
			Long orderId = Long.parseLong(request.getParameter("orderId")); // 订单号
			payType = Integer.parseInt(request.getParameter("payType")); // 1.还款 2.展期
			String isUseCoupon = request.getParameter("isUseCoupon"); // 是否使用优惠券
			String body = request.getParameter("body"); // 商品描述
			String money = request.getParameter("total_fee"); // 总金额
			channel = request.getParameter("channel"); // 来源

			logger.info("xxx支付宝请求：ordrId:" + orderId + ",payType:" + payType + "channel:" + channel + ",money:" + money
					+ ",isUseCoupon:" + isUseCoupon);

			if (CommUtils.isNull(orderId) || CommUtils.isNull(orderId) || CommUtils.isNull(payType)
					|| (payType != 1 && payType != 2) || CommUtils.isNull(channel)
					|| (CommUtils.isNull(money) && payType == 1)) {
				request.setAttribute("channel", channel);
				request.setAttribute("payType", payType);
				logger.info("支付宝支付：系统异常" + orderId);
				if (payType == 2) {
					return "alipay_fail_zq";
				} else {
					return "alipay_fail";
				}
			}

			AppResponseResult result = null;
			// 是否使用了优惠券
			boolean useCoupons = true;
			if (payType == 1) { // 还款
				result = productService.calcRepaymentCost(orderId, useCoupons, Double.parseDouble(money));
				Map<String, Object> resultMap = (Map<String, Object>) result.getResult();

				if (resultMap != null) {
					Double totalUseCouponsAmount = NumberUtil.parseDouble(resultMap.get("totalUseCouponsAmount") + "",
							0.0);
					Double totalAmount = NumberUtil.parseDouble(resultMap.get("totalAmount") + "", 0.0);

					if (useCoupons && Double.parseDouble(money) >= totalAmount) { // 使用优惠券且全额还款
						money = totalUseCouponsAmount.toString();
						useCoupons = true;
					} else {
						useCoupons = false;
					}
				}

				logger.info("【ALiPayController.alipay】还款orderId:" + orderId + ",money=" + money + ",useCoupons="
						+ useCoupons + ",resultMap=" + resultMap);

			} else if (payType == 2) { // 展期
				result = productService.calcZhanQiCost(orderId); // 根据订单号计算展期金额
				LoanInfo loanInfo = (LoanInfo) result.getResult();
				if (loanInfo != null) {
					money = loanInfo.getAmt(); // 展期金额
				}
				logger.info(
						"【ALiPayController.alipay】展期orderId:" + orderId + ",loanInfo=" + JSON.toJSONString(loanInfo));
			}

			if (!"000".equals(result.getCode())) {
				logger.info("【ALiPayController.alipay】orderId:" + orderId + ",payType:" + payType + ",不满足支付条件,result:"
						+ JSON.toJSONString(result));
				request.setAttribute("channel", channel);
				request.setAttribute("payType", payType);
				logger.info("支付宝支付：不满足展期条件," + orderId);
				if (payType == 2) {
					return "alipay_fail_zq";
				} else {
					return "alipay_fail";
				}
			}

			BwAlipayOrder bwAlipayOrder = null;

			if (!CommUtils.isNull(orderId)) {
				BigDecimal chargeMoney = new BigDecimal(money).setScale(2, BigDecimal.ROUND_UP);

				bwAlipayOrder = bwAlipayOrderService.queryBwAlipayOrderType(orderId, 0, money); // 0未处理的订单

				int fz = 0;
				if (bwAlipayOrder != null) {
					fz = (int) (((new Date()).getTime() - bwAlipayOrder.getCreateTime().getTime()) / 60000);
					if (fz >= 5) {
						bwAlipayOrder.setType(-1); // -1 已过期
						bwAlipayOrderService.update(bwAlipayOrder);
					}

					if (!("X" + payType + channel).equals(bwAlipayOrder.getRemark())) {
						bwAlipayOrder.setRemark("X" + payType + channel);
						bwAlipayOrderService.update(bwAlipayOrder);
					}
				}

				if (bwAlipayOrder == null || fz >= 5) {
					bwAlipayOrder = new BwAlipayOrder();
					bwAlipayOrder.setAlipayType(2); // 这里要改 之前是易倍佳
					bwAlipayOrder.setMoney(new BigDecimal(money));
					bwAlipayOrder.setType(0); // 未处理
					bwAlipayOrder.setOrderId(orderId);
					bwAlipayOrder.setOtherOrderNo("");
					bwAlipayOrder.setAlipayNo("");
					bwAlipayOrder.setCreateTime(new Date());
					bwAlipayOrder.setRemark("X" + payType + channel);
					bwAlipayOrder.setChargeMoney(chargeMoney);
					bwAlipayOrderService.save(bwAlipayOrder);
				}
			} else {
				logger.info("支付宝支付：系统异常," + orderId);
				request.setAttribute("channel", channel);
				request.setAttribute("payType", payType);
				if (payType == 2) {
					return "alipay_fail_zq";
				} else {
					return "alipay_fail";
				}
			}

			String out_trade_no = CommUtils.toString(bwAlipayOrder.getId()) + "X" + payType + channel; // 拼接

			hm.put("method", AliPayConstant.CREATEMETHOD);
			hm.put("appid", AliPayConstant.APPID);
			hm.put("mch_id", AliPayConstant.MCH_ID);
			hm.put("nonce_str", nonce_str);
			hm.put("body", body);
			hm.put("out_trade_no", out_trade_no); // 传递的是拼接后商户订单号
			hm.put("total_fee", money);
			hm.put("notify_url", "/alipayNotify.do"); // 接收支付宝支付结果回调通知
			// hm.put("return_url", ""); // 支付成功跳转地址

			Map<String, String> params = SignUtils.paraFilter(hm); // 过滤参数
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
			SignUtils.buildPayParams(buf, params, false);
			String preStr = buf.toString();
			String sign = MD5.sign(preStr, "&key=" + AliPayConstant.KEY, "utf-8").toUpperCase(); // 生成签名
			hm.put("sign", sign);

			logger.info("开始调用支付宝接口");
			String xmlStr = CorefireHttpPost.connect(AliPayConstant.COMMON_URL, hm); // 对支付宝发送请求

			if (CommUtils.isNull(xmlStr)) {
				return "{\"code\":0,\"msg\": 调用支付宝请求异常}";			
			}

			logger.info("支付宝回调结果==》" + xmlStr);

			Map<String, String> resultMap = XmlUtils.xml2map(xmlStr, "xml"); // 解析xml
			
			String result_code = resultMap.get("result_code");
			String return_code = resultMap.get("return_code");
			String code_url = resultMap.get("code_url");

			if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
				if (!StringUtils.isEmpty(code_url)) {
					request.setAttribute("channel", channel);
					request.setAttribute("payType", payType);
					request.setAttribute("code_url", code_url);
				}
			}

		} catch (Exception e) {
			request.setAttribute("channel", channel);
			request.setAttribute("payType", payType);
			logger.error("调用支付宝预创建接口异常" + e);
			if (payType == 2) {
				return "alipay_fail_zq";
			} else {
				return "alipay_fail";
			}
		}
		return "alijspayConfirm";
	}

	@RequestMapping(value = "/alipayNotify.do")
	@ResponseBody
	public String aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("进入支付宝通知接口");
		SortedMap map = new TreeMap();
		String idStr = null;
		String result = null;
		String nonce_str = UUID.randomUUID().toString().replace("-", "");
		AliQueryRequest aliQueryRequest = null;
		BwAlipayOrder bwAlipayOrder = null;
		try {
			String params = IOUtils.toString(request.getInputStream(), "utf-8"); // 获取参数
			if (CommUtils.isNull(params)) {
				map.put("return_code", "FAIL");
				map.put("return_msg", "ACQ.INVALID_PARAMETER");
				result = XmlUtils.parseXML(map);
				return result;
			}

			Map<String, String> resultMap = XmlUtils.xml2map(params, "xml"); // 解析xml
			System.out.println("请求返回数据：" + params);

			String return_code = resultMap.get("return_code"); // 获取通信结果

			if (!"SUCCESS".equals(return_code)) {
				map.put("return_msg", resultMap.get("return_msg"));
				result = XmlUtils.parseXML(map);
				return result;
			} else {
				if (!SignUtils.checkParam(resultMap, AliPayConstant.KEY)) {
					map.put("return_code", "FAIL");
					map.put("return_msg", "ACQ.INVALID_SIGN"); // 验证签名失败
					result = XmlUtils.parseXML(map);
					return result;
				}
			}

			if ("SUCCESS".equals(resultMap.get("result_code"))) { // 成功
				String out_trade_no = resultMap.get("out_trade_no"); // 商户订单号
				String paytime = resultMap.get("time_end"); // 支付时间
				String openid = resultMap.get("openid"); // 买家支付宝用户 ID
				String transactionId = resultMap.get("transaction_id"); // 支付宝交易流水号
				BigDecimal total_fee = new BigDecimal(resultMap.get("total_fee")); // 金额

				// bwAlipayOrder ID
				idStr = out_trade_no.split("X")[0];

				// 防重复请求锁
				boolean lockRequest = ControllerUtil.lockRequest(RedisKeyConstant.LOCK_KEY_PRE + idStr, 30);
				if (!lockRequest) {
					// 否则返回失败
					logger.info("========payNotify重复回调orderId:" + idStr);
					map.put("return_code", "FAIL");
					map.put("return_msg", "重复");
					result = XmlUtils.parseXML(map);
					return result;
				}

				String payType = out_trade_no.split("X")[1].substring(0, 1);
				String appRequest = out_trade_no.split("X")[1].substring(1, 2);
				Long id = Long.parseLong(idStr);

				bwAlipayOrder = bwAlipayOrderService.queryBwAlipayOrderById(id, 0);
				if (CommUtils.isNull(bwAlipayOrder)) {
					map.put("return_code", "FAIL");
					map.put("return_msg", "ACQ.TRADE_NOT_EXIST");
					result = XmlUtils.parseXML(map);
					return result;
				} else {
					if ("2".equals(bwAlipayOrder.getType())) { // 如果是支付成功，代表重复推送了
						map.put("return_code", "SUCCESS");
						map.put("return_msg", "已经支付成功，订单重复推送");
						result = XmlUtils.parseXML(map);
						return result;
					}

					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date creatTime = format.parse(paytime);

					bwAlipayOrder.setMoney(total_fee);
					bwAlipayOrder.setUpdateTime(creatTime);
					bwAlipayOrder.setType(2); // 支付成功
					bwAlipayOrder.setAlipayType(2); // 不知道什么渠道
					bwAlipayOrder.setMoney(total_fee); // 金额
					bwAlipayOrder.setRemark(out_trade_no);
					bwAlipayOrder.setAlipayNo(transactionId);
					bwAlipayOrderService.update(bwAlipayOrder);

					RepaymentDto repaymentDto = new RepaymentDto();
					repaymentDto.setOrderId(bwAlipayOrder.getOrderId());
					repaymentDto.setType(NumberUtil.parseInteger(payType, null));
					repaymentDto.setTerminalType(NumberUtil.parseInteger(appRequest, null));
					repaymentDto.setTradeNo(transactionId);
					repaymentDto.setUseCoupon(true);
					repaymentDto.setAmount(bwAlipayOrder.getMoney().doubleValue());
					repaymentDto.setPayChannel(5); // 支付宝渠道
					repaymentDto.setTradeTime(new Date());
					repaymentDto.setTradeType(2); // 转账
					repaymentDto.setTradeCode(resultMap.get("result_code"));

					// 支付成功
					logger.info("支付宝还款开始idStr:" + idStr);
					AppResponseResult appResponseResult = iBwRepaymentService.updateOrderByTradeMoney(repaymentDto);
					if ("000".equals(appResponseResult.getCode())) {
						map.put("return_code", "SUCCESS");
						map.put("return_msg", "支付成功");
						logger.info("支付宝还款成功idStr：" + idStr + appResponseResult.getMsg());
						result = XmlUtils.parseXML(map);
						return result;
					} else {
						map.put("return_code", "FAIL");
						map.put("return_msg", "支付失败原因：" + appResponseResult.getMsg());
						logger.info("支付宝还款失败idStr：" + idStr + appResponseResult.getMsg());
						result = XmlUtils.parseXML(map);
						return result;
					}
				}
			} else {
				bwAlipayOrder.setType(3); // 状态更改为支付失败
				bwAlipayOrderService.update(bwAlipayOrder);
				map.put("return_code", resultMap.get("err_code"));
				map.put("return_msg", resultMap.get("err_code_des"));
				result = XmlUtils.parseXML(map);
				return result;
			}

		} catch (Exception e) {
			logger.error("支付宝通知回调接口异常", e);
		} finally {
			if (!CommUtils.isNull(idStr)) {
				logger.info("========payNotify删除回调锁orderId:" + idStr);
				RedisUtils.del(RedisKeyConstant.LOCK_KEY_PRE + idStr);
			}
		}

		result = XmlUtils.parseXML(map);
		return result;
	}

	@RequestMapping(value = "/aliPayQuery.do")
	@ResponseBody
	public AppResponseResult aliPayQuery(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			logger.info("进入支付宝订单查询接口");
			String nonce_str = UUID.randomUUID().toString().replace("-", ""); // 随机字符串
			String orderIdStr = request.getParameter("orderId");
			if (CommUtils.isNull(orderIdStr)) {
				appResponseResult.setCode("-1");
				appResponseResult.setMsg("参数有误");
				return appResponseResult;
			}

			// 拆分数据
			String aliPayId = orderIdStr.split("X")[0]; // 订单id
			String payType = orderIdStr.split("X")[1].substring(0, 1); // 支付类型
			String channel = orderIdStr.split("X")[1].substring(1, 2); // 渠道

			// 为支付宝查询接口填充数据
			SortedMap hm = new TreeMap();
			hm.put("method", AliPayConstant.QUERYMETHOD);
			hm.put("mch_id", AliPayConstant.MCH_ID);
			hm.put("appid", AliPayConstant.APPID);
			hm.put("nonce_str", nonce_str);
			hm.put("out_trade_no", orderIdStr);

			Map<String, String> params = SignUtils.paraFilter(hm); // 过滤参数
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
			SignUtils.buildPayParams(buf, params, false);
			String preStr = buf.toString();
			String sign = MD5.sign(preStr, "&key=" + AliPayConstant.KEY, "utf-8").toUpperCase(); // 生成签名
			hm.put("sign", sign);

			// 对支付宝发送请求
			logger.info("开始调用支付宝查询接口");
			String xmlStr = CorefireHttpPost.connect(AliPayConstant.COMMON_URL, hm);
			logger.info("支付宝查询接口结果==》" + xmlStr);

			// 将支付宝回调参数转换成map
			Map<String, String> resultMap = XmlUtils.xml2map(xmlStr, "xml");
			Long id = Long.parseLong(aliPayId); // BwAlipayOrder 订单编号

			BwAlipayOrder bwAlipayOrder = bwAlipayOrderService.queryBwAlipayOrderById(id);

			if (!"SUCCESS".equals(resultMap.get("return_code"))) {
				appResponseResult.setCode("-1");
				appResponseResult.setMsg("支付宝查询接口通讯异常");
				return appResponseResult;
			}

			if (!"SUCCESS".equals(resultMap.get("result_code"))) {
				appResponseResult.setCode("-1");
				appResponseResult
						.setMsg("支付宝查询接口错误：" + resultMap.get("err_code") + "原因：" + resultMap.get("err_code_des"));
				return appResponseResult;
			}

			String trade_state = resultMap.get("trade_state"); // 支付状态

			if (bwAlipayOrder != null) {
				if ("SUCCESS".equals(trade_state)) {
					bwAlipayOrder.setType(2); // 支付成功
					bwAlipayOrder.setAlipayNo(resultMap.get("transaction_id"));
					bwAlipayOrder.setRemark(orderIdStr);

					// 支付成功
					RepaymentDto repaymentDto = new RepaymentDto();
					repaymentDto.setOrderId(bwAlipayOrder.getOrderId());
					repaymentDto.setType(NumberUtil.parseInteger(payType, null));
					repaymentDto.setTerminalType(NumberUtil.parseInteger(channel, null));
					repaymentDto.setTradeNo(resultMap.get("transaction_id"));
					repaymentDto.setUseCoupon(true);
					repaymentDto.setAmount(bwAlipayOrder.getMoney().doubleValue());
					repaymentDto.setPayChannel(5);
					repaymentDto.setTradeType(2);
					repaymentDto.setTradeCode(resultMap.get("result_code"));
					repaymentDto.setTradeTime(new Date());
					bwAlipayOrder.setUpdateTime(new Date());
					bwAlipayOrderService.update(bwAlipayOrder);

					// 支付成功
					logger.info("支付宝查询还款开始idStr:" + orderIdStr);
					appResponseResult = iBwRepaymentService.updateOrderByTradeMoney(repaymentDto);

					if ("000".equals(appResponseResult.getCode())) {
						logger.info("支付宝查询还款成功idStr：" + orderIdStr + appResponseResult.getMsg());
					} else {
						logger.info("支付宝查询还款失败idStr：" + orderIdStr + appResponseResult.getMsg());
					}

					appResponseResult.setCode("0");
					appResponseResult.setMsg("支付成功");
					return appResponseResult;
				} else {
					bwAlipayOrder.setType(3);
					bwAlipayOrderService.update(bwAlipayOrder);
					appResponseResult.setCode("-1");
					appResponseResult.setMsg("支付失败");
					return appResponseResult;
				}

			} else {
				appResponseResult.setCode("-1");
				appResponseResult.setMsg("未查询到支付订单信息");
				return appResponseResult;
			}
		} catch (Exception e) {
			logger.error("支付宝订单查询接口异常", e);
		}

		return appResponseResult;
	}

	/**
	 * 撤销订单接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/aliPayReverse.do")
	@ResponseBody
	public AppResponseResult reverseOrder(HttpServletRequest request, HttpServletResponse response) {
		logger.info("进入支付宝订单撤销接口");
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			String nonce_str = UUID.randomUUID().toString().replace("-", ""); // 随机字符串
			String transaction_id = request.getParameter("transaction_id");

			if (StringUtils.isEmpty(transaction_id)) {
				appResponseResult.setCode("-1");
				appResponseResult.setMsg("支付宝交易订单号为空");
				return appResponseResult;
			}

			// 填充参数
			SortedMap hm = new TreeMap();
			hm.put("appid", AliPayConstant.APPID);
			hm.put("mch_id", AliPayConstant.APPID);
			hm.put("transaction_id", transaction_id);
			hm.put("nonce_str", nonce_str);

			Map<String, String> params = SignUtils.paraFilter(hm); // 过滤参数
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
			SignUtils.buildPayParams(buf, params, false);
			String preStr = buf.toString();
			String sign = MD5.sign(preStr, "&key=" + AliPayConstant.KEY, "utf-8").toUpperCase(); // 生成签名
			hm.put("sign", sign);

			// 对支付宝发送请求
			logger.info("开始调用支付宝撤回订单接口");
			String xmlStr = CorefireHttpPost.connect(AliPayConstant.COMMON_URL, hm);
			logger.info("支付宝撤回订单接口结果==》" + xmlStr);

			// 将支付宝回调参数转换成map
			Map<String, String> resultMap = XmlUtils.xml2map(xmlStr, "xml");
			logger.info("撤回订单返回结果" + resultMap);

			// 下面是撤销订单之后的逻辑

			appResponseResult.setCode("0");
			appResponseResult.setMsg("成功");

		} catch (Exception e) {
			logger.error("撤回订单接口异常", e);
			appResponseResult.setCode("-1");
			appResponseResult.setMsg("撤回订单接口异常");
		}

		return appResponseResult;
	}

	/**
	 * 申请退款接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/aliPayRefund.do")
	@ResponseBody
	public AppResponseResult refundPay(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			logger.info("进入支付宝申请退款接口");
			String nonce_str = UUID.randomUUID().toString().replace("-", ""); // 随机字符串
			String transaction_id = request.getParameter("transaction_id");

			if (StringUtils.isEmpty(transaction_id)) {
				appResponseResult.setCode("-1");
				appResponseResult.setMsg("支付宝交易订单号为空");
				return appResponseResult;
			}

			SortedMap<String, String> hm = new TreeMap<>();
			hm.put("method", "mbupay.alipay.refund");
			hm.put("appid", AliPayConstant.APPID);
			hm.put("mch_id", AliPayConstant.MCH_ID);
			hm.put("transaction_id", transaction_id); // 商户退款单号
			hm.put("refund_fee", ""); // 退款金额
			hm.put("op_user_id", AliPayConstant.MCH_ID); // 操作员
			hm.put("nonce_str", nonce_str);

			Map<String, String> params = SignUtils.paraFilter(hm); // 过滤参数
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
			SignUtils.buildPayParams(buf, params, false);
			String preStr = buf.toString();
			String sign = MD5.sign(preStr, "&key=" + AliPayConstant.KEY, "utf-8").toUpperCase(); // 生成签名
			hm.put("sign", sign);

			// 对支付宝发送请求
			logger.info("开始调用支付宝申请退款接口");
			String xmlStr = CorefireHttpPost.connect(AliPayConstant.COMMON_URL, hm);
			logger.info("支付宝申请退款接口结果==》" + xmlStr);

			// 将支付宝回调参数转换成map
			Map<String, String> resultMap = XmlUtils.xml2map(xmlStr, "xml");
			logger.info("支付宝申请退款返回结果" + resultMap);

			// 下面是申请退款之后的逻辑

		} catch (Exception e) {
			logger.error("支付宝申请退款接口异常", e);
			appResponseResult.setCode("-1");
			appResponseResult.setMsg("支付宝申请退款接口异常");
		}

		return appResponseResult;
	}
}
