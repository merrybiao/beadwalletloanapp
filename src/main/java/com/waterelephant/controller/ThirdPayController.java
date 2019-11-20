package com.waterelephant.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.entity.lianlian.CardQueryResult;
import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.entity.lianlian.NotifyResult;
import com.beadwallet.entity.lianlian.PlanResult;
import com.beadwallet.entity.lianlian.RepayRequest;
import com.beadwallet.entity.lianlian.RepaymentPlan;
import com.beadwallet.entity.lianlian.RepaymentResult;
import com.beadwallet.entity.lianlian.SignalLess;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.utils.RSAUtil;
import com.waterelephant.annotation.LockAndSyncRequest;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwPayLog;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwPayLogService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;

/**
 * 三方支付
 */
@Controller
@RequestMapping("/thirdPay")
public class ThirdPayController {
	private Logger logger = Logger.getLogger(ThirdPayController.class);
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BwPayLogService bwPayLogService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private IBwRepaymentService bwRepaymentService;
	// 是否测试
	private boolean testBool = SystemConstant.WITHHOLD_TEST_BOOL;

	@ResponseBody
	@RequestMapping("/payment.do")
	@LockAndSyncRequest(redisKeyAfterByRequestName = "orderId")
	public AppResponseResult payment(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		Long orderId = NumberUtil.parseLong(request.getParameter("orderId"), 0L);
		try {
			BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
			if (bwOrder == null) {
				result.setCode("110");
				result.setMsg("工单不存在");
				return result;
			}
			Integer channel = 4;
			Integer payType = 1;// 1.还款，2.展期
			Double payMoney = 0.0;
			if (payType == 1) {// 还款
				result = productService.calcRepaymentCost(orderId);
				Map<String, Object> resultMap = (Map<String, Object>) result.getResult();
				if (resultMap != null) {
					payMoney = NumberUtil.parseDouble(resultMap.get("totalLeftAmount") + "", 0.0);
				}
			}
			if (!"000".equals(result.getCode())) {
				logger.info("【ThirdPayController.repayment】orderId:" + orderId + ",payType:" + payType
						+ ",不满足支付条件,result:" + JSON.toJSONString(result));
				return result;
			}
			BwRepaymentPlan lastPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByOrderId(orderId);
			Long borrowerId = bwBorrower.getId();
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
			RepaymentDto repaymentDto = new RepaymentDto();
			repaymentDto.setPayChannel(2);
			repaymentDto.setAmount(payMoney);
			repaymentDto.setTradeTime(new Date());
			repaymentDto.setBorrowerId(borrowerId);
			repaymentDto.setOrderId(orderId);
			repaymentDto.setType(payType);
			repaymentDto.setTradeType(1);
			repaymentDto.setPayWay(1);
			repaymentDto.setTerminalType(channel);
			lianlianWithHold(result, repaymentDto, bwOrder, bwBorrower, bwBankCard, lastPlan.getId());
		} catch (Exception e) {
			logger.error("【ThirdPayController.payment】orderId:" + orderId + "支付异常", e);
			result.setCode("999");
			result.setMsg("支付异常");
		}
		return result;
	}

