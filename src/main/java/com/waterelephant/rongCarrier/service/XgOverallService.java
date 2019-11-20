package com.waterelephant.rongCarrier.service;

import com.waterelephant.rongCarrier.entity.XgOverall;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 16:45
 */
public interface XgOverallService {

	public boolean save(XgOverall xgOverall);

	public boolean deleteZ(XgOverall xgOverall);

	/**
	 * @author 崔雄健
	 * @date 2017年5月18日
	 * @description
	 * @param
	 * @return
	 */
	XgOverall findXgOverall(Long borrowerId);

	/**
	 * @author 崔雄健
	 * @date 2017年5月18日
	 * @description
	 * @param
	 * @return
	 */
	void updateXgOverall(XgOverall xgOverall);

}
