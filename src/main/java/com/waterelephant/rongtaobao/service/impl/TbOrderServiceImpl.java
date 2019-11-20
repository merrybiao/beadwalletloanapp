package com.waterelephant.rongtaobao.service.impl;

import com.waterelephant.rongtaobao.entity.TbOrder;
import com.waterelephant.rongtaobao.service.TbOrderService;
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
public class TbOrderServiceImpl extends BaseService<TbOrder,Long> implements TbOrderService {

	private Logger logger = Logger.getLogger(TbOrderServiceImpl.class);

	@Override
	public boolean saveTbOrder(TbOrder tbOrder) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(tbOrder) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean deleteTbOrder(TbOrder tbOrder) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(tbOrder) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}
}
