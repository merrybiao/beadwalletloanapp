package com.waterelephant.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.service.BwOrderHourStatisticsService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.DateUtil;

@Controller
@RequestMapping("/order/statistics")
public class OrderStatisticsController {
	
	private Logger logger = LoggerFactory.getLogger(OrderStatisticsController.class);
	
	@Autowired
	private BwOrderHourStatisticsService bwOrderHourStatisticsService;
	
	@RequestMapping("/index.do")
	public String index() {
		return "statistics/index_statistics";
	}
	
	@ResponseBody
	@RequestMapping("/hour/apply/order_count.do")
	public AppResponseResult queryHourOrderApplyCount(@RequestBody JSONObject params) {
		
		Map<String,Object> data = bwOrderHourStatisticsService.currOrderApplyCountStatistics();
		AppResponseResult responseResult = new AppResponseResult();
		responseResult.setCode("0000");
		responseResult.setMsg("请求接口成功~");
		responseResult.setResult(data);
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping("/hour/apply/order12hCount.do")
	public AppResponseResult query12HourOrderApplyCount(@RequestBody JSONObject params) {
		long start = System.currentTimeMillis();
		AppResponseResult responseResult = new AppResponseResult();

		String startTime = params.getString("startTime");
		String endTime = params.getString("endTime");
		
		try {
			
			List<String> dateTitle = new ArrayList<>();
			if(StringUtils.isEmpty(endTime)) {
				Calendar calendar = Calendar.getInstance();
				int hour = calendar.get(Calendar.HOUR);
				int min = calendar.get(Calendar.MINUTE);
				if(min>=0 && min <20)
					min = 0;
				else if(min >=20 && min<40)
				    min = 20;
				else
					min = 40;
				calendar.set(Calendar.MINUTE,min);//设置秒为0,20,40
				calendar.set(Calendar.SECOND, 0);//秒设置为0
				
				Date endDateTime = calendar.getTime();//结束时间
				calendar.set(Calendar.HOUR, hour-3);//当前时间减去3小时
				Date startDateTime = calendar.getTime();//开始时间
				
				startTime = DateUtil.getDateString(startDateTime, DateUtil.yyyy_MM_dd_HHmmss);
				
				endTime = DateUtil.getDateString(endDateTime, DateUtil.yyyy_MM_dd_HHmmss);
				
				dateTitle = getDateTitle(startDateTime, endDateTime);
			}
			
			Map<String, Object> data = bwOrderHourStatisticsService.currOrderApplyCountStatistics(startTime, endTime);
			
			data.put("date_title", dateTitle);
			
			responseResult.setCode("0000");
			responseResult.setMsg("请求接口成功~");
			responseResult.setResult(data);
		} catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求接口失败，请稍后再试~");
			logger.error("---工单统计--请求工单申请数接口异常：{}--" + e.getMessage());
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("---请求{}接口耗时：{}ms--","/hour/apply/order12hCount.do",(end-start));
		}
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping("/hour/audit/order_count.do")
	public AppResponseResult queryHourOrderAuditCount(@RequestBody JSONObject params) {
		
		Map<String,Object> data = bwOrderHourStatisticsService.currOrderAuditCountStatistics();
		AppResponseResult responseResult = new AppResponseResult();
		responseResult.setCode("0000");
		responseResult.setMsg("请求接口成功~");
		responseResult.setResult(data);
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping("/hour/audit/order12hCount.do")
	public AppResponseResult query12HourOrderAuditCount(@RequestBody JSONObject params) {
		long start = System.currentTimeMillis();
		AppResponseResult responseResult = new AppResponseResult();

		String startTime = params.getString("startTime");
		String endTime = params.getString("endTime");
		
		try {
			
			List<String> dateTitle = new ArrayList<>();
			if(StringUtils.isEmpty(endTime)) {
				Calendar calendar = Calendar.getInstance();
				int hour = calendar.get(Calendar.HOUR);
				int min = calendar.get(Calendar.MINUTE);
				if(min>=0 && min <20)
					min = 0;
				else if(min >=20 && min<40)
				    min = 20;
				else
					min = 40;
				calendar.set(Calendar.MINUTE,min);//设置秒为0,20,40
				calendar.set(Calendar.SECOND, 0);//秒设置为0
				
				Date endDateTime = calendar.getTime();//结束时间
				calendar.set(Calendar.HOUR, hour-3);//当前时间减去3小时
				Date startDateTime = calendar.getTime();//开始时间
				
				startTime = DateUtil.getDateString(startDateTime, DateUtil.yyyy_MM_dd_HHmmss);
				
				endTime = DateUtil.getDateString(endDateTime, DateUtil.yyyy_MM_dd_HHmmss);
				
				dateTitle = getDateTitle(startDateTime, endDateTime);
			}
			
			Map<String, Object> data = bwOrderHourStatisticsService.currOrderAuditCountStatistics(startTime, endTime);
			
			data.put("date_title", dateTitle);
			
			responseResult.setCode("0000");
			responseResult.setMsg("请求接口成功~");
			responseResult.setResult(data);
		} catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求接口失败，请稍后再试~");
			logger.error("---工单统计--请求工单进件数接口异常：{}--" + e.getMessage());
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("---请求{}接口耗时：{}ms--","/hour/audit/order12hCount.do",(end-start));
		}
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping("/hour/loans/order_count.do")
	public AppResponseResult queryHourOrderLoansCount(@RequestBody JSONObject params) {
		
		Map<String,Object> data = bwOrderHourStatisticsService.currOrderLoansCountStatistics();
		AppResponseResult responseResult = new AppResponseResult();
		responseResult.setCode("0000");
		responseResult.setMsg("请求接口成功~");
		responseResult.setResult(data);
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping("/hour/loans/order12hCount.do")
	public AppResponseResult query12HourOrderLoansCount(@RequestBody JSONObject params) {
		long start = System.currentTimeMillis();
		AppResponseResult responseResult = new AppResponseResult();

		String startTime = params.getString("startTime");
		String endTime = params.getString("endTime");
		
		try {
			List<String> dateTitle = new ArrayList<>();
			if(StringUtils.isEmpty(endTime)) {
				Calendar calendar = Calendar.getInstance();
				int hour = calendar.get(Calendar.HOUR);
				int min = calendar.get(Calendar.MINUTE);
				if(min>=0 && min <20)
					min = 0;
				else if(min >=20 && min<40)
				    min = 20;
				else
					min = 40;
				calendar.set(Calendar.MINUTE,min);//设置秒为0,20,40
				calendar.set(Calendar.SECOND, 0);//秒设置为0
				
				Date endDateTime = calendar.getTime();//结束时间
				calendar.set(Calendar.HOUR, hour-3);//当前时间减去3小时
				Date startDateTime = calendar.getTime();//开始时间
				
				startTime = DateUtil.getDateString(startDateTime, DateUtil.yyyy_MM_dd_HHmmss);
				
				endTime = DateUtil.getDateString(endDateTime, DateUtil.yyyy_MM_dd_HHmmss);
				
				dateTitle = getDateTitle(startDateTime, endDateTime);
			}
			
			Map<String, Object> data = bwOrderHourStatisticsService.currOrderLoansCountStatistics(startTime, endTime);
			
			data.put("date_title", dateTitle);
			
			responseResult.setCode("0000");
			responseResult.setMsg("请求接口成功~");
			responseResult.setResult(data);
		} catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求接口失败，请稍后再试~");
			logger.error("---工单统计--请求工单放款数接口异常：{}--" + e.getMessage());
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			logger.info("---请求{}接口耗时：{}ms--","/hour/loans/order12hCount.do",(end-start));
		}
		return responseResult;
	}
	
	public List<String> getDateTitle(Date startDateTime,Date endDateTime){
		List<String> list = new ArrayList<>();
		do {
			Calendar c = Calendar.getInstance();
			c.setTime(endDateTime);
			System.out.println();
			list.add(DateUtil.getDateString(endDateTime, "HH:mm"));
			c.add(Calendar.MINUTE, -20);
			endDateTime = c.getTime();
		}while(endDateTime.getTime()>=startDateTime.getTime());
		return list;
	}

}
