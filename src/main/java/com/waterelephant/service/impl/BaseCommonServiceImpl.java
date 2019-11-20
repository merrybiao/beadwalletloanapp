package com.waterelephant.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.service.BaseCommonService;
import com.waterelephant.utils.SqlMapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public class BaseCommonServiceImpl<TT, PK> implements BaseCommonService<TT, PK> {

	@Autowired
	protected Mapper<TT> mapper;

	@Autowired
	protected SqlMapper sqlMapper;

	@Override
	public TT selectOne(TT record) {
		return mapper.selectOne(record);
	}

	@Override
	public List<TT> select(TT record) {
		return mapper.select(record);
	}

	@Override
	public int selectCount(TT record) {
		return mapper.selectCount(record);
	}

	@Override
	public TT selectByPrimaryKey(PK key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	@SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS record.id")
	public int insert(TT record) {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(TT record) {
		return mapper.insertSelective(record);
	}

	@Override
	public int delete(TT record) {
		return mapper.delete(record);
	}

	@Override
	public int deleteByPrimaryKey(PK key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(TT record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int selectCountByExample(Example example) {
		return mapper.selectCountByExample(example);
	}

	@Override
	public int deleteByExample(Example example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public List<TT> selectByExample(Example example) {
		return mapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(TT record, Example example) {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public PageResponseVo<TT> page(PageRequestVo page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<TT> list = mapper.selectByExample(page.getExample());
		PageInfo<TT> pageInfo = new PageInfo<TT>(list);
		PageResponseVo<TT> rspVo = new PageResponseVo<TT>(pageInfo.getTotal(), pageInfo.getList());
		rspVo.setPageNum(page.getPageNum());
		rspVo.setPageSize(page.getPageSize());
		return rspVo;
	}

}
