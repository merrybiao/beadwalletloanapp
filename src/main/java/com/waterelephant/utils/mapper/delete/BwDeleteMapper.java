package com.waterelephant.utils.mapper.delete;

import org.apache.ibatis.annotations.DeleteProvider;

import com.waterelephant.utils.mapper.provider.BwMapperProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface BwDeleteMapper<T> {

	@DeleteProvider(type = BwMapperProvider.class, method = "dynamicSQL")
    int deleteNewTab(T record);
}
