package com.waterelephant.jiedianqian.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.drainage.service.DrainageService;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.jiedianqian.entity.AddressBook;
import com.waterelephant.jiedianqian.entity.Basic;
import com.waterelephant.jiedianqian.entity.Calls;
import com.waterelephant.jiedianqian.entity.JDQCalculationResponse;
import com.waterelephant.jiedianqian.entity.JieDianQianResponse;
import com.waterelephant.jiedianqian.entity.LoanInfo;
import com.waterelephant.jiedianqian.entity.Operator;
import com.waterelephant.jiedianqian.entity.OrderInfoRequest;
import com.waterelephant.jiedianqian.entity.UserCheckResp;
import com.waterelephant.jiedianqian.entity.UserContact;
import com.waterelephant.jiedianqian.entity.UserInfo;
import com.waterelephant.jiedianqian.service.JieDianQianService;
import com.waterelephant.jiedianqian.util.Base64;
import com.waterelephant.jiedianqian.util.FaceResult;
import com.waterelephant.jiedianqian.util.GzipUtil;
import com.waterelephant.jiedianqian.util.JieDianQianContext;
import com.waterelephant.jiedianqian.util.JieDianQianUtils;
import com.waterelephant.jiedianqian.util.UploadToAliyUtils;
import com.waterelephant.service.BwOperateBasicService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwContactListService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * 
 * 
 * Module:
 * 
 * JieDianQianServiceImpl.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <借点钱>
 */
@Service
public class JieDianQianServiceImpl implements JieDianQianService {

	private Logger logger = Logger.getLogger(JieDianQianServiceImpl.class);

	@Autowired
	private DrainageService drainageService;

	@Autowired
	private IBwBorrowerService bwBorrowerService;

	@Autowired
	private IBwContactListService bwContactListService;

	@Autowired
	private IBwOrderService bwOrderService;

	@Autowired
	private BwOrderRongService bwOrderRongService;

	@Autowired
	private BwOrderAuthService bwOrderAuthService;

	@Autowired
	private IBwWorkInfoService bwWorkInfoService;

	@Autowired
	private BwOperateBasicService bwOperateBasicService;

	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;

	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;

	@Autowired
	private IBwAdjunctService bwAdjunctService;

	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	
	@Autowired
	private BwRepaymentPlanService bwRepaymentPlanService;
	
	@Autowired
	private ThirdCommonService thirdCommonService;
	
	
	private static String CHANNEL = JieDianQianContext.get("channel");// 渠道编码

