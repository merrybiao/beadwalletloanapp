package com.waterelephant.rongtaobao.service;

import com.waterelephant.rongtaobao.entity.TbOrder;

/**
 * Service接口
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-8 9:36:18
 */
public interface TbOrderService {

	
	/**
	 * 保存
	 * @param tbOrder
	 */
	boolean saveTbOrder(TbOrder tbOrder);
	
	/**
	 * 修改
	 * @param tbOrder
	 */
	boolean deleteTbOrder(TbOrder tbOrder);
}
