/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.capital.koudai;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.capital.service.CapitalKoudaiService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCapitalOrder;
import com.waterelephant.entity.BwCapitalPush;
import com.waterelephant.entity.BwCapitalRepay;
import com.waterelephant.entity.BwCapitalWithhold;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwCapitalOrderService;
import com.waterelephant.service.BwCapitalPushService;
import com.waterelephant.service.BwCapitalRepayService;
import com.waterelephant.service.BwCapitalWithholdService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * @author 崔雄健
 * @date 2017年4月12日
 * @description
 */
@Controller
@RequestMapping("/koudai")
public class KoudaiController {
	private Logger logger = Logger.getLogger(KoudaiController.class);

	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private IBwOrderPushInfoService bwOrderPushInfoService;
	@Resource
	private BwOrderRongService bwOrderRongService;
	@Resource
	private BwRepaymentPlanService bwRepaymentPlanService;
	@Resource
	private IBwBankCardService bwBankCardService;
	@Resource
	private BwCapitalPushService bwCapitalPushService;
	@Resource
	private BwCapitalRepayService bwCapitalRepayService;
	@Resource
	private BwProductDictionaryService bwProductDictionaryService;
	@Resource
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Resource
	private BwCapitalOrderService bwCapitalOrderService;

	@Resource
	private BwCapitalWithholdService bwCapitalWithholdService;
	@Resource
	private IBwRepaymentService iBwRepaymentService;

	@Resource
	private BwBorrowerService bwBorrowerService;

	@ResponseBody
	@RequestMapping("/loanAdvicePush.do") // 放款通知推送
	public AppResponseResult loanAdvicePush(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("口袋回调参数==》" + params);
			Map<String, String> paramsMap = KdaiSignUtils.getNotityMap(params); // 参数转换
			KdaiSignUtils.argSort(paramsMap); // 排序
			if (!KdaiSignUtils.verifySign(JSON.parseObject(JSON.toJSONString(paramsMap)), // 验签
					SystemConstant.CAPITAL_KOUDAI_VERIFY_KEY)) {
				appResponseResult.setCode("1003");
				appResponseResult.setMsg("未知的请求。");
				return appResponseResult;
			}

			if (CommUtils.isNull(params)) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("参数为空。");
				return appResponseResult;
			}
			// Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);

