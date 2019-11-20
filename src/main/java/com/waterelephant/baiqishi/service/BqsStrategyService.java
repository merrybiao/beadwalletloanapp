package com.waterelephant.baiqishi.service;

import com.waterelephant.baiqishi.entity.BqsStrategy;

import java.util.List;

/**
 *
         * @author GuoKun
        * @version 1.0
        * @create_date 2017/5/27 10:56
        */
public interface BqsStrategyService {
     boolean saveBqsStrategy(BqsStrategy bqsStrategy);
     boolean saveBqsStrategyList(List<BqsStrategy> bqsStrategyList);

     boolean deleteBqsStrategy(BqsStrategy bqsStrategy);
}
