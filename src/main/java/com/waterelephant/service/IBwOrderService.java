package com.waterelephant.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.beadwallet.service.zhengxin91.entity.LoanInfo;
import com.waterelephant.dto.BwOrderInfoDto;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.dto.YiQiHaoApplyRequestDto;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.exception.BusException;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.vo.orderVO;

import tk.mybatis.mapper.entity.Example;

/**
 * 借款工单
 * 
 * @author lujilong
 *
 */
public interface IBwOrderService {
	/**
	 * 查询分期灰名单借款人拒绝30天后的时间
	 * 
	 * @param bwId
	 * @return
	 */
	Date findInstalmentBorrowWhiteTimeByBorrowId(String bwId, String productType);

	/**
	 * 查询单期灰名单借款人拒绝91天后的时间
	 * 
	 * @param bwId
	 * @return
	 */
	Date findBorrowWhiteTimeByBorrowId(String bwId);

	/**
	 * 根据借贷人id统计已还款工单的个数
	 * 
	 * @param bwId
	 * @return
	 */
	int findCountBwOrderByBorrowId(String bwId);

	/**
	 * 根据借贷人id查询工单状态为6，9，13的工单
	 * 
	 * @param bwid
	 * @return
	 */
	List<orderVO> getBwOrderByStatusId(String bwid);

	/**
	 * 
	 * 根据借款人id查询所有的工单获得最新创建的一条工单
	 * 
	 * @param bwId
	 * @return
	 */
	BwOrder findNewBwOrderByBwIdNotLimitStatusID(String bwId);

	BwOrder findBwOrderByAttr(BwOrder bwOrder);

	List<BwOrder> findBwOrderListByAttr(BwOrder bwOrder);

	PageResponseVo<BwOrder> findBwOrderPage(PageRequestVo reqVo);

	List<BwOrder> findBwOrderByExample(Example example);

	/**
	 * 根据条件查询工单并关联查询产品
	 * 
	 * @param example
	 * @return
	 */
	List<BwOrder> findBwOrderAndProductByExample(Example example);

	/**
	 * 根据工单ID查询工单信息
	 * 
	 * @param orderId 工单ID
	 * @return
	 */
	BwOrderInfoDto findBwOrderInfoByOrderId(Long orderId);

	/**
	 * 根据第三方工单号查询工单号
	 * 
	 * @param thirdOrderNo
	 * @return
	 */
	BwOrder findOrderNoByThirdOrderNo(String thirdOrderNo);

	/**
	 * 根据三方订单和渠道号查询工单
	 * 
	 * @param thirdOrderNo
	 * @return
	 */
	BwOrder findOrderByThirdOrderNoAndChannel(String thirdOrderNo, String channel);

	/**
	 * 新增工单
	 * 
	 * @param order 工单信息实体
	 * @return 添加结果
	 */
	boolean addBwOrder(BwOrder order);

	/**
	 * 修改工单
	 * 
	 * @param order 工单信息实体
	 * @return 修改结果
	 */
	int updateBwOrder(BwOrder order);

	int updateOrg(BwOrder bo);

	int updateChannel(Long orderId, String channel);

	/**
	 * 转账成功后 处理工单 及一系列操作
	 */
	// void updateBwOrderAndXuDai(Long id) throws BusException;

	/**
	 * 更具工单编号更新为人工审核
	 */
	int updateaUditStatusOrderId(Long orderId);

	Long add(BwOrder order);

	/**
	 * 查询待推送的工单信息
	 */
	List<YiQiHaoApplyRequestDto> findApplyRequest();

	/**
	 * 
	 * @Title: findsignStatusByOrderId @Description: TODO(根据工单编号查询银行卡签约状态) @param @param orderId @param @return
	 *         设定文件 @return int 返回类型 @throws
	 */
	int findsignStatusByOrderId(Long orderId);

	/**
	 * 根据主键查找工单信息
	 * 
	 * @param orderId 主键
	 * @return 工单信息
	 */
	BwOrder findBwOrderById(String orderId);

	void update(BwOrder bwOrder);

	void updateStrvice(String orderId);

	/**
	 * 根据用户id查询最近一条工单id
	 */
	Long findBorrwerIdByOrderId(Long borrwer);

	AppResponseResult initbwTransferBu(String orderId, String userId, String string, BwBorrower bwBorrow)
			throws Exception;

