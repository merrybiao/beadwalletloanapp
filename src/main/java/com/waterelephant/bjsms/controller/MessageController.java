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
import com.beadwallet.utils.CommUtils;
import com.waterelephant.bjsms.entity.DefaultResponse;
import com.waterelephant.bjsms.entity.RequestShopIngSmsData;
import com.waterelephant.bjsms.utils.SmsMD5Util;
import com.waterelephant.utils.RedisUtils;

@Controller
@RequestMapping({ "/beijing/message" })
public class MessageController {
	private Logger logger = Logger.getLogger(MessageController.class);

	@ResponseBody
	@RequestMapping(value = "/sedMessage.do", method = RequestMethod.POST, consumes = "application/json")
	public DefaultResponse sedMessage(@RequestBody RequestShopIngSmsData shopdata) {
		DefaultResponse response = new DefaultResponse("0000", "请求成功");
		try {
			if (CommUtils.isNull(shopdata)) {
				response.setRequestCode("300");
				response.setRequestMsg("请求参数为空，请检查相关参数");
				return response;
			}
			Assert.hasText(shopdata.getSecretkey(), "传入参数不完整，缺少[~secretkey~]");
			Assert.isTrue(!CommUtils.isNull(shopdata.getData()), "传入参数不完整，缺少[~data~]");
			Assert.hasText(shopdata.getData().getPhone(), "传入参数不完整，缺少[~phone~]");
			Assert.hasText(shopdata.getData().getMsg(), "传入参数不完整，缺少[~msg~]");
			Assert.hasText(shopdata.getData().getType(), "传入参数不完整，缺少[~type~]");
			Assert.hasText(shopdata.getData().getBusinessScenario(),"传入参数不完整，缺少[~businessScenario~]");
			String key = SmsMD5Util.encoding(JSON.toJSONString(shopdata.getData()));
			logger.info("加密的key为" + key);
			if (!shopdata.getSecretkey().equals(key)) {
				response.setRequestCode("400");
				response.setRequestMsg("非法访问");
				return response;
			}
			if(RedisUtils.exists("system:ShoppingMallSing")){
				Set<String> keys = RedisUtils.hkeys("system:ShoppingMallSing");
				Iterator<String> iterator = keys.iterator();
				boolean flag = false;
				while(iterator.hasNext()){
					//所有的key
					String keyval = iterator.next();
					String sign =  RedisUtils.hget("system:ShoppingMallSing", keyval);
					if(shopdata.getData().getMsg().contains(sign)){
						flag = true;
						break;
					}
				}
				if(flag){
					RedisUtils.rpush("system:sendMessage_shanngCheng", JSON.toJSONString(shopdata.getData()));
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
}
