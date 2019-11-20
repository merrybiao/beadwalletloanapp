//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwKaniuBillResult;
//import com.waterelephant.entity.BwKaniuCalcuteresult;
//import com.waterelephant.entity.BwKaniuFriendResult;
//import com.waterelephant.entity.BwKaniuOperatorBasicInfo;
//import com.waterelephant.entity.BwKaniuPhoneCount;
//import com.waterelephant.entity.BwKaniuProvince;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.impl.BwKaniuBillResultServiceImpl;
//import com.waterelephant.service.impl.BwKaniuCalcuteresultServiceImpl;
//import com.waterelephant.service.impl.BwKaniuFriendResultServiceImpl;
//import com.waterelephant.service.impl.BwKaniuOperatorBasicInfoServiceImpl;
//import com.waterelephant.service.impl.BwKaniuPhoneCountServiceImpl;
//import com.waterelephant.service.impl.BwKaniuProvinceServiceImpl;
//import com.waterelephant.service.impl.BwOrderService;
//import com.waterelephant.sxyDrainage.entity.kaNiu.BankCalcuteResults;
//import com.waterelephant.sxyDrainage.entity.kaNiu.BillListVo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.BillResults;
//import com.waterelephant.sxyDrainage.entity.kaNiu.ContactListVo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.CreditCalcuteResults;
//import com.waterelephant.sxyDrainage.entity.kaNiu.FriendDataVo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.FriendResultsVo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KNOperatorBasicInfo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuPushUserReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.OperatorInfoVo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.PhoneCountPojos;
//import com.waterelephant.sxyDrainage.entity.kaNiu.ProvincePojos;
//import com.waterelephant.sxyDrainage.service.AsyncKaNiuService;
//import com.waterelephant.sxyDrainage.service.BqsCheckService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.KaNiuUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//
//@Service
//public class AsyncKaNiuServiceImpl implements AsyncKaNiuService {
//
//	private final Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private BwKaniuPhoneCountServiceImpl bwKaniuPhoneCountService;
//	@Autowired
//	private BwKaniuBillResultServiceImpl bwKaniuBillResultService;
//	@Autowired
//	private BwKaniuCalcuteresultServiceImpl bwKaniuCalcuteresultService;
//	@Autowired
//	private BwKaniuFriendResultServiceImpl bwKaniuFriendResultService;
//	@Autowired
//	private BwKaniuOperatorBasicInfoServiceImpl bwKaniuOperatorBasicInfoService;
//	@Autowired
//	private BwKaniuProvinceServiceImpl bwKaniuProvinceService;
//	@Autowired
//	private BqsCheckService bqsCheckService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//	@Autowired
//	private BwOperateVoiceService bwOperateVoiceService;
//	@Autowired
//	private BwOrderProcessRecordService bwOrderProcessRecordService;
//	@Autowired
//	private BwOrderService bwOrderService;
//
//	/**
//	 * 
//	 */
//	@Async("taskExecutor")
//	@Override
//	public void addAsynkaNiuOperator(long sessionId, String thirdOrderNo, BwOrder bwOrder, BwBorrower borrower,
//			KaNiuPushUserReq kaNiuPushUserReq) {
//		try {
//			if (null == kaNiuPushUserReq) {
//				return;
//			}
//			KNOperatorBasicInfo knOperatorBasicInfo = kaNiuPushUserReq.getOperatorBasicInfo();
//			// 运营商
//			OperatorInfoVo operatorInfoVo = kaNiuPushUserReq.getOperatorInfo();
//			String frontFile = kaNiuPushUserReq.getFrontImgURL();
//			String backFile = kaNiuPushUserReq.getBackImgURL();
//			String natureFile = kaNiuPushUserReq.getFaceImage();
//			long orderId = bwOrder.getId();
//			logger.info(sessionId + ">>>开始处理卡牛异步数据处理:orderId=" + orderId);
//			// 认证图片
//			if (StringUtils.isNotBlank(frontFile)) {
//				String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01"); // 身份证正面照
//				logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId,
//						borrower.getId(), 0); // 保存身份证正面照
//				logger.info(sessionId + ">>> 身份证正面保存 " + result);
//			}
//			if (StringUtils.isNotBlank(backFile)) {
//				String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02"); // 身份证反面照
//				logger.info(sessionId + ">>> 身份证反面 " + backImage);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId,
//						borrower.getId(), 0); // 保存身份证反面照
//				logger.info(sessionId + ">>> 身份证反面保存 " + result);
//			}
//			if (StringUtils.isNotBlank(natureFile)) {
//				String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03"); // 手持照
//				logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId,
//						borrower.getId(), 0); // 保存手持照
//				logger.info(sessionId + ">>> 手持照/人脸保存 " + result);
//			}
//			// 运营商
//			if (null != operatorInfoVo) {
//				BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrower.getId());
//				if (bwOperateBasic == null) {
//					bwOperateBasic = new BwOperateBasic();
//					bwOperateBasic.setBorrowerId(borrower.getId());
//					bwOperateBasic.setUserSource(KaNiuUtil.toOperSou(operatorInfoVo.getType()));
//					bwOperateBasic.setIdCard(operatorInfoVo.getIdcardNo());
//					bwOperateBasic.setAddr(
//							operatorInfoVo.getProvince() + operatorInfoVo.getCity() + operatorInfoVo.getLinkAddr());
//					bwOperateBasic.setRealName(operatorInfoVo.getUserName());// operatorInfoVo.getRealName()
//					bwOperateBasic.setPhoneRemain(operatorInfoVo.getBalance());
//					bwOperateBasic.setPhone(operatorInfoVo.getPhone());
//					bwOperateBasic
//							.setRegTime(DrainageUtils.formatToDate(operatorInfoVo.getOpenTime(), "yyy-MM-dd HH:mm:ss"));
//					bwOperateBasic.setPhoneStatus(operatorInfoVo.getState() + "");// 0,欠费;1,正常
//					bwOperateBasic.setPackageName(operatorInfoVo.getUsePackage());
//					bwOperateBasic.setStarLevel(operatorInfoVo.getLevel());
//					bwOperateBasic.setCreateTime(new Date());
//					bwOperateBasic.setUpdateTime(new Date());
//					bwOperateBasicService.save(bwOperateBasic);
//				} else {
//					bwOperateBasic.setBorrowerId(borrower.getId());
//					bwOperateBasic.setUserSource(KaNiuUtil.toOperSou(operatorInfoVo.getType()));
//					bwOperateBasic.setIdCard(operatorInfoVo.getIdcardNo());
//					bwOperateBasic.setAddr(
//							operatorInfoVo.getProvince() + operatorInfoVo.getCity() + operatorInfoVo.getLinkAddr());
//					bwOperateBasic.setRealName(operatorInfoVo.getUserName());// operatorInfoVo.getRealName()
//					bwOperateBasic.setPhoneRemain(operatorInfoVo.getBalance());
//					bwOperateBasic.setPhone(operatorInfoVo.getPhone());
//					bwOperateBasic
//							.setRegTime(DrainageUtils.formatToDate(operatorInfoVo.getOpenTime(), "yyy-MM-dd HH:mm:ss"));
//					bwOperateBasic.setPhoneStatus(operatorInfoVo.getState() + "");// 0,欠费;1,正常
//					bwOperateBasic.setPackageName(operatorInfoVo.getUsePackage());
//					bwOperateBasic.setStarLevel(operatorInfoVo.getLevel());
//					bwOperateBasic.setUpdateTime(new Date());
//					bwOperateBasicService.update(bwOperateBasic);
//				}
//				logger.info(sessionId + ">>> 处理运营商信息");
//				// 通话记录
//				List<BillListVo> billList = operatorInfoVo.getBillList();
//				if (CollectionUtils.isNotEmpty(billList)) {
//					Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
//					BwOperateVoice bwOperateVoice = null;
//					SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//					for (BillListVo billListVo : billList) {
//						List<ContactListVo> voiceList = billListVo.getContactList();
//						if (CollectionUtils.isNotEmpty(voiceList)) {
//							for (ContactListVo contactListVo : voiceList) {
//								if (StringUtils.isNotBlank(contactListVo.getOtherSidePhone())
//										&& contactListVo.getOtherSidePhone().length() > 20) {
//									logger.info(sessionId + ">>> 通话记录中，对方号码过长，略过次条记录>ReceivePhone = "
//											+ contactListVo.getOtherSidePhone());
//									continue;
//								}
//								try {
//									bwOperateVoice = new BwOperateVoice();
//									Date jsonCallData = sdf_hms.parse(contactListVo.getCallTime());
//									if (callDate == null || jsonCallData.after(callDate)) { // 通话记录采取最新追加的方式
//										if (StringUtils.isNotBlank(contactListVo.getCallType())) {
//											if ((contactListVo.getCallType().contains("本地"))) {
//												bwOperateVoice.setTrade_type(1);
//											} else {
//												bwOperateVoice.setTrade_type(2);
//											}
//										}
//										if (("0").equals(contactListVo.getCallMethod() + "")) {// 0,主叫；1,被叫
//											bwOperateVoice.setCall_type(1);// 主叫：1，被叫：2
//										} else if (("1").equals(contactListVo.getCallMethod() + "")) {
//											bwOperateVoice.setCall_type(2);
//										}
//										if (StringUtils.isNotBlank(contactListVo.getCallDuration())) {
//											try {
//												bwOperateVoice.setTrade_time(Math.ceil(
//														NumberUtils.toDouble(contactListVo.getCallDuration()) * 60)
//														+ "");
//											} catch (Exception e) {
//												bwOperateVoice.setTrade_time(contactListVo.getCallDuration());
//											}
//										}
//										bwOperateVoice.setCall_time(contactListVo.getCallTime());
//										bwOperateVoice.setTrade_addr(contactListVo.getOtherSidePlace());
//										bwOperateVoice.setReceive_phone(contactListVo.getOtherSidePhone());
//										bwOperateVoice.setUpdateTime(new Date());
//										bwOperateVoice.setBorrower_id(borrower.getId());
//										bwOperateVoiceService.save(bwOperateVoice);
//									}
//								} catch (Exception e) {
//									logger.error(sessionId + ">>> 插入单条通话记录异常，忽略该条记录" + e.getMessage());
//								}
//							}
//						}
//					}
//				}
//				thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, bwOrder.getChannel());// 插入运营商认证记录
//				logger.info(sessionId + ">>> 处理通话记录信息 ");
//			}
//			if (null != knOperatorBasicInfo) {
//				BwKaniuOperatorBasicInfo bkobi = new BwKaniuOperatorBasicInfo();
//				bkobi.setOrderId(orderId);
//				List<BwKaniuOperatorBasicInfo> BwKaniuOperatorBasicInfoList = bwKaniuOperatorBasicInfoService
//						.select(bkobi);
//				if (CollectionUtils.isEmpty(BwKaniuOperatorBasicInfoList)) {
//					bkobi = new BwKaniuOperatorBasicInfo();
//					bkobi.setAge(knOperatorBasicInfo.getAge());
//					bkobi.setAveincall(knOperatorBasicInfo.getAveInCall());
//					bkobi.setAveincallcount(knOperatorBasicInfo.getAveInCallCount());
//					bkobi.setAveoutcall(knOperatorBasicInfo.getAveOutCall());
//					bkobi.setAveoutcallcount(knOperatorBasicInfo.getAveOutCallCount());
//					bkobi.setBusiness(knOperatorBasicInfo.getBusiness());
//					bkobi.setConcatmacao(knOperatorBasicInfo.getConcatMacao());
//					bkobi.setContact110(knOperatorBasicInfo.getContact110());
//					bkobi.setContact120(knOperatorBasicInfo.getContact120());
//					bkobi.setCrawlidcardno(knOperatorBasicInfo.getCrawlIdCardNo());
//					bkobi.setCrawlname(knOperatorBasicInfo.getCrawlName());
//					bkobi.setCreateTime(new Date());
//					bkobi.setEnteridcardno(knOperatorBasicInfo.getEnterIdCardNo());
//					bkobi.setEntername(knOperatorBasicInfo.getEnterName());
//					bkobi.setFriendprovince(knOperatorBasicInfo.getFriendProvince());
//					bkobi.setIsblacklist(knOperatorBasicInfo.getIsBlacklist());
//					bkobi.setIscallfriendone(knOperatorBasicInfo.getIsCallFriendOne());
//					bkobi.setIscallfriendtwo(knOperatorBasicInfo.getIsCallFriendTwo());
//					bkobi.setIscallfriendthree(knOperatorBasicInfo.getIsCallFriendThree());
//					bkobi.setIscheckname(knOperatorBasicInfo.getIsCheckName());
//					bkobi.setIsidcardno(knOperatorBasicInfo.getIsIdCardNo());
//					bkobi.setIsphoneoneblacklist(knOperatorBasicInfo.getIsPhoneOneBlackList());
//					bkobi.setIsphonetwoblacklist(knOperatorBasicInfo.getIsPhoneTwoBlackList());
//					bkobi.setIsphonethreeblacklist(knOperatorBasicInfo.getIsPhoneThreeBlackList());
//					bkobi.setIsrealname(knOperatorBasicInfo.getIsRealName());
//					bkobi.setLinkaddr(knOperatorBasicInfo.getLinkAddr());
//					bkobi.setMutualcontactcount(knOperatorBasicInfo.getMutualContactCount());
//					bkobi.setOnlinetime(knOperatorBasicInfo.getOnlineTime());
//					bkobi.setOrderId(orderId);
//					bkobi.setOrigin(knOperatorBasicInfo.getOrigin());
//					bkobi.setPhone(knOperatorBasicInfo.getPhone());
//					bkobi.setPlace(knOperatorBasicInfo.getPlace());
//					bkobi.setProvince(knOperatorBasicInfo.getProvince());
//					bkobi.setProvincepercent(knOperatorBasicInfo.getProvincePercent());
//					bkobi.setSex(knOperatorBasicInfo.getSex());
//					bkobi.setStateone(knOperatorBasicInfo.getStateOne());
//					bkobi.setStatetwo(knOperatorBasicInfo.getStateTwo());
//					bkobi.setStatethree(knOperatorBasicInfo.getStateThree());
//					bkobi.setThreamonthcontactcount(knOperatorBasicInfo.getThreaMonthContactCount());
//					bkobi.setThreemonthcontacttime(knOperatorBasicInfo.getThreeMonthContactTime());
//					bkobi.setTotalcontacttime(knOperatorBasicInfo.getTotalContactTime());
//					bkobi.setUpdateTime(new Date());
//					bwKaniuOperatorBasicInfoService.save(bkobi);
//				} else {
//					bkobi = BwKaniuOperatorBasicInfoList.get(0);
//					bkobi.setAge(knOperatorBasicInfo.getAge());
//					bkobi.setAveincall(knOperatorBasicInfo.getAveInCall());
//					bkobi.setAveincallcount(knOperatorBasicInfo.getAveInCallCount());
//					bkobi.setAveoutcall(knOperatorBasicInfo.getAveOutCall());
//					bkobi.setAveoutcallcount(knOperatorBasicInfo.getAveOutCallCount());
//					bkobi.setBusiness(knOperatorBasicInfo.getBusiness());
//					bkobi.setConcatmacao(knOperatorBasicInfo.getConcatMacao());
//					bkobi.setContact110(knOperatorBasicInfo.getContact110());
//					bkobi.setContact120(knOperatorBasicInfo.getContact120());
//					bkobi.setCrawlidcardno(knOperatorBasicInfo.getCrawlIdCardNo());
//					bkobi.setCrawlname(knOperatorBasicInfo.getCrawlName());
//					bkobi.setEnteridcardno(knOperatorBasicInfo.getEnterIdCardNo());
//					bkobi.setEntername(knOperatorBasicInfo.getEnterName());
//					bkobi.setFriendprovince(knOperatorBasicInfo.getFriendProvince());
//					bkobi.setIsblacklist(knOperatorBasicInfo.getIsBlacklist());
//					bkobi.setIscallfriendone(knOperatorBasicInfo.getIsCallFriendOne());
//					bkobi.setIscallfriendtwo(knOperatorBasicInfo.getIsCallFriendTwo());
//					bkobi.setIscallfriendthree(knOperatorBasicInfo.getIsCallFriendThree());
//					bkobi.setIscheckname(knOperatorBasicInfo.getIsCheckName());
//					bkobi.setIsidcardno(knOperatorBasicInfo.getIsIdCardNo());
//					bkobi.setIsphoneoneblacklist(knOperatorBasicInfo.getIsPhoneOneBlackList());
//					bkobi.setIsphonetwoblacklist(knOperatorBasicInfo.getIsPhoneTwoBlackList());
//					bkobi.setIsphonethreeblacklist(knOperatorBasicInfo.getIsPhoneThreeBlackList());
//					bkobi.setIsrealname(knOperatorBasicInfo.getIsRealName());
//					bkobi.setLinkaddr(knOperatorBasicInfo.getLinkAddr());
//					bkobi.setMutualcontactcount(knOperatorBasicInfo.getMutualContactCount());
//					bkobi.setOnlinetime(knOperatorBasicInfo.getOnlineTime());
//					bkobi.setOrigin(knOperatorBasicInfo.getOrigin());
//					bkobi.setPhone(knOperatorBasicInfo.getPhone());
//					bkobi.setPlace(knOperatorBasicInfo.getPlace());
//					bkobi.setProvince(knOperatorBasicInfo.getProvince());
//					bkobi.setProvincepercent(knOperatorBasicInfo.getProvincePercent());
//					bkobi.setSex(knOperatorBasicInfo.getSex());
//					bkobi.setStateone(knOperatorBasicInfo.getStateOne());
//					bkobi.setStatetwo(knOperatorBasicInfo.getStateTwo());
//					bkobi.setStatethree(knOperatorBasicInfo.getStateThree());
//					bkobi.setThreamonthcontactcount(knOperatorBasicInfo.getThreaMonthContactCount());
//					bkobi.setThreemonthcontacttime(knOperatorBasicInfo.getThreeMonthContactTime());
//					bkobi.setTotalcontacttime(knOperatorBasicInfo.getTotalContactTime());
//					bkobi.setUpdateTime(new Date());
//					bwKaniuOperatorBasicInfoService.save(bkobi);
//				}
//				logger.info(sessionId + ">>>bwKaniuOperatorBasicInfo:orderId=" + orderId);
//
//				List<BillResults> billS = knOperatorBasicInfo.getBillResults();
//				if (CollectionUtils.isNotEmpty(billS)) {
//					BwKaniuBillResult bwKaniuBillResult = new BwKaniuBillResult();
//					bwKaniuBillResult.setOrderId(orderId);
//					int count = bwKaniuBillResultService.selectCount(bwKaniuBillResult);
//					if (count > 0 && billS.size() > 0) {
//						bwKaniuBillResultService.delete(bwKaniuBillResult);
//					}
//					for (BillResults b : billS) {
//						bwKaniuBillResult = new BwKaniuBillResult();
//						bwKaniuBillResult.setOrderId(orderId);
//						bwKaniuBillResult.setBasicinfoId(bkobi.getId());
//						bwKaniuBillResult.setBusiness(b.getBusiness());
//						bwKaniuBillResult.setContactcost(b.getContactCost());
//						bwKaniuBillResult.setCreateTime(new Date());
//						bwKaniuBillResult.setIncalltime(b.getInCallTime());
//						bwKaniuBillResult.setMonth(b.getMonth());
//						bwKaniuBillResult.setMsgcount(b.getMsgCount());
//						bwKaniuBillResult.setOutcalltime(b.getOutCallTime());
//						bwKaniuBillResult.setPhone(b.getPhone());
//						bwKaniuBillResult.setProvince(b.getProvince());
//						try {
//							bwKaniuBillResultService.save(bwKaniuBillResult);
//						} catch (Exception e) {
//							logger.error("bwKaniuBillResult写表失败，跳过此条数据：" + orderId);
//						}
//					}
//				}
//				logger.info(sessionId + ">>>bwKaniuBillResult:orderId=" + orderId);
//
//				List<CreditCalcuteResults> CreditCalcuteResultS = knOperatorBasicInfo.getCreditCalcuteResults();
//				if (CollectionUtils.isNotEmpty(CreditCalcuteResultS)) {
//					BwKaniuCalcuteresult bwKaniuCalcuteresult = new BwKaniuCalcuteresult();
//					bwKaniuCalcuteresult.setOrderId(orderId);
//					bwKaniuCalcuteresult.setCardType("2");
//					int count = bwKaniuCalcuteresultService.selectCount(bwKaniuCalcuteresult);
//					if (count > 0 && CreditCalcuteResultS.size() > 0) {
//						bwKaniuCalcuteresultService.delete(bwKaniuCalcuteresult);
//					}
//					for (CreditCalcuteResults c : CreditCalcuteResultS) {
//						bwKaniuCalcuteresult = new BwKaniuCalcuteresult();
//						bwKaniuCalcuteresult.setOrderId(orderId);
//						bwKaniuCalcuteresult.setBasicinfoId(bkobi.getId());
//						bwKaniuCalcuteresult.setCardType("2");
//						bwKaniuCalcuteresult.setBank(c.getBank());
//						bwKaniuCalcuteresult.setContactcount(c.getContactCount());
//						bwKaniuCalcuteresult.setContacttime(c.getContactTime());
//						bwKaniuCalcuteresult.setIncallcount(c.getInCallCount());
//						bwKaniuCalcuteresult.setIncalltime(c.getInCallTime());
//						bwKaniuCalcuteresult.setOutcalltime(c.getOutCallTime());
//						bwKaniuCalcuteresult.setOutcallcount(c.getOutCallCount());
//						bwKaniuCalcuteresult.setCreateTime(new Date());
//						try {
//							bwKaniuCalcuteresultService.save(bwKaniuCalcuteresult);
//						} catch (Exception e) {
//							logger.error("bwKaniuCalcuteresult写表失败，跳过此条数据：" + orderId + ":" + e);
//						}
//					}
//				}
//				logger.info(sessionId + ">>>bwKaniuCalcuteresult:orderId=" + orderId);
//
//				List<BankCalcuteResults> bankCalcuteResults = knOperatorBasicInfo.getBankCalcuteResults();
//				if (CollectionUtils.isNotEmpty(bankCalcuteResults)) {
//					BwKaniuCalcuteresult bwKaniuCalcuteresult = new BwKaniuCalcuteresult();
//					bwKaniuCalcuteresult.setOrderId(orderId);
//					bwKaniuCalcuteresult.setCardType("1");
//					int count = bwKaniuCalcuteresultService.selectCount(bwKaniuCalcuteresult);
//					if (count > 0 && bankCalcuteResults.size() > 0) {
//						bwKaniuCalcuteresultService.delete(bwKaniuCalcuteresult);
//					}
//					for (BankCalcuteResults c : bankCalcuteResults) {
//						bwKaniuCalcuteresult = new BwKaniuCalcuteresult();
//						bwKaniuCalcuteresult.setOrderId(orderId);
//						bwKaniuCalcuteresult.setBasicinfoId(bkobi.getId());
//						bwKaniuCalcuteresult.setCardType("1");
//						bwKaniuCalcuteresult.setBank(c.getBank());
//						bwKaniuCalcuteresult.setContactcount(c.getContactCount());
//						bwKaniuCalcuteresult.setContacttime(c.getContactTime());
//						bwKaniuCalcuteresult.setIncallcount(c.getInCallCount());
//						bwKaniuCalcuteresult.setIncalltime(c.getInCallTime());
//						bwKaniuCalcuteresult.setOutcalltime(c.getOutCallTime());
//						bwKaniuCalcuteresult.setOutcallcount(c.getOutCallCount());
//						bwKaniuCalcuteresult.setCreateTime(new Date());
//						try {
//							bwKaniuCalcuteresultService.save(bwKaniuCalcuteresult);
//						} catch (Exception e) {
//							logger.error("bwKaniuCalcuteresult写表失败，跳过此条数据：" + orderId + ":" + e);
//						}
//					}
//				}
//				logger.info(sessionId + ">>>bwKaniuCalcuteresult:orderId=" + orderId);
//
//				List<FriendResultsVo> friendResultS = knOperatorBasicInfo.getFriendResults();
//				if (CollectionUtils.isNotEmpty(friendResultS)) {
//					for (FriendResultsVo f : friendResultS) {
//						List<FriendDataVo> flist = f.getData();
//						if (CollectionUtils.isNotEmpty(flist)) {
//							BwKaniuFriendResult bwKaniuFriendResult = new BwKaniuFriendResult();
//							bwKaniuFriendResult.setOrderId(orderId);
//							bwKaniuFriendResult.setPhone(f.getPhone());
//							int count = bwKaniuFriendResultService.selectCount(bwKaniuFriendResult);
//							if (count > 0 && flist.size() > 0) {
//								bwKaniuFriendResultService.delete(bwKaniuFriendResult);
//							}
//							for (FriendDataVo friendDataVo : flist) {
//								bwKaniuFriendResult = new BwKaniuFriendResult();
//								bwKaniuFriendResult.setOrderId(orderId);
//								bwKaniuFriendResult.setBasicinfoId(bkobi.getId());
//								bwKaniuFriendResult.setCreateTime(new Date());
//								bwKaniuFriendResult.setPhone(f.getPhone());
//								bwKaniuFriendResult.setCallduration(friendDataVo.getCallDuration());
//								bwKaniuFriendResult.setCounts(friendDataVo.getCounts());
//								bwKaniuFriendResult.setMonth(friendDataVo.getMonth());
//								try {
//									bwKaniuFriendResultService.save(bwKaniuFriendResult);
//								} catch (Exception e) {
//									logger.error("bwKaniuFriendResult写表失败，跳过此条数据：" + orderId + ":" + e);
//								}
//							}
//						}
//					}
//				}
//				logger.info(sessionId + ">>>bwKaniuFriendResult:orderId=" + orderId);
//
//				List<PhoneCountPojos> phoneCountPojoS = knOperatorBasicInfo.getPhoneCountPojos();
//				if (CollectionUtils.isNotEmpty(phoneCountPojoS)) {
//					BwKaniuPhoneCount bwKaniuPhoneCount = new BwKaniuPhoneCount();
//					bwKaniuPhoneCount.setOrderId(orderId);
//					int count = bwKaniuPhoneCountService.selectCount(bwKaniuPhoneCount);
//					if (count > 0 && phoneCountPojoS.size() > 0) {
//						bwKaniuPhoneCountService.delete(bwKaniuPhoneCount);
//					}
//					for (PhoneCountPojos p : phoneCountPojoS) {
//						bwKaniuPhoneCount = new BwKaniuPhoneCount();
//						bwKaniuPhoneCount.setOrderId(orderId);
//						bwKaniuPhoneCount.setBasicinfoId(bkobi.getId());
//						bwKaniuPhoneCount.setCreateTime(new Date());
//						if (StringUtils.isBlank(p.getPhone()) || p.getPhone().length() > 20) {
//							logger.info(sessionId + ">>>bwKaniuFriendResult:orderId=" + orderId + ">>>" + p.getPhone());
//							continue;
//						}
//						bwKaniuPhoneCount.setPhone(p.getPhone());
//						bwKaniuPhoneCount.setContactcount(p.getContactCount());
//						bwKaniuPhoneCount.setContacttime(p.getContactTime());
//						bwKaniuPhoneCount.setFirstcontact(p.getFirstContact());
//						bwKaniuPhoneCount.setIncallcount(p.getInCallCount());
//						bwKaniuPhoneCount.setIncalltime(p.getInCallTime());
//						bwKaniuPhoneCount.setLabel(p.getLabel());
//						bwKaniuPhoneCount.setLastcontact(p.getLastContact());
//						bwKaniuPhoneCount.setMonthtimes(p.getMonthTimes());
//						bwKaniuPhoneCount.setOutcallcount(p.getOutCallCount());
//						bwKaniuPhoneCount.setOutcalltime(p.getOutCallTime());
//						bwKaniuPhoneCount.setProvince(p.getProvince());
//						bwKaniuPhoneCount.setWeekstimes(p.getWeeksTimes());
//						try {
//							bwKaniuPhoneCountService.save(bwKaniuPhoneCount);
//						} catch (Exception e) {
//							logger.error("bwKaniuPhoneCount写表失败，跳过此条数据：" + orderId + ":" + e);
//						}
//					}
//					logger.info(sessionId + ">>>bwKaniuPhoneCount:orderId=" + orderId);
//				}
//
//				List<ProvincePojos> provincePojoS = knOperatorBasicInfo.getProvincePojos();
//				if (CollectionUtils.isNotEmpty(provincePojoS)) {
//					BwKaniuProvince bwKaniuProvince = new BwKaniuProvince();
//					bwKaniuProvince.setOrderId(orderId);
//					int count = bwKaniuProvinceService.selectCount(bwKaniuProvince);
//					if (count > 0 && phoneCountPojoS.size() > 0) {
//						bwKaniuProvinceService.delete(bwKaniuProvince);
//					}
//					for (ProvincePojos p : provincePojoS) {
//						bwKaniuProvince = new BwKaniuProvince();
//						bwKaniuProvince.setOrderId(orderId);
//						bwKaniuProvince.setBasicinfoId(bkobi.getId());
//						bwKaniuProvince.setCreateTime(new Date());
//						bwKaniuProvince.setContactcount(p.getContactCount());
//						bwKaniuProvince.setContacttime(p.getContactTime());
//						bwKaniuProvince.setIncallcount(p.getInCallCount());
//						bwKaniuProvince.setIncalltime(p.getInCallTime());
//						bwKaniuProvince.setOutcallcount(p.getOutCallCount());
//						bwKaniuProvince.setOutcalltime(p.getOutCallTime());
//						bwKaniuProvince.setPhonecount(p.getPhoneCount());
//						bwKaniuProvince.setProvince(p.getProvince());
//						try {
//							bwKaniuProvinceService.save(bwKaniuProvince);
//						} catch (Exception e) {
//							logger.error("bwKaniuProvince写表失败，跳过此条数据：" + orderId + ":" + e);
//						}
//					}
//					logger.info(sessionId + ">>>bwKaniuProvince:orderId=" + orderId);
//				}
//			}
//			String isExists = RedisUtils.getset("tripartite:kaniu:pushorder:" + bwOrder.getId(), "2");
//			logger.info(sessionId + ">>> 添加 KEY > tripartite:kaniu:pushorder:" + bwOrder.getId());
//			if ("1".equals(isExists)) {
//				doSubmit(sessionId, bwOrder, borrower, thirdOrderNo);
//			}
//			logger.info(sessionId + ">>>结束处理卡牛异步数据处理:orderId=" + orderId);
//		} catch (Exception e) {
//			logger.error(sessionId + ">>>结束处理卡牛异步数据异常:thirdOrderNo=" + thirdOrderNo + ">>>" + e);
//		}
//	}
//
//	@Override
//	public void doSubmit(Long sessionId, BwOrder bwOrder, BwBorrower borrower, String thirdOrderNo) {
//		if (1L != bwOrder.getStatusId()) {
//			logger.info(sessionId + ">>>处理卡牛提交审核数据处理:订单状态>" + bwOrder.getStatusId());
//			return;
//		}
//		bwOrder.setStatusId(2L);
//		bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//		bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//		bwOrderService.updateBwOrder(bwOrder);
//		BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//		bwOrderProcessRecord.setSubmitTime(new Date());
//		bwOrderProcessRecord.setOrderId(bwOrder.getId());
//		bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//		logger.info(sessionId + ">>> 更改订单进行时间");
//
//		try {
//			String res = bqsCheckService.doBqsCheck(sessionId, bwOrder.getId() + "");
//			logger.info(sessionId + "白骑士校验>>orderId" + bwOrder.getId() + ">>>res" + ("0".equals(res) ? "成功" : "失败"));
//		} catch (Exception e) {
//			logger.error(sessionId + "调用白骑士校验异常" + e);
//		}
//
//		HashMap<String, String> hm = new HashMap<>();
//		hm.put("channelId", bwOrder.getChannel() + "");
//		hm.put("orderId", String.valueOf(bwOrder.getId()));
//		hm.put("orderStatus", 2 + "");
//		hm.put("result", "");
//		String hmData = JSON.toJSONString(hm);
//		RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//		// 放入redis
//		SystemAuditDto systemAuditDto = new SystemAuditDto();
//		systemAuditDto.setIncludeAddressBook(0);
//		systemAuditDto.setOrderId(bwOrder.getId());
//		systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//		systemAuditDto.setName(borrower.getName());
//		systemAuditDto.setPhone(borrower.getPhone());
//		systemAuditDto.setIdCard(borrower.getIdCard());
//		systemAuditDto.setChannel(bwOrder.getChannel());
//		systemAuditDto.setThirdOrderId(thirdOrderNo);
//		systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//		RedisUtils.hset(SystemConstant.AUDIT_KEY, bwOrder.getId() + "", JsonUtils.toJson(systemAuditDto));
//		logger.info(sessionId + ">>> 修改订单状态，并放入redis");
//		// 更改订单进行时间
//		RedisUtils.del("tripartite:kaniu:pushorder:" + bwOrder.getId());
//		logger.info(sessionId + ">>> 删除KEY > tripartite:kaniu:pushorder: " + bwOrder.getId());
//	}
//
//}