	/**
	 * 根据工单id查询工单状态
	 * 
	 * @param orderId
	 * @return
	 */
	Integer findOrderStatusByOrderId(Long orderId);

	/**
	 * 根据工单查询工单更新时间
	 * 
	 * @param orderId
	 * @return
	 */
	Date findUpdateTimeByOrderId(Long orderId);

	/**
	 * 更具工单编号更新工单状态
	 * 
	 * @param orderId 工单id
	 * @param status 工单状态
	 * @return
	 */
	int updateStatusByOrderId(Long orderId, int status);

	/**
	 * 根据借款人id查询工单状态
	 * 
	 * @param borrowerId
	 */
	Integer findOrderStatusByBorrowerId(Long borrowerId);

	String getBwOrderById(Long orderId);

	/**
	 * 根据借款人查询最近一条工单
	 * 
	 * @param borrwer
	 * @return
	 */
	BwOrder findOrderIdByBorrwerId(Long borrwer);

	/**
	 * 根据借款人查询最近一条工单
	 * 
	 * @param borrwer
	 * @param excludeStatusIds 排除的状态
	 * @return
	 */
	BwOrder findOrderIdByBorrwerId(Long borrwerId, int[] excludeStatusIds);

	List<String> findPathByOrderId(Long orderId);

	Long findOrderIdByNo(String orderNo);

	/**
	 * 查询当天的总单量
	 * 
	 * @return
	 */
	public Long findTodayOrderCount();

	/**
	 * 查询当天的总放款额
	 * 
	 * @return
	 */
	public Double findTodayOrderAmount();

	/**
	 * 查询初审单量
	 * 
	 * @return
	 */
	public Long findCheckOrderCount();

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年3月7日
	 * @description
	 * @param
	 * @return Long
	 */
	public Long findPathByChannel(Long orderId);

	/**
	 * 查询未签约单量
	 * 
	 * @return
	 */
	public Long findSignOrderCount();

	/**
	 * 查询还款单量
	 * 
	 * @return
	 */
	public Long findRepayOrderCount();

	/**
	 * 查询未推送成功及未满标返回的单量
	 * 
	 * @return
	 */
	public Long findNotPushOrderCount();

	/**
	 * 查询最近一周的总单量
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findWeekOrderCount();

	/**
	 * 根据判断未完工单
	 * 
	 * @author 崔雄健
	 * @date 2017年3月10日
	 * @description
	 * @param
	 * @return
	 */
	public BwOrder findCheckOrder(String phone);

	/**
	 * 征信信息共享
	 * 
	 * @param orderId
	 * @return
	 */
	public List<LoanInfo> zhengxin(String orderId);

	/**
	 * 初审、终审、待审批确认、待债匹、债匹中、待放款、还款中、逾期
	 * 
	 * @param phone
	 * @return
	 */
	public Long findProOrder(String borrowerId);

	/**
	 * 初审、终审、待审批确认、待债匹、债匹中、待放款、还款中、逾期
	 * 
	 * @param phone
	 * @return
	 */
	public Long findProcessOrder(String borrowerId);

	/**
	 * 是否有未完成工单(结束、拒绝、撤回)
	 * 
	 * @param borrowerId
	 * @return
	 */
	boolean hasNotCompleteOrder(Long borrowerId);

	/**
	 * 获取最近已经结束工单
	 * 
	 * @param borrowerId
	 * @return
	 */
	BwOrder getLastEndOrder(Long borrowerId);

	/**
	 * 获得最新的工单
	 * 
	 * @param bwId
	 * @return
	 */
	BwOrder findNewBwOrderByBwId(String bwId);

	/**
	 * 判断用户是否可以一键拿钱
	 * 
	 * @param bwId
	 * @return 若可以一键拿钱，返回的result是用户最后一次结束的工单
	 */
	AppResponseResult canKeyMoney(Long bwId);

	/**
	 * 一键拿钱<br />
	 * 1.未超过31天直接跳过认证到马上拿钱页面<br />
	 * 2.距离上次运营商认证超过31天，则跳转认证页面，运营商、芝麻信用重新认证，其他两个默认已认证
	 * 
	 * @param borrowerId
	 * @param channelId
	 * @return
	 * @throws BusException
	 */
	AppResponseResult updateAndKeyMoney(Long borrowerId, Integer channelId) throws BusException;

