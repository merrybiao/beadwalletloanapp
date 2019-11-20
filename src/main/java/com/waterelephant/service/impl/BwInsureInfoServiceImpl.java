package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwInsureInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwInsureInfoService;

@Service
public class BwInsureInfoServiceImpl extends BaseService<BwInsureInfo, Long> implements BwInsureInfoService {

	@Override
	public Long save(BwInsureInfo bwInsureInfo) {
		mapper.insert(bwInsureInfo);
		return bwInsureInfo.getId();
	}

	@Override
	public BwInsureInfo getByIdCard(String idCard) {
		BwInsureInfo bii = new BwInsureInfo();
		bii.setIdCard(idCard);
		return mapper.selectOne(bii);
	}

	@Override
	public List<BwInsureInfo> queryInfo(Long orderId) {
		String sql = "select* from bw_insure_info i where i.order_id=" + orderId + "";
		return sqlMapper.selectList(sql, BwInsureInfo.class);
	}

	@Override
	public void add(BwInsureInfo bwInsureInfo) {
		bwInsureInfo.setId(null);
		mapper.insert(bwInsureInfo);
	}

	/**
	 * 融360 - 社保 - 删除社保信息（code0084）
	 * 
	 * @see com.waterelephant.service.BwInsureInfoService#deleteBwInsureInfo(com.waterelephant.entity.BwInsureInfo)
	 */
	@Override
	public boolean deleteBwInsureInfo(BwInsureInfo bwInsureInfo) {
		return mapper.delete(bwInsureInfo) > 0;
	}

}
