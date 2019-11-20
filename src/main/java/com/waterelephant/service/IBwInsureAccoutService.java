package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.BwInsureAccout;

public interface IBwInsureAccoutService {

	//保存社保账号
	public int save(BwInsureAccout bwInsureAccout,Long bId);
	
	//修改社保账号
	public int update(BwInsureAccout bwInsureAccout);
	
	//根据工单id获取社保账号信息
	BwInsureAccout getBwInsureAuthById(Long orderId);
	
	//根据工单id和主键id获取社保账号信息
	List<BwInsureAccout> getBwInsureAuthById(Long id,Long orderId);
	
	/**
	 * 根据条件查询社保账户
	 * @param insureAccout 条件封装实体类
	 * @return
	 */
	BwInsureAccout findBwInsureAccoutByAttr(BwInsureAccout insureAccout);
	
}
