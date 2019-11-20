package com.waterelephant.drainage.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.drainage.entity.common.PushOrderRequest;
import com.waterelephant.drainage.entity.xianJinCard.AddressBook;
import com.waterelephant.drainage.entity.xianJinCard.BasicVo;
import com.waterelephant.drainage.entity.xianJinCard.CallsVo;
import com.waterelephant.drainage.entity.xianJinCard.CarInfo;
import com.waterelephant.drainage.entity.xianJinCard.ContactInfo;
import com.waterelephant.drainage.entity.xianJinCard.Email;
import com.waterelephant.drainage.entity.xianJinCard.FamilyInfo;
import com.waterelephant.drainage.entity.xianJinCard.HouseInfo;
import com.waterelephant.drainage.entity.xianJinCard.Marriage;
import com.waterelephant.drainage.entity.xianJinCard.OperatorVerify;
import com.waterelephant.drainage.entity.xianJinCard.OrderInfo;
import com.waterelephant.drainage.entity.xianJinCard.UserAdditional;
import com.waterelephant.drainage.entity.xianJinCard.UserInfo;
import com.waterelephant.drainage.entity.xianJinCard.UserVerify;
import com.waterelephant.drainage.entity.xianJinCard.WorkEnterpriseInfo;
import com.waterelephant.drainage.entity.xianJinCard.WorkFreeInfo;
import com.waterelephant.drainage.entity.xianJinCard.WorkOfficeInfo;
import com.waterelephant.drainage.entity.xianJinCard.WorkSoleInfo;
import com.waterelephant.drainage.entity.xianJinCard.XianJinCardCommonRequest;
import com.waterelephant.drainage.entity.xianJinCard.XianJinCardResponse;
import com.waterelephant.drainage.entity.xianJinCard.ZmVerify;
import com.waterelephant.drainage.service.DrainageService;
import com.waterelephant.drainage.service.XianJinCardService;
import com.waterelephant.drainage.util.DrainageUtils;
import com.waterelephant.drainage.util.xianjincard.XianJinCardConstant;
import com.waterelephant.drainage.util.xianjincard.XianJinCardUtils;
import com.waterelephant.dto.ProductFeeDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwIdentityCard2;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.service.CheckUserService;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.third.utils.ThirdUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * 
 * Module:
 * 
 * XianJinCardServiceImpl.java 现金白卡接口
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class XianJinCardServiceImpl implements XianJinCardService {
	private Logger logger = Logger.getLogger(XianJinCardServiceImpl.class);
	@Autowired
	DrainageService drainageService;
	@Autowired
	private CheckUserService checkUserService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private ThirdCommonService thirdCommonService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private IBwOrderChannelService bwOrderChannelService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;

	/**
	 * 用户过滤
	 */
	@Override
	public XianJinCardResponse checkUser(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest) {
		logger.warn(sessionId + "开始userCheck()方法>>>" + xianJinCardCommonRequest.toString());
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String userName = xianJinCardCommonRequest.getUser_name();
			String userPhone = xianJinCardCommonRequest.getUser_phone();
			String userIdCard = xianJinCardCommonRequest.getUser_idcard();
			// String md5 = xianJinCardCommonRequest.getMd5();
			if (StringUtils.isBlank(userName)) {
				return failReturn(sessionId, map, "姓名为空");
			}
			if (StringUtils.isBlank(userPhone)) {
				return failReturn(sessionId, map, "手机号码为空");
			}
			if (StringUtils.isBlank(userIdCard)) {
				return failReturn(sessionId, map, "身份证号码为空");
			}

			// 第一步：年龄判断
			int age = DrainageUtils.getAgeByIdCard(userIdCard);
			if (age < 20 || age > 45) {
				map.put("result", 301);
				return failReturn(sessionId, map, "用户年龄过大或过小");
			}

			// 第二步：是否黑名单
			userIdCard = userIdCard.replace("*", "%");
			userPhone = userPhone.replace("*", "%");
			boolean isBlackUser = checkUserService.isBlackUser(userIdCard, userName, userPhone);
			if (isBlackUser) {
				// 命中黑名单
				map.put("result", 403);
				return failReturn(sessionId, map, "该用户是征信系统黑名单用户");
			}
			// 第三步：是否进行中的订单
			boolean isProcessingOrder = checkUserService.isPocessingOrder(userIdCard, userName, userPhone);
			if (isProcessingOrder) {
				// 命中进行中订单
				map.put("result", 401);
				return failReturn(sessionId, map, "用户在机构有未完成的借款");
			}

			// 第四步：是否有被拒记录 -1 为永拒 1为7天被拒
			String isRejectRecord = isRejectRecord(userIdCard, userName, userPhone);
			if (!"0".equals(isRejectRecord)) {
				map.put("result", 505);
				if ("1".equals(isRejectRecord)) {
					map.put("can_loan_time",
							MyDateUtils.DateToString(MyDateUtils.addDays(new Date(), 7), "yyyy-MM-dd"));
				} // 如当前没有借款权限，需告知用户在什么时候才可以借款，精确到天即可.例如：2017-02-29
				return failReturn(sessionId, map, "命中被机构审批拒绝中规则");

			}
			// 第五步，获取产品
			BwProductDictionary bwProductDictionary = thirdCommonService.getProductByLike(userName, userIdCard,
					userPhone, 0);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
			xianJinCardResponse.setMessage("success");
			map.put("result", 200);// 借款权限
			map.put("amount", 5000 * 100);// 可贷额度，单位: 分
			List<Integer> list = new ArrayList<Integer>();
			if (null != bwProductDictionary && StringUtils.isNumeric(bwProductDictionary.getpTerm())) {
				list.add(NumberUtils.toInt(bwProductDictionary.getpTerm()));
			} else {
				list.add(14);
			}
			map.put("terms", list);// 可贷期限。如: [7,14,30]
			map.put("term_type", 1);// 贷款期限单位。1:按天; 2：按月; 3：按年
			map.put("loan_mode", 0);// 0：标准流程；1：简化流程
			xianJinCardResponse.setResponse(map);
		} catch (Exception e) {
			logger.error(sessionId + "：执行checkUser method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
		}
		logger.warn(sessionId + "：结束checkUser：" + JSON.toJSONString(xianJinCardResponse));
		return xianJinCardResponse;
	}

	/**
	 * 保存工单信息
	 * 
	 */
	@Override
	public XianJinCardResponse savePushOrder(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest) {
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		logger.warn(sessionId + "-开始pushOrder");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			OrderInfo orderInfo = xianJinCardCommonRequest.getOrder_info();
			UserInfo userInfo = xianJinCardCommonRequest.getUser_info();
			UserAdditional userAdditional = xianJinCardCommonRequest.getUser_additional();
			UserVerify userVerify = xianJinCardCommonRequest.getUser_verify();
			if (null == orderInfo || null == userInfo || null == userAdditional || null == userVerify) {
				return failReturn(sessionId, map, "请求参数为空！");
			}
			String name = userInfo.getUser_name(); // 姓名
			String idCard = userInfo.getId_number(); // 身份证号
			String phone = userInfo.getUser_phone(); // 注册手机号

			if (StringUtils.isBlank(name)) {
				return failReturn(sessionId, map, "姓名为空");
			}
			if (StringUtils.isBlank(idCard)) {
				return failReturn(sessionId, map, "手机号码为空");
			}
			if (StringUtils.isBlank(phone)) {
				return failReturn(sessionId, map, "身份证号码为空");
			}

			String thirdOrderNo = orderInfo.getOrder_sn();
			String loanAmountStr = orderInfo.getLoan_amount();// 借款金额 单位（分）
			if (StringUtils.isBlank(thirdOrderNo)) {
				return failReturn(sessionId, map, "第三方订单号为空");
			}
			// if (StringUtils.isBlank(loanAmountStr) || !StringUtils.isNumeric(loanAmountStr)) {
			// return failReturn(sessionId, map, "借款金额错误");
			// }
			Double loanAmount = 0.0D;
			if (StringUtils.isNotBlank(loanAmountStr) || StringUtils.isNumeric(loanAmountStr)) {
				loanAmount = NumberUtils.toDouble(loanAmountStr) / 100D;
			}
			String loadTerm = orderInfo.getLoan_term();// 借款期限
			// String termType = orderInfo.getTerm_type();// 期限单位：1: 按天; 2: 按月; 3: 按年;
			int loadt = 0;
			if (StringUtils.isNotBlank(loadTerm) && StringUtils.isNumeric(loadTerm)) {
				loadt = NumberUtils.toInt(loadTerm);
			}
			// 渠道Id
			String channelIdStr = XianJinCardConstant.CHANNELID;
			if (StringUtils.isBlank(channelIdStr) || !StringUtils.isNumeric(channelIdStr)) {
				return failReturn(sessionId, map, "渠道不存在！");
			}
			int channelId = NumberUtils.toInt(channelIdStr);

			PushOrderRequest pushOrderRequest = new PushOrderRequest();
			pushOrderRequest.setThirdOrderNo(thirdOrderNo);
			pushOrderRequest.setUserName(name);
			pushOrderRequest.setPhone(phone);
			pushOrderRequest.setIdCard(idCard);
			pushOrderRequest.setChannelId(channelId);
			pushOrderRequest.setExpectMoney(loanAmount);
			pushOrderRequest.setExpectNumber(loadt);

			String professionType = userInfo.getProfession_type(); // 职业类型 1上班族 2企业主 3个体户 4学生 5自由职业
			logger.warn(sessionId + "（获取工作信息）");
			BwWorkInfo bwWorkInfo = getWorkInfo(sessionId, userAdditional, professionType, pushOrderRequest);
			pushOrderRequest.setBwWorkInfo(bwWorkInfo);

			logger.warn(sessionId + "（获取通讯录信息）");
			List<BwContactList> contactList = getContactList(sessionId, userVerify);
			pushOrderRequest.setBwContactList(contactList);

			OperatorVerify operatorVerify = userVerify.getOperator_verify();
			logger.warn(sessionId + "（获取运营商信息）");
			BwOperateBasic bwOperateBasic = getOperatorVerify(sessionId, operatorVerify);
			pushOrderRequest.setBwOperateBasic(bwOperateBasic);

			logger.warn(sessionId + "（获取通话记信息）");
			List<BwOperateVoice> bwOperateVoiceList = getCalls(sessionId, operatorVerify);
			pushOrderRequest.setBwOperateVoiceList(bwOperateVoiceList);

			ZmVerify zmVerify = userVerify.getZm_verify();
			logger.warn(sessionId + "（获取芝麻信用信息）");
			if (null != zmVerify) {
				int sesameScore = zmVerify.getZmxy_score();
				pushOrderRequest.setSesameScore(sesameScore);
			}

			logger.warn(sessionId + "（获取身份证图片信息）");
			pushOrderRequest.setIdCardFrontImage(userInfo.getId_number_z_picture());
			pushOrderRequest.setIdCardBackImage(userInfo.getId_number_f_picture());
			if (StringUtils.isNotBlank(userInfo.getId_number_picture())) {
				pushOrderRequest.setIdCardHanderImage(userInfo.getId_number_picture());
			} else {
				pushOrderRequest.setIdCardHanderImage(userInfo.getFace_recognition_picture());
			}
			BwIdentityCard2 bwIdentityCard2 = getIdentityCard(sessionId, userInfo);
			pushOrderRequest.setBwIdentityCard(bwIdentityCard2);

			logger.warn(sessionId + "（获取亲属联系人信息）");
			BwPersonInfo bwPersonInfo = getPersonInfo(sessionId, userAdditional, userVerify);
			pushOrderRequest.setBwPersonInfo(bwPersonInfo);
			// 无银行卡信息
			pushOrderRequest.setOrderStatus(2);
			String json = JSON.toJSONString(pushOrderRequest);
			RedisUtils.rpush("tripartite:pushOrder", json);
			// DrainageResponse drainageResponse = drainageService.savePushOrder(sessionId, pushOrderRequest);
			// if ("0000".equals(drainageResponse.getCode())) {
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
			xianJinCardResponse.setMessage("success");
			xianJinCardResponse.setResponse(true);
			logger.warn(sessionId + "-结束pushOrder>>>>success");
			// } else {
			// xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			// xianJinCardResponse.setMessage(drainageResponse.getMessage());
			// logger.warn(sessionId + "-结束pushOrder>>>>" + drainageResponse.getMessage());
			// }
		} catch (Exception e) {
			logger.error(sessionId + "：执行pushOrder method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
			logger.warn(sessionId + "：结束pushOrder：" + e.getMessage());
		}
		return xianJinCardResponse;
	}

	/**
	 * 
	 * @param sessionId
	 * @param userAdditional
	 * @param userVerify
	 * @return
	 */
	private BwPersonInfo getPersonInfo(long sessionId, UserAdditional userAdditional, UserVerify userVerify) {
		String homeAdr = "", homeAreas = "", email = "", firstName = "", firstPhone = "", secontName = "",
				secondPhone = "";
		int carStatu = -1, houseStatu = -1, marrStatu = -1;
		FamilyInfo familyInfo = userAdditional.getFamily_info();
		if (null != familyInfo) {
			homeAdr = familyInfo.getHome_address();
			homeAreas = familyInfo.getHome_areas();
		}
		CarInfo carInfo = userAdditional.getCar_info();
		if (null != carInfo) {
			if ("1".equals(carInfo.getCar_status()) || "2".equals(carInfo.getCar_status())
					|| StringUtils.isNotBlank(carInfo.getCar_price())) {
				carStatu = 1;
			}
		}
		HouseInfo houseInfo = userAdditional.getHouse_info();
		if (null != houseInfo) {
			if (StringUtils.isNotBlank(houseInfo.getHouse_status()) && !"1".equals(houseInfo.getHouse_status())) {
				houseStatu = 1;
			} else {
				houseStatu = 0;
			}
		}
		Email emailVo = userAdditional.getEmail();
		if (null != emailVo) {
			email = emailVo.getEmail();
		}
		Marriage marriage = userAdditional.getMarriage();
		if (null != marriage) {
			String marriageStr = marriage.getMarriage();// 婚姻状况:1.未婚,2.已婚,3.离异
			if ("2".equals(marriageStr)) {
				marrStatu = 1;
			} else {
				marrStatu = 0;
			}
		}
		ContactInfo contact_info = userVerify.getContact_info();
		if (null != contact_info) {
			firstName = contact_info.getName();
			firstPhone = contact_info.getMobile();
			secontName = contact_info.getName_spare();
			secondPhone = contact_info.getMobile_spare();
		}
		BwPersonInfo bwPersonInfo = new BwPersonInfo();
		bwPersonInfo.setAddress(homeAdr);
		bwPersonInfo.setCityName(homeAreas);
		bwPersonInfo.setEmail(email);
		if (-1 != houseStatu) {
			bwPersonInfo.setHouseStatus(houseStatu);
		}
		if (-1 != marrStatu) {
			bwPersonInfo.setCarStatus(carStatu);
		}
		if (-1 != marrStatu) {
			bwPersonInfo.setMarryStatus(marrStatu);
		}
		bwPersonInfo.setRelationName(firstName);
		bwPersonInfo.setRelationPhone(firstPhone);
		bwPersonInfo.setUnrelationName(secontName);
		bwPersonInfo.setUnrelationPhone(secondPhone);
		return bwPersonInfo;
	}

	/**
	 * 
	 * @param sessionId
	 * @param userInfo
	 */
	private BwIdentityCard2 getIdentityCard(long sessionId, UserInfo userInfo) {
		BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
		try {
			String idCard = userInfo.getId_number();
			String name = userInfo.getUser_name();
			String birthday = userInfo.getOcr_birthday();
			if (StringUtils.isBlank(birthday)) {
				String year = idCard.substring(6, 10);
				String month = idCard.substring(10, 12);
				String day = idCard.substring(12, 14);
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
				birthday = year + month + day;
			}
			String gender = userInfo.getOcr_sex();
			if (StringUtils.isBlank(gender)) {
				int sexNum = ThirdUtil.getSexByIdCard(idCard);
				if (sexNum == 0) {
					gender = "女";
				} else {
					gender = "男";
				}
			}
			bwIdentityCard.setValidDate(userInfo.getOcr_start_time() + "-" + userInfo.getOcr_end_time());
			bwIdentityCard.setAddress(userInfo.getOcr_address());
			bwIdentityCard.setBirthday(birthday);
			bwIdentityCard.setGender(gender);
			bwIdentityCard.setIdCardNumber(idCard);
			bwIdentityCard.setIssuedBy(userInfo.getOcr_issued_by());
			bwIdentityCard.setName(name);
			bwIdentityCard.setRace(userInfo.getOcr_race());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bwIdentityCard;
	}

	/**
	 * 解析通话记录
	 * 
	 * @param sessionId
	 * @param operatorVerify
	 */
	private List<BwOperateVoice> getCalls(long sessionId, OperatorVerify operatorVerify) {
		List<BwOperateVoice> list = new ArrayList<BwOperateVoice>();
		if (null != operatorVerify) {
			List<CallsVo> callList = operatorVerify.getCalls();
			if (CollectionUtils.isNotEmpty(callList)) {
				BwOperateVoice bwOperateVoice = null;
				for (CallsVo callsVo : callList) {
					try {
						bwOperateVoice = new BwOperateVoice();
						bwOperateVoice.setUpdateTime(new Date());
						bwOperateVoice.setCall_time(callsVo.getStart_time());
						bwOperateVoice.setCall_type(callsVo.getcallType());
						bwOperateVoice.setReceive_phone(callsVo.getOther_cell_phone());
						bwOperateVoice.setTrade_addr(callsVo.getPlace());
						bwOperateVoice.setTrade_time(callsVo.getUse_time() + "");
						// bwOperateVoice.setTrade_type();
						list.add(bwOperateVoice);
					} catch (Exception e) {
						logger.error(sessionId + "插入单条通话记录异常，忽略该条记录");
					}
				}
			}
		}
		return list;
	}

	/**
	 * 解析运营商信息
	 * 
	 * @param sessionId
	 * @param operatorVerify
	 */
	private BwOperateBasic getOperatorVerify(long sessionId, OperatorVerify operatorVerify) {
		BwOperateBasic bwOperateBasic = new BwOperateBasic();
		try {
			String realName = "", idCard_oper = "", phone_oper = "", regTime = "", dataSource = "";
			if (null != operatorVerify) {
				dataSource = operatorVerify.getDatasource();
				if (StringUtils.isBlank(dataSource)) {
					dataSource = "网络号码";
				}
				BasicVo basicVo = operatorVerify.getBasic();
				if (null != basicVo) {
					idCard_oper = basicVo.getIdcard();
					phone_oper = basicVo.getCell_phone();
					realName = basicVo.getReal_name();
					regTime = basicVo.getReg_time();
				}
			}
			bwOperateBasic.setUserSource(dataSource);
			bwOperateBasic.setIdCard(idCard_oper);
			bwOperateBasic.setPhone(phone_oper);
			bwOperateBasic.setRealName(realName);
			if (StringUtils.isNotBlank(regTime)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				bwOperateBasic.setRegTime(sdf.parse(regTime));
			}
		} catch (Exception e) {
			logger.warn("解析运营商信息" + e.getMessage());
		}
		return bwOperateBasic;

	}

	/**
	 * 解析通讯录
	 * 
	 * @param sessionId
	 * @param userVerify
	 * @return
	 */
	private List<BwContactList> getContactList(long sessionId, UserVerify userVerify) {
		List<BwContactList> list = new ArrayList<BwContactList>();
		AddressBook addressBook = userVerify.getAddress_book();
		if (null != addressBook) {
			BwContactList bwContactList = null;
			List<String> phoneList = addressBook.getPhone_list();
			if (CollectionUtils.isNotEmpty(phoneList)) {
				for (String ph : phoneList) {
					if (StringUtils.isNotBlank(ph)) {
						String[] phoneArr = ph.split("_");
						if (phoneArr.length == 2) {
							try {
								bwContactList = new BwContactList();
								bwContactList.setCreateTime(new Date());
								bwContactList.setName(com.waterelephant.utils.CommUtils.filterEmoji(phoneArr[0]));
								bwContactList.setUpdateTime(new Date());
								bwContactList.setPhone(phoneArr[1]);
								list.add(bwContactList);
							} catch (Exception e) {
								logger.warn("解析记录失败，忽略该条记录" + e.getMessage());
							}
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * 解析工作信息
	 * 
	 * @param sessionId
	 * @param userAdditional
	 * @param professionType
	 * @param pushOrderRequest
	 */
	private BwWorkInfo getWorkInfo(long sessionId, UserAdditional userAdditional, String professionType,
			PushOrderRequest pushOrderRequest) {
		BwWorkInfo bwWorkInfo = new BwWorkInfo();
		String comName = "", workYears = "", income = "", industry = "";
		// 判断是否有工作信息
		if (null != userAdditional) {
			if ("1".equals(professionType)) {// 职业-上班族
				WorkOfficeInfo work_office_info = userAdditional.getWork_office_info();
				if (null != work_office_info) {
					comName = work_office_info.getCompany_name();
					workYears = XianJinCardUtils.getWorkAge(work_office_info.getWork_age());
					income = work_office_info.getRevenue();
					industry = XianJinCardUtils.getWorkType(work_office_info.getType());
				}
			} else if ("2".equals(professionType)) {// 职业-企业主
				WorkEnterpriseInfo workEnterpriseInfo = userAdditional.getWork_enterprise_info();
				if (null != workEnterpriseInfo) {
					comName = workEnterpriseInfo.getCompany_name();
					industry = workEnterpriseInfo.getIndustry();
					// workYears = workEnterpriseInfo.getManage_life_time();
					// income = workEnterpriseInfo.getTotal_revenue();
				}
			} else if ("3".equals(professionType)) {// 职业-个体户
				WorkSoleInfo workSoleInfo = userAdditional.getWork_sole_info();
				if (null != workSoleInfo) {
					// comName = workSoleInfo.getIndustry();
					industry = workSoleInfo.getIndustry();
					workYears = workSoleInfo.getManage_life_time();
					income = workSoleInfo.getTotal_revenue();// 每月总营收（万元）
				}
			} else if ("4".equals(professionType)) {// 职业-学生
			} else if ("5".equals(professionType)) {// 职业-自由职业
				WorkFreeInfo workFreeInfo = userAdditional.getWork_free_info();
				if (null != workFreeInfo) {
					income = workFreeInfo.getFree_income();// 每月总营收（万元）
				}
			}
		}
		bwWorkInfo = new BwWorkInfo();
		bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
		bwWorkInfo.setWorkYears(workYears);
		bwWorkInfo.setComName(comName);
		bwWorkInfo.setIncome(income);
		bwWorkInfo.setIndustry(industry);
		return bwWorkInfo;
	}

	/**
	 * 绑卡
	 * 
	 */
	@Override
	public Map<String, String> saveBindCard(long sessionId, Map<String, String> treeMap) {
		logger.warn(sessionId + "：开始bindcard method：" + treeMap.toString());
		Map<String, String> map = new HashMap<>();
		try {
			String userIdcard = treeMap.get("user_idcard");
			String userName = treeMap.get("user_name");
			String userPhone = treeMap.get("user_phone");
			String returnUrl = treeMap.get("return_url");
			String thirdOrderNo = treeMap.get("order_sn");// 推单前绑卡 为空，推单后绑卡 不为空
			if (StringUtils.isBlank(userIdcard) || StringUtils.isBlank(userName) || StringUtils.isBlank(userPhone)
					|| StringUtils.isBlank(returnUrl)) {
				map.put("resCode", "-1");
				map.put("resMsg", "入参不合法！");
				return map;
			}

			String channelIdStr = XianJinCardConstant.CHANNELID;
			if (StringUtils.isBlank(channelIdStr) || !StringUtils.isNumeric(channelIdStr)) {
				map.put("resCode", "-1");
				map.put("resMsg", "渠道不存在！");
				return map;
			}
			int channelId = NumberUtils.toInt(channelIdStr);
			BwOrderChannel orderChannel = new BwOrderChannel();
			orderChannel.setId(channelId);
			BwOrderChannel bwOrderChannel = bwOrderChannelService.findBwOrderChannel(orderChannel);
			// 新增或修改借款人
			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, userIdcard, userPhone,
					channelId);
			// 订单
			BwOrder bwOrder = null;
			BwOrderRong bwOrderRong = null;
			if (StringUtils.isNotBlank(thirdOrderNo)) {
				bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
				if (!CommUtils.isNull(bwOrderRong)) {
					Long orderId = bwOrderRong.getOrderId();
					bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
				}
			}
			// 生成订单
			if (CommUtils.isNull(bwOrder)) {
				bwOrder = new BwOrder();
				bwOrder.setBorrowerId(borrower.getId());
				bwOrder.setStatusId(1L);
				bwOrder.setProductType(1);
				bwOrder.setChannel(channelId);
				List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
				bwOrder.setStatusId(8L);

				List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
				boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
				if (boList != null && boList.size() > 0) {
					bwOrder = boList.get(boList.size() - 1);
					bwOrder.setUpdateTime(Calendar.getInstance().getTime());
					bwOrder.setProductType(1);
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
					bwOrder.setProductType(1);
					bwOrderService.addBwOrder(bwOrder);
				}
			}
			if (CommUtils.isNull(bwOrderRong) && StringUtils.isNotBlank(thirdOrderNo)) {
				bwOrderRong = new BwOrderRong();
				bwOrderRong.setOrderId(bwOrder.getId());
				bwOrderRong.setThirdOrderNo(thirdOrderNo);
				bwOrderRong.setChannelId(Long.valueOf(channelId));
				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
				bwOrderRongService.save(bwOrderRong);
			}
			// String bindCardFront = RedisUtils.rpush("tripartite:bindCard:front:" + bwOrder.getId(), "");
			RedisUtils.setNxAndEx("tripartite:bindCard:front:" + bwOrder.getId(), bwOrder.getId() + "", 60 * 30);
			// 绑卡回调使用

			RedisUtils.setNxAndEx("xianjin:echo_data:" + channelId + ":" + bwOrder.getId(), treeMap.get("echo_data"),
					60 * 60 * 12);

			// 绑卡结果展示URL
			RedisUtils.hset("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + bwOrder.getOrderNo(),
					returnUrl);
			RedisUtils.hset("third:bindCard:failReturnUrl:" + channelId, "orderNO_" + bwOrder.getOrderNo(), returnUrl);
			String directUrl = "/third/xianjincard/bindcardDirect.do?channelId=" + channelId + "&orderNO="
					+ bwOrder.getOrderNo() + "&echo_data=" + treeMap.get("echo_data");
			RedisUtils.hset("third:bindCard:selfUrl:" + channelId, "orderNO_" + bwOrder.getOrderNo(), directUrl);
			// RedisUtils.setNxAndEx("third:bindCard:selfUrl:" + channelId + ":orderNO_" + bwOrder.getOrderNo(),
			// directUrl,
			// 60 * 60 * 12);
			map.put("resCode", "1");
			String url = XianJinCardConstant.BINDCARD_URL + "/weixinApp/html/LianlianMidPage/index.html?orderNO="
					+ bwOrder.getOrderNo() + "&channelCode=" + bwOrderChannel.getChannelCode();
			map.put("resMsg", url);
			return map;
		} catch (Exception e) {
			logger.error(sessionId + "：执行XianJinCardServiceImpl.bindcard method 异常：", e);
			map.put("resCode", "-1");
			map.put("resMsg", "请求失败！");
			return map;
		}
	}

	/**
	 * 获取已经绑定银行卡
	 */
	@Override
	public XianJinCardResponse getBindCard(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest) {
		logger.warn(sessionId + "：开始getBindCard method：" + JSON.toJSONString(xianJinCardCommonRequest));
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String userIdcard = xianJinCardCommonRequest.getUser_idcard();
			String userName = xianJinCardCommonRequest.getUser_name();
			String userPhone = xianJinCardCommonRequest.getUser_phone();

			if (StringUtils.isBlank(userIdcard) || StringUtils.isBlank(userName) || StringUtils.isBlank(userPhone)) {
				return failReturn(sessionId, map, "传入参数为空！");
			}
			BwBorrower bwBorrower = new BwBorrower();
			bwBorrower.setName(userName);
			bwBorrower.setIdCard(userIdcard);
			bwBorrower.setPhone(userPhone);
			bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
			if (null == bwBorrower) {
				return failReturn(sessionId, map, "用户不存在！");
			}

			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwBorrower.getId());

			if (null == bwBankCard || "0".equals(bwBankCard.getSignStatus())) {
				xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
				xianJinCardResponse.setMessage("没有绑定的银行卡");
			} else {
				xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
				xianJinCardResponse.setMessage("success");
				List<Map<String, Object>> list = new ArrayList<>();
				map.put("bind_status", "200");
				map.put("bank_name", bwBankCard.getBankName());
				map.put("bank_code", XianJinCardUtils.convertToBankCode(bwBankCard.getBankName()));
				map.put("card_no", bwBankCard.getCardNo());
				map.put("bank_mobile", bwBankCard.getPhone());
				map.put("name", userName);
				map.put("main_card", 1);
				map.put("bank_address", "");
				list.add(map);
				xianJinCardResponse.setResponse(list);
			}
		} catch (Exception e) {
			logger.error(sessionId + "：执行getBindCard method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
		}
		logger.warn(sessionId + "：结束getBindCard：" + JSON.toJSONString(xianJinCardResponse));
		return xianJinCardResponse;
	}

	/**
	 * 审批签约
	 */
	@Override
	public XianJinCardResponse updateSignContract(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest) {
		logger.warn(sessionId + "：开始updateSignContract method：" + JSON.toJSONString(xianJinCardCommonRequest));
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String thirdOrderNo = xianJinCardCommonRequest.getOrder_sn();
			String confirmResult = xianJinCardCommonRequest.getConfirm_result();

			if (StringUtils.isBlank(thirdOrderNo) || StringUtils.isBlank(confirmResult)) {
				return failReturn(sessionId, map, "传入参数为空！");
			}
			// 渠道Id
			String channelIdStr = XianJinCardConstant.CHANNELID;
			if (StringUtils.isBlank(channelIdStr) || !StringUtils.isNumeric(channelIdStr)) {
				return failReturn(sessionId, map, "渠道不存在！");
			}
			int channelId = NumberUtils.toInt(channelIdStr);
			if ("200".equals(confirmResult)) {
				ThirdResponse thirdResponse = thirdCommonService.updateSignContract(thirdOrderNo, channelId);
				if (200 == thirdResponse.getCode()) {
					xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
					xianJinCardResponse.setMessage("success");
					xianJinCardResponse.setResponse(true);
				} else {
					return failReturn(sessionId, map, thirdResponse.getMsg());
				}
			}
		} catch (Exception e) {
			logger.error(sessionId + "：执行updateSignContract method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
		}
		logger.warn(sessionId + "：结束updateSignContract：" + JSON.toJSONString(xianJinCardResponse));
		return xianJinCardResponse;
	}

	/**
	 * 还款计划
	 */
	@Override
	public XianJinCardResponse getRepayplan(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest) {
		logger.warn(sessionId + "：开始getRepayplan method：" + JSON.toJSONString(xianJinCardCommonRequest));
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String thirdOrderNo = xianJinCardCommonRequest.getOrder_sn();

			if (StringUtils.isBlank(thirdOrderNo)) {
				return failReturn(sessionId, map, "第三方订单号为空！");
			}
			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				return failReturn(sessionId, map, "第三方订单不存在！");
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				return failReturn(sessionId, map, "订单为空！");
			}
			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bwBankCard)) {
				return failReturn(sessionId, map, "银行卡信息为空！");
			}
			// 获取还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwOrder.getId());
			if (CommUtils.isNull(bwRepaymentPlan)) {
				return failReturn(sessionId, map, "还款计划为空！");
			}

			int orderStatus = bwOrder.getStatusId().intValue();
			if (9 == orderStatus || 13 == orderStatus || 6 == orderStatus) {
				// 获取产品表
				Integer productId = bwOrder.getProductId();
				BwProductDictionary bwProductDictionary = bwProductDictionaryService
						.findBwProductDictionaryById(productId);
				Double borrowAmount = bwOrder.getBorrowAmount();// 审批金额

				ProductFeeDto productFeeDto = queryProductFee(borrowAmount, bwProductDictionary);
				Double service_fee = productFeeDto.getLoanAmount();// 服务费

				// 获取逾期表
				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
				bwOverdueRecord.setOrderId(bwOrder.getId());
				bwOverdueRecord = bwOverdueRecordService.queryBwOverdueRecord(bwOverdueRecord);
				Integer overdueDay = 0;
				Double overduefee = 0D;
				if (bwOverdueRecord != null) {
					overdueDay = bwOverdueRecord.getOverdueDay();// 逾期天数
					overduefee = bwOverdueRecord.getOverdueAccrualMoney();
				}
				Integer total_period = 1;
				if (null != bwProductDictionary.getDefaultNumber()) {
					total_period = bwProductDictionary.getDefaultNumber();// 总期数
				}
				Double repay_amount = DoubleUtil.add(borrowAmount, productFeeDto.getFundUtilizationFee());
				Double total_amount = DoubleUtil.add(repay_amount, overduefee);// 应还总额
				//
				List<Map<String, Object>> list = new ArrayList<>();
				map.put("order_sn", thirdOrderNo);// 订单唯一编号
				map.put("total_amount", total_amount * 100);// 还款总额 单位:分
				map.put("total_svc_fee", service_fee * 100);// 总服务费; 单位: 分
				map.put("already_paid", bwRepaymentPlan.getAlreadyRepayMoney() * 100);// 已还金额; 单位: 分
				map.put("total_period", total_period);// 总期数
				if (6 == orderStatus) {
					map.put("finish_period", bwRepaymentPlan.getNumber());// 已还期数
				} else {
					map.put("finish_period",
							(bwRepaymentPlan.getNumber() >= 1) ? (bwRepaymentPlan.getNumber() - 1) : 0);// 已还期数
				}
				Map<String, Object> map_new = new HashMap<>();
				map_new.put("period_no", bwRepaymentPlan.getNumber());// 还款期号
				map_new.put("principle", bwRepaymentPlan.getRepayCorpusMoney() * 100);// 本期还款本金 单位: 分
				map_new.put("interest", bwRepaymentPlan.getRepayAccrualMoney() * 100);// 本期还款利息; 单位: 分
				map_new.put("service_fee", service_fee * 100);// 本期服务费用; 单位: 分
				map_new.put("bill_status", (6 == orderStatus) ? "1" : "0");// 本期账单状态; -1: 未出账; 0: 待还款; 1: 已还款
				map_new.put("total_amount", total_amount * 100);// 本期还款总额; 单位: 分
				map_new.put("already_paid", bwRepaymentPlan.getAlreadyRepayMoney() * 100);// 本期已还金额; 单位: 分
				map_new.put("loan_time", bwRepaymentPlan.getCreateTime().getTime() / 1000);// 实际起息时间
				map_new.put("due_time", bwRepaymentPlan.getRepayTime().getTime() / 1000);// 最迟还款时间（精确到秒超过该时间就算逾期）
				map_new.put("can_pay_time", bwRepaymentPlan.getCreateTime().getTime() / 1000);// 可以还款时间
				if (6 == orderStatus) {
					map.put("finish_pay_time", bwOrder.getUpdateTime().getTime() / 1000 + "");// 实际完成还款时间
				} else {
					map.put("finish_pay_time", "0");// 实际完成还款时间
				}
				map_new.put("overdue_day", overdueDay);// 逾期天数
				map_new.put("overdue_fee", overduefee * 100);// 逾期费用; 单位: 分
				map_new.put("period_fee_desc", "还款金额:" + total_amount);// 本期费用描述
				map_new.put("pay_type", 4);// 还款支付方式; 如: 0.未还款 1. 主动还款 2.系统扣款 3. 支付宝转账 4. 银行转账或其它方式
				list.add(map_new);
				map.put("repayment_plan", list);// 具体还款计划数组
				xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
				xianJinCardResponse.setMessage("success");
				xianJinCardResponse.setResponse(map);
				logger.warn(sessionId + "：结束getRepayplan：" + JSON.toJSONString(xianJinCardResponse));
				return xianJinCardResponse;
			} else {
				return failReturn(sessionId, map, "还款计划为空！");
			}
		} catch (Exception e) {
			logger.error(sessionId + "：执行getRepayplan method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
			logger.warn(sessionId + "：结束getRepayplan：" + JSON.toJSONString(xianJinCardResponse));
			return xianJinCardResponse;
		}
	}

	/**
	 * 
	 * 获取订单状态 1: 拉取订单审批结果，2：拉取订单签约状态，3：拉取订单放款状态
	 */
	@Override
	public XianJinCardResponse getOrderStatus(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest) {
		logger.warn(sessionId + "：开始getOrderStatus method：" + JSON.toJSONString(xianJinCardCommonRequest));
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String thirdOrderNo = xianJinCardCommonRequest.getOrder_sn();
			int actType = xianJinCardCommonRequest.getAct_type();// 1: 拉取订单审批结果，2：拉取订单签约状态，3：拉取订单放款状态
			if (StringUtils.isBlank(thirdOrderNo)) {
				return failReturn(sessionId, map, "第三方订单号为空！");
			}
			// 根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				return failReturn(sessionId, map, "第三方订单不存在！");
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				return failReturn(sessionId, map, "订单为空！");
			}
			if (1 == actType) {
				// 1: 拉取订单审批结果，
				long orderStatus = bwOrder.getStatusId();
				String approve_status = "";
				if (7 == orderStatus || 8 == orderStatus) {
					approve_status = "403";// 403 审批拒绝
					map.put("approve_time", "");// 审批时间（十位标准时时间戳）（审批中为空）
					map.put("approve_remark", "评分不足");// 审批状态备注
				} else if (4 <= orderStatus) {
					approve_status = "200";// 200 审批通过
					map.put("approve_time", "");// 审批时间（十位标准时时间戳）（审批中为空）
					map.put("approve_remark", "OK");// 审批状态备注
				} else {
					approve_status = "100";// 100 审批中
					map.put("approve_time", "");// 审批时间（十位标准时时间戳）（审批中为空）
					map.put("approve_remark", "");// 审批状态备注
				}
				map.put("approve_term", bwOrder.getRepayTerm());// 审批后的可借周期TODO
				map.put("order_sn", thirdOrderNo);// 订单唯一编号
				map.put("result_type", actType);// 结果类型，要求和请求参数中的act_type一致。
				map.put("approve_status", approve_status);//
				String approve_amount = "";
				if (null != bwOrder.getBorrowAmount()) {
					approve_amount = 100 * (bwOrder.getBorrowAmount()) + "";
				}
				map.put("approve_amount", approve_amount);// 审批后的可借金额单位（分）
				int term_type = 1;
				if (null != bwOrder.getProductId()) {
					BwProductDictionary bwProductDictionary = bwProductDictionaryService
							.findBwProductDictionaryById(bwOrder.getProductId());
					if (null != bwProductDictionary) {
						if ("1".equals(bwProductDictionary.getpTermType())) {
							term_type = 2;// 产品类型（1：月；2：天）pTermType
						}
					}
				}
				map.put("term_type", term_type);// 1:按天; 2：按月; 3：按年
			} else if (2 == actType) {
				// 2：拉取订单签约状态，
				long orderStatus = bwOrder.getStatusId();
				String confirm_status = "";
				if (7 == orderStatus || 8 == orderStatus) {
					confirm_status = "402";// 402 签约拒绝
					map.put("confirm_remark", "");// 签约状态备注
				} else if (5 <= orderStatus) {
					confirm_status = "200";// 200 签约成功
					map.put("confirm_remark", "");// 签约状态备注
				} else {
					confirm_status = "100";// 100 待签约
					map.put("confirm_remark", "");// 签约状态备注
				}
				map.put("order_sn", thirdOrderNo);// 订单唯一编号
				map.put("result_type", actType);// 结果类型，要求和请求参数中的act_type一致。
				map.put("confirm_status", confirm_status);// 签约状态
				map.put("confirm_time", "");// 签约后时间（十位标准时时间戳）（待签约为空）
				String confirm_amount = "";
				if (null != bwOrder.getBorrowAmount()) {
					confirm_amount = 100 * (bwOrder.getBorrowAmount()) + "";
				}
				map.put("confirm_amount", confirm_amount);// 签约后的可借金额单位（分）
				int term_type = 1;
				if (null != bwOrder.getProductId()) {
					BwProductDictionary bwProductDictionary = bwProductDictionaryService
							.findBwProductDictionaryById(bwOrder.getProductId());
					if (null != bwProductDictionary) {
						if ("1".equals(bwProductDictionary.getpTermType())) {
							term_type = 2;// 产品类型（1：月；2：天）pTermType
						}
					}
				}
				map.put("term_type", term_type);// 1:按天; 2：按月; 3：按年
				map.put("confirm_term", "1");// 否 签约后的可借周期
			} else if (3 == actType) {
				long orderStatus = bwOrder.getStatusId();
				String lending_status = "";// 放款状态
				if (7 == orderStatus || 8 == orderStatus) {
					lending_status = "401";// 401 放款拒绝
				} else if (9 == orderStatus || 13 == orderStatus) {
					lending_status = "200";// 200 放款成功
				} else {
					lending_status = "100";// 100 待放款
				}
				map.put("order_sn", thirdOrderNo);// 订单唯一编号
				map.put("result_type", actType);// 结果类型，要求和请求参数中的act_type一致。
				map.put("lending_status", lending_status);// 放款状态
				map.put("lending_remark", "");// 放款状态备注
				map.put("lending_time", "");// 放款时间（十位标准时时间戳）（待放款为空）
				String lending_amount = "";
				if (null != bwOrder.getBorrowAmount()) {
					lending_amount = 100 * (bwOrder.getBorrowAmount()) + "";
				}
				map.put("lending_amount", lending_amount);// 放款后的可借金额单位（分）
				int term_type = 1;
				if (null != bwOrder.getProductId()) {
					BwProductDictionary bwProductDictionary = bwProductDictionaryService
							.findBwProductDictionaryById(bwOrder.getProductId());
					if (null != bwProductDictionary) {
						if ("1".equals(bwProductDictionary.getpTermType())) {
							term_type = 2;// 产品类型（1：月；2：天）pTermType
						}
					}
				}
				map.put("term_type", term_type);// 1:按天; 2：按月; 3：按年
				map.put("lending_term", "1");// 否 放款后的可借周期
			}
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
			xianJinCardResponse.setMessage("success");
			xianJinCardResponse.setResponse(map);
		} catch (Exception e) {
			logger.error(sessionId + "：执行getOrderStatus method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
		}
		logger.warn(sessionId + "：结束getOrderStatus：" + JSON.toJSONString(xianJinCardResponse));
		return xianJinCardResponse;
	}

	/**
	 * 借款试算接口
	 */
	@Override
	public XianJinCardResponse loanCalculate(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest) {
		logger.warn(sessionId + "：开始loanCalculate method：" + JSON.toJSONString(xianJinCardCommonRequest));
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String loan_amount = xianJinCardCommonRequest.getLoan_amount();// 审批金额 单位（分）
			String loan_term = xianJinCardCommonRequest.getLoan_term();// 审批期限
			String term_type = xianJinCardCommonRequest.getTerm_type();// 1:按天; 2：按月; 3：按年
			// 产品为单期
			if (StringUtils.isBlank(loan_amount) || !StringUtils.isNumeric(loan_amount)) {
				return failReturn(sessionId, map, "审批金额 为空！");
			}
			Double loanAmount = NumberUtils.toDouble(loan_amount);
			if (!"1".equals(term_type)) {
				return failReturn(sessionId, map, "产品不存在！");
			}
			// 第一步：验证（最大最小金额验证）
			Double minLoanAmount = 500D * 100D;
			Double maxLoanAmount = 5000D * 100D;
			if (loanAmount < minLoanAmount) {
				return failReturn(sessionId, map, "本次借款金额" + loanAmount + "分，小于最小借款金额" + minLoanAmount + "分");
			}
			if (loanAmount > maxLoanAmount) {
				return failReturn(sessionId, map, "本次借款金额" + loanAmount + "分，大于最大借款金额" + maxLoanAmount + "分");
			}
			BwProductDictionary bwProductDictionary = null;
			if ("30".equals(loan_term)) {
				bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(3);
			} else {
				bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(5);
			}

			ProductFeeDto productFeeDto = queryProductFee(loanAmount, bwProductDictionary);
			Double service_fee = productFeeDto.getLoanAmount();// 服务费
			String service_fee_desc = "服务费：" + service_fee / 100D + "元";
			int interest_fee = 0;// 利息
			Double receive_amount = productFeeDto.getArrivalAmount();// 到账金额
			Double repay_amount = DoubleUtil.add(loanAmount, productFeeDto.getFundUtilizationFee());// 应还总额
			List<Map<String, Object>> repay_plan = new ArrayList<Map<String, Object>>();

			Map<String, Object> map_ = new HashMap<String, Object>();
			map_.put("period_no", 1);// 期号
			map_.put("repay_amount", repay_amount);// 本期应还金额
			map_.put("repay_amount_desc", "仲裁费：" + (loanAmount * 0.03) / 100D + "元");// 本期应还金额描述
			repay_plan.add(map_);
			map.put("service_fee", service_fee);
			map.put("service_fee_desc", service_fee_desc);
			map.put("interest_fee", interest_fee);
			map.put("receive_amount", receive_amount);
			map.put("repay_amount", repay_amount);
			map.put("repay_plan", repay_plan);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
			xianJinCardResponse.setMessage("success");
			xianJinCardResponse.setResponse(map);
		} catch (Exception e) {
			logger.error(sessionId + "：执行loanCalculate method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
		}
		logger.warn(sessionId + "：结束loanCalculate：" + JSON.toJSONString(xianJinCardResponse));
		return xianJinCardResponse;
	}

	/**
	 * 获取合同
	 */
	@Override
	public XianJinCardResponse getContracts(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest) {
		logger.warn(sessionId + "：开始XianJinCardServiceImpl.getContracts method："
				+ JSON.toJSONString(xianJinCardCommonRequest));
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		try {
			String thirdOrderNo = xianJinCardCommonRequest.getOrder_sn();
			Map<String, String> contractUrls = null;
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			if (StringUtils.isNotBlank(thirdOrderNo)) {
				BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
				if (!CommUtils.isNull(bwOrderRong)) {
					Long orderId = bwOrderRong.getOrderId();
					List<BwAdjunct> bwAdjunctList = bwAdjunctService.findBwAdjunctByOrderIdNew(orderId);
					if (!CommUtils.isNull(bwAdjunctList)) {
						for (BwAdjunct bwAdjunct : bwAdjunctList) {
							if (bwAdjunct.getAdjunctType() == 11) {
								contractUrls = new HashMap<>();
								String adjunctPath = bwAdjunct.getAdjunctPath();
								String conUrl = SystemConstant.PDF_URL + adjunctPath;
								contractUrls.put("name", "借款协议");
								contractUrls.put("link", conUrl);
								list.add(contractUrls);
								continue;
							}
							if (bwAdjunct.getAdjunctType() == 13) {
								contractUrls = new HashMap<>();
								String adjunctPath = bwAdjunct.getAdjunctPath();
								String conUrl = SystemConstant.PDF_URL + adjunctPath;
								contractUrls.put("name", "服务协议");
								contractUrls.put("link", conUrl);
								list.add(contractUrls);
							}
						}
					}
				}
			}
			if (CollectionUtils.isEmpty(list)) {
				contractUrls = new HashMap<>();
				String conUrl = "https://www.sxfq.com/weixinApp3.0/html/Agreement/manageAssociation.html";
				contractUrls.put("name", "服务协议");
				contractUrls.put("link", conUrl);
				list.add(contractUrls);
			}
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_SUCCESS);
			xianJinCardResponse.setMessage("success");
			xianJinCardResponse.setResponse(list);
		} catch (Exception e) {
			logger.error(sessionId + "：执行XianJinCardServiceImpl.getContracts method 异常：", e);
			xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
			xianJinCardResponse.setMessage("请求失败");
		}
		logger.warn(sessionId + "：结束XianJinCardServiceImpl.getContracts：" + JSON.toJSONString(xianJinCardResponse));
		return xianJinCardResponse;
	}

	/****************************************************************************************************
	 ***********************************************************************************************/
	/**
	 * 
	 * 
	 * @param idCard
	 * @param name
	 * @param mobile
	 * @return 0 为正常状态，-1 永久拒绝 ,1 为7天拒绝
	 */
	private String isRejectRecord(String idCard, String name, String mobile) {
		BwBorrower borrower = bwBorrowerService.oldUserFilter2(name, mobile, idCard);
		if (CommUtils.isNull(borrower)) {
			return "0";
		}
		// 查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
		BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
		if (!CommUtils.isNull(record)) {
			// 永久拒绝
			if ("0".equals(String.valueOf(record.getRejectType()))) {
				return "-1";
			} else {
				// 判断临时拒绝是否到期
				Date rejectDate = record.getCreateTime();
				long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime()) / (24 * 60 * 60 * 1000);
				if (day <= 7) {
					return "1";
				}
			}
		}
		return "0";
	}

	/**
	 * 
	 * 直接返回失败信息
	 * 
	 * @param sessionId
	 * @param xianJinCardResponse
	 * @param msg 错误原因
	 * @return
	 */
	private XianJinCardResponse failReturn(long sessionId, Map<String, Object> map, String msg) {
		XianJinCardResponse xianJinCardResponse = new XianJinCardResponse();
		xianJinCardResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
		xianJinCardResponse.setMessage(msg);
		if (null != map && map.size() > 0) {
			xianJinCardResponse.setResponse(map);
		}
		logger.warn(sessionId + "-结束XianJinCardServiceImpl-" + JSON.toJSONString(xianJinCardResponse));
		return xianJinCardResponse;
	}

	public ProductFeeDto queryProductFee(Double borrowAmount, BwProductDictionary product) {
		if (product == null || borrowAmount == null) {
			return null;
		}
		Double ratePreService = product.getRatePreService();// 贷前咨询服务费率
		Double rateAfterLoan = product.getRateAfterLoan();// 贷后信用管理费率
		Double capitalUseCost = product.getpCapitalUseCost();// 信息服务费率
		Double rateFundUtilization = product.getRateFundUtilization();// 资金使用费率
		Double rateOverdue = product.getRateOverdue();// 逾期费率
		Double interestRate = product.getInterestRate();// 分期利息
		Double preServiceFee = DoubleUtil.mul(borrowAmount, ratePreService);
		Double afterLoanFee = DoubleUtil.mul(borrowAmount, rateAfterLoan);
		Double capitalUseFee = DoubleUtil.mul(borrowAmount, capitalUseCost);
		Double fundUtilizationFee = DoubleUtil.mul(borrowAmount, rateFundUtilization);
		ProductFeeDto productFeeDto = new ProductFeeDto();
		productFeeDto.setPreServiceFee(preServiceFee);
		productFeeDto.setAfterLoanFee(afterLoanFee);
		productFeeDto.setCapitalUseFee(capitalUseFee);
		productFeeDto.setFundUtilizationFee(fundUtilizationFee);
		productFeeDto.setOverdueFee(DoubleUtil.mul(borrowAmount, rateOverdue));
		productFeeDto.setInterest(DoubleUtil.round(DoubleUtil.mul(borrowAmount, interestRate), 2));
		// 借款工本费
		Double loanAmount = DoubleUtil.add(DoubleUtil.add(preServiceFee, afterLoanFee), capitalUseFee);
		// 到账金额
		Double arrivelAmount = DoubleUtil.sub(borrowAmount, loanAmount);
		productFeeDto.setLoanAmount(loanAmount);
		productFeeDto.setArrivalAmount(arrivelAmount);
		return productFeeDto;
	}

}
