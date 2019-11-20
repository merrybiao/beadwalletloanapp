package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwTbDeliverAddre;

public interface BwTbDeliverAddreService {

	List<BwTbDeliverAddre> findListByAttr(BwTbDeliverAddre bwTbDeliverAddre);

	BwTbDeliverAddre findByAttr(BwTbDeliverAddre bwTbDeliverAddre);

}