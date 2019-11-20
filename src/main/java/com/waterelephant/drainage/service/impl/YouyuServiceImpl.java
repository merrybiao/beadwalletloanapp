package com.waterelephant.drainage.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.utils.HttpClientHelper;
import com.waterelephant.drainage.entity.youyu.Applydetail;
import com.waterelephant.drainage.entity.youyu.Idinfo;
import com.waterelephant.drainage.entity.youyu.Mobileinfo;
import com.waterelephant.drainage.entity.youyu.Orderinfo;
import com.waterelephant.drainage.entity.youyu.QueryOrderStatus;
import com.waterelephant.drainage.entity.youyu.ResponseCheckUser;
import com.waterelephant.drainage.entity.youyu.SignOrder;
import com.waterelephant.drainage.entity.youyu.Teldata;
import com.waterelephant.drainage.entity.youyu.Userdata;
import com.waterelephant.drainage.entity.youyu.YouyuRequestCheckUser;
import com.waterelephant.drainage.entity.youyu.YouyuRequestPush;
import com.waterelephant.drainage.entity.youyu.YouyuResponse;
import com.waterelephant.drainage.service.DrainageService;
import com.waterelephant.drainage.service.YouyuService;
import com.waterelephant.drainage.util.youyu.YouyuConstant;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwIdentityCard2;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.service.BwOperateBasicService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwIdentityCardService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.third.utils.ThirdUtil;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.utils.UploadToCssUtils;

/**
 * Module: 有鱼接口的实现类 YouyuServiceImpl.java
 * 
 * @author He Longyun
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * 
 */
@Service
@Transactional
public class YouyuServiceImpl implements YouyuService {
	private Logger logger = Logger.getLogger(YouyuServiceImpl.class);
	@Autowired
	private DrainageService drainageService;
	@Autowired
	private ThirdCommonService thirdCommonService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private BwOperateBasicService bwOperateBasicService;
	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;
	@Autowired
	private IBwRepaymentPlanService iBwRepaymentPlanService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Autowired
	private IBwIdentityCardService bwIdentityCardServiceImpl;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;

