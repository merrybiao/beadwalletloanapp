package com.waterelephant.rongtaobao.service.impl;

import com.waterelephant.rongtaobao.entity.TbUser;
import com.waterelephant.rongtaobao.service.TbUserService;
import com.waterelephant.service.BaseService;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;


/**
 * Service层
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-8 9:36:19
 */
@Service
public class TbUserServiceImpl extends BaseService<TbUser,Long> implements TbUserService {

	private Logger logger = Logger.getLogger(TbUserServiceImpl.class);

	@Override
	public boolean saveTbUser(TbUser tbUser) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(tbUser) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean deleteTbUser(TbUser tbUser) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(tbUser) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}
}
