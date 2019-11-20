package com.waterelephant.drainage.util.fqgj.jkzj;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * 基础请求参数
 */
public class RequestParams {


    protected Map<String,String> paramMap=new TreeMap<String, String>();

    public RequestParams(){

    }

    public RequestParams(String appId) {
        paramMap.put("sign_type","RSA");
        paramMap.put("version","1.0");
        paramMap.put("format","json");
        paramMap.put("timestamp",String.valueOf(System.currentTimeMillis()));
        paramMap.put("app_id",appId);
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public Map<String, String> getParams() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public void put(String key,String value){
        paramMap.put(key,value);
    }

    public void remove(String key){
        paramMap.remove(key);
    }
}
