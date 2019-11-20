package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfCallInfoRecord;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfCallInfoRecordMapper extends Mapper<BwMfCallInfoRecord>,InsertListMapper<BwMfCallInfoRecord> {
}