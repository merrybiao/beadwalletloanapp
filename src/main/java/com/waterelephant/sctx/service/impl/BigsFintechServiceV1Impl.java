package com.waterelephant.sctx.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.sctx.SctxApplySDKV1;
import com.waterelephant.entity.BwSctxBaseinfo;
import com.waterelephant.mapper.BwSctxCommonMapper;
import com.waterelephant.sctx.service.IBigsFintechServiceV1;
import com.waterelephant.service.IBwSctxBaseinfoService;
import com.waterelephant.service.IBwSctxBlackinfoService;
import com.waterelephant.utils.CommUtils;
@Service
public class BigsFintechServiceV1Impl implements IBigsFintechServiceV1{
	
	private Logger logger = LoggerFactory.getLogger(BigsFintechServiceV1Impl.class);
	
	@Autowired
	private IBwSctxBaseinfoService bwsctxbaseinfoserviceimpl;
	
	@Autowired
	private BwSctxCommonMapper sctxcommonmapper;
	
	@Autowired
	private IBwSctxBlackinfoService  bwsctxblackinfoserviceimpl;
	

	@Override
	public List<Map<String,String>> saveApplyInfo(Long id,String name, String mobile, String idCard,String onlyId) throws Exception {
		
			String resp = SctxApplySDKV1.getApplyReport(mobile, idCard, name);
			
			JSONObject json = JSONObject.parseObject(resp);
			
			if(json.getString("retCode").equals("0000")){
				
				String haveData = json.getString("retData");
				
				if("[]".equals(haveData)){
					
					logger.info("数创天下未命中查询人：~"+name+"~");
					
					return new ArrayList<Map<String,String>>();
					
				} else {
					
					List<Map<String,String>> listmap = JSONObject.parseObject(haveData, ArrayList.class);
					
					for (Map<String, String> map : listmap) {
						
						if(!map.containsKey("phone")){
							map.put("phone", "");
						}
						
						if(!map.containsKey("name")){
							map.put("name", "");
						}
						
						if(!map.containsKey("idCard")){
							map.put("idCard", "");
						}
						boolean isblack = bwsctxblackinfoserviceimpl.saveSctxBlack(id, map.get("overdue_amt"), map.get("plat_code"),  map.get("loan_type"));
						
						if(isblack){
							logger.info("数创天下命中查询人：~"+name+"~");
						}
					}
					
					return listmap;
				}
			} else {
				
				throw new IllegalArgumentException("数创天下调用接口请求数据失败！！请稍后再试。。。");
			}
	  
	}

	public Long saveBaseInfo(String name, String mobile, String idCard,String onlyId) throws Exception{
		
		return bwsctxbaseinfoserviceimpl.saveBaseInfo(name, mobile, idCard, onlyId);
		
	}
	
	
	@Override
	public List<Map<String,String>> querydataBaseInfoByonlyId(String onlyId) throws Exception{
		
		return sctxcommonmapper.queryAllDataByOnlyId(onlyId);
	}

	@Override
	public BwSctxBaseinfo queryBaseinfoByonlyId(String onlyId) throws Exception {
		
		return bwsctxbaseinfoserviceimpl.queryBaseInfoByonlyId(onlyId);
	}
}
