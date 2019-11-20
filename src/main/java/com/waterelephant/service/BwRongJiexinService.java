/**
 * @author heqiwen
 * @date 2017年1月23日
 */
package com.waterelephant.service;

import com.waterelephant.entity.BwRongJiexinData;

/**
 * @author Administrator
 *
 */
public interface BwRongJiexinService {

	public String getLoginToken();
	
	public BwRongJiexinData getRongJiexinBlacks(String name,String idCard,String phone);
}
