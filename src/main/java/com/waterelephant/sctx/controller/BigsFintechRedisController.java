package com.waterelephant.sctx.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.bjsms.entity.DefaultResponse;
import com.waterelephant.utils.RedisUtils;

@Controller
@RequestMapping("/bigsfintech/redis/")
public class BigsFintechRedisController {
	
	private static Logger logger = LoggerFactory.getLogger(BigsFintechRedisController.class);	
	
	@ResponseBody
	@RequestMapping(value = "getRedisApply.do",method=RequestMethod.POST)
	public DefaultResponse getRedisApply(HttpServletRequest request){
		String method_name = getClass().getName().concat("getRedisApply.do");
		DefaultResponse response = null;
		String mobile = request.getParameter("mobile");
		String order_id = request.getParameter("order_id");
		String borrower_id = request.getParameter("borrower_id");
		logger.info("传入的mobile值为:"+mobile);
		try {  
			if(CommUtils.isNull(mobile)){
				 throw new IllegalArgumentException("缺少必填字段或填入值为空");
			}
			if(!CommUtils.isNull(order_id)){
				Assert.isTrue(NumberUtils.isNumber(order_id),"order_id的值必须为数字类型");
			}
			if(!CommUtils.isNull(borrower_id)){
				Assert.isTrue(NumberUtils.isNumber(borrower_id),"borrower_id的值必须为数字类型");
			}
			Assert.isTrue(NumberUtils.isNumber(mobile),"mobile的值必须为数字类型");
			Assert.isTrue(CommUtils.validate(3, mobile),"请填写正确的手机号码格式!!!!!");
			JSONObject json = new JSONObject();
			json.put("mobile", mobile);
			json.put("order_id", order_id);
			json.put("borrower_id", borrower_id);
			String obj = JSON.toJSONString(json);
			RedisUtils.rpush("phone_apply", obj);
			response = new DefaultResponse("0000","手机号存储成功");
		}catch (IllegalArgumentException e) {
			response = new DefaultResponse("900",e.getMessage());
		} catch (Exception e) {
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			return new DefaultResponse("800", "调用接口异常");
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "getRedisApplyV1.do",method=RequestMethod.POST)
	public DefaultResponse getRedisApplyV1(HttpServletRequest request){
		String method_name = getClass().getName().concat("getRedisApplyV1.do");
		DefaultResponse response = null;
		String data = request.getParameter("data");
		logger.info("传入的data值为:"+data);
		try {  
			if(CommUtils.isNull(data)){
				 throw new IllegalArgumentException("缺少必填字段或填入值为空");
			}
			String[] arrayData = data.split(";");
			JSONObject json = null;
			String obj = null;
			List<String> list = new ArrayList<String>();
			for(int i=0;i<arrayData.length;i++){
				String str = arrayData[i];
				String[] dataEver = str.split(",");
				json = new JSONObject();
				json.put("mobile", dataEver[0]);
				json.put("order_id", dataEver[1]);
				json.put("borrower_id", dataEver[2]);
				obj = JSON.toJSONString(json);
				list.add(obj);
			}
			String[] keys = list.toArray(new String[list.size()]);
			RedisUtils.rpush("phone_apply", keys);
			response = new DefaultResponse("0000","手机号存储成功");
		}catch (IllegalArgumentException e) {
			response = new DefaultResponse("900",e.getMessage());
		} catch (Exception e) {
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			return new DefaultResponse("800", "调用接口异常");
		}
		return response;
	}

}
