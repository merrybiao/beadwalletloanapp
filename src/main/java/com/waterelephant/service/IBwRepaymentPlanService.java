package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.entity.lianlian.NotifyResult;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.dto.RepayInfoDto;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 借款工单service
 * 
 * @author lujilong
 *
 */
public interface IBwRepaymentPlanService {
	/**
	 * 
	 * 
	 * @param orderId
	 * @return
	 */
	public BwRepaymentPlan getLastRepaymentPlanAndXudaiByOrderId(Long orderId);

	/**
	 * 根据订单id查询展期详情
	 * 
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> findBwRepaymentPlanAndDetailByOrderId(Long orderId);

	/**
	 * 查询所有还款计划
	 * 
	 * @param orderId
	 * @return
	 */
	List<BwRepaymentPlan> listBwRepaymentPlanByOrderId(Long orderId);

	BwRepaymentPlan findBwRepaymentPlanByAttr(BwRepaymentPlan bwRepaymentPlan);

	List<BwRepaymentPlan> findBwRepaymentPlanByRepay(BwRepaymentPlan bwRepaymentPlan);

	PageResponseVo<BwRepaymentPlan> findBwRepaymentPlanPage(PageRequestVo pageRequestVo);

	List<BwRepaymentPlan> findBwRepaymentPlanByExample(Example example);

	/**
	 * 查询未还款计划
	 * 
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> findBwRepaymentPlanByOrderId(Long orderId);

	/**
	 * 根据工单查询还款计划信息，包含关联产品信息
	 * 
	 * @param orderId
	 * @param calcCoupon 计算优惠金额
	 * @return
	 */
	JSONObject findRepaymentPlanInfoByOrderId(Long orderId, boolean calcCoupon);

	// 根据工单id统计已还款的本金和利息
	Map<String, Object> sumCorpusAndAccrualByOrderId(Long orderId);

	Map<String, Object> sumRepayMoneyByOrderIdAndStatusId(Long orderId, int repayStatus);

	/**
	 * 根据工单信息生成借款人还款计划
	 * 
	 * @param order
	 */
	void saveRepaymentPlanByOrder(BwOrder order);

	/**
	 * 根据工单信息和指定还款时间生成借款人还款计划
	 * 
	 * @param order 工单信息
	 * @param repayDate 还款日期
	 */
	void saveRepaymentPlanByOrderAndReapyDate(BwOrder order, Date repayTime);

	/**
	 * 根据工单信息和指定还款时间生成借款人还款计划
	 * 
	 * @param order 工单信息
	 * @param repayDate 还款日期
	 */
	// void saveRepaymentPlanByOrderAndReapyDateNew(BwOrder order, Date repayTime);

	BwRepaymentPlan getPlan(Long orderId, int num);

	List<BwRepaymentPlan> findRepaymentPlanByExample(Example example);

	BwRepaymentPlan getBwRepaymentPlanByOrderId(Long orderId);

	/**
	 * 查找待展期工单号集合
	 * 
	 * @return
	 */
	List<String> findZhanqiOrderNo();

	/**
	 * 修改
	 * 
	 * @param plan
	 */
	void update(BwRepaymentPlan plan);

	boolean findBwRepaymentPlanByOrderNo(String orderNO);

	BwRepaymentPlan getLastRepaymentPlanByOrderId(Long orderId);

	/**
	 * 连连还款回调
	 * 
	 * @param notifyResult
	 * @return
	 */
	NotifyNotice updateForLianlianPaymentNotify(NotifyResult notifyResult);

	/**
	 * 连连续贷回调
	 * 
	 * @param notifyResult
	 * @return
	 * @throws Exception 
	 */
	NotifyNotice updateForLianlianXudaiNotify(NotifyResult notifyResult) throws Exception;

	/**
	 * 获取某个时间之后已续贷次数
	 * 
	 * @param orderId
	 * @param afterDate
	 * @return
	 */
	int getXuDaiCountAfterDate(Long orderId, Date afterDate);

