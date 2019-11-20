package com.waterelephant.rongCarrier.email.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.email.entity.EmailInfo;
import com.waterelephant.rongCarrier.email.service.EmailInfoService;
import com.waterelephant.service.BaseService;

@Service
public class EmailInfoServiceImpl extends BaseService<EmailInfo, Long> implements EmailInfoService {

	@Override
	public boolean saveEmailInfo(EmailInfo emailInfo) {
		return mapper.insert(emailInfo) > 0;
	}

	@Override
	public boolean updateEmailInfo(EmailInfo emailInfo) {
		return mapper.updateByPrimaryKey(emailInfo) > 0;
	}

	@Override
	public EmailInfo queryEmailInfo(Long borrowerId, String mail, Long paymentCurDate) {
		String sql = "select * from bw_email_info a where a.borrower_id = " + borrowerId +" and a.mail= '" + mail + "' and a.payment_cur_date = " + paymentCurDate;
		EmailInfo emailInfo = sqlMapper.selectOne(sql, EmailInfo.class);
		return emailInfo;
	}

}
