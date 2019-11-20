package com.waterelephant.baiqishi.service.impl;

import com.waterelephant.baiqishi.entity.BqsHitRule;
import com.waterelephant.baiqishi.service.BqsHitRuleService;
import com.waterelephant.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/27 10:58
 */
@Service
public class BqsHitRuleServiceImpl extends BaseService<BqsHitRule, Long> implements BqsHitRuleService {


    @Override
    public boolean saveBqsHitRule(BqsHitRule bqsHitRule) {
        boolean isSuccess = false;
        try {
            isSuccess = mapper.insert(bqsHitRule) > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return isSuccess;
    }

    @Override
    public boolean deleteBqsHitRule(BqsHitRule bqsHitRule) {
        boolean isSuccess = false;
        try {
            isSuccess = mapper.delete(bqsHitRule) > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return isSuccess;
    }
}
