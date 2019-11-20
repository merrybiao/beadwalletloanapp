package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwZrobotFinanceBehavior;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwZrobotFinanceBehaviorService;
import com.waterelephant.utils.DateUtil;
/**
 * BwZrobotFinanceBehaviorServiceImpl.java
 * @author Administrator
 *
 */
@Service
public class BwZrobotFinanceBehaviorServiceImpl extends BaseService<BwZrobotFinanceBehavior, Long>
		implements BwZrobotFinanceBehaviorService {

	@Override
	public boolean save(BwZrobotFinanceBehavior record) throws Exception {
		return insert(record)>0;
	}

	@Override
	public boolean update() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BwZrobotFinanceBehavior queryByTransationId(String transationId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BwZrobotFinanceBehavior queryFinanceBehavior(String name,String idCardNum,String cellPhoneNum) throws Exception {
		
		Calendar date = Calendar.getInstance();
		
		date.add(Calendar.DATE, -30);//30天
		
		String expTime = DateUtil.getDateString(date.getTime(),"yyyy-MM-dd HH:mm:ss");//转年月日时分秒字符串
		
		String sql = "SELECT * "
					+ "FROM bw_zrobot_finance_behavior t " 
					+ "WHERE t.id_card_num = '"+idCardNum+"'"
					+ " AND t.cell_phone_num ='"+cellPhoneNum+"'"
					+ " AND t.`name` = '"+name+"'" 
					+ " AND t.create_time > '"+expTime+"'"
					+ " AND t.transaction_id IS NOT NULL"
					+ " ORDER BY t.create_time desc"
					+ " LIMIT 1";
		
		return sqlMapper.selectOne(sql, BwZrobotFinanceBehavior.class);
	}

}
