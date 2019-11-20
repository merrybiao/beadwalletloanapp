package com.waterelephant.zlgxb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwZlgxbAuthData;
import com.waterelephant.service.IBwZlgxbAuthDataService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.zlgxb.service.IZlgxbBaseInfoService;

@Service
public class ZlgxbBaseInfoServiceImpl extends ZlgxbdealSdk implements IZlgxbBaseInfoService{
	
	private Logger logger = LoggerFactory.getLogger(ZlgxbBaseInfoServiceImpl.class);
	
	@Autowired
	private IBwZlgxbAuthDataService ibwzlgxbauthdataserviceimpl;

	/**
	 * 存储基本信息
	 */
	@Override
	public JSONObject saveAuthData(String name, String phone, String idCard,
			String authItem, String sequenceNo, String returnUrl,
			String notifyUrl, String timestamp) throws Exception {
		
		BwZlgxbAuthData bwzlgxbauthdata = ibwzlgxbauthdataserviceimpl.queryAutuData(sequenceNo);
		
		if(!CommUtils.isNull(bwzlgxbauthdata)) throw new IllegalArgumentException("存在重复的[sequence_no]，请保持唯一性");
		
		Long id = null;
		
		if(CommUtils.isNull(bwzlgxbauthdata)){
			
			id = ibwzlgxbauthdataserviceimpl.saveAutuData(name,phone,idCard,authItem,sequenceNo,returnUrl,notifyUrl,timestamp);
		}
		
		if(id != null){
			logger.info("请求授权基本参数保存成功，主键值为"+id);
		}
		
		//获取token
		String token = getToken( name, idCard, phone, timestamp, authItem, sequenceNo);
		
		
		if(CommUtils.isNull(token)) throw new Exception("获取token失败，返回结果为空");
		
		boolean falg = false;
		if(!CommUtils.isNull(id)){
			
			falg = ibwzlgxbauthdataserviceimpl.updateToken(token, id);
			
		}

		if(falg){
			logger.info("token存库成功");
		}
		
		//执行授权
		JSONObject autudata = getAutuData(token,returnUrl);
		
		if(CommUtils.isNull(autudata)) throw new Exception("获取授权链接url失败，返回结果为空");
		
		autudata.put("token", token);
			
		return autudata;
			
	}

	/**
	 * 查询单个信息
	 */
	@Override
	public BwZlgxbAuthData queryAuthData(String sequenceno) throws Exception {
		
		return ibwzlgxbauthdataserviceimpl.queryAutuData(sequenceno);
		
	}

	/**
	 * 推送数据时存库
	 */
	@Override
	public boolean updateAuthData(String score, String status, String authStatus,String authTime,String sequenceNo) throws Exception {
		
		boolean flag = ibwzlgxbauthdataserviceimpl.updateAutuData(score,status,authStatus,authTime,sequenceNo);
		
		return flag;
	}

	/**
	 * 收动获取信用分时存库
	 */
	@Override
	public JSONObject updateAuthDatByQuery(String token,String sequenceNo) throws Exception {
		
		BwZlgxbAuthData bwzlgxbauthdata = ibwzlgxbauthdataserviceimpl.queryAutuData(sequenceNo);
		
		if(!CommUtils.isNull(bwzlgxbauthdata)&&!CommUtils.isNull(bwzlgxbauthdata.getScore())&&"2".equals(bwzlgxbauthdata.getStatus())){
			
			JSONObject json = new JSONObject();
			
			json.put("score", String.valueOf(bwzlgxbauthdata.getScore()));
			
			json.put("success", "true");
			
			json.put("status", bwzlgxbauthdata.getStatus());
			
			return json;
			
		} else {
			String authdata = getScore(token);
			
			if(CommUtils.isNull(authdata))  throw new Exception("手动获取信用分失败，返回结果为空");
			
			JSONObject json = JSONObject.parseObject(authdata);
			
			if("1".equals(json.getString("status"))) throw new IllegalArgumentException("用户未授权！！！");
				
			if("3".equals(json.getString("status"))) throw new IllegalArgumentException("授权已过期！！！");

			String status = json.getString("status");
			
			String score = json.getString("score");
			
			ibwzlgxbauthdataserviceimpl.updateAutuDatabyQuery(score,status,sequenceNo);
			
			return JSONObject.parseObject(authdata);
		}
	}
}
