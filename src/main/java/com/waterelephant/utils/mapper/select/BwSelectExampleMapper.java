package com.waterelephant.utils.mapper.select;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.waterelephant.utils.mapper.provider.BwMapperProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Example;

@RegisterMapper
public interface BwSelectExampleMapper<T> {

	@SelectProvider(type = BwMapperProvider.class, method="dynamicSQL")
	List<T> selectNewTabByExample(Example example);
}
