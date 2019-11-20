package com.waterelephant.zlgxb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.utils.HttpClientHelper;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwZlgxbAuthData;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.zlgxb.service.IZlgxbBaseInfoService;
import com.waterelephant.zlgxb.utils.ZhimaScoreConstant;
/**
 * 公信宝-授权中心	
 * @author wurenbiao
 *
 */
@Controller
@RequestMapping("/zlgxb")
public class ZlgxbAuthCentersController {
	
	private Logger logger = LoggerFactory.getLogger(ZlgxbAuthCentersController.class);
	
	@Autowired
	private IZlgxbBaseInfoService baseinfoserviceimpl;
	
	@Autowired
	private ThreadPoolTaskExecutor gxbTaskExecutor;
	
	@ResponseBody
	@RequestMapping(value="/auth/zhimaScore.do",method= RequestMethod.POST)
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
			String authItem = ZhimaScoreConstant.zhimaScore;
			JSONObject resp = baseinfoserviceimpl.saveAuthData(name, phone, idCard, authItem, sequenceNo, returnUrl, notifyUrl, timestamp);
			responseResult.setCode("0000");
			responseResult.setMsg("success~");
			responseResult.setResult(resp);
		} catch(IllegalArgumentException e) {
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		}catch (Exception e) {
			logger.error("滴滴芝麻授权异常,参数name:{},id_card:{},phone:{},sequence_no:{},timestamp:{},returnUrl:{},notify_url{}异常信息：{}",name,idCard,phone,sequenceNo,timestamp,returnUrl,notifyUrl,e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg("滴滴芝麻授权失败~");
		}
		return responseResult;
	}
	
	/**
	 * 手动获取信用分
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/auth/AuthDataByQuery.do",method= RequestMethod.POST)
	public AppResponseResult getAuthData(HttpServletRequest request,HttpServletResponse response){
		AppResponseResult responseResult = new AppResponseResult();
		String token = request.getParameter("token");
		String sequenceNo = request.getParameter("sequenceNo");
		try {
			Assert.hasText(token, "缺少参数,[token]字段不能为空~");
			Assert.hasText(token, "缺少参数,[sequenceNo]字段不能为空~");
			JSONObject resp = baseinfoserviceimpl.updateAuthDatByQuery(token, sequenceNo);
			responseResult.setCode("0000");
			responseResult.setMsg("success~");
			responseResult.setResult(resp);
		} catch(IllegalArgumentException e) {
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		}catch (Exception e) {
			logger.error("滴滴芝麻授权异常,参数token:{},sequenceNo:{},异常信息：{}",token,sequenceNo,e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg("滴滴芝麻授权失败~");
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
	@RequestMapping(value ="/auth/report.do",produces="text/html;charset=UTF-8") 
	public String report(@RequestBody JSONObject json){
		String response = null;
		if(!CommUtils.isNull(json)){
			try {
				json.put("authJson", json.getJSONObject("authJson"));
				JSONObject jsonresp = json.getJSONObject("authJson");
				String score = jsonresp.getString("score");
				String status = jsonresp.getString("status");
				String authStatus = json.getString("authStatus");
				String sequenceNo = json.getString("sequenceNo");
				String authTime = json.getString("authTime");
				baseinfoserviceimpl.updateAuthData(score,status,authStatus,authTime,sequenceNo);
				//执行推送数据
				pushData(sequenceNo,json);
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
			BwZlgxbAuthData authdata = baseinfoserviceimpl.queryAuthData(sequenceNo);
			if(authdata== null || StringUtils.isEmpty(authdata.getNotifyUrl())) {
				logger.info("sequence_no:{}的授权记录不存在或notifiy_url为空");
				return;
			}
			String notifyurl = authdata.getNotifyUrl();
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
}
