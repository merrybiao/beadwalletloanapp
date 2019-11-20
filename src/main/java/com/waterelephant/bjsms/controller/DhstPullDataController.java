package com.waterelephant.bjsms.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.bjsms.entity.DhstResponse;
import com.waterelephant.bjsms.entity.PushResponse;
import com.waterelephant.bjsms.service.DhstPullDataSmsService;


@Controller
@RequestMapping("daHanSanTong")
public class DhstPullDataController {
	
	private Logger logger = LoggerFactory.getLogger(DhstPullDataController.class);
	
	@Autowired
	private DhstPullDataSmsService dhstPullDataSmsService;
	
	@ResponseBody
	@RequestMapping(value = "/getDhstSmsReport.do",method = RequestMethod.POST)
	public PushResponse getDhstSmsReport(HttpServletRequest request){
		Long startTime = System.currentTimeMillis();
		PushResponse resonse = new PushResponse("fail");
		String reports = null;
		try {
			reports = request.getParameter("report");
			logger.info("【大汉三通】推送数据为》》》》{}",reports);
			
			if(CommUtils.isNull(reports)) return resonse;
			
			DhstResponse response = JSONObject.parseObject(reports, DhstResponse.class);
			if(response.getResult().equals("0")){
				boolean isSuccess = dhstPullDataSmsService.saveMongoSms(response.getReports());
				if(isSuccess) resonse.setStatus("success");;
			}
		} catch (Exception e) {
			logger.error("【大汉三通】获取短信推送数据异常，信息为：{}",e.getMessage());
			e.printStackTrace();
		}finally{
			Long endTime = System.currentTimeMillis();
			logger.info("【大汉三通】推送数据{}存储数据库总耗时{}ms",reports,endTime - startTime);
		}
		return resonse;
	};
}
