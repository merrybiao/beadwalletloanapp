package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;


import com.waterelephant.entity.WsmOrderPush;
import com.waterelephant.service.IWsmOrderPushService;
@Service
public class WsmOrderPushService extends BaseCommonServiceImpl<WsmOrderPush, Long> implements IWsmOrderPushService {

	@Override
	public WsmOrderPush findWsmOrderByAttr(WsmOrderPush wsmOrderPush) {
		return mapper.selectOne(wsmOrderPush);
	}

	@Override
	public WsmOrderPush findWsmOrderByWsmOrderNo(String wsmOrderNo) {
		String sql="select * from wsm_order_push where wsm_order_no ="+ wsmOrderNo;
		return sqlMapper.selectOne(sql, WsmOrderPush.class);
	}

	@Override
	public boolean addWsmOrder(WsmOrderPush wsmOrderPush) {
		int count = mapper.insert(wsmOrderPush);
		return count>0 ? true :false;
	}

	@Override
	public int deleteWsmOrder(WsmOrderPush wsmOrderPush) {
		return mapper.delete(wsmOrderPush);
	}

	@Override
	public int updateWsmOrderSelective(WsmOrderPush wsmOrderPush) {
		return mapper.updateByPrimaryKeySelective(wsmOrderPush);
	}

}
