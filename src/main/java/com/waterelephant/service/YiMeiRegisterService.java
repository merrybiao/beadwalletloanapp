/**
 * @author heqiwen
 * @date 2016年12月14日
 */
package com.waterelephant.service;

import java.util.List;


import com.waterelephant.entity.YimeiRegisterinfo;



/**
 * @author heqiwen
 *
 */
public interface YiMeiRegisterService {
	
	public List<YimeiRegisterinfo> getYiMeiRegisterByPhone(String phone);
	
	public void addYiMeiRegisterVo(YimeiRegisterinfo YiMeiRegister);
	
	public int addYiMeiRegister(YimeiRegisterinfo YiMeiRegister);
	
	public void updateYiMeiRegisterVo(YimeiRegisterinfo YiMeiRegister);
	
	public int updateYiMeiRegister(YimeiRegisterinfo YiMeiRegister);
	
	public int deleteYiMeiRegister(String phone);
	


}