	@Override
	public YouyuResponse checkUser(long sessionId, YouyuRequestCheckUser requestCheckUser) {
		logger.info(sessionId + "开始YouyuServiceImpl.userCheck()方法" + requestCheckUser);
		String id_card = requestCheckUser.getUser_id();// 银行卡号
		String user_name = requestCheckUser.getUser_name();
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			ResponseCheckUser responseCheckUser = new ResponseCheckUser();
			// 第一步检验是否是黑名单
			boolean isBlackUser = drainageService.isBlackUser(id_card, user_name);
			if (isBlackUser) {
				responseCheckUser.setCode(ResponseCheckUser.CODE_UNAPPLIED);
				responseCheckUser.setDesc("不可申请，该客户为黑名单");
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);// 1:成功 其他：失败

				youyuResponse.setDesc("命中黑名单规则");
				youyuResponse.setData(JSON.toJSONString(responseCheckUser));
				logger.info(sessionId + "结束YouyuServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第二步检验是否是进行的订单
			boolean isProcessingOrder = drainageService.isPocessingOrder(id_card);
			if (isProcessingOrder) {
				responseCheckUser.setCode(ResponseCheckUser.CODE_UNAPPLIED);// 3
				responseCheckUser.setDesc("不可申请，有正在进行的订单");
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);// 业务状态码 1:成功
				// 其他：失败
				youyuResponse.setDesc("命中在贷规则");
				youyuResponse.setData(JSON.toJSONString(responseCheckUser));
				logger.info(sessionId + "结束YouyuServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第三步：是否有被拒记录(已经是存量用户)
			boolean isRejectRecord = drainageService.isRejectRecord(id_card, user_name);// 方法要改
			if (isRejectRecord) {
				responseCheckUser.setCode(ResponseCheckUser.CODE_UNAPPLIED);
				responseCheckUser.setDesc("不可申请，有被拒记录");
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);// 业务状态码 1:成功
				youyuResponse.setDesc("命中在贷规则");
				youyuResponse.setData(JSON.toJSONString(responseCheckUser));
				logger.info(sessionId + "结束YouyuServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}

			// 第四步：是否是老用户
			boolean isOldUser = drainageService.isOldUser2(id_card, user_name);
			if (isOldUser == true) {
				responseCheckUser.setCode(ResponseCheckUser.CODE_OLD_USER);
				responseCheckUser.setDesc("可申请，老用户");
				youyuResponse.setCode(YouyuResponse.CODE_SUCCESS);// 1:成功：其他失败

				youyuResponse.setDesc("success");
				youyuResponse.setData(JSON.toJSONString(responseCheckUser));

			} else {
				responseCheckUser.setCode(ResponseCheckUser.CODE_NEW_USER);
				responseCheckUser.setDesc("可申请，新用户");
				youyuResponse.setCode(YouyuResponse.CODE_SUCCESS);

				youyuResponse.setDesc("success");
				youyuResponse.setData(JSON.toJSONString(responseCheckUser));
			}

		} catch (Exception e) {
			logger.error("执行YouyuServiceImpl.userCheck()方法异常", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("接口调用异常");
		}

		return youyuResponse;

	}

	/**
	 * 1.1有鱼-订单推送
	 */
	@Override
	public YouyuResponse savePushLoanBaseInfo(long sessionId, YouyuRequestPush youyuRequestPush) {
		String meth = "youyuServiceImpl.savePushLoanBaseInfo method：";
		int channelId = Integer.parseInt(YouyuConstant.YOUYU_CHANNEL); // 渠道IDRAQHEFD57251H7C
		logger.info(sessionId + ">>>>>>" + meth + "订单开始推送");
		YouyuResponse youyuResponse = new YouyuResponse();
		try {

			// 判断参数是都为空
			Applydetail applydetail = youyuRequestPush.getApplydetail();// 产品信息
			// Cardinfo cardinfo = youyuRequestPush.getCardinfo();// 绑定银行卡信息
			Mobileinfo mobileinfo = youyuRequestPush.getMobileinfo();// 手机信息
			Orderinfo orderinfo = youyuRequestPush.getOrderinfo();// 订单信息
			Idinfo idinfo = youyuRequestPush.getIdinfo();// 身份证信息
			Userdata userdata = mobileinfo.getUserdata();
			// 保存订单信息
			// 第一步：判断有鱼订单是否已存在
			String order_no = orderinfo.getOrder_no();
			if (StringUtils.isEmpty(order_no)) {

				youyuResponse.setCode(YouyuResponse.CODE_FAIL);// 1:成功其他：失败

				youyuResponse.setDesc("第三方订单编号为空！");
				logger.info(sessionId + "：" + meth + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(order_no);
			if (bwOrderRong != null) {

				youyuResponse.setCode(YouyuResponse.CODE_FAIL);// 1:成功 其他：失败

				youyuResponse.setDesc("订单已存在，请勿重复推送");
				logger.info(sessionId + "：结束" + meth + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}

			// 第二步：新增或修改借款人
			String user_name = orderinfo.getUser_name();
			String user_mobile = orderinfo.getUser_mobile();
			String user_id = orderinfo.getUser_id();
			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, user_name, user_id, user_mobile,
					channelId);

			// 第三步：查询是否有进行中的订单
			long count = bwOrderService.findProOrder(String.valueOf(borrower.getId()));
			if (count > 0) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);// 1:成功其他：失败

				youyuResponse.setDesc("存在进行中的订单，请勿重复推送");
				logger.info(sessionId + "：结束" + meth + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			// 第四步：判断是否有草稿状态的订单（并做保存或修改）
			BwOrder bwOrder = saveModifyOrder(channelId, orderinfo, borrower);
			// 第五步：判断是否有有鱼订单（修改或保存）
			saveModifyRong(channelId, order_no, bwOrder);

			// 第六步：工作信息
			jobs(sessionId, channelId, applydetail, borrower, bwOrder);

			// 第七步：通讯录
			phoneList(mobileinfo, borrower, userdata);

			// 第十步：通话记录
			tel(sessionId, channelId, mobileinfo, borrower, bwOrder);

			// 第十一步：身份证图片idinfo
			addOrUpdateCard(sessionId, channelId, idinfo, borrower, bwOrder);
			logger.debug(sessionId + "：成功执行service层（第十一步：身份证图片）");

			// 第十四步：银行卡
			// saveAndUpdateBankcard(cardinfo, borrower, bwOrder);
			// 修改订单状态
			bwOrder.setStatusId(2L);
			bwOrder.setUpdateTime(Calendar.getInstance().getTime());
			bwOrder.setSubmitTime(Calendar.getInstance().getTime());
			bwOrderService.updateBwOrder(bwOrder);
			logger.debug(sessionId + "：成功执行service层（修改工单状态为2）");
			// 第十六步：放入redis 第三方通知
			saveRedis(channelId, order_no, borrower, bwOrder);

			// 第十七步 更改订单进行时间
			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
			bwOrderProcessRecord.setSubmitTime(new Date());
			bwOrderProcessRecord.setOrderId(bwOrder.getId());
			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
			logger.warn(sessionId + "：成功执行service层（更改订单进行时间）");
			// 第十八步：成功返回
			youyuResponse.setCode(YouyuResponse.CODE_SUCCESS);
			youyuResponse.setDesc("success");

			logger.warn(sessionId + "：成功执行service层（成功返回）");

		} catch (Exception e) {
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);// 1表示成功//其他表示失败
			youyuResponse.setDesc("fail");
		}
		logger.info(sessionId + "：结束YuyuServiceImpl.savePushLoanBaseInfo method：" + JSON.toJSONString(youyuResponse));
		return youyuResponse;
	}

	/**
	 * 
	 * 
	 * @Title: saveModifyOrder @Description: 订单保存或修改 @param channelId @param orderinfo @param borrower @return @throws
	 *         ParseException @return BwOrder @throws
	 */
	private BwOrder saveModifyOrder(int channelId, Orderinfo orderinfo, BwBorrower borrower) {
		BwOrder bwOrder = new BwOrder();
		bwOrder.setBorrowerId(borrower.getId());
		bwOrder.setStatusId(1L);
		bwOrder.setProductType(1);
		List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
		if (boList != null && boList.size() > 0) {
			bwOrder = boList.get(boList.size() - 1);
			bwOrder.setExpectMoney(Double.valueOf(orderinfo.getApplication_amount()) / 100D);
			bwOrder.setExpectNumber(1);
			bwOrder.setProductId(3);
			bwOrder.setChannel(channelId);
			bwOrder.setUpdateTime(Calendar.getInstance().getTime());
			bwOrder.setProductType(1);
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String order_time = orderinfo.getOrder_time();
				long parseLong = Long.parseLong(order_time);
				String plan = sdf.format(parseLong);
				Date parse = sdf.parse(plan);
				bwOrder.setSubmitTime(parse);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bwOrderService.updateBwOrder(bwOrder);
		} else {
			bwOrder.setOrderNo(OrderUtil.generateOrderNo());
			bwOrder.setBorrowerId(borrower.getId());
			bwOrder.setStatusId(1L);
			bwOrder.setCreateTime(Calendar.getInstance().getTime());
			bwOrder.setUpdateTime(Calendar.getInstance().getTime());
			bwOrder.setChannel(channelId);
			bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
			bwOrder.setApplyPayStatus(0);
			bwOrder.setProductId(5);
			bwOrder.setProductType(1);
			bwOrder.setExpectMoney(Double.valueOf(orderinfo.getApplication_amount()) / 100D);
			bwOrder.setExpectNumber(1);
			bwOrder.setSubmitTime(new Date());
			bwOrderService.addBwOrder(bwOrder);
		}
		return bwOrder;
	}

	/**
	 * 
	 * @Title: saveModifyRong @Description: 判断是否有有鱼订单（修改或保存） @param channelId @param order_no @param bwOrder @return
	 *         void @throws
	 */
	private void saveModifyRong(int channelId, String order_no, BwOrder bwOrder) {
		BwOrderRong bwOrderRong = bwOrderRongService.findBwOrderRongByOrderId(bwOrder.getId());
		if (bwOrderRong == null) {
			bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(bwOrder.getId());
			bwOrderRong.setThirdOrderNo(order_no);
			bwOrderRong.setChannelId(Long.valueOf(channelId));
			bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
			bwOrderRongService.save(bwOrderRong);
		} else {
			bwOrderRong.setChannelId(Long.valueOf(channelId));
			bwOrderRong.setThirdOrderNo(order_no);
			bwOrderRongService.update(bwOrderRong);
		}
	}

	/**
	 * 
	 * @Title: jobs @Description: 工作信息 @param sessionId @param channelId @param applydetail @param borrower @param
	 *         bwOrder @return void @throws
	 */
	private void jobs(long sessionId, int channelId, Applydetail applydetail, BwBorrower borrower, BwOrder bwOrder) {
		BwWorkInfo bwWorkInfo = bwWorkInfoService.findBwWorkInfoByOrderId(bwOrder.getId());
		if (bwWorkInfo == null) {
			bwWorkInfo = new BwWorkInfo();
			bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
			bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
			bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
			bwWorkInfo.setWorkYears(String.valueOf(applydetail.getWork_period()));
			bwWorkInfo.setIncome(applydetail.getUser_income_by_card());
			bwWorkInfo.setOrderId(bwOrder.getId());
			bwWorkInfoService.save(bwWorkInfo, borrower.getId());
		} else {
			bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
			bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
			bwWorkInfo.setWorkYears(String.valueOf(applydetail.getWork_period()));
			bwWorkInfo.setIncome(applydetail.getUser_income_by_card());
			bwWorkInfoService.update(bwWorkInfo);
		}
		thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 2, channelId); // 插入个人认证记录
	}

	/**
	 * 
	 * @Title: phoneList @Description: 通讯录 @param mobileinfo @param borrower @param userdata @throws
	 *         ParseException @return
	 */
	private void phoneList(Mobileinfo mobileinfo, BwBorrower borrower, Userdata userdata) {
		BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrower.getId());
		if (bwOperateBasic == null) {
			bwOperateBasic = new BwOperateBasic();
			bwOperateBasic.setBorrowerId(borrower.getId());
			bwOperateBasic.setUserSource(userdata.getUser_source());
			bwOperateBasic.setIdCard(CommUtils.isNull(userdata.getUser_id()) ? "" : userdata.getUser_id());
			bwOperateBasic.setAddr(
					CommUtils.isNull(mobileinfo.getUserdata().getAddr()) ? "" : mobileinfo.getUserdata().getAddr());
			bwOperateBasic.setRealName(CommUtils.isNull(userdata.getUser_name()) ? "" : userdata.getUser_name());
			bwOperateBasic
					.setPhoneRemain(CommUtils.isNull(userdata.getPhone_remain()) ? "" : userdata.getPhone_remain());
			bwOperateBasic.setPhone(CommUtils.isNull(userdata.getUser_mobile()) ? "" : userdata.getUser_mobile());
			if (CommUtils.isNull(userdata.getRegist_date()) == false) {
				String regist_date = userdata.getRegist_date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date;
				try {
					date = dateFormat.parse(regist_date);
					bwOperateBasic.setRegTime(date);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			bwOperateBasic.setCreateTime(new Date());
			bwOperateBasic.setUpdateTime(new Date());
			bwOperateBasicService.save(bwOperateBasic);
		} else {
			bwOperateBasic.setBorrowerId(borrower.getId());
			bwOperateBasic.setUserSource(userdata.getUser_source());
			bwOperateBasic.setIdCard(CommUtils.isNull(userdata.getUser_id()) ? "" : userdata.getUser_id());
			bwOperateBasic.setAddr(
					CommUtils.isNull(mobileinfo.getUserdata().getAddr()) ? "" : mobileinfo.getUserdata().getAddr());
			bwOperateBasic.setRealName(CommUtils.isNull(userdata.getUser_name()) ? "" : userdata.getUser_name());
			bwOperateBasic
					.setPhoneRemain(CommUtils.isNull(userdata.getPhone_remain()) ? "" : userdata.getPhone_remain());
			bwOperateBasic.setPhone(CommUtils.isNull(userdata.getUser_mobile()) ? "" : userdata.getUser_mobile());
			if (CommUtils.isNull(userdata.getRegist_date()) == false) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				try {
					bwOperateBasic.setRegTime(sdf.parse(userdata.getRegist_date()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			bwOperateBasic.setUpdateTime(new Date());
			bwOperateBasicService.update(bwOperateBasic);
		}
	}

	/**
	 * 
	 * @Title: tel @Description: 通话记录 @param sessionId @param channelId @param mobileinfo @param borrower @param
	 *         bwOrder @throws Exception @return void @throws
	 */
	private void tel(long sessionId, int channelId, Mobileinfo mobileinfo, BwBorrower borrower, BwOrder bwOrder)
			throws Exception {
		ArrayList<Teldata> tel = mobileinfo.getTel();
		Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
		if (tel != null) {
			for (Teldata teldata : tel) {
				if (teldata != null) {
					String call_addr = teldata.getCall_addr();
					String call_phone = teldata.getCall_phone();
					String str = teldata.getCall_time();
					String call_type = teldata.getCall_type();
					String talk_time = teldata.getTalk_time();
					try {
						Date d = new SimpleDateFormat("yyy/MM/dd HH:mm:ss").parse(str);
						SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
						String call_time = sdf.format(d);
						// Date jsonCallData = sdf.parse(call_time);
						if (callDate == null || d.after(callDate)) { // 通话记录采取最新追加的方式
							BwOperateVoice bwOperateVoice = new BwOperateVoice();
							bwOperateVoice.setUpdateTime(new Date());
							bwOperateVoice.setBorrower_id(borrower.getId());
							bwOperateVoice.setCall_time(call_time);
							bwOperateVoice.setTrade_addr(call_addr);
							bwOperateVoice.setTrade_time(talk_time);
							bwOperateVoice.setReceive_phone(call_phone);
							if (Integer.valueOf(call_type) == 0) {
								bwOperateVoice.setCall_type(1);
							}
							if (Integer.valueOf(call_type) == 1) {
								bwOperateVoice.setCall_type(2);
							}
							bwOperateVoiceService.save(bwOperateVoice);
						}
					} catch (Exception e) {
						logger.error(sessionId + "插入单条通话记录异常，忽略该条记录");
					}
				}
			}

		}

		thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 1, channelId);// 插入运营商认证记录
	}

	/**
	 * 
	 * @Title: saveRedis @Description: 存放rendis @param channelId @param order_no @param borrower @param bwOrder @return
	 *         void @throws
	 */
	private void saveRedis(int channelId, String order_no, BwBorrower borrower, BwOrder bwOrder) {
		logger.info("初审===" + bwOrder.getId());
		HashMap<String, String> hm = new HashMap<>();
		hm.put("channelId", channelId + "");
		hm.put("orderId", String.valueOf(bwOrder.getId()));
		hm.put("orderStatus", "2");
		hm.put("result", "审核");
		String hmData = JSON.toJSONString(hm);
		RedisUtils.rpush("tripartite:orderStatusNotify:" + channelId, hmData);

		SystemAuditDto systemAuditDto = new SystemAuditDto();
		systemAuditDto.setIncludeAddressBook(0);
		systemAuditDto.setOrderId(bwOrder.getId());
		systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
		systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
		systemAuditDto.setName(borrower.getName());
		systemAuditDto.setPhone(borrower.getPhone());
		systemAuditDto.setIdCard(borrower.getIdCard());
		systemAuditDto.setChannel(channelId);
		systemAuditDto.setThirdOrderId(order_no);
		RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
				JsonUtils.toJson(systemAuditDto));
	}

	/**
	 * 
	 * <<<<<<< .mine
	 * 
	 * @Title: saveAndUpdateBankcard @Description: 保存或者更新银行卡信息系 @param cardinfo @param borrower @param bwOrder @return
	 *         void @throws
	 */
	/*
	 * private void saveAndUpdateBankcard(Cardinfo cardinfo, BwBorrower borrower, BwOrder bwOrder) { if (cardinfo !=
	 * null) { BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId()); if
	 * (bwBankCard == null) { bwBankCard = new BwBankCard(); String bankName =
	 * BankUtil.getname(cardinfo.getBank_card()); String bankCode = ThirdUtil.convertToBankCode(bankName);
	 * bwBankCard.setBorrowerId(borrower.getId()); bwBankCard.setCardNo(cardinfo.getBank_card());
	 * bwBankCard.setProvinceCode(cardinfo.getBank_province()); bwBankCard.setCityCode(cardinfo.getBank_city());
	 * bwBankCard.setBankCode(bankCode); bwBankCard.setBankName(cardinfo.getOpen_bank());
	 * bwBankCard.setPhone(cardinfo.getUser_mobile()); bwBankCard.setSignStatus(0); bwBankCard.setCreateTime(new
	 * Date()); bwBankCard.setUpdateTime(new Date()); bwBankCardService.saveBwBankCard(bwBankCard,
	 * bwOrder.getBorrowerId()); } else if (bwBankCard != null && 0 == bwBankCard.getSignStatus()) { String bankName =
	 * BankUtil.getname(cardinfo.getBank_card()); String bankCode = ThirdUtil.convertToBankCode(bankName); boolean flag
	 * = (bwBankCard.getCreateTime() == null || "".equals(bwBankCard.getCreateTime()));
	 * bwBankCard.setBorrowerId(borrower.getId()); bwBankCard.setCardNo(cardinfo.getBank_card());
	 * bwBankCard.setProvinceCode(cardinfo.getBank_province()); bwBankCard.setCityCode(cardinfo.getBank_city());
	 * bwBankCard.setBankCode(bankCode); bwBankCard.setBankName(cardinfo.getOpen_bank());
	 * bwBankCard.setPhone(cardinfo.getUser_mobile()); bwBankCard.setSignStatus(0); bwBankCard.setCreateTime(flag ? (new
	 * Date()) : bwBankCard.getCreateTime()); bwBankCard.setUpdateTime(new Date());
	 * bwBankCardService.updateBwBankCard(bwBankCard); } } }
	 */

	/**
	 * 
	 * ======= >>>>>>> .r14370
	 * 
	 * @Title: addOrUpdateCard @Description: 保存身份张且插入记录 @param sessionId @param channelId @param idinfo @param
	 *         borrower @param bwOrder @throws Exception @return void @throws
	 */
	private void addOrUpdateCard(long sessionId, int channelId, Idinfo idinfo, BwBorrower borrower, BwOrder bwOrder)
			throws Exception {
		String frontImage = UploadToCssUtils.urlUpload(idinfo.getId_positive_img(), bwOrder.getId() + "_01"); // 身份证正面照
		logger.info("===========1" + idinfo.getId_positive_img());
		String backImage = UploadToCssUtils.urlUpload(idinfo.getId_negative_img(), bwOrder.getId() + "_02"); // 身份证反面照
		logger.info("===========2" + idinfo.getId_negative_img());
		String handerImage = UploadToCssUtils.urlUpload(idinfo.getId_live_img(), bwOrder.getId() + "_03"); // 活体
		logger.info("===========3" + idinfo.getId_live_img());
		thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, bwOrder.getId(), borrower.getId(), 0); // 保存身份证正面照
		thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, bwOrder.getId(), borrower.getId(), 0); // 保存身份证反面照
		thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, bwOrder.getId(), borrower.getId(), 0); // 保存手持照
		// 保存身份证信息
		BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
		bwIdentityCard.setBorrowerId(borrower.getId());
		bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);

		String idCardNum = idinfo.getUser_id();
		String year = idCardNum.substring(6, 10);
		String month = idCardNum.substring(10, 12);
		String day = idCardNum.substring(12, 14);
		if ("0".equals(month.substring(0, 1))) {
			month = month.replace("0", "-");
		} else {
			month = "-" + month;
		}
		if ("0".equals(day.substring(0, 1))) {
			day = day.replace("0", "-");
		} else {
			day = "-" + day;
		}
		String birthday = year + month + day;

		int sexNum = ThirdUtil.getSexByIdCard(idCardNum);
		String gender;
		if (sexNum == 0) {
			gender = "女";
		} else {
			gender = "男";
		}
		if (bwIdentityCard == null) {
			bwIdentityCard = new BwIdentityCard2();
			// bwIdentityCard.setAddress(identifyInfo.getAddress());
			bwIdentityCard.setBirthday(birthday);
			bwIdentityCard.setBorrowerId(borrower.getId());
			bwIdentityCard.setCreateTime(new Date());
			bwIdentityCard.setGender(gender);
			bwIdentityCard.setIdCardNumber(idinfo.getUser_id());
			bwIdentityCard.setIssuedBy(idinfo.getAuthority());
			bwIdentityCard.setName(idinfo.getUser_name());
			// bwIdentityCard.setRace(identifyInfo.getNation());
			bwIdentityCard.setUpdateTime(new Date());
			bwIdentityCard.setValidDate(idinfo.getValid_period());
			bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
		} else {
			// bwIdentityCard.setAddress(identifyInfo.getAddress());
			bwIdentityCard.setBirthday(birthday);
			bwIdentityCard.setGender(gender);
			bwIdentityCard.setIdCardNumber(idinfo.getUser_id());
			bwIdentityCard.setIssuedBy(idinfo.getAuthority());
			bwIdentityCard.setName(idinfo.getUser_name());
			// bwIdentityCard.setRace(identifyInfo.getNation());
			bwIdentityCard.setUpdateTime(new Date());
			bwIdentityCard.setValidDate(idinfo.getValid_period());
			bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
		}
		thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 3, channelId);// 插入身份认证记录
	}

