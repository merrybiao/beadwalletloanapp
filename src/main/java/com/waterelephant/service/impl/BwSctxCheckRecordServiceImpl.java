package com.waterelephant.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwSctxCheckRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwSctxCheckRecordService;

@Service
public class BwSctxCheckRecordServiceImpl extends BaseService<BwSctxCheckRecord, Long>
		implements BwSctxCheckRecordService {
	
	private static final Logger logger= LoggerFactory.getLogger(BwSctxCheckRecordServiceImpl.class);

	@Override
	public BwSctxCheckRecord saveRecord(String name,String mobile,String idCard, String checkResult) {
		BwSctxCheckRecord record = null;
		try {
			record = new BwSctxCheckRecord();
			record.setName(name);
			record.setMobile(mobile);
			record.setIdCard(idCard);
			record.setCheckResult(checkResult);
			record.setState(parseResult(checkResult));
			record.setCreateTime(LocalDateTime.now());
			insert(record);
		} catch (Exception e) {
			logger.error("----保存数创天下三要素校验记录失败，name:{},mobile:{},idCard:{},checkResult:{}",name,mobile,idCard,checkResult);
			e.printStackTrace();
		}
		return null;
	}

	private String parseResult(String checkResult) {
		
		if(StringUtils.isEmpty(checkResult))
			return null;
		try {
			JSONObject jsonResult = JSON.parseObject(checkResult);
			
			String resCode = jsonResult.getString("resCode");
			
			if(!"0000".equals(resCode)) {
				return null;
				
			}
			
			JSONObject handlerData = jsonResult.getJSONObject("handlerData");
			return handlerData.getString("code_status");
		} catch (Exception e) {
			logger.error("----解析数创天下三要素结果失败：{}",checkResult);
			e.printStackTrace();
		}
		return null;
	}

}
