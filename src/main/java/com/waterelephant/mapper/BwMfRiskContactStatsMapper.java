package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfRiskContactStats;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfRiskContactStatsMapper  extends Mapper<BwMfRiskContactStats>,InsertListMapper<BwMfRiskContactStats> {
}