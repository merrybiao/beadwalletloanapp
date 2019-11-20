package com.waterelephant.zhengxin91.service;

import java.util.Map;

import com.waterelephant.utils.AppResponseResult;

/**
 * 征信91 service层
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 13:34
 */
public interface Zx91Service {

	public AppResponseResult saveTongBuGet(Map reqMap);

	public AppResponseResult saveTongBu1003(Map reqMap);

	public byte[] saveYiBu(byte[] postData);

	/**
	 * 91征信 - 1.1 借贷信息查询接口 客户->平台（报文主体定义）
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean createTrxNo(Map<String, String> paramMap);

	/**
	 * 91征信 - 测试1.1接口
	 * 
	 * @param realName
	 * @param idCard
	 * @param borrowId
	 * @param orderId
	 * @return
	 */
	public AppResponseResult saveZhenXin11(Map<String, String> paramMap);

}
