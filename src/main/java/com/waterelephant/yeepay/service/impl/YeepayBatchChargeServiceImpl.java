package com.waterelephant.yeepay.service.impl;


import com.waterelephant.yeepay.entity.YeepayBatchCharge;
import com.waterelephant.yeepay.service.YeepayBatchChargeService;
import com.waterelephant.service.BaseService;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;


/**
 * Service层
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-22 10:21:09
 */
@Service
public class YeepayBatchChargeServiceImpl extends BaseService<YeepayBatchCharge,Long> implements YeepayBatchChargeService {

	private Logger logger = Logger.getLogger(YeepayBatchChargeServiceImpl.class);

	@Override
	public boolean saveYeepayBatchCharge(YeepayBatchCharge yeepayBatchCharge) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(yeepayBatchCharge) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	
	
	@Override
	public boolean deleteYeepayBatchCharge(YeepayBatchCharge yeepayBatchCharge) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(yeepayBatchCharge) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public YeepayBatchCharge findByMerchantBatchNo(String merchantBatchNo) {
		String sql = "select * from bw_yeepay_batch_charge where merchant_batch_no = #{merchantBatchNo} LIMIT 1 ";
		return sqlMapper.selectOne(sql, merchantBatchNo, YeepayBatchCharge.class);
	}



	@Override
	public boolean updateYeepayBatchCharge(YeepayBatchCharge yeepayBatchCharge) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.updateByPrimaryKey(yeepayBatchCharge) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}
}
