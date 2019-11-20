package com.waterelephant.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.entity.BwBorrowerWealth;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwBorrowerWealthService;
import com.waterelephant.utils.StringUtil;

@Service
public class BwBorrowerWealthServiceImpl extends BaseService<BwBorrowerWealth, Long>
		implements BwBorrowerWealthService {
	private Logger logger = Logger.getLogger(BwBorrowerWealthServiceImpl.class);

	@Override
	public Integer getUserRankByBorrowerId(String borrowerId) {

		String wealthSql = "select  wealth from bw_borrower_wealth where borrower_id = " + borrowerId + " ";
		// 正常结清本金（无续贷）
		String nString = "select COUNT(1) from bw_order o where borrower_id =  " + borrowerId
				+ "   and status_id = 6 and o.id not in (select xd.order_id from bw_order_xudai xd where  xd.original_order_id is not null) and o.id not in (select xd.original_order_id from bw_order_xudai xd where  xd.original_order_id is not null) "
				+ " and product_type = 1 ";
		// 续贷后结清本金
		String xString = " select COUNT(1)  from bw_order o  left JOIN bw_order_xudai xd on o.id = xd.original_order_id where o.borrower_id =  "
				+ borrowerId + "  and xd.settle_status=1  and product_type = 1  ";

		// 查询财富值
		Double wealth = sqlMapper.selectOne(wealthSql, Double.class);

		// 查询归还本金次数
		Integer count = sqlMapper.selectOne(nString, Integer.class) + sqlMapper.selectOne(xString, Integer.class);

		Integer rank = 1; // 用户等级
		if (null == wealth) {
			logger.info("没有查到该用户财富值,默认为1星用户");
			return rank;
		}
		if (wealth < 0) {
			logger.info("该用户财富值为负数,默认为0星用户");
			rank = 0;
		}
		if (wealth >= 0 && wealth <= 379) {
			rank = 1;
		}
		if (wealth > 379 && wealth <= 580) {
			rank = 2;
		}
		if (wealth > 580) {
			rank = 3;
		}
		if (wealth > 850 && count >= 5) {
			rank = 4;
		}
		if (wealth > 1500 && count >= 8) {
			rank = 5;
		}
		if (wealth > 2500 && count >= 14) {
			rank = 6;
		}
		return rank;
	}

	/**
	 * 统计更新个人财富值
	 * 
	 * @see com.waterelephant.service.BwBorrowerWealthService#saveCountWealth()
	 */
	@Override
	public void saveCountWealth(Integer BorrowerId) {
		try {
			// 删除财富记录
			deleteAllBwBorrowerWealth(BorrowerId);
			BwBorrowerWealth bwBorrowerWealt = new BwBorrowerWealth();
			// 查询本金加利息产生的财富值
			BwBorrowerWealth pL = selectWealthForPrincipal(BorrowerId);
			logger.info("本金加利息产生的财富值查询成功================");
			// 查询续贷产生的财富值
			BwBorrowerWealth xudai = selectWealthForXudai(BorrowerId);
			logger.info("续贷产生的财富值查询成功================");
			// 查询逾期扣除的财富值
			BwBorrowerWealth overdue = selectWealthForOverdue(BorrowerId);
			logger.info("逾期扣除的财富值查询成功================");
			// 查询邀请产生的财富值
			BwBorrowerWealth invite = selectWealthForInvite(BorrowerId);
			logger.info("邀请产生的财富值查询成功================");

			bwBorrowerWealt.setBorrowerId(BorrowerId);
			bwBorrowerWealt.setWealth(0.00);
			if (!CommUtils.isNull(pL)) {
				bwBorrowerWealt.setWealth(pL.getWealth() + bwBorrowerWealt.getWealth());
			}
			if (!CommUtils.isNull(xudai)) {
				bwBorrowerWealt.setWealth(xudai.getWealth() + bwBorrowerWealt.getWealth());
			}
			if (!CommUtils.isNull(overdue)) {
				bwBorrowerWealt.setWealth(bwBorrowerWealt.getWealth() - overdue.getWealth());
			}
			if (!CommUtils.isNull(invite)) {
				bwBorrowerWealt.setWealth(invite.getWealth() + bwBorrowerWealt.getWealth());
			}

			saveBorrowerWealth(bwBorrowerWealt);
			logger.info("统计更新财富值成功===============");
		} catch (Exception e) {
			logger.info("统计财富值失败===============");
		}

	}

	/**
	 * 根据借款人Id查询财富值记录
	 * 
	 * @see com.waterelephant.service.BwBorrowerWealthService#selectBwBorrowerWealth(java.lang.String)
	 */
	@Override
	public BwBorrowerWealth selectBwBorrowerWealth(Integer borrowerId) {
		BwBorrowerWealth record = new BwBorrowerWealth();
		record.setBorrowerId(borrowerId);
		return mapper.selectOne(record);
	}

	/**
	 * 保存或更新借款人财富值记录
	 * 
	 * @see com.waterelephant.service.BwBorrowerWealthService#saveOrUpateBorrowerWealth(com.waterelephant.entity.BwBorrowerWealth)
	 */
	@Override
	public void saveBorrowerWealth(BwBorrowerWealth entity) {
		// 创建时间 更新时间
		entity.setUpdateTime(new Date());
		entity.setCreateTime(new Date());
		mapper.insert(entity);
	}

	/**
	 * 根据用户ID查询本金加利息产生的财富值（归还本金的金额 每1元 = 0.1 财富值 ，借贷的利息 每1元 = 0.5 财富值）
	 * 
	 * @see com.waterelephant.service.BwBorrowerWealthService#selectWealthForPrincipal()
	 */
	@Override
	public BwBorrowerWealth selectWealthForPrincipal(Integer BorrowerId) {
		BwBorrowerWealth bwBorrowerWealth = sqlMapper.selectOne(
				"select borrower_id,sum(borrow_amount*(0.1+0.18*0.5)) wealth from bw_order where borrower_id="
						+ BorrowerId
						+ " and status_id=6 and create_time >'2016-12-12 00:00:00' and id not in(select order_id from bw_order_xudai) limit 0,1",
				BwBorrowerWealth.class);

		return bwBorrowerWealth;
	}

	/**
	 * 根据用户ID查询续贷产生的财富值（归还利息+展期的金额 每1元 = 0.3 财富值）
	 * 
	 * @return
	 */
	@Override
	public BwBorrowerWealth selectWealthForXudai(Integer BorrowerId) {
		BwBorrowerWealth bwBorrowerWealth = sqlMapper
				.selectOne(
						"select o.borrower_id,sum(borrow_amount*(0.18*0.3+0.1)) wealth from bw_order_xudai bo left join bw_order o on bo.order_id=o.id where o.borrower_id ="
								+ BorrowerId + " and bo.create_time >'2016-12-12 00:00:00' limit 0,1",
						BwBorrowerWealth.class);
		return bwBorrowerWealth;
	}

	/**
	 * 根据用户ID查询逾期天数扣除的财富值 （逾期1-10天：每天减2财富值 逾期11-20天：每天减5财富值 逾期21-30天：每天减8财富值 逾期31+：每天减10财富值 ）
	 * 
	 * @see com.waterelephant.service.BwBorrowerWealthService#selectWealthForOverdue()
	 */
	@Override
	public BwBorrowerWealth selectWealthForOverdue(Integer BorrowerId) {
		Map<String, Object> map = sqlMapper.selectOne(
				"select o.borrower_id,sum(overdue_day) overdue_day from bw_overdue_record bo left join bw_order o on bo.order_id=o.id  where  o.borrower_id="
						+ BorrowerId + " and  bo.create_time >'2016-12-12 00:00:00' LIMIT 0,1");
		BwBorrowerWealth borrowerWealth = new BwBorrowerWealth();
		if (!CollectionUtils.isEmpty(map)) {

			borrowerWealth.setBorrowerId(StringUtil.toInteger(map.get("borrower_id")));
			// 逾期天数
			Integer overdueDay = StringUtil.toInteger(map.get("overdue_day"));
			Integer reduceWealth = reduceWealth(overdueDay);
			borrowerWealth.setBorrowerId(StringUtil.toInteger(map.get("borrower_id")));
			borrowerWealth.setWealth(Double.valueOf(reduceWealth.toString()));

		} else {
			return null;
		}
		return borrowerWealth;

	}

	private Integer reduceWealth(Integer overdueDay) {
		int reduceWealth = 0;
		if (!StringUtil.isEmpty(overdueDay)) {
			int day = overdueDay.intValue();
			// 逾期1-10天：每天减2财富值
			if (day >= 1 && day <= 10) {
				reduceWealth = day * 2;
			}
			// 逾期11-20天：每天减5财富值
			if (day >= 11 && day <= 20) {
				reduceWealth = 10 * 2 + (day - 10) * 5;
			}
			// 逾期21-30天：每天减8财富值
			if (day >= 21 && day <= 30) {
				reduceWealth = 10 * 2 + 10 * 5 + (day - 10 - 10) * 8;
			}
			// 逾期31+：每天减10财富值
			if (day >= 31) {
				reduceWealth = 10 * 2 + 10 * 5 + 10 * 8 + (day - 10 - 10 - 10) * 10;
			}
		}
		return reduceWealth;
	}

	/**
	 * 查询邀请好友并成功借贷的利息所产生的财富值，每1元 = 0.1财富值
	 */
	@Override
	public BwBorrowerWealth selectWealthForInvite(Integer BorrowerId) {
		BwBorrowerWealth bwBorrowerWealth = sqlMapper.selectOne(
				"SELECT	b.borrower_id,sum(o.borrow_amount * 0.18 * 0.5 ) wealth FROM bw_order o LEFT JOIN bw_borrower b ON o.borrower_id = b.id WHERE b.borrower_id="
						+ BorrowerId
						+ " and status_id = 6  and o.create_time >'2016-12-12 00:00:00' AND o.id NOT IN (SELECT order_id FROM bw_order_xudai) AND b.borrower_id IS NOT NULL limit 0,1",
				BwBorrowerWealth.class);

		return bwBorrowerWealth;
	}

	/**
	 * 根据用户Id清空财富记录
	 */
	private void deleteAllBwBorrowerWealth(Integer BorrowerId) {
		String sql = "delete from bw_borrower_wealth where borrower_id =" + BorrowerId;
		sqlMapper.delete(sql);
		logger.info("删除用户财富记录成功");
	}
}
