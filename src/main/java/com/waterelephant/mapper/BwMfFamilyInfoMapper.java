package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfFamilyInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfFamilyInfoMapper extends Mapper<BwMfFamilyInfo>,InsertListMapper<BwMfFamilyInfo> {
}