package com.waterelephant.drainage.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.drainage.entity.rongShu.BwRepaymentPlanVo;
import com.waterelephant.drainage.entity.rongShu.CallbackData;
import com.waterelephant.drainage.entity.rongShu.LoanCalculationResponse;
import com.waterelephant.drainage.entity.rongShu.OrderPushRequest;
import com.waterelephant.drainage.entity.rongShu.RongShuResponse;
import com.waterelephant.drainage.entity.rongShu.UserCheckResponse;
import com.waterelephant.drainage.service.DrainageService;
import com.waterelephant.drainage.service.RongShuService;
import com.waterelephant.drainage.util.rongshu.HttpRequest;
import com.waterelephant.drainage.util.rongshu.RongShuConstant;
import com.waterelephant.drainage.util.rongshu.RongShuUtil;
import com.waterelephant.drainage.util.rongshu.SignUtil;
import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.CommUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 榕树（code0087）
 * 
 * 
 * Module:
 * 
 * RongShuServiceImpl.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <榕树>
 */
@Service
public class RongShuServiceImpl implements RongShuService {
	private Logger logger = Logger.getLogger(RongShuServiceImpl.class);
	@Autowired
	private DrainageService drainageService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private BwRepaymentPlanService bwRepaymentService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;

	/**
	 * 榕树 - 5.1 存量用户检验接口
	 * 
	 * @author liuDaodao
	 * @param cid
	 * @param phone
	 * @param name
	 * @return UserCheckResponse
	 */
	@Override
	public RongShuResponse userCheck(String sessionId, String idCard, String phone, String name) {
		logger.info(sessionId + "开始RongShuServiceImpl.userCheck()方法{idCard=" + idCard + ",phone=" + phone + ",name="
				+ name + "}");
		RongShuResponse rongShuResponse = new RongShuResponse();
		try {
			UserCheckResponse userCheckResponse = new UserCheckResponse();
			// 第一步：是否是老用户
			boolean isOldUser = drainageService.oldUserFilter2(name, phone, idCard);
			userCheckResponse.setIsStock(isOldUser == true ? 1 : 0); // 存量用户（0：否，1：是）

			// 第二步：是否黑名单
			boolean isBlackUser = drainageService.isBlackUser2(name, phone, idCard);
			if (isBlackUser) {
				userCheckResponse.setIsCanLoan(0); // 是否可以借款（0：否，1：是）
				userCheckResponse.setIsBlackList(1); // 是否命中黑名单（0：否，1：是）
				userCheckResponse.setRejectReason(1); // 拒绝原因（1：黑名单，2：在贷）

				rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
				rongShuResponse.setMessage("命中黑名单规则");
				rongShuResponse.setResponse(userCheckResponse);
				logger.info(
						sessionId + "结束RongShuServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			// 第三步：是否进行中的订单
			boolean isProcessingOrder = drainageService.isPocessingOrder2(name, phone, idCard);
			if (isProcessingOrder) {
				userCheckResponse.setIsCanLoan(0); // 是否可以借款（0：否，1：是）
				userCheckResponse.setIsBlackList(0); // 是否命中黑名单（0：否，1：是）
				userCheckResponse.setRejectReason(2); // 拒绝原因（1：黑名单，2：在贷）

				rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
				rongShuResponse.setMessage("命中在贷规则");
				rongShuResponse.setResponse(userCheckResponse);
				logger.info(
						sessionId + "结束RongShuServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			// 第四步：是否有被拒记录(已经是存量用户)
			boolean isRejectRecord = drainageService.isRejectRecord(idCard, name);
			if (isRejectRecord) {
				userCheckResponse.setIsCanLoan(0); // 是否可以借款（0：否，1：是）
				userCheckResponse.setIsBlackList(0); // 是否命中黑名单（0：否，1：是）
				userCheckResponse.setRejectReason(2); // 拒绝原因（1：黑名单，2：在贷）

				rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
				rongShuResponse.setMessage("命中在贷规则");
				rongShuResponse.setResponse(userCheckResponse);
				logger.info(
						sessionId + "结束RongShuServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			// 第五步：查询借款信息（二三四规则已过，可以借款）
			userCheckResponse.setIsCanLoan(1); // 是否可以借款（0：否，1：是）
			userCheckResponse.setIsBlackList(0); // 是否命中黑名单（0：否，1：是）
			userCheckResponse.setRejectReason(0); // 拒绝原因（1：黑名单，2：在贷）

			// TODO code0087 最高，低额度；最大，小可借周期后期是否改为从产品表中获取
			userCheckResponse.setMaxLimit(5000);// 最高额度（单位：元）
			userCheckResponse.setMinLimit(500);// 最低额度（单位：元）
			userCheckResponse.setMaxPeriod(30);// 最大可借周期
			userCheckResponse.setMinPeriod(30);// 最小可借周期
			userCheckResponse.setPeriodUnit(1);// 周期单位（1：天，2：月）

			rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
			rongShuResponse.setMessage("成功");
			rongShuResponse.setResponse(userCheckResponse);

		} catch (Exception e) {
			logger.error("执行RongShuServiceImpl.userCheck()方法异常", e);
			rongShuResponse.setCode(RongShuResponse.CODE_FAIL);
			rongShuResponse.setMessage("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + "结束RongShuServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(rongShuResponse));
		return rongShuResponse;
	}

	/**
	 * 榕树 - 5.2 贷款试算器接口
	 * 
	 * @author liuDaodao
	 * @param loanAmountNum
	 * @param loanPeriodNum
	 * @param periodUnitNum
	 * @return RongShuResponse
	 */
	@Override
	public RongShuResponse loanCalculation(String sessionId, int loanAmount, int loanPeriod, int periodUnit) {
		logger.info(sessionId + "开始RongShuServiceImpl.loanCalculation()方法{loanAmount=" + loanAmount + ",loanPeriod="
				+ loanPeriod + ",periodUnit=" + periodUnit + "}");
		RongShuResponse rongShuResponse = new RongShuResponse();
		LoanCalculationResponse loanCalculationResponse = new LoanCalculationResponse();
		try {
			// 第一步：验证（最大最小金额验证）
			int minLoanAmount = 500;
			int maxLoanAmount = 5000;
			if (loanAmount < minLoanAmount) {
				loanCalculationResponse.setIsCanLoan(0);
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("本次借款金额" + loanAmount + "小于最小借款金额" + minLoanAmount);
				rongShuResponse.setResponse(loanCalculationResponse);
				logger.info(sessionId + "结束RongShuServiceImpl.loanCalculation()方法，返回结果："
						+ JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (loanAmount > maxLoanAmount) {
				loanCalculationResponse.setIsCanLoan(0);
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("本次借款金额" + loanAmount + "大于最大借款金额" + maxLoanAmount);
				rongShuResponse.setResponse(loanCalculationResponse);

				logger.info(sessionId + "结束RongShuServiceImpl.loanCalculation()方法，返回结果："
						+ JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			// 第二步：计算
			long productId = 0;
			if (loanPeriod == 30) {
				productId = 3;
			} else {
				productId = 5;
			}

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(productId);
			Double fee = bwProductDictionary.getpFastReviewCost() + bwProductDictionary.getpPlatformUseCost()
					+ bwProductDictionary.getpNumberManageCost() + bwProductDictionary.getpCapitalUseCost()
					+ bwProductDictionary.getpCollectionPassagewayCost();
			Double zjw = bwProductDictionary.getZjwCost();

			loanCalculationResponse.setIsCanLoan(1);

			loanCalculationResponse.setRefundAmount(loanAmount + loanAmount * zjw);

			loanCalculationResponse.setActualAmount(loanAmount - loanAmount * fee);

			// 第三步：返回
			rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
			rongShuResponse.setMessage("成功");
			rongShuResponse.setResponse(loanCalculationResponse);

		} catch (Exception e) {
			logger.error("执行RongShuServiceImpl.loanCalculation()方法异常", e);
			rongShuResponse.setCode(RongShuResponse.CODE_FAIL);
			rongShuResponse.setMessage("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + "结束RongShuServiceImpl.loanCalculation()方法，返回结果：" + JSON.toJSONString(rongShuResponse));
		return rongShuResponse;
	}

	/***
	 * 榕树 - 5.3. 进件推送接口
	 * 
	 * @param object
	 */
	@Override
	public RongShuResponse saveUserInfo(OrderPushRequest orderPushRequest) {
		RongShuResponse rongShuResponse = new RongShuResponse();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmsss");
		String sessionId = simpleDateFormat.format(new Date());
		logger.info(sessionId + "RongShuServiceImpl.saveUserInfo.start");
		String thirdOrderNo = orderPushRequest.getOrderId();
		String phone = orderPushRequest.getRegisterPhone();
		String idCard = orderPushRequest.getCid();
		logger.info("开始查询借款人信息系统中是否存在,idCard=" + idCard);
		Example example = new Example(BwBlacklist.class);
		example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card", idCard.toUpperCase());
		List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
		if (CommUtils.isNull(desList) == false) {
			logger.info("命中黑名单");
			rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
			rongShuResponse.setMessage("命中黑名单");
			return rongShuResponse;
		}
		BwBorrower bw = new BwBorrower();
		bw.setPhone(phone);
		bw = bwBorrowerService.findBwBorrowerByAttr(bw);
		logger.info("结束查询借款人信息,bwBorrower=" + JSONObject.toJSONString(bw));
		if (!CommUtils.isNull(bw)) {
			logger.info("开始查询进行中的订单,borrowerId=" + bw.getId());
			Long count = bwOrderService.findProcessOrder(String.valueOf(bw.getId()));
			logger.info("结束查询进行中的订单,count=" + count);
			if (count != null && count.intValue() > 0) {
				logger.info("该用户有进行中的订单,不做处理...");
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("该用户有进行中的订单");
				return rongShuResponse;
			}
		}
		logger.info("开始查询工单:thirdOrderNo=" + thirdOrderNo);
		BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
		if (!CommUtils.isNull(bwOrderRong)) {
			logger.info("榕树工单已经存在，不做重复处理...");
			rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
			rongShuResponse.setMessage("工单已经存在");
			return rongShuResponse;
		}
		rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
		rongShuResponse.setMessage("成功");
		return rongShuResponse;
	}

	/**
	 * 还款试算器
	 */
	@Override
	public RongShuResponse figureRepayMent(String sessionId, String orderId, String periods) {
		logger.info(sessionId + "开始RongShuServiceImpl.figureRepayMent()");
		RongShuResponse rongShuResponse = new RongShuResponse();
		BwOrder bwOrder = new BwOrder();
		try {
			// 查询订单号
			logger.info(sessionId + "查询工单号为orderId=" + orderId + "的工单");
			bwOrder = bwOrderService.selectByPrimaryKey(Long.parseLong(orderId));
			if (CommUtils.isNull(bwOrder)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数不合法,未找到该工单号");
				return rongShuResponse;
			}
			// 查询还款计划
			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrder.getId());
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
			if (CommUtils.isNull(bwRepaymentPlan)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("系统异常");
				logger.info("还款计划为空");
				return rongShuResponse;
			}
			// 账单映射
			String billStatus = RongShuUtil.convertBillStatus(bwOrder.getStatusId());
			logger.info("映射榕树的账单状态为:" + billStatus);
			if (StringUtils.isBlank(billStatus)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("系统异常");
				return rongShuResponse;
			}
			// 查询详情
			Map<String, String> data = new HashMap<String, String>();

			if ("3".equals(billStatus)) {
				BwOverdueRecord bov = new BwOverdueRecord();
				bov.setOrderId(bwOrder.getId());
				bov = findBwOverdueRecordByAttrProxy(bov);
				if (!CommUtils.isNull(bov) && bov.getOverdueAccrualMoney() != null) {
					data.put("overdueAmount", bov.getOverdueAccrualMoney() + "");
					data.put("refundAmount",
							(bwRepaymentPlan.getRealityRepayMoney() + bov.getOverdueAccrualMoney()) + "");
				}
			}
			data.put("loanAmount", bwOrder.getBorrowAmount() + "");
			data.put("refundAmount", bwRepaymentPlan.getRealityRepayMoney() + "");
			rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
			rongShuResponse.setMessage("成功");
			rongShuResponse.setResponse(data);

		} catch (Exception e) {
			logger.error("执行RongShuServiceImpl.figureRepayMent()方法异常", e);
			rongShuResponse.setCode(RongShuResponse.CODE_FAIL);
			rongShuResponse.setMessage("接口调用异常");
			rongShuResponse.setResponse("");
		}

		return rongShuResponse;
	}

	// 5.18. 变更还款计划接口
	public boolean changeRepayMentPlan(long orderId) {
		boolean flag = false;
		try {
			// 查询是否存在订单
			BwOrder bwOrder = new BwOrder();
			bwOrder.setId(orderId);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			if (CommUtils.isNull(bwOrder.getId())) {
				logger.info("未找到工单");
				return flag;
			}
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(orderId);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			String thridOrderId = "";
			if (!CommUtils.isNull(bwOrderRong)) {
				thridOrderId = bwOrderRong.getThirdOrderNo();
			}
			BwRepaymentPlan repayment = new BwRepaymentPlan();
			List<BwRepaymentPlan> bwRepayList = null;
			// TODO查找变更还款计划
			if (CommUtils.isNull(bwOrder.getCreditLimit()) && bwOrder.getCreditLimit() > 0) {
				// 查询还款计划
				repayment.setOrderId(bwOrder.getId());
				repayment = bwRepaymentService.findBwRepaymentPlanByAttr(repayment);
				// bwRepayList =
				// bwRepaymentService.findBwRepaymentPlanByRepay(repayment);
				BwRepaymentPlanVo bwRepaymentPlanVo = new BwRepaymentPlanVo();
				bwRepaymentPlanVo.setActualRefundAmount(String.valueOf(repayment.getRepayAccrualMoney()));

			}
			JSONObject json = new JSONObject();
			json.put("repaypaln", bwRepayList);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
			String timestamp = sdf.format(new Date()).toString();// 时间戳
			String loanAmount = String
					.valueOf(CommUtils.isNull(bwOrder.getBorrowAmount()) ? "0" : bwOrder.getBorrowAmount());
			String period = String.valueOf(CommUtils.isNull(bwOrder.getProductType()) ? "0" : bwOrder.getProductType());
			String refundAmount = String
					.valueOf(CommUtils.isNull(repayment.getRepayMoney()) ? "0" : repayment.getRepayMoney());
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appId", RongShuConstant.appId);// 合作机构id
			paramMap.put("orderId", thridOrderId);//
			paramMap.put("contractId", String.valueOf(bwOrder.getId()));//
			paramMap.put("period", period);
			paramMap.put("loanAmount", loanAmount);//
			paramMap.put("refundAmount", refundAmount);// ???
			paramMap.put("serviceFee", refundAmount);// ???
			paramMap.put("overdueFee", refundAmount);//
			paramMap.put("status", String.valueOf(bwOrder.getStatusId()));// 状态
			paramMap.put("remark", bwOrder.getMark());// 备注
			paramMap.put("timestamp", timestamp);
			String checkSign = SignUtil.rsaSign(paramMap, RongShuConstant.PRI_KEY, "UTF-8");// 加签
			checkSign = URLEncoder.encode(checkSign, "UTF-8");
			String url = RongShuResponse.DEFAULT_URL + "/hermes/api/refund/feedback.do";// 获取数据url
			StringBuffer buffer = new StringBuffer();
			buffer.append("appId=" + RongShuConstant.appId);
			buffer.append("&orderId=" + thridOrderId);
			buffer.append("&contractId=" + String.valueOf(bwOrder.getId()));
			buffer.append("&period=" + thridOrderId);
			buffer.append("&loanAmount=" + loanAmount);
			buffer.append("&refundAmount=" + refundAmount);
			buffer.append("&serviceFee=" + refundAmount);
			buffer.append("&overdueFee=" + refundAmount);
			buffer.append("&status=" + String.valueOf(bwOrder.getStatusId()));
			buffer.append("&remark=" + bwOrder.getMark());
			buffer.append("&timestamp=" + timestamp);
			buffer.append("&sign=" + checkSign);
			String callback = HttpRequest.doPost(url, buffer.toString());
			logger.info("调用榕树5.18. 变更还款计划接口返回数据" + callback);
			if (CommUtils.isNull(callback)) {
				logger.info("5.18. 变更还款计划接口调用失败");
				return flag;
			}
			CallbackData callbackData = JSONObject.parseObject(callback, CallbackData.class);
			if ("0".equals(callbackData.getCode())) {
				logger.info("~~~5.18. 变更还款计划接口接口成功~~~~~~");
				flag = true;
			}
		} catch (Exception e) {
			logger.info("");
		}

		return flag;
	}

	private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
		logger.info("开始查询还款计划,bwRepaymentPlan=" + JSONObject.toJSONString(bwRepaymentPlan));
		bwRepaymentPlan = bwRepaymentService.getLastRepaymentPlanByOrderId(bwRepaymentPlan.getOrderId());
		logger.info("结束查询还款计划,bwRepaymentPlan=" + JSONObject.toJSONString(bwRepaymentPlan));
		return bwRepaymentPlan;
	}

	private BwOverdueRecord findBwOverdueRecordByAttrProxy(BwOverdueRecord overdueRecord) {
		logger.info("开始查询逾期记录,overdueRecord=" + JSONObject.toJSONString(overdueRecord));
		overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(overdueRecord);
		logger.info("结束查询逾期记录,overdueRecord=" + JSONObject.toJSONString(overdueRecord));
		return overdueRecord;
	}
}
