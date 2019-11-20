package com.waterelephant.drainage.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.DigestException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.drainage.baidu.entity.ApplyInfoEntity;
import com.waterelephant.drainage.baidu.entity.ApplyPlan;
import com.waterelephant.drainage.baidu.entity.AuthCodeEntity;
import com.waterelephant.drainage.baidu.entity.AuthStatuEntity;
import com.waterelephant.drainage.baidu.entity.DataEntity;
import com.waterelephant.drainage.baidu.entity.OrderInfo;
import com.waterelephant.drainage.baidu.entity.OrderInfoData;
import com.waterelephant.drainage.baidu.entity.RepaymentPlanData;
import com.waterelephant.drainage.baidu.entity.SignEntity;
import com.waterelephant.drainage.baidu.entity.UserEntity;
import com.waterelephant.drainage.baidu.response.HttpResult;
import com.waterelephant.drainage.baidu.util.BaiduUtil;
import com.waterelephant.drainage.baidu.util.CheckEntityUtil;
import com.waterelephant.drainage.baidu.util.Constant;
import com.waterelephant.drainage.baidu.util.Md5Util;
import com.waterelephant.drainage.baidu.util.Sha1Util;
import com.waterelephant.drainage.baidu.util.StatusUtil;
import com.waterelephant.drainage.baidu.util.ThirdCommonResponse;
import com.waterelephant.drainage.service.BaiduService;
import com.waterelephant.drainage.service.DrainageService;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;