			if (CommUtils.isNull(StringUtil.toString(paramsMap.get("order_id")))) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("order_id参数为空。");
				return appResponseResult;
			}
			String code = StringUtil.toString(paramsMap.get("code")); // 获取口袋入参
			String orderNo = StringUtil.toString(paramsMap.get("order_id"));
			String loanDate = StringUtil.toString(paramsMap.get("opr_dat"));

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder); // 查询订单

			if (bwOrder == null) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("工单信息不存在。");
				return appResponseResult;
			}
			Long orderId = bwOrder.getId();

			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId, 2); // 查询渠道为口袋的对应工单

			if (bwCapitalPush != null) { // 如果有订单，且状态为放款成功
				if ((2 == bwCapitalPush.getPushStatus() && "0".equals(code))) {
					appResponseResult.setCode("1004");
					appResponseResult.setMsg("该order_Id已推送成功，请不要重复推送");
					logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
					return appResponseResult;
				}
			} else { // 如果没有订单，创建订单
				bwCapitalPush = new BwCapitalPush();
				bwCapitalPush.setOrderId(orderId);
				bwCapitalPush.setCreateTime(now);
				bwCapitalPush.setCapitalId(2); // 渠道设置为口袋
				bwCapitalPush.setPushCount(0); // 发送次数
			}

			if (CommUtils.isNull(bwCapitalPush.getId())) {
				bwCapitalPushService.save(bwCapitalPush); // 保存资方订单信息
			}
			bwCapitalPush.setCode(StringUtil.toString(paramsMap.get("code"))); // 获取状态码
			bwCapitalPush.setMsg(StringUtil.toString(paramsMap.get("result"))); // 响应结果
			bwCapitalPush.setUpdateTime(now);

			if ("0".equals(code)) {

				if (CommUtils.isNull(StringUtil.toString(paramsMap.get("opr_dat")))) {
					appResponseResult.setCode("1002");
					appResponseResult.setMsg("opr_dat参数为空。");
					return appResponseResult;
				}

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date loanTime = null;
				try {
					loanDate = DateUtil.getDateString(CommUtils.convertStringToDate(loanDate, "yyyyMMdd"),
							"yyyy-MM-dd");
					loanTime = format.parse(loanDate);

				} catch (Exception e) {
					logger.error("还款时间：", e);
					e.printStackTrace();
					appResponseResult.setCode("1001");
					appResponseResult.setMsg("opr_dat格式错误");
					return appResponseResult;
				}

				bwCapitalPush.setPushStatus(2);
				bwCapitalPush.setCode(StringUtil.toString(paramsMap.get("code")));
				bwCapitalPush.setMsg(StringUtil.toString(paramsMap.get("result")));
				bwCapitalPush.setUpdateTime(now);

				try {
					// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给口袋
					List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							.listBwRepaymentPlanByOrderId(orderId);
					if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
						appResponseResult.setCode("1004");
						appResponseResult.setMsg("该order_Id已推送成功，请不要重复推送");
						logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
						return appResponseResult;
					}
					// 还款时间
					Date repaymentTime = null;
					if (7 == bwOrder.getProductId() || 8 == bwOrder.getProductId()) {
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder,
								CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd"));
					} else {
						if (2 == bwOrder.getProductType()) { // 如果产品类型是分期
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);

						} else {
							// 根据产品类型查询产品（分期）
							BwProductDictionary bwProductDictionary = bwProductDictionaryService
									.findBwProductDictionaryById(bwOrder.getProductId());
							String termType = null;
							String term = null;
							if (bwProductDictionary != null) {
								termType = bwProductDictionary.getpTermType(); // 产品类型（1：月；2：天）pTermType
								term = bwProductDictionary.getpTerm(); // 产品期限
							}

							if ("1".equals(termType)) {
								repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
							} else if ("2".equals(termType)) {
								repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
							} else {
								repaymentTime = MyDateUtils.addDays(loanTime, 30);
							}
							// 根据工单信息和指定还款时间生成借款人还款计划
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
									bwProductDictionary);
						}
					}

					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repayTime);
					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, repayDate);

					bwCapitalPush.setPushStatus(2);// 放款成功
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

					// 更新push 表
					BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 2); // 渠道为口袋的工单
					if (bwOrderPushInfo != null) {
						bwOrderPushInfo.setFullTime(loanTime);
						bwOrderPushInfo.setPushStatus(2); // 状态。0：推送失败，1：推送成功，2：接收成功，3：接收失败
						bwOrderPushInfo.setPushRemark("接收成功");
						bwOrderPushInfo.setUpdateTime(now);
						bwOrderPushInfo.setLoanTime(loanTime); // 设置放款时间
						bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
					}
					bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);

					try {

						// 渠道放款成功 - 通知
						// 渠道放款成功 - 通知
						String channel = String.valueOf(bwOrder.getChannel());
						if (RedisUtils.hexists("tripartite:channels", "channel_" + bwOrder.getChannel())) {

							HashMap<String, String> hm = new HashMap<>();
							hm.put("channelId", channel);
							hm.put("orderId", String.valueOf(bwOrder.getId()));
							hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
							hm.put("result", "放款成功");
							String hmData = JSON.toJSONString(hm);
							RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);

						}
						// 通知短信
						Long borrowerId = bwOrder.getBorrowerId();
						BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
						String sex = bwBorrower.getSex() + "";
						String userName = bwBorrower.getName();

						MessageDto messageDto = new MessageDto();
						messageDto.setBusinessScenario("1"); // 营销短信
						messageDto.setPhone(bwBorrower.getPhone());
						messageDto.setType("1"); // 文字短信

						if ("0".equals(sex)) {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "女士，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						} else {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "先生，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						}

						RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));

						// // 12:渠道为好贷
						// if ("12".equals(channel) || "81".equals(channel)) {
						// // Map<String, String> maps = haoDaiService.getLoanStatusData(bwOrder.getId());
						// // if (maps.get("code").equals("1000")) {
						// // 放完款推送数据好贷
						// // 获取对应好贷 订单
						// String thirdOrderId = bwOrderRongService
						// .findThirdOrderNoByOrderId(String.valueOf(bwOrder.getId()));
						// BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "3");
						// // }
						// PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
						// BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
						// }

					} catch (Exception e) {
						e.printStackTrace();
					}

					// 根据工单id查询融360对应的订单号
					// try {
					// // logger.info("判断工单来源是否为融360");
					// if (11 == bwOrder.getChannel().intValue()) {
					// BwOrderRong bwOrderRong = new BwOrderRong();
					// bwOrderRong.setOrderId(bwOrder.getId());
					// bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
					// BwBankCard bwBankCard = new BwBankCard();
					// bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
					// bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
					// /**
					// * 融360反馈
					// */
					// OrderFeedBackReq orderFeedBackReq = new OrderFeedBackReq();
					// orderFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
					// orderFeedBackReq.setOrder_status(170);
					// logger.info("开始调用融360订单状态反馈接口," + orderFeedBackReq);
					// OrderFeedBackResp orderFeedBackResp = BeadWalletRong360Service
					// .orderFeedBack(orderFeedBackReq);
					// logger.info("结束调用融360订单状态反馈接口," + orderFeedBackResp);
					//
					// /**
					// * 融360反馈
					// */
					// // 还款计划推送
					// BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService
					// .getBwRepaymentPlanByOrderId(orderId);// （code0090）
					// double realityRepayMoney = 0;
					// if (bwRepaymentPlan != null && bwRepaymentPlan.getZjw() != null) {
					// realityRepayMoney = bwRepaymentPlan.getRealityRepayMoney();
					// }
					//
					// PushRepaymentReq pushRepaymentReq = new PushRepaymentReq();
					// RepaymentPlan plan = new RepaymentPlan();
					//
					// plan.setAmount(String.valueOf(realityRepayMoney));
					// String timestamp = String.valueOf(repaymentTime.getTime() / 1000);
					// plan.setDue_time(Integer.valueOf(timestamp).toString());
					// plan.setPay_type(4);
					// plan.setPeriod_no("1"); // 默认只有1期
					// String remark = "本金:" + bwOrder.getBorrowAmount();
					// plan.setRemark(remark);
					// plan.setBill_status(1); // 1.表示未到期（默认是1）
					//
					// List<RepaymentPlan> planList = new ArrayList<RepaymentPlan>();
					// planList.add(plan);
					//
					// pushRepaymentReq.setOrder_no(bwOrderRong.getThirdOrderNo());
					// pushRepaymentReq.setRepayment_plan(planList);
					// pushRepaymentReq.setBank_card(bwBankCard.getCardNo());
					// pushRepaymentReq.setOpen_bank(bwBankCard.getBankName());
					// logger.info("开始调用融360放款账单推送接口," + pushRepaymentReq);
					// PushRepaymentResp pushRepaymentResp = BeadWalletRong360Service
					// .pushRepayment(pushRepaymentReq);
					// logger.info("结束调用融360放款账单推送接口，" + pushRepaymentResp);
					//
					// // 账单状态
					// RepaymentFeedBackReq repaymentFeedBackReq = new RepaymentFeedBackReq();
					// repaymentFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
					// repaymentFeedBackReq.setPeriod_no("1");
					// repaymentFeedBackReq.setBill_status("1");
					// repaymentFeedBackReq.setRemark("未到期");
					// logger.info("开始调用融360账单状态反馈接口," + repaymentFeedBackReq);
					// RepaymentFeedBackResp repaymentFeedBackResp = BeadWalletRong360Service
					// .repaymentFeedBack(repaymentFeedBackReq);
					// logger.info("结束调用融360账单状态反馈接口," + repaymentFeedBackResp);
					// }
					// } catch (Exception e) {
					// logger.error("调用融360接口异常", e);
					// }
					logger.info("口袋推送处理结果：接收成功，当前工单号：" + orderId + "，还款日期：" + loanDate);
					// 口袋创建订单
					Map<String, Object> map = bwOrderService.findCapitalKoudaiC(orderId);
					if (map != null) {
						try {
							String str = null;
							if (bwCapitalPush != null && "28".equals(bwCapitalPush.getRemark())) {
								str = CapitalKoudaiService.createLoanApplySxy(JSON.toJSONString(map));
							} else {
								str = CapitalKoudaiService.createLoanApply(JSON.toJSONString(map));
							}

							if (CommUtils.isNull(str)) {
								RedisUtils.rpush("capital:createError", "创建订单异常：" + orderId);

							} else {
								JSONObject returnJson = JSON.parseObject(str);
								if ("0".equals(CommUtils.toString(returnJson.get("code")))) {
									BwCapitalOrder bwCapitalOrder = new BwCapitalOrder();
									bwCapitalOrder.setCapitalId(2L);
									bwCapitalOrder.setOrderId(orderId);
									bwCapitalOrder.setOrderNo(orderNo);
									bwCapitalOrder.setCreateTime(new Date());
									bwCapitalOrder.setCapitalNo(CommUtils.toString(returnJson.get("order_id")));
									bwCapitalOrder.setType(1);
									bwCapitalOrder.setUpdateTime(new Date());

									bwCapitalOrderService.save(bwCapitalOrder);
									logger.info("口袋创建订单成功：" + orderId);
								} else {
									RedisUtils.rpush("capital:createError", "创建订单失败：" + orderId);
								}
							}

						} catch (Exception e) {
							logger.error("口袋创建订单异常：" + orderId, e);
							RedisUtils.rpush("capital:createError", "创建订单异常：" + orderId);
						}

					}

				} catch (Exception e) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					logger.info("口袋推送处理结果：保存信息出错，当前工单号：" + orderId);
					throw e;
				}

				appResponseResult.setCode("0");
				appResponseResult.setMsg("成功");
			} else if ("1003".equals(code)) {
				bwCapitalPush.setPushStatus(3);// 放款失败
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setCode("0");
				appResponseResult.setMsg("成功");
			} else {
				appResponseResult.setCode("0");
				appResponseResult.setMsg("未知的请求");
			}

		} catch (Exception e) {
			logger.error("口袋推送结果异常：", e);
		}

		return appResponseResult;
	}

	@ResponseBody
	@RequestMapping("/loanAdviceCreate.do")
	public void loanAdviceCreate(HttpServletRequest request, HttpServletResponse response) {
		// 口袋创建订单
		String orderIds = request.getParameter("orderIds");
		if (CommUtils.isNull(orderIds)) {
			try {
				orderIds = IOUtils.toString(request.getInputStream(), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("待处理单号" + orderIds);
		if (CommUtils.isNull(orderIds)) {
			return;
		}
		String[] orders = orderIds.split(",");
		for (int i = 0; i < orders.length; i++) {
			String orderId = orders[i].trim();
			logger.info(orderId);
			try {
				Map<String, Object> map = bwOrderService.findCapitalKoudaiC(Long.parseLong(orderId)); // 查询口袋订单信息(订单,借款人,银行卡,产品信息)
				if (map != null) {
					BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(Long.parseLong(orderId), 2); // 查询渠道为口袋的对应工单
					String str = null;
					if (bwCapitalPush != null && "28".equals(bwCapitalPush.getRemark())) {
						str = CapitalKoudaiService.createLoanApplySxy(JSON.toJSONString(map));
					} else {
						str = CapitalKoudaiService.createLoanApply(JSON.toJSONString(map));
					}
					// String str = CapitalKoudaiService.createLoanApply(JSON.toJSONString(map)); //
					// 发送请求到service,调用口袋接口,获取回调参数

					JSONObject returnJson = JSON.parseObject(str);
					if ("0".equals(CommUtils.toString(returnJson.get("code")))) { // 如果状态码是0，创建资方订单，入库
						BwCapitalOrder bwCapitalOrder = new BwCapitalOrder();
						bwCapitalOrder.setCapitalId(2L);
						bwCapitalOrder.setOrderId(Long.parseLong(orderId));
						// bwCapitalOrder.setOrderNo(orderNo);
						bwCapitalOrder.setCreateTime(new Date());
						bwCapitalOrder.setCapitalNo(CommUtils.toString(returnJson.get("order_id")));
						bwCapitalOrder.setType(1);
						bwCapitalOrder.setUpdateTime(new Date());

						bwCapitalOrderService.save(bwCapitalOrder);
						logger.info("口袋创建订单成功：" + orderId);
					} else {
						logger.info("口袋创建订单失败：" + orderId);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@RequestMapping("/orderQuerys.do")
	public void orderQuerys(HttpServletRequest request, HttpServletResponse response) {
		logger.info("口袋获取订单状态");
		String orderIds = request.getParameter("orderIds");
		if (CommUtils.isNull(orderIds)) {
			try {
				orderIds = IOUtils.toString(request.getInputStream(), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("口袋获取订单状态所有：" + orderIds);
		if (CommUtils.isNull(orderIds)) {
			return;
		}
		String[] orders = orderIds.split(",");
		if (CommUtils.isNull(orders)) {
			return;
		}
		for (String orderId : orders) {
			try {
				logger.info("口袋获取订单状态开始：" + orderId);
				BwOrder bwOrder = new BwOrder();
				bwOrder.setId(Long.parseLong(orderId.trim()));
				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
				if (bwOrder == null) {
					continue;
				}
				String returnStr = CapitalKoudaiService.orderQuery("{\"order_no\":\"" + bwOrder.getOrderNo() + "\"}");
				logger.info("口袋获取订单状态结束：" + returnStr);

				JSONObject jsb = JSONObject.parseObject(returnStr);
				JSONObject jsb_date = jsb.getJSONObject("data");
				String code = StringUtil.toString(jsb.get("code"));
				String loanDate = StringUtil.toString(jsb_date.get("opr_dat"));
				String msg = StringUtil.toString(jsb.get("msg"));

				BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(bwOrder.getId(), 2);

				if (bwCapitalPush != null) {
					if ((2 == bwCapitalPush.getPushStatus() && "0".equals(code))) {
						logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
						continue;
					}
				} else {
					bwCapitalPush = new BwCapitalPush();
					bwCapitalPush.setOrderId(bwOrder.getId());
					bwCapitalPush.setCreateTime(new Date());
					bwCapitalPush.setCapitalId(2);
					bwCapitalPush.setPushCount(0);
					bwCapitalPushService.save(bwCapitalPush);
				}

				if ("0".equals(code)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date loanTime = null;
					try {
						loanDate = DateUtil.getDateString(CommUtils.convertStringToDate(loanDate, "yyyyMMdd"),
								"yyyy-MM-dd");
						loanTime = format.parse(loanDate);

					} catch (Exception e) {
						logger.error("还款时间异常：" + orderId, e);
						continue;
					}

					bwCapitalPush.setCode(code);
					bwCapitalPush.setMsg(msg);
					bwCapitalPush.setUpdateTime(new Date());

					try {
						// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给口袋
						List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
								.listBwRepaymentPlanByOrderId(bwOrder.getId());
						if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
							logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
							continue;
						}
						// 还款时间
						Date repaymentTime = null;
						if (7 == bwOrder.getProductId()) {
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder,
									CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd"));
						} else {
							if (2 == bwOrder.getProductType()) {
								bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate); // 保存还款计划

							} else {

								BwProductDictionary bwProductDictionary = bwProductDictionaryService
										.findBwProductDictionaryById(bwOrder.getProductId());
								String termType = null;
								String term = null;
								if (bwProductDictionary != null) {
									termType = bwProductDictionary.getpTermType();
									term = bwProductDictionary.getpTerm();
								}

								if ("1".equals(termType)) {
									repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
								} else if ("2".equals(termType)) {
									repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
								} else {
									repaymentTime = MyDateUtils.addDays(loanTime, 30);
								}

								bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
										bwProductDictionary);
							}
						}

						bwCapitalPush.setPushStatus(2);// 放款成功
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

						// 更新push 表
						BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(bwOrder.getId(), 2);
						if (bwOrderPushInfo != null) {
							bwOrderPushInfo.setFullTime(loanTime);
							bwOrderPushInfo.setPushStatus(2);
							bwOrderPushInfo.setPushRemark("接收成功");
							bwOrderPushInfo.setUpdateTime(new Date());
							bwOrderPushInfo.setLoanTime(loanTime);
							bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
						}
						bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(bwOrder.getId(), loanTime);

						try {

							// 渠道放款成功 - 通知
							String channel = String.valueOf(bwOrder.getChannel());
							HashMap<String, String> hm = new HashMap<>();
							hm.put("channelId", channel);
							hm.put("orderId", String.valueOf(bwOrder.getId()));
							hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
							hm.put("result", "放款成功");
							String hmData = JSON.toJSONString(hm);
							RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);

							// 待定短信
							Long borrowerId = bwOrder.getBorrowerId();
							BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
							String sex = bwBorrower.getSex() + "";
							String userName = bwBorrower.getName();

							MessageDto messageDto = new MessageDto();
							messageDto.setBusinessScenario("1"); // 营销短信
							messageDto.setPhone(bwBorrower.getPhone());
							messageDto.setType("1"); // 文字短信
							if ("0".equals(sex)) {
								messageDto.setMsg(
										"【水象分期】尊敬的" + userName + "女士，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
							} else {
								messageDto.setMsg(
										"【水象分期】尊敬的" + userName + "先生，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
							}

							RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
							// 12:渠道为好贷
							// String channel = String.valueOf(bwOrder.getChannel());
							// if ("12".equals(channel) || "81".equals(channel)) {
							// // Map<String, String> maps = haoDaiService.getLoanStatusData(bwOrder.getId());
							// // if (maps.get("code").equals("1000")) {
							// // 放完款推送数据好贷
							// // 获取对应好贷 订单
							// String thirdOrderId = bwOrderRongService
							// .findThirdOrderNoByOrderId(String.valueOf(bwOrder.getId()));
							// BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "3");
							// // }
							// PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
							// BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
							// }
							// channelService.sendOrderStatus(CommUtils.toString(bwOrder.getChannel()),
							// CommUtils.toString(bwOrder.getId()), "14");
							// PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
							// BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
						} catch (Exception e) {
							e.printStackTrace();
						}

						// 根据工单id查询融360对应的订单号
						try {
							// logger.info("判断工单来源是否为融360");
							// if (11 == bwOrder.getChannel().intValue()) {
							// BwOrderRong bwOrderRong = new BwOrderRong();
							// bwOrderRong.setOrderId(bwOrder.getId());
							// bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
							// BwBankCard bwBankCard = new BwBankCard();
							// bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
							// bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
							// /**
							// * 融360反馈
							// */
							// OrderFeedBackReq orderFeedBackReq = new OrderFeedBackReq();
							// orderFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
							// orderFeedBackReq.setOrder_status(170);
							// logger.info("开始调用融360订单状态反馈接口," + orderFeedBackReq);
							// OrderFeedBackResp orderFeedBackResp = BeadWalletRong360Service
							// .orderFeedBack(orderFeedBackReq);
							// logger.info("结束调用融360订单状态反馈接口," + orderFeedBackResp);
							//
							// /**
							// * 融360反馈
							// */
							// // 还款计划推送
							// BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService
							// .getBwRepaymentPlanByOrderId(Long.parseLong(orderId));// （code0090）
							// double realityRepayMoney = 0;
							// if (bwRepaymentPlan != null && bwRepaymentPlan.getZjw() != null) {
							// realityRepayMoney = bwRepaymentPlan.getRealityRepayMoney();
							// }
							//
							// PushRepaymentReq pushRepaymentReq = new PushRepaymentReq();
							// RepaymentPlan plan = new RepaymentPlan();
							//
							// plan.setAmount(String.valueOf(realityRepayMoney));
							// String timestamp = String.valueOf(repaymentTime.getTime() / 1000);
							// plan.setDue_time(Integer.valueOf(timestamp).toString());
							// plan.setPay_type(4);
							// plan.setPeriod_no("1"); // 默认只有1期
							// String remark = "本金:" + bwOrder.getBorrowAmount();
							// plan.setRemark(remark);
							// plan.setBill_status(1); // 1.表示未到期（默认是1）
							//
							// List<RepaymentPlan> planList = new ArrayList<RepaymentPlan>();
							// planList.add(plan);
							//
							// pushRepaymentReq.setOrder_no(bwOrderRong.getThirdOrderNo());
							// pushRepaymentReq.setRepayment_plan(planList);
							// pushRepaymentReq.setBank_card(bwBankCard.getCardNo());
							// pushRepaymentReq.setOpen_bank(bwBankCard.getBankName());
							// logger.info("开始调用融360放款账单推送接口," + pushRepaymentReq);
							// PushRepaymentResp pushRepaymentResp = BeadWalletRong360Service
							// .pushRepayment(pushRepaymentReq);
							// logger.info("结束调用融360放款账单推送接口，" + pushRepaymentResp);
							//
							// // 账单状态
							// RepaymentFeedBackReq repaymentFeedBackReq = new RepaymentFeedBackReq();
							// repaymentFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
							// repaymentFeedBackReq.setPeriod_no("1");
							// repaymentFeedBackReq.setBill_status("1");
							// repaymentFeedBackReq.setRemark("未到期");
							// logger.info("开始调用融360账单状态反馈接口," + repaymentFeedBackReq);
							// RepaymentFeedBackResp repaymentFeedBackResp = BeadWalletRong360Service
							// .repaymentFeedBack(repaymentFeedBackReq);
							// logger.info("结束调用融360账单状态反馈接口," + repaymentFeedBackResp);
							// }
						} catch (Exception e) {
							logger.error("调用融360接口异常", e);
						}
						logger.info("口袋推送处理结果：接收成功，当前工单号：" + orderId + "，还款日期：" + loanDate);
						// 口袋创建订单
						Map<String, Object> map = bwOrderService.findCapitalKoudaiC(bwOrder.getId());
						if (map != null) {
							try {
								String str = null;
								if (bwCapitalPush != null && "28".equals(bwCapitalPush.getRemark())) {
									str = CapitalKoudaiService.createLoanApplySxy(JSON.toJSONString(map));
								} else {
									str = CapitalKoudaiService.createLoanApply(JSON.toJSONString(map));
								}
								// String str = CapitalKoudaiService.createLoanApply(JSON.toJSONString(map));
								if (CommUtils.isNull(str)) {
									RedisUtils.rpush("capital:createError", "创建订单异常：" + orderId);

								} else {
									JSONObject returnJson = JSON.parseObject(str);
									if ("0".equals(CommUtils.toString(returnJson.get("code")))) {
										BwCapitalOrder bwCapitalOrder = new BwCapitalOrder();
										bwCapitalOrder.setCapitalId(2L);
										bwCapitalOrder.setOrderId(bwOrder.getId());
										bwCapitalOrder.setOrderNo(bwOrder.getOrderNo());
										bwCapitalOrder.setCreateTime(new Date());
										bwCapitalOrder.setCapitalNo(CommUtils.toString(returnJson.get("order_id")));
										bwCapitalOrder.setType(1);
										bwCapitalOrder.setUpdateTime(new Date());

										bwCapitalOrderService.save(bwCapitalOrder);
										logger.info("口袋创建订单成功：" + orderId);
									} else {
										RedisUtils.rpush("capital:createError", "创建订单失败：" + orderId);
									}
								}

							} catch (Exception e) {
								logger.error("口袋创建订单异常：" + orderId, e);
								RedisUtils.rpush("capital:createError", "创建订单异常：" + orderId);
							}

						}

					} catch (Exception e) {

						logger.info("口袋推送处理结果：保存信息出错，当前工单号：" + orderId);
					}

				} else if ("1003".equals(code)) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPush.setMsg(msg);
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				} else if ("1006".equals(code)) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPush.setMsg(msg);
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				}

			} catch (Exception e) {
				logger.error("异常");
				continue;
			}

		}
	}

	@ResponseBody
	@RequestMapping("/withholdNotify.do")
	public AppResponseResult withholdNotify(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		Long orderId = null;
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("口袋回调参数==》" + params);

			if (CommUtils.isNull(params)) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("参数为空。");
				return appResponseResult;
			}

			Map<String, String> paramsMap = KdaiSignUtils.getNotityMap(params);
			KdaiSignUtils.argSort(paramsMap);
			if (!KdaiSignUtils.verifySign(JSON.parseObject(JSON.toJSONString(paramsMap)),
					SystemConstant.CAPITAL_KOUDAI_VERIFY_KEY_D)) {
				appResponseResult.setCode("1003");
				appResponseResult.setMsg("未知的请求。");
				return appResponseResult;
			}

			if (CommUtils.isNull(StringUtil.toString(paramsMap.get("order_id")))) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("参数为空。");
				return appResponseResult;
			}
			String code = StringUtil.toString(paramsMap.get("err_code"));
			String msg = StringUtil.toString(paramsMap.get("err_msg"));
			String pushId = StringUtil.toString(paramsMap.get("order_id"));
			String moneyWithhold = StringUtil.toString(paramsMap.get("money"));

			String orderNo = pushId.split("K")[0];
			String withId = pushId.split("K")[1];
			logger.info("【KoudaiController.withholdNotify】orderNo:" + orderNo + "口袋回调返回:" + paramsMap);

			String thirdPlatform = StringUtil.toString(paramsMap.get("third_platform"));
			String otherOrderNo = StringUtil.toString(paramsMap.get("pay_order_id"));

			String state = StringUtil.toString(paramsMap.get("state"));
			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder != null) {
				orderId = bwOrder.getId();
			}
			// if ("0".equals(code)) {
			if (bwOrder == null) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("工单信息不存在。");
				return appResponseResult;
			}
			// 防重复请求锁
			boolean lockRequest = ControllerUtil.lockRequest(RedisKeyConstant.LOCK_KEY_PRE + orderId, 30);
			if (!lockRequest) {// 重复回调
				logger.info("口袋重复回调orderId:" + orderId);
				appResponseResult.setCode("1012");
				appResponseResult.setMsg("处理中");
				return appResponseResult;
			}

			BwCapitalWithhold bwCapitalWithhold = bwCapitalWithholdService.queryBwCapitalWithhold(orderId,
					Long.parseLong(withId));

			if (bwCapitalWithhold == null) {
				appResponseResult.setCode("1004");
				appResponseResult.setMsg("未知的订单。");
				return appResponseResult;
			}

			if (bwCapitalWithhold != null && bwCapitalWithhold.getPushStatus() == 2) {
				appResponseResult.setCode("0");
				appResponseResult.setMsg("重复推送。");
				return appResponseResult;
			}

			bwCapitalWithhold.setPushStatus(Integer.parseInt(state));
			bwCapitalWithhold.setCode(code);
			bwCapitalWithhold.setMsg(msg);
			bwCapitalWithhold.setUpdateTime(now);
			bwCapitalWithhold.setThirdPlatform(StringUtil.toInteger(thirdPlatform));
			bwCapitalWithhold.setOtherOrderNo(otherOrderNo);
			bwCapitalWithhold.setMoneyWithhold(new BigDecimal(moneyWithhold).divide(new BigDecimal("100")).setScale(2));

			bwCapitalWithholdService.updateBwCapitalWithhold(bwCapitalWithhold);
			if ("0".equals(state)) {
				// 处理业务 还款成功0：待扣款，
				appResponseResult.setCode("0");
				appResponseResult.setMsg("已处理。");
				return appResponseResult;
			} else if ("1".equals(state)) {
				// 处理业务 1：扣款中
				appResponseResult.setCode("0");
				appResponseResult.setMsg("已处理。");
				return appResponseResult;
			} else if ("2".equals(state)) {
				// 处理业务 还款成功
				updateSuccessNotify(bwCapitalWithhold);

				appResponseResult.setCode("0");
				appResponseResult.setMsg("已处理。");
				return appResponseResult;
			} else if ("3".equals(state)) {
				// 处理业务 还款失败
				RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
				RedisUtils.hdel("pay_info", orderId.toString());

				appResponseResult.setCode("0");
				appResponseResult.setMsg("已处理。");
				return appResponseResult;
			} else {
				appResponseResult.setCode("1008");
				appResponseResult.setMsg("未知状态。");
				return appResponseResult;
			}
			// } else {
			// // 失败处理
			// if (orderId != null) {
			// RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
			// }
			// appResponseResult.setCode("0");
			// appResponseResult.setMsg("已处理。");
			// return appResponseResult;
			// }

		} catch (Exception e) {
			logger.error("口袋推送结果异常：", e);
			appResponseResult.setCode("1009");
			appResponseResult.setMsg("处理失败。");
		} finally {
			// 删除重复回调锁
			if (orderId != null) {
				RedisUtils.del(RedisKeyConstant.LOCK_KEY_PRE + orderId);
			}
		}

		return appResponseResult;
	}

	@RequestMapping("/withholdQuery.do")
	public void withholdQuery(HttpServletRequest request, HttpServletResponse response) {
		String orderIds = request.getParameter("orderIds");
		if (CommUtils.isNull(orderIds)) {
			try {
				orderIds = IOUtils.toString(request.getInputStream(), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("口袋获取订单状态所有：" + orderIds);
		if (CommUtils.isNull(orderIds)) {
			return;
		}
		String[] orders = orderIds.split(",");
		if (CommUtils.isNull(orders)) {
			return;
		}
		for (String pushId : orders) {
			try {
				String orderNo = pushId.split("K")[0];
				String withId = pushId.split("K")[1];

				BwOrder bwOrder = new BwOrder();
				bwOrder.setOrderNo(orderNo);
				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
				Long orderId = bwOrder.getId();

				String returnStr = CapitalKoudaiService.withholdQuery("{\"order_id\":\"" + pushId + "\"}");
				logger.info("口袋代扣查询：" + returnStr);
				if (CommUtils.isNull(returnStr)) {
					continue;
				}

				JSONObject jsb = JSONObject.parseObject(returnStr);

				String code = StringUtil.toString(jsb.get("code"));
				String msg = StringUtil.toString(jsb.get("msg"));

				// if ("0".equals(code)) {
				JSONObject jsb_date = jsb.getJSONObject("data");
				String state = jsb_date.getString("err_code");
				String errMsg = jsb_date.getString("err_msg");
				String thirdPlatform = jsb_date.getString("third_platform");
				String moneyWithhold = jsb_date.getString("money");
				String otherOrderNo = jsb_date.getString("pay_order_id");

				BwCapitalWithhold bwCapitalWithhold = bwCapitalWithholdService
						.queryBwCapitalWithhold(Long.parseLong(withId));

				if (bwCapitalWithhold == null) {
					continue;
				}

				if (bwCapitalWithhold != null && bwCapitalWithhold.getPushStatus() == 2) {
					logger.info("已获取到回调信息");
					continue;
				}

				bwCapitalWithhold.setPushStatus(Integer.parseInt(state));
				bwCapitalWithhold.setCode(code);
				bwCapitalWithhold.setMsg(errMsg);
				bwCapitalWithhold.setUpdateTime(new Date());
				bwCapitalWithhold.setThirdPlatform(StringUtil.toInteger(thirdPlatform));
				bwCapitalWithhold.setOtherOrderNo(otherOrderNo);
				bwCapitalWithhold
						.setMoneyWithhold(new BigDecimal(moneyWithhold).divide(new BigDecimal("100")).setScale(2));

				bwCapitalWithholdService.updateBwCapitalWithhold(bwCapitalWithhold);
				if ("0".equals(state)) {
					// 处理业务 还款成功0：待扣款，
				} else if ("1".equals(state)) {
					// 处理业务 1：扣款中
				} else if ("2".equals(state)) {
					// 处理业务 还款成功
					updateSuccessNotify(bwCapitalWithhold);
				} else if ("3".equals(state)) {
					// 处理业务 还款失败
					// 删除redis数据
					RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
					RedisUtils.hdel("pay_info", orderId.toString());
				}
				// } else {
				// // 失败处理
				// RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
				// }

			} catch (Exception e) {
				logger.error("口袋代扣查询异常：", e);
			}
		}
	}

	/**
	 * 成功回调
	 *
	 * @param bwCapitalWithhold
	 * @throws Exception
	 */
	private void updateSuccessNotify(BwCapitalWithhold bwCapitalWithhold) throws Exception {
		Long orderId = bwCapitalWithhold.getOrderId();
		Integer payType = bwCapitalWithhold.getPayType();
		Integer terminalType = bwCapitalWithhold.getTerminalType();
		if (terminalType == null || terminalType != 0) {// 前端口袋回调
			logger.info("【KoudaiController.updateSuccessNotify】orderId:" + orderId + "前端口袋回调");
			String otherOrderNo = bwCapitalWithhold.getOtherOrderNo();
			Date tradeTime = bwCapitalWithhold.getCreateTime();
			RepaymentDto repaymentDto = new RepaymentDto();
			repaymentDto.setOrderId(orderId);
			repaymentDto.setType(payType);
			repaymentDto.setTerminalType(terminalType);
			repaymentDto.setTradeNo(otherOrderNo);
			String pay_info = RedisUtils.hget("pay_info", orderId.toString());
			if (StringUtils.isNotEmpty(pay_info)) {
				RepaymentDto redisDto = JSON.parseObject(pay_info, RepaymentDto.class);
				if (redisDto != null) {
					repaymentDto.setUseCoupon(redisDto.getUseCoupon());
				}
			}

			double moneyWithholdDou = bwCapitalWithhold.getMoneyWithhold().doubleValue();
			if (SystemConstant.WITHHOLD_TEST_BOOL) {// 测试
				moneyWithholdDou = bwCapitalWithhold.getMoney().doubleValue();
			}

			repaymentDto.setAmount(moneyWithholdDou);
			repaymentDto.setPayChannel(7);
			repaymentDto.setTradeTime(tradeTime);
			repaymentDto.setTradeType(1);
			repaymentDto.setTradeCode("0");

			// 支付成功
			AppResponseResult updateResult = iBwRepaymentService.updateOrderByTradeMoney(repaymentDto);

			if ("000".equals(updateResult.getCode())) {
				logger.info("口袋扣款回调处理成功orderId：" + orderId);
			} else {
				logger.info("口袋扣款回调处理失败orderId：" + orderId + updateResult.getMsg());
			}
		} else {// 审核后台口袋回调
			logger.info("【KoudaiController.updateSuccessNotify】orderId:" + orderId + "审核后台口袋回调");
		}
		// 删除redis数据
		RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
		RedisUtils.hdel("pay_info", orderId.toString());
	}

	@ResponseBody
	@RequestMapping("/singlePushBack.do") // 放款通知推送
	public AppResponseResult singlePushBack(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("口袋回调参数==》" + params);
			if (CommUtils.isNull(params)) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("参数为空。");
				return appResponseResult;
			}
			JSONObject jsb = JSON.parseObject(params);
			String code = CommUtils.toString(jsb.get("retCode"));

			String msg = unicodeToString(CommUtils.toString(jsb.get("retMsg")));

			JSONObject jsbTime = jsb.getJSONObject("requestContent");
			String loanDate = StringUtil.toString(jsbTime.get("loanTime"));

			JSONObject jsbData = jsb.getJSONObject("originalData");
			JSONObject jsbOrder = jsbData.getJSONObject("orderBase");

			String orderNo = StringUtil.toString(jsbOrder.get("outTradeNo"));

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder); // 查询订单

			if (bwOrder == null) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("工单信息不存在。");
				return appResponseResult;
			}
			Long orderId = bwOrder.getId();

			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId, 2); // 查询渠道为口袋的对应工单

			if (bwCapitalPush != null) { // 如果有订单，且状态为放款成功
				if ((2 == bwCapitalPush.getPushStatus() && "0".equals(code))) {
					appResponseResult.setCode("1004");
					appResponseResult.setMsg("该order_Id已推送成功，请不要重复推送");
					logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
					return appResponseResult;
				}
			} else { // 如果没有订单，创建订单
				bwCapitalPush = new BwCapitalPush();
				bwCapitalPush.setOrderId(orderId);
				bwCapitalPush.setCreateTime(now);
				bwCapitalPush.setCapitalId(2); // 渠道设置为口袋
				bwCapitalPush.setPushCount(0); // 发送次数
			}

			if (CommUtils.isNull(bwCapitalPush.getId())) {
				bwCapitalPushService.save(bwCapitalPush); // 保存资方订单信息
			}
			bwCapitalPush.setCode(code); // 获取状态码
			bwCapitalPush.setMsg(msg); // 响应结果
			bwCapitalPush.setUpdateTime(now);

			if ("30001".equals(code)) {

				if (CommUtils.isNull(loanDate)) {
					appResponseResult.setCode("1002");
					appResponseResult.setMsg("loanDate参数为空。");
					return appResponseResult;
				}

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date loanTime = null;
				try {
					loanDate = CommUtils.convertDateToString(new Date(Long.parseLong(loanDate) * 1000),
							"yyyy-MM-dd HH:mm:ss");
					loanTime = format.parse(loanDate);

				} catch (Exception e) {
					logger.error("还款时间：", e);
					e.printStackTrace();
					appResponseResult.setCode("1001");
					appResponseResult.setMsg("loanTime格式错误");
					return appResponseResult;
				}

				bwCapitalPush.setPushStatus(2);
				bwCapitalPush.setCode(code);
				bwCapitalPush.setMsg(msg);
				bwCapitalPush.setUpdateTime(now);

				try {
					// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给口袋
					List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							.listBwRepaymentPlanByOrderId(orderId);
					if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
						appResponseResult.setCode("1004");
						appResponseResult.setMsg("该order_Id已推送成功，请不要重复推送");
						logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
						return appResponseResult;
					}
					// 还款时间
					Date repaymentTime = null;
					if (7 == bwOrder.getProductId()) {
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder,
								CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd"));
					} else {
						if (2 == bwOrder.getProductType()) { // 如果产品类型是分期
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);

						} else {
							// 根据产品类型查询产品（分期）
							BwProductDictionary bwProductDictionary = bwProductDictionaryService
									.findBwProductDictionaryById(bwOrder.getProductId());
							String termType = null;
							String term = null;
							if (bwProductDictionary != null) {
								termType = bwProductDictionary.getpTermType(); // 产品类型（1：月；2：天）pTermType
								term = bwProductDictionary.getpTerm(); // 产品期限
							}

							if ("1".equals(termType)) {
								repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
							} else if ("2".equals(termType)) {
								repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
							} else {
								repaymentTime = MyDateUtils.addDays(loanTime, 30);
							}
							// 根据工单信息和指定还款时间生成借款人还款计划
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
									bwProductDictionary);
						}
					}

					bwCapitalPush.setPushStatus(2);// 放款成功
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

					// 更新push 表
					BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 2); // 渠道为口袋的工单
					if (bwOrderPushInfo != null) {
						bwOrderPushInfo.setFullTime(loanTime);
						bwOrderPushInfo.setPushStatus(2); // 状态。0：推送失败，1：推送成功，2：接收成功，3：接收失败
						bwOrderPushInfo.setPushRemark("接收成功");
						bwOrderPushInfo.setUpdateTime(now);
						bwOrderPushInfo.setLoanTime(loanTime); // 设置放款时间
						bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
					}
					bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);

					BwCapitalRepay bwCapitalRepay = new BwCapitalRepay();
					bwCapitalRepay.setOrderId(orderId);
					bwCapitalRepay.setCreateTime(now);
					bwCapitalRepay.setCapitalId(2); // 渠道设置为口袋
					bwCapitalRepay.setType(0);
					Date repayTime = MyDateUtils.addDays(loanTime, 30);
					bwCapitalRepay.setRepayTime(repayTime);

					bwCapitalRepayService.save(bwCapitalRepay);

					try {

						// 渠道放款成功 - 通知
						String channel = String.valueOf(bwOrder.getChannel());
						if (!"1".equals(channel) && !"2".equals(channel)) {
							HashMap<String, String> hm = new HashMap<>();
							hm.put("channelId", channel);
							hm.put("orderId", String.valueOf(bwOrder.getId()));
							hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
							hm.put("result", "放款成功");
							String hmData = JSON.toJSONString(hm);
							RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);
						}

						// 通知短信
						Long borrowerId = bwOrder.getBorrowerId();
						BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
						String sex = bwBorrower.getSex() + "";
						String userName = bwBorrower.getName();

						MessageDto messageDto = new MessageDto();
						messageDto.setBusinessScenario("1"); // 营销短信
						messageDto.setPhone(bwBorrower.getPhone());
						messageDto.setType("1"); // 文字短信

						if ("0".equals(sex)) {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "女士，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						} else {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "先生，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						}

						RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));

					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					logger.info("口袋推送处理结果：保存信息出错，当前工单号：" + orderId);
					throw e;
				}

				appResponseResult.setCode("0");
				appResponseResult.setMsg("成功");
			} else if ("30002".equals(code)) {
				bwCapitalPush.setPushStatus(3);// 放款失败
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setCode("0");
				appResponseResult.setMsg("成功");
			} else {
				appResponseResult.setCode("0");
				appResponseResult.setMsg("未知的请求");
			}

		} catch (Exception e) {
			logger.error("口袋推送结果异常：", e);
		}

		return appResponseResult;
	}

	@ResponseBody
	@RequestMapping("/singlePushQuery.do") // 放款状态查询
	public void singlePushQuery(HttpServletRequest request, HttpServletResponse response) {
		logger.info("口袋获取订单状态");
		String orderIds = request.getParameter("orderIds");
		if (CommUtils.isNull(orderIds)) {
			try {
				orderIds = IOUtils.toString(request.getInputStream(), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("口袋获取订单状态所有：" + orderIds);
		if (CommUtils.isNull(orderIds)) {
			return;
		}
		String[] orders = orderIds.split(",");
		if (CommUtils.isNull(orders)) {
			return;
		}
		for (String orderIdStr : orders) {
			System.out.println(orderIdStr + ":开始");
			try {
				BwOrder bwOrder = new BwOrder();
				bwOrder.setId(Long.parseLong(orderIdStr));
				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder); // 查询订单
				if (bwOrder.getStatusId() != 14) {
					continue;
				}
				Long orderId = bwOrder.getId();
				Date now = new Date();
				Map map = new HashMap<>();
				map.put("orderNo", bwOrder.getOrderNo());
				String params = CapitalKoudaiService.queryOrderWithdrawStatus(JSON.toJSONString(map));

				logger.info("口袋返回参数==》" + params);
				if (CommUtils.isNull(params)) {
					;
				}
				JSONObject jsb = JSON.parseObject(params);
				String code = CommUtils.toString(jsb.get("retCode"));
				String msg = CommUtils.toString(jsb.get("retMsg"));

				JSONObject jsbTime = jsb.getJSONObject("retData");
				String status = StringUtil.toString(jsbTime.get("status"));
				String loanDate = "";
				if ("2".equals(status)) {
					loanDate = StringUtil.toString(jsbTime.get("loanTime"));
					logger.info("口袋放款成功：" + orderId + ",日期：" + loanDate);
				} else {
					logger.info("口袋放款失败：" + orderId);
				}

				BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId, 2); // 查询渠道为口袋的对应工单

				if (bwCapitalPush != null) { // 如果有订单，且状态为放款成功
					if ((2 == bwCapitalPush.getPushStatus() && "0".equals(code))) {
						logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
						continue;
					}
				} else { // 如果没有订单，创建订单
					bwCapitalPush = new BwCapitalPush();
					bwCapitalPush.setOrderId(orderId);
					bwCapitalPush.setCreateTime(now);
					bwCapitalPush.setCapitalId(2); // 渠道设置为口袋
					bwCapitalPush.setPushCount(0); // 发送次数
				}

				if (CommUtils.isNull(bwCapitalPush.getId())) {
					bwCapitalPushService.save(bwCapitalPush); // 保存资方订单信息
				}
				bwCapitalPush.setCode(code); // 获取状态码
				bwCapitalPush.setMsg(msg); // 响应结果
				bwCapitalPush.setUpdateTime(now);

				if ("0".equals(code) && "2".equals(status)) {
					if (CommUtils.isNull(loanDate)) {
						continue;
					}

					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date loanTime = null;
					try {
						loanDate = CommUtils.convertDateToString(new Date(Long.parseLong(loanDate) * 1000),
								"yyyy-MM-dd HH:mm:ss");
						loanTime = format.parse(loanDate);

					} catch (Exception e) {
						logger.error("还款时间：", e);
						e.printStackTrace();
						continue;
					}

					bwCapitalPush.setPushStatus(2);
					bwCapitalPush.setCode(code);
					bwCapitalPush.setMsg(msg);
					bwCapitalPush.setUpdateTime(now);

					try {
						// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给口袋
						List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
								.listBwRepaymentPlanByOrderId(orderId);
						if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {

							logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
							continue;
						}
						// 还款时间
						Date repaymentTime = null;
						if (7 == bwOrder.getProductId()) {
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder,
									CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd"));
						} else {
							if (2 == bwOrder.getProductType()) { // 如果产品类型是分期
								bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);

							} else {
								// 根据产品类型查询产品（分期）
								BwProductDictionary bwProductDictionary = bwProductDictionaryService
										.findBwProductDictionaryById(bwOrder.getProductId());
								String termType = null;
								String term = null;
								if (bwProductDictionary != null) {
									termType = bwProductDictionary.getpTermType(); // 产品类型（1：月；2：天）pTermType
									term = bwProductDictionary.getpTerm(); // 产品期限
								}

								if ("1".equals(termType)) {
									repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
								} else if ("2".equals(termType)) {
									repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
								} else {
									repaymentTime = MyDateUtils.addDays(loanTime, 30);
								}
								// 根据工单信息和指定还款时间生成借款人还款计划
								bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
										bwProductDictionary);
							}
						}

						bwCapitalPush.setPushStatus(2);// 放款成功
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

						// 更新push 表
						BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 2); // 渠道为口袋的工单
						if (bwOrderPushInfo != null) {
							bwOrderPushInfo.setFullTime(loanTime);
							bwOrderPushInfo.setPushStatus(2); // 状态。0：推送失败，1：推送成功，2：接收成功，3：接收失败
							bwOrderPushInfo.setPushRemark("接收成功");
							bwOrderPushInfo.setUpdateTime(now);
							bwOrderPushInfo.setLoanTime(loanTime); // 设置放款时间
							bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
						}
						bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);

						try {

							// 渠道放款成功 - 通知
							String channel = String.valueOf(bwOrder.getChannel());
							if (!"1".equals(channel) && !"2".equals(channel)) {
								HashMap<String, String> hm = new HashMap<>();
								hm.put("channelId", channel);
								hm.put("orderId", String.valueOf(bwOrder.getId()));
								hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
								hm.put("result", "放款成功");
								String hmData = JSON.toJSONString(hm);
								RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);

							}

							// 通知短信
							Long borrowerId = bwOrder.getBorrowerId();
							BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
							String sex = bwBorrower.getSex() + "";
							String userName = bwBorrower.getName();

							MessageDto messageDto = new MessageDto();
							messageDto.setBusinessScenario("1"); // 营销短信
							messageDto.setPhone(bwBorrower.getPhone());
							messageDto.setType("1"); // 文字短信

							if ("0".equals(sex)) {
								messageDto.setMsg(
										"【水象分期】尊敬的" + userName + "女士，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
							} else {
								messageDto.setMsg(
										"【水象分期】尊敬的" + userName + "先生，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
							}

							RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));

						} catch (Exception e) {
							e.printStackTrace();
						}

					} catch (Exception e) {
						bwCapitalPush.setPushStatus(3);// 接收失败
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
						logger.info("口袋推送处理结果：保存信息出错，当前工单号：" + orderId);
						throw e;
					}

					continue;
				} else if ("0".equals(code) && "3".equals(status)) {
					bwCapitalPush.setPushStatus(3);// 放款失败
					bwCapitalPush.setMsg("放款失败");
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					continue;
				} else if ("0".equals(code) && "1".equals(status)) {
					bwCapitalPush.setPushStatus(1);// 提现中
					bwCapitalPush.setMsg("提现中");
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					continue;
				} else {
					continue;
				}

			} catch (Exception e) {
				logger.error("口袋推送结果异常：", e);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/repayNoticeBack.do") // 还款通知推送
	public AppResponseResult repayNoticeBack(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("口袋还款回调参数==》" + params);
			if (CommUtils.isNull(params)) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("参数为空。");
				return appResponseResult;
			}
			JSONObject jsb = JSON.parseObject(params);
			String code = CommUtils.toString(jsb.get("retCode"));

			String msg = unicodeToString(CommUtils.toString(jsb.get("retMsg")));

			JSONObject jsbData = jsb.getJSONObject("retData");
			String orderNo = StringUtil.toString(jsbData.get("out_trade_no"));
			JSONObject jsbTime = jsb.getJSONObject("requestContent");
			String repayTime = CommUtils.toString(jsbTime.get("trueRepaymentTime"));

			JSONObject jsbOriginalData = jsb.getJSONObject("originalData");
			String money = CommUtils.toString(jsbOriginalData.get("money"));
			String interest = CommUtils.toString(jsbOriginalData.get("interest"));

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder); // 查询订单

			if (bwOrder == null) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("工单信息不存在。");
				return appResponseResult;
			}
			Long orderId = bwOrder.getId();

			BwCapitalRepay bwCapitalRepay = bwCapitalRepayService.queryBwCapitalRepay(orderId, 2); // 查询渠道为口袋的对应工单

			if (bwCapitalRepay != null) { // 如果有订单，且状态为放款成功
				if ((2 == bwCapitalRepay.getType() && "20005".equals(code))) {
					appResponseResult.setCode("1004");
					appResponseResult.setMsg("该out_trade_no已推送成功，请不要重复推送");
					logger.info("口袋推送处理结果：重复推送，当前工单号：" + orderId);
					return appResponseResult;
				}
			} else { // 如果没有订单，创建订单
				bwCapitalRepay = new BwCapitalRepay();
				bwCapitalRepay.setOrderId(orderId);
				bwCapitalRepay.setCreateTime(now);
				bwCapitalRepay.setCapitalId(2); // 渠道设置为口袋
			}

			if (CommUtils.isNull(bwCapitalRepay.getId())) {
				bwCapitalRepayService.save(bwCapitalRepay); // 保存资方订单信息
			}
			bwCapitalRepay.setCode(code); // 获取状态码
			bwCapitalRepay.setMessage(msg); // 响应结果
			bwCapitalRepay.setUpdateTime(now);
			bwCapitalRepay.setMoney(money);
			bwCapitalRepay.setInterest(interest);

			if ("20005".equals(code)) {

				if (CommUtils.isNull(repayTime)) {
					appResponseResult.setCode("1002");
					appResponseResult.setMsg("trueRepaymentTime参数为空。");
					return appResponseResult;
				}

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date loanTime = null;
				try {
					repayTime = CommUtils.convertDateToString(new Date(Long.parseLong(repayTime) * 1000),
							"yyyy-MM-dd HH:mm:ss");
					loanTime = format.parse(repayTime);

				} catch (Exception e) {
					logger.error("还款时间：", e);
					e.printStackTrace();
					appResponseResult.setCode("1001");
					appResponseResult.setMsg("loanTime格式错误");
					return appResponseResult;
				}

				bwCapitalRepay.setType(2);
				bwCapitalRepay.setSettlementTime(loanTime);
				bwCapitalRepayService.updateBwCapitalRepay(bwCapitalRepay);

				appResponseResult.setCode("0");
				appResponseResult.setMsg("成功");
			} else if ("30002".equals(code)) {
				bwCapitalRepay.setType(3);// 还款失败
				bwCapitalRepayService.updateBwCapitalRepay(bwCapitalRepay);
				appResponseResult.setCode("0");
				appResponseResult.setMsg("成功");
			} else {
				bwCapitalRepay.setType(3);// 还款失败
				bwCapitalRepayService.updateBwCapitalRepay(bwCapitalRepay);
				appResponseResult.setCode("0");
				appResponseResult.setMsg("未知的请求");
			}

		} catch (Exception e) {
			logger.error("口袋推送结果异常：", e);
		}

		return appResponseResult;
	}

	public static String unicodeToString(String str) {

		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			// group 6728
			String group = matcher.group(2);
			// ch:'木' 26408
			ch = (char) Integer.parseInt(group, 16);
			// group1 \u6728
			String group1 = matcher.group(1);
			str = str.replace(group1, ch + "");
		}
		return str;
	}

	public static void main(String[] args) throws Exception {
		// String str =
		// "order_id=B20170728050104230762K5&pay_order_id=j6g3dyg6f76c5suy&pay_date=20170817&err_code=0&err_msg=%E6%88%90%E5%8A%9F&money=1&state=2&pay_summary=20170817145600&third_platform=7&result=%E4%BA%A4%E6%98%93%E6%88%90%E5%8A%9F&sign=ZXJyX2NvZGU9MCZlcnJfbXNnPSVFNiU4OCU5MCVFNSU4QSU5RiZtb25leT0xJm9yZGVyX2lkPUIyMDE3MDcyODA1MDEwNDIzMDc2Mks1JnBheV9kYXRlPTIwMTcwODE3JnBheV9vcmRlcl9pZD1qNmczZHlnNmY3NmM1c3V5JnBheV9zdW1tYXJ5PTIwMTcwODE3MTQ1NjAwJnJlc3VsdD0lRTQlQkElQTQlRTYlOTglOTMlRTYlODglOTAlRTUlOEElOUYmc3RhdGU9MiZ0aGlyZF9wbGF0Zm9ybT03a2RsYyoqa291ZGFpX3NodWl4aWFuZ18yMDE3KipwYXk%3D";
		//
		// Map<String, String> map = KdaiSignUtils.getNotityMap(str);
		// // map.remove("sign");
		// KdaiSignUtils.argSort(map);
		// System.out.println(JSON.toJSONString(map));
		// // KdaiSignUtils.verifySign(JSON.parseObject(JSON.toJSONString(map)), "**kdpay_koudai_shuixiang_2017**");
		// System.out.println(
		// KdaiSignUtils.verifySign(JSON.parseObject(JSON.toJSONString(map)), "kdlc**koudai_shuixiang_2017**pay"));
		// KdaiSignUtils.getSign(str, "**kdpay_koudai_shuixiang_2017**");
		// System.out.println();
		String str = "{\"code\":\"-2\",\"message\":\"{\"retCode\":30004,\"retData\":{\"idNo\":\"429004199110191438\"},\"retMsg\":\"开户异常\",\"retTraceId\":\"a9491f2cf1ad198ae6785b9e21a6dd6f\",\"version\":\"1.0\",\"txCode\":\"accountOpen\"}\"}";
		// JSONObject jsb = JSON.parseObject(str);
		// System.out.println(jsb.get("code"));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date loanTime = null;
		try {
			String repayTime = CommUtils.convertDateToString(new Date(Long.parseLong("1520235854")),
					"yyyy-MM-dd HH:mm:ss");
			loanTime = format.parse(repayTime);

		} catch (Exception e) {
		}
		System.out.println(CommUtils.convertDateToString(loanTime, "yyyy-MM-dd HH:mm:ss"));

		System.out.println(unicodeToString(CommUtils.toString("\u878d\u8d44\u4eba\u8fd8\u6b3e\u6210\u529f")));
	}

}
