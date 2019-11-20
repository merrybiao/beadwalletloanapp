package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfBillInfoRecord;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfBillInfoRecordMapper extends Mapper<BwMfBillInfoRecord>,InsertListMapper<BwMfBillInfoRecord> {
}