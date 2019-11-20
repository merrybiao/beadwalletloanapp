package com.waterelephant.service;

import java.util.List;

import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.beans.factory.annotation.Autowired;

import com.waterelephant.utils.mapper.BwNewTabMapper;

import tk.mybatis.mapper.entity.Example;

public abstract class BaseNewTabService<T, PK> extends BaseService<T, PK> {
	
	@Autowired
	protected BwNewTabMapper<T> bwMapper;
	
	@SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS record.id")
	public int insertNewTab(T record) {
		return bwMapper.insertNewTab(record);
	}
	
	public int deleteNewTab(T record) {
		return bwMapper.deleteNewTab(record);
	}

	public int deleteNewTabByPrimaryKey(PK key) {
		return bwMapper.deleteNewTabByPrimaryKey(key);
	}
	
	public int deleteNewTabByExample(Example example) {
		return bwMapper.deleteNewTabByExample(example);
	}
	
	public List<T> selectNewTabByExample(Example example) {
		return bwMapper.selectNewTabByExample(example);
	}

}