	/**
	 * 借点钱 -存量用户检验接口
	 * 
	 * @author 张博
	 * @param phone
	 * @param id_number
	 * @return JieDianQianResponse
	 */
	@Override
	public JieDianQianResponse checkUserInfo(String sessionId, String name, String phone, String id_number) {
		logger.info(sessionId + " 开始JieDianQianServiceImpl.userCheck()方法{name=" + name + ",phone=" + phone
				+ ",id_number=" + id_number + "}");

		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		try {
			UserCheckResp userCheckResp = new UserCheckResp();
			// 第一步：是否是老用户
			boolean isOrderUser = drainageService.oldUserFilter2(name, phone, id_number);
			userCheckResp.setUser_type(isOrderUser == true ? "2" : "1"); // 0-借点钱老用户 1-新用户 2-我方老用户

			// 第二步：是否黑名单
			boolean isBlackUser = drainageService.isBlackUser2(name, phone, id_number);
			if (isBlackUser) {
				userCheckResp.setIf_can_loan("0"); // 是否可以借款：0-否；1-是
				userCheckResp.setCan_loan_time(null);
				userCheckResp.setAmount(null); // 不可贷

				jieDianQianResponse.setCode(0);
				jieDianQianResponse.setDesc("命中黑名单规则");
				jieDianQianResponse.setData(userCheckResp);
				logger.info(sessionId + " 结束JieDianQianServiceImpl.checkUserInfo()方法，返回结果："
						+ JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse;
			}

			// 第三步：是否进行中的订单
			boolean isProcessingOrder = drainageService.isPocessingOrder2(name, phone, id_number);
			if (isProcessingOrder) {
				userCheckResp.setIf_can_loan("0"); // 是否可以借款（0：否，1：是）
				userCheckResp.setCan_loan_time(null);
				userCheckResp.setAmount(null); // 不可贷

				jieDianQianResponse.setCode(0);
				jieDianQianResponse.setDesc("命中在贷规则");
				jieDianQianResponse.setData(userCheckResp);
				logger.info(sessionId + " 结束JieDianQianServiceImpl.checkUserInfo()方法，返回结果："
						+ JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse;
			}

			// 第四步：是否有被拒记录
			UserCheckResp isRejectRecord = drainageService.isRejectRecord2(name, phone, id_number);
			
			if (isRejectRecord.getIf_can_loan().equals("0")) {//1等于相当于true,被拒
				userCheckResp.setIf_can_loan("0"); // 是否可以借款（0：否，1：是）
				String can_loan_time=CommUtils.isNull(isRejectRecord.getCan_loan_time())?null:isRejectRecord.getCan_loan_time();
				userCheckResp.setCan_loan_time(can_loan_time);
				userCheckResp.setAmount(null); // 不可贷

				jieDianQianResponse.setCode(0);
				jieDianQianResponse.setDesc("命中被拒规则");
				jieDianQianResponse.setData(userCheckResp);
				logger.info(sessionId + " 结束JieDianQianServiceImpl.checkUserInfo()方法，返回结果："
						+ JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse;
			}

			// 第五步：查询借款信息（二三四规则已过，可以借款）
			userCheckResp.setIf_can_loan("1"); // 是否可以借款（0：否，1：是）
			userCheckResp.setAmount(new BigDecimal(5000)); // 借款额度5000
			userCheckResp.setCan_loan_time(null);

			jieDianQianResponse.setCode(0); // 操作成功
			jieDianQianResponse.setDesc("用户规则通过，允许推送");
			jieDianQianResponse.setData(userCheckResp);

		} catch (Exception e) {
			logger.error(sessionId + " 执行JieDianQianServiceImpl.checkUserInfo()方法异常", e);
			jieDianQianResponse.setCode(-2);
			jieDianQianResponse.setData(null);
			jieDianQianResponse.setDesc("接口调用异常");
		}

		return jieDianQianResponse;
	}

	/**
	 * 
	 * @see com.waterelephant.jiedianqian.service.JieDianQianService#loanCalculate(java.lang.String, java.lang.String)
	 */
	@Override
	public JieDianQianResponse loanCalculate(String sessionId, String amonut,String orderNo) {
		logger.info(sessionId + "开始JieDianQianServiceImpl.loanCalculate()方法{amonut=" + amonut + "}");
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		JDQCalculationResponse jdqCalculationResponse = new JDQCalculationResponse();
		Map<String, String> hm = new HashMap<String, String>();
		
		try {
			// 第一步：验证（最大最小金额验证）
			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNo);
			
			if(CommUtils.isNull(bwOrder)){
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("该订单号"+orderNo+"对应的订单不存在");
				logger.info(sessionId + "结束JieDianQianServiceImpl.loanCalculate()方法，返回结果："
						+ JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse;
				
			}
			Double borrowAmountDU = bwOrder.getBorrowAmount();
			double borrowAmount = borrowAmountDU.doubleValue();
			
			
			int minLoanAmount = (int) borrowAmount;
			int maxLoanAmount = (int) borrowAmount;

			int loanAmount = (int) Double.parseDouble(amonut);
			
			if (loanAmount > maxLoanAmount) {
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("本次借款金额" + loanAmount + "大于最大借款金额" + maxLoanAmount);

				logger.info(sessionId + "结束JieDianQianServiceImpl.loanCalculate()方法，返回结果："
						+ JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse;
			}

			if (loanAmount == 0) {
				hm.put("card_amount", "0");
				jieDianQianResponse.setCode(0);
				jieDianQianResponse.setData(hm);
				jieDianQianResponse.setDesc("提现金额不能为0");
				return jieDianQianResponse;
			}

			if (loanAmount < minLoanAmount) {
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("本次借款金额" + loanAmount + "小于最小借款金额" + minLoanAmount);
				logger.info(sessionId + "结束JieDianQianServiceImpl.loanCalculate()方法，返回结果："
						+ JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse;
			}
			//产品字典表
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(bwOrder.getProductId());
			//总费率
			double fra_rate=DoubleUtil.add(1, bwProductDictionary.getZjwCost());
			//服务费用利率0.18
			Double servicefee = bwProductDictionary.getpFastReviewCost()
		            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
		            + bwProductDictionary.getpCollectionPassagewayCost()
		            + bwProductDictionary.getpCapitalUseCost();
			//计算到账金额：审批金额*0.82
			double arrivelAmount=DoubleUtil.mul(bwOrder.getBorrowAmount(), DoubleUtil.sub(1, servicefee));
			
			
			// 计算
			String[] fra = { DoubleUtil.mul(borrowAmount, fra_rate)+"" };
			String[] loan_days={bwProductDictionary.getpTerm()+"天"};
			String[] loan_terms={"1"};
			
			
			jdqCalculationResponse.setFirst_repay_amount(fra);
			jdqCalculationResponse.setLoan_days(loan_days);
			jdqCalculationResponse.setLoan_terms(loan_terms);
			jdqCalculationResponse.setMax_amount(String.valueOf(maxLoanAmount));
			jdqCalculationResponse.setMin_amount(String.valueOf(minLoanAmount));
			jdqCalculationResponse.setMultiple("100");
			jdqCalculationResponse.setCard_amount(String.valueOf(arrivelAmount));
			
			// 第三步：返回
			jieDianQianResponse.setCode(0);
			jieDianQianResponse.setDesc("success");
			jieDianQianResponse.setData(jdqCalculationResponse);
		} catch (Exception e) {
			logger.error(sessionId + " 执行JieDianQianServiceImpl.loanCalculate()方法异常", e);
			jieDianQianResponse.setCode(-1);
			jieDianQianResponse.setDesc("接口调用异常");
			jieDianQianResponse.setData(jdqCalculationResponse);
		}
		logger.info(sessionId + "结束JieDianQianServiceImpl.loanCalculate()方法，返回结果："
				+ JSON.toJSONString(jieDianQianResponse));
		return jieDianQianResponse;
	}

	@Override
	public JieDianQianResponse saveBwOrder(String sessionId,String bizData) {
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		String methodName = "JieDianQianServiceImpl.savePushOrder";
		logger.info("开始接收借点钱推送工单信息");

		try {
			JSONObject jsonObject = JSON.parseObject(bizData);

			String operatorData = jsonObject.getString("operator");

			operatorData = GzipUtil.uncompress(Base64.decode(operatorData), "utf-8"); // 解压运营商数据

			Operator operator = JSONObject.parseObject(operatorData, Operator.class);

			jsonObject.remove("operator");

			String jsonString = jsonObject.toJSONString();

			// 封装订单推送信息
			OrderInfoRequest orderInfoRequest = JSONObject.parseObject(jsonString, OrderInfoRequest.class);

			if (CommUtils.isNull(orderInfoRequest)) {
				logger.info("接收借点钱推送工单信息失败");
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("接收借点钱推送工单信息失败");
				return jieDianQianResponse;
			}

			UserInfo userData = orderInfoRequest.getUser_info();

			String thridOrderNo = orderInfoRequest.getSource_order_id(); // 借点钱订单号
			String name = userData.getName(); // 姓名
			String phone = userData.getPhone(); // 手机号
			String idCard = userData.getId_card(); // 身份证
			String companyName = userData.getCompany_name(); // 公司名
			
			LoanInfo loan_info = orderInfoRequest.getLoan_info();
			BwBorrower bw = new BwBorrower();
			bw.setPhone(phone);
			bw = bwBorrowerService.findBwBorrowerByAttr(bw);
			
			// 获取产品类型
			BwProductDictionary product = thirdCommonService.getProduct(name, idCard, phone, Integer.parseInt(loan_info.getDay()));
			//存储到订单中
			logger.info("申请天数"+loan_info.getDay());
			logger.info("申请的产品"+product.getId());
			

			BwOrder bwOrder = new BwOrder();
			HashMap<String, String> hm = new HashMap<>();
			if (!CommUtils.isNull(bw)) {
				logger.info("开始查询进行中的订单,borrowerId=" + bw.getId());
				Long count = bwOrderService.findProcessOrder(String.valueOf(bw.getId()));
				logger.info("结束查询进行中的订单,count=" + count);
				if (count != null && count.intValue() > 0) {
					logger.info(phone + "该用户有进行中的订单,不做处理...");
					bwOrder = bwOrderService.findOrderIdByBorrwerId(bw.getId());
					BwOrder ThirdBwOrder = bwOrderService.findOrderNoByThirdOrderNo(thridOrderNo);
					List<Long> processStatus=new ArrayList<Long>();//进行中订单状态
					processStatus.add(1L);
					processStatus.add(2L);
					processStatus.add(3L);
					processStatus.add(4L);
					processStatus.add(5L);
					processStatus.add(11L);
					processStatus.add(12L);
					processStatus.add(14L);
					processStatus.add(9L);
					processStatus.add(13L);
					processStatus.add(8L);
					//根据第三方订单找出订单状态
					Long thirdBwOrderStatus= ThirdBwOrder.getStatusId()==null?null:ThirdBwOrder.getStatusId();
					
					if(!CommUtils.isNull(ThirdBwOrder)&&
							processStatus.contains(thirdBwOrderStatus)){
						jieDianQianResponse.setCode(0);
						jieDianQianResponse.setDesc("该用户有进行中的订单,不做处理...");
					}else{
						jieDianQianResponse.setCode(-1);
						jieDianQianResponse.setDesc("该用户有进行中的订单,不做处理...");
					}
					
					hm.put("orderId", bwOrder.getOrderNo());
					jieDianQianResponse.setData(hm);
					return jieDianQianResponse;
				}
				
			}
			
			long session=Long.parseLong(sessionId);
			int channelId=Integer.parseInt(CHANNEL);
			// 新增OR修改借款人信息
			//BwBorrower borrower = addOrUpdateBwer(name, idCard, phone);
			BwBorrower borrower=thirdCommonService.addOrUpdateBorrower(session, name, idCard, phone, channelId);
			
			// 新增工单
			logger.info("开始新增工单");
			BwOrder order = saveBwOrder(borrower,loan_info,product);
			
			//新增借点钱工单	
			logger.info(phone + "开始新增借点钱工单");
			saveBwROrder(order.getId(), thridOrderNo);
			
			// 2:个人信息
			logger.info("开始新增个人信息");
			saveOrUpdateOrderAuth(order.getId(), 2); // '认证类型，1：运营商 2：个人信息 3：拍照 4：芝麻信用 5：社保 6：公积金 7：邮箱 8：淘宝 9：京东 ',
			
			// 工作信息
			logger.info(phone + "开始新增工作信息");
			String workYears = userData.getCompany_work_year();
			workYears = convertWorkYears(workYears);
			saveWorkInfo(order.getId(), companyName, workYears);


			// 运营商
			logger.info(phone + "开始更新运营商......");
			addOrUpdateOperate(order.getId(), operator, borrower.getId());

			// 通话记录
			logger.info(phone + "开始更新通话记录......");
			addOperateVoice(operator.getCalls(), borrower.getId());
			
			//通讯录
			logger.info(phone + "开始更新通讯录......");
			addOrUpdateBwContactLists(orderInfoRequest.getAddress_book(), borrower.getId());

			// 身份证正面
			logger.info(phone + "开始上传身份证正面照");
			String positiveUrl = UploadToAliyUtils.urlUploadNew(userData.getId_positive(),
					order.getOrderNo() + "_01.jpg");
			logger.info(phone + "当前工单号：" + order.getId() + "的身份证正面url：" + positiveUrl);

			logger.info(phone + "开始更新身份证正面照信息...");
			saveOrUpdateSfzzm(order.getId(), borrower.getId(), positiveUrl);

			// 身份证背面
			logger.info(phone + "开始上传身份证反面照");
			String negativeUrl = UploadToAliyUtils.urlUploadNew(userData.getId_negative(),
					order.getOrderNo() + "_02.jpg");
			logger.info(phone + "当前工单号：" + order.getId() + "的身份证反面url：" + negativeUrl);

			logger.info(phone + "开始更新身份证反面照信息...");
			saveOrUpdateSfzfm(order.getId(), borrower.getId(), negativeUrl);

			// 上传持证照
			logger.info(phone + "开始上传活体照");
			String personUrl = UploadToAliyUtils.urlUploadNew(userData.getHand_id_photo(),
					order.getOrderNo() + "_03.jpg");
			logger.info(phone + "当前工单号：" + order.getId() + "的活体照url：" + personUrl);

			logger.info(phone + "开始活体照片信息...");
			saveOrUpdatePerson(order.getId(), borrower.getId(), personUrl);

			// 3:照片
			saveOrUpdateOrderAuth(order.getId(), 3);

			// 紧急联系人
			logger.info(phone + "新增紧急联系人...");
			saveOrUpdatePersonInfo(orderInfoRequest, order.getId());

			// 修改认证状态为4 ,代表已认证
			logger.info("将借款人:" + borrower.getId() + "的认证状态修改为4");
			borrower = updateBorrower(borrower.getId());

			// 修改工单
			logger.info("将工单:" + order.getId() + "的状态修改为2");
			order.setStatusId(2L);
			HashMap<String, String> hm1 = new HashMap<>();
			hm1.put("channelId", String.valueOf(order.getChannel()));
			hm1.put("orderId",String.valueOf(order.getId()));
			hm1.put("orderStatus", String.valueOf(order.getStatusId()));
			hm1.put("result", "初级审核中");
			String hmData = JSON.toJSONString(hm1);
			RedisUtils.rpush("tripartite:orderStatusNotify:" + order.getChannel(), hmData);
			order.setSubmitTime(Calendar.getInstance().getTime());
			bwOrderService.updateBwOrder(order);

			// 将待审核的信息放入Redis中
			logger.info("开始存入redis[" + SystemConstant.AUDIT_KEY + "]");
			Long result = addRedis(order, borrower, thridOrderNo);
			if (!CommUtils.isNull(result)) {
				logger.info("存入redis[" + SystemConstant.AUDIT_KEY + "]成功");
			} else {
				logger.info("存入redis[" + SystemConstant.AUDIT_KEY + "]失败");
			}
			
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("orderId", order.getOrderNo());
			jieDianQianResponse.setCode(0);
			jieDianQianResponse.setDesc("创建成功");
			jieDianQianResponse.setData(hashMap);
			
		} catch (Exception e) {
			logger.error("接收借点钱推送工单信息异常", e);
			jieDianQianResponse.setCode(-2);
			jieDianQianResponse.setDesc("接收借点钱推送工单信息异常");
		}

		logger.info(methodName + " end,接收借点钱用户信息成功");
		return jieDianQianResponse;
	}
	@Override
	public Map<String, Object> queryOrderInfo(String sessionId,BwOrder bwOrder) {
		Map<String, Object> result=new HashMap<String,Object>();
		try {
			
			logger.info( sessionId +"开始订单状态查询推送接口");
			Long orderId = bwOrder.getId();
			if (CommUtils.isNull(orderId)) {
				logger.info(sessionId +"订单id【"+orderId+"】为空");
				return result;
			}
			 bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) { // 如果没查到,返回
				logger.info( sessionId + " 查询订单信息，返回结果：" + JSON.toJSONString(bwOrder));
				return result;
			}
			// 第二步：通知
			// 0:待审核 （2）
			if(bwOrder.getStatusId()==2){
				result=checking(bwOrder);
			}
			// 2:审核失败（7）
			if(bwOrder.getStatusId()==7||bwOrder.getStatusId()==8){
				result=checkFail(bwOrder);
			}
			// 3:审核成功（4） // 4:待签约（4）
			if(bwOrder.getStatusId()==4){
				result=checkSuccess(bwOrder);
			}
			// loanPend 6:待放款，（12,14）
			if(bwOrder.getStatusId()==12||bwOrder.getStatusId()==14){
				result=loanPend(bwOrder);
			}
			// 7:已放款，(9)
			if(bwOrder.getStatusId()==9){
				result=loanSuccess(bwOrder);
			}
			// 8:已还清，(6) // 
			if(bwOrder.getStatusId()==6){
				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
				bwOverdueRecord.setOrderId(bwOrder.getId());
				bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
				if(!CommUtils.isNull(bwOverdueRecord)&&bwOverdueRecord.getOverdueStatus()==2){
					result=OverdueOff(bwOrder);
				}else{
					result=paidOff(bwOrder);
				}
				
			}
			
			// 13:已逾期，(13)
			if(bwOrder.getStatusId()==13){
				result=Overdue(bwOrder);
			}
		} catch (Exception e) {
			logger.error(sessionId +"借点钱订单状态通知异常：", e);
			return result;
		}
		
		logger.info("查询返回的数据为："+result);
		
		return result;
	}
	/**
	 * 订单状态通知 - 待审核
	 * 
	 * @param bwOrder
	 * @return
	 */
	private Map<String, Object> checking(BwOrder bwOrder) {
		Map<String, Object> result= null;
		
		try {
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 第一步：组装出参
			BwProductDictionary bwProductDictionary = 
					bwProductDictionaryService.findById(Long.parseLong(bwOrder.getProductId().toString()));
			if(CommUtils.isNull(bwProductDictionary)){
				logger.info(bwOrder.getId()+"产品字典为空");
				return result;
			}
			Double servicefee = bwProductDictionary.getpFastReviewCost()
		            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
		            + bwProductDictionary.getpCollectionPassagewayCost()
		            + bwProductDictionary.getpCapitalUseCost();
			
			String apply_time=simpleDateFormat1.format(bwOrder.getCreateTime());//进件时间
			result=new HashMap<String, Object>();;
			String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
			result.put("order_id", bwOrder.getOrderNo());
			result.put("status", status);
			result.put("approval_amount", "0");
			result.put("approval_periods", "0");
			result.put("sign_loan_amount", "0");
			result.put("sign_loan_periods", bwProductDictionary.getProductType()); // 贷款期数
			result.put("approval_period_days", bwProductDictionary.getpTerm()); // 审批每期天数
			result.put("approval_days", bwProductDictionary.getpTerm()); // 审批总天数
			result.put("interest_rate", servicefee+""); // 贷款利息率
			result.put("overdue_rate", bwProductDictionary.getRateOverdue()); // 逾期费率
			result.put("apply_time",apply_time );
			Map<String, String> payResult = new HashMap<String, String>();
			List<Map<String,String >> planMaps=new ArrayList<Map<String,String >>();
			planMaps.add(payResult);
			result.put("repayment_plan", planMaps);
		} catch (Exception e) {
			logger.error("借点钱订单状态[审核失败]通知异常：", e);
			return null;
		}
			return result;
	
	}

	/**
	 * 订单状态通知 - 审核失败
	 * 
	 * @param bwOrder
	 * @return
	 */
	private Map<String, Object>  checkFail(BwOrder bwOrder) {
		Map<String, Object> result= null;
		try {
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 第一步：组装出参
			BwProductDictionary bwProductDictionary = 
					bwProductDictionaryService.findById(Long.parseLong(bwOrder.getProductId().toString()));
			if(CommUtils.isNull(bwProductDictionary)){
				logger.info(bwOrder.getId()+"产品字典为空");
				return result;
			}
			Double servicefee = bwProductDictionary.getpFastReviewCost()
		            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
		            + bwProductDictionary.getpCollectionPassagewayCost()
		            + bwProductDictionary.getpCapitalUseCost();
			
			String apply_time=simpleDateFormat1.format(bwOrder.getCreateTime());//进件时间
			result = new HashMap<String, Object>();
			String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
			result.put("order_id", bwOrder.getOrderNo());
			result.put("status", status);
			result.put("approval_amount", "0");
			result.put("approval_periods", "0");
			result.put("sign_loan_amount", "0");
			result.put("sign_loan_periods", bwProductDictionary.getProductType()); // 贷款期数
			result.put("approval_period_days", bwProductDictionary.getpTerm()); // 审批每期天数
			result.put("approval_days", bwProductDictionary.getpTerm()); // 审批总天数
			result.put("interest_rate", servicefee+""); // 贷款利息率
			result.put("overdue_rate", bwProductDictionary.getRateOverdue()); // 逾期费率
			result.put("check_finish_time",simpleDateFormat1.format(bwOrder.getUpdateTime()));//时间
			result.put("apply_time",apply_time );
			Map<String, String> payResult = new HashMap<String, String>();
			List<Map<String,String >> planMaps=new ArrayList<Map<String,String >>();
			planMaps.add(payResult);
			result.put("repayment_plan", planMaps);
			
		} catch (Exception e) {
			logger.error("借点钱订单状态[审核失败]通知异常：", e);
			return null;
		}
		return result;
	}	

	/**
	 * 订单状态通知 - 审核成功
	 * 
	 * @param bwOrder
	 * @return
	 */
	private Map<String, Object> checkSuccess(BwOrder bwOrder) {
		Map<String, Object> result= null;
		try {
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 第一步：组装出参
			BwProductDictionary bwProductDictionary = 
					bwProductDictionaryService.findById(Long.parseLong(bwOrder.getProductId().toString()));
			if(CommUtils.isNull(bwProductDictionary)){
				logger.info(bwOrder.getId()+"产品字典为空");
				return  result;
			}
			Double servicefee = bwProductDictionary.getpFastReviewCost()
		            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
		            + bwProductDictionary.getpCollectionPassagewayCost()
		            + bwProductDictionary.getpCapitalUseCost();
			
			String apply_time=simpleDateFormat1.format(bwOrder.getCreateTime());//进件时间
			 result = new HashMap<String, Object>();
			String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
			result.put("order_id", bwOrder.getOrderNo());
			result.put("status", status);
			result.put("approval_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("approval_periods",bwProductDictionary.getProductType());
			result.put("sign_loan_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("sign_loan_periods", bwProductDictionary.getProductType()); // 贷款期数
			result.put("approval_period_days", bwProductDictionary.getpTerm()); // 审批每期天数
			result.put("approval_days", bwProductDictionary.getpTerm()); // 审批总天数
			result.put("interest_rate", servicefee+""); // 贷款利息率
			result.put("overdue_rate", bwProductDictionary.getRateOverdue()); // 逾期费率
			result.put("check_finish_time",simpleDateFormat1.format(bwOrder.getUpdateTime()));//时间
			result.put("apply_time",apply_time);
			Map<String, String> payResult = new HashMap<String, String>();
			List<Map<String,String >> planMaps=new ArrayList<Map<String,String >>();
			planMaps.add(payResult);
			result.put("repayment_plan", planMaps);
			
			
		} catch (Exception e) {
			logger.error("借点钱订单状态[审核成功]通知异常：", e);
			return null;
		}
		return result;
	}
	/**
	 * 订单状态通知 - 待放款
	 * 
	 * @param bwOrder
	 * @return
	 */
	private Map<String, Object> loanPend(BwOrder bwOrder) {
		Map<String, Object> result= null;
		try {

			// 第一步：组装出参
			BwProductDictionary bwProductDictionary = 
					bwProductDictionaryService.findById(Long.parseLong(bwOrder.getProductId().toString()));
			if(CommUtils.isNull(bwProductDictionary)){
				logger.info(bwOrder.getId()+"产品字典为空");
				return result;
			}
			Double servicefee = bwProductDictionary.getpFastReviewCost()
		            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
		            + bwProductDictionary.getpCollectionPassagewayCost()
		            + bwProductDictionary.getpCapitalUseCost();
			
			 result = new HashMap<String, Object>();
			String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
			result.put("order_id", bwOrder.getOrderNo());
			result.put("status", status);
			result.put("approval_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("approval_periods",bwProductDictionary.getProductType());
			result.put("sign_loan_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("sign_loan_periods", bwProductDictionary.getProductType()); // 贷款期数
			result.put("approval_period_days", bwProductDictionary.getpTerm()); // 审批每期天数
			result.put("approval_days", bwProductDictionary.getpTerm()); // 审批总天数
			result.put("interest_rate", servicefee+""); // 贷款利息率
			result.put("overdue_rate", bwProductDictionary.getRateOverdue()); // 逾期费率
			Map<String, String> payResult = new HashMap<String, String>();
			List<Map<String,String >> planMaps=new ArrayList<Map<String,String >>();
			planMaps.add(payResult);
			result.put("repayment_plan", planMaps);
		} catch (Exception e) {
			logger.error("借点钱订单状态[待放款]通知异常：", e);
			return null;
		}
		return result;
				
	}
	
	/**
	 * 订单状态通知 - 放款成功
	 * 
	 * @param bwOrder
	 * @return
	 */
	private Map<String, Object> loanSuccess(BwOrder bwOrder) {
		Map<String, Object> result= null;
		try {

			// 第一步：组装出参
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BwProductDictionary bwProductDictionary = 
					bwProductDictionaryService.findById(Long.parseLong(bwOrder.getProductId().toString()));
			if(CommUtils.isNull(bwProductDictionary)){
				logger.info(bwOrder.getId()+"产品字典为空");
				return result;
			}
			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrder.getId());
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
			if(CommUtils.isNull(bwRepaymentPlan)){
				logger.info("该订单["+bwOrder.getId()+"]还款计划不存在");
				return result;
			}
			//利率
			Double servicefee = bwProductDictionary.getpFastReviewCost()
            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
            + bwProductDictionary.getpCollectionPassagewayCost()
            + bwProductDictionary.getpCapitalUseCost();
			//利率加额外湛江委
			double perioFee_rate=DoubleUtil.add(servicefee, bwProductDictionary.getZjwCost());
			//到账本金利率
			double amount_rate=DoubleUtil.sub(1, servicefee);
			
			String apply_time=simpleDateFormat1.format(bwOrder.getCreateTime());//进件时间
			result = new HashMap<String, Object>();
			String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
			result.put("order_id", bwOrder.getOrderNo());
			result.put("status", status);
			result.put("approval_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("approval_periods",bwProductDictionary.getProductType());
			result.put("sign_loan_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("sign_loan_periods", bwProductDictionary.getProductType()); // 贷款期数
			result.put("approval_period_days", bwProductDictionary.getpTerm()); // 审批每期天数
			result.put("approval_days", bwProductDictionary.getpTerm()); // 审批总天数
			result.put("interest_rate", servicefee+""); // 贷款利息率
			result.put("overdue_rate", bwProductDictionary.getRateOverdue()); // 逾期费率
			result.put("apply_time",apply_time );
			Map<String, String> payResult = new HashMap<String, String>();
			List<Map<String, String>> planMaps = new ArrayList<Map<String, String>>();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//计算手续费用
			double perioFee=DoubleUtil.mul(bwOrder.getBorrowAmount(), perioFee_rate);
			//计划还款时间
			String plan_repayment_time = simpleDateFormat.format(bwRepaymentPlan.getRepayTime());
			
			String grant_finish_time=simpleDateFormat1.format(bwRepaymentPlan.getCreateTime());
			result.put("grant_finish_time", grant_finish_time);
			payResult.put("status", "1");
			payResult.put("plan_repayment_time", plan_repayment_time); // 计划还款日期
			payResult.put("amount",DoubleUtil.mul(bwOrder.getBorrowAmount(), amount_rate)+"" ); // 本期还款本金，单位元 repayCorpusMoney
			payResult.put("period_fee", String.valueOf(perioFee)); // 本期手续（利息）费，单位元
			payResult.put("period", "1"); // 本期期数
			
			payResult.put("overdue", "0"); // 是否逾期，0未逾期，1逾期
			planMaps.add(payResult);
			result.put("repayment_plan", planMaps);
			
		} catch (Exception e) {
			logger.error("借点钱订单状态[放款]通知异常：", e);
			return null;
		}
		return result;
	}
	/**
	 * 订单状态通知 - 已还清逾期
	 * 
	 * @param bwOrder
	 * @return
	 */
	private Map<String, Object> OverdueOff(BwOrder bwOrder) {
		Map<String, Object> result= null;
		try {
			// 第一步：组装出参
			BwProductDictionary bwProductDictionary = 
					bwProductDictionaryService.findById(Long.parseLong(bwOrder.getProductId().toString()));
			if(CommUtils.isNull(bwProductDictionary)){
				logger.info(bwOrder.getId()+"产品字典为空");
				return result;
			}
			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrder.getId());
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
			if(CommUtils.isNull(bwRepaymentPlan)){
				logger.info("该订单["+bwOrder.getId()+"]还款计划不存在");
				return result;
			}
			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			bwOverdueRecord.setOrderId(bwOrder.getId());
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
			if(CommUtils.isNull(bwOverdueRecord)){
				logger.info("该订单["+bwOrder.getId()+"]逾期记录不存在");
				return result ;
			}
			//计算逾期费用
			double advance=CommUtils.isNull(bwOverdueRecord.getAdvance())?0.0:bwOverdueRecord.getAdvance();
			double planMoney = DoubleUtil.sub(bwOverdueRecord.getOverdueAccrualMoney(),
					advance);
			//利率
			Double servicefee = bwProductDictionary.getpFastReviewCost()
            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
            + bwProductDictionary.getpCollectionPassagewayCost()
            + bwProductDictionary.getpCapitalUseCost();
			//利率加额外湛江委
			double perioFee_rate=DoubleUtil.add(servicefee, bwProductDictionary.getZjwCost());
			//到账本金利率
			double amount_rate=DoubleUtil.sub(1, servicefee);
			
			result = new HashMap<String, Object>();
			//String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
			result.put("order_id", bwOrder.getOrderNo());
			result.put("status", "10");
			result.put("approval_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("approval_periods",bwProductDictionary.getProductType());
			result.put("sign_loan_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("sign_loan_periods", bwProductDictionary.getProductType()); // 贷款期数
			result.put("approval_period_days", bwProductDictionary.getpTerm()); // 审批每期天数
			result.put("approval_days", bwProductDictionary.getpTerm()); // 审批总天数
			result.put("interest_rate", servicefee+""); // 贷款利息率
			result.put("overdue_rate", bwProductDictionary.getRateOverdue()); // 逾期费率
			Map<String, String> payResult = new HashMap<String, String>();
			List<Map<String, String>> planMaps = new ArrayList<Map<String, String>>();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//计算手续费用
			double perioFee=DoubleUtil.mul(bwOrder.getBorrowAmount(), perioFee_rate);
			//计划还款时间
			String plan_repayment_time = simpleDateFormat.format(bwRepaymentPlan.getRepayTime());
			String true_repayment_time = simpleDateFormat.format(bwOrder.getUpdateTime());
			payResult.put("status", "3");
			payResult.put("plan_repayment_time", plan_repayment_time); // 计划还款日期
			payResult.put("true_repayment_time", true_repayment_time);//还款时间
			payResult.put("amount",DoubleUtil.mul(bwOrder.getBorrowAmount(), amount_rate)+"" ); // 本期还款本金，单位元 repayCorpusMoney
			payResult.put("period_fee", String.valueOf(perioFee)); // 本期手续（利息）费，单位元
			payResult.put("period", "1"); // 本期期数
			payResult.put("overdue_fee", planMoney < 0 ? "0" : planMoney + ""); // 逾期罚款，单位元
			payResult.put("overdue", "1"); // 是否逾期，0未逾期，1逾期
			payResult.put("overdue_day", bwOverdueRecord.getOverdueDay() + ""); // 逾期天数
			planMaps.add(payResult);
			result.put("repayment_plan", planMaps);
			
			
		
			
		} catch (Exception e) {
			logger.error("借点钱订单状态[已还清逾期]通知异常：", e);
			return null;
		}
		return result;
	}
	/**
	 * 订单状态通知 - 已还清
	 * 
	 * @param bwOrder
	 * @return
	 */
	private Map<String, Object> paidOff(BwOrder bwOrder) {
		Map<String, Object> result= null;
		try {
			// 第一步：组装出参
			BwProductDictionary bwProductDictionary = 
					bwProductDictionaryService.findById(Long.parseLong(bwOrder.getProductId().toString()));
			if(CommUtils.isNull(bwProductDictionary)){
				logger.info("该订单["+bwOrder.getId()+"]产品字典为空");
				return result;
			}
			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrder.getId());
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
			if(CommUtils.isNull(bwRepaymentPlan)){
				logger.info("该订单["+bwOrder.getId()+"]还款计划不存在");
				return result;
			}
			//利率
			Double servicefee = bwProductDictionary.getpFastReviewCost()
            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
            + bwProductDictionary.getpCollectionPassagewayCost()
            + bwProductDictionary.getpCapitalUseCost();
			//利率加额外湛江委
			double perioFee_rate=DoubleUtil.add(servicefee, bwProductDictionary.getZjwCost());
			//到账本金利率
			double amount_rate=DoubleUtil.sub(1, servicefee);
			
			result = new HashMap<String, Object>();
			String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
			result.put("order_id", bwOrder.getOrderNo());
			result.put("status", status);
			result.put("approval_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("approval_periods",bwProductDictionary.getProductType());
			result.put("sign_loan_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("sign_loan_periods", bwProductDictionary.getProductType()); // 贷款期数
			result.put("approval_period_days", bwProductDictionary.getpTerm()); // 审批每期天数
			result.put("approval_days", bwProductDictionary.getpTerm()); // 审批总天数
			result.put("interest_rate", servicefee+""); // 贷款利息率
			result.put("overdue_rate", bwProductDictionary.getRateOverdue()); // 逾期费率
			Map<String, String> payResult = new HashMap<String, String>();
			List<Map<String, String>> planMaps = new ArrayList<Map<String, String>>();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//计算手续费用
			double perioFee=DoubleUtil.mul(bwOrder.getBorrowAmount(), perioFee_rate);
			//计划还款时间
			String plan_repayment_time = simpleDateFormat.format(bwRepaymentPlan.getRepayTime());
			String true_repayment_time = simpleDateFormat.format(bwOrder.getUpdateTime());
			payResult.put("status", "2");
			payResult.put("plan_repayment_time", plan_repayment_time); // 计划还款日期
			payResult.put("amount",DoubleUtil.mul(bwOrder.getBorrowAmount(), amount_rate)+"" ); // 本期还款本金，单位元 repayCorpusMoney
			payResult.put("period_fee", String.valueOf(perioFee)); // 本期手续（利息）费，单位元
			payResult.put("period", "1"); // 本期期数
			payResult.put("true_repayment_time", true_repayment_time);//还款时间
			payResult.put("overdue", "0"); // 是否逾期，0未逾期，1逾期
			planMaps.add(payResult);
			result.put("repayment_plan", planMaps);
		} catch (Exception e) {
			logger.error("借点钱订单状态[已还清]通知异常：", e);
			return null;
		}
		return result;
	}
	/**
	 * 订单状态通知 - 逾期
	 * 
	 * @param bwOrder
	 * @return
	 */
	private Map<String, Object> Overdue(BwOrder bwOrder) {
		Map<String, Object> result= null;
		try {
			// 第一步：组装出参
			BwProductDictionary bwProductDictionary = 
					bwProductDictionaryService.findById(Long.parseLong(bwOrder.getProductId().toString()));
			if(CommUtils.isNull(bwProductDictionary)){
				logger.info("该订单["+bwOrder.getId()+"]产品字典为空");
				return result;
			}
			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrder.getId());
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
			if(CommUtils.isNull(bwRepaymentPlan)){
				logger.info("该订单["+bwOrder.getId()+"]还款计划不存在");
				return result;
			}
			//利率
			Double servicefee = bwProductDictionary.getpFastReviewCost()
            + bwProductDictionary.getpPlatformUseCost() + bwProductDictionary.getpNumberManageCost()
            + bwProductDictionary.getpCollectionPassagewayCost()
            + bwProductDictionary.getpCapitalUseCost();
			//利率加额外湛江委
			double perioFee_rate=DoubleUtil.add(servicefee, bwProductDictionary.getZjwCost());
			//到账本金利率
			double amount_rate=DoubleUtil.sub(1, servicefee);
			
			result = new HashMap<String, Object>();
			String status = JieDianQianUtils.convertStatus(bwOrder.getStatusId()); // 状态转换
			result.put("order_id", bwOrder.getOrderNo());
			result.put("status", status);
			result.put("approval_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("approval_periods",bwProductDictionary.getProductType());
			result.put("sign_loan_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
			result.put("sign_loan_periods", bwProductDictionary.getProductType()); // 贷款期数
			result.put("approval_period_days", bwProductDictionary.getpTerm()); // 审批每期天数
			result.put("approval_days", bwProductDictionary.getpTerm()); // 审批总天数
			result.put("interest_rate", servicefee+""); // 贷款利息率
			result.put("overdue_rate", bwProductDictionary.getRateOverdue()); // 逾期费率
			Map<String, String> payResult = new HashMap<String, String>();
			List<Map<String, String>> planMaps = new ArrayList<Map<String, String>>();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//计算手续费用
			double perioFee=DoubleUtil.mul(bwOrder.getBorrowAmount(), perioFee_rate);
			//计划还款时间
			String plan_repayment_time = simpleDateFormat.format(bwRepaymentPlan.getRepayTime());
			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			bwOverdueRecord.setOrderId(bwOrder.getId());
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
			//计算逾期费用
			double advance=CommUtils.isNull(bwOverdueRecord.getAdvance())?0.0:bwOverdueRecord.getAdvance();
			double planMoney = DoubleUtil.sub(bwOverdueRecord.getOverdueAccrualMoney(),
					advance);
			if(CommUtils.isNull(bwOverdueRecord)){
				logger.info("该订单["+bwOrder.getId()+"]逾期记录不存在");
				return null;
			}
			payResult.put("status", "4");
			payResult.put("plan_repayment_time", plan_repayment_time); // 计划还款日期
			payResult.put("amount",DoubleUtil.mul(bwOrder.getBorrowAmount(), amount_rate)+"" ); // 本期还款本金，单位元 repayCorpusMoney
			payResult.put("period_fee", String.valueOf(perioFee)); // 本期手续（利息）费，单位元
			payResult.put("period", "1"); // 本期期数
			
			payResult.put("overdue_fee", planMoney < 0 ? "0" : planMoney + ""); // 逾期罚款，单位元
			payResult.put("overdue_day", bwOverdueRecord.getOverdueDay() + ""); // 逾期天数
			payResult.put("overdue", "1"); // 是否逾期，0未逾期，1逾期
			
			planMaps.add(payResult);
			result.put("repayment_plan", planMaps);
			
			
		
			
		} catch (Exception e) {
			logger.error("借点钱订单状态[逾期]通知异常：", e);
			return null;
		}
		return result;
		
	}

	private BwOrder updateBorrower(BwOrder bwOrder, Long borrowerId) {
		bwOrder = bwOrderService.findBwOrderByOrderNo(bwOrder.getOrderNo());
		bwOrder.setBorrowerId(borrowerId);
		bwOrder.setId(bwOrder.getId());
		bwOrder.setStatusId(1L);
		bwOrder.setCreateTime(Calendar.getInstance().getTime());
		bwOrder.setChannel(Integer.valueOf(CHANNEL));
		bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE)); // '免罚息天数',
		bwOrder.setApplyPayStatus(0); // 申请还款状态 0:未申请 1:申请
		bwOrder.setProductId(3); // 产品类型（默认3）
		bwOrder.setProductType(1); // '产品类型(1、单期，2、分期)',

		bwOrderService.update(bwOrder);

		logger.info("工单创建成功，工单ID为：" + bwOrder.getId());
		return bwOrder;
	}

	private BwOrder saveBwOrder(BwBorrower borrower,LoanInfo loan_info ,BwProductDictionary product) {
		String money = loan_info.getMoney();
		// 创建工单
		BwOrder order = new BwOrder();
		order.setOrderNo(OrderUtil.generateOrderNo());
		order.setBorrowerId(borrower.getId());
		order.setStatusId(1L); // 草稿
		order.setCreateTime(Calendar.getInstance().getTime());
		order.setChannel(Integer.valueOf(CHANNEL));
		order.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE)); // '免罚息天数',
		order.setApplyPayStatus(0); // 申请还款状态 0:未申请 1:申请
		order.setProductId(Integer.parseInt(product.getId().toString())); // 产品类型（默认3）
		order.setProductType(1); // '产品类型(1、单期，2、分期)',
		order.setExpectMoney(Double.parseDouble(money));
		bwOrderService.addBwOrder(order);

		logger.info("工单创建成功，工单号为：" + order.getId());

		return order;
	}

	private String convertWorkYears(String workYears) {
		String str = "";
		switch (workYears) {
		case "1-5个月":
			str = "1年以内";
			break;
		case "6-11个月":
			str = "1年以内";
			break;
		case "1-3年":
			str = "1-3年";
			break;
		case "4-7年":
			str = "4-7年";
			break;
		case "7年以上":
			str = "4-7年";
			break;
		default:
			break;
		}
		return str;
	}

	private Long addRedis(BwOrder bwOrder, BwBorrower borrower, String thirdOrderId) {
		SystemAuditDto systemAuditDto = new SystemAuditDto();
		systemAuditDto.setIncludeAddressBook(0);
		systemAuditDto.setOrderId(bwOrder.getId());
		systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
		systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
		systemAuditDto.setName(borrower.getName());
		systemAuditDto.setPhone(borrower.getPhone());
		systemAuditDto.setIdCard(borrower.getIdCard());
		systemAuditDto.setChannel(Integer.valueOf(CHANNEL));
		systemAuditDto.setThirdOrderId(thirdOrderId);
		Long result = RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
				JsonUtils.toJson(systemAuditDto));
		return result;
	}

	private BwBorrower updateBorrower(Long borrowerId) {
		BwBorrower borrower = new BwBorrower();
		borrower.setId(borrowerId);
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		borrower.setAuthStep(4);
		borrower.setUpdateTime(Calendar.getInstance().getTime());
		bwBorrowerService.updateBwBorrower(borrower);
		return borrower;
	}

	private void saveOrUpdatePersonInfo(OrderInfoRequest orderInfoRequest, Long orderId) {
		UserContact userData = orderInfoRequest.getUser_contact();
		// 亲属联系人
		String relationName = userData.getName();
		String relationPhone = userData.getMobile();
		// 非亲属联系人
		String unrelationName = userData.getName_spare();
		String unrelationPhone = userData.getMobile_spare();
		BwPersonInfo bpi = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
		if (CommUtils.isNull(bpi)) {
			bpi = new BwPersonInfo();
			bpi.setOrderId(orderId);
			bpi.setRelationName(CommUtils.isNull(relationName) ? "" : relationName);
			bpi.setRelationPhone(CommUtils.isNull(relationPhone) ? "" : JieDianQianUtils.checkPhone(relationPhone));
			bpi.setUnrelationName(CommUtils.isNull(unrelationName) ? "" : unrelationName);
			bpi.setUnrelationPhone(
					CommUtils.isNull(unrelationPhone) ? "" : JieDianQianUtils.checkPhone(unrelationPhone));
			bpi.setUpdateTime(Calendar.getInstance().getTime());
			bpi.setAddress(orderInfoRequest.getUser_info().getLiving_address()); // 居住详细地址
			bwPersonInfoService.save(bpi);
		} else {
			bpi.setOrderId(orderId);
			bpi.setRelationName(relationName);
			bpi.setRelationPhone(JieDianQianUtils.checkPhone(relationPhone));
			bpi.setUnrelationName(unrelationName);
			bpi.setUnrelationPhone(JieDianQianUtils.checkPhone(unrelationPhone));
			bpi.setUpdateTime(Calendar.getInstance().getTime());
			bpi.setAddress(orderInfoRequest.getUser_info().getLiving_address());
			bwPersonInfoService.update(bpi);
		}
	}

	private void saveOrUpdatePerson(Long orderId, Long borrowerId, String czzUrl) {
		BwAdjunct czzbaj = new BwAdjunct();
		czzbaj.setAdjunctType(3);
		czzbaj.setOrderId(orderId);
		czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
		if (CommUtils.isNull(czzbaj)) {
			czzbaj = new BwAdjunct();
			czzbaj.setAdjunctType(3);
			czzbaj.setAdjunctPath(czzUrl);
			czzbaj.setOrderId(orderId);
			czzbaj.setBorrowerId(borrowerId);
			czzbaj.setCreateTime(new Date());
			czzbaj.setPhotoState(1);
			czzbaj.setAdjunctDesc(JSONObject.toJSONString(new FaceResult("000", "success")));
			bwAdjunctService.save(czzbaj);
		} else {
			czzbaj.setAdjunctPath(czzUrl);
			czzbaj.setUpdateTime(Calendar.getInstance().getTime());
			bwAdjunctService.update(czzbaj);
		}
	}

	private void saveOrUpdateSfzfm(Long orderId, Long borrowerId, String sfzfmUrl) {
		BwAdjunct sfzfmbaj = new BwAdjunct();
		sfzfmbaj.setAdjunctType(2);
		sfzfmbaj.setOrderId(orderId);
		sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
		if (CommUtils.isNull(sfzfmbaj)) {
			sfzfmbaj = new BwAdjunct();
			sfzfmbaj.setAdjunctType(2);
			sfzfmbaj.setAdjunctPath(sfzfmUrl);
			sfzfmbaj.setOrderId(orderId);
			sfzfmbaj.setBorrowerId(borrowerId);
			sfzfmbaj.setCreateTime(Calendar.getInstance().getTime());
			bwAdjunctService.save(sfzfmbaj);
		} else {
			sfzfmbaj.setAdjunctPath(sfzfmUrl);
			sfzfmbaj.setUpdateTime(Calendar.getInstance().getTime());
			bwAdjunctService.update(sfzfmbaj);
		}
	}

	private void saveOrUpdateSfzzm(Long orderId, Long borrowerId, String sfzzmUrl) {
		BwAdjunct sfzzmbaj = new BwAdjunct();
		sfzzmbaj.setAdjunctType(1);
		sfzzmbaj.setOrderId(orderId);
		sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
		if (CommUtils.isNull(sfzzmbaj)) {
			sfzzmbaj = new BwAdjunct();
			sfzzmbaj.setAdjunctType(1);
			sfzzmbaj.setAdjunctPath(sfzzmUrl);
			sfzzmbaj.setOrderId(orderId);
			sfzzmbaj.setBorrowerId(borrowerId);
			sfzzmbaj.setCreateTime(Calendar.getInstance().getTime());
			bwAdjunctService.save(sfzzmbaj);
		} else {
			sfzzmbaj.setAdjunctPath(sfzzmUrl);
			sfzzmbaj.setUpdateTime(Calendar.getInstance().getTime());
			bwAdjunctService.update(sfzzmbaj);
		}
	}

	public void addOrUpdateZmxy(Long authOrderId, Long borrowerId, Integer zmScore) {
		// 芝麻信用分
		if (!CommUtils.isNull(zmScore)) {
			BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrowerId);
			logger.info("开始查询芝麻信用记录，borrowerId=" + borrowerId);
			if (CommUtils.isNull(bwZmxyGrade)) {
				bwZmxyGrade = new BwZmxyGrade();
				bwZmxyGrade.setBorrowerId(borrowerId);
				bwZmxyGrade.setZmScore(zmScore);
				bwZmxyGrade.setCreateTime(Calendar.getInstance().getTime());
				bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
				bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
			} else {
				// 更新
				bwZmxyGrade.setZmScore(zmScore);
				bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
				bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
			}

			// 4：芝麻信用
			saveOrUpdateOrderAuth(authOrderId, 4);
		}
	}

	private void addOperateVoice(List<Calls> calls, Long borrowerId) throws Exception {
		SimpleDateFormat sdf_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEsString(borrowerId); // 根据手机号查询最近一次抓取的通话记录时间
		Date callDate = new Date(Long.parseLong(updateTime));
		callDate = sdf_hms.parse(sdf_hms.format(callDate));
		if (!CommUtils.isNull(calls)) {
			for (Calls call : calls) {
				Date jsonCallDate = sdf_hms.parse(call.getStart_time());
				try {
					if (jsonCallDate.after(callDate)) {
						BwOperateVoice bwOperateVoice = new BwOperateVoice();
						bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
						bwOperateVoice.setBorrower_id(borrowerId);

						// 检验日期格式
						String callTime = null;
						callTime = sdf_hms.format(sdf_hms.parse(call.getStart_time()));
						bwOperateVoice.setCall_time(callTime); // 通话时间
						bwOperateVoice.setCall_type("主叫".equals(call.getCall_type()) ? 1 : 2); // 呼叫类型
						bwOperateVoice.setReceive_phone(call.getOther_cell_phone()); // 对方号码
						bwOperateVoice.setTrade_addr(CommUtils.isNull(call.getPlace()) ? "" : call.getPlace()); // 通话地点
						bwOperateVoice.setTrade_time(call.getUse_time()); // 通话时长
						bwOperateVoice.setTrade_type("本地通话".equals(call.getCall_type()) ? 1 : 2); // 通信类型 1.本地通话,国内长途
						bwOperateVoiceService.save(bwOperateVoice);
					}
				} catch (Exception e) {
					logger.error("保存通话记录异常,忽略此条通话记录...", e);
					continue;
				}
			}
		}
	}

	private void addOrUpdateOperate(Long orderId, Operator operator, Long borrowerId) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (CommUtils.isNull(operator)) {
			logger.info("运营商数据为空,borrowerId=" + borrowerId);
			return;
		}

		logger.info("开始查询运营商记录,borrowerId=" + borrowerId);
		BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
		Basic basic = operator.getBasic();
		if (CommUtils.isNull(bwOperateBasic)) {
			logger.info("运营商记录不存在，开始新增，borrowerId=" + borrowerId);
			// 添加基本信息
			bwOperateBasic = new BwOperateBasic();

			String datasource = operator.getDatasource();
			datasource = convertDataSource(datasource); // 转换运营商公司信息

			bwOperateBasic.setUserSource(datasource.toUpperCase()); // 号码类型 chinamobile：移动 chinaunicom：联通
																	// chinatelecom：电信
			bwOperateBasic.setIdCard(CommUtils.isNull(basic.getIdcard()) ? "" : basic.getIdcard());
			bwOperateBasic.setAddr("无"); // 注册该号码所填写的地址
			bwOperateBasic.setPhone(basic.getCell_phone()); // 本机号码
			String phoneRemain = "0";
			DecimalFormat df = new DecimalFormat("#.00");
			bwOperateBasic.setPhoneRemain(df.format((Double.parseDouble(phoneRemain) / 100))); // 当前账户余额
			bwOperateBasic.setRealName(CommUtils.isNull(basic.getReal_name()) ? "" : basic.getReal_name());
			bwOperateBasic.setBorrowerId(borrowerId);

			if (!CommUtils.isNull(basic.getReg_time())) {
				bwOperateBasic.setRegTime(sdf.parse(basic.getReg_time()));
			}
			
			bwOperateBasicService.save(bwOperateBasic);
		
		} else {
			logger.info("运营商记录已存在，开始修改，borrowerId=" + borrowerId);
			// 修改基本信息
			String datasource = operator.getDatasource();
			datasource = convertDataSource(datasource); // 转换运营商公司信息

			bwOperateBasic.setUserSource(datasource.toUpperCase()); // '号码类型',
			bwOperateBasic.setIdCard(CommUtils.isNull(basic.getIdcard()) ? "" : basic.getIdcard()); // '身份证号',
			bwOperateBasic.setAddr("无"); // 注册该号码所填写的地址
			bwOperateBasic.setPhone(basic.getCell_phone()); // 本机号码
			String phoneRemain = "0";
			DecimalFormat df = new DecimalFormat("#.00");
			bwOperateBasic.setPhoneRemain(df.format((Double.parseDouble(phoneRemain) / 100))); // 当前账户余额
			bwOperateBasic.setRealName(CommUtils.isNull(basic.getReal_name()) ? "" : basic.getReal_name());
			bwOperateBasic.setBorrowerId(borrowerId);
			
			if (!CommUtils.isNull(basic.getReg_time())) {
				bwOperateBasic.setRegTime(sdf.parse(basic.getReg_time()));
			}
			bwOperateBasicService.update(bwOperateBasic);
		}

		// 1：运营商
		saveOrUpdateOrderAuth(orderId, 1);
	}

	private String convertDataSource(String datasource) {
		if (datasource.startsWith("chinamobile")) {
			datasource = datasource.substring(0, "chinamobile".length());
		} else if (datasource.startsWith("chinaunicom")) {
			datasource = datasource.substring(0, "chinaunicom".length());
		} else if (datasource.startsWith("chinatelecom")) {
			datasource = datasource.substring(0, "chinatelecom".length());
		}
		return datasource;
	}

	private void addOrUpdateBwContactLists(List<AddressBook> addressBooks, Long borrowerId) {
		// 录入通讯录
		List<BwContactList> list = new ArrayList<BwContactList>();
		for (AddressBook addressBook : addressBooks) {
			if (CommUtils.isNull(addressBook.getName())) {
				continue;
			}
			if (CommUtils.isNull(addressBook.getMobile())) {
				continue;
			}

			try {
				BwContactList bwContactList = new BwContactList();
				bwContactList.setBorrowerId(borrowerId);
				bwContactList.setCreateTime(Calendar.getInstance().getTime());
				bwContactList.setName(addressBook.getName());
				bwContactList.setPhone(addressBook.getMobile());
				bwContactList.setUpdateTime(Calendar.getInstance().getTime());
				list.add(bwContactList);
			} catch (Exception e) {
				logger.error("borrowerId=" + borrowerId + ",录入通讯录出现异常, 忽略此条通讯录...", e);
			}
		}
		bwContactListService.addOrUpdateBwContactLists(list, borrowerId);
	}

	private void saveWorkInfo(Long orderId, String companyName, String workYears) {
		// 更新公司信息
		BwWorkInfo bwi = new BwWorkInfo();
		bwi.setOrderId(orderId);
		bwi.setComName(companyName);
		bwi.setWorkYears(workYears);
		bwi.setUpdateTime(new Date());
		bwi.setCreateTime(new Date());
		bwWorkInfoService.addBwWorkInfo(bwi);
	}

	private BwOrderRong saveBwROrder(Long orderId, String orderNo) {
		BwOrderRong bwOrderRong = new BwOrderRong();
		logger.info("订单号为：" + orderNo);
		
		bwOrderRong.setOrderId(orderId);
		bwOrderRong.setThirdOrderNo(orderNo);
		bwOrderRong.setChannelId(Long.parseLong(CHANNEL));
		bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
		bwOrderRongService.save(bwOrderRong);

		return bwOrderRong;
	}

	private void saveOrUpdateOrderAuth(Long orderId, Integer authType) {
		try {
			logger.info("判断验证记录是否存在,orderId=" + orderId + ",authType=" + authType);
			BwOrderAuth bwOrderAuth = null;
			bwOrderAuth = bwOrderAuthService.findBwOrderAuth(orderId, authType); // '认证类型，1：运营商 2：个人信息 3：拍照 4：芝麻信用 5：社保
																					// 6：公积金 7：邮箱 8：淘宝 9：京东 ',
			logger.info("查询结果:" + JSONObject.toJSONString(bwOrderAuth));
			if (!CommUtils.isNull(bwOrderAuth)) {
				logger.info("验证记录已存在,orderId=" + orderId);
				return;
			}

			bwOrderAuth = new BwOrderAuth();
			logger.info("验证记录不存在，开始新增,orderId=" + orderId);
			bwOrderAuth.setOrderId(orderId);
			bwOrderAuth.setAuth_type(authType);
			bwOrderAuth.setAuth_channel(Integer.valueOf(CHANNEL));
			bwOrderAuth.setCreateTime(Calendar.getInstance().getTime());
			bwOrderAuth.setUpdateTime(Calendar.getInstance().getTime());
			bwOrderAuth.setPhotoState(1);
			bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
			logger.info("验证记录新增成功,orderId=" + orderId);
		} catch (Exception e) {
			logger.error("orderId=" + orderId + ",验证记录新增异常:", e);
		}

	}



	private int getAgeByIdCard(String idCard) {
		Calendar c = Calendar.getInstance();
		int age = 0;
		int year = c.get(Calendar.YEAR);
		String ageNum = idCard.substring(6, 10);
		age = year - Integer.parseInt(ageNum);

		return age;
	}

	private int getSexByIdCard(String idCard) {
		int sex = 0;
		String sexNum = idCard.substring(idCard.length() - 2, idCard.length() - 1);
		if ((Integer.parseInt(sexNum)) % 2 == 0) {
			sex = 0;
		} else {
			sex = 1;
		}
		return sex;
	}

	public String getMsg(String pwd) {
		String pattern = "尊敬的用户您好，恭喜您成功注册，您的登录密码为：{0}，您还可以登录 t.cn/R6rhkzU 查看最新的借款进度哦！";
		String msg = MessageFormat.format(pattern, new Object[] { pwd });
		return msg;
	}

	public String bankNameConvertCode(String bankName) {
		switch (bankName) {
		case "工商银行":
			return "0102";
		case "建设银行":
			return "0105";
		case "农业银行":
			return "0103";
		case "中国银行":
			return "0104";
		case "光大银行":
			return "0303";
		case "兴业银行":
			return "0309";
		case "广发银行":
			return "0306";
		case "浦发银行":
			return "0310";
		case "民生银行":
			return "0305";
		case "中信银行":
			return "0302";
		case "平安银行":
			return "0307";
		case "华夏银行":
			return "0304";
		default:
			return "";
		}
		
		
	}
	
	private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
		bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
		return bwRepaymentPlan;
	}


	
}
