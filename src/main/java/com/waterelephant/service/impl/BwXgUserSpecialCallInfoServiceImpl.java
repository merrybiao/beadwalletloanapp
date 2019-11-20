package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwXgUserSpecialCallInfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwXgUserSpecialCallInfoService;

@Service
public class BwXgUserSpecialCallInfoServiceImpl extends BaseService<BwXgUserSpecialCallInfo, Long>
		implements BwXgUserSpecialCallInfoService {

	/**
	 * 
	 * @see com.waterelephant.service.BwXgUserSpecialCallInfoService#findListByAttr(com.waterelephant.entity.BwXgUserSpecialCallInfo)
	 */
	@Override
	public List<BwXgUserSpecialCallInfo> findListByAttr(BwXgUserSpecialCallInfo bwXgUserSpecialCallInfo) {
		return mapper.select(bwXgUserSpecialCallInfo);
	}

}
