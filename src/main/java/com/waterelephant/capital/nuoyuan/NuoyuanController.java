/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.capital.nuoyuan;

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
import com.beadwallet.service.capital.service.CapitalNuoyuanService;
import com.waterelephant.capital.koudai.KdaiSignUtils;
import com.waterelephant.entity.BwCapitalLoan;
import com.waterelephant.entity.BwCapitalPush;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.entity.BwPaymentDetail;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwCapitalLoanService;
import com.waterelephant.service.BwCapitalPushService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwPaymentDetailService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.CapitalBaseService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.Base64Utils;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * 
 * NuoyuanController.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2017年9月26日
 */
@Controller
@RequestMapping("/nuoyuan")
public class NuoyuanController {
	private Logger logger = Logger.getLogger(NuoyuanController.class);

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
	private CapitalBaseService capitalBaseService;
	@Resource
	private BwCapitalLoanService bwCapitalLoanService;
	@Resource
	private BwPaymentDetailService bwPaymentDetailService;

	@ResponseBody
	@RequestMapping("/loanAdvicePush.do")
	public JSONObject loanAdvice(HttpServletRequest request, HttpServletResponse response) {
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("诺远回调参数==》" + params);

			if (CommUtils.isNull(params)) {
				return returnStr(1002, "参数为空。");
			}
			Map<String, String> paramsMap = KdaiSignUtils.getNotityMap(params);

			if (CommUtils.isNull(paramsMap.get("params"))) {
				return returnStr(1002, "参数为空。");
			}

			byte[] paramByte = ThreeDes.decryptMode(SystemConstant.CAPITAL_NUOYUAN_IV,
					SystemConstant.CAPITAL_NUOYUAN_KEY.getBytes("UTF-8"), Base64Utils.decode(paramsMap.get("params")));

			if (CommUtils.isNull(paramByte)) {
				return returnStr(1002, "参数有误。");
			}
			String pushStr = new String(paramByte);
			pushStr = pushStr.replace("\\", "").replace("\"{", "{").replace("}\"", "}");

			logger.info("诺远回调参数==》" + pushStr);

			Map returnMap = JSON.parseObject(pushStr);
			Map bodyMap = (Map) returnMap.get("body");

			if (CommUtils.isNull(StringUtil.toString(bodyMap.get("bid_no")))) {
				return returnStr(1002, "bid_no参数为空。");
			}
			String code = StringUtil.toString(bodyMap.get("bid_status"));
			String msg = StringUtil.toString(bodyMap.get("bid_remark"));
			String orderNo = StringUtil.toString(bodyMap.get("bid_no"));
			String payTime = StringUtil.toString(bodyMap.get("pre_loan_day"));

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder == null) {
				return returnStr(1002, "工单信息不存在。");
			}
			Long orderId = bwOrder.getId();

			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId, 7);

			if (bwCapitalPush != null) {
				if ((2 == bwCapitalPush.getPushStatus() && "0".equals(code))) {
					logger.info("诺远推送处理结果：重复推送，当前工单号：" + orderId);
					return returnStr(0, "该bid_no已推送成功，请不要重复推送"); // 修改状态码
				}
			} else {
				bwCapitalPush = new BwCapitalPush();
				bwCapitalPush.setOrderId(orderId);
				bwCapitalPush.setCreateTime(now);
				bwCapitalPush.setCapitalId(6);
				bwCapitalPush.setPushCount(0);
			}

			if (CommUtils.isNull(bwCapitalPush.getId())) {
				bwCapitalPushService.save(bwCapitalPush);
			}
			bwCapitalPush.setCode(code);
			bwCapitalPush.setMsg(msg);
			bwCapitalPush.setUpdateTime(now);

			if ("4".equals(code)) {

				if (CommUtils.isNull(StringUtil.toString(payTime))) {
					return returnStr(1002, "该pre_loan_day已推送成功，请不要重复推送");
				}

				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date loanTime = null;
				String loanDate = null;
				try {
					loanDate = CommUtils.convertDateToString(format.parse(payTime), "yyyy-MM-dd HH:mm:ss");
					loanTime = format1.parse(loanDate);

				} catch (Exception e) {
					logger.error("还款时间：", e);
					e.printStackTrace();
					return returnStr(1001, "该pre_loan_day格式错误");
				}

				bwCapitalPush.setPushStatus(2);
				bwCapitalPush.setCode(code);
				bwCapitalPush.setMsg(msg);
				bwCapitalPush.setUpdateTime(now);

				try {
					// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给诺远
					List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							.listBwRepaymentPlanByOrderId(orderId);
					if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
						return returnStr(0, "已推送成功，请不要重复推送"); // 修改状态码
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
					BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 2);
					if (bwOrderPushInfo != null) {
						bwOrderPushInfo.setFullTime(loanTime);
						bwOrderPushInfo.setPushStatus(2);
						bwOrderPushInfo.setPushRemark("接收成功");
						bwOrderPushInfo.setUpdateTime(now);
						bwOrderPushInfo.setLoanTime(loanTime);
						bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
					}
					capitalBaseService.pushOrderStatus(bwOrder, repaymentTime);

					bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);

					logger.info("诺远推送处理结果：接收成功，当前工单号：" + orderId + "，还款日期：" + loanDate);

				} catch (Exception e) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					logger.info("诺远推送处理结果：保存信息出错，当前工单号：" + orderId);
					throw e;
				}
				return returnStr(0, "成功");
			} else if ("-4".equals(code)) {
				bwCapitalPush.setPushStatus(3);// 放款失败
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				return returnStr(0, "成功");
			} else if ("1".equals(code)) {
				bwCapitalPush.setPushStatus(1);// 审核成功
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				return returnStr(0, "成功");
			} else if ("-1".equals(code)) {
				bwCapitalPush.setPushStatus(3);// 审核失败
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				return returnStr(0, "成功");
			} else if ("-5".equals(code)) {
				BwCapitalLoan bwCapitalLoan = bwCapitalLoanService.queryBwCapitalLoan(orderId, 7);
				if (bwCapitalLoan != null) {
					bwCapitalLoanService.updateBwCapitalLoanByorderId(orderId, 7, -1);
				}
				return returnStr(0, "成功");
			} else if ("5".equals(code)) {
				// bwCapitalPush.setPushStatus(3);// 已还款
				// bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				BwCapitalLoan bwCapitalLoan = bwCapitalLoanService.queryBwCapitalLoan(orderId, 7);
				if (bwCapitalLoan != null) {
					bwCapitalLoan.setPushStatus(2);
					bwCapitalLoan.setMsg(msg);
					bwCapitalLoanService.updateBwCapitalLoan(bwCapitalLoan);
					// bwCapitalLoanService.updateBwCapitalLoanByorderId(orderId, 7, 2);
				}
				return returnStr(0, "成功");
			} else {
				return returnStr(-1, "未知的请求");
			}

		} catch (Exception e) {
			logger.error("诺远推送结果异常：", e);
		}

		return returnStr(-1, "异常请求");
	}

	@ResponseBody
	@RequestMapping("/repayMentPush.do")
	public JSONObject repayMentPush(HttpServletRequest request, HttpServletResponse response) {
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("诺远回调参数==》" + params);

			if (CommUtils.isNull(params)) {
				return returnStr(1002, "参数为空。");
			}
			Map<String, String> paramsMap = KdaiSignUtils.getNotityMap(params);

			if (CommUtils.isNull(paramsMap.get("params"))) {
				return returnStr(1002, "参数为空。");
			}

			byte[] paramByte = ThreeDes.decryptMode(SystemConstant.CAPITAL_NUOYUAN_IV,
					SystemConstant.CAPITAL_NUOYUAN_KEY.getBytes("UTF-8"), Base64Utils.decode(paramsMap.get("params")));

			if (CommUtils.isNull(paramByte)) {
				return returnStr(1002, "参数有误。");
			}
			String pushStr = new String(paramByte);
			pushStr = pushStr.replace("\\", "").replace("\"{", "{").replace("}\"", "}");

			logger.info("诺远回调参数==》" + pushStr);

			Map returnMap = JSON.parseObject(pushStr);
			Map bodyMap = (Map) returnMap.get("body");

			if (CommUtils.isNull(StringUtil.toString(bodyMap.get("bid_no")))) {
				return returnStr(1002, "bid_no参数为空。");
			}
			String code = StringUtil.toString(bodyMap.get("bill_status"));
			String orderNo = StringUtil.toString(bodyMap.get("bid_no"));
			String capitalNo = StringUtil.toString(bodyMap.get("ue_tid"));

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder == null) {
				return returnStr(0, "工单信息不存在。");
			}

			BwCapitalLoan bwCapitalLoan = bwCapitalLoanService.queryBwCapitalLoanCapitalNo(bwOrder.getId(), 7,
					capitalNo);

			if (bwCapitalLoan == null || 2 == bwCapitalLoan.getPushStatus()) {
				return returnStr(0, "工单信息不存在或重复推送。");
			}

			if ("0".equals(code)) {
				bwCapitalLoan.setPushStatus(2);
				bwCapitalLoan.setMsg("正常还款");
			} else if ("-1".equals(code)) {
				bwCapitalLoan.setPushStatus(-1);
				bwCapitalLoan.setMsg("逾期但已结清");
			} else if ("-2".equals(code)) {
				bwCapitalLoan.setPushStatus(-1);
				bwCapitalLoan.setMsg("未还款");
			} else if ("-3".equals(code)) {
				bwCapitalLoan.setPushStatus(-1);
				bwCapitalLoan.setMsg("逾期系统已垫付，未还款");
			} else {
				return returnStr(-1, "未知的请求");
			}
			bwCapitalLoan.setUpdateTime(new Date());
			bwCapitalLoanService.updateBwCapitalLoan(bwCapitalLoan);
			return returnStr(0, "处理成功");
		} catch (Exception e) {
			logger.error("诺远推送结果异常：", e);
		}

		return returnStr(-1, "异常请求");
	}

	@ResponseBody
	@RequestMapping("/queryOrderDetail.do")
	public JSONObject queryOrderDetail(HttpServletRequest request, HttpServletResponse response) {
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("诺远工单查询参数==》" + params);

			if (CommUtils.isNull(params)) {
				return returnStr(1002, "参数为空。");
			}
			Map<String, String> paramsMap = KdaiSignUtils.getNotityMap(params);

			if (CommUtils.isNull(paramsMap.get("params"))) {
				return returnStr(1002, "参数为空。");
			}

			byte[] paramByte = ThreeDes.decryptMode(SystemConstant.CAPITAL_NUOYUAN_IV,
					SystemConstant.CAPITAL_NUOYUAN_KEY.getBytes("UTF-8"), Base64Utils.decode(paramsMap.get("params")));

			if (CommUtils.isNull(paramByte)) {
				return returnStr(1002, "参数有误。");
			}
			String pushStr = new String(paramByte);
			pushStr = pushStr.replace("\\", "").replace("\"{", "{").replace("}\"", "}");

			logger.info("诺远查询工单参数==》" + pushStr);

			Map returnMap = JSON.parseObject(pushStr);
			Map bodyMap = (Map) returnMap.get("body");

			if (CommUtils.isNull(StringUtil.toString(bodyMap.get("bid_no")))) {
				return returnStr(1002, "bid_no参数为空。");
			}
			String orderNo = StringUtil.toString(bodyMap.get("bid_no"));

			if (CommUtils.isNull(orderNo)) {
				return returnStr(1002, "bid_no参数为空。");
			}
			// String orderNo = "B20171017050938657753";
			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder == null) {
				return returnStr(1002, "工单信息不存在。");
			}

			BwRepaymentPlan repaymentPlan = bwRepaymentPlanService
					.getLastRepaymentPlanAndXudaiByOrderId(bwOrder.getId());

			Map map = new HashMap<>();
			Map billMap = new HashMap<>();

			billMap.put("period", "1");
			billMap.put("repayment_day", repaymentPlan.getRepayTime().getTime());
			Integer repayment_corpus = (int) (repaymentPlan.getRepayMoney() * 100);
			billMap.put("repayment_corpus", repayment_corpus);
			Integer repayment_interest = (int) (repaymentPlan.getZjw() * 100);
			billMap.put("repayment_interest", repayment_interest);

			billMap.put("repayment_viorate", 0);
			billMap.put("repayment_fine", 0);
			billMap.put("real_repayment_corpus", 0);
			billMap.put("real_repayment_interest", 0);
			billMap.put("real_repayment_viorate", 0);
			billMap.put("real_repayment_fine", 0);

			if (9 == bwOrder.getStatusId()) {
				billMap.put("status", "-2");
			} else if (13 == bwOrder.getStatusId()) {
				billMap.put("status", "-3");
			} else if (6 == bwOrder.getStatusId() && 2 == repaymentPlan.getRepayStatus()) {
				if (1 == repaymentPlan.getRepayType() || 3 == repaymentPlan.getRepayType()) {
					billMap.put("status", "0");
					billMap.put("real_repayment_corpus", repayment_corpus);
					billMap.put("real_repayment_interest", repayment_interest);

				} else if (2 == repaymentPlan.getRepayType()) {
					billMap.put("status", "-1");

					billMap.put("real_repayment_corpus", repayment_corpus);
					billMap.put("real_repayment_interest", repayment_interest);

					BwPaymentDetail bwPaymentDetail = bwPaymentDetailService.query(repaymentPlan.getId());
					if (bwPaymentDetail != null) {
						billMap.put("repayment_fine", bwPaymentDetail.getOverdueAmount());
						billMap.put("real_repayment_fine", bwPaymentDetail.getRealOverdueAmount());
					}
				}
			} else {
				billMap.put("status", "-3");
			}

			map.put("code", 0);
			map.put("msg", "处理成功");
			map.put("bills", billMap);

			return JSONObject.parseObject(JSON.toJSONString(map));

		} catch (Exception e) {
			logger.error("诺远推送结果异常：", e);
		}
		return returnStr(-1, "异常请求");
	}

	@ResponseBody
	@RequestMapping("/checkContract.do")
	public JSONObject checkContract(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> map = new HashMap<>();
			String orderNo = request.getParameter("orderNo");
			String orderId = request.getParameter("orderId");
			/**
			 * 1.对订单编号和，订单Id进行判断 2.查看订单的资方的ID是否为诺远(这里不需要对推送方做判断) 3.装入map,调用SDK方法
			 */
			if (CommUtils.isNull(orderNo)) {
				return returnStr(1002, "订单编号为空");
			}
			if (CommUtils.isNull(orderId)) {
				return returnStr(1002, "订单ID为空");
			}
			BwOrder bwOrder = bwOrderService.findBwOrderById(orderId);
			if (CommUtils.isNull(bwOrder)) {
				return returnStr(1004, "未获取到相应的订单");
			}
			// //需不需要对订单状态进行判断？
			// BwOrderPushInfo orderPushInfo = bwOrderPushInfoService.getOrderPushInfoByOrderId(Long.valueOf(orderId));
			// if(CommUtils.isNull(orderPushInfo)){
			// return returnStr(1004,"未获取到相应订单的推送信息");
			// }
			// Integer captail = orderPushInfo.getFinancingChannel();
			// if(7==captail){
			map.put("orderNo", orderNo);
			map.put("orderId", orderId);
			String params = JSON.toJSONString(map);
			String json = CapitalNuoyuanService.queryContractUrl(params);
			Map<String, Object> dataMap = JSONObject.parseObject(json);
			String code = CommUtils.toString(dataMap.get("code"));
			if ("0".equals(code)) {
				return JSONObject.parseObject(json);
			} else {
				// 这里强转可能会出错
				return returnStr(-1, CommUtils.toString(dataMap.get("msg")));
				// }
				// }else{
				// return returnStr(1003,"该订单不为诺远推送");
			}
		} catch (Exception e) {
			logger.error("获取诺远合同异常：", e);
			return returnStr(-1, "系统异常");
		}
	}

	public static JSONObject returnStr(int code, String msg) {
		Map map = new HashMap();
		map.put("code", code);
		map.put("msg", msg);

		return JSONObject.parseObject(JSON.toJSONString(map));
	}

}
