/**
 * @author wurenbiao
 *
 */
package com.waterelephant.sctx.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.sctx.SctxApplySDK;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwBigsFintech;
import com.waterelephant.sctx.service.IBigsFintechService;
import com.waterelephant.service.IBigsFintechSqlService;
@Service
public class BigsFintechServiceImpl  implements IBigsFintechService{
	
	private Logger logger = LoggerFactory.getLogger(BigsFintechServiceImpl.class);
	
	@Autowired
	private IBigsFintechSqlService bigsfintechsqlserviceimpl;

	@Override
	public String saveApplyInfo(String mobile, String order_id, String borrower_id) throws Exception {
		String resp = SctxApplySDK.getApplyReport(mobile);
		JSONObject res = new JSONObject();
		if(!CommUtils.isNull(resp)){
			 BwBigsFintech bwbigsfintech = null;
			if(JSON.parseObject(resp).get("resCode").equals("0000")){
				 //通过接口获取到的数据
				 String handlerData =  JSON.parseObject(resp).get("handlerData").toString();
				 JSONObject jsonObject = JSONObject.parseObject(handlerData);
				 String  mc3_apply_02 = jsonObject.get("mc3_apply_02").toString();
				 JSONObject json = JSONObject.parseObject(mc3_apply_02);
				 //网贷平台注册数
				 String phone_apply = null;
				 if(json.containsKey("phone_apply")&&!CommUtils.isNull(json.get("phone_apply"))){
					 phone_apply = json.get("phone_apply").toString();
				 }
				 //查询时间
				 SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 Date time_query = df.parse(String.valueOf(json.get("time_query"))); 
				 //通过数据库查询到的数据
				 bwbigsfintech = new BwBigsFintech();
				// BwBigsFintech query = bigsfintechsqlserviceimpl.selectPhoneQueryTotal(mobile);
				 bwbigsfintech.setPhoneApply(phone_apply);
				 bwbigsfintech.setTimeQuery(time_query);
				 bwbigsfintech.setMobile(mobile);
				if(!CommUtils.isNull(order_id)){
					bwbigsfintech.setOrderId(order_id);
				}
				if(!CommUtils.isNull(borrower_id)){
					bwbigsfintech.setBorrowerId(borrower_id);
				}
				 //不是第一次查询
				/*if(query !=null && query.getTotalQuery() != 0){
					bwbigsfintech.setTotalQuery(query.getTotalQuery()+1);
					Integer num = bigsfintechsqlserviceimpl.saveApplyInfo(bwbigsfintech);
					if(num != 0){
						logger.info("插入成功！！！！！！");
					}
				}
				//是第一次查询
				else{
					long totalQuery = 1;
					bwbigsfintech.setTotalQuery(totalQuery);
					Integer num =bigsfintechsqlserviceimpl.saveApplyInfo(bwbigsfintech);
					if(num != 0){
						logger.info("插入成功！！！！！！");
					}
				}*/
				Integer num =bigsfintechsqlserviceimpl.saveApplyInfo(bwbigsfintech);
				if(num != 0){
					logger.info("命中插入成功！！！！！！");
				}
				res.put("resCode", JSON.parseObject(resp).get("resCode").toString());
				res.put("phone_apply", phone_apply);
				return JSON.toJSONString(res);
			}else{
				bwbigsfintech = new BwBigsFintech();
				if(!CommUtils.isNull(order_id)){
					bwbigsfintech.setOrderId(order_id);
				}
				if(!CommUtils.isNull(borrower_id)){
					bwbigsfintech.setBorrowerId(borrower_id);
				}
				bwbigsfintech.setMobile(mobile);
				Integer num =bigsfintechsqlserviceimpl.saveApplyInfo(bwbigsfintech);
				if(num != 0){
					logger.info("返回数据异常插入成功！！！！！！");
				}
				res.put("resCode", JSON.parseObject(resp).get("resCode").toString());
				return JSON.toJSONString(res);
			}
		}else{
			return null;
		}
	}	
}