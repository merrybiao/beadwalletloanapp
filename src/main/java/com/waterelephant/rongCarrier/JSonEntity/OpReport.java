package com.waterelephant.rongCarrier.JSonEntity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 运营商报告
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 13:41
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpReport {

	private String error;
	private String msg;
	private List<TripAnalysis> trip_analysis;
	private UserPortrait user_portrait;
	private InputInfo input_info;
	private List<CallLog> call_log;
	private List<AreaAnalysis> area_analysis;
	private List<EmergencyAnalysis> emergency_analysis;
	private BasicInfo basic_info;
	private List<MonthlyConsumption> monthly_consumption;
	private List<SpecialCate> special_cate;
	private HeadInfo head_info;
	private RiskAnalysis risk_analysis;

	public List<TripAnalysis> getTrip_analysis() {
		return trip_analysis;
	}

	public void setTrip_analysis(List<TripAnalysis> trip_analysis) {
		this.trip_analysis = trip_analysis;
	}

	public UserPortrait getUser_portrait() {
		return user_portrait;
	}

	public void setUser_portrait(UserPortrait user_portrait) {
		this.user_portrait = user_portrait;
	}

	public InputInfo getInput_info() {
		return input_info;
	}

	public void setInput_info(InputInfo input_info) {
		this.input_info = input_info;
	}

	public HeadInfo getHead_info() {
		return head_info;
	}

	public void setHead_info(HeadInfo head_info) {
		this.head_info = head_info;
	}

	public List<CallLog> getCall_log() {
		return call_log;
	}

	public void setCall_log(List<CallLog> call_log) {
		this.call_log = call_log;
	}

	public List<AreaAnalysis> getArea_analysis() {
		return area_analysis;
	}

	public void setArea_analysis(List<AreaAnalysis> area_analysis) {
		this.area_analysis = area_analysis;
	}

	public List<EmergencyAnalysis> getEmergency_analysis() {
		return emergency_analysis;
	}

	public void setEmergency_analysis(List<EmergencyAnalysis> emergency_analysis) {
		this.emergency_analysis = emergency_analysis;
	}

	public BasicInfo getBasic_info() {
		return basic_info;
	}

	public void setBasic_info(BasicInfo basic_info) {
		this.basic_info = basic_info;
	}

	public List<MonthlyConsumption> getMonthly_consumption() {
		return monthly_consumption;
	}

	public void setMonthly_consumption(List<MonthlyConsumption> monthly_consumption) {
		this.monthly_consumption = monthly_consumption;
	}

	public List<SpecialCate> getSpecial_cate() {
		return special_cate;
	}

	public void setSpecial_cate(List<SpecialCate> special_cate) {
		this.special_cate = special_cate;
	}

	public RiskAnalysis getRisk_analysis() {
		return risk_analysis;
	}

	public void setRisk_analysis(RiskAnalysis risk_analysis) {
		this.risk_analysis = risk_analysis;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
