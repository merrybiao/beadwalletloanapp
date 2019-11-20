package com.waterelephant.rongCarrier.jd.service.impl; // code0002


import java.math.BigDecimal;
import java.util.*;

import com.waterelephant.utils.CommUtils;
import freemarker.ext.beans.HashAdapter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.rong360.jd.RongJDSDK;
import com.waterelephant.rongCarrier.jd.entity.Bankcards;
import com.waterelephant.rongCarrier.jd.entity.OrderList;
import com.waterelephant.rongCarrier.jd.entity.ShippingAddrs;
import com.waterelephant.rongCarrier.jd.entity.UserInfo;
import com.waterelephant.rongCarrier.jd.service.BankcardsService;
import com.waterelephant.rongCarrier.jd.service.OrderListService;
import com.waterelephant.rongCarrier.jd.service.RongJDService;
import com.waterelephant.rongCarrier.jd.service.ShippingAddrsService;
import com.waterelephant.rongCarrier.jd.service.UserInfoService;
import com.waterelephant.rongCarrier.jd.utils.DateUtil;

@Service
public class RongJDServiceImpl implements RongJDService {

	private Logger logger = LoggerFactory.getLogger(RongJDServiceImpl.class);
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private OrderListService orderListService;
	@Autowired
	private ShippingAddrsService shippingAddrsService;
	@Autowired
	private BankcardsService bankcardsService;

	/**
	 * rong360获取认证链接地址
	 */
	@Override
	public String collectuserCommon(Map<String, String> paramMap) {
		logger.info("开始执行app的RongJDServiceImpl的collectuserCommon（）方法，入参：" + JSONObject.toJSONString(paramMap) );
		String json = null;
		Map<String, String> params = new HashMap<>();
		try {
			// 第一步：验证参数是否为空
			if (paramMap == null) {
				params.put("code", "103");
				params.put("msg", "参数为空！");
				return JSONObject.toJSONString(params);
			
			}

			json = RongJDSDK.collectuserCommon(paramMap);
			if (StringUtils.isEmpty(json)) {
				params.put("code", "103");
				params.put("msg", "server返回信息为空");
				return JSONObject.toJSONString(params);
			}
		}catch (Exception e) {
			params.put("code", "900");
			params.put("msg", "执行app的RongJDServiceImpl的collectuserCommon（）方法异常。");
			logger.error("执行app的RongJDServiceImpl的collectuserCommon（）方法,异常：",e.getMessage());
			return JSONObject.toJSONString(params);
		}
		
		logger.info("结束执行app的RongJDServiceImpl的collectuserCommon（）方法，出参：" + json);
		return json;
	}

