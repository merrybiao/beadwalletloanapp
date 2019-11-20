package com.waterelephant.service.impl;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beadwallet.service.entity.response.Zhima;
import com.beadwallet.service.entiyt.middle.Tel;
import com.beadwallet.service.entiyt.middle.TelData;
import com.google.gson.reflect.TypeToken;
import com.waterelephant.dto.AddInfo;
import com.waterelephant.dto.ApplyDetail;
import com.waterelephant.dto.Contacts;
import com.waterelephant.dto.Mobile;
import com.waterelephant.dto.OrderInfo;
import com.waterelephant.dto.PhoneList;
import com.waterelephant.dto.RongOrderDetail;
import com.waterelephant.dto.RongOrderDto;
import com.waterelephant.dto.User;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.entity.BwZmxyScore;
import com.waterelephant.service.BwOperateBasicService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwRongOrderDetailService;
import com.waterelephant.service.BwZmxyScoreService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwContactListService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.SystemConstant;

import net.sf.json.JSONObject;

@Service
public class BwRongOrderDetailServiceImpl implements BwRongOrderDetailService {
	private Logger logger = Logger.getLogger(BwRongOrderDetailServiceImpl.class);
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwContactListService bwContactListService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private BwOperateBasicService bwOperateBasicService;
	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;
	@Autowired
	private BwZmxyScoreService bwZmxyScoreService;

