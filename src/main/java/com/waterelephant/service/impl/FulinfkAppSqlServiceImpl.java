package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwFullinkReport;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IFulinfkAppSqlService;
import com.waterelephant.utils.DateUtil;
@Service
public class FulinfkAppSqlServiceImpl extends BaseService<BwFullinkReport, Long> implements IFulinfkAppSqlService{

	@Override
	public Integer saveFullinkInfo(BwFullinkReport bwfullinkreport) {
		return mapper.insert(bwfullinkreport);
	}

	@Override
	public BwFullinkReport selectScore(String phone, String idCard, String name) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30);
		String time = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		String sql = "SELECT * FROM bw_fullink_report WHERE ID_CARD_NO = '"+idCard+"' AND MOBILE = '"+phone+"' AND NAME = '"+name+"' and create_time >'"+time+"'";
		StringBuffer buffer = new StringBuffer(sql);
		buffer.append( "and score is not null");
		buffer.append(" order by create_time desc limit 1");
		return sqlMapper.selectOne(buffer.toString(), BwFullinkReport.class);
	}
}
