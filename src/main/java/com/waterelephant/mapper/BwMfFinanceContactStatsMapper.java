package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfFinanceContactStats;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfFinanceContactStatsMapper extends Mapper<BwMfFinanceContactStats>,InsertListMapper<BwMfFinanceContactStats> {
}