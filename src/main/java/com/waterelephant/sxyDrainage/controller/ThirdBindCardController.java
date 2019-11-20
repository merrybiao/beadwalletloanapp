//
//package com.waterelephant.sxyDrainage.controller;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.dto.PaySignDto;
//import com.waterelephant.sxyDrainage.service.ThirdBindCardService;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.KaNiuConstant;
//import com.waterelephant.utils.AppResponseResult;
//import com.waterelephant.utils.CheckSignUtil;
//import com.waterelephant.utils.RedisUtils;
//
///**
// * 
// * @ClassName: ThirdBindCardController
// * @Description: 绑卡操作
// * @author liwanliang
// * @date 2018年5月16日
// *
// */
//
//@Controller
//public class ThirdBindCardController {
//
//	private Logger logger = LoggerFactory.getLogger(ThirdBindCardController.class);
//
//	@Autowired
//	private ThirdBindCardService thirdBindCardService;
//	
//	//展示绑卡页面
//	@RequestMapping("/sxyDrainage/cloud/bindCardPage.do")
//	public String bindCardPage(@RequestParam(required = true) String orderNo) {
//		logger.info("【ThirdBindCardController.bindCardPage进入展示绑卡页面接口接受的参数】orderNo=" + orderNo);
//		StringBuilder strBuilder = new StringBuilder();
//		try {
//			//四要素
//			String phone = null;
//			String name = null;
//			String idCard = null;
//			String cardNo = null;
//			//注册手机号
//			String relPhone = null;
//			String jsonStr = RedisUtils.get("tripartite:bindCardURL:" + orderNo);
//			logger.info("【ThirdBindCardController.bindCardPage从redis中获取的参数为】：jsonStr" + jsonStr);
//			if (StringUtils.isNotBlank(jsonStr)) {
//				JSONObject jsonObject = JSON.parseObject(jsonStr);
//				if (null != jsonObject) {
//					phone = jsonObject.getString("phone");
//					name = jsonObject.getString("name");
//					idCard = jsonObject.getString("idCard");
//					cardNo = jsonObject.getString("cardNo");
//					relPhone = jsonObject.getString("relPhone");
//				}
//			}
//			String pageUrl = KaNiuConstant.PAGE_URL;
//			String pageProjectName = KaNiuConstant.PAGE_BINDCARD_NAME;
//			String publicKeyValue = KaNiuConstant.PUBLIC_KEY_VALUE;
//
//			// 签名
//			String sign = CheckSignUtil.sign(relPhone, publicKeyValue, "UTF-8");
//			logger.info("签名 sign====》{}",sign);
//			
//			if(StringUtils.isNotBlank(name)){
//				try {
//					name = URLEncoder.encode(name, "UTF-8");
//				} catch (UnsupportedEncodingException e) {
//					logger.info("【ThirdBindCardController.bindCardPage】编码异常", e);
//				}
//			}
//			String url = pageUrl + pageProjectName + "/bindCard.html?phone=" + phone + "&name=" + name + "&idCard=" + idCard
//					+ "&cardNo=" + cardNo + "&sign=" + sign + "&relPhone=" + relPhone;
//
//			strBuilder.append("redirect:" + url);
//		} catch (Exception e) {
//			logger.error("卡牛跳转绑卡页面失败,orderNo:{},发生异常:{}",orderNo,e);
//		}finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(KaNiuConstant.SX_CHANNELID, orderNo, "0","跳转绑卡页面");
//		}
//		return strBuilder.toString();
//	}
//	
//	
//	// 获取支持的银行
//	@RequestMapping("/sxyDrainage/cloud/getBankByCardNo.do")
//	@ResponseBody
//	public AppResponseResult getSupportBank(@RequestParam(required = true) String cardNo) {
//		logger.info("【ThirdBindCardController.getSupportBank请求入参】：cardNo=" + cardNo);
//
//		AppResponseResult result = null;
//		try {
//			result = thirdBindCardService.querySupportBank(cardNo);
//		} catch (Exception e) {
//			logger.info("获取支持的银行异常:",e);
//			result = new AppResponseResult();
//			result.setCode("111");
//			result.setMsg("操作失败，请重试操作或联系客服人员！");
//		}
//		return result;
//	}
//
//	// 预绑卡发送验证码
//	@RequestMapping("/sxyDrainage/cloud/readyBindBankCard.do")
//	@ResponseBody
//	public AppResponseResult readyBindBankCard(@RequestParam(required = true) String cardNo,
//			@RequestParam(required = true) String phone, @RequestParam(required = true) String name,
//			@RequestParam(required = true) String idCard, @RequestParam(required = true) String sign,
//			@RequestParam(required = true) String relPhone) {
//		AppResponseResult result = new AppResponseResult();
//		try {
//			JSONObject json = new JSONObject();
//			json.put("cardNo", cardNo);
//			json.put("phone", phone);
//			json.put("name", name);
//			json.put("idCard", idCard);
//			json.put("sign", sign);
//			json.put("relPhone", relPhone);
//			logger.info("【ThirdBindCardController.readyBindBankCard请求入参】：" + json.toJSONString());
//
//			PaySignDto paySignDto = new PaySignDto();
//
//			paySignDto.setPhone(phone);
//			paySignDto.setCardNo(cardNo);
//			paySignDto.setName(name);
//			paySignDto.setIdCard(idCard);
//
//			String publicKeyValue = KaNiuConstant.PUBLIC_KEY_VALUE;
//			// 验签
//			boolean checksign = CheckSignUtil.verify(relPhone, sign, publicKeyValue, "UTF-8");
//			logger.info("验签结果：checksign=" + checksign);
//			if (!checksign) {
//				result.setCode("001");
//				result.setMsg("验签失败！");
//				return result;
//			}
//			result = thirdBindCardService.updateAndReadyBindBankCard(paySignDto, relPhone);
//			if("000".equals(result.getCode())){
//				logger.info("预绑卡成功!cardNo:{}",cardNo);
//			}else{
//				logger.info("预绑卡失败!cardNo:{},原因:{}",cardNo,result.getMsg());
//			}
//		} catch (Exception e) {
//			logger.error("预绑卡失败--->phone:{},预绑卡接口异常:{}",phone,e);
//			result.setMsg("预绑卡接口发生异常");
//		}finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(KaNiuConstant.SX_CHANNELID, phone, result.getCode(),"预留手机号",result.getMsg());
//		}
//		return result;
//	}
//
//	// 确认绑卡
//	@RequestMapping("/sxyDrainage/cloud/sureBindBankCard.do")
//	@ResponseBody
//	public AppResponseResult sureBindBankCard(@RequestParam(required = true) String cardNo,
//			@RequestParam(required = true) String phone, @RequestParam(required = true) String name,
//			@RequestParam(required = true) String idCard, @RequestParam(required = true) String verifyCode,
//			@RequestParam(required = true) String sign, @RequestParam(required = true) String relPhone) {
//		AppResponseResult result = new AppResponseResult();
//		try {
//			JSONObject json = new JSONObject();
//			json.put("cardNo", cardNo);
//			json.put("phone", phone);
//			json.put("name", name);
//			json.put("idCard", idCard);
//			json.put("sign", sign);
//			json.put("relPhone", relPhone);
//			json.put("verifyCode", verifyCode);
//
//			logger.info("【ThirdBindCardController.sureBindBankCard请求入参】：" + json.toJSONString());
//
//			PaySignDto paySignDto = new PaySignDto();
//			paySignDto.setPhone(phone);
//			paySignDto.setCardNo(cardNo);
//			paySignDto.setName(name);
//			paySignDto.setIdCard(idCard);
//			paySignDto.setVerifyCode(verifyCode);
//			
//			String publicKeyValue = KaNiuConstant.PUBLIC_KEY_VALUE;
//			// 验签
//			boolean checksign = CheckSignUtil.verify(relPhone, sign, publicKeyValue, "UTF-8");
//			if (!checksign) {
//				result.setCode("001");
//				result.setMsg("验签失败！");
//				return result;
//			}
//			result = thirdBindCardService.updateAndSureBindCard(paySignDto, relPhone);
//			if("000".equals(result.getCode())){
//				logger.info("确认绑卡成功!cardNo:{}",cardNo);
//			}else{
//				logger.info("确认绑卡失败!cardNo:{},原因:{}",cardNo,result.getMsg());
//			}
//		} catch (Exception e) {
//			logger.error("绑卡失败--->relPhone:{},绑卡接口异常:{}",relPhone,e);
//			result.setMsg("确认绑卡接口发生异常");
//		}finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(KaNiuConstant.SX_CHANNELID, relPhone, result.getCode(),"注册手机号",result.getMsg());
//		}
//		return result;
//		
//	}
//}
