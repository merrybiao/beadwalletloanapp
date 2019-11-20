package com.waterelephant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.entity.lianlian.NotifyResult;
import com.beadwallet.service.serve.BeadWalletHaoDaiService;
import com.waterelephant.activity.service.ActivityService;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.*;
import com.waterelephant.entity.*;
import com.waterelephant.service.*;
import com.waterelephant.utils.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.*;

@Service
public class BwRepaymentPlanService extends BaseService<BwRepaymentPlan, Long> implements IBwRepaymentPlanService {
	private Logger logger = Logger.getLogger(BwRepaymentPlanService.class);
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwOrderStatusRecordService bwOrderStatusRecordService;
	@Autowired
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private BwPaymentDetailService bwPaymentDetailService;
	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private ProductService productService;
	@Autowired
	private IBwRepaymentService bwRepaymentService;

	@Override
	public BwRepaymentPlan findBwRepaymentPlanByAttr(BwRepaymentPlan bwRepaymentPlan) {

		return mapper.selectOne(bwRepaymentPlan);
	}

	@Override
	public List<BwRepaymentPlan> findBwRepaymentPlanByRepay(BwRepaymentPlan bwRepaymentPlan) {

		return mapper.select(bwRepaymentPlan);
	}

	@Override
	public PageResponseVo<BwRepaymentPlan> findBwRepaymentPlanPage(PageRequestVo pageRequestVo) {

		return page(pageRequestVo);
	}

	@Override
	public List<BwRepaymentPlan> findBwRepaymentPlanByExample(Example example) {
		return mapper.selectByExample(example);
	}

	@Override
	public Map<String, Object> sumCorpusAndAccrualByOrderId(Long orderId) {
		// String sql = "SELECT SUM(p.repay_accrual_money) as accrual,
		// SUM(p.repay_corpus_money) as corpus FROM
		// bw_repayment_plan p WHERE p.order_id= #{orderId} AND p.repay_status = 2";
		String sql = "SELECT sum(p.reality_repay_money) as accrual, 0 as corpus FROM bw_repayment_plan p WHERE p.order_id= #{orderId} AND p.repay_status = 2";
		return sqlMapper.selectOne(sql, orderId);
	}

	@Override
	public List<Map<String, Object>> findBwRepaymentPlanByOrderId(Long orderId) {
		String sql = "SELECT o.repay_term,o.borrow_amount,p.* FROM bw_order o LEFT JOIN bw_repayment_plan p "
				+ "ON o.id = p.order_id " + "WHERE p.order_id = #{orderId} " + "AND p.repay_status in(1,3) "
				+ "ORDER BY p.create_time DESC ";
		return sqlMapper.selectList(sql, orderId);
	}

	@Override
	public JSONObject findRepaymentPlanInfoByOrderId(Long orderId, boolean calcCoupon) {
		String sql = "SELECT o.borrower_id,bpd.p_term term,bpd.p_term_type term_type,o.borrow_amount,p.* FROM bw_order o LEFT JOIN bw_repayment_plan p "
				+ "ON o.id = p.order_id LEFT JOIN bw_product_dictionary bpd on o.product_id=bpd.id "
				+ "WHERE p.order_id = #{orderId} " + "AND p.repay_status in(1,3) " + "ORDER BY p.create_time DESC";
		List<Map<String, Object>> planInfoList = sqlMapper.selectList(sql, orderId);
		if (planInfoList != null && !planInfoList.isEmpty()) {
			// 银行卡信息
			BwBankCard bwBankCard = sqlMapper.selectOne(
					"select b.* from bw_bank_card b left join bw_order o on b.borrower_id=o.borrower_id where o.id="
							+ orderId + " order by b.create_time desc limit 1",
					BwBankCard.class);
			// 逾期记录
			BwOverdueRecord overdueRecord = sqlMapper.selectOne(
					"select * from bw_overdue_record where order_id=" + orderId + " ORDER BY repay_time desc limit 1",
					BwOverdueRecord.class);
			for (Map<String, Object> planMap : planInfoList) {
				int borrowId = NumberUtils.toInt(planMap.get("borrower_id").toString(), 0);
				ActivityDiscountDistribute paramActivity = new ActivityDiscountDistribute();
				paramActivity.setBorrowId(borrowId);
				String cardNo = bwBankCard.getCardNo();
				// 取银行卡号后四位
				planMap.put("cardNoEnd", cardNo.substring(cardNo.length() - 4));
				planMap.put("bankName", RedisUtils.hget(RedisKeyConstant.FUIOU_BANK, bwBankCard.getBankCode()));
				planMap.put("couponAmount", 0.0);// 现金券优惠金额
				// 最后需要还款金额
				double realRepayMoney = NumberUtils.toDouble(planMap.get("borrow_amount").toString(), 0.0);
				// 计算逾期金额
				if (overdueRecord != null) {
					Integer overdueDay = overdueRecord.getOverdueDay();// 逾期天数
					Double overdueAccrualMoney = overdueRecord.getOverdueAccrualMoney();// 逾期利息
					realRepayMoney = DoubleUtil.add(realRepayMoney, overdueAccrualMoney);
					planMap.put("overdueDay", overdueDay);
					planMap.put("overdueAccrualMoney", overdueAccrualMoney);
				} else {
					planMap.put("overdueDay", 0);
					planMap.put("overdueAccrualMoney", 0.0);
				}
				if (Integer.parseInt(planMap.get("overdueDay").toString()) > 0) {
					planMap.put("isOverdue", true);
				} else {
					planMap.put("isOverdue", false);
				}

				// 最大可使用优惠券
				Object maxCoupon = activityService.getMaxCoupon(paramActivity);
				planMap.put("hasMaxCoupon", false);
				if (maxCoupon != null) {// 有可用优惠券
					planMap.put("hasMaxCoupon", true);
					if (calcCoupon) {
						JSONObject maxCouponJson = JSON.parseObject(maxCoupon.toString());
						Double couponAmount = maxCouponJson.getDouble("amount");
						if (couponAmount != null) {
							realRepayMoney = DoubleUtil.sub(realRepayMoney, couponAmount);
							planMap.put("couponAmount", couponAmount);
						}
					}
				}
				planMap.put("realRepayMoney", realRepayMoney);

			}
		}
		// 组装返回数据
		JSONObject resultJson = new JSONObject();
		if (planInfoList != null && !planInfoList.isEmpty()) {
			Map<String, Object> map = planInfoList.get(0);
			resultJson.put("borrowerId", map.get("borrower_id"));
			resultJson.put("term", map.get("term"));
			resultJson.put("termType", map.get("term_type"));
			resultJson.put("borrowAmount", map.get("borrow_amount"));
			resultJson.put("cardNoEnd", map.get("cardNoEnd"));
			resultJson.put("bankName", map.get("bankName"));
			resultJson.put("hasMaxCoupon", map.get("hasMaxCoupon"));
			resultJson.put("couponAmount", map.get("couponAmount"));
			resultJson.put("isOverdue", map.get("isOverdue"));
			resultJson.put("overdueDay", map.get("overdueDay"));
			resultJson.put("overdueAccrualMoney", map.get("overdueAccrualMoney"));
			resultJson.put("realRepayMoney", map.get("realRepayMoney"));
			resultJson.put("repayMoney", map.get("repay_money"));
			resultJson.put("realityRepayMoney", map.get("reality_repay_money"));
			resultJson.put("repayType", map.get("repay_type"));
			resultJson.put("repayStatus", map.get("repay_status"));
			resultJson.put("repayIsCorpus", map.get("repay_is_corpus"));
			resultJson.put("repayCorpusMoney", map.get("repay_corpus_money"));
			resultJson.put("repayAccrualMoney", map.get("repay_accrual_money"));
			resultJson.put("number", map.get("number"));
			resultJson.put("repayTime", map.get("repay_time"));
			resultJson.put("createTime", map.get("create_time"));
			resultJson.put("updateTime", map.get("update_time"));
		}
		return resultJson;
	}

