package com.waterelephant.dto;

import com.waterelephant.entity.BwCardCity;

public class IndentityScoreDto {

	private String sex;
	private Integer age;
	private BwCardCity bwCardCity;
	private String sqCity;

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public BwCardCity getBwCardCity() {
		return bwCardCity;
	}
	public void setBwCardCity(BwCardCity bwCardCity) {
		this.bwCardCity = bwCardCity;
	}
	public String getSqCity() {
		return sqCity;
	}
	public void setSqCity(String sqCity) {
		this.sqCity = sqCity;
	}
	
	
}
