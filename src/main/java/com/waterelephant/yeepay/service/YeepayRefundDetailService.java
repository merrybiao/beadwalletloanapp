package com.waterelephant.yeepay.service;

import com.waterelephant.yeepay.entity.YeepayRefundDetail;

public interface YeepayRefundDetailService {
	
	/**
	 * 保存退款信息
	 * @param yeepayRefundDetail
	 * @return
	 */
	boolean saveYeepayRefundDetail(YeepayRefundDetail yeepayRefundDetail);
	
	/**
	 * 在调用退款查询接口后更改退款信息
	 * @param yeepayRefundDetail
	 * @return
	 */
	boolean updateYeepayRefundDetail(YeepayRefundDetail yeepayRefundDetail);
	
	
	/**
	 * 通过退款请求号定位到退款请求
	 * @param requestNo
	 * @return
	 */
	YeepayRefundDetail findYeepayRefundDetailByRequestNo(String requestNo);

}
