package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwBqsDecision;

public interface BwBqsDecisionService {

	List<BwBqsDecision> findListByAttr(BwBqsDecision bwBqsDecision);
}