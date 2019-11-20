package com.waterelephant.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.entity.BwBorrower;

/**
 * @author
 * @since 2018-07-23
 */
public interface IBwBorrowerService {

	/**
	 * 根据借款对象查询借款对象（参数借款对象的属性为查询条件）
	 * 
	 * @param borrower
	 * @return
	 */
	@Deprecated
	BwBorrower findBwBorrowerByAttr(BwBorrower borrower);

	/**
	 * 添加借款对象
	 * 
	 * @param borrower
	 * @return
	 */
	int addBwBorrower(BwBorrower borrower);

	/**
	 * 更新借款对象
	 * 
	 * @param borrower
	 * @return
	 */
	int updateBwBorrower(BwBorrower borrower);

	/**
	 * 更新借款对象，不全局更新字段
	 * 
	 * @param borrower
	 * @return
	 */
	int updateBwBorrowerSelective(BwBorrower borrower);

	/**
	 * 根据id查询借款人对象
	 * 
	 * @param borrower
	 * @return
	 */
	BwBorrower findBwBorrowerById(Object obj);

	/**
	 * 根据借款人id更新借款人邀请码
	 */
	int updateBwBorrowerInviteCode(BwBorrower borrower);

	/**
	 * 融360 老用户过滤
	 */
	BwBorrower oldUserFilter(String idCard, String name);

	/**
	 * 融360 老用户过滤
	 */
	BwBorrower oldUserFilter(String phone, String idCard, String name);

	/**
	 * 融360 老用户过滤
	 */
	BwBorrower oldUserFilter2(String name, String phone, String id_number);

	/**
	 * 根据手机号过滤
	 */
	BwBorrower oldUserFilter3(String phone);

	/**
	 * 根据工单编号查询借款人id
	 * 
	 * @param orderId
	 * @return
	 */
	Long findBorrowerIdByOrderNo(Long orderId);

	int updateBorrwerAuthStep(Long orderId);

	Map<String, Object> getopen(String orderId);

	// void updateRenew(Long orderId, BwOrder order, String orderNo, Double
	// borrowAmount, BwRepaymentPlan
	// bwRepaymentPlan)throws BusException ;

	BwBorrower findBwBorrowerByOrderId(Long id);

	/**
	 * 根据订单id查询借款人ID
	 * 
	 * @param orderId
	 * @return
	 */
	BwBorrower findBwBorrowerIdByOrderId(Long orderId);

	/**
	 * 统计邀请人数
	 * 
	 * @param borrowerId 被统计人Id
	 * @return
	 */
	int countInvite(Long borrowerId);

	/**
	 * 根据借款人id查询借款人姓名和身份证号
	 * 
	 * @param borrowerId
	 * @return
	 */
	BwBorrower findNameAndIdCardById(Long borrowerId);

	/**
	 * 根据活动时间、活动ID和最小邀请人数量(包含)统计查询用户邀请好友人数
	 * 
	 * @param activityInfo 活动信息，包含：主键ID，判断用户是否有未发放活动；活动主键ID，判断用户是否有未发放活动；活动开始和结束时间
	 * @param minInvitedNum 最小邀请人数量，为空则不加此条件
	 * @return 返回统计数据集合，格式如：[{"borrower_id":1,"invited_num":3}]
	 */
	List<Map<String, Object>> getInvitedNumStatis(ActivityInfo activityInfo, Integer minInvitedNum);

	List<BwBorrower> findBwBorrowerListByIdCard(BwBorrower borrower);

	void deleteBorrower(BwBorrower borrower);

	/**
	 * 检查新老用户
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 */
	BwBorrower checkOldUser(String idCard, String name);

	/**
	 * (code:sxyDrainageJob)查询特定时间段用户信息
	 * 
	 * @param startTime
	 * @param endTime
	 * @param channelId
	 */
	List<BwBorrower> findBorrowerByCreateTimeAndChannel(String startTime, String endTime, String channelId);

	BwBorrower selectLogin(String phone, int flag);

	/**
	 * 根据提供的渠道检查新老用户(手机号，身份证掩码)
	 * 
	 */
	BwBorrower checkOldUserByChannel(String phone, String idCard, String name, Integer channelId);

	/**
	 * 查询用户信息和经纬度
	 * 
	 * @param borrowerId
	 * @return
	 */
	Map<String, Object> findBorrowerAndPositionXY(Long borrowerId);

	List<BwBorrower> selectSignListByIdCard(String idCard);

	/**
	 * 获取用户(手机掩码，姓名)
	 * 
	 * @param phone
	 * @param name
	 * @return
	 */
	BwBorrower findBwBorrowerByPhoneAndName(String phone, String name);

	/**
	 * 根据身份证掩码+姓名查用户集合
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 */
	List<BwBorrower> checkOldUserList(String idCard, String name);

	List<BwBorrower> findBwBorrowerByPhoneORIdcard(String phone, String idCard);

	/**
	 * 根据手机号或者身份证
	 * @param phone
	 * @param idCard
	 * @return
	 */
	List<BwBorrower> findByPhoneOrIdcard(String phone, String idCard);
	
	
	Integer getAppid(Long borrorerId);
}
