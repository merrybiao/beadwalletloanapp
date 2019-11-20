package com.waterelephant.service;

import java.util.Map;

/**
 * 工单关系数据接口
 * 
 * @author song
 *
 */
public interface SystemDataCentersService {

	Map<String, Object> queryKoudai(Map<String, Object> map);

	Map<String, Object> queryBaiqishi(Map<String, Object> map);

}
