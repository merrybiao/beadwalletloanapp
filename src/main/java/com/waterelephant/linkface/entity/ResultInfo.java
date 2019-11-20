package com.waterelephant.linkface.entity;

/**
 * 商汤人脸识别（code0088）
 * 
 * 
 * Module:
 * 
 * ResultInfo.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <商汤人脸识别>
 */
public class ResultInfo {
	private String sfzzm; // 身份证正面
	private String sfzfm; // 身份证反面
	private String name; // 姓名
	private String id_card_number; // 身份证号

	public String getSfzzm() {
		return sfzzm;
	}

	public void setSfzzm(String sfzzm) {
		this.sfzzm = sfzzm;
	}

	public String getSfzfm() {
		return sfzfm;
	}

	public void setSfzfm(String sfzfm) {
		this.sfzfm = sfzfm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId_card_number() {
		return id_card_number;
	}

	public void setId_card_number(String id_card_number) {
		this.id_card_number = id_card_number;
	}

}
