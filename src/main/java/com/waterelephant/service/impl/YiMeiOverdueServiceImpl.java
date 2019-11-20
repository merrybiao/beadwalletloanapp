/**
 * @author heqiwen
 * @date 2016年12月14日
 */
package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.YimeiOverdueinfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.YiMeiOverdueService;



/**
 * @author Administrator
 *
 */
@Service
public class YiMeiOverdueServiceImpl extends BaseService<YimeiOverdueinfo,Long> implements YiMeiOverdueService{
	
	/** 
	 * @author heqiwen
	 * @date 2016年12月14日
	 * @see com.waterelephant.yimei.service.YiMeiOverdueService#getYiMeiOverdueByPhone(java.lang.String)
	 */
	public List<YimeiOverdueinfo> getYiMeiOverdueByPhone(String phone) {
		YimeiOverdueinfo overdue=new YimeiOverdueinfo();
		overdue.setPhone(phone);
		return mapper.select(overdue);
	}
	
	public void addYiMeiOverdueVo(YimeiOverdueinfo YiMeiOverdue){
		
	}
	
	public int addYiMeiOverdue(YimeiOverdueinfo YiMeiOverdue){
		
		return mapper.insert(YiMeiOverdue);
	}
	
	public void updateYiMeiOverdueVo(YimeiOverdueinfo YiMeiOverdue){
		//mapper.delete(YiMeiOverdue);
		
	}
	
	public int updateYiMeiOverdue(YimeiOverdueinfo YiMeiOverdue){
		String sql = "update yimei_overdueinfo update_time=#{updateTime} where phone=#{phone}";
		return sqlMapper.update(sql,YiMeiOverdue);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月15日
	 */
	public int deleteYiMeiOverdue(String phone) {
		YimeiOverdueinfo YiMeiOverdue=new YimeiOverdueinfo();
		YiMeiOverdue.setPhone(phone);
		return mapper.delete(YiMeiOverdue);
	}

	

}
