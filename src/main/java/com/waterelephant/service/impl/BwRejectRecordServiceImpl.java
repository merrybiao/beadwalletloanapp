package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwReconsider;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.exception.BusException;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.MyDateUtils;

import cn.jpush.api.utils.StringUtils;

/**
 * 认证被拒记录
 * 
 * @author duxiaoyong
 *
 */
@Service
public class BwRejectRecordServiceImpl extends BaseService<BwRejectRecord, Long> implements BwRejectRecordService {

	@Override
	public BwRejectRecord findBwRejectRecordByAtta(BwRejectRecord record) {
		String sql = "SELECT * FROM bw_reject_record r WHERE r.order_id = #{orderId} ORDER BY r.create_time DESC LIMIT 1";
		return sqlMapper.selectOne(sql, record.getOrderId(), BwRejectRecord.class);
	}

	@Override
	public int save(BwRejectRecord record) {
		int result = mapper.insert(record);
		// 更新工单状态
		String sql = "update bw_order set status_id = 7 where id = " + record.getOrderId();

		sqlMapper.update(sql);
		// 更新借款人状态
		String sql2 = "select borrower_id from bw_order where id = #{orderId}";
		Map<String, Object> res = sqlMapper.selectOne(sql2, record.getOrderId());
		String sql3 = "update bw_borrower set auth_step = 1 where id = "
				+ Long.parseLong(res.get("borrower_id").toString());
		sqlMapper.update(sql3);
		return result;
	}

	@Override
	public int saveBwRejectRecord(BwRejectRecord record, Long borrow_id) {
		int result = mapper.insert(record);
		// 更新借款人状态
		String sql = "update bw_borrower set auth_step = 1 where id = " + borrow_id;
		sqlMapper.update(sql);
		return result;
	}

	@Override
	public int findRejectRecordByPhone(String phone) {
		String sql1 = "select b.id from bw_borrower b where b.phone='" + phone + "'";
		String borrowerId = sqlMapper.selectOne(sql1, String.class);
		if (CommUtils.isNull(borrowerId)) {
			return 0;
		}
		String sql2 = "select o.id from bw_order o where o.borrower_id=" + borrowerId
				+ "  and product_type = 1  order by o.create_time desc";
		List<BwOrder> listOrder = sqlMapper.selectList(sql2, BwOrder.class);
		if (CommUtils.isNull(listOrder)) {
			return 0;
		}

		for (BwOrder bwOrder : listOrder) {
			String sql3 = "select r.reject_type,r.create_time from bw_reject_record r where r.order_id ="
					+ bwOrder.getId() + " order by r.reject_type asc ,r.create_time desc limit 1";
			BwRejectRecord bwRejectRecord = sqlMapper.selectOne(sql3, BwRejectRecord.class);

			if (CommUtils.isNull(bwRejectRecord)) {
				return 0;
			}
			if (bwRejectRecord.getRejectType() == 0) {
				return 1;
			} else {
				if (!CommUtils.isNull(bwRejectRecord.getCreateTime())) {
					try {
						int daySpace = MyDateUtils.getDaySpace(bwRejectRecord.getCreateTime(), new Date());
						if (daySpace <= 7) {
							return 1;
						}
					} catch (Exception e) {

					}

				}
			}
		}

		// String sql = "select count(1) from bw_reject_record r LEFT JOIN bw_order o ON r.order_id =o.id LEFT JOIN
		// bw_borrower b "
		// + "ON o.borrower_id=b.id where b.phone=#{phone} AND r.reject_type=0 or(r.reject_type=1 AND TO_DAYS(NOW()) "
		// + "- TO_DAYS(data_Build_Time) < 60)";
		return 0;
	}

	@Override
	public List<BwReconsider> reconsider(String orderId) throws BusException {
		if (StringUtils.isEmpty(orderId)) {
			throw new BusException("工单ID为空");
		}
		String sql = "select * from bw_reconsider where order_id='" + orderId + "'";
		return sqlMapper.selectList(sql, BwReconsider.class);
	}

	@Override
	public List<BwRejectRecord> queryRecord(String orderId) {
		String sql = "select * from bw_reject_record where order_id = '" + orderId + "'";
		return sqlMapper.selectList(sql, BwRejectRecord.class);
	}

	@Override
	public void add(BwRejectRecord bwRejectRecord) {
		bwRejectRecord.setId(null);
		mapper.insert(bwRejectRecord);

	}

	@Override
	public int insert(BwRejectRecord bwRejectRecord) {
		String sql = "INSERT into bw_reject_record(reject_info,order_id,create_time,reject_type) VALUES('"
				+ bwRejectRecord.getRejectInfo() + "'," + "" + bwRejectRecord.getOrderId() + ",now(),"
				+ bwRejectRecord.getRejectType() + ")";
		return sqlMapper.insert(sql);
	}

	@Override
	public Date findCreateTimeByOrderId(Long orderId) {
		String sql = "select r.create_time from bw_reject_record r where r.order_id=#{orderId} ORDER BY r.create_time DESC LIMIT 1;";
		return sqlMapper.selectOne(sql, orderId, Date.class);
	}

	@Override
	public BwRejectRecord findBwRejectRecordByBid(Long borrowerId) {
		String sql = "SELECT id,create_time,reject_type FROM bw_reject_record t WHERE t.order_id IN (SELECT id FROM bw_order WHERE borrower_id = #{borrowerId}) ORDER BY t.create_time desc LIMIT 1";
		return sqlMapper.selectOne(sql, borrowerId, BwRejectRecord.class);
	}

	@Override
	public BwRejectRecord findBwRejectRecordByOrderId(Long orderId) {
		BwRejectRecord record = new BwRejectRecord();
		record.setOrderId(orderId);
		record = findBwRejectRecordByAtta(record);
		return record;
	}
}