	/**
	 * rong360获取京东数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getData(String userId, String searchId) throws NumberFormatException {
		Map<String, String> params = new HashMap<>();
		params.put("userId", searchId);
		try {
			String json = RongJDSDK.getData(params);
			Map<String, Object> resultMap = JSONObject.parseObject(json);
			long borrowerId = Long.parseLong(userId);
			if (resultMap != null) {
				Map<String, Object> mapObj = JSONObject.parseObject(resultMap.get("obj").toString(), Map.class);
				if (CommUtils.isNull(mapObj)) { // 判断参数是否为空
					return "error";
				}
				Map<String, Object> mapData = (Map<String, Object>)mapObj.get("data");
				if (CommUtils.isNull(mapData)) { // 判断参数是否为空
					return "error";
				}
				List<Map<String, Object>> dataList = (List<Map<String, Object>>)mapData.get("data_list");
				for (Map<String, Object> mapDataList : dataList) {
					Map<String, String> mapAuthInfo = (Map<String, String>)mapDataList.get("auth_info");
					Map<String, Object> mapBtInfo = (Map<String, Object>)mapDataList.get("bt_info");
					Map<String, Object> mapUserInfo = (Map<String, Object>)mapDataList.get("user_info");
					UserInfo userInfo = new UserInfo();
					userInfo.setBorrowerId(borrowerId);
					if (mapUserInfo != null) {
						userInfo.setLoginName(mapUserInfo.get("login_name").toString());
						userInfo.setUserName(mapUserInfo.get("username").toString());
						userInfo.setSetLoginName(mapUserInfo.get("set_login_name").toString());
						userInfo.setNickname(mapUserInfo.get("nickname").toString());
						userInfo.setSex(mapUserInfo.get("sex").toString());
						userInfo.setBirthday(DateUtil.getStringToDate(mapUserInfo.get("birthday").toString()));
						userInfo.setHobbies(mapUserInfo.get("hobbies").toString());
						userInfo.setEmail(mapUserInfo.get("email").toString());
						userInfo.setRealName(mapUserInfo.get("real_name").toString());
						userInfo.setMarriage(mapUserInfo.get("marriage").toString());
						userInfo.setIncome(mapUserInfo.get("income").toString());
						userInfo.setIdCard(mapUserInfo.get("id_card").toString());
						userInfo.setEducation(mapUserInfo.get("education").toString());
						userInfo.setIndustry(mapUserInfo.get("industry").toString());
						userInfo.setIsQqBound(mapUserInfo.get("is_qq_bound").toString());
						userInfo.setIsWechatBound(mapUserInfo.get("is_wechat_bound").toString());
						userInfo.setAccountGrade(mapUserInfo.get("account_grade").toString());
						userInfo.setAccountType(mapUserInfo.get("account_type").toString());
					}
					if (mapAuthInfo != null) {
						userInfo.setAuthTime(DateUtil.getStringToDate(mapAuthInfo.get("auth_time")));
						userInfo.setAuthChannel(mapAuthInfo.get("auth_channel"));
						userInfo.setBindingPhone(mapAuthInfo.get("binding_phone"));
						userInfo.setFinancialService(mapAuthInfo.get("financial_service"));
					}
					if (mapBtInfo != null) {
						userInfo.setBtCreditPoint(Double.parseDouble(mapBtInfo.get("bt_credit_point").toString()));
						userInfo.setBtOverdraft(Double.parseDouble(mapBtInfo.get("bt_overdraft").toString()));
						userInfo.setBtQuota(Double.parseDouble(mapBtInfo.get("bt_quota").toString()));
					}
					// 数据库查询是否存在改用后的京东数据
					UserInfo queryUserInfo = userInfoService.queryUserInfo(borrowerId);
					if (queryUserInfo == null) {
						userInfo.setCreateTime(new Date());
						userInfo.setUpdateTime(new Date());
						userInfoService.saveUserInfo(userInfo);
					}else {
						userInfo.setId(queryUserInfo.getId());
						userInfo.setUpdateTime(new Date());
						userInfoService.updateUserInfo(userInfo);
					}
					List<Map<String, String>> shippingAddrsList = (List<Map<String, String>>)mapDataList.get("shipping_addrs");
					if (shippingAddrsList.size() > 0) {
						for(Map<String, String> mapShippingAddrs : shippingAddrsList) {
							String addrId = mapShippingAddrs.get("addr_id");
							ShippingAddrs shippingAddrs = new ShippingAddrs();
							shippingAddrs.setBorrowerId(borrowerId);
							shippingAddrs.setLoginName(mapShippingAddrs.get("login_name"));
							shippingAddrs.setAddrId(addrId);
							shippingAddrs.setReceiver(mapShippingAddrs.get("receiver"));
							shippingAddrs.setRegion(mapShippingAddrs.get("region"));
							shippingAddrs.setAddress(mapShippingAddrs.get("address"));
							shippingAddrs.setMobilePhone(mapShippingAddrs.get("mobile_phone"));
							shippingAddrs.setFixedPhone(mapShippingAddrs.get("fixed_phone"));
							shippingAddrs.setEmailAddr(mapShippingAddrs.get("email_addr"));
							// 查询数据库中是否存在该用户信息，不存在则保存，存在则更新
							ShippingAddrs queryShippingAddrs = shippingAddrsService.queryShippingAddrs(borrowerId, addrId);
							if (queryShippingAddrs == null) {
								shippingAddrs.setCreateTime(new Date());
								shippingAddrs.setUpdateTime(new Date());
								shippingAddrsService.saveShippingAddrs(shippingAddrs);
							}else {
								shippingAddrs.setId(queryShippingAddrs.getId());
								shippingAddrs.setUpdateTime(new Date());
								shippingAddrsService.updateShippingAddrs(shippingAddrs);
							}
						}
					}
					List<Map<String, String>> bankcardsList = (List<Map<String, String>>)mapDataList.get("bankcards");
					if (bankcardsList.size() > 0) {
						for (Map<String, String> mapBankcards : bankcardsList) {
							String cardId = mapBankcards.get("card_id");
							Bankcards bankcards = new Bankcards();
							bankcards.setBorrowerId(borrowerId);
							bankcards.setLoginName(mapBankcards.get("login_name"));
							bankcards.setCardId(cardId);
							bankcards.setBankName(mapBankcards.get("bank_name"));
							bankcards.setTailNumber(mapBankcards.get("tail_number"));
							bankcards.setCardType(mapBankcards.get("card_type"));
							bankcards.setOwnerName(mapBankcards.get("owner_name"));
							bankcards.setPhone(mapBankcards.get("phone"));
							// 查询数据库中是否存在该用户信息，不存在则保存，存在则更新
							Bankcards queryBankcards = bankcardsService.queryBankcards(borrowerId, cardId);
							if (queryBankcards == null) {
								bankcards.setCreateTime(new Date());
								bankcards.setUpdateTime(new Date());
								bankcardsService.saveBankcards(bankcards);
							} else {
								bankcards.setId(queryBankcards.getId());
								bankcards.setUpdateTime(new Date());
								bankcardsService.updateBankcards(bankcards);
							}
						}
					}
					List<Map<String, Object>> orderListList = (List<Map<String, Object>>)mapDataList.get("order_list");
					if (orderListList.size() > 0) {
						List<OrderList> list = new ArrayList<>();
						for (Map<String, Object> mapOrderList : orderListList) {
							String orderId = mapOrderList.get("order_id").toString();
							OrderList orderList = new OrderList();
							orderList.setBorrowerId(borrowerId);
							orderList.setOrderId(orderId);
							orderList.setReceiver(mapOrderList.get("receiver").toString());
							orderList.setMoney(new BigDecimal(mapOrderList.get("money").toString()));
							orderList.setBuyWay(mapOrderList.get("buy_way").toString());
							orderList.setBuyTime(Long.parseLong(mapOrderList.get("buy_time").toString()));
							orderList.setOrderStatus(mapOrderList.get("order_status").toString());
							orderList.setLoginName(mapOrderList.get("login_name").toString());
							orderList.setReceiverAddr(mapOrderList.get("receiver_addr").toString());
							orderList.setReceiverFixPhone(mapOrderList.get("receiver_fix_phone").toString());
							orderList.setReceiverTelephone(mapOrderList.get("receiver_telephone").toString());
							String productName = mapOrderList.get("product_names").toString();
							if (productName.length() >= 181) {
								productName = productName.substring(0,180);
							}
							orderList.setProductNames(productName);
							orderList.setInvoiceType(mapOrderList.get("invoice_type").toString());
							orderList.setInvoiceHeader(mapOrderList.get("invoice_header").toString());
							orderList.setInvoiceContent(mapOrderList.get("invoice_content").toString());
							list.add(orderList);
						}
						// 查询数据库中是否存在该用户信息，不存在则保存，存在则更新
						int count = orderListService.queryOrderList(borrowerId);
						if (count > 0) {
							orderListService.deleteOrderList(borrowerId);
						}
						orderListService.saveJdOrderList(list);
					}
				}
				return "success";
			}
		}catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			logger.error("数据转换异常:",nfe.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("获取数据异常：",e.getMessage());
		}
		return "false";
	}

}
