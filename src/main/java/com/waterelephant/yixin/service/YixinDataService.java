/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.service;

import com.waterelephant.yixin.dto.YixinMainData;

/**
 * @author heqiwen
 *
 */
public interface YixinDataService {
	
	public YixinMainData saveAndQueryYixinDatas(String idNo,String name,String queryReason);

}
