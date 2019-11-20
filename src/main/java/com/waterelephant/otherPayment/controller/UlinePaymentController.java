package com.waterelephant.otherPayment.controller;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.otherPayment.UlineService;
import com.beadwallet.service.utils.HttpRequest;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.LoanInfo;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwWinpayOrder;
import com.waterelephant.otherPayment.util.MD5;
import com.waterelephant.otherPayment.util.StreamUtils;
import com.waterelephant.otherPayment.util.Xml;
import com.waterelephant.service.BwWinpayOrderService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * Module:
 * 
 * UlinePaymentController.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/ulinePayment")
public class UlinePaymentController {

	private Logger logger = Logger.getLogger(UlinePaymentController.class);

	@Resource
	private ProductService productService;

	@Resource
	private BwWinpayOrderService bwWinpayOrderService;

	@Resource
	private IBwRepaymentService iBwRepaymentService;

	private boolean testBool = false;

	@RequestMapping(value = "/winChatPay.do")
	public void winChatPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String orderId = request.getParameter("orderId");// 单号
			String isUseCoupon = request.getParameter("isUseCoupon");// 优惠卷是否使用
			String payType = request.getParameter("payType");// 1还款 2展期
			String channel = request.getParameter("channel"); // 来源
			String money = request.getParameter("money");// 还款金额
			String productType = request.getParameter("productType");
			Map<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("orderId", orderId);
			paramsMap.put("isUseCoupon", isUseCoupon);
			paramsMap.put("payType", payType);
			paramsMap.put("channel", channel);
			paramsMap.put("money", money);
			paramsMap.put("productType", productType);
			String params = JSON.toJSONString(paramsMap);
			String appid = SystemConstant.ULINE_APP_ID;
			String transfer_url = SystemConstant.ULINE_TRANSFER_URL;
			logger.info("params:" + params);

