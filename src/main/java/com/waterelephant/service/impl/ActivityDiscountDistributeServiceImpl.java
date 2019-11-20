package com.waterelephant.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.waterelephant.entity.ActivityDiscountDistribute;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.ActivityDiscountDistributeService;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.CommUtils;

@Service
public class ActivityDiscountDistributeServiceImpl extends BaseService<ActivityDiscountDistribute, Long>
		implements ActivityDiscountDistributeService {

	private Logger logger = Logger.getLogger(ActivityDiscountDistributeServiceImpl.class);

	@Override
	public void addActivityDiscountDistribute(ActivityDiscountDistribute activityDiscountDistribute) {
		mapper.insert(activityDiscountDistribute);
	}

	@Override
	public void updateActivityDiscountDistribute(ActivityDiscountDistribute activityDiscountDistribute) {
		mapper.updateByPrimaryKeySelective(activityDiscountDistribute);
	}

	@Override
	public List<ActivityDiscountDistribute> queryActivityDiscountDistribute(Map<String, Object> map) {
		String sql = "SELECT * from activity_discount_distribute";

		List<ActivityDiscountDistribute> activityDiscountDistributesList = sqlMapper.selectList(sql,
				ActivityDiscountDistribute.class);
		return activityDiscountDistributesList;
	}

	@Override
	public ActivityDiscountDistribute findMaxActivityDiscountDistribute(Long borrowId) {
		// 通过借款人id查询工单
		String orderSql = "select o.* from bw_order o where o.borrower_id=#{borrwerId} and o.status_id!=1  and product_type = 1   ORDER BY o.create_time DESC LIMIT 1;";
		BwOrder bwOrder = sqlMapper.selectOne(orderSql, borrowId, BwOrder.class);
		if (bwOrder == null) {
			logger.info("查询工单信息为空");
			return new ActivityDiscountDistribute();
		}

		// 查询最新一期的还款计划
		String planSql = "SELECT o.repay_term,o.borrow_amount,p.* FROM bw_order o LEFT JOIN bw_repayment_plan p "
				+ "ON o.id = p.order_id " + "WHERE p.order_id = #{orderId} " + "AND p.repay_status in(1,3) "
				+ "ORDER BY p.number DESC ";
		List<Map<String, Object>> planByOrderIds = sqlMapper.selectList(planSql, bwOrder.getId());
		// 查询符合条件的优惠券
		if (planByOrderIds.size() > 0) {
			Double realityRepayMoney = Double.parseDouble(planByOrderIds.get(0).get("reality_repay_money").toString());
			String sql = "select * from activity_discount_distribute  where borrow_id=" + borrowId
					+ " and amount=(select max(amount) from activity_discount_distribute where expiry_time>=now() and borrow_id="
					+ borrowId + " and number > 0 and (loan_amount  is null or  loan_amount<=" + realityRepayMoney
					+ ")) ORDER BY create_time DESC LIMIT 1";
			ActivityDiscountDistribute selectOne = sqlMapper.selectOne(sql, ActivityDiscountDistribute.class);
			if (CommUtils.isNull(selectOne)) {
				logger.info("查询优惠券信息为空");
				return new ActivityDiscountDistribute();
			}
			return selectOne;
		} else {
			logger.info("未查询到符合条件的优惠券");
		}
		return new ActivityDiscountDistribute();
	}

	@Override
	public ActivityDiscountDistribute findActivityDiscountDistributeById(Long distributeId) {
		String sql = "select * from activity_discount_distribute where distribute_id=" + distributeId;
		return this.sqlMapper.selectOne(sql, ActivityDiscountDistribute.class);
	}

	@Override
	public BwRepaymentPlan findBwRepaymentPlanByAttr(Long orderId) {
		String sql = "select * from bw_repayment_plan where order_id=" + orderId;
		return this.sqlMapper.selectOne(sql, BwRepaymentPlan.class);
	}

	@Override
	public String findNewBwRepaymentPlanMoney(Long orderId) {
		// 查询所有的的还款计划
		String planSql = "SELECT p.repay_money FROM bw_order o LEFT JOIN bw_repayment_plan p " + "ON o.id = p.order_id "
				+ "WHERE p.order_id = #{orderId} " + "AND p.repay_status in(1,3) " + "ORDER BY p.number DESC ";
		List<Map<String, Object>> list = sqlMapper.selectList(planSql, orderId);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0).get("repay_money").toString();
		}
		return null;
	}

	@Override
	public ActivityDiscountDistribute findDiscountDistributeByBorrowerId(Long borrowerId) {
		String sql = "select * from activity_discount_distribute where borrow_id = " + borrowerId;
		ActivityDiscountDistribute selectOne = this.sqlMapper.selectOne(sql, ActivityDiscountDistribute.class);
		if (CommUtils.isNull(selectOne)) {
			selectOne = new ActivityDiscountDistribute();
		}
		return selectOne;
	}

}
