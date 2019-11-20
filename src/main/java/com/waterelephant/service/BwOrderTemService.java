package com.waterelephant.service;

import com.waterelephant.entity.BwOrderTem;

public interface BwOrderTemService {

	// 添加
	public int save(BwOrderTem bwOrderTem);

	// 根据电话号码查询
	public BwOrderTem getByPhonne(String phone, String channelKey);

	/**
	 * @author 崔雄健
	 * @date 2017年3月13日
	 * @description
	 * @param
	 * @return
	 */
	public void deleteBwOrderTem(BwOrderTem bwOrderTem);

}