	private AppResponseResult lianlianWithHold(AppResponseResult result, RepaymentDto repaymentDto, BwOrder bwOrder,
			BwBorrower bwBorrower, BwBankCard bwBankCard, Long repayId) throws Exception {
		repaymentDto.setPayChannel(2);
		Double payMoney = repaymentDto.getAmount();
		Integer payType = repaymentDto.getType();
		Long orderId = repaymentDto.getOrderId();
		Long borrowerId = bwBorrower.getId();
		String cardNo = bwBankCard.getCardNo();
		// 保存redis
		RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId, orderId.toString(), 900);
		RedisUtils.hset("pay_info", orderId.toString(), JSON.toJSONString(repaymentDto));
		// 保存redis处理中
		String setResult = RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId, orderId.toString(), 15 * 60);
		if (setResult == null) {
			result.setCode("123");
			result.setMsg("支付失败");
			// 删除redis
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
			RedisUtils.hdel("pay_info", orderId.toString());
			return result;
		}

		// 连连签约查询
		CardQueryResult cardResult = LianLianPayService.cardBindQuery(borrowerId.toString(), cardNo);
		if (CommUtils.isNull(cardResult.getAgreement_list())) {
			result.setCode("113");
			result.setMsg("该用户未签约连连支付");
			logger.info("【ThirdPayController.lianlianWithHold】orderId:" + orderId + "连连卡扣,该用户未签约连连支付");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
			RedisUtils.hdel("pay_info", orderId.toString());
			return result;
		}
		String no_agree = cardResult.getAgreement_list().get(0).getNo_agree();
		logger.info("【ThirdPayController.lianlianWithHold】orderId:" + orderId + "borrowerId:" + borrowerId + ",连连支付协议号:"
				+ no_agree);

		// 连连支付单独授权接口
		List<Object> list = new ArrayList<>();
		list.add(1);
		list.add(3);
		Example example = new Example(BwRepaymentPlan.class);
		example.createCriteria().andEqualTo("orderId", orderId).andIn("repayStatus", list);
		List<BwRepaymentPlan> plans = bwRepaymentPlanService.findBwRepaymentPlanByExample(example);
		List<RepaymentPlan> repays = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		// 设置实际支付金额
		for (BwRepaymentPlan plan : plans) {
			RepaymentPlan repay = new RepaymentPlan();
			repay.setAmount(payMoney.toString());
			repay.setDate(dateFormat2.format(plan.getRepayTime()));
			repays.add(repay);
		}
		SignalLess signalLess = new SignalLess();
		signalLess.setAcct_name(bwBorrower.getName());
		signalLess.setApp_request(
				repaymentDto.getTerminalType() == null ? null : repaymentDto.getTerminalType().toString());
		signalLess.setCard_no(cardNo);
		signalLess.setId_no(bwBorrower.getIdCard());
		signalLess.setNo_agree(no_agree);
		signalLess.setUser_id(borrowerId.toString());
		PlanResult planResult = LianLianPayService.sigalAccreditPay(signalLess, bwOrder.getOrderNo(), repays);
		if (!"0000".equals(planResult.getRet_code())) {// 授权失败
			result.setCode("114");
			result.setMsg(planResult.getRet_msg());
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
			RedisUtils.hdel("pay_info", orderId.toString());
			return result;
		}

		// 授权成功，去卡扣
		RepayRequest repayRequest = new RepayRequest();
		repayRequest.setNo_order(GenerateSerialNumber.getSerialNumber().substring(8) + repayId);
		repayRequest.setUser_id(borrowerId.toString());
		repayRequest.setNo_agree(no_agree);
		// repayRequest.setDt_order(dateFormat.format(order.getCreateTime()));
		repayRequest.setDt_order(dateFormat.format(new Date()));
		repayRequest.setName_goods("易秒贷");
		if (testBool) {
			repayRequest.setMoney_order("0.01");
		} else {
			repayRequest.setMoney_order(payMoney.toString());
		}
		repayRequest.setSchedule_repayment_date(repays.get(0).getDate());
		repayRequest.setRepayment_no(bwOrder.getOrderNo());
		repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
		repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
		repayRequest.setUser_info_full_name(bwBorrower.getName());
		repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
		if (payType == 2) {
			repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/thirdPay/lianlianExtendNotify.do");// 展期回调
		} else {
			repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/thirdPay/lianlianRepaymentNotify.do");// 正常还款回调
		}
		RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);

		// 保存支付日志
		savePayLog4Lianlian(bwOrder, repaymentDto, repayRequest, repaymentResult);

		if (!"0000".equals(repaymentResult.getRet_code())) {
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
			String ret_msg = repaymentResult.getRet_msg();
			if (ret_msg != null && !ret_msg.contains("扣款接口")) {// 调用异常
				RedisUtils.hdel("pay_info", orderId.toString());
			}
			if (StringUtils.isEmpty(ret_msg)) {
				result.setCode("123");
				result.setMsg("支付失败");
			} else {// 前端弹框
				result.setCode("106");
				result.setMsg(ret_msg);
			}
			return result;
		}
		result.setCode("000");
		result.setMsg("支付成功");
		return result;
	}

	@ResponseBody
	@RequestMapping("/lianlianRepaymentNotify.do")
	public NotifyNotice lianlianRepaymentNotify(HttpServletRequest request) {
		NotifyNotice notice = new NotifyNotice();
		String repayId = null;
		try {
			String receiveJsonStr = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("==================连连还款回调接收参数=========" + receiveJsonStr);
			NotifyResult notifyResult = JSON.parseObject(receiveJsonStr, NotifyResult.class);
			// 验签
			if (CommUtils.isNull(notifyResult)) {
				logger.info("==================异步通知为空========================");
				notice.setRet_code("101");
				notice.setRet_msg("异步通知为空");
				return notice;
			}
			Map<String, String> map = new HashMap<>();
			map.put("oid_partner", notifyResult.getOid_partner());
			map.put("sign_type", notifyResult.getSign_type());
			map.put("dt_order", notifyResult.getDt_order());
			map.put("no_order", notifyResult.getNo_order());
			map.put("oid_paybill", notifyResult.getOid_paybill());
			map.put("money_order", notifyResult.getMoney_order());
			map.put("result_pay", notifyResult.getResult_pay());
			map.put("settle_date", notifyResult.getSettle_date());
			map.put("info_order", notifyResult.getInfo_order());
			map.put("pay_type", notifyResult.getPay_type());
			map.put("bank_code", notifyResult.getBank_code());
			map.put("no_agree", notifyResult.getNo_agree());
			map.put("id_type", notifyResult.getId_type());
			map.put("id_no", notifyResult.getId_no());
			map.put("acct_name", notifyResult.getAcct_name());
			map.put("card_no", notifyResult.getCard_no());

			// 验签
			String osign = RSAUtil.sortParams(map);
			if (!RSAUtil.checksign(SystemConstant.YT_PUB_KEY, osign, notifyResult.getSign())) {// 未通过验签
				logger.info("==================异步通知未通过========================");
				notice.setRet_code("102");
				notice.setRet_msg("验签未通过");
				return notice;
			}
			if (CommUtils.isNull(notifyResult.getNo_order())) {// 订单号
				logger.info("==================工单id为空========================");
				notice.setRet_code("103");
				notice.setRet_msg("工单id为空");
				return notice;
			}
			String no_order = notifyResult.getNo_order();// 商户订单号
			repayId = notifyResult.getNo_order().substring(20);
			// 防重复请求锁
			boolean lockRequest = ControllerUtil.lockRequest(RedisKeyConstant.LOCK_KEY_PRE + repayId, 30);
			if (!lockRequest) {// 重复回调
				logger.info("连连还款重复回调orderId:" + repayId);
				notice.setRet_code("1012");
				notice.setRet_msg("处理中");
				return notice;
			}
			BwRepaymentPlan plan = bwRepaymentPlanService.getBwRepaymentPlanByPlanId(Long.parseLong(repayId));
			Long orderId = plan.getOrderId();
			if ("SUCCESS".equals(notifyResult.getResult_pay())) {
				Double payMoney = Double.parseDouble(notifyResult.getMoney_order());// 支付金额
				RepaymentDto repaymentDto = new RepaymentDto();
				String pay_info = RedisUtils.hget("pay_info", orderId.toString());
				if (StringUtils.isNotEmpty(pay_info)) {
					RepaymentDto redisDto = JSON.parseObject(pay_info, RepaymentDto.class);
					if (redisDto != null) {
						repaymentDto.setTerminalType(redisDto.getTerminalType());
						if (testBool) {// 测试
							payMoney = redisDto.getAmount();
						}
					}
				}
				repaymentDto.setUseCoupon(false);
				repaymentDto.setOrderId(orderId);
				repaymentDto.setType(1);
				repaymentDto.setTradeNo(notifyResult.getOid_paybill());
				repaymentDto.setAmount(payMoney);
				repaymentDto.setPayChannel(2);
				repaymentDto.setTradeTime(new Date());
				repaymentDto.setTradeType(1);
				repaymentDto.setTradeCode(notifyResult.getResult_pay());

				// 支付成功
				AppResponseResult appResponseResult = bwRepaymentService.updateOrderByTradeMoney(repaymentDto);
				if ("000".equals(appResponseResult.getCode())) {
					logger.info("支付成功orderId：" + orderId + JSON.toJSONString(appResponseResult));
				} else {
					logger.info("支付失败orderId：" + orderId + JSON.toJSONString(appResponseResult));
				}
			}
			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
			RedisUtils.hdel("pay_info", orderId.toString());
			return notice;
		} catch (Exception e) {
			notice.setRet_code("9999");
			notice.setRet_msg("交易异常");
			return notice;
		} finally {
			// 删除重复回调锁
			if (repayId != null) {
				RedisUtils.del(RedisKeyConstant.LOCK_KEY_PRE + repayId);
			}
		}
	}

	/**
	 * 保存连连调用支付接口日志
	 *
	 * @param bwOrder
	 * @param repaymentDto
	 * @param repayRequest
	 * @param repaymentResult
	 */
	private void savePayLog4Lianlian(BwOrder bwOrder, RepaymentDto repaymentDto, RepayRequest repayRequest,
			RepaymentResult repaymentResult) {
		BwPayLog bwPayLog = new BwPayLog();
		bwPayLog.setOrderId(bwOrder.getId());
		bwPayLog.setBorrowerId(bwOrder.getBorrowerId());
		bwPayLog.setPayChannel(2);
		bwPayLog.setLogType(1);
		bwPayLog.setPayType(repaymentDto.getType());
		bwPayLog.setTerminalType(repaymentDto.getTerminalType());
		bwPayLog.setTradeMoney(NumberUtil.parseDouble(repayRequest.getMoney_order(), 0.0));
		bwPayLog.setRequestData(JSON.toJSONString(repayRequest));
		bwPayLog.setResultData(JSON.toJSONString(repaymentResult));
		bwPayLog.setTradeNo(repayRequest.getNo_order());
		String retCode = repaymentResult.getRet_code();
		bwPayLog.setTradeCode(retCode);
		Date nowDate = new Date();
		bwPayLog.setTradeTime(nowDate);
		bwPayLog.setCreateTime(nowDate);
		bwPayLog.setUpdateTime(nowDate);
		if ("0000".equals(retCode)) {// 接口调用成功
			bwPayLog.setTradeStatus(1);
		} else {
			bwPayLog.setTradeStatus(3);
		}
		bwPayLogService.insertPayLog(bwPayLog);
	}

	/**
	 * 保存连连调用支付接口日志
	 *
	 * @param bwOrder
	 * @param repaymentDto
	 * @param notifyResult
	 */
	private void savePayNotifyLog4Lianlian(BwOrder bwOrder, RepaymentDto repaymentDto, NotifyResult notifyResult) {
		BwPayLog bwPayLog = new BwPayLog();
		bwPayLog.setOrderId(bwOrder.getId());
		bwPayLog.setBorrowerId(bwOrder.getBorrowerId());
		bwPayLog.setPayChannel(2);
		bwPayLog.setLogType(2);
		bwPayLog.setPayType(repaymentDto.getType());
		bwPayLog.setTerminalType(repaymentDto.getTerminalType());
		bwPayLog.setTradeMoney(NumberUtil.parseDouble(notifyResult.getMoney_order(), 0.0));
		bwPayLog.setRequestData(JSON.toJSONString(notifyResult));
		bwPayLog.setResultData(JSON.toJSONString(notifyResult));
		bwPayLog.setTradeNo(notifyResult.getNo_order());
		String retCode = "0000";// 连连成功才回调
		bwPayLog.setTradeCode(retCode);
		Date nowDate = new Date();
		bwPayLog.setTradeTime(nowDate);
		bwPayLog.setCreateTime(nowDate);
		bwPayLog.setUpdateTime(nowDate);
		if ("0000".equals(retCode)) {// 接口调用成功
			bwPayLog.setTradeStatus(1);
		} else {
			bwPayLog.setTradeStatus(3);
		}
		bwPayLogService.insertPayLog(bwPayLog);
	}
}