package com.waterelephant.baiqishi.service;

import com.waterelephant.baiqishi.entity.BqsDecision;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/27 10:54
 */
public interface BqsDecisionService {
	public boolean saveBqsDecision(BqsDecision bqsDecision);

	public boolean deleteBqsDecision(BqsDecision bqsDecision);

	BqsDecision getBqsDecision(String phone, String idCard);

	BqsDecision getBqsDecisionExternal(String phone, String idCard);
}
