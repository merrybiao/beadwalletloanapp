package com.waterelephant.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrder;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.ITempService;
import com.waterelephant.utils.MyDateUtils;

@Service
public class TempServiceImpl extends BaseService<BwOrder, Long> implements ITempService {
	private Logger logger = Logger.getLogger(TempServiceImpl.class);

	@Override
	public Long findTodayOrderCount() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = format.format(new Date());
		String sql = "select count(id) from bw_order o where o.create_time >= '" + strDate
				+ " 00:00:00' and o.create_time <= '" + strDate + " 23:59:59'";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public Double findTodayOrderAmount() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = format.format(new Date());
		String sql = "SELECT SUM(r.reality_repay_money) FROM bw_order_push_info p JOIN bw_repayment_plan r ON p.order_id = r.order_id WHERE p.push_status = 2 and r.rollover_number=0 and r.create_time >= '"
				+ strDate + " 00:00:00' and r.create_time <= '" + strDate + " 23:59:59'";
		return sqlMapper.selectOne(sql, Double.class);
	}

	@Override
	public Long findSignOrderCount() {
		String sql = "select count(*) from bw_order o where o.status_id = 4 ";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public Long findNotPushOrderCount() {
		String sql = "select count(*) from bw_order_push_info p where p.push_status = 1 or (p.push_status = 0 and p.push_remark = '借款申请失败：当前可放款额度不足，请稍后再试！')";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public List<Map<String, Object>> findWeekOrderCount() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date end = MyDateUtils.addDays(new Date(), -7);
		String startTime = format.format(end);
		String endTime = format.format(new Date());
		String sql = "SELECT date(o.create_time) as create_time ,count(*) as c FROM bw_order o WHERE o.create_time >= '"
				+ startTime + " 00:00:00' and o.create_time<= '" + endTime + " 23:59:59' GROUP BY date(o.create_time);";
		return sqlMapper.selectList(sql);
	}

	@Override
	public Long findCheckOrderCount() {
		String sql = "select count(*) from bw_order o where o.status_id = 2 ";
		return sqlMapper.selectOne(sql, Long.class);
	}

	@Override
	public Long findRepayOrderCount() {
		String sql = "select count(*) from bw_repayment_plan p ";
		return sqlMapper.selectOne(sql, Long.class);
	}

}