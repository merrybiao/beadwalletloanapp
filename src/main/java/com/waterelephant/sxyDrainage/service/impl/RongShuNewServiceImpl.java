//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.tuple.Pair;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.drainage.service.DrainageService;
//import com.waterelephant.entity.BwAdjunct;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwAdjunctService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.service.impl.BwRepaymentPlanService;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.BeijingDrainageResponse;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RtyResponse;
//import com.waterelephant.sxyDrainage.entity.rongshu.ContactInfoResponse;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsRequest;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsResponse;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsRsponseInfo;
//import com.waterelephant.sxyDrainage.entity.rongshu.SxyBasicInfo;
//import com.waterelephant.sxyDrainage.entity.rongshu.SxyContact;
//import com.waterelephant.sxyDrainage.entity.rongshu.SxyIdentifyInfo;
//import com.waterelephant.sxyDrainage.entity.rongshu.SxyOperator;
//import com.waterelephant.sxyDrainage.entity.rongshu.SxyRequestPush;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.RongShuNewService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.rongshu.HttpsUtil;
//import com.waterelephant.sxyDrainage.utils.rongshu.RongShuConstant;
//import com.waterelephant.sxyDrainage.utils.rongshu.RongShuUtils;
//import com.waterelephant.third.entity.MessageDto;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.third.utils.ThirdUtil;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
//@Service
//public class RongShuNewServiceImpl implements RongShuNewService {
//	public static final String CHANNELID = RongShuConstant.CHANNELID;
//	private Logger logger = Logger.getLogger(RongShuNewServiceImpl.class);
//
//	/**
//	 * token失效时间,单位:分钟
//	 */
//	public final static int EXPIRED_TIME = 1 * 24 * 60 * 60;
//	public static final String TOKEN_PREFIX = "FenLingServiceImpl:";
//
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private DrainageService drainageService;
//	@Autowired
//	private IBwOrderService bwOrderService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//	@Autowired
//	private IBwWorkInfoService bwWorkInfoService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//	@Autowired
//	private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//	@Autowired
//	private IBwPersonInfoService bwPersonInfoService;
//	@Autowired
//	private BwOrderProcessRecordService bwOrderProcessRecordService;
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//	@Autowired
//	private BwRepaymentPlanService bwRepaymentPlanService;
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//	@Autowired
//	private IBwAdjunctService bwAdjunctService;
//	
//	@Override
//	public RsResponse checkUser(Long sessionId, String user_name, String user_mobile, String id_card) {
//		logger.info(" 开始RongShuNewServiceImpl.checkUser()方法{user_name=" + user_name + ",user_mobile=" + user_mobile
//				+ ",id_card=" + id_card + "}");
//		RsResponse rsResponse = new RsResponse();
//		if(StringUtils.isBlank(user_name)){
//			rsResponse.setCode(RsResponse.CODE_PARMERR);
//			rsResponse.setMessage("用户名不能为空");
//			logger.info("系统异常,用户名为空");
//			return rsResponse;
//		}
//		if(StringUtils.isBlank(user_mobile)){
//			rsResponse.setCode(RsResponse.CODE_PARMERR); 
//			rsResponse.setMessage("电话号码为空");
//			logger.info("系统异常,电话号码为空");
//			return rsResponse;
//		}
//		if(StringUtils.isBlank(id_card)){
//			rsResponse.setCode(RsResponse.CODE_PARMERR);
//			rsResponse.setMessage("身份证号码为空");
//			logger.info("系统异常,身份证号码为空");
//			return rsResponse;
//		}
//		
//		/**
//		 * 新增的判断用户多个手机号贷款问题
//		 */
//		boolean flag = false;
//		try {
//			flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, id_card, user_name);
//		} catch (Exception e) {
//			logger.info("榕树效验调用checkUserAccountProgressOrder方法异常:"+e);
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("榕树效验调用验重方法异常");
//		}
//	      if (flag) {
//	    	rsResponse.setCode(RsResponse.CODE_FAILERR);
//		    rsResponse.setMessage("存在进行中的订单，请勿重复推送");
//	        logger.info(sessionId + "：结束榕树service层订单推送接口：" + JSON.toJSONString(rsResponse));
//	        return rsResponse; 
//	      }
//		
//		DrainageRsp checkUser = commonService.checkUser(sessionId, user_name, user_mobile, id_card); 
//		// 获取公共接口返回的信息判断给三方返回的数据
//		String drainageRspCode = checkUser.getCode();
//		logger.info(JSONObject.toJSONString("checkUser检查用户接口返回code为：" + drainageRspCode));
//		// 返回数据
//		// 验证数据是否为空
//		if (drainageRspCode.equals("1002")) {
//			rsResponse.setCode(RsResponse.CODE_PARMERR);
//			rsResponse.setMessage(checkUser.getMessage());
//			logger.info("系统异常,用户" + user_name + ":" + checkUser.getMessage());
//			return rsResponse;
//
//		}
//		// 验证是否符合申请要求
//		if (drainageRspCode.equals("2001") || drainageRspCode.equals("2002") || drainageRspCode.equals("2003")
//				|| drainageRspCode.equals("2004")) {
//			rsResponse.setCode(Integer.parseInt(drainageRspCode));
//			rsResponse.setMessage(checkUser.getMessage());
//			logger.info(user_name + "不可申请," + checkUser.getMessage());
//			return rsResponse;
//		}
//		if("0000".equals(drainageRspCode)){
//		rsResponse.setCode(RsResponse.CODE_SUCCESS);
//		rsResponse.setMessage("该用户允许通过");
//		logger.info("检查用户接口返回数据为：" + JSONObject.toJSONString(rsResponse));
//		}else{
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("该用户不允许通过");
//			logger.info("检查用户接口返回数据为：" + JSONObject.toJSONString(rsResponse));
//		}
//
//		return rsResponse;
//	}
//
//	/**
//	 * 进件推送
//	 */
//	@Override
//	public RsResponse savePushOrder(Long sessionId, RsRequest rsRequest) {
//		RsResponse rsResponse = new RsResponse();
//		Map<String,Object> mapResp = new HashMap<String,Object>();
//		logger.info(sessionId + ":进件推送接口>>>");
//		try {
//			SxyRequestPush sxyRequestPush = JSON.parseObject(rsRequest.getRequest(), SxyRequestPush.class);
//			if (sxyRequestPush == null) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("请求参数为空");
//				logger.info(sessionId+"参数为空sxyRequestPush:"+sxyRequestPush);
//				return rsResponse;
//			}
//			SxyBasicInfo sxyBasicInfo = sxyRequestPush.getBasicInfo();
//			SxyIdentifyInfo sxyIdentifyInfo = sxyRequestPush.getIdInfo();
//			SxyContact sxyContact = sxyRequestPush.getContact();
//			SxyOperator sxyOperator = sxyRequestPush.getOperator();
//			if (null == sxyBasicInfo || null == sxyIdentifyInfo || null == sxyContact || null == sxyOperator) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("请求参数为空");
//				logger.info(sessionId+"参数为空sxyBasicInfo:"+sxyBasicInfo+"||sxyIdentifyInfo:"+sxyIdentifyInfo+"||sxyContact:"+sxyContact+"||sxyOperator:"+sxyOperator);
//				return rsResponse;
//			}
//			String userName = sxyIdentifyInfo.getName();
//			String idCard = sxyIdentifyInfo.getCid();
//			String phone = sxyRequestPush.getRegisterPhone();//电话号码
//			Integer channelId = Integer.parseInt(rsRequest.getChannelId());//
//			String thirdOrderNo = String.valueOf(sxyRequestPush.getOrderId());//三方订单号
//			String passworde = null;
//			int uid = sxyRequestPush.getUid();
//			if (StringUtils.isBlank(userName)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("姓名为空");
//				return rsResponse;
//			}
//			if (StringUtils.isBlank(idCard)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("身份证号码为空");
//				return rsResponse;
//			}
//			if (StringUtils.isBlank(phone)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("手机号为空");
//				return rsResponse;
//			}
//			if (CommUtils.isNull(channelId)) {
//				logger.info(sessionId + " 结束RongShuNewServiceImpl.savePushOrder()方法，返回结果：渠道编码为空");
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("渠道编码为空");
//				return rsResponse;
//			}
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				logger.info(sessionId + " 结束RongShuNewServiceImpl.savePushOrder()方法，返回结果:三方订单号为空");
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("三方订单号为空");
//				return rsResponse;
//			}
//			
//			// 新增或更新借款人
//			BwBorrower borrower = addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId, passworde);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//			
//			
//			// 判断该渠道是否有撤回的订单
//			/*BwOrder order = new BwOrder();
//			order.setBorrowerId(borrowerId);
//			order.setStatusId(8L);
//			order.setChannel(channelId);
//			order = bwOrderService.findBwOrderByAttr(order);
//
//			if (order == null) {
//				// 查询是否有进行中的订单
//				long count = bwOrderService.findProOrder(borrowerId + "");
//				logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//				if (count > 0) {
//					rsResponse.setCode(RsResponse.CODE_FAILERR);
//					rsResponse.setMessage("存在进行中的订单，请勿重复推送");
//					mapResp.put("status", "2002");
//					mapResp.put("remark", "存在进行中的订单，请勿重复推送");
//					rsResponse.setResponse(JSON.toJSONString(mapResp));
//					return rsResponse;
//				}
//			}*/
//			
//			/**
//			 * 新增的判断用户多个手机号贷款问题
//			 */
//			boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//		      if (flag) {
//		    	rsResponse.setCode(RsResponse.CODE_FAILERR);
//			    rsResponse.setMessage("存在进行中的订单，请勿重复推送");
//			    mapResp.put("status", "2002");
//				mapResp.put("remark", "存在进行中的订单，请勿重复推送");
//				rsResponse.setResponse(JSON.toJSONString(mapResp));
//		        logger.info(sessionId + "：结束榕树service层订单推送接口：" + JSON.toJSONString(rsResponse));
//		        return rsResponse;
//		      }
//			
//			
//			Integer productId = Integer.valueOf(RongShuConstant.PRODUCTID);
//			// 判断是否有草稿状态的订单
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setBorrowerId(borrowerId);
//			bwOrder.setStatusId(1L);
//			bwOrder.setProductType(2);
//			bwOrder.setChannel(channelId);
//			bwOrder.setProductId(productId);
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
//			bwOrder.setStatusId(8L);
//			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
//			boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//			if (boList != null && boList.size() > 0) {
//				bwOrder = boList.get(boList.size() - 1);
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setProductType(2);
//				bwOrder.setStatusId(1L);
//				bwOrder.setExpectMoney(sxyRequestPush.getLoanAmount()+0.0D);//预借金额
//				bwOrder.setExpectNumber(4);
//				bwOrder.setRepayType(2);
//				bwOrderService.updateBwOrder(bwOrder);
//			} else {
//				bwOrder = new BwOrder();
//				bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//				bwOrder.setBorrowerId(borrower.getId());
//				bwOrder.setStatusId(1L);
//				bwOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setChannel(channelId);
//				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//				bwOrder.setApplyPayStatus(0);
//				bwOrder.setProductId(productId);
//				bwOrder.setProductType(2);
//				bwOrder.setRepayType(2);
//				bwOrder.setExpectMoney(sxyRequestPush.getLoanAmount()+0.0D);
//				bwOrder.setExpectNumber(4);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//			logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//			
//			// 判断是否有订单
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			//bwOrderRong.setOrderId(orderId);
//			bwOrderRong.setThirdOrderNo(thirdOrderNo);
//			bwOrderRong.setChannelId(Long.parseLong(CHANNELID));
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (bwOrderRong == null) {
//				bwOrderRong = new BwOrderRong();
//				bwOrderRong.setOrderId(orderId);
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.save(bwOrderRong);
//			} else {
//				//bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setOrderId(orderId);
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRongService.update(bwOrderRong);
//			}
//			logger.info(sessionId + ">>> 判断是否有订单");
//			
//			// 判断是否有商户订单信息
//			BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//			bwMerchantOrder.setOrderId(orderId);
//			bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//			if (bwMerchantOrder == null) {
//				bwMerchantOrder = new BwMerchantOrder();
//				bwMerchantOrder.setBorrowerId(borrowerId);
//				bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwMerchantOrder.setExt3("0");
//				bwMerchantOrder.setMerchantId(0L);
//				bwMerchantOrder.setOrderId(orderId);
//				bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwMerchantOrderServiceImpl.insertByAtt(bwMerchantOrder);
//			} else {
//				bwMerchantOrder.setBorrowerId(borrowerId);
//				bwMerchantOrder.setExt3("0");
//				bwMerchantOrder.setMerchantId(0L);
//				bwMerchantOrder.setOrderId(orderId);
//				bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwMerchantOrderServiceImpl.updateByAtt(bwMerchantOrder);
//			}
//
//			// 判断是否有工作信息
//			BwWorkInfo bwWorkInfo = new BwWorkInfo();
//			bwWorkInfo.setOrderId(orderId);
//			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//			@SuppressWarnings("unchecked")
//			Map<String,Object> objectExt = (Map<String,Object>)sxyRequestPush.getExtendInfo();
//			if(null==objectExt || 0==objectExt.size()){
//				logger.info(sessionId + " 结束RongShuNewServiceImpl.savePushOrder()方法，返回结果:扩展数据为空");
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("扩展数据为空");
//				mapResp.put("status", "2002");
//				mapResp.put("remark", "扩展数据为空");
//				rsResponse.setResponse(JSON.toJSONString(mapResp));
//				return rsResponse;
//			}
//			if (null == bwWorkInfo) {
//				bwWorkInfo = new BwWorkInfo();
//				bwWorkInfo.setOrderId(orderId);
//				bwWorkInfo.setIncome(String.valueOf(objectExt.get("income")));//收入
//				bwWorkInfo.setComName(sxyBasicInfo.getCompanyName());//公司名称
//				bwWorkInfo.setIndustry(String.valueOf(objectExt.get("industry")));//行业
//				bwWorkInfo.setWorkYears(RongShuUtils.getWorkYear(String.valueOf(objectExt.get("jobTime"))));
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//				bwWorkInfoService.save(bwWorkInfo, borrowerId);
//			} else {
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setIncome(String.valueOf(objectExt.get("income")));
//				bwWorkInfo.setComName(sxyBasicInfo.getCompanyName());
//				bwWorkInfo.setIndustry(String.valueOf(objectExt.get("industry")));
//				bwWorkInfo.setWorkYears(RongShuUtils.getWorkYear(String.valueOf(objectExt.get("jobTime"))));
//				bwWorkInfoService.update(bwWorkInfo);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId); // 插入个人认证记录
//			logger.info(sessionId + ">>> 判断是否有工作信息");
//			
//			// 保存身份证信息
//			BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//			bwIdentityCard.setBorrowerId(borrowerId);
//			bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//			if (null == bwIdentityCard) {
//				bwIdentityCard = new BwIdentityCard2();
//				bwIdentityCard.setAddress(sxyIdentifyInfo.getAddress());
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(userName);
//				bwIdentityCard.setIssuedBy(sxyIdentifyInfo.getIssuedBy());
//				//bwIdentityCard.setRace(sxyIdentifyInfo.getNation());//名族
//				bwIdentityCard.setValidDate(sxyIdentifyInfo.getValidDate());
//				bwIdentityCard.setBorrowerId(borrowerId);
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//			} else {
//				bwIdentityCard.setAddress(sxyIdentifyInfo.getAddress());
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(userName);
//				bwIdentityCard.setIssuedBy(sxyIdentifyInfo.getIssuedBy());
//				//bwIdentityCard.setRace(sxyIdentifyInfo.getNation());
//				bwIdentityCard.setValidDate(sxyIdentifyInfo.getValidDate());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);// 插入身份认证记录
//			logger.info(sessionId + ">>> 处理身份证信息");
//		
//			
//			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//			bwOrderProcessRecord.setSubmitTime(new Date());
//			bwOrderProcessRecord.setOrderId(bwOrder.getId());
//			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//				
//			String  key  =  "phone_apply";
//			Map<String,Object>  params  =  new  HashMap<>();
//			params.put("mobile",  phone);
//			params.put("order_id",  orderId);
//			params.put("borrower_id",  borrowerId);
//			String  value  =  JSON.toJSONString(params);
//			RedisUtils.rpush(key,  value);
//			
//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("status", "2001");
//            map.put("remark", "进件初始化成功");
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);
//			rsResponse.setMessage("进件初始化成功");
//			rsResponse.setResponse(JSON.toJSONString(map));
//            Map<String,Object> mapParams = new HashMap<String,Object>();
//            mapParams.put("channelId", channelId);
//            mapParams.put("uid", uid);
//            mapParams.put("thirdOrderId", thirdOrderNo);
//            mapParams.put("orderId", orderId);
//            mapParams.put("appId", RongShuConstant.APPID);
//            mapParams.put("borrowerId", borrowerId);
//            mapParams.put("idCard", idCard);
//            mapParams.put("invokeTime", 0);
//            mapParams.put("qqAccount", String.valueOf(objectExt.get("qqAccount")));
//            mapParams.put("wechat", String.valueOf(objectExt.get("wechat")));
//            mapParams.put("houseCity", sxyBasicInfo.getHouseCity());
//            mapParams.put("houseAddress", sxyBasicInfo.getHouseAddress());
//            mapParams.put("firstName", sxyContact.getFirstName());
//            mapParams.put("firstPhone", sxyContact.getFirstPhone());
//            mapParams.put("secondName", sxyContact.getSecondName());
//            mapParams.put("secondPhone", sxyContact.getSecondPhone());
//            mapParams.put("marriage", RongShuUtils.getMarryStatus(sxyBasicInfo.getMarriage()));
//			RedisUtils.lpush("tripartite:addParams:" + channelId, JSON.toJSONString(mapParams));
//			return rsResponse;
//		} catch (Exception e) {
//			logger.error(sessionId + "执行进件推送接口异常", e);
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("内部错误");
//		}
//		return rsResponse;
//	}
//	
//	public BwBorrower addOrUpdateBorrower(long sessionId, String name, String idCard, String phone, int channelId,
//			String password) throws Exception {
//		// 根据手机号查询
//		BwBorrower borrower = new BwBorrower();
//		borrower.setPhone(phone);
//		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//		if (!CommUtils.isNull(borrower)) {
//			borrower.setPhone(phone);
//			borrower.setAuthStep(1);
//			borrower.setFlag(1);
//			borrower.setState(1);
//			//borrower.setChannel(channelId); // 表示该借款人来源
//			borrower.setIdCard(idCard);
//			borrower.setName(name);
//			borrower.setAge(ThirdUtil.getAgeByIdCard(idCard));
//			borrower.setSex(ThirdUtil.getSexByIdCard(idCard));
//			borrower.setUpdateTime(Calendar.getInstance().getTime());
//			bwBorrowerService.updateBwBorrower(borrower);
//		} else {
//			borrower = new BwBorrower();
//			boolean flag = true;
//			if (StringUtils.isBlank(password)) {
//				flag = false;
//				password = CommUtils.getMD5(ThirdUtil.getRandomPwd().getBytes());
//			}
//			// 创建借款人
//			borrower.setPhone(phone);
//			borrower.setPassword(password);
//			borrower.setAuthStep(1);
//			borrower.setFlag(1);
//			borrower.setState(1);
//			borrower.setChannel(channelId); // 表示该借款人来源
//			borrower.setIdCard(idCard);
//			borrower.setName(name);
//			borrower.setAge(ThirdUtil.getAgeByIdCard(idCard));
//			borrower.setSex(ThirdUtil.getSexByIdCard(idCard));
//			borrower.setCreateTime(Calendar.getInstance().getTime());
//			borrower.setUpdateTime(Calendar.getInstance().getTime());
//			bwBorrowerService.addBwBorrower(borrower);
//			/*if (!flag) {
//				// 发送短信
//				try {
//					if (RedisUtils.exists("tripartite:smsFilter:registerPassword:" + channelId) == false) {
//						String message = ThirdUtil.getMsg(password);
//						MessageDto messageDto = new MessageDto();
//						messageDto.setBusinessScenario("2");
//						messageDto.setPhone(phone);
//						messageDto.setMsg(message);
//						messageDto.setType("1");
//						RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
//					}
//				} catch (Exception e) {
//					logger.error(sessionId + "发送短信异常:", e);
//				}
//			}*/
//		}
//		return borrower;
//	}
//	
//	public static void main(String[] args) {
//		//RsResponse addressData= new RongShuNewServiceImpl().getAddressList(1111l, 3931041);
//		//List<ContactInfoResponse> listContact = RongShuUtils.getNameOrPhone(addressData.getResponse());
//	}
//	
//	
//	/**
//	 * 
//	 * 查询审核结果
//	 * @param sessionId
//	 * @param rytRequest
//	 * @return
//	 */
//	@Override
//	public RsResponse getApprovalResult(long sessionId, Map<String,String> mapData) {
//		RsResponse rsResponse = new RsResponse();
//		try {
//			Integer channelId = Integer.valueOf(RongShuConstant.CHANNELID);
//			String thirdOrderNo = mapData.get("orderId");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("手机号码为空");
//				return rsResponse;
//			}
//			
//			BwOrderRong bwRong = new BwOrderRong();
//			bwRong.setThirdOrderNo(thirdOrderNo);
//			bwRong.setChannelId(Long.parseLong(CHANNELID));
//			bwRong = bwOrderRongService.findBwOrderRongByAttr(bwRong);
//			if (CommUtils.isNull(bwRong)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("工单不存在");
//				logger.info(sessionId + "结束榕树查询审核结果接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setId(bwRong.getOrderId());
//			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//			if (CommUtils.isNull(bwOrder)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("工单不存在");
//				logger.info(sessionId + "结束榕树查询审核结果接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}
//			logger.info("审核结果查询接口borrowerId="+bwOrder.getBorrowerId());
//			BwBorrower bwBorrower = new BwBorrower();
//			bwBorrower.setId(bwOrder.getBorrowerId());
//			bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//			if (CommUtils.isNull(bwBorrower)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("用户表为空");
//				logger.info(sessionId + "结束榕树查询审核结果接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}
//			
//			/*BwOrder bwOrder = bwOrderService.findBwOrderByPhoneAndChannel(mobile, channelId);
//			if (null == bwOrder) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单不存在");
//				return rtyResponse;
//			}*/
//			Map<String, Object> map = new HashMap<>();
//			Map<String, Object> amountLimitMap = new HashMap<>();
//			Map<String, Object> amountOptionMap = new HashMap<>();
//			List<Map<String, Object>> amountOptionList = new ArrayList<>();
//			//map.put("mobile", bwBorrower.getPhone());
//			long statusId = bwOrder.getStatusId();
//			if (statusId >= 4 && 7 != statusId && 8 != statusId) {
//				map.put("status", 6002);
//				map.put("remark", "审核通过");
//				amountLimitMap.put("maxLoan", bwOrder.getBorrowAmount());
//				amountLimitMap.put("minLoan", bwOrder.getBorrowAmount());
//				amountLimitMap.put("maxPeriod", 4);
//				amountLimitMap.put("minPeriod", 4);
//				map.put("amountLimit", amountLimitMap);
//				amountOptionMap.put("min", bwOrder.getBorrowAmount());
//				amountOptionMap.put("max", bwOrder.getBorrowAmount());
//				amountOptionMap.put("step", 100);
//				int[] perStr={4};
//				amountOptionMap.put("periods", perStr);
//				amountOptionList.add(amountOptionMap);
//				map.put("amountOption", amountOptionList);
//				rsResponse.setCode(RsResponse.CODE_SUCCESS);
//				rsResponse.setMessage("返回成功");
//				rsResponse.setResponse(JSON.toJSONString(map));
//                return rsResponse;
//			} else {
//				if(7 == statusId || 8 == statusId){
//					map.put("status", 6004);
//					map.put("remark", "审核失败");
//					rsResponse.setCode(RsResponse.CODE_SUCCESS);
//					rsResponse.setMessage("审核失败");
//					rsResponse.setResponse(JSON.toJSONString(map));
//	                return rsResponse;
//				}
//				map.put("status", 6003);
//				map.put("remark", "审核中");
//				rsResponse.setCode(RsResponse.CODE_SUCCESS);
//				rsResponse.setMessage("返回成功");
//				rsResponse.setResponse(JSON.toJSONString(map));
//                return rsResponse;
//			}
//
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rsResponse.setCode(RtyResponse.CODE_FAILURE);
//			rsResponse.setMessage("请求异常");
//		}
//		return rsResponse;
//	}
//	
//	
//	/**
//	 * 获取还款计划接口
//	 */
//	@Override
//	public RsResponse getRepayPlan(long sessionId, Map<String,String> mapData) {
//		RsResponse rsResponse = new RsResponse();
//		try {
//			String thirdOrderNo = mapData.get("orderId");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单编号为空");
//				return rsResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单不存在");
//				return rsResponse;
//			}
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("用户不存在");
//				return rsResponse;
//			}
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard =  bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("银行卡不存在");
//				return rsResponse;
//			}
//			
//			List<BwRepaymentPlan> list = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//			if (CollectionUtils.isEmpty(list)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("还款计划不存在");
//				return rsResponse;
//			}
//			Double borrowAmount = bwOrder.getBorrowAmount();// 审批金额
//			Map<String,Object> mapDatas = new HashMap<String, Object>();
//			mapDatas.put("status", "9001");
//			mapDatas.put("bankName", bwBankCard.getBankName());
//			mapDatas.put("bankCard", bwBankCard.getCardNo());
//			mapDatas.put("receiveAmount", borrowAmount);//放款金额(到账金额)
//			String bankCode = bwBankCard.getBankCode();
//			if(!StringUtils.isBlank(bankCode)){
//				bankCode = DrainageUtils.convertFuiouBankCodeToBaofu(bankCode);
//				if("GDB".equals(bankCode)){
//					bankCode = "CGB";
//				}
//				else if("BCOM".equals(bankCode)){
//					bankCode = "BCM";
//				}
//				
//			}
//			logger.info("还款计划返回bankCode:"+bankCode);
//			mapDatas.put("bankCode", bankCode);
//			List<Map<String, Object>> planList = new ArrayList<>();
//			Map<String, Object> map = null;
//			double repaymentAmount = 0d;
//			double repayPenaltyInt = 0d;
//			for (BwRepaymentPlan bwRepaymentPlan : list) {
//				
//				map = new HashMap<>();
//				map.put("periodNo", bwRepaymentPlan.getNumber());// 还款周期编号
//				map.put("payType", 4);// 还款类型
//				map.put("alreadyRepayAmount", bwRepaymentPlan.getAlreadyRepayMoney());// 已还金额
//
//				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//				bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//				bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//				if (null != bwOverdueRecord) {
//						double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//								: bwOverdueRecord.getOverdueAccrualMoney();
//						double advance = bwOverdueRecord.getAdvance() == null ? 0D
//								: bwOverdueRecord.getAdvance();
//						repayPenaltyInt = overdueAccrualMoney - advance;
//						
//					//map.put("overdueFee", bwOverdueRecord.getAdvance());// 逾期费用
//				}
//				map.put("overdueFee", repayPenaltyInt);// 逾期费用
//				map.put("repayAmount", bwRepaymentPlan.getRealityRepayMoney()+repayPenaltyInt);// 应还金额
//				// 应还总额
//				repaymentAmount = repaymentAmount + bwRepaymentPlan.getRealityRepayMoney()
//						+ repayPenaltyInt;
//				map.put("status", bwRepaymentPlan.getRepayStatus());// 账单状态 1 未到期 2 已还款 3 逾期
//				map.put("dueTime", bwRepaymentPlan.getRepayTime().getTime());// 到期时间
//				map.put("canRepayTime", new Date().getTime());// 可以还款的日期
//				if ("2".equals(bwRepaymentPlan.getRepayStatus() + "")) {
//					map.put("successTime",bwRepaymentPlan.getUpdateTime().getTime());// 成功还款时间
//				}
//				planList.add(map);
//			}
//			mapDatas.put("repayPlan", JSON.toJSONString(planList));
//			mapDatas.put("totalRepayAmount", repaymentAmount);//应还总金额
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);
//			rsResponse.setMessage("获取还款计划成功");
//			rsResponse.setResponse(JSON.toJSONString(mapDatas));
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("请求异常");
//		}
//		return rsResponse;
//	}
//	
//	public RsResponse queryFk(long sessionId, Map<String,String> mapData){
//		RsResponse rsResponse = new RsResponse();
//		try {
//			String thirdOrderNo = mapData.get("orderId");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单编号为空");
//				return rsResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单不存在");
//				return rsResponse;
//			}
//			if (bwOrder.getStatusId() != 9 && bwOrder.getStatusId() != 6 && bwOrder.getStatusId() !=13 ) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("放款结果查询接口还未放款");
//				logger.info("结束放款结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("用户不存在");
//				return rsResponse;
//			}
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard =  bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("银行卡不存在");
//				return rsResponse;
//			}
//			
//			List<BwRepaymentPlan> list = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//			if (CollectionUtils.isEmpty(list)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("还款计划不存在");
//				return rsResponse;
//			}
//			Double borrowAmount = bwOrder.getBorrowAmount();// 审批金额
//			Map<String,Object> mapDatas = new HashMap<String, Object>();
//			mapDatas.put("contractId", bwOrder.getOrderNo());
//			mapDatas.put("status", "7001");
//			mapDatas.put("remark", "放款成功");
//			mapDatas.put("loanAmount", borrowAmount);//放款金额(到账金额)
//			mapDatas.put("receiveAmount", borrowAmount);//(到账金额)
//			
//			List<Map<String, Object>> planList = new ArrayList<>();
//			Map<String, Object> map = null;
//			double repaymentAmount = 0d;
//			double repayPenaltyInt = 0d;
//			for (BwRepaymentPlan bwRepaymentPlan : list) {
//				
//				map = new HashMap<>();
//				map.put("periodNo", bwRepaymentPlan.getNumber());// 还款周期编号
//				map.put("payType",4);// 还款类型1.自动代扣; 2.主动扣款; 3.主动支付; 4.1+2; 5.1+3; 6.2+3.
//				//map.put("interest_amount", bwRepaymentPlan.getRepayAccrualMoney());// 已还金额
//
//				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//				bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//				bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//				if (null != bwOverdueRecord) {
//						double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//								: bwOverdueRecord.getOverdueAccrualMoney();
//						double advance = bwOverdueRecord.getAdvance() == null ? 0D
//								: bwOverdueRecord.getAdvance();
//						repayPenaltyInt = overdueAccrualMoney - advance;
//						
//					//map.put("overdue_fee", bwOverdueRecord.getAdvance());// 逾期费用
//				} /*else {
//					map.put("overdue_fee", 0);// 逾期费用
//				}*/
//				map.put("amount", bwRepaymentPlan.getRealityRepayMoney()+repayPenaltyInt);// 当期金额
//				map.put("repayAmount", bwRepaymentPlan.getRealityRepayMoney()+repayPenaltyInt);// 应还金额
//				// 应还总额
//				repaymentAmount = repaymentAmount + bwRepaymentPlan.getRealityRepayMoney()
//						+ repayPenaltyInt;
//				//map.put("status", bwRepaymentPlan.getRepayStatus());// 账单状态 1 未到期 2 已还款 3 逾期
//				map.put("dueTime", bwRepaymentPlan.getRepayTime().getTime());// 到期时间
//				map.put("canRepayTime", new Date().getTime());// 可以还款的日期(是不是需要减一天)
//				/*if ("2".equals(bwRepaymentPlan.getRepayStatus() + "")) {
//					map.put("successTime",bwRepaymentPlan.getUpdateTime().getTime());// 成功还款时间
//				}*/
//				planList.add(map);
//			}
//			mapDatas.put("repayPlan", JSON.toJSONString(planList));
//			mapDatas.put("repayAmount", repaymentAmount);//应还总金额
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);
//			rsResponse.setMessage("获取放款结果成功");
//			rsResponse.setResponse(JSON.toJSONString(mapDatas));
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("请求异常");
//		}
//		return rsResponse;
//	}
//	
//	/*public static void main(String[] args) {
//		Map<String,String> map = new HashMap();
//		map.put("period", "1");
//		map.put("thirdOrderNo","1234235736");
//	    new RongShuNewServiceImpl().getPaymentCell(11111l, map);
//	}*/
//	public RsResponse getPaymentCell(long sessionId, Map<String,String> mapData){
//		RsResponse rsResponse = new RsResponse();
//		try {
//			String thirdOrderNo = mapData.get("orderId");
//			String period = mapData.get("period");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单编号为空");
//				return rsResponse;
//			}
//			if (StringUtils.isBlank(period)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("期数为空");
//				return rsResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单不存在");
//				return rsResponse;
//			}
//			
//		    if("-1".equals(period)){
//				BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
//				bwRepaymentPlan.setOrderId(bwOrder.getId());
//				List<BwRepaymentPlan> list = bwRepaymentPlanService.findBwRepaymentPlanByRepay(bwRepaymentPlan);
//				if (null==list || list.size()==0) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("还款计划不存在");
//					return rsResponse;
//				}
//				for(BwRepaymentPlan RepaymentPlan:list){
//					if(RepaymentPlan.getRepayStatus()!=2){
//						rsResponse.setCode(RsResponse.CODE_FAILERR);
//						rsResponse.setMessage("第"+RepaymentPlan.getNumber()+"期还未还清");
//						return rsResponse;
//					}
//				}
//				rsResponse.setCode(RsResponse.CODE_SUCCESS);
//				rsResponse.setMessage("已全部还清");
//				return rsResponse;
//			}
//			
//			/*if (bwOrder.getStatusId() != 9) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("放款结果查询接口还未放款");
//				logger.info("结束放款结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}*/
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("用户不存在");
//				return rsResponse;
//			}
//			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
//			bwRepaymentPlan.setOrderId(bwOrder.getId());
//			bwRepaymentPlan.setNumber(Integer.parseInt(period));
//			bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
//			if (null==bwRepaymentPlan) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("还款计划不存在");
//				return rsResponse;
//			}
//			double repaymentAmount = 0d;
//			double repayPenaltyInt = 0d;
//			double alreadyRepayMoney = 0d;
//			Map<String, Object> map = new HashMap<String,Object>();
//			map.put("loanAmount", bwRepaymentPlan.getRepayCorpusMoney());//当期借款金额
//			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//			bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//			int OverdueDay =0;
//			//查询是否有逾期
//			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//			if (null != bwOverdueRecord) {
//					double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//							: bwOverdueRecord.getOverdueAccrualMoney();
//					double advance = bwOverdueRecord.getAdvance() == null ? 0D
//							: bwOverdueRecord.getAdvance();
//					repayPenaltyInt = overdueAccrualMoney - advance;
//					OverdueDay = bwOverdueRecord.getOverdueDay();
//			}
//			alreadyRepayMoney = bwRepaymentPlan.getAlreadyRepayMoney();//已还金额
//			
//			repaymentAmount = bwRepaymentPlan.getRealityRepayMoney()
//			+ repayPenaltyInt - alreadyRepayMoney;//本期应还金额
//			map.put("repayAmount", repaymentAmount);//应还金额
//			map.put("overdueFee", repayPenaltyInt);//逾期金额 =逾期金额-免罚息
//	        map.put("remark", "应还"+repaymentAmount+"元，包含"+OverdueDay+"天逾期费共计"+repayPenaltyInt+"元。");			
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);
//			rsResponse.setMessage("获取主动还款试算成功");
//			rsResponse.setResponse(JSON.toJSONString(map));
//		} catch (Exception e) {
//			logger.info("请求异常:"+e);
//			logger.error("请求异常" + e.getMessage());
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("请求异常");
//		}
//		return rsResponse;
//	}
//	
//	
//	/**
//	 * 主动还款
//	 */
//	@Override
//	public RsResponse updateRepayment(long sessionId, Map<String,String> mapData) {
//		RsResponse rsResponse = new RsResponse();
//		try {
//			String errorMsg = "还款失败";
//			String thirdOrderNo = mapData.get("orderId");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单编号为空");
//				return rsResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("未查询到订单");
//				return rsResponse;
//			}
//			
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//			Map<String, String> map = new HashMap<>();
//			if (null != drainageRsp) {
//				if ("000".equals(drainageRsp.getCode())) {
//					rsResponse.setCode(RsResponse.CODE_SUCCESS);
//					rsResponse.setMessage("还款申请成功");
//				} else {
//					rsResponse.setCode(RsResponse.CODE_FAILERR);
//					errorMsg = drainageRsp.getMessage();
//					rsResponse.setMessage("还款申请失败,原因:"+errorMsg);				
//					return rsResponse;
//				}
//			} else {
//				rsResponse.setCode(RsResponse.CODE_FAILERR);
//				rsResponse.setMessage("还款申请失败,原因:返回为空");				
//				return rsResponse;
//			}
//			
//			map.put("status", "5001");// 还款状态8001成功8003失败
//			rsResponse.setResponse(JSON.toJSONString(map));
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("请求异常");
//		}
//		return rsResponse;
//
//	}
//
//	
//	@Override
//	public RsResponse getRepaymentResult(long sessionId, Map<String, String> mapData) {
//		RsResponse rsResponse = new RsResponse();
//		try {
//			String thirdOrderNo = mapData.get("orderId");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单编号为空");
//				return rsResponse;
//			}
//			String period = mapData.get("period");
//			if (StringUtils.isBlank(period)) {
//				//没传就查第1期
//				period = "1";
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单不存在");
//				return rsResponse;
//			}
//			/*if (bwOrder.getStatusId() != 9) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("还款结果查询接口还未放款");
//				logger.info("结束还款结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}*/
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("用户不存在");
//				return rsResponse;
//			}
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard =  bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("银行卡不存在");
//				return rsResponse;
//			}
//			
//			double alreadyRepayAmount = 0d;//已还金额
//			double willRepayAmount = 0d;//未还金额
//			double repayPenaltyInt = 0d;
//			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
//			bwRepaymentPlan.setOrderId(bwOrder.getId());
//			bwRepaymentPlan.setNumber(Integer.parseInt(period));
//			bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
//			if (null==bwRepaymentPlan) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("还款计划不存在");
//				return rsResponse;
//			}
//			Map<String, Object> map = new HashMap<String, Object>();
//			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//			bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//			if (null != bwOverdueRecord) {
//					double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//							: bwOverdueRecord.getOverdueAccrualMoney();
//					double advance = bwOverdueRecord.getAdvance() == null ? 0D
//							: bwOverdueRecord.getAdvance();
//					repayPenaltyInt = overdueAccrualMoney - advance;
//			}
//	
//			if(2==bwRepaymentPlan.getRepayStatus()){
//				alreadyRepayAmount = bwRepaymentPlan.getRealityRepayMoney()+repayPenaltyInt;
//				map.put("status", "8001");//当期已还清
//			}else{
//				double alreadyRepayMoney = bwRepaymentPlan.getAlreadyRepayMoney();
//				if(alreadyRepayMoney>0){		
//					map.put("status", "8002");//部分还款
//					//状态非等于2，而真实还款金额又大于0，那么就是在前端部分还款的
//					alreadyRepayAmount = alreadyRepayMoney;
//					willRepayAmount = bwRepaymentPlan.getRealityRepayMoney()+repayPenaltyInt-alreadyRepayMoney;
//				}else{	
//				map.put("status", "8006");
//				willRepayAmount = bwRepaymentPlan.getRealityRepayMoney()+repayPenaltyInt;
//				
//				}
//			}
//
//			map.put("alreadyRepayAmount", alreadyRepayAmount);
//			map.put("willRepayAmount", willRepayAmount);
//			map.put("repayPeriod", period);
//			map.put("remark", "已还"+alreadyRepayAmount+"元，未还"+willRepayAmount+"元，当前第"+period+"期。");
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);
//			rsResponse.setMessage("获取还款结果成功");
//			rsResponse.setResponse(JSON.toJSONString(map));
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("请求异常");
//		}
//		return rsResponse;
//	}
//	
//	
//	/**
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.XianJinBaiKaService#getContracts(long,
//	 *      com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaCommonRequest)
//	 */
//	@Override
//	public RsResponse getContracts(long sessionId, Map<String,String> mapData) {
//		logger.info(sessionId + "：开始榕树合同接口 method：" + JSON.toJSONString(mapData));
//		RsResponse rsResponse = new RsResponse();
//		try {
//			String thirdOrderNo = mapData.get("orderId");
//			Map<String, String> contractUrls = null;
//			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//			if (StringUtils.isNotBlank(thirdOrderNo)) {
//				BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//				if (!CommUtils.isNull(bwOrderRong)) {
//					Long orderId = bwOrderRong.getOrderId();
//					List<BwAdjunct> bwAdjunctList = bwAdjunctService.findBwAdjunctByOrderId(orderId);
//					if (!CommUtils.isNull(bwAdjunctList)) {
//						for (BwAdjunct bwAdjunct : bwAdjunctList) {
//							if (bwAdjunct.getAdjunctType() == 29) {
//								contractUrls = new HashMap<>();
//								String adjunctPath = bwAdjunct.getAdjunctPath();
//								String conUrl = SystemConstant.PDF_URL + adjunctPath;
//								contractUrls.put("小微金融水象分期信息咨询及信用管理服务合同",conUrl);
//								list.add(contractUrls);
//							}
//							if (bwAdjunct.getAdjunctType() == 30) {
//								contractUrls = new HashMap<>();
//								String adjunctPath = bwAdjunct.getAdjunctPath();
//								String conUrl = SystemConstant.PDF_URL + adjunctPath;
//								contractUrls.put("征信及信息披露授权书",conUrl);
//								list.add(contractUrls);
//							}
//						}
//					}
//				}
//			}
//
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);
//			rsResponse.setMessage("success");
//			rsResponse.setResponse(JSON.toJSONString(list));
//		} catch (Exception e) {
//			logger.error(sessionId + "：执行榕树合同接口 异常：", e);
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("请求失败");
//		}
//		logger.info(sessionId + "：结束榕树合同接口：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//	}
//	
//
//	/*public static void main(String[] args) {
//		RsResponse rs = new RongShuNewServiceImpl().getAddressList(12112121L,11151);
//		Map<String,Map<String,Object>> map = JSON.parseObject(rs.getResponse(),Map.class);
//		Object object = map.get("userInfo").get("contact");
//		List<Map<String,Object>> list = JSON.parseObject(object.toString(), List.class);
//		Object obj = list.get(0).get("names");
//		JSONArray str = (JSONArray)obj;
//		System.out.println(str.get(0));
//		String appId = RongShuConstant.APPID;
//		String url = RongShuConstant.URL+"api/v2/data/device.do";
//		Map<String,String> mapRequest = new HashMap<String, String>();
//		mapRequest.put("uid", "11151");
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("appId",appId);
//		map.put("request", JSON.toJSONString(mapRequest));
//		map.put("timestamp", String.valueOf(System.currentTimeMillis()));
//		String sign = RongShuUtils.RSAEncrypt(map, RongShuConstant.PRIKEY);
//		Pair<Integer, String> data = HttpsUtil.postByJson(url, JSON.toJSONString(map), null);
//		System.out.println(data);
//	}*/
//
//	@Override
//	public RsResponse confirmLoan(long sessionId, Map<String, String> mapData) {
//		RsResponse rsResponse = new RsResponse();
//		try {
//			String order_no = mapData.get("orderId");//三方订单号
//			if (StringUtils.isBlank(order_no)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单编号为空");
//				return rsResponse;
//			}
//			
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(order_no);
//			if (null == bwOrder) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("订单不存在");
//				return rsResponse;
//			}
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("用户不存在");
//				return rsResponse;
//			}
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard =  bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("银行卡不存在");
//				return rsResponse;
//			}
//			String successUrl = mapData.get("successUrl");
//			String errorUrl = mapData.get("failedUrl");
//			String platform = mapData.get("platform");
//			String phone = borrower.getPhone();
//			if (StringUtils.isBlank(successUrl)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("successUrl为空");
//				return rsResponse;
//			}
//			if (StringUtils.isBlank(errorUrl)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("failedUrl为空");
//				return rsResponse;
//			}
//			if (StringUtils.isBlank(platform)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("platform为空");
//				return rsResponse;
//			}
//			if (StringUtils.isBlank(phone)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("phone为空");
//				return rsResponse;
//			}
//			String params = "phone="+phone+"&order_no="+order_no;
//			params = RongShuUtils.getMD5(params);
//			String url = RongShuConstant.confirmURL.trim()+"?phone="+phone+"&order_no="+order_no+"&platform="+platform+"&params="+params+"&returnUrl="+errorUrl;
//			
//		    Map<String,Object> map = new HashMap<>();
//		    map.put("url", url);
//            map.put("status", "4001");
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);	
//			rsResponse.setMessage("请求成功");
//			rsResponse.setResponse(JSON.toJSONString(map));
//			Map<String,String> mapUrl = new HashMap<String, String>();
//			mapUrl.put("successUrl", successUrl);
//			mapUrl.put("errorUrl", errorUrl);
//			RedisUtils.lpush("orderSuccOrErrUrl:orderId:" +bwOrder.getId() , JSON.toJSONString(mapUrl));
//	}catch (Exception e) {
//		logger.error(sessionId + "：执行榕树确认订单接口 异常：", e);
//		rsResponse.setCode(RsResponse.CODE_FAILERR);
//		rsResponse.setMessage("请求失败");
//	}
//        return rsResponse;
//	}
//	
//	
//	
//}