	@Override
	public Map<String, Object> sumRepayMoneyByOrderIdAndStatusId(Long orderId, int repayStatus) {
		String sql = "select sum(br.reality_repay_money) as repayMoney from bw_repayment_plan br where br.repay_status = ${repayStatus} and br.order_id = #{orderId} ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repayStatus", repayStatus);
		map.put("orderId", orderId);
		return sqlMapper.selectOne(sql, map);
	}

	/**
	 * 根据工单信息生成借款人还款计划
	 * 
	 * @param order
	 */
	@Override
	public void saveRepaymentPlanByOrder(BwOrder order) {
		Double realityRepayMoney = 0D; // 实际还款金额
		BwProductDictionary bwProductDictionary = null;
		// 如果工单的产品Id不为空
		if (order.getProductId() != null) {
			// 查询工单的产品
			bwProductDictionary = bwProductDictionaryService.findById(Long.parseLong(order.getProductId().toString()));
		}
		// 先息后本
		if (1 == order.getRepayType()) {
			// 给当月投资人的总利息 = 借款金额*借款月利率
			double rMoney = order.getBorrowAmount() * order.getBorrowRate();
			// 平息差 = 实际还款金额 - 借款金额
			double tempMoney = order.getContractAmount() - order.getBorrowAmount();
			// 生成还款计划
			for (Short i = 0; i < order.getRepayTerm(); i++) {
				// 还款计划对象
				BwRepaymentPlan bw = new BwRepaymentPlan();
				bw.setOrderId(order.getId());
				// 按月算
				Date repayTime = MyDateUtils.addMonths(order.getUpdateTime(), i + 1);
				bw.setRepayTime(repayTime);// 每一期还款时间
				// 合同月利率
				double tareatyBorrowMonthlyRate = order.getContractRate();
				// o=平息差 *合同月利率*(1+合同月利率)^(期数-1)
				double o = tempMoney * tareatyBorrowMonthlyRate * Math.pow((1 + tareatyBorrowMonthlyRate), i);
				// t= (1+利率)^期数+1
				double t = Math.pow((1 + tareatyBorrowMonthlyRate), order.getRepayTerm()) - 1;
				double cMoney = Double.valueOf(String.format("%.2f", o / t));// 当期总本金
				double aMoney = rMoney - cMoney;// 当期利息
				if ((i + 1) == order.getRepayTerm()) {
					// 最后一期加上本金
					bw.setRepayMoney(rMoney + order.getBorrowAmount());// 还款金额
					bw.setRepayCorpusMoney(cMoney + order.getBorrowAmount());// 还款本金
					bw.setRepayAccrualMoney(aMoney);// 还款利息
				} else {
					bw.setRepayMoney(rMoney);// 还款金额
					bw.setRepayCorpusMoney(cMoney);// 还款本金
					bw.setRepayAccrualMoney(aMoney);// 还款利息
				}
				bw.setCreateTime(new Date());
				bw.setNumber(i + 1);
				bw.setRepayStatus(1);
				bw.setRepayType(1);
				bw.setRepayIsCorpus(2);
				// 初始化实际还款金额为还款金额
				// bw.setRealityRepayMoney(bw.getRepayMoney());
				if (bwProductDictionary != null) {
					// 如果还款期限为一个月
					if (bwProductDictionary.getpTerm().equals("1")) {
						if (bwProductDictionary.getId() == 2) {
							// 实际还款金额=借款金额*借款月利率（0.09）
							realityRepayMoney = bwProductDictionary.getpInvestRateMonth() * order.getBorrowAmount();
							bw.setRealityRepayMoney(bw.getRepayMoney() - realityRepayMoney);
						} else {
							bw.setRealityRepayMoney(bw.getRepayMoney());
						}
					}
				}
				mapper.insert(bw);
			}
		} else {
			// 等额本息
			// 给所有投资人的本金加利息 = 借款金额/借款期数 + 借款金额*借款月利率
			double rMoney = order.getBorrowAmount() / order.getRepayTerm()
					+ order.getBorrowAmount() * order.getBorrowRate();// 给所有投资人的本金加利息
			// 等额本息
			for (Short i = 0; i < order.getRepayTerm(); i++) {
				// 还款计划对象
				BwRepaymentPlan bw = new BwRepaymentPlan();
				bw.setOrderId(order.getId());
				// 按月算
				Date repayTime = MyDateUtils.addMonths(order.getUpdateTime(), i + 1);
				bw.setRepayTime(repayTime);// 每一期还款时间
				// 合同月利率
				double tareatyBorrowMonthlyRate = order.getContractRate();
				// o = 还款金额*合同月利率*(1+合同月利率)^(期数-1)
				double o = order.getContractAmount() * tareatyBorrowMonthlyRate
						* Math.pow((1 + tareatyBorrowMonthlyRate), i);
				// t = (1+合同月利率)^期数-1
				double t = Math.pow((1 + tareatyBorrowMonthlyRate), order.getRepayTerm()) - 1;
				double cMoney = Double.valueOf(String.format("%.2f", o / t));// 当月给所有投资人的本金
				double aMoney = rMoney - cMoney;// 利息
				bw.setRepayMoney(rMoney);// 还款金额
				bw.setRepayCorpusMoney(cMoney);// 还款本金
				bw.setRepayAccrualMoney(aMoney);// 还款利息
				bw.setCreateTime(new Date());
				bw.setNumber(i + 1);
				bw.setRepayStatus(1);
				bw.setRepayType(1);
				bw.setRepayIsCorpus(2);
				// 初始化实际还款金额为还款金额
				// bw.setRealityRepayMoney(bw.getRepayMoney());
				if (bwProductDictionary != null) {
					// 如果还款期限为一个月
					if (bwProductDictionary.getpTerm().equals("1")) {
						if (bwProductDictionary.getId() == 2) {
							// 实际还款金额=借款金额*借款月利率（0.09）
							realityRepayMoney = bwProductDictionary.getpInvestRateMonth() * order.getBorrowAmount();
							bw.setRealityRepayMoney(bw.getRepayMoney() - realityRepayMoney);
						} else {
							bw.setRealityRepayMoney(bw.getRepayMoney());
						}
					}
				}
				mapper.insert(bw);
			}

		}
		// 更新时间，修改工单状态
		order.setUpdateTime(new Date());
		order.setStatusId(9L);
		bwOrderService.updateBwOrder(order);
	}

	/**
	 * 根据工单信息和指定还款时间生成借款人还款计划
	 * 
	 * @param order 工单信息
	 * @param repayTime 还款日期
	 */
	@Override
	public void saveRepaymentPlanByOrderAndReapyDate(BwOrder order, Date repayTime) {
		// 生成还款计划
		BwRepaymentPlan bw = new BwRepaymentPlan();
		bw.setOrderId(order.getId());// 工单ID
		bw.setRepayTime(repayTime);// 还款时间
		// 先息后本
		// 给投资人的总利息 = 借款金额*借款月利率
		double rMoney = order.getBorrowAmount() * order.getBorrowRate();
		// 平息差 = 实际还款金额 - 借款金额
		double tempMoney = order.getContractAmount() - order.getBorrowAmount();
		// 合同月利率
		double tareatyBorrowMonthlyRate = order.getContractRate();
		// o=平息差 *合同月利率*(1+合同月利率)^(期数-1)
		double o = tempMoney * tareatyBorrowMonthlyRate * Math.pow((1 + tareatyBorrowMonthlyRate), 0);
		// t= (1+利率)^期数+1
		double t = Math.pow((1 + tareatyBorrowMonthlyRate), order.getRepayTerm()) - 1;
		double cMoney = Double.valueOf(String.format("%.2f", o / t));// 当期总本金
		double aMoney = rMoney - cMoney;// 当期利息
		// 最后一期加上本金
		bw.setRepayMoney(rMoney + order.getBorrowAmount());// 还款金额
		bw.setRepayCorpusMoney(cMoney + order.getBorrowAmount());// 还款本金
		bw.setRepayAccrualMoney(aMoney);// 还款利息
		bw.setCreateTime(new Date());
		bw.setNumber(1);
		bw.setRepayStatus(1);
		bw.setRepayType(1);
		bw.setRepayIsCorpus(2);
		// 初始化实际还款金额为还款金额
		// bw.setRealityRepayMoney(bw.getRepayMoney());
		Double realityRepayMoney = 0D; // 实际还款金额
		BwProductDictionary bwProductDictionary = null;
		// 如果工单的产品Id不为空
		if (order.getProductId() != null) {
			// 查询工单的产品
			bwProductDictionary = bwProductDictionaryService.findById(Long.parseLong(order.getProductId().toString()));
		}
		if (bwProductDictionary != null) {
			// 如果还款期限为一个月
			if (bwProductDictionary.getpTerm().equals("1")) {
				if (bwProductDictionary.getId() == 2) {
					// 实际还款金额=借款金额*借款月利率（0.09）
					realityRepayMoney = bwProductDictionary.getpInvestRateMonth() * order.getBorrowAmount();
					bw.setRealityRepayMoney(bw.getRepayMoney() - realityRepayMoney);
				} else {
					bw.setRealityRepayMoney(bw.getRepayMoney());
				}
			}
		}
		mapper.insert(bw);
		// 更新时间，修改工单状态
		order.setUpdateTime(new Date());
		order.setStatusId(9L);
		bwOrderService.updateBwOrder(order);
	}

	/**
	 * 根据工单信息和指定还款时间生成借款人还款计划
	 * 
	 * @param order 工单信息
	 * @param repayTime 还款日期
	 */
	@Override
	public void saveRepaymentPlanByOrderAndReapyDateNew(BwOrder order, Date repayTime,
			BwProductDictionary bwProductDictionary) {
		// 生成还款计划
		BwRepaymentPlan bw = new BwRepaymentPlan();
		bw.setOrderId(order.getId());// 工单ID
		bw.setRepayTime(repayTime);// 还款时间
		// // 先息后本
		// // 给投资人的总利息 = 借款金额*借款月利率
		// double rMoney = order.getBorrowAmount() * order.getBorrowRate();
		// // 平息差 = 实际还款金额 - 借款金额
		// double tempMoney = order.getContractAmount()
		// - order.getBorrowAmount();
		// // 合同月利率
		// double tareatyBorrowMonthlyRate = order.getContractRate();
		// // o=平息差 *合同月利率*(1+合同月利率)^(期数-1)
		// double o = tempMoney * tareatyBorrowMonthlyRate
		// * Math.pow((1 + tareatyBorrowMonthlyRate), 0);
		// // t= (1+利率)^期数+1
		// double t = Math.pow((1 + tareatyBorrowMonthlyRate),
		// order.getRepayTerm()) - 1;
		// double cMoney = Double.valueOf(String.format("%.2f", o / t));// 当期总本金
		// double aMoney = rMoney - cMoney;// 当期利息
		// // 最后一期加上本金

		// 湛江仲裁费用
		double zjzcMoney = 0.00;
		if (bwProductDictionary != null) {
			zjzcMoney = order.getBorrowAmount() * bwProductDictionary.getZjwCost();
		}

		bw.setRepayMoney(order.getBorrowAmount());// 还款金额
		bw.setRepayCorpusMoney(order.getBorrowAmount());// 还款本金
		bw.setRepayAccrualMoney(0D);// 还款利息
		bw.setCreateTime(new Date());
		bw.setNumber(1);
		bw.setRepayStatus(1);
		bw.setRepayType(1);
		bw.setRepayIsCorpus(2);
		// 初始化实际还款金额为还款金额
		bw.setRealityRepayMoney(bw.getRepayMoney() + zjzcMoney);
		bw.setRolloverNumber(0);
		bw.setZjw(zjzcMoney);
		// Double realityRepayMoney = 0D; //实际还款金额
		// BwProductDictionary bwProductDictionary = null;
		// //如果工单的产品Id不为空
		// if(order.getProductId()!=null){
		// //查询工单的产品
		// bwProductDictionary =
		// bwProductDictionaryService.findById(Long.parseLong(order.getProductId().toString()));
		// }
		// if(bwProductDictionary!=null){
		// //如果还款期限为一个月
		// if(bwProductDictionary.getpTerm().equals("1")){
		// if(bwProductDictionary.getId()==2){
		// //实际还款金额=借款金额*借款月利率（0.09）
		// realityRepayMoney=bwProductDictionary.getpInvestRateMonth()*order.getBorrowAmount();
		// bw.setRealityRepayMoney(bw.getRepayMoney()-realityRepayMoney);
		// }else{
		// bw.setRealityRepayMoney(bw.getRepayMoney());
		// }
		// }
		// }
		mapper.insert(bw);
		// 更新时间，修改工单状态
		order.setUpdateTime(new Date());
		order.setStatusId(9L);
		bwOrderService.updateBwOrder(order);
	}

	public int addBwRepaymentPlan(BwRepaymentPlan bwRepaymentPlan) {

		return mapper.insert(bwRepaymentPlan);
	}

	@Override
	public BwRepaymentPlan getPlan(Long orderId, int num) {
		Example example = new Example(BwRepaymentPlan.class);
		example.setOrderByClause("repay_time DESC");
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orderId", orderId);
		criteria.andEqualTo("number", num);
		List<BwRepaymentPlan> list = mapper.selectByExample(example);
		BwRepaymentPlan plan = null;
		if (list != null && !list.isEmpty()) {
			plan = list.get(0);
		}
		return plan;
	}

	@Override
	public List<BwRepaymentPlan> findRepaymentPlanByExample(Example example) {
		return mapper.selectByExample(example);
	}

	@Override
	public BwRepaymentPlan getBwRepaymentPlanByOrderId(Long orderId) {
		String sql = "select r.* from bw_repayment_plan r where r.order_id=#{orderId} LIMIT 1";
		return sqlMapper.selectOne(sql, orderId, BwRepaymentPlan.class);
	}

	@Override
	public List<String> findZhanqiOrderNo() {
		// 查询未还款、垫付和已续贷的工单ID
		String sql = "SELECT o.order_no FROM bw_repayment_plan rp" + " LEFT JOIN bw_order o ON o.id = rp.order_id"
				+ " WHERE date_format(rp.repay_time,'%Y-%m-%d') = curdate()" + " AND rp.repay_status = 1"
				+ " OR rp.repay_status = 3"
				+ " OR (rp.repay_status = 2 AND (SELECT COUNT(ox.order_id) FROM bw_order_xudai ox WHERE ox.order_id = rp.order_id AND ox.settle_status = 2) > 1)";
		List<Map<String, Object>> list = sqlMapper.selectList(sql);
		// 处理返回记录
		List<String> orderNoList = new ArrayList<>();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				orderNoList.add(YiQiHaoUtil.convertObjToStr(map.get("order_no")));
			}
		}
		return orderNoList;
	}

	@Override
	public void update(BwRepaymentPlan plan) {
		mapper.updateByPrimaryKeySelective(plan);
	}

	@Override
	public boolean findBwRepaymentPlanByOrderNo(String orderNO) {
		String sql = "select count(1) from bw_repayment_plan p left join bw_order o on o.id = p.order_id where o.order_no = '"
				+ orderNO + "'";
		int i = this.sqlMapper.selectOne(sql, Integer.class);
		if (i >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see com.waterelephant.service.IBwRepaymentPlanService#listBwRepaymentPlanByOrderId(java.lang.Long)
	 */
	@Override
	public List<BwRepaymentPlan> listBwRepaymentPlanByOrderId(Long orderId) {
		String sql = "select * from bw_repayment_plan where order_id = #{order_id} ORDER BY repay_time DESC";
		List<BwRepaymentPlan> selectList = this.sqlMapper.selectList(sql, orderId, BwRepaymentPlan.class);
		if (selectList != null && selectList.size() > 0) {
			return selectList;
		}
		return null;
	}

	@Override
	public BwRepaymentPlan getLastRepaymentPlanByOrderId(Long orderId) {
		String sql = "select * from bw_repayment_plan where order_id = #{order_id} ORDER BY repay_time DESC";
		List<BwRepaymentPlan> selectList = this.sqlMapper.selectList(sql, orderId, BwRepaymentPlan.class);
		if (selectList != null && selectList.size() > 0) {
			return selectList.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwRepaymentPlanService#findBwRepaymentPlanAndDetailByOrderId(java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> findBwRepaymentPlanAndDetailByOrderId(Long orderId) {
		String sql = "SELECT p.id as repayId, (IFNULL(p.rollover_number,0) + 1) AS xudaiCount,IF(p.repay_status=2 OR p.repay_status=4,1,0) AS hasRepay,"
				+ "  CAST(d.trade_amount  AS CHAR)  AS tradeAmount, date_format( p.repay_time,'%Y-%m-%d') AS repayTime,"
				+ " date_format( d.create_time,'%Y-%m-%d')  AS tradeTime FROM bw_repayment_plan p LEFT JOIN"
				+ " bw_payment_detail d ON p.id = d.repay_id and d.trade_type =2 WHERE p.order_id = #{orderId}   "
				+ " order by xudaiCount asc ";
		List<Map<String, Object>> list = sqlMapper.selectList(sql, orderId);
		if (!CommUtils.isNull(list) && list.size() > 0) {
			logger.info("展期列表数量为" + list.size());
			Iterator<Map<String, Object>> iterator = list.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> next = iterator.next();
				Object object = next.get("tradeAmount");
				// 展期费用为空，计算展期费用
				if (CommUtils.isNull(object)) {
					next = getXudaiMap(next, orderId);
				}
			}
			list.remove(list.size() - 1);
		} else {
			logger.info("展期列表数量为空");
		}

		return list;
	}

	/**
	 * 获取展期金额：续贷费用本金*18% + 逾期费用
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> getXudaiMap(Map<String, Object> map, Long orderId) {
		Long repayId = (Long) map.get("repayId");// 还款计划id

		// 获取还款计划
		BwRepaymentPlan plan = new BwRepaymentPlan();
		plan.setId(repayId);
		plan = findBwRepaymentPlanByAttr(plan);
		logger.info("==============还款计划为" + plan);
		Double realityRepayMoney = plan.getRepayMoney();// 实际还款金额
		realityRepayMoney = realityRepayMoney == null ? 0.00 : realityRepayMoney;
		map.put("repayTime", MyDateUtils.DateToString(plan.getRepayTime(), MyDateUtils.DATE_TO_STRING_SHORT_PATTERN));
		Date updateTime = plan.getUpdateTime();
		if (!CommUtils.isNull(updateTime)) {
			map.put("tradeTime", MyDateUtils.DateToString(updateTime, MyDateUtils.DATE_TO_STRING_SHORT_PATTERN));
		} else {
			map.put("tradeTime", "");
		}
		Integer repayStatus = plan.getRepayStatus();// 还款状态：1 未还款 2 已还款 3垫付 4展期
		if (repayStatus == 2 || repayStatus == 4) {
			map.put("hasRepay", 1);
		} else {
			map.put("hasRepay", 0);
		}
		// 计算逾期金额
		LoanInfo loanInfo = new LoanInfo();
		Double calcOverdueCost = productService.calcOverdueCost(orderId, repayId, loanInfo);
		calcOverdueCost = calcOverdueCost == null ? 0.00 : calcOverdueCost;
		logger.info("==============逾期金额为：   " + calcOverdueCost);
		// 续贷金额
		Double xudaiAmount = DoubleUtil.mul(realityRepayMoney, 0.27);
		Double tradeAmount = DoubleUtil.add(xudaiAmount, calcOverdueCost);
		map.put("tradeAmount", DoubleUtil.toTwoDecimal(tradeAmount));
		logger.info("==============展期金额为：	" + tradeAmount);
		return map;
	}

	@Override
	public NotifyNotice updateForLianlianPaymentNotify(NotifyResult notifyResult) {
		NotifyNotice notice = new NotifyNotice();
		String repayId = notifyResult.getNo_order().substring(20);
		BwRepaymentPlan plan = mapper.selectByPrimaryKey(Long.valueOf(repayId));
		Long orderId = plan.getOrderId();
		BwOrder order = bwOrderService.findBwOrderById(orderId.toString());
		if (order.getStatusId() == 6) {// 已经还款
			logger.info(
					"=======================该工单:" + orderId + " 已经处理，状态======================" + order.getStatusId());
			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
			return notice;
		}

		// 查询还款人信息
		BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
		if (CommUtils.isNull(borrower)) {
			logger.info("==================借款人信息为空========================");
			notice.setRet_code("101");
			notice.setRet_msg("借款人为空");
			return notice;
		}

		// 查询银行卡信息
		BwBankCard card = bwBankCardService.findBwBankCardByBoorwerId(borrower.getId());
		if (CommUtils.isNull(card)) {
			logger.info("==================银行卡信息为空========================");
			notice.setRet_code("101");
			notice.setRet_msg("银行卡信息为空");
			return notice;
		}

		// 验证是否成功
		if ("SUCCESS".equals(notifyResult.getResult_pay())) {
			String bwOrderRepaymentBatchDetailStr = RedisUtils.hget(RedisKeyConstant.BATCH_REPAYMENT_DETAIL,
					orderId.toString());
			if (StringUtils.isNotEmpty(bwOrderRepaymentBatchDetailStr)) {
				BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = JSON
						.parseObject(bwOrderRepaymentBatchDetailStr, BwOrderRepaymentBatchDetail.class);
				// 记录弹窗(分批)
				bwOrderStatusRecordService.insertRecord(order,
						"您于" + DateUtil.getCurrentDateString(DateUtil.YMD) + "已经成功还款"
								+ Double.valueOf(notifyResult.getMoney_order()) + "元" + ",剩余还款金额"
								+ bwOrderRepaymentBatchDetail.getResidualAmount() + "元",
						ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_BATCHREPAYSUCCESS);
			} else {
				// 记录弹窗(不分批)
				bwOrderStatusRecordService.insertRecord(order,
						"您于" + DateUtil.getCurrentDateString(DateUtil.YMD) + "已经成功还款"
								+ Double.valueOf(notifyResult.getMoney_order()) + "元",
						ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_REPAYSUCCESS);
			}

			// 记录分批还款明细，全额一次还款或最后一批还款则记录支付金额明细
			BwPaymentDetail paymentDetail = bwOrderRepaymentBatchDetailService
					.saveBatchDetailAndRepaymentDetailByRedis(orderId, Long.parseLong(repayId), false);

			// 记录流水
			BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
			bwPlatformRecord.setTradeNo(notifyResult.getOid_paybill());
			bwPlatformRecord.setTradeAmount(Double.valueOf(notifyResult.getMoney_order()));// 交易金额
			bwPlatformRecord.setTradeType(1);// 1划拨2转账
			bwPlatformRecord.setOutAccount(card.getCardNo());
			bwPlatformRecord.setOutName(borrower.getName());
			bwPlatformRecord.setInAccount("上海水象金融信息服务有限公司-连连支付");
			bwPlatformRecord.setInName("上海水象金融信息服务有限公司-连连支付");
			bwPlatformRecord.setOrderId(order.getId());
			bwPlatformRecord.setTradeTime(new Date());
			bwPlatformRecord.setTradeRemark("连连还款扣款");
			bwPlatformRecord.setTradeChannel(3);// 连连支付
			if (null != paymentDetail) {
				bwPlatformRecord.setTerminalType(paymentDetail.getTerminalType());// 终端类型
			}
			// 派发优惠券并还款
			notice = activityService.addParticipationActivity(order, bwPlatformRecord, true);
			bwOrderRepaymentBatchDetailService.deleteBatchDetailAndRepaymentDetailRedis(orderId,
					Long.parseLong(repayId));

			try {
				String channel = order.getChannel() + "";
				if ("12".equals(channel) || "81".equals(channel)) {
					// 好贷
					String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(String.valueOf(order.getId()));
					if (!StringUtils.isEmpty(thirdOrderId)) {
						BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "4");
					}
				}
				// channelService.sendOrderStatus(CommUtils.toString(order.getChannel()),
				// orderId, "6");
			} catch (Exception e) {
				logger.info("====================渠道同步工单状态，回调失败======================");
			}
			if (notice != null && "000".equals(notice.getRet_code())) {
				notice.setRet_code("0000");
			}
			return notice;
		} else {

			logger.info("====================交易失败======================");
			notice.setRet_code("102");
			notice.setRet_msg("交易失败");

			// 删除redis支付详情
			RedisUtils.hdel(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString());
			RedisUtils.hdel(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, orderId.toString());

			// 往BwOrderStatusRecord表插入记录用于弹窗
			bwOrderStatusRecordService.insertRecord(order, ActivityConstant.BWORDERSTATUSRECORD_MSG.MSG_ERROR,
					ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_REPAYFAIL);
			return notice;
		}
	}

	@Override
	public NotifyNotice updateForLianlianXudaiNotify(NotifyResult notifyResult) throws Exception {
		NotifyNotice notice = new NotifyNotice();
		// 验证redis中是否存在该工单
		String repayId = notifyResult.getNo_order().substring(20);
		BwRepaymentPlan plan = mapper.selectByPrimaryKey(Long.valueOf(repayId));
		Long orderId = plan.getOrderId();
		boolean b = RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId.toString());
		if (b) {
			logger.info("=======================该工单:" + orderId + " 已经存在redis中======================");
			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
			return notice;
		}

		// 先验证订单状态是否修改，避免重复修改
		if (CommUtils.isNull(notifyResult.getNo_order())) {// 订单号
			logger.info("==================工单id为空========================");
			notice.setRet_code("101");
			notice.setRet_msg("工单id为空");
			return notice;
		}
		BwOrder order = bwOrderService.findBwOrderById(orderId.toString());
		if (order.getStatusId() == 6) {// 已经还款
			logger.info("=======================该工单:" + notifyResult.getNo_order() + " 已经处理，状态======================"
					+ order.getStatusId());
			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
			return notice;
		}

		// 查询还款人信息
		BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(orderId);
		if (CommUtils.isNull(borrower)) {
			logger.info("==================借款人信息为空========================");
			notice.setRet_code("101");
			notice.setRet_msg("借款人为空");
			return notice;
		}

		// 查询银行卡信息
		BwBankCard card = bwBankCardService.findBwBankCardByBoorwerId(borrower.getId());
		if (CommUtils.isNull(card)) {
			logger.info("==================银行卡信息为空========================");
			notice.setRet_code("101");
			notice.setRet_msg("银行卡信息为空");
			return notice;
		}

		// 验证是否成功
		if ("SUCCESS".equals(notifyResult.getResult_pay())) {
			// TODO 获取渠道、是否用优惠券
			String bwOrderRepaymentBatchDetailStr = RedisUtils.hget(RedisKeyConstant.BATCH_REPAYMENT_DETAIL,
					orderId.toString());
			BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = null;
			if (StringUtils.isNotEmpty(bwOrderRepaymentBatchDetailStr)) {
				bwOrderRepaymentBatchDetail = JSON.parseObject(bwOrderRepaymentBatchDetailStr,
						BwOrderRepaymentBatchDetail.class);
			}
			String payDetailStr = RedisUtils.hget(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString());
			BwPaymentDetail redisPaymentDetail = null;
			if (!StringUtil.isEmpty(payDetailStr)) {
				redisPaymentDetail = JSON.parseObject(payDetailStr, BwPaymentDetail.class);
			}
			Integer terminalType = null;
			if (bwOrderRepaymentBatchDetail != null) {
				terminalType = bwOrderRepaymentBatchDetail.getTerminalType();
			}
			if (terminalType == null && redisPaymentDetail != null) {
				terminalType = redisPaymentDetail.getTerminalType();
			}

			RepaymentDto repaymentDto = new RepaymentDto();
			repaymentDto.setOrderId(orderId);
			repaymentDto.setType(2);
			repaymentDto.setUseCoupon(false);
			repaymentDto.setTerminalType(terminalType);
			repaymentDto.setTradeNo(notifyResult.getOid_paybill());
			repaymentDto.setAmount(Double.valueOf(notifyResult.getMoney_order()));
			repaymentDto.setPayChannel(2);
			repaymentDto.setTradeTime(new Date());
			repaymentDto.setTradeCode(notifyResult.getResult_pay());
			repaymentDto.setTradeType(1);
			AppResponseResult result = bwRepaymentService.updateOrderByTradeMoney(repaymentDto);
			if (!"000".equals(result.getCode())) {
				logger.info("【BwRepaymentPlanService.updateForLianlianXudaiNotify】orderId:" + orderId
						+ "连连展期回调更新updateOrderByTradeMoney失败");
				notice.setRet_code("101");
				notice.setRet_msg("失败");
				return notice;
			}
			// 成功删除redis
			bwPaymentDetailService.deleteRedis(Long.valueOf(repayId));
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId());
			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
			return notice;

		} else {
			logger.info("====================交易失败======================");
			notice.setRet_code("102");
			notice.setRet_msg("交易失败");
			// 删除redis支付详情
			RedisUtils.hdel(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString());
			return notice;
		}
	}

	/**
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public BwRepaymentPlan getLastRepaymentPlanAndXudaiByOrderId(Long orderId) {
		String sql = "select *  from bw_repayment_plan where order_id= #{orderId} order by rollover_number desc limit 1";
		BwRepaymentPlan selectOne = sqlMapper.selectOne(sql, orderId, BwRepaymentPlan.class);
		return selectOne;
	}

	@Override
	public int getXuDaiCountAfterDate(Long orderId, Date afterDate) {
		StringBuilder sqlSB = new StringBuilder("select count(*) from bw_repayment_plan where order_id=#{order_id}");
		if (afterDate != null) {
			sqlSB.append(" and create_time>='");
			sqlSB.append(CommUtils.convertDateToString(afterDate, SystemConstant.YMD_HMS));
			sqlSB.append("'");
		}
		sqlSB.append(" and rollover_number is not null and rollover_number>0");
		Integer xudaiCount = sqlMapper.selectOne(sqlSB.toString(), orderId, Integer.class);
		if (xudaiCount == null) {
			xudaiCount = 0;
		}
		return xudaiCount;
	}

	@Override
	public int getXuDaiCountAfterDate(Long orderId) {
		int xudaiCount = getXuDaiCountAfterDate(orderId,
				CommUtils.convertStringToDate(SystemConstant.XUDAI_AFTER_DATE, SystemConstant.YMD_HMS));
		return xudaiCount;
	}

	@Override
	public BwRepaymentPlan getEarlyNotRepaymentPlan(Long orderId) {
		return sqlMapper.selectOne(
				"select * from bw_repayment_plan where order_id=#{orderId} and repay_status in (1,3) order by number asc limit 1",
				orderId, BwRepaymentPlan.class);
	}

	@Override
	public List<BwRepaymentPlan> getRepaymentPlanList(Long orderId, List<Object> repayStatusList) {
		Example example = new Example(BwRepaymentPlan.class);
		example.setOrderByClause("number asc");
		Criteria criteria = example.createCriteria();
		if (repayStatusList != null && !repayStatusList.isEmpty()) {
			criteria.andIn("repayStatus", repayStatusList);
		}
		criteria.andEqualTo("orderId", orderId);
		return mapper.selectByExample(example);
	}

	/**
	 * 查询所有还款计划
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getInstallmentRepaymentList(Long orderId) {
		String sql = "select number,IFNULL(reality_repay_money,0)reality_repay_money,repay_time,repay_status,overdue_day,IFNULL(overdue_accrual_money,0)overdue_accrual_money from bw_repayment_plan brp left join bw_overdue_record bor on brp.id=bor.repay_Id where brp.order_id="
				+ orderId + " order by number";
		List<Map<String, Object>> list = sqlMapper.selectList(sql);
		return list;
	}

	/**
	 * 查询逾期还款计划
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public List<BwRepaymentPlan> getOverdueRepaymentPlanList(Long orderId) {
		String sql = "select * from bw_repayment_plan where order_id=" + orderId
				+ " and repay_status=3 order by number";
		List<BwRepaymentPlan> list = sqlMapper.selectList(sql, BwRepaymentPlan.class);
		return list;
	}

	/**
	 * 查询所有未还款还款计划
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public List<BwRepaymentPlan> getOutstandRepaymentPlan(Long orderId) {
		String sql = "select * from bw_repayment_plan where order_id=" + orderId + " and repay_status in(1,3)";
		List<BwRepaymentPlan> list = sqlMapper.selectList(sql, BwRepaymentPlan.class);
		return list;
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwRepaymentPlanService#getCurrentNumber(java.lang.Long)
	 */
	@Override
	public Integer getCurrentNumber(Long orderId) {
		String sql = "select max(number) from bw_repayment_plan where order_id=" + orderId
				+ " and repay_status in (2,3) order by number";
		Integer currentNumber = sqlMapper.selectOne(sql, Integer.class);
		return StringUtil.toInteger(currentNumber) + 1;
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwRepaymentPlanService#saveBwRepaymentPlan(com.waterelephant.entity.BwRepaymentPlan)
	 */
	@Override
	public void saveBwRepaymentPlan(BwRepaymentPlan plan) {
		mapper.insertSelective(plan);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwRepaymentPlanService#deleteBwRepaymentPlan(java.lang.Long)
	 */
	@Override
	public void deleteBwRepaymentPlan(Long planId) {
		sqlMapper.delete("delete from bw_repayment_plan where id=" + planId);

	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwRepaymentPlanService#getBwRepaymentPlanByPlanId(java.lang.Long)
	 */
	@Override
	public BwRepaymentPlan getBwRepaymentPlanByPlanId(Long planId) {
		return mapper.selectByPrimaryKey(planId);
	}

	@Override
	public String saveRepaymentPlanByOrderAndReapyDateCapital(BwOrder order, String repayDate) {
		int num = 0;
		if (order == null) {
			return "工单不存在";
		}
		if (StringUtil.isEmpty(order.getId())) {
			return "工单Id不能为空";
		}
		if (StringUtil.isEmpty(repayDate)) {
			return "放款时间不能为空";
		}

		order.setStatusId(9l);
		order.setUpdateTime(new Date());
		bwOrderService.updateBwOrder(order);
		Integer borrowNumber = order.getBorrowNumber();
		Double borrowAmount = order.getBorrowAmount();
		if (borrowNumber == null) {
			borrowNumber = 1;
		}
		if (borrowAmount == null) {
			return "borrowAmount不能为空";
		}

		double preMoney = Math.floor(DoubleUtil.div(borrowAmount, Double.valueOf(borrowNumber)));// 平均每期应还参数
		for (int i = 1; i <= borrowNumber; i++) {
			BwRepaymentPlan plan = new BwRepaymentPlan();
			plan.setOrderId(order.getId());
			if (i < borrowNumber) {
				Double realityRepayMoney = Math.floor(DoubleUtil.add(preMoney, DoubleUtil.mul(borrowAmount, 0.07)));// 分期的手续费？
				plan.setRepayMoney(realityRepayMoney);//
				plan.setRealityRepayMoney(realityRepayMoney);
				plan.setRepayCorpusMoney(preMoney);// 这个才是本金
			} else {
				Double lastMoney = DoubleUtil.sub(borrowAmount, DoubleUtil.mul(preMoney, borrowNumber - 1));
				Double realityRepayMoney = Math.floor(DoubleUtil.add(lastMoney, DoubleUtil.mul(borrowAmount, 0.07)));
				plan.setRepayMoney(realityRepayMoney);
				plan.setRealityRepayMoney(realityRepayMoney);
				plan.setRepayCorpusMoney(lastMoney);
			}
			plan.setRepayTime(MyDateUtils.addDays(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd), 30 * i));
			plan.setRepayStatus(1);
			plan.setRepayType(1);
			plan.setRepayIsCorpus(2);

			plan.setRepayAccrualMoney(Math.floor(DoubleUtil.mul(borrowAmount, 0.07)));
			plan.setCreateTime(DateUtil.stringToDate(repayDate, DateUtil.yyyy_MM_dd));
			plan.setNumber(i);
			plan.setAlreadyRepayMoney(0.00);
			saveBwRepaymentPlan(plan);
			num++;
		}
		logger.info("工单Id" + order.getId() + "插入还款计划条数:" + num);
		return "工单Id" + order.getId() + "插入还款计划条数:" + num;
	}

	@Override
	public String saveRepaymentPlanByOrderAndReapyDateNew(BwOrder order, Date repayDate) {
		int num = 0;
		if (order == null) {
			return "工单不存在";
		}
		if (StringUtil.isEmpty(order.getId())) {
			return "工单Id不能为空";
		}
		if (StringUtil.isEmpty(repayDate)) {
			return "放款时间不能为空";
		}
		List<BwRepaymentPlan> planList = findRepaymentPlanListByOrderId(order.getId());
		if (planList != null && !planList.isEmpty()) {
			return "已有还款计划";
		}

		order.setStatusId(9l);
		order.setUpdateTime(new Date());
		bwOrderService.updateBwOrder(order);
		Integer borrowNumber = order.getBorrowNumber();
		Double borrowAmount = order.getBorrowAmount();
		if (borrowNumber == null) {
			borrowNumber = 1;
		}
		if (borrowAmount == null) {
			return "borrowAmount不能为空";
		}

		double preMoney = Math.floor(DoubleUtil.div(borrowAmount, Double.valueOf(borrowNumber)));// 平均每期应还参数
		BwProductDictionary product = bwProductDictionaryService.findBwProductDictionaryById(order.getProductId());
		Double interestRate = product.getInterestRate();
		Date calcRepayDate = productService.calcRepayTime(repayDate, product.getpTerm(), product.getpTermType());
		for (int i = 1; i <= borrowNumber; i++) {
			BwRepaymentPlan plan = new BwRepaymentPlan();
			plan.setOrderId(order.getId());
			Double calcRateMoney = DoubleUtil.sub(borrowAmount, DoubleUtil.mul(preMoney, i - 1));// 计算利息的本金
			Double interestMoney = DoubleUtil.round(DoubleUtil.mul(calcRateMoney, interestRate), 0);// 利息
			plan.setRepayAccrualMoney(interestMoney);
			if (i < borrowNumber) {
				Double realityRepayMoney = DoubleUtil.round(DoubleUtil.add(preMoney, interestMoney), 0);// 分期的手续费？
				plan.setRepayMoney(realityRepayMoney);//
				plan.setRealityRepayMoney(realityRepayMoney);
				plan.setRepayCorpusMoney(preMoney);// 这个才是本金
			} else {
				Double lastMoney = DoubleUtil.sub(borrowAmount, DoubleUtil.mul(preMoney, borrowNumber - 1));
				Double realityRepayMoney = DoubleUtil.round(DoubleUtil.add(lastMoney, interestMoney), 0);
				plan.setRepayMoney(realityRepayMoney);
				plan.setRealityRepayMoney(realityRepayMoney);
				plan.setRepayCorpusMoney(lastMoney);
			}
			plan.setRepayTime(calcRepayDate);
			calcRepayDate = productService.calcRepayTime(calcRepayDate, product.getpTerm(), product.getpTermType());
			plan.setRepayStatus(1);
			plan.setRepayType(1);
			plan.setRepayIsCorpus(2);

			plan.setCreateTime(new Date());
			plan.setNumber(i);
			plan.setAlreadyRepayMoney(0.00);
			saveBwRepaymentPlan(plan);
			num++;
		}
		logger.info("工单Id" + order.getId() + "插入还款计划条数:" + num);
		return "工单Id" + order.getId() + "插入还款计划条数:" + num;
	}

	@Override
	public List<BwRepaymentPlan> selectNotZhanqiPlan(Long orderId, Integer productType) {
		if (productType == null) {
			BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
			productType = bwOrder.getProductType();
		}
		List<BwRepaymentPlan> list = null;
		if (productType != null) {
			if (productType == 1) {// 单期
				list = new ArrayList<BwRepaymentPlan>();
				BwRepaymentPlan plan = getLastRepaymentPlanByOrderId(orderId);
				if (plan != null) {
					list.add(plan);
				}
			} else if (productType == 2) {// 分期
				Example example = new Example(BwRepaymentPlan.class);
//				example.setOrderByClause("repayTime ASC");
				example.orderBy("repayTime").asc();
				Criteria criteria = example.createCriteria();
				criteria.andEqualTo("orderId", orderId);
				list = findRepaymentPlanByExample(example);
			}
		}
		return list;
	}

	@Override
	public BwRepaymentPlan findRepaymentPlanByOrderIdAndRepayStatus(Long orderId, Integer repayStatus) {
		String sql = "select * from bw_repayment_plan where order_id = #{orderId} and repay_status = #{repayStatus}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repayStatus", repayStatus);
		map.put("orderId", orderId);
		return sqlMapper.selectOne(sql, map, BwRepaymentPlan.class);
	}

	@Override
	public List<BwRepaymentPlan> findRepaymentPlanListByOrderIdAndRepayStatus(Long orderId, Integer repayStatus) {
		String sql = "select * from bw_repayment_plan where order_id = #{orderId} and repay_status = #{repayStatus}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("repayStatus", repayStatus);
		map.put("orderId", orderId);
		return sqlMapper.selectList(sql, map, BwRepaymentPlan.class);
	}

	@Override
	public List<BwRepaymentPlan> findRepaymentPlanListByOrderId(Long orderId) {
		String sql = "select * from bw_repayment_plan where order_id = #{orderId} and repay_status != 4";

		return sqlMapper.selectList(sql, orderId, BwRepaymentPlan.class);
	}

	@Override
	public void saveAutoPlan2Redis(Long orderId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT rp.id, rp.repay_time repayTime, rp.reality_repay_money realityRepayMoney, rp.already_repay_money alreadyRepayMoney, rp.repay_type repayType,")
				.append(" o.id orderId, o.order_no orderNo, o.create_time orderTime,")
				.append(" b.id borrowerId, b.name, b.phone, b.id_card idCard, b.create_time regTime,")
				.append(" bc.card_no cardNo, bc.bank_code bankCode, bc.sign_status signStatus, r.overdue_accrual_money overdueAccrualMoney")
				.append(" FROM bw_repayment_plan rp LEFT JOIN bw_order o ON o.id= rp.order_id")
				.append(" LEFT JOIN bw_borrower b ON b.id= o.borrower_id")
				.append(" LEFT JOIN bw_bank_card bc ON bc.borrower_id= b.id")
				.append(" LEFT JOIN bw_overdue_record r ON r.repay_Id= rp.id")
				.append(" WHERE o.id=")
				.append(orderId).append(" and o.status_id in (9,13) and rp.repay_status in (1,3)")
				.append(" and rp.repay_time<='")
				.append(CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS))
				.append("' order by rp.repay_time asc limit 1");

		String sql = sb.toString();
		List<Map<String, Object>> list = sqlMapper.selectList(sql);
		if (list != null && !list.isEmpty()) {
			RedisUtils.lpush("ReapyJob:cloudAutoRepay:now", JSON.toJSONString(list.get(0)));
		}
	}

	/**
	 * 获取回调成功或失败通知三方数据，回调处理之前调用
	 *
	 * @param bwOrder
	 * @param payMoney
	 * @param payStatus 1.处理中，2.成功，3.失败
	 * @param notifyMsg 三方返回的msg
	 * @return 返回需要保存的数据，key:redis的键，value:redis的值，处理完后存redis
	 */
	@Override
	public Map<String, String> getNotifyThirdData(BwOrder bwOrder, Double payMoney, int payStatus, String notifyMsg) {
		Long orderId = bwOrder.getId();
		Map<String, String> dataMap = new HashMap<>();
		try {
			Integer productType = bwOrder.getProductType();
			BwProductDictionary product = bwProductDictionaryService.findBwProductDictionaryById(bwOrder.getProductId());
			List<BwRepaymentPlan> allPlanList = selectNotZhanqiPlan(orderId, productType);
			Double totalRepayMoney = bwOrder.getBorrowAmount();// 总共还款金额，不算利息
			double totalAmount = 0.0;// 总共应还金额
			double totalRealityRepayMoney = 0.0;
			double batchLeftMoney = payMoney;
			for (BwRepaymentPlan plan : allPlanList) {
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
				loanInfo.setAmt(totalRepayMoney.toString());
				Double realityRepayMoney = plan.getRealityRepayMoney();
				totalRealityRepayMoney = DoubleUtil.add(totalRealityRepayMoney, realityRepayMoney);
				Long repayId = plan.getId();
				Integer repayStatus = plan.getRepayStatus();
				Double calcRepaymentCost = productService.calcRepaymentCost(realityRepayMoney, orderId, repayId, product,
						loanInfo);
				totalAmount = DoubleUtil.add(totalAmount, calcRepaymentCost);// 总共还款金额
				if (repayStatus == 1 || repayStatus == 3) {// 未还款
					// 还款计划已还款字段
					Double alreadyRepayMoney = plan.getAlreadyRepayMoney();
					if (alreadyRepayMoney == null || (productType==1 && (repayStatus == 4 || RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId.toString())))) {
						alreadyRepayMoney = 0.0;
					}
					double leftAmount = DoubleUtil.sub(calcRepaymentCost, alreadyRepayMoney);// 当前还款计划剩余还款金额
					batchLeftMoney = DoubleUtil.sub(batchLeftMoney, leftAmount);
					Integer channel = bwOrder.getChannel();
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("orderId", orderId);
					jsonObject.put("channelId", channel);
					jsonObject.put("number", plan.getNumber());
					jsonObject.put("result", notifyMsg);
					if (payStatus == 2) {// 成功
						jsonObject.put("repayStatus", 1);
					} else {
						jsonObject.put("repayStatus", 0);
					}
					dataMap.put("tripartite:repayResultNotify:" + channel, JSON.toJSONString(jsonObject));
					if (batchLeftMoney <= 0.0) {
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("【BwRepaymentPlanService.notifyThird】orderId:" + orderId + "获取通知三方数据异常", e);
		}
		return dataMap;
	}

	@Override
	public void notifyThird(Map<String, String> notifyThirdDataMap) {
		if (notifyThirdDataMap != null && !notifyThirdDataMap.isEmpty()) {
			for (Map.Entry<String, String> entry : notifyThirdDataMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (StringUtils.isNotEmpty(key)) {
					RedisUtils.lpush(key, value);
				}
			}
		}
	}

	@Override
	public RepayInfoDto selectAutoRepayByRepayId(Long repayId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT rp.id, rp.repay_time, rp.reality_repay_money, rp.already_repay_money, rp.repay_type,")
				.append(" o.id order_id, o.order_no, o.create_time order_time,")
				.append(" b.id borrower_id, b.name, b.phone, b.id_card, b.create_time reg_time,")
				.append(" bc.card_no, bc.bank_code, bc.sign_status, r.overdue_accrual_money")
				.append(" FROM bw_repayment_plan rp LEFT JOIN bw_order o ON o.id= rp.order_id")
				.append(" LEFT JOIN bw_borrower b ON b.id= o.borrower_id")
				.append(" LEFT JOIN bw_bank_card bc ON bc.borrower_id= b.id")
				.append(" LEFT JOIN bw_overdue_record r ON r.repay_Id= rp.id")
				.append(" LEFT JOIN bw_merchant_order bmo ON bmo.order_id= o.id")
				.append(" inner join(select p2.order_id, min(p2.id) min_id from bw_repayment_plan p2")
				.append(" where p2.repay_status in(1, 3) and p2.create_time>= '2017-12-12' group by p2.order_id) t on rp.id= t.min_id")
				.append(" WHERE o.status_id in(9, 13) AND rp.repay_status IN(1, 3) AND o.product_id= 7")
				.append(" and DATE_FORMAT(rp.repay_time,'%Y-%m-%d')<='"
						+ DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
				.append("' and rp.id=").append(repayId).append(" limit 1");
		String sql = sb.toString();
		return sqlMapper.selectOne(sql, RepayInfoDto.class);
	}
}
