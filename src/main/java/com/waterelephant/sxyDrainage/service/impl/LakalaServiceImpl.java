//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
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
//import com.waterelephant.entity.BwProductDictionary;
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
//import com.waterelephant.sxyDrainage.entity.lakala.LklRespData;
//import com.waterelephant.sxyDrainage.entity.lakala.LklResponse;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.LakalaService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.lakala.LakalaConstant;
//import com.waterelephant.sxyDrainage.utils.lakala.LakalaUtils;
//import com.waterelephant.sxyDrainage.utils.rongshu.RongShuConstant;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.third.utils.ThirdUtil;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//
//import tk.mybatis.mapper.entity.Example;
//
//@Service
//public class LakalaServiceImpl implements LakalaService {
//	public static final String CHANNELID = LakalaConstant.channelId;
//	private Logger logger = Logger.getLogger(LakalaServiceImpl.class);
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
//	public LklRespData checkUser(Long sessionId, String user_name, String user_mobile, String id_card) {
//		logger.info(" 开始LakalaServiceImpl.checkUser()方法{user_name=" + user_name + ",user_mobile=" + user_mobile
//				+ ",id_card=" + id_card + "}");
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		if(StringUtils.isBlank(user_name)){
//			lklResponse.setCode(LklResponse.CODE_PARMERR);
//			lklResponse.setDesc("用户名不能为空");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info("系统异常,用户名为空");
//			return lklRespData;
//		}
//		if(StringUtils.isBlank(user_mobile)){
//			lklResponse.setCode(LklResponse.CODE_PARMERR); 
//			lklResponse.setDesc("电话号码为空");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info("系统异常,电话号码为空");
//			return lklRespData;
//		}
//		if(StringUtils.isBlank(id_card)){
//			lklResponse.setCode(LklResponse.CODE_PARMERR);
//			lklResponse.setDesc("身份证号码为空");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info("系统异常,身份证号码为空");
//			return lklRespData;
//		}
//		
//		/**
//		 * 新增的判断用户多个手机号贷款问题
//		 */
//		boolean flag = false;
//		try {
//			flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, id_card, user_name);
//		} catch (Exception e) {
//			logger.info("拉卡拉效验调用checkUserAccountProgressOrder方法异常:"+e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("拉卡拉效验调用验重方法异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//	      if (flag) {
//	    	lklResponse.setCode(LklResponse.CODE_FAILERR);
//		    lklResponse.setDesc("存在进行中的订单，请勿重复推送");
//		    lklRespData.setHead(JSON.toJSONString(lklResponse));
//	        logger.info(sessionId + "：结束拉卡拉service层订单推送接口：" + JSON.toJSONString(lklResponse));
//	        return lklRespData;
//	      }
//		
//		DrainageRsp checkUser = commonService.checkUser(sessionId, user_name, user_mobile, id_card);
//		// 获取公共接口返回的信息判断给三方返回的数据
//		String drainageRspCode = checkUser.getCode();
//		logger.info(JSONObject.toJSONString("checkUser检查用户接口返回code为：" + drainageRspCode));
//		// 返回数据
//		// 验证数据是否为空
//		if (drainageRspCode.equals("1002")) {
//			lklResponse.setCode(LklResponse.CODE_PARMERR);
//			lklResponse.setDesc(checkUser.getMessage());
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info("系统异常,用户" + user_name + ":" + checkUser.getMessage());
//			return lklRespData;
//
//		}
//		// 验证是否符合申请要求
//		if (drainageRspCode.equals("2001") || drainageRspCode.equals("2002") || drainageRspCode.equals("2003")
//				|| drainageRspCode.equals("2004")) {
//			lklResponse.setCode(drainageRspCode);
//			lklResponse.setDesc(checkUser.getMessage());
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info(user_name + "不可申请," + checkUser.getMessage());
//			return lklRespData;
//		}
//		if("0000".equals(drainageRspCode)){
//		lklResponse.setCode(LklResponse.CODE_SUCCESS);
//		lklResponse.setDesc("该用户允许通过");
//		lklRespData.setHead(JSON.toJSONString(lklResponse));
//		logger.info("检查用户接口返回数据为：" + JSONObject.toJSONString(lklResponse));
//		}else{
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("该用户不允许通过");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			logger.info("检查用户接口返回数据为：" + JSONObject.toJSONString(lklRespData));
//		}
//
//		return lklRespData;
//	}
//
//	/**
//	 * 进件推送
//	 */
//	@Override
//	public LklRespData savePushOrder(Long sessionId, Map<String,Object> map) {
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		Map<String,Object> mapResp = new HashMap<String,Object>();
//		logger.info(sessionId + ":拉卡拉service进件推送接口map>>>"+JSON.toJSONString(map));
//		try {
//
//			String userName = String.valueOf(map.get("userName"));
//			String idCard = String.valueOf(map.get("idNo"));
//			String phone = String.valueOf(map.get("userMobile"));//电话号码
//			Integer channelId = Integer.parseInt(LakalaConstant.channelId);//渠道号
//			String thirdOrderNo = String.valueOf(map.get("applySerial"));//三方订单号
//			String applyAmt = String.valueOf(map.get("applyAmt"));//申请金额
//			String passworde = null;
//			if (StringUtils.isBlank(userName)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("姓名为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			if (StringUtils.isBlank(idCard)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("身份证号码为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			if (StringUtils.isBlank(phone)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("手机号为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			if (CommUtils.isNull(channelId)) {
//				logger.info(sessionId + " 结束RongShuNewServiceImpl.savePushOrder()方法，返回结果：渠道编码为空");
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("渠道编码为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				logger.info(sessionId + " 结束RongShuNewServiceImpl.savePushOrder()方法，返回结果:三方订单号为空");
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("三方订单号为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			
//			// 新增或更新借款人
//			BwBorrower borrower = addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId, passworde);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//			
//			/**
//			 * 新增的判断用户多个手机号贷款问题
//			 */
//			boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//		      if (flag) {
//		    	lklResponse.setCode(LklResponse.CODE_FAILERR);
//			    lklResponse.setDesc("存在进行中的订单，请勿重复推送");
//			    mapResp.put("status", "2002");
//				mapResp.put("remark", "存在进行中的订单，请勿重复推送");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				lklRespData.setResponse(JSON.toJSONString(mapResp));
//		        logger.info(sessionId + "：结束拉卡拉service层订单推送接口：" + JSON.toJSONString(lklResponse));
//		        return lklRespData;
//		      }
//			
//			// 判断是否有草稿状态的订单
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setBorrowerId(borrowerId);
//			bwOrder.setStatusId(1L);
//			bwOrder.setProductType(2);
//			bwOrder.setChannel(channelId);
//			bwOrder.setProductId(7);
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
//			bwOrder.setStatusId(8L);
//			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
//			boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//			if (boList != null && boList.size() > 0) {
//				bwOrder = boList.get(boList.size() - 1);
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setProductType(2);
//				bwOrder.setStatusId(1L);
//				bwOrder.setExpectMoney(Double.parseDouble(applyAmt));//预借金额
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
//				bwOrder.setProductId(7);
//				bwOrder.setProductType(2);
//				bwOrder.setRepayType(2);
//				bwOrder.setExpectMoney(Double.parseDouble(applyAmt));
//				bwOrder.setExpectNumber(4);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//			logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//			
//			// 判断是否有订单
//			BwOrderRong bwOrderRong = new BwOrderRong();
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
//			if (null == bwWorkInfo) {
//				bwWorkInfo = new BwWorkInfo();
//				bwWorkInfo.setOrderId(orderId);
//				//bwWorkInfo.setIncome(String.valueOf(objectExt.get("income")));//收入
//				bwWorkInfo.setComName(String.valueOf(map.get("unitName")));//公司名称
//				bwWorkInfo.setIndustry("未知");//行业
//				//bwWorkInfo.setWorkYears();//年份
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//				bwWorkInfoService.save(bwWorkInfo, borrowerId);
//			} else {
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				//bwWorkInfo.setIncome(String.valueOf(objectExt.get("income")));
//				bwWorkInfo.setComName(String.valueOf(map.get("unitName")));
//				bwWorkInfo.setIndustry("未知");
//				//bwWorkInfo.setWorkYears(RongShuUtils.getWorkYear(String.valueOf(objectExt.get("jobTime"))));
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
//				bwIdentityCard.setAddress(String.valueOf(map.get("livingAddr")));//居住地址
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(userName);
//				//bwIdentityCard.setIssuedBy(sxyIdentifyInfo.getIssuedBy());// 签发机关
//				//bwIdentityCard.setRace(sxyIdentifyInfo.getNation());//名族
//				//bwIdentityCard.setValidDate(sxyIdentifyInfo.getValidDate());//身份证有效期
//				bwIdentityCard.setBorrowerId(borrowerId);
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//			} else {
//				bwIdentityCard.setAddress(String.valueOf(map.get("livingAddr")));
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(userName);
//				//bwIdentityCard.setIssuedBy(sxyIdentifyInfo.getIssuedBy());
//				//bwIdentityCard.setRace(sxyIdentifyInfo.getNation());
//				//bwIdentityCard.setValidDate(sxyIdentifyInfo.getValidDate());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);// 插入身份认证记录
//			logger.info(sessionId + ">>> 处理身份证信息结束");
//			
//			// 认证图片
//			String frontFile = String.valueOf(map.get("certpicUrlIf"));
//			String backFile = String.valueOf(map.get("certpicUrlIc"));
//			String natureFile = String.valueOf(map.get("intravitalUrl"));
//			if (StringUtils.isNotBlank(frontFile)) {
//				String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01"); // 身份证正面照
//				logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId,
//						borrowerId, 0); // 保存身份证正面照
//				logger.info(sessionId + ">>> 身份证正面保存 " + result);
//			}
//			if (StringUtils.isNotBlank(backFile)) {
//				String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02"); // 身份证反面照
//				logger.info(sessionId + ">>> 身份证反面 " + backImage);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId,
//						borrowerId, 0); // 保存身份证反面照
//				logger.info(sessionId + ">>> 身份证反面保存 " + result);
//			}
//			if (StringUtils.isNotBlank(natureFile)) {
//				String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03"); // 手持照
//				logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId,
//						borrowerId, 0); // 保存手持照
//				logger.info(sessionId + ">>> 手持照/人脸保存 " + result);
//			}
//	
//			// 亲属联系人
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			if (bwPersonInfo == null) {
//				bwPersonInfo = new BwPersonInfo();
//				bwPersonInfo.setAddress(String.valueOf(map.get("livingAddr")));
//				bwPersonInfo.setOrderId(orderId);
//				bwPersonInfo.setCityName(String.valueOf(map.get("livingCity")));
//				//bwPersonInfo.setEmail(kaNiuPushUserReq.getEmail());
//				bwPersonInfo.setRelationName(String.valueOf(map.get("relativesName")));
//				bwPersonInfo.setRelationPhone(String.valueOf(map.get("relativesMobile")));
//				bwPersonInfo.setUnrelationName(String.valueOf(map.get("contactName")));
//				bwPersonInfo.setUnrelationPhone(String.valueOf(map.get("contactMobile")));
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setUpdateTime(new Date());
//				/*bwPersonInfo.setColleagueName(kaNiuPushUserReq.getContactsCname());
//				bwPersonInfo.setColleaguePhone(kaNiuPushUserReq.getContactsCphone());
//				bwPersonInfo.setFriend1Name(kaNiuPushUserReq.getContactsDname());
//				bwPersonInfo.setFriend1Phone(kaNiuPushUserReq.getContactsDphone());
//				bwPersonInfo.setFriend2Name(kaNiuPushUserReq.getContactsEname());
//				bwPersonInfo.setFriend2Phone(kaNiuPushUserReq.getContactsEphone());
//				bwPersonInfo.setQqchat(kaNiuPushUserReq.getQq());
//				bwPersonInfo.setWechat(kaNiuPushUserReq.getWeChat());
//				bwPersonInfo.setMarryStatus(KaNiuUtil.toMarryStatus(kaNiuPushUserReq.getMarriageInfo()));*/
//				bwPersonInfoService.add(bwPersonInfo);
//			} else {
//				bwPersonInfo.setCityName(String.valueOf(map.get("livingCity")));
//				//bwPersonInfo.setEmail(kaNiuPushUserReq.getEmail());
//				bwPersonInfo.setRelationName(String.valueOf(map.get("relativesName")));
//				bwPersonInfo.setRelationPhone(String.valueOf(map.get("relativesMobile")));
//				bwPersonInfo.setUnrelationName(String.valueOf(map.get("contactName")));
//				bwPersonInfo.setUnrelationPhone(String.valueOf(map.get("contactMobile")));
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setUpdateTime(new Date());
//				/*bwPersonInfo.setColleagueName(kaNiuPushUserReq.getContactsCname());
//				bwPersonInfo.setColleaguePhone(kaNiuPushUserReq.getContactsCphone());
//				bwPersonInfo.setFriend1Name(kaNiuPushUserReq.getContactsDname());
//				bwPersonInfo.setFriend1Phone(kaNiuPushUserReq.getContactsDphone());
//				bwPersonInfo.setFriend2Name(kaNiuPushUserReq.getContactsEname());
//				bwPersonInfo.setFriend2Phone(kaNiuPushUserReq.getContactsEphone());
//				bwPersonInfo.setQqchat(kaNiuPushUserReq.getQq());
//				bwPersonInfo.setWechat(kaNiuPushUserReq.getWeChat());
//				bwPersonInfo.setMarryStatus(KaNiuUtil.toMarryStatus(kaNiuPushUserReq.getMarriageInfo()));*/
//				bwPersonInfoService.update(bwPersonInfo);
//			}
//			logger.info(sessionId + ">>> 处理亲属联系人信息");
//		
//			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//			bwOrderProcessRecord.setSubmitTime(new Date());
//			bwOrderProcessRecord.setOrderId(bwOrder.getId());
//			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//			
//            Map<String,Object> mapData = new HashMap<String,Object>();
//            map.put("status", "2001");
//            map.put("orderId", orderId);
//            map.put("remark", "进件初始化成功");
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("进件初始化成功");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			lklRespData.setResponse(JSON.toJSONString(map));
//            /*Map<String,Object> mapParams = new HashMap<String,Object>();
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
//			RedisUtils.lpush("tripartite:addParams:" + channelId, JSON.toJSONString(mapParams));*/
//			return lklRespData;
//		} catch (Exception e) {
//			logger.error(sessionId + "执行进件推送接口异常", e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("内部错误");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//		return lklRespData;
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
//		//LklResponse addressData= new RongShuNewServiceImpl().getAddressList(1111l, 3931041);
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
//	public LklRespData getApprovalResult(long sessionId, Map<String,String> mapData) {
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			Integer channelId = Integer.valueOf(RongShuConstant.CHANNELID);
//			String thirdOrderNo = mapData.get("orderId");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("手机号码为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			
//			BwOrderRong bwRong = new BwOrderRong();
//			bwRong.setThirdOrderNo(thirdOrderNo);
//			bwRong.setChannelId(Long.parseLong(CHANNELID));
//			bwRong = bwOrderRongService.findBwOrderRongByAttr(bwRong);
//			if (CommUtils.isNull(bwRong)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("工单不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉查询审核结果接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setId(bwRong.getOrderId());
//			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//			if (CommUtils.isNull(bwOrder)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("工单不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉查询审核结果接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			logger.info("审核结果查询接口borrowerId="+bwOrder.getBorrowerId());
//			BwBorrower bwBorrower = new BwBorrower();
//			bwBorrower.setId(bwOrder.getBorrowerId());
//			bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//			if (CommUtils.isNull(bwBorrower)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("用户表为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info(sessionId + "结束拉卡拉查询审核结果接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklRespData;
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
//				lklResponse.setCode(LklResponse.CODE_SUCCESS);
//				lklResponse.setDesc("返回成功");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				lklRespData.setResponse(JSON.toJSONString(map));
//                return lklRespData;
//			} else {
//				if(7 == statusId || 8 == statusId){
//					map.put("status", 6004);
//					map.put("remark", "审核失败");
//					lklResponse.setCode(LklResponse.CODE_SUCCESS);
//					lklResponse.setDesc("审核失败");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					lklRespData.setResponse(JSON.toJSONString(map));
//	                return lklRespData;
//				}
//				map.put("status", 6003);
//				map.put("remark", "审核中");
//				lklResponse.setCode(LklResponse.CODE_SUCCESS);
//				lklResponse.setDesc("返回成功");
//				lklRespData.setResponse(JSON.toJSONString(map));
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//                return lklRespData;
//			}
//
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("请求异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//		return lklRespData;
//	}
//	
//	
//	/**
//	 * 获取还款计划接口
//	 */
//	@Override
//	public LklRespData getRepayPlan(long sessionId, Map<String,String> mapData) {
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			String thirdOrderNo = mapData.get("applySerial");
//			//String applySerial = mapData.get("applySerial");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单编号为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("用户不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard =  bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("银行卡不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			
//			List<BwRepaymentPlan> list = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//			if (CollectionUtils.isEmpty(list)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("还款计划不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
//			Double borrowAmount = bwOrder.getBorrowAmount();// 审批金额
//			Map<String,Object> mapDatas = new HashMap<String, Object>();
//			mapDatas.put("applySerial", thirdOrderNo);
//			mapDatas.put("dueBillNo", bwOrder.getId());
//			mapDatas.put("acctNo", bwBankCard.getCardNo());
//			mapDatas.put("productNo", 7);
//			mapDatas.put("productName", "水象云28天");
//			mapDatas.put("loanTerm", 4);
//			int currTerm = 1;
//			String lastPayDate = "";//当前到期还款日
//			double yesCurrentAmt = 0d;//本期已还金额
//			double repayPenaltyInt = 0d;//逾期费用
//			double noCurrentAmt =0d;//本期未还金额
//			double hisUnpaidAmt = 0d;//往期未还金额
//			int i = 0;
//			for(BwRepaymentPlan bwRepaymentPlan : list){
//				if(bwRepaymentPlan.getRepayStatus()==2 && i==0){
//					currTerm = bwRepaymentPlan.getNumber();
//					lastPayDate = sdf2.format(bwRepaymentPlan.getRepayTime());
//					yesCurrentAmt = bwRepaymentPlan.getAlreadyRepayMoney();
//					noCurrentAmt = 0;
//					break;
//				}
//				if(bwRepaymentPlan.getRepayStatus()==1){
//					
//				currTerm = bwRepaymentPlan.getNumber();
//				lastPayDate = sdf2.format(bwRepaymentPlan.getRepayTime());
//				yesCurrentAmt = bwRepaymentPlan.getAlreadyRepayMoney();
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
//				}
//				noCurrentAmt = bwRepaymentPlan.getRealityRepayMoney() + repayPenaltyInt - yesCurrentAmt;
//				hisUnpaidAmt = noCurrentAmt;//本期就是往期
//				}
//				i++;
//			}
//			mapDatas.put("currTerm", currTerm);//当前期数
//			mapDatas.put("cycleType", "D");
//			mapDatas.put("applyAmt", borrowAmount*100);
//			mapDatas.put("capitalLimit", borrowAmount*100);
//			mapDatas.put("capitalLoaned", borrowAmount*100);
//			mapDatas.put("applyDate", sdf.format(bwOrder.getCreateTime()));
//			mapDatas.put("loanDate", sdf.format(list.get(0).getCreateTime()));
//			mapDatas.put("startDate", sdf2.format(list.get(0).getCreateTime()));
//			mapDatas.put("dueDate", sdf2.format(list.get(0).getRepayTime()));
//			mapDatas.put("contractStatus", LakalaUtils.getOrderStatus(bwOrder.getStatusId()));
//			
//			double actualRepayAmt = 0d;//已还总额
//			double repayPenalty = 0d;//全部已还款金额
//			double settleAmt = 0d;//提前结清金额
//			double unsettled = 0d;//剩余还款金额
//			List listReplayPlan = new ArrayList();
//			Map<String,Object> mapReplayPlan = null;
//			
//			List scheduleList = null;
//			Map<String,Object> scheduleMap = null;
//			
//			for (BwRepaymentPlan bwRepaymentPlan : list) {
//				
//				mapReplayPlan = new HashMap<String, Object>();
//				
//				mapReplayPlan.put("term", bwRepaymentPlan.getNumber());//期数
//				mapReplayPlan.put("dueDate", sdf2.format(bwRepaymentPlan.getRepayTime()));
//				if(2!=bwRepaymentPlan.getRepayStatus()){
//				mapReplayPlan.put("repaymentAvailable", 1);//只要状态非等于2都可还款
//				}else{
//					mapReplayPlan.put("repaymentAvailable", 0);	
//				}
//				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//				bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//				bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//				if (null != bwOverdueRecord) {
//						double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//								: bwOverdueRecord.getOverdueAccrualMoney();
//						double advance = bwOverdueRecord.getAdvance() == null ? 0D
//								: bwOverdueRecord.getAdvance();
//						repayPenalty = overdueAccrualMoney - advance;
//						
//				}
//				if ("2".equals(bwRepaymentPlan.getRepayStatus() + "")) {
//					// 已还总额
//					actualRepayAmt = actualRepayAmt + bwRepaymentPlan.getRealityRepayMoney()
//							+ repayPenalty;
//				}
//				if("2".equals(bwRepaymentPlan.getRepayStatus()+"") && "3".equals(bwRepaymentPlan.getRepayType()+"")){
//					settleAmt = actualRepayAmt + bwRepaymentPlan.getRealityRepayMoney()
//					+ repayPenalty;//提前还款金额
//				}
//				if("1".equals(bwRepaymentPlan.getRepayStatus()+"")){
//					unsettled = unsettled + bwRepaymentPlan.getRealityRepayMoney()
//					+ repayPenalty;
//				}
//				hisUnpaidAmt = 0;
//				scheduleList = new ArrayList();
//				for(int k=0;k<3;k++){
//					scheduleMap = new HashMap<String, Object>();
//					if(k==0){
//					    scheduleMap.put("assetType","本金");
//					    scheduleMap.put("amtReceivable", bwRepaymentPlan.getRepayCorpusMoney());
//					}else if(k==1){
//						scheduleMap.put("assetType","利息");
//						scheduleMap.put("amtReceivable", bwRepaymentPlan.getRepayAccrualMoney());	
//					}else{
//						scheduleMap.put("assetType","逾期金额");
//						scheduleMap.put("amtReceivable", repayPenalty);	
//					}
//					scheduleList.add(scheduleMap); 
//				}				
//			    mapReplayPlan.put("scheduleList", scheduleList);
//				listReplayPlan.add(mapReplayPlan);
//			}
//			
//			//查询水象云产品
//			BwProductDictionary bwProductDictionary = bwProductDictionaryService
//								.findBwProductDictionaryById(Integer.valueOf(7));
//			Double interestRate = bwProductDictionary.getInterestRate();// 分期利息率
//			//mapDatas.put("repayPlan", JSON.toJSONString(planList));
//			mapDatas.put("unsettled", unsettled*100);//剩余还款金额
//			mapDatas.put("lastPayDate", lastPayDate);//当前到期还款日
//			mapDatas.put("yesCurrentAmt", yesCurrentAmt*100);// 已还金额
//			mapDatas.put("noCurrentAmt", noCurrentAmt*100);// 未还金额
//			mapDatas.put("actualRepayAmt", actualRepayAmt*100);//全部已还金额
//			mapDatas.put("hisUnpaidAmt", hisUnpaidAmt*100);
//			mapDatas.put("repaymentAvailable", 1);
//			mapDatas.put("settleAmt", settleAmt);
//			mapDatas.put("interestMode", 30);
//			mapDatas.put("interestRt", interestRate/7);
//			mapDatas.put("productList", listReplayPlan);
//			
//			
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("获取拉卡拉还款计划成功");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			lklRespData.setResponse(JSON.toJSONString(mapDatas));
//		} catch (Exception e) {
//			logger.error("请求异常" + e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("请求异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			return lklRespData;
//		}
//		return lklRespData;
//	}
//	
//	public LklRespData queryFk(long sessionId, Map<String,String> mapData){
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			String thirdOrderNo = mapData.get("orderId");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单编号为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			if (bwOrder.getStatusId() != 9 && bwOrder.getStatusId() != 6 && bwOrder.getStatusId() !=13 ) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("放款结果查询接口还未放款");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				logger.info("结束放款结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("用户不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard =  bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("银行卡不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			
//			List<BwRepaymentPlan> list = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//			if (CollectionUtils.isEmpty(list)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("还款计划不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
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
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("获取放款结果成功");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			lklRespData.setResponse(JSON.toJSONString(mapDatas));
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("请求异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//		return lklRespData;
//	}
//	
//	/*public static void main(String[] args) {
//		Map<String,String> map = new HashMap();
//		map.put("period", "1");
//		map.put("thirdOrderNo","1234235736");
//	    new RongShuNewServiceImpl().getPaymentCell(11111l, map);
//	}*/
//	public LklRespData getPaymentCell(long sessionId, Map<String,String> mapData){
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			String orderId = String.valueOf(mapData.get("contractNo"));
//			logger.info("获取拉卡拉订单编码orderId:"+orderId);
//			//String period = mapData.get("period");
//			if (StringUtils.isBlank(orderId)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单编号为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			/*if (StringUtils.isBlank(period)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("期数为空");
//				return lklRespData;
//			}*/
//			BwOrder bwOrder = bwOrderService.findBwOrderById(orderId);
//			if (null == bwOrder) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			
//		   /* if("-1".equals(period)){
//				BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
//				bwRepaymentPlan.setOrderId(bwOrder.getId());
//				List<BwRepaymentPlan> list = bwRepaymentPlanService.findBwRepaymentPlanByRepay(bwRepaymentPlan);
//				if (null==list || list.size()==0) {
//					lklResponse.setCode(LklResponse.CODE_PARMERR);
//					lklResponse.setDesc("还款计划不存在");
//					return lklRespData;
//				}
//				for(BwRepaymentPlan RepaymentPlan:list){
//					if(RepaymentPlan.getRepayStatus()!=2){
//						lklResponse.setCode(LklResponse.CODE_FAILERR);
//						lklResponse.setDesc("第"+RepaymentPlan.getNumber()+"期还未还清");
//						return lklRespData;
//					}
//				}
//				lklResponse.setCode(LklResponse.CODE_SUCCESS);
//				lklResponse.setDesc("已全部还清");
//				return lklRespData;
//			}*/
//			
//			/*if (bwOrder.getStatusId() != 9) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("放款结果查询接口还未放款");
//				logger.info("结束放款结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}*/
//			Map<String, Object> map = new HashMap<String,Object>();
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("用户不存在");
//				return lklRespData;
//			}
//			Example example = new Example(BwRepaymentPlan.class);
//			example.createCriteria().andEqualTo("orderId", orderId);
//			example.setOrderByClause("createTime asc");
//			List<BwRepaymentPlan> list = bwRepaymentPlanService.findRepaymentPlanByExample(example);
//			
//			if (CollectionUtils.isEmpty(list)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("还款计划不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			double repaymentAmount = 0d;
//			double repayPenaltyInt = 0d;
//			double alreadyRepayMoney = 0d;		
//			
//			for (BwRepaymentPlan bwRepaymentPlan : list) {
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
//			map.put("term", bwRepaymentPlan.getNumber());
//			map.put("minTxnAmt", repaymentAmount);//应还最小金额
//			map.put("maxTxnAmt", repaymentAmount);//应还最大金额		
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("获取主动还款试算成功");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			lklRespData.setResponse(JSON.toJSONString(map));
//			if(bwRepaymentPlan.getRepayStatus()==1){
//				break;
//			}
//			}
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("请求异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//		return lklRespData;
//	}
//	
//	
//	/**
//	 * 主动还款
//	 */
//	@Override
//	public LklRespData updateRepayment(long sessionId, Map<String,String> mapData) {
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			String errorMsg = "还款失败";
//			String thirdOrderNo = mapData.get("applySerial");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单编号为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("未查询到订单");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//			Map<String, String> map = new HashMap<>();
//			if (null != drainageRsp) {
//				if ("000".equals(drainageRsp.getCode())) {
//					lklResponse.setCode(LklResponse.CODE_SUCCESS);
//					lklResponse.setDesc("还款申请成功");
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//				} else {
//					lklResponse.setCode(LklResponse.CODE_FAILERR);
//					errorMsg = drainageRsp.getMessage();
//					lklResponse.setDesc("还款申请失败,原因:"+errorMsg);	
//					lklRespData.setHead(JSON.toJSONString(lklResponse));
//					return lklRespData;
//				}
//			} else {
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("还款申请失败,原因:返回为空");				
//				return lklRespData;
//			}
//			
//			map.put("respCd", "00");
//			map.put("respMsg", "申请还款成功");
//			lklRespData.setResponse(JSON.toJSONString(map));
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("请求异常");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//		return lklRespData;
//
//	}
//
//	
//	@Override
//	public LklRespData getRepaymentResult(long sessionId, Map<String, String> mapData) {
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			String thirdOrderNo = mapData.get("applySerial");
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单编号为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			String period = mapData.get("term");
//			if (StringUtils.isBlank(period)) {
//				//没传就查第1期
//				period = "1";
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			/*if (bwOrder.getStatusId() != 9) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("还款结果查询接口还未放款");
//				logger.info("结束还款结果查询接口，返回结果：" + JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}*/
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("用户不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard =  bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("银行卡不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
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
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("还款计划不存在");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
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
//				//map.put("status", "8001");//当期已还清
//			}else{
//				lklResponse.setCode(LklResponse.CODE_FAILERR);
//				lklResponse.setDesc("未还款");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//				/*double alreadyRepayMoney = bwRepaymentPlan.getAlreadyRepayMoney();
//				if(alreadyRepayMoney>0){		
//					map.put("status", "8002");//部分还款
//					//状态非等于2，而真实还款金额又大于0，那么就是在前端部分还款的
//					alreadyRepayAmount = alreadyRepayMoney;
//					willRepayAmount = bwRepaymentPlan.getRealityRepayMoney()+repayPenaltyInt-alreadyRepayMoney;
//				}else{	
//				map.put("status", "8006");
//				willRepayAmount = bwRepaymentPlan.getRealityRepayMoney()+repayPenaltyInt;
//				
//				}*/
//			}
//            map.put("repaySerial", bwOrder.getId());
//            map.put("dueBillNo", bwOrder.getId());
//            map.put("orgNo", "SXFQ");
//            map.put("payType", "DS");
//			map.put("paymentAmt", alreadyRepayAmount*100);
//			map.put("reliefAmt", 0);
//			map.put("repayDate", bwRepaymentPlan.getUpdateTime());
//			map.put("term", period);
//			int RepayType = bwRepaymentPlan.getRepayType();
//			int payModel = 2;
//			if(RepayType==1)payModel=0;
//			if(RepayType==3)payModel=1;
//			map.put("payModel",payModel);
//			//map.put("remark", "已还"+alreadyRepayAmount+"元，未还"+willRepayAmount+"元，当前第"+period+"期。");
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("查询还款结果成功");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			lklRespData.setResponse(JSON.toJSONString(map));
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("请求异常");
//		}
//		return lklRespData;
//	}
//	
//	
//	/**
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.XianJinBaiKaService#getContracts(long,
//	 *      com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaCommonRequest)
//	 */
//	@Override
//	public LklRespData getContracts(long sessionId, Map<String,String> mapData) {
//		logger.info(sessionId + "：开始拉卡拉合同接口 method：" + JSON.toJSONString(mapData));
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();
//		try {
//			String thirdOrderNo = mapData.get("applySerial");
//			Map<String, Object> contractUrls = null;
//			List list = new ArrayList();
//			if (StringUtils.isNotBlank(thirdOrderNo)) {
//				BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//				if (!CommUtils.isNull(bwOrderRong)) {
//					Long orderId = bwOrderRong.getOrderId();
//					List<BwAdjunct> bwAdjunctList = bwAdjunctService.findBwAdjunctByOrderId(orderId);
//					if (!CommUtils.isNull(bwAdjunctList)) {
//						for (BwAdjunct bwAdjunct : bwAdjunctList) {
//							if (bwAdjunct.getAdjunctType() == 29) {
//								contractUrls = new HashMap<String, Object>();
//								String adjunctPath = bwAdjunct.getAdjunctPath();
//								String conUrl = SystemConstant.PDF_URL + adjunctPath;
//								contractUrls.put("titleName","小微金融水象分期信息咨询及信用管理服务合同");
//								contractUrls.put("url",conUrl);
//								list.add(contractUrls);
//							}
//							if (bwAdjunct.getAdjunctType() == 30) {
//								contractUrls = new HashMap<>();
//								String adjunctPath = bwAdjunct.getAdjunctPath();
//								String conUrl = SystemConstant.PDF_URL + adjunctPath;
//								contractUrls.put("titleName","征信及信息披露授权书");
//								contractUrls.put("url",conUrl);
//								list.add(contractUrls);
//							}
//						}
//					}
//				}
//			}
//			Map<String,Object> mapJson = new HashMap<String,Object>();
//			mapJson.put("agreeList", list);
//			
//			lklResponse.setCode(LklResponse.CODE_SUCCESS);
//			lklResponse.setDesc("查询合同接口成功");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			lklRespData.setResponse(JSON.toJSONString(mapJson));
//		} catch (Exception e) {
//			logger.error(sessionId + "：执行拉卡拉合同接口 异常：", e);
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("请求失败");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//		}
//		logger.info(sessionId + "：结束拉卡拉合同接口：" + JSON.toJSONString(lklResponse));
//		return lklRespData;
//	}
//	
//
//	/*public static void main(String[] args) {
//		LklResponse rs = new RongShuNewServiceImpl().getAddressList(12112121L,11151);
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
//	public LklRespData updateLoan(long sessionId, Map<String, String> mapData) {
//		LklResponse lklResponse = new LklResponse();
//		LklRespData lklRespData = new LklRespData();	
//		try {
//			String order_no = mapData.get("applySerial");//三方订单号
//			if (StringUtils.isBlank(order_no)) {
//				lklResponse.setCode(LklResponse.CODE_PARMERR);
//				lklResponse.setDesc("订单编号为空");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			
//			DrainageRsp drainageRsp = commonService.updateSignContract(sessionId, order_no);
//			logger.info("拉卡拉签约返回对象:"+JSON.toJSONString(drainageRsp));
//			String code = drainageRsp.getCode();
//			if("0000".equals(code)){
//				lklResponse.setCode(LklResponse.CODE_SUCCESS);
//				lklResponse.setDesc("拉卡拉签约成功");
//				lklRespData.setHead(JSON.toJSONString(lklResponse));
//				return lklRespData;
//			}
//			logger.info("拉卡拉签约失败:"+JSON.toJSONString(drainageRsp));
//			lklResponse.setCode(LklResponse.CODE_FAILERR);
//			lklResponse.setDesc("拉卡拉签约失败");
//			lklRespData.setHead(JSON.toJSONString(lklResponse));
//			
//	}catch (Exception e) {
//		logger.error(sessionId + "：执行拉卡拉确认订单接口 异常：", e);
//		lklResponse.setCode(LklResponse.CODE_FAILERR);
//		lklResponse.setDesc("请求失败");
//		lklRespData.setHead(JSON.toJSONString(lklResponse));
//		return lklRespData;
//	}
//        return lklRespData;
//	}
//	
//	
//	
//}
