package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;


import com.waterelephant.entity.BwOrderRelation;

import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderRelationService;

@Service
public class BwOrderRelationServiceImpl extends BaseService<BwOrderRelation,Long> implements BwOrderRelationService {

	@Override
	public int addBwOrderRelation(Long orderId) {
		return sqlMapper.insert("insert into bw_order_relation(order_id,order_status,allot_status,create_time,update_time) "
				+ "values("+orderId+",1,1,now(),now())");
	}

	@Override
	public BwOrderRelation queryOrderRelation(Long orderId, Long orderStatus) {
		 String sql = "select * from bw_order_relation where order_id = "+orderId+" and order_status = "+orderStatus;
	        return this.sqlMapper.selectOne(sql, BwOrderRelation.class);

	}

	@Override
	public int add(BwOrderRelation orderRelation) {
		// TODO Auto-generated method stub
		return mapper.insert(orderRelation);
	}

	@Override
	public int updateOrderRelation(BwOrderRelation bwOrderRelation) {
		return mapper.updateByPrimaryKey(bwOrderRelation);
		
	}

	@Override
	public BwOrderRelation getOrderRelation(Long orderId, Long orderStatus, Long borrowerId) {
		String sql = "select * from bw_order_relation where order_id = "+orderId+" and order_status = "+orderStatus+" and user_id="+borrowerId;
        return this.sqlMapper.selectOne(sql, BwOrderRelation.class);

	}

}
