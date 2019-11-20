/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.capital.bairongZhitou;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.beadwallet.service.capital.service.CapitalBairongZhitouService;
import com.waterelephant.entity.BwCapitalLoan;
import com.waterelephant.entity.BwCapitalOrder;
import com.waterelephant.entity.BwCapitalPush;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwCapitalLoanService;
import com.waterelephant.service.BwCapitalOrderService;
import com.waterelephant.service.BwCapitalPushService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.CapitalBaseService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;

/**
 * 
 * BairongZhitouController.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2017年9月8日
 */
@Controller
@RequestMapping("/bairongZhitou")
public class BairongZhitouController {
	private Logger logger = Logger.getLogger(BairongZhitouController.class);

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
	private BwCapitalOrderService bwCapitalOrderService;

	@Resource
	private IBwRepaymentService iBwRepaymentService;

	@Resource
	private CapitalBaseService capitalBaseService;
	@Resource
	private BwCapitalLoanService bwCapitalLoanService;

	@ResponseBody
	@RequestMapping("/pushOrderNotity.do")
	public String pushOrderNotity(HttpServletRequest request, HttpServletResponse response) {
		// %7B%22data%22%3A%7B%22requestId%22%3A%221706280180115976%22%2C%22data%22%3A%7B%22repaymentStatus%22%3A%224%22%2C%22repaymentMsg%22%3A%22%E8%B6%85%E5%87%BA%E7%BA%A6%E5%AE%9A%E6%94%BE%E6%AC%BE%E7%AD%89%E5%BE%85%E6%97%B6%E9%97%B4%EF%BC%8C%E8%87%AA%E5%8A%A8%E6%B5%81%E6%A0%87%22%7D%7D%7D
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("百融回调参数==》" + params);
			params = URLDecoder.decode(params, "utf-8");
			logger.info("百融回调参数==》" + params);
			if (CommUtils.isNull(params)) {
				return returnStr("fail");
			}
			Map map = JSON.parseObject(params);
			Map mapData = JSON.parseObject(CommUtils.toString(map.get("data")));
			String capitalNo = CommUtils.toString(mapData.get("requestId"));
			Map detailMap = (Map) mapData.get("data");
			String repaymentStatus = CommUtils.toString(detailMap.get("repaymentStatus"));
			String repaymentMsg = CommUtils.toString(detailMap.get("repaymentMsg"));
			if (CommUtils.isNull(capitalNo)) {
				return returnStr("fail");
			}
			BwCapitalOrder capitalOrder = bwCapitalOrderService.queryBwCapitalOrder(capitalNo, 5);
			if (capitalOrder == null) {
				return returnStr("fail");
			}

			BwOrder bwOrder = bwOrderService.findBwOrderById(CommUtils.toString(capitalOrder.getOrderId()));
			if (bwOrder == null) {
				return returnStr("fail");
			}
			Long orderId = bwOrder.getId();

			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId, 5);
			if (bwCapitalPush != null) {
				if ((2 == bwCapitalPush.getPushStatus() && "3".equals(repaymentStatus))) {
					return returnStr("fail");
				}
			} else {
				bwCapitalPush = new BwCapitalPush();
				bwCapitalPush.setOrderId(orderId);
				bwCapitalPush.setCreateTime(now);
				bwCapitalPush.setCapitalId(5);
				bwCapitalPush.setPushCount(0);
			}