	/**
	 * 查询订单的状态1.7
	 *
	 * 
	 * @see com.waterelephant.drainage.service.YouyuService#queryOrderStatus(long,
	 *      com.waterelephant.drainage.entity.youyu.QueryOrderStatus)
	 */
	@Override
	public YouyuResponse queryOrderStatus(long sessionId, QueryOrderStatus queryOrderStatus) {

		logger.info(sessionId + "：开始youyuServiceImpl.queryOrderStatus method：" + JSON.toJSONString(queryOrderStatus));
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			String order_no = queryOrderStatus.getOrder_no();
			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(order_no);
			if (CommUtils.isNull(bwOrderRong)) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("第三方订单编号为空！");
				logger.info(
						sessionId + "-结束youyuServiceImpl.queryOrderStatus method-" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			Long orderId = bwOrderRong.getOrderId();
			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("订单为空！");
				logger.info(
						sessionId + "：结束YouyuServiceImpl.queryOrderStatus method：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			Long statusId = bwOrder.getStatusId();
			String orderStatus = YouyuConstant.ORDER_STATUS_MAP_L.get(String.valueOf(statusId));
			logger.info("映射为有鱼订单状态为：" + orderStatus);
			TreeMap<String, Object> tm = new TreeMap<>();
			tm.put("order_no", order_no);
			tm.put("order_status", orderStatus);
			tm.put("order_detail", YouyuConstant.ORDER_STATUS_MAP.get(orderStatus));
			// 还款计划
			if (9 == statusId || 13 == statusId) {// 9:还款中 13：:逾期
				BwRepaymentPlan bwRepaymentPlan = iBwRepaymentPlanService.getBwRepaymentPlanByOrderId(bwOrder.getId());
				Integer productId = bwOrder.getProductId();
				BwProductDictionary bwProductDictionaryById = bwProductDictionaryService
						.findBwProductDictionaryById(productId);
				Double borrowAmount = bwOrder.getBorrowAmount();// 审批金额
				Double servicefee = bwProductDictionaryById.getpFastReviewCost()
						+ bwProductDictionaryById.getpPlatformUseCost() + bwProductDictionaryById.getpNumberManageCost()
						+ bwProductDictionaryById.getpCollectionPassagewayCost()
						+ bwProductDictionaryById.getpCapitalUseCost();
				Double service_fee = servicefee * borrowAmount;// 预扣除费用
				Double extral = bwProductDictionaryById.getZjwCost();
				Double extral_fee = extral * borrowAmount;// 每期额外费用（湛江）
				Double rateOverdue = bwProductDictionaryById.getRateOverdue();// 逾期费率rateOverdue

				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
				bwOverdueRecord.setOrderId(bwOrder.getId());
				bwOverdueRecord = bwOverdueRecordService.queryBwOverdueRecord(bwOverdueRecord);
				Integer overdueDay = 0;
				if (bwOverdueRecord != null) {
					overdueDay = bwOverdueRecord.getOverdueDay();// 逾期天数
				}
				Double rateOverdue_fee = borrowAmount * rateOverdue * overdueDay;// 逾期费用

				Map<String, Object> desc = new HashMap<>();// 卡号信息
				BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(bwOrder.getBorrowerId());
				desc.put("card_bank", "");// 开户行名称
				desc.put("card_num", "");// 卡号
				if (bwBankCard != null) {
					String bankName = bwBankCard.getBankName();
					int indexOf = bankName.indexOf("·");
					if (indexOf != -1) {
						bankName = bankName.substring(0, indexOf);
					}
					desc.put("card_bank", bankName);// 开户行名称
					desc.put("card_num", bwBankCard.getCardNo());// 卡号
				}
				tm.put("desc", desc);
				TreeMap<String, Object> tm1 = new TreeMap<>();
				tm1.put("amount", bwRepaymentPlan.getRealityRepayMoney() + rateOverdue_fee);// 还款金额
				tm1.put("due_time", bwRepaymentPlan.getRepayTime().getTime());// 还款时间
				tm1.put("pay_type", 3);// 还款方式 1：主动还款 2：银行代扣 3：两者同时支持
				tm1.put("period_no", bwOrder.getBorrowNumber());// 期数bwOrder.getBorrowNumber()
				tm1.put("remark", "本金:" + borrowAmount + ",服务费:" + service_fee + ",律师费:" + extral_fee + ",逾期费:"
						+ rateOverdue_fee);// 还款费用说明
				List<TreeMap<String, Object>> repayment_plan = new ArrayList<>();
				repayment_plan.add(tm1);
				tm.put("repayment_plan", repayment_plan);
			}
			// 还款反馈repayment_feedback
			if (6 == statusId) {
				BwRepaymentPlan bwRepaymentPlan = iBwRepaymentPlanService.getBwRepaymentPlanByOrderId(bwOrder.getId());
				Long borrowerId = bwOrder.getBorrowerId();// 获取借款人ID
				BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
				Double borrowAmount = bwOrder.getBorrowAmount();// 审批金额
				Integer productId = bwOrder.getProductId();
				BwProductDictionary bwProductDictionaryById = bwProductDictionaryService
						.findBwProductDictionaryById(productId);
				Double rateOverdue = bwProductDictionaryById.getRateOverdue();// 逾期费率rateOverdue
				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
				bwOverdueRecord.setOrderId(bwOrder.getId());
				bwOverdueRecord = bwOverdueRecordService.queryBwOverdueRecord(bwOverdueRecord);
				Integer overdueDay = 0;
				if (bwOverdueRecord != null) {
					overdueDay = bwOverdueRecord.getOverdueDay();// 逾期天数
				}
				Double rateOverdue_fee = borrowAmount * rateOverdue * overdueDay;// 逾期费用
				// 封装数据
				Map<String, String> map = new HashMap<>();
				String bankName = bwBankCard.getBankName();
				int indexOf = bankName.indexOf("·");
				if (indexOf != -1) {
					bankName = bankName.substring(0, indexOf);
				}
				map.put("card_bank", bankName);// 开户行
				map.put("card_num", bwBankCard.getCardNo());// 卡号
				String repay_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bwOrder.getUpdateTime());
				TreeMap<String, Object> repayment_feedback = new TreeMap<>();
				repayment_feedback.put("order_no", bwOrderRong.getThirdOrderNo());// 第三方机构的订单号
				repayment_feedback.put("amount", bwRepaymentPlan.getRealityRepayMoney() + rateOverdue_fee);// 还款金额
				repayment_feedback.put("bill_status", "2");// 1:未到期 2:还款成功
															// 3:还款失败
				repayment_feedback.put("repay_time", repay_time);// 实际还款日期
				repayment_feedback.put("repayment_desc", map);// 还款说明（银行卡）
				repayment_feedback.put("period_no", bwRepaymentPlan.getNumber());// 还款期数
				repayment_feedback.put("remark", "本金:" + bwRepaymentPlan.getRepayCorpusMoney() + ",律师费:"
						+ bwRepaymentPlan.getZjw() + ",逾期费:" + rateOverdue_fee);//
				tm.put("repayment_feedback", repayment_feedback);
			}

			// 订单审批反馈approvefeedback
			if (statusId == 4 || statusId == 7 || statusId == 8 || statusId == 9) {
				TreeMap<String, Object> approvefeedback = new TreeMap<>();
				if (statusId == 9) {
					Integer productId = bwOrder.getProductId();
					BwProductDictionary bwProductDictionaryById = bwProductDictionaryService
							.findBwProductDictionaryById(productId);
					Double borrowAmount = bwOrder.getBorrowAmount();// 审批金额
					Double extral = bwProductDictionaryById.getZjwCost();
					Double extral_fee = extral * borrowAmount;// 每期额外费用
					// 预扣除费用 :快速信审费、平台使用费 、账户管理费、代收通道费、信息服务费 五个费用相加
					Double servicefee = bwProductDictionaryById.getpFastReviewCost()
							+ bwProductDictionaryById.getpPlatformUseCost()
							+ bwProductDictionaryById.getpNumberManageCost()
							+ bwProductDictionaryById.getpCollectionPassagewayCost()
							+ bwProductDictionaryById.getpCapitalUseCost();
					Double service_fee = servicefee * borrowAmount;// 预扣除费用

					approvefeedback.put("order_no", bwOrderRong.getThirdOrderNo());// 三方订单编号√
					approvefeedback.put("conclusion", 10);// 审批结论 10-审批通过√
					approvefeedback.put("approval_amount", borrowAmount);// 审批金额√
					approvefeedback.put("approval_term", bwOrder.getBorrowNumber());// 审批期数（bwOrder.getBorrowNumber()）
					approvefeedback.put("term_unit", 1);// 期限单位 1:天 2:月
					approvefeedback.put("service_fee", service_fee);// 预扣除费用
					approvefeedback.put("extral_fee", extral_fee + service_fee);// 每期额外费用(单期)
					approvefeedback.put("real_amount", borrowAmount - service_fee);// 实际到账费用(借款金额-预扣除费用)
					approvefeedback.put("fee_desc", "贷款金额" + borrowAmount + "元, 已扣除手续费" + service_fee + "元, 实际到账"
							+ (borrowAmount - service_fee) + "元");// 费用说明
					approvefeedback.put("remark", null);// 备注说明
					approvefeedback.put("bind_status", "1");// 1：成功2:成功
					tm.put("approvefeedback", approvefeedback);

				}
				if (statusId == 4) {// 待签约
					Integer productId = bwOrder.getProductId();
					BwProductDictionary bwProductDictionaryById = bwProductDictionaryService
							.findBwProductDictionaryById(productId);

					// signfeedback
					TreeMap<String, String> signfeedback = new TreeMap<>();
					String sign_url = YouyuConstant.YOUYU_CREDIT_SYNTONYURL + "?orderNo=" + bwOrder.getOrderNo()
							+ "&torder_no=" + bwOrderRong.getThirdOrderNo();
					signfeedback.put("order_no", bwOrderRong.getThirdOrderNo());
					signfeedback.put("lower_approve_money", bwOrder.getBorrowAmount() + "");// 实时审批金额下限borrowAmount
					signfeedback.put("upper_approve_money", bwOrder.getBorrowAmount() + "");// 实时审批金额上限
					signfeedback.put("money_unit", "1");// 审批金额的单位
					signfeedback.put("lower_term", bwProductDictionaryById.getpTerm());// 实时审批期限下限BwProductDictionary
					signfeedback.put("upper_term", bwProductDictionaryById.getpTerm());// 实时审批期限上限
					signfeedback.put("term_unit", "1");// 实时审批期限单位
					signfeedback.put("low_rate", "0");// 实时审批利率下限
					signfeedback.put("upper_rate", "0");// 实时审批利率上限
					signfeedback.put("rate_unit", "1");// 实时审批利率单位
					signfeedback.put("sign_url", sign_url);// 签约页面(自定义接口)
					tm.put("signfeedback", signfeedback);
				}
				if (statusId == 7 || statusId == 8) {// 拒绝
					approvefeedback.put("order_no", bwOrderRong.getThirdOrderNo());
					approvefeedback.put("order_status", "70");
					approvefeedback.put("order_detail", "审批拒绝");
					tm.put("approvefeedback", approvefeedback);
				}

			}
			youyuResponse.setCode(YouyuResponse.CODE_SUCCESS);
			youyuResponse.setDesc("获取成功！");
			youyuResponse.setData(tm);
		} catch (Exception e) {
			logger.error(sessionId + "-结束YouyuServiceImpl.queryOrderStatus method-" + e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("请求失败");
		}
		logger.info(sessionId + "-结束YouyuServiceImpl.queryOrderStatus method-" + JSON.toJSONString(youyuResponse));
		return youyuResponse;

	}

	@Override
	public YouyuResponse updateSignContract(long sessionId, SignOrder signOrder) {
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			String order_no = signOrder.getOrder_no();
			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(order_no);
			if (CommUtils.isNull(bwOrder)) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("订单为空！");
				logger.info("-结束updateSignContract method-" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}

			if (Integer.parseInt(String.valueOf(bwOrder.getStatusId())) == 4) {
				logger.info("-updateSignContract method-" + "处理签约业务");
				ThirdResponse thirdResponse = thirdCommonService.updateSignContract(order_no,
						Integer.parseInt(YouyuConstant.YOUYU_CHANNEL));
				if (thirdResponse.getCode() == 200) {
					youyuResponse.setCode(YouyuResponse.CODE_SUCCESS);
					youyuResponse.setDesc(thirdResponse.getMsg());
				} else {
					youyuResponse.setCode(YouyuResponse.CODE_FAIL);
					youyuResponse.setDesc(thirdResponse.getMsg());
				}
			} else {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("未达到待签约状态");
			}
		} catch (Exception e) {
			logger.info(sessionId + "：签约异常：" + JSON.toJSONString(youyuResponse));
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("系统异常");
			return youyuResponse;
		}
		return youyuResponse;
	}

