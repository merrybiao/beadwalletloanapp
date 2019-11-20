package com.waterelephant.drainage.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.utils.JsonUtils;
import com.waterelephant.drainage.entity.common.DrainageResponse;
import com.waterelephant.drainage.entity.common.PushOrderRequest;
import com.waterelephant.drainage.entity.fqgj.DrainageResp;
import com.waterelephant.drainage.service.DrainageService;
import com.waterelephant.drainage.util.DrainageUtils;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwIdentityCard2;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.jiedianqian.entity.UserCheckResp;
import com.waterelephant.rong360.entity.Rong360Resp;
import com.waterelephant.rong360.util.ThreadLocalUtil;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwOperateBasicService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.impl.BwContactListService;
import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.utils.UploadToCssUtils;
import com.yeepay.g3.utils.common.CollectionUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/26 10:39
 */
@Service
public class DrainageServiceImpl implements DrainageService {

	private Logger logger = Logger.getLogger(DrainageServiceImpl.class);
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private ThirdCommonService thirdCommonService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private BwContactListService bwContactListService;
	@Autowired
	private BwOperateBasicService bwOperateBasicService;
	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;

	/**
	 * 老用户&黑名单过滤
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 */
	@Override
	public DrainageResp isOldUser(String idCard, String name) {
		DrainageResp resp = new DrainageResp();
		Map<String, String> data = new HashMap<String, String>();
		// 1判断是否存在借款人信息，如果不存在则是新用户,返回200+is_reloan=0，否则查询绑卡信息
		logger.info("开始查询借款人信息,idCard:" + idCard + ",name=" + name);
		BwBorrower borrower = bwBorrowerService.oldUserFilter(idCard.substring(0, idCard.length() - 5), name);
		logger.info("查询结果:" + JSONObject.toJSONString(borrower));

		if (CommUtils.isNull(borrower)) {
			resp.setCode("200");
			resp.setMsg("success");
			return resp;
		}

		// 2判断是否有签约成功，如果没有则是新用户,返回200+is_reloan=0，如果有则查询是否有黑名单，正在进行中的订单，被拒绝记录
		logger.info("开始查询银行卡信息");
		BwBankCard bankCard = new BwBankCard();
		bankCard.setBorrowerId(borrower.getId());
		bankCard.setSignStatus(2);
		bankCard = findBwBankCardByAttrProxy(bankCard);

		if (CommUtils.isNull(bankCard)) {
			logger.info("银行卡信息不存在");
			resp.setCode("200");
			resp.setMsg("新用户");
			return resp;
		}

		// 3判断是否是黑名单，如果存在则返回400，如果没有则查询是否有进行中的订单
		logger.info("开始验证系统平台黑名单");
		Example example = new Example(BwBlacklist.class);
		String idNo = borrower.getIdCard();
		example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card", idNo.toUpperCase());
		List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
		if (!CommUtils.isNull(desList)) {
			resp.setCode("400");
			resp.setMsg("有不良贷款记录");
			return resp;
		}

		// 4查询是否有进行中的订单，如果存在则返回400，否则查询是否有被拒记录
		logger.info("开始查询进行中的订单:borrowerId=" + borrower.getId());
		Long count = bwOrderService.findProOrder(String.valueOf(borrower.getId()));
		logger.info("结束查询进行中的订单:count:" + count);
		if (!CommUtils.isNull(count) && count.intValue() > 0) {
			resp.setCode("400");
			resp.setMsg("有进行中的贷款");
			resp.setReason("C001");
			return resp;
		}

		// 5查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
		logger.info("开始查询拒绝记录");
		BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
		logger.info("结束查询拒绝记录,rejectRecord=" + JSONObject.toJSONString(record));
		if (!CommUtils.isNull(record)) {
			// 永久拒绝
			if ("0".equals(String.valueOf(record.getRejectType()))) {
				logger.info("有永久拒绝记录");
				resp.setCode("400");
				resp.setMsg("该用户被永久拒绝");
				return resp;
			} else {
				// 6判断临时拒绝是否到期，如果没到期则返回400，否则返回200+is_reloan=1
				Date rejectDate = record.getCreateTime();
				long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime()) / (24 * 60 * 60 * 1000);
				if (day <= 7) {
					resp.setCode("400");
					resp.setMsg("7天内被机构审批拒绝过");
					return resp;
				}
			}
		}

