package com.waterelephant.bjsms.controller;

import java.io.IOException;
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
import com.beadwallet.utils.CommUtils;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.bjsms.entity.DefaultResponse;
import com.waterelephant.bjsms.utils.SmsMD5Util;
import com.waterelephant.utils.RedisUtils;

@RequestMapping("dhst/message")
@Controller
public class DhstMesController {

private Logger logger = Logger.getLogger(DhstMesController.class);
	
	@ResponseBody
	@RequestMapping( "/sendMessage.do")
	public DefaultResponse sedSxypMessage(@RequestBody JSONObject data) throws IOException {
		
		DefaultResponse response = new DefaultResponse("0000", "请求成功");
		try {
			if (CommUtils.isNull(data)) {
				response.setRequestCode("300");
				response.setRequestMsg("请求参数为空，请检查相关参数");
				return response;
			}
			Assert.hasText(data.getString("secretkey"), "传入参数不完整，缺少[~secretkey~]");
			Assert.hasText(data.getString("channel"), "传入参数不完整，缺少[~channel~]");
			Assert.isTrue(!CommUtils.isNull(data.getString("data")), "传入参数不完整，缺少[~data~]");
			JSONObject content = data.getJSONObject("data");
			Assert.hasText(content.getString("sign"), "传入参数不完整，缺少[~sign~]");
			Assert.hasText(content.getString("content"), "传入参数不完整，缺少[~content~]");
			Assert.hasText(content.getString("phones"), "传入参数不完整，缺少[~phones~]");
			String key = SmsMD5Util.encoding(content.toString());
			logger.info("【大汉三通】加密的key为" + key);
			if (!data.getString("secretkey").equals(key)) {
				response.setRequestCode("400");
				response.setRequestMsg("非法访问");
				return response;
			}
			if(RedisUtils.exists("system:dhsnCallSing")){
				Set<String> keys = RedisUtils.hkeys("system:dhsnCallSing");
				Iterator<String> iterator = keys.iterator();
				boolean flag = false;
				while(iterator.hasNext()){
					//所有的key
					String keyval = iterator.next();
					String sign =  RedisUtils.hget("system:dhsnCallSing", keyval);
					if(content.getString("sign").contains(sign)){
						flag = true;
						break;
					}
				}
				if(flag){
					//增加大汉三通还是亿美方式判断
					if(data.getString("channel").equals("001")){
						//大汉三通
						RedisUtils.rpush("system:sendDhstNoticeSms", JSON.toJSONString(content));
					} else if(data.getString("channel").equals("002")){
						//亿美
						MessageDto dto = new MessageDto();
						String msg = content.getString("sign")+content.getString("content");
						dto.setMsg(msg);
						dto.setPhone(content.getString("phones"));
						dto.setType("1");
						dto.setBusinessScenario("1");
 						RedisUtils.rpush("system:sendMessage", JSON.toJSONString(dto));
					} else {
						response.setRequestCode("500");
						response.setRequestMsg("不存在的channel");
					}
				} else {
					response.setRequestCode("600");
					response.setRequestMsg("短信缺少签名或签名错误");
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
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendMessage2.do", method = RequestMethod.POST)
	public String kdfmgk(){
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendMessage3.do", method = RequestMethod.POST,consumes = "application/json")
	public String kdfmg4pok(@RequestBody JSONObject data){
		return data.toJSONString();
	}
}
