package com.waterelephant.rongtaobao.service.impl;

import com.waterelephant.rongtaobao.entity.TbDeliverAddre;
import com.waterelephant.rongtaobao.service.TbDeliverAddreService;
import com.waterelephant.service.BaseService;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;


/**
 * Service层
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-8 9:36:18
 */
@Service
public class TbDeliverAddreServiceImpl extends BaseService<TbDeliverAddre,Long> implements TbDeliverAddreService {

	private Logger logger = Logger.getLogger(TbDeliverAddreServiceImpl.class);

	@Override
	public boolean saveTbDeliverAddre(TbDeliverAddre tbDeliverAddre) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(tbDeliverAddre) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean deleteTbDeliverAddre(TbDeliverAddre tbDeliverAddre) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(tbDeliverAddre) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}
}
