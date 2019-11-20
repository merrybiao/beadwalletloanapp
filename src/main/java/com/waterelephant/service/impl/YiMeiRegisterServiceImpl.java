/**
 * @author heqiwen
 * @date 2016年12月14日
 */
package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.YimeiRegisterinfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.YiMeiRegisterService;



/**
 * @author heqiwen
 *
 */
@Service
public class YiMeiRegisterServiceImpl extends BaseService<YimeiRegisterinfo,Long> implements YiMeiRegisterService{

	
	/** 
	 * @author heqiwen
	 * @date 2016年12月14日
	 */
	public List<YimeiRegisterinfo> getYiMeiRegisterByPhone(String phone) {
		YimeiRegisterinfo register=new YimeiRegisterinfo();
		register.setPhone(phone);
		return mapper.select(register);
	}
	
	/**
	 * 
	 * @author heqiwen
	 * @date 2016年12月18日
	 */
	public int deleteYiMeiRegister(String phone){
		YimeiRegisterinfo register=new YimeiRegisterinfo();
		register.setPhone(phone);
		return mapper.delete(register);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月14日
	 */
	public void addYiMeiRegisterVo(YimeiRegisterinfo YiMeiRegister) {
		mapper.insert(YiMeiRegister);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月14日
	 */
	public int addYiMeiRegister(YimeiRegisterinfo YiMeiRegister) {
		return mapper.insert(YiMeiRegister);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月14日
	 */
	public void updateYiMeiRegisterVo(YimeiRegisterinfo YiMeiRegister) {
		mapper.updateByExample(YiMeiRegister, YimeiRegisterinfo.class);
		
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月14日
	 */
	public int updateYiMeiRegister(YimeiRegisterinfo YiMeiRegister) {
		return mapper.updateByPrimaryKey(YiMeiRegister);
	}
	
	
	


}
