package com.waterelephant.sctx.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.sctx.SctxApplySDK;
import com.waterelephant.service.BwSctxCheckRecordService;
import com.waterelephant.utils.AppResponseResult;
@Controller
@RequestMapping("/sctx")
public class SctxCheck3ElementController {
	
	private Logger logger = LoggerFactory.getLogger(SctxCheck3ElementController.class);
	
	@Autowired
	private BwSctxCheckRecordService bwSctxCheckRecordService;
	
	@ResponseBody
	@RequestMapping(value = "/check_3_element.do",method = RequestMethod.POST)
	public AppResponseResult check3element(@RequestBody JSONObject params) {
		logger.info("----/check_3_element接口入参：{}----",params.toJSONString());
		long star = System.currentTimeMillis();
		AppResponseResult responseResult = new AppResponseResult();
		String name = params.getString("name");
		String mobile = params.getString("mobile");
		String idCard = params.getString("idCard");
		
		String checkResult = null;
		try {
			Assert.hasText(name, "请求参数name不能为空~");
			Assert.hasText(mobile, "请求参数mobile不能为空~");
			Assert.hasText(idCard, "请求参数idCard不能为空~");
			long star1 = System.currentTimeMillis();
			checkResult = SctxApplySDK.check3elements(name, mobile, idCard);
			logger.info("----SctxApplySDK.check3elements请求耗时：{}ms",(System.currentTimeMillis() - star1));
//			checkResult = "{\"handlerData\":{\"code_status\":\"2007\"},\"resCode\":\"0000\",\"resMsg\":\"成功\"}";
			Assert.hasText(checkResult,"调用接口返回结果为空~");
			JSONObject result = null;
			
			try {
				result = JSON.parseObject(checkResult);
			} catch (Exception e) {
				responseResult.setCode("9999");
				responseResult.setMsg("调用接口解析结果异常~");
				return responseResult;
			}
			
			String resCode = result.getString("resCode");
			String resMsg  = result.getString("resMsg");
			JSONObject handlerData = result.getJSONObject("handlerData");
			String code_status = handlerData.getString("code_status");
			
			if("0000".equals(resCode) ) {
				Map<String,Object> data = new HashMap<>();
				data.put("status", "2005".equals(code_status));
				responseResult.setResult(data);
				responseResult.setCode("0000");
				responseResult.setMsg(resMsg+"["+code_status+"]");
			} else {
				responseResult.setCode("9999");
				responseResult.setMsg(resMsg+"["+code_status+"]");
			}
			//记录校验结果
			bwSctxCheckRecordService.saveRecord(name, mobile, idCard, checkResult);
		} catch (IllegalArgumentException e) {
			
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("----/check_3_element.do接口调用异常,params:{}----",params.toJSONString());
			e.printStackTrace();
			responseResult.setCode("9999");
			responseResult.setMsg("调用接口失败");
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("----/check_3_element.do接口请求耗时：{}ms",(end - star));
			
		}
		return responseResult;
		
	}

}
