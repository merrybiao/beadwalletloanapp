package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgAreaAnalysisDetail;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgAreaAnalysisDetailService;

@Service
public class BwXgAreaAnalysisDetailServiceImpl extends BaseService<BwXgAreaAnalysisDetail, Long>
		implements BwXgAreaAnalysisDetailService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgAreaAnalysisDetailService#findListByAttr(com.waterelephant.entity.BwXgAreaAnalysisDetail)
	 */
	@Override
	public List<BwXgAreaAnalysisDetail> findListByAttr(BwXgAreaAnalysisDetail bwXgAreaAnalysisDetail) {
		return mapper.select(bwXgAreaAnalysisDetail);
	}

}
