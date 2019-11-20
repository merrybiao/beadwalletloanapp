package com.waterelephant.service;



import com.waterelephant.entity.WsmOrderPush;


public interface IWsmOrderPushService extends BaseCommonService<WsmOrderPush, Long>{

	WsmOrderPush findWsmOrderByAttr(WsmOrderPush wsmOrderPush);
	
	WsmOrderPush findWsmOrderByWsmOrderNo(String wsmOrderNo);
	
	boolean addWsmOrder(WsmOrderPush wsmOrderPush);
	
	int deleteWsmOrder(WsmOrderPush wsmOrderPush);
	
	int updateWsmOrderSelective(WsmOrderPush wsmOrderPush);
}
