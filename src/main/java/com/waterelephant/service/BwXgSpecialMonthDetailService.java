package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgSpecialMonthDetail;

public interface BwXgSpecialMonthDetailService {
	List<BwXgSpecialMonthDetail> findListByAttr(BwXgSpecialMonthDetail bwXgSpecialMonthDetail);
}
