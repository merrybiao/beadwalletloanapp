package com.waterelephant.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwCheckRecord;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwCheckRecordService;
import com.waterelephant.service.BwRejectRecordService;

/**
 * 
 * @ClassName: BwCheckRecordServiceImpl
 * @Description: TODO(工单审核记录实体类)
 * @author SongYajun
 * @date 2016年8月27日 下午9:14:54
 *
 */
@Service
public class BwCheckRecordServiceImpl extends BaseService<BwCheckRecord, Long> implements BwCheckRecordService {

	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private BwBorrowerService borrowerService;

	@Override
	public int saveBwCheckRecord(BwOrder bwOrder, BwCheckRecord bwCheckRecord, String result) {
		if (result.equals("0") || result.equals("1")) {
			bwOrder.setStatusId(7L);
			bwCheckRecord.setResult(7);
			BwRejectRecord bwRejectRecord = new BwRejectRecord();
			bwRejectRecord.setRejectInfo(bwCheckRecord.getComment()); // 被拒信息
			bwRejectRecord.setOrderId(bwCheckRecord.getOrderId()); // 工单编号
			bwRejectRecord.setRejectType(Integer.valueOf(result));
			bwOrder.setRejectType(1);
			bwRejectRecordService.insert(bwRejectRecord);
			String str = "UPDATE bw_borrower set  auth_step='1' ,update_time=now() where id=(select borrower_id from bw_order where id='"
					+ bwOrder.getId() + "' and borrower_id is not null  ) ";
			sqlMapper.update(str);
		} else {
			bwOrder.setStatusId(Long.valueOf(result));
			bwCheckRecord.setResult(Integer.valueOf(result));
		}
		// 更新工单
		String sql = "UPDATE bw_order set status_id = #{statusId} ,credit_limit=#{creditLimit},borrow_amount=#{creditLimit},update_time=#{updateTime} ,reject_type=#{rejectType} where id =#{id}";
		int updateNum = sqlMapper.update(sql, bwOrder);
		// 添加工单记录
		int insertNum = mapper.insert(bwCheckRecord);
		// 如果结果为7：表示副理拒绝,需要录入工单拒绝数据
		if (updateNum > 0 && insertNum > 0) {
			return updateNum;
		}
		return 0;
	}

	@Override
	public int saveCardinal(String grade, String limit, String id) {
		String sql = "UPDATE bw_order set mark ='" + grade + "',credit_limit ='" + limit + "' where id ='" + id + "'";
		return sqlMapper.update(sql);
	}

	@Override
	public List<BwCheckRecord> queryCheck(String orderId) {
		String sql = "select b.* from bw_check_record b where b.order_id =" + orderId + " order by b.create_time desc";
		return sqlMapper.selectList(sql, BwCheckRecord.class);
	}

	@Override
	public int addBwCheckRecordUpOrder(BwOrder bwOrder, BwCheckRecord bwCheckRecord, String result) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if (result.equals("0") || result.equals("2")) {
			bwOrder.setStatusId(7L);
			bwCheckRecord.setResult(7);
			BwRejectRecord bwRejectRecord = new BwRejectRecord();
			bwRejectRecord.setRejectInfo(bwCheckRecord.getComment()); // 被拒信息
			bwRejectRecord.setOrderId(bwCheckRecord.getOrderId()); // 工单编号
			bwRejectRecord.setCreateTime(time);// 被拒时间
			if (result.equals("0")) {
				bwRejectRecord.setRejectType(0);
			} else {
				bwRejectRecord.setRejectType(1);
			}
			bwOrder.setRejectType(1); // 系统拒绝
			String str = "UPDATE bw_borrower set  auth_step='1' ,update_time=now() where id=(select borrower_id from bw_order where id='"
					+ bwOrder.getId() + "' and borrower_id is not null  ) ";
			sqlMapper.update(str);
			bwRejectRecordService.insert(bwRejectRecord);
		} else {
			bwOrder.setStatusId(Long.valueOf(result));
			bwCheckRecord.setResult(Integer.valueOf(result));
		}

		// 更新工单状体、额度
		String sql = "UPDATE bw_order set status_id =#{statusId},credit_limit=#{creditLimit},"
				+ "borrow_amount=#{creditLimit},update_time=#{updateTime},reject_type=#{rejectType} where id =#{id}";
		int updateNum = sqlMapper.update(sql, bwOrder);
		// 添加工单记录
		int insertNum = mapper.insert(bwCheckRecord);
		if (updateNum > 0 && insertNum > 0) {
			return updateNum;
		}
		return 0;
	}

	@Override
	public void updateOrderRefused(BwCheckRecord bwCheckRecord) {
		// 更新工单状态
		String sql = "UPDATE bw_order set status_id =" + bwCheckRecord.getResult() + "," + "update_time ='"
				+ bwCheckRecord.getCreateTime() + "' where id =" + bwCheckRecord.getOrderId() + "";
		// System.out.println(sql);
		sqlMapper.update(sql);
		// 添加工单记录
		mapper.insert(bwCheckRecord);
		// 客户拒绝更改借款人认证状态
		borrowerService.updateBorrwerAuthStep(bwCheckRecord.getOrderId());

	}

	@Override
	public void add(BwCheckRecord bwCheckRecord) {
		bwCheckRecord.setId(null);
		mapper.insert(bwCheckRecord);

	}

	@Override
	public int isThrough(String orderId) {
		String sql = "select b.* from bw_check_record b where b.order_id =" + orderId
				+ "  and ( b.status_id =7 or b.status_id =8)";
		List<BwCheckRecord> bwCheckRecords = sqlMapper.selectList(sql, BwCheckRecord.class);
		if (bwCheckRecords.size() > 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public Date findCreateTimeByOrderId(Long orderId) {
		String sql = "select c.create_time from bw_check_record c where c.order_id=#{orderId} AND c.result IN(7,8) LIMIT 1";
		return sqlMapper.selectOne(sql, orderId, Date.class);
	}

	@Override
	public Date findCreateTimeByOrderId(BwCheckRecord bwCheckRecord) {
		String sql = "select c.create_time from bw_check_record c where c.order_id=#{orderId} AND c.result=#{result} AND c.status_id=#{statusId} LIMIT 1";
		return sqlMapper.selectOne(sql, bwCheckRecord, Date.class);
	}

	@Override
	public BwCheckRecord findNewWithdrawByOrderId(String orderId) {
		String sql = "select * from bw_check_record where order_id = #{orderId} and result = 8 order by create_time desc limit 1 ";
		return sqlMapper.selectOne(sql, orderId, BwCheckRecord.class);
	}
}
