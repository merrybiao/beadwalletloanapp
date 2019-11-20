package com.waterelephant.bjsms.controller;


import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.bjsms.entity.CurrencyParam;
import com.waterelephant.bjsms.entity.DefaultResponse;
import com.waterelephant.bjsms.entity.MarketParam;
import com.waterelephant.bjsms.entity.RequestData;
import com.waterelephant.bjsms.utils.SmsMD5Util;
import com.waterelephant.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("lianluMarket/message")
@Slf4j
public class LianluMarketController {
	
	@ResponseBody
	@RequestMapping(value = "/sendMarketMsg.do", method = RequestMethod.POST)
	public DefaultResponse receiveLianluMarketMsg(@RequestBody RequestData<MarketParam> data) {
		DefaultResponse response = new DefaultResponse("0000", "请求成功");
		try {
			if (CommUtils.isNull(data)) {
				response.setRequestCode("300");
				response.setRequestMsg("请求参数为空，请检查相关参数");
				return response;
			}
			Assert.hasText(data.getSecretkey(), "传入参数不完整，缺少[~secretkey~]");
			Assert.isTrue(!CommUtils.isNull(data.getParam().getContent()), "传入参数不完整，缺少[~content~]");
			Assert.hasText(data.getParam().getPhones(), "传入参数不完整，缺少[~phones~]");
			Assert.hasText(data.getParam().getSign(), "传入参数不完整，缺少[~sign~]");
			Assert.hasText(data.getParam().getBatch(), "传入参数不完整，缺少[~batch~]");
			String key = SmsMD5Util.encoding(JSON.toJSONString(data.getParam()));
			log.info("【联麓营销信息】传入的值为"+JSON.toJSONString(data)+"算出来的加密的key为" + key);
			if (!data.getSecretkey().equals(key)) {
				response.setRequestCode("400");
				response.setRequestMsg("非法访问");
				return response;
			}
			if(RedisUtils.exists("system:lianluMarketSing")){
				Set<String> keys = RedisUtils.hkeys("system:lianluMarketSing");
				Iterator<String> iterator = keys.iterator();
				boolean flag = false;
				while(iterator.hasNext()){
					String keyval = iterator.next();
					String sign =  RedisUtils.hget("system:lianluMarketSing", keyval);
					if(data.getParam().getSign().equals(sign)){
						flag = true;
						break;
					}
				}
				if(flag){				
					RedisUtils.rpush("system:lianluMarket_sendMessage", JSON.toJSONString(data.getParam()));
				} else {
					response.setRequestCode("500");
					response.setRequestMsg("短信缺少签名或签名错误");
					return response;
				}
			} else {
				response.setRequestCode("600");
				response.setRequestMsg("不存在的签名，请使用正确的签名");
				return response;
			}
		} catch (IllegalArgumentException e) {
			response.setRequestCode("700");
			response.setRequestMsg(e.getMessage());
		} catch (Exception e) {
			log.error("短信接口异常，请求失败" + e);
			e.printStackTrace();
			response.setRequestCode("999");
			response.setRequestMsg("接口异常，请求失败" + e);
		}
		return response;
	}
}
