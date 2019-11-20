///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdBill;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdCall;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdFamily;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdMobileSrc;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdNet;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdPackage;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdRecharge;
//import com.waterelephant.sxyDrainage.entity.haodai.TodHdSms;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdBill;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdCall;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdCallItem;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdFamily;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdFamilyMembers;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdMobileSrc;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdNet;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdNetItem;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdPackage;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdPackageItem;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdRecharge;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdRechargeItem;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdSms;
//import com.waterelephant.sxyDrainage.entity.haodai.operator.HdSmsItem;
//import com.waterelephant.sxyDrainage.service.AsyncTodHdService;
//import com.waterelephant.sxyDrainage.service.TodHdBillService;
//import com.waterelephant.sxyDrainage.service.TodHdCallService;
//import com.waterelephant.sxyDrainage.service.TodHdFamilyService;
//import com.waterelephant.sxyDrainage.service.TodHdMobileSrcService;
//import com.waterelephant.sxyDrainage.service.TodHdNetService;
//import com.waterelephant.sxyDrainage.service.TodHdPackageService;
//import com.waterelephant.sxyDrainage.service.TodHdRechargeService;
//import com.waterelephant.sxyDrainage.service.TodHdSmsService;
//import com.waterelephant.utils.CommUtils;
//
///**
// * TodHdServiceImpl.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月19日
// * @Description: <好贷网运营商数据存储>
// * 
// */
//@Component
//public class AsyncTodHdServiceImpl implements AsyncTodHdService {
//	private Logger logger = Logger.getLogger(AsyncTodHdServiceImpl.class);
//
//	@Autowired
//	private TodHdBillService todHdBillService;
//	@Autowired
//	private TodHdCallService todHdCallService;
//	@Autowired
//	private TodHdMobileSrcService todHdMobileSrcService;
//	@Autowired
//	private TodHdNetService todHdNetService;
//	@Autowired
//	private TodHdPackageService todHdPackageService;
//	@Autowired
//	private TodHdSmsService todHdSmsService;
//	@Autowired
//	private TodHdFamilyService todHdFamilyService;
//	@Autowired
//	private TodHdRechargeService todHdRechargeService;
//
//	// @Transactional(rollbackFor = RuntimeException.class)
//	// @Async("taskExecutor")
//	@Override
//	public void save(String json, Long orderId) {
//		logger.info("开始存储好贷网[订单号：" + orderId + "]运营商数据！");
//
//		try {
//			HdMobileSrc hdMobileSrc = JSON.parseObject(json, HdMobileSrc.class);
//			if (CommUtils.isNull(hdMobileSrc)) {
//				logger.info("===解析运营商数据：好贷网>>>数据为空");
//				return;
//			}
//
//			try {
//				// 基本信息字段
//				todHdMobileSrcService.delAllByOrderId(orderId);// 删除订单相关数据
//				TodHdMobileSrc todHdMobileSrc = new TodHdMobileSrc();
//				todHdMobileSrc.setOrderId(orderId);
//				todHdMobileSrc.setCreateTime(new Date());
//				todHdMobileSrc.setMobile(hdMobileSrc.getMobile());
//				todHdMobileSrc.setName(hdMobileSrc.getName());
//				todHdMobileSrc.setIdcard(hdMobileSrc.getIdcard());
//				todHdMobileSrc.setCarrier(hdMobileSrc.getCarrier());
//				todHdMobileSrc.setProvince(hdMobileSrc.getProvince());
//				todHdMobileSrc.setCity(hdMobileSrc.getCity());
//				todHdMobileSrc.setOpenTime(hdMobileSrc.getOpen_time());
//				todHdMobileSrc.setLevel(hdMobileSrc.getLevel());
//				todHdMobileSrc.setPackageName(hdMobileSrc.getPackage_name());
//				todHdMobileSrc.setState(hdMobileSrc.getState());
//				todHdMobileSrc.setAvailableBalance(hdMobileSrc.getAvailable_balance());
//				todHdMobileSrc.setLastModifyTime(hdMobileSrc.getLast_modify_time());
//				todHdMobileSrc.setCode(hdMobileSrc.getCode());
//				todHdMobileSrc.setMessage(hdMobileSrc.getMessage());
//				todHdMobileSrcService.save(todHdMobileSrc);
//				logger.info("===解析运营商数据：好贷网>>>基本信息字段");
//			} catch (Exception e) {
//				logger.error("===解析运营商数据 异常失败：好贷网>>>基本信息字段", e);
//			}
//
//			try {
//				// 账单信息
//				List<HdBill> bills = hdMobileSrc.getBills();
//				if (!CommUtils.isNull(bills)) {
//					todHdBillService.delAllByOrderId(orderId);// 删除订单相关数据
//					for (HdBill hdBill : bills) {
//						TodHdBill todHdBill = new TodHdBill();
//						todHdBill.setOrderId(orderId);
//						todHdBill.setCreateTime(new Date());
//						todHdBill.setBillMonth(hdBill.getBill_month());
//						todHdBill.setBillStartDate(hdBill.getBill_start_date());
//						todHdBill.setBillEndDate(hdBill.getBill_end_date());
//						todHdBill.setBaseFee(hdBill.getBase_fee());
//						todHdBill.setExtraServiceFee(hdBill.getExtra_service_fee());
//						todHdBill.setVoiceFee(hdBill.getVoice_fee());
//						todHdBill.setSmsFee(hdBill.getSms_fee());
//						todHdBill.setWebFee(hdBill.getWeb_fee());
//						todHdBill.setExtraFee(hdBill.getExtra_fee());
//						todHdBill.setTotalFee(hdBill.getTotal_fee());
//						todHdBill.setDiscount(hdBill.getDiscount());
//						todHdBill.setExtraDiscount(hdBill.getExtra_discount());
//						todHdBill.setActualFee(hdBill.getActual_fee());
//						todHdBill.setPaidFee(hdBill.getPaid_fee());
//						todHdBill.setUnpaidFee(hdBill.getUnpaid_fee());
//						todHdBill.setPoint(hdBill.getPoint());
//						todHdBill.setLastPoint(hdBill.getLast_point());
//						todHdBill.setRelatedMobiles(hdBill.getRelated_mobiles());
//						todHdBill.setNotes(hdBill.getNotes());
//						todHdBillService.save(todHdBill);
//					}
//				}
//				logger.info("===解析运营商数据：好贷网>>>账单信息");
//			} catch (Exception e) {
//				logger.error("===解析运营商数据 异常失败：好贷网>>>账单信息", e);
//			}
//
//			try {
//				// 语音详情
//				List<HdCall> calls = hdMobileSrc.getCalls();
//				if (!CommUtils.isNull(calls)) {
//					todHdCallService.delAllByOrderId(orderId);// 删除订单相关数据
//					for (HdCall hdCall : calls) {
//						List<HdCallItem> items = hdCall.getItems();
//						if (!CommUtils.isNull(items)) {
//							for (HdCallItem hdCallItem : items) {
//								TodHdCall todHdCall = new TodHdCall();
//								todHdCall.setOrderId(orderId);
//								todHdCall.setCreateTime(new Date());
//								todHdCall.setBillMonth(hdCall.getBill_month());
//								todHdCall.setTotalSize(hdCall.getTotal_size());
//								todHdCall.setDetailsId(hdCallItem.getDetails_id());
//								todHdCall.setTime(hdCallItem.getTime());
//								todHdCall.setPeerNumber(hdCallItem.getPeer_number());
//								todHdCall.setLocation(hdCallItem.getLocation());
//								todHdCall.setLocationType(hdCallItem.getLocation_type());
//								todHdCall.setDuration(hdCallItem.getDuration());
//								todHdCall.setDialType(hdCallItem.getDial_type());
//								todHdCall.setFee(hdCallItem.getFee());
//								todHdCallService.save(todHdCall);
//							}
//						}
//					}
//				}
//				logger.info("===解析运营商数据：好贷网>>>语音详情");
//			} catch (Exception e) {
//				logger.error("===解析运营商数据 异常失败：好贷网>>>语音详情", e);
//			}
//			try {
//				// 亲情网
//				List<HdFamily> families = hdMobileSrc.getFamilies();
//				if (!CommUtils.isNull(families)) {
//					todHdFamilyService.delAllByOrderId(orderId);// 删除订单相关数据
//					for (HdFamily hdFamily : families) {
//						List<HdFamilyMembers> family_members = hdFamily.getFamily_members();
//						if (!CommUtils.isNull(family_members)) {
//							for (HdFamilyMembers hdFamilyMembers : family_members) {
//								TodHdFamily todHdFamily = new TodHdFamily();
//								todHdFamily.setOrderId(orderId);
//								todHdFamily.setCreateTime(new Date());
//								todHdFamily.setFamilyNum(hdFamily.getFamily_num());
//								todHdFamily.setLongNumber(hdFamilyMembers.getLong_number());
//								todHdFamily.setShortNumber(hdFamilyMembers.getShort_number());
//								todHdFamily.setMemberType(hdFamilyMembers.getMember_type());
//								todHdFamily.setJoinDate(hdFamilyMembers.getJoin_date());
//								todHdFamily.setExpireDate(hdFamilyMembers.getExpire_date());
//								todHdFamilyService.save(todHdFamily);
//							}
//						}
//					}
//				}
//				logger.info("===解析运营商数据：好贷网>>>亲情网");
//			} catch (Exception e) {
//				logger.error("===解析运营商数据 异常失败：好贷网>>>亲情网", e);
//			}
//			try {
//				// 流量详情
//				List<HdNet> nets = hdMobileSrc.getNets();
//				if (!CommUtils.isNull(nets)) {
//					todHdNetService.delAllByOrderId(orderId);// 删除订单相关数据
//					for (HdNet hdNet : nets) {
//						List<HdNetItem> items = hdNet.getItems();
//						if (!CommUtils.isNull(items)) {
//							for (HdNetItem hdNetItem : items) {
//								TodHdNet todHdNet = new TodHdNet();
//								todHdNet.setOrderId(orderId);
//								todHdNet.setCreateTime(new Date());
//								todHdNet.setBillMonth(hdNet.getBill_month());
//								todHdNet.setTotalSize(hdNet.getTotal_size());
//								todHdNet.setDetailsId(hdNetItem.getDetails_id());
//								todHdNet.setTime(hdNetItem.getTime());
//								todHdNet.setDuration(hdNetItem.getDuration());
//								todHdNet.setLocation(hdNetItem.getLocation());
//								todHdNet.setSubflow(hdNetItem.getSubflow());
//								todHdNet.setNetType(hdNetItem.getNet_type());
//								todHdNet.setServiceName(hdNetItem.getService_name());
//								todHdNet.setFee(hdNetItem.getFee());
//								todHdNetService.save(todHdNet);
//							}
//						}
//					}
//				}
//				logger.info("===解析运营商数据：好贷网>>>流量详情");
//			} catch (Exception e) {
//				logger.error("===解析运营商数据 异常失败：好贷网>>>流量详情", e);
//			}
//			try {
//				// 套餐
//				List<HdPackage> packages = hdMobileSrc.getPackages();
//				if (!CommUtils.isNull(packages)) {
//					todHdPackageService.delAllByOrderId(orderId);// 删除订单相关数据
//					for (HdPackage hdPackage : packages) {
//						List<HdPackageItem> items = hdPackage.getItems();
//						if (!CommUtils.isNull(items)) {
//							for (HdPackageItem hdPackageItem : items) {
//								TodHdPackage todHdPackage = new TodHdPackage();
//								todHdPackage.setOrderId(orderId);
//								todHdPackage.setCreateTime(new Date());
//								todHdPackage.setBillStartDate(hdPackage.getBill_start_date());
//								todHdPackage.setBillEndDate(hdPackage.getBill_end_date());
//								todHdPackage.setItem(hdPackageItem.getItem());
//								todHdPackage.setTotal(hdPackageItem.getTotal());
//								todHdPackage.setUsed(hdPackageItem.getUsed());
//								todHdPackage.setUnit(hdPackageItem.getUnit());
//								todHdPackageService.save(todHdPackage);
//							}
//						}
//					}
//				}
//				logger.info("===解析运营商数据：好贷网>>>套餐");
//			} catch (Exception e) {
//				logger.error("===解析运营商数据 异常失败：好贷网>>>套餐", e);
//			}
//			try {
//				// 充值记录
//				List<HdRecharge> recharges = hdMobileSrc.getRecharges();
//				if (!CommUtils.isNull(recharges)) {
//					todHdRechargeService.delAllByOrderId(orderId);// 删除订单相关数据
//					for (HdRecharge hdRecharge : recharges) {
//						List<HdRechargeItem> items = hdRecharge.getItems();
//						if (!CommUtils.isNull(items)) {
//							for (HdRechargeItem hdRechargeItem : items) {
//								TodHdRecharge todHdRecharge = new TodHdRecharge();
//								todHdRecharge.setOrderId(orderId);
//								todHdRecharge.setCreateTime(new Date());
//								todHdRecharge.setDetailsId(hdRechargeItem.getDetails_id());
//								todHdRecharge.setRechargeTime(hdRechargeItem.getRecharge_time());
//								todHdRecharge.setAmount(hdRechargeItem.getAmount());
//								todHdRecharge.setType(hdRechargeItem.getType());
//								todHdRechargeService.save(todHdRecharge);
//							}
//						}
//					}
//				}
//				logger.info("===解析运营商数据：好贷网>>>充值记录");
//			} catch (Exception e) {
//				logger.error("===解析运营商数据 异常失败：好贷网>>>充值记录", e);
//			}
//			try {
//				// 短信详情
//				List<HdSms> smses = hdMobileSrc.getSmses();
//				if (!CommUtils.isNull(smses)) {
//					todHdSmsService.delAllByOrderId(orderId);// 删除订单相关数据
//					for (HdSms hdSms : smses) {
//						List<HdSmsItem> items = hdSms.getItems();
//						if (!CommUtils.isNull(items)) {
//							for (HdSmsItem hdSmsItem : items) {
//								TodHdSms todHdSms = new TodHdSms();
//								todHdSms.setOrderId(orderId);
//								todHdSms.setCreateTime(new Date());
//								todHdSms.setBillMonth(hdSms.getBill_month());
//								todHdSms.setTotalSize(hdSms.getTotal_size());
//								todHdSms.setDetailsId(hdSmsItem.getDetails_id());
//								todHdSms.setTime(hdSmsItem.getTime());
//								todHdSms.setPeerNumber(hdSmsItem.getPeer_number());
//								todHdSms.setLocation(hdSmsItem.getLocation());
//								todHdSms.setSendType(hdSmsItem.getSend_type());
//								todHdSms.setMsgType(hdSmsItem.getMsg_type());
//								todHdSms.setServiceName(hdSmsItem.getService_name());
//								todHdSms.setFee(hdSmsItem.getFee());
//								todHdSmsService.save(todHdSms);
//							}
//						}
//					}
//				}
//				logger.info("===解析运营商数据：好贷网>>> 短信详情");
//			} catch (Exception e) {
//				logger.error("===解析运营商数据 异常失败：好贷网>>>短信详情", e);
//			}
//
//			logger.info("好贷网[订单号：" + orderId + "]运营商数据存储成功！");
//		} catch (Exception e) {
//			logger.error("===解析运营商数据 异常失败：好贷网>>>无法解析运营商数据", e);
//		}
//	}
//}
