package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwTbZhifubaoBinding;

public interface BwTbZhifubaoBindingService {

	List<BwTbZhifubaoBinding> findListByAttr(BwTbZhifubaoBinding bwTbZhifubaoBinding);

	BwTbZhifubaoBinding findByAttr(BwTbZhifubaoBinding bwTbZhifubaoBinding);
}