package com.waterelephant.yeepay.service;



import com.waterelephant.yeepay.entity.YeepayBatchDetail;

/**
 * Service接口
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-22 10:21:09
 */
public interface YeepayBatchDetailService {

	
	/**
	 * 保存
	 * @param yeepayBatchDetail
	 */
	boolean saveYeepayBatchDetail(YeepayBatchDetail yeepayBatchDetail);
	
	/**
	 * 修改？
	 * @param yeepayBatchDetail
	 */
	boolean deleteYeepayBatchDetail(YeepayBatchDetail yeepayBatchDetail);


	/**
	 * 更新扣款详情的状态
	 * @param yeepayBatchDetail
	 * @return
	 */
	boolean updateYeepayBatchDetail(YeepayBatchDetail yeepayBatchDetail);
	
	
	/**
	 * 通过商家批次号和扣款请求号查找到扣款记录
	 * @param merchantBatchNo
	 * @param requestNo
	 * @return
	 */
	YeepayBatchDetail findByMerchantBatchNoAndRequestNo(String merchantBatchNo,String requestNo);
	
	/**
	 * 单笔扣款根据请求号查询扣款实体
	 * @param requestNo
	 * @return
	 */
	YeepayBatchDetail findByRequestNo(String requestNo);
	

	
	
}
