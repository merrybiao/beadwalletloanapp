package com.waterelephant.fulinfk.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.fulinfk.FulinfkSDK;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwFullinkReport;
import com.waterelephant.fulinfk.service.IFulinfkAppService;
import com.waterelephant.service.IFulinfkAppSqlService;
import com.waterelephant.utils.RedisUtils;

@Service
public class FulinfkAppServiceImpl implements IFulinfkAppService {
	
	private static Logger logger = LoggerFactory.getLogger(FulinfkAppServiceImpl.class);

	@Autowired
	private IFulinfkAppSqlService ifulinfkappsqlservice;

	@Override
	public String getFullinkKey() throws Exception {
		String result = null;
		result = FulinfkSDK.getKey();
		if (!StringUtils.isEmpty(result)) {
			JSONObject json = JSON.parseObject(result);
			String data = json.getString("data");
			JSONObject key = JSON.parseObject(data);
			if (!RedisUtils.exists("encryptKey")) {
				RedisUtils.set("fullink", key.getString("encryptKey"));
			}
			return result;
		} else {
			return null;
		}
	}

	@Override
	public String saveFullinkReport(Map<String, String> map) throws Exception {
		if (RedisUtils.exists("fullink")) {
			String key = RedisUtils.get("fullink");
			map.put("key", key);
		} else {
			String result = getFullinkKey();
			if (!StringUtils.isEmpty(result)) {
				JSONObject json = JSON.parseObject(result);
				String data = json.getString("data");
				JSONObject key = JSON.parseObject(data);
				map.put("key", key.getString("encryptKey"));
			}
		}
		BwFullinkReport report = ifulinfkappsqlservice.selectScore(map.get("mobile"), map.get("idCardNo"),map.get("name"));
		logger.info("获取数据库的值为：report："+report);
		if (report == null || CommUtils.isNull(report.getScore())) {
			String resp = FulinfkSDK.getReport(map);
			JSONObject json = JSON.parseObject(resp);
			BwFullinkReport bwfullinkreport = null;
			//调用成功的情况
			if (json.getString("resCode").equals("200")) {
				json.remove("resCode");
				bwfullinkreport = JSON.parseObject(resp, BwFullinkReport.class);
				bwfullinkreport.setCreateTime(new Date());
				bwfullinkreport.setMobile(map.get("mobile"));
				bwfullinkreport.setIdCardNo(map.get("idCardNo"));
				bwfullinkreport.setName(map.get("name"));
				logger.info("需要存入数据库的值为：resp："+JSONObject.toJSON(bwfullinkreport));
				Integer num  = ifulinfkappsqlservice.saveFullinkInfo(bwfullinkreport);
				if(num != 0){
					logger.info("命中存储数据库成功！！！");
				}
				return bwfullinkreport.getScore();
			//调用失败的情况，只用存储时间+传进来的参数
			} else {
				bwfullinkreport = new BwFullinkReport();
				bwfullinkreport.setCreateTime(new Date());
				bwfullinkreport.setMobile(map.get("mobile"));
				bwfullinkreport.setIdCardNo(map.get("idCardNo"));
				bwfullinkreport.setName(map.get("name"));
				Integer num  = ifulinfkappsqlservice.saveFullinkInfo(bwfullinkreport);
				if(num != 0){
					logger.info("调用发生未知情况，存储数据库成功！！！");
				}
				return null;
			}
		} else {
			return report.getScore();
		}
	}
}
