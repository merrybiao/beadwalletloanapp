package com.waterelephant.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderStatusRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderStatusRecordService;
import com.waterelephant.utils.StringUtil;

@Service
public class BwOrderStatusRecordServiceImpl extends BaseService<BwOrderStatusRecord, Long>
		implements BwOrderStatusRecordService {
	private Logger logger = Logger.getLogger(BwOrderStatusRecordServiceImpl.class);

	@Override
	public void insertRecord(BwOrder order, String msg, String dialogStyle) {
		BwOrderStatusRecord bwOrderStatusRecord = new BwOrderStatusRecord();
		if (CommUtils.isNull(order) || StringUtil.isEmpty(msg) || StringUtil.isEmpty(dialogStyle)) {
			logger.info("添加失败，参数不能为空");
			return;
		}
		bwOrderStatusRecord.setDialogStyle(dialogStyle);
		bwOrderStatusRecord.setOrderId(StringUtil.toString(order.getId()));
		bwOrderStatusRecord.setOrderStatus(order.getStatusId());
		bwOrderStatusRecord.setEffective(ActivityConstant.BWORDERSTATUSRECORD_EFFECTIVE.EFFECTIVE_1);
		bwOrderStatusRecord.setMsg(msg);
		bwOrderStatusRecord.setCreateTime(new Date());
		sqlMapper.update("update bw_order_status_record set effective=0 where order_id=#{orderId}", order.getId());
		mapper.insert(bwOrderStatusRecord);
	}

	@Override
	public BwOrderStatusRecord getBwOrderStatusRecordByOrderId(String orderId) {
		String sql = "select * from bw_order_status_record where order_id = " + orderId + " and effective = "
				+ ActivityConstant.BWORDERSTATUSRECORD_EFFECTIVE.EFFECTIVE_1 + " limit 0,1";
		BwOrderStatusRecord bwOrderStatusRecord = sqlMapper.selectOne(sql, BwOrderStatusRecord.class);
		return bwOrderStatusRecord;
	}

	@Override
	public void updateRecord(BwOrderStatusRecord bwOrderStatusRecord) {
		mapper.updateByPrimaryKey(bwOrderStatusRecord);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwOrderStatusRecordService#deleteBwOrderStatusRecord(java.lang.Long)
	 */
	@Override
	public void deleteBwOrderStatusRecord(Long orderId) {
		sqlMapper.delete("delete from bw_order_status_record where order_id=" + orderId);

	}

}
