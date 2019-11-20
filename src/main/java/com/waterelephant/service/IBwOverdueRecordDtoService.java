package com.waterelephant.service;

import com.waterelephant.dto.BwOverdueRecordDto;

public interface IBwOverdueRecordDtoService {

	/**
	 * 查询逾期
	 * 
	 * @param bwOverdueRecordDto
	 * @return
	 */
	BwOverdueRecordDto findBwOverdueRecordDtoByAttr(BwOverdueRecordDto bwOverdueRecordDto);

}
