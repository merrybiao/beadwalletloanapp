package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgOverall;

public interface BwXgOverallService {

	BwXgOverall findByAttr(BwXgOverall bwXgOverall);

	List<BwXgOverall> findListByAttr(BwXgOverall bwXgOverall);
}
