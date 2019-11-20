package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwZlgxbAuthData;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwZlgxbAuthDataService;
import com.waterelephant.utils.DateUtil;

@Service
public class BwZlgxbAuthDataServiceImpl extends BaseService<BwZlgxbAuthData, Long>  implements IBwZlgxbAuthDataService {

	@Override
	public Long saveAutuData(String name, String phone, String idCard,
			String authItem, String sequenceNo, String returnUrl,
			String notifyUrl, String timestamp) {
		BwZlgxbAuthData data = new BwZlgxbAuthData();
		data.setName(name);
		data.setPhone(phone);
		data.setIdcard(idCard);
		data.setAuthItem(authItem);
		data.setAuthStatus("0");
		data.setStatus("1");
		data.setSequenceNo(sequenceNo);
		data.setReturnUrl(returnUrl);
		data.setNotifyUrl(notifyUrl);
		data.setTimestamp(timestamp);
		data.setCreateTime(new Date());
		return insert(data) == 1 ? data.getId() : 0;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean updateToken(String token,Long id) throws Exception {
		String sql = "UPDATE bw_zlgxb_auth_data SET token ='"+token+"' where id='"+id+"'";
		return sqlMapper.update(sql)>0;
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean updateAutuData(String score, String status, String authStatus,String authTime,String sequenceno) throws Exception {
		String sql = "UPDATE bw_zlgxb_auth_data SET score ='"+score+"' "+","
				+ " status='"+status+"'" +","
				+ " auth_status='"+authStatus+"'"+","
				+ " auth_time='"+authTime+"'"
				+ " where sequence_no='"+sequenceno+"'";
		return sqlMapper.update(sql)>0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BwZlgxbAuthData queryAutuData(String sequenceno) throws Exception {
		String sql = "SELECT * FROM bw_zlgxb_auth_data WHERE sequence_no ='"+sequenceno+"' limit 1";
		return sqlMapper.selectOne(sql,BwZlgxbAuthData.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean updateAutuDatabyQuery(String score, String status,
			String sequenceno) {
		String sql = "UPDATE bw_zlgxb_auth_data SET score ='"+score+"' " +","
				+ " status='"+status+"'" 
				+ " where sequence_no='"+sequenceno+"'";
		return sqlMapper.update(sql)>0;
	}

}
