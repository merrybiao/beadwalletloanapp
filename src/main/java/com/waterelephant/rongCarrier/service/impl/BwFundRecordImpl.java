package com.waterelephant.rongCarrier.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beadwallet.service.rong360.entity.response.FundFlow;
import com.waterelephant.rongCarrier.entity.BwFundInfo;
import com.waterelephant.rongCarrier.entity.BwFundRecord;
import com.waterelephant.rongCarrier.service.BwFundRecordService;
import com.waterelephant.service.BaseService;

/**
 * 
 * 融360 - 公积金 - 缴费记录（code0085）
 * 
 * Module:
 * 
 * BwFundRecordImpl.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwFundRecordImpl extends BaseService<BwFundRecord, Long> implements BwFundRecordService {

	/**
	 * 融360 - 公积金 - 删除缴费记录
	 * 
	 * @see com.waterelephant.rongCarrier.service.BwFundRecordService#deleteBwFundRecord(com.waterelephant.rongCarrier.entity.BwFundRecord)
	 */
	@Override
	public boolean deleteBwFundRecord(BwFundRecord bwFundRecord) throws Exception {
		return mapper.delete(bwFundRecord) > 0;
	}

	/**
	 * 融360 - 公积金 - 批量保存
	 * 
	 * @see com.waterelephant.rongCarrier.service.BwFundRecordService#saveList(List,
	 *      long, long)
	 */
	@Override
	public void saveList(List<FundFlow> flowList, BwFundInfo bwFundInfo, long orderId) throws Exception{
		// 拼接SQL
		List<String> sqlList = new ArrayList<>();
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < flowList.size(); i++) {
			FundFlow fundFlow = flowList.get(i);
			sBuilder.append("(");
			sBuilder.append("    '" + bwFundInfo.getIdCard() + "',"); // id_card
			sBuilder.append("    '" + fundFlow.getPay_date() + "',"); // pay_date
			sBuilder.append("    '" + fundFlow.getStart_date() + "',"); // start_date
			sBuilder.append("    '" + fundFlow.getEnd_date() + "',"); // end_date
			sBuilder.append("    '" + fundFlow.getBase_rmb() + "',"); // base_rmb
			sBuilder.append("    '" + fundFlow.getCom_rmb() + "',"); // com_rmb
			sBuilder.append("    '" + fundFlow.getPer_rmb() + "',"); // per_rmb
			sBuilder.append("    '" + fundFlow.getBalance_rmb() + "',"); // balance_rmb
			sBuilder.append("    '" + fundFlow.getMonth_rmb() + "',"); // month_rmb
			sBuilder.append("    '" + bwFundInfo.getComName() + "',"); // com_name
			sBuilder.append("    '" + fundFlow.getPay_type() + "',"); // pay_type
			sBuilder.append("    '" + fundFlow.getFlow_type() + "',"); // flow_type
			sBuilder.append("    '" + bwFundInfo.getId() + "',"); // fund_info_id
			sBuilder.append("    '" + orderId + "',"); // order_id
			sBuilder.append("    '" + fundFlow.getOut_month_rmb() + "',"); // out_month_rmb
			sBuilder.append("    '" + fundFlow.getIn_month_rmb() + "'"); // in_month_rmb
			sBuilder.append(")");

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
			sBuilder2.append("INSERT INTO  bw_fund_record");
			sBuilder2.append(" (");
			sBuilder2.append("     `id_card`,");
			sBuilder2.append("     `pay_date`,");
			sBuilder2.append("     `start_date`,");
			sBuilder2.append("     `end_date`,");
			sBuilder2.append("     `base_rmb`,");
			sBuilder2.append("     `com_rmb`,");
			sBuilder2.append("     `per_rmb`,");
			sBuilder2.append("     `balance_rmb`,");
			sBuilder2.append("     `month_rmb`,");
			sBuilder2.append("     `com_name`,");
			sBuilder2.append("     `pay_type`,");
			sBuilder2.append("     `flow_type`,");
			sBuilder2.append("     `fund_info_id`,");
			sBuilder2.append("      `order_id`,");
			sBuilder2.append("    `out_month_rmb`,");
			sBuilder2.append("     `in_month_rmb`");
			sBuilder2.append(")");
			sBuilder2.append("VALUES");
			sBuilder2.append(sql);

			sqlMapper.insert(sBuilder2.toString());
		}
	}

}
