
package com.waterelephant.shengtian.service;

import com.waterelephant.shengtian.entity.BwCountUV;

/**
 * ClassName:CountAccessNumService <br/>
 * Function: TODO  <br/>
 * Date:     2018年5月10日 上午11:00:22 <br/>
 * @author   liwanliang
 * @version   1.0
 * @since    JDK 1.7
 * @see 	 
 */


public interface CountAccessNumService {

	/**
	 * insertAccessNumRecord:向统计UV表中插入数据<br/>
	 * 
	 * @author   liwanliang
	 * @param countUV
	 * @since JDK 1.7
	 */
	void insertAccessNumRecord(BwCountUV countUV);
	
}

