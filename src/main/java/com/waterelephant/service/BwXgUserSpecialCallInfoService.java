package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgUserSpecialCallInfo;

public interface BwXgUserSpecialCallInfoService {
	List<BwXgUserSpecialCallInfo> findListByAttr(BwXgUserSpecialCallInfo bwXgUserSpecialCallInfo);
}