	/**
	 * 有鱼-合同接口1.13
	 */
	@Override
	public YouyuResponse agreement(long sessionId, SignOrder signOrder) {
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			String order_no = signOrder.getOrder_no();
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(order_no);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("第三方订单为空");
				return youyuResponse;
			}
			// 获取合同中地址
			Long orderId = bwOrderRong.getOrderId();
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setOrderId(orderId);
			bwAdjunct.setAdjunctType(13);
			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
			if (CommUtils.isNull(bwAdjunct)) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("附件信息为空！");
				logger.info(sessionId + "-结束YouYuServiceImpl.agreement method-" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			}
			String agreement_url = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
			TreeMap<String, String> treeMap = new TreeMap<>();
			treeMap.put("order_no", order_no);
			treeMap.put("agreement_url", agreement_url);
			youyuResponse.setCode(YouyuResponse.CODE_SUCCESS);
			youyuResponse.setDesc("success");
			youyuResponse.setData(treeMap);
		} catch (Exception e) {
			logger.error(sessionId + "：执行agreement method 异常：", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("请求失败");
		}
		return youyuResponse;
	}

	/**
	 * 有鱼-费用试算1.18
	 */
	@Override
	public YouyuResponse count(long sessionId, String loan_money, String loan_term, String money_unit,
			String term_unit) {
		logger.info("count>>" + sessionId + ">>>" + "开始费用试算");
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			TreeMap<String, Object> treeMap = new TreeMap<>();
			BwProductDictionary bwProductDictionaryById = null;
			if ("30".equals(loan_term)) {
				bwProductDictionaryById = bwProductDictionaryService.findBwProductDictionaryById(3);
			} else if ("14".equals(loan_term)) {
				bwProductDictionaryById = bwProductDictionaryService.findBwProductDictionaryById(5);
			} else {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("没有该期限产品");
				return youyuResponse;
			}
			Double servicefee = bwProductDictionaryById.getpFastReviewCost()
					+ bwProductDictionaryById.getpPlatformUseCost() + bwProductDictionaryById.getpNumberManageCost()
					+ bwProductDictionaryById.getpCollectionPassagewayCost()
					+ bwProductDictionaryById.getpCapitalUseCost();
			Double zjwCost = bwProductDictionaryById.getZjwCost();// 湛江律师费

			double d = Double.parseDouble(loan_money);
			Double service_fee = servicefee * d;// 预扣除费用
			String fee = Double.toString(service_fee + zjwCost * d);
			Map<String, String> map = new HashMap<>();
			map.put("amount", service_fee + "");
			map.put("type", "服务费");
			Map<String, String> map1 = new HashMap<>();
			map1.put("amount", zjwCost * d + "");
			map1.put("type", "湛江委律师函手续费");
			List<Map<String, String>> fee_items = new ArrayList<>();
			fee_items.add(map);
			fee_items.add(map1);
			double rate_explain = 0.0001;
			double total_repay = zjwCost * d + d;// 总待还款
			double receive_money = d - service_fee;// 到账金额
			treeMap.put("total_repay", total_repay);
			treeMap.put("receive_money", receive_money);
			treeMap.put("fee", fee);
			treeMap.put("fee_items", fee_items);
			treeMap.put("rate_explain", rate_explain);
			youyuResponse.setCode(YouyuResponse.CODE_SUCCESS);
			youyuResponse.setDesc("success");
			youyuResponse.setData(treeMap);
			logger.info("count>>" + sessionId + ">>>" + youyuResponse);
		} catch (Exception e) {
			logger.error(sessionId + "：执行agreement method 异常：", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("请求失败");
			logger.info("count>>" + sessionId + ">>>" + youyuResponse);
		}
		return youyuResponse;
	}

	/**
	 * 有鱼-还款接口1.19
	 */
	@Override
	public YouyuResponse repayment(long sessionId, String order_no) {
		logger.info("repayment>>" + sessionId + ">>>" + order_no);
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			// 判断第三方订单
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(order_no);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			if (bwOrderRong == null) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("不存在三方订单");
				logger.info("repayment>>" + sessionId + ">>>" + "不存在三方订单");
				return youyuResponse;
			}
			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
			if (bwOrder == null) {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("不存在该订单");
				logger.info("repayment>>" + sessionId + ">>>" + "不存在该订单");
				return youyuResponse;
			}
			Long statusId = bwOrder.getStatusId();
			if (statusId == 9 || statusId == 13) {
				Long orderid = bwOrder.getId();// 订单号

				String url = YouyuConstant.YOUYU_THIRDPAY_PAYMENT + "?orderId=" + orderid;
				// 调用还款接口
				logger.info("repayment>>" + sessionId + ">>>" + "请求还款");
				String json = HttpClientHelper.post(url, "utf-8", order_no);
				AppResponseResult appResponseResult = JSON.parseObject(json, AppResponseResult.class);
				youyuResponse.setCode(appResponseResult.getCode());
				youyuResponse.setDesc(appResponseResult.getMsg());
				youyuResponse.setData(appResponseResult.getResult());
				logger.info("repayment>>" + sessionId + ">>>" + youyuResponse);
			} else {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("未到应还款状态");
				return youyuResponse;
			}
			return youyuResponse;
		} catch (Exception e) {
			logger.error(sessionId + "：执行repayment method 异常：", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("请求异常");
			logger.info("repayment>>" + sessionId + ">>>" + youyuResponse);
			return youyuResponse;
		}
	}

	/**
	 * 绑卡跳转路径 orderNo：订单编号 torder_no：三方编号
	 */
	@Override
	public String montageURL(String orderNo, String torder_no) {
		String failReturnUrl = YouyuConstant.YOUYU_SKIPURL + "#fail";
		logger.info("montageURL>>" + orderNo + ">>>" + torder_no);
		try {
			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNo);
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(torder_no);
			if (bwOrder == null || bwOrderRong == null) {// 订单空
				logger.info("montageURL>>" + orderNo + ">>>" + torder_no + "绑卡失败");
				return "redirect:" + failReturnUrl;
			}

			logger.info("montageURL>>" + bwOrder.getId());
			Long borrowerId = bwOrder.getBorrowerId();
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
			if (bwBankCard == null) {// 银行卡空
				logger.info("montageURL>>" + orderNo + ">>>" + torder_no + "绑卡失败");
				return "redirect:" + failReturnUrl;
			}
			logger.info("montageURL>>" + bwBankCard.getId());
			String cardNo = bwBankCard.getCardNo();
			String bankName = bwBankCard.getBankName();
			int indexOf = bankName.indexOf("·");
			if (indexOf != -1) {
				bankName = bankName.substring(0, indexOf);
			}
			String card = cardNo.substring(cardNo.length() - 4, cardNo.length());// 取银行卡后四位
			if (bwBankCard.getSignStatus() == 2 || bwBankCard.getSignStatus() == 1) {// 签约成功
				String successReturnUrl = YouyuConstant.YOUYU_SKIPURL + "#success?order_no=" + torder_no + "&app_id="
						+ YouyuConstant.YOUYUAPP_ID + "&last4num=" + card;
				logger.info("montageURL>>" + successReturnUrl);
				return "redirect:" + successReturnUrl;
			} else {// 失败
				logger.info("montageURL>>" + failReturnUrl);
				return "redirect:" + failReturnUrl;
			}
		} catch (Exception e) {
			logger.info("montageURL>>" + "跳转异常", e);
			return "redirect:" + failReturnUrl;
		}
	}

	@Override
	public String syntonyUrl(String orderNo, String torder_no) {
		String failReturnUrl = YouyuConstant.YOUYU_SKIPURL + "#fail";
		try {

			// 存入跳转页面
			String directUrl = "/youyu/interface/montageURL.do?channelId=" + YouyuConstant.YOUYU_CHANNEL + "&orderNo="
					+ orderNo + "&torder_no=" + torder_no;
			RedisUtils.hset("third:bindCard:selfUrl:" + YouyuConstant.YOUYU_CHANNEL, "orderNO_" + orderNo, directUrl);
			// 绑卡页面
			String sign_url = YouyuConstant.YOUYU_CREDIT_CARD_URL + "?orderNO=" + orderNo + "&channelCode="
					+ YouyuConstant.YOUYU_CHANNEL_CODE + "&appid=" + YouyuConstant.YOUYUAPP_ID + "&order_no ="
					+ torder_no;
			return "redirect:" + sign_url;
		} catch (Exception e) {
			logger.error("有鱼绑卡跳转异常：", e);
			return "redirect:" + failReturnUrl;
		}

	}

	/**
	 * 放弃签约
	 * 
	 * @Title: finish
	 * @Description:
	 * @return: void
	 */
	@Override
	public YouyuResponse finish(long sessionId, BwOrder bwOrder) {
		YouyuResponse youyuResponse = new YouyuResponse();
		try {
			if (bwOrder.getStatusId() == 4) {
				bwOrder.setStatusId(6L);
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setSubmitTime(Calendar.getInstance().getTime());
				bwOrderService.updateBwOrder(bwOrder);
				youyuResponse.setCode(YouyuResponse.CODE_SUCCESS);
				youyuResponse.setDesc("success");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(youyuResponse));
				return youyuResponse;
			} else {
				youyuResponse.setCode(YouyuResponse.CODE_FAIL);
				youyuResponse.setDesc("未到待签约状态");
				return youyuResponse;
			}
		} catch (Exception e) {
			logger.error(sessionId + "执行service层放弃签约接口异常:", e);
			youyuResponse.setCode(YouyuResponse.CODE_FAIL);
			youyuResponse.setDesc("fail");
			return youyuResponse;
		}
	}
}
