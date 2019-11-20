package com.waterelephant.gxb.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 滴滴打车-车主信息
 * @author dinglinhao
 *
 */
public class TaxiDriverInfoDto implements Serializable {

	private static final long serialVersionUID = 240264234975487249L;
	
	private String driveLicenseName;//驾照上的姓名
	private String driveLicenseNumber;//驾照号码
	private String carNumber;//车牌号
	private String carBrand;//车辆品牌
	private String carType;//车辆型号
	private String carColor;//车辆颜色
	private String travelLicenseName;//车辆所有人
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date driveLicenseStartDate;//驾照领证日期
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date travelStartDate;//行驶证注册日期
	
	public String getDriveLicenseName() {
		return driveLicenseName;
	}
	public void setDriveLicenseName(String driveLicenseName) {
		this.driveLicenseName = driveLicenseName;
	}
	public String getDriveLicenseNumber() {
		return driveLicenseNumber;
	}
	public void setDriveLicenseNumber(String driveLicenseNumber) {
		this.driveLicenseNumber = driveLicenseNumber;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getTravelLicenseName() {
		return travelLicenseName;
	}
	public void setTravelLicenseName(String travelLicenseName) {
		this.travelLicenseName = travelLicenseName;
	}
	public Date getDriveLicenseStartDate() {
		return driveLicenseStartDate;
	}
	public void setDriveLicenseStartDate(Date driveLicenseStartDate) {
		this.driveLicenseStartDate = driveLicenseStartDate;
	}
	public Date getTravelStartDate() {
		return travelStartDate;
	}
	public void setTravelStartDate(Date travelStartDate) {
		this.travelStartDate = travelStartDate;
	}

}
