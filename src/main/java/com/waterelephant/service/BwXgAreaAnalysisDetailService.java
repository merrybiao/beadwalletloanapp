package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgAreaAnalysisDetail;

public interface BwXgAreaAnalysisDetailService {

	List<BwXgAreaAnalysisDetail> findListByAttr(BwXgAreaAnalysisDetail bwXgAreaAnalysisDetail);
}
