package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfDataInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfDataInfoMapper extends Mapper<BwMfDataInfo>,InsertListMapper<BwMfDataInfo> {
}