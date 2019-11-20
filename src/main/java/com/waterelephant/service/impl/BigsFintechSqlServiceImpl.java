package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBigsFintech;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBigsFintechSqlService;
import com.waterelephant.utils.DateUtil;
@Service
public class BigsFintechSqlServiceImpl extends BaseService<BwBigsFintech, Long> implements IBigsFintechSqlService{

	@Override
	public Integer saveApplyInfo(BwBigsFintech bwbigsfintech)  throws Exception {
		return mapper.insert(bwbigsfintech);
	}

	@Override
	public BwBigsFintech selectPhoneQueryTotal(String mobile,String orderId,String borrowerId) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		String time = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT * FROM  bw_bigfintech_report WHERE mobile = '"+mobile+"'";
		StringBuffer buffer = new StringBuffer(sql);
//		if(!CommUtils.isNull(orderId)){
//			buffer.append(" AND borrower_Id = '"+borrowerId+"'");
//		}
//		if(!CommUtils.isNull(borrowerId)){
//			buffer.append(" AND order_Id = '"+orderId+"'");
//		}
		buffer.append(" AND time_Query > '"+time+"'");
		buffer.append(" AND phone_Apply IS NOT NULL");
		buffer.append(" ORDER BY time_Query DESC LIMIT 1");
		System.out.println(buffer.toString());
		return sqlMapper.selectOne(String.valueOf(buffer),BwBigsFintech.class);
	}
	
}
