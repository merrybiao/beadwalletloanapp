package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.BwDhstreportSms;
import com.waterelephant.mapper.BwDhstreportSmsMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwDhstreportSmsService;
import com.waterelephant.utils.DateUtil;

@Service
public class BwDhstreportSmsImpl extends BaseService<BwDhstreportSms, Long> implements BwDhstreportSmsService{
	@Autowired
	private BwDhstreportSmsMapper bwDhstreportSmsMapper;
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public boolean saveDaHanSanTongSms(List<JSONObject> jsonArray) throws Exception{
		/*SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession();	
		BwDhstreportSmsMapper bwDhstreportSmsMapper = session.getMapper(BwDhstreportSmsMapper.class);*/
		List<BwDhstreportSms> list = new ArrayList<BwDhstreportSms>();
		for(int i = 0;i<jsonArray.size();i++){
		JSONObject resp = jsonArray.get(i);
		BwDhstreportSms sms = new BwDhstreportSms();
		sms.setMsgid(resp.getString("msgid"));
		sms.setPhone(resp.getString("phone"));
		sms.setSmsCount(resp.getString("smsCount"));
		sms.setSmsIndex(resp.getString("smsIndex"));
		sms.setStatus(resp.getString("status"));
		sms.setDescration(resp.getString("desc"));
		sms.setTime(CommUtils.isNull(resp.getString("time")) ? new Date() : DateUtil.stringToDate(resp.getString("time"), DateUtil.yyyy_MM_dd_HHmmss));
		sms.setWgcode(resp.getString("wgcode"));
		sms.setCreateTime(new Date());
		sms.setUpdateTime(new Date());
		/*int insert = insert(sms);
		System.out.println(insert);*/
		list.add(sms);
		}
//		session.commit();
		int count = bwDhstreportSmsMapper.insertList(list);
		return count>0 ? true : false;
	}
}
