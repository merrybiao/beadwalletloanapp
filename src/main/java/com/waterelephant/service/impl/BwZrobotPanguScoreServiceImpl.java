package com.waterelephant.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.waterelephant.entity.BwZrobotPanguScore;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwZrobotPanguScoreService;
import com.waterelephant.utils.DateUtil;
/**
 * BwZrobotPanguScoreServiceImpl.java
 * @author dinglinhao
 *
 */
@Service
public class BwZrobotPanguScoreServiceImpl extends BaseService<BwZrobotPanguScore, Long> implements BwZrobotPanguScoreService {

	@Override
	public Long save(String name, String idCardNum, String cellPhoneNum, String bankCardNum) throws Exception {
		BwZrobotPanguScore record = new BwZrobotPanguScore();
		record.setName(name);
		record.setIdCardNum(idCardNum);
		record.setCellPhoneNum(cellPhoneNum);
		record.setBankCardNum(bankCardNum);
		record.setCreateTime(new Date());
		return insert(record) >0 ? record.getId() : 0;
	}

	@Override
	public boolean update(BwZrobotPanguScore record) throws Exception {
		Assert.notNull(record,"参数为空");
		Assert.notNull(record.getId(),"参数id为空~");
		return updateByPrimaryKeySelective(record)>0;
	}

	@Override
	public BwZrobotPanguScore queryPangu(String name, String idCardNum, String cellPhoneNum, String bankCardNum)
			throws Exception {
		BwZrobotPanguScore record = new BwZrobotPanguScore();
		record.setName(name);
		record.setIdCardNum(idCardNum);
		record.setCellPhoneNum(cellPhoneNum);
		record.setBankCardNum(bankCardNum);
		return selectOne(record);
	}

	@Override
	public BwZrobotPanguScore queryPanguRecord(String name, String idCardNum, String cellPhoneNum, String bankCardNum)
			throws Exception {
		
		Calendar date = Calendar.getInstance();
		
		date.add(Calendar.DATE, -30);//30天
		
		String expTime = DateUtil.getDateString(date.getTime(),"yyyy-MM-dd HH:mm:ss");//转年月日时分秒字符串
		
		String sql = "SELECT * FROM bw_zrobot_pangu_score t "
				+ "WHERE t.id_card_num = '"+idCardNum+"' "
				+ "AND  t.cell_phone_num = '"+cellPhoneNum+"' "
				+ "AND t.bank_card_num = '"+bankCardNum+"' "
				+ "AND t.`name` = '"+name+"' "
				+ "AND t.create_time > '"+expTime+"' "
				+ "AND t.score IS NOT NULL "
				+ "AND t.score >0 "
				+ "ORDER BY t.create_time DESC "
				+ "LIMIT 1";
		return sqlMapper.selectOne(sql, BwZrobotPanguScore.class);
	}

}
