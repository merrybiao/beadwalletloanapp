package com.waterelephant.service;

import com.waterelephant.entity.BwDssjPushdata;
import com.waterelephant.gxb.dto.AuthInfoDto;

public interface BwDssjPushdataService {
	
	/**
	 * 查询记录
	 * @param transId
	 * @return BwDssjPushdata
	 */
	public BwDssjPushdata queryBwDssjPushdatabyTransId(String transId)  throws Exception ;
	
	/**
	 * 保存 bwdssjpushdata
	 * @param bwdssjpushdata
	 * @return long
	 */
	public long saveBwDssjPushdata(BwDssjPushdata bwdssjpushdata)  throws Exception ;
	
	
}
