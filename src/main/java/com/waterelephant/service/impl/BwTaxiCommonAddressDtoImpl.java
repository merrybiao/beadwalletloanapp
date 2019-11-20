package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwGxbTaxiCommonAddress;
import com.waterelephant.gxb.dto.TaxiCommonAddressDto;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwGxbTaxiCommonAddressDtoService;

@Service
public class BwTaxiCommonAddressDtoImpl extends BaseService<BwGxbTaxiCommonAddress, Long> implements 
		BwGxbTaxiCommonAddressDtoService {

	@Override
	public Integer saveTaxiCommonAddressDto(
			List<TaxiCommonAddressDto> gxbtaxicommonaddressdto,Long uid) throws Exception {
		Integer result = 0;
		for (TaxiCommonAddressDto dto : gxbtaxicommonaddressdto) {
			BwGxbTaxiCommonAddress address = new BwGxbTaxiCommonAddress();
			address.setAddr(dto.getAddr());
			address.setAddrName(dto.getAddrName());
			address.setCityName(dto.getCityName());
			address.setInsertTime(new Date());
			address.setLat(dto.getLat());
			address.setLng(dto.getLng());
			address.setToAddress(dto.getToAddress());
			address.setToName(dto.getToName());
			address.setUid(uid);
			int count = insert(address);
			if(count >0)result ++;
		}
		return result;
	}
}
