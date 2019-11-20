package com.waterelephant.gxb.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.utils.HttpClientHelper;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwGxbAuthRecord;
import com.waterelephant.gxb.dto.AuthInfoDto;
import com.waterelephant.gxb.dto.TaxiCommonAddressDto;
import com.waterelephant.gxb.dto.TaxiDataDto;
import com.waterelephant.gxb.dto.TaxiOrderDto;
import com.waterelephant.gxb.service.GxbBusiService;
import com.waterelephant.gxb.utils.GxbConstant;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.DateUtil;

/**
 * 公信宝-授权中心
 * @author dinglinhao
 *
 */
@Controller
@RequestMapping("/gxb")
public class GxbAuthCentersController {
	
	private Logger logger = LoggerFactory.getLogger(GxbAuthCentersController.class);
	
	@Autowired
	private GxbBusiService gxbBusiService;
	
	@Autowired
	private ThreadPoolTaskExecutor gxbTaskExecutor;
	
	@ResponseBody
	@RequestMapping(value="/auth/didizm.do",method= RequestMethod.POST)
	public AppResponseResult auth(HttpServletRequest request,HttpServletResponse response) {
		AppResponseResult responseResult = new AppResponseResult();
		String name = request.getParameter("name");
		String idCard = request.getParameter("id_card");
		String phone = request.getParameter("phone");
		String timestamp = request.getParameter("timestamp");
		String returnUrl = request.getParameter("return_url");
		String notifyUrl = request.getParameter("notify_url");
		String sequenceNo = request.getParameter("sequence_no");
		
		try {
			Assert.hasText(name, "缺少参数,[name]字段不能为空~");
			Assert.hasText(idCard, "缺少参数,[id_card]字段不能为空~");
			Assert.hasText(phone, "缺少参数,[phone]字段不能为空~");
			Assert.hasText(sequenceNo, "缺少参数[sequence_no]字段不能为空~");
			Assert.hasText(timestamp, "缺少参数,[timestamp]字段不能为空~");
			Assert.hasText(returnUrl, "缺少参数,[return_url]字段不能为空~");
			Assert.hasText(notifyUrl, "缺少参数,[notify_url]字段不能为空~");
			String authItem = GxbConstant.GxbAuthItem.DIDIZM;
			Long id = gxbBusiService.saveAuthRecord(name, phone, idCard, authItem, sequenceNo, returnUrl, notifyUrl, timestamp);
//			String token = gxbBusiService.getToken(name, phone, idCard, authItem,sequenceNo,timestamp);
//			String redirectUrl = gxbBusiService.auth(token,returnUrl);
			Map<String,String> resultMap = gxbBusiService.quickAuth(sequenceNo, name, idCard, phone, authItem, returnUrl, notifyUrl, timestamp);
			String redirectUrl = resultMap.get("redirect_url");
			String token = resultMap.remove("token");
			gxbBusiService.updateAuthRecord(id,redirectUrl,token);
			responseResult.setCode("0000");
			responseResult.setMsg("success~");
			responseResult.setResult(resultMap);
		} catch(IllegalArgumentException e) {
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		}catch (Exception e) {
			logger.error("滴滴芝麻授权异常,参数name:{},idCard:{},phone:{},timestamp:{},returnUrl:{},异常信息：{}",name,idCard,phone,timestamp,returnUrl,e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg("滴滴芝麻授权失败~");
		}
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/rawdata.do")
	public AppResponseResult rawdata(HttpServletRequest request,HttpServletResponse response) {
		AppResponseResult responseResult = new AppResponseResult();
		String sequenceNo = request.getParameter("sequence_no");
		try {
			Assert.hasText(sequenceNo, "参数[sequence_no]不能为空~");
			
			BwGxbAuthRecord authRecord = gxbBusiService.queryAuthRecordBySequenceNo(sequenceNo);
			
			if(null == authRecord) throw new IllegalAccessException("授权记录不存在或非法参数信息~");
			TaxiDataDto result = null;
			if("1".equals(authRecord.getAuthStatus())) {
				result = gxbBusiService.queryRawdata(sequenceNo);
			}
			if(null == result) {
				result = gxbBusiService.checkRawdata(authRecord.getToken(),sequenceNo);
			}
			responseResult.setCode("0000");
			responseResult.setMsg("success~");
			responseResult.setResult(result);
		} catch(IllegalArgumentException e) {
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("获取数据报告异常，参数sequence_no：{},异常信息：{}",sequenceNo,e.getMessage());
			e.printStackTrace();
			responseResult.setCode("9999");
			responseResult.setMsg("获取数据报告失败~");
		}
		return responseResult;
	}
	
	/**
	 * 获取推送数据
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="/report.do",produces="text/html;charset=UTF-8") 
	public String report(@RequestBody JSONObject json){
		String response = null;
		if(!CommUtils.isNull(json)){
			try {
				json.put("authJson", json.getJSONObject("authJson"));
				logger.info("修改过后的数据为:"+json);
				String sequenceNo = json.getString("sequenceNo");
				String authTime = json.getString("authTime");
				String token = json.getString("token");
				JSONObject authJson = json.getJSONObject("authJson");
				String orderList = authJson.getString("orderList");
				String commonaddresslist = authJson.getString("commonAddressList");
				TaxiDataDto taxidatadto = new TaxiDataDto();
				taxidatadto.setBalance(authJson.getBigDecimal("balance"));
				taxidatadto.setCoin(authJson.getInteger("coin"));
				taxidatadto.setEmail(authJson.getString("email"));
				taxidatadto.setGender(authJson.getInteger("gender"));
				taxidatadto.setIdCard(authJson.getString("idCard"));
				taxidatadto.setLastUpdateDate(authJson.getDate("lastUpdateDate"));
				taxidatadto.setLevel(authJson.getIntValue("level"));
				taxidatadto.setLevelName(authJson.getString("levelName"));
				taxidatadto.setName(authJson.getString("name"));
				taxidatadto.setPhone(authJson.getString("phone"));
				taxidatadto.setSesameScore(authJson.getInteger("sesameScore"));
				taxidatadto.setSpecialCardBalance(authJson.getBigDecimal("specialCardBalance"));
				taxidatadto.setStatus(authJson.getIntValue("status"));
				Date authTimedate = DateUtil.stringToDate(authTime,"yyyy-MM-dd HH:mm:ss");
				AuthInfoDto authinfodto = new AuthInfoDto();
				authinfodto.setSequenceNo(sequenceNo);
				authinfodto.setAuthTime(authTimedate);
				authinfodto.setToken(token);
				List<TaxiCommonAddressDto> taxicommonaddressdto =  JSONArray.parseArray(commonaddresslist,TaxiCommonAddressDto.class); 
				List<TaxiOrderDto> listTaxiOrderDto = JSONArray.parseArray(orderList, TaxiOrderDto.class);
				//存儲数据库
				boolean resp = gxbBusiService.savereport(listTaxiOrderDto, taxidatadto,authinfodto,taxicommonaddressdto);
				//存储数据库成功，执行推送数据操作
				if(resp){
					//进入推送流程
					pushData(sequenceNo,json);
				}
				JSONObject json2 = new JSONObject();
				json2.put("retCode", 1);
				json2.put("retMsg", "成功");
				response = json2.toString();
			} catch (Exception e) {
				JSONObject json2 = new JSONObject();
				json2.put("retCode", 2);
				json2.put("retMsg", "失败");
				response = json2.toString();
				logger.error("获取数据报告异常，异常信息：{}",e);
			}
		}else{
			JSONObject json2 = new JSONObject();
			json2.put("retCode", 2);
			json2.put("retMsg", "失败");
			response = json2.toString();
			logger.info("获取数据报告数据为空");
		}
		return response;
	}
	
	private void pushData(String sequenceNo,JSONObject json) {
		try {
			//推送数据
			logger.info("开始推送sequence_no:{}数据",sequenceNo);
			BwGxbAuthRecord authRecord = gxbBusiService.queryAuthRecordBySequenceNo(sequenceNo);
			if(authRecord== null || StringUtils.isEmpty(authRecord.getNotifyUrl())) {
				logger.info("sequence_no:{}的授权记录不存在或notifiy_url为空");
				return;
			}
			
			String notifyurl = authRecord.getNotifyUrl();

			AppResponseResult responseResult = new AppResponseResult();
			responseResult.setCode("0000");
			responseResult.setMsg("推送数据~");
			responseResult.setResult(json);
			
			String jsonStrData = JSON.toJSONString(responseResult);
				Thread task = new Thread(new Runnable() {
					
					@Override
					public void run() {
						for(int i =0;i<3;i++) {
							logger.info("执行sequence_no:{},notifyUrl:{}推送",sequenceNo,notifyurl);
							String result = HttpClientHelper.post(notifyurl, "utf-8", jsonStrData);
							logger.info("执行sequence_no:{},notifyUrl:{}推送,返回结果：{}",sequenceNo,notifyurl,result);
							if(!StringUtils.isEmpty(result)) {
								AppResponseResult appResponseResult = JSON.parseObject(result, AppResponseResult.class);
								if(null != appResponseResult) {
									String recode = appResponseResult.getCode();
									if(!StringUtils.isEmpty(recode)) {
										if("0000".equals(recode)) {
											logger.info("执行sequence_no:{},notifyUrl:{}推送，数据推送成功~",sequenceNo,notifyurl);
											return;
										}
									}
								}
							}
							continue;
						}
						logger.info("执行sequence_no:{},notifyUrl:{}数据推送，推送3次后还是失败了~",sequenceNo,notifyurl);
					}
				});
				gxbTaskExecutor.execute(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行sequenceNo：{}数据推送异常，异常信息：{}",e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping("/mock/reception.do")
	public Map<String,String> receptionData(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, String> resultMap = new HashMap<>();
		try {
			String body = IOUtils.toString(request.getInputStream());
			logger.info("{}方法接收数据：{}","/mock/reception.do",body);
			resultMap = new HashMap<>();
			resultMap.put("code", "0000");
			resultMap.put("msg", "成功啦~");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("接收数据失败，异常信息：{}",e.getMessage());
			resultMap.put("code", "9999");
			resultMap.put("msg", "失败啦~");
		}
		return resultMap;
	}

}
