package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgCallLogDetail;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgCallLogDetailService;

@Service
public class BwXgCallLogDetailServiceImpl extends BaseService<BwXgCallLogDetail, Long>
		implements BwXgCallLogDetailService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgCallLogDetailService#findListByAttr(com.waterelephant.entity.BwXgCallLogDetail)
	 */
	@Override
	public List<BwXgCallLogDetail> findListByAttr(BwXgCallLogDetail bwXgCallLogDetail) {
		return mapper.select(bwXgCallLogDetail);
	}

}
