package com.waterelephant.gxb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 公信宝-滴滴推送数据-订单信息
 * @author dinglinhao
 * @date 2018年6月29日11:55:09
 *
 */
public class TaxiOrderDto implements Serializable {
    private static final long serialVersionUID = 6243693089676569400L;

    /**
     * 0未知/取消订单 1已付 2未付
     */
    private int payStatus;

    /**
     * 总费用（元）
     */
    private BigDecimal totalFee;

    /**
     * 实际付费（元）
     */
    private BigDecimal actualPayFee;
    
    /**
     * 货币简写如 CNY
     */
    private String currency;

    /**
     * 订单状态 0未知 3已完成 6已关闭
     */
    private Integer status;

    /**
     * 城市名称
     */
    private String cityName;
    
    /**
     * 区号
     */
    private String district;
    
    /**
     * 地区编号
     */
    private String fromArea;
    
    /**
     * 订单产品类型名（快车,专车等）
     */
    private String productName;
    
    /**
     * 订单产品类型 0未知 4快车（后续会陆续添加）
     */
    private Integer productType;
    
    /**
     * 上车时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    /**
     * 订单结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishTime;
    /**
     * 起始位置地址
     */
    private String fromAddress;
    
    /**
     * 起始位置名称
     */
    private String fromName;

    /**
     * 终点位置地址
     */
    private String toAddress;

    /**
     * 终点位置名称
     */
    private String toName;
    /**
     * 起始位置经度
     */
    private Float fromLng;
    /**
     * 起始位置纬度
     */
    private Float fromLat;
    /**
     * 终点位置经度
     */
    private Float toLng;
    /**
     * 终点位置纬度
     */
    private Float toLat;
    
    /**
     * 司机名
     */
    private String driverName;
    /**
     * 司机公司名
     */
    private String driverCompany;
    /**
     * 司机评价等级
     */
    private Float driverLevel;
    
    /**
     * 车牌号
     */
    private String licenseNum;
    
    /**
     * 车辆描述
     */
    private String carTitle;
    
    /**
     * 司机接单数
     */
    private Integer driverOrderCnt;
    /**
     * 对当前订单的评价 0表示为评价 1-5分别表示1-5星
     */
    private Integer commentLevel;
    
    /**
     * 订单创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getActualPayFee() {
		return actualPayFee;
	}

	public void setActualPayFee(BigDecimal actualPayFee) {
		this.actualPayFee = actualPayFee;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getFromArea() {
		return fromArea;
	}

	public void setFromArea(String fromArea) {
		this.fromArea = fromArea;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public Float getFromLng() {
		return fromLng;
	}

	public void setFromLng(Float fromLng) {
		this.fromLng = fromLng;
	}

	public Float getFromLat() {
		return fromLat;
	}

	public void setFromLat(Float fromLat) {
		this.fromLat = fromLat;
	}

	public Float getToLng() {
		return toLng;
	}

	public void setToLng(Float toLng) {
		this.toLng = toLng;
	}

	public Float getToLat() {
		return toLat;
	}

	public void setToLat(Float toLat) {
		this.toLat = toLat;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverCompany() {
		return driverCompany;
	}

	public void setDriverCompany(String driverCompany) {
		this.driverCompany = driverCompany;
	}

	public Float getDriverLevel() {
		return driverLevel;
	}

	public void setDriverLevel(Float driverLevel) {
		this.driverLevel = driverLevel;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public String getCarTitle() {
		return carTitle;
	}

	public void setCarTitle(String carTitle) {
		this.carTitle = carTitle;
	}

	public Integer getDriverOrderCnt() {
		return driverOrderCnt;
	}

	public void setDriverOrderCnt(Integer driverOrderCnt) {
		this.driverOrderCnt = driverOrderCnt;
	}

	public Integer getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(Integer commentLevel) {
		this.commentLevel = commentLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
 }