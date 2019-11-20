package com.waterelephant.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beadwallet.service.zhengxin91.entity.LoanInfo;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.constants.OrderStatusConstant;
import com.waterelephant.constants.ParameterConstant;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.BwOrderInfoDto;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.dto.RepaymentBatch;
import com.waterelephant.dto.YiQiHaoApplyRequestDto;
import com.waterelephant.entity.ActivityDiscountDistribute;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwInsureInfo;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwPlatformRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.ExtraConfig;
import com.waterelephant.exception.BusException;
import com.waterelephant.installment.constants.InstallmentConstant;
import com.waterelephant.installment.service.OrderService;
import com.waterelephant.mapper.BwOrderMapper;
import com.waterelephant.service.ActivityDiscountDistributeService;
import com.waterelephant.service.ActivityDiscountUseageService;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwInsureInfoService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRepaymentBatchDetailService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.FuYouService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwOrderXuDaiService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.utils.YiQiHaoUtil;
import com.waterelephant.vo.BorrowRcordVO;
import com.waterelephant.vo.orderVO;

import cn.jpush.api.utils.StringUtils;
import net.sf.json.JSONObject;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional(rollbackFor = Exception.class)
public class BwOrderService extends BaseService<BwOrder, Long> implements IBwOrderService {
	private Logger logger = Logger.getLogger(BwOrderService.class);

	@Autowired
	private IBwOrderXuDaiService bwOrderXuDaiService;
	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private FuYouService fuYouService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private BwInsureInfoService bwInsureInfoService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private ActivityDiscountUseageService activityDiscountUseageService;
	@Autowired
	private ActivityDiscountDistributeService activityDiscountDistributeService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Autowired
	private BwOrderMapper bwOrderMapper;

	@Override
	public List<orderVO> getListOrderData(String bwid) {
		String sql = "select id as orderId,borrow_amount as borrowAmount,status_id as statusId,"
				+ "date_format(create_time, '%Y-%m-%d') as createTime ,borrow_number   as  borrowNumber "
				+ "  from bw_order where borrower_id = " + bwid
				+ " and status_id in (6,9,13)   ORDER BY  create_time  DESC ";
		logger.info("查询借款记录列表的sql： " + sql);

		List<orderVO> selectList = this.sqlMapper.selectList(sql, orderVO.class);
		if (selectList != null && selectList.size() > 0) {
			Iterator<orderVO> iterator = selectList.iterator();
			while (iterator.hasNext()) {
				orderVO orderVO = iterator.next();
				String borrowAmount = orderVO.getBorrowAmount();
				if (orderVO.getBorrowNumber() == null) {// 单期
					borrowAmount = "单期借款金额  " + borrowAmount;
					orderVO.setBorrowAmount(borrowAmount);
				} else {
					borrowAmount = "现金分期金额  " + borrowAmount;
					orderVO.setBorrowAmount(borrowAmount);
				}

			}
		}
		return selectList;
	}

