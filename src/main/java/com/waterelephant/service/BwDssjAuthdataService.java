package com.waterelephant.service;

import com.waterelephant.dssj.entity.DssjAuthpulldata;
import com.waterelephant.entity.BwDssjAuthdata;

public interface BwDssjAuthdataService {
	
	/**
	 * 查询通过sequenceno
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public BwDssjAuthdata queryBwDssjAuthdataByTiNo(String tiNo) throws Exception;
	
	
	/**
	 * 查询
	 * @param transId
	 * @return
	 * @throws Exception
	 */
	public BwDssjAuthdata queryBwDssjAuthdataByorderNo(String orderno) throws Exception;

	
	/**
	 * 保存
	 * @param uid
	 * @param name
	 * @param idCard
	 * @param phone
	 * @param transId
	 * @param orderNo
	 * @param authorized
	 * @param score
	 * @param url
	 * @param notify_url
	 * @param redit_url
	 * @return
	 * @throws Exception
	 */
	public long saveBwDssjAuthdata(String id,String name,String idCard,String phone,String notify_url,String redit_url,String url,String orderNo) throws Exception;
	
	/**
	 * 修改保存推送过来的数据
	 * @return
	 * @throws Exception
	 */
	public boolean updatePullBwDssjAuthdata(DssjAuthpulldata dssjauthpulldata) throws Exception;
	
	/**
	 * 修改信用分
	 * @param bwdssjpushdata
	 * @return
	 * @throws Exception
	 */
	boolean updateScore(String score,String tiNo)  throws Exception ;
	
	/**
	 * 查询重定向页面地址
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BwDssjAuthdata queryRedirtUrl(String tino) throws Exception ;

}
