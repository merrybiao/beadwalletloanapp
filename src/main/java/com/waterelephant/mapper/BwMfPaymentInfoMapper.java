package com.waterelephant.mapper;

import com.waterelephant.entity.BwMfPaymentInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BwMfPaymentInfoMapper extends Mapper<BwMfPaymentInfo>,InsertListMapper<BwMfPaymentInfo> {
}