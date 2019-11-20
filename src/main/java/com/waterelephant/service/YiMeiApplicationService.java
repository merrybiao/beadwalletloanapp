/**
 * @author heqiwen
 * @date 2016年12月14日
 */
package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.YimeiApplicationinfo;



/**
 * @author Administrator
 *
 */
public interface YiMeiApplicationService {

	public List<YimeiApplicationinfo> getYiMeiApplicationByPhone(String phone);
	
	public void addYiMeiApplicationVo(YimeiApplicationinfo yiMeiApplication);
	
	public int addYiMeiApplication(YimeiApplicationinfo yiMeiApplication);
	
	public void updateYiMeiApplicationVo(YimeiApplicationinfo yiMeiApplication);
	
	public int updateYiMeiApplication(YimeiApplicationinfo yiMeiApplication);
	
	public int deleteYiMeiApplication(String phone);
	
	
}
