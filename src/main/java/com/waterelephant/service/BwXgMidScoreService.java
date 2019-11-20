package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwXgMidScore;

public interface BwXgMidScoreService {

	BwXgMidScore findByAttr(BwXgMidScore bwXgMidScore);

	List<BwXgMidScore> findListByAttr(BwXgMidScore bwXgMidScore);
}
