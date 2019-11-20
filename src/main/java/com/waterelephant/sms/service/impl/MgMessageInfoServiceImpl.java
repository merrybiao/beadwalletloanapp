package com.waterelephant.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.waterelephant.sms.entity.MgMessageInfo;
import com.waterelephant.sms.service.MgMessageInfoService;
import com.waterelephant.sms.util.DateUtil;

@Service
public class MgMessageInfoServiceImpl implements MgMessageInfoService {

	private Logger logger = LoggerFactory.getLogger(MgMessageInfoServiceImpl.class);
	
	/**
	 * 保存短信
	 */
	@Override
	public void save(DBCollection dbCollection, String phone, String msg, String seqid, int chenal, int type) {
		// 判断手机号是一个还是多个
		String[] arrayStr = phone.split(",");
		try {
			for (int i=0; i<arrayStr.length; i++) {
				// 将数据放入MgMessageInfo实体中
				MgMessageInfo mgMessageInfo = new MgMessageInfo();
				mgMessageInfo.setPhone(arrayStr[i]);
				mgMessageInfo.setMsg(msg);
				mgMessageInfo.setSeqid(seqid);
				mgMessageInfo.setCreate_time(DateUtil.getDate());
				mgMessageInfo.setUpdate_time(DateUtil.getDate());
				mgMessageInfo.setState(0);
				mgMessageInfo.setState_value("");
				mgMessageInfo.setChenal(chenal);
				mgMessageInfo.setType(type);
				
				// 第三步：保存实体数据到数据库
				Gson gson = new Gson(); 
				DBObject dbobject = (DBObject)JSON.parse(gson.toJson(mgMessageInfo));  // 转换成json字符串，再转换成DBObject对象
				dbCollection.insert(dbobject); // 向数据库插入数据
				logger.info("保存短信到数据库成功！");
			}
		}catch (Exception e) {
			logger.error("保存数据异常:",e.getMessage());
		}
	}
	
	/**
	 * 更新数据
	 */
	@Override
	public void update(DBCollection dbCollection, String seqid, int state, String stateValue, String phone) {
		
		// 第二步：设置更新条件
		try {
			DBObject updateCondition = new BasicDBObject();
			updateCondition.put("seqid", seqid);
			updateCondition.put("phone", phone);
			DBObject updateValue = new BasicDBObject();
			updateValue.put("update_time", DateUtil.getDate());
			updateValue.put("state", state);
			updateValue.put("state_value", stateValue);
			DBObject updateSetValue = new BasicDBObject("$set",updateValue);
			// 第三步：根据phone更新实体
			dbCollection.update(updateCondition, updateSetValue);
		}catch (Exception e) {
			logger.error("更新数据异常：",e.getMessage());
		}
	}
}
