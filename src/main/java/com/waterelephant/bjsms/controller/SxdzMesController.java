package com.waterelephant.bjsms.controller;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.sms.dto.MessageDto;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.bjsms.entity.DefaultResponse;
import com.waterelephant.bjsms.utils.SmsMD5Util;
import com.waterelephant.utils.RedisUtils;

@Controller
@RequestMapping("sxdz/message")
public class SxdzMesController {
		
	private Logger logger = Logger.getLogger(SxdzMesController.class);

	@ResponseBody
	@RequestMapping(value = "/sedMessage.do", method = RequestMethod.POST, consumes = "application/json")
	public DefaultResponse sedMessage(@RequestBody JSONObject json) {
		DefaultResponse response = new DefaultResponse("0000", "请求成功");
		JSONObject jsonData = null;
		try {
			if (json.isEmpty()) {
				response.setRequestCode("300");
				response.setRequestMsg("请求参数为空，请检查相关参数");
				return response;
			}
			Assert.isTrue(json.containsKey("secretkey"), "传入参数不完整，缺少secretkey");
			Assert.isTrue(json.containsKey("data"), "传入参数不完整，缺少data");
			jsonData = json.getJSONObject("data");
			Assert.isTrue(jsonData.containsKey("phone"), "传入参数不完整，缺少phone");
			Assert.isTrue(jsonData.containsKey("msg"), "传入参数不完整，缺少msg");
			Assert.isTrue(jsonData.containsKey("type"), "传入参数不完整，缺少type");
			Assert.isTrue(jsonData.containsKey("businessScenario"),
					"传入参数不完整，缺少phobusinessScenarione");
			String secretkey = json.getString("secretkey");
			String key = SmsMD5Util.encoding(json.getString("data"));
			logger.info("加密的key为" + key);
			if (!secretkey.equals(key)) {
				response.setRequestCode("400");
				response.setRequestMsg("非法访问");
				return response;
			}
			String phone = jsonData.getString("phone");
			String msg = jsonData.getString("msg");
			String type = jsonData.getString("type");
			String businessScenario = jsonData.getString("businessScenario");
			if ((CommUtils.isNull(phone)) || (CommUtils.isNull(type))
					|| (CommUtils.isNull(businessScenario))) {
				response.setRequestCode("500");
				response.setRequestMsg("参数缺失，请求失败");
				return response;
			}
			if(RedisUtils.exists("system:sign")){
				Set<String> keys = RedisUtils.hkeys("system:sign");
				Iterator<String> iterator = keys.iterator();
				boolean flag = false;
				while(iterator.hasNext()){
					//所有的key
					String keyval = iterator.next();
					String sign =  RedisUtils.hget("system:sign", keyval);
					if(msg.contains(sign)){
						flag = true;
						break;
					}
				}
				if(flag){
					MessageDto messageDto = JSONObject.parseObject(jsonData.toString(), MessageDto.class);
					RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
				} else {
					response.setRequestCode("600");
					response.setRequestMsg("短信缺少签名或签名错误");
					return response;
				}
			}
		} catch (IllegalArgumentException e) {
			response.setRequestCode("700");
			response.setRequestMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("短信接口异常，请求失败" + e);
			e.printStackTrace();
			response.setRequestCode("999");
			response.setRequestMsg("接口异常，请求失败" + e);
			return response;
		}
		return response;
	}
}



