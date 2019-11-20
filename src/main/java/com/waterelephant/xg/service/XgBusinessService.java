package com.waterelephant.xg.service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.rongCarrier.JSonEntity.OpReport;

/**
 * 西瓜运营商数据
 * @author dinglinhao
 * @date 2018年6月13日11:36:01
 *
 */
public interface XgBusinessService {
	
	
	void saveXgData(OpReport opReport, Long borrowerId) throws Exception;
	
	void saveXgDataToNewTab(OpReport opReport, Long borrowerId) throws Exception;

	void saveXgData2(JSONObject jsonObject, Long borrowerId) throws Exception;
	
	void saveXgData2ToNewTab(JSONObject jsonObject, Long borrowerId) throws Exception;

}
