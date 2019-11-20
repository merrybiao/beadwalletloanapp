package com.waterelephant.morpho.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.credit.morpho.MorphoUploadSDK;
import com.beadwallet.service.credit.morpho.entity.IssueRequest;
import com.beadwallet.service.credit.morpho.entity.TrackRequest;
import com.beadwallet.service.entity.response.Response;
import com.waterelephant.morpho.entity.BwMorphoUploadLog;
import com.waterelephant.morpho.service.BwMorphoUploadLogService;
import com.waterelephant.morpho.utils.MorphoMd5Util;


/**
 * 
 * Module: MorphoUploadController.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述> 闪蝶上传中转接口
 */
@Controller
public class MorphoUploadController {
	private Logger logger = Logger.getLogger(MorphoUploadController.class);
	@Autowired
	private BwMorphoUploadLogService bwMorphoUploadLogService;

	/**
	 * 上传接口
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "morpho/morphoIssue.do", method = RequestMethod.POST)
	@ResponseBody
	public Response<Object> morphoIssue(@RequestParam(required = true) String json) {
		long sessionId = System.currentTimeMillis();
		logger.info("[闪蝶] 上传中转接口:开始>>>" + sessionId);
		Response<Object> resp = new Response<Object>();
		resp.setRequestCode("-1");
		resp.setRequestMsg("请求失败");
		BwMorphoUploadLog bwMorphoUploadLog = new BwMorphoUploadLog();
		try {
			// 校验接口请求参数
			if (StringUtils.isBlank(json)) {
				resp.setRequestCode("444");
				resp.setRequestMsg("请求参数为空，请检查相关参数！");
				return resp;
			}
			JSONObject requestData = JSON.parseObject(json);
			if (null == requestData) {
				resp.setRequestCode("444");
				resp.setRequestMsg("请求参数为空，请检查相关参数！");
				return resp;
			}
			String pk1 = requestData.getString("pk");
			String data = requestData.getString("data");
			if (StringUtils.isBlank(pk1) || StringUtils.isBlank(data)) {
				resp.setRequestCode("444");
				resp.setRequestMsg("请求参数为空，请检查相关参数！");
				return resp;
			}
			bwMorphoUploadLog.setRequestData(data);
			// 请求合法性校验
			String pk2 = MorphoMd5Util.encoding(data);
			if (!pk2.equals(pk1)) {
				// 非法请求 返回
				resp.setRequestCode("445");
				resp.setRequestMsg("验签失败");
				return resp;
			}
			// 封装请求参数
			IssueRequest issueRequest = JSON.parseObject(data, IssueRequest.class);
			if (issueRequest == null) {
				resp.setRequestCode("446");
				resp.setRequestMsg("请求参数错误，请检查相关参数！");
				return resp;
			}
			bwMorphoUploadLog.setOrderId(issueRequest.getLoanId());
			resp = MorphoUploadSDK.issue(issueRequest);
		} catch (Exception e) {
			logger.error("[闪蝶] 上传中转接口 >>>异常：" + sessionId + ">>>" + e.getMessage());
		} finally {
			try {
				String responseData = JSON.toJSONString(resp);
				if (responseData.length() > 500) {
					responseData = responseData.substring(0, 500);
				}
				bwMorphoUploadLog.setResponseData(JSON.toJSONString(resp));
				bwMorphoUploadLog.setTradeCode(resp.getRequestCode());
				bwMorphoUploadLog.setServiceName("issue");
				bwMorphoUploadLog.setCreateTime(new Date());
				bwMorphoUploadLog.setUpdateTime(new Date());
				bwMorphoUploadLogService.save(bwMorphoUploadLog);
				logger.info("[闪蝶] 上传中转接口:结束>>>" + sessionId + ">>" + resp.getRequestCode());
			} catch (Exception e2) {
				logger.info("[闪蝶] 上传中转接口:结束>>>日志保存异常" + sessionId + ">>" + e2.getMessage());
			}
		}
		return resp;
	}

	/**
	 * 上传更新接口
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "morpho/morphoTrack.do", method = RequestMethod.POST)
	@ResponseBody
	public Response<Object> morphoTrack(@RequestParam(required = true) String json) {
		long sessionId = System.currentTimeMillis();
		logger.info("[闪蝶] 上传更新中转接口:开始>>>" + sessionId);
		Response<Object> resp = new Response<Object>();
		resp.setRequestCode("-1");
		resp.setRequestMsg("请求失败");
		BwMorphoUploadLog bwMorphoUploadLog = new BwMorphoUploadLog();	
		try {
			// 校验接口请求参数
			if (StringUtils.isBlank(json)) {
				resp.setRequestCode("444");
				resp.setRequestMsg("请求参数为空，请检查相关参数！");
				return resp;
			}
			JSONObject requestData = JSON.parseObject(json);
			if (null == requestData) {
				resp.setRequestCode("444");
				resp.setRequestMsg("请求参数为空，请检查相关参数！");
				return resp;
			}
			String pk1 = requestData.getString("pk");
			String data = requestData.getString("data");
			if (StringUtils.isBlank(pk1) || StringUtils.isBlank(data)) {
				resp.setRequestCode("444");
				resp.setRequestMsg("请求参数为空，请检查相关参数！");
				return resp;
			}
			bwMorphoUploadLog.setRequestData(data);
			// 请求合法性校验
			String pk2 = MorphoMd5Util.encoding(data);
			if (!pk2.equals(pk1)) {
				// 非法请求 返回
				resp.setRequestCode("445");
				resp.setRequestMsg("验签失败");
				return resp;
			}
			// 封装请求参数
			TrackRequest trackRequest = JSON.parseObject(data, TrackRequest.class);
			if (trackRequest == null) {
				resp.setRequestCode("444");
				resp.setRequestMsg("请求参数为空，请检查相关参数！");
				return resp;
			}
			bwMorphoUploadLog.setOrderId(trackRequest.getLoanId());
			resp = MorphoUploadSDK.track(trackRequest);
		} catch (Exception e) {
			logger.error("[闪蝶] 上传更新中转接口 >>>异常：" + e.getMessage());
		} finally {
			try {
				String responseData = JSON.toJSONString(resp);
				if (responseData.length() > 500) {
					responseData = responseData.substring(0, 500);
				}
				bwMorphoUploadLog.setResponseData(responseData);
				bwMorphoUploadLog.setTradeCode(resp.getRequestCode());
				bwMorphoUploadLog.setServiceName("track");
				bwMorphoUploadLog.setCreateTime(new Date());
				bwMorphoUploadLog.setUpdateTime(new Date());
				bwMorphoUploadLogService.save(bwMorphoUploadLog);
				logger.info("[闪蝶] 上传更新中转接口:结束>>>" + sessionId + ">>" + resp.getRequestCode());
			} catch (Exception e2) {
				logger.info("[闪蝶] 上传更新中转接口:结束>>>日志保存异常" + sessionId + ">>" + e2.getMessage());
			}
		}
		return resp;
	}
}
