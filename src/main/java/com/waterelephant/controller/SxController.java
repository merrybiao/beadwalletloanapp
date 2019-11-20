package com.waterelephant.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.dataSource.DataSource;
import com.waterelephant.dataSource.DataSourceHolderManager;
import com.waterelephant.service.BwShouxinInfoService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;

@RestController
@RequestMapping("shouXin")
public class SxController {
	
	private Logger logger = LoggerFactory.getLogger(SxController.class);
	
	@Autowired
	private BwShouxinInfoService bwShouxinInfoServiceImpl;
	
	//商品分期环节
	@RequestMapping("/pullProductData.do")
	private AppResponseResult pullProductData(@RequestBody JSONObject requestdata,HttpServletRequest request){
		AppResponseResult result = new AppResponseResult();
		long startTime = System.currentTimeMillis();
		String no_busb = null;
			try {
				 String app_term = requestdata.getString("app_term");
				 String apply_state = requestdata.getString("apply_state");
				 String apply_time = requestdata.getString("apply_time");
				 String bank_card_no = requestdata.getString("bank_card_no");
				 String bank_code = requestdata.getString("bank_code");
				 String channel_info = requestdata.getString("channel_info");
				 String code_bus = requestdata.getString("code_bus");
				 JSONObject device = requestdata.getJSONObject("device");
				 String id_card = requestdata.getString("id_card");
				 String id_type = requestdata.getString("id_type");
				 String mobile = requestdata.getString("mobile");
				 String name_custc = requestdata.getString("name_custc");
				 no_busb = requestdata.getString("no_busb");
				 String prod_type = requestdata.getString("prod_type");
				 String re_loan = requestdata.getString("re_loan");
				 JSONArray order_Info = requestdata.getJSONArray("order_info");
				 String receiver_name = null;
				 String receiver_mobile = null;
				 String receiver_add = null;
				 String order_no = null;
				 String order_app_term = null;
				 String total_quantity = null;
				 String order_time = null;
				 for (int i = 0; i < order_Info.size(); i++) {
					 JSONObject order = JSONObject.parseObject(order_Info.get(i).toString());
					 receiver_name = order.getString("receiver_name");
					 receiver_mobile = order.getString("receiver_mobile");
					 receiver_add = order.getString("receiver_add");
					 order_no = order.getString("order_no");
					 app_term = order.getString("app_term");
					 total_quantity = order.getString("total_quantity");
					 order_time = order.getString("order_time");
					 Assert.hasText(order_no,"~数组中索引为"+i+"授信订单数据[order_no]信息不能为空~");
					 Assert.hasText(receiver_add,"~数组中索引为"+i+"授信订单数据[receiver_add]信息不能为空~");
					 Assert.hasText(total_quantity,"~数组中索引为"+i+"授信订单数据[total_quantity]信息不能为空~");
					 Assert.hasText(receiver_name,"~数组中索引为"+i+"授信订单数据[receiver_name]信息不能为空~");
					 Assert.hasText(order_time,"~数组中索引为"+i+"授信订单数据[order_time]信息不能为空~");
					 Assert.hasText(receiver_mobile,"~数组中索引为"+i+"授信订单数据[receiver_mobile]信息不能为空~");
				 	 }
					 Assert.hasText(apply_time,"~授信基础数据[apply_time]信息不能为空~");
					 Assert.hasText(code_bus,"~授信基础数据[code_bus]信息不能为空~");
					 Assert.hasText(id_card,"~授信基础数据[id_card]信息不能为空~");
					 Assert.hasText(mobile,"~授信基础数据[mobile]信息不能为空~");
					 Assert.hasText(name_custc,"~授信基础数据[name_custc]信息不能为空~");
					 Assert.hasText(no_busb,"~授信基础数据[no_busb]信息不能为空~");
					 Assert.hasText(prod_type,"~授信基础数据[prod_type]信息不能为空~");
					 Assert.hasText(app_term,"~授信订单数据[app_term]信息不能为空~");
					 DataSourceHolderManager.set(DataSource.MASTER_NEW);
				 boolean isSuccess = bwShouxinInfoServiceImpl.saveProductData(order_app_term, apply_state, apply_time, bank_card_no, bank_code, channel_info, code_bus, device, id_card, id_type, receiver_mobile, name_custc, no_busb, order_Info.toString(), prod_type, re_loan);
			     String msg = isSuccess ? "no_busb:"+no_busb+",存储数据库成功！！" : "no_busb:"+no_busb+",存储数据库失败！！";
				 logger.info("~【商品分期】~{}开始推送数据到北京审核！！",msg);
				 JSONObject returnSxResult = bwShouxinInfoServiceImpl.returnSxResult(requestdata);
				 if(!CommUtils.isNull(returnSxResult)) {
					  boolean successFlag = bwShouxinInfoServiceImpl.updateProductResult(no_busb,returnSxResult.toString());
					  String msgupdate = successFlag ? "根据no_busb:"+no_busb+",修改数据库成功！！" : "根据no_busb:"+no_busb+",修改对用数据审核结果失败！！";
					  logger.info("~【商品分期】~{}",msgupdate);
					  result.setCode("0000");
					  result.setMsg("请求成功");
					  result.setResult(returnSxResult);
				  } else {
					  result.setCode("700");
					  result.setMsg("~系统开小差了，请稍后再试~");
				  }
			} catch (IllegalArgumentException e) {
				 result.setCode("800");
				 result.setMsg(e.getMessage());
			}catch (Exception e) {
				 result.setCode("900");
				 result.setMsg("~系统开小差了，请稍后再试~");
			     logger.error("~【商品分期】~{}推送授信单异常，异常信息为{}",e.getMessage());
			     e.printStackTrace();
			}finally {
				logger.info("【商品分期】:查询唯一标识{}授信结果耗时{}ms",no_busb,System.currentTimeMillis()-startTime);
				DataSourceHolderManager.reset();
				
			}
				return result;
		}
	
	
	  //提现环节
	  @RequestMapping("/pullCashMoneyData.do")
	  private AppResponseResult pullCashMoneyData(@RequestBody JSONObject cashData,HttpServletRequest request){ 
		      AppResponseResult resp = new AppResponseResult();
			  long startTime = System.currentTimeMillis();
		      String no_busb = null;
		      try {
				  BigDecimal app_limit =cashData.getBigDecimal("app_limit"); 
				  Short app_term = cashData.getShort("app_term"); 
				  String apply_state =  cashData.getString("apply_state"); 
				  String apply_time =cashData.getString("apply_time"); 
				  String bank_code =cashData.getString("bank_card_no"); 
				  String bank_card_no = cashData.getString("bank_code"); 
				  String channel_info =cashData.getString("channel_info"); 
				  String code_bus = cashData.getString("code_bus");
				  JSONObject device = cashData.getJSONObject("device"); 
				  String id_card = cashData.getString("id_card");
				  String id_type = cashData.getString("id_type");
				  String mobile =  cashData.getString("mobile"); 
				  String name_custc = cashData.getString("name_custc"); 
				  no_busb = cashData.getString("no_busb");
				  String prod_type = cashData.getString("prod_type");
				  String re_loan = cashData.getString("re_loan"); 
				  Assert.isTrue(!CommUtils.isNull(no_busb),"~工单[no_busb]信息不能为空~");
				  Assert.hasText(apply_time,"~申请单的环节[apply_time]信息不能为空~");
				  Assert.hasText(code_bus,"~授信[code_bus]信息不能为空~");
				  Assert.hasText(id_card,"~授信[id_card]信息不能为空~");
				  Assert.hasText(mobile,"~授信[mobile]信息不能为空~");
				  Assert.hasText(name_custc,"~授信[name_custc]信息不能为空~");
				  Assert.hasText(no_busb,"~授信[no_busb]信息不能为空~");
				  Assert.hasText(prod_type,"~授信[prod_type]信息不能为空~");
				  DataSourceHolderManager.set(DataSource.MASTER_NEW);
				  boolean isSuccess = bwShouxinInfoServiceImpl.saveCashMoneyData(app_limit, app_term, apply_state, apply_time, bank_card_no, bank_code, channel_info, code_bus, device, id_card, id_type, mobile, name_custc, no_busb, prod_type, re_loan);
				  String msg = isSuccess ? "no_busb:"+no_busb+",存储数据库成功！！" : "no_busb:"+no_busb+",存储数据库失败！！";
				  logger.info("~【提现环节】~{}开始推送数据到北京审核！！",msg);
				  JSONObject returnSxResult = bwShouxinInfoServiceImpl.returnSxResult(cashData);
				 if(!CommUtils.isNull(returnSxResult)) {
					  boolean successFlag = bwShouxinInfoServiceImpl.updateCashMoneyResult(no_busb,returnSxResult.toString());
					  String msgupdate = successFlag ? "根据no_busb:"+no_busb+",修改数据库成功！！" : "根据no_busb:"+no_busb+",修改对用数据审核结果失败！！";
					  logger.info("~【提现环节】~{}",msgupdate);
					  resp.setCode("0000");
					  resp.setMsg("请求成功");
					  resp.setResult(returnSxResult);
				  } else {
					  resp.setCode("700");
					  resp.setMsg("~系统开小差了，请稍后再试~");
				  }
			} catch (IllegalArgumentException e) {
				 resp.setCode("800");
				 resp.setMsg(e.getMessage());
			}catch (Exception e) {
				resp.setCode("900");
				resp.setMsg("~系统开小差了，请稍后再试~");
		        logger.error("~【提现环节】~{}推送授信单异常，异常信息为{}",e.getMessage());
		        e.printStackTrace();		
			}finally {
				logger.info("【提现环节】查询唯一标识:{}授信结果耗时{}ms",no_busb,System.currentTimeMillis()-startTime);
				DataSourceHolderManager.reset();
			}
		      return resp; 
	  }
}