package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfPackageUsageRecord;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfPackageUsageRecordMapper extends Mapper<BwMfPackageUsageRecord>,InsertListMapper<BwMfPackageUsageRecord> {
}