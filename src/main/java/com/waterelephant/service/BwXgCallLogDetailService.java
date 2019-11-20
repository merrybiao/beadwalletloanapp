package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgCallLogDetail;

public interface BwXgCallLogDetailService {

	List<BwXgCallLogDetail> findListByAttr(BwXgCallLogDetail bwXgCallLogDetail);
}
