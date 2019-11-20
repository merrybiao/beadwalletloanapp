package com.waterelephant.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderStatusRecord;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderStatusRecordService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.OrderAndBlacklistService;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.StringUtil;

@Service
public class OrderAndBlacklistServiceImpl extends BaseService<BwOrder, Long> implements OrderAndBlacklistService {

	private Logger logger = Logger.getLogger(OrderAndBlacklistServiceImpl.class);
	@Autowired
	BwOrderStatusRecordService bwOrderStatusRecordService;
	@Autowired
	IBwRepaymentPlanService iBwRepaymentPlanService;
	@Autowired
	BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	IBwOrderService iBwOrderService;
	@Autowired
	BwRejectRecordService bwRejectRecordService;

	@Override
	public Map<String, Object> getOrderAndBlacklist(String bwId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询用户最新一期工单
		String bwOrderSql = "select o.id, o.borrow_amount amount,o.status_id status,o.expect_money, o.create_time  , p.p_term term,p.p_term_type termType  from bw_order o,bw_product_dictionary p where o.product_id=p.id and "
				+ "  o.borrower_id = " + bwId + " and o.product_type = 1  ORDER by create_time desc limit 0,1";
		Map<String, Object> map = sqlMapper.selectOne(bwOrderSql);
		if (CommUtils.isNull(map)) {
			return null;
		}
		if (null != map) {
			logger.info("工单ID：" + map.get("id"));
			String orderId = StringUtil.toString(map.get("id"));
			// 查询还款计划
			List<Map<String, Object>> list = iBwRepaymentPlanService
					.findBwRepaymentPlanByOrderId(Long.parseLong(orderId));
			if (!CommUtils.isNull(list)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String realityRepayMoney = StringUtil.toString(list.get(0).get("reality_repay_money"));
				String repayTime = StringUtil.toString(sdf.format(list.get(0).get("repay_time")));
				resultMap.put("repayTime", repayTime);
				resultMap.put("realityRepayMoney", Double.parseDouble(realityRepayMoney));
			} else {
				resultMap.put("repayTime", "");
				resultMap.put("realityRepayMoney", 0.00);
			}

			// 查询工单状态记录表
			BwOrderStatusRecord bwOrderStatusRecord = bwOrderStatusRecordService
					.getBwOrderStatusRecordByOrderId(orderId);
			if (!CommUtils.isNull(bwOrderStatusRecord)) {

				bwOrderStatusRecord.setEffective(ActivityConstant.BWORDERSTATUSRECORD_EFFECTIVE.EFFECTIVE_0);
				bwOrderStatusRecordService.updateRecord(bwOrderStatusRecord);
			}
			BwRepaymentPlan lastPlan = iBwRepaymentPlanService.getLastRepaymentPlanByOrderId(Long.parseLong(orderId));
			// 查询逾期表
			BwOverdueRecord bo = new BwOverdueRecord();
			bo.setOrderId(Long.parseLong(orderId));
			bo.setRepayId(lastPlan != null ? lastPlan.getId() : null);
			bo = bwOverdueRecordService.findBwOverdueRecordByAttr(bo);

			// 查询该用户是否在黑名单
			String blackSql = "select sort from bw_blacklist where card = (select id_card from bw_borrower where id = "
					+ bwId + ") and status = 1 ";
			Integer sort = sqlMapper.selectOne(blackSql, Integer.class);
			sort = StringUtil.isEmpty(sort) ? 0 : sort;
			// 查询记录并返回
			BwRejectRecord record = new BwRejectRecord();
			logger.info("工单Id：" + orderId);
			record.setOrderId(Long.parseLong(orderId));
			record = bwRejectRecordService.findBwRejectRecordByAtta(record);
			if (!CommUtils.isNull(record)) {
				if (sort != 1) {
					Integer rejectType = record.getRejectType();
					if (rejectType == 1) {
						sort = 2;
					}
					if (rejectType == 0) {
						sort = 1;
					}
				}
			}
			String whiteTime = null;
			boolean isExpire = true;
			// 如果不是黑名单用户
			if (!sort.equals(ActivityConstant.BLACK_SORT.SORT_1)) {
				// 如果不是黑名单用户返回到期时间
				Date whiteDate = iBwOrderService.findBorrowWhiteTimeByBorrowId(bwId);
				if (!CommUtils.isNull(whiteDate)) {
					isExpire = DateUtil.isBeforeTime(whiteDate, new Date());
					whiteTime = DateUtil.getDateString(whiteDate, DateUtil.yyyy_MM_dd);
				}
			}
			resultMap.put("whiteTime", CommUtils.isNull(whiteTime) ? "" : whiteTime);
			resultMap.put("isExpire", isExpire);
			resultMap.put("orderId", map.get("id"));
			resultMap.put("orderStatus", map.get("status"));
			resultMap.put("isDialog", CommUtils.isNull(bwOrderStatusRecord) ? false : true);
			resultMap.put("dialogMsg", CommUtils.isNull(bwOrderStatusRecord) ? "" : bwOrderStatusRecord.getMsg());
			resultMap.put("dialogStyle",
					CommUtils.isNull(bwOrderStatusRecord) ? "" : bwOrderStatusRecord.getDialogStyle());
			if (map.get("amount") == null) {
				resultMap.put("amount", map.get("expect_money"));
			} else {
				resultMap.put("amount", map.get("amount"));
			}
			String term = map.get("term").toString();
			String termType = map.get("termType").toString();
			if ("2".equals(termType)) {
				resultMap.put("term", term + "天");
			} else if ("1".equals(termType)) {
				resultMap.put("term", term + "月");
			} else {
				resultMap.put("term", term);
			}
			resultMap.put("overdueDay", StringUtil.toString(bo != null ? bo.getOverdueDay() : "0"));
			resultMap.put("sort", StringUtil.isEmpty(sort) ? ActivityConstant.BLACK_SORT.SORT_0 : sort);
			return resultMap;

		} else {
			return null;
		}

	}

	@Override
	public List<BwOrder> getTopTenBwOrder() {
		String sql = "select * from bw_order where status_id in(6 ,9,13)   ORDER BY update_time  desc  limit  "
				+ ActivityConstant.RRSULT_TOP + " ";

		return sqlMapper.selectList(sql, BwOrder.class);
	}

}
