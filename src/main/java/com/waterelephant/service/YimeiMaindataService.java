/**
 * @author heqiwen
 * @date 2016年12月18日
 */
package com.waterelephant.service;

import java.util.Map;

import com.waterelephant.entity.YimeiMaindata;



/**
 * @author Administrator
 *
 */
public interface YimeiMaindataService {

	
	
	public int addYimeiMaindata(YimeiMaindata YiMeiOverdue);
	
	//public void updateYimeiMaindata(YimeiMaindata YiMeiOverdue);
	
	public int updateYimeiMaindata(YimeiMaindata YiMeiOverdue);
	
	public int deleteYimeiMaindata(String phone);
	
	public Map<String,Object> saveYiMeiDataByPhone(String phone);
}
