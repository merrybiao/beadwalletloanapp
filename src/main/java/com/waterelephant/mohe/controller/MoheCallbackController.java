package com.waterelephant.mohe.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 数据魔盒通知回调接口
 * @author dinglinhao
 *
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.utils.HttpClientHelper;
import com.waterelephant.entity.BwMoheAuthRecord;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.mf.service.MfBussinessService;
import com.waterelephant.service.BwMoheAuthRecordService;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
@Controller
@RequestMapping("/mohe/callback")
public class MoheCallbackController {
	
	private Logger logger = LoggerFactory.getLogger(MoheCallbackController.class);
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private BwMoheAuthRecordService bwMoheAuthRecordService;
	
	@Autowired
	private MfBussinessService mfBussinessService;

	@ResponseBody
	@RequestMapping("/notify.do")
	public Map<String,Object> notify(HttpServletRequest request,HttpServletResponse response){
		logger.info("----【数据魔盒】----notify.do接口开始接收数据----");
		String notify_event = request.getParameter("notify_event");//通知事件
		String notify_type = request.getParameter("notify_type");//通知类型
		String notify_time = request.getParameter("notify_time");//通知时间。YYYY-MM-DD HH:MM:SS
		String sign = request.getParameter("sign");//暂不支持 验证签名
		String passback_params = request.getParameter("passback_params");//传参数，创建任务时传入
		String notify_data = request.getParameter("notify_data");//通知数据
		//数据推送给三方~
		Map<String,Object> resultMap = new HashMap<>();
		
		try {
			Assert.hasText(notify_event, "缺少参数[notify_event]字段不能为空~");
			Assert.hasText(notify_type, "缺少参数[notify_type]字段不能为空~");
			Assert.hasText(notify_time, "缺少参数[notify_time]字段不能为空~");
			Assert.hasText(passback_params, "缺少参数[passback_params]字段不能为空~");
			Assert.hasText(notify_data, "缺少参数[notify_data]字段不能为空~");
			
			logger.info("----【数据魔盒】----notify.do参数：notify_event:{},notify_type:{},notify_time:{},passback_params:{},notify_data:{}",notify_event,notify_type,notify_time,passback_params,notify_data);
			//从notify_data中解析返回taskId
			String taskId = JSON.parseObject(notify_data).getString("task_id");
			
			BwMoheAuthRecord bwMoheAuthRecord =bwMoheAuthRecordService.updateRecord(passback_params, taskId, notify_event, notify_type, notify_time);
			logger.info("----【数据魔盒】授权记录：{}--",JSON.toJSONString(bwMoheAuthRecord));
			forwardNotifry(bwMoheAuthRecord.getAuthNotifyUrl(),notify_event,notify_type,notify_time,sign,passback_params,notify_data,bwMoheAuthRecord.getTaskId());
			
			resultMap.put("code", 0);
			resultMap.put("message", "回调处理成功");
		} catch (IllegalArgumentException | BusinessException e) {
			logger.error("----【数据魔盒】----notify.do接口接收数据异常：{}",e.getMessage());
			resultMap.put("code", 600);
			resultMap.put("message", e.getMessage());
		} catch (Exception e) {
			logger.error("----【数据魔盒】----notify.do接口接收数据异常：{}",e.getMessage());
			resultMap.put("code", 9999);
			resultMap.put("message", "回调处理失败，数据接收失败~");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/return_h5.do")
	public String returnByH5(HttpServletRequest request,HttpServletResponse response){
		String taskId = request.getParameter("task_id");
		String reqId = request.getParameter("passback_params");
		String allSubmit = request.getParameter("all_submit");
		logger.info("----【数据魔盒】----return_h5.do接口接收数据,task_id:{},passback_params:{},all_submit:{}----",taskId,reqId,allSubmit);
		//通过reqId去更新数据库记录
		BwMoheAuthRecord record = null;
		try {
			Assert.hasText(reqId,"缺少参数[passback_params]字段不能为空~");
			record = bwMoheAuthRecordService.updateRecord(reqId, taskId, null, null, null);
		} catch (BusinessException e) {
			logger.error("----【数据魔盒】----同步回调接口异常：{}----",e.getMessage());
		} catch (Exception e) {
			logger.error("----【数据魔盒】----同步回调接口异常：{}----",e.getMessage());
			e.printStackTrace();
		}
		
		if(null != record) {
			String authReturnUrl = record.getAuthReturnUrl();
			String params =String.format("all_submit=%s&task_id=%s&passback_params=%s",allSubmit,taskId,reqId);
			String redirectUrl = authReturnUrl + "?" + params;
			return "redirect:" + redirectUrl;
		} else {
			return "mohe_auth_fail";
		}
	}
	
	/**
	 * 转发通知
	 * @param notifyUrl 通知地址
	 * @param notify_event 通知事件
	 * @param notify_type 通知类型
	 * @param notify_time 通知事件
	 * @param sign 签名 暂不支持
	 * @param passback_params 透传字段 reqId
	 * @param notify_data 数据
	 */
	private void forwardNotifry(String notifyUrl,String notify_event,String notify_type,String notify_time,String sign,String passback_params,String notify_data,String taskId) {
		if(StringUtils.isEmpty(notifyUrl)) return;
		Thread task = null;
		//判断notifyUrl是否是json字符串，如果是则是渠道授权（渠道授权时将orderNo和channel以json的方式存储到该字段做扩展）
		if(StringUtil.isJson(notifyUrl)) {
			//通知时间不为SUCCESS则return
			if(!"SUCCESS".equals(notify_event)) return;
			//通知类型不为report则return
			if(!"REPORT".equalsIgnoreCase(notify_type)) return;
			//创建线程
			task = new Thread(new Runnable() {
				@Override
				public void run() {
					//魔方运营商数据
					JSONObject result = JSON.parseObject(notifyUrl);
					Long orderId = result.getLong("orderId");
					String channel = result.getString("channel");
					String status = null,msg = null;
					try {
						logger.info("----[魔方]----开始保存运营商数据orderId:{},channel:{}----",orderId,channel);
						mfBussinessService.saveTaskData(taskId,Long.valueOf(orderId));
						mfBussinessService.saveTaskReport(taskId, Long.valueOf(orderId));
						status = "0";
						msg ="保存运营商数据成功~";
					} catch(BusinessException e) {
						status = "1";
						msg = e.getMessage();
						logger.error("----【魔方】保存运营商数据失败，orderId:{},channel:{},异常：{}--",orderId,channel,e.getMessage());
					} catch (Exception e) {
						status = "1";
						msg = "保存运营商数据失败~";
						logger.error("----【魔方】保存运营商数据失败，orderId:{},channel:{},异常：{}--",orderId,channel,e.getMessage());
						e.printStackTrace();
					} finally {
						JSONObject object = new JSONObject();
						object.put("orderId", orderId);
						object.put("status", status);
						object.put("msg", msg);
						String value = object.toJSONString();
						String key = String.format("system:mf:mobile:%s:order", channel);
						RedisUtils.rpush(key,value);    
						logger.info("----【磨合】拉取运营商数据后存reids Key:{}标记：{}----",key,value);
					}
				}
			});
		} else {
			task = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					Map<String,String> paramsMap = new HashMap<>();
					paramsMap.put("notify_event", notify_event);
					paramsMap.put("notify_type", notify_type);
					paramsMap.put("notify_time", notify_time);
					paramsMap.put("sign", sign);
					paramsMap.put("passback_params", passback_params);
					paramsMap.put("notify_data", notify_data);
					
					//最多通知3次~
					for(int i =0;i<3;i++) {
						//通知第三方
						logger.info("-----【魔盒异步通知】--转发通知给：{}，参数：{}",notifyUrl,paramsMap);
						String result = HttpClientHelper.post(notifyUrl, "utf-8", paramsMap);
						logger.info("-----【魔盒异步通知】--第{}次--转发通知给：{}，返回结果：{}",(i+1),notifyUrl,result);
						if(!StringUtils.isEmpty(result)) {
							try {
								JSONObject jsonResult = JSON.parseObject(result);
								if("0000".equals(jsonResult.getString("code"))) return;
							} catch (Exception e) {
								logger.error("-----【魔盒异步通知】--第{}次--转发通知给：{}，返回结果不是JSON：{}",(i+1),notifyUrl,result);
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
		
		taskExecutor.execute(task);
	}

}
