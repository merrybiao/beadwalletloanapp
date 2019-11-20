package com.waterelephant.baiqishi.service;

import com.waterelephant.baiqishi.entity.BqsHitRule;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/27 10:57
 */
public interface BqsHitRuleService {
    boolean saveBqsHitRule(BqsHitRule bqsHitRule);

    boolean deleteBqsHitRule(BqsHitRule bqsHitRule);
}
