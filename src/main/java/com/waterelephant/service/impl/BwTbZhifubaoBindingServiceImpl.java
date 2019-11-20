package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwTbZhifubaoBinding;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwTbZhifubaoBindingService;

@Service
public class BwTbZhifubaoBindingServiceImpl extends BaseService<BwTbZhifubaoBinding, Long>
		implements BwTbZhifubaoBindingService {

	/**
	 * 
	 * @see com.waterelephant.service.BwTbZhifubaoBindingService#findListByAttr(com.waterelephant.entity.BwTbZhifubaoBinding)
	 */
	@Override
	public List<BwTbZhifubaoBinding> findListByAttr(BwTbZhifubaoBinding bwTbZhifubaoBinding) {
		return mapper.select(bwTbZhifubaoBinding);
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwTbZhifubaoBindingService#findByAttr(com.waterelephant.entity.BwTbZhifubaoBinding)
	 */
	@Override
	public BwTbZhifubaoBinding findByAttr(BwTbZhifubaoBinding bwTbZhifubaoBinding) {
		return mapper.selectOne(bwTbZhifubaoBinding);
	}

}