/**
 * 
 * 
 * Module:
 * 
 * BaiduController.java
 * 
 * @author 甘立思
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/drainage/baidu")
public class BaiduController {

	private Logger logger = LoggerFactory.getLogger(BaiduController.class);

	@Autowired
	private DrainageService drainageService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlantService;
	@Autowired
	private BaiduService baiduService;

	@Autowired
	private BwOrderRongService bwOrderRongService;

	@Autowired
	private IBwOrderService bwOrderService;

	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;

	@Autowired
	private IBwBankCardService bankCardService;

	@Autowired
	private IBwOrderChannelService bwOrderChannelService;

	@Autowired
	private ThirdCommonService thirdCommonServiceImpl;

	@Autowired
	private IBwBorrowerService borrowerService;

	private static String BASE_INFO_BACK_TEMP_REDIS = "baidu:orderPush:back:temp";
	private static String TPCODE = Constant.TPCODE;
	private static String MERCHANT_CODE = Constant.MERCHANTCODE;
	private static String CHANNEL = Constant.CHANNEL;
	/**
	 * 百度金融商城和商户系统的账号系统打通
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user.do")
	public HttpResult getUser(@RequestBody UserEntity user) {
		logger.info("~~~~~~~~~BaiduController.getUser开始执行百度金融的用户打通接口~~~~~~~~~");
		HttpResult result = new HttpResult();
		// UserEntity user = new UserEntity();
		try {
			// // request.setCharacterEncoding("UTF-8");
			// String event = request.getParameter("event");
			// String timestamp = request.getParameter("timestamp");
			// String idCard = request.getParameter("id_card");
			// String mobile = request.getParameter("mobile");
			// // String userName = request.getParameter("user_name");
			// String userName = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "utf-8");
			// String sign = request.getParameter("sign");
			// user.setEvent(event);
			// user.setIdCard(idCard);
			// user.setMobile(mobile);
			// user.setSign(sign);
			// user.setTimeStamp(timestamp);
			// user.setUserName(userName);
			// 第一步：判断数据是否为空
			ThirdCommonResponse response = CheckEntityUtil.validataEntity(user);
			if (!CommUtils.isNull(response)) {
				result.setCode(response.getCode());
				result.setMsg(response.getMsg());
				logger.info("~~~~~~~~~BaiduController.getUser执行百度金融的用户打通接口异常~~~~~~~~~" + JSON.toJSONString(result));
				return result;
			}
			logger.info("参数是：" + JSONObject.toJSONString(user));
			if (!"user_bind".equals(user.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info("~~~~~~~~~BaiduController.getUser执行百度金融的用户打通接口异常~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			// 第二步：校验签名
			String signNew = user.getSign();
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject.parse(JSONObject.toJSONString(user));
			// Map<String, String> paramMap = new HashMap<String, String>();
			// paramMap.put("event ", user.getEvent());
			// paramMap.put("timestamp", user.getTimeStamp());
			// paramMap.put("id_card ", user.getIdCard());
			// paramMap.put("mobile", user.getMobile());
			// paramMap.put("user_name", user.getUserName());
			String signKey = Md5Util.getSign(paramMap);
			System.out.println("签名是" + signKey);
			if (!signKey.equals(signNew)) {
				result.setCode(1002);
				result.setMsg("签名错误！");
				logger.info("~~~~~~~~~BaiduController.getUser结束执行百度金融的用户打通接口~~~~~~~~~，userName=" + user.getUser_name()
						+ JSON.toJSONString(result));
				return result;
			}
			String idCardNew = user.getId_card();
			String username = user.getUser_name();
			// 第六步：判断用户是否是老用户
			Map<String, Object> data = new HashMap<String, Object>();
			BwBorrower borrower = baiduService.getUserInfoByIdCardAndName(user.getId_card(), user.getUser_name());
			// 生成authCode
			String authCode = System.currentTimeMillis() + String.valueOf(new Random().nextInt(6));
			if (borrower != null) {

				// 第三步：判断用户是否是黑名单用户
				boolean bo = drainageService.isBlackUser(idCardNew, username);
				if (bo) {
					result.setCode(1003);
					result.setMsg("用户为黑名单用户！");
					logger.info("~~~~~~~~~BaiduController.getUser结束执行百度金融的用户打通接口，用户为黑名单用户~~~~~~~~~，userName="
							+ user.getUser_name() + JSON.toJSONString(result));
					return result;
				}
				// 第四步：是否进行中的订单
				boolean isProcessingOrder = drainageService.isPocessingOrder(idCardNew);
				if (isProcessingOrder) {
					result.setCode(1004);
					result.setMsg("命中在贷规则");
					logger.info("~~~~~~~~~BaiduController.getUser结束执行百度金融的用户打通接口，用户有进行中的订单~~~~~~~~~，userName="
							+ user.getUser_name() + JSON.toJSONString(result));
					return result;
				}
				// 第五步：是否有被拒记录
				boolean isRejectRecord = drainageService.isRejectRecord(idCardNew, username);
				if (isRejectRecord) {
					result.setCode(1005);
					result.setMsg("命中被拒规则");
					logger.info("~~~~~~~~~BaiduController.getUser结束执行百度金融的用户打通接口，用户有被拒的记录~~~~~~~~~，userName="
							+ user.getUser_name() + JSON.toJSONString(result));
					return result;
				}
				data.put("auth_code", authCode);
				data.put("user_status", 1);
			} else {
				data.put("auth_code", authCode);
				data.put("user_status", 0);
			}
			// 保存用户信息
			int channel = Integer.valueOf(CHANNEL);
			drainageService.addOrUpdateBwer(user.getUser_name(), user.getId_card(), user.getMobile(), channel);
			// 存在redis中两小时
			RedisUtils.setex("baidu:" + authCode, borrower.getId().toString(), 60 * 60 * 2);
			// 第三步：校验authCode
			if (!RedisUtils.exists("baidu:" + authCode)) {
				result.setCode(1099);
				result.setMsg("authCode保存失败");
				logger.info("~~~~~~~~~BaiduController.signPreviewCard结束执行百度金融的用户打通接口：authCode保存失败~~~~~~~~~");
				return result;
			}
			result.setCode(0);
			result.setMsg("成功");
			result.setData(data);
			logger.info(
					"~~~~~~~~~BaiduController.getUser结束执行百度金融的用户打通接口，打通成功~~~~~~~~~，userName=" + user.getUser_name());
		} catch (Exception e) {
			result.setCode(1099);
			result.setMsg("服务器内部错误，请联系商户及时处理！");
			logger.error("BaiduController.getUser百度金融商城和商户系统的账号系统打通异常：", e);
		}
		return result;
	}

	/**
	 * 百度金融获取授权链接
	 * 
	 * @param authCodeEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getAuthUrl.do")
	public HttpResult getAuthUrl(@RequestBody AuthCodeEntity authCodeEntity) {
		logger.info("BaiduController.getAuthUrl开始执行百度金融的获取授权链接接口");
		HttpResult result = new HttpResult();
		try {
			// 第一步：校验参数
			ThirdCommonResponse response = CheckEntityUtil.validataEntity(authCodeEntity);
			if (!CommUtils.isNull(response)) {
				result.setCode(response.getCode());
				result.setMsg(response.getMsg());
				logger.info(
						"~~~~~~~~~BaiduController.signPreviewCard结束执行百度金融的签约预览接口，" + response.getMsg() + "~~~~~~~~~");
				return result;
			}
			if (!"auth_redirect".equals(authCodeEntity.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info("~~~~~~~~~结束执行BaiduController.signPreviewCard结束执行百度金融的获取授权链接~~~~~~~~~,result="
						+ result.getMsg());
				return result;
			}
			// 第二步：校验签名
			String sign = authCodeEntity.getSign();
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject
					.parse(JSONObject.toJSONString(authCodeEntity));
			String signKey = Md5Util.getSign(paramMap);

			if (!signKey.equals(sign)) {
				result.setCode(102);
				result.setMsg("签名校验失败！");
				logger.info("BaiduController.getAuthUrl结束执行百度金融的获取授权链接接口,签名错误");
				return result;
			}

			// 第三步：校验authCode是否过期
			if (!RedisUtils.exists("baidu:" + authCodeEntity.getAuth_code())) {
				result.setCode(103);
				result.setMsg("授权码失效或不存在！");
				logger.info("BaiduController.getAuthUrl结束执行百度金融的获取授权链接接口,authCode已失效或不存在");
				return result;
			}
			// 第四步：判断工单是否存在，不存在则生成新的工单
			String borrowerId = RedisUtils.get("baidu:" + authCodeEntity.getAuth_code());
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(authCodeEntity.getOrder_id());
			if (bwOrderRong == null) { // 三方工单不存在，生成新的工单
				Long orderId = baiduService.saveOrder(Long.parseLong(borrowerId));
				bwOrderRong = baiduService.saveBwROrder(orderId, authCodeEntity.getOrder_id());
				if (bwOrderRong != null) {
					logger.info("BaiduController.getAuthUrl百度金融保存工单成功,orderId=" + orderId);
				}
			}
            // 第五步：返回绑卡页面给百度金融 
			Map<String, Object> data = new HashMap<String, Object>();
			String orderNo = getOrderNoByThirdOderNo(authCodeEntity.getOrder_id());
			String url = Constant.CARDURL + "?orderNO=" + orderNo + "&channelCode=" + Constant.CHANNELCODE;
			data.put("redirect_url", url);
			result.setCode(0);
			result.setMsg("成功");
			result.setData(data);
			logger.info("~~~~~~~~~BaiduController.signPreviewCard结束执行百度金融的获取授权链接接口，" + result.getMsg()
					+ "~~~~~~~~~borrowerId=" + borrowerId);
			String cardUrl = Constant.CARDTOURL + "?order_id=" + authCodeEntity.getOrder_id();
			RedisUtils.hset("third:bindCard:successReturnUrl:" + Constant.CHANNEL, "orderNO_" + orderNo, cardUrl);
			RedisUtils.hset("third:bindCard:failReturnUrl:" + Constant.CHANNEL, "orderNO_" + orderNo, cardUrl);
		} catch (Exception e) {
			result.setCode(900);
			result.setMsg("服务器内部错误，请联系商户及时处理！");
			logger.error("BaiduController.getAuthUrl百度金融的获取授权链接接口异常：", e);
		}
		return result;
	}

	/**
	 * 签约预览接口
	 * 
	 * @param applyInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/signpreview.do")
	public HttpResult signPreviewCard(@RequestBody ApplyInfoEntity applyInfo) {
		logger.info("~~~~~~~~~BaduController.signPreviewCard开始执行百度金融的签约预览接口~~~~~~~~~");
		HttpResult result = new HttpResult();
		try {
			// 第一步：校验参数
			ThirdCommonResponse response = CheckEntityUtil.validataEntity(applyInfo);
			if (!CommUtils.isNull(response)) {
				result.setCode(response.getCode());
				result.setMsg(response.getMsg());
				logger.info(
						"~~~~~~~~~BaiduController.signPreviewCard结束执行百度金融的签约预览接口，" + response.getMsg() + "~~~~~~~~~");
				return result;
			}
			if (!"preview".equals(applyInfo.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info(
						"~~~~~~~~~BaiduController.signPreviewCard结束执行百度金融的签约预览接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			// 第二步：校验签名
			String sign = applyInfo.getSign();
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject.parse(JSONObject.toJSONString(applyInfo));
			String signKey = Md5Util.getSign(paramMap);
			if (!signKey.equals(sign)) {
				result.setCode(1002);
				result.setMsg("签名错误");
				logger.info("~~~~~~~~~BaiduController.signPreviewCard结束执行百度金融的签约预览接口：签名错误~~~~~~~~~");
				return result;
			}

			// 第三步：校验authCode
			if (!RedisUtils.exists("baidu:" + applyInfo.getAuth_code())) {
				result.setCode(1003);
				result.setMsg("authCode已失效");
				logger.info("~~~~~~~~~BaiduController.signPreviewCard结束执行百度金融的签约预览接口：authCode已失效~~~~~~~~~");
				return result;
			}
			// 检验三方订单是否存在
			BwOrder bwOrder = getOrderByTOrderNo(applyInfo.getOrder_id());
			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				result.setCode(104);
				result.setMsg("商户订单不存在！");
				logger.info(
						"~~~~~~~~~BaiduController.signPreviewCard结束执行百度金融的签约预览接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}

			// 第四步：封装返回信息到map中，并返回数据给百度
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("balance", Integer.parseInt(Constant.BALANCE));
			paramsMap.put("service_fee", Integer.parseInt(Constant.BALANCE) * Double.valueOf(Constant.RATE) / 100);
			paramsMap.put("real_balance", Integer.parseInt(Constant.REALBALANCE));
			paramsMap.put("month_balance", Integer.parseInt(Constant.MONTHBALANCE));
			String repayDate = baiduService.getRepayDate();
			paramsMap.put("repay_data", repayDate);
			result.setCode(0);
			result.setMsg("成功");
			result.setData(paramsMap);
			logger.info("~~~~~~~~~BaduController.signPreviewCard结束执行百度金融的签约预览接口~~~~~~~~~,result=" + result.getMsg());
		} catch (Exception e) {
			result.setCode(900);
			result.setMsg("服务器内部错误，请联系商户及时处理");
			logger.error("BaiduController.signPreviewCard百度金融商城的签约预览接口异常：", e);
		}
		return result;
	}

	/**
	 * 签约接口(订单推送接口)
	 * 
	 * @param signEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/singRongCard.do")
	public HttpResult singRongCard(@RequestBody SignEntity signEntity) {
		logger.info("~~~~~~~~~singRongCard开始执行百度金融的签约接口~~~~~~~~~");
		HttpResult result = new HttpResult();
		try {
			String check = BaiduUtil.checkSignEntity(signEntity);
			if (StringUtils.isNotBlank(check)) {
				result.setCode(101);
				result.setMsg(check);
				logger.info("~~~~~~~~~结束执行百度金融的签约接口~~~~~~~~~," + check + "~~~~~~~~~");
				return result;
			}
			if (!"sign".equals(signEntity.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info("~~~~~~~~~结束执行百度金融的签约接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			// 验签
			@SuppressWarnings("unchecked")
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject.parse(JSONObject.toJSONString(signEntity));
			String signKey = Md5Util.getSign(paramMap);
			if (!signKey.equals(signEntity.getSign())) {
				result.setCode(102);
				result.setMsg("签名校验失败！");
				logger.info("~~~~~~~~~结束执行百度金融的签约接口~~~~~~~~~签名校验失败！");
				return result;
			}
			// 第三步：校验authCode
			if (!RedisUtils.exists("baidu:" + signEntity.getAuth_code())) {
				result.setCode(1003);
				result.setMsg("authCode已失效");
				logger.info("~~~~~~~~~结束执行百度金融的签约接口：authCode已失效~~~~~~~~~");
				return result;
			}
			// 检验三方订单是否存在
			BwOrder bwOrder = getOrderByTOrderNo(signEntity.getOrder_id());
			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				result.setCode(104);
				result.setMsg("商户订单不存在！");
				logger.info("~~~~~~~~~结束执行百度金融的签约接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			BwOrderChannel orderChannel = new BwOrderChannel();
			orderChannel.setId(Integer.valueOf(Constant.CHANNEL));
			orderChannel.setChannelName(new String("百度推广".getBytes(), "utf-8"));
			BwOrderChannel channel = bwOrderChannelService.findBwOrderChannel(orderChannel);
			// 判断渠道码
			if (CommUtils.isNull(channel)) {
				result.setCode(105);
				result.setMsg("商户渠道为空！");
				logger.info("~~~~~~~~~结束执行百度金融的签约接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}

			// result = baiduService.saveOrderPush(signEntity, result);
			// RedisUtils.rpush(BASE_INFO_BACK_TEMP_REDIS, JSONObject.toJSONString(signEntity));
			logger.info("准备放入redis-list[" + BASE_INFO_BACK_TEMP_REDIS + "]");
			Long resultNum = RedisUtils.rpush(BASE_INFO_BACK_TEMP_REDIS, JSONObject.toJSONString(signEntity));
			if (!CommUtils.isNull(resultNum)) {
				logger.info("百度金融订单基本信息存入redis-list[" + BASE_INFO_BACK_TEMP_REDIS + "]成功");
				result.setCode(0);
				HashMap<String, String> map = new HashMap<>();
				String orderId = bwOrder.getOrderNo();
				map.put("apply_id", orderId);
				String data = JSONObject.toJSONString(map);
				result.setData(data);
				result.setMsg("成功");
			} else {
				logger.info("百度金融订单基本信息存入redis-list[" + BASE_INFO_BACK_TEMP_REDIS + "]失败");
				result.setCode(103);
				result.setMsg("系统异常，请稍后再试");
				logger.info("~~~~~~~~~执行百度金融的签约接口~~~~~~~~~ end,result=" + result.getMsg());
				return result;
			}

		} catch (Exception e) {
			logger.error("~~~~~~~~~执行百度金融的签约接口~~~~~~~~~  异常", e);
			result.setCode(900);
			result.setMsg("系统异常，请稍后再试");
		}
		logger.info("~~~~~~~~~执行百度金融的签约接口~~~~~~~~~ end,result=" + result.getMsg());
		return result;
	}

	/**
	 * 百度金融授权状态查询接口
	 * 
	 * @param authStatuEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/app/baidu/getAuthorization.do")
	public HttpResult getBorrowBalance(@RequestBody AuthStatuEntity authStatuEntity) {
		logger.info("~~~~~~~~~~~~~BaiduController.getBorrowBalance开始执行百度金融的授权状态查询接口~~~~~~~~~~~~~~~~");
		HttpResult result = new HttpResult();
		try {
			// 验空
			ThirdCommonResponse response = CheckEntityUtil.validataEntity(authStatuEntity);
			if (!CommUtils.isNull(response)) {
				result.setCode(response.getCode());
				result.setMsg(response.getMsg());
				logger.info("~~~~~~~~~getBorrowBalance结束执行百度金融的授权状态查询接口~~~~~~~~~,result=" + result.getMsg());

				return result;
			}
			// 检验授权码
			if (!"auth_status".equals(authStatuEntity.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info("~~~~~~~~~getBorrowBalance结束执行百度金融的授权状态查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			// 验签
			String sign = authStatuEntity.getSign();
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject
					.parse(JSONObject.toJSONString(authStatuEntity));
			String signKey = Md5Util.getSign(paramMap);
			if (!signKey.equals(sign)) {
				result.setCode(102);
				result.setMsg("签名校验失败！");
				logger.info("~~~~~~~~~getBorrowBalance结束执行百度金融的授权状态查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			// 检验授权码
			if (!RedisUtils.exists("baidu:" + authStatuEntity.getAuth_code())) {
				result.setCode(103);
				result.setMsg("授权码失效或不存在！");
				logger.info("~~~~~~~~~getBorrowBalance结束执行百度金融的授权状态查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			// 检验三方订单是否存在
			BwOrder bwOrder = getOrderByTOrderNo(authStatuEntity.getOrder_id());
			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				result.setCode(104);
				result.setMsg("商户订单不存在！");
				logger.info("~~~~~~~~~getBorrowBalance结束执行百度金融的授权状态查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			String authStatus = getStatusData(authStatuEntity.getOrder_id(), bwOrder);
			Map<String, String> map = new HashMap<>();
			map.put("auth_statu", authStatus);
			map.put("apply_id", bwOrder.getOrderNo());
			String data = JSONObject.toJSONString(map);
			logger.info("~~~~~~~~~百度 结束获取用户状态~~~~~~~~~,orderId=" + bwOrder.getId() + ",data = " + data);
			result.setCode(0);
			result.setData(data);
			result.setMsg("成功");
		} catch (Exception e) {
			result.setCode(900);
			result.setMsg("系统异常，请稍后再试");
			logger.error("~~~~~~~~~getBorrowBalance结束执行百度金融的授权状态查询接口异常~~~~~~~~~" + e);
		}

		return result;
	}

	/**
	 * 百度金融订单 信息查询接口
	 * 
	 * @param OrderInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/app/baidu/getOrderInfo.do")
	public HttpResult getOrderInfo(@RequestBody OrderInfo OrderInfo) {
		logger.info("~~~~~~~~~~~~~getOrderInfo开始执行百度金融订单 信息查询接口~~~~~~~~~~~~~~~~");
		HttpResult result = new HttpResult();
		try {
			ThirdCommonResponse response = CheckEntityUtil.validataEntity(OrderInfo);
			if (!CommUtils.isNull(response)) {
				result.setCode(response.getCode());
				result.setMsg(response.getMsg());
				logger.info("~~~~~~~~~getOrderInfo结束执行百度金融订单 信息查询接口~~~~~~~~~,result=" + result.getMsg());

				return result;
			}
			if (!"order_query".equals(OrderInfo.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info("~~~~~~~~~getOrderInfo结束执行百度金融订单 信息查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			String sign = OrderInfo.getSign();
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject.parse(JSONObject.toJSONString(OrderInfo));
			String signKey = Md5Util.getSign(paramMap);
			if (!signKey.equals(sign)) {
				result.setCode(102);
				result.setMsg("签名校验失败！");
				logger.info("~~~~~~~~~getOrderInfo结束执行百度金融订单 信息查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			if (!RedisUtils.exists("baidu:" + OrderInfo.getAuth_code())) {
				result.setCode(103);
				result.setMsg("授权码失效或不存在！");
				logger.info("~~~~~~~~~getOrderInfo结束执行百度金融订单 信息查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			BwOrder bwOrder = getOrderByTOrderNo(OrderInfo.getOrder_id());

			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				result.setCode(104);
				result.setMsg("商户订单不存在！");
				logger.info("~~~~~~~~~结束执行百度金融订单 信息查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			Long orderId = getOrderIdByOrderNo(OrderInfo.getApply_id());
			BwOrder order = bwOrderService.findBwOrderById(orderId.toString());
			if (CommUtils.isNull(order) || CommUtils.isNull(order.getBorrowAmount())) {
				result.setCode(105);
				result.setMsg("批复金额不存在！");
				return result;
			}
			Long borrowerId = getBorrwerIdByOrderId(OrderInfo, bwOrder);
			if (CommUtils.isNull(borrowerId)) {
				result.setCode(106);
				result.setMsg("查无此借款人！");
				logger.info("~~~~~~~~~结束执行百度金融订单 信息查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			BwBankCard bankCard = getBankCardByBorrowerId(bwOrder, borrowerId);
			if (CommUtils.isNull(bankCard)) {
				result.setCode(107);
				result.setMsg("没有签约记录！");
				logger.info("~~~~~~~~~结束执行百度金融订单 信息查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			String bankCode = bankCard.getBankCode();
			String cardNo = bankCard.getCardNo();
			BwRepaymentPlan plan = getBorrwerPlan(OrderInfo);
			if (CommUtils.isNull(plan)) {
				result.setCode(108);
				result.setMsg("没有还款计划！");
				logger.info("~~~~~~~~~结束执行百度金融订单 信息查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			String repayTime = String.valueOf(plan.getUpdateTime().getTime() / 1000);
			String data = getOrderInfoData(OrderInfo.getOrder_id(), bwOrder, order, bankCode, cardNo, repayTime);
			result.setCode(0);
			result.setMsg("成功");
			result.setData(data);
			logger.info("~~~~~~~~~~~~~getOrderInfo结束执行百度金融订单 信息查询接口~~~~~~~~~~~~~~~~result=" + result.getMsg());
		} catch (Exception e) {
			result.setCode(900);
			result.setMsg("系统异常，请稍后再试");
			logger.error("~~~~~~~~~getOrderInfo结束执行百度金融订单 信息查询接口异常~~~~~~~~~" + e);
		}
		return result;
	}

	/**
	 * 百度金融 还款 接口
	 * 
	 * @param OrderInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/app/baidu/getRepayment.do")
	public HttpResult getRepayment(@RequestBody OrderInfo OrderInfo) {
		logger.info("~~~~~~~~~~~~~getRepayment开始执行百度金融 还款 接口~~~~~~~~~~~~~~~~");
		HttpResult result = new HttpResult();
		try {
			ThirdCommonResponse response = CheckEntityUtil.validataEntity(OrderInfo);
			if (!CommUtils.isNull(response)) {
				result.setCode(response.getCode());
				result.setMsg(response.getMsg());
				logger.info("~~~~~~~~~getRepayment结束执行百度金融 还款 接口~~~~~~~~~,result=" + result.getMsg());

				return result;
			}
			if (!"repay".equals(OrderInfo.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info("~~~~~~~~~getRepayment结束执行百度金融 还款 接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			String sign = OrderInfo.getSign();
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject.parse(JSONObject.toJSONString(OrderInfo));
			String signKey = Md5Util.getSign(paramMap);
			if (!signKey.equals(sign)) {
				result.setCode(102);
				result.setMsg("签名校验失败！");
				logger.info("~~~~~~~~~getRepayment结束执行百度金融 还款 接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			if (!RedisUtils.exists("baidu:" + OrderInfo.getAuth_code())) {
				result.setCode(103);
				result.setMsg("授权码失效或不存在！");
				logger.info("~~~~~~~~~getRepayment结束执行百度金融 还款 接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			BwOrder bwOrder = getOrderByTOrderNo(OrderInfo.getOrder_id());

			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				result.setCode(104);
				result.setMsg("商户订单不存在！");
				logger.info("~~~~~~~~~结束执行百度金融 还款 接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			// 第五步：返回还款页面给百度金融
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("redirect_url", "");
			result.setCode(0);
			result.setMsg("成功");
			result.setData(data);
			logger.info("~~~~~~~~~~~~~getRepayment结束执行百度金融 还款 接口~~~~~~~~~~~~~~~~result=" + result.getMsg());
		} catch (Exception e) {
			result.setCode(900);
			result.setMsg("系统异常，请稍后再试");
			logger.error("~~~~~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口异常~~~~~~~~~~~~~~~~" + e);
		}

		return result;
	}

	/**
	 * 获取用户协议 链接接口
	 * 
	 * @param OrderInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/app/baidu/getUserAgreement.do")
	public HttpResult getUserAgreement(@RequestBody OrderInfo OrderInfo) {
		logger.info("~~~~~~~~~~~~~getUserAgreement开始执行百度金融获取用户协议 链接接口~~~~~~~~~~~~~~~~");
		HttpResult result = new HttpResult();
		try {
			ThirdCommonResponse response = CheckEntityUtil.validataEntity(OrderInfo);
			if (!CommUtils.isNull(response)) {
				result.setCode(response.getCode());
				result.setMsg(response.getMsg());
				logger.info("~~~~~~~~~getUserAgreement结束执行百度金融获取用户协议 链接接口~~~~~~~~~,result=" + result.getMsg());

				return result;
			}
			if (!"agreement_query".equals(OrderInfo.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info("~~~~~~~~~getUserAgreement结束执行百度金融获取用户协议 链接接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			String sign = OrderInfo.getSign();
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject.parse(JSONObject.toJSONString(OrderInfo));
			String signKey = Md5Util.getSign(paramMap);
			if (!signKey.equals(sign)) {
				result.setCode(102);
				result.setMsg("签名校验失败！");
				logger.info("~~~~~~~~~getUserAgreement结束执行百度金融获取用户协议 链接接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			if (!RedisUtils.exists("baidu:" + OrderInfo.getAuth_code())) {
				result.setCode(103);
				result.setMsg("授权码失效或不存在！");
				logger.info("~~~~~~~~~getUserAgreement结束执行百度金融获取用户协议 链接接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			BwOrder bwOrder = getOrderByTOrderNo(OrderInfo.getOrder_id());

			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				result.setCode(104);
				result.setMsg("商户订单不存在！");
				logger.info("~~~~~~~~~结束执行百度金融获取用户协议 链接接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			// 第五步：返回合同协议页面给百度金融
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("redirect_url", "");
			result.setCode(0);
			result.setMsg("成功");
			result.setData(data);
			logger.info("~~~~~~~~~~~~~getUserAgreement结束执行百度金融获取用户协议 链接接口~~~~~~~~~~~~~~~~result=" + result.getMsg());
		} catch (Exception e) {
			result.setCode(900);
			result.setMsg("系统异常，请稍后再试");
			logger.error("~~~~~~~~~~~~~getUserAgreement开始执行百度金融获取用户协议 链接接口异常~~~~~~~~~~~~~~~~" + e);
		}

		return result;
	}

	/**
	 * 还款 计划查询接口
	 * 
	 * @param applyPlan
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/app/baidu/getRepaymentPlan.do")
	public HttpResult getRepaymentPlan(@RequestBody ApplyPlan applyPlan) {
		logger.info("~~~~~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口~~~~~~~~~~~~~~~~");
		HttpResult result = new HttpResult();
		try {
			ThirdCommonResponse response = CheckEntityUtil.validataEntity(applyPlan);
			if (!CommUtils.isNull(response)) {
				result.setCode(response.getCode());
				result.setMsg(response.getMsg());
				logger.info("~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			if (!"plan_query".equals(applyPlan.getEvent())) {
				result.setCode(101);
				result.setMsg("event校验失败！");
				logger.info("~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			String sign = applyPlan.getSign();
			Map<String, Object> paramMap = (Map<String, Object>) JSONObject.parse(JSONObject.toJSONString(applyPlan));
			// 校验签名
			String signKey = Md5Util.getSign(paramMap);
			if (!signKey.equals(sign)) {
				result.setCode(102);
				result.setMsg("签名校验失败！");
				logger.info("~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			if (!RedisUtils.exists("baidu:" + applyPlan.getAuth_code())) {
				result.setCode(103);
				result.setMsg("授权码失效或不存在！");
				logger.info("~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			BwOrder bwOrder = getOrderByTOrderNo(applyPlan.getOrder_id());

			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				result.setCode(104);
				result.setMsg("商户订单不存在！");
				logger.info("~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口~~~~~~~~~,result=" + result.getMsg());
				return result;
			}
			if (bwOrder.getStatusId() == 9) {
				logger.info("~~~~~~~~~百度金融开始查询还款计划~~~~~~~~~");
				BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
				Long oderId = getOrderIdByOrderNo(applyPlan.getApply_id());
				bwRepaymentPlan.setOrderId(oderId);
				List<BwRepaymentPlan> brpList = bwRepaymentPlantService.findBwRepaymentPlanByRepay(bwRepaymentPlan);
				logger.info("~~~~~~~~~百度金融结束查询还款计划~~~~~~~~~");
				if (CommUtils.isNull(brpList) || brpList.size() == 0) {
					result.setCode(105);
					result.setMsg("还款计划不存在");
					logger.info("~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口~~~~~~~~~,result=" + result.getMsg());
					return result;
				}
				result.setCode(0);
				result.setMsg("成功");
				result.setData(setRepaymentPlanData(bwOrder, brpList));
				logger.info("~~~~~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口结束~~~~~~~~~~~~~~~~result=" + result.getMsg());
			} else {
				result.setCode(106);
				result.setMsg("不在还款状态中");
				logger.info("~~~~~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口结束~~~~~~~~~~~~~~~~result=" + result.getMsg());
			}
		} catch (Exception e) {
			result.setCode(900);
			result.setMsg("系统异常，请稍后再试");
			logger.error("~~~~~~~~~~~~~getRepaymentPlan百度金融还款 计划查询接口异常~~~~~~~~~~~~~~~~" + e);
		}

		return result;
	}

	/**
	 * 获取订单查询接口的返回data
	 * 
	 * @param bwOrder
	 * @param order
	 * @param bankCode
	 * @param cardNo
	 * @param repayTime
	 */
	private String getOrderInfoData(String thirdOrderId, BwOrder bwOrder, BwOrder order, String bankCode, String cardNo,
			String repayTime) {
		int amount = order.getBorrowAmount().intValue();
		int balance = amount * 100;
		int term = 30;
		int serviceFee = (int) (balance * 0.18);
		int realBalance = balance - serviceFee;
		String monthBalanceStr = String.valueOf(balance);
		BwOverdueRecord overdueRecord = getOverdueRecordByOrderId(bwOrder);
		if (!CommUtils.isNull(overdueRecord)) {
			int overdue = overdueRecord.getOverdueAccrualMoney().intValue();
			overdue = overdue * 100;
			logger.info("有逾期记录,累加逾期金额");
			monthBalanceStr = new BigDecimal(monthBalanceStr).add(new BigDecimal(overdue))
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}
		Integer monthBalance = Integer.valueOf(monthBalanceStr);
		int remainDebt = monthBalance;
		String status = getStatusData(thirdOrderId, order);
		OrderInfoData infoData = new OrderInfoData();
		infoData.setApply_status(Integer.valueOf(status));
		infoData.setBalance(balance); // 批复金额
		infoData.setBank_code(bankCode);// 提现银行
		infoData.setCard_no(cardNo); // 提现银行卡号
		infoData.setMonth_balance(monthBalance);// 每期应还
		infoData.setReal_balance(realBalance);// 到账金额
		infoData.setRemain_debt(remainDebt);// 剩余还款金额
		infoData.setRepay_time(repayTime);// 还款时间
		infoData.setService_fee(serviceFee);// 借款服务费
		infoData.setTerm(term);// 批复期限
		return JSON.toJSONString(infoData);
	}

	/**
	 * 获取用户状态
	 * 
	 * @param bwOrder
	 */
	private String getStatusData(String thirdOrderId, BwOrder bwOrder) {
		logger.info("~~~~~~~~~百度 开始获取用户状态~~~~~~~~~,orderId=" + bwOrder.getId());
		String authStatus = "";
		Long statusId = bwOrder.getStatusId();
		if (statusId == 1) {
			Integer bankStatus = getBankCard(bwOrder);
			if (bankStatus == 2) {
				authStatus = "1102";
			}
			return authStatus;
		}
		if (statusId == 4) {
			logger.info("~~~~~~~~~百度 开始用户签约~~~~~~~~~,orderId=" + bwOrder.getId());
			ThirdResponse thirdResponse = thirdCommonServiceImpl.updateSignContract(thirdOrderId, bwOrder.getChannel());
			logger.info("~~~~~~~~~百度 结束用户签约~~~~~~~~~,接口调用结果是 = " + JSONObject.toJSONString(thirdResponse));
		}
		if (statusId == 9 || statusId == 6) {
			logger.info("~~~~~~~~~百度 根据订单查询还款计划开始~~~~~~~~~,orderId=" + bwOrder.getId());
			BwRepaymentPlan plan = bwRepaymentPlantService.getBwRepaymentPlanByOrderId(bwOrder.getId());
			logger.info("~~~~~~~~~百度 根据订单查询还款计划结束~~~~~~~~~,orderId=" + bwOrder.getId());
			// 未还款的情况下
			if (plan.getRepayStatus() == 1) {
				Long payTime = plan.getRepayTime().getTime() / 1000;
				Long currentTime = System.currentTimeMillis() / 1000;
				authStatus = payTime > currentTime ? "5103" : "5102";
				return authStatus;
			}
			// 已还款的情况
			if (plan.getRepayStatus() == 2) {
				authStatus = plan.getRepayType() == 2 ? "5202" : "5201";
				return authStatus;
			}
		}
		if ("0".equals(bwOrder.getCreditLimit().toString())) {
			authStatus = "202";
			return authStatus;
		}
		if (bwOrder.getCreditLimit() != null) {
			authStatus = "1201";
			return authStatus;
		}
		authStatus = StatusUtil.convertOrderStatus(statusId);
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~用户授权状态：~~~~~~~~~~~~~~~~~~~~~~~~~~" + authStatus);
		return authStatus;
	}

	/**
	 * 
	 * @param bwOrder
	 */
	private Integer getBankCard(BwOrder bwOrder) {
		logger.info("~~~~~~~~~结束百度金融查询银行卡信息~~~~~~~~~~~~~~~~,orderId=" + bwOrder.getId());
		BwBankCard bankCard = bankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
		if (!CommUtils.isNull(bankCard)) {
			logger.info("~~~~~~~~~结束百度金融查询银行卡信息~~~~~~~~~~~~~~~~,cardId=" + bankCard.getId());
			return bankCard.getSignStatus();
		}
		return null;
	}

	/**
	 * 获取还款计划
	 * 
	 * @param OrderInfo
	 */
	private BwRepaymentPlan getBorrwerPlan(OrderInfo OrderInfo) {
		BwRepaymentPlan repaymentPlan = new BwRepaymentPlan();
		Long orderId = getOrderIdByOrderNo(OrderInfo.getApply_id());
		repaymentPlan.setOrderId(orderId);
		logger.info("~~~~~~~~~结束百度金融查询借款计划记录~~~~~~~~~~~~~~~~orderId=" + orderId);
		BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlantService.findBwRepaymentPlanByAttr(repaymentPlan);
		logger.info("~~~~~~~~~结束百度金融查询借款计划记录~~~~~~~~~~~~~~~~~~orderId=" + orderId);
		return bwRepaymentPlan;
	}

	/**
	 * 根据订单id查询逾期信息
	 * 
	 * @param bwOrder
	 * @return
	 */
	private BwOverdueRecord getOverdueRecordByOrderId(BwOrder bwOrder) {
		BwOverdueRecord overdueRecord = new BwOverdueRecord();
		overdueRecord.setOrderId(bwOrder.getId());
		logger.info("~~~~~~~~~开始百度金融查询逾期信息~~~~~~~~~，bwOrderId=" + bwOrder.getId());
		overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(overdueRecord);
		logger.info("~~~~~~~~~结束百度金融查询逾期信息~~~~~~~~~");
		return overdueRecord;
	}

	/**
	 * 根据借款人id查询银行卡信息
	 * 
	 * @param bwOrder
	 * @param borrowerId
	 * @return
	 */
	private BwBankCard getBankCardByBorrowerId(BwOrder bwOrder, Long borrowerId) {
		logger.info("~~~~~~~~~开始百度金融根据借款人id查询银行卡信息~~~~~~~~~，bwOrderId=" + bwOrder.getId());
		BwBankCard bankCard = bankCardService.findBwBankCardByBoorwerId(borrowerId);
		logger.info("~~~~~~~~~结束百度金融根据借款人id查询银行卡信息~~~~~~~~~bankCardId=" + bankCard.getId());
		return bankCard;
	}

	/**
	 * 根据订单号查询订单id
	 * 
	 * @param orderNo 订单号
	 * @return
	 */
	private Long getOrderIdByOrderNo(String orderNo) {
		logger.info("~~~~~~~~~开始百度金融根据订单号查询订单id~~~~~~~~~，orderNo=" + orderNo);
		BwOrder order = bwOrderService.findBwOrderByOrderNo(orderNo);
		if (!CommUtils.isNull(order)) {
			logger.info("~~~~~~~~~结束百度金融根据订单号查询订单id~~~~~~~~~orderId=" + order.getId());
			return order.getId();
		}
		return null;
	}

	/**
	 * 根据第三方订单号查询订单号
	 * 
	 * @param thirdOrderNo 第三方订单号
	 * @return
	 */
	private String getOrderNoByThirdOderNo(String thirdOrderNo) {
		logger.info("~~~~~~~~~开始百度金融根据第三方订单号查询订单号~~~~~~~~~，thirdOrderNo=" + thirdOrderNo);
		BwOrder order = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
		if (!CommUtils.isNull(order)) {
			logger.info("~~~~~~~~~结束百度金融根据第三方订单号查询订单号~~~~~~~~~,orderNo=" + order.getOrderNo());
			return order.getOrderNo();
		}
		return null;
	}

	/**
	 * 根据订单id查询借款人id
	 * 
	 * @param OrderInfo
	 * @param bwOrder
	 * @return
	 */
	private Long getBorrwerIdByOrderId(OrderInfo OrderInfo, BwOrder bwOrder) {
		logger.info("~~~~~~~~~百度金融开始查询借款人~~~~~~~~~，bwOrderId=" + bwOrder.getId());
		Long orderId = getOrderIdByOrderNo(OrderInfo.getApply_id());
		BwBorrower bwBorrower = borrowerService.findBwBorrowerIdByOrderId(orderId);
		if (CommUtils.isNull(bwBorrower)) {
			logger.info("~~~~~~~~~百度金融查询借款人为空~~~~~~~~~bwOrderId=" + bwOrder);
			return null;
		}
		Long borrowerId = bwBorrower.getId();
		logger.info("~~~~~~~~~百度金融结束查询借款人~~~~~~~~~borrowerId=" + borrowerId);
		return borrowerId;
	}

	/**
	 * 
	 * 获取还款计划返回的data数据
	 * 
	 * @param bwOrder
	 * @param brpList
	 */
	private String setRepaymentPlanData(BwOrder bwOrder, List<BwRepaymentPlan> brpList) {
		List<RepaymentPlanData> rpList = new ArrayList<>();
		for (BwRepaymentPlan brp : brpList) {
			String amountStr = "0";
			RepaymentPlanData repaymentPlanData = new RepaymentPlanData();
			BwOverdueRecord overdueRecord = getOverdueRecordByOrderId(bwOrder);
			if (!CommUtils.isNull(overdueRecord)) {
				logger.info("有逾期记录,累加逾期金额");
				amountStr = new BigDecimal(amountStr).add(new BigDecimal(overdueRecord.getOverdueAccrualMoney()))
						.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			}
			amountStr = String.valueOf(brp.getRealityRepayMoney());
			// 已还款
			int status = 1;
			if (brp.getRepayStatus() != 2) {
				status = 0; // 未还款
			}
			// 计划还款时间
			int dueTime = Long.valueOf(brp.getRepayTime().getTime() / 1000).intValue();
			// 实际还款时间
			int realRepayTime = 0;// 已还款的情况
			// 未还款的情况
			if (brp.getRepayStatus() == 2) {
				realRepayTime = Long.valueOf(brp.getUpdateTime().getTime() / 1000).intValue();
			}
			String planId = brp.getId().toString();
			repaymentPlanData.setStatus(status);
			repaymentPlanData.setAmount(Integer.valueOf(amountStr));
			repaymentPlanData.setDue_time(dueTime);
			repaymentPlanData.setReal_repay_time(realRepayTime);
			repaymentPlanData.setPlan_id(planId);
			rpList.add(repaymentPlanData);
		}
		return JSON.toJSONString(rpList);
	}

	/**
	 * 检查百度订单和自己订单是否存在
	 * 
	 * @param orderNo
	 * @return
	 */
	private BwOrder getOrderByTOrderNo(String orderNo) {
		BwOrder bwOrder = null;
		try {
			logger.info("百度金融开始查询orderRong工单，thirdOrderNo=" + orderNo + "...");
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
			logger.info("百度金融查询orderRong工单为:thirdOrderNo=" + orderNo + "...");

			if (com.beadwallet.utils.CommUtils.isNull(bwOrderRong)) {
				logger.info("orderRong工单不存在");
				return null;
			}

			logger.info("百度金融开始查询工单信息，orderId=" + bwOrderRong.getOrderId() + "...");
			bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
			logger.info("百度金融查询工单信息为id:" + bwOrderRong.getOrderId());

			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				logger.info("百度金融工单不存在" + bwOrderRong.getOrderId());
				return null;
			}
		} catch (Exception e) {
			logger.error("百度金融getOrderByTOrderNo occured exception:", e);
		}
		return bwOrder;
	}

	private String urlClient(String url, Map<String, String> map) {
		// String url = "http://106.14.238.126:8092/beadwalletloanapp/app/register/register.do";
		String body = null;
		try {
			body = BaiduUtil.send(url, map, "utf-8");
		} catch (ParseException | IOException e) {
			logger.error("获取下载资源url异常:", e);
		}
		return body;

	}

	/**
	 * 获取下载资源url
	 * 
	 * @param url
	 * @param orderId
	 * @param mobile
	 * @return
	 * @throws DigestException
	 */
	private String getUrl(String url, String orderId, String mobile) throws DigestException {

		logger.info("~~~~~~~~~~~~~~百度 获取下载url开始~~~~~~~~~~,url=" + url);
		Map<String, String> paramMap = new HashMap<>();
		DataEntity dataEntity = new DataEntity();
		dataEntity.setOrder_id(orderId);
		dataEntity.setMerchant_code(MERCHANT_CODE);
		dataEntity.setMobile(mobile);
		paramMap.put("tp_code", TPCODE);
		paramMap.put("timestamp", new Date().getTime() + "");
		paramMap.put("data", JSONObject.toJSONString(dataEntity));
		// String sign = getSign(paramMap);
		String sign = Sha1Util.getParamStrSHA1(paramMap);
		paramMap.put("sign", sign);
		String param = "tp_code=" + TPCODE + "&timestamp=" + new Date().getTime() + "" + "&data="
				+ JSONObject.toJSONString(dataEntity) + "&sign=" + sign;
		String urlNew = urlClient(url, paramMap);
		logger.info("~~~~~~~~~~~~~~百度 获取下载url结束~~~~~~~~~~,url=" + url);
		return urlNew;
	}

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(Long.valueOf(date.getTime() / 1000).intValue());
		System.out.println(date.getTime() / 1000);
		String plan_id = Long.valueOf(20L).toString();
		System.out.println(plan_id);
		List<RepaymentPlanData> list = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			RepaymentPlanData repaymentPlanData = new RepaymentPlanData();
			repaymentPlanData.setAmount(2);
			repaymentPlanData.setDue_time(3);
			repaymentPlanData.setPlan_id("20");
			repaymentPlanData.setReal_repay_time(22);
			repaymentPlanData.setStatus(0);
			@SuppressWarnings("unchecked")
			Map<String, String> paramMap = (Map<String, String>) JSONObject
					.parse(JSONObject.toJSONString(repaymentPlanData));
			System.out.println("-----" + JSONObject.toJSONString(repaymentPlanData));
			System.out.println(paramMap);
			list.add(repaymentPlanData);
		}
		if (!CommUtils.isNull(list) || list.size() != 0) {
			System.out.println(JSON.toJSONString(list));
		}
		if ("0".equals(0L + "")) {
			Long currentTime = System.currentTimeMillis();
			System.out.println("------++++++" + currentTime / 1000);
		}
		DataEntity dataEntity = new DataEntity();
		dataEntity.setOrder_id("2");
		dataEntity.setMerchant_code(MERCHANT_CODE);
		dataEntity.setMobile("3365");
		String sign = "sdfsdfsdgsdgsd";
		String param = "tp_code=" + TPCODE + "&timestamp=" + new Date().getTime() + "" + "&data="
				+ JSONObject.toJSONString(dataEntity) + "&sign=" + sign;
		System.out.println("****************" + param);
		if (RedisUtils.exists(BASE_INFO_BACK_TEMP_REDIS)) {
			System.out.println("-----------------------------------------------");
		}
		RedisUtils.rpush("baidu:orderPush:back:temp", "sdgsdfgsdgsd");
		System.out.println("结果是：" + RedisUtils.lpop("baidu:orderPush:back:temp"));
	}
}
