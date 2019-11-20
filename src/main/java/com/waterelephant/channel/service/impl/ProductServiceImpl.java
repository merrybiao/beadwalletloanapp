package com.waterelephant.channel.service.impl;

import com.alibaba.fastjson.JSON;
import com.waterelephant.activity.service.ActivityService;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ParameterConstant;
import com.waterelephant.constants.ProductConstant;
import com.waterelephant.dto.*;
import com.waterelephant.entity.*;
import com.waterelephant.service.*;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ProductServiceImpl extends BaseService<BwProductDictionary, Long> implements ProductService {
	private Logger logger = Logger.getLogger(ProductServiceImpl.class);
	@Autowired
	private BwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private BwPaymentDetailService bwPaymentDetailService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
	@Autowired
	private IBwRepaymentService bwRepaymentService;
	@Autowired
	private ExtraConfigService extraConfigService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;

	@Override
	public List<BwProductDictionary> selectByExample(Example example) {
		return mapper.selectByExample(example);
	}

	// 根据产品编号查询产品信息
	@Override
	public BwProductDictionary getProductInfo(String pNo) {
		BwProductDictionary bwProductDictionary = new BwProductDictionary();
		String sql = "select * FROM bw_product_dictionary b where p_no = '" + pNo + "'";
		bwProductDictionary = sqlMapper.selectOne(sql, BwProductDictionary.class);
		return bwProductDictionary;
	}

	@Override
	public BwProductDictionary queryByOrderId(Long orderId) {
		BwProductDictionary bwProductDictionary = null;
		Long productId = sqlMapper.selectOne("select product_id from bw_order where id=" + orderId, Long.class);
		if (productId != null) {
			bwProductDictionary = sqlMapper.selectOne("select * from bw_product_dictionary where id=" + productId,
					BwProductDictionary.class);
		}
		if (bwProductDictionary == null) {
			bwProductDictionary = queryCurrentProduct();
		}
		return bwProductDictionary;
	}

	// 根据产品id和期数返回产品配置信息
	@Override
	public BwProductTerm getProductTermInfo(int id, int termNum) {
		BwProductTerm bwProductTerm = new BwProductTerm();
		String sql = "select id,p_id,term_num,rate from bw_product_term where p_id = " + id + " and term_num = "
				+ termNum;
		bwProductTerm = sqlMapper.selectOne(sql, BwProductTerm.class);
		return bwProductTerm;
	}

	// 根据产品编号和期数返回产品配置信息
	@Override
	public BwProductTerm getProductTermInfo(String pNo, int termNum) {
		BwProductTerm bwProductTerm = new BwProductTerm();
		String sql = "select id,p_id,term_num,rate from bw_product_term where p_id = (select id from bw_product_dictionary where p_no='"
				+ pNo + "') and term_num = " + termNum;
		bwProductTerm = sqlMapper.selectOne(sql, BwProductTerm.class);
		return bwProductTerm;
	}

	@Override
	public BwProductTerm getMaxProductTermInfo(String pNo) {
		BwProductTerm bwProductTerm = new BwProductTerm();
		String sql = "select id,p_id,term_num,rate from bw_product_term where p_id = (select id from bw_product_dictionary where p_no='"
				+ pNo + "') order by term_num desc limit 1";
		bwProductTerm = sqlMapper.selectOne(sql, BwProductTerm.class);
		return bwProductTerm;
	}

	@Override
	public Integer selectMaxTermNumByNo(String pNo) {
		String sql = "select max(term_num) from bw_product_term where p_id = (select id from bw_product_dictionary where p_no='"
				+ pNo + "')";
		Integer maxTermNum = sqlMapper.selectOne(sql, Integer.class);
		return maxTermNum;
	}

	@Override
	public Integer selectMaxTermNumById(Long id) {
		String sql = "select max(term_num) from bw_product_term where p_id=" + id;
		Integer maxTermNum = sqlMapper.selectOne(sql, Integer.class);
		return maxTermNum;
	}

	// 查询配置信息列表
	@Override
	public List<ExtraConfig> getExtraConfigList() {
		List<ExtraConfig> list = new ArrayList<ExtraConfig>();
		String sql = "select ecid,code,value,describe_info,create_time,update_time from extra_config";
		list = sqlMapper.selectList(sql, ExtraConfig.class);
		return list;
	}

	@Override
	public String queryCurrentProductNo() {
		return ProductConstant.PRODUCT_USE_NO;
	}

	@Override
	public BwProductDictionary queryCurrentProduct() {
		return getProductInfo(queryCurrentProductNo());
	}

	@Override
	public QueryRepayInfo calcBorrowCost(double borrowAmount, BwProductDictionary product) {
		Double fastReviewCost = product.getpFastReviewCost();// 快速信审费
		Double platformUseCost = product.getpPlatformUseCost();// 平台使用费
		Double numberManageCost = product.getpNumberManageCost();// 账户管理费
		Double collectionPassagewayCost = product.getpCollectionPassagewayCost();// 代收通道费
		Double capitalUseCost = product.getpCapitalUseCost();// 资金使用费
		double costRate = DoubleUtil.add(fastReviewCost, platformUseCost);
		costRate = DoubleUtil.add(costRate, numberManageCost);
		costRate = DoubleUtil.add(costRate, collectionPassagewayCost);
		costRate = DoubleUtil.add(costRate, capitalUseCost);
		DecimalFormat df = new DecimalFormat("######0.00");
		QueryRepayInfo queryRepayInfo = new QueryRepayInfo();
		queryRepayInfo.setBorrowAmount(df.format(borrowAmount));
		Double loanAmount = Double.valueOf(df.format(DoubleUtil.mul(borrowAmount, costRate)));
		queryRepayInfo.setReceivedAmount(df.format(DoubleUtil.sub(borrowAmount, loanAmount)));
		queryRepayInfo.setCheckAmount(df.format(DoubleUtil.mul(borrowAmount, fastReviewCost)));
		queryRepayInfo.setPlatformAmount(df.format(DoubleUtil.mul(borrowAmount, platformUseCost)));
		queryRepayInfo.setGuanliAmount(df.format(DoubleUtil.mul(borrowAmount, numberManageCost)));
		queryRepayInfo.setTongdaoAmount(df.format(DoubleUtil.mul(borrowAmount, collectionPassagewayCost)));
		queryRepayInfo.setUseAmount(df.format(DoubleUtil.mul(borrowAmount, capitalUseCost)));
		queryRepayInfo.setLoanAmount(loanAmount);
		queryRepayInfo.setTerm(product.getpTerm());
		queryRepayInfo.setTermType(product.getpTermType());
		queryRepayInfo.setCostRate(costRate);
		// 计算利息
		Double interestRate = product.getInterestRate();
		queryRepayInfo.setInterestRate(interestRate);
		Double interestAmount = Double
				.valueOf(df.format(DoubleUtil.mul(borrowAmount, queryRepayInfo.getInterestRate())));
		queryRepayInfo.setInterestAmount(interestAmount);
		return queryRepayInfo;
	}

	@Override
	public double calcTotalCostRateByType(Integer productType) {
		BwProductDictionary product = selectCurrentByProductType(productType);
		Double fastReviewCost = product.getpFastReviewCost();// 快速信审费
		Double platformUseCost = product.getpPlatformUseCost();// 平台使用费
		Double numberManageCost = product.getpNumberManageCost();// 账户管理费
		Double collectionPassagewayCost = product.getpCollectionPassagewayCost();// 代收通道费
		Double capitalUseCost = product.getpCapitalUseCost();// 资金使用费
		double costRate = DoubleUtil.add(fastReviewCost, platformUseCost);
		costRate = DoubleUtil.add(costRate, numberManageCost);
		costRate = DoubleUtil.add(costRate, collectionPassagewayCost);
		costRate = DoubleUtil.add(costRate, capitalUseCost);
		return costRate;
	}

	@Override
	public double calcTotalInterestRateByType(Integer productType) {
		BwProductDictionary product = selectCurrentByProductType(productType);
		Double interestRate = product.getInterestRate();
		if (interestRate == null) {
			interestRate = 0.0;
		}
		return interestRate;
	}

	@Override
	public Double calcXudaiCost(double borrowAmount, Long orderId, Long repayId, BwProductDictionary product,
			int termNum, LoanInfo loanInfo) {
		if (loanInfo == null) {
			loanInfo = new LoanInfo();
		}
		if (loanInfo.getAvoidFineDate() == null) {
			BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
			loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
		}
		BwPaymentDetail bwPaymentDetail = bwPaymentDetailService.queryByRedisOrDB(repayId);
		// 免罚息天数
		DecimalFormat df = new DecimalFormat("######0.00");
		loanInfo.setOverdueAmount("0.00");
		loanInfo.setNoOverdueAmount("0.00");
		loanInfo.setIsOverdue(false);
		Double xudaiCost = 0.0;
		QueryRepayInfo repayInfo = calcBorrowCost(borrowAmount, product);
		Double loanAmount = repayInfo.getLoanAmount();// 18%借款费用
		// 续贷服务费
		BwProductTerm bwProductTerm = getProductTermInfo(product.getpNo(), termNum);
		if (bwProductTerm == null) {// 若超过最大期数，直接使用最大期数的服务费配置
			bwProductTerm = getMaxProductTermInfo(product.getpNo());
		}
		double serviceCost = 0.0;// 续贷服务费
		if (bwProductTerm != null) {
			Double rate = bwProductTerm.getRate();// 服务费
			serviceCost = DoubleUtil.mul(borrowAmount, rate);// 计算服务费
		}

		logger.info("orderId：" + orderId + "支付明细：" + bwPaymentDetail);
		if (bwPaymentDetail != null && bwPaymentDetail.getTradeType() == 2) {// 已续贷，5月13号之后续贷次数
			serviceCost = DoubleUtil.sub(bwPaymentDetail.getXudaiAmount(), loanAmount);
		}

		xudaiCost = DoubleUtil.add(loanAmount, serviceCost);// 18%借款费用+服务费
		loanInfo.setServiceAmount(df.format(DoubleUtil.round(xudaiCost, 2)));
		double realOverdueAmount = calcOverdueCost(orderId, repayId, loanInfo);// 实际应扣逾期金额(减去免罚息)
		double overdueAmount = NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0);
		if (overdueAmount > 0.0) {// 逾期
			xudaiCost = DoubleUtil.add(realOverdueAmount, xudaiCost);// 18%借款费用+服务费+逾期费
		}
		xudaiCost = DoubleUtil.round(xudaiCost, 2);

		// 湛江委3%金额
		Double zjwAmount = NumberUtil.parseDouble(loanInfo.getZjwAmount(), 0.0);
		xudaiCost = DoubleUtil.add(xudaiCost, zjwAmount);
		String repayTime = loanInfo.getRepayTime();
		if (StringUtils.isNotEmpty(repayTime)) {
			loanInfo.setZhanqiRepayTime(CommUtils
					.convertDateToString(calcExtendRepayTime(CommUtils.convertStringToDate(repayTime, SystemConstant.YMD_HMS),
							product.getpTerm(), product.getpTermType()), SystemConstant.YMD_HMS));
		}
		loanInfo.setTermStr(getTermStr(product));
		loanInfo.setAmt(df.format(xudaiCost));
		loanInfo.setRealOverdueAmount(df.format(realOverdueAmount));
		return xudaiCost;
	}

	@Override
	public Double calcRepaymentCost(double borrowAmount, Long orderId, Long repayId, BwProductDictionary product,
			LoanInfo loanInfo) {
		if (loanInfo == null) {
			loanInfo = new LoanInfo();
		}
		if (loanInfo.getAvoidFineDate() == null) {
			BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
			loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
		}

		DecimalFormat df = new DecimalFormat("######0.00");
		double noOverdueAmount = 0.0;// 免罚息金额
		double realOverdueAmount = calcOverdueCost(orderId, repayId, loanInfo);// 实际应扣逾期金额(减去免罚息)
		Double repaymentCost = loanInfo.getRealityRepayMoney();
		double overdueAmount = NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0);
		if (overdueAmount > 0.0) {// 逾期
			repaymentCost = DoubleUtil.add(realOverdueAmount, repaymentCost);// 本金+逾期费（算上减免罚息）
		}
		repaymentCost = DoubleUtil.round(repaymentCost, 2);
		if (loanInfo != null) {
			loanInfo.setOverdueAmount(df.format(overdueAmount));
			loanInfo.setBorrowAmount(df.format(borrowAmount));
			loanInfo.setNoOverdueAmount(df.format(noOverdueAmount));
			loanInfo.setRealOverdueAmount(df.format(realOverdueAmount));
			if (overdueAmount > 0.0) {
				loanInfo.setIsOverdue(true);
			} else {
				loanInfo.setIsOverdue(false);
			}
		}

		// if (product.getProductType() == 2) {// 分期计算服务费
		// // 总共还款金额
		// Double totalRepayMoney = NumberUtil.parseDouble(loanInfo.getAmt(), null);
		// if (totalRepayMoney == null) {
		// totalRepayMoney = sqlMapper.selectOne("select borrow_amount from bw_order
		// where id=#{orderId}",
		// orderId, Double.class);// 总共还款金额，不算利息
		// }
		// // 计算利息
		// Double interestRate = product.getInterestRate();
		// double interestAmount = DoubleUtil.mul(totalRepayMoney, interestRate);
		// loanInfo.setServiceAmount(DoubleUtil.toTwoDecimal(interestAmount));
		// repaymentCost =
		// DoubleUtil.add(Double.parseDouble(DoubleUtil.toTwoDecimal(interestAmount)),
		// repaymentCost);
		// }
		loanInfo.setAmt(DoubleUtil.toTwoDecimal(repaymentCost));
		return repaymentCost;
	}

	@Override
	public Double calcOverdueCost(Long orderId, Long repayId, LoanInfo loanInfo) {
		Double realOverdueAmount = 0.0;
		// 查询逾期
		BwOverdueRecord overdueRecord = bwOverdueRecordService.queryBwOverdueByRepayId(repayId);
		if (loanInfo == null) {
			loanInfo = new LoanInfo();
		}
		loanInfo.setOverdueDay(0);
		loanInfo.setOverdueAmount("0.00");
		loanInfo.setNoOverdueAmount("0.00");
		BwRepaymentPlan plan = bwRepaymentPlanService.selectByPrimaryKey(repayId);
		loanInfo.setRepayTime(CommUtils.convertDateToString(plan.getRepayTime(), SystemConstant.YMD_HMS));
		loanInfo.setZjwAmount(DoubleUtil.toTwoDecimal(plan.getZjw()));
		loanInfo.setRealityRepayMoney(plan.getRealityRepayMoney());
		BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
		Double borrowAmount = bwOrder.getBorrowAmount();
		Integer productId = bwOrder.getProductId();
		loanInfo.setBorrowAmount(DoubleUtil.toTwoDecimal(borrowAmount));
		if (overdueRecord == null) {
			return realOverdueAmount;
		}
		loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());

		Integer overdueDay = overdueRecord.getOverdueDay();// 逾期天数
		// 逾期利息
		Double overdueMoney = overdueRecord.getOverdueAccrualMoney();
		Double overdueCorpus = overdueRecord.getOverdueCorpus();
		Double advance = overdueRecord.getAdvance();// 后台免罚息金额
		if (advance == null || advance < 0.0) {
			advance = 0.0;
		}
		loanInfo.setOverdueDay(overdueDay);
		Integer avoidFineDate = 0;// 免罚息天数
		Integer repayStatus = plan.getRepayStatus();
		Double repayMoney = plan.getRealityRepayMoney();
		logger.info("免罚期天数为" + loanInfo.getAvoidFineDate() + "===============逾期天数为：	" + overdueDay
				+ "===============实际还款金额为：	" + repayMoney);
		boolean isOverdue = false;// 是否逾期
		if (overdueDay != null && overdueDay > 0) {
			isOverdue = true;
		}
		if (repayStatus == 2 || repayStatus == 4) {// 已还款或展期
			// 支付明细
			BwPaymentDetail paymentDetail = bwPaymentDetailService.query(repayId);
			if (paymentDetail != null) {// 新记录用新表获取金额
				realOverdueAmount = paymentDetail.getRealOverdueAmount();
				Double overdueAmount = paymentDetail.getOverdueAmount();
				Double noOverdueAmount = paymentDetail.getNoOverdueAmount();
				loanInfo.setOverdueAmount(StringUtil.isEmpty(overdueAmount) ? "0.00" : overdueAmount.toString());
				loanInfo.setNoOverdueAmount(StringUtil.isEmpty(noOverdueAmount) ? "0.00" : noOverdueAmount.toString());
			} else {// 老数据通过逾期天数计算
				if (overdueDay != null && overdueDay > 0) {
					if (overdueMoney == null || overdueMoney <= 0) {// 以前逾期清零
						double dayOverdueMoney = calculateOverdueMoney(borrowAmount, productId);
						overdueMoney = DoubleUtil.round(DoubleUtil.mul(dayOverdueMoney, overdueDay), 2);
					}
					realOverdueAmount = DoubleUtil.sub(overdueMoney, advance);
					if (realOverdueAmount < 0.0) {
						realOverdueAmount = 0.0;
					}
					loanInfo.setOverdueAmount(
							StringUtil.isEmpty(realOverdueAmount) ? "0.00" : realOverdueAmount.toString());
				}
			}
		} else {// 未还款
			if (overdueDay != null && overdueDay > 0) {
				double noOverdueAmount = 0.0;// 免罚息金额
				loanInfo.setOverdueAmount(StringUtil.isEmpty(overdueMoney) ? "0.00" : overdueMoney.toString());
				overdueMoney = DoubleUtil.sub(overdueMoney, advance);
				if (overdueMoney < 0.0) {
					overdueMoney = 0.0;
				}
				// 计算免罚息金额
				if (avoidFineDate != null && avoidFineDate > 0) {
					double dayOverdueMoney = calculateOverdueMoney(borrowAmount, productId);
					noOverdueAmount = DoubleUtil.mul(dayOverdueMoney, avoidFineDate);// 免罚息金额
					if (overdueMoney > noOverdueAmount) {// 逾期金额大于免罚息金额计算，否则真实逾期罚息0
						realOverdueAmount = DoubleUtil.sub(overdueMoney, noOverdueAmount);
					} else {
						realOverdueAmount = 0.0;
					}
				} else {
					realOverdueAmount = overdueMoney;
				}
				noOverdueAmount = DoubleUtil.add(noOverdueAmount, advance);
				loanInfo.setNoOverdueAmount(String.valueOf(noOverdueAmount));
			}
		}
		loanInfo.setIsOverdue(isOverdue);
		loanInfo.setRealOverdueAmount(StringUtil.isEmpty(realOverdueAmount) ? "0.00" : realOverdueAmount.toString());
		return StringUtil.isEmpty(realOverdueAmount) ? 0.0 : realOverdueAmount;
	}

	@Override
	public Date calcRepayTime(Date date, String term, String termType) {
		Date calcDate = null;
		if ("1".equals(termType)) {// 月
			calcDate = DateUtils.addMonths(date, Integer.parseInt(term));
		} else if ("2".equals(termType)) {// 天
			calcDate = DateUtils.addDays(date, Integer.parseInt(term));
		} else if ("3".equals(termType)) {// 周
			calcDate = DateUtils.addWeeks(date, Integer.parseInt(term));
		}
		return calcDate;
	}

	@Override
	public Date calcExtendRepayTime(Date date, String term, String termType) {
		Date calcDate = null;
		if ("1".equals(termType)) {// 月
			calcDate = DateUtils.addMonths(date, Integer.parseInt(term));
		} else if ("2".equals(termType)) {// 天
			// 30天产品展期7天，和定时器同步
			if ("30".equals(term)) {
				term = "7";
			}
			calcDate = DateUtils.addDays(date, Integer.parseInt(term));
		} else if ("3".equals(termType)) {// 周
			calcDate = DateUtils.addWeeks(date, Integer.parseInt(term));
		}
		return calcDate;
	}

	/**
	 * 根据参数编号查询参数信息
	 * 
	 */
	@Override
	public ExtraConfig getParameterInfo(String code) {
		String sql = "select ecid,code,value,describe_info,create_time,update_time from extra_config where code = '"
				+ code + "'";
		return sqlMapper.selectOne(sql, ExtraConfig.class);
	}

	@Override
	public AppResponseResult canXuDai(Long orderId) {
		AppResponseResult result = new AppResponseResult();
		BwRepaymentPlan queryPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
		if (queryPlan == null) {
			result.setCode("151");
			result.setMsg("查不到还款计划");
			return result;
		}
		BwProductDictionary product = queryByOrderId(orderId);
		if (product == null || product.getProductType() != 1) {
			logger.info("============================该产品不能展期canXuDai：orderId：" + orderId + ",product="
					+ JSON.toJSONString(product));
			result.setCode("152");
			result.setMsg("该产品不能展期");
			return result;
		}
		boolean isProcessing = bwRepaymentService.isProcessing(orderId);
		if (isProcessing) {
			logger.info("============================该工单正在处理中canXuDai：orderId：" + orderId + ",product="
					+ JSON.toJSONString(product));
			result.setCode("106");
			result.setMsg("该工单正在处理中");
			return result;
		}
		// 分批还款
		RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService.getRepaymentBatch(orderId);
		// 分批已还款金额
		Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
		if (alreadyTotalBatchMoney > 0.0) {// 已分批还款不能续贷
			result.setCode("123");
			result.setMsg("已分批还款不能展期");
			logger.info("工单" + orderId + "已分批还款不能展期");
			return result;
		}
		int termType = Integer.parseInt(product.getpTermType());// 产品类型（1：月；2：天）
		int term = Integer.parseInt(product.getpTerm());// 产品期限
		// 放款时间
		Date loanTime = queryPlan.getCreateTime();
		Date repayTime = queryPlan.getRepayTime();
		if (termType == 1) {
			loanTime = MyDateUtils.addMonths(repayTime, -term);
		} else if (termType == 2) {
			loanTime = MyDateUtils.addDays(repayTime, -term);
		}
		logger.info("============================canXuDai：orderId：" + orderId + "放款时间loanTime"
				+ CommUtils.convertDateToString(loanTime, SystemConstant.YMD_HMS) + "，还款时间："
				+ CommUtils.convertDateToString(repayTime, SystemConstant.YMD_HMS));
		// 借款日期到现在间隔时间
		int intervalDay = MyDateUtils.getDaySpace(loanTime, new Date());
		logger.info("canXuDai：orderId：" + orderId + "还款中状态下放款间隔天数============================" + intervalDay);

		// 借款多少天之后才能续贷配置
		ExtraConfig borrowDayConfig = getParameterInfo(ParameterConstant.XUDAI_AFTER_BORROW_DAY);
		int limitBorrowDay = 0;
		if (borrowDayConfig != null) {
			limitBorrowDay = NumberUtils.toInt(borrowDayConfig.getValue(), 0);
		}
		if (intervalDay < limitBorrowDay) {
			result.setCode("107");
			result.setMsg("放款" + limitBorrowDay + "天后方可进行展期！");
			return result;
		}

		// 逾期多少天之后不能续贷
		ExtraConfig overdueDayConfig = getParameterInfo(ParameterConstant.UN_XUDAI_AFTER_OVERDUE_DAY);
		int limitOverdueDay = 0;
		if (overdueDayConfig != null) {
			limitOverdueDay = NumberUtils.toInt(overdueDayConfig.getValue(), 0);
		}
		BwOverdueRecord paramOverdueRecord = new BwOverdueRecord();
		paramOverdueRecord.setOrderId(orderId);
		paramOverdueRecord.setRepayId(queryPlan.getId());
		BwOverdueRecord overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(paramOverdueRecord);
		int overdueDay = 0;// 逾期天数
		if (overdueRecord != null) {
			overdueDay = overdueRecord.getOverdueDay();
		}
		if (overdueDay > limitOverdueDay) {
			result.setCode("117");
			result.setMsg("逾期超过" + limitOverdueDay + "天以上不允许展期！");
			return result;
		}
		result.setCode("000");
		result.setMsg("SUCCESS");
		return result;
	}

	@Override
	public BwProductDictionary selectCurrentByProductType(Integer productType) {
		BwProductDictionary product = null;
		if (productType != null) {
			if (productType == 1) {// 单期
				product = getProductInfo(ProductConstant.PRODUCT_USE_NO);
			} else if (productType == 2) {// 分期
				product = getProductInfo(ProductConstant.PRODUCT_INSTALLMENT_USE_NO);
			}
		}
		return product;
	}

	@Override
	public AppResponseResult calcZhanQiCost(Long orderId) {
		AppResponseResult result = canXuDai(orderId);
		if ("151".equals(result.getCode()) || "152".equals(result.getCode())) {
			return result;
		}
		BwOrder bwOrder = bwOrderService.selectByPrimaryKey(orderId);
		Double borrowAmount = bwOrder.getBorrowAmount();
		BwProductDictionary product = queryByOrderId(orderId);
		BwRepaymentPlan repaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
		Long repayId = repaymentPlan.getId();
		// 5月13号以后续贷次数
		Integer hasAfterXudaiTerm = bwRepaymentPlanService.getXuDaiCountAfterDate(orderId);
		Double realityRepayMoney = repaymentPlan.getRealityRepayMoney();// 真实还款金额
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
		loanInfo.setAmt(realityRepayMoney.toString());
		// 展期金额
		calcXudaiCost(borrowAmount, orderId, repayId, product, hasAfterXudaiTerm + 1, loanInfo);
		result.setResult(loanInfo);
		return result;
	}

	@Override
	public AppResponseResult calcRepaymentCost(Long orderId, boolean useCoupons, Double batchMoney) {
		AppResponseResult result = new AppResponseResult();
		result.setCode("000");
		BwOrder bwOrder = bwOrderService.selectByPrimaryKey(orderId);

		if (bwOrder == null) {
			result.setCode("151");
			result.setMsg("工单不存在");
			return result;
		}
		BwProductDictionary product = queryByOrderId(orderId);
		if (product == null) {
			logger.info("============================没有产品信息calcRepaymentCost：orderId：" + orderId + ",product="
					+ JSON.toJSONString(product));
			result.setCode("152");
			result.setMsg("没有产品信息");
			return result;
		}

		boolean isProcessing = bwRepaymentService.isProcessing(orderId);
		if (isProcessing) {
			logger.info("============================该工单正在处理中calcRepaymentCost：orderId：" + orderId + ",product="
					+ JSON.toJSONString(product));
			result.setCode("106");
			result.setMsg("该工单正在处理中");
		}

		Integer productType = product.getProductType();
		List<BwRepaymentPlan> allPlanList = bwRepaymentPlanService.selectNotZhanqiPlan(orderId, productType);
		// 未还款还款计划，根据还款时间升序排序
		List<BwRepaymentPlan> notRepayPlanList = new ArrayList<BwRepaymentPlan>();
		Double totalRepayMoney = bwOrder.getBorrowAmount();// 总共还款金额，不算利息
		double totalAmount = 0.0;// 总共应还金额
		Map<Long, LoanInfo> loanInfoMap = new HashMap<Long, LoanInfo>();
		for (BwRepaymentPlan plan : allPlanList) {
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
			loanInfo.setAmt(totalRepayMoney.toString());
			Double realityRepayMoney = plan.getRealityRepayMoney();
			Long repayId = plan.getId();
			Integer repayStatus = plan.getRepayStatus();
			calcRepaymentCost(realityRepayMoney, orderId, repayId, product, loanInfo);
			double realOverdueAmount = NumberUtil.parseDouble(loanInfo.getRealOverdueAmount(), 0.0);
			totalAmount = DoubleUtil.add(DoubleUtil.add(totalAmount, realityRepayMoney), realOverdueAmount);// 总共还款金额
			loanInfoMap.put(repayId, loanInfo);
			if (repayStatus == 1 || repayStatus == 3) {// 未还款
				notRepayPlanList.add(plan);
			}
		}

		// 分批还款
		RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService.getRepaymentBatch(orderId);
		Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
		// 总共剩余还款金额
		double totalLeftAmount = DoubleUtil.sub(totalAmount, alreadyTotalBatchMoney);
		if (alreadyTotalBatchMoney == null) {
			alreadyTotalBatchMoney = 0.0;
		}
		if (alreadyTotalBatchMoney > 0.0 || batchMoney < totalLeftAmount) {// 已分批或分批还款不使用优惠券
			useCoupons = false;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalAmount", totalAmount);// 总共需要还款金额(包括已还款，不算优惠券)
		resultMap.put("totalLeftAmount", totalLeftAmount);// 总共剩余还款金额
		resultMap.put("alreadyTotalBatchMoney", alreadyTotalBatchMoney);// 已还款金额
		double totalUseCouponsAmount = totalAmount;
		if (useCoupons) {
			totalUseCouponsAmount = NumberUtil.parseDouble(
					activityService.getRealityTrandeAmount("1", totalAmount + "", bwOrder.getBorrowerId()),
					totalAmount);
		}
		resultMap.put("totalUseCouponsAmount", totalUseCouponsAmount);// 使用优惠券后总金额
		resultMap.put("useCoupons", useCoupons);// 是否使用优惠券

		// 最少分期还款金额
		String minBatchRepaymentAmountStr = extraConfigService
				.findCountExtraConfigByCode(ParameterConstant.MIN_BATCH_REPAYMENT_AMOUNT);
		double minBatchRepaymentAmount = NumberUtils.toDouble(minBatchRepaymentAmountStr, 0.0);// 100
		if (batchMoney > totalLeftAmount) {
			result.setCode("121");
			result.setMsg("还款金额不能大于剩余还款金额");
		} else if (minBatchRepaymentAmount >= totalLeftAmount && batchMoney < totalLeftAmount) {// 剩余还款金额小于最小金额，且还款金额小于剩余还款金额
			result.setCode("122");
			result.setMsg("需一次还完剩下的欠款！");
		} else if (batchMoney < totalLeftAmount && batchMoney < minBatchRepaymentAmount) {// 非最后一次还款，且还款金额小于最小还款金额
			result.setCode("122");
			result.setMsg("每次还款金额不能低于" + minBatchRepaymentAmountStr + "元");
		}
		result.setResult(resultMap);
		return result;
	}

	@Override
	public AppResponseResult calcRepaymentCost(Long orderId) {
		AppResponseResult result = new AppResponseResult();
		result.setCode("000");
		BwOrder bwOrder = bwOrderService.selectByPrimaryKey(orderId);

		if (bwOrder == null) {
			result.setCode("151");
			result.setMsg("工单不存在");
			return result;
		}
		BwProductDictionary product = queryByOrderId(orderId);
		if (product == null) {
			logger.info("============================没有产品信息calcRepaymentCost：orderId：" + orderId + ",product="
					+ JSON.toJSONString(product));
			result.setCode("152");
			result.setMsg("没有产品信息");
			return result;
		}

		boolean isProcessing = bwRepaymentService.isProcessing(orderId);
		if (isProcessing) {
			logger.info("============================该工单正在处理中calcRepaymentCost：orderId：" + orderId + ",product="
					+ JSON.toJSONString(product));
			result.setCode("106");
			result.setMsg("该工单正在处理中");
		}

		Integer productType = product.getProductType();
		List<BwRepaymentPlan> allPlanList = bwRepaymentPlanService.selectNotZhanqiPlan(orderId, productType);
		// 未还款还款计划，根据还款时间升序排序
		List<BwRepaymentPlan> notRepayPlanList = new ArrayList<BwRepaymentPlan>();
		Double totalRepayMoney = bwOrder.getBorrowAmount();// 总共还款金额，不算利息
		double totalAmount = 0.0;// 总共应还金额
		double firstTotalLeftAmount = 0.0;// 最早一期未还款还款计划剩余还款金额
		Map<Long, LoanInfo> loanInfoMap = new HashMap<Long, LoanInfo>();
		boolean firstPlan = true;
		for (BwRepaymentPlan plan : allPlanList) {
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
			loanInfo.setAmt(totalRepayMoney.toString());
			Double realityRepayMoney = plan.getRealityRepayMoney();
			Double alreadyRepayMoney = plan.getAlreadyRepayMoney();
			if (alreadyRepayMoney == null || alreadyRepayMoney <= 0) {
				alreadyRepayMoney = 0.0;
			}
			Long repayId = plan.getId();
			Integer repayStatus = plan.getRepayStatus();
			calcRepaymentCost(realityRepayMoney, orderId, repayId, product, loanInfo);
			double realOverdueAmount = NumberUtil.parseDouble(loanInfo.getRealOverdueAmount(), 0.0);
			totalAmount = DoubleUtil.add(DoubleUtil.add(totalAmount, realityRepayMoney), realOverdueAmount);// 总共还款金额
			loanInfoMap.put(repayId, loanInfo);
			if (repayStatus == 1 || repayStatus == 3) {// 未还款
				notRepayPlanList.add(plan);
			}
			if (firstPlan && repayStatus != 2 && repayStatus != 4) {
				firstPlan = false;
				firstTotalLeftAmount = DoubleUtil.add(realityRepayMoney, realOverdueAmount);
				firstTotalLeftAmount = DoubleUtil.sub(firstTotalLeftAmount, alreadyRepayMoney);
			}
		}

		// 分批还款
		RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService.getRepaymentBatch(orderId);
		Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
		// 总共剩余还款金额
		double totalLeftAmount = DoubleUtil.sub(totalAmount, alreadyTotalBatchMoney);
		if (alreadyTotalBatchMoney == null) {
			alreadyTotalBatchMoney = 0.0;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalAmount", totalAmount);// 总共需要还款金额(包括已还款，不算优惠券)
		resultMap.put("firstTotalLeftAmount", firstTotalLeftAmount);// 最早一期未还款还款计划剩余还款金额
		resultMap.put("totalLeftAmount", totalLeftAmount);// 总共剩余还款金额
		resultMap.put("alreadyTotalBatchMoney", alreadyTotalBatchMoney);// 已还款金额
		double totalUseCouponsAmount = totalAmount;
		resultMap.put("totalUseCouponsAmount", totalUseCouponsAmount);// 使用优惠券后总金额

		// 最少分期还款金额
		String minBatchRepaymentAmountStr = extraConfigService
				.findCountExtraConfigByCode(ParameterConstant.MIN_BATCH_REPAYMENT_AMOUNT);
		double minBatchRepaymentAmount = NumberUtils.toDouble(minBatchRepaymentAmountStr, 0.0);// 100
		result.setResult(resultMap);
		return result;
	}

	/**
	 * 计算湛江委扣除金额
	 * 
	 * @param calcAmount
	 * @param product
	 * @return
	 */
	@Override
	public double calcZjwAmount(double calcAmount, BwProductDictionary product) {
		double zjwAmount = 0.0;
		// 单期湛江委3%手续费
		if (product.getProductType() == 1) {
			Double zjwCost = product.getZjwCost();
			if (zjwCost == null || zjwCost < 0.0) {
				zjwCost = 0.0;
			}
			zjwAmount = DoubleUtil.round(DoubleUtil.mul(calcAmount, zjwCost), 2);
		}
		return zjwAmount;
	}

	@Override
	public ProductFeeDto queryProductFee(Double borrowAmount, BwProductDictionary product) {
		if (product == null || borrowAmount == null) {
			return null;
		}
		Double ratePreService = product.getRatePreService();// 贷前咨询服务费率
		Double rateAfterLoan = product.getRateAfterLoan();// 贷后信用管理费率
		Double capitalUseCost = product.getpCapitalUseCost();// 信息服务费率
		Double rateFundUtilization = product.getRateFundUtilization();// 资金使用费率
		Double rateOverdue = product.getRateOverdue();// 逾期费率
		Double interestRate = product.getInterestRate();// 分期利息
		Double preServiceFee = DoubleUtil.mul(borrowAmount, ratePreService);
		Double afterLoanFee = DoubleUtil.mul(borrowAmount, rateAfterLoan);
		Double capitalUseFee = DoubleUtil.mul(borrowAmount, capitalUseCost);
		Double fundUtilizationFee = DoubleUtil.mul(borrowAmount, rateFundUtilization);
		ProductFeeDto productFeeDto = new ProductFeeDto();
		productFeeDto.setPreServiceFee(preServiceFee);
		productFeeDto.setAfterLoanFee(afterLoanFee);
		productFeeDto.setCapitalUseFee(capitalUseFee);
		productFeeDto.setFundUtilizationFee(fundUtilizationFee);
		productFeeDto.setOverdueFee(DoubleUtil.mul(borrowAmount, rateOverdue));
		productFeeDto.setInterest(DoubleUtil.round(DoubleUtil.mul(borrowAmount, interestRate), 2));
		// 借款工本费
		Double loanAmount = DoubleUtil.add(DoubleUtil.add(preServiceFee, afterLoanFee), capitalUseFee);
		// 到账金额
		Double arrivelAmount = DoubleUtil.sub(borrowAmount, loanAmount);
		productFeeDto.setLoanAmount(loanAmount);
		productFeeDto.setArrivalAmount(arrivelAmount);
		return productFeeDto;
	}

	@Override
	public PaymentRespDto getNeedPaymentInfo(Long orderId) {
		BwOrder bwOrder = bwOrderService.findBwOrderById(orderId + "");
		PaymentRespDto paymentRespDto = new PaymentRespDto();
		if (bwOrder == null || bwOrder.getBorrowAmount() == null || bwOrder.getBorrowAmount() == 0.0) {
			paymentRespDto.setOverdue(false);
			paymentRespDto.setTotalLeftRepaymentAmount(0.0);
			paymentRespDto.setTotalRepaymentAmount(0.0);
			paymentRespDto.setRealityRepayMoney(0.0);
			paymentRespDto.setPaymentFee(0.0);
			paymentRespDto.setArrivelAmount(0.0);
			paymentRespDto.setBorrowAmount(0.0);
			paymentRespDto.setAlreadyTotalBatchMoney(0.0);
			paymentRespDto.setRealOverdueAmount(0.0);
			return paymentRespDto;
		}
		Integer productId = bwOrder.getProductId();
		Double borrowAmount = bwOrder.getBorrowAmount();
		Integer productType = bwOrder.getProductType();// 1.单期 2.分期
		BwProductDictionary product = bwProductDictionaryService.findBwProductDictionaryById(productId);
		RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService.getRepaymentBatch(orderId);
		// 已还金额
		Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
		ProductFeeDto productFeeDto = queryProductFee(borrowAmount, product);
		paymentRespDto.setOverdue(false);
		paymentRespDto.setAlreadyTotalBatchMoney(alreadyTotalBatchMoney);
		paymentRespDto.setBorrowAmount(borrowAmount);
		paymentRespDto.setArrivelAmount(productFeeDto.getArrivalAmount());
		paymentRespDto.setPaymentFee(productFeeDto.getLoanAmount());
		// 总还款金额，包含湛江委金额，多期则所有还款计划相加，不算逾期金额
		double totalRealityRepayMoney = 0.0;
		// 还款总额，真实还款金额(包含湛江委金额)+逾期金额-免罚息金额
		double totalRepaymentAmount = 0.0;
		// 实际逾期罚息金额
		double totalRealOverdueAmount = 0.0;
		List<BwRepaymentPlan> planList = bwRepaymentPlanService.selectNotZhanqiPlan(orderId, productType);
		if (planList != null && !planList.isEmpty() && planList.get(0) != null) {
			for (BwRepaymentPlan plan : planList) {
				totalRealityRepayMoney = DoubleUtil.add(totalRealityRepayMoney, plan.getRealityRepayMoney());
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
				Double realityRepayMoney = plan.getRealityRepayMoney();
				Long repayId = plan.getId();
				calcRepaymentCost(realityRepayMoney, orderId, repayId, product, loanInfo);
				double realOverdueAmount = NumberUtil.parseDouble(loanInfo.getRealOverdueAmount(), 0.0);
				totalRepaymentAmount = DoubleUtil.add(DoubleUtil.add(totalRepaymentAmount, realityRepayMoney), realOverdueAmount);// 总共还款金额
				Integer repayStatus = plan.getRepayStatus();
				if (repayStatus != 2 && repayStatus != 4) {
					totalRealOverdueAmount = DoubleUtil.add(totalRealOverdueAmount, realOverdueAmount);
				}
				Boolean isOverdue = loanInfo.getIsOverdue();
				if (isOverdue != null && isOverdue) {
					paymentRespDto.setOverdue(isOverdue);
				}
			}
		} else {// 无还款计划
			// totalRealityRepayMoney  totalRepaymentAmount
			Integer borrowNumber = bwOrder.getBorrowNumber();
			// 资金使用费
			Double fundUtilizationFee = productFeeDto.getFundUtilizationFee();
			if (borrowNumber != null) {
				fundUtilizationFee = DoubleUtil.mul(fundUtilizationFee, borrowNumber);
			}
			Double interest = productFeeDto.getInterest();// 利息
			if (borrowNumber != null) {
				interest = DoubleUtil.mul(interest, borrowNumber);
			}
			totalRealityRepayMoney = DoubleUtil.add(borrowAmount, fundUtilizationFee);
			totalRealityRepayMoney = DoubleUtil.add(totalRealityRepayMoney, interest);
		}
		// 总共剩余还款金额，真实还款金额(包含湛江委金额)+(逾期金额-免罚息金额)-已还金额
		double totalLeftRepaymentAmount = DoubleUtil.sub(totalRepaymentAmount, alreadyTotalBatchMoney);
		paymentRespDto.setRealityRepayMoney(totalRealityRepayMoney);
		paymentRespDto.setTotalRepaymentAmount(totalRepaymentAmount);
		paymentRespDto.setTotalLeftRepaymentAmount(totalLeftRepaymentAmount);
		paymentRespDto.setRealOverdueAmount(totalRealOverdueAmount);
		return paymentRespDto;
	}

	private String getTermStr(BwProductDictionary product) {
		String termStr = "";
		if (product != null) {
			String term = product.getpTerm();
			String termType = product.getpTermType();
			if ("1".equals(termType)) {
				termStr = term + "月";
			} else if ("2".equals(termType)) {
				termStr = term + "天";
			} else if ("3".equals(termType)) {
				termStr = term + "周";
			}
		}
		return termStr;
	}

	private double calculateOverdueMoney(double borrowAmount, long productId) {
		BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(productId);
		Double rateOverdue = bwProductDictionary.getRateOverdue();
		if (rateOverdue == null) {
			rateOverdue = 0.01;
		}
		return DoubleUtil.round(DoubleUtil.mul(borrowAmount, rateOverdue), 2);
	}
}