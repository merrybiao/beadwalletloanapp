package com.waterelephant.rongtaobao.service;

import com.waterelephant.rongtaobao.entity.TbUser;

/**
 * Service接口
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-8 9:36:19
 */
public interface TbUserService {

	
	/**
	 * 保存
	 * @param tbUser
	 */
	boolean saveTbUser(TbUser tbUser);
	
	/**
	 * 修改
	 * @param tbUser
	 */
	boolean deleteTbUser(TbUser tbUser);
}
