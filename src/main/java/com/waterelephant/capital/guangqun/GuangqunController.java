/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.capital.guangqun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.sms.dto.MessageDto;
import com.beadwallet.service.utils.HttpRequest;
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
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;

/**
 * 
 * Module:
 * 
 * GuangqunController.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/guangqun")
public class GuangqunController {
	private Logger logger = Logger.getLogger(GuangqunController.class);

	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private IBwOrderPushInfoService bwOrderPushInfoService;
	@Resource
	private BwOrderRongService bwOrderRongService;
	@Resource
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Resource
	private BwCapitalPushService bwCapitalPushService;
	@Resource
	private BwProductDictionaryService bwProductDictionaryService;
	@Resource
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Resource
	private BwBorrowerService bwBorrowerService;

	@ResponseBody
	@RequestMapping("/loanAdvicePush.do")
	public AppResponseResult loanAdvice(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();

		try {
			Date now = new Date();

			// 计算还款时间
			String params = request.getParameter("params");
			logger.info("广群 回调参数==》" + params);

			if (CommUtils.isNull(params)) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("参数为空。");
				return appResponseResult;
			}
			Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);

			if (CommUtils.isNull(StringUtil.toString(paramsMap.get("prdId")))) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("prdId参数为空。");
				return appResponseResult;
			}
			String code = StringUtil.toString(paramsMap.get("code"));
			String orderStr = StringUtil.toString(paramsMap.get("prdId"));

			if (!CommUtils.isNull(orderStr) && orderStr.startsWith("Q")) {
				// RedisUtils.rpush("capital:guangqunNotity", params);
				try {
					HttpRequest.doPost("http://47.100.14.112:8222/wallet77_loanapp_job/guangqun/pushNotity.do",
							"params=" + params);
				} catch (Exception e) {
					e.printStackTrace();
					appResponseResult.setCode("1002");
					appResponseResult.setMsg("工单信息有误。");
					return appResponseResult;
				}
				appResponseResult.setCode("0000");
				appResponseResult.setMsg("成功");
				return appResponseResult;
			}
			Long orderId = Long.parseLong(orderStr);

			String loanDate = StringUtil.toString(paramsMap.get("repayDate"));
			String maturityStr = StringUtil.toString(paramsMap.get("maturitytime"));

			BwOrder bwOrder = new BwOrder();
			bwOrder.setId(orderId);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder == null) {
				appResponseResult.setCode("1002");
				appResponseResult.setMsg("工单信息不存在。");
				return appResponseResult;
			}

			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId, 3);

			if (bwCapitalPush != null) {
				if ((2 == bwCapitalPush.getPushStatus() && "0000".equals(code))
						|| (4 == bwCapitalPush.getPushStatus() && "1006".equals(code))) {
					appResponseResult.setCode("1004");
					appResponseResult.setMsg("该prdId已推送成功，请不要重复推送");
					logger.info("广群推送处理结果：重复推送，当前工单号：" + orderId);
					return appResponseResult;
				}
			} else {
				bwCapitalPush = new BwCapitalPush();
				bwCapitalPush.setOrderId(orderId);
				bwCapitalPush.setCreateTime(now);
				bwCapitalPush.setCapitalId(3);
				bwCapitalPush.setPushCount(0);
			}

			if (CommUtils.isNull(bwCapitalPush.getId())) {
				bwCapitalPushService.save(bwCapitalPush);
			}
			bwCapitalPush.setCode(StringUtil.toString(paramsMap.get("code")));
			bwCapitalPush.setMsg(StringUtil.toString(paramsMap.get("msg")));
			bwCapitalPush.setUpdateTime(now);

			if ("0000".equals(code)) {

				if (CommUtils.isNull(StringUtil.toString(paramsMap.get("repayDate")))) {
					appResponseResult.setCode("1002");
					appResponseResult.setMsg("repayDate参数为空。");
					return appResponseResult;
				}

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date loanTime = null;
				Date maturityTime = null;
				try {
					loanDate = DateUtil.getDateString(loanDate, "yyyy-MM-dd");
					loanTime = format.parse(loanDate);
					if (!CommUtils.isNull(maturityStr)) {
						maturityTime = CommUtils.convertStringToDate(maturityStr, "yyyy-MM-dd HH:mm:ss");
					}

				} catch (Exception e) {
					logger.error("还款时间：", e);
					e.printStackTrace();
					appResponseResult.setCode("1001");
					appResponseResult.setMsg("repayDate格式错误");
					return appResponseResult;
				}

				bwCapitalPush.setMaturityTime(maturityTime);
				bwCapitalPush.setPushStatus(2);
				bwCapitalPush.setCode(StringUtil.toString(paramsMap.get("code")));
				bwCapitalPush.setMsg(StringUtil.toString(paramsMap.get("msg")));
				bwCapitalPush.setUpdateTime(now);

				try {
					// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给广群
					List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							.listBwRepaymentPlanByOrderId(orderId);
					if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
						appResponseResult.setCode("1004");
						appResponseResult.setMsg("该prdId已推送成功，请不要重复推送");
						logger.info("广群推送处理结果：重复推送，当前工单号：" + orderId);
						return appResponseResult;
					}
					// 还款时间
					Date repaymentTime = null;

					if (7 == bwOrder.getProductId()) {
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder,
								CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd"));
					} else {
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
					}

					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repayTime);
					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, repayDate);

					bwCapitalPush.setPushStatus(2);// 放款成功
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

					// 更新push 表
					BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 5);
					if (bwOrderPushInfo != null) {
						bwOrderPushInfo.setFullTime(loanTime);
						bwOrderPushInfo.setPushStatus(2);
						bwOrderPushInfo.setPushRemark("接收成功");
						bwOrderPushInfo.setUpdateTime(now);
						bwOrderPushInfo.setLoanTime(loanTime);
						bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
					}
					bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);

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

						// 待定
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

					// 根据工单id查询融360对应的订单号
					// try {
					//
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
					// PushRepaymentResp pushRepaymentResp = BeadWalletRong360Service // 发送通知
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
					// RepaymentFeedBackResp repaymentFeedBackResp = BeadWalletRong360Service // 发送通知
					// .repaymentFeedBack(repaymentFeedBackReq);
					// logger.info("结束调用融360账单状态反馈接口," + repaymentFeedBackResp);
					// }
					// } catch (Exception e) {
					// logger.error("调用融360接口异常", e);
					// }

					logger.info("广群推送处理结果：接收成功，当前工单号：" + orderId + "，还款日期：" + loanDate);
				} catch (Exception e) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					logger.info("广群推送处理结果：保存信息出错，当前工单号：" + orderId);
					throw e;
				}
				// 放款成功存储orderId到Redis中 dycode0001
				try {
					RedisUtils.rpush("order:loan", String.valueOf(orderId));
				} catch (Exception e) {
					logger.error("放款成功存储orderId到redis异常：", e);
				}
				appResponseResult.setCode("0000");
				appResponseResult.setMsg("成功");
			} else if ("1001".equals(code) || "1002".equals(code) || "1003".equals(code) || "1004".equals(code)) {
				bwCapitalPush.setPushStatus(3);// 放款失败
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setCode("0000");
				appResponseResult.setMsg("成功");
			} else if ("1005".equals(code)) {
				bwCapitalPush.setPushStatus(5);// 还款失败
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setCode("0000");
				appResponseResult.setMsg("成功");
			} else if ("1006".equals(code)) {
				bwCapitalPush.setPushStatus(4);// 还款成功
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setCode("0000");
				appResponseResult.setMsg("成功");
			} else if ("1007".equals(code)) {
				bwCapitalPush.setPushStatus(0);// 撤销
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setCode("0000");
				appResponseResult.setMsg("成功");
			} else if ("1008".equals(code)) {
				bwCapitalPush.setPushStatus(-1);// 撤销
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setCode("0000");
				appResponseResult.setMsg("成功");
			} else if ("1009".equals(code)) {
				bwCapitalPush.setPushStatus(1);// 推送接收成功
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				appResponseResult.setCode("0000");
				appResponseResult.setMsg("成功");
			} else {
				appResponseResult.setCode("10003");
				appResponseResult.setMsg("未知的请求");
			}

		} catch (Exception e) {
			logger.error("广群推送结果异常：", e);
		}

		return appResponseResult;
	}

	public static void main(String[] args) {
		// 计算还款时间
		String orderStr = "Q123141";

		System.out.println(orderStr.substring(1, orderStr.length()));

	}

}