			if (CommUtils.isNull(bwCapitalPush.getId())) {
				bwCapitalPushService.save(bwCapitalPush);
			}
			bwCapitalPush.setCode(repaymentStatus);
			bwCapitalPush.setMsg(repaymentMsg);
			bwCapitalPush.setUpdateTime(now);
			if ("3".equals(repaymentStatus)) {
				String loanDate = CommUtils.convertDateToString(bwCapitalPush.getUpdateTime(), "yyyy-MM-dd");

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date loanTime = null;
				try {
					loanTime = format.parse(loanDate);

				} catch (Exception e) {
					logger.error("还款时间：", e);
					e.printStackTrace();
					return returnStr("fail");
				}

				bwCapitalPush.setPushStatus(2);
				try {
					// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给百融
					// List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
					// .listBwRepaymentPlanByOrderId(orderId);
					// if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
					// return returnStr("fail");
					// }
					// // 还款时间
					// Date repaymentTime = null;

					// if (2 == bwOrder.getProductType()) {
					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);
					//
					// } else {
					//
					// BwProductDictionary bwProductDictionary = bwProductDictionaryService
					// .findBwProductDictionaryById(bwOrder.getProductId());
					// String termType = null;
					// String term = null;
					// if (bwProductDictionary != null) {
					// termType = bwProductDictionary.getpTermType();
					// term = bwProductDictionary.getpTerm();
					// }
					//
					// if ("1".equals(termType)) {
					// repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
					// } else if ("2".equals(termType)) {
					// repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
					// } else {
					// repaymentTime = MyDateUtils.addDays(loanTime, 30);
					// }
					//
					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
					// bwProductDictionary);
					// }

					bwCapitalPush.setPushStatus(2);// 放款成功
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

					// 更新push 表
					// BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 4);
					// if (bwOrderPushInfo != null) {
					// bwOrderPushInfo.setFullTime(loanTime);
					// bwOrderPushInfo.setPushStatus(4);
					// bwOrderPushInfo.setPushRemark("接收成功");
					// bwOrderPushInfo.setUpdateTime(now);
					// bwOrderPushInfo.setLoanTime(loanTime);
					// bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
					// }
					// bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);

					// capitalBaseService.pushOrderStatus(bwOrder, repaymentTime);

					// loanData(bwOrder, RedisUtils.hget("capital:token", "token5"), capitalOrder.getCapitalNo());
					loanData1(bwOrder, RedisUtils.hget("capital:token", "token5"), capitalOrder.getCapitalNo(),
							loanDate);

				} catch (Exception e) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					logger.info("百融推送处理结果：保存信息出错，当前工单号：" + orderId);
					throw e;
				}

				return returnStr("success");
			} else if ("4".equals(repaymentStatus)) {
				bwCapitalPush.setPushStatus(3);// 放款失败
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				return returnStr("success");
			} else {
				return returnStr("fail");
			}

		} catch (Exception e) {
			logger.error("百融推送结果异常：", e);
		}
		return returnStr("fail");
	}

	@ResponseBody
	@RequestMapping("/pushOrderQuerys.do")
	public void pushOrderQuerys(HttpServletRequest request, HttpServletResponse response) {
		logger.info("百融获取订单状态");
		String orderIds = request.getParameter("orderIds");
		if (CommUtils.isNull(orderIds)) {
			try {
				orderIds = IOUtils.toString(request.getInputStream(), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("百融获取订单状态所有：" + orderIds);
		if (CommUtils.isNull(orderIds)) {
			return;
		}
		String[] orders = orderIds.split(",");
		if (CommUtils.isNull(orders)) {
			return;
		}
		for (String orderId : orders) {
			try {
				logger.info("百融获取订单状态开始：" + orderId);
				BwOrder bwOrder = new BwOrder();
				bwOrder.setId(Long.parseLong(orderId.trim()));
				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
				if (bwOrder == null) {
					logger.info("百融获取订单状态工单不存在：" + orderId);
					continue;
				}
				BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(bwOrder.getId(), 5);
				BwCapitalOrder bwCapitalOrder = bwCapitalOrderService.queryBwCapitalOrder(bwOrder.getId(), 5);

				if (bwCapitalPush == null || bwCapitalOrder == null) {
					logger.info("百融获取订单状态推送记录不存在：" + orderId);
					continue;
				}

				if (2 == bwCapitalPush.getPushStatus()) {
					logger.info("百融获取订单状态推送记录已完成：" + orderId);
					continue;
				}

				String token = RedisUtils.hget("capital:token", "token5");
				if (CommUtils.isNull(token)) {
					logger.info("百融获取订单状态token不存在：" + token);
					return;
				}
				Map map = new HashMap<>();
				map.put("tokenid", token);
				map.put("requestId", bwCapitalOrder.getCapitalNo());

				String returnStr = CapitalBairongZhitouService.pushOrderQuery(JSON.toJSONString(map));

				logger.info("百融推送状态结果：" + returnStr);
				JSONObject jsb = JSONObject.parseObject(returnStr);
				String responseCode = jsb.getString("responseCode");
				String responseMsg = jsb.getString("responseMsg");

				Date now = new Date();

				bwCapitalPush.setPushStatus(2);

				bwCapitalPush.setUpdateTime(now);

				String loanDate = CommUtils.convertDateToString(bwCapitalPush.getUpdateTime(), "yyyy-MM-dd");

				if ("900".equals(responseCode)) {
					JSONObject jsb_date = jsb.getJSONObject("data");
					String repaymentStatus = jsb_date.getString("repaymentStatus");
					String repaymentMsg = jsb_date.getString("repaymentMsg");

					bwCapitalPush.setCode(repaymentStatus);
					bwCapitalPush.setMsg(repaymentMsg);

					if ("3".equals(repaymentStatus)) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date loanTime = null;
						try {
							loanDate = DateUtil.getDateString(CommUtils.convertStringToDate(loanDate, "yyyyMMdd"),
									"yyyy-MM-dd");
							loanTime = format.parse(loanDate);

						} catch (Exception e) {
							logger.error("还款时间：", e);
							e.printStackTrace();
							continue;
						}

						try {
							// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给百融
							// List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							// .listBwRepaymentPlanByOrderId(bwOrder.getId());
							// if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
							// logger.info("百融推送处理结果：重复推送，当前工单号：" + orderId);
							// continue;
							// }
							// // 还款时间
							// Date repaymentTime = null;
							//
							// if (2 == bwOrder.getProductType()) {
							// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);
							//
							// } else {
							//
							// BwProductDictionary bwProductDictionary = bwProductDictionaryService
							// .findBwProductDictionaryById(bwOrder.getProductId());
							// String termType = null;
							// String term = null;
							// if (bwProductDictionary != null) {
							// termType = bwProductDictionary.getpTermType();
							// term = bwProductDictionary.getpTerm();
							// }
							//
							// if ("1".equals(termType)) {
							// repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
							// } else if ("2".equals(termType)) {
							// repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
							// } else {
							// repaymentTime = MyDateUtils.addDays(loanTime, 30);
							// }
							//
							// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
							// bwProductDictionary);
							// }
							// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repayTime);
							// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, repayDate);

							bwCapitalPush.setPushStatus(2);// 放款成功
							bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

							// 更新push 表
							// BwOrderPushInfo bwOrderPushInfo =
							// bwOrderPushInfoService.getOrderPushInfo(bwOrder.getId(),
							// 4);
							// if (bwOrderPushInfo != null) {
							// bwOrderPushInfo.setFullTime(loanTime);
							// bwOrderPushInfo.setPushStatus(2);
							// bwOrderPushInfo.setPushRemark("接收成功");
							// bwOrderPushInfo.setUpdateTime(now);
							// bwOrderPushInfo.setLoanTime(loanTime);
							// bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
							// }
							// bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(bwOrder.getId(), loanTime);
							//
							// capitalBaseService.pushOrderStatus(bwOrder, repaymentTime);
							loanData1(bwOrder, RedisUtils.hget("capital:token", "token5"),
									bwCapitalOrder.getCapitalNo(), loanDate);
						} catch (Exception e) {
							bwCapitalPush.setPushStatus(3);// 接收失败
							bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
							logger.info("百融推送处理结果：保存信息出错，当前工单号：" + orderId);
							throw e;
						}

					} else if ("4".equals(repaymentStatus)) {
						bwCapitalPush.setPushStatus(3);
						bwCapitalPush.setCode(repaymentStatus);
						bwCapitalPush.setMsg(repaymentMsg);
						bwCapitalPush.setUpdateTime(now);
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					}
				} else {
					bwCapitalPush.setPushStatus(3);
					bwCapitalPush.setCode(responseCode);
					bwCapitalPush.setMsg(responseMsg);
					bwCapitalPush.setUpdateTime(now);
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				}

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}
	}

	public void loanData(BwOrder bwOrder, String token, String requestId) {
		try {
			List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
					.listBwRepaymentPlanByOrderId(bwOrder.getId());

			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setRepayTime(new Date());

			BwCapitalLoan bwCapitalLoan = bwCapitalLoanService.queryBwCapitalLoan(bwOrder.getId(), 5);

			if (bwCapitalLoan == null) {
				bwCapitalLoan = new BwCapitalLoan();

				bwCapitalLoan.setCapitalId(5);
				bwCapitalLoan.setCapitalNo(requestId);
				bwCapitalLoan.setCreateTime(new Date());
				bwCapitalLoan.setOrderId(bwOrder.getId());
				bwCapitalLoan.setPushCount(0);
				bwCapitalLoan.setPushStatus(0);

				bwCapitalLoanService.save(bwCapitalLoan);

			} else {
				if (1 == bwCapitalLoan.getPushStatus() || 2 == bwCapitalLoan.getPushStatus()) {
					logger.info("已经推送，请勿重推：" + bwOrder.getId());
					return;
				}
				if (CommUtils.isNull(bwCapitalLoan.getPushCount())) {
					bwCapitalLoan.setPushCount(0);
				}
				bwCapitalLoan.setPushCount(bwCapitalLoan.getPushCount() + 1);
			}

			Map map = new HashMap<>();
			// bwOrder
			map.put("borrow_amount", bwOrder.getBorrowAmount());
			map.put("tokenid", token);
			map.put("requestId", requestId);
			map.put("planList", bwRepaymentPlanList);

			// logger.info("百融推送还款计划请求内容：" + JSON.toJSONString(map));

			String returnStr = CapitalBairongZhitouService.loanData(JSON.toJSONString(map));
			logger.info("百融推送还款计划结果：" + returnStr);

			JSONObject jsb = JSON.parseObject(returnStr);
			String responseCode = jsb.getString("responseCode");
			String responseMsg = jsb.getString("responseMsg");

			bwCapitalLoan.setCode(responseCode);
			bwCapitalLoan.setMsg(responseMsg);

			if ("900".equals(responseCode)) {
				bwCapitalLoan.setPushStatus(2);
			}
			bwCapitalLoan.setUpdateTime(new Date());

			bwCapitalLoanService.updateBwCapitalLoan(bwCapitalLoan);
		} catch (Exception e) {
			logger.error("百融推送还款计划异常", e);
		}
	}

	public void loanData1(BwOrder bwOrder, String token, String requestId, String repayDate) {
		try {
			List<BwRepaymentPlan> bwRepaymentPlanList = new ArrayList<>();
			double preMoney = Math.floor(DoubleUtil.div(bwOrder.getBorrowAmount(), Double.valueOf(3)));
			for (int i = 1; i <= 3; i++) {
				BwRepaymentPlan plan = new BwRepaymentPlan();
				plan.setOrderId(bwOrder.getId());
				if (i < 3) {
					Double realityRepayMoney = Math.floor(DoubleUtil.add(preMoney, DoubleUtil.mul(3, 0.07)));
					plan.setRepayMoney(realityRepayMoney);
					plan.setRealityRepayMoney(realityRepayMoney);
					plan.setRepayCorpusMoney(preMoney);
				} else {
					Double lastMoney = DoubleUtil.sub(bwOrder.getBorrowAmount(), DoubleUtil.mul(preMoney, 3 - 1));
					Double realityRepayMoney = Math
							.floor(DoubleUtil.add(lastMoney, DoubleUtil.mul(bwOrder.getBorrowAmount(), 0.07)));
					plan.setRepayMoney(realityRepayMoney);
					plan.setRealityRepayMoney(realityRepayMoney);
					plan.setRepayCorpusMoney(lastMoney);
				}
				plan.setRepayTime(MyDateUtils.addDays(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd), 30 * i));
				plan.setRepayStatus(1);
				plan.setRepayType(1);
				plan.setRepayIsCorpus(2);

				plan.setRepayAccrualMoney(Math.floor(DoubleUtil.mul(bwOrder.getBorrowAmount(), 0.07)));
				plan.setCreateTime(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd));
				plan.setNumber(i);
				plan.setAlreadyRepayMoney(0.00);
				bwRepaymentPlanList.add(plan);
			}

			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setRepayTime(new Date());

			BwCapitalLoan bwCapitalLoan = bwCapitalLoanService.queryBwCapitalLoan(bwOrder.getId(), 5);

			if (bwCapitalLoan == null) {
				bwCapitalLoan = new BwCapitalLoan();

				bwCapitalLoan.setCapitalId(5);
				bwCapitalLoan.setCapitalNo(requestId);
				bwCapitalLoan.setCreateTime(new Date());
				bwCapitalLoan.setOrderId(bwOrder.getId());
				bwCapitalLoan.setPushCount(0);
				bwCapitalLoan.setPushStatus(0);

				bwCapitalLoanService.save(bwCapitalLoan);

			} else {
				if (1 == bwCapitalLoan.getPushStatus() || 2 == bwCapitalLoan.getPushStatus()) {
					logger.info("已经推送，请勿重推：" + bwOrder.getId());
					return;
				}
				if (CommUtils.isNull(bwCapitalLoan.getPushCount())) {
					bwCapitalLoan.setPushCount(0);
				}
				bwCapitalLoan.setPushCount(bwCapitalLoan.getPushCount() + 1);
			}

			Map map = new HashMap<>();
			// bwOrder
			map.put("borrow_amount", bwOrder.getBorrowAmount());
			map.put("tokenid", token);
			map.put("requestId", requestId);
			map.put("planList", bwRepaymentPlanList);

			// logger.info("百融推送还款计划请求内容：" + JSON.toJSONString(map));

			String returnStr = CapitalBairongZhitouService.loanData(JSON.toJSONString(map));
			logger.info("百融推送还款计划结果：" + returnStr);

			JSONObject jsb = JSON.parseObject(returnStr);
			String responseCode = jsb.getString("responseCode");
			String responseMsg = jsb.getString("responseMsg");

			bwCapitalLoan.setCode(responseCode);
			bwCapitalLoan.setMsg(responseMsg);

			if ("900".equals(responseCode)) {
				bwCapitalLoan.setPushStatus(2);
			}
			bwCapitalLoan.setUpdateTime(new Date());

			bwCapitalLoanService.updateBwCapitalLoan(bwCapitalLoan);
		} catch (Exception e) {
			logger.error("百融推送还款计划异常", e);
		}
	}

	public static String returnStr(String result) {
		return "{\"result\":\"" + result + "\"}";
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode(
				"{\"data\":{\"requestId\":\"1711030170146176\",\"data\":{\"repaymentStatus\":\"3\"}}}", "utf-8"));
	}

}
