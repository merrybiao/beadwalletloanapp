package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beadwallet.service.rong360.entity.response.SheBaoFlow;
import com.waterelephant.entity.BwInsureRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwInsureRecordService;

@Service
public class BwInsureRecordImpl extends BaseService<BwInsureRecord, Long> implements BwInsureRecordService {

	@Override
	public Long save(BwInsureRecord bwInsureRecord) {
		mapper.insert(bwInsureRecord);
		return bwInsureRecord.getId();
	}

	@Override
	public List<BwInsureRecord> getRecords() {
		//
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String month1;
		String month2;
		String month3;
		if (month == 12) {
			month1 = year + "-" + month;
			month2 = year + "-" + (month - 1);
			month3 = year + "-" + (month - 2);
		} else if (month == 11) {
			month1 = year + "-" + month;
			month2 = year + "-" + (month - 1);
			month3 = year + "-" + "0" + (month - 2);
		} else if (month == 10) {
			month1 = year + "-" + month;
			month2 = year + "-" + "0" + (month - 1);
			month3 = year + "-" + "0" + (month - 2);
		} else {
			month1 = year + "-" + "0" + month;
			month2 = year + "-" + "0" + (month - 1);
			month3 = year + "-" + "0" + (month - 2);
		}

		String sql = "SELECT a.* FROM bw_insure_record a " + "WHERE a.id_card = '513021199209024392' "
				+ "AND a.pay_date IN ('" + month1 + "','" + month2 + "','" + month3 + "') "
				+ "AND a.pay_type = '正常应缴记录'";
		return sqlMapper.selectList(sql, BwInsureRecord.class);
	}

	/**
	 * 融360 - 社保 - 删除社保记录（code0084）
	 * 
	 * @see com.waterelephant.service.BwInsureRecordService#deleteBwInsureRecord(com.waterelephant.entity.BwInsureRecord)
	 */
	@Override
	public boolean deleteBwInsureRecord(BwInsureRecord bwInsureRecord) {
		return mapper.delete(bwInsureRecord) > 0;
	}

	/**
	 * 融360 - 社保 - 批量保存（code0084）
	 * 
	 * @see com.waterelephant.service.BwInsureRecordService#saveList(java.util.List)
	 */
	@Override
	public void saveList(List<SheBaoFlow> flowList, long insureInfoId, long orderId) {
		// 拼接SQL
		List<String> sqlList = new ArrayList<>();
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < flowList.size(); i++) {
			SheBaoFlow sheBaoFlow = flowList.get(i);
			sBuilder.append("(");
			sBuilder.append("    '" + sheBaoFlow.getId_card() + "',"); // id_card
			sBuilder.append("    '" + sheBaoFlow.getPay_date() + "',"); // pay_date
			sBuilder.append("    '" + sheBaoFlow.getStart_date() + "',"); // start_date
			sBuilder.append("    '" + sheBaoFlow.getEnd_date() + "',"); // end_date
			sBuilder.append("    '" + sheBaoFlow.getBase_rmb() + "',"); // base_rmb
			sBuilder.append("    '" + sheBaoFlow.getCom_rmb() + "',"); // com_rmb
			sBuilder.append("    '" + sheBaoFlow.getPer_rmb() + "',"); // per_rmb
			sBuilder.append("    '" + sheBaoFlow.getBalance_rmb() + "',"); // balance_rmb
			sBuilder.append("    '" + sheBaoFlow.getMonth_rmb() + "',"); // month_rmb
			sBuilder.append("    '" + sheBaoFlow.getCom_name() + "',"); // com_name
			sBuilder.append("    '" + sheBaoFlow.getPay_type() + "',"); // pay_type
			sBuilder.append("   '" + sheBaoFlow.getFlow_type() + "',"); // flow_type
			sBuilder.append("    '" + insureInfoId + "',"); // insure_info_id
			sBuilder.append("    '" + orderId + "'"); // order_id
			sBuilder.append(")"); //

			if (i != 100 && (i != (flowList.size() - 1))) {
				sBuilder.append(","); // 添加逗号
			}

			if (i == 100 || (i == (flowList.size() - 1))) {
				sqlList.add(sBuilder.toString());
				sBuilder = new StringBuilder();
			}
		}

		// 插入数据库
		for (String sql : sqlList) {
			StringBuilder sBuilder2 = new StringBuilder();
			sBuilder2.append("INSERT INTO bw_insure_record");
			sBuilder2.append(" (");
			sBuilder2.append("     `id_card`,");
			sBuilder2.append("     `pay_date`,");
			sBuilder2.append("    `start_date`,");
			sBuilder2.append("     `end_date`,");
			sBuilder2.append("     `base_rmb`,");
			sBuilder2.append("     `com_rmb`,");
			sBuilder2.append("     `per_rmb`,");
			sBuilder2.append("     `balance_rmb`,");
			sBuilder2.append("     `month_rmb`,");
			sBuilder2.append("     `com_name`,");
			sBuilder2.append("     `pay_type`,");
			sBuilder2.append("     `flow_type`,");
			sBuilder2.append("     `insure_info_id`,");
			sBuilder2.append("    `order_id`");
			sBuilder2.append(")");
			sBuilder2.append("VALUES");
			sBuilder2.append(sql);

			sqlMapper.insert(sBuilder2.toString());
		}
	}

	/**
	 * (code:s2s)
	 * 
	 * @see com.waterelephant.service.BwInsureRecordService#findListByAttr(com.waterelephant.entity.BwInsureRecord)
	 */
	@Override
	public List<BwInsureRecord> findListByAttr(BwInsureRecord bwInsureRecord) {
		return mapper.select(bwInsureRecord);
	}

}
