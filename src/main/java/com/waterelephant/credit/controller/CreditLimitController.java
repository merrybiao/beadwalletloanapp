package com.waterelephant.credit.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.credit.service.CreditService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.IdcardValidator;

@RestController
@RequestMapping("/credit")
public class CreditLimitController {
	
	private Logger logger = LoggerFactory.getLogger(CreditLimitController.class);
	
	@Autowired
	private CreditService creditService;
	
	//查询已使用信用额度
	@RequestMapping("/query/used_limit.do")
	public AppResponseResult queryUsedLimit(@RequestBody JSONObject params) {
		long star = System.currentTimeMillis();
		AppResponseResult responseResult = new AppResponseResult();
		String idcard = params.getString("idcard");
		try {
			Assert.hasText(idcard, "缺少参数[idcard]字段不能为空~");
			Assert.isTrue(IdcardValidator.isValidatedAllIdcard(idcard), "[idcard]字段值不合法~");
			Double usedLimit = this.creditService.calculateUseCreditMoney(idcard);
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("used_limit", usedLimit);
			responseResult.setCode("0000");
			responseResult.setMsg("请求接口成功");
			responseResult.setResult(resultMap);
		} catch (IllegalArgumentException e) {
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求接口失败，请稍后再试~");
			logger.error("----查询用户idcard:{}信用额度失败，异常信息：{}----",idcard,e.getMessage());
			e.printStackTrace();
		} finally {
			logger.info("----查询用户idcard:{}信用额度方法耗时：{}ms----",idcard,(System.currentTimeMillis() - star));
		}
		return responseResult;
	}

}
