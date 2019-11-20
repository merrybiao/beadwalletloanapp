package com.waterelephant.rongtaobao.service.impl;

import com.waterelephant.rongtaobao.entity.TbZhifubaoBinding;
import com.waterelephant.rongtaobao.service.TbZhifubaoBindingService;
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
public class TbZhifubaoBindingServiceImpl extends BaseService<TbZhifubaoBinding,Long> implements TbZhifubaoBindingService {

	private Logger logger = Logger.getLogger(TbZhifubaoBindingServiceImpl.class);

	@Override
	public boolean saveTbZhifubaoBinding(TbZhifubaoBinding tbZhifubaoBinding) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(tbZhifubaoBinding) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	@Override
	public boolean deleteTbZhifubaoBinding(TbZhifubaoBinding tbZhifubaoBinding) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(tbZhifubaoBinding) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}
}
