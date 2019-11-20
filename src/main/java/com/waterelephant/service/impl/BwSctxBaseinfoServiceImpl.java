package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.waterelephant.entity.BwSctxBaseinfo;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwSctxBaseinfoService;
import com.waterelephant.utils.CommUtils;

@Service
public class BwSctxBaseinfoServiceImpl  extends BaseService<BwSctxBaseinfo, Long> implements IBwSctxBaseinfoService{

	@Override
	public Long saveBaseInfo(String name, String mobile, String idCard,String onlyId) {
		BwSctxBaseinfo base = new BwSctxBaseinfo();
		if(!CommUtils.isNull(name)){
			base.setName(name);
		}
		if(!CommUtils.isNull(mobile)){
			base.setPhone(mobile);
		}
		if(!CommUtils.isNull(idCard)){
			base.setIdcard(idCard);
		}
		base.setOnlyId(onlyId);
		base.setCreateTime(new Date());
		base.setUpdateTime(new Date());
		return insert(base) == 1 ? base.getId() : 0;
	}

	@Override
	public BwSctxBaseinfo queryBaseInfoByonlyId(String onlyId) {
		Example example = new Example(BwSctxBaseinfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("onlyId", onlyId);
		example.setOrderByClause("create_time desc");
		List<BwSctxBaseinfo> list = mapper.selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

}
