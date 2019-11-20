package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwInsureAccout;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwInsureAccoutService;
import com.waterelephant.utils.CommUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwInsureAccoutServiceImpl extends BaseService<BwInsureAccout, Long> implements IBwInsureAccoutService{

	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Override
	public int save(BwInsureAccout bwInsureAccout,Long bId) {
		int num = mapper.insert(bwInsureAccout);
		if (num > 0) {
			BwBorrower borrower = bwBorrowerService.findBwBorrowerById(bId);
			if (CommUtils.isNull(borrower)) {
				borrower.setState(3);
				num = bwBorrowerService.updateBwBorrower(borrower);
			}
		}
		return num;
	}

	@Override
	public int update(BwInsureAccout bwInsureAccout) {
		return mapper.updateByPrimaryKey(bwInsureAccout);
	}

	@Override
	public BwInsureAccout getBwInsureAuthById(Long orderId) {
		BwInsureAccout bia = new BwInsureAccout();
		bia.setOrderId(orderId);
		return mapper.selectOne(bia);
	}

	@Override
	public List<BwInsureAccout> getBwInsureAuthById(Long id, Long orderId) {
		Example example  = new Example(BwInsureAccout.class);
		example.or().andEqualTo("", id).andEqualTo("", orderId);
		return mapper.selectByExample(example);
	}
	
	@Override
	public BwInsureAccout findBwInsureAccoutByAttr(BwInsureAccout insureAccout) {
		return mapper.selectOne(insureAccout);
	}
	
}
