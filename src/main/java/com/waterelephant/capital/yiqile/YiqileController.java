/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.capital.yiqile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.capital.service.CapitalYqileService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCapitalPush;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwCapitalPushService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * 
 * Module:
 * 
 * YiqileController.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/yiqile")
public class YiqileController {
	private Logger logger = Logger.getLogger(YiqileController.class);

	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private IBwOrderPushInfoService bwOrderPushInfoService;
	@Resource
	private BwOrderRongService bwOrderRongService;
	@Resource
	private BwRepaymentPlanService bwRepaymentPlanService;
	@Resource
	private BwCapitalPushService bwCapitalPushService;
	@Resource
	private BwProductDictionaryService bwProductDictionaryService;
	@Resource
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Resource
	private IBwAdjunctService bwAdjunctService;
	@Resource
	private BwBorrowerService bwBorrowerService;

	@ResponseBody
	@RequestMapping("/loanAdvicePush.do")
	public ResponseResult loanAdvice(HttpServletRequest request, HttpServletResponse response) {
		ResponseResult appResponseResult = new ResponseResult();

		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("亿奇乐回调参数==》" + params);
			if (CommUtils.isNull(params)) {
				appResponseResult.setRtnCode(1002);
				appResponseResult.setRtnMsg("参数为空。");
				return appResponseResult;
			}
			Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);

			if (!ApiUtil.checkSign(3, SystemConstant.CAPITAL_YIQILE_KEY, paramsMap)) {
				appResponseResult.setRtnCode(1002);
				appResponseResult.setRtnMsg("验签失败。");
				logger.info("验签失败：" + params);
				return appResponseResult;
			}

