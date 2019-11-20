///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.kaNiu;
//
///**
// * 
// * 
// * Module:
// * 
// * SupRiskInfo.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class SupRiskInfo {
//	private String userkn_90d_changecity_num;// 1
//	private String userkn_90d_changeprovince_num;// 1
//	private String userkn_alipaybill_num;// 0
//	private Integer userkn_app_bank_cnt;// 0
//	private Integer userkn_app_bank_loan_cnt;// 0
//	private Integer userkn_app_buy_car_cnt;// 0
//	private Integer userkn_app_buy_house_cnt;// 0
//	private Integer userkn_app_car_owner_cnt;// 0
//	private Integer userkn_app_cash_loan_cnt;// 0
//	private Integer userkn_app_credit_information_cnt;// 0
//	private Integer userkn_app_financial_cnt;// 0
//	private Integer userkn_app_gambling_cnt;// 0
//	private Integer userkn_app_home_decoration_cnt;// 0
//	private Integer userkn_app_instalment_cnt;// 0
//	private Integer userkn_app_insurance_cnt;// 0
//	private Integer userkn_app_job_search_cnt;// 0
//	private Integer userkn_app_on_sale_cnt;// 0
//	private Integer userkn_app_p2p_financial_cnt;// 0
//	private Integer userkn_app_payment_cnt;// 0
//	private Integer userkn_app_renting_cnt;// 0
//	private Integer userkn_app_save_money_cnt;// 0
//	private Integer userkn_app_shopping_cnt;// 0
//	private Integer userkn_app_stock_fund_cnt;// 0
//	private String userkn_firstcardtype;//
//	private String userkn_lastcity;// 深圳市
//	private String userkn_lastlocation;// 广东省_深圳市_南山区_科技南十二路_
//	private String userkn_lastprovince;// 广东省
//	private String userkn_newfirstcredit_date;// 2018-05-16
//	private String userkn_newfirstdeposit_date;//
//	private String userkn_phone_city;// 深圳
//	private String userkn_phone_province;// 广东
//	private String userkn_rc_credit_card_num;// 4
//	private String userkn_rc_reposit_card_num;// 0
//	private String userkn_user_reggap;// 6
//	private String userkn_work_location;// 广东省_深圳市_南山区_高新南六道_
//	private String userkn_nowork_location;//
//	private String userkn_avgperday_opencardniu_cnt;//
//	private String userkn_click_loanproduct_firsttime;//
//	private String userkn_click_loanproduct_lasttime;//
//	private String userkn_first_opencardniu_time;//
//	private String userkn_6m_opencardniu_days;//
//	private String userkn_30d_opencardniu_days;//
//	private String userkn_60d_opencardniu_days;//
//	private String userkn_action_enterapplyloanpage_firsttimegap;//
//	private String userkn_action_opencardniu_cnt_firstapplyday;//
//	private String userkn_daygap_importcard_open;//
//	private String userkn_hourgap_aenterapplyloanpage_open;//
//	private String userkn_hourgap_importcard_open;//
//	private String userkn_importbillcnt_firstday;//
//	private String userkn_importbillrate_firstday;//
//	private String userkn_importcardcnt_firstday;//
//	private String userkn_importcardrate_firstday;//
//	private String userkn_registercnt;//
//	private String userkn_udiduseridcnt;//
//	private String devkn_device_atime;//
//
//	public String getUserkn_nowork_location() {
//		return userkn_nowork_location;
//	}
//
//	public void setUserkn_nowork_location(String userkn_nowork_location) {
//		this.userkn_nowork_location = userkn_nowork_location;
//	}
//
//	public String getUserkn_avgperday_opencardniu_cnt() {
//		return userkn_avgperday_opencardniu_cnt;
//	}
//
//	public void setUserkn_avgperday_opencardniu_cnt(String userkn_avgperday_opencardniu_cnt) {
//		this.userkn_avgperday_opencardniu_cnt = userkn_avgperday_opencardniu_cnt;
//	}
//
//	public String getUserkn_click_loanproduct_firsttime() {
//		return userkn_click_loanproduct_firsttime;
//	}
//
//	public void setUserkn_click_loanproduct_firsttime(String userkn_click_loanproduct_firsttime) {
//		this.userkn_click_loanproduct_firsttime = userkn_click_loanproduct_firsttime;
//	}
//
//	public String getUserkn_click_loanproduct_lasttime() {
//		return userkn_click_loanproduct_lasttime;
//	}
//
//	public void setUserkn_click_loanproduct_lasttime(String userkn_click_loanproduct_lasttime) {
//		this.userkn_click_loanproduct_lasttime = userkn_click_loanproduct_lasttime;
//	}
//
//	public String getUserkn_first_opencardniu_time() {
//		return userkn_first_opencardniu_time;
//	}
//
//	public void setUserkn_first_opencardniu_time(String userkn_first_opencardniu_time) {
//		this.userkn_first_opencardniu_time = userkn_first_opencardniu_time;
//	}
//
//	public String getUserkn_6m_opencardniu_days() {
//		return userkn_6m_opencardniu_days;
//	}
//
//	public void setUserkn_6m_opencardniu_days(String userkn_6m_opencardniu_days) {
//		this.userkn_6m_opencardniu_days = userkn_6m_opencardniu_days;
//	}
//
//	public String getUserkn_30d_opencardniu_days() {
//		return userkn_30d_opencardniu_days;
//	}
//
//	public void setUserkn_30d_opencardniu_days(String userkn_30d_opencardniu_days) {
//		this.userkn_30d_opencardniu_days = userkn_30d_opencardniu_days;
//	}
//
//	public String getUserkn_60d_opencardniu_days() {
//		return userkn_60d_opencardniu_days;
//	}
//
//	public void setUserkn_60d_opencardniu_days(String userkn_60d_opencardniu_days) {
//		this.userkn_60d_opencardniu_days = userkn_60d_opencardniu_days;
//	}
//
//	public String getUserkn_action_enterapplyloanpage_firsttimegap() {
//		return userkn_action_enterapplyloanpage_firsttimegap;
//	}
//
//	public void setUserkn_action_enterapplyloanpage_firsttimegap(String userkn_action_enterapplyloanpage_firsttimegap) {
//		this.userkn_action_enterapplyloanpage_firsttimegap = userkn_action_enterapplyloanpage_firsttimegap;
//	}
//
//	public String getUserkn_action_opencardniu_cnt_firstapplyday() {
//		return userkn_action_opencardniu_cnt_firstapplyday;
//	}
//
//	public void setUserkn_action_opencardniu_cnt_firstapplyday(String userkn_action_opencardniu_cnt_firstapplyday) {
//		this.userkn_action_opencardniu_cnt_firstapplyday = userkn_action_opencardniu_cnt_firstapplyday;
//	}
//
//	public String getUserkn_daygap_importcard_open() {
//		return userkn_daygap_importcard_open;
//	}
//
//	public void setUserkn_daygap_importcard_open(String userkn_daygap_importcard_open) {
//		this.userkn_daygap_importcard_open = userkn_daygap_importcard_open;
//	}
//
//	public String getUserkn_hourgap_aenterapplyloanpage_open() {
//		return userkn_hourgap_aenterapplyloanpage_open;
//	}
//
//	public void setUserkn_hourgap_aenterapplyloanpage_open(String userkn_hourgap_aenterapplyloanpage_open) {
//		this.userkn_hourgap_aenterapplyloanpage_open = userkn_hourgap_aenterapplyloanpage_open;
//	}
//
//	public String getUserkn_hourgap_importcard_open() {
//		return userkn_hourgap_importcard_open;
//	}
//
//	public void setUserkn_hourgap_importcard_open(String userkn_hourgap_importcard_open) {
//		this.userkn_hourgap_importcard_open = userkn_hourgap_importcard_open;
//	}
//
//	public String getUserkn_importbillcnt_firstday() {
//		return userkn_importbillcnt_firstday;
//	}
//
//	public void setUserkn_importbillcnt_firstday(String userkn_importbillcnt_firstday) {
//		this.userkn_importbillcnt_firstday = userkn_importbillcnt_firstday;
//	}
//
//	public String getUserkn_importbillrate_firstday() {
//		return userkn_importbillrate_firstday;
//	}
//
//	public void setUserkn_importbillrate_firstday(String userkn_importbillrate_firstday) {
//		this.userkn_importbillrate_firstday = userkn_importbillrate_firstday;
//	}
//
//	public String getUserkn_importcardcnt_firstday() {
//		return userkn_importcardcnt_firstday;
//	}
//
//	public void setUserkn_importcardcnt_firstday(String userkn_importcardcnt_firstday) {
//		this.userkn_importcardcnt_firstday = userkn_importcardcnt_firstday;
//	}
//
//	public String getUserkn_importcardrate_firstday() {
//		return userkn_importcardrate_firstday;
//	}
//
//	public void setUserkn_importcardrate_firstday(String userkn_importcardrate_firstday) {
//		this.userkn_importcardrate_firstday = userkn_importcardrate_firstday;
//	}
//
//	public String getUserkn_registercnt() {
//		return userkn_registercnt;
//	}
//
//	public void setUserkn_registercnt(String userkn_registercnt) {
//		this.userkn_registercnt = userkn_registercnt;
//	}
//
//	public String getUserkn_udiduseridcnt() {
//		return userkn_udiduseridcnt;
//	}
//
//	public void setUserkn_udiduseridcnt(String userkn_udiduseridcnt) {
//		this.userkn_udiduseridcnt = userkn_udiduseridcnt;
//	}
//
//	public String getDevkn_device_atime() {
//		return devkn_device_atime;
//	}
//
//	public void setDevkn_device_atime(String devkn_device_atime) {
//		this.devkn_device_atime = devkn_device_atime;
//	}
//
//	public String getUserkn_90d_changecity_num() {
//		return userkn_90d_changecity_num;
//	}
//
//	public void setUserkn_90d_changecity_num(String userkn_90d_changecity_num) {
//		this.userkn_90d_changecity_num = userkn_90d_changecity_num;
//	}
//
//	public String getUserkn_90d_changeprovince_num() {
//		return userkn_90d_changeprovince_num;
//	}
//
//	public void setUserkn_90d_changeprovince_num(String userkn_90d_changeprovince_num) {
//		this.userkn_90d_changeprovince_num = userkn_90d_changeprovince_num;
//	}
//
//	public String getUserkn_alipaybill_num() {
//		return userkn_alipaybill_num;
//	}
//
//	public void setUserkn_alipaybill_num(String userkn_alipaybill_num) {
//		this.userkn_alipaybill_num = userkn_alipaybill_num;
//	}
//
//	public Integer getUserkn_app_bank_cnt() {
//		return userkn_app_bank_cnt;
//	}
//
//	public void setUserkn_app_bank_cnt(Integer userkn_app_bank_cnt) {
//		this.userkn_app_bank_cnt = userkn_app_bank_cnt;
//	}
//
//	public Integer getUserkn_app_bank_loan_cnt() {
//		return userkn_app_bank_loan_cnt;
//	}
//
//	public void setUserkn_app_bank_loan_cnt(Integer userkn_app_bank_loan_cnt) {
//		this.userkn_app_bank_loan_cnt = userkn_app_bank_loan_cnt;
//	}
//
//	public Integer getUserkn_app_buy_car_cnt() {
//		return userkn_app_buy_car_cnt;
//	}
//
//	public void setUserkn_app_buy_car_cnt(Integer userkn_app_buy_car_cnt) {
//		this.userkn_app_buy_car_cnt = userkn_app_buy_car_cnt;
//	}
//
//	public Integer getUserkn_app_buy_house_cnt() {
//		return userkn_app_buy_house_cnt;
//	}
//
//	public void setUserkn_app_buy_house_cnt(Integer userkn_app_buy_house_cnt) {
//		this.userkn_app_buy_house_cnt = userkn_app_buy_house_cnt;
//	}
//
//	public Integer getUserkn_app_car_owner_cnt() {
//		return userkn_app_car_owner_cnt;
//	}
//
//	public void setUserkn_app_car_owner_cnt(Integer userkn_app_car_owner_cnt) {
//		this.userkn_app_car_owner_cnt = userkn_app_car_owner_cnt;
//	}
//
//	public Integer getUserkn_app_cash_loan_cnt() {
//		return userkn_app_cash_loan_cnt;
//	}
//
//	public void setUserkn_app_cash_loan_cnt(Integer userkn_app_cash_loan_cnt) {
//		this.userkn_app_cash_loan_cnt = userkn_app_cash_loan_cnt;
//	}
//
//	public Integer getUserkn_app_credit_information_cnt() {
//		return userkn_app_credit_information_cnt;
//	}
//
//	public void setUserkn_app_credit_information_cnt(Integer userkn_app_credit_information_cnt) {
//		this.userkn_app_credit_information_cnt = userkn_app_credit_information_cnt;
//	}
//
//	public Integer getUserkn_app_financial_cnt() {
//		return userkn_app_financial_cnt;
//	}
//
//	public void setUserkn_app_financial_cnt(Integer userkn_app_financial_cnt) {
//		this.userkn_app_financial_cnt = userkn_app_financial_cnt;
//	}
//
//	public Integer getUserkn_app_gambling_cnt() {
//		return userkn_app_gambling_cnt;
//	}
//
//	public void setUserkn_app_gambling_cnt(Integer userkn_app_gambling_cnt) {
//		this.userkn_app_gambling_cnt = userkn_app_gambling_cnt;
//	}
//
//	public Integer getUserkn_app_home_decoration_cnt() {
//		return userkn_app_home_decoration_cnt;
//	}
//
//	public void setUserkn_app_home_decoration_cnt(Integer userkn_app_home_decoration_cnt) {
//		this.userkn_app_home_decoration_cnt = userkn_app_home_decoration_cnt;
//	}
//
//	public Integer getUserkn_app_instalment_cnt() {
//		return userkn_app_instalment_cnt;
//	}
//
//	public void setUserkn_app_instalment_cnt(Integer userkn_app_instalment_cnt) {
//		this.userkn_app_instalment_cnt = userkn_app_instalment_cnt;
//	}
//
//	public Integer getUserkn_app_insurance_cnt() {
//		return userkn_app_insurance_cnt;
//	}
//
//	public void setUserkn_app_insurance_cnt(Integer userkn_app_insurance_cnt) {
//		this.userkn_app_insurance_cnt = userkn_app_insurance_cnt;
//	}
//
//	public Integer getUserkn_app_job_search_cnt() {
//		return userkn_app_job_search_cnt;
//	}
//
//	public void setUserkn_app_job_search_cnt(Integer userkn_app_job_search_cnt) {
//		this.userkn_app_job_search_cnt = userkn_app_job_search_cnt;
//	}
//
//	public Integer getUserkn_app_on_sale_cnt() {
//		return userkn_app_on_sale_cnt;
//	}
//
//	public void setUserkn_app_on_sale_cnt(Integer userkn_app_on_sale_cnt) {
//		this.userkn_app_on_sale_cnt = userkn_app_on_sale_cnt;
//	}
//
//	public Integer getUserkn_app_p2p_financial_cnt() {
//		return userkn_app_p2p_financial_cnt;
//	}
//
//	public void setUserkn_app_p2p_financial_cnt(Integer userkn_app_p2p_financial_cnt) {
//		this.userkn_app_p2p_financial_cnt = userkn_app_p2p_financial_cnt;
//	}
//
//	public Integer getUserkn_app_payment_cnt() {
//		return userkn_app_payment_cnt;
//	}
//
//	public void setUserkn_app_payment_cnt(Integer userkn_app_payment_cnt) {
//		this.userkn_app_payment_cnt = userkn_app_payment_cnt;
//	}
//
//	public Integer getUserkn_app_renting_cnt() {
//		return userkn_app_renting_cnt;
//	}
//
//	public void setUserkn_app_renting_cnt(Integer userkn_app_renting_cnt) {
//		this.userkn_app_renting_cnt = userkn_app_renting_cnt;
//	}
//
//	public Integer getUserkn_app_save_money_cnt() {
//		return userkn_app_save_money_cnt;
//	}
//
//	public void setUserkn_app_save_money_cnt(Integer userkn_app_save_money_cnt) {
//		this.userkn_app_save_money_cnt = userkn_app_save_money_cnt;
//	}
//
//	public Integer getUserkn_app_shopping_cnt() {
//		return userkn_app_shopping_cnt;
//	}
//
//	public void setUserkn_app_shopping_cnt(Integer userkn_app_shopping_cnt) {
//		this.userkn_app_shopping_cnt = userkn_app_shopping_cnt;
//	}
//
//	public Integer getUserkn_app_stock_fund_cnt() {
//		return userkn_app_stock_fund_cnt;
//	}
//
//	public void setUserkn_app_stock_fund_cnt(Integer userkn_app_stock_fund_cnt) {
//		this.userkn_app_stock_fund_cnt = userkn_app_stock_fund_cnt;
//	}
//
//	public String getUserkn_firstcardtype() {
//		return userkn_firstcardtype;
//	}
//
//	public void setUserkn_firstcardtype(String userkn_firstcardtype) {
//		this.userkn_firstcardtype = userkn_firstcardtype;
//	}
//
//	public String getUserkn_lastcity() {
//		return userkn_lastcity;
//	}
//
//	public void setUserkn_lastcity(String userkn_lastcity) {
//		this.userkn_lastcity = userkn_lastcity;
//	}
//
//	public String getUserkn_lastlocation() {
//		return userkn_lastlocation;
//	}
//
//	public void setUserkn_lastlocation(String userkn_lastlocation) {
//		this.userkn_lastlocation = userkn_lastlocation;
//	}
//
//	public String getUserkn_lastprovince() {
//		return userkn_lastprovince;
//	}
//
//	public void setUserkn_lastprovince(String userkn_lastprovince) {
//		this.userkn_lastprovince = userkn_lastprovince;
//	}
//
//	public String getUserkn_newfirstcredit_date() {
//		return userkn_newfirstcredit_date;
//	}
//
//	public void setUserkn_newfirstcredit_date(String userkn_newfirstcredit_date) {
//		this.userkn_newfirstcredit_date = userkn_newfirstcredit_date;
//	}
//
//	public String getUserkn_newfirstdeposit_date() {
//		return userkn_newfirstdeposit_date;
//	}
//
//	public void setUserkn_newfirstdeposit_date(String userkn_newfirstdeposit_date) {
//		this.userkn_newfirstdeposit_date = userkn_newfirstdeposit_date;
//	}
//
//	public String getUserkn_phone_city() {
//		return userkn_phone_city;
//	}
//
//	public void setUserkn_phone_city(String userkn_phone_city) {
//		this.userkn_phone_city = userkn_phone_city;
//	}
//
//	public String getUserkn_phone_province() {
//		return userkn_phone_province;
//	}
//
//	public void setUserkn_phone_province(String userkn_phone_province) {
//		this.userkn_phone_province = userkn_phone_province;
//	}
//
//	public String getUserkn_rc_credit_card_num() {
//		return userkn_rc_credit_card_num;
//	}
//
//	public void setUserkn_rc_credit_card_num(String userkn_rc_credit_card_num) {
//		this.userkn_rc_credit_card_num = userkn_rc_credit_card_num;
//	}
//
//	public String getUserkn_rc_reposit_card_num() {
//		return userkn_rc_reposit_card_num;
//	}
//
//	public void setUserkn_rc_reposit_card_num(String userkn_rc_reposit_card_num) {
//		this.userkn_rc_reposit_card_num = userkn_rc_reposit_card_num;
//	}
//
//	public String getUserkn_user_reggap() {
//		return userkn_user_reggap;
//	}
//
//	public void setUserkn_user_reggap(String userkn_user_reggap) {
//		this.userkn_user_reggap = userkn_user_reggap;
//	}
//
//	public String getUserkn_work_location() {
//		return userkn_work_location;
//	}
//
//	public void setUserkn_work_location(String userkn_work_location) {
//		this.userkn_work_location = userkn_work_location;
//	}
//
//}
