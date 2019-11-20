package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfServiceContactStats;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfServiceContactStatsMapper extends Mapper<BwMfServiceContactStats>,InsertListMapper<BwMfServiceContactStats> {
}