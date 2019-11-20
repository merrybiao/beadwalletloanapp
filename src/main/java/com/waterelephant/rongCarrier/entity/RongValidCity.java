package com.waterelephant.rongCarrier.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 融360 - 社保 - 可用城市(code0084)
 *
 * @author liuDaodao
 * @version 1.0
 * @create_date 2017/5/31
 */
@Table(name = "bw_rong_validCity")
public class RongValidCity {
	public static final int TYPE_SHEBAO = 1; // 社保
	public static final int TYPE_GONGJIJIN = 2; // 公积金

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 主键
	private int cityId; // 城市ID
	private String cityName; // 城市名称
	private String privinceName; // 省份
	private int type; // 类型（1：社保，2：公积金）

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPrivinceName() {
		return privinceName;
	}

	public void setPrivinceName(String privinceName) {
		this.privinceName = privinceName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
