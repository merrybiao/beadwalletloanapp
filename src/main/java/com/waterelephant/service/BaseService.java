package com.waterelephant.service;

import java.util.List;

import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.entity.BwXgOverall;
import com.waterelephant.utils.SqlMapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public abstract class BaseService<T, PK> {

	@Autowired
	protected Mapper<T> mapper;
	
//	@Autowired
//	protected BwNewTabMapper<T> bwMapper;

	@Autowired
	protected SqlMapper sqlMapper;
	
//	@Autowired
//	protected MongoTemplate mongoTemplate;

	public T selectOne(T record) {
		return mapper.selectOne(record);
	}

	public List<T> select(T record) {
		return mapper.select(record);
	}

	public int selectCount(T record) {
		return mapper.selectCount(record);
	}

	public T selectByPrimaryKey(PK key) {
		return mapper.selectByPrimaryKey(key);
	}

	@SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS record.id")
	public int insert(T record) {

		return mapper.insert(record);
	}
	
//	@SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS record.id")
//	public int insertNewTab(T record) {
//		return bwMapper.insertNewTab(record);
//	}

	public int insertSelective(T record) {
		return mapper.insertSelective(record);
	}

	public int delete(T record) {
		return mapper.delete(record);
	}
	
//	public int deleteNewTab(T record) {
//		return bwMapper.deleteNewTab(record);
//	}
//
//	public int deleteNewTabByPrimaryKey(PK key) {
//		return bwMapper.deleteNewTabByPrimaryKey(key);
//	}
	
	public int deleteByPrimaryKey(PK key) {
		return mapper.deleteByPrimaryKey(key);
	}

	public int updateByPrimaryKey(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	public int selectCountByExample(Example example) {
		return mapper.selectCountByExample(example);
	}

	public int deleteByExample(Example example) {
		return mapper.deleteByExample(example);
	}
	
//	public int deleteNewTabByExample(Example example) {
//		return bwMapper.deleteNewTabByExample(example);
//	}

	public List<T> selectByExample(Example example) {
		return mapper.selectByExample(example);
	}
	
//	public List<T> selectNewTabByExample(Example example) {
//		return bwMapper.selectNewTabByExample(example);
//	}

	public int updateByExampleSelective(T record, Example example) {
		return mapper.updateByExampleSelective(record, example);
	}

	public int updateByExample(T record, Example example) {
		return mapper.updateByExample(record, example);
	}

	public PageResponseVo<T> page(PageRequestVo page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<T> list = mapper.selectByExample(page.getExample());
		PageInfo<T> pageInfo = new PageInfo(list);
		PageResponseVo<T> rspVo = new PageResponseVo(pageInfo.getTotal(), pageInfo.getList());
		rspVo.setPageNum(page.getPageNum());
		rspVo.setPageSize(page.getPageSize());
		return rspVo;
	}

//	public PageResponseVo<T> pageBySql(PageRequestVo page) {
//		PageHelper.startPage(page.getPageNum(), page.getPageSize());
//		List<T> list =sqlMapper.selectList("", page.getClszz());
//		PageInfo<T> pageInfo = new PageInfo(list);
//		PageResponseVo<T> rspVo = new PageResponseVo(pageInfo.getTotal(), pageInfo.getList());
//		rspVo.setPageNum(page.getPageNum());
//		rspVo.setPageSize(page.getPageSize());
//		return rspVo;
//	}

}
