package com.waterelephant.zrobot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.entity.BwZrobotFinanceBehavior;
import com.waterelephant.entity.BwZrobotPanguScore;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.service.BwZrobotFinanceBehaviorService;
import com.waterelephant.service.BwZrobotPanguScoreService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.zrobot.dto.FinanceBehaviorDto;
import com.waterelephant.zrobot.dto.ZrobotPanguScoreDto;
import com.waterelephant.zrobot.service.ZrobotBusiService;

/**
 * ZrobotAuthController.java
 * zrobot 平台金融行为标签授权查询
 * @author dinglinhao
 *
 */
@Controller
@RequestMapping("/zrobot")
public class ZrobotAuthController {
	
	private Logger logger = LoggerFactory.getLogger(ZrobotAuthController.class);
	
	@Autowired
	private ZrobotBusiService zrobotBusiService;
	
	@Autowired
	private BwZrobotFinanceBehaviorService bwZrobotFinanceBehaviorService;
	
	@Autowired
	private BwZrobotPanguScoreService bwZrobotPanguScoreService;
	
	@ResponseBody
	@RequestMapping(value="/auth.do",method=RequestMethod.POST)
	public AppResponseResult auth(HttpServletRequest request,HttpServletResponse response) {
		AppResponseResult responseResult = new AppResponseResult();
		
		String name = request.getParameter("name");
		String idCardNum = request.getParameter("id_card");
		String cellPhoneNum = request.getParameter("phone");
		
		logger.info("请求{}方法，入参参数name:{},id_card:{},phone:{}","/zrobot/auth.do",name,idCardNum,cellPhoneNum);
		try {
			Assert.hasText(name, "缺少参数,[name]字段为空~");
			Assert.hasText(idCardNum, "缺少参数,[id_card]字段为空~");
			Assert.hasText(cellPhoneNum, "缺少参数,[phone]字段为空~");
			BwZrobotFinanceBehavior record = bwZrobotFinanceBehaviorService.queryFinanceBehavior(name, idCardNum, cellPhoneNum);
			Map<String,Object> resultMap = null;
			if(null == record) {
				String token = zrobotBusiService.getToken();
				resultMap = zrobotBusiService.queryfb(token,name,idCardNum,cellPhoneNum);
				zrobotBusiService.savefb(name,idCardNum,cellPhoneNum,resultMap);
			}else {
				resultMap = new HashMap<>();
				resultMap.put("transaction_id", record.getTransactionId());
				FinanceBehaviorDto dto = new FinanceBehaviorDto();
				BeanUtils.copyProperties(dto,record);
				resultMap.put("data", JSON.toJSON(dto));
			}
			responseResult.setCode("0000");
			responseResult.setMsg("success~");
			responseResult.setResult(resultMap);
		} catch (IllegalArgumentException | BusinessException e) {
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("请求/zrobot/auth.do方法异常，参数name:{},idcard:{},phone:{},异常信息：{}",name,idCardNum,cellPhoneNum,e.getMessage());
			e.printStackTrace();
			responseResult.setCode("9999");
			responseResult.setMsg("授权失败，请稍后再试~");
		}
		
		return responseResult;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/query/pangu.do",method=RequestMethod.POST)
	public AppResponseResult queryPangu(HttpServletRequest request,HttpServletResponse response) {
		AppResponseResult responseResult = new AppResponseResult();
		
		String name = request.getParameter("name");
		String idCardNum = request.getParameter("id_card");
		String cellPhoneNum = request.getParameter("phone");
		String bankCardNum = request.getParameter("bank_card");
		logger.info("请求{}方法，入参name:{},id_card:{},phone:{},bank_card:{}","/zrobot/query/pangu.do",name,idCardNum,cellPhoneNum,bankCardNum);
		try {
			Assert.hasText(name, "缺少参数,[name]字段为空~");
			Assert.hasText(idCardNum, "缺少参数,[id_card]字段为空~");
			Assert.hasText(cellPhoneNum, "缺少参数,[phone]字段为空~");
			Assert.hasText(bankCardNum, "缺少参数,[bank_card]字段为空~");
			BwZrobotPanguScore panguRecord = bwZrobotPanguScoreService.queryPanguRecord(name, idCardNum, cellPhoneNum, bankCardNum);
			ZrobotPanguScoreDto  dto = null;
			if(null == panguRecord) {
				Long id = zrobotBusiService.savePangu(name, idCardNum, cellPhoneNum, bankCardNum);
				dto = zrobotBusiService.queryPangu(name, idCardNum, cellPhoneNum, bankCardNum);
				zrobotBusiService.updatePangu(id, dto);
			}else {
				dto = new ZrobotPanguScoreDto();
				BeanUtils.copyProperties(dto,panguRecord);
			}
			responseResult.setCode("0000");
			responseResult.setMsg("success~");
			responseResult.setResult(dto);
		} catch (IllegalArgumentException | BusinessException e) {
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("请求/zrobot/query/pangu.do方法异常，参数name:{},idcard:{},phone:{},异常信息：{}",name,idCardNum,cellPhoneNum,e.getMessage());
			e.printStackTrace();
			responseResult.setCode("9999");
			responseResult.setMsg("授权失败，请稍后再试~");
		}
		
		return responseResult;
	}

}