	/**
	 * 通过订单entry获得订单记录实体BorrowRcordVO<br />
	 * 分已还款和未还款两种情况，逾期金额
	 * 
	 * @param bwOrder
	 * @return
	 */
	AppResponseResult getBorrowRcprdVP(BwOrder bwOrder);

	/**
	 * 获取最近一个工单
	 * 
	 * @param borrowerId
	 * @param productType
	 * @param statusIdList
	 * @return
	 */
	BwOrder selectLastOrder(Long borrowerId, Integer productType, List<Object> statusIdList);

	/**
	 * 
	 * @param bwId
	 * @return
	 */
	List<orderVO> getListOrderData(String bwId);

	/**
	 * 根据借款人id查询所有工单信息，包括单期和分期
	 * 
	 * @param bwId
	 * @return
	 */
	List<BwOrder> fandAllOrderBy(String bwId);

	/**
	 * 马上拿钱
	 * 
	 * @param orderId
	 * @param repayType
	 * @return
	 * @throws Exception
	 */
	AppResponseResult updateAndTakeMoney(Long orderId, Integer repayType) throws Exception;

	Map<String, Object> findCapitalKoudaiC(Long orderId);

	Map<String, Object> findCapitalKoudaiD(Long orderId);

	/**
	 * 根据工单id查询紧急联系人
	 * 
	 * @param orderId
	 * @return
	 */
	BwPersonInfo findBwPersonInfoByOrderId(Long orderId);

	/**
	 * 根据订单编号查询订单信息
	 * 
	 * @param orderNo
	 * @return
	 */
	BwOrder findBwOrderByOrderNo(String orderNo);

	/**
	 * 根据渠道和手机号码查最近订单
	 * 
	 * @param phone
	 * @param channelId
	 * @return
	 */
	BwOrder findBwOrderByPhoneAndChannel(String phone, Integer channelId);

	/**
	 * (code:saas)
	 * 
	 * 根据创建时间、页数、订单状态查询borrowerId
	 * 
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNum
	 * @param orderStatus
	 * @return
	 */
	List<BwOrder> findBwOrderByParams(String startTime, String endTime, int pageSize, int pageNum, int orderStatus);

	/**
	 * 根据创建时间、订单状态查询订单数量
	 * 
	 * @param startTime
	 * @param endTime
	 * @param orderStatus
	 * @return
	 */
	Integer findCountByParams(String startTime, String endTime, int orderStatus);

	/**
	 * (code:saas)
	 * 
	 * 根据更新时间、页数、订单状态查询borrowerId
	 * 
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNum
	 * @param orderStatus
	 * @return
	 */
	List<BwOrder> findBwOrderByParams2(String startTime, String endTime, int pageSize, int pageNum, int orderStatus);

	/**
	 * 根据更新时间、订单状态查询订单数量
	 * 
	 * @param startTime
	 * @param endTime
	 * @param orderStatus
	 * @return
	 */
	Integer findCountByParams2(String startTime, String endTime, int orderStatus);

	/**
	 * 根据创建时间查询
	 * 
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	List<Long> findByCreateTime(String startTime, String endTime, int pageSize, int pageNum);

	/**
	 * (code:saas)
	 * 
	 * 查询订单状态为6，商户id不为0的用户
	 * 
	 * @param merchantId
	 * @return
	 */
	List<BwBorrower> findByMerchantId(Long merchantId);

	/**
	 * (code:s2s)
	 * 
	 * 根据用户id和订单状态，以订单id倒序查询一条数据
	 * 
	 * @param borrowerId
	 * @param orderStatus
	 * @return
	 */
	Long findOrderId(Long borrowerId, Integer orderStatus);

	Integer queryLastOverdue(Long borrowerId, String days);

	Integer queryNowOverdue(Long borrowerId);
	/**
	 * 查询进行中的工单
	 * String inStatus = "9,13";//待还款、逾期
	 * @param borrowerId 申请人ID
	 * @return
	 */
	Integer queryOrderIng(Long borrowerId);
	
	Integer querySmOrderIng(Long borrowerId);
	/**
	 * 
	 * @param borrowerId
	 * @return
	 */
	Integer queryUnderwayOrderCount(Long borrowerId);

	Integer queryUnclearedOrderCount(Long borrowerId);

	Integer queryOverdueOrderCount(Long borrowerId);
}