package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOverdueRecordService;
import tk.mybatis.mapper.entity.Example;

@Service
public class BwOverdueRecordServiceImpl extends BaseService<BwOverdueRecord, Long> implements BwOverdueRecordService {

	@Autowired
	private BwRepaymentPlanService bwRepaymentPlanService;

	@Override
	public int updateBwOverdueRecord(BwOverdueRecord bwOverdueRecord) {
		return updateByPrimaryKey(bwOverdueRecord);
	}

	@Override
	public BwOverdueRecord queryBwOverdueRecord(BwOverdueRecord bwOverdueRecord) {
		return mapper.selectOne(bwOverdueRecord);
	}

	@Override
	public BwOverdueRecord findBwOverdueRecordByAttr(BwOverdueRecord bo) {
		return mapper.selectOne(bo);
	}

	@Override
	public void updateBwOverdueRecordMoney(BwOrder bwOrder) {
		Date now = new Date();
		if (bwOrder == null || bwOrder.getId() == null) {
			return;
		}
		BwRepaymentPlan plan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwOrder.getId());
		if (plan == null) {
			return;
		}
		if (now.after(plan.getRepayTime())) {
			// 逾期记录罚息金额清零
			BwOverdueRecord paramOverdue = new BwOverdueRecord();
			paramOverdue.setOrderId(bwOrder.getId());
			paramOverdue.setRepayId(plan.getId());
			BwOverdueRecord overdueRecord = findBwOverdueRecordByAttr(paramOverdue);
			if (overdueRecord != null) {
				paramOverdue.setId(overdueRecord.getId());
				paramOverdue.setOverdueAccrualMoney(0.0);
				mapper.updateByPrimaryKeySelective(paramOverdue);
			}
		}
	}

	@Override
	public void updateBwOverdueRecordMoney(Long orderId, Long repayId) {
		Date now = new Date();
		// 逾期记录罚息金额清零
		BwOverdueRecord paramOverdue = new BwOverdueRecord();
		paramOverdue.setOrderId(orderId);
		paramOverdue.setRepayId(repayId);
		BwOverdueRecord overdueRecord = findBwOverdueRecordByAttr(paramOverdue);
		if (overdueRecord != null) {
			paramOverdue.setId(overdueRecord.getId());
			paramOverdue.setOverdueAccrualMoney(0.0);
			paramOverdue.setUpdateTime(now);
			mapper.updateByPrimaryKeySelective(paramOverdue);
		}
	}

	/**
	 * 根据还款计划Id查询逾期记录
	 * 
	 * @see com.waterelephant.service.BwOverdueRecordService#queryBwOverdueByRepayId(java.lang.Long)
	 */
	@Override
	public BwOverdueRecord queryBwOverdueByRepayId(Long repayId) {
		if (repayId == null || repayId <= 0L) {
			return null;
		}
		BwOverdueRecord param = new BwOverdueRecord();
		param.setRepayId(repayId);
		List<BwOverdueRecord> list = mapper.select(param);
		if (list != null && !list.isEmpty()) {
			return list.get(list.size() - 1);
		}
		return null;
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwOverdueRecordService#saveBwOverdueRecord(com.waterelephant.entity.BwOverdueRecord)
	 */
	@Override
	public void saveBwOverdueRecord(BwOverdueRecord record) {
		mapper.insert(record);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwOverdueRecordService#deleteBwOverdueRecord(java.lang.Long)
	 */
	@Override
	public void deleteBwOverdueRecord(Long repayId) {
		sqlMapper.delete("delete from bw_overdue_record where repay_Id=" + repayId);
	}

}
