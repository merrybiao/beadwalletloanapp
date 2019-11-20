/**
 * @author heqiwen
 * @date 2016年12月14日
 */
package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.YimeiOverdueinfo;



/**
 * @author Administrator
 *
 */
public interface YiMeiOverdueService {
	
	public List<YimeiOverdueinfo> getYiMeiOverdueByPhone(String phone);
	
	public void addYiMeiOverdueVo(YimeiOverdueinfo YiMeiOverdue);
	
	public int addYiMeiOverdue(YimeiOverdueinfo YiMeiOverdue);
	
	public void updateYiMeiOverdueVo(YimeiOverdueinfo YiMeiOverdue);
	
	public int updateYiMeiOverdue(YimeiOverdueinfo YiMeiOverdue);
	
	public int deleteYiMeiOverdue(String phone);
	

}
