package com.waterelephant.rongCarrier.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.entity.XgMidScore;
import com.waterelephant.rongCarrier.service.XgMidScoreService;
import com.waterelephant.service.BaseService;

/**
 * 1
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 16:28
 */
@Service
public class XgMidScoreServiceImpl extends BaseService<XgMidScore, Long> implements XgMidScoreService {
    @Override
    public boolean save(XgMidScore xgMidScore) {
        return mapper.insert(xgMidScore)>0;
    }

    @Override
    public boolean deleteZ(XgMidScore xgMidScore) {
        return mapper.delete(xgMidScore) > 0;
    }

}
