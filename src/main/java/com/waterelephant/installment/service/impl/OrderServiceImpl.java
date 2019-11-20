/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.constants.OrderStatusConstant;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.installment.constants.InstallmentConstant;
import com.waterelephant.installment.service.AuthInfoService;
import com.waterelephant.installment.service.InstallmentService;
import com.waterelephant.installment.service.OrderService;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderRepaymentBatchDetailService;
import com.waterelephant.service.BwOrderStatusRecordService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.ExtraConfigService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.StringUtil;

import tk.mybatis.mapper.entity.Example;

/**
 * 查询工单信息
 * 
 * Module:
 * 
 * OrderServiceImpl.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class OrderServiceImpl extends BaseService<BwOrder, Long> implements OrderService {
	private Logger logger = Logger.getLogger(OrderServiceImpl.class);
	@Resource
	private BwOrderAuthService bwOrderAuthService;
	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private ProductService productService;
	@Resource
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Resource
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
	@Resource
	private InstallmentService installmentService;
	@Resource
	private AuthInfoService authInfoService;
	@Resource
	private ExtraConfigService extraConfigService;
	@Resource
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	BwOrderStatusRecordService bwOrderStatusRecordService;

	/**
	 * 根据借款人Id和产品类型查询工单
	 * 
	 * @see com.waterelephant.installment.service.OrderService#getOrderByProductType(java.lang.String, java.lang.String)
	 */
	@Override
	public BwOrder getOrderByProductType(Long borrowerId, Integer productType) {
		String sql = "select * from bw_order where borrower_id=" + borrowerId + " and product_type=" + productType
				+ " order by create_time desc limit 1";
		BwOrder bwOrder = sqlMapper.selectOne(sql, BwOrder.class);
		return CommUtils.isNull(bwOrder) ? new BwOrder() : bwOrder;
	}

	/**
	 * 将数据库中的工单状态转换为前端需要的工单状态
	 * 
	 * @param statusId
	 * @return
	 */
	private int getStatus(Integer oriStatus) {
		int status = 1;

		// 1、无工单
		if (0 == oriStatus) {
			status = 1;
		}
		// 2、草稿
		if (1 == oriStatus) {
			status = 2;
		}
		// 3、待签约
		if (4 == oriStatus) {
			status = 3;
		}
		// 4、还款
		if (9 == oriStatus) {
			status = 4;
		}
		// 5、放款中
		if (5 == oriStatus || 11 == oriStatus || 12 == oriStatus || 14 == oriStatus) {
			status = 5;
		}
		// 6、拒绝
		if (7 == oriStatus) {
			status = 6;
		}
		// 7、撤回
		if (8 == oriStatus) {
			status = 7;
		}
		// 8、逾期
		if (13 == oriStatus) {
			status = 8;
		}
		// 9、审核中
		if (2 == oriStatus || 3 == oriStatus) {
			status = 9;
		}
		return status;
	}

	/**
	 * 构建返回工单
	 * 
	 * @param order
	 * @param otherOrder
	 * @return
	 */
	private Map<String, Object> getOrderMap(Long borrowerId, BwOrder order, BwOrder otherOrder, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 原始工单状态
		Integer oriStatus = StringUtil.toInteger(order.getStatusId());
		Integer otherStatus = StringUtil.toInteger(otherOrder.getStatusId());
		Map<String, Object> blackMap = new HashMap<String, Object>();
		if (null != order.getBorrowerId()) {
			// 判断是否在黑名单
			blackMap = authInfoService.getBorrowerType(order.getBorrowerId(), order.getProductType());
		}
		Double repayMoney = null;
		Date repayTime = null;
		// 查询单期待还款金额
		if (null != order.getBorrowerId() && null != order.getProductType()
				&& order.getProductType() == InstallmentConstant.PRODUCT_TYPE.SINGLE) {
			Map<String, Object> singleMap = getMoneyByOrderId(order.getId());
			repayMoney = Double.parseDouble(CommUtils.isNull(singleMap.get("reality_repay_money")) ? "0.00"
					: singleMap.get("reality_repay_money").toString());
			if (!CommUtils.isNull(singleMap.get("repay_time"))) {
				repayTime = DateUtil.stringToDate(singleMap.get("repay_time").toString(), DateUtil.yyyy_MM_dd);
			}

		}
		// 查询 多期待还款金额
		if (null != order.getBorrowerId() && null != order.getProductType()
				&& order.getProductType() == InstallmentConstant.PRODUCT_TYPE.MULTI) {
			Map<String, Object> orderMap = getMultiOrderRepay(order.getId());
			repayMoney = Double.parseDouble(
					CommUtils.isNull(orderMap.get("totalAmount")) ? "0.00" : orderMap.get("totalAmount").toString());

			// 查询还款计划列表
			List<BwRepaymentPlan> list = getRepayTimeByOrderId(order.getId());
			if (!CommUtils.isNull(list)) {
				for (BwRepaymentPlan bwRepaymentPlan : list) {
					if (bwRepaymentPlan.getRepayStatus() == 1) {
						repayTime = bwRepaymentPlan.getRepayTime();
						break;
					}
					repayTime = bwRepaymentPlan.getRepayTime();
				}
			}
		}
		Integer orderStatus = getStatus(oriStatus);
		map.put("sort", StringUtil.toInteger(blackMap.get("sort")));
		map.put("isExpire", StringUtil.isEmpty(blackMap.get("isExpire")) ? false : blackMap.get("isExpire"));
		map.put("whiteTime", StringUtil.toString(blackMap.get("whiteTime")));
		map.put("orderId", StringUtil.toInteger(order.getId()));
		map.put("orderStatus", orderStatus);// 工单状态（1、无工单，2、草稿，3、待签约，4、还款，5、进度查询，6、拒绝，7、撤回,8逾期 9、审核中 ）
		map.put("isOverdue", 13 == oriStatus ? 1 : 0);// 是否逾期（0、否，1、是）
		map.put("borrowLimit", DoubleUtil.toTwoDecimal(order.getBorrowAmount()));// 借款额度
		map.put("operationMsg", "");// 不能操作提示信息
		map.put("canOperation", 0);// 是否能操作（0、否，1、是）
		if ((null == order.getId() || 1 == oriStatus || 8 == oriStatus || 6 == oriStatus
				|| ("true".equals(blackMap.get("isExpire").toString()) && 7 == oriStatus))
				& (2 == otherStatus || 3 == otherStatus)) { // 工单正常 另一个工单在审核中
			map.put("operationMsg", InstallmentConstant.SINGLE_OPERATION_MSG.IN_AUDIT);
		} else if (6 == orderStatus && blackMap.get("sort").toString().equals("1")) { // 黑名单状态
			map.put("operationMsg", InstallmentConstant.SINGLE_OPERATION_MSG.PERM_REJECT);
		} else if (6 == orderStatus && !blackMap.get("sort").toString().equals("1")
				&& ("false".equals(blackMap.get("isExpire").toString()))) { // 灰名单未过时间
			map.put("operationMsg", InstallmentConstant.SINGLE_OPERATION_MSG.TEMP_REJECT + ",请于"
					+ StringUtil.toString(blackMap.get("whiteTime") + "后重新申请"));
		} else if (6 == orderStatus && !blackMap.get("sort").toString().equals("1")
				&& ("true".equals(blackMap.get("isExpire").toString()))) { // 灰名单过了时间
			map.put("canOperation", 1);// 是否能操作（0、否，1、是）
		} else {
			map.put("canOperation", 1);// 是否能操作（0、否，1、是）
		}

		// 判断是否有正在处理中的工单
		boolean hasProcessingOrder = false;
		Integer canOperation = (Integer) map.get("canOperation");
		if (canOperation != null && canOperation == 1) {
			if (otherStatus != 0 && otherStatus != 1 && otherStatus != 6 && otherStatus != 7) {
				hasProcessingOrder = true;
			}
			// TODO 分期入口暂时被封，分期撤回，单期可以点击
			if (type == InstallmentConstant.PRODUCT_TYPE.SINGLE && otherStatus == 8) {
				hasProcessingOrder = false;
			}
		}
		if (hasProcessingOrder) {
			map.put("canOperation", 0);// 是否能操作（0、否，1、是）
			map.put("operationMsg", InstallmentConstant.SINGLE_OPERATION_MSG.IN_PROCESSING);
		}

		// 1、无工单
		if (1 == orderStatus && type == InstallmentConstant.PRODUCT_TYPE.SINGLE) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.SINGLE_AUTO_MONEY);// 提示信息
		}
		if (1 == orderStatus && type == InstallmentConstant.PRODUCT_TYPE.MULTI) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.MULTI_AUTO_MONEY);// 提示信息
		}
		// 2、草稿
		if (2 == orderStatus && order.getProductType() == InstallmentConstant.PRODUCT_TYPE.SINGLE) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.SINGLE_AUTO_MONEY);// 提示信息
		}
		if (2 == orderStatus && order.getProductType() == InstallmentConstant.PRODUCT_TYPE.MULTI) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.MULTI_AUTO_MONEY);// 提示信息
		}
		// 3、待签约
		if (3 == orderStatus) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.GETMONEY + order.getBorrowAmount());// 提示信息
		}
		// 放款中
		if (5 == orderStatus) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.CREDIT
					+ NumberUtil.parseInt(null == order.getBorrowAmount() ? 0 : order.getBorrowAmount(), null) + "元");// 提示信息
		}
		// 4、还款
		if (4 == orderStatus && order.getProductType() == InstallmentConstant.PRODUCT_TYPE.SINGLE) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.SINGLE_LEFT
					+ NumberUtil.parseInt(null == order.getBorrowAmount() ? 0 : order.getBorrowAmount(), null) + "元");// 提示信息
		}
		if (4 == orderStatus && order.getProductType() == InstallmentConstant.PRODUCT_TYPE.MULTI) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.MULTI_LEFT + DoubleUtil.toTwoDecimal(repayMoney)
					+ " </br> " + InstallmentConstant.SINGLE_TEXT_MSG.MULTI_LEFT2
					+ (CommUtils.isNull(repayTime) ? "" : DateFormatUtils.format(repayTime, DateUtil.yyyy_MM_dd)));// 提示信息
		}
		// 6、拒绝
		if (6 == orderStatus && blackMap.get("sort").toString().equals("1")) { // 永久拒绝
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.PERM_REJECT);// 提示信息
		}
		if (6 == orderStatus && !blackMap.get("sort").toString().equals("1")
				&& ("false".equals(blackMap.get("isExpire").toString()))) { // 临时拒绝 还没过拒绝时间
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.TEMP_REJECT);// 提示信息
		}
		if (6 == orderStatus && !blackMap.get("sort").toString().equals("1")
				&& "true".equals(blackMap.get("isExpire").toString())
				&& order.getProductType() == InstallmentConstant.PRODUCT_TYPE.SINGLE) {// 临时拒绝 过了拒绝时间 的单期工单

			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.SINGLE_AUTO_MONEY);// 提示信息
		}
		if (6 == orderStatus && !blackMap.get("sort").toString().equals("1")
				&& "true".equals(blackMap.get("isExpire").toString())
				&& order.getProductType() == InstallmentConstant.PRODUCT_TYPE.MULTI) { // 临时拒绝 过了拒绝时间 的多期工单

			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.MULTI_AUTO_MONEY);// 提示信息
		}
		// 7、撤回
		if (7 == orderStatus) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.BACK);// 提示信息
		}
		// 8、逾期
		if (8 == orderStatus) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.OVERDUE);// 提示信息
		}
		// 9、审核中
		if (9 == orderStatus) {
			map.put("textMsg", InstallmentConstant.SINGLE_TEXT_MSG.IN_AUDIT);// 提示信息
		}

		return map;
	}

	/**
	 * 根据借款人Id查询借款人单期工单和分期工单
	 * 
	 * @see com.waterelephant.installment.service.OrderService#saveIndexOrders(java.lang.String)
	 */
	@Override
	public Map<String, Object> saveIndexOrders(Long borrowerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询单期工单
		BwOrder singleOrder = getOrderByProductType(borrowerId, InstallmentConstant.PRODUCT_TYPE.SINGLE);
		logger.info("【OrderServiceImpl.saveIndexOrders】borrowerId：" + borrowerId + "，单期工单");
		// 查询单期工单 弹窗
		List<Map<String, Object>> singleList = authInfoService
				.getBwOrderStatusRecordByOrderId((null == singleOrder.getId()) ? null : singleOrder.getId().toString());
		if (!CollectionUtils.isEmpty(singleList)) {
			// 改变弹框状态
			for (int i = 0; i < singleList.size(); i++) {
				Integer id = Integer.parseInt(singleList.get(i).get("id").toString());
				authInfoService.updateRecord(id);
			}
			logger.info(
					"【OrderServiceImpl.saveIndexOrders】borrowerId：" + borrowerId + "，单期工单弹窗数量:" + singleList.size());
		} else {
			logger.info("【OrderServiceImpl.saveIndexOrders】borrowerId：" + borrowerId + "，单期工单弹窗数量:0");
		}

		// 查询多期工单
		BwOrder multiOrder = getOrderByProductType(borrowerId, InstallmentConstant.PRODUCT_TYPE.MULTI);
		logger.info("【OrderServiceImpl.saveIndexOrders】borrowerId：" + borrowerId + "，分期工单");
		// 查询多期工单 弹窗
		List<Map<String, Object>> multiList = authInfoService
				.getBwOrderStatusRecordByOrderId((null == multiOrder.getId()) ? null : multiOrder.getId().toString());
		if (!CollectionUtils.isEmpty(multiList) && multiList.size() > 0) {
			// 改变弹框状态
			for (int i = 0; i < multiList.size(); i++) {
				Integer id = Integer.parseInt(multiList.get(i).get("id").toString());
				authInfoService.updateRecord(id);
			}
			logger.info("【OrderServiceImpl.saveIndexOrders】borrowerId：" + "，分期工单弹窗数量:" + singleList.size());
		} else {
			logger.info("【OrderServiceImpl.saveIndexOrders】borrowerId：" + borrowerId + "，分期工单弹窗数量:0");

		}
		map.put("multiDialogsList", multiList);
		map.put("singleDialogsList", singleList);
		Map<String, Object> singleOrderMap = getOrderMap(borrowerId, singleOrder, multiOrder,
				InstallmentConstant.PRODUCT_TYPE.SINGLE);
		singleOrderMap.put("canOperation", 1);
		singleOrderMap.put("operationMsg", "");
		map.put("singleOrder", singleOrderMap);
		Map<String, Object> multiOrderMap = getOrderMap(borrowerId, multiOrder, singleOrder,
				InstallmentConstant.PRODUCT_TYPE.MULTI);
		// String orderStatus = StringUtil.toString(multiOrderMap.get("orderStatus"));
		// if (orderStatus.equals("1") || orderStatus.equals("2") || orderStatus.equals("6") || orderStatus.equals("7"))
		// {
		// multiOrderMap.put("canOperation", 0);
		// multiOrderMap.put("operationMsg", "抱歉，该产品申请人数已满，请申请单期借款");
		// }
		multiOrderMap.put("canOperation", 1);
		multiOrderMap.put("operationMsg", "");
		map.put("multiOrder", multiOrderMap);
		return map;
	}

	/**
	 * 查询主页还款列表
	 * 
	 * @see com.waterelephant.installment.service.OrderService#getOrderLoanList(java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> getOrderLoanList(Long borrowerId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from bw_order where borrower_id = " + borrowerId
				+ " and status_id in (9,13) order by create_time desc";
		logger.info("【OrderServiceImpl.getOrderLoanList】borrowerId ： " + borrowerId + "    ,查询的sql为： " + sql);
		List<BwOrder> orderlist = sqlMapper.selectList(sql, BwOrder.class);
		logger.info("【OrderServiceImpl.getOrderLoanList】borrowerId：" + borrowerId + ",还款列表orderlist："
				+ JSONArray.toJSONString(orderlist));
		if (!CollectionUtils.isEmpty(orderlist)) {
			for (BwOrder order : orderlist) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderId", StringUtil.toInteger(order.getId()));// 工单Id
				map.put("productType", StringUtil.toInteger(order.getProductType()));// 产品类型(1、单期，2、分期)
				map.put("borrowAmount", DoubleUtil.toTwoDecimal(order.getBorrowAmount()));// 借款金额
				map.put("createTime", DateUtil.getDateString(order.getCreateTime(), DateUtil.yyyy_MM_dd));// 借款时间
				map.put("orderStatus", StringUtil.toInteger(order.getStatusId()));// 工单状态id（9 还款中 13 逾期 ）
				map.put("totalNumber", StringUtil.toInteger(order.getBorrowNumber()));// 分期期数
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 查询借款记录
	 * 
	 * @see com.waterelephant.installment.service.OrderService#getOrderList(java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> getOrderList(Long borrowerId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from bw_order where borrower_id = " + borrowerId
				+ " and status_id in (6,9,13) order by create_time desc";
		List<BwOrder> orderlist = sqlMapper.selectList(sql, BwOrder.class);
		logger.info("【OrderServiceImpl.getOrderList】borrowerId：" + borrowerId + "查询借款记录orderlist");
		if (!CollectionUtils.isEmpty(orderlist)) {
			for (BwOrder order : orderlist) {
				Integer productType = StringUtil.toInteger(order.getProductType());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderId", StringUtil.toInteger(order.getId()));// 工单Id
				map.put("productType", productType);// 产品类型(1、单期，2、分期)
				map.put("borrowAmount", DoubleUtil.toTwoDecimal(order.getBorrowAmount()));// 借款金额
				map.put("createTime", DateUtil.getDateString(order.getCreateTime(), DateUtil.yyyy_MM_dd));// 借款时间
				map.put("orderStatus", StringUtil.toInteger(order.getStatusId()));// 工单状态id（6 结束 9 还款中 13 逾期 ）
				map.put("totalNumber", StringUtil.toInteger(order.getBorrowNumber()));// 分期期数
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 查询借款记录-现金分期详情
	 * 
	 * @see com.waterelephant.installment.service.OrderService#getMultiOrderDetail(java.lang.Long)
	 */
	@Override
	public AppResponseResult getMultiOrderDetail(Long orderId) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询分期工单
		BwOrder order = bwOrderService.findBwOrderById(orderId.toString());
		if (order == null) {
			result.setCode("101");
			result.setMsg("未查询到该工单");
			return result;
		}
		logger.info("【OrderServiceImpl.getMultiOrderDetail】orderId：" + orderId + "，分期工单 order：" + order);
		Double overAmount = 0.00;// 计算逾期费用
		String contractPperiod = "";// 合同时间
		String repayType = "";
		double rate = productService
				.calcTotalInterestRateByType(Integer.parseInt(OrderStatusConstant.ORDER_PRODUCT_TYPE.MORE));
		logger.info("【OrderServiceImpl.getMultiOrderDetail】orderId：" + orderId + "，分期工单 rate：" + rate);
		Double interest = DoubleUtil.mul(order.getBorrowAmount(), rate);
		interest = interest == null ? 0.00 : interest;
		interest = DoubleUtil.round(DoubleUtil.mul(interest, order.getBorrowNumber()), 2);

		// 分期计算逾期费用
		overAmount = getOverAmount(order);
		overAmount = overAmount == null ? 0.00 : overAmount;
		logger.info("【OrderServiceImpl.getMultiOrderDetail】orderId：" + orderId + "，逾期金额overAmount：" + overAmount);

		Example example = new Example(BwRepaymentPlan.class);
		example.createCriteria().andEqualTo("orderId", orderId);
