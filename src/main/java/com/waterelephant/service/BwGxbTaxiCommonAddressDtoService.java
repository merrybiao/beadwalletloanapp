package com.waterelephant.service;

import java.util.List;

import com.waterelephant.gxb.dto.TaxiCommonAddressDto;

public interface BwGxbTaxiCommonAddressDtoService {
	
	public Integer saveTaxiCommonAddressDto(List<TaxiCommonAddressDto> gxbtaxicommonaddressdto,Long uid) throws Exception;

}
