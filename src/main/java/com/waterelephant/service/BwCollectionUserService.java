package com.waterelephant.service;

import com.waterelephant.entity.BwCollectionUser;

import java.util.Date;

public interface BwCollectionUserService extends BaseCommonService<BwCollectionUser, Long> {

    /**
     * 查询最近的分单信息
     *
     * @param repayId
     * @param beforeTime
     * @return
     */
    BwCollectionUser findLastByRepayId(Long repayId, Date beforeTime);
}