			String code_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
					+ transfer_url + "&response_type=code&scope=snsapi_base&state=" + params + "#wechat_redirect";
			logger.info("code_url:" + code_url);
			response.sendRedirect(code_url);

		} catch (Exception e) {
			logger.error("异常", e);
		}
		return;
	}

	@RequestMapping(value = "/transfer.do")
	public void transfer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String code = request.getParameter("code");

		String state = request.getParameter("state");
		String redirect_url = SystemConstant.ULINE_REDIRECT_URL;
		// String str = HttpRequest.doGet(redirect_url + "?code=" + code + "&state=" + state);
		response.sendRedirect(redirect_url + "?code=" + code + "&state=" + state);
	}

	@RequestMapping(value = "/result.do")
	public String result(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String state = request.getParameter("state");
		String money = request.getParameter("money");
		System.out.println("---------------数据包---------------");
		System.out.println(state);
		request.setAttribute("getBrandWCPayRequest", state);
		request.setAttribute("money", money);
		// request.getRequestDispatcher("WEB-INF/pages/winpay_success.jsp").forward(request, response);
		return "winpay_success";
	}

	/**
	 * 微信支付请求
	 */
	@RequestMapping(value = "/winChatPayPush.do")
	public String winChatPayPush(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fialUrl = SystemConstant.ULINE_FAIL_HK + "?productType=" + 1;
		try {

			String code = request.getParameter("code");

			String state = request.getParameter("state");

			JSONObject paramsJson = JSONObject.parseObject(state);

			String orderIdStr = paramsJson.getString("orderId");
			Long orderId = NumberUtil.parseLong(orderIdStr, null);
			String isUseCoupon = paramsJson.getString("isUseCoupon");

			Integer payType = NumberUtil.parseInteger(paramsJson.getString("payType"), null);// 1 还款 2 展期
			String channel = paramsJson.getString("channel"); // 来源
			String money = paramsJson.getString("money");// 还款金额
			String productType = paramsJson.getString("productType");
			if (CommUtils.isNull(productType)) {
				productType = "1";
			}

			request.setAttribute("productType", productType);
			request.setAttribute("payType", CommUtils.toString(payType));
			if (2 == payType) {
				fialUrl = SystemConstant.ULINE_FAIL_ZQ;
			} else {
				fialUrl = SystemConstant.ULINE_FAIL_HK + "?productType=" + productType;
			}

			String openid = null;
			if (!CommUtils.isNull(code)) {
				String appid = SystemConstant.ULINE_APP_ID;
				String secret = SystemConstant.ULINE_SECRET;
				String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret="
						+ secret + "&code=" + code + "&grant_type=authorization_code";

				String oppid = HttpRequest.doGet(requestUrl);
				JSONObject oppidObj = JSONObject.parseObject(oppid);
				openid = (String) oppidObj.get("openid");
				logger.info("oppid：" + oppid);
				logger.info("openid：" + openid);
				if (CommUtils.isNull(openid)) {
					return "redirect:" + fialUrl;
				}
			} else {
				return "redirect:" + fialUrl;
			}
			// String money = "0.01";

			logger.info("聚合支付微信请求：orderIdStr:" + orderIdStr + ",payType:" + payType + ",channel:" + channel + ",money:"
					+ money + ",isUseCoupon:" + isUseCoupon);

			if (CommUtils.isNull(orderIdStr) || CommUtils.isNull(orderId) || CommUtils.isNull(payType)
					|| (payType != 1 && payType != 2) || CommUtils.isNull(channel)
					|| (CommUtils.isNull(money) && payType == 1)) {
				logger.info("数据缺失");
				return "redirect:" + fialUrl;
			}

			AppResponseResult result = null;
			// 是否使用了优惠券
			boolean useCoupons = false;
			if ("1".equals(isUseCoupon)) {
				useCoupons = true;
			}
			LoanInfo loanInfo = null;
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
				logger.info("【UlinePaymentController.alipay】还款orderId:" + orderId + ",money=" + money + ",useCoupons="
						+ useCoupons + ",resultMap=" + resultMap);
			} else if (payType == 2) {// 展期
				result = productService.calcZhanQiCost(orderId);
				loanInfo = (LoanInfo) result.getResult();
				if (loanInfo != null) {
					money = loanInfo.getAmt();
				}
				logger.info("【UlinePaymentController.alipay】展期orderId:" + orderId + ",loanInfo="
						+ JSON.toJSONString(loanInfo));
			}

			if (!"000".equals(result.getCode())) {
				logger.info("【UlinePaymentController.winChatPay】orderId:" + orderId + ",payType:" + payType
						+ ",不满足支付条件,result:" + JSON.toJSONString(result));
				return "redirect:" + fialUrl;
			}

			BigDecimal chargeMoney = null;
			BwWinpayOrder bwWinpayOrder = null;
			if (orderIdStr != null && orderIdStr.trim().length() > 0) {

				if (CommUtils.isNull(SystemConstant.ULINE_CHARGE)) {
					chargeMoney = new BigDecimal(0);
				} else {
					chargeMoney = new BigDecimal(money).multiply(new BigDecimal(SystemConstant.ULINE_CHARGE))
							.setScale(2, BigDecimal.ROUND_UP);
				}

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
					bwWinpayOrder.setCreateTime(new Date());
					bwWinpayOrder.setWinpayType(2);
					bwWinpayOrder.setMoney(new BigDecimal(money));
					bwWinpayOrder.setType(0);
					bwWinpayOrder.setOrderId(orderId);
					bwWinpayOrder.setOtherOrderNo("");
					bwWinpayOrder.setWinpayNo("");
					bwWinpayOrder.setRemark("W" + payType + channel);
					bwWinpayOrder.setChargeMoney(chargeMoney);
					bwWinpayOrderService.save(bwWinpayOrder);
				}
			} else {
				return "redirect:" + fialUrl;
			}

			if (testBool) {// 测试，回调取测试金额，不取1分钱
				bwWinpayOrder.setMoney(new BigDecimal(money));
				money = "0.01";
				chargeMoney = new BigDecimal(0.01);
			}
			Double db = (new BigDecimal(money).add(chargeMoney)).multiply(new BigDecimal(100)).doubleValue();
			String outTradeNo = CommUtils.toString(bwWinpayOrder.getId()) + "W" + payType + channel;

			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("out_trade_no", outTradeNo);
			paramsMap.put("total_fee", db.intValue());
			paramsMap.put("openid", openid);

			try {
				String returnStr = UlineService.winxPay(JSON.toJSONString(paramsMap));
				returnStr = returnStr.replace("\\", "").replace("\"{", "{").replace("}\"", "}");
				logger.info("数据返回：" + returnStr);
				if (!CommUtils.isNull(returnStr)) {
					JSONObject jsonObjectXml = JSONObject.parseObject(returnStr);

					if (jsonObjectXml != null) {
						JSONObject jsonObject = JSONObject.parseObject(jsonObjectXml.getString("xml"));
						String js_prepay_info = jsonObject.getString("js_prepay_info");
						if ("SUCCESS".equals(jsonObject.get("result_code"))) {
							logger.info("数据包:" + js_prepay_info);
							bwWinpayOrder.setType(1);
							bwWinpayOrderService.update(bwWinpayOrder);
							request.setAttribute("getBrandWCPayRequest", js_prepay_info);
							request.setAttribute("money", bwWinpayOrder.getMoney());

							if (2 == payType) {
								request.setAttribute("term", loanInfo.getTermStr());
								request.setAttribute("xuDaiRepayTime", loanInfo.getZhanqiRepayTime());
								request.setAttribute("totalXudaiAmount", loanInfo.getAmt());
								request.setAttribute("borrowAmount", loanInfo.getBorrowAmount());
							}
							// 吊起微信H5支付
							// request.getRequestDispatcher("/WEB-INF/pages/winpay_success.jsp").forward(request,
							// response);
							// String url = "https://www.beadwallet.com/loanpage/test/winpay_success.html";
							// response.sendRedirect(url + "?getBrandWCPayRequest=" + js_prepay_info);
							// response.sendRedirect("https://106.14.238.126:8092/beadwalletloanapp/ulinePayment/result.do"
							// + "?state=" + js_prepay_info);
							// request.setAttribute("getBrandWCPayRequest", js_prepay_info);
							// response.sendRedirect("https://www.beadwallet.com/beadwalletloanapp/ulinePayment/result.do"
							// + "?state=" + js_prepay_info + "&money=" + bwWinpayOrder.getMoney());
							return "winpay_success";
						} else {
							return "redirect:" + fialUrl;
						}
					}
				} else {
					logger.info("数据返回为空:");
				}

			} catch (Exception e) {
				logger.error("优畅异常：", e);
			}
		} catch (Exception e) {
			return "redirect:" + fialUrl;
		}

		return "redirect:" + fialUrl;
	}

	// 结果回调
	@RequestMapping(value = "/payNotify.do")
	@ResponseBody
	public String payNotify(HttpServletRequest request, HttpServletResponse response) {
		String idStr = null;
		Map<String, String> returnMap = new HashMap<>();
		try {
			String data = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
			data = java.net.URLDecoder.decode(data, "utf-8");
			// data =
			// "<xml><sub_is_subscribe>Y</sub_is_subscribe><time_end>20170809180256</time_end><openid>oGVqXs75PaGaFvy-xsQ471JjVUsw</openid><is_subscribe>N</is_subscribe><transaction_id>4100015316455150227293300000024</transaction_id><total_fee>1</total_fee><out_trade_no>71W12</out_trade_no><return_code>SUCCESS</return_code><nonce_str>9e321ead874ea63e3af3b2903263407b</nonce_str><fee_type>CNY</fee_type><mch_id>100015316455</mch_id><cash_fee>1</cash_fee><trade_state>SUCCESS</trade_state><bank_type>CFT</bank_type><sub_appid>wx7e629f5c5fe6b005</sub_appid><sub_openid>ozm1wwGkYuuZYFtRYdC4igqAphRg</sub_openid><trade_type>JSAPI</trade_type><out_transaction_id>4004472001201708095348734808</out_transaction_id><sign>61ACE50815C30F9794C1C4D14B512C01</sign><result_code>SUCCESS</result_code><ul_mch_id>100015316455</ul_mch_id></xml>";

			String dataJson = Xml.xmlToJSON(data);
			JSONObject jsonObjectXml = null;
			if (dataJson != null) {
				jsonObjectXml = JSONObject.parseObject(dataJson);
			} else {
				returnMap.put("return_code", "SUCCESS");
				returnMap.put("return_msg", "");
				return returnStr(returnMap);
			}
			JSONObject jsonObject = null;
			if (jsonObjectXml != null) {
				jsonObject = JSONObject.parseObject(jsonObjectXml.getString("xml"));
			} else {
				returnMap.put("return_code", "SUCCESS");
				returnMap.put("return_msg", "");
				return returnStr(returnMap);
			}
			if (jsonObject == null) {
				returnMap.put("return_code", "FAIL");
				returnMap.put("return_msg", "");
				return returnStr(returnMap);
			}

			logger.info("优畅微信支付反馈：data:" + jsonObject.toJSONString());

			if (!MD5.checkSign(jsonObject.toJSONString(), SystemConstant.ULINE_SIGN_KEY)) {
				returnMap.put("return_code", "FAIL");
				returnMap.put("return_msg", "");
				logger.info("优畅微信支付反馈：验签失败");
				return returnStr(returnMap);
			}

			if ("SUCCESS".equals(CommUtils.toString(jsonObject.get("return_code")))) {
				String outTradeNo = CommUtils.toString(jsonObject.get("out_trade_no"));

				// 返回成功
				idStr = outTradeNo.split("W")[0];
				String payType = outTradeNo.split("W")[1].substring(0, 1);
				String appRequest = outTradeNo.split("W")[1].substring(1, 2);
				// 防重复请求锁
				boolean lockRequest = ControllerUtil.lockRequest(RedisKeyConstant.LOCK_KEY_PRE + idStr, 30);
				if (!lockRequest) {// 重复回调
					// 否则返回失败
					logger.info("========payNotify重复回调outTradeNo:" + outTradeNo);
					response.getOutputStream().write("fail".getBytes());
					returnMap.put("return_code", "FAIL");
					returnMap.put("return_msg", "短时间重复回调");
					return returnStr(returnMap);
				}
				Long id = Long.parseLong(idStr);
				BwWinpayOrder bwWinpayOrder = bwWinpayOrderService.queryBwWinpayOrderById(id);
				if (bwWinpayOrder != null) {
					bwWinpayOrder.setWinpayType(2);

					bwWinpayOrder.setOtherOrderNo(CommUtils.toString(jsonObject.get("transaction_id")));
					bwWinpayOrder.setRemark(outTradeNo);
				} else {
					// 否则返回失败
					returnMap.put("return_code", "SUCCESS");
					returnMap.put("return_msg", "支付记录不存在");
					return returnStr(returnMap);
				}
				if ("SUCCESS".equals(CommUtils.toString(jsonObject.get("result_code")))) {
					bwWinpayOrder.setType(2);
					bwWinpayOrder.setUpdateTime(new Date());
					bwWinpayOrderService.update(bwWinpayOrder);

					RepaymentDto repaymentDto = new RepaymentDto();
					repaymentDto.setOrderId(bwWinpayOrder.getOrderId());
					repaymentDto.setType(NumberUtil.parseInteger(payType, null));
					repaymentDto.setTerminalType(NumberUtil.parseInteger(appRequest, null));
					repaymentDto.setTradeNo(bwWinpayOrder.getOtherOrderNo());
					repaymentDto.setUseCoupon(true);
					repaymentDto.setAmount(bwWinpayOrder.getMoney().doubleValue());
					repaymentDto.setPayChannel(6);
					repaymentDto.setTradeTime(new Date());
					repaymentDto.setTradeType(2);
					// repaymentDto.setTradeCode(payResult);

					// 支付成功
					logger.info("优畅微信还款开始idStr:" + idStr);
					AppResponseResult appResponseResult = iBwRepaymentService.updateOrderByTradeMoney(repaymentDto);

					if ("000".equals(appResponseResult.getCode())) {
						logger.info("优畅微信还款成功idStr：" + idStr + appResponseResult.getMsg());
					} else {
						logger.info("优畅微信还款失败idStr：" + idStr + appResponseResult.getMsg());
					}
					returnMap.put("return_code", "SUCCESS");
					returnMap.put("return_msg", "");
					// return JSON.toJSONString(returnMap);
				} else {
					returnMap.put("return_code", "SUCCESS");
					returnMap.put("return_msg", "");
				}

			} else {
				returnMap.put("return_code", "SUCCESS");
				returnMap.put("return_msg", "");
			}

		} catch (Exception ex) {
			logger.error("微信回调异常：", ex);
			returnMap.put("return_code", "SUCCESS");
			returnMap.put("return_msg", "数据有误");
			// return JSON.toJSONString(returnMap);
		} finally {
			if (!CommUtils.isNull(idStr)) {
				logger.info("========payNotify删除回调锁orderId:" + idStr);
				// RedisUtils.del(RedisKeyConstant.LOCK_KEY_PRE + idStr);
			}
		}
		return returnStr(returnMap);
	}

	public String returnStr(Map<String, String> returnMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>").append("<return_code>").append(returnMap.get("return_code")).append("</return_code>")
				.append("<return_msg>").append(returnMap.get("return_msg")).append("</return_msg>").append("</xml>");
		return sb.toString();
	}

	@RequestMapping(value = "/winChatPayPush1.do")
	public String winChatPayPush1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("getBrandWCPayRequest", "ssssssss");
		request.setAttribute("money", "11.1234");
		request.setAttribute("payType", "2");
		// var str =
		// "http://106.14.238.126/weixinApp/html/Repayment/renewalSuccess.html?term="+<%=term%>+"&xuDaiRepayTime="+<%=xuDaiRepayTime%>+"&totalXudaiAmount="+<%=totalXudaiAmount%>+"&borrowAmount="+<%=borrowAmount%>;
		Integer payType = 2;
		if (2 == payType) {
			request.setAttribute("term", "打完");
			request.setAttribute("xuDaiRepayTime", "2018:55");
			request.setAttribute("totalXudaiAmount", "124.00");
			request.setAttribute("borrowAmount", "124.0");
		}
		return "winpay_success1";
	}

	public static void main(String[] args) {
		BigDecimal chargeMoney = new BigDecimal("525.00").multiply(new BigDecimal("0.005")).setScale(2,
				BigDecimal.ROUND_UP);
		System.out.println(chargeMoney);
	}

}