	/**
	 * 获取某个时间之后已续贷次数
	 * 
	 * @param orderId
	 * @return
	 */
	int getXuDaiCountAfterDate(Long orderId);

	/**
	 * 根据工单ID获取最早一期未还款的还款计划
	 * 
	 * @param orderId
	 * @return
	 */
	BwRepaymentPlan getEarlyNotRepaymentPlan(Long orderId);

	/**
	 * 根据工单ID获取还款计划，number升序排序
	 * 
	 * @param orderId
	 * @param repayStatusList
	 * @return
	 */
	List<BwRepaymentPlan> getRepaymentPlanList(Long orderId, List<Object> repayStatusList);

	/**
	 * 查询还款计划还款信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> getInstallmentRepaymentList(Long orderId);

	/**
	 * 查询逾期还款计划
	 * 
	 * @param orderId
	 * @return
	 */
	List<BwRepaymentPlan> getOverdueRepaymentPlanList(Long orderId);

	/**
	 * 查询所有未还款还款计划
	 * 
	 * @param orderId
	 * @return
	 */
	public List<BwRepaymentPlan> getOutstandRepaymentPlan(Long orderId);

	/**
	 * 查询当期还款期数
	 * 
	 * @param orderId
	 * @return
	 */
	Integer getCurrentNumber(Long orderId);

	void saveBwRepaymentPlan(BwRepaymentPlan plan);

	void deleteBwRepaymentPlan(Long planId);

	BwRepaymentPlan getBwRepaymentPlanByPlanId(Long planId);

	String saveRepaymentPlanByOrderAndReapyDateCapital(BwOrder order, String repayDate);

	String saveRepaymentPlanByOrderAndReapyDateNew(BwOrder order, Date repayDate);

	/**
	 * 获取非展期还款计划
	 * 
	 * @param orderId
	 * @param productType
	 * @return
	 */
	List<BwRepaymentPlan> selectNotZhanqiPlan(Long orderId, Integer productType);

	void saveRepaymentPlanByOrderAndReapyDateNew(BwOrder order, Date repayTime,
			BwProductDictionary bwProductDictionary);
	
	/**
	 * 根据订单id查询还款状态为已还款即状态repayStatus为2的还款计划
	 * @param orderId
	 * @param repayStatus
	 * @return
	 */
	BwRepaymentPlan findRepaymentPlanByOrderIdAndRepayStatus(Long orderId,Integer repayStatus);
	
	
	/**
	 * 根据订单id和还款状态查询还款计划
	 * @param orderId
	 * @param repayStatus
	 * @return
	 */
	List<BwRepaymentPlan> findRepaymentPlanListByOrderIdAndRepayStatus(Long orderId,Integer repayStatus);
	
	
	/**
	 * 根据订单号查询所有状态不为展期的还款计划
	 * @param orderId
	 * @return
	 */
	List<BwRepaymentPlan> findRepaymentPlanListByOrderId(Long orderId);

	/**
	 * 保存自动代扣信息到redis
	 *
	 * @param orderId
	 */
    void saveAutoPlan2Redis(Long orderId);

	/**
	 * 获取回调成功或失败通知三方数据，回调处理之前调用
	 *
	 * @param bwOrder
	 * @param payMoney
	 * @param payStatus 1.处理中，2.成功，3.失败
	 * @param notifyMsg 三方返回的msg
	 * @return 返回需要保存的数据，key:redis的键，value:redis的值，处理完后存redis
	 */
	Map<String, String> getNotifyThirdData(BwOrder bwOrder, Double payMoney, int payStatus, String notifyMsg);

	/**
	 * 通知三方
	 *
	 * @param notifyThirdDataMap 先调用getNotifyThirdData获取数据
	 */
	void notifyThird(Map<String, String> notifyThirdDataMap);

    RepayInfoDto selectAutoRepayByRepayId(Long repayId);
}