//		example.setOrderByClause(" createTime asc ");
		example.orderBy("createTime").asc();
		List<BwRepaymentPlan> planList = bwRepaymentPlanService.findRepaymentPlanByExample(example);
		logger.info("【OrderServiceImpl.getMultiOrderDetail】orderId：" + orderId + "，分期还款记录planList：" + planList);

		Date createTime = planList.get(0).getCreateTime();// 分期还款的起始时间
		Date repayTime = planList.get(planList.size() - 1).getRepayTime();// 分期的最后一次还款的结束时间

		// 获取分期最后一期的还款时间
		// String sql = "select DATE_FORMAT(p.repay_time ,'%Y-%m-%d') from bw_order o LEFT JOIN bw_repayment_plan p on "
		// + " o.id=p.order_id where o.id= #{orderId} order by p.number desc LIMIT 1 ";
		// logger.info("订单id:" + orderId + " 查询最后一起分期的还款时间的sql语句 ：" + sql);
		// String selectOne = sqlMapper.selectOne(sql, orderId, String.class);
		// logger.info("订单id:" + orderId + " 查询最后一起分期的还款时间 ：" + selectOne);
		contractPperiod = DateUtil.getDateString(createTime, MyDateUtils.DATE_TO_STRING_SHORT_PATTERN) + " 至 "
				+ DateUtil.getDateString(repayTime, MyDateUtils.DATE_TO_STRING_SHORT_PATTERN);

		Integer intRepayType = StringUtil.toInteger(order.getRepayType());
		if (intRepayType == 1) {
			repayType = "先息后本";
		}
		if (intRepayType == 2) {
			repayType = "等额本息";
		}

		// 是否付款过
		Integer hasPayAmount = 0;
		List<Map<String, Object>> list = this.getInstallmentPayInfo(orderId);
		if (list != null && list.size() > 0) {
			hasPayAmount = 1;
		}
		map.put("hasPayAmount", hasPayAmount);// 是否有还款记录 0:未还款1:还款过
		map.put("statusId", order.getStatusId());
		map.put("totalAmount",
				DoubleUtil.toTwoDecimal(DoubleUtil.add(DoubleUtil.add(order.getBorrowAmount(), interest), overAmount))
						+ "");// 应还总额（本金+利息+逾期费用）
		map.put("borrowAmount", DoubleUtil.toTwoDecimal(order.getBorrowAmount()) + "");// 本金/借款金额
		map.put("interest", DoubleUtil.toTwoDecimal(interest));// 利息
		map.put("overAmount", DoubleUtil.toTwoDecimal(overAmount) + "");// 逾期费用
		map.put("createTime", MyDateUtils.DateToString(createTime, MyDateUtils.DATE_TO_STRING_SHORT_PATTERN));// 借款时间
		map.put("contractPperiod", contractPperiod);// 合同期限(2016-3-23 至 2016-5-23)
		map.put("repayType", repayType);// 还款方式 1:先息后本 2:等额本息
		result.setCode(ActivityConstant.ErrorCode.SUCCESS);
		result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		result.setResult(map);
		logger.info("【OrderServiceImpl.getMultiOrderDetail】orderId：" + orderId + "，分期还款记录map：" + map);

		return result;
	}

	/**
	 * 根据分期工单查询逾期的金额
	 * 
	 * @param order
	 * @return
	 */
	private Double getOverAmount(BwOrder order) {
		Long orderId = order.getId();
		Double totalOverAmount = 0.00;// 逾期总额
		Long statusId = order.getStatusId();
		if (statusId == 9 || statusId == 13) {
			List<Map<String, Object>> planList = bwRepaymentPlanService.getInstallmentRepaymentList(orderId);
			if (!CollectionUtils.isEmpty(planList)) {
				for (Map<String, Object> map : planList) {
					// 还款状态：1 未还款 2 已还款 3垫付 4展期
					Integer repayStatus = StringUtil.toInteger(map.get("repay_status"));
					// 逾期金额
					Double overdueAccrualMoney = Double.valueOf(StringUtil.toString(map.get("overdue_accrual_money")));
					// 逾期,计算逾期金额
					if (3 == repayStatus) {
						totalOverAmount = DoubleUtil.add(totalOverAmount, overdueAccrualMoney);
					}
				}
			}

			// String sql = "select SUM(o.overdue_day) from bw_repayment_plan p LEFT JOIN bw_overdue_record o on p.id
			// =o.repay_id where p.order_id = #{orderId} ";
			// Integer overdueDay = sqlMapper.selectOne(sql, orderId, Integer.class);
			// overdueDay = overdueDay == null ? 0 : overdueDay;
			// if (overdueDay > 0) {
			// mul = DoubleUtil.mul(borrowAmount, DoubleUtil.mul(0.01, overdueDay));
			// }
		}
		if (statusId == 6) {// 还款成功，bw_payment_detail获取逾期金额
			String sql2 = "SELECT SUM(d.real_overdue_amount) as overdueAmount FROM bw_repayment_plan p LEFT JOIN bw_payment_detail d ON p.id = d.repay_id WHERE p.order_id = #{orderId}";
			logger.info("订单id:" + orderId + " 还款成功查询bw_payment_detail获取逾期金额的sql为： " + sql2);
			totalOverAmount = sqlMapper.selectOne(sql2, orderId, Double.class);
		}
		return totalOverAmount;
	}

	/**
	 * 查询分期还款
	 * 
	 * @see com.waterelephant.installment.service.OrderService#getMultiOrderRepay(java.lang.Long)
	 */
	@Override
	public Map<String, Object> getMultiOrderRepay(Long orderId) {
		BwOrder order = bwOrderService.findBwOrderById(orderId.toString());
		logger.info("【OrderServiceImpl.getMultiOrderRepay】orderId：" + orderId + "，查询分期的工单order");
		// 借款总额
		Double borrowAmount = order.getBorrowAmount();
		if (borrowAmount == null) {
			borrowAmount = 0.0;
		}
		List<Map<String, Object>> planList = bwRepaymentPlanService.getInstallmentRepaymentList(orderId);
		logger.info("【OrderServiceImpl.getMultiOrderRepay】orderId：" + orderId + "，查询分期的还款计划列表 planList： " + planList);
		// 当前期数
		Integer currentNumber = bwRepaymentPlanService.getCurrentNumber(orderId);
		Double maxRealityRepayMoney = 0.00; // 最大还款本金
		Double maxInterest = 0.00;// 最大利息
		Double totalRealityRepayMoney = 0.00; // 本金
		Double totalInterest = 0.00; // 利息
		Double totalOverAmount = 0.00; // 逾期费用
		double rate = productService
				.calcTotalInterestRateByType(Integer.parseInt(OrderStatusConstant.ORDER_PRODUCT_TYPE.MORE));
		// 利息
		Double interest = DoubleUtil.mul(borrowAmount, rate);
		interest = Double.parseDouble(DoubleUtil.toTwoDecimal(interest));
		if (!CollectionUtils.isEmpty(planList)) {
			for (Map<String, Object> map : planList) {
				// 还款时间
				Integer number = StringUtil.toInteger(map.get("number"));
				// 当期应还总额
				Double realityRepayMoney = Double.valueOf(StringUtil.toString(map.get("reality_repay_money")));
				// 还款状态：1 未还款 2 已还款 3垫付 4展期
				Integer repayStatus = StringUtil.toInteger(map.get("repay_status"));
				// 逾期金额
				Double overdueAccrualMoney = Double.valueOf(StringUtil.toString(map.get("overdue_accrual_money")));
				// 未还款,计算最大还款本金
				maxRealityRepayMoney = DoubleUtil.add(maxRealityRepayMoney, realityRepayMoney);
				maxInterest = DoubleUtil.add(maxInterest, interest);
				// 未还款或逾期,并且在当前期数之前,计算还款本金和利息
				if (number <= currentNumber) {
					totalRealityRepayMoney = DoubleUtil.add(totalRealityRepayMoney, realityRepayMoney);
					totalInterest = DoubleUtil.add(totalInterest, interest);
				}
				// 逾期,计算逾期金额
				if (3 == repayStatus) {
					totalOverAmount = DoubleUtil.add(totalOverAmount, overdueAccrualMoney);
				}

			}
		}
		Double alreadyTotal = bwPlatformRecordService.getAlreadyTotal(orderId);
		// 支付明细逾期总额
		Double totalDetailOverdueAmount = sqlMapper.selectOne(
				"select sum(real_overdue_amount) from bw_payment_detail where order_id=" + orderId, Double.class);
		if (totalDetailOverdueAmount == null) {
			totalDetailOverdueAmount = 0.0;
		}
		alreadyTotal = DoubleUtil.sub(alreadyTotal, totalDetailOverdueAmount);
		logger.info("【OrderServiceImpl.getMultiOrderRepay】orderId：" + orderId + "，已还总额alreadyTotal:" + alreadyTotal);
		Double maxAmount = DoubleUtil.sub(DoubleUtil.add(maxRealityRepayMoney, totalOverAmount), alreadyTotal);
		Double totalAmount = DoubleUtil.sub(DoubleUtil.add(totalRealityRepayMoney, totalOverAmount), alreadyTotal);
		Map<String, Object> map = new HashMap<String, Object>();
		double realityRepayMoney = DoubleUtil.sub(totalRealityRepayMoney, alreadyTotal);
		if (realityRepayMoney < 0.0) {
			totalOverAmount = DoubleUtil.add(totalOverAmount, realityRepayMoney);
			realityRepayMoney = 0.0;
		}
		List<Map<String, Object>> installmentList = installmentService.getInstallmentList(orderId);
		// if (installmentList != null && !installmentList.isEmpty()) {
		// for (Map<String, Object> installmentMap : installmentList) {
		// Double tempBorrowAmount = NumberUtil.parseDouble(installmentMap.get("borrowAmount") + "", null);
		// if (tempBorrowAmount != null) {
		// installmentMap.put("borrowAmount",
		// DoubleUtil.toTwoDecimal(DoubleUtil.add(tempBorrowAmount, interest)));
		// }
		// }
		// }
		logger.info(
				"【OrderServiceImpl.getMultiOrderRepay】orderId：" + orderId + "，分期明细installmentList：" + installmentList);
		map.put("maxAmount", DoubleUtil.toTwoDecimal(maxAmount));// 最大还款金额
		map.put("totalAmount", DoubleUtil.toTwoDecimal(totalAmount));// 应还总额（本金+利息+逾期费用）
		map.put("realityRepayMoney", DoubleUtil.toTwoDecimal(realityRepayMoney));// 本金/借款金额
		map.put("interest", DoubleUtil.toTwoDecimal(totalInterest));// 利息
		map.put("overAmount", DoubleUtil.toTwoDecimal(totalOverAmount));// 逾期费用
		map.put("installment", installmentList);// 分期明细
		map.put("borrowAmount", DoubleUtil.toTwoDecimal(borrowAmount));// 本金
		map.put("statusId", order.getStatusId());
		logger.info("【OrderServiceImpl.getMultiOrderRepay】orderId：" + orderId + "，还款信息map：" + JSON.toJSONString(map));
		return map;
	}

	/**
	 * 查询借款记录-现金分期-还款记录
	 */
	@Override
	public List<Map<String, Object>> getInstallmentPayInfo(Long orderId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select  DATE_FORMAT(d.create_time,'%Y-%m-%d')  as createTime, CAST(d.amount AS char ) AS tradeAmount "
				+ " from  bw_order_repayment_batch_detail d  where d.order_id = #{orderId} order by d.create_time";
		list = sqlMapper.selectList(sql, orderId);
		return list;
	}

	@Override
	public boolean hasInAuditOrder(Long borrowerId) {
		// 有正在审核中的工单
		Integer count = sqlMapper.selectOne(
				"select count(*) from bw_order where status_id in (2,3) and borrower_id=" + borrowerId, Integer.class);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasProcessingOrder(Long borrowerId, Integer productType) {
		String sql = "select count(*) from bw_order where status_id in (2,3,4,5,8,9,11,12,13,14) and borrower_id="
				+ borrowerId;
		if (productType != null) {
			sql += " and product_type=" + productType;
		}
		// 有正在进行中的工单
		Integer count = sqlMapper.selectOne(sql, Integer.class);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasStatusIdOrder(Long borrowerId, String statusIdStr, Integer productType) {
		String sql = "select count(*) from bw_order where status_id in (" + statusIdStr + ") and borrower_id="
				+ borrowerId;
		if (productType != null) {
			sql += " and product_type=" + productType;
		}
		// 有正在进行中的工单
		Integer count = sqlMapper.selectOne(sql, Integer.class);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据工单号查询还款计划列表
	 */
	private List<BwRepaymentPlan> getRepayTimeByOrderId(Long id) {
		String sql = "SELECT * from bw_repayment_plan where order_id = " + id
				+ " and repay_status = 1 GROUP BY number ASC ";
		List<BwRepaymentPlan> list = sqlMapper.selectList(sql, BwRepaymentPlan.class);

		return null == list ? new ArrayList<BwRepaymentPlan>() : list;
	}

	/**
	 * 根据单期工单号查询应还金额（最新一期还款计划）
	 */
	private Map<String, Object> getMoneyByOrderId(Long id) {
		String sql = "select reality_repay_money , repay_time  from bw_repayment_plan where order_id =" + id
				+ " and repay_status = 1   ORDER BY create_time DESC LIMIT 0,1 ";
		Map<String, Object> map = sqlMapper.selectOne(sql);
		return CommUtils.isNull(map) ? new HashMap<String, Object>() : map;
	}
}
