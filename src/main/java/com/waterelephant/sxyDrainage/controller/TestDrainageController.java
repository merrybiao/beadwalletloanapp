//package com.waterelephant.sxyDrainage.controller;
//
//import java.util.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.waterelephant.entity.*;
//import com.waterelephant.mapper.BwRejectRecordMapper;
//import com.waterelephant.service.*;
//import com.waterelephant.utils.*;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.channel.service.ProductService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwOrderService;
//import com.waterelephant.service.impl.BwRepaymentPlanService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import tk.mybatis.mapper.entity.Example;
//
//@Controller
//@RequestMapping("/testDrainage")
//public class TestDrainageController {
//
//	private Logger logger = Logger.getLogger(TestDrainageController.class);
//
//	@Autowired
//	private TestAnalogService testAnalogService;
//	@Autowired
//	private BwCheckRecordService bwCheckRecordService;
//	@Autowired
//	private BwOrderService bwOrderService;
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//	@Autowired
//	private BwOrderProcessRecordService bwOrderProcessRecordService;
//	@Autowired
//	private BwRepaymentPlanService bwRepaymentPlanService;
//	@Autowired
//	private ProductService productService;
//
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//
//	@Autowired
//	private BwRejectRecordService bwRejectRecordService;
//
//	@Autowired
//	protected SqlMapper sqlMapper;
//
//	@Autowired
//	private BwRejectRecordMapper bwRejectRecordMapper;
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//
//
//	/**
//	 * 模拟测试
//	 *
//	 * @param resquest
//	 * @return
//	 */
//	@RequestMapping("/testDrainage.do")
//	public String analogTest(HttpServletRequest resquest) {
//		return "drainageTest";
//	}
//
//	/**
//	 * 删除用户
//	 *
//	 * @param resquest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/testDeleteBorrower.do",produces = "text/html;charset=UTF-8")
//	public String deleteBorrower(HttpServletRequest resquest) {
//		try {
//			String phone = resquest.getParameter("phone");
//			if (StringUtil.isEmpty(phone)) {
//				return "手机号不能为空";
//			}
//			String resultStr = testAnalogService.deleteBorrower(phone);
//			return resultStr;
//		} catch (Exception e) {
//			logger.error("删除用户失败", e);
//			return "ERROR";
//		}
//	}
//
//
//	/**
//	 * 审核通过
//	 *
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="/testAuditSuccess.do",produces="text/html;charset=UTF-8")
//	public String testAuditSuccess(HttpServletRequest request) {
//		try {
//			Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
//			logger.info("【TestAnalogController.testAuditSuccess】paramMap=" + paramMap);
//			String phone = request.getParameter("phone");
//			String orderIdStr = request.getParameter("orderId");
//			Long orderId = NumberUtil.parseLong(orderIdStr, null);
//			if (CommUtils.isNull(phone)) {
//				return "手机号不能为空";
//			}
//			BwOrder bwOrder = null;
//			if (StringUtils.isBlank(orderIdStr)) {
//				String thirdOrderNo = request.getParameter("thirdOrderNo");
//				if (StringUtils.isBlank(thirdOrderNo)) {
//					return "工单号或三方单号为空";
//				}
//				bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			} else {
//				bwOrder = bwOrderService.selectByPrimaryKey(orderId);
//			}
//
//			if (bwOrder == null) {
//				return "没有此工单";
//			}
//			BwBorrower paramBorrower = new BwBorrower();
//			paramBorrower.setPhone(phone);
//			BwBorrower bwBorrower = bwBorrowerService.selectOne(paramBorrower);
//			if (bwBorrower == null) {
//				return "没有此用户";
//			}
//			Integer productType = bwOrder.getProductType();
//			if (productType == null) {
//				return "productType为空";
//			}
//			if (!bwOrder.getBorrowerId().equals(bwBorrower.getId())) {
//				return "工单borrowerId和借款人不匹配";
//			}
//			bwOrder.setBorrowAmount(bwOrder.getExpectMoney());
//			bwOrder.setMark("100");
//			bwOrder.setBorrowNumber(bwOrder.getExpectNumber());
//			bwOrder.setBorrowUse("生活消费");
//			bwOrder.setRepayTerm(1);
//			bwOrder.setBorrowRate(0.09);
//			bwOrder.setContractRate(0.079);
//			bwOrder.setContractMonthRate(0.00635634);
//			bwOrder.setStatusId(4L);
//			bwOrder.setUpdateTime(new Date());
//			bwOrderService.updateByPrimaryKey(bwOrder);
//			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//			bwOrderProcessRecord.setOrderId(bwOrder.getId());
//			bwOrderProcessRecord.setArtifiAuditFinalTime(new Date());
//			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//			BwCheckRecord bwCheckRecord = new BwCheckRecord();
//			bwCheckRecord.setOrderId(bwOrder.getId());
//			bwCheckRecord.setStatusId(4L);
//			bwCheckRecord.setResult(4);
//			bwCheckRecord.setComment("审核通过");
//			bwCheckRecord.setCreateTime(new Date());
//			bwCheckRecordService.add(bwCheckRecord);
//			HashMap<String, String> hm = new HashMap<>();
//			hm.put("channelId", bwOrder.getChannel() + "");
//			hm.put("orderId", bwOrder.getId() + "");
//			hm.put("orderStatus", 4 + "");
//			hm.put("result", "");
//			String hmData = JSON.toJSONString(hm);
//			RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//			return "OK";
//		} catch (Exception e) {
//			logger.error("审核异常", e);
//			return "ERROR";
//		}
//	}
//
//	/**
//	 * 放款
//	 *
//	 * @param resquest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="/analogMultiTermLoan.do",produces="text/html;charset=UTF-8")
//	public String analogMultiTermLoan(HttpServletRequest resquest) {
//		try {
//			String repayDate = resquest.getParameter("repayDate");
//			BwOrder bwOrder = null;
//			int num = 0;
//			String phone = resquest.getParameter("phone");
//			String orderIdStr = resquest.getParameter("orderId");
//			if (CommUtils.isNull(phone)) {
//				return "手机号不能为空";
//			}
//			if (StringUtils.isBlank(orderIdStr)) {
//				String thirdOrderNo = resquest.getParameter("thirdOrderNo");
//				if (StringUtils.isBlank(thirdOrderNo)) {
//					return "工单号或三方单号为空";
//				}
//				bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			} else {
//				bwOrder = bwOrderService.selectByPrimaryKey(NumberUtil.parseLong(orderIdStr, null));
//			}
//
//			if (bwOrder == null) {
//				return "没有此工单";
//			}
//			BwBorrower paramBorrower = new BwBorrower();
//			paramBorrower.setPhone(phone);
//			BwBorrower bwBorrower = bwBorrowerService.selectOne(paramBorrower);
//			if (bwBorrower == null) {
//				return "没有此用户";
//			}
//			if (StringUtil.isEmpty(repayDate)) {
//				return "放款时间不能为空";
//			}
//			bwOrder.setStatusId(9L);
//			bwOrderService.update(bwOrder);
//			Integer borrowNumber = bwOrder.getBorrowNumber();
//			Double borrowAmount = bwOrder.getBorrowAmount();
//			if (StringUtil.toInteger(borrowNumber) == 0) {
//				borrowNumber = 1;
//			}
//			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
//			bwRepaymentPlan.setOrderId(bwOrder.getId());
//			bwRepaymentPlanService.delete(bwRepaymentPlan);
//			double preMoney = Math.floor(DoubleUtil.div(borrowAmount, Double.valueOf(borrowNumber)));
//			for (int i = 1; i <= borrowNumber; i++) {
//				BwRepaymentPlan plan = new BwRepaymentPlan();
//				plan.setOrderId(bwOrder.getId());
//				BwProductDictionary product = productService.queryByOrderId(bwOrder.getId());
//				Double repayAccrualMoney = DrainageUtils.calculateRepayMoney(borrowAmount, i, product.getInterestRate());
//				plan.setRepayMoney(preMoney + repayAccrualMoney);
//				plan.setRealityRepayMoney(preMoney + repayAccrualMoney);
//				plan.setRepayCorpusMoney(preMoney);
//				plan.setRepayTime(MyDateUtils.addDays(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd), 7 * i));
//				plan.setRepayStatus(1);
//				plan.setRepayType(1);
//				plan.setRepayAccrualMoney(repayAccrualMoney);
//				plan.setCreateTime(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd));
//				plan.setNumber(i);
//				plan.setAlreadyRepayMoney(0.00);
//				bwRepaymentPlanService.saveBwRepaymentPlan(plan);
//				num++;
//			}
//			HashMap<String, String> hm = new HashMap<>();
//			hm.put("channelId", bwOrder.getChannel() + "");
//			hm.put("orderId", bwOrder.getId() + "");
//			hm.put("orderStatus", 9 + "");
//			hm.put("result", "放款成功");
//			String hmData = JSON.toJSONString(hm);
//			RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//			return "工单Id" + bwOrder.getId() + "插入还款计划条数:" + num;
//		} catch (Exception e) {
//			logger.error("插入分期还款计划失败", e);
//			return "ERROR";
//		}
//	}
//
//	/**
//	 * 生成逾期记录
//	 *
//	 * @param resquest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/analogMultiTermOverDue.do")
//	public String analogMultiTermOverDue(HttpServletRequest resquest) {
//		try {
//			String orderId = resquest.getParameter("orderId");
//			String resultStr = testAnalogService.updateAndAnalogOverDue(orderId);
//			return resultStr;
//		} catch (Exception e) {
//			logger.error("插入逾期记录失败", e);
//			return "ERROR";
//		}
//	}
//
//
//	/***
//	 * 新生成逾期记录
//	 */
//	@ResponseBody
//	@RequestMapping(value="/makeOverDue.do",produces="text/html;charset=UTF-8")
//	public String makeOverDue(HttpServletRequest resquest) {
//		try {
//			String orderId = resquest.getParameter("orderId");
//			//输入逾期的期数
//			String numberStr = resquest.getParameter("number");
//			if(CommUtils.isNull(numberStr)){
//				return "请输入逾期期数";
//			}
//			int number=Integer.valueOf(numberStr);
//			BwOrder order = null;
//			int num = 0;
//			if (StringUtil.isEmpty(orderId)) {
//				return "orderId不能为空";
//			}
//			order = bwOrderService.findBwOrderById(orderId);
//			if (order == null) {
//				return "工单不存在";
//			}
//			List<BwRepaymentPlan> planList = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(order.getId());
//			if(planList==null){
//				return "没有对应的还款计划";
//			}
//
//			Date now = new Date();
//			boolean updateOrderStatusBool = true;
//			int updateOrderStatusId = 9;
//			BwProductDictionary bwProductDictionary = bwProductDictionaryService
//					.findBwProductDictionaryById(order.getProductId());
//			for (BwRepaymentPlan plan : planList) {
//				// 根据还款计划查找逾期记录表
//				// 生成新逾期记录
//				if (number > plan.getNumber()) {
//					if (plan.getRepayStatus() != 2) {
//						return "请确认是否正常还清";
//					}
//				}
//				if (number == plan.getNumber()) {
//					if (plan.getRepayStatus() == 2) {
//						return "本期已划款,不能生成逾期记录";
//					}
//				}
//			}
//			for (BwRepaymentPlan plan : planList) {
//				// 根据还款计划查找逾期记录表
//				// 生成新逾期记录
//
//				if(number==plan.getNumber()){
//					//默认逾期一天
//					int day=1;
//					if (updateOrderStatusId != 13) {
//						updateOrderStatusId = 13;
//					}
//					// 罚息：（逾期天数-免罚息期）*（借款金额*0.015）
//					double money = DoubleUtil.mul(DoubleUtil.mul(day, order.getBorrowAmount()),
//							bwProductDictionary.getRateOverdue());
//					BwOverdueRecord bo = new BwOverdueRecord();
//					bo.setOverdueAccrualMoney(money);
//					bo.setOverdueCorpus(order.getBorrowAmount());
//					bo.setOverdueDay(day);
//					bo.setOverdueStatus(1);
//					bo.setUpdateTime(now);
//					BwOverdueRecord queryBo = bwOverdueRecordService.queryBwOverdueByRepayId(plan.getId());
//					if (queryBo != null) {
//						bo.setId(queryBo.getId());
//						bwOverdueRecordService.updateBwOverdueRecord(bo);
//					} else {
//						bo.setOrderId(order.getId());
//						bo.setRepayId(plan.getId());
//						bo.setCreateTime(now);
//						bwOverdueRecordService.saveBwOverdueRecord(bo);
//					}
//					// 逾期类型，垫付状态
//					plan.setRepayType(2);
//					plan.setRepayStatus(3);
//					plan.setRepayTime(MyDateUtils.addDays(dayStart(now),-1));
//					plan.setCreateTime(MyDateUtils.addDays(dayStart(now),-8));
//					bwRepaymentPlanService.update(plan);
//					bwOrderService.update(order);
//					HashMap<String, String> hm = new HashMap<>();
//					hm.put("channelId", order.getChannel() + "");
//					hm.put("orderId", order.getId() + "");
//					hm.put("orderStatus", 13 + "");
//					hm.put("result", "已逾期");
//					String hmData = JSON.toJSONString(hm);
//					RedisUtils.rpush("tripartite:orderStatusNotify:" + order.getChannel(), hmData);
//					num++;
//					logger.info("还款计划Id" + plan.getId() + "插入逾期记录条数:" + num);
//				}
//
//			}
//			if (updateOrderStatusBool) {
//				sqlMapper.update("update bw_order set status_id=" + updateOrderStatusId + " where id=" + orderId);
//			}
//			return "OK";
//		} catch (Exception e) {
//			logger.error("插入逾期记录失败", e);
//			return "ERROR";
//		}
//	}
//
//	/**
//	 * 清除还款数据
//	 * 
//	 * @param resquest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="/clearMultiOrder.do",produces="text/html;charset=UTF-8")
//	public String clearMultiOrder(HttpServletRequest resquest) {
//		try {
//			String orderId = resquest.getParameter("orderId");
//			if (StringUtil.isEmpty(orderId)) {
//				return "orderId不能为空";
//			}
//			String resultStr = testAnalogService.updateAndClearOrder(orderId);
//			return resultStr;
//		} catch (Exception e) {
//			logger.error("清除还款数据失败", e);
//		}
//		return "OK";
//	}
//
//	/**
//	 * 删除工单、认证、还款计划
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="/deleteOrderInfo.do",produces="text/html;charset=UTF-8")
//	public String deleteOrderInfo(HttpServletRequest request) {
//		String resultStr = "FAIL";
//		try {
//			Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
//			logger.info("【TestAnalogController.deleteOrderInfo】paramMap=" + paramMap);
//			String phone = request.getParameter("phone");
//			String orderIdStr = request.getParameter("orderId");
//			resultStr = testAnalogService.deleteOrderInfo(phone, orderIdStr);
//		} catch (Exception e) {
//			logger.error("删除工单、还款记录、认证、分批、支付明细、弹框、优惠券等信息异常", e);
//			resultStr = "ERROR";
//		}
//		return resultStr;
//	}
//
//	/**
//	 * 审批失败（状态为1,2,4）
//	 * @param request
//	 * @return 返回信息
//	 */
//	@ResponseBody
//	@RequestMapping(value="/approveFail.do",produces="text/html;charset=UTF-8")
//	public String approveFail(HttpServletRequest request){
//		String resultStr = "OK";
//		try {
//			BwOrder bwOrder=null;
//			String orderIdStr = request.getParameter("orderId");
//			String thirdOrderNo = request.getParameter("thirdOrderNo");
//			if(CommUtils.isNull(orderIdStr) && CommUtils.isNull(thirdOrderNo) ){
//				return "参数不能为空";
//			}
//			if(!CommUtils.isNull(orderIdStr)){
//				bwOrder = bwOrderService.selectByPrimaryKey(NumberUtil.parseLong(orderIdStr, null));
//			}else if(!CommUtils.isNull(thirdOrderNo)){
//				bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			}
//			if(bwOrder==null){
//				return "没有此订单";
//			}
//			Long statusId = bwOrder.getStatusId();
//			if(statusId != 4 && statusId != 1 && statusId != 2){
//				return "非审批中的状态";
//			}
//			Long orderId = bwOrder.getId();
//			//修改审批失败
//			bwOrder.setStatusId(7L);
//			bwOrder.setUpdateTime(new Date());
//			bwOrder.setSubmitTime(new Date());
//			bwOrderService.update(bwOrder);
//			//添加被拒记录
//			BwRejectRecord bwRejectRecord = new BwRejectRecord();
//			bwRejectRecord.setOrderId(orderId);
//			bwRejectRecord.setRejectInfo("测试拒绝");
//			bwRejectRecord.setRejectType(1);
//			bwRejectRecord.setCreateTime(new Date());
//			bwRejectRecordService.insert(bwRejectRecord);
//			//放入redis
//			HashMap<String, String> hm = new HashMap<>();
//			hm.put("channelId", bwOrder.getChannel() + "");
//			hm.put("orderId", bwOrder.getId() + "");
//			hm.put("orderStatus", 7 + "");
//			hm.put("result", "审批失败");
//			String hmData = JSON.toJSONString(hm);
//			RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//
//		} catch (Exception e) {
//			logger.error("修改审核失败异常",e);
//			resultStr = "系统异常";
//		}
//		return resultStr;
//	}
//
//	/**
//	 * 清除被拒记录
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="/clearReject.do",produces="text/html;charset=UTF-8")
//	public String clearReject(HttpServletRequest request) {
//		String str="OK";
//		try {
//			String phone = request.getParameter("phone");
//			if(CommUtils.isNull(phone)){
//				return "手机号为空";
//			}
//			BwBorrower bwBorrower = new BwBorrower();
//			bwBorrower.setPhone(phone);
//			BwBorrower bwBorrowerByAttr = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//			if(CommUtils.isNull(bwBorrowerByAttr)){
//				return "没有此用户";
//			}
//			Long borrwerId = bwBorrowerByAttr.getId();
//			Example example = new Example(BwOrder.class);
//			example.createCriteria().andEqualTo("borrowerId",borrwerId);
//			List<BwOrder> bwOrderList = bwOrderService.findBwOrderByExample(example);
//			if(bwOrderList!=null && bwOrderList.size()>0){
//				for(BwOrder bwOrder:bwOrderList){
//					bwRejectRecordMapper.deleteByOrderId(bwOrder.getId());
//				}
//			}
//		} catch (Exception e) {
//			logger.error("清除被拒记录异常",e);
//			str="ERROR";
//		}
//		return str;
//
//	}
//
//	/**
//	 * 清除银行卡信息
//	 * @param request
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="/clearCardInfo.do",produces="text/html;charset=UTF-8")
//	public String clearCardInfo(HttpServletRequest request) {
//		String str="OK";
//		try {
//			String phone = request.getParameter("phone");
//			if(CommUtils.isNull(phone)){
//				return "手机参数为空";
//			}
//			BwBorrower bwBorrower = new BwBorrower();
//			bwBorrower.setPhone(phone);
//			BwBorrower bwBorrowerByAttr = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//			if(bwBorrowerByAttr==null){
//				return "无此用户";
//			}
//			bwBankCardService.deleteBankCard(bwBorrowerByAttr.getId());
//		} catch (Exception e) {
//			str="ERROR";
//			logger.error("系统异常",e);
//		}
//		return str;
//	}
//
//	private static  Date dayStart(Date date) {
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.set(Calendar.HOUR_OF_DAY, 00);
//		c.set(Calendar.MINUTE, 00);
//		c.set(Calendar.SECOND, 00);
//		c.set(Calendar.MILLISECOND, 00);
//		return new Date(c.getTimeInMillis());
//	}
//
//	@RequestMapping("/test.do")
//	public String test() {
//		return "testHtml";
//	}
//}
