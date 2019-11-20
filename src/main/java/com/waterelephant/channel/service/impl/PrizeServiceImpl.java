package com.waterelephant.channel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.channel.service.PrizeService;
import com.waterelephant.entity.ActivityDiscountInfo;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.SqlMapper;

@Service
public class PrizeServiceImpl implements PrizeService {

	@Autowired
	private SqlMapper SqlMapper;

	/**
	 * 保存pv记录
	 */
	@Override
	public void savePvRecord(String ip, int activity_id) {
		String sql = "insert into activity_pv_record (activity_id,ip,create_time) values (" + activity_id + ",'" + ip
				+ "','" + DateUtil.getCurrentDateString(DateUtil.yyyy_MM_dd_HHmmss) + "')";
		SqlMapper.insert(sql);
	}

	/**
	 * 查询奖品列表
	 * 
	 * @param activity_id
	 * @return
	 */
	@Override
	public List<ActivityDiscountInfo> getPrizeList(int activity_id) {
		String sql = "select discount_id,bonus_amount,loan_amount,img,type,prize_name from activity_discount_info where activity_id = "
				+ activity_id + " order by sort";
		System.out.println(sql);
		return SqlMapper.selectList(sql, ActivityDiscountInfo.class);
	}

	/**
	 * 查询中奖记录
	 * 
	 * @param activity_id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPrizeRecordList(int activity_id) {
		String sql = "select c.id,c.prize_id,c.grant_status,c.create_time,d.prize_name,b.phone from activity_draw_record c "
				+ " left join bw_borrower b on c.borrower_id = b.id "
				+ " left join activity_discount_info d on c.prize_id = d.discount_id "
				+ " where c.is_winning = 1 and c.activity_id = " + activity_id + " limit 0,30";
		return SqlMapper.selectList(sql);
	}

	/**
	 * 查询此人的上次抽中实物填写的联系人和联系电话
	 * 
	 * @param borrower_id
	 * @param activity_id
	 * @return
	 */
	@Override
	public Map<String, Object> getContactInfo(String borrower_id, int activity_id) {
		String sql = "select contacts_name,contacts_phone from activity_draw_record where borrower_id = " + borrower_id
				+ " and activity_id = " + activity_id + " order by create_time desc limit 1";
		return SqlMapper.selectOne(sql);
	}

	/**
	 * 查询今天抽奖次数
	 * 
	 * @see com.waterelephant.channel.service.PrizeService#getDrawCount(java.lang.String)
	 */
	@Override
	public int getDrawCount(String borrower_id) {
		String sql = "select count(1) from activity_draw_record where to_days(create_time) = to_days(now()) and borrower_id = "
				+ borrower_id;
		return SqlMapper.selectOne(sql, Integer.class);
	}

	/**
	 * 查询所有奖品列表
	 * 
	 * @see com.waterelephant.channel.service.PrizeService#getPrizeListAll(int)
	 */
	@Override
	public List<ActivityDiscountInfo> getPrizeListAll(int activityId) {
		String sql = "select discount_id,activity_id,bonus_amount,loan_amount,instructions,probability,type,prize_name,prize_surplus,img from activity_discount_info where activity_id = "
				+ activityId;
		return SqlMapper.selectList(sql, ActivityDiscountInfo.class);
	}

	/**
	 * 查询优惠券的奖品列表
	 * 
	 * @see com.waterelephant.channel.service.PrizeService#getPrizeListCoupon(int)
	 */
	@Override
	public List<ActivityDiscountInfo> getPrizeListCoupon(int activityId) {
		String sql = "select discount_id,activity_id,bonus_amount,loan_amount,instructions,probability,type,prize_name,prize_surplus,img from activity_discount_info where type in (1,3,4) and activity_id = "
				+ activityId;
		return SqlMapper.selectList(sql, ActivityDiscountInfo.class);
	}

	/**
	 * 新增抽奖记录
	 * 
	 * @see com.waterelephant.channel.service.PrizeService#saveRrawRecord(java.util.Map)
	 */
	@Override
	public void saveRrawRecord(Map<String, Object> recordMap) {
		String sql = "insert into activity_draw_record (borrower_id,activity_id,is_winning,prize_id,grant_status,create_time) values ("
				+ recordMap.get("borrower_id") + "," + recordMap.get("activity_id") + "," + recordMap.get("is_winning")
				+ "," + recordMap.get("prize_id") + "," + recordMap.get("grant_status") + ",'"
				+ DateUtil.getCurrentDateString(DateUtil.yyyy_MM_dd_HHmmss) + "')";
		SqlMapper.insert(sql);
	}

	/**
	 * 查询该用户之前提交过审核的订单数
	 * 
	 * @see com.waterelephant.channel.service.PrizeService#getApplyCount(java.lang.String)
	 */
	@Override
	public int getApplyCount(String borrower_id) {
		String sql = "select count(1) from bw_order where submit_time is not null and borrower_id = " + borrower_id;
		return SqlMapper.selectOne(sql, Integer.class);
	}

	/**
	 * 修改实物数量
	 * 
	 * @see com.waterelephant.channel.service.PrizeService#updatePrizeCount(java.lang.Integer)
	 */
	@Override
	public int updatePrizeCount(Integer discountId) {
		String sql = "update activity_discount_info set prize_surplus = prize_surplus - 1 where discount_id = "
				+ discountId;
		return SqlMapper.update(sql);
	}

	/**
	 * 根据用户id查询用户手机号
	 * 
	 * @see com.waterelephant.channel.service.PrizeService#getPhoneByBorrowerId(java.lang.String)
	 */
	@Override
	public String getPhoneByBorrowerId(String borrower_id) {
		String sql = "select phone from bw_borrower where id = " + borrower_id;
		return SqlMapper.selectOne(sql, String.class);
	}

	/**
	 * 查询免息券信息
	 * 
	 * @see com.waterelephant.channel.service.PrizeService#getMianXiDiscountInfo()
	 */
	@Override
	public ActivityDiscountInfo getMianXiDiscountInfo() {
		String sql = "select discount_id,activity_id,bonus_amount,number,loan_amount,invited_number,create_time,instructions,"
				+ "probability,img,type,prize_name,prize_total,prize_surplus,update_time,is_open"
				+ " from activity_discount_info where type = 4 order by create_time desc limit 1";
		return SqlMapper.selectOne(sql, ActivityDiscountInfo.class);
	}

}
