package com.waterelephant.saas.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.saas.service.FqyBussinessService;
import com.waterelephant.utils.OkHttpUtil;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

@Service
public class FqyBussinessServiceImpl implements FqyBussinessService {
	
	private Logger logger = LoggerFactory.getLogger(FqyBussinessServiceImpl.class);

	@Override
	public Map<String, Object> querySaaSOperatorData(String orderNo, String partnerCode, boolean gzip) {
		//beadloanapp_fqy/fqy/getyysData.do
		String result= null;
		long star = System.currentTimeMillis();
		try {
			String url = SystemConstant.FQY_REQUEST_PATH + SystemConstant.FQY_OPERATOR_DATA_URL;
			Map<String,Object> paramsMap = new HashMap<>();
			paramsMap.put("taskID", orderNo);
			paramsMap.put("gzip", gzip);
			result = OkHttpUtil.post(url, paramsMap);
		} catch (Exception e) {
			logger.error("----根据orderNo：{}查询[分七云]运营商接口异常：{}--",orderNo,e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			logger.info("----根据orderNo：{}查询[分七云]运营商接口耗时：{}ms----",orderNo,(System.currentTimeMillis() - star));
		}
		
		if(StringUtils.isEmpty(result)) {
			logger.error("----根据orderNo：{}查询[分七云]运营商接口返回结果为：{}----",orderNo,result);
			throw new BusinessException("根据orderNo查询[分七云]运营商接口返回为空~");
		} 
		
		if(!StringUtil.isJson(result)) {
			logger.error("----根据orderNo：{}查询[分七云]运营商接口返回结果为：{}----",orderNo,result);
			throw new BusinessException("根据orderNo查询[分七云]运营商接口返回数据格式不正确~");
		}
		
		JSONObject jsonResult = JSON.parseObject(result);
		String retCode = jsonResult.getString("ret_code");
		if(!"0000".equals(retCode)) {
			String retMsg = jsonResult.getString("ret_msg");
			logger.error("----根据orderNo：{}查询[分七云]运营商接口返回结果为：{}----",orderNo,result);
			throw new BusinessException("根据orderNo查询[分七云]运营商接口返回:"+retMsg);
		}
		jsonResult.remove("ret_code");
		jsonResult.remove("ret_msg");
		return jsonResult;
	}
	
	
	public static void main(String[] args) {
		
		ResourceBundle config_fqy = ResourceBundle.getBundle("beadloanapp_fqy");
		if (config_fqy == null) {
			throw new IllegalArgumentException("[beadloanapp_fqy.properties] is not found!");
		}
		SystemConstant.FQY_REQUEST_PATH = config_fqy.getString("request_path");
		SystemConstant.FQY_OPERATOR_DATA_URL = config_fqy.getString("operator_data_url");
		
		FqyBussinessServiceImpl impl = new FqyBussinessServiceImpl();
		String orderNo = "Y201806061008";
		String partnerCode = null;
		boolean gzip = false;
		impl.querySaaSOperatorData(orderNo, partnerCode, gzip);
	}

}
