package com.waterelephant.yixin.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.yixin.dto.BackYiXinDto;
import com.waterelephant.yixin.dto.param.YiXinParamDto;

/**
 * 
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月16日 上午9:39:14
 */
public interface YiXinService {

	/**
	 * 查询相关的借款和逾期的内容
	 */
	public List<Map<String,Object>> getBorrowingOrder(YiXinParamDto yiXinParamDto);

	
	/**
	 * 查询风险名单
	 */
	public List<Map<String,Object>> getRiskOrder(YiXinParamDto yiXinParamDto);
	
	
	/**
	 * 处理逻辑拼接字符串
	 */
	public BackYiXinDto backYiXinDto(YiXinParamDto yiXinParamDto,String rc4key);
	
	/**
	 * 查询多期或者单期的情况
	 */
	public List<Map<String,Object>> findOrderInfo(String orderId);
}
