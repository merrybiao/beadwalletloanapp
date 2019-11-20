package com.waterelephant.bjsms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.waterelephant.bjsms.entity.BwDhstreportSms;
import com.waterelephant.bjsms.service.DhstPullDataSmsService;

@Service
public class DhstPullDataSmsServiceImpl implements DhstPullDataSmsService{
	
	private Logger logger = LoggerFactory.getLogger(DhstPullDataSmsServiceImpl.class);

	@Override
	public boolean saveMongoSms(List<BwDhstreportSms> listReport) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}
	
	/*
	 * // @Autowired private MongoTemplate mongoTemplate;
	 * 
	 * @Override public boolean saveMongoSms(List<BwDhstreportSms> list) throws
	 * Exception { if(!CommUtils.isNull(list)){ mongoTemplate.insertAll(list);
	 * for(int i =0;i<list.size();i++){ BwDhstreportSms report = list.get(i);
	 * Criteria cra = new Criteria().andOperator(
	 * Criteria.where("phone").is(report.getPhone()),
	 * Criteria.where("msgid").is(report.getMsgid()) ); Query query =
	 * Query.query(cra); Update update = Update.update("desc",
	 * report.getDesc()).set("wgcode", report.getWgcode()); WriteResult result =
	 * mongoTemplate.updateMulti(query, update, BwDhstSendSuccessSms.class);
	 * logger.info("【大汉三通】手机号码：{}的状态更新成功！,更新了{}条数据。",report.getPhone(),result.getN()
	 * ); } return true; } return false; }
	 */
}
