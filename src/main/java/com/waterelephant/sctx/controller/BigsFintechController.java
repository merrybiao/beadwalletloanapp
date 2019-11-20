
/**
 * @author wurenbiao
 *
 */
package com.waterelephant.sctx.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.bjsms.entity.DefaultResponse;
import com.waterelephant.entity.BwBigsFintech;
import com.waterelephant.sctx.entity.SctxResponse;
import com.waterelephant.sctx.service.IBigsFintechService;
import com.waterelephant.service.IBigsFintechSqlService;
@Controller
@RequestMapping("/bigsfintech")
public class BigsFintechController{
	
	private static Logger logger = LoggerFactory.getLogger(BigsFintechController.class);
	
	@Autowired
	private IBigsFintechService bigsfintechserviceimpl;
	
	@Autowired
	private IBigsFintechSqlService bigsfintechsqlserviceimpl;
	
	@ResponseBody
	@RequestMapping(value = "/getApply.do",method=RequestMethod.POST)
	public DefaultResponse getApply(HttpServletRequest request){
		String method_name = getClass().getName().concat("register.do");
		DefaultResponse response = null;
		String mobile = request.getParameter("mobile");
		String order_id = request.getParameter("order_id");
		String borrower_id = request.getParameter("borrower_id");
		try {
			if(CommUtils.isNull(mobile)){
				 throw new IllegalArgumentException("缺少必填字段或填入值为空");
			}
			Assert.isTrue(CommUtils.validate(3, mobile),"请填写正确的手机号码格式!!!!!");
			Assert.isTrue(NumberUtils.isNumber(mobile),"mobile的值必须为数字类型");
			if(!CommUtils.isNull(order_id)){
				Assert.isTrue(NumberUtils.isNumber(order_id),"order_id的值必须为数字类型");
			}
			if(!CommUtils.isNull(borrower_id)){
				Assert.isTrue(NumberUtils.isNumber(borrower_id),"borrower_id的值必须为数字类型");
			}
			String resp = bigsfintechserviceimpl.saveApplyInfo(mobile, order_id, borrower_id);
			
			if(!StringUtils.isEmpty(resp)){
				JSONObject res = JSONObject.parseObject(resp);
				if(res.getString("resCode").equals("0000")){
					response = new SctxResponse("0000","请求成功",res.getString("phone_apply"));
				}else{
					response = new DefaultResponse("600","请求失败");
				}
			}else{
				response = new DefaultResponse("700","请求结果为空");
			}
		}catch (IllegalArgumentException e) {
			response = new DefaultResponse("900",e.getMessage());
		} catch (Exception e) {
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			return new DefaultResponse("800", "调用接口异常");
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getApplyV1.do",method=RequestMethod.POST)
	public DefaultResponse getApplyV1(HttpServletRequest request){
		String method_name = getClass().getName().concat("getApplyV1.do");
		DefaultResponse response = null;
		String mobile = request.getParameter("mobile");
		String order_id = request.getParameter("order_id");
		String borrower_id = request.getParameter("borrower_id");
		try {
			Assert.hasText(mobile, "缺少必填字段[mobile]为空");
//			if(CommUtils.isNull(mobile)){
//				 throw new IllegalArgumentException("缺少必填字段或填入值为空");
//			}
//			Assert.isTrue(NumberUtils.isNumber(mobile),"mobile的值必须为数字类型");
//			Assert.isTrue(CommUtils.validate(3, mobile),"请填写正确的手机号码格式!!!!!");
//			if(!CommUtils.isNull(order_id)){
//				Assert.isTrue(NumberUtils.isNumber(order_id),"order_id的值必须为数字类型");
//			}
//			if(!CommUtils.isNull(borrower_id)){
//				Assert.isTrue(NumberUtils.isNumber(borrower_id),"borrower_id的值必须为数字类型");
//			}
			// TODO 从数据库获取 网贷平台注册数（1周内有效）
			BwBigsFintech fintech = bigsfintechsqlserviceimpl.selectPhoneQueryTotal(mobile, order_id, borrower_id);
			// TODO 如果数据取不到，就去调用SDK获取，存数据并返回~
			if(!CommUtils.isNull(fintech)){
				response = new SctxResponse("0000","请求成功",fintech.getPhoneApply());
			}else{
				String resp = bigsfintechserviceimpl.saveApplyInfo(mobile, order_id, borrower_id);
				if(!StringUtils.isEmpty(resp)){
					JSONObject res = JSONObject.parseObject(resp);
					if(res.getString("resCode").equals("0000")){
						response = new SctxResponse("0000","请求成功",res.getString("phone_apply"));
					}else{
						response = new DefaultResponse("600","请求失败");
					}
				}else{
					response = new DefaultResponse("700","请求结果为空");
				}
			}
		}catch (IllegalArgumentException e) {
			response = new DefaultResponse("900",e.getMessage());
		} catch (Exception e) {
			logger.error("请求{}接口，系统异常：{}",method_name,e.getMessage());
			return new DefaultResponse("800", "调用接口异常");
		}
		return response;
	}
}