	@Override
	public int save(RongOrderDto rongOrderDto) throws Exception {
		Date now = new Date();
		String biz_data = rongOrderDto.getBiz_data();
		String sign = rongOrderDto.getSign();
		JSONObject bizData = JSONObject.fromObject(biz_data);
		Type type = new TypeToken<RongOrderDetail>() {
		}.getType();
		// 根据引流的订单判断
		RongOrderDetail rongOrderDetail = JsonUtils.fromJson(bizData.toString(), type);
		OrderInfo orderInfo = rongOrderDetail.getOrderInfo();
		ApplyDetail applyDetail = rongOrderDetail.getApplyDetail();
		logger.info("orderInfo:" + orderInfo.getOrder_No());
		logger.info("applyDetail:" + applyDetail.getUser_id());
		String orderNo = orderInfo.getOrder_No();
		String name = orderInfo.getUser_name();
		String phone = orderInfo.getUser_mobile();
		String idCard = applyDetail.getUser_id();
		BwBorrower borrower = new BwBorrower();
		borrower.setPhone(phone);
		borrower.setFlag(1);// 未删除的
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		if (!CommUtils.isNull(borrower)) {
			logger.info("修改的借款人id：" + borrower.getId());
			// 已存在(更新借款人)
			borrower.setPhone(phone);
			borrower.setCreateTime(now);
			borrower.setAuthStep(1);
			borrower.setFlag(1);
			borrower.setState(1);
			borrower.setChannel(11); // 表示该借款人来源于融360
			borrower.setIdCard(idCard);
			borrower.setName(name);
			borrower.setAge(getAgeByIdCard(idCard));
			borrower.setSex(getSexByIdCard(idCard));
			bwBorrowerService.updateBwBorrower(borrower);
		} else {
			// 创建借款人
			String password = "123456a";
			borrower = new BwBorrower();
			borrower.setPhone(phone);
			borrower.setPassword(CommUtils.getMD5(password.getBytes()));
			borrower.setCreateTime(now);
			borrower.setAuthStep(1);
			borrower.setFlag(1);
			borrower.setState(1);
			borrower.setChannel(11); // 表示该借款人来源于融360
			borrower.setIdCard(idCard);
			borrower.setName(name);
			borrower.setAge(getAgeByIdCard(idCard));
			borrower.setSex(getSexByIdCard(idCard));
			bwBorrowerService.addBwBorrower(borrower);
			logger.info("生成的借款人id：" + borrower.getId());
		}
		BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
		if (CommUtils.isNull(bwOrderRong)) {
			// 创建工单
			BwOrder order = new BwOrder();
			order.setOrderNo(OrderUtil.generateOrderNo());
			if (CommUtils.isNull(borrower.getId())) {
				return -4;
			}
			order.setBorrowerId(borrower.getId());
			order.setStatusId(1L);
			order.setCreateTime(now);
			order.setChannel(11);
			order.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
			order.setApplyPayStatus(0);
			bwOrderService.addBwOrder(order);
			logger.info("生成的工单号：" + order.getId());
			// 将工单与融360的订单绑定
			bwOrderRong = new BwOrderRong();
			logger.info("订单号为：" + orderNo);
			bwOrderRong.setOrderId(order.getId());
			bwOrderRong.setThirdOrderNo(orderNo);
			bwOrderRong.setChannelId(11l);
			bwOrderRong.setCreateTime(now);
			bwOrderRongService.save(bwOrderRong);
			// 公司信息
			BwWorkInfo bwi = new BwWorkInfo();
			bwi.setOrderId(order.getId());
			bwi.setCallTime("10:00 - 12:00");// 默认值（融360没有提供相关数据）
			bwi.setCreateTime(now);
			if (CommUtils.isNull(applyDetail.getWork_period())) {
				bwi.setWorkYears("1年以内");
			} else {
				switch (applyDetail.getWork_period()) {
				case "1":
					bwi.setWorkYears("1年以内");
					break;
				case "2":
					bwi.setWorkYears("1年以内");
					break;
				case "3":
					bwi.setWorkYears("1-3年");
					break;
				case "4":
					bwi.setWorkYears("3-5年");
					break;
				case "5":
					bwi.setWorkYears("5-10年");
					break;
				default:
					bwi.setWorkYears("1年以内");
					break;
				}
			}
			bwWorkInfoService.save(bwi, borrower.getId());
			// 录入通讯录
			AddInfo addInfo = rongOrderDetail.getAddInfo();
			Contacts contacts = addInfo.getContacts();
			if (CommUtils.isNull(contacts)) {
				return -5;
			}
			List<PhoneList> phoneLists = contacts.getPhone_list();
			List<BwContactList> list = new ArrayList<>();
			for (PhoneList phoneList : phoneLists) {
				if (CommUtils.isNull(phoneList.getName())) {
					continue;
				}
				if (CommUtils.isNull(phoneList.getPhone())) {
					continue;
				}
				BwContactList bwContactList = new BwContactList();
				bwContactList.setBorrowerId(borrower.getId());
				bwContactList.setCreateTime(now);
				bwContactList.setName(phoneList.getName());
				bwContactList.setPhone(phoneList.getPhone());
				bwContactList.setUpdateTime(now);
				list.add(bwContactList);

			}
			bwContactListService.addOrUpdateBwContactLists(list, borrower.getId());
			// 运营商信息
			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Mobile mobile = addInfo.getMobile();
			User user = mobile.getUser();
			phone = user.getPhone();
			logger.info("借款人的手机号是：" + phone);
			BwOperateBasic bwOperateBasic = bwOperateBasicService.getBwOperateBasicByBorrowerId(borrower.getId());
			if (CommUtils.isNull(bwOperateBasic)) {
				// 添加基本信息
				bwOperateBasic = new BwOperateBasic();
				bwOperateBasic.setUserSource(user.getUser_source());
				bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
				bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
				bwOperateBasic.setPhone(user.getPhone());
				bwOperateBasic.setPhoneRemain(user.getPhone_remain());
				bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
				if (!CommUtils.isNull(user.getReg_time())) {
					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
				}
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasicService.save(bwOperateBasic);
			} else {
				// 修改基本信息
				// System.out.println("根据手机号查询的id是：" + bwOperateBasic.getId());
				bwOperateBasic.setUserSource(user.getUser_source());
				bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
				bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
				bwOperateBasic.setPhone(user.getPhone());
				bwOperateBasic.setPhoneRemain(user.getPhone_remain());
				bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
				if (!CommUtils.isNull(user.getReg_time())) {
					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
				}
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasicService.update(bwOperateBasic);
			}
			Date updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
			Date callDate = updateTime;
			callDate = sdf_hms.parse(sdf_hms.format(callDate));
			List<Tel> tels = mobile.getTel();
			for (Tel tel : tels) {
				List<TelData> lists = tel.getTeldata();
				if (!CommUtils.isNull(lists)) {
					for (TelData telData : lists) {
						Date jsonCallData = sdf_hms.parse(telData.getCall_time());
						if (jsonCallData.after(callDate)) {
							BwOperateVoice bwOperateVoice = new BwOperateVoice();
							bwOperateVoice.setUpdateTime(now);
							bwOperateVoice.setBorrower_id(borrower.getId());
							// 检验日期格式
							String callTime = null;
							try {
								callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
							} catch (ParseException e) {
								e.printStackTrace();
								// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
								continue;
							}
							bwOperateVoice.setCall_time(callTime);
							bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
							bwOperateVoice.setReceive_phone(telData.getReceive_phone());
							bwOperateVoice
									.setTrade_addr(CommUtils.isNull(telData.getTrade_addr()) ? "" : user.getAddr());
							bwOperateVoice.setTrade_time(telData.getTrade_time());
							bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
							bwOperateVoiceService.save(bwOperateVoice);
						}
					}
				}
			}
			// 芝麻信用分
			Zhima zhima = addInfo.getZhima();
			BwZmxyScore bwZmxyScore = new BwZmxyScore();
			bwZmxyScore.setName(name);
			bwZmxyScore.setIdCard(idCard);
			bwZmxyScore = bwZmxyScoreService.findByAttr(bwZmxyScore);
			if (CommUtils.isNull(bwZmxyScore)) {
				bwZmxyScore = new BwZmxyScore();
				bwZmxyScore.setIdCard(idCard);
				bwZmxyScore.setName(name);
				bwZmxyScore.setPhone(phone);
				bwZmxyScore.setZmxyScore(zhima.getAli_trust_score().getScore());
				bwZmxyScore.setCreateTime(now);
				bwZmxyScoreService.save(bwZmxyScore);
			} else {
				// 更新
				bwZmxyScore.setZmxyScore(zhima.getAli_trust_score().getScore());
				bwZmxyScore.setUpdateTime(now);
				bwZmxyScoreService.update(bwZmxyScore);
			}
		} else {
			// 查询工单
			BwOrder order = new BwOrder();
			order.setId(bwOrderRong.getOrderId());
			order = bwOrderService.findBwOrderByAttr(order);
			// 更新公司信息
			BwWorkInfo bwi = new BwWorkInfo();
			bwi.setOrderId(order.getId());
			bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
			bwi.setOrderId(order.getId());
			bwi.setCallTime("10:00 - 12:00");// 默认值（融360没有提供相关数据）
			bwi.setUpdateTime(now);
			if (CommUtils.isNull(applyDetail.getWork_period())) {
				bwi.setWorkYears("1年以内");
			} else {
				switch (applyDetail.getWork_period()) {
				case "1":
					bwi.setWorkYears("1年以内");
					break;
				case "2":
					bwi.setWorkYears("1年以内");
					break;
				case "3":
					bwi.setWorkYears("1-3年");
					break;
				case "4":
					bwi.setWorkYears("3-5年");
					break;
				case "5":
					bwi.setWorkYears("5-10年");
					break;
				default:
					bwi.setWorkYears("1年以内");
					break;
				}
			}
			bwWorkInfoService.update(bwi);
			// 录入通讯录
			AddInfo addInfo = rongOrderDetail.getAddInfo();
			Contacts contacts = addInfo.getContacts();
			List<PhoneList> phoneLists = contacts.getPhone_list();
			List<BwContactList> list = new ArrayList<>();
			for (PhoneList phoneList : phoneLists) {
				if (CommUtils.isNull(phoneList.getName())) {
					continue;
				}
				if (CommUtils.isNull(phoneList.getPhone())) {
					continue;
				}
				BwContactList bwContactList = new BwContactList();
				bwContactList.setBorrowerId(borrower.getId());
				bwContactList.setCreateTime(now);
				bwContactList.setName(phoneList.getName().toString());
				bwContactList.setPhone(phoneList.getPhone());
				bwContactList.setUpdateTime(now);
				list.add(bwContactList);
			}
			bwContactListService.addOrUpdateBwContactLists(list, borrower.getId());
			// 运营商信息
			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Mobile mobile = addInfo.getMobile();
			User user = mobile.getUser();
			phone = user.getPhone();
			if (CommUtils.isNull(phone)) {
				return -7;
			}
			logger.info("借款人的手机号是：" + phone);
			BwOperateBasic bwOperateBasic = bwOperateBasicService.getBwOperateBasicByBorrowerId(borrower.getId());
			if (CommUtils.isNull(bwOperateBasic)) {
				// 添加基本信息
				bwOperateBasic = new BwOperateBasic();
				bwOperateBasic.setUserSource(user.getUser_source());
				bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
				bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
				bwOperateBasic.setPhone(user.getPhone());
				bwOperateBasic.setPhoneRemain(user.getPhone_remain());
				bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
				if (!CommUtils.isNull(user.getReg_time())) {
					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
				}
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasicService.save(bwOperateBasic);
			} else {
				// 修改基本信息
				bwOperateBasic.setUserSource(user.getUser_source());
				bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
				bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
				bwOperateBasic.setPhone(user.getPhone());
				bwOperateBasic.setPhoneRemain(user.getPhone_remain());
				bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
				if (!CommUtils.isNull(user.getReg_time())) {
					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
				}
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasicService.update(bwOperateBasic);
			}
			Date updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
			Date callDate = updateTime;
			callDate = sdf_hms.parse(sdf_hms.format(callDate));
			List<Tel> tels = mobile.getTel();
			for (Tel tel : tels) {
				List<TelData> lists = tel.getTeldata();
				if (!CommUtils.isNull(lists)) {
					for (TelData telData : lists) {
						Date jsonCallData = sdf_hms.parse(telData.getCall_time());
						if (jsonCallData.after(callDate)) {
							BwOperateVoice bwOperateVoice = new BwOperateVoice();
							bwOperateVoice.setUpdateTime(now);
							bwOperateVoice.setBorrower_id(borrower.getId());
							// 检验日期格式
							String callTime = null;
							try {
								callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
							} catch (ParseException e) {
								e.printStackTrace();
								// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
								continue;
							}
							bwOperateVoice.setCall_time(callTime);
							bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
							bwOperateVoice.setReceive_phone(telData.getReceive_phone());
							bwOperateVoice
									.setTrade_addr(CommUtils.isNull(telData.getTrade_addr()) ? "" : user.getAddr());
							bwOperateVoice.setTrade_time(telData.getTrade_time());
							bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
							bwOperateVoiceService.save(bwOperateVoice);
						}
					}
				}
			}
			// 芝麻信用分
			Zhima zhima = addInfo.getZhima();
			if (!CommUtils.isNull(zhima)) {
				BwZmxyScore bwZmxyScore = new BwZmxyScore();
				bwZmxyScore.setName(name);
				bwZmxyScore.setIdCard(idCard);
				bwZmxyScore = bwZmxyScoreService.findByAttr(bwZmxyScore);
				if (CommUtils.isNull(bwZmxyScore)) {
					bwZmxyScore = new BwZmxyScore();
					bwZmxyScore.setIdCard(idCard);
					bwZmxyScore.setName(name);
					bwZmxyScore.setPhone(phone);
					bwZmxyScore.setZmxyScore(zhima.getAli_trust_score().getScore());
					bwZmxyScore.setCreateTime(now);
					bwZmxyScoreService.save(bwZmxyScore);
				} else {
					// 更新
					bwZmxyScore.setZmxyScore(zhima.getAli_trust_score().getScore());
					bwZmxyScore.setUpdateTime(now);
					bwZmxyScoreService.update(bwZmxyScore);
				}
			}
			logger.info("推单结果成功");
			return 1;
		}
		logger.info("推单结果成功");
		return 1;
	}

