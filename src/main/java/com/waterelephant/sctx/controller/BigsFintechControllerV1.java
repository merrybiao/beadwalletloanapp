package com.waterelephant.sctx.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwSctxBaseinfo;
import com.waterelephant.sctx.service.IBigsFintechServiceV1;
import com.waterelephant.utils.DefaultResponse;
import com.waterelephant.utils.ResponseData;

@Controller
@RequestMapping("/bigsfintechV1")
public class BigsFintechControllerV1 {
	
	private static Logger logger = LoggerFactory.getLogger(BigsFintechControllerV1.class);
	
	@Autowired
	private IBigsFintechServiceV1 bigsfintechservicev1impl;
	
	@ResponseBody
	@RequestMapping(value = "/getApply.do",method=RequestMethod.POST)
	public DefaultResponse getApply(HttpServletRequest request){
		String method_name = getClass().getName().concat("register.do");
		DefaultResponse response = null;
		String name = request.getParameter("name");
		String idCard = request.getParameter("idCard");
		String mobile = request.getParameter("mobile");
		String onlyId = request.getParameter("onlyId");
		try {
			Assert.hasText(onlyId, "缺少必填字段[~onlyId~]");
			if(!StringUtils.hasText(idCard)&&!StringUtils.hasText(mobile)){
				throw new IllegalArgumentException("[~idCard~]和[~mobile~]不能全为空！！！");
			}
			List<Map<String, String>>  data = bigsfintechservicev1impl.querydataBaseInfoByonlyId(onlyId);
			if(CommUtils.isNull(data)){
				BwSctxBaseinfo sctx = bigsfintechservicev1impl.queryBaseinfoByonlyId(onlyId);
			if(CommUtils.isNull(sctx)){
				Long id = bigsfintechservicev1impl.saveBaseInfo(name, mobile, idCard, onlyId);
			 if(id > 0){
				  logger.info("查询人~"+name+"~基本数据插入成功！！！");
			  }
			    List<Map<String, String>> resp = bigsfintechservicev1impl.saveApplyInfo(id,name, mobile, idCard, onlyId);
			    response = new ResponseData("0000", "请求成功", resp);
		    } else {
		    	response = new ResponseData("0000", "请求成功", "[]");
		    }
			} else {
				response = new ResponseData("0000", "请求成功", data);
			}
		}catch (IllegalArgumentException e) {
			logger.info("请求数据错误。信息为：{}",e.getMessage());
			response = new DefaultResponse("900",e.getMessage());
		} catch (Exception e) {
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			return new DefaultResponse("800", "系统开小差了，请稍后再试~~~~");
		}
		return response;
	}
}
