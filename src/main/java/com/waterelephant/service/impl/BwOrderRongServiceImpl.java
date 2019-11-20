package com.waterelephant.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderRongService;

@Service
public class BwOrderRongServiceImpl extends BaseService<BwOrderRong, Long> implements BwOrderRongService {

	@Override
	public int save(BwOrderRong bwOrderRong) {
		return mapper.insert(bwOrderRong);
	}

	@Override
	public BwOrderRong getByOrderNo(String orderNo) {
		String sql = "select * from bw_order_rong where third_order_no = #{orderNo}";
		return sqlMapper.selectOne(sql, orderNo, BwOrderRong.class);
	}

	@Override
	public BwOrderRong findBwOrderRongByOrderIdAndChannel(Long orderId, Long channelId) {
		String sql = "select o.* from bw_order_rong o where o.order_id=" + orderId + " and o.channel_id=" + channelId
				+ " LIMIT 1";
		return sqlMapper.selectOne(sql, BwOrderRong.class);
	}

	@Override
	public BwOrderRong findBwOrderRongByAttr(BwOrderRong bwOrderRong) {
		return mapper.selectOne(bwOrderRong);
	}

	@Override
	public BwOrderRong findBwOrderRongByOrderId(Long orderId) {
		String sql = "select * from bw_order_rong o where o.order_id=" + orderId + " LIMIT 1";
		return sqlMapper.selectOne(sql, BwOrderRong.class);
	}

	@Override
	public String findThirdOrderNoByOrderId(String orderId) {
		String sql = "select o.third_order_no from bw_order_rong o where o.order_id=" + orderId + " LIMIT 1";
		return sqlMapper.selectOne(sql, String.class);
	}

	@Override
	public int findThirdOrderNoCount(String thirdOrderNo, int channelId) {
		String sql = "select COUNT(id) from bw_order_rong o where o.third_order_no=" + thirdOrderNo
				+ " AND o.channel_id=" + channelId + " LIMIT 1";
		return sqlMapper.selectOne(sql, Integer.class);
	}

	@Override
	public int updateBwOrderRongNo(BwOrderRong bwOrderRong) {
		String sql = "update bw_order_rong set third_order_no='" + bwOrderRong.getThirdOrderNo() + "' , channel_id='"
				+ bwOrderRong.getChannelId() + "' where order_id='" + bwOrderRong.getOrderId() + "'";
		return sqlMapper.update(sql);
	}

	@Override
	public List<Map<String, Object>> btsj() {
		String sql = "select phone as userId ,searchid as search_id from bw_test";
		return sqlMapper.selectList(sql);
	}

	/**
	 * 修改融360订单（code0091）
	 * 
	 * @see com.waterelephant.service.BwOrderRongService#update(com.waterelephant.entity.BwOrderRong)
	 * @author liuDaodao
	 */
	@Override
	public int update(BwOrderRong bwOrderRong) {
		return mapper.updateByPrimaryKey(bwOrderRong);
	}

}
