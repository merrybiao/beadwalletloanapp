package com.waterelephant.third.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.servcie.LianLianPayService;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.third.service.ThirdService;
import com.waterelephant.third.utils.ThirdUtil;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.BankInfoUtils;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * 公共 - 用于绑卡（code0095）
 * 
 * 
 * Module:
 * 
 * BindCardController.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
public class BindCardController {
	private Logger logger = LoggerFactory.getLogger(BindCardController.class);
	@Autowired
	IBwOrderService bwOrderService;
	@Autowired
	IBwBorrowerService bwBorrowerService;
	@Autowired
	IBwOrderChannelService bwOrderChannelService;
	@Autowired
	ThirdService thirdService;
	@Autowired
	IBwBankCardService bwBankCardService;
	@Autowired
	BwOrderRongService bwOrderRongService;

	/**
	 * 绑卡 - 有界面 - 根据订单NO获取用户信息（code0095）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/bindCard/common/getUserInfoByOrderNO.do")
	@ResponseBody
	public AppResponseResult getUserInfoByOrderNO(HttpServletRequest request, HttpServletResponse response) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始controller层获取信息接口");
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			// 第一步：获取参数
			String orderNO = request.getParameter("orderNO"); // 订单编号
			String channelCode = request.getParameter("channelCode"); // 渠道编号

			// 第二步验证参数
			if (StringUtil.isEmpty(orderNO) == true) {
				appResponseResult.setCode("103");
				appResponseResult.setMsg("订单编号为空");
				return appResponseResult;
			}
			if (StringUtil.isEmpty(channelCode) == true) {
				appResponseResult.setCode("103");
				appResponseResult.setMsg("渠道编号为空");
				return appResponseResult;
			}

			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			if (orderChannel == null) {
				appResponseResult.setCode("103");
				appResponseResult.setMsg("渠道不存在，请联系系统管理员");
				return appResponseResult;
			}

			// 第二步：获取用户信息
			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNO);
			if (bwOrder == null) {
				appResponseResult.setCode("103");
				appResponseResult.setMsg("订单不存在");
				return appResponseResult;
			}
			BwBorrower bwBorrower = bwBorrowerService.findNameAndIdCardById(bwOrder.getBorrowerId());
			if (bwBorrower == null) {
				appResponseResult.setCode("103");
				appResponseResult.setMsg("用户不存在");
				return appResponseResult;
			}

			// 第三步：返回
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("cardholderName", bwBorrower.getName());
			resultMap.put("certificateType", "身份证");
			resultMap.put("idCard", bwBorrower.getIdCard());

			appResponseResult.setCode("000");
			appResponseResult.setMsg("获取成功");
			appResponseResult.setResult(resultMap);
		} catch (Exception e) {
			logger.error("公共 - 绑卡 - 获取用户信息异常", e);
			appResponseResult.setCode("100");
			appResponseResult.setMsg("系统异常");
		}
		return appResponseResult;
	}

	/**
	 * 绑卡 - 有界面 - 绑卡（code0095）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/bindCard/common/bindCardPage.do")
	public void bindCardPage(HttpServletRequest request, HttpServletResponse response) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始controller层绑卡接口");
		try {
			// 第一步：获取参数
			String bankCardNO = request.getParameter("bankCardNO"); // 银行卡号
			String bankPhone = request.getParameter("bankPhone"); // 银行卡预留手机号
			String orderNO = request.getParameter("orderNO"); // 订单编号

			// 第二步：获取订单、用户信息
			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNO);
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByOrderId(bwOrder.getId());
			BwOrderRong bwOrderRong = bwOrderRongService.findBwOrderRongByOrderId(bwOrder.getId());
			RedisUtils.hset("third:bindCard", String.valueOf(bwBorrower.getId()), orderNO); // 放入redis用于回调使用

			// 第三步：插入银行卡信息
			String bankName = BankInfoUtils.getNameOfBankCard(bankCardNO);
			BwBankCard bwBankCard = new BwBankCard();
			bwBankCard.setBorrowerId(bwBorrower.getId());
			bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
			if (bwBankCard != null && (bwBankCard.getSignStatus() == 1 || bwBankCard.getSignStatus() == 2)) {
				// // 修改工单状态为2
				// bwOrder.setStatusId(2L);
				// bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				// bwOrder.setSubmitTime(Calendar.getInstance().getTime());
				// bwOrderService.updateBwOrder(bwOrder);
				//
				// // 放入redis
				// SystemAuditDto systemAuditDto = new SystemAuditDto();
				// systemAuditDto.setIncludeAddressBook(0);
				// systemAuditDto.setOrderId(bwOrder.getId());
				// systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
				// systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
				// systemAuditDto.setName(bwBorrower.getName());
				// systemAuditDto.setPhone(bwBorrower.getPhone());
				// systemAuditDto.setIdCard(bwBorrower.getIdCard());
				// systemAuditDto.setChannel(bwOrder.getChannel());
				// systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
				// RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
				// JsonUtils.toJson(systemAuditDto));

				// 存入Redis中，通知第三方机构
				Map<String, Object> map = new HashMap<>();
				map.put("orderId", bwOrder.getId());
				map.put("channelId", bwOrder.getChannel());
				map.put("result", "");
				String json = JSON.toJSONString(map);
				RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), json);
			} else if (bwBankCard != null) {
				bwBankCard.setBankName(bankName);
				String bankCode = ThirdUtil.convertToBankCode(bankName);
				bwBankCard.setBankCode(bankCode);
				bwBankCard.setCardNo(bankCardNO);
				bwBankCard.setPhone(bankPhone);
				bwBankCard.setProvinceCode(null);
				bwBankCard.setCityCode(null);
				bwBankCard.setSignStatus(0);
				bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
				bwBankCardService.updateBwBankCard(bwBankCard);
			} else {
				bwBankCard = new BwBankCard();
				bwBankCard.setBankName(bankName);

				String bankCode = ThirdUtil.convertToBankCode(bankName);
				bwBankCard.setBankCode(bankCode);

				bwBankCard.setCardNo(bankCardNO);
				bwBankCard.setPhone(bankPhone);
				bwBankCard.setBorrowerId(bwBorrower.getId());
				bwBankCard.setProvinceCode(null);
				bwBankCard.setCityCode(null);
				bwBankCard.setSignStatus(0);
				bwBankCard.setCreateTime(Calendar.getInstance().getTime());
				bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
				bwBankCardService.saveBwBankCard(bwBankCard, bwBorrower.getId());
			}

			// 第四步：连连签约
			String urlReturn = SystemConstant.NOTIRY_URL + "/bindCard/common/bindCardCallback.do";
			SignLess signLess = new SignLess();
			signLess.setUser_id(String.valueOf(bwBorrower.getId()));
			signLess.setId_no(bwBorrower.getIdCard());
			signLess.setAcct_name(bwBorrower.getName());
			signLess.setCard_no(bankCardNO);
			signLess.setUrl_return(urlReturn);
			logger.info(sessionId + "开始调用连连绑卡接口,signLess=" + JSONObject.toJSONString(signLess));
			LianLianPayService.signAccreditPay(signLess, response);
			logger.info(sessionId + "结束调用连连绑卡接口");
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层绑卡接口异常", e);
		}
		logger.info(sessionId + "结束controller层绑卡接口");
	}

	/**
	 * 绑卡 - 无界面 - 绑卡（code0095）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bindCard/common/bindCard.do")
	public void bindCard(HttpServletRequest request, HttpServletResponse response) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始controller层绑卡接口");
		try {
			// 第一步：获取参数
			String borrowerID = request.getParameter("borrowerId"); // 用户ID
			String orderNO = request.getParameter("orderNO"); // 订单编号
			String bankCardNO = request.getParameter("bankCardNO"); // 银行卡号

			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNO);
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerIdByOrderId(bwOrder.getId());

			RedisUtils.hset("third:bindCard", borrowerID, orderNO); // 放入redis用于回调使用

			// 第二步：连连签约
			String urlReturn = SystemConstant.NOTIRY_URL + "/bindCard/common/bindCardCallback.do";
			SignLess signLess = new SignLess();
			signLess.setUser_id(borrowerID);
			signLess.setId_no(bwBorrower.getIdCard());
			signLess.setAcct_name(bwBorrower.getName());
			signLess.setCard_no(bankCardNO);
			signLess.setUrl_return(urlReturn);
			logger.info(sessionId + "开始调用连连绑卡接口,signLess=" + JSONObject.toJSONString(signLess));
			LianLianPayService.signAccreditPay(signLess, response);
			logger.info(sessionId + "结束调用连连绑卡接口");
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层绑卡接口异常", e);
		}
		logger.info(sessionId + "结束controller层绑卡接口");
	}

	/**
	 * 绑卡 - 绑卡回调（code0095）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bindCard/common/bindCardCallback.do")
	public String bindCardCallback(HttpServletRequest request, HttpServletResponse response) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始controller层绑卡回调接口");

		// 第一步：获取参数
		String status = request.getParameter("status"); // 状态
		String result = request.getParameter("result"); // 返回结果
		String userId = request.getParameter("user_id");// 用户ID
		logger.info("返回状态"+status);
		if(userId == null){
			JSONObject jsonObject = JSONObject.parseObject(result);
			userId = jsonObject.getString("user_id");
		}
		
		String orderNo = RedisUtils.hget("third:bindCard", userId);
		BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNo);
		Integer channelId = bwOrder.getChannel();
		Long orderId = bwOrder.getId();

		Map<String, Object> map = new HashMap<>();
		map.put("channelId", channelId);
		map.put("orderId", orderId);
		map.put("result", result);
		String json = JSON.toJSONString(map);
		logger.info("返回的json"+json);
		// 成功跳转
		String selfUrl = RedisUtils.hget("third:bindCard:selfUrl:" + channelId, "orderNO_" + orderNo);// 自定义跳转地址
		String successReturnUrl = RedisUtils.hget("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + orderNo); // 成功后的跳转URL
		if (!CommUtils.isNull(selfUrl)) {
			successReturnUrl = "forward:" + selfUrl;
			RedisUtils.hdel("third:bindCard:selfUrl:" + channelId, "orderNO_" + orderNo);
		} else if (!CommUtils.isNull(successReturnUrl)) {
			successReturnUrl = "redirect:" + successReturnUrl;
			RedisUtils.hdel("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + orderNo);
		} else {
			successReturnUrl = "sign_success_third";
		}

		// 失败跳转
		String failReturnUrl = RedisUtils.hget("third:bindCard:failReturnUrl:" + channelId, "orderNO_" + orderNo); // 失败后的跳转URL
		if (!CommUtils.isNull(selfUrl)) {
			failReturnUrl = "forward:" + selfUrl;
			RedisUtils.hdel("third:bindCard:selfUrl:" + channelId, "orderNO_" + orderNo);
		} else if (!CommUtils.isNull(failReturnUrl)) {
			failReturnUrl = "redirect:" + failReturnUrl;
			RedisUtils.hdel("third:bindCard:failReturnUrl:" + channelId, "orderNO_" + orderNo);
		} else {
			failReturnUrl = "sign_fail_third";
		}
		logger.info("失败页面："+failReturnUrl);
		try {
			// 第二步：验证参数
			if (CommUtils.isNull(status) || CommUtils.isNull(result)) {
				// 绑卡状态通知
				logger.info(sessionId + "：结束controller层绑卡回调接口，入参为空");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				return failReturnUrl;
			}

			// 判断是否重复绑卡
			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (!CommUtils.isNull(bwBankCard) && 2 == bwBankCard.getSignStatus()) {
				logger.info(sessionId + "结束controller层绑卡回调接口");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				return successReturnUrl;
			}

			boolean isSuccess = thirdService.updateBindCardCallback(sessionId, status, result);
			logger.info("更新绑卡接口返回结果"+isSuccess);
			if (isSuccess == true) {
				logger.info(sessionId + "结束controller层绑卡回调接口");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				return successReturnUrl;
			} else {
				logger.info(sessionId + "结束controller层绑卡回调接口");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				return failReturnUrl;
			}
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层绑卡回调接口异常", e);
			RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
			return failReturnUrl;
		}
	}

	// 1.绑卡 - 有界面 - 根据订单编号获取用户信息
	//
	// 路径：/bindCard/common/getUserInfoByOrderNO.do
	//
	// 入参：
	// - orderNO：订单编号
	// - channelCode：渠道编号
	//
	// 出参：
	// - code：编号（000：成功，100：系统异常，103：参数错误）
	// - msg：code对应的信息
	// - result：返回结果
	// - cardholderName：持卡人姓名
	// - certificateType：证件类型（固定值：身份证）
	// - idCard：身份证号
	//

	// 2.绑卡 - 有界面 - 绑卡
	//
	// 路径：/bindCard/common/bindCardPage.do
	//
	// 入参
	// - bankCardNO：银行卡号
	// - bankPhone：银行卡预留手机号
	// - orderNO：订单编号
	//
	// 出参：
	// 返回绑卡成功、失败提示页面

	// 3.绑卡 - 无界面 - 绑卡
	//
	// 路径：/bindCard/common/bindCard.do
	//
	// 入参：
	// - borrowerId：用户ID
	// - orderNO：订单编号
	// - bankCardNO：银行卡号
	//
	// 出参：
	// 返回绑卡成功、失败提示页面

	// 4.绑卡 - 绑卡回调（连连回调）
}
