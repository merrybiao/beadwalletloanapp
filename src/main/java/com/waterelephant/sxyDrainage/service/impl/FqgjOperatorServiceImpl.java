///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.waterelephant.drainage.entity.common.PushOrderRequest;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.mapper.BwContactListMapper;
//import com.waterelephant.mapper.BwOperateVoiceMapper;
//import com.waterelephant.service.*;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.*;
//import com.waterelephant.sxyDrainage.entity.fqgj.*;
//import com.waterelephant.sxyDrainage.mapper.FqgjCallMapper;
//import com.waterelephant.sxyDrainage.mapper.FqgjMsgBillMapper;
//import com.waterelephant.sxyDrainage.mapper.FqgjPhoneBillMapper;
//import com.waterelephant.sxyDrainage.mapper.FqgjRechargeMapper;
//import com.waterelephant.sxyDrainage.utils.fenqiguanjia.FenQiGuanJiaConstant;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.UploadToCssUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.log4j.Logger;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.sxyDrainage.service.FqgjOperatorService;
//import com.waterelephant.utils.CommUtils;
//
///**
// *
// *
// * Module:
// *
// * FqgjOperatorServiceImpl.java
// *
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Service
//public class FqgjOperatorServiceImpl implements FqgjOperatorService {
//
//	private Logger logger = Logger.getLogger(FqgjOperatorServiceImpl.class);
//
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//
//	@Autowired
//	private FqgjDeviceInfoService fqgjDeviceInfoService;
//
//	@Autowired
//	private IBwBorrowerService bwBorrowerService;
//
//	@Autowired
//	private IBwContactListService bwContactListService;
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    @Autowired
//	private BwContactListMapper bwContactListMapper;
//
//	/**
//	 *
//	 * @see com.waterelephant.sxyDrainage.service.FqgjOperatorService(java.lang.String)
//	 */
//	@Async("taskExecutor")
//	@Override
//	public void saveOperator(String operatorData, Long orderId,Long borrowerId) {
//		logger.info(orderId + "开始进入FqgjOperatorServiceImpl.saveOperator");
//		try {
//
//			if (CommUtils.isNull(operatorData)) {
//				logger.info(orderId+"传入数据operatorData为空");
//				return;
//			}
//
//			Add_info addInfo = JSONObject.parseObject(operatorData, Add_info.class);
//
//			if(CommUtils.isNull(addInfo)){
//				logger.info(orderId+"传入数据addInfo为空");
//				return;
//			}
//
//			Mobile mobile = addInfo.getMobile();
//
//			if (CommUtils.isNull(mobile)) {
//				logger.info(orderId+"传入数据mobile为空");
//				return;
//			}
//            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//
//            try {
//			    //风控数据
//                saveNewData(mobile,orderId,sqlSession);
//            } catch (Exception e) {
//                logger.error(orderId+"存储风控数据异常",e);
//			}
//
//			logger.info(orderId + "结束FqgjOperatorServiceImpl.saveOperator");
//		} catch (Exception e) {
//			logger.error(orderId+"存储运营商数据发生异常", e);
//		}
//
//	}
//
//	@Transactional(rollbackFor = RuntimeException.class)
//	@Async("taskExecutor")
//	@Override
//	public void savePhotoes(PushOrderRequest pushOrderRequest, Long orderId,Long borrowerId,long sessionId,Contacts contacts) {
//
//        logger.info(orderId+"开始进入FqgjOperatorServiceImpl.savePhotoes");
//		if(CommUtils.isNull(pushOrderRequest)){
//			logger.info(orderId+"处理图片传入参数为空");
//			return;
//		}
//		String frontFile = pushOrderRequest.getIdCardFrontImage();
//		String backFile = pushOrderRequest.getIdCardBackImage();
//		String natureFile = pushOrderRequest.getIdCardHanderImage();
//
//		if (StringUtils.isNotBlank(frontFile)) {
//			String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01");
//			// 身份证正面照
//			logger.info(orderId + ">>> 身份证正面 " + frontImage);
//			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0);
//			// 保存身份证正面照
//		}
//		if (StringUtils.isNotBlank(backFile)) {
//			String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02");
//			// 身份证反面照
//			logger.info(orderId + ">>> 身份证反面 " + backImage);
//			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0);
//			// 保存身份证反面照
//		}
//		if (StringUtils.isNotBlank(natureFile)) {
//			String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03");
//			// 手持照
//			logger.info(orderId + ">>> 手持照/人脸 " + handerImage);
//			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0);
//			// 保存手持照
//		}
//		thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, Integer.valueOf(FenQiGuanJiaConstant.get("channelId")));
//		// 插入身份认证记录
//		logger.info(orderId + ">>>成功处理认证图片");
//
//		logger.info(orderId+"开始进入处理设备信息");
//		Object deviceInfoAllData = pushOrderRequest.getOperaterData();
//
//		try {
//			if(!CommUtils.isNull(deviceInfoAllData)){
//				Device_info_all deviceInfoAll = JSONObject.parseObject(JSON.toJSONString(deviceInfoAllData), Device_info_all.class);
//				//获取信息
//				String platform = deviceInfoAll.getPlatform();
//				String teleName = deviceInfoAll.getTele_name();
//				String teleNum = deviceInfoAll.getTele_num();
//				String imei = deviceInfoAll.getImei();
//				String imsi = deviceInfoAll.getImsi();
//				String seriaNo = deviceInfoAll.getSeria_no();
//				String androidId = deviceInfoAll.getAndroid_id();
//				String udid = deviceInfoAll.getUdid();
//				String mac = deviceInfoAll.getMac();
//				String idfa = deviceInfoAll.getIdfa();
//				String idfv = deviceInfoAll.getIdfv();
//				String iosPlat = deviceInfoAll.getIos_plat();
//				String iosVer = deviceInfoAll.getIos_ver();
//				String uuid = deviceInfoAll.getUuid();
//				String isRoot = deviceInfoAll.getIs_root();
//				String dns = deviceInfoAll.getDns();
//				String memSize = deviceInfoAll.getMem_size();
//				String storageSize = deviceInfoAll.getStorage_size();
//				String avaStorageSize = deviceInfoAll.getAva_storage_size();
//				String phoneBrand = deviceInfoAll.getPhone_brand();
//				String androidVer = deviceInfoAll.getAndroid_ver();
//				String deviceModel = deviceInfoAll.getDevice_model();
//				String installedApps = deviceInfoAll.getInstalled_apps();
//				String appLocation = deviceInfoAll.getApp_location();
//
//				List<Installed_apps> installedAppsList = JSONArray.parseArray(installedApps, Installed_apps.class);
//				//解析手机应用安装列表
//				StringBuilder appName=new StringBuilder();
//				if(CollectionUtils.isNotEmpty(installedAppsList)){
//					for (Installed_apps installedApp:installedAppsList) {
//
//						if(installedApp != null || !CommUtils.isNull(installedApp.getApp_name()) ){
//							appName.append(installedApp.getApp_name()).append(",");
//						}
//					}
//					if(!CommUtils.isNull(appName)){
//						appName= appName.delete(appName.length() - 1, appName.length());
//					}
//
//				}
//				//储存信息
//				FqgjDeviceInfo fqgjDeviceInfo = fqgjDeviceInfoService.getFqgjDeviceInfoByOrderId(orderId);
//				if(CommUtils.isNull(fqgjDeviceInfo)){
//					fqgjDeviceInfo=new FqgjDeviceInfo();
//					fqgjDeviceInfo.setAndroidId(androidId);
//					fqgjDeviceInfo.setAndroidVer(androidVer);
//					fqgjDeviceInfo.setAppLocation(appLocation);
//					fqgjDeviceInfo.setAvaStorageSize(avaStorageSize);
//					fqgjDeviceInfo.setDeviceModel(deviceModel);
//					fqgjDeviceInfo.setDns(dns);
//					fqgjDeviceInfo.setIdfa(idfa);
//					fqgjDeviceInfo.setIdfv(idfv);
//					fqgjDeviceInfo.setInstalledApps(appName.toString());
//					fqgjDeviceInfo.setPhoneBrand(phoneBrand);
//					fqgjDeviceInfo.setStorageSize(storageSize);
//					fqgjDeviceInfo.setMemSize(memSize);
//					fqgjDeviceInfo.setIsRoot(isRoot);
//					fqgjDeviceInfo.setUdid(udid);
//					fqgjDeviceInfo.setUuid(uuid);
//					fqgjDeviceInfo.setIosPlat(iosPlat);
//					fqgjDeviceInfo.setIosVer(iosVer);
//					fqgjDeviceInfo.setPlatform(platform);
//					fqgjDeviceInfo.setTeleName(teleName);
//					fqgjDeviceInfo.setTeleNum(teleNum);
//					fqgjDeviceInfo.setMac(mac);
//					fqgjDeviceInfo.setSeriaNo(seriaNo);
//					fqgjDeviceInfo.setImei(imei);
//					fqgjDeviceInfo.setImsi(imsi);
//					fqgjDeviceInfo.setOrderId(orderId);
//					fqgjDeviceInfo.setCreateTime(new Date());
//					fqgjDeviceInfoService.save(fqgjDeviceInfo);
//					logger.info(orderId+"存入设备信息成功");
//
//				}else{
//					fqgjDeviceInfo.setAndroidId(androidId);
//					fqgjDeviceInfo.setAndroidVer(androidVer);
//					fqgjDeviceInfo.setAppLocation(appLocation);
//					fqgjDeviceInfo.setAvaStorageSize(avaStorageSize);
//					fqgjDeviceInfo.setDeviceModel(deviceModel);
//					fqgjDeviceInfo.setDns(dns);
//					fqgjDeviceInfo.setIdfa(idfa);
//					fqgjDeviceInfo.setIdfv(idfv);
//					fqgjDeviceInfo.setInstalledApps(appName.toString());
//					fqgjDeviceInfo.setPhoneBrand(phoneBrand);
//					fqgjDeviceInfo.setStorageSize(storageSize);
//					fqgjDeviceInfo.setMemSize(memSize);
//					fqgjDeviceInfo.setIsRoot(isRoot);
//					fqgjDeviceInfo.setUdid(udid);
//					fqgjDeviceInfo.setUuid(uuid);
//					fqgjDeviceInfo.setIosPlat(iosPlat);
//					fqgjDeviceInfo.setIosVer(iosVer);
//					fqgjDeviceInfo.setPlatform(platform);
//					fqgjDeviceInfo.setTeleName(teleName);
//					fqgjDeviceInfo.setTeleNum(teleNum);
//					fqgjDeviceInfo.setMac(mac);
//					fqgjDeviceInfo.setSeriaNo(seriaNo);
//					fqgjDeviceInfo.setImei(imei);
//					fqgjDeviceInfo.setImsi(imsi);
//					fqgjDeviceInfo.setUpdateTime(new Date());
//					fqgjDeviceInfoService.updateFqgjDeviceInfo(fqgjDeviceInfo);
//					logger.info(orderId+"更新设备信息成功");
//
//				}
//
//			}
//		} catch (Exception e) {
//			logger.error(orderId+"设备信息存储发生异常");
//		}
//
//		try {
//			logger.info(orderId+"开始进入通讯录存储");
//			addOrUpdateConcast(contacts, orderId);
//		} catch (Exception e) {
//			logger.error(orderId+"通讯录存储异常");
//		}
//		logger.info(orderId+"结束FqgjOperatorServiceImpl.savePhotoes");
//
//
//	}
//
//	/**
//	 * 分期管家添加或更新通讯录
//	 * @param contacts
//	 * @param orderId
//	 */
//
//	private void addOrUpdateConcast(Contacts contacts, Long orderId) {
//			logger.info("分期管家-->>开始添加借款人通讯录,工单为：" + orderId + "");
//			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(orderId);
//			if (CommUtils.isNull(borrower)) {
//				logger.info("借款人不存在");
//				return;
//			}
//			List<Phone_list> phoneList = contacts.getPhone_list();
//			if (CommUtils.isNull(phoneList)) {
//				logger.info("contacts.phoneList is null");
//				return;
//			}
//			// 轮询
//			List<BwContactList> contactList = new ArrayList<>();
//			for (Phone_list fqgjContact : phoneList) {
//				if (CommUtils.isNull(fqgjContact.getPhone())) {
//					logger.info("通话记录中电话不存在");
//					continue;
//				}
//				if (CommUtils.isNull(fqgjContact.getName())) {
//					logger.info("通话记录中姓名不存在");
//					continue;
//				}
//				String phone = fqgjContact.getPhone().replaceAll(" ", "");
//				if (phone.startsWith("+86")) {
//					phone = phone.substring(3);
//				}
//				BwContactList bwContact = new BwContactList();
//				bwContact.setPhone(phone);
//				bwContact.setName(fqgjContact.getName());
//				bwContact.setUpdateTime(new Date());
//				bwContact.setBorrowerId(borrower.getId());
//				bwContact.setCreateTime(new Date());
//				contactList.add(bwContact);
//			}
//		bwContactListService.batchAddContact(contactList, borrower.getId());
//
//	}
//
//
//
//    /**
//     * 分期管家存储风控数据
//     * @param mobile
//     * @param orderId
//     */
//    private void saveNewData(Mobile mobile,Long orderId , SqlSession sqlSession){
//        logger.info(orderId+"开始FqgjOperatorServiceImpl.saveNewData");
//        // 设置批量处理
//
//        try {
//            List<Tel> callList = mobile.getTel();
//            FqgjCallMapper fqgjCallMapper = sqlSession.getMapper(FqgjCallMapper.class);
//
//            FqgjCall delFqgjCall=new FqgjCall();
//            delFqgjCall.setOrderId(orderId);
//            fqgjCallMapper.delete(delFqgjCall);
//
//            logger.info(orderId + "开始存储通话记录");
//            if (callList != null && callList.size() > 0) {
//                for (Tel tel : callList) {
//                    if (!CommUtils.isNull(tel)) {
//                        List<Teldata> teldataList = tel.getTeldata();
//                        for (Teldata teldata : teldataList) {
//                            if (!CommUtils.isNull(teldata)) {
//                                    FqgjCall fqgjCall = new FqgjCall();
//                                    String businessName = teldata.getBusiness_name();
//                                    String callTime = teldata.getCall_time();
//                                    String callType = teldata.getCall_type();
//                                    String fee = teldata.getFee();
//                                    String receivePhone = teldata.getReceive_phone();
//                                    if(!CommUtils.isNull(receivePhone) && receivePhone.length()>19){
//                                    	continue;
//									}
//                                    String specialOffer = teldata.getSpecial_offer();
//                                    String tradeAddr = teldata.getTrade_addr();
//                                    String tradeTime = teldata.getTrade_time();
//                                    String tradeType = teldata.getTrade_type();
//                                    if (!CommUtils.isNull(receivePhone)) {
//                                        receivePhone = receivePhone.replace("-", "").replace("*", "").replace(" ",
//                                                "");
//                                    }
//                                    fqgjCall.setBusinessName(businessName);
//                                    fqgjCall.setCallTime(callTime);
//                                    fqgjCall.setCallType(callType);
//                                    fqgjCall.setFee(fee);
//                                    fqgjCall.setOrderId(orderId);
//                                    fqgjCall.setReceivePhone(receivePhone);
//                                    fqgjCall.setSpecialOffer(specialOffer);
//                                    fqgjCall.setTradeAddr(tradeAddr);
//                                    fqgjCall.setTradeTime(tradeTime);
//                                    fqgjCall.setTradeType(tradeType);
//                                    fqgjCall.setCreateTime(new Date());
//                                    fqgjCallMapper.insert(fqgjCall);
//                            }
//                        }
//                    }
//                }
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "结束存储通话记录");
//            }
//        } catch (Exception e) {
//            logger.error(orderId+"分期管家储存通话记录数据异常",e);
//        }
//
//        logger.info(orderId + "开始存储月账单记录");
//        try {
//            List<Bill> phoneBillList = mobile.getBill();
//            FqgjPhoneBillMapper fqgjPhoneBillMapper = sqlSession.getMapper(FqgjPhoneBillMapper.class);
//
//            FqgjPhoneBill delFqgjPhoneBill = new FqgjPhoneBill();
//            delFqgjPhoneBill.setOrderId(orderId);
//            fqgjPhoneBillMapper.delete(delFqgjPhoneBill);
//            Set<String> set=new HashSet<>();
//            if (phoneBillList != null && phoneBillList.size() > 0) {
//
//                for (Bill bill : phoneBillList) {
//                        if (!CommUtils.isNull(bill)) {
//                            String addtionalFee = bill.getAddtional_fee();
//                            String callPay = bill.getCall_pay();
//                            String generationFee = bill.getGeneration_fee();
//                            String month = bill.getMonth();
//                            String msgFee = bill.getMsg_fee();
//                            String netFee = bill.getNet_fee();
//                            String otherFee = bill.getOther_fee();
//                            String packageFee = bill.getPackage_fee();
//                            String preferentialFee = bill.getPreferential_fee();
//                            String score = bill.getScore();
//                            String telFee = bill.getTel_fee();
//
//                            boolean flag=set.add(month);
//                            if(flag){
//                                FqgjPhoneBill fqgjPhoneBill = new FqgjPhoneBill();
//                                fqgjPhoneBill.setAddtionalFee(addtionalFee);
//                                fqgjPhoneBill.setCallPay(callPay);
//                                fqgjPhoneBill.setGenerationFee(generationFee);
//                                fqgjPhoneBill.setMonth(month);
//                                fqgjPhoneBill.setMsgFee(msgFee);
//                                fqgjPhoneBill.setNetFee(netFee);
//                                fqgjPhoneBill.setOrderId(orderId);
//                                fqgjPhoneBill.setOtherFee(otherFee);
//                                fqgjPhoneBill.setPackageFee(packageFee);
//                                fqgjPhoneBill.setPreferentialFee(preferentialFee);
//                                fqgjPhoneBill.setTelFee(telFee);
//                                fqgjPhoneBill.setScore(score);
//                                fqgjPhoneBill.setCreateTime(new Date());
//                                fqgjPhoneBillMapper.insert(fqgjPhoneBill);
//                            }
//                        }
//                }
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "结束存储月账单记录");
//            }
//        } catch (Exception e) {
//            logger.error(orderId+"分期管家存储月账单数据异常",e);
//        }
//
//        try {
//            List<Recharge> rechargeList = mobile.getRecharge();
//
//            FqgjRechargeMapper fqgjRechargeMapper = sqlSession.getMapper(FqgjRechargeMapper.class);
//            FqgjRecharge delfqgjRecharge = new FqgjRecharge();
//            delfqgjRecharge.setOrderId(orderId);
//            fqgjRechargeMapper.delete(delfqgjRecharge);
//
//            if (rechargeList != null && rechargeList.size() > 0) {
//                for (Recharge recharge : rechargeList) {
//                        if (!CommUtils.isNull(recharge)) {
//                            String fee = recharge.getFee();
//                            String rechargeTime = recharge.getRecharge_time();
//                            String rechargeWay = recharge.getRecharge_way();
//                            FqgjRecharge fqgjRecharge = new FqgjRecharge();
//                            fqgjRecharge.setOrderId(orderId);
//                            fqgjRecharge.setRechargeTime(rechargeTime);
//                            fqgjRecharge.setRechargeWay(rechargeWay);
//                            fqgjRecharge.setFee(fee);
//                            fqgjRecharge.setCreateTime(new Date());
//                            fqgjRechargeMapper.insert(fqgjRecharge);
//                        }
//                }
//            }
//            sqlSession.commit();
//            sqlSession.clearCache();
//            logger.info(orderId + "结束存储充值记录");
//        } catch (Exception e) {
//            logger.error(orderId+"分期管家存储充值记录数据异常",e);
//        }
//
//        logger.info(orderId+"开始存储短信账单记录");
//
//        try {
//            List<Msg> msgBillList = mobile.getMsg();
//            FqgjMsgBillMapper fqgjMsgBillMapper = sqlSession.getMapper(FqgjMsgBillMapper.class);
//
//            FqgjMsgBill delFqgjMsgBill = new FqgjMsgBill();
//            delFqgjMsgBill.setOrderId(orderId);
//            fqgjMsgBillMapper.delete(delFqgjMsgBill);
//
//            if (msgBillList != null && msgBillList.size() > 0) {
//                for (Msg msg : msgBillList) {
//                    if (!CommUtils.isNull(msg)) {
//                        List<Msgdata> msgdataList = msg.getMsgdata();
//                        if (msgdataList != null && msgdataList.size() > 0) {
//                            for (Msgdata msgdata : msgdataList) {
//                                    if (!CommUtils.isNull(msgdata)) {
//                                        String businessName = msgdata.getBusiness_name();
//                                        String fee = msgdata.getFee();
//                                        String receiverPhone = msgdata.getReceiver_phone();
//                                        String sendTime = msgdata.getSend_time();
//                                        String tradeAddr = msgdata.getTrade_addr();
//                                        String tradeType = msgdata.getTrade_type();
//                                        int tradeWay = msgdata.getTrade_way();
//                                        FqgjMsgBill fqgjMsgBill = new FqgjMsgBill();
//                                        fqgjMsgBill.setBusinessName(businessName);
//                                        fqgjMsgBill.setFee(fee);
//                                        fqgjMsgBill.setOrderId(orderId);
//                                        fqgjMsgBill.setReceiverPhone(receiverPhone);
//                                        fqgjMsgBill.setSendTime(sendTime);
//                                        fqgjMsgBill.setTradeAddr(tradeAddr);
//                                        fqgjMsgBill.setTradeType(tradeType);
//                                        fqgjMsgBill.setTradeWay(
//                                                tradeWay == 0 ? String.valueOf(3) : String.valueOf(tradeWay));
//                                        fqgjMsgBill.setCreateTime(new Date());
//                                        fqgjMsgBillMapper.insert(fqgjMsgBill);
//                                    }
//                            }
//                        }
//                    }
//                }
//            }
//            sqlSession.commit();
//            sqlSession.clearCache();
//            logger.info(orderId + "结束存储短信记录");
//        } catch (Exception e) {
//            logger.error("分期管家存储短信账单数据异常",e);
//        }
//        logger.info(orderId + "结束FqgjOperatorServiceImpl.saveNewData");
//
//    }
//
//}
