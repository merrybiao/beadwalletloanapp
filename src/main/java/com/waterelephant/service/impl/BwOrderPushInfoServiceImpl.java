package com.waterelephant.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.utils.CdnUploadTools;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwOrderPushInfoServiceImpl extends BaseService<BwOrderPushInfo, Long> implements IBwOrderPushInfoService {

	@Autowired
	private IBwAdjunctService bwAdjunctService;

	@Override
	public BwOrderPushInfo getOrderPushInfo(Long orderId, int channel) {
		String sql = "select * from bw_order_push_info p where p.order_id=" + orderId + " and p.financing_channel="
				+ channel + " order by update_time desc LIMIT 1";
		return sqlMapper.selectOne(sql, BwOrderPushInfo.class);
	}

	@Override
	public void saveOrderPushInfo(BwOrderPushInfo bwOrderPushInfo) {
		mapper.insertSelective(bwOrderPushInfo);
	}

	@Override
	public void saveOrderPushInfoAndUpdateOrder(BwOrderPushInfo bwOrderPushInfo) {
		mapper.insertSelective(bwOrderPushInfo);
		// 更新工单状态为债匹中
		String sql = "update bw_order set status_id = 14 where id = #{orderId}";
		sqlMapper.update(sql, bwOrderPushInfo.getOrderId());
	}

	@Override
	public BwOrderPushInfo getUniqueOrderPushInfoByExample(Example example) throws Exception {
		List<BwOrderPushInfo> list = mapper.selectByExample(example);
		if (list != null && list.size() > 1) {
			throw new Exception("Expected one result (or null), but found:" + list.size());
		}
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	@Override
	public void updateOrderPushInfo(BwOrderPushInfo bwOrderPushInfo) {
		mapper.updateByPrimaryKeySelective(bwOrderPushInfo);
	}

	@Override
	public void saveYiQiHaoAttachment(Long orderId, String filename) {
		// 持证照
		List<BwAdjunct> adjunctList = bwAdjunctService.findAdjunctByOrderIdAndAdjunctType(orderId, 3);
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (BwAdjunct bwAdjunct : adjunctList) {
			CdnUploadTools.downloadOSSFile(bwAdjunct.getAdjunctPath(), SystemConstant.YIQIHAO_FILE_PATH + File.separator
					+ format.format(now) + File.separator + filename + ".jpg", true);
		}

	}

	@Override
	public Date findCreateTimeByOrderId(Long orderId) {
		String sql = "select p.create_time from bw_order_push_info p where p.order_id=#{orderId} LIMIT 1";
		return sqlMapper.selectOne(sql, orderId, Date.class);
	}

	@Override
	public Date findLoanTimeByOrderId(Long orderId) {
		String sql = "select p.loan_time from bw_order_push_info p where p.order_id=#{orderId} LIMIT 1";
		return sqlMapper.selectOne(sql, orderId, Date.class);
	}

	@Override
	public BwOrderPushInfo getOrderPushInfoByOrderId(Long orderId) {
		String sql = "select * from bw_order_push_info p where p.order_id=" + orderId + "order by update_time desc LIMIT 1";
		return sqlMapper.selectOne(sql, BwOrderPushInfo.class);
	}

}
