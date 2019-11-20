/**
 * 
 */
package com.waterelephant.fudata.controller;

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
import com.waterelephant.fudata.entity.DefaultFudataResponse;
import com.waterelephant.fudata.entity.FudataResponse;
import com.waterelephant.fudata.entity.RegisterPostResponse;
import com.waterelephant.fudata.entity.TaskInfoResponse;
import com.waterelephant.fudata.entity.TaskReportResponse;
import com.waterelephant.fudata.entity.vo.FudataCrawlerProgressVO;
import com.waterelephant.fudata.entity.vo.FudataCrawlerVO;
import com.waterelephant.fudata.entity.vo.FudataRawDataVO;
import com.waterelephant.fudata.entity.vo.FudataUserInfoVO;
import com.waterelephant.fudata.service.FudataService;
/**
 * fudata(富数)
 * @author dinglinhao
 *
 */
@Controller
@RequestMapping("/app/fudata")
public class FudataController {
	
	private static Logger logger = LoggerFactory.getLogger(FudataController.class);
	
	@Autowired
	private FudataService fudataService;
	
	@ResponseBody
	@RequestMapping("/register.do")
	public DefaultFudataResponse register(@RequestBody Map<String,String> params) {
		
		String method_name = getClass().getName().concat("register.do");
		
		String uid = params.get("u_id");
		
		if(StringUtils.isEmpty(uid)) {
			
			return new DefaultFudataResponse(999, "参数不能为空");
		}
		
		RegisterPostResponse response = null;

		try {
			String token = fudataService.getToken();
			
			String result = fudataService.register(token, uid);
			
			response = JSONObject.parseObject(result,RegisterPostResponse.class);
			
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/organization_list.do")
	public DefaultFudataResponse organizationList(@RequestBody Map<String,String> params) {
		
		String method_name = getClass().getName().concat("organizationList.do");
		
		String organizationType = params.get("org_type");
		
		if(StringUtils.isEmpty(organizationType)) {
			
			return new DefaultFudataResponse(999, "参数不能为空");
		}

		FudataResponse response = null;
		
		try {
			String token = fudataService.getToken();
			
			String result = fudataService.organizationList(token, organizationType);
			
			response = new FudataResponse();
			
			response.setData(JSONObject.parse(result));
			
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/entry_info.do")
	public DefaultFudataResponse entryInfo(@RequestBody Map<String,String> params) {
		
		String method_name = getClass().getName().concat("entryInfo.do");
		
		String orgId = params.get("org_id");
		
		if(StringUtils.isEmpty(orgId)) {
			
			return new DefaultFudataResponse(999, "参数不能为空");
		}
		
		FudataResponse response = null;
		
		try {
			String token = fudataService.getToken();
			
			String result = fudataService.entryInfo(token, orgId);
			
			response = JSONObject.parseObject(result,FudataResponse.class);
			
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/post_user_info.do")
	public DefaultFudataResponse postUserInfo(@RequestBody FudataUserInfoVO vo) {
		
		String method_name = getClass().getName().concat("post_user_info.do");
		
		DefaultFudataResponse response = null;
		
		try {
			String token = fudataService.getToken();
			
			String result = fudataService.postUserInfo(token, vo.getOpen_id(), vo.getUser_name(), vo.getId_card(), vo.getMobile());
			
			response =JSONObject.parseObject(result,DefaultFudataResponse.class);
			
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/crawler.do")
	public DefaultFudataResponse crawler(@RequestBody Map<String,String> params) {
		
		String method_name = getClass().getName().concat("crawler.do");
		
		TaskInfoResponse response = null;
		
		FudataCrawlerVO vo = new FudataCrawlerVO(params);

		try {
			String token = fudataService.getToken();
			
			//账户信息中包含password,则需要获取公钥和Id
			if(vo.getAccountInfo() != null && vo.getAccountInfo().containsKey("password")) {
				
				String publickey = fudataService.getPublickey(token);
				
				String publickeyId = fudataService.getPublickeyId(token);
				//添加公钥
				vo.getAccountInfo().put("publickey", publickey);
				//添加公钥Id
				vo.getAccountInfo().put("publickey_id", publickeyId);
			}
			
			String result = fudataService.crawler(token, vo.getOpen_id(), vo.getOrganization_id(), vo.getEntry_id(),vo.getVersion(), vo.getCustParams(), vo.getAccountInfo());
			
			response = JSONObject.parseObject(result,TaskInfoResponse.class);
			
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/crawler_progress.do")
	public DefaultFudataResponse crawlerProgress(@RequestBody FudataCrawlerProgressVO vo) {
		
		String method_name = getClass().getName().concat("crawler_progress.do");
		
		TaskInfoResponse response = null;
		
		try {
			
			String token = fudataService.getToken();
			
			String refresh = null;
			
			String result = fudataService.crawlerProgress(token, vo.getTask_id(), refresh, vo.getCaptcha_mobile(), vo.getCaptcha_picture());
			
			response = JSONObject.parseObject(result,TaskInfoResponse.class);
			
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/crawler_status.do")
	public DefaultFudataResponse crawlerStatus(@RequestBody Map<String,String> params) {
		
		String method_name = getClass().getName().concat("crawler_status.do");
		
		String taskId = params.get("task_id");
		
		if(StringUtils.isEmpty(taskId)) {
			
			return new DefaultFudataResponse(999, "参数不能为空");
		}
		
		TaskReportResponse response = null;
		
		try {
			String token = fudataService.getToken();
			
			String result = fudataService.crawlerStatus(token, taskId);
			
			response = JSONObject.parseObject(result,TaskReportResponse.class);
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/raw_data.do")
	public DefaultFudataResponse rawData(@RequestBody FudataRawDataVO vo) {
		
		String method_name = getClass().getName().concat("raw_data.do");
		
		FudataResponse response = null;
		
		try {
			String token = fudataService.getToken();
			
			String result = fudataService.rawData(token, vo.getOpen_id(), vo.getTask_id(),vo.getVersion());
			
			response = JSONObject.parseObject(result,FudataResponse.class);
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		
		return response;
	}

	@ResponseBody
	@RequestMapping("/get_report.do")
	public DefaultFudataResponse getReport(@RequestBody FudataRawDataVO vo) {
		
		String methodName = getClass().getName().concat("get_report.do");
		
		if(vo == null || StringUtils.isEmpty(vo.getOpen_id()) || StringUtils.isEmpty(vo.getTask_id())) {
			
			return new DefaultFudataResponse(999, "参数不能为空~");
		}
		
		FudataResponse response = null;
		
		try {
			String token = fudataService.getToken();
			
			String result = fudataService.getReport(token, vo.getOpen_id(), vo.getTask_id());
			
			response =JSONObject.parseObject(result,FudataResponse.class);
			
		} catch (Exception e) {
			
			logger.error("请求{}接口，系统异常：{}",methodName,e.getMessage());
			
			return new DefaultFudataResponse(777, "调用接口异常");
		}
		
		return response;
	}
	
//	@RequestMapping("/fudata/crawler_callback.do")
//	@ResponseBody
//	public DefaultFudataResponse crawlerCallback(@RequestBody Map<String,String> params) {
//		
//		String methodName = getClass().getName().concat("crawler_callback.do");
//		
//		String returnUrl = params.get("url");
//		
//		if(StringUtils.isEmpty(returnUrl)) {
//			
//			return new DefaultFudataResponse(999, "参数不能为空~");
//		}
//		
//		DefaultFudataResponse response = null;
//		
//		try {
//		
//			String token = fudataService.getToken();
//			
//			String result = fudataService.crawlerCallback(token,returnUrl);
//			
//			response = JSONObject.parseObject(result,FudataResponse.class);
//			
//		} catch (Exception e) {
//			
//			logger.error("请求{}接口，系统异常：{}",methodName,e.getMessage());
//			
//			return new DefaultFudataResponse(777, "调用接口异常");
//		}
//		
//		return response;
//	}

}
