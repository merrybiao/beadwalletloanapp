package com.waterelephant.rongCarrier.jd.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.jd.entity.ShippingAddrs;
import com.waterelephant.rongCarrier.jd.service.ShippingAddrsService;
import com.waterelephant.service.BaseService;

@Service
public class ShippingAddrsServiceImpl extends BaseService<ShippingAddrs, Long>
		implements ShippingAddrsService {

	@Override
	public boolean saveShippingAddrs(ShippingAddrs shippingAddrs) {
		return mapper.insert(shippingAddrs) > 0;
	}

	@Override
	public boolean updateShippingAddrs(ShippingAddrs shippingAddrs) {
		return mapper.updateByPrimaryKey(shippingAddrs) > 0;
	}

	@Override
	public ShippingAddrs queryShippingAddrs(Long borrowerId, String addrId) {
		String sql = "select * from bw_jd_shipping_addrs a where a.borrower_id = " + borrowerId + " and a.addr_id = '" + addrId +"'";
		ShippingAddrs shippingAddrs = sqlMapper.selectOne(sql, ShippingAddrs.class);
		return shippingAddrs;
	}

}
