package com.waterelephant.yeepay.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.waterelephant.service.BaseService;
import com.waterelephant.yeepay.entity.YeepayRefundDetail;
import com.waterelephant.yeepay.service.YeepayRefundDetailService;


@Service
public class YeepayRefundDetailServiceImp extends BaseService<YeepayRefundDetail,Long> implements YeepayRefundDetailService {

	private Logger logger = Logger.getLogger(YeepayRefundDetailServiceImp.class);
	
	@Override
	public boolean saveYeepayRefundDetail(YeepayRefundDetail yeepayRefundDetail) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(yeepayRefundDetail) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean updateYeepayRefundDetail(YeepayRefundDetail yeepayRefundDetail) {
		
		boolean isSuccess = false;
		try {
			isSuccess = mapper.updateByPrimaryKey(yeepayRefundDetail) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public YeepayRefundDetail findYeepayRefundDetailByRequestNo(String requestNo) {
		String sql = "select * from bw_yeepay_refund_detail where request_no = #{requestNo} LIMIT 1 ";
		return sqlMapper.selectOne(sql, requestNo, YeepayRefundDetail.class);
	}

}
