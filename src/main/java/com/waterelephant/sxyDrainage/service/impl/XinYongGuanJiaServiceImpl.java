//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.drainage.service.DrainageService;
//import com.waterelephant.entity.BwAdjunct;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderChannel;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderChannelService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwAdjunctServiceImpl;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsResponse;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.CallVo;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.RepaymentResultReq;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjBasicInfo;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjBindCard;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjCompanyInfo;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjContact;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjIdentifyInfo;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjOperator;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjOperatorBase;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjPushOrderReq;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjResponse;
//import com.waterelephant.sxyDrainage.service.AsyncXjgjTask;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.XinYongGuanJiaService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.rongshu.RongShuUtils;
//import com.waterelephant.sxyDrainage.utils.xinYongGuanJia.XinYongGuanJiaConstant;
//import com.waterelephant.sxyDrainage.utils.xinYongGuanJia.XinYongGuanJiaUtils;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.AESUtil;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//
///**
// * 
// * <p>
// * Title: XinYongGuanJiaServiceImpl
// * </p>
// * <p>
// * Description: 信用管家
// * </p>
// * 
// * @since JDK 1.8
// * @author xiongfeng
// */
//@Service
//public class XinYongGuanJiaServiceImpl implements XinYongGuanJiaService {
//	private Logger logger = LoggerFactory.getLogger(XinYongGuanJiaServiceImpl.class);
//	private static String AES_KEY = "RESKEY.CHANNEL_";
//
//	@Autowired
//	private IBwOrderChannelService bwOrderChannelService;
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private IBwOrderService bwOrderService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//	@Autowired
//	private IBwWorkInfoService bwWorkInfoService;
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//	@Autowired
//	private BwContactListService bwContactListService;
//	@Autowired
//	private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//	@Autowired
//	private IBwPersonInfoService bwPersonInfoService;
//	@Autowired
//	private BwOrderProcessRecordService bwOrderProcessRecordService;
//	@Autowired
//	private IBwRepaymentPlanService bwRepaymentPlanService;
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//	@Autowired
//	private BwAdjunctServiceImpl bwAdjunctService;
//	@Autowired
//	private AsyncXjgjTask asyncXygjTask;
//	@Autowired
//	private BwOperateVoiceService bwOperateVoiceService;
//	@Autowired
//	private DrainageService drainageService;
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//
//	/**
//	 * 4.1.1存量用户检验接口
//	 */
//	@Override
//	public XygjResponse checkUser(long sessionId, String appId, String aesRequest) {
//		logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>进入checkUser()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//		//XygjCheckUserInfo xygjCheckUserInfo = new XygjCheckUserInfo();
//
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			logger.info(sessionId + ":信用管家>>>checkUser()方法>>>获取orderChannel");
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>存量用户检验接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + orderChannel.getId());
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>存量用户检验接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			// 第二步 ：用户检验
//			// 解开三元信息
//			Map<String, String> reqMap = JSON.parseObject(requestJson, Map.class);
//			String name = String.valueOf(reqMap.get("name"));
////			String idCard = String.valueOf(map.get("idCard")).replace("*", "%");
////			String mobile = String.valueOf(map.get("mobile")).replace("*", "%");
//			String idCard = String.valueOf(reqMap.get("idCard"));
//			String mobile = String.valueOf(reqMap.get("mobile"));
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>开始checkUser()用户检验>>>" + JSON.toJSONString(reqMap));
//			DrainageRsp drainageRsp = commonService.checkUser(sessionId, name, mobile, idCard);
//
//			// 0000 规则已过，可以借款
//			// 2001 命中黑名单规则
//			// 2002 存在进行中的订单
//			// 2003 命中被拒规则
//			// 2004 用户年龄超限
//			Map<String, Object> responseMap = new HashMap<>();
//			String resultCode = drainageRsp.getCode();
//			if (!"0000".equals(resultCode)) {
//				responseMap.put("isCanLoan", 0); // 是否可以借款0否1是
//				if ("2001".equals(resultCode)) {
//					responseMap.put("rejectReason", 1); // 命中黑名单
//					
//				} else if ("2002".equals(resultCode)) {
//					responseMap.put("rejectReason", 2); // 存在进行中的订单
//					
//				} else if ("2003".equals(resultCode)) {
//					responseMap.put("rejectReason", 3); // 命中被拒规则
//					
//				} else {
//					responseMap.put("rejectReason", 4); // 其他
//				}
//				responseMap.put("remark", drainageRsp.getMessage());  // 备注
//				xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//				xygjResponse.setMsg("SUCCESS");
//				
//			} else {
//				xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//				xygjResponse.setMsg("SUCCESS");
//				responseMap.put("isCanLoan", 1); // 是否可以借款0否1是
//			}
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>结束checkUser()用户检验>>>");
//
//			// 第三步 : 判断增量用户
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>开始oldUserFilter()增量用户判断>>>");
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>效验参数mobile:"+mobile+"idCard:"+idCard+"name:"+name);
//			BwBorrower bw = bwBorrowerService.oldUserFilter(mobile, idCard, name);
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>用户信心表返回数据"+bw);
//			if (null != bw) {
//				// 老用户
//				responseMap.put("isStock", 1);   // 增量用户0否1是
//				// 是否绑卡
//				BwBankCard bwBankCard = new BwBankCard();
//				bwBankCard.setBorrowerId(bw.getId());
//				bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//				logger.info(sessionId + ":信用管家>>>bwBankCard返回值:"+bwBankCard);
//				// 对于已绑卡用户要回传银行名、卡号、预留手机号
//				if (bwBankCard != null && bwBankCard.getSignStatus()>0) {
//					String bankCode = DrainageUtils.convertFuiouBankCodeToBaofu(bwBankCard.getBankCode());
//					responseMap.put("bank", bankCode);      // 已绑银行
//					responseMap.put("bankCardNum", bwBankCard.getCardNo()); // 已绑银行卡号
//					responseMap.put("phone", bwBankCard.getPhone());        // 银行预留手机号
//				}
//			} else {
//				// 新用户
//				responseMap.put("isStock", 0);   // 增量用户0否1是
//			}
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>结束oldUserFilter()增量用户判断>>>");
//
//			// 第四步 : 金额限制
//			responseMap.put("maxLimit", 20000 * 100); // 最高额度 单位:分
//			responseMap.put("minLimit", 2000 * 100);  // 最低额度
//			responseMap.put("step", 100 * 100);       // 步长 100
//			responseMap.put("number", 4);  	   // 期数
//			responseMap.put("periodNum", 28);  // 可贷期限
//			responseMap.put("periodUnit", 1);  // 期限单位
//
//			// 对响应参数AES加密
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>response加密前数据:" + JSON.toJSONString(responseMap));
//			String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), AESKey);
//			xygjResponse.setResponse(response);
//
//			logger.info(sessionId + ":信用管家>>>存量用户检验接口>>>返回成功" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行存量用户检验接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//
//	}
//
//	/**
//	 * 4.1.2进件推送接口
//	 */
//	@Override
//	public XygjResponse saveOrder(long sessionId, String appId, String aesRequest) {
//		logger.info(sessionId + ":信用管家>>>进件推送接口>>>进入pushOrder()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>进件推送接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>进件推送接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>进件推送接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			XygjPushOrderReq request = JSON.parseObject(requestJson, XygjPushOrderReq.class);
//			if (null == request) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>进件推送接口request为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 基本信息
//			XygjBasicInfo basicInfo = request.getBasicInfo();
//			// 实名认证信息
//			XygjIdentifyInfo identifyInfo = request.getIdentifyInfo();
//			// 公司信息
//			XygjCompanyInfo companyInfo = request.getCompanyInfo();
//			// 通讯录
//			List<XygjContact> contacts = request.getContacts();
//			// 运营商数据
//			XygjOperator operator = request.getOperator();
//
//			if (null == basicInfo || null == identifyInfo || null == companyInfo || null == contacts
//					|| null == operator) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>进件推送接口封装信息为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			// 新增或更新借款人
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始新增或更新借款人:" + JSON.toJSONString(basicInfo));
//			String userName = basicInfo.getName();
//			String idCard = basicInfo.getIdCard();
//			String phone = basicInfo.getPhone();
//
//			String thirdOrderNo = basicInfo.getThirdOrderNo();
//
//			if (StringUtils.isBlank(userName)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("姓名为空");
//				logger.info(sessionId + ":信用管家>>>结束pushOrder方法，返回结果:姓名为空" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			if (StringUtils.isBlank(idCard)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("身份证号码为空");
//				logger.info(sessionId + ":信用管家>>>结束pushOrder方法，返回结果:身份证号码为空" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			if (StringUtils.isBlank(phone)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("手机号为空");
//				logger.info(sessionId + ":信用管家>>>结束pushOrder方法，返回结果:手机号为空" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			if (CommUtils.isNull(channelId)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道编码为空");
//				logger.info(sessionId + ":信用管家>>>结束pushOrder方法，返回结果:渠道编码为空" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("三方订单号为空");
//				logger.info(sessionId + ":信用管家>>>结束pushOrder()方法，返回结果:三方订单号为空" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			
//			// 返回结果封装
//			Map<String,Object> mapRes = new HashMap<String,Object>();
//			/**
//			 * 判断一个身份证是否有多个手机号码申请贷款
//			 */
//			boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//			if (flag) {
//				mapRes.put("status", "302");
//				mapRes.put("remark", "该用户已存在进行中的订单");
//				logger.info(sessionId + ":进件失败>>>response加密前数据:" + JSON.toJSONString(mapRes));
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), AESKey);
//				xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//				xygjResponse.setMsg("SUCCESS");
//				xygjResponse.setResponse(response);
//				return xygjResponse;
//			}
//
//			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束新增或更新借款人:borrowerId[" + borrowerId + "]");
//
//			// 判断该渠道是否有撤回的订单
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始判断该渠道是否有撤回的订单:borrowerId[" + borrowerId + "]");
//			BwOrder order = new BwOrder();
//			order.setBorrowerId(borrowerId);
//			order.setStatusId(8L); 			// 8:撤回
//			order.setChannel(channelId);
//			order = bwOrderService.findBwOrderByAttr(order);
//			if (order == null) {
//				// 查询是否有进行中的订单
//				long count = bwOrderService.findProOrder(borrowerId + "");
//				logger.info(sessionId + ">>>进行中的订单校验：" + count);
//				if (count > 0) {
//					mapRes.put("status", "302");
//					mapRes.put("remark", "该用户已存在进行中的订单");
//					// 对响应参数AES加密
//					logger.info(sessionId + ":进件失败>>>response加密前数据:" + JSON.toJSONString(mapRes));
//					String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), AESKey);
//					xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//					xygjResponse.setMsg("SUCCESS");
//					xygjResponse.setResponse(response);
//					return xygjResponse;
//				}
//			}
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束新增或更新借款人" + JSON.toJSONString(xygjResponse));
//
//			// 判断是否有草稿状态的订单
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始判断是否有草稿状态的订单");
//			Integer productId = Integer.valueOf(XinYongGuanJiaConstant.PRODUCTID);
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setBorrowerId(borrowerId);
//			bwOrder.setStatusId(1L); // 当前工单状态 1草稿
//			bwOrder.setProductType(2);// 产品类型(1、单期，2、分期)
//			bwOrder.setChannel(channelId);
//			bwOrder.setProductId(productId);
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
//			bwOrder.setStatusId(8L);
//			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
//			boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//			if (boList != null && boList.size() > 0) {
//				bwOrder = boList.get(boList.size() - 1);
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setProductType(2); // 产品类型(1、单期，2、分期)
//				bwOrder.setStatusId(1L); // 当前工单状态 1草稿
//				bwOrder.setExpectMoney(basicInfo.getLoanAmount() + 0.0D); // 用户预期借款金额
//				bwOrder.setExpectNumber(4);
//				bwOrder.setRepayType(2); // // 还款方式 1:先息后本 2:等额本息
//				bwOrderService.updateBwOrder(bwOrder);
//			} else {
//				bwOrder = new BwOrder();
//				bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//				bwOrder.setBorrowerId(borrower.getId());
//				bwOrder.setStatusId(1L);
//				bwOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setChannel(channelId);
//				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE)); // 免罚息期
//				bwOrder.setApplyPayStatus(0); // 申请还款状态 0:未申请 1:申请
//				bwOrder.setProductId(productId);
//				bwOrder.setProductType(2);
//				bwOrder.setRepayType(2);
//				bwOrder.setExpectMoney(basicInfo.getLoanAmount() + 0.0D);
//				bwOrder.setExpectNumber(4);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束判断是否有草稿状态的订单orderId:" + orderId);
//
//			// 判断是否有订单
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始判断是否有订单orderId:" + orderId);
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setOrderId(orderId);
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (bwOrderRong == null) {
//				bwOrderRong = new BwOrderRong();
//				bwOrderRong.setOrderId(orderId);
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.save(bwOrderRong);
//			} else {
//				if (null == bwOrderRong.getChannelId()) {
//					bwOrderRong.setChannelId(Long.valueOf(channelId));
//				}
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRongService.update(bwOrderRong);
//			}
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束判断是否有订单");
//
//			// 判断是否有商户订单信息
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始判断是否有商户订单信息orderId:" + orderId);
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
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束判断是否有商户订单信息");
//
//			// 判断是否有工作信息
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始判断是否有工作信息orderId:" + orderId);
//			BwWorkInfo bwWorkInfo = new BwWorkInfo();
//			bwWorkInfo.setOrderId(orderId);
//			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//			if (null == bwWorkInfo) {
//				bwWorkInfo = new BwWorkInfo();
//				bwWorkInfo.setOrderId(orderId);
//				bwWorkInfo.setIncome(companyInfo.getIncome()); // 月收入
//				bwWorkInfo.setComName(companyInfo.getCompanyName()); // 公司名称
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值 方便接听电话时间
//				bwWorkInfo.setIndustry(XinYongGuanJiaUtils.getIndustry(companyInfo.getIndustry())); // 行业
//
//				bwWorkInfo.setWorkYears(XinYongGuanJiaUtils.getWorkYear(companyInfo.getJobTime())); // 工作年限
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//				bwWorkInfoService.save(bwWorkInfo, borrowerId);
//				logger.info("保存借款人" + borrowerId + "工作信息成功");
//			} else {
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setIncome(companyInfo.getIncome());
//				bwWorkInfo.setComName(companyInfo.getCompanyName());
//				bwWorkInfo.setIndustry(XinYongGuanJiaUtils.getIndustry(companyInfo.getIndustry()));
//				bwWorkInfo.setWorkYears(XinYongGuanJiaUtils.getWorkYear(companyInfo.getJobTime()));
//				bwWorkInfoService.update(bwWorkInfo);
//				logger.info("更新借款人" + borrowerId + "工作信息成功");
//			}
//			
//			/**
//			 * 插入个人认证记录 bw_order_auth
//			 * 认证类型:
//			 * 1：运营商 2：个人信息 3：拍照 4：芝麻信用 5：社保 6：公积金 7：邮箱 8：淘宝 9：京东 10:单位信息 11：用款确认
//			 */
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId); 
//																							
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束判断是否有工作信息");
//
//			// 通讯录
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始处理通讯录信息");
//			if (CollectionUtils.isNotEmpty(contacts)) {
//				List<BwContactList> listConS = new ArrayList<BwContactList>();
//				for (XygjContact xygjContact : contacts) {
//					if (CommUtils.isNull(xygjContact.getName()) || CommUtils.isNull(xygjContact.getPhoneNum())) {
//						continue;
//					}
//					// 借款人通讯录列表
//					BwContactList bwContactList = new BwContactList();
//					bwContactList.setBorrowerId(borrowerId);
//					bwContactList.setPhone(xygjContact.getPhoneNum());
//					bwContactList.setName(xygjContact.getName());
//					listConS.add(bwContactList);
//				}
//				bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//				logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束处理通讯录信息");
//			}
//
//			try {
//				// 运营商 通话记录  公共表操作
//				addOrUpdateOperate(sessionId, orderId, operator, borrowerId, channelId);
//
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//
//			// 认证图片
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始处理认证图片");
//			String frontFile = identifyInfo.getFrontFile(); // 身份证正面照片url
//			String backFile = identifyInfo.getBackFile(); // 身份证反面照片url
//			String natureFile = identifyInfo.getNatureFile(); // 生活照url/手持照
//			if (StringUtils.isNotBlank(frontFile)) {
//				String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01"); // 身份证正面照
//				logger.info(sessionId + ">>>身份证正面 " + frontImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0); // 保存身份证正面照
//			}
//			if (StringUtils.isNotBlank(backFile)) {
//				String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02"); // 身份证反面照
//				logger.info(sessionId + ">>>身份证反面 " + backImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0); // 保存身份证反面照
//			}
//			if (StringUtils.isNotBlank(natureFile)) {
//				String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03"); // 手持照
//				logger.info(sessionId + ">>>手持照/人脸 " + handerImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0); // 保存手持照
//			}
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束处理认证图片");
//
//			// 保存身份证信息
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始处理身份证信息");
//			BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//			bwIdentityCard.setBorrowerId(borrowerId);
//			bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//			if (null == bwIdentityCard) {
//				bwIdentityCard = new BwIdentityCard2();
//				bwIdentityCard.setAddress(identifyInfo.getAddress());
//				bwIdentityCard.setIdCardNumber(basicInfo.getIdCard());
//				bwIdentityCard.setName(basicInfo.getName());
//				bwIdentityCard.setIssuedBy(identifyInfo.getIssuedBy()); // 身份证发证处
//				bwIdentityCard.setRace(identifyInfo.getNation()); // 名族
//				bwIdentityCard.setValidDate(identifyInfo.getValidDate());// 身份证有效期
//				bwIdentityCard.setBorrowerId(borrowerId);
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//				logger.info(sessionId + ":保存身份证信息 ");
//			} else {
//				bwIdentityCard.setAddress(identifyInfo.getAddress());
//				bwIdentityCard.setIdCardNumber(basicInfo.getIdCard());
//				bwIdentityCard.setName(basicInfo.getName());
//				bwIdentityCard.setIssuedBy(identifyInfo.getIssuedBy());
//				bwIdentityCard.setRace(identifyInfo.getNation());
//				bwIdentityCard.setValidDate(identifyInfo.getValidDate());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//				logger.info(sessionId + ":更新身份证信息 ");
//			}
//			// 插入身份认证记录
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束处理身份证信息");
//
//			// 亲属联系人
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始处理亲属联系人信息");
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			if (null == bwPersonInfo) {
//				bwPersonInfo = new BwPersonInfo();
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setOrderId(orderId);
//				bwPersonInfo.setUpdateTime(new Date());
//				// bwPersonInfo.setCityName(basicInfo.getHouseCity());
//				bwPersonInfo.setAddress(basicInfo.getHouseAddress());
//				bwPersonInfo.setRelationName(basicInfo.getFirstName());
//				bwPersonInfo.setRelationPhone(basicInfo.getFirstPhone());
//				bwPersonInfo.setUnrelationName(basicInfo.getSecondName());
//				bwPersonInfo.setUnrelationPhone(basicInfo.getSecondPhone());
//				bwPersonInfo.setMarryStatus(basicInfo.getMarriage()); // 婚姻状况
//																		// 0：未婚，1：已婚
//				bwPersonInfo.setEmail(basicInfo.getEmail());
//				bwPersonInfo.setHouseStatus(basicInfo.getHaveHouse());
//				bwPersonInfo.setCarStatus(basicInfo.getHaveCar());
//				bwPersonInfo.setColleagueName(basicInfo.getColleagueName());
//				bwPersonInfo.setColleaguePhone(basicInfo.getColleaguePhone());
//				bwPersonInfo.setFriend1Name(basicInfo.getFriend1Name());
//				bwPersonInfo.setFriend1Phone(basicInfo.getFriend1Phone());
//				bwPersonInfo.setFriend2Name(basicInfo.getFriend2Name());
//				bwPersonInfo.setFriend2Phone(basicInfo.getFriend2Phone());
//				bwPersonInfo.setQqchat(basicInfo.getQqchat());
//				bwPersonInfo.setWechat(basicInfo.getWechat());
//				bwPersonInfoService.add(bwPersonInfo);
//			} else {
//				// bwPersonInfo.setCityName(sxyBasicInfo.getHouseCity());
//				bwPersonInfo.setAddress(basicInfo.getHouseAddress());
//				bwPersonInfo.setRelationName(basicInfo.getFirstName());
//				bwPersonInfo.setRelationPhone(basicInfo.getFirstPhone());
//				bwPersonInfo.setUnrelationName(basicInfo.getSecondName());
//				bwPersonInfo.setUnrelationPhone(basicInfo.getSecondPhone());
//				bwPersonInfo.setMarryStatus(basicInfo.getMarriage());
//				bwPersonInfo.setEmail(basicInfo.getEmail());
//				bwPersonInfo.setHouseStatus(basicInfo.getHaveHouse());
//				bwPersonInfo.setCarStatus(basicInfo.getHaveCar());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setColleagueName(basicInfo.getColleagueName());
//				bwPersonInfo.setColleaguePhone(basicInfo.getColleaguePhone());
//				bwPersonInfo.setFriend1Name(basicInfo.getFriend1Name());
//				bwPersonInfo.setFriend1Phone(basicInfo.getFriend1Phone());
//				bwPersonInfo.setFriend2Name(basicInfo.getFriend2Name());
//				bwPersonInfo.setFriend2Phone(basicInfo.getFriend2Phone());
//				bwPersonInfo.setQqchat(basicInfo.getQqchat());
//				bwPersonInfo.setWechat(basicInfo.getWechat());
//				bwPersonInfoService.update(bwPersonInfo);
//			}
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束处理亲属联系人信息");
//
//			/*// 修改工单进程表
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始修改工单进程表");
//			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//			bwOrderProcessRecord.setSubmitTime(new Date());
//			bwOrderProcessRecord.setOrderId(bwOrder.getId());
//			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束更改订单进行时间");*/
//
//			/* 
//		修改订单状态放到异步运营商中处理
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>开始修改订单状态");
//			bwOrder.setStatusId(2L); // 2初审
//			bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//			bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//			bwOrderService.updateBwOrder(bwOrder);*/
//
//			//异步处理运营商数据
//	        asyncXygjTask.addOperator(sessionId, bwOrder, borrower, operator);
//			
//			// 系统审核任务 审核 放入redis
//			/*SystemAuditDto systemAuditDto = new SystemAuditDto();
//			systemAuditDto.setIncludeAddressBook(0);
//			systemAuditDto.setOrderId(orderId);
//			systemAuditDto.setBorrowerId(borrowerId);
//			systemAuditDto.setName(userName);
//			systemAuditDto.setPhone(phone);
//			systemAuditDto.setIdCard(idCard);
//			systemAuditDto.setChannel(channelId);
//			systemAuditDto.setThirdOrderId(thirdOrderNo);
//			systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//			RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//			logger.info(sessionId + ":信用管家>>>pushOrder()方法>>>结束修改订单状态,并放入redis");*/
//
//	        mapRes.put("status", "301");
//			mapRes.put("remark", "进件成功");
//			
//			// 对响应参数AES加密
//			logger.info(sessionId + ":进件成功>>>response加密前数据:" + JSON.toJSONString(mapRes));
//			String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), AESKey);
//			xygjResponse.setResponse(response);
//			xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//			xygjResponse.setMsg("SUCCESS");
//			logger.info(sessionId + ":信用管家>>>进件推送接口>>>返回成功" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>进件推送接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//	}
//
//	/**
//	 * 4.1.3银行卡预绑卡接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public XygjResponse saveBindCardReady(long sessionId, String appId, String aesRequest) {
//		logger.info(sessionId + ":信用管家>>>预绑卡接口>>>进入bindCardReady()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>预绑卡接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>预绑卡接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>预绑卡接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			XygjBindCard request = JSON.parseObject(requestJson, XygjBindCard.class);
//			if (null == request) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>预绑卡接口request为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			logger.info(sessionId + ":信用管家>>>开始预绑卡saveBindCard_NewReady()方法channelId:" + channelId);
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			// drainageBindCardVO.setThirdOrderNo(request.getThirdOrderNo()); //
//			// 平台工单号
//			drainageBindCardVO.setIdCardNo(request.getIdCard()); // 身份证
//			drainageBindCardVO.setName(request.getName()); // 持卡人姓名
//			drainageBindCardVO.setBankCardNo(request.getBankCardNo()); // 银行卡
//			drainageBindCardVO.setBankCode(request.getBankCode()); // 银行编码
//			drainageBindCardVO.setPhone(request.getPhone());
//			drainageBindCardVO.setBindType("1"); // 绑卡类型，1 前置 其他为后置 // 前置绑卡 传phone 后置绑卡 传 thirdOrderNo
//			drainageBindCardVO.setChannelId(channelId); // 渠道号
//			drainageBindCardVO.setNotify(true); // 是否需要通知
//			drainageBindCardVO.setRegPhone(request.getBankPhone()); // 银行预留手机
//			
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//			if (null != drainageRsp) {
//				if ("0000".equals(drainageRsp.getCode())) {
//					xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//					xygjResponse.setMsg("预绑卡申请成功");
//					logger.info(sessionId + ":信用管家>>>预绑卡申请成功" + JSON.toJSONString(xygjResponse));
//				} else {
//					xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//					xygjResponse.setMsg(drainageRsp.getMessage());
//					logger.info(sessionId + ":信用管家>>>预绑卡申请失败" + JSON.toJSONString(xygjResponse));
//				}
//			} else {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("预绑卡申请失败");
//				logger.info(sessionId + ":信用管家>>>预绑卡申请失败drainageRsp为空" + JSON.toJSONString(xygjResponse));
//			}
//			return xygjResponse;
//
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>银行预绑卡接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//
//	}
//
//	/**
//	 * 4.1.4银行卡确认绑卡接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public XygjResponse saveBindCardSure(long sessionId, String appId, String aesRequest) {
//		logger.info(sessionId + ":信用管家>>>开始银行卡确认绑卡接口>>>进入saveBindCardSure()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>确认绑卡接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>确认绑卡接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>确认绑卡接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			XygjBindCard request = JSON.parseObject(requestJson, XygjBindCard.class);
//			if (null == request) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>确认绑卡接口request为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			logger.info(sessionId + ":信用管家>>>开始确认绑卡saveBindCard_NewSure()方法channelId:" + channelId);
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setBindType("1"); // 绑卡类型，1 前置 其他为后置 // 前置绑卡 传 phone 后置绑卡 传 thirdOrderNo
//			drainageBindCardVO.setChannelId(channelId);
//			drainageBindCardVO.setPhone(request.getPhone());
//			// drainageBindCardVO.setThirdOrderNo(request.getThirdOrderNo()); // 平台工单号
//			drainageBindCardVO.setNotify(true);
//			drainageBindCardVO.setVerifyCode(request.getVerifyCode()); // 验证码
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//
//			if (null != drainageRsp) {
//				if ("0000".equals(drainageRsp.getCode())) {
//					xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//					xygjResponse.setMsg("确认绑卡成功");
//					logger.info(sessionId + ":信用管家>>>确认绑卡成功" + JSON.toJSONString(xygjResponse));
//				} else {
//					xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//					xygjResponse.setMsg(drainageRsp.getMessage());
//					logger.info(sessionId + ":信用管家>>>确认绑卡失败" + JSON.toJSONString(xygjResponse));
//				}
//			} else {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("确认绑卡失败");
//				logger.info(sessionId + ":信用管家>>>确认绑卡失败drainageRsp为空" + JSON.toJSONString(xygjResponse));
//			}
//			return xygjResponse;
//
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>银行确认绑卡接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//	}
//
//	/**
//	 * 4.1.5 主动还款接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public XygjResponse updateActiveRepayment(long sessionId, String appId, String aesRequest) {
//		// TODO Auto-generated method stub
//		logger.info(sessionId + ":信用管家>>>主动还款接口>>>进入ActiveRepayment()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>主动还款接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>主动还款接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>主动还款接口>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			Map<String, String> map = JSON.parseObject(requestJson, Map.class);
//			String thirdOrderNo = String.valueOf(map.get("thirdOrderNo"));
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>主动还款接口thirdOrderNo为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			logger.info(sessionId + ":信用管家>>>开始主动还款updateRepayment()方法>>>");
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//			Map<String,Object> mapRes = new HashMap<String,Object>();
//            
//			if (null != drainageRsp) {
//				xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//				xygjResponse.setMsg("SUCCESS");
//				if ("000".equals(drainageRsp.getCode())) {
//					mapRes.put("status", "601");
//					mapRes.put("remark", "申请还款请求成功");
//					logger.info(sessionId + ":信用管家>>>主动还款成功" + JSON.toJSONString(mapRes));
//
//				} else {
//					mapRes.put("status", "603");
//					mapRes.put("remark", "申请还款请求失败,原因:"+drainageRsp.getMessage());//drainageRsp.getMessage()
//					logger.info(sessionId + ":信用管家>>>主动还款失败" + JSON.toJSONString(mapRes));
//				}
//				
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), AESKey);
//				xygjResponse.setResponse(response);
//			} else {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("操作失败");
//				logger.info(sessionId + ":信用管家>>>主动还款失败drainageRsp为空" + JSON.toJSONString(xygjResponse));
//			}
//			return xygjResponse;
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error(sessionId + ":信用管家>>>执行主动还款接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//	}
//
//	/**
//	 * 4.1.6 拉取还款计划接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public XygjResponse getRepayPlan(long sessionId, String appId, String aesRequest) {
//		// TODO Auto-generated method stub
//		logger.info(sessionId + ":信用管家>>>拉取还款计划接口>>>进入getRepayPlan()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":拉取还款计划接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			Map<String, String> map = JSON.parseObject(requestJson, Map.class);
//			String thirdOrderNo = String.valueOf(map.get("thirdOrderNo")); // 机构订单号
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口thirdOrderNo为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 查询订单
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("订单不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口bwOrder为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 查询借款人
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("用户不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口borrower为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 查询银行卡
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("银行卡不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口bwBankCard为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			// 查询还款计划
//			List<BwRepaymentPlan> planList = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//			if (CollectionUtils.isEmpty(planList)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("还款计划不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口planList不存在>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			// 封装还款计划
//			logger.info(sessionId + ":信用管家>>>getRepayPlan()>>>开始封装还款计划>>>");
//			List<Map<String, Object>> plans = new ArrayList<Map<String, Object>>();
//			
//			// 得到每一期的还款计划 并封装
//			for (BwRepaymentPlan plan : planList) {
//				Map<String,Object> planMap = new HashMap<String,Object>();
//				planMap.put("number", plan.getNumber());  // 期数
//				planMap.put("principal", new Double(plan.getRepayCorpusMoney() * 100).intValue());  // 还款本金
//				planMap.put("interest", new Double(plan.getRepayAccrualMoney() * 100).intValue());  // 利息
//				planMap.put("repay_money", new Double(plan.getRepayMoney() * 100).intValue());      // 应还金额
//				planMap.put("already_paid", new Double(plan.getAlreadyRepayMoney() * 100).intValue()); // 已还金额
//				
//				if (plan.getRepayStatus() == 2) {
//					// 已还款
//					//planMap.put("repay_date", String.valueOf(plan.getUpdateTime()));  // 还款日期
//					planMap.put("state", "601");
//				} else {
//					// 未还款
//				//	planMap.put("repay_date", String.valueOf(plan.getRepayTime()));  // 还款日期
//					planMap.put("state", "602");
//				}
//				planMap.put("repay_date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(plan.getRepayTime()));  // 还款日期
//				
//				// 计算逾期
//				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//				bwOverdueRecord.setRepayId(plan.getId());
//				bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//				if (null != bwOverdueRecord) {
//					// 逾期
//					planMap.put("state", "604");
//					
//					Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D
//							: bwOverdueRecord.getOverdueAccrualMoney();
//					Double advance = bwOverdueRecord.getAdvance();
//					Double overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//
//					planMap.put("overdue_days", bwOverdueRecord.getOverdueDay());
//					planMap.put("overdue_fee", new Double(overdueFee * 100).intValue());
//					planMap.put("remark", "逾期");
//				} else {
//					// 没有逾期
//					planMap.put("overdue_days", 0); // 逾期天数
//					planMap.put("overdue_fee", 0);  // 逾期费用
//				}
//				// 还款计划添加到集合
//				plans.add(planMap);
//			}
//			logger.info(sessionId + ":信用管家>>>getRepayPlan()>>>结束封装还款计划>>>");
//			
//			
//			Map<String,Object> responsePlan = new HashMap<String,Object>();
//			responsePlan.put("principalSum", bwOrder.getBorrowAmount());
//			responsePlan.put("thirdOrderNo", thirdOrderNo);
//			responsePlan.put("repayPlans", plans);
//
//			// 对响应参数AES加密
//			logger.info(sessionId + "：拉取还款计划接口>>>response加密前数据:" + JSON.toJSONString(responsePlan));
//			String response = AESUtil.Encrypt(JSON.toJSONString(responsePlan), AESKey);
//			xygjResponse.setResponse(response);
//			xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//			xygjResponse.setMsg("拉取还款计划成功");
//			logger.error(sessionId + ":信用管家>>>结束getRepayPlan()方法");
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error(sessionId + ":信用管家>>>执行拉取还款计划接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//	}
//
//	/**
//	 * 4.1.7 拉取合同接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public XygjResponse getContract(long sessionId, String appId, String aesRequest) {
//		// TODO Auto-generated method stub
//		logger.info(sessionId + ":信用管家>>>拉取合同接口>>>进入getContract()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>拉取合同接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>拉取合同接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取合同接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			Map<String, String> map = JSON.parseObject(requestJson, Map.class);
//			String thirdOrderNo = String.valueOf(map.get("thirdOrderNo")); // 机构订单号
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取合同接口thirdOrderNo为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (bwOrderRong == null) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取合同接口bwOrderRong为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			// 封装合同
//			Map<String, Object> contractMap = null;
//			List<Map<String, Object>> contractList = new ArrayList<Map<String, Object>>();
//			Long orderId = bwOrderRong.getOrderId();
//			List<BwAdjunct> bwAdjunctList = bwAdjunctService.findBwAdjunctByOrderId(orderId);
//			if (!CommUtils.isNull(bwAdjunctList)) {
//				
//				logger.info(sessionId + ":信用管家>>>开始封装合同>>>"+JSON.toJSONString(bwAdjunctList));
//				for (BwAdjunct bwAdjunct : bwAdjunctList) {
//					if (bwAdjunct.getAdjunctType() == 29) {
//						contractMap = new HashMap<>();
//						String adjunctPath = bwAdjunct.getAdjunctPath();
//						String conUrl = SystemConstant.PDF_URL + adjunctPath;
//						contractMap.put("小微金融水象分期信息咨询及信用管理服务合同",conUrl);
//						contractList.add(contractMap);
//					}
//					if (bwAdjunct.getAdjunctType() == 30) {
//						contractMap = new HashMap<>();
//						String adjunctPath = bwAdjunct.getAdjunctPath();
//						String conUrl = SystemConstant.PDF_URL + adjunctPath;
//						contractMap.put("征信及信息披露授权书",conUrl);
//						contractList.add(contractMap);
//					}
//				}
//				logger.info(sessionId + ":信用管家>>>结束封装合同>>>");
//				
//			} else {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("合同列表不存在");
//				logger.info(sessionId + ":信用管家>>>拉取合同接口bwAdjunctList不存在>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			// 对响应参数AES加密
//			logger.info(sessionId + ":信用管家>>>拉取合同接口>>>response加密前数据:" + JSON.toJSONString(contractList));
//			String response = AESUtil.Encrypt(JSON.toJSONString(contractList), AESKey);
//			xygjResponse.setResponse(response);
//			xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//			xygjResponse.setMsg("拉取合同成功");
//			logger.error(sessionId + ":信用管家>>>执行service层拉取合同成功");
//			return xygjResponse;
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error(sessionId + ":信用管家>>>执行拉取合同接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//	}
//
//	/**
//	 * 拉取订单状态接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public XygjResponse getOrderStatus(long sessionId, String appId, String aesRequest) {
//		// TODO Auto-generated method stub
//		logger.info(sessionId + ":信用管家>>>拉取订单状态接口>>>进入getOrderStatus()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>拉取订单状态接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>拉取订单状态接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取订单状态接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			Map<String, String> mapReq = JSON.parseObject(requestJson, Map.class);
//			String thirdOrderNo = String.valueOf(mapReq.get("thirdOrderNo")); // 机构订单号
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取订单状态接口thirdOrderNo为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (CommUtils.isNull(bwOrderRong)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("三方订单为空");
//				logger.info(sessionId + ":信用管家>>>拉取订单状态接口bwOrderRong为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			Long orderId = bwOrderRong.getOrderId();
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//			if (null == bwOrder) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("订单为空");
//				logger.info(sessionId + ":信用管家>>>拉取订单状态接口bwOrder为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			long orderStatus = bwOrder.getStatusId();
//			logger.info(sessionId + "信用管家返回订单初始状态数据orderStatus:"+orderStatus);
//			// 封装订单状态
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("thirdOrderNo", thirdOrderNo);// 订单唯一编号
//			String convertOrderStatus = XinYongGuanJiaUtils.convertOrderStatus(orderStatus);
//			logger.info(sessionId + "信用管家返回订单状态数据convertOrderStatus:"+convertOrderStatus);
//			map.put("status", null == convertOrderStatus ? "000" : convertOrderStatus);
//
//			// if(1 == resultType){ // 审核状态 (审批状态 审批后可借金额 备注)
//			// logger.info(sessionId + ":信用管家>>>拉取审核状态>>>订单状态:" + orderStatus);
//			// if (7 == orderStatus || 8 == orderStatus) {
//			// map.put("status", "403");// 审批状态 403 审批拒绝
//			// map.put("remark", "系统评分不足");// 审批状态备注
//			// //map.put("approve_amount", new Double(bwOrder.getExpectMoney() *
//			// 100).intValue() + "");// 审批后的可借金额单位（分）
//			// } else if (4 <= orderStatus && 7!= orderStatus && 8 !=
//			// orderStatus) {
//			// map.put("status", "401");// 审批状态 401 审批通过
//			// map.put("remark", "审批通过");// 审批状态备注
//			// map.put("approve_amount", new Double(bwOrder.getBorrowAmount() *
//			// 100).intValue());// 审批后的可借金额单位（分）
//			// } else {
//			// map.put("status", "402");// 审批状态 402 审批中
//			// map.put("remark", "审批中");// 审批状态备注
//			// }
//			//
//			// }else if(2 == resultType){ // 放款结果(放款状态 备注)
//			// logger.info(sessionId + ":信用管家>>>拉取放款结果>>>订单状态:" + orderStatus);
//			// if (7 == orderStatus || 8 == orderStatus) {
//			// map.put("status", "503");// 放款状态 503 放款拒绝
//			// map.put("remark", "放款失败");// 放款状态备注
//			//
//			// } else if (9 == orderStatus || 13 == orderStatus) {
//			// map.put("status", "501");// 放款状态 501 放款成功
//			// map.put("remark", "已放款");// 放款状态备注
//			// } else {
//			// map.put("status", "502");// 放款状态 502 待放款
//			// map.put("remark", "待放款");// 放款状态备注
//			// }
//			//
//			// }else{
//			// xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//			// xygjResponse.setMsg("请求参数错误");
//			// logger.info(sessionId + ":信用管家>>>拉取订单状态接口bwOrder为空>>>" +
//			// JSON.toJSONString(xygjResponse));
//			// return xygjResponse;
//			// }
//
//			// 对响应参数AES加密
//			logger.info(sessionId + ":信用管家>>>拉取订单状态接口>>>response加密前数据:" + JSON.toJSONString(map));
//			String response = AESUtil.Encrypt(JSON.toJSONString(map), AESKey);
//			xygjResponse.setResponse(response);
//			xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//			xygjResponse.setMsg("拉取订单状态成功");
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error(sessionId + ":信用管家>>>执行拉取订单状态接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//		}
//		logger.error(sessionId + ":信用管家>>>执行service层拉取订单状态接口成功");
//		return xygjResponse;
//	}
//
//	/**
//	 * 拉取还款结果接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public XygjResponse getRepaymentResult(long sessionId, String appId, String aesRequest) {
//		// TODO Auto-generated method stub
//		logger.info(sessionId + ":信用管家>>>拉取还款结果接口>>>进入getRepaymentResult()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>拉取还款结果接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			RepaymentResultReq repaymentResultReq = JSON.parseObject(requestJson, RepaymentResultReq.class);
//			if (null == repaymentResultReq) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口repaymentResultReq为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			String thirdOrderNo = repaymentResultReq.getThirdOrderNo();
//			int number = repaymentResultReq.getNumber(); // 还款期数
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口thirdOrderNo为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("订单不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口bwOrder为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			/*
//			 * if (bwOrder.getStatusId() != 9) {
//			 * rsResponse.setCode(RsResponse.CODE_PARMERR);
//			 * rsResponse.setMessage("还款结果查询接口还未放款");
//			 * logger.info("结束还款结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//			 * return rsResponse; }
//			 */
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("用户不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口borrower为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (null == bwBankCard) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("银行卡不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口bwBankCard为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
//			bwRepaymentPlan.setOrderId(bwOrder.getId());
//			bwRepaymentPlan.setNumber(number);
//			bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
//			if (null == bwRepaymentPlan) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("还款计划不存在");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口bwRepaymentPlan为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("thirdOrderNo", thirdOrderNo);// 订单唯一编号
//			map.put("number", bwRepaymentPlan.getNumber()); // 期数
//
//			// 查询逾期
//			int repay_money = 0;// 还款金额
//			double repayPenaltyInt = 0d;
//			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//			bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//			if (null != bwOverdueRecord) {
//				double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//						: bwOverdueRecord.getOverdueAccrualMoney();
//				double advance = bwOverdueRecord.getAdvance() == null ? 0D : bwOverdueRecord.getAdvance();
//				repayPenaltyInt = overdueAccrualMoney - advance;
//			}
//			if (2 == bwRepaymentPlan.getRepayStatus()) { // 已还款
//				repay_money = new Double(bwRepaymentPlan.getAlreadyRepayMoney() * 100).intValue(); // 已还金额
//				map.put("repay_money", repay_money);
//				map.put("status", "601"); // 还款成功
//				map.put("repay_date",  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(bwRepaymentPlan.getUpdateTime()));  // 还款时间
//
//			} else { // 未还款
//				repay_money = new Double((bwRepaymentPlan.getRealityRepayMoney() + repayPenaltyInt) * 100 ).intValue(); // 应还款金额
//				map.put("repay_money", repay_money);
//				map.put("status", "602"); // 未还款
//				map.put("repay_date",  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(bwRepaymentPlan.getRepayTime()));  // 还款时间
//				map.put("remark", "当前第:" + bwRepaymentPlan.getNumber() + "期未还款,应还款金额:" + repay_money + "单位:分");
//			}
//
//			// 对响应参数AES加密
//			logger.info(sessionId + ":信用管家>>>拉取还款结果接口>>>response加密前数据:" + JSON.toJSONString(map));
//			String response = AESUtil.Encrypt(JSON.toJSONString(map), AESKey);
//			xygjResponse.setResponse(response);
//			xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//			xygjResponse.setMsg("拉取还款结果成功");
//			return xygjResponse;
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error(sessionId + ":信用管家>>>拉取还款结果接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//	}
//
//	/**
//	 * 添加基本的运营商数据  通话记录
//	 */
//	private void addOrUpdateOperate(long sessionId, Long orderId, XygjOperator operator, Long borrowerId,
//			Integer channelId) throws Exception {
//
//		XygjOperatorBase base = operator.getBase(); // 用户基本信息
//		List<CallVo> callVoList = operator.getCallVOList(); // 通话详单
//		if (null != base) {
//			logger.info(sessionId + ":信用管家>>>开始处理运营商基础信息borrowerId:"+borrowerId);
//			// 基本的运营商数据
//			BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//			if (bwOperateBasic == null) {
//				bwOperateBasic = new BwOperateBasic();
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUserSource(base.getType());
//				bwOperateBasic.setIdCard(base.getIdCard());
//				bwOperateBasic.setAddr(base.getAddress());
//				bwOperateBasic.setRealName(base.getTruename());
//				bwOperateBasic.setPhoneRemain(base.getBalance());
//				bwOperateBasic.setPhone(base.getMobile());
//				bwOperateBasic.setRegTime(DrainageUtils.formatToDate(base.getOpenTime(), "yyy-MM-dd HH:mm:ss"));
//				// bwOperateBasic.setPhoneStatus(operatorInfoVo.getState() + "");// 0,欠费;1,正常
//				// bwOperateBasic.setPackageName(operatorInfoVo.getUsePackage());
//				// bwOperateBasic.setStarLevel(operatorInfoVo.getLevel());
//				bwOperateBasic.setCreateTime(new Date());
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasicService.save(bwOperateBasic);
//
//			} else {
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUserSource(base.getType());
//				bwOperateBasic.setIdCard(base.getIdCard());
//				bwOperateBasic.setAddr(base.getAddress());
//				bwOperateBasic.setRealName(base.getTruename());
//				bwOperateBasic.setPhoneRemain(base.getBalance());
//				bwOperateBasic.setPhone(base.getMobile());
//				bwOperateBasic.setRegTime(DrainageUtils.formatToDate(base.getOpenTime(), "yyy-MM-dd HH:mm:ss"));
//				// bwOperateBasic.setPhoneStatus(operatorInfoVo.getState() + "");// 0,欠费;1,正常
//				// bwOperateBasic.setPackageName(operatorInfoVo.getUsePackage());
//				// bwOperateBasic.setStarLevel(operatorInfoVo.getLevel());
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasicService.update(bwOperateBasic);
//
//			}
//			logger.info(sessionId + ":信用管家>>>结束处理运营商基础信息");
//		}
//
//		/**
//		 * 处理通话记录
//		 */
//		if (CollectionUtils.isNotEmpty(callVoList)) {
//			logger.info(sessionId + ":信用管家>>>开始更新通话记录......");
//			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			//String updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEsString(borrowerId); // 根据手机号查询最近一次抓取的通话记录时间
//			Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//			//Date callDate = new Date(Long.parseLong(updateTime));
//			//callDate = sdf_hms.parse(sdf_hms.format(callDate));
//			for (CallVo callVo : callVoList) {
//				Date jsonCallDate = sdf_hms.parse(callVo.getCalltime());
//				try {
//					if (null == callDate || jsonCallDate.after(callDate)) { // 通话记录采取最新追加的方式
//						BwOperateVoice bwOperateVoice = new BwOperateVoice();
//						bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
//						bwOperateVoice.setBorrower_id(borrowerId);
//
//						// 检验日期格式
//						String callTime = null;
//						callTime = sdf_hms.format(sdf_hms.parse(callVo.getCalltime()));
//						bwOperateVoice.setCall_time(callTime); // 通话时间
//						bwOperateVoice.setCall_type("主叫".equals(callVo.getCalltype()) ? 1 : 2); // 呼叫类型
//						bwOperateVoice.setReceive_phone(callVo.getCallphone()); // 对方号码
//						bwOperateVoice
//								.setTrade_addr(CommUtils.isNull(callVo.getHomearea()) ? "" : callVo.getHomearea()); // 通话地点
//						bwOperateVoice.setTrade_time(String.valueOf(callVo.getCalllong())); // 通话时长
//						// bwOperateVoice.setTrade_type("本地通话".equals(call.getCall_type()) ? 1 : 2); // 通信类型 1.本地通话,国内长途
//						bwOperateVoiceService.save(bwOperateVoice);
//					}
//				} catch (Exception e) {
//					logger.error(sessionId + ":信用管家>>>保存通话记录异常,忽略此条通话记录...", e);
//					continue;
//				}
//			}
//			logger.info(sessionId + ":信用管家>>>更新通话记录结束......");
//		}
//		thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);// 插入运营商认证记录
//	}
//	
//	
//	/**
//	 * 贷款预算接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public XygjResponse loanCalculation(long sessionId, String appId, String aesRequest) {
//		logger.info(sessionId + ":信用管家>>>贷款试算接口>>>进入loanCalculation()方法");
//		XygjResponse xygjResponse = new XygjResponse();
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":信用管家>>>贷款试算接口orderChannel为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String AESKey = XinYongGuanJiaConstant.xygjConfig.getString(AES_KEY + channelId);
//			String requestJson = AESUtil.Decrypt(aesRequest.replace(" ", "+"), AESKey);
//			logger.info(sessionId + ":信用管家>>>贷款试算接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>贷款试算接口requestJson为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// 解开request
//			Map map = JSON.parseObject(requestJson, Map.class);
//			if (null == map) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>贷款试算接口request为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//
//			logger.info(sessionId + ":信用管家>>>贷款试算接口channelId:" + channelId);
//			
//			
//			Double loanAmount = Double.parseDouble(String.valueOf(map.get("loanAmount")));//借款金额
//			//int loanPeriod = Integer.parseInt(map.get("loanPeriod"));//借款周期
//			//String periodUnit = String.valueOf(map.get("periodUnit"));//周期单位
//			//查询水象云产品
//			BwProductDictionary bwProductDictionary = bwProductDictionaryService
//								.findBwProductDictionaryById(7);
//			// 第四步，获取放款金额限制
//			Integer maxLoanAmount = bwProductDictionary.getMaxAmount();
//			Integer minLoanAmount = bwProductDictionary.getMinAmount();
//			Double interestRate = bwProductDictionary.getInterestRate();// 分期利息率
//
//			if (loanAmount > maxLoanAmount) {
//				//rsResponse.setCode(RsResponse.CODE_PARMERR);
//				//rsResponse.setMessage("借款金额不通过");
//				//logger.info(sessionId + "结束榕树贷款试算期接口异常，返回结果借款金额过大,参数loanAmount:"+loanAmount);
//				//return rsResponse;
//			} else if (loanAmount < minLoanAmount) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("借款金额不通过");
//				logger.info(sessionId + "结束信用管家贷款试算接口异常，返回结果借款金额过小,参数loanAmount:"+loanAmount);
//				return xygjResponse;
//			}
//			// 每期应还本金
//			//Double eachAmount = Double.valueOf(loanAmount) / 4;
//			// 每期应还利息
//			Double periodOne = RongShuUtils.calculateRepayMoney(loanAmount, 1, interestRate);
//			Double periodTwo = RongShuUtils.calculateRepayMoney(loanAmount, 2, interestRate);
//			Double periodThree = RongShuUtils.calculateRepayMoney(loanAmount, 3, interestRate);
//			Double periodFour = RongShuUtils.calculateRepayMoney(loanAmount, 4, interestRate);
//
//			Double periodAll = periodOne + periodTwo + periodThree + periodFour;	
//			Double amount = Double.valueOf(loanAmount) + periodAll;
//			
//			Map<String,Object> mapData = new TreeMap<String, Object>(); 
//			mapData.put("refundAmount", amount);//还款总金额
//			mapData.put("actualAmount", loanAmount);//实际到账金额
//			mapData.put("remark", "到账金额"+loanAmount+"元，手续费"+periodAll+"元，预计还款总金额"+amount+"元");//到账金额描述
//			String response = AESUtil.Encrypt(JSON.toJSONString(mapData), AESKey);
//			xygjResponse.setCode(XygjResponse.RESULT_SUCCESS);
//			xygjResponse.setMsg("查询贷款试算接口成功");
//			xygjResponse.setResponse(response);			
//			return xygjResponse;
//
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>银行预绑卡接口Service异常", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("系统异常,请稍后再试");
//			return xygjResponse;
//		}
//
//	}
//	
//}
