package com.waterelephant.channel.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.ActivityDiscountInfo;

public interface PrizeService {

	/**
	 * 保存pv记录
	 */
	public void savePvRecord(String ip, int activity_id);

	/**
	 * 查询奖品列表
	 * 
	 * @param activity_id
	 * @return
	 */
	public List<ActivityDiscountInfo> getPrizeList(int activity_id);

	/**
	 * 查询中奖记录
	 * 
	 * @param activity_id
	 * @return
	 */
	public List<Map<String, Object>> getPrizeRecordList(int activity_id);

	/**
	 * 查询此人的上次抽中实物填写的联系人和联系电话
	 * 
	 * @param borrower_id
	 * @param activity_id
	 * @return
	 */
	public Map<String, Object> getContactInfo(String borrower_id, int activity_id);

	/**
	 * 查询抽奖次数
	 * 
	 * @param borrower_id
	 * @return
	 */
	public int getDrawCount(String borrower_id);

	/**
	 * 查询所有奖品列表
	 * 
	 * @param activityId
	 * @return
	 */
	public List<ActivityDiscountInfo> getPrizeListAll(int activityId);

	/**
	 * 查询优惠券的奖品列表
	 * 
	 * @param activityId
	 * @return
	 */
	public List<ActivityDiscountInfo> getPrizeListCoupon(int activityId);

	/**
	 * 新增抽奖记录
	 * 
	 * @param recordMap
	 */
	public void saveRrawRecord(Map<String, Object> recordMap);

	/**
	 * 查询该用户之前提交过审核的订单数
	 * 
	 * @param borrower_id
	 * @return
	 */
	public int getApplyCount(String borrower_id);

	/**
	 * 修改实物数量
	 * 
	 * @param discountId
	 */
	public int updatePrizeCount(Integer discountId);

	/**
	 * 根据用户id查询用户手机号
	 * 
	 * @param borrower_id
	 * @return
	 */
	public String getPhoneByBorrowerId(String borrower_id);

	/**
	 * 查询免息券信息
	 * 
	 * @return
	 */
	public ActivityDiscountInfo getMianXiDiscountInfo();

}