		data.put("is_reloan", "0");
		data.put("approval_amount", "5000");
		data.put("approval_term", "1");
		data.put("term_unit", "2");
		data.put("bank_card", bankCard.getCardNo());
		data.put("open_bank", bankCard.getBankName());
		data.put("user_name", borrower.getName());
		data.put("user_mobile", borrower.getPhone());

		resp.setCode("200");
		resp.setMsg("复贷用户");
		return resp;
	}

	/**
	 * 公共方法 - 添加或保存用户信息（liuDaodao）
	 * 
	 * @param name
	 * @param idCard
	 * @param phone
	 * @param channel（渠道编号）
	 * @return
	 */
	@Override
	public BwBorrower addOrUpdateBwer(String name, String idCard, String phone, int channel) {
		BwBorrower borrower = new BwBorrower();
		borrower.setIdCard(idCard);
		borrower.setFlag(1);// 未删除的
		logger.info("开始查询借款人,idCard=" + idCard);
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		logger.info("查询借款人信息为:idCard=" + idCard);

		// 如果借款人信息已存在就修改，不存在就新增
		if (!CommUtils.isNull(borrower)) {
			logger.info("借款人信息已存在，开始修改借款人信息：" + borrower.getId());
			// 已存在(更新借款人)
			borrower.setPhone(phone);
			borrower.setAuthStep(1);
			borrower.setFlag(1);
			borrower.setState(1);
			borrower.setChannel(channel);
			borrower.setIdCard(idCard);
			borrower.setName(name);
			borrower.setAge(DrainageUtils.getAgeByIdCard(idCard));
			borrower.setSex(DrainageUtils.getSexByIdCard(idCard));
			borrower.setUpdateTime(Calendar.getInstance().getTime());
			bwBorrowerService.updateBwBorrower(borrower);
		} else {
			// 创建借款人
			logger.info("借款人信息不存在，开始创建借款人");
			String password = "123456a";
			borrower = new BwBorrower();
			borrower.setPhone(phone);
			borrower.setPassword(CommUtils.getMD5(password.getBytes()));
			borrower.setAuthStep(1);
			borrower.setFlag(1);
			borrower.setState(1);
			borrower.setChannel(channel);
			borrower.setIdCard(idCard);
			borrower.setName(name);
			borrower.setAge(DrainageUtils.getAgeByIdCard(idCard));
			borrower.setSex(DrainageUtils.getSexByIdCard(idCard));
			borrower.setCreateTime(Calendar.getInstance().getTime());
			borrower.setUpdateTime(Calendar.getInstance().getTime());
			bwBorrowerService.addBwBorrower(borrower);
			logger.info("生成的借款人id：" + borrower.getId());

			// 发送短信
			try {
				// String message = DrainageUtils.getMsg(password);
				// MsgReqData msg = new MsgReqData();
				// msg.setPhone(phone);
				// msg.setMsg(message);
				// msg.setType("0");
				// logger.info("开始发送密码短信,phone=" + phone);
				// Response<Object> response =
				// BeadWalletSendMsgService.sendMsg(msg);
				// logger.info("发送完成,发送结果：" +
				// JSONObject.toJSONString(response));
				// boolean bo =
				// sendMessageCommonService.commonSendMessage(phone, message);
				// if (bo) {
				// logger.info("短信发送成功！");
				// } else {
				// logger.info("短信发送失败！");
				// }
			} catch (Exception e) {
				logger.error("发送短信异常:", e);
			}
		}
		return borrower;
	}

	/**
	 * 公共方法 - 判断是否是老用户
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 * @author liuDaodao
	 * 
	 */
	@Override
	public boolean isOldUser2(String idCard, String name) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter(idCard.substring(0, idCard.length() - 5), name);
		if (borrower != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 公共方法 - 判断是否是老用户
	 * 
	 * @param phone
	 * @param id_number
	 * @return
	 * @author 张博
	 * 
	 */
	@Override
	public boolean oldUserFilter2(String name, String phone, String id_number) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter2(name, phone, id_number);
		if (borrower != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 公共方法 - 判断是否黑名单
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 * @author liuDaodao
	 * 
	 */
	@Override
	public boolean isBlackUser(String idCard, String name) {
		Example example = new Example(BwBlacklist.class);
		example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card", idCard.toUpperCase());
		List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
		if (CommUtils.isNull(desList) == false) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 公共方法 - 判断是否黑名单（掩码版）
	 * 
	 * @param id_number
	 * @param name
	 * @return
	 * @author 张博
	 * 
	 */
	@Override
	public boolean isBlackUser2(String name, String phone, String id_number) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter2(name, phone, id_number);
		if (CommUtils.isNull(borrower)) {
			return false;
		}

		Example example = new Example(BwBlacklist.class);
		example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card",
				borrower.getIdCard().toUpperCase());
		List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
		if (CommUtils.isNull(desList) == false) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 公共方法 - 判断是否有进行中的订单
	 * 
	 * @author liuDaodao
	 * @param idCard
	 * @return boolean
	 */
	@Override
	public boolean isPocessingOrder(String idCard) {
		BwBorrower bw = new BwBorrower();
		bw.setIdCard(idCard); // 身份证号
		bw = bwBorrowerService.findBwBorrowerByAttr(bw);
		if (bw != null) {
			logger.info("开始考拉征信查询进行中的订单,borrowerId=" + bw.getId());
			Long count = bwOrderService.findProOrder(String.valueOf(bw.getId()));
			logger.info("结束考拉征信查询进行中的订单,count=" + count);
			if (count != null && count.intValue() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 公共方法 - 判断是否有进行中的订单
	 * 
	 * @author 张博
	 * @param id_number
	 * @return boolean
	 */
	@Override
	public boolean isPocessingOrder2(String name, String phone, String id_number) {
		BwBorrower bw = bwBorrowerService.oldUserFilter2(name, phone, id_number);
		bw = bwBorrowerService.findBwBorrowerByAttr(bw);
		if (bw != null) {
			logger.info("开始查询进行中的订单,borrowerId=" + bw.getId());

			/*
			 * BwOrder bwOrder=new BwOrder(); bwOrder.setStatusId(8L); bwOrder.setBorrowerId(bw.getId()); bwOrder =
			 * bwOrderService.findBwOrderByAttr(bwOrder); if(!CommUtils.isNull(bwOrder)){ return false; }
			 */

			Long count = bwOrderService.findProOrder(String.valueOf(bw.getId()));
			logger.info("结束查询进行中的订单,count=" + count);
			if (count != null && count.intValue() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 公共方法 - 是否被拒（使用该方法得确保用户已存在）
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 * @author liuDaodao
	 */
	@Override
	public boolean isRejectRecord(String idCard, String name) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter(idCard.substring(0, idCard.length() - 5), name);
		if (CommUtils.isNull(borrower)) {
			return false;
		}
		// 5查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
		BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
		if (!CommUtils.isNull(record)) {
			// 永久拒绝
			if ("0".equals(String.valueOf(record.getRejectType()))) {
				return true;
			} else {
				// 6判断临时拒绝是否到期，如果没到期则返回400，否则返回200+is_reloan=1
				Date rejectDate = record.getCreateTime();
				long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime()) / (24 * 60 * 60 * 1000);
				if (day <= 7) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 公共方法 - 是否被拒（使用该方法得确保用户已存在，掩码版）
	 * 
	 * @param name
	 * @param phone
	 * @param id_number
	 * @return
	 * @author 张博
	 */
	@Override
	public UserCheckResp isRejectRecord2(String name, String phone, String id_number) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter2(name, phone, id_number);
		UserCheckResp userCheckResp = new UserCheckResp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (CommUtils.isNull(borrower)) {
			// if_can_loan 是否可借 0-否；1-是
			// return false;
			userCheckResp.setIf_can_loan("1");
			return userCheckResp;
		}
		// 5查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
		BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());

		if (!CommUtils.isNull(record)) {
			Date rejectDate = record.getCreateTime();
			// 永久拒绝
			if ("0".equals(String.valueOf(record.getRejectType()))) {
				userCheckResp.setIf_can_loan("0");
				userCheckResp.setCan_loan_time(null);
				return userCheckResp;
				// return true;
			} else {
				// 6判断临时拒绝是否到期，如果没到期则返回400，否则返回200+is_reloan=1

				long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime()) / (24 * 60 * 60 * 1000);
				logger.info("开始查询是否被拒的订单,day=" + day + ">>" + Calendar.getInstance().getTime().getTime() + ">>"
						+ rejectDate.getTime());
				if (day <= 7) {
					String can_loan_time = sdf.format(rejectDate.getTime() + 24 * 60 * 60 * 1000 * 7);
					userCheckResp.setCan_loan_time(can_loan_time);
					userCheckResp.setIf_can_loan("0");
					return userCheckResp;
					// return true;
				}
			}
		}

		userCheckResp.setIf_can_loan("1");

		return userCheckResp;
		// return false;
	}

	private void methodEnd(StopWatch stopWatch, String methodName, String message, Rong360Resp resp) {
		stopWatch.stop();
		logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis() + "," + message + ",resp=" + resp);
		ThreadLocalUtil.remove();
	}

	private BwBankCard findBwBankCardByAttrProxy(BwBankCard bwBankCard) {
		logger.info("开始查询银行卡信息:bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
		logger.info("银行卡信息查询结果：bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		return bwBankCard;
	}

	/*
	 * 推单信息处理
	 */
	@Override
	public DrainageResponse savePushOrder(long sessionId, PushOrderRequest pushOrderRequest) {
		logger.info("START>>>savePushOrder>>>" + sessionId);
		try {
			if (null == pushOrderRequest) {
				return pushOrderReturn(sessionId, DrainageResponse.CODE_PARAMETER, "入参为空");
			}
			String userName = pushOrderRequest.getUserName();
			String idCard = pushOrderRequest.getIdCard();
			String phone = pushOrderRequest.getPhone();
			Integer channelId = pushOrderRequest.getChannelId();
			String thirdOrderNo = pushOrderRequest.getThirdOrderNo();
			if (StringUtils.isBlank(userName)) {
				return pushOrderReturn(sessionId, DrainageResponse.CODE_PARAMETER, "姓名为空");
			}
			if (StringUtils.isBlank(idCard)) {
				return pushOrderReturn(sessionId, DrainageResponse.CODE_PARAMETER, "身份证号码为空");
			}
			if (StringUtils.isBlank(phone)) {
				return pushOrderReturn(sessionId, DrainageResponse.CODE_PARAMETER, "手机号码为空");
			}
			if (CommUtils.isNull(channelId)) {
				return pushOrderReturn(sessionId, DrainageResponse.CODE_PARAMETER, "渠道编码不存在");
			}
			if (StringUtils.isBlank(thirdOrderNo)) {
				return pushOrderReturn(sessionId, DrainageResponse.CODE_PARAMETER, "三方订单号为空");
			}
			// 获得产品
			BwProductDictionary bwProductDictionary = thirdCommonService.getProduct(userName, idCard, phone,
					pushOrderRequest.getExpectNumber());
			Integer productId = bwProductDictionary.getId().intValue();
			logger.info(sessionId + ">>> 获取产品 ID：" + productId);
			// 新增或更新借款人
			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
			long borrowerId = borrower.getId();
			logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);

			// 判断该渠道是否有撤回的订单
			BwOrder order = new BwOrder();
			order.setBorrowerId(borrowerId);
			order.setStatusId(8L);
			order.setChannel(channelId);
			order = bwOrderService.findBwOrderByAttr(order);

			if (order == null) {
				// 查询是否有进行中的订单
				long count = bwOrderService.findProOrder(borrowerId + "");
				logger.info(sessionId + ">>> 进行中的订单校验：" + count);
				if (count > 0) {
					return pushOrderReturn(sessionId, DrainageResponse.CODE_FAIL, "存在进行中的订单，请勿重复推送");
				}
			}
			// 判断是否有草稿状态的订单
			BwOrder bwOrder = new BwOrder();
			bwOrder.setBorrowerId(borrowerId);
			bwOrder.setStatusId(1L);
			bwOrder.setProductType(1);
			bwOrder.setChannel(channelId);
			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
			bwOrder.setStatusId(8L);
			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
			boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
			if (boList != null && boList.size() > 0) {
				bwOrder = boList.get(boList.size() - 1);
				// bwOrder.setChannel(channelId);
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setProductType(1);
				bwOrder.setProductId(productId);
				bwOrder.setExpectMoney(pushOrderRequest.getExpectMoney());//
				bwOrder.setExpectNumber(pushOrderRequest.getExpectNumber());
				// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// bwOrder.setSubmitTime(simpleDateFormat.parse(basicInfo.getApplyDate()));
				// bwOrder.setBorrowUse(basicInfo.getDesc());
				bwOrderService.updateBwOrder(bwOrder);
			} else {
				bwOrder = new BwOrder();
				bwOrder.setOrderNo(OrderUtil.generateOrderNo());
				bwOrder.setBorrowerId(borrower.getId());
				bwOrder.setStatusId(1L);
				bwOrder.setCreateTime(Calendar.getInstance().getTime());
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setChannel(channelId);
				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
				bwOrder.setApplyPayStatus(0);
				bwOrder.setProductId(bwProductDictionary.getId().intValue());
				bwOrder.setProductType(1);
				bwOrder.setExpectMoney(pushOrderRequest.getExpectMoney());//
				bwOrder.setExpectNumber(pushOrderRequest.getExpectNumber());
				// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// bwOrder.setSubmitTime(simpleDateFormat.parse(basicInfo.getApplyDate()));
				// bwOrder.setBorrowUse(basicInfo.getDesc());
				bwOrderService.addBwOrder(bwOrder);
			}
			long orderId = bwOrder.getId();
			logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);

			// 判断是否有融360订单
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(orderId);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			if (bwOrderRong == null) {
				bwOrderRong = new BwOrderRong();
				bwOrderRong.setOrderId(orderId);
				bwOrderRong.setThirdOrderNo(thirdOrderNo);
				bwOrderRong.setChannelId(Long.valueOf(channelId));
				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
				bwOrderRongService.save(bwOrderRong);
			} else {
				if (null == bwOrderRong.getChannelId()) {
					bwOrderRong.setChannelId(Long.valueOf(channelId));
				}
				bwOrderRong.setThirdOrderNo(thirdOrderNo);
				bwOrderRongService.update(bwOrderRong);
			}
			logger.info(sessionId + ">>> 判断是否有融360订单");

			// 判断是否有工作信息
			BwWorkInfo bwWorkInfo = new BwWorkInfo();
			bwWorkInfo.setOrderId(orderId);
			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
			BwWorkInfo bwWorkInfo_ = pushOrderRequest.getBwWorkInfo();
			if (null == bwWorkInfo_) {
				bwWorkInfo_ = new BwWorkInfo();
			}
			if (bwWorkInfo == null) {
				bwWorkInfo_.setOrderId(orderId);
				bwWorkInfo_.setCallTime("10:00 - 12:00");// 默认值
				bwWorkInfo_.setUpdateTime(Calendar.getInstance().getTime());
				bwWorkInfo_.setCreateTime(Calendar.getInstance().getTime());
				bwWorkInfoService.save(bwWorkInfo_, borrowerId);
			} else {
				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
				bwWorkInfo.setWorkYears(bwWorkInfo_.getWorkYears());
				bwWorkInfo.setComName(bwWorkInfo_.getComName());
				bwWorkInfo.setIncome(bwWorkInfo_.getIncome());
				bwWorkInfo.setIndustry(bwWorkInfo_.getIndustry());
				bwWorkInfoService.update(bwWorkInfo);
			}
			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId); // 插入个人认证记录
			logger.info(sessionId + ">>> 判断是否有工作信息");

			// 通讯录
			List<BwContactList> contactList_ = pushOrderRequest.getBwContactList();
			List<BwContactList> listConS = new ArrayList<BwContactList>();
			if (contactList_ != null && contactList_.size() > 0) {
				for (BwContactList contact : contactList_) {
					if (CommUtils.isNull(contact.getName())) {
						continue;
					}
					if (CommUtils.isNull(contact.getPhone())) {
						continue;
					}
					BwContactList bwContactList = new BwContactList();
					bwContactList.setBorrowerId(borrowerId);
					bwContactList.setPhone(contact.getPhone());
					bwContactList.setName(contact.getName());
					listConS.add(bwContactList);
				}
				bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
			}
			logger.info(sessionId + ">>> 处理通讯录信息 ");

			// 运营商
			BwOperateBasic bwOperateBasic_ = pushOrderRequest.getBwOperateBasic();
			BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
			if (null == bwOperateBasic_) {
				bwOperateBasic_ = new BwOperateBasic();
			}
			if (bwOperateBasic == null) {
				bwOperateBasic_.setBorrowerId(borrowerId);
				bwOperateBasic_.setCreateTime(new Date());
				bwOperateBasic_.setUpdateTime(new Date());
				bwOperateBasicService.save(bwOperateBasic_);
			} else {
				bwOperateBasic.setBorrowerId(borrowerId);
				bwOperateBasic.setUpdateTime(new Date());
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasic.setUserSource(bwOperateBasic_.getUserSource());
				bwOperateBasic.setIdCard(bwOperateBasic_.getIdCard());
				bwOperateBasic.setAddr(bwOperateBasic_.getAddr());
				bwOperateBasic.setPhone(bwOperateBasic_.getPhone());
				bwOperateBasic.setPhoneRemain(bwOperateBasic_.getPhoneRemain());
				bwOperateBasic.setRealName(bwOperateBasic_.getRealName());
				bwOperateBasic.setUpdateTime(new Date());
				bwOperateBasic.setRegTime(bwOperateBasic_.getRegTime());
				bwOperateBasicService.update(bwOperateBasic);
			}
			logger.info(sessionId + ">>> 处理运营商信息");

			// 通话记录
			List<BwOperateVoice> bwOperateVoice_ = pushOrderRequest.getBwOperateVoiceList();
			Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
			if (CollectionUtils.isNotEmpty(bwOperateVoice_)) {
				SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
				for (BwOperateVoice bwOperateVoice : bwOperateVoice_) {
					try {
						Date jsonCallData = sdf_hms.parse(bwOperateVoice.getCall_time());
						if (callDate == null || jsonCallData.after(callDate)) { // 通话记录采取最新追加的方式
							bwOperateVoice.setUpdateTime(new Date());
							bwOperateVoice.setBorrower_id(borrowerId);
							bwOperateVoiceService.save(bwOperateVoice);
						}
					} catch (Exception e) {
						logger.error(sessionId + ">>> 插入单条通话记录异常，忽略该条记录" + e.getMessage());
					}
				}
			}
			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);// 插入运营商认证记录
			logger.info(sessionId + ">>> 处理通话记录信息 ");

			// 芝麻信用
			Integer sesameScore_ = pushOrderRequest.getSesameScore();
			if (null != sesameScore_) {
				BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrowerId);
				if (bwZmxyGrade == null) {
					bwZmxyGrade = new BwZmxyGrade();
					bwZmxyGrade.setBorrowerId(borrowerId);
					bwZmxyGrade.setZmScore(sesameScore_);
					bwZmxyGrade.setCreateTime(Calendar.getInstance().getTime());
					bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
					bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
				} else {
					bwZmxyGrade.setZmScore(sesameScore_);
					bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
					bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
				}
				thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 4, channelId);// 插入芝麻认证记录
			}
			logger.info(sessionId + ">>> 处理芝麻信用信息 ");

			// 认证图片
			String frontFile = pushOrderRequest.getIdCardFrontImage();
			String backFile = pushOrderRequest.getIdCardBackImage();
			String natureFile = pushOrderRequest.getIdCardHanderImage();
			if (StringUtils.isNotBlank(frontFile)) {
				String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01"); // 身份证正面照
				logger.info(sessionId + ">>> 身份证正面 " + frontImage);
				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0); // 保存身份证正面照
			}
			if (StringUtils.isNotBlank(backFile)) {
				String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02"); // 身份证反面照
				logger.info(sessionId + ">>> 身份证反面 " + backImage);
				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0); // 保存身份证反面照
			}
			if (StringUtils.isNotBlank(natureFile)) {
				String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03"); // 手持照
				logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0); // 保存手持照
			}
			logger.info(sessionId + ">>> 处理认证图片 ");

			// 保存身份证信息
			BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
			bwIdentityCard.setBorrowerId(borrowerId);
			bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
			BwIdentityCard2 bwIdentityCard_ = pushOrderRequest.getBwIdentityCard();
			if (null == bwIdentityCard_) {
				bwIdentityCard_ = new BwIdentityCard2();
			}
			if (bwIdentityCard == null) {
				bwIdentityCard_.setBorrowerId(borrowerId);
				bwIdentityCard_.setCreateTime(new Date());
				bwIdentityCard_.setUpdateTime(new Date());
				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard_);
			} else {
				bwIdentityCard.setAddress(bwIdentityCard_.getAddress());
				bwIdentityCard.setBirthday(bwIdentityCard_.getBirthday());
				bwIdentityCard.setGender(bwIdentityCard_.getGender());
				bwIdentityCard.setIdCardNumber(bwIdentityCard_.getIdCardNumber());
				bwIdentityCard.setIssuedBy(bwIdentityCard_.getIssuedBy());
				bwIdentityCard.setName(bwIdentityCard_.getName());
				bwIdentityCard.setRace(bwIdentityCard_.getRace());
				bwIdentityCard.setUpdateTime(new Date());
				bwIdentityCard.setValidDate(bwIdentityCard_.getValidDate());
				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
			}
			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);// 插入身份认证记录
			logger.info(sessionId + ">>> 处理身份证信息");

			// 亲属联系人
			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
			BwPersonInfo bwPersonInfo_ = pushOrderRequest.getBwPersonInfo();
			if (null == bwPersonInfo_) {
				bwPersonInfo_ = new BwPersonInfo();
			}
			if (bwPersonInfo == null) {
				bwPersonInfo_.setCreateTime(new Date());
				bwPersonInfo_.setOrderId(orderId);
				bwPersonInfo_.setUpdateTime(new Date());
				bwPersonInfoService.add(bwPersonInfo_);
			} else {
				bwPersonInfo.setAddress(bwPersonInfo_.getAddress());
				bwPersonInfo.setCarStatus(bwPersonInfo_.getCarStatus());
				bwPersonInfo.setCityName(bwPersonInfo_.getCityName());
				bwPersonInfo.setEmail(bwPersonInfo_.getEmail());
				bwPersonInfo.setHouseStatus(bwPersonInfo_.getHouseStatus());
				bwPersonInfo.setMarryStatus(bwPersonInfo_.getMarryStatus());
				// bwPersonInfo.setOrderId(orderId);
				bwPersonInfo.setRelationName(bwPersonInfo_.getRelationName());
				bwPersonInfo.setRelationPhone(bwPersonInfo_.getRelationPhone());
				bwPersonInfo.setUnrelationName(bwPersonInfo_.getUnrelationName());
				bwPersonInfo.setUnrelationPhone(bwPersonInfo_.getUnrelationPhone());
				bwPersonInfo.setUpdateTime(new Date());
				bwPersonInfoService.update(bwPersonInfo);
			}
			logger.info(sessionId + ">>> 处理亲属联系人信息");

			// 银行卡
			BwBankCard bwBankCard_ = pushOrderRequest.getBwBankCard();
			if (null != bwBankCard_) {
				BwBankCard bwBankCard = new BwBankCard();
				bwBankCard.setBorrowerId(borrowerId);
				bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
				if (bwBankCard != null && (bwBankCard.getSignStatus() == 1 || bwBankCard.getSignStatus() == 2)) {
					// 绑卡通知
					Map<String, Object> map = new HashMap<>();
					map.put("channelId", channelId);
					map.put("orderId", orderId);
					map.put("result", "");
					String json = JSON.toJSONString(map);
					RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
					logger.info(sessionId + ">>> 已经绑卡,放入绑卡通知");
				} else if (bwBankCard != null) {
					bwBankCard.setBankName(bwBankCard_.getBankName());
					bwBankCard.setBankCode(bwBankCard_.getBankCode());
					bwBankCard.setCardNo(bwBankCard_.getCardNo());
					bwBankCard.setPhone(bwBankCard_.getPhone());
					bwBankCard.setProvinceCode(bwBankCard_.getProvinceCode());
					bwBankCard.setCityCode(bwBankCard_.getCityCode());
					bwBankCard.setSignStatus(0);
					bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
					bwBankCardService.updateBwBankCard(bwBankCard);
				} else {
					bwBankCard_.setBorrowerId(borrowerId);
					bwBankCard_.setSignStatus(0);
					bwBankCard_.setCreateTime(Calendar.getInstance().getTime());
					bwBankCard_.setUpdateTime(Calendar.getInstance().getTime());
					bwBankCardService.saveBwBankCard(bwBankCard_, borrowerId);
				}
				logger.info(sessionId + ">>> 保存银行卡");
			}

			if (null != pushOrderRequest.getOrderStatus()) {
				bwOrder.setStatusId(Long.valueOf(pushOrderRequest.getOrderStatus()));
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setSubmitTime(Calendar.getInstance().getTime());
				bwOrderService.updateBwOrder(bwOrder);

				logger.info(sessionId + ">>> 修改工单状态为" + pushOrderRequest.getOrderStatus());
				HashMap<String, String> hm = new HashMap<>();
				hm.put("channelId", channelId + "");
				hm.put("orderId", String.valueOf(bwOrder.getId()));
				hm.put("orderStatus", pushOrderRequest.getOrderStatus() + "");
				hm.put("result", "");
				String hmData = JSON.toJSONString(hm);
				RedisUtils.rpush("tripartite:orderStatusNotify:" + channelId, hmData);
				// 放入redis
				SystemAuditDto systemAuditDto = new SystemAuditDto();
				systemAuditDto.setIncludeAddressBook(0);
				systemAuditDto.setOrderId(orderId);
				systemAuditDto.setBorrowerId(borrowerId);
				systemAuditDto.setName(userName);
				systemAuditDto.setPhone(phone);
				systemAuditDto.setIdCard(idCard);
				systemAuditDto.setChannel(channelId);
				systemAuditDto.setThirdOrderId(thirdOrderNo);
				systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
				RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
				logger.info(sessionId + ">>> 修改订单状态，并放入redis");
			}

			// 更改订单进行时间
			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
			bwOrderProcessRecord.setSubmitTime(new Date());
			bwOrderProcessRecord.setOrderId(bwOrder.getId());
			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
			logger.info(sessionId + ">>> 更改订单进行时间");
			return pushOrderReturn(sessionId, DrainageResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(sessionId + "END>>>savePushOrder>>>" + e.getMessage());
			return pushOrderReturn(sessionId, DrainageResponse.CODE_EXCEPITON, "系统异常");
		}
	}

	private DrainageResponse pushOrderReturn(long sessionId, String code, String msg) {
		logger.info(sessionId + "END>>>savePushOrder>>>" + msg);
		DrainageResponse drainageResponse = new DrainageResponse();
		drainageResponse.setCode(code);
		drainageResponse.setMessage(msg);
		return drainageResponse;
	}

}
