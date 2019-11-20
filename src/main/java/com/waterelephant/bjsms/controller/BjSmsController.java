package com.waterelephant.bjsms.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.sms.dto.MessageDto;
import com.beadwallet.utils.CommUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.waterelephant.bjsms.utils.SmsMD5Util;
import com.waterelephant.utils.RedisUtils;

@Controller
public class BjSmsController
{
    private Logger logger;
    
    public BjSmsController() {
        this.logger = Logger.getLogger(BjSmsController.class);
    }
    
    @RequestMapping({ "bj/smsSend.do" })
    @ResponseBody
    public Response<Object> smsSend(@RequestBody JSONObject jsonb) {
        final Response<Object> resp =new Response<Object>();	
        String json = null;
        if(!jsonb.isEmpty()){
        	json = JSON.toJSONString(jsonb);
        }else{
        	  resp.setRequestCode("401");
              resp.setRequestMsg("请求参数为空，请检查相关参数！");
              return resp;
        }
        try {
            if (StringUtils.isBlank(json)) {
                resp.setRequestCode("401");
                resp.setRequestMsg("请求参数为空，请检查相关参数！");
                return resp;
            }
            this.logger.info((Object)("===================短信入参：" + json));
            final Gson gson = new Gson();
            final JsonObject fromJson = (JsonObject)gson.fromJson(json,JsonObject.class);
            final String pk1 = fromJson.get("pk").getAsString();
            final String pk2 = SmsMD5Util.encoding(fromJson.get("data").toString());
            final JsonElement jsonElement = fromJson.get("data");
            final JsonObject data = jsonElement.getAsJsonObject();
            if (!pk2.equals(pk1)) {
                resp.setRequestCode("501");
                resp.setRequestMsg("非法访问！");
                return resp;
            }
            final String phone = data.get("phone").getAsString();
            final String msg = data.get("msg").getAsString();
            final String type = data.get("type").getAsString();
            final String businessScenario = data.get("businessScenario").getAsString();
            if (!msg.contains("【水象云贷】")) {
                resp.setRequestCode("403");
                resp.setRequestMsg("签名缺失，请求失败！");
                return resp;
            }
            if (CommUtils.isNull((Object)phone) || CommUtils.isNull((Object)msg) || CommUtils.isNull((Object)type) || CommUtils.isNull((Object)businessScenario)) {
                resp.setRequestCode("402");
                resp.setRequestMsg("参数缺失，请求失败！");
                return resp;
            }
            final JSONObject parseObject = JSONObject.parseObject(json);
            final JSONObject jsonObject = parseObject.getJSONObject("data");
            final MessageDto messageDto = JSON.parseObject(jsonObject.toJSONString(), MessageDto.class);
            RedisUtils.rpush("system:sendMessage", new String[] { JSON.toJSONString(messageDto) });
            resp.setRequestCode("200");
            resp.setRequestMsg("请求成功！");
            return resp;
        }
        catch (Exception e) {
            this.logger.error((Object)("北京短信接口异常，请求失败！" + e));
            e.printStackTrace();
            resp.setRequestCode("500");
            resp.setRequestMsg("接口异常，请求失败！" + e);
            return resp;
        }
    }
}
