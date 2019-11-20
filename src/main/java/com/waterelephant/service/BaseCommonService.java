/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service;

import java.util.List;

import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * 
 * Module:
 * 
 * IBaseService.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface BaseCommonService<TT, PK> {

	/**
	 * 根据实体查询单个对象
	 * 
	 * @param record
	 * @return
	 */
	TT selectOne(TT record);

	/**
	 * 根据实体查询多个对象
	 * 
	 * @param record
	 * @return
	 */
	List<TT> select(TT record);

	/**
	 * 根据实体查询记录条数
	 * 
	 * @param record
	 * @return
	 */
	int selectCount(TT record);

	/**
	 * 根据主键查询对象
	 * 
	 * @param key
	 * @return
	 */
	TT selectByPrimaryKey(PK key);

	/**
	 * 插入对象(所有字段)
	 * 
	 * @param record
	 * @return
	 */
	int insert(TT record);

	/**
	 * 插入对象(空字段不插入)
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(TT record);

	/**
	 * 根据实体删除对象
	 * 
	 * @param record
	 * @return
	 */
	int delete(TT record);

	/**
	 * 根据主键删除对象
	 * 
	 * @param key
	 * @return
	 */
	int deleteByPrimaryKey(PK key);

	/**
	 * 更新实体(空字段不更新)
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(TT record);

	/**
	 * 查询记录条数
	 * 
	 * @param example
	 * @return
	 */
	int selectCountByExample(Example example);

	/**
	 * 删除记录
	 * 
	 * @param example
	 * @return
	 */
	int deleteByExample(Example example);

	/**
	 * 查询多条记录
	 * 
	 * @param example
	 * @return
	 */
	List<TT> selectByExample(Example example);

	/**
	 * 更新数据(空字段不更新)
	 * 
	 * @param record
	 * @param example(条件)
	 * @return
	 */
	int updateByExampleSelective(TT record, Example example);

	/**
	 * 查询分页对象
	 * 
	 * @param page
	 * @return
	 */
	PageResponseVo<TT> page(PageRequestVo page);

}