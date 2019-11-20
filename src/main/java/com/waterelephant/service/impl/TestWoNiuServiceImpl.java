package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.WoNiuOrder;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.TestWoNiuService;

@Service
public class TestWoNiuServiceImpl extends BaseService<WoNiuOrder, Long> implements TestWoNiuService {
	
	@Override
	public WoNiuOrder findWoNiuByAttr(WoNiuOrder woNiuOrder) {
		return mapper.selectOne(woNiuOrder);
	}

	@Override
	public WoNiuOrder findWoNiuByThirdNo(String thirdNo) {
		String sql="select * from wn_oherder_push where third_no ="+thirdNo;
		return sqlMapper.selectOne(sql, WoNiuOrder.class);
	}

	@Override
	public boolean addWoNiuOrder(WoNiuOrder woNiuOrder) {
		int count = mapper.insert(woNiuOrder);
		return count>0 ? true :false;
	}

	@Override
	public int deleteWoNiuOrder(WoNiuOrder woNiuOrder) {
		return mapper.delete(woNiuOrder);
	}

	@Override
	public int updateWoNiuOrderSelective(WoNiuOrder woNiuOrder) {
		return mapper.updateByPrimaryKeySelective(woNiuOrder);
	}
	
	
}
