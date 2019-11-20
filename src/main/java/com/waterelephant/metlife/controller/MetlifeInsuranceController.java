package com.waterelephant.metlife.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMetlifeInsuranceApplyRecord;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.metlife.comm.ProductType;
import com.waterelephant.metlife.dto.MetlifeInsuranceOrderDto;
import com.waterelephant.metlife.service.MetlifeBusiService;
import com.waterelephant.metlife.vo.MetLifeInsuredVo;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.DateUtil;

@Controller
@RequestMapping("/metlife/insurance")
public class MetlifeInsuranceController {
	
	private Logger logger = LoggerFactory.getLogger(MetlifeInsuranceController.class);
	
	@Autowired
	private MetlifeBusiService metlifeBusiService;
	
	@ResponseBody
	@RequestMapping("/apply.do")
	public AppResponseResult apply(@RequestBody MetLifeInsuredVo vo) {
		AppResponseResult responseResult = new AppResponseResult();
		
		try {
			Assert.notNull(vo, "JSON字符串转JavaBean失败~");
			
			Assert.hasText(vo.getOrderNo(),"缺失参数，字段[orderNo]不能为空~");
//			Assert.notNull(vo.getLoanDays(), "缺失参数，字段[loanDays]不能为空~");
			if(null == vo.getLoanDays() && null == vo.getLoanMonths()) {
				throw new IllegalArgumentException("缺失参数，字段[loanDays]或[loanmonths]不能为空~") ;
			}
			Assert.hasText(vo.getLoanDate(),"缺失参数，字段[loanDate]不能为空~");
			Assert.notNull(vo.getLoanAmount(),"缺失参数，字段[loanAmount]不能为空~");
			
			String loanDate = DateUtil.getDateString(vo.getLoanDate(), DateUtil.yyyy_MM_dd);
			Assert.hasText(loanDate, "参数[loanDate]格式不正确(yyyy-MM-dd)~");
			
			Assert.isTrue(vo.getLoanAmount().doubleValue()>0, "参数[loanAmount]值不是Double类型~");
			
			Assert.hasText(vo.getInsuredName(),"缺失参数，字段[insuredName]不能为空~");
			Assert.hasText(vo.getInsuredIdNo(),"缺失参数，字段[insuredIdNo]不能为空~");
			Assert.notNull(vo.getInsuredIdType(),"缺失参数，字段[insuredIdType]不能为空~");
			Assert.notNull(vo.getInsuredGender(),"缺失参数，字段[insuredGender]不能为空~");
			Assert.hasText(vo.getInsuredBirthday(),"缺失参数，字段[insuredBirthday]不能为空~");
			Assert.hasText(vo.getInsuredMobile(),"缺失参数，字段[insuredMobile]不能为空~");
			Assert.hasText(vo.getProductNo(),"缺失参数，字段[productNo]不能为空~");
			Assert.notNull(ProductType.get(vo.getProductNo()), "字段[productNo]无效或不合法~");
			
			boolean result = metlifeBusiService.checkApplyInsurance(vo);
			if(result) {
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功，请稍后~");
			}
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage());
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch(BusinessException e) {
			e.printStackTrace();
			responseResult.setCode("700");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用大都会保险-申请接口异常，异常信息：{}",e.getMessage());
			responseResult.setCode("9999");
			responseResult.setMsg("申请失败，请稍后再试~");
		}
		
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping("/cancel.do")
	public AppResponseResult cancel(@RequestBody JSONObject jsonParams) {
		AppResponseResult responseResult = new AppResponseResult();
		
		String productNo = jsonParams.getString("productNo");
		String orderNo = jsonParams.getString("orderNo");
		String remark = jsonParams.getString("desc");
		try {
			
			Assert.hasText(productNo, "缺失参数，字段[productNo]不能为空~");
			Assert.hasText(orderNo,"缺失参数,字段[orderNo]不能为空~");
			Assert.hasText(remark,"参数缺失，字段[desc]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			
			boolean flag  = metlifeBusiService.checkCancelInsurance(productNo,orderNo,remark);
			
			if(flag) {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功，已取消~");
			}else {
				responseResult = new AppResponseResult();
				responseResult.setCode("9999");
				responseResult.setMsg("操作失败~请查询申请状态信息~");
			}
		} catch(IllegalArgumentException e) {
			responseResult = new AppResponseResult();
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据orderNo:{}取消保险申请异常，异常信息：{}",orderNo,e.getMessage());
			responseResult = new AppResponseResult();
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		}
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping("/applyState.do")
	public AppResponseResult queryApplyState(@RequestBody JSONObject jsonParams) {
		AppResponseResult responseResult = null;
		
		String productNo = jsonParams.getString("productNo");
		String orderNo = jsonParams.getString("orderNo");
		
		try {
			
			Assert.hasText(productNo, "缺失参数，字段[productNo]不能为空~");
			Assert.hasText(orderNo,"缺失参数,字段[orderNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			BwMetlifeInsuranceApplyRecord  record = metlifeBusiService.queryApplyState(orderNo,productNo);
			if(null == record) {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功，该记录不存在~");
				responseResult.setResult(new HashMap<>());
			}else {
				
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功~");
				
				Map<String,Object> resultMap = new HashMap<>();
				resultMap.put("orderNo", record.getOrderNo());
				resultMap.put("list", metlifeBusiService.queryOrderApplyState(orderNo));
				responseResult.setResult(resultMap);
			}
		} catch(IllegalArgumentException e) {
			responseResult = new AppResponseResult();
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据orderNo:{}查询保险申请状态异常，异常信息：{}",orderNo,e.getMessage());
			responseResult = new AppResponseResult();
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		}
		return responseResult;
	}
	
	//根据工单号查询工单详情
	@ResponseBody
	@RequestMapping("/detail.do")
	public AppResponseResult insureOrder(@RequestBody JSONObject jsonParams) {
		AppResponseResult responseResult = null;
		String productNo = jsonParams.getString("productNo");
		String orderNo = jsonParams.getString("orderNo");
		
		try {
			
			Assert.hasText(productNo, "缺失参数，字段[productNo]不能为空~");
			Assert.hasText(orderNo,"缺失参数,字段[orderNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			List<MetlifeInsuranceOrderDto> orderList = metlifeBusiService.queryInsuranceOrderList(orderNo,productNo);
			if(null == orderList || orderList.isEmpty()) {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功，该记录不存在~");
				responseResult.setResult(new HashMap<>());
			}else {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功~");
				HashMap<String,Object> resultMap = new HashMap<>();
				resultMap.put("list", orderList);
				responseResult.setResult(resultMap);
				responseResult.setResult(resultMap);
			}
		} catch(IllegalArgumentException e) {
			responseResult = new AppResponseResult();
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("---------根据工单号查询工单详情，参数productNo:{},orderNo:{}查询保险详情异常，异常信息：{}",productNo,orderNo,e.getMessage());
			responseResult = new AppResponseResult();
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		}
		return responseResult;
	}
	
	//根据日期查询保单号
	@ResponseBody
	@RequestMapping("/getPolicyNo.do")
	public AppResponseResult queryPolicyNoByTrimDate(@RequestBody JSONObject jsonParams) {
		
		AppResponseResult responseResult = new AppResponseResult();
		String productNo = jsonParams.getString("productNo");
		String trimDate = jsonParams.getString("trimDate");
		try {
			Assert.hasText(productNo, "缺失参数，字段[productNo]不能为空~");
			Assert.hasText(trimDate,"缺失参数,字段[trimDate]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			
			List<String> list = this.metlifeBusiService.queryPolicyNoByTrimDate(productNo,trimDate);
			if(null == list) {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功，该记录不存在或保单号还未录入~");
				responseResult.setResult(new HashMap<>());
			}else {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功~");
				HashMap<String,Object> resultMap = new HashMap<>();
				resultMap.put("policyNo", list);
				responseResult.setResult(resultMap);
			}
		} catch(IllegalArgumentException e) {
			responseResult = new AppResponseResult();
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("---------根据日查询保单号，参数productNo:{}，trimDate:{}查询保险保单编号异常，异常信息：{}",productNo,trimDate,e.getMessage());
			responseResult = new AppResponseResult();
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		}
		return responseResult;
	}
	
	//根据工单查询保单号
	@ResponseBody
	@RequestMapping("/queryPolicyNo.do")
	public AppResponseResult queryPolicyNoByOrderNo(@RequestBody JSONObject jsonParams) {
		
		AppResponseResult responseResult = new AppResponseResult();
		String orderNo = jsonParams.getString("orderNo");
		String productNo = jsonParams.getString("productNo");
		try {
			Assert.hasText(productNo, "缺失参数，字段[productNo]不能为空~");
			Assert.hasText(orderNo,"缺失参数,字段[trimDate]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			
			List<String> list = this.metlifeBusiService.queryPolicyNoByOrderNo(productNo,orderNo);
			if(null == list) {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功，该记录不存在或保单号还未录入~");
				responseResult.setResult(new HashMap<>());
			}else {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功~");
				HashMap<String,Object> resultMap = new HashMap<>();
				resultMap.put("policyNo", list);
				responseResult.setResult(resultMap);
			}
		} catch(IllegalArgumentException e) {
			responseResult = new AppResponseResult();
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-------根据工单号查询保单号，参数productNo:{}，orderNo:{}查询保险保单编号异常，异常信息：{}",productNo,orderNo,e.getMessage());
			responseResult = new AppResponseResult();
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		}
		return responseResult;
	}
	
	//根据保单号查询保单列表
	@ResponseBody
	@RequestMapping("/queryInsuranceList.do")
	public AppResponseResult queryInsuranceListByPolicyNo(@RequestBody JSONObject jsonParams) {
		AppResponseResult responseResult = new AppResponseResult();
		String productNo = jsonParams.getString("productNo");
		String policyNo = jsonParams.getString("policyNo");
		try {
			Assert.hasText(productNo, "缺失参数，字段[productNo]不能为空~");
			Assert.hasText(policyNo,"缺失参数,字段[policyNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			
			List<MetlifeInsuranceOrderDto> insuranceList = this.metlifeBusiService.queryInsuranceListByPolicyNo(policyNo,productNo);
			if(null == insuranceList) {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功，该记录不存在或保单号还未录入~");
				responseResult.setResult(new HashMap<>());
			}else {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功~");
				HashMap<String,Object> resultMap = new HashMap<>();
				resultMap.put("productNo",productNo);//产品编号
				resultMap.put("policyNo",policyNo);//保单号
				resultMap.put("list", insuranceList);
				responseResult.setResult(resultMap);
			}
		} catch(IllegalArgumentException e) {
			responseResult = new AppResponseResult();
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------根据保单号查询保险列表，参数productNo:{}，policyNo:{}查询保单列表异常，异常信息：{}",productNo,policyNo,e.getMessage());
			responseResult = new AppResponseResult();
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		}
		return responseResult;
	}
	
	//根据工单号查询保单列表
	@ResponseBody
	@RequestMapping("/list.do")
	public AppResponseResult queryList(@RequestBody JSONObject jsonParams) {
		AppResponseResult responseResult = new AppResponseResult();
		String productNo = jsonParams.getString("productNo");
		String orderNo = jsonParams.getString("orderNo");
		try {
			Assert.hasText(productNo, "缺失参数，字段[productNo]不能为空~");
			Assert.hasText(orderNo,"缺失参数,字段[orderNo]不能为空~");
			Assert.notNull(ProductType.get(productNo), "字段[productNo]无效或不合法~");
			
			List<MetlifeInsuranceOrderDto> orderList = metlifeBusiService.queryInsuranceOrderList(orderNo,productNo);
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("orderNo", orderNo);
			resultMap.put("productNo", productNo);
			if(null == orderList) {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功，记录不存在~");
				resultMap.put("list", new ArrayList<>());
				responseResult.setResult(resultMap);
			}else {
				responseResult = new AppResponseResult();
				responseResult.setCode("0000");
				responseResult.setMsg("请求成功~");
				resultMap.put("list", orderList);
				responseResult.setResult(resultMap);
			}
		} catch(IllegalArgumentException e) {
			responseResult = new AppResponseResult();
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据productNo:{}，orderNo:{}查询保险列表异常，异常信息：{}",productNo,orderNo,e.getMessage());
			responseResult = new AppResponseResult();
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		}
		return responseResult;
	}
	
	@ResponseBody
	@RequestMapping("/input/policy_no.do")
	public AppResponseResult updatePolicyNo(@RequestBody JSONObject params) {
		AppResponseResult responseResult = new AppResponseResult();
		String batchNo = params.getString("batchNo");
		String policyNo = params.getString("policyNo");
		try {
			Assert.hasText(batchNo, "缺少参数【batchNo】不能为空~");
			Assert.hasText(policyNo,"缺少参数【policyNo】不能为空~");
			int count = metlifeBusiService.updatePolicyNo(batchNo,policyNo);
			responseResult.setCode("0000");
			responseResult.setMsg("请求成功~");
			Map<String,Object> result = new HashMap<>();
			result.put("count", count);
			responseResult.setResult(result);
		} catch (IllegalArgumentException e) {
			responseResult.setCode("600");
			responseResult.setMsg(e.getMessage());
		} catch (Exception e) {
			responseResult.setCode("9999");
			responseResult.setMsg("请求失败，请稍后再试~");
		}
		return responseResult;
	}
	
}
