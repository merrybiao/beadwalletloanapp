package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrderTem;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderTemService;

@Service
public class BwOrderTemServiceImpl extends BaseService<BwOrderTem, Long> implements BwOrderTemService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.waterelephant.service.BwOrderTemService#save(com.waterelephant.entity.BwOrderTem)
	 */
	@Override
	public int save(BwOrderTem bwOrderTem) {
		return mapper.insert(bwOrderTem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.waterelephant.service.BwOrderTemService#getByPhonne(java.lang.String, java.lang.String)
	 */
	@Override
	public BwOrderTem getByPhonne(String phone, String channelKey) {
		String sql = "select * from bw_order_tem where phone = '" + phone + "' AND channel_key='" + channelKey
				+ "' order by create_time desc LIMIT 1";
		return sqlMapper.selectOne(sql, BwOrderTem.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.waterelephant.service.BwOrderTemService#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteBwOrderTem(BwOrderTem bwOrderTem) {
		mapper.delete(bwOrderTem);
	}

}
