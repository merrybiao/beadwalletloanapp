package com.waterelephant.service.impl;

import java.util.Date;

import com.waterelephant.dto.RepaymentBatch;
import com.waterelephant.service.BwOrderRepaymentBatchDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwPlatformRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwPlatformRecordService;

@Service
public class BwPlatformRecordServiceImpl extends BaseService<BwPlatformRecord, Long>
		implements BwPlatformRecordService {

	@Autowired
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;

	@Override
	public int saveBwPlatFormRecord(BwPlatformRecord bwPlatformRecord) {
		return mapper.insert(bwPlatformRecord);
	}

	@Override
	public int saveOrUpdateByTradeNo(BwPlatformRecord bwPlatformRecord) {
		int insertCount = 0;
		if (bwPlatformRecord != null) {
			String tradeNo = bwPlatformRecord.getTradeNo();
			BwPlatformRecord queryRecord = null;
			if (StringUtils.isNotEmpty(tradeNo)) {
				queryRecord = sqlMapper.selectOne(
						"select * from bw_platform_record where trade_no=#{trade_no} order by id desc limit 1", tradeNo,
						BwPlatformRecord.class);
			}
			if (queryRecord != null) {
				bwPlatformRecord.setId(queryRecord.getId());
				insertCount = mapper.updateByPrimaryKeySelective(bwPlatformRecord);
			} else {
				insertCount = mapper.insertSelective(bwPlatformRecord);
			}
		}
		return insertCount;
	}

	@Override
	public BwPlatformRecord saveBwPlatFormRecordByPayNotify(Long orderId, String tradeNo, Double tradeAmount,
			Integer tradeType, String outAccount, String outName, String inAccount, String inName, Date tradeTime,
			String tradeRemark, Integer tradeChannel) {
		BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
		bwPlatformRecord.setTradeNo(tradeNo);
		bwPlatformRecord.setTradeAmount(tradeAmount);// 交易金额
		bwPlatformRecord.setTradeType(tradeType);// 1划拨2转账
		bwPlatformRecord.setOutAccount(outAccount);
		bwPlatformRecord.setOutName(outName);
		bwPlatformRecord.setInAccount(inAccount);
		bwPlatformRecord.setInName(inName);
		bwPlatformRecord.setOrderId(orderId);
		bwPlatformRecord.setTradeTime(tradeTime);
		bwPlatformRecord.setTradeRemark(tradeRemark);
		bwPlatformRecord.setTradeChannel(tradeChannel);
		mapper.insert(bwPlatformRecord);
		return bwPlatformRecord;
	}

	/**
	 * 查询已还总额
	 * 
	 * @see com.waterelephant.service.BwPlatformRecordService#getAlreadyTotal(java.lang.Long)
	 */
	@Override
	public Double getAlreadyTotal(Long orderId) {
		RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService.getRepaymentBatch(orderId);
		return repaymentBatch.getAlreadyTotalBatchMoney();
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwPlatformRecordService#deleteBwPlatformRecord(java.lang.Long)
	 */
	@Override
	public void deleteBwPlatformRecord(Long orderId) {
		sqlMapper.delete("delete from bw_platform_record where order_id=" + orderId);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwPlatformRecordService#getBwPlatformRecordCount(com.waterelephant.entity.BwPlatformRecord)
	 */
	@Override
	public int getBwPlatformRecordCount(BwPlatformRecord entity) {
		int i = mapper.selectCount(entity);
		return i;
	}
}
