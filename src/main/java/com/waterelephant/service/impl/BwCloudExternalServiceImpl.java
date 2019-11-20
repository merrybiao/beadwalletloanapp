package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCloudExternal;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCloudExternalService;

@Service
public class BwCloudExternalServiceImpl extends BaseService<BwCloudExternal, Long> implements BwCloudExternalService {

	@Override
	public void saveBwCloudExternal(BwCloudExternal bwCloudExternal) {
		mapper.insert(bwCloudExternal);
	}

	@Override
	public void updateBwCloudExternal(BwCloudExternal bwCloudExternal) {
		mapper.updateByPrimaryKey(bwCloudExternal);
	}

	@Override
	public BwCloudExternal findBwCloudExternalByOrderId(String orderId, int source) {
		String sql = "select * from bw_cloud_external where order_id = " + orderId + " and type = 0 and source = "
				+ source + " order by id desc limit 1";
		return sqlMapper.selectOne(sql, BwCloudExternal.class);
	}

	@Override
	public BwCloudExternal findBwCloudExternalByExternalNo(String externalNo, int source) {
		String sql = "select * from bw_cloud_external where external_no = '" + externalNo + "'  and source = " + source
				+ " order by id desc limit 1";
		return sqlMapper.selectOne(sql, BwCloudExternal.class);
	}

}
