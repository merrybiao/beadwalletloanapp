package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfSmsInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfSmsInfoMapper extends Mapper<BwMfSmsInfo>,InsertListMapper<BwMfSmsInfo> {
}