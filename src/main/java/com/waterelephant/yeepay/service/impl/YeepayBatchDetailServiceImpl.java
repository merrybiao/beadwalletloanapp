package com.waterelephant.yeepay.service.impl;


import com.waterelephant.yeepay.entity.YeepayBatchCharge;
import com.waterelephant.yeepay.entity.YeepayBatchDetail;
import com.waterelephant.yeepay.service.YeepayBatchDetailService;
import com.waterelephant.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * Service层
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-22 10:21:09
 */
@Service
public class YeepayBatchDetailServiceImpl extends BaseService<YeepayBatchDetail,Long> implements YeepayBatchDetailService {

	private Logger logger = Logger.getLogger(YeepayBatchDetailServiceImpl.class);

	@Override
	public boolean saveYeepayBatchDetail(YeepayBatchDetail yeepayBatchDetail) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(yeepayBatchDetail) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean deleteYeepayBatchDetail(YeepayBatchDetail yeepayBatchDetail) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(yeepayBatchDetail) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean updateYeepayBatchDetail(YeepayBatchDetail yeepayBatchDetail) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.updateByPrimaryKeySelective(yeepayBatchDetail) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public YeepayBatchDetail findByMerchantBatchNoAndRequestNo(String merchantBatchNo, String requestNo) {
		Map<String, String> map = new HashMap<>();
		map.put("merchantBatchNo", merchantBatchNo);
		map.put("requestNo", requestNo);
		String sql = "select * from bw_yeepay_batch_detail where merchant_batch_no = #{merchantBatchNo} and request_no =#{requestNo}  LIMIT 1 ";
		return sqlMapper.selectOne(sql,map,YeepayBatchDetail.class);
		
		
	}

	@Override
	public YeepayBatchDetail findByRequestNo(String requestNo) {
		String sql = "select * from bw_yeepay_batch_detail where request_no =#{requestNo}  LIMIT 1 ";
		return sqlMapper.selectOne(sql,requestNo,YeepayBatchDetail.class);
		
	}


}