	@Override
	public int saveNew(String biz_data) throws Exception {
		Date now = new Date();
		JSONObject bizData = JSONObject.fromObject(biz_data);
		Type type = new TypeToken<RongOrderDetail>() {
		}.getType();
		// 根据引流的订单判断
		RongOrderDetail rongOrderDetail = JsonUtils.fromJson(bizData.toString(), type);
		OrderInfo orderInfo = rongOrderDetail.getOrderInfo();
		ApplyDetail applyDetail = rongOrderDetail.getApplyDetail();
		logger.info("orderInfo:" + orderInfo.getOrder_No());
		logger.info("applyDetail:" + applyDetail.getUser_id());
		String orderNo = orderInfo.getOrder_No();
		String name = orderInfo.getUser_name();
		String phone = orderInfo.getUser_mobile();
		String idCard = applyDetail.getUser_id();
		BwBorrower borrower = new BwBorrower();
		borrower.setPhone(phone);
		borrower.setFlag(1);// 未删除的
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		if (!CommUtils.isNull(borrower)) {
			logger.info("修改的借款人id：" + borrower.getId());
			// 已存在(更新借款人)
			borrower.setPhone(phone);
			borrower.setCreateTime(now);
			borrower.setAuthStep(1);
			borrower.setFlag(1);
			borrower.setState(1);
			borrower.setChannel(11); // 表示该借款人来源于融360
			borrower.setIdCard(idCard);
			borrower.setName(name);
			borrower.setAge(getAgeByIdCard(idCard));
			borrower.setSex(getSexByIdCard(idCard));
			bwBorrowerService.updateBwBorrower(borrower);
		} else {
			// 创建借款人
			String password = "123456a";
			borrower = new BwBorrower();
			borrower.setPhone(phone);
			borrower.setPassword(CommUtils.getMD5(password.getBytes()));
			borrower.setCreateTime(now);
			borrower.setAuthStep(1);
			borrower.setFlag(1);
			borrower.setState(1);
			borrower.setChannel(11); // 表示该借款人来源于融360
			borrower.setIdCard(idCard);
			borrower.setName(name);
			borrower.setAge(getAgeByIdCard(idCard));
			borrower.setSex(getSexByIdCard(idCard));
			bwBorrowerService.addBwBorrower(borrower);
			logger.info("生成的借款人id：" + borrower.getId());
		}
		BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
		if (CommUtils.isNull(bwOrderRong)) {
			// 创建工单
			BwOrder order = new BwOrder();
			order.setOrderNo(OrderUtil.generateOrderNo());
			if (CommUtils.isNull(borrower.getId())) {
				return -4;
			}
			order.setBorrowerId(borrower.getId());
			order.setStatusId(1L);
			order.setCreateTime(now);
			order.setChannel(11);
			order.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
			order.setApplyPayStatus(0);
			bwOrderService.addBwOrder(order);
			logger.info("生成的工单号：" + order.getId());
			// 将工单与融360的订单绑定
			bwOrderRong = new BwOrderRong();
			logger.info("订单号为：" + orderNo);
			bwOrderRong.setOrderId(order.getId());
			bwOrderRong.setThirdOrderNo(orderNo);
			bwOrderRong.setChannelId(11l);
			bwOrderRong.setCreateTime(now);
			bwOrderRongService.save(bwOrderRong);
			// 公司信息
			BwWorkInfo bwi = new BwWorkInfo();
			bwi.setOrderId(order.getId());
			bwi.setCallTime("10:00 - 12:00");// 默认值（融360没有提供相关数据）
			bwi.setCreateTime(now);
			if (CommUtils.isNull(applyDetail.getWork_period())) {
				bwi.setWorkYears("1年以内");
			} else {
				switch (applyDetail.getWork_period()) {
				case "1":
					bwi.setWorkYears("1年以内");
					break;
				case "2":
					bwi.setWorkYears("1年以内");
					break;
				case "3":
					bwi.setWorkYears("1-3年");
					break;
				case "4":
					bwi.setWorkYears("3-5年");
					break;
				case "5":
					bwi.setWorkYears("5-10年");
					break;
				default:
					bwi.setWorkYears("1年以内");
					break;
				}
			}
			bwWorkInfoService.save(bwi, borrower.getId());
			// 录入通讯录
			AddInfo addInfo = rongOrderDetail.getAddInfo();
			Contacts contacts = addInfo.getContacts();
			if (CommUtils.isNull(contacts)) {
				return -5;
			}
			List<PhoneList> phoneLists = contacts.getPhone_list();
			List<BwContactList> list = new ArrayList<>();
			for (PhoneList phoneList : phoneLists) {
				if (CommUtils.isNull(phoneList.getName())) {
					continue;
				}
				if (CommUtils.isNull(phoneList.getPhone())) {
					continue;
				}
				BwContactList bwContactList = new BwContactList();
				bwContactList.setBorrowerId(borrower.getId());
				bwContactList.setCreateTime(now);
				bwContactList.setName(phoneList.getName());
				bwContactList.setPhone(phoneList.getPhone());
				bwContactList.setUpdateTime(now);
				list.add(bwContactList);

			}
			bwContactListService.addOrUpdateBwContactLists(list, borrower.getId());
			// 运营商信息
			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Mobile mobile = addInfo.getMobile();
			User user = mobile.getUser();
			phone = user.getPhone();
			logger.info("借款人的手机号是：" + phone);
			BwOperateBasic bwOperateBasic = bwOperateBasicService.getBwOperateBasicByBorrowerId(borrower.getId());
			if (CommUtils.isNull(bwOperateBasic)) {
				// 添加基本信息
				bwOperateBasic = new BwOperateBasic();
				bwOperateBasic.setUserSource(user.getUser_source());
				bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
				bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
				bwOperateBasic.setPhone(user.getPhone());
				bwOperateBasic.setPhoneRemain(user.getPhone_remain());
				bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
				if (!CommUtils.isNull(user.getReg_time())) {
					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
				}
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasicService.save(bwOperateBasic);
			} else {
				// 修改基本信息
				// System.out.println("根据手机号查询的id是：" + bwOperateBasic.getId());
				bwOperateBasic.setUserSource(user.getUser_source());
				bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
				bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
				bwOperateBasic.setPhone(user.getPhone());
				bwOperateBasic.setPhoneRemain(user.getPhone_remain());
				bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
				if (!CommUtils.isNull(user.getReg_time())) {
					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
				}
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasicService.update(bwOperateBasic);
			}
			Date updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
			Date callDate = updateTime;
			callDate = sdf_hms.parse(sdf_hms.format(callDate));
			List<Tel> tels = mobile.getTel();
			for (Tel tel : tels) {
				List<TelData> lists = tel.getTeldata();
				if (!CommUtils.isNull(lists)) {
					for (TelData telData : lists) {
						Date jsonCallData = sdf_hms.parse(telData.getCall_time());
						if (jsonCallData.after(callDate)) {
							BwOperateVoice bwOperateVoice = new BwOperateVoice();
							bwOperateVoice.setUpdateTime(now);
							bwOperateVoice.setBorrower_id(borrower.getId());
							// 检验日期格式
							String callTime = null;
							try {
								callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
							} catch (ParseException e) {
								e.printStackTrace();
								// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
								continue;
							}
							bwOperateVoice.setCall_time(callTime);
							bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
							bwOperateVoice.setReceive_phone(telData.getReceive_phone());
							bwOperateVoice
									.setTrade_addr(CommUtils.isNull(telData.getTrade_addr()) ? "" : user.getAddr());
							bwOperateVoice.setTrade_time(telData.getTrade_time());
							bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
							bwOperateVoiceService.save(bwOperateVoice);
						}
					}
				}
			}
			// 芝麻信用分
			Zhima zhima = addInfo.getZhima();
			BwZmxyScore bwZmxyScore = new BwZmxyScore();
			bwZmxyScore.setName(name);
			bwZmxyScore.setIdCard(idCard);
			bwZmxyScore = bwZmxyScoreService.findByAttr(bwZmxyScore);
			if (CommUtils.isNull(bwZmxyScore)) {
				bwZmxyScore = new BwZmxyScore();
				bwZmxyScore.setIdCard(idCard);
				bwZmxyScore.setName(name);
				bwZmxyScore.setPhone(phone);
				bwZmxyScore.setZmxyScore(zhima.getAli_trust_score().getScore());
				bwZmxyScore.setCreateTime(now);
				bwZmxyScoreService.save(bwZmxyScore);
			} else {
				// 更新
				bwZmxyScore.setZmxyScore(zhima.getAli_trust_score().getScore());
				bwZmxyScore.setUpdateTime(now);
				bwZmxyScoreService.update(bwZmxyScore);
			}
		} else {
			// 查询工单
			BwOrder order = new BwOrder();
			order.setId(bwOrderRong.getOrderId());
			order = bwOrderService.findBwOrderByAttr(order);
			// 更新公司信息
			BwWorkInfo bwi = new BwWorkInfo();
			bwi.setOrderId(order.getId());
			bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
			bwi.setOrderId(order.getId());
			bwi.setCallTime("10:00 - 12:00");// 默认值（融360没有提供相关数据）
			bwi.setUpdateTime(now);
			if (CommUtils.isNull(applyDetail.getWork_period())) {
				bwi.setWorkYears("1年以内");
			} else {
				switch (applyDetail.getWork_period()) {
				case "1":
					bwi.setWorkYears("1年以内");
					break;
				case "2":
					bwi.setWorkYears("1年以内");
					break;
				case "3":
					bwi.setWorkYears("1-3年");
					break;
				case "4":
					bwi.setWorkYears("3-5年");
					break;
				case "5":
					bwi.setWorkYears("5-10年");
					break;
				default:
					bwi.setWorkYears("1年以内");
					break;
				}
			}
			bwWorkInfoService.update(bwi);
			// 录入通讯录
			AddInfo addInfo = rongOrderDetail.getAddInfo();
			Contacts contacts = addInfo.getContacts();
			List<PhoneList> phoneLists = contacts.getPhone_list();
			List<BwContactList> list = new ArrayList<>();
			for (PhoneList phoneList : phoneLists) {
				if (CommUtils.isNull(phoneList.getName())) {
					continue;
				}
				if (CommUtils.isNull(phoneList.getPhone())) {
					continue;
				}
				BwContactList bwContactList = new BwContactList();
				bwContactList.setBorrowerId(borrower.getId());
				bwContactList.setCreateTime(now);
				bwContactList.setName(phoneList.getName().toString());
				bwContactList.setPhone(phoneList.getPhone());
				bwContactList.setUpdateTime(now);
				list.add(bwContactList);
			}
			bwContactListService.addOrUpdateBwContactLists(list, borrower.getId());
			// 运营商信息
			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Mobile mobile = addInfo.getMobile();
			User user = mobile.getUser();
			phone = user.getPhone();
			if (CommUtils.isNull(phone)) {
				return -7;
			}
			logger.info("借款人的手机号是：" + phone);
			BwOperateBasic bwOperateBasic = bwOperateBasicService.getBwOperateBasicByBorrowerId(borrower.getId());
			if (CommUtils.isNull(bwOperateBasic)) {
				// 添加基本信息
				bwOperateBasic = new BwOperateBasic();
				bwOperateBasic.setUserSource(user.getUser_source());
				bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
				bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
				bwOperateBasic.setPhone(user.getPhone());
				bwOperateBasic.setPhoneRemain(user.getPhone_remain());
				bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
				if (!CommUtils.isNull(user.getReg_time())) {
					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
				}
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasicService.save(bwOperateBasic);
			} else {
				// 修改基本信息
				bwOperateBasic.setUserSource(user.getUser_source());
				bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
				bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
				bwOperateBasic.setPhone(user.getPhone());
				bwOperateBasic.setPhoneRemain(user.getPhone_remain());
				bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
				if (!CommUtils.isNull(user.getReg_time())) {
					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
				}
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasicService.update(bwOperateBasic);
			}
			Date updateTime = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
			Date callDate = updateTime;
			callDate = sdf_hms.parse(sdf_hms.format(callDate));
			List<Tel> tels = mobile.getTel();
			for (Tel tel : tels) {
				List<TelData> lists = tel.getTeldata();
				if (!CommUtils.isNull(lists)) {
					for (TelData telData : lists) {
						Date jsonCallData = sdf_hms.parse(telData.getCall_time());
						if (jsonCallData.after(callDate)) {
							BwOperateVoice bwOperateVoice = new BwOperateVoice();
							bwOperateVoice.setUpdateTime(now);
							bwOperateVoice.setBorrower_id(borrower.getId());
							// 检验日期格式
							String callTime = null;
							try {
								callTime = sdf_hms.format(sdf_hms.parse(telData.getCall_time()));
							} catch (ParseException e) {
								e.printStackTrace();
								// System.out.println("错误的日期格式：" + telData.getCall_time() + "，已跳过。");
								continue;
							}
							bwOperateVoice.setCall_time(callTime);
							bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
							bwOperateVoice.setReceive_phone(telData.getReceive_phone());
							bwOperateVoice
									.setTrade_addr(CommUtils.isNull(telData.getTrade_addr()) ? "" : user.getAddr());
							bwOperateVoice.setTrade_time(telData.getTrade_time());
							bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
							bwOperateVoiceService.save(bwOperateVoice);
						}
					}
				}
			}
			// 芝麻信用分
			Zhima zhima = addInfo.getZhima();
			if (!CommUtils.isNull(zhima)) {
				BwZmxyScore bwZmxyScore = new BwZmxyScore();
				bwZmxyScore.setName(name);
				bwZmxyScore.setIdCard(idCard);
				bwZmxyScore = bwZmxyScoreService.findByAttr(bwZmxyScore);
				if (CommUtils.isNull(bwZmxyScore)) {
					bwZmxyScore = new BwZmxyScore();
					bwZmxyScore.setIdCard(idCard);
					bwZmxyScore.setName(name);
					bwZmxyScore.setPhone(phone);
					bwZmxyScore.setZmxyScore(zhima.getAli_trust_score().getScore());
					bwZmxyScore.setCreateTime(now);
					bwZmxyScoreService.save(bwZmxyScore);
				} else {
					// 更新
					bwZmxyScore.setZmxyScore(zhima.getAli_trust_score().getScore());
					bwZmxyScore.setUpdateTime(now);
					bwZmxyScoreService.update(bwZmxyScore);
				}
			}
			logger.info("推单结果成功");
			return 1;
		}
		logger.info("推单结果成功");
		return 1;
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

}