	/**
	 * 查询单期灰名单借款人拒绝91天后的时间
	 *
	 * 
	 * @param bwId
	 * @return
	 */
	@Override
	public Date findBorrowWhiteTimeByBorrowId(String bwId) {
		// 查询当前借款人是否存在被拒工单，如果是永久被拒则永不创建工单，非永久被拒则一个月之后再次创建工单
		Example example1 = new Example(BwOrder.class);
		example1.createCriteria().andEqualTo("statusId", 7l).andEqualTo("borrowerId", bwId).andEqualTo("productType",
				OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
		example1.setOrderByClause(" createTime desc ");
		List<BwOrder> rejectList = this.findBwOrderByExample(example1);
		if (!CommUtils.isNull(rejectList)) {
			Long rejectOId = rejectList.get(0).getId();
			logger.info("借款人" + bwId + "的拒绝工单id：" + rejectOId);
			// 根据工单id查找拒绝记录
			BwRejectRecord record = new BwRejectRecord();
			record.setOrderId(rejectOId);
			record = bwRejectRecordService.findBwRejectRecordByAtta(record);
			Date blackTime = record.getCreateTime();// 黑名单时间
			logger.info("拒绝时间" + blackTime);
			// Date whiteTime = MyDateUtils.addMonths(blackTime, 3);//白名单时间
			Date whiteTime = MyDateUtils.addDays(blackTime, ActivityConstant.BLACKTIME);
			logger.info("白名单时间" + whiteTime);

			return whiteTime;
		}
		logger.info("没有查到白名单时间！");
		return null;
	}

	/**
	 * 查询分期灰名单借款人拒绝30天后的时间
	 *
	 * 
	 * @param bwId
	 * @return
	 */
	@Override
	public Date findInstalmentBorrowWhiteTimeByBorrowId(String bwId, String productType) {
		// 查询当前借款人是否存在被拒工单，如果是永久被拒则永不创建工单，非永久被拒则一个月之后再次创建工单
		Example example1 = new Example(BwOrder.class);
		example1.createCriteria().andEqualTo("statusId", 7l).andEqualTo("borrowerId", bwId).andEqualTo("productType",
				productType);
		example1.setOrderByClause(" createTime desc ");
		List<BwOrder> rejectList = this.findBwOrderByExample(example1);
		if (!CommUtils.isNull(rejectList)) {
			Long rejectOId = rejectList.get(0).getId();
			logger.info("借款人" + bwId + "的拒绝工单id：" + rejectOId);
			// 根据工单id查找拒绝记录
			BwRejectRecord record = new BwRejectRecord();
			record.setOrderId(rejectOId);
			record = bwRejectRecordService.findBwRejectRecordByAtta(record);
			Date blackTime = record.getCreateTime();// 黑名单时间
			logger.info("拒绝时间" + blackTime);
			// Date whiteTime = MyDateUtils.addMonths(blackTime, 3);//白名单时间
			Date whiteTime = MyDateUtils.addDays(blackTime, ActivityConstant.BLACKTIME);// 白名单时间 30天
			logger.info("白名单时间" + whiteTime);

			return whiteTime;
		}
		logger.info("没有查到白名单时间！");
		return null;
	}

	@Override
	public BwOrder findBwOrderByAttr(BwOrder bwOrder) {
		BwOrder queryBwOrder = mapper.selectOne(bwOrder);
		if (queryBwOrder != null) {
			Integer productId = queryBwOrder.getProductId();
			BwProductDictionary product = null;
			if (productId != null) {
				product = bwProductDictionaryService.findBwProductDictionaryById(productId);
			}
			if (product != null) {
				queryBwOrder.setTerm(product.getpTerm());
				queryBwOrder.setTermType(product.getpTermType());
			}
		}
		return mapper.selectOne(bwOrder);
	}

	@Override
	public List<BwOrder> findBwOrderListByAttr(BwOrder bwOrder) {

		return mapper.select(bwOrder);
	}

	@Override
	public PageResponseVo<BwOrder> findBwOrderPage(PageRequestVo reqVo) {

		return page(reqVo);
	}

	@Override
	public List<BwOrder> findBwOrderByExample(Example example) {

		return mapper.selectByExample(example);
	}

	@Override
	public List<BwOrder> findBwOrderAndProductByExample(Example example) {
		List<BwOrder> orderList = mapper.selectByExample(example);
		if (orderList != null && !orderList.isEmpty()) {
			Set<Integer> productIdSet = new HashSet<Integer>();
			for (BwOrder bwOrder : orderList) {
				Integer productId = bwOrder.getProductId();
				if (productId != null) {
					productIdSet.add(bwOrder.getProductId());
				}
			}
			if (!productIdSet.isEmpty()) {
				Example productExample = new Example(BwProductDictionary.class);
				Criteria productCriteria = productExample.createCriteria();
				productCriteria.andIn("id", new ArrayList<Object>(productIdSet));
				List<BwProductDictionary> productList = productService.selectByExample(productExample);
				if (productList != null && !productList.isEmpty()) {
					for (BwOrder bwOrder : orderList) {
						for (BwProductDictionary product : productList) {
							if (bwOrder.getProductId() != null
									&& NumberUtils.toInt(product.getId().toString()) == bwOrder.getProductId()) {
								bwOrder.setTerm(product.getpTerm());
								bwOrder.setTermType(product.getpTermType());
								break;
							}
						}
					}
				}
			}
		}
		return orderList;
	}

	@Override
	public BwOrderInfoDto findBwOrderInfoByOrderId(Long orderId) {

		String sql = "SELECT bo.id AS order_id, bo.borrow_amount, bo.repay_term, bo.repay_type, count(br.id) AS period_num, sum(br.reality_repay_money) AS repay_money_amount "
				+ "FROM bw_repayment_plan br " + "LEFT JOIN bw_order bo ON bo.id = br.order_id "
				+ "WHERE br.repay_status = 1 " + "AND bo.id = #{id} " + "GROUP BY bo.id, bo.repay_type";
		return sqlMapper.selectOne(sql, orderId, BwOrderInfoDto.class);
	}

	@Override
	public BwOrder findOrderNoByThirdOrderNo(String thirdOrderNo) {

		String sql = "SELECT * FROM bw_order o JOIN bw_order_rong r ON r.third_order_no = #{third_order_no}"
				+ " AND o.id = r.order_id;";
		return sqlMapper.selectOne(sql, thirdOrderNo, BwOrder.class);
	}

	@Override
	public boolean addBwOrder(BwOrder order) {
		return mapper.insert(order) > 0;
	}

	@Override
	public int updateBwOrder(BwOrder order) {
		return mapper.updateByPrimaryKey(order);
	}

	@Override
	public int updateOrg(BwOrder bo) {
		String sql = "update bw_order set org_id='" + bo.getOrgId() + "' where id='" + bo.getId() + "'";
		return sqlMapper.update(sql);
	}

	@Override
	public int updateChannel(Long orderId, String channel) {
		String sql = "update bw_order set channel='" + channel + "' where id='" + orderId + "'";
		return sqlMapper.update(sql);
	}

	/**
	 * 续贷回调处理
	 *
	 * @param order
	 * @param bpr
	 * @param intRecord
	 * @throws BusException
	 */
	/*
	 * @Override public void updateBwOrderAndXuDai(Long id) throws BusException {
	 * 
	 * BwOrder order = mapper.selectByPrimaryKey(id); Long orderId=order.getId(); // 修改原工单状态已结清
	 * logger.info("=======原工单状态修改开始=============================="); String sql1 =
	 * "update bw_order set status_id = 6,update_time = now() where id = " +orderId; int
	 * updateOrder=sqlMapper.update(sql1); logger.info("=======原工单状态修改结束=============================="+updateOrder) ;
	 * //修改还款计划和逾期 Long statusId=order.getStatusId(); BwRepaymentPlan bwRepaymentPlan=
	 * bwRepaymentPlanService.getPlan(orderId, 1);//最新一期还款计划 if(statusId==9){//还款中
	 * logger.info("=======还款计划表修改开始================================"); Long repayType=1L;//还款类型：1正常还款 2 逾期还款 3 提前还款',
	 * // 获取还款日期 if(bwRepaymentPlan!=null){ Date repayTime=bwRepaymentPlan.getRepayTime(); int spaceDays=
	 * MyDateUtils.daysOfTwo(bwRepaymentPlan.getRepayTime(), new Date()); if(spaceDays==0){//正常还款 repayType=1L; }
	 * if(spaceDays>0){//逾期还款 repayType=2L; } if(spaceDays<0){//提前还款 repayType=3L; } } String sql2 =
	 * "update bw_repayment_plan set repay_type ="+repayType+ " ,repay_status = 2 where order_id = "+orderId; int hkUpd=
	 * sqlMapper.update(sql2); logger.info("=======还款计划表修改结束================================"+hkUpd); }
	 * 
	 * if(statusId==13){//逾期 logger.info("=======逾期表修改开始================================"); String sql3 =
	 * "update bw_overdue_record set overdue_status = 2,update_time = now() where order_id = " +orderId; int yqUpd=
	 * sqlMapper.update(sql3); logger.info("=======逾期表修改结束================================"+yqUpd); } // 复制原工单一系列操作
	 * logger.info("=======复制工单开始================================"); String orderNo = "B" +
	 * CommUtils.convertDateToString(new Date(),"yyyyMMddhhmmssSSS") + CommUtils.getRandomNumber(3); //生成工单编号
	 * bwBorrowerService.updateRenew(orderId,order,orderNo, order.getBorrowAmount(),bwRepaymentPlan);
	 * logger.info("=======复制工单结束================================");
	 * 
	 * //续贷一系列处理 logger.info("========续贷操作开始=========================");
	 * bwOrderXuDaiService.operateOrder(orderId,order.getId()); logger.info("========续贷操作结束=========================");
	 * 
	 * 
	 * }
	 */

	@Override
	public int updateaUditStatusOrderId(Long orderId) {
		String sql = "UPDATE bw_order o SET o.audit_status=1 where o.id=#{orderId}";
		return sqlMapper.update(sql, orderId);
	}

	@Override
	public Long add(BwOrder order) {
		order.setId(null);
		mapper.insertSelective(order);
		return order.getId();
	}

	@Override
	public BwPersonInfo findBwPersonInfoByOrderId(Long orderId) {
		String sql = "select * from bw_person_info p where p.order_id = " + orderId;
		List<BwPersonInfo> list = sqlMapper.selectList(sql, BwPersonInfo.class);
		return CommUtils.isNull(list) ? null : list.get(0);
	}

	@Override
	public List<YiQiHaoApplyRequestDto> findApplyRequest() {
		// 查询未推送和推送失败次数小于3的工单信息
		String sql = "SELECT o.id order_id, o.order_no loan_sid, o.borrow_amount loan_amount, o.repay_term loan_deadline,"
				+ " b.`name` user_realname, b.id_card user_idcard, b.phone bank_mobile, b.age user_age,"
				+ " bc.bank_code bank_code, bc.card_no bank_card,"
				+ " CASE pi.marry_status WHEN 1 THEN '已婚' ELSE '未婚' END marry_status, pi.city_name city_name,"
				+ " wi.industry industry, wi.work_years work_years," + " cc.`name` area_name" + " FROM bw_order o"
				+ " LEFT JOIN bw_person_info pi ON pi.order_id = o.id"
				+ " LEFT JOIN bw_work_info wi ON wi.order_id = o.id"
				+ " LEFT JOIN bw_borrower b ON b.id = o.borrower_id"
				+ " LEFT JOIN bw_bank_card bc ON bc.borrower_id = o.borrower_id"
				+ " LEFT JOIN bw_city_code cc ON cc.`code` = SUBSTRING(b.id_card,1,4)"
				+ " LEFT JOIN bw_order_push_info opi ON opi.order_id = o.id" + " WHERE o.status_id = 12"
				+ " AND (SELECT COUNT(ox.order_id) FROM bw_order_xudai ox WHERE ox.order_id = o.id AND ox.`status` = 1) = 0"
				+ " GROUP BY CASE WHEN opi.order_id IS NULL THEN o.id ELSE opi.order_id END"
				+ " HAVING COUNT(opi.order_id) < 3";
		List<Map<String, Object>> list = sqlMapper.selectList(sql);

		// 处理返回记录
		List<YiQiHaoApplyRequestDto> resList = new ArrayList<>();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				YiQiHaoApplyRequestDto applyRequest = new YiQiHaoApplyRequestDto();
				Object orderId = map.get("order_id");// 工单ID
				if (orderId != null) {
					applyRequest.setOrderId(Long.parseLong(orderId.toString()));
				}
				applyRequest.setUserRealname(YiQiHaoUtil.convertObjToStr(map.get("user_realname")));// 借款人姓名
				applyRequest.setUserIdcard(YiQiHaoUtil.convertObjToStr(map.get("user_idcard")));// 借款人身份证号码
				applyRequest.setBankMobile(YiQiHaoUtil.convertObjToStr(map.get("bank_mobile")));// 银行卡预留手机号
				Object bankCode = map.get("bank_code");// 银行卡开户行标识
				if (bankCode != null) {
					applyRequest.setBankCode(YiQiHaoUtil.convertFuiouBankcodeToYiqihao(bankCode.toString()));
				}
				applyRequest.setBankCard(YiQiHaoUtil.convertObjToStr(map.get("bank_card")));// 银行卡号
				applyRequest.setLoanSid(YiQiHaoUtil.convertObjToStr(map.get("loan_sid")));// 工单号
				Object laonAmount = map.get("loan_amount");// 借款金额
				if (laonAmount != null) {
					applyRequest.setLoanAmount(Double.parseDouble(laonAmount.toString()));
				}
				applyRequest.setLoanDeadline(YiQiHaoUtil.convertObjToStr(map.get("loan_deadline")));// 借款期限
				Object marryStatus = map.get("marry_status");// 婚姻状态
				Object userAge = map.get("user_age");// 年龄
				Object areaName = map.get("area_name");// 籍贯
				Object cityName = map.get("city_name");// 居住地
				Object industry = map.get("industry");// 行业
				Object workYears = map.get("work_years");// 工作年限

				if (industry == null) {
					industry = "其他";
				}
				if (marryStatus != null && userAge != null && areaName != null && cityName != null && industry != null
						&& workYears != null) {
					String loanDescription = "借款人婚姻状态" + marryStatus.toString() + "，现年" + userAge.toString()
							+ "岁，户籍所在地为" + areaName.toString() + "，现居住地为" + cityName.toString() + "。借款人目前从事" + industry
							+ "行业，已工作" + workYears + "。";
					applyRequest.setLoanDescription(loanDescription);// 借款描述
				}
				resList.add(applyRequest);
			}
		}
		return resList;
	}

	@Override
	public int findsignStatusByOrderId(Long orderId) {
		String sql = "select c.sign_status from bw_bank_card c where c.borrower_id =(select b.id from bw_order o "
				+ "LEFT JOIN bw_borrower b ON o.borrower_id = b.id where o.id =" + orderId + ")";
		// System.out.println(sql);
		Integer num = sqlMapper.selectOne(sql, Integer.class);
		return num == null ? 0 : num;
	}

	@Override
	public BwOrder findBwOrderById(String orderId) {
		String sql = "select o.* from bw_order o where o.id=" + orderId + "";
		return sqlMapper.selectOne(sql, BwOrder.class);
	}

	@Override
	public void update(BwOrder bwOrder) {
		mapper.updateByPrimaryKeySelective(bwOrder);
	}

	@Override
	public void updateStrvice(String orderId) {
		String sql = "update bw_order set status_id='11' ,update_time=now() WHERE id='" + orderId + "'";
		sqlMapper.update(sql);
	}

	@Override
	public Long findBorrwerIdByOrderId(Long borrwer) {
		String sql = "select o.id from bw_order o where o.borrower_id=#{borrwer} ORDER BY o.create_time DESC LIMIT 1";
		return sqlMapper.selectOne(sql, borrwer, Long.class);
	}

	@Override
	public AppResponseResult initbwTransferBu(String orderId, String userId, String amt, BwBorrower bwBorrow)
			throws Exception {
		AppResponseResult appResponseResult = new AppResponseResult();
		// 获取orderId
		if (CommUtils.isNull(orderId)) {
			appResponseResult.setCode("1201");
			appResponseResult.setMsg("用户执行还款----工单ID为空");
			return appResponseResult;
		}
		BwRepaymentPlan paramEntity = new BwRepaymentPlan();
		paramEntity.setOrderId(Long.parseLong(orderId));
		BwRepaymentPlan bwRepayment = bwRepaymentPlanService.findBwRepaymentPlanByAttr(paramEntity);
		if (CommUtils.isNull(bwRepayment)) {
			appResponseResult.setCode("1202");
			appResponseResult.setMsg("用户执行还款----还款计划对象为空");
			return appResponseResult;
		}
		// 已还款
		// 获取还款日期
		BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getPlan(Long.parseLong(orderId), 1);
		if (bwRepaymentPlan != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date repayTime = dateFormat.parse(dateFormat.format(bwRepaymentPlan.getRepayTime()));
			Date now = dateFormat.parse(dateFormat.format(new Date()));
			if (now.before(repayTime)) {
				bwRepayment.setRepayType(3);
				logger.info("正常还款-------------");
			}
			if (now.after(repayTime)) {
				bwRepayment.setRepayType(2);
				logger.info("逾期还款-------------");
			}
			if (now.equals(repayTime)) {
				bwRepayment.setRepayType(1);
				logger.info("提前还款-------------");
			}
		}
		bwRepayment.setRepayStatus(2);
		bwRepaymentPlanService.update(bwRepayment);
		logger.info("开始执行我要还款---还款 --- 修改还款计划已还款的状态");
		// 修改逾期表 状态
		BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
		bwOverdueRecord.setOrderId(Long.parseLong(orderId));
		bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
		BwOverdueRecord bwOverdue = bwOverdueRecordService.queryBwOverdueRecord(bwOverdueRecord);
		if (!CommUtils.isNull(bwOverdue)) {
			bwOverdue.setOverdueStatus(2);
			bwOverdue.setUpdateTime(new Date());
			bwOverdueRecordService.updateBwOverdueRecord(bwOverdue);
		}
		logger.info("开始执行我要还款---还款 --- 修改逾期表已还款的状态");
		// 修改工单状态
		BwOrder order = new BwOrder();
		order.setId(Long.parseLong(orderId));
		;
		BwOrder resultOrder = this.findBwOrderByAttr(order);
		if (!CommUtils.isNull(resultOrder)) {
			resultOrder.setStatusId(6l);
			resultOrder.setUpdateTime(new Date());
			this.updateBwOrder(resultOrder);
		}
		// 更新续贷表
		logger.info("更新续贷表开始---查询工单是否续贷过-有就正常结清-------------");
		int updXudai = bwOrderXuDaiService.updOrderXudai(Long.parseLong(orderId));
		logger.info("-更新续贷表结束--------" + updXudai);
		/////////////////
		// 修改借款人状态
		bwBorrow.setAuthStep(1);
		bwBorrowerService.updateBwBorrower(bwBorrow);

		logger.info("开始执行我要还款---还款 --- 订单表已结束的状态");
		logger.info("开始执行我要还款---还款 --- 个人转到风险备用金");
		// 个人转到风险备用金
		String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
		AppResponseResult appResult = fuYouService.bwTransferBu(userId, Double.parseDouble(amt), mchnt_txn_ssn);
		if (!appResult.getCode().equals("0000")) {
			logger.info("开始执行我要还款---还款 --- 险备用金转账失败" + appResult.getMsg());
			// 冻结
			fuYouService.freezeAccount(bwBorrow.getFuiouAcct(), Double.parseDouble(amt));
			appResponseResult.setCode("999");
			appResponseResult.setMsg("数据调用异常--风险备用金转账失败" + appResult.getMsg());
			return appResponseResult;
		}
		logger.info("开始执行我要还款---还款 --- 险备用金转账成功");
		// 账户充值到--平台
		BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
		// 交易流水号 private String tradeNo; 第三方返回的流水号
		bwPlatformRecord.setTradeNo(mchnt_txn_ssn);
		bwPlatformRecord.setTradeAmount(Double.parseDouble(amt));
		bwPlatformRecord.setOutAccount(bwBorrow.getFuiouAcct());
		bwPlatformRecord.setOutName(bwBorrow.getName());
		bwPlatformRecord.setInAccount(SystemConstant.FUIOU_MCHNT_BACKUP);
		bwPlatformRecord.setInName(null);
		bwPlatformRecord.setTradeType(1);
		bwPlatformRecord.setOrderId(Long.parseLong(orderId));
		bwPlatformRecord.setTradeTime(new Date());
		bwPlatformRecord.setTradeChannel(1);
		if (!appResult.getCode().equals("0000")) {
			bwPlatformRecord.setTradeRemark(appResult.getMsg());
		} else {
			bwPlatformRecord.setTradeRemark("交易成功");
		}
		bwPlatformRecord.setTradeCode(appResult.getCode().toString());
		bwPlatformRecord.setInName("风险备用金");
		bwPlatformRecordService.saveBwPlatFormRecord(bwPlatformRecord);
		appResponseResult.setCode("000");
		appResponseResult.setMsg("数据调用成功");
		logger.info("开始执行我要还款---还款 --- 账户充值到--平台成功");

		return appResponseResult;
	}

	@Override
	public Integer findOrderStatusByOrderId(Long orderId) {
		String sql = "select o.status_id from bw_order o where o.id=#{orderId} LIMIT 1";
		return sqlMapper.selectOne(sql, orderId, Integer.class);
	}

	@Override
	public Date findUpdateTimeByOrderId(Long orderId) {
		String sql = "select o.update_time from bw_order o where o.id=#{orderId} LIMIT 1";
		return sqlMapper.selectOne(sql, orderId, Date.class);
	}

	@Override
	public int updateStatusByOrderId(Long orderId, int status) {
		String sql = "update bw_order o SET o.status_id=" + status + " where o.id=" + orderId + "";
		return sqlMapper.update(sql);
	}

	@Override
	public Integer findOrderStatusByBorrowerId(Long borrowerId) {
		String sql = "select o.status_id from bw_order o where o.borrower_id=#{borrowerId} ORDER BY o.create_time LIMIT 1";
		return sqlMapper.selectOne(sql, borrowerId, Integer.class);
	}

	@Override
	public String getBwOrderById(Long orderId) {
		String sql = "select b.city_name from bw_order  a left join sys_city b on  a.org_id=b.id where a.id=" + orderId;
		return sqlMapper.selectOne(sql, String.class);
	}

	@Override
	public BwOrder findOrderIdByBorrwerId(Long borrwerId) {
		String sql = "select o.* from bw_order o where o.borrower_id=#{borrwerId} ORDER BY o.create_time DESC LIMIT 1;";
		return sqlMapper.selectOne(sql, borrwerId, BwOrder.class);
	}

	@Override
	public BwOrder findOrderIdByBorrwerId(Long borrwerId, int[] excludeStatusIds) {
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append("select o.* from bw_order o where o.borrower_id=#{borrwerId}");
		if (excludeStatusIds != null && excludeStatusIds.length > 0) {
			StringBuilder statusIdWhereSB = new StringBuilder();
			if (excludeStatusIds.length == 1) {
				statusIdWhereSB.append(" and o.status_id<>");
				statusIdWhereSB.append(excludeStatusIds[0]);
			} else {
				int i = 1;
				statusIdWhereSB.append(" and o.status_id not in (");
				for (int excludeStatusId : excludeStatusIds) {
					statusIdWhereSB.append(excludeStatusId);
					if (excludeStatusIds.length > i) {
						statusIdWhereSB.append(",");
					}
					i++;
				}
				statusIdWhereSB.append(")");
			}
			sqlSB.append(statusIdWhereSB);
		}
		sqlSB.append(" ORDER BY o.create_time DESC LIMIT 1");
		// System.out.println(sqlSB);
		return sqlMapper.selectOne(sqlSB.toString(), borrwerId, BwOrder.class);
	}

	@Override
	public List<String> findPathByOrderId(Long orderId) {
		String sql = "select a.adjunct_path from bw_adjunct a where a.order_id = #{orderId} and a.adjunct_type in (1,2,3)";
		return sqlMapper.selectList(sql, orderId, String.class);
	}

	@Override
	public Long findPathByChannel(Long orderId) {
		String sql = "select a.channel from bw_order a where a.id = #{orderId}";
		return sqlMapper.selectOne(sql, orderId, Long.class);
	}

	@Override
	public Long findOrderIdByNo(String orderNo) {
		String sql = "select o.id from bw_order o where o.order_no = #{orderNo}";
		return sqlMapper.selectOne(sql, orderNo, Long.class);
	}

	@Override
	public Long findTodayOrderCount() {
		String sql = "select count(1) from bw_order o where TO_DAYS(o.create_time) = TO_DAYS(NOW()) ";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public Double findTodayOrderAmount() {
		String sql = "SELECT SUM(r.reality_repay_money) FROM bw_order_push_info p JOIN bw_repayment_plan r ON p.order_id = r.order_id WHERE p.push_status = 2 AND TO_DAYS(r.create_time) = TO_DAYS(NOW())";
		return sqlMapper.selectOne(sql, Double.class);
	}

	@Override
	public Long findSignOrderCount() {
		String sql = "select count(1) from bw_order o where o.status_id = 4 ";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public Long findNotPushOrderCount() {
		String sql = "select count(1) from bw_order_push_info p where p.push_status = 1 or (p.push_status = 0 and p.push_remark = '借款申请失败：当前可放款额度不足，请稍后再试！')";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public List<Map<String, Object>> findWeekOrderCount() {
		String sql = "SELECT date(o.create_time) as create_time ,count(1) as c FROM bw_order o WHERE TO_DAYS(NOW()) - TO_DAYS(o.create_time) < 7 GROUP BY date(o.create_time);";
		return sqlMapper.selectList(sql);
	}

	@Override
	public Long findCheckOrderCount() {
		String sql = "select count(1) from bw_order o where o.status_id = 2 ";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public Long findRepayOrderCount() {
		String sql = "select count(1) from bw_repayment_plan p ";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public BwOrder findCheckOrder(String phone) {
		// String sql = "select a.channel from bw_order a , bw_borrower b where
		// a.borrower_id=b.id and a.flag=1 "
		// + "and a.status_id not in (6,7,8) and b.phone='" + phone + "' order by
		// a.create_time desc LIMIT 1";
		String sql = "select a.* from bw_order a LEFT JOIN bw_borrower b on a.borrower_id=b.id where a.flag=1 "
				+ " and a.status_id not in (6,7,8) and b.phone='" + phone + "' order by a.create_time desc LIMIT 1";
		return sqlMapper.selectOne(sql, BwOrder.class);
	}

	@Override
	public List<LoanInfo> zhengxin(String id_card) {
		String sql = " select '1' as borrow_type, " + " case  " + " when t1.status_id = 7 then 1 "
				+ " when (t1.status_id = 6 or t1.status_id = 9 or t1.status_id = 13) then 2  "
				+ " when t1.status_id = 8 then 4 " + " when (t1.status_id = 1 or t1.status_id = 2 or t1.status_id = 3  "
				+ " or t1.status_id = 4 or t1.status_id = 11 or t1.status_id = 12 or t1.status_id = 14) then 5  "
				+ " when t1.status_id = 5 then 6  " + " else 0 " + " end  as borrowState,  " + " case "
				+ " when  0 <= t1.borrow_amount and  t1.borrow_amount < 1000.00 then -7 "
				+ " when  1000.00 <= t1.borrow_amount and  t1.borrow_amount < 2000.00 then -6   "
				+ " when  2000.00 <= t1.borrow_amount and  t1.borrow_amount < 3000.00 then -5   "
				+ " when  3000.00 <= t1.borrow_amount and  t1.borrow_amount < 4000.00 then -4   "
				+ " when  4000.00 <= t1.borrow_amount and  t1.borrow_amount < 6000.00 then -3   "
				+ " when  6000.00 <= t1.borrow_amount and  t1.borrow_amount < 8000.00 then -2   "
				+ " when  8000.00 <= t1.borrow_amount and  t1.borrow_amount < 10000.00 then -1   "
				+ " when  10000.00 <= t1.borrow_amount and  t1.borrow_amount < 20000.00 then 1   " + " else 0  "
				+ " end  " + " as borrowAmount,    " + " t4.create_time as contractDate,  " + " case   "
				+ " when t1.status_id = 9 then 1  " + " when t1.status_id = 13 then 2 "
				+ " when t1.status_id = 6 then 9 " + " else 0 " + " end  as repayState, " + " 0 as arrearsAmount "
				+ " from bw_order t1  " + " LEFT JOIN bw_borrower t2 on t1.borrower_id = t2.borrower_id "
				+ " LEFT JOIN (select  order_id, create_time from bw_adjunct where adjunct_type = 13) t4 on t1.id = t4.order_id "
				+ " where t2.id_card = #{id_card} ";
		return sqlMapper.selectList(sql, id_card, LoanInfo.class);
	}

	@Override
	public Long findProOrder(String borrowerId) {
		String sql = "select count(1) from bw_order a where a.borrower_id=" + borrowerId
				+ " and a.status_id in (2,3,4,5,11,12,14,9,13,8)";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public boolean hasNotCompleteOrder(Long borrowerId) {
		// 查询最新的非草稿工单
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", borrowerId).andNotEqualTo("statusId", 1);
		example.setOrderByClause(" create_time desc limit 1");
		List<BwOrder> list = mapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			BwOrder bwOrder = list.get(0);
			if (bwOrder != null) {
				Long statusId = bwOrder.getStatusId();
				if (statusId != null) {
					if (statusId == 6 || statusId == 7 || statusId == 8) {// 结束、拒绝、撤回
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public BwOrder getLastEndOrder(Long borrowerId) {
		// 查询最新的非草稿工单
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", borrowerId).andEqualTo("statusId", 6);
		example.setOrderByClause(" create_time desc limit 1");
		List<BwOrder> list = mapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			BwOrder bwOrder = list.get(0);
			return bwOrder;
		}
		return null;
	}

	@Override
	public BwOrder findNewBwOrderByBwId(String bwId) {

		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", bwId).andNotEqualTo("statusId", 1).andEqualTo("productType",
				OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//		example.setOrderByClause(" createTime desc ");
		example.orderBy("createTime").desc();
		List<BwOrder> list = findBwOrderByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询最新的工单
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findNewBwOrderByBwIdNotLimitStatusID(java.lang.String)
	 */
	@Override
	public BwOrder findNewBwOrderByBwIdNotLimitStatusID(String bwId) {

		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", bwId).andEqualTo("productType",
				OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
//		example.setOrderByClause(" createTime desc ");
		example.orderBy("createTime").desc();
		List<BwOrder> list = findBwOrderByExample(example);
		if (!CommUtils.isNull(list) && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public AppResponseResult canKeyMoney(Long bwId) {
		AppResponseResult appResponseResult = new AppResponseResult();
		appResponseResult.setResult(false);
		BwBorrower queryBwBorrower = null;
		if (bwId != null && bwId > 0L) {
			queryBwBorrower = bwBorrowerService.findBwBorrowerById(bwId);
		}
		if (queryBwBorrower == null) {
			appResponseResult.setCode("901");
			appResponseResult.setMsg("没有此借款人");
			return appResponseResult;
		}
		// 获取借款人身份证
		String idCard = queryBwBorrower.getIdCard();

		// 根据身份证查询已通过的名单，判断借款人是否是白名单
		int blackType = bwBlacklistService.findAdoptTypeByCard(idCard);
		if (blackType != 3) {
			appResponseResult.setCode("902");
			appResponseResult.setMsg("借款人不是白名单用户");
			return appResponseResult;
		}

		// 判断借款人是否有未还款工单，若有，则不能一键拿钱
		boolean hasNotCompleteOrder = hasNotCompleteOrder(bwId);
		if (hasNotCompleteOrder) {
			appResponseResult.setCode("903");
			appResponseResult.setMsg("借款人有未完成工单");
			return appResponseResult;
		}

		// 判断借款人是否有已结束工单，若没有，则不能一键拿钱
		BwOrder lastEndOrder = getLastEndOrder(bwId);
		if (lastEndOrder == null) {
			appResponseResult.setCode("904");
			appResponseResult.setMsg("借款人未完成过借款");
			return appResponseResult;
		}

		// 判断是否有正在处理中的工单
		boolean hasInAuditOrder = orderService.hasProcessingOrder(bwId, null);
		if (hasInAuditOrder) {
			appResponseResult.setCode("905");
			appResponseResult.setMsg(InstallmentConstant.SINGLE_OPERATION_MSG.IN_PROCESSING);
			return appResponseResult;
		}

		appResponseResult.setCode("000");
		appResponseResult.setMsg("可以一键拿钱");
		appResponseResult.setResult(lastEndOrder);
		return appResponseResult;
	}

	/**
	 * 
	 * @throws BusException
	 * @see com.waterelephant.service.IBwOrderService#updateAndKeyMoney(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public AppResponseResult updateAndKeyMoney(Long borrowerId, Integer channelId) throws BusException {
		AppResponseResult appResponseResult = canKeyMoney(borrowerId);
		JSONObject result = new JSONObject();
		if ("000".equals(appResponseResult.getCode())) {// 满足一键拿钱条件
			BwOrder lastEndOrder = (BwOrder) appResponseResult.getResult();
			result.put("orderAuth", true);// 是否需要重新认证
			// 获取用户上次运营商认证记录，判断是否超过31天
			BwOrderAuth bwOrderAuth = bwOrderAuthService.findLastOrderAuthByBorrowerId(borrowerId, 1);
			if (bwOrderAuth == null) {
				logger.info("借款人" + borrowerId + "没有最近的运营商认证记录，不能一键拿钱");
				appResponseResult.setMsg("不能一键拿钱");
				appResponseResult.setCode("910");
				return appResponseResult;
			}
			BwProductDictionary product = productService.queryCurrentProduct();
			// 配置信息，白名单免认证（运营商和芝麻信用）天数(31天)
			ExtraConfig extraConfig = productService.getParameterInfo(ParameterConstant.FREE_AUTHENTICATION_DAY);
			int preOperatorDay = Integer.parseInt(extraConfig.getValue());
			Date updateTime = bwOrderAuth.getUpdateTime();
			BwOrder bwOrder = addOrGetDraftOrder(borrowerId, channelId);
			// 状态改为待签约，用户点击马上拿钱后改为待生成合同，合同会定时每隔一分钟自动生成
			// 上次运营商认证更新时间到现在间隔多少天
			int diffDay = MyDateUtils.daysOfTwo(updateTime, new Date());
			if (diffDay > preOperatorDay) {// 超过31天重新认证运营商、芝麻信用，草稿工单
				bwOrder.setStatusId(1L);
			} else {// 跳过认证直接到马上拿钱，待签约状态
				bwOrder.setStatusId(4L);
			}
			// 使用的产品
			bwOrder.setProductId(Integer.parseInt(String.valueOf(product.getId())));
			bwOrder.setBorrowRate(product.getpInvestRateMonth());// 借款月利率
			bwOrder.setContractRate(product.getpInvesstRateYear());// 合同年利率
			bwOrder.setContractMonthRate(product.getpBorrowRateMonth());// 合同月利率
			bwOrder.setMark(lastEndOrder.getMark());
			// 免罚息期
			bwOrder.setAvoidFineDate(0);
			bwOrder.setContractAmount(lastEndOrder.getContractAmount());
			bwOrder.setRank(lastEndOrder.getRank());
			bwOrder.setCreditLimit(lastEndOrder.getCreditLimit());
			bwOrder.setBorrowAmount(lastEndOrder.getBorrowAmount());
			bwOrder.setBorrowUse(lastEndOrder.getBorrowUse());
			bwOrder.setRepayType(1);
			bwOrder.setRejectType(lastEndOrder.getRejectType());
			bwOrder.setOrgId(lastEndOrder.getOrgId());
			bwOrder.setExpectMoney(lastEndOrder.getBorrowAmount());// 期望金额
			update(bwOrder);
			Date nowDate = new Date();
			Long newOrderId = bwOrder.getId();

			if (diffDay > preOperatorDay) {// 超过31天全部重新认证
				appResponseResult.setCode("000");
				appResponseResult.setMsg("超过" + preOperatorDay + "天，需要重新认证");
				appResponseResult.setResult(result);
				return appResponseResult;
			} else {// 跳过认证直接到马上拿钱
				result.put("orderAuth", false);
				// 运营商、芝麻信用自动认证
				bwOrderAuthService.saveOrderAuth(newOrderId, borrowerId, 1, channelId, nowDate, updateTime);
				// 芝麻信用
				BwOrderAuth zmAuth = bwOrderAuthService.findLastOrderAuth(lastEndOrder.getId(), 4);
				Date zmUpdateTime = (zmAuth == null ? nowDate : zmAuth.getUpdateTime());
				bwOrderAuthService.saveOrderAuth(newOrderId, borrowerId, 4, channelId, nowDate, zmUpdateTime);
			}

			// 个人信息、拍照自动认证
			Long lastEndOrderId = lastEndOrder.getId();
			logger.info("--------复制个人信息开始----------");
			bwOrderAuthService.saveCopyPensonInfoAndAuth(newOrderId, lastEndOrderId, channelId);
			logger.info("--------复制个人信息结束----------");

			logger.info("--------复制身份证照开始----------");
			bwOrderAuthService.saveCopyPicInfoAndAuth(newOrderId, lastEndOrderId, borrowerId, channelId);
			logger.info("--------复制身份证照结束----------");

			// 更新认证状态(前面没复制状态)
			BwOrderAuth paramOrderAuth = new BwOrderAuth();
			paramOrderAuth.setOrderId(newOrderId);
			List<Map<String, Object>> newOrderAuthList = sqlMapper.selectList(
					"select id,auth_type,auth_channel,photo_state from bw_order_auth where order_id=#{order_id}",
					newOrderId);
			List<Map<String, Object>> lastEndOrderAuthList = sqlMapper.selectList(
					"select id,auth_type,auth_channel,photo_state from bw_order_auth where order_id=#{order_id}",
					lastEndOrderId);
			if (newOrderAuthList != null && !newOrderAuthList.isEmpty()) {
				for (Map<String, Object> tempNewAuth : newOrderAuthList) {
					for (Map<String, Object> tempLastEndAuth : lastEndOrderAuthList) {
						if (tempLastEndAuth.get("auth_type").equals(tempNewAuth.get("auth_type"))) {
							BwOrderAuth updateOrderAuth = new BwOrderAuth();
							updateOrderAuth.setId(Long.parseLong(tempNewAuth.get("id").toString()));
							updateOrderAuth
									.setPhotoState(NumberUtils.toInt(tempLastEndAuth.get("photo_state") + "", 0));
							bwOrderAuthService.updateBwOrderAuth(updateOrderAuth);
							break;
						}
					}
				}
			}

			logger.info("--------复制社保开始----------");
			List<BwInsureInfo> BwInsureInfoList = bwInsureInfoService.queryInfo(lastEndOrderId);
			if (!CommUtils.isNull(BwInsureInfoList)) {
				for (int z = 0; z < BwInsureInfoList.size(); z++) {
					BwInsureInfo bwInsureInfo = BwInsureInfoList.get(z);
					bwInsureInfo.setId(null);
					bwInsureInfo.setOrderId(newOrderId);
					bwInsureInfo.setCreateTime(new Date());
					bwInsureInfoService.add(bwInsureInfo);
				}
			}
			logger.info("--------复制社保结束----------");
			appResponseResult.setMsg("一键拿钱成功");
			appResponseResult.setResult(result);
		}
		return appResponseResult;
	}

	/**
	 * 获取最新草稿，没有则新增返回
	 * 
	 * @param borrowerId
	 * @param channelId
	 * @return
	 */
	private BwOrder addOrGetDraftOrder(Long borrowerId, Integer channelId) {
		Example example = new Example(BwOrder.class);
		int productType = Integer.parseInt(OrderStatusConstant.ORDER_PRODUCT_TYPE.SINGLE);
		List<Object> statusList = new ArrayList<Object>();
		statusList.add(1);// 草稿
		example.createCriteria().andIn("statusId", statusList).andEqualTo("borrowerId", borrowerId)
				.andEqualTo("productType", productType);
//		example.setOrderByClause(" createTime desc ");
		example.orderBy("createTime").desc();
		List<BwOrder> list = findBwOrderByExample(example);
		if (!CommUtils.isNull(list) && list.size() > 0) {
			BwOrder oldOrder = list.get(0);
			return oldOrder;
		}
		BwProductDictionary product = productService.selectCurrentByProductType(productType);
		Date nowDate = new Date();
		BwOrder order = new BwOrder();
		String orderNo = OrderUtil.generateOrderNo();
		order.setOrderNo(orderNo);
		order.setBorrowerId(borrowerId);
		order.setCreateTime(nowDate);
		order.setChannel(channelId);
		order.setAvoidFineDate(0);
		order.setApplyPayStatus(0);
		order.setStatusId(1l);
		order.setUpdateTime(nowDate);
		order.setSubmitTime(nowDate);
		order.setProductId(Integer.parseInt(product.getId().toString()));
		order.setProductType(productType);
		addBwOrder(order);
		return order;
	}

	@Override
	public List<orderVO> getBwOrderByStatusId(String bwid) {
		String sql = "select id as orderId,borrow_amount as borrowAmount,status_id as statusId,date_format(create_time, '%Y-%m-%d %H:%I:%S') as createTime   from bw_order where borrower_id = "
				+ bwid + " and status_id in (6,9,13)  ORDER BY create_time  DESC ";
		List<orderVO> selectList = this.sqlMapper.selectList(sql, orderVO.class);
		logger.info("查询工单列表的sql为 ： " + sql);
		return selectList;
	}

	@Override
	public int findCountBwOrderByBorrowId(String bwId) {
		int tmp = 0;
		String sql = "SELECT COUNT(1) from bw_order where status_id=6 and borrower_id = " + bwId;
		Map<String, Object> map = sqlMapper.selectOne(sql);
		if (!CommUtils.isNull(map)) {
			String string = map.get("COUNT(1)").toString();
			tmp = Integer.parseInt(string);
		}
		return tmp;
	}

	@Override
	public AppResponseResult getBorrowRcprdVP(BwOrder bwOrder) {
		AppResponseResult respResult = new AppResponseResult();
		BorrowRcordVO borrowRcordVO = new BorrowRcordVO();
		Long orderId = bwOrder.getId();
		// 根据工单查询是否分批
		RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService.getRepaymentBatch(orderId);
		Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
		borrowRcordVO.setBatchTotal(DoubleUtil.toTwoDecimal(alreadyTotalBatchMoney));
		int isBatch = 0;// 是否分批
		boolean selectIsBatch = bwOrderRepaymentBatchDetailService.selectIsBatch(orderId);
		if (selectIsBatch) {
			isBatch = 1;
		}
		borrowRcordVO.setIsBatch(isBatch);
		// 费用产品信息从产品表中获取
		Integer productId = bwOrder.getProductId();// 获得该工单的产品id
		BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(productId);
		borrowRcordVO.setStatusId(Integer.parseInt(bwOrder.getStatusId().toString()));// 工单状态
		if (!CommUtils.isNull(bwProductDictionary)) {
			Double getpFastReviewCost = bwProductDictionary.getpFastReviewCost();// 快速审核费
			Double getpPlatformUseCost = bwProductDictionary.getpPlatformUseCost();// 平台使用费
			Double getpNumberManageCost = bwProductDictionary.getpNumberManageCost();// 账户管理费
			Double getpCollectionPassagewayCost = bwProductDictionary.getpCollectionPassagewayCost();// 代收通道费
			Double getpCapitalUseCost = bwProductDictionary.getpCapitalUseCost();// 资金使用费
			DecimalFormat df = new DecimalFormat("######0.00");

			BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(bwOrder.getBorrowerId());
			if (!CommUtils.isNull(card)) {
				String cardNo = card.getCardNo();
				// 取银行卡号后四位
				borrowRcordVO.setCardNoEnd(cardNo.substring(cardNo.length() - 4));// 银行卡后四位
				String bankName = RedisUtils.hget(RedisKeyConstant.FUIOU_BANK, card.getBankCode());
				if (StringUtils.isNotEmpty(bankName) && bankName.contains("公司") && bankName.contains("银行")) {
					bankName = bankName.substring(0, bankName.indexOf("银行") + 2);
				}
				borrowRcordVO.setBankName(bankName);// 银行名称
				logger.info("银行卡名称为 ： " + bankName + ActivityConstant.MSG_BEFORE + "银行后四位为："
						+ cardNo.substring(cardNo.length() - 4));
			}

			// 查询还款计划
			List<BwRepaymentPlan> list = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
			if (!CommUtils.isNull(list) && list.size() > 0) {
				BwRepaymentPlan bwRepaymentPlan = list.get(0);
				if (alreadyTotalBatchMoney == null || alreadyTotalBatchMoney <= 0.0) {
					alreadyTotalBatchMoney = bwRepaymentPlan.getAlreadyRepayMoney();
					if (alreadyTotalBatchMoney == null) {
						alreadyTotalBatchMoney = 0.0;
					}
					borrowRcordVO.setBatchTotal(DoubleUtil.toTwoDecimal(alreadyTotalBatchMoney));
				}
				Double borrowAmount = bwOrder.getBorrowAmount();
				borrowAmount = borrowAmount == null ? 0.00 : borrowAmount;
				Double realityRepayMoney = bwRepaymentPlan.getRealityRepayMoney();// 借款金额
				realityRepayMoney = realityRepayMoney == null ? 0.00 : realityRepayMoney;
				logger.info("===========该用户的还款计划为：" + list.get(0));
				Double borrowAmount2 = borrowAmount * (getpFastReviewCost + getpPlatformUseCost + getpNumberManageCost
						+ getpCollectionPassagewayCost + getpCapitalUseCost);
				String loanAmount = df.format(borrowAmount2);// 借款费用总额
				String arrivalAmount = df.format(DoubleUtil.sub(borrowAmount, Double.valueOf(loanAmount)));// 到账金额
				Date submitTime = bwOrder.getCreateTime();
				if (!CommUtils.isNull(submitTime)) {
					String submit = MyDateUtils.DateToString(submitTime, MyDateUtils.DATE_TO_STRING_SHORT_PATTERN);
					borrowRcordVO.setSubmitTime(submit);
				}

				borrowRcordVO.setBorrowAmount(df.format(borrowAmount));// 借款金额
				borrowRcordVO.setArrivalAmount(arrivalAmount);// 到款金额
				borrowRcordVO.setLoanAmount(loanAmount);// 综合费用
				borrowRcordVO.setpFastReviewCost(df.format(DoubleUtil.mul(borrowAmount, getpFastReviewCost)));// 快速审核费
				borrowRcordVO.setpPlatformUseCost(df.format(DoubleUtil.mul(borrowAmount, getpPlatformUseCost)));// 平台使用费
				borrowRcordVO.setpNumberManageCost(df.format(DoubleUtil.mul(borrowAmount, getpNumberManageCost)));// 账户管理费
				borrowRcordVO.setpCollectionPassagewayCost(
						df.format(DoubleUtil.mul(borrowAmount, getpCollectionPassagewayCost)));// 代收通道费
				borrowRcordVO.setpCapitalUseCost(df.format(DoubleUtil.mul(borrowAmount, getpCapitalUseCost)));// 资金使用费

				String term = bwProductDictionary.getpTerm();// 产品期限
				String termType = bwProductDictionary.getpTermType();// 产品类型（1：月；2：天）
				if ("2".equals(termType)) {
					borrowRcordVO.setTerm(term + "天");// 产品期限
				}
				if ("1".equals(termType)) {
					borrowRcordVO.setTerm(term + "月");// 产品期限
				}

				Double repayMoney = list.get(0).getRealityRepayMoney();
				borrowRcordVO.setRepayMoney(df.format(repayMoney));// 还款金额
				Date update_time = list.get(0).getUpdateTime();
				if (!CommUtils.isNull(update_time)) {
					String update_time2 = MyDateUtils.DateToString(update_time,
							MyDateUtils.DATE_TO_STRING_SHORT_PATTERN);
					borrowRcordVO.setUpdateTime(update_time2);
				}
				Date repay_time = list.get(0).getRepayTime();
				Date xudaiRepay = null;
				if ("1".equals(termType)) {// 月
					xudaiRepay = MyDateUtils.addMonths(repay_time, Integer.parseInt(term));// 续贷还款时间
				} else {// 天
					xudaiRepay = MyDateUtils.addDays(repay_time, Integer.parseInt(term));// 续贷还款时间
				}
				String xudaiRepay2 = MyDateUtils.DateToString(xudaiRepay, MyDateUtils.DATE_TO_STRING_SHORT_PATTERN);
				borrowRcordVO.setXuDaiRepayTime(xudaiRepay2);// 续贷到期还款日
				String repay_time2 = MyDateUtils.DateToString(repay_time, MyDateUtils.DATE_TO_STRING_SHORT_PATTERN);
				borrowRcordVO.setRepayTime(repay_time2);// 到期还款时间
				int distanceRepayTime = MyDateUtils.getDaySpace(new Date(), repay_time);// 距离还款日期
				borrowRcordVO.setDistanceRepayTime(distanceRepayTime);// 距离还款日期
				Long order_id = list.get(0).getOrderId();
				Long repayId = bwRepaymentPlan.getId();

				// 查询最近的展期次数
				BwRepaymentPlan plan = bwRepaymentPlanService.getLastRepaymentPlanAndXudaiByOrderId(bwOrder.getId());
				int xuDaiCount = 0;// 真实续贷次数
				// 某个时间之后的续贷次数
				int afterDateXuDaiCount = bwRepaymentPlanService.getXuDaiCountAfterDate(order_id);
				if (!CommUtils.isNull(plan)) {
					// 判断还款计划是否结清，如果结清则最后一条不是续贷
					xuDaiCount = StringUtil.toInteger(plan.getRolloverNumber());
					// 展期期数
					borrowRcordVO.setXuDaiCount(xuDaiCount);
					borrowRcordVO.setXudaiCreateTime(DateUtil.getDateString(plan.getCreateTime(), DateUtil.yyyy_MM_dd));
				}
				// 从产品表中获取续贷其数和费率
				BwProductDictionary product = productService.queryByOrderId(order_id);

				// 计算续贷服务费
				com.waterelephant.dto.LoanInfo loanInfo = new com.waterelephant.dto.LoanInfo();
				loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
				Integer repayStatus = list.get(0).getRepayStatus();
				Double totalXudaiAmount = null;
				if (repayStatus == 2 || repayStatus == 4) {// 已还款或展期
					// 工单当前续贷期数
					totalXudaiAmount = productService.calcXudaiCost(borrowAmount, order_id, repayId, product,
							xuDaiCount, loanInfo);// 历史工单续贷金额
				} else {
					totalXudaiAmount = productService.calcXudaiCost(borrowAmount, order_id, repayId, product,
							afterDateXuDaiCount + 1, loanInfo);// 续贷金额
				}
				String xuDaiAmount = loanInfo.getServiceAmount();
				borrowRcordVO.setXuDaiAmount(xuDaiAmount == null ? "" : xuDaiAmount);

				// 逾期
				BwOverdueRecord bwOverdueRecord = sqlMapper.selectOne("select * from bw_overdue_record where repay_id="
						+ repayId + " order by create_time desc limit 1", BwOverdueRecord.class);
				double overAmont = 0.0;
				if (!CommUtils.isNull(bwOverdueRecord) && !CommUtils.isNull(bwOverdueRecord.getOverdueDay())) {
					String overdueAmountStr = loanInfo.getRealOverdueAmount();
					Integer overdueDay = bwOverdueRecord.getOverdueDay();
					borrowRcordVO.setDaySpace(overdueDay);// 逾期天数
					borrowRcordVO.setOverAmont(overdueAmountStr);
					overAmont = NumberUtils.toDouble(overdueAmountStr, 0.0);
					logger.info("逾期天数为：" + overdueDay + ActivityConstant.MSG_BEFORE + "逾期金额为：" + overdueAmountStr);
				} else {
					borrowRcordVO.setDaySpace(0);// 逾期天数
					borrowRcordVO.setOverAmont("0.00");
				}

				borrowRcordVO.setXuDaiAmount(xuDaiAmount);// 续期费用
				logger.info("续期次数为：" + xuDaiCount + ActivityConstant.MSG_BEFORE + "续期费用为 ： " + xuDaiAmount);

				// 查询最大的优惠金额
				ActivityDiscountDistribute activityDiscountDistribute = activityDiscountDistributeService
						.findMaxActivityDiscountDistribute(bwOrder.getBorrowerId());
				Double maxAmount = activityDiscountDistribute.getAmount();
				maxAmount = (null == maxAmount) ? 0.00 : maxAmount;
				borrowRcordVO.setMaxAmount(df.format(maxAmount));// 设置最大优惠券
				logger.info("最大优惠券金额为： " + maxAmount);
				// 还款总金额，使用优惠券后金额
				Double totalAmount = 0.00;
				// 还款总金额（还款金额+逾期金额），不使用优惠券
				Double totalNotCouponAmount = DoubleUtil.add(realityRepayMoney, overAmont);
				Double zjw = bwRepaymentPlan.getZjw();
				if (zjw == null || zjw < 0) {
					zjw = 0.0;
				}
				borrowRcordVO.setZjwAmount(DoubleUtil.toTwoDecimal(zjw));
				if (1 == repayStatus || 3 == repayStatus || 4 == repayStatus) {// 1 未还款
					// totalAmount = DoubleUtil.sub(totalNotCouponAmount, maxAmount);//未还款默认使用优惠券
					totalAmount = totalNotCouponAmount;// 未还款默认不使用优惠券
				}
				Double userAmount = 0.00;
				// 借款人已还款
				if (2 == repayStatus || repayStatus == 4) {// 已还款或展期
					userAmount = activityDiscountUseageService.getDiscountUseageByRepaymentPlanId(order_id);
					totalAmount = DoubleUtil.sub(DoubleUtil.add(realityRepayMoney, overAmont), userAmount);
				}
				if (totalAmount < 0.0) {
					totalAmount = 0.0;
				}
				borrowRcordVO.setUserAmount(df.format(userAmount));// 已还款使用优惠券金额
				borrowRcordVO.setTotalAmount(df.format(totalAmount));// 总还款金额
				borrowRcordVO.setTotalNotCouponAmount(df.format(totalNotCouponAmount));// 还款总金额（还款金额+逾期金额），不使用优惠券
				borrowRcordVO.setTotalXudaiAmount(df.format(totalXudaiAmount));// 设置总费用
				// 是否可以续贷
				// 判断是否可以续期
				AppResponseResult canXuDaiResult = productService.canXuDai(order_id);
				if (!"000".equals(canXuDaiResult.getCode())) {
					borrowRcordVO.setCanXuDai(false);
					borrowRcordVO.setXuDaiErrMsg(canXuDaiResult.getMsg());
				} else {
					borrowRcordVO.setCanXuDai(true);
				}

				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
				JSONObject fromObject = JSONObject.fromObject(borrowRcordVO);
				respResult.setResult(fromObject);
				return respResult;
			} else {
				respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
				respResult.setMsg("该工单未生成还款计划");
				respResult.setResult(null);
				return respResult;
			}

		}
		respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
		respResult.setMsg("订单未绑定产品编号");
		JSONObject fromObject = JSONObject.fromObject(borrowRcordVO);
		respResult.setResult(fromObject);
		return respResult;
	}

	@Override
	public BwOrder selectLastOrder(Long borrowerId, Integer productType, List<Object> statusIdList) {
		Example example = new Example(BwOrder.class);
		Criteria criteria = example.createCriteria();
		if (statusIdList != null && !statusIdList.isEmpty()) {
			criteria.andIn("statusId", statusIdList);
		}
		if (productType != null) {
			criteria.andEqualTo("productType", productType);
		}
		criteria.andEqualTo("borrowerId", borrowerId);
		example.setOrderByClause(" create_time desc limit 1");
		List<BwOrder> orderList = selectByExample(example);
		if (orderList != null && !orderList.isEmpty()) {
			return orderList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwOrderService#fandAllOrderBy(java.lang.String)
	 */
	@Override
	public List<BwOrder> fandAllOrderBy(String bwId) {
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", bwId);
//		example.setOrderByClause(" createTime desc ");
		example.orderBy("createTime").desc();
		List<BwOrder> list = findBwOrderByExample(example);
		return list;
	}

	@Override
	public Long findProcessOrder(String borrowerId) {
		String sql = "select count(1) from bw_order a where a.borrower_id=" + borrowerId
				+ " and a.status_id in (1,2,3,4,5,11,12,14,9,13,8)";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public AppResponseResult updateAndTakeMoney(Long orderId, Integer repayType) throws Exception {
		AppResponseResult result = new AppResponseResult();
		// 计算合同金额
		BwOrder order = null;
		if (orderId != null && orderId > 0L) {
			order = mapper.selectByPrimaryKey(orderId);
		}
		if (CommUtils.isNull(order)) {
			result.setCode("1006");
			result.setMsg("根据工单id获取工单信息为空");
			return result;
		}
		if (CommUtils.isNull(order.getBorrowAmount())) {
			result.setCode("1007");
			result.setMsg("借款金额为空");
			return result;
		}
		Long statusId = order.getStatusId();
		// 获取利率字典表信息
		BwProductDictionary bwProductDictionary = productService.queryByOrderId(orderId);
		Double contractMonthRate = 0.0;
		Double repayAmount = 0.0;
		Double contractAmount = 0.0;
		int orderTerm = 1;// 废弃字段
		// 等额本息
		// 计算合同月利率
		contractMonthRate = bwProductDictionary.getpBorrowRateMonth();
		// 计算还款金额
		repayAmount = DoubleUtil.round(((order.getBorrowAmount() / orderTerm)
				+ (order.getBorrowAmount() * bwProductDictionary.getpInvestRateMonth())), 2);
		// 计算合同金额
		contractAmount = DoubleUtil.round((repayAmount * (Math.pow(1 + contractMonthRate, orderTerm) - 1))
				/ (contractMonthRate * (Math.pow(1 + contractMonthRate, orderTerm))), 2);
		BwOrder bo = mapper.selectByPrimaryKey(orderId);
		bo.setRepayTerm(1);// 该字段废弃，关联产品表的期限
		if (bo.getRepayType() == null) {
			bo.setRepayType(repayType);
		}
		bo.setBorrowRate(bwProductDictionary.getpInvestRateMonth());
		bo.setContractRate(bwProductDictionary.getpInvesstRateYear());
		bo.setContractMonthRate(contractMonthRate);
		bo.setStatusId(11l);
		bo.setContractAmount(contractAmount);
		Date nowDate = new Date();
		// 工单修改时间
		bo.setUpdateTime(nowDate);
		logger.info("工单状态=============" + statusId);
		if (statusId == 4) {// 签约
			int num = updateBwOrder(bo);
			logger.info("修改工单条数=============" + num);
			if (num == 0) {
				result.setCode("1004");
				result.setMsg("修改工单失败");
				return result;
			}

			// 保存签约时间
			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord().setOrderId(orderId)
					.setSignTime(nowDate);
			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);

			RedisUtils.lpush("system:contract", orderId.toString());
			logger.info("放入生成合同队列=============" + orderId);
			RedisUtils.hdel("arbitration:isFirst", orderId.toString());
			logger.info("清除第一次点击确认拿钱标识=============" + orderId);
		}
		result.setCode("000");
		result.setMsg("成功");
		result.setResult(bo);
		// 签约成功存储orderId到redis中 dycode0001
		try {
			RedisUtils.rpush("order:sign", String.valueOf(orderId));
		} catch (Exception e) {
			logger.error("存储orderId到redeis异常：", e);
		}
		return result;
	}

	// 创建口袋订单
	@Override
	public Map<String, Object> findCapitalKoudaiC(Long orderId) {

		String sql = "select a.id,a.order_no,b.id_card,b.name,b.phone,b.sex,a.borrow_amount,c.p_term_type,a.borrow_number,c.p_term,c.p_invesst_rate_year,"
				+ "d.bank_code ,d.card_no from bw_order a LEFT JOIN bw_borrower b on b.id =a.borrower_id "
				+ " LEFT JOIN bw_product_dictionary c ON c.id = a.product_id LEFT JOIN bw_bank_card d on d.borrower_id=b.id WHERE a.id ="
				+ orderId;

		Map<String, Object> map = sqlMapper.selectOne(sql);
		return map;
	}

	// 口袋代扣
	@Override
	public Map<String, Object> findCapitalKoudaiD(Long orderId) {

		String sql = "select a.id,a.order_no,b.id_card,b.name,b.phone,a.borrow_amount,a.borrower_id,"
				+ "d.bank_code ,d.card_no from bw_order a LEFT JOIN bw_borrower b on b.id =a.borrower_id "
				+ " LEFT JOIN bw_product_dictionary c ON c.id = a.product_id LEFT JOIN bw_bank_card d on d.borrower_id=b.id WHERE a.id ="
				+ orderId;

		Map<String, Object> map = sqlMapper.selectOne(sql);
		return map;
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findBwOrderByOrderNo(java.lang.String)
	 */
	@Override
	public BwOrder findBwOrderByOrderNo(String orderNo) {
//		String sql = "select o.* from bw_order o where o.order_no='" + orderNo + "'";
//		return sqlMapper.selectOne(sql, BwOrder.class);
		Example example = new Example(BwOrder.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orderNo", orderNo);
		example.setOrderByClause(" create_time desc");
		List<BwOrder> list = selectByExample(example);
		
		return list == null  || list.isEmpty() ? null : list.get(0);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findBwOrderByPhoneAndChannel(java.lang.String, java.lang.Integer)
	 */
	@Override
	public BwOrder findBwOrderByPhoneAndChannel(String phone, Integer channelId) {
		String sql = "SELECT A.* FROM bw_order A WHERE A.STATUS_ID = 1 AND A.CHANNEL = " + channelId
				+ " AND A.BORROWER_ID = (SELECT B.ID FROM bw_borrower B WHERE B.PHONE = '" + phone
				+ "') ORDER BY A.UPDATE_TIME DESC LIMIT 1";
		return sqlMapper.selectOne(sql, BwOrder.class);
	}

	/**
	 * (code:saas)
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findByTimeAndStatusAndType(java.lang.String, java.lang.String,
	 *      int, int)
	 */
	@Override
	public List<BwOrder> findBwOrderByParams(String startTime, String endTime, int pageSize, int pageNum,
			int orderStatus) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from bw_order where status_id = ");
		sb.append(orderStatus);
		sb.append(" and create_time between '");
		sb.append(startTime);
		sb.append("' and '");
		sb.append(endTime);
		sb.append("' limit ");
		sb.append((pageNum - 1) * pageSize);
		sb.append(", ");
		sb.append(pageSize);
		logger.info("sql---" + sb.toString());
		return sqlMapper.selectList(sb.toString(), BwOrder.class);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findCountByParams(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Integer findCountByParams(String startTime, String endTime, int orderStatus) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) from bw_order where status_id = ");
		sb.append(orderStatus);
		sb.append(" and create_time between '");
		sb.append(startTime);
		sb.append("' and '");
		sb.append(endTime);
		sb.append("'");
		return sqlMapper.selectOne(sb.toString(), Integer.class);
	}

	/**
	 * (code:saas)
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findByTimeAndStatusAndType(java.lang.String, java.lang.String,
	 *      int, int)
	 */
	@Override
	public List<BwOrder> findBwOrderByParams2(String startTime, String endTime, int pageSize, int pageNum,
			int orderStatus) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from bw_order where status_id = ");
		sb.append(orderStatus);
		sb.append(" and update_time between '");
		sb.append(startTime);
		sb.append("' and '");
		sb.append(endTime);
		sb.append("' limit ");
		sb.append((pageNum - 1) * pageSize);
		sb.append(", ");
		sb.append(pageSize);
		logger.info("sql---" + sb.toString());
		return sqlMapper.selectList(sb.toString(), BwOrder.class);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findCountByParams(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Integer findCountByParams2(String startTime, String endTime, int orderStatus) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) from bw_order where status_id = ");
		sb.append(orderStatus);
		sb.append(" and update_time between '");
		sb.append(startTime);
		sb.append("' and '");
		sb.append(endTime);
		sb.append("'");
		return sqlMapper.selectOne(sb.toString(), Integer.class);
	}

	/**
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findByCreateTime(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<Long> findByCreateTime(String startTime, String endTime, int pageSize, int pageNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("select borrower_id from bw_order where status_id = 1 ");
		sb.append("and create_time between '");
		sb.append(startTime);
		sb.append("' and '");
		sb.append(endTime);
		sb.append("' limit ");
		sb.append((pageNum - 1) * pageSize);
		sb.append(", ");
		sb.append(pageSize);
		logger.info("sql---" + sb.toString());
		return sqlMapper.selectList(sb.toString(), Long.class);
	}

	/**
	 * (code:saas)
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findBorrowerIdByStatusIdAndMerchantId(int, int)
	 */
	@Override
	public List<BwBorrower> findByMerchantId(Long merchantId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select bb.id ,bb.name ,bb.phone from bw_order bo");
		sb.append(" right join bw_merchant_order bmo on bo.id= bmo.order_id");
		sb.append(" right join bw_borrower bb on bb.id= bmo.borrower_id");
		sb.append(" where bmo.merchant_id= ");
		sb.append(merchantId);
		sb.append(" and bo.status_id= 6 GROUP BY bb.id");
		logger.info("sql---" + sb.toString());
		return sqlMapper.selectList(sb.toString(), BwBorrower.class);
	}

	/**
	 * (code:s2s)
	 * 
	 * @see com.waterelephant.service.IBwOrderService#findOrderId(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public Long findOrderId(Long borrowerId, Integer orderStatus) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from bw_order where borrower_id =");
		sb.append(borrowerId);
		sb.append(" and status_id =");
		sb.append(orderStatus);
		sb.append(" ORDER BY id desc LIMIT 1");
		return sqlMapper.selectOne(sb.toString(), Long.class);
	}

	/**
	 * 根据三方订单和渠道号查询工单
	 */
	@Override
	public BwOrder findOrderByThirdOrderNoAndChannel(String thirdOrderNo, String channel) {
		String sql = "SELECT * FROM bw_order o JOIN bw_order_rong r ON  o.id = r.order_id AND r.third_order_no=#{third_order_no} AND o.channel="
				+ channel + ";";
		return sqlMapper.selectOne(sql, thirdOrderNo, BwOrder.class);

	}

	/**
	 * 查询最近一次逾期超过days的记录
	 */
	@Override
	public Integer queryLastOverdue(Long borrowerId, String days) {
		String sql = "select count(1) from bw_overdue_record a WHERE a.order_id = (select o.id from bw_order o where o.borrower_id ="
				+ borrowerId + " and o.status_id =6 order by o.update_time desc limit 1) and a.overdue_day > " + days;

		Integer count = sqlMapper.selectOne(sql, Integer.class);
		return count == null ? 0 : count;
	}

	/**
	 * 查询逾期工单
	 */
	@Override
	public Integer queryNowOverdue(Long borrowerId) {
		String sql = "select count(1) from bw_order a where a.borrower_id =" + borrowerId + " and a.status_id = 13";

		Integer count = sqlMapper.selectOne(sql, Integer.class);
		return count == null ? 0 : count;
	}

	/**
	 * 进行中的工单状态
	 * @param borrowerId
	 * @return
	 */
	@Override
	public Integer queryOrderIng(Long borrowerId) {
		//进行中的工单
		List<Integer> inStatus = Arrays.asList(2,3,4,9,10,11,12,13,14);//待还款、逾期
		Integer result = /*bwOrderMapper.*/queryOrderByStates(borrowerId, inStatus);
		return result == null ? 0 : result;
	}
	
	/**
	 * 速秒进行中的工单状态
	 * @param borrowerId
	 * @return
	 */
	@Override
	public Integer querySmOrderIng(Long borrowerId) {
		
		List<Integer> productIds = Arrays.asList(7,8);//速秒、商城
		//进行中的工单
		List<Integer> inStatus = Arrays.asList(2,3,4,9,10,11,12,13,14);//待还款、逾期
		Integer result = /*bwOrderMapper.*/querySmOrderByStates(borrowerId, productIds,inStatus);
		return result == null ? 0 : result;
	}
	
	/**
	 * 未完结工单 (代还款、逾期)
	 * @param borrowerId
	 * @return
	 */
	@Override
	public Integer queryUnderwayOrderCount(Long borrowerId) {
		//未完结工单 
		List<Integer> inStatus = Arrays.asList(9,13);//待还款、逾期
		Integer result = /*bwOrderMapper.*/queryOrderByStates(borrowerId, inStatus);
		return result == null ? 0 : result;
	}

	@Override
	public Integer queryUnclearedOrderCount(Long borrowerId) {
		//未结清
		List<Integer> inStatus = Arrays.asList(9);//待还款
		Integer result = /*bwOrderMapper.*/queryOrderByStates(borrowerId, inStatus);
		return result == null ? 0 : result;
	}

	@Override
	public Integer queryOverdueOrderCount(Long borrowerId) {
		//逾期
		List<Integer> inStatus = Arrays.asList(13);//逾期
		Integer result = /*bwOrderMapper.*/queryOrderByStates(borrowerId, inStatus);
		return result == null ? 0 : result;
	}
	
	public Integer queryOrderByStates(Long borrowerId,List<Integer> inStatus) {
		
		Example example = new Example(BwOrder.class);
		example.createCriteria().andEqualTo("borrowerId", borrowerId).andIn("statusId", new ArrayList<>(inStatus));
		return bwOrderMapper.selectCountByExample(example);
	}
	
	public Integer querySmOrderByStates(Long borrowerId,List<Integer> productIds,List<Integer> inStatus) {
		Example example = new Example(BwOrder.class);
		example.createCriteria()
		.andEqualTo("borrowerId", borrowerId)
		.andIn("productId", new ArrayList<>(productIds))
		.andIn("statusId", new ArrayList<>(inStatus));
		return bwOrderMapper.selectCountByExample(example);
	}
	
}