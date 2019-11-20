/**
 * @author heqiwen
 * @date 2016年12月14日
 */
package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.YimeiApplicationinfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.YiMeiApplicationService;



/**
 * @author Administrator
 *
 */
@Service
public class YiMeiApplicationServiceImpl extends BaseService<YimeiApplicationinfo,Long> implements YiMeiApplicationService{
	
	public List<YimeiApplicationinfo> getYiMeiApplicationByPhone(String phone){
		YimeiApplicationinfo application=new YimeiApplicationinfo();
		application.setPhone(phone);
		
		return mapper.select(application);
	}
	
	public void addYiMeiApplicationVo(YimeiApplicationinfo yiMeiApplication){
		
	}
	
	public int addYiMeiApplication(YimeiApplicationinfo yiMeiApplication){
		
		return mapper.insert(yiMeiApplication);
	}
	
	public void updateYiMeiApplicationVo(YimeiApplicationinfo yiMeiApplication){
		
		
	}
	
	public int updateYiMeiApplication(YimeiApplicationinfo yiMeiApplication){
		return 1;
	}
	
	public int deleteYiMeiApplication(String phone){
		YimeiApplicationinfo yiMeiApplication=new YimeiApplicationinfo();
		yiMeiApplication.setPhone(phone);
		return mapper.delete(yiMeiApplication);
	}

}
