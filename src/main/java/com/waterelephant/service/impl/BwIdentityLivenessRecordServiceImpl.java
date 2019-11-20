package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.waterelephant.entity.BwIdentityLivenessRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwIdentityLivenessRecordService;

@Service
public class BwIdentityLivenessRecordServiceImpl extends BaseService<BwIdentityLivenessRecord, Long> implements BwIdentityLivenessRecordService {

	@Override
	public boolean saveLivenessRecord(String productNo, String livenessSource,String idcardNum,String name,String livenessUrl,String livenessData, Object resultMap)
			throws Exception {
		BwIdentityLivenessRecord record = new BwIdentityLivenessRecord();
		record.setProductNo(productNo);
		record.setIdcardNumber(idcardNum);
		record.setName(name);
		record.setLivenessUrl(livenessUrl);
		record.setLivenessData(null);//活体数据base64不保存至数据库
		record.setLivenessSource(livenessSource);
		record.setLivenessResult(JSON.toJSONString(resultMap));
		record.setCreateTime(new Date());
		
		return mapper.insert(record)>0;
	}

}
