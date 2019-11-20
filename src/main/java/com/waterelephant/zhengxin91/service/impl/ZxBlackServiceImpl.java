package com.waterelephant.zhengxin91.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.waterelephant.service.BaseService;
import com.waterelephant.zhengxin91.entity.ZxBlack;
import com.waterelephant.zhengxin91.service.ZxBlackService;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 11:12
 */
@Service
public class ZxBlackServiceImpl extends BaseService<ZxBlack, Long> implements ZxBlackService {

	private Logger logger = Logger.getLogger(ZxBlackServiceImpl.class);

	@Override
	public boolean saveZxBlack(ZxBlack zxBlack) {
		return mapper.insert(zxBlack) > 0;
	}

	@Override
	public boolean updateZxBlackh(ZxBlack zxBlack) {
		return mapper.updateByPrimaryKey(zxBlack) > 0;
	}

	@Override
	public boolean deleteZxBlack(ZxBlack zxBlack) {
		return mapper.delete(zxBlack) > 0;
	}

	@Override
	public List<ZxBlack> queryZxBlack(ZxBlack zxBlack) {
		List<ZxBlack> zxBlackList = null;
		try {
			zxBlackList = mapper.select(zxBlack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zxBlackList;
	}

	@Override
	public List<ZxBlack> queryZxBlack91(String cardId) {
		String sql = "select * from bw_zx_black t " + " where t.source = 20 "
				+ "  and (t.reject_type = 0 or t.reject_type =1) " + " and t.id_card ='" + cardId + "' ";
		return sqlMapper.selectList(sql, ZxBlack.class);
	}

	@Override
	public ZxBlack queryZxBlack(long borrowerId, long orderId, long source, long sourceItem) {

		String sql = "select * from bw_zx_black z where z.borrow_id = " + borrowerId + " and z.order_id = " + orderId
				+ " and z.source= " + source + " and z.source_item = " + sourceItem
				+ " order by z.update_time desc limit 1";
		ZxBlack zxBlack = sqlMapper.selectOne(sql, ZxBlack.class);
		return zxBlack;
	}

}