			if (CommUtils.isNull(StringUtil.toString(paramsMap.get("payment_id")))) {
				appResponseResult.setRtnCode(1002);
				appResponseResult.setRtnMsg("payment_id参数为空。");
				return appResponseResult;
			}
			String code = StringUtil.toString(paramsMap.get("result"));
			String msg = StringUtil.toString(paramsMap.get("message"));
			String orderNo = StringUtil.toString(paramsMap.get("payment_id"));
			String payTime = StringUtil.toString(paramsMap.get("pay_time"));
			String money = StringUtil.toString(paramsMap.get("money"));

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder == null) {
				appResponseResult.setRtnCode(1002);
				appResponseResult.setRtnMsg("工单信息不存在。");
				return appResponseResult;
			}
			Long orderId = bwOrder.getId();

			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(bwOrder.getId(), 4);

			if (bwCapitalPush != null) {
				if ((2 == bwCapitalPush.getPushStatus() && "0".equals(code))) {
					appResponseResult.setRtnCode(1004);
					appResponseResult.setRtnMsg("该prdId已推送成功，请不要重复推送");

					logger.info("亿奇乐推送处理结果：重复推送，当前工单号：" + bwOrder.getId());
					return appResponseResult;
				}
			} else {
				bwCapitalPush = new BwCapitalPush();
				bwCapitalPush.setOrderId(bwOrder.getId());
				bwCapitalPush.setCreateTime(now);
				bwCapitalPush.setCapitalId(4);
				bwCapitalPush.setPushCount(0);
			}

			if (CommUtils.isNull(bwCapitalPush.getId())) {
				bwCapitalPushService.save(bwCapitalPush);
			}
			bwCapitalPush.setCode(StringUtil.toString(paramsMap.get("code")));
			bwCapitalPush.setMsg(StringUtil.toString(paramsMap.get("msg")));
			bwCapitalPush.setUpdateTime(now);

			if ("0".equals(code)) {

				if (CommUtils.isNull(payTime)) {
					appResponseResult.setRtnCode(1002);
					appResponseResult.setRtnMsg("payTime参数为空。");
					return appResponseResult;
				}

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date loanTime = null;
				Date maturityTime = null;
				String loanDate = null;
				try {
					loanDate = CommUtils.convertDateToString(new Date(Long.parseLong(payTime)), "yyyy-MM-dd HH:mm:ss");
					loanTime = format.parse(loanDate);

				} catch (Exception e) {
					logger.error("还款时间：", e);
					e.printStackTrace();
					appResponseResult.setRtnCode(1001);
					appResponseResult.setRtnMsg("pay_time格式错误");
					return appResponseResult;
				}

				bwCapitalPush.setPushStatus(2);
				bwCapitalPush.setCode(code);
				bwCapitalPush.setMsg(msg);
				bwCapitalPush.setUpdateTime(now);

				try {
					// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给亿奇乐
					List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							.listBwRepaymentPlanByOrderId(bwOrder.getId());
					if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
						appResponseResult.setRtnCode(0);
						appResponseResult.setRtnMsg("该单已推送成功，请不要重复推送");
						logger.info("亿奇乐推送处理结果：重复推送，当前工单号：" + bwOrder.getId());
						return appResponseResult;
					}
					// 还款时间
					Date repaymentTime = null;

					if (2 == bwOrder.getProductType()) {
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);

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
					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repayTime);
					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, repayDate);

					bwCapitalPush.setPushStatus(2);// 放款成功
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

					// 更新push 表
					BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(bwOrder.getId(), 8);
					if (bwOrderPushInfo != null) {
						bwOrderPushInfo.setFullTime(loanTime);
						bwOrderPushInfo.setPushStatus(2);
						bwOrderPushInfo.setPushRemark("接收成功");
						bwOrderPushInfo.setUpdateTime(now);
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
							messageDto.setMsg("【水象分期】尊敬的" + userName + "女士，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						} else {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "先生，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
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

					logger.info("亿奇乐推送处理结果：接收成功，当前工单号：" + orderId + "，还款日期：" + loanDate);
				} catch (Exception e) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					logger.info("亿奇乐推送处理结果：保存信息出错，当前工单号：" + orderId);
					throw e;
				}
				// 放款成功存储orderId到Redis中 dycode0001
				try {
					RedisUtils.rpush("order:loan", String.valueOf(orderId));
				} catch (Exception e) {
					logger.error("放款成功存储orderId到redis异常：", e);
				}
				appResponseResult.setRtnCode(0);
				appResponseResult.setRtnMsg("成功");
			} else if ("1".equals(code)) {
				bwCapitalPush.setPushStatus(3);// 放款失败
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setRtnCode(0);
				appResponseResult.setRtnMsg("成功");
			} else {
				appResponseResult.setRtnCode(0);
				appResponseResult.setRtnMsg("成功");
			}

		} catch (Exception e) {
			logger.error("亿奇乐推送结果异常：", e);
		}
		logger.info(JSON.toJSONString(appResponseResult));
		return appResponseResult;
	}

	/**
	 * 主动查询放款状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/orderQuerys.do")
	public void orderQuerys(HttpServletRequest request, HttpServletResponse response) {

		String token = RedisUtils.hget("capital:token", "token4");
		if (CommUtils.isNull(token)) {
			logger.info("亿奇乐token为空");
			return;
		}

		logger.info("亿奇乐获取订单状态");
		String orderIds = request.getParameter("orderIds");
		if (CommUtils.isNull(orderIds)) {
			try {
				orderIds = IOUtils.toString(request.getInputStream(), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("亿奇乐获取订单状态所有：" + orderIds);
		if (CommUtils.isNull(orderIds)) {
			return;
		}
		String[] orders = orderIds.split(",");
		if (CommUtils.isNull(orders)) {
			return;
		}
		for (String orderId : orders) {
			try {
				logger.info("亿奇乐获取订单状态开始：" + orderId);
				BwOrder bwOrder = new BwOrder();
				bwOrder.setId(Long.parseLong(orderId.trim()));
				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
				if (bwOrder == null) {
					continue;
				}
				String returnStr = CapitalYqileService
						.orderQuery("{\"order_no\":\"" + bwOrder.getOrderNo() + "\",\"token\":\"" + token + "\"}");
				logger.info("亿奇乐获取订单状态结束：" + returnStr);

				JSONObject jsb = JSONObject.parseObject(returnStr);
				returnStr = returnStr.replace("\\", "").replace("\"{", "{").replace("}\"", "}");
				String rtnCode = CommUtils.toString(jsb.getString("rtn_code"));
				if ("0".equals(rtnCode)) {
					JSONObject body = jsb.getJSONObject("body");
					String code = StringUtil.toString(body.get("payment_status"));
					String payTime = StringUtil.toString(body.get("pay_time"));
					String msg = StringUtil.toString(body.get("payment_msg"));

					BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(bwOrder.getId(), 4);

					if (bwCapitalPush != null) {
						if ((2 == bwCapitalPush.getPushStatus() && "0".equals(code))) {
							logger.info("亿奇乐推送处理结果：重复推送，当前工单号：" + orderId);
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

					if ("4".equals(code)) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date loanTime = null;
						String loanDate = null;
						try {
							loanDate = CommUtils.convertDateToString(new Date(Long.parseLong(payTime)),
									"yyyy-MM-dd HH:mm:ss");
							loanTime = format.parse(loanDate);

						} catch (Exception e) {
							logger.error("还款时间异常：" + orderId, e);
							continue;
						}

						bwCapitalPush.setCode(code);
						bwCapitalPush.setUpdateTime(new Date());

						try {
							// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给亿奇乐
							List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
									.listBwRepaymentPlanByOrderId(bwOrder.getId());
							if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
								logger.info("亿奇乐推送处理结果：重复推送，当前工单号：" + orderId);
								continue;
							}
							// 还款时间
							Date repaymentTime = null;

							if (2 == bwOrder.getProductType()) {
								bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);

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
							bwCapitalPush.setMsg(msg);
							bwCapitalPush.setPushStatus(2);// 放款成功
							bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

							// 更新push 表
							BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(bwOrder.getId(),
									8);
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

								// // 12:渠道为好贷
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
								// // channelService.sendOrderStatus(CommUtils.toString(bwOrder.getChannel()),
								// // CommUtils.toString(bwOrder.getId()), "14");
								// // PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
								// // BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
							} catch (Exception e) {
								e.printStackTrace();
							}

							// // 根据工单id查询融360对应的订单号
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
							// } catch (Exception e) {
							// logger.error("调用融360接口异常", e);
							// }

						} catch (Exception e) {
							logger.info("亿奇乐推送处理结果：保存信息出错，当前工单号：" + orderId);
						}

					} else if ("1".equals(code)) {
						bwCapitalPush.setPushStatus(0);//
						bwCapitalPush.setMsg(msg);
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					} else if ("5".equals(code)) {
						bwCapitalPush.setPushStatus(3);
						bwCapitalPush.setMsg(msg);
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					} else {
						bwCapitalPush.setMsg(msg);
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					}
				} else {
					logger.info("亿奇乐查询处理结果失败：" + orderId + ":" + returnStr);
				}

			} catch (Exception e) {
				logger.error("异常");
				continue;
			}

		}
	}

	/**
	 * 查看合同
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkContract.do")
	public String checkContract(HttpServletRequest request, HttpServletResponse response) {
		String params = null;
		try {
			params = CommUtils.toString(request.getParameter("params"));
			if (CommUtils.isNull(params)) {
				params = IOUtils.toString(request.getInputStream(), "utf-8");
			}
			// String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("亿奇乐合同查看==》" + params);
			if (CommUtils.isNull(params)) {
				return "contract_fail";
			}
			Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);

			if (!ApiUtil.checkSign(3, SystemConstant.CAPITAL_YIQILE_KEY, paramsMap)) {
				return "contract_fail";
			}

			String orderNo = StringUtil.toString(paramsMap.get("payment_id"));
			String paymentType = StringUtil.toString(paramsMap.get("payment_type"));

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder == null) {
				return "contract_fail";
			}
			int type = 0;
			if ("1".equals(paymentType)) {
				type = 13;
			} else {
				type = 21;
			}
			List<BwAdjunct> list = bwAdjunctService.findAdjunctByOrderIdAndAdjunctType(bwOrder.getId(), type);

			if (list == null) {
				return "contract_fail";
			} else {
				response.setContentType("application/pdf");
				return "redirect:" + SystemConstant.PDF_URL + list.get(0).getAdjunctPath();
				// return "redirect:" + "http://img.beadwallet.com/" + list.get(0).getAdjunctPath();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "contract_fail";
	}

	public static void main(String[] args) {
		System.out.println(
				CommUtils.convertDateToString(new Date(Long.parseLong("1510743192779")), "yyyy-MM-dd HH:mm:ss"));
	}

}
