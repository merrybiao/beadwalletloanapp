package com.waterelephant.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.waterelephant.channel.service.ProductService;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.service.ActivityDiscountUseageService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRepaymentBatchDetailService;
import com.waterelephant.service.BwOrderStatusRecordService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwPaymentDetailService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.TestAnalogService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.SqlMapper;
import com.waterelephant.utils.StringUtil;

/**
 * 
 * Module:
 * 
 * TestAnalogServiceImpl.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class TestAnalogServiceImpl implements TestAnalogService {
	private Logger logger = Logger.getLogger(TestAnalogServiceImpl.class);
	@Autowired
	protected SqlMapper sqlMapper;
	@Autowired
	private IBwOrderService orderService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private BwPaymentDetailService bwPaymentDetailService;
	@Autowired
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private BwOrderStatusRecordService bwOrderStatusRecordService;
	@Autowired
	private ActivityDiscountUseageService activityDiscountUseageService;
	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private IBwBankCardService bankCardService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;

	@Override
	public String updateAndSignBank(String name, String phone, String idCard, String cardNo) {
		if (StringUtil.isEmpty(name)) {
			return "姓名不能为空";
		}
		if (StringUtil.isEmpty(phone)) {
			return "手机号不能为空";
		}
		if (StringUtil.isEmpty(idCard)) {
			return "身份证号不能为空";
		}
		if (StringUtil.isEmpty(cardNo)) {
			return "银行卡号不能为空";
		}
		BwBorrower entity = new BwBorrower();
		entity.setPhone(phone);
		BwBorrower borrower = bwBorrowerService.findBwBorrowerByAttr(entity);
		if (null == borrower || StringUtil.isEmpty(borrower.getId())) {
			return "根据手机号查询不到借款人";
		}
		borrower.setName(name);
		borrower.setIdCard(idCard);
		borrower.setAge(18);
		bwBorrowerService.updateBwBorrower(borrower);
		BwBankCard bwBankCard = new BwBankCard();
		bwBankCard.setBorrowerId(borrower.getId());
		bwBankCard.setCardNo(cardNo);
		bwBankCard.setSignStatus(2);
		bwBankCard.setBankCode("0102");
		bwBankCard.setBankName("中国工商银行");
		BwBankCard alBwBankCard = bankCardService.findBwBankCardByAttr(bwBankCard);
		if (null != alBwBankCard && !StringUtil.isEmpty(alBwBankCard.getId())) {
			return "银行卡已绑定";
		}
		bankCardService.addBwBankCard(bwBankCard);
		return "OK";
	}

	@Override
	public String updateAndOrderAuth(String orderId, String authType) {
		if (StringUtil.isEmpty(orderId)) {
			return "工单Id不能为空";
		}
		if (StringUtil.isEmpty(authType)) {
			return "认证类型不能为空";
		}
		BwOrder order = orderService.findBwOrderById(orderId);
		if (null == order || StringUtil.isEmpty(order.getId())) {
			return "工单不存在";
		}
		BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(Long.parseLong(orderId),
				Integer.parseInt(authType));
		if (null != bwOrderAuth && !StringUtil.isEmpty(bwOrderAuth.getId())) {
			return "此类型已认证过";
		}
		if ("4".equals(authType)) {
			BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(order.getBorrowerId());
			if (null == bwZmxyGrade || StringUtil.isEmpty(bwZmxyGrade.getId())) {
				bwZmxyGrade = new BwZmxyGrade();
				bwZmxyGrade.setBorrowerId(order.getBorrowerId());
				bwZmxyGrade.setCreateTime(new Date());
				bwZmxyGrade.setUpdateTime(new Date());
				bwZmxyGrade.setZmScore(750);
				bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
			} else {
				bwZmxyGrade.setUpdateTime(new Date());
				bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
			}

		}
		bwOrderAuth = new BwOrderAuth();
		bwOrderAuth.setOrderId(Long.parseLong(orderId));
		bwOrderAuth.setAuth_type(Integer.parseInt(authType));
		bwOrderAuth.setPhotoState(1);
		bwOrderAuth.setAuth_channel(4);
		bwOrderAuth.setCreateTime(new Date());
		bwOrderAuth.setUpdateTime(new Date());
		bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
		return "OK";
	}

	@Override
	public String updateAndAuditSuccess(String phone, String orderIdStr) {
		Long orderId = NumberUtil.parseLong(orderIdStr, null);
		if (orderId == null) {
			return "工单Id不能为空";
		}
		if (CommUtils.isNull(phone)) {
			return "手机号不能为空";
		}
		BwBorrower paramBorrower = new BwBorrower();
		paramBorrower.setPhone(phone);
		BwBorrower bwBorrower = bwBorrowerService.selectOne(paramBorrower);
		if (bwBorrower == null) {
			return "没有此用户";
		}
		BwOrder bwOrder = bwOrderService.selectByPrimaryKey(orderId);
		if (bwOrder == null) {
			return "没有此工单";
		}
		Integer productType = bwOrder.getProductType();
		if (productType == null) {
			return "productType为空";
		}
		if (!bwOrder.getBorrowerId().equals(bwBorrower.getId())) {
			return "工单borrowerId和借款人不匹配";
		}
		bwOrder.setBorrowAmount(bwOrder.getExpectMoney());
		bwOrder.setMark("100");
		bwOrder.setBorrowNumber(bwOrder.getExpectNumber());
		bwOrder.setBorrowUse("生活消费");
		bwOrder.setRepayTerm(1);
		bwOrder.setBorrowRate(0.09);
		bwOrder.setContractRate(0.079);
		bwOrder.setContractMonthRate(0.00635634);
		bwOrder.setStatusId(4L);
		bwOrder.setUpdateTime(new Date());
		bwOrderService.updateByPrimaryKey(bwOrder);
		BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
		bwOrderProcessRecord.setOrderId(bwOrder.getId());
		bwOrderProcessRecord.setArtifiAuditFinalTime(new Date());
		bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
		return "OK";
	}

	@Override
	public String deleteBorrower(String phone) {
		if (StringUtil.isEmpty(phone)) {
			return "手机号不能为空";
		}
		BwBorrower borrower = new BwBorrower();
		borrower.setPhone(phone);
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		if (null == borrower || StringUtil.isEmpty(borrower.getId())) {
			return "用户不存在";
		}
		bwBorrowerService.deleteBorrower(borrower);
		bankCardService.deleteBankCard(borrower.getId());
		return "OK";
	}

	@Override
	public String updateAndAnalogLoan(String orderId, String repayDate) {
		BwOrder order = null;
		int num = 0;
		if (StringUtil.isEmpty(orderId)) {
			return "工单Id不能为空";
		}
		if (StringUtil.isEmpty(repayDate)) {
			return "放款时间不能为空";
		}
		order = orderService.findBwOrderById(orderId);
		if (order == null) {
			return "工单不存在";
		}
		order.setStatusId(9l);
		orderService.update(order);
		Integer borrowNumber = order.getBorrowNumber();
		Double borrowAmount = order.getBorrowAmount();
		if (StringUtil.toInteger(borrowNumber) == 0) {
			borrowNumber = 1;
		}
		double preMoney = Math.floor(DoubleUtil.div(borrowAmount, Double.valueOf(borrowNumber)));
		for (int i = 1; i <= borrowNumber; i++) {
			BwRepaymentPlan plan = new BwRepaymentPlan();
			plan.setOrderId(order.getId());
			BwProductDictionary product = productService.queryByOrderId(order.getId());
			if (1 == order.getProductType()) {
//				BwProductDictionary product = productService.queryByOrderId(order.getId());
				plan.setRepayMoney(borrowAmount);
				plan.setRepayTime(MyDateUtils.addDays(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd),
						StringUtil.toInteger(product.getpTerm()) * i));
				plan.setRepayStatus(1);
				plan.setRepayType(1);
				plan.setRepayCorpusMoney(preMoney);
				plan.setRepayAccrualMoney(0.00);
				plan.setCreateTime(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd));
				plan.setNumber(i);
				plan.setAlreadyRepayMoney(0.00);
				Double zjwCost = 0.0;
				if (product != null && product.getZjwCost() != null) {
					zjwCost = product.getZjwCost();
				}
				plan.setZjw(DoubleUtil.round(DoubleUtil.mul(order.getBorrowAmount(), zjwCost), 2));
				plan.setRealityRepayMoney(DoubleUtil.add(borrowAmount, plan.getZjw()));
			}
			if (2 == order.getProductType()) {
				if (i < borrowNumber) {
					Double realityRepayMoney = Math.floor(DoubleUtil.add(preMoney, DoubleUtil.mul(borrowAmount, 0.07)));
					plan.setRepayMoney(realityRepayMoney);
					plan.setRealityRepayMoney(realityRepayMoney);
					plan.setRepayCorpusMoney(preMoney);
				} else {
					Double lastMoney = DoubleUtil.sub(borrowAmount, DoubleUtil.mul(preMoney, borrowNumber - 1));
					Double realityRepayMoney = Math
							.floor(DoubleUtil.add(lastMoney, DoubleUtil.mul(borrowAmount, 0.07)));
					plan.setRepayMoney(realityRepayMoney);
					plan.setRealityRepayMoney(realityRepayMoney);
					plan.setRepayCorpusMoney(lastMoney);
				}
				if(product.getpTermType().equals("3")){
					plan.setRepayTime(MyDateUtils.addDays(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd), 7 * i));
				}else{
					plan.setRepayTime(MyDateUtils.addDays(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd), 30 * i));
				}
				plan.setRepayStatus(1);
				plan.setRepayType(1);
				plan.setRepayAccrualMoney(Math.floor(DoubleUtil.mul(borrowAmount, 0.07)));
				plan.setCreateTime(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd));
				plan.setNumber(i);
				plan.setAlreadyRepayMoney(0.00);
			}
			bwRepaymentPlanService.saveBwRepaymentPlan(plan);
			num++;
		}
		logger.info("工单Id" + order.getId() + "插入还款计划条数:" + num);
		return "工单Id" + order.getId() + "插入还款计划条数:" + num;
	}

	@Override
	public String updateAndAnalogOverDue(String orderId) {
		BwOrder order = null;
		int num = 0;
		if (StringUtil.isEmpty(orderId)) {
			return "orderId不能为空";
		}
		order = orderService.findBwOrderById(orderId);
		if (order == null) {
			return "工单不存在";
		}
		List<BwRepaymentPlan> planList = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(order.getId());
		Date now = new Date();
		String nowTime = CommUtils.convertDateToString(dayStart(now), "yyyy-MM-dd HH:mm:ss");
		boolean updateOrderStatusBool = true;
		int updateOrderStatusId = 9;
		BwProductDictionary bwProductDictionary = bwProductDictionaryService
				.findBwProductDictionaryById(order.getProductId());
		for (BwRepaymentPlan plan : planList) {
			// 根据还款计划查找逾期记录表
			// 生成新逾期记录
			int day = MyDateUtils.getDaySpace(dayStart(plan.getRepayTime()),
					DateUtil.stringToDate(nowTime, DateUtil.yyyy_MM_dd_HHmmss));
			if (day > 0) {
				if (updateOrderStatusId != 13) {
					updateOrderStatusId = 13;
				}
				// 罚息：（逾期天数-免罚息期）*（借款金额*0.01）
				double money = DoubleUtil.mul(DoubleUtil.mul(day, order.getBorrowAmount()),
						bwProductDictionary.getRateOverdue());
				BwOverdueRecord bo = new BwOverdueRecord();
				bo.setOverdueAccrualMoney(money);
				bo.setOverdueCorpus(order.getBorrowAmount());
				bo.setOverdueDay(day);
				bo.setOverdueStatus(1);
				bo.setUpdateTime(now);
				BwOverdueRecord queryBo = bwOverdueRecordService.queryBwOverdueByRepayId(plan.getId());
				if (queryBo != null) {
					bo.setId(queryBo.getId());
					bwOverdueRecordService.updateBwOverdueRecord(bo);
				} else {
					bo.setOrderId(order.getId());
					bo.setRepayId(plan.getId());
					bo.setCreateTime(now);
					bwOverdueRecordService.saveBwOverdueRecord(bo);
				}
				// 逾期类型，垫付状态
				plan.setRepayType(2);
				plan.setRepayStatus(3);
				bwRepaymentPlanService.update(plan);
				orderService.update(order);
				num++;
				logger.info("还款计划Id" + plan.getId() + "插入逾期记录条数:" + num);
			}
		}
		if (updateOrderStatusBool) {
			sqlMapper.update("update bw_order set status_id=" + updateOrderStatusId + " where id=" + orderId);
		}
		return "OK";
	}

	@Override
	public String updateAndClearOrder(String orderId) {
		if (StringUtil.isEmpty(orderId)) {
			return "orderId不能为空";
		}
		BwOrder order = orderService.findBwOrderById(orderId);
		order.setStatusId(9l);
		orderService.update(order);
		List<BwRepaymentPlan> planList = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(order.getId());
		if (!CollectionUtils.isEmpty(planList)) {
			for (BwRepaymentPlan plan : planList) {
				bwRepaymentPlanService.deleteBwRepaymentPlan(plan.getId());
				// 删除redis支付明细
				bwPaymentDetailService.deleteRedis(plan.getId());
				// 删除数据库支付明细
				bwPaymentDetailService.deleteBwPaymentDetail(plan.getId());
				// 删除redis分批还款
				bwOrderRepaymentBatchDetailService.deleteBatchDetailAndRepaymentDetailRedis(order.getId(),
						plan.getId());
				// 删除逾期记录
				bwOverdueRecordService.deleteBwOverdueRecord(plan.getId());
			}
		}
		// 删除数据库分批还款
		bwOrderRepaymentBatchDetailService.deleteBwOrderRepaymentBatchDetail(order.getId());
		// 删除数据库支付流水
		bwPlatformRecordService.deleteBwPlatformRecord(order.getId());
		// 删除弹窗记录
		bwOrderStatusRecordService.deleteBwOrderStatusRecord(order.getId());
		// 删除优惠券使用记录
		activityDiscountUseageService.deleteActivityDiscountUseage(order.getId());
		return "OK";
	}

	@Override
	public String deleteOrderInfo(String phone, String orderIdStr) {
		String resultStr = "FAIL";
		Long orderId = NumberUtil.parseLong(orderIdStr, null);
		if (orderId == null) {
			return "工单Id不能为空";
		}
		if (CommUtils.isNull(phone)) {
			return "手机号不能为空";
		}
		BwBorrower paramBorrower = new BwBorrower();
		paramBorrower.setPhone(phone);
		BwBorrower bwBorrower = bwBorrowerService.selectOne(paramBorrower);
		if (bwBorrower == null) {
			return "没有此用户";
		}
		BwOrder bwOrder = bwOrderService.selectByPrimaryKey(orderId);
		if (bwOrder == null) {
			return "没有此工单";
		}
		Integer productType = bwOrder.getProductType();
		if (productType == null) {
			return "productType为空";
		}
		if (!bwOrder.getBorrowerId().equals(bwBorrower.getId())) {
			return "工单borrowerId和借款人不匹配";
		}
		resultStr = updateAndClearOrder(orderIdStr);
		if ("OK".equalsIgnoreCase(resultStr)) {
			// 删除认证信息和工单
			BwOrderAuth paramAuth = new BwOrderAuth();
			paramAuth.setOrderId(orderId);
			bwOrderAuthService.deleteBwOrderAuth(paramAuth);
			bwOrderService.deleteByPrimaryKey(orderId);
		}
		return resultStr;
	}

	private Date dayStart(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		c.set(Calendar.MILLISECOND, 00);
		return new Date(c.getTimeInMillis());
	}

}
