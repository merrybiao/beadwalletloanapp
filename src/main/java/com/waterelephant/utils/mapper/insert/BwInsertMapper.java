package com.waterelephant.utils.mapper.insert;

import org.apache.ibatis.annotations.InsertProvider;

import com.waterelephant.utils.mapper.provider.BwMapperProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface BwInsertMapper<T> {

	@InsertProvider(type = BwMapperProvider.class, method = "dynamicSQL")
    int insertNewTab(T record);
}
