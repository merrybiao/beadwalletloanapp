package com.waterelephant.baiqishi.service.impl;

import com.waterelephant.baiqishi.entity.BqsStrategy;
import com.waterelephant.baiqishi.service.BqsStrategyService;
import com.waterelephant.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/27 10:58
 */
@Service
public class BqsStrategyServiceImpl extends BaseService<BqsStrategy, Long> implements BqsStrategyService {


    @Override
    public boolean saveBqsStrategy(BqsStrategy bqsStrategy) {
        boolean isSuccess = false;
        try {
            isSuccess = mapper.insert(bqsStrategy) > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return isSuccess;
    }

    @Override
    public boolean saveBqsStrategyList(List<BqsStrategy> bqsStrategyList) {
        return false;
    }

    @Override
    public boolean deleteBqsStrategy(BqsStrategy bqsStrategy) {
        boolean isSuccess = false;
        try {
            isSuccess = mapper.delete(bqsStrategy) > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return isSuccess;
    }
}
