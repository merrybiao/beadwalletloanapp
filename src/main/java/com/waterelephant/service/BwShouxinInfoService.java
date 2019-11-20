package com.waterelephant.service;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;

public interface BwShouxinInfoService {
	
	public boolean saveProductData(String app_term, String apply_state, String apply_time, String bank_card_no, String bank_code, String channel_info, String code_bus, JSONObject device, String id_card, String id_type, String mobile,String name_custc,String no_busb,String order_info,String prod_type,String re_loan);
	
	public boolean saveCashMoneyData(BigDecimal app_limit, Short app_term, String apply_state, String apply_time, String bank_card_no, String bank_code, String channel_info, String code_bus, JSONObject device, String id_card, String id_type,String mobile,String name_custc,String no_busb,String prod_type,String re_loan);

	public JSONObject returnSxResult(JSONObject jsondata);
	
	public boolean updateProductResult(String no_busb,String result);
	
	public boolean updateCashMoneyResult(String applyState,String result);
	
}
