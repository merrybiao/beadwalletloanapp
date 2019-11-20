
package com.waterelephant.shengtian.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.service.BaseService;
import com.waterelephant.shengtian.entity.BwCountUV;
import com.waterelephant.shengtian.service.CountAccessNumService;

/**
 * ClassName:CountAccessNumServiceImpl <br/>
 * Function: TODO  <br/>
 * Date:     2018年5月10日 上午11:00:46 <br/>
 * @author   liwanliang
 * @version   1.0
 * @since    JDK 1.7
 * @see 	 
 */

@Service
public class CountAccessNumServiceImpl extends BaseService<BwCountUV, Long> implements CountAccessNumService {

	
	@Override
	public void insertAccessNumRecord(BwCountUV countUV) {
		String sql = "INSERT INTO bw_count_uv (user_ip,cookie_val,create_time) VALUES (#{userIp},#{cookieVal},#{createTime})";
		sqlMapper.insert(sql , countUV);
	}

}

