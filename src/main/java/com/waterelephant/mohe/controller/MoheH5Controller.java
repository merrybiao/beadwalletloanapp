package com.waterelephant.mohe.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.shujumohe.ShujumoheSDKService;
import com.waterelephant.authentication.annotation.ApiCheckSign;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.mohe.common.MofangAuthType;
import com.waterelephant.mohe.service.MoheBussinessService;
import com.waterelephant.service.BwMoheAuthRecordService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.ProductType;

/**
 * 魔方（数据魔盒）H5接口
 * H5接口，能够快速接入，无须开发成本，适用于APP、公众号等应用。接近零开发成本，能支持部分页面定制
 * @author dinglinhao
 *
 */
@Controller
@RequestMapping("/mohe/h5")
public class MoheH5Controller {
	
	private Logger logger = LoggerFactory.getLogger(MoheH5Controller.class);
	
	@Autowired
	private BwMoheAuthRecordService bwMoheAuthRecordService;
	
	@Autowired
	private MoheBussinessService moheBussinessService;
	
	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/mobile/auth.do")
	public AppResponseResult authByMobile(@RequestBody JSONObject params) {
		AppResponseResult responseResult = new AppResponseResult();
		logger.info("----【数据魔盒】--运营商授权,接口入参:{}---",params.toJSONString());
		String realName = params.getString("realName");
		String idcardNum = params.getString("idcardNum");
		String userMobile = params.getString("userMobile");
		String returnUrl = params.getString("returnUrl");
		String notifyUrl = params.getString("notifyUrl");
		String productNo = params.getString("productNo");
		String reqId = null;//请求唯一ID
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(realName, "缺少参数[realName]字段不能为空~");
			Assert.hasText(idcardNum, "缺少参数[idcardNum]字段不能为空~");
			Assert.hasText(userMobile,"缺少参数[userMobile]字段不能为空~");
			Assert.hasText(returnUrl,"缺少参数[returnUrl]字段不能为空~");
			Assert.hasText(notifyUrl,"缺少参数[notifyUrl]字段不能为空~");
			reqId = DigestUtils.md5Hex(String.format("%s%s%s%s%s", realName, idcardNum, userMobile,MofangAuthType.MOBILE_OPERATOR.getAuthType(), System.currentTimeMillis()));
			String result = ShujumoheSDKService.authByMobile(realName, idcardNum, userMobile, reqId);
			responseResult = JSON.parseObject(result,AppResponseResult.class);
			bwMoheAuthRecordService.saveRecord(productNo, MofangAuthType.MOBILE_OPERATOR.getAuthType(), realName, idcardNum, userMobile, reqId, returnUrl, notifyUrl);
		} catch(IllegalArgumentException e) {
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());  
		} catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求接口失败，请稍后再试~");
			logger.error("----【数据魔盒】--运营商授权接口异常，异常信息：{}",e.getMessage());
			e.printStackTrace();
		} 
		return responseResult;
	}
	
	
	@ResponseBody
	@RequestMapping("/mobile/auth_channel.do")
	public AppResponseResult channelAuthByMobile(@RequestBody String param) {
		JSONObject params = JSON.parseObject(param);
		AppResponseResult responseResult = new AppResponseResult();
		logger.info("----【数据魔盒】--运营商授权,接口入参:{}---",params.toJSONString());
		String productNo = params.getString("productNo");
		String realName = params.getString("realName");
		String idcardNum = params.getString("idcardNum");
		String userMobile = params.getString("userMobile");
		String returnUrl = params.getString("returnUrl");
		String orderId = params.getString("orderId");
		String channel = params.getString("channel");
		String reqId = null;//请求唯一ID
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasText(realName, "缺少参数[realName]字段不能为空~");
			Assert.hasText(idcardNum, "缺少参数[idcardNum]字段不能为空~");
			Assert.hasText(userMobile,"缺少参数[userMobile]字段不能为空~");
			Assert.hasText(orderId,"缺少参数[orderId]字段不能为空~");
			Assert.hasText(channel,"缺少参数[channel]字段不能为空~");
			Assert.hasText(returnUrl,"缺少参数[returnUrl]字段不能为空~");
			reqId = DigestUtils.md5Hex(String.format("%s%s%s%s%s", realName, idcardNum, userMobile,MofangAuthType.MOBILE_OPERATOR.getAuthType(), System.currentTimeMillis()));
			String result = ShujumoheSDKService.authByMobile(realName, idcardNum, userMobile, reqId);
			responseResult = JSON.parseObject(result,AppResponseResult.class);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("orderId", orderId);
			jsonObject.put("channel", channel);
			String ext = jsonObject.toJSONString();
			bwMoheAuthRecordService.saveRecord(productNo, MofangAuthType.MOBILE_OPERATOR.getAuthType(), realName, idcardNum, userMobile, reqId, returnUrl,ext);
		} catch(IllegalArgumentException e) {
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());  
		} catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求接口失败，请稍后再试~");
			logger.error("----【数据魔盒】--运营商授权接口异常，异常信息：{}",e.getMessage());
			e.printStackTrace();
		} 
		return responseResult;
	}
	
//	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/query/task_report.do")
	public AppResponseResult queryTaskReport(@RequestBody JSONObject params) {
		AppResponseResult responseResult = new AppResponseResult();
		logger.info("----【数据魔盒】--查询任务报告,接口入参:{}---",params.toJSONString());
		String productNo = params.getString("productNo");
		String taskId = params.getString("taskId");
		String appId = params.getString("appId");
		String gzip = params.getString("gzip");
		
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasLength(taskId,"缺少参数[taskId]字段不能为空~");
			
			Map<String,Object> result = moheBussinessService.queryTaskReport(taskId,appId,StringUtils.isEmpty(gzip) ? false : Boolean.valueOf(gzip));
			responseResult.setResult(result);
			responseResult.setCode("0000");
			responseResult.setMsg("请求成功~");
		} catch (IllegalArgumentException e) {
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
			responseResult.setResult(new HashMap<>());
		} catch (BusinessException e) {
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
			responseResult.setResult(new HashMap<>());
		}catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求接口失败，请稍后再试~");
			logger.error("----【数据魔盒】--查询任务报告接口异常，异常信息：{}",e.getMessage());
			e.printStackTrace();
		}
		return responseResult;
	}
	
	
//	@ApiCheckSign
	@ResponseBody
	@RequestMapping("/query/task_data.do")
	public AppResponseResult queryTaskData(@RequestBody JSONObject params) {
		AppResponseResult responseResult = new AppResponseResult();
		logger.info("----【数据魔盒】--查询任务原始数据,接口入参:{}---",params.toJSONString());
		String productNo = params.getString("productNo");
		String taskId = params.getString("taskId");
		String gzip = params.getString("gzip");
		String appId = params.getString("appId");
		try {
			Assert.hasText(productNo,"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			Assert.hasLength(taskId,"缺少参数[taskId]字段不能为空~");
			
			Map<String,Object> result = moheBussinessService.queryTaskData(taskId,appId,StringUtils.isEmpty(gzip) ? false : Boolean.valueOf(gzip));
			responseResult.setResult(result);
			responseResult.setCode("0000");
			responseResult.setMsg("请求成功~");
		} catch (IllegalArgumentException e) {
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
			responseResult.setResult(new HashMap<>());
		} catch (BusinessException e) {
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
			responseResult.setResult(new HashMap<>());
		}catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求接口失败，请稍后再试~");
			logger.error("----【数据魔盒】--查询任务原始数据接口异常，异常信息：{}",e.getMessage());
			e.printStackTrace();
		}
		return responseResult;
	}
	
}
