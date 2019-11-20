package com.waterelephant.fulinfk.controller;

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
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.fulinfk.service.IFulinfkAppService;

@Controller
@RequestMapping("/fulinfk")
public class FulinfkController {
	
	private static Logger logger = LoggerFactory.getLogger(FulinfkController.class);

	@Autowired
	private IFulinfkAppService ifulinfkappservice;

	@ResponseBody
	@RequestMapping(value = "/getReport.do",method=RequestMethod.POST,consumes = "application/json")
	public JSONObject getReport(@RequestBody String reqStr) {
		JSONObject resp = null;
		Long startTime = System.currentTimeMillis();
		String name = null;
		String mobile = null;
		String idCardNo = null;
		try {
			JSONObject json = JSON.parseObject(reqStr);
			Assert.isTrue(json.containsKey("name"), "传入参数不完整，缺少name");
			Assert.isTrue(json.containsKey("mobile"), "传入参数不完整，缺少mobile");
			Assert.isTrue(json.containsKey("idCardNo"), "传入参数不完整，缺少idCardNo");
			 name = json.getString("name");
			 mobile = json.getString("mobile");
			 idCardNo = json.getString("idCardNo");
			if (CommUtils.isNull(name) || CommUtils.isNull(mobile) || CommUtils.isNull(idCardNo)) {
				resp = new JSONObject();
				resp.put("code", "101");
				resp.put("msg", "必填字段的值为空");
			} else {
				Map<String, String> mapType = JSON.parseObject(json.toString(), Map.class);
				logger.info("获取的请求数据为:"+mapType);
				String scoreString = ifulinfkappservice.saveFullinkReport(mapType);
				if (CommUtils.isNull(scoreString)) {
					resp = new JSONObject();
					resp.put("code", "900");
					resp.put("msg", "请求失败");
				} else {
					resp = new JSONObject();
					resp.put("code", "0000");
					resp.put("msg", "请求成功");
					resp.put("score", scoreString);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			resp = new JSONObject();
			resp.put("code", "900");
			resp.put("msg", "获取接口调用发生异常,必须传入json数据");
		} catch (Exception e) {
			e.printStackTrace();
			resp = new JSONObject();
			resp.put("code", "900");
			resp.put("msg", "获取接口调用发生异常" + e.getMessage());
		}finally{
			Long endTime = System.currentTimeMillis();
			logger.info("用户姓名:{},身份证号:{},电话号码{},返回值为：{}，请求的总时间为：{}ms,",name,idCardNo,mobile,resp,endTime-startTime);
		}
		return resp;
	}
}
