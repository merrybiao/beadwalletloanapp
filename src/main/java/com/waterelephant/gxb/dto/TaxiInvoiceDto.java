package com.waterelephant.gxb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 滴滴打车发票信息
 * @author dinglinhao
 *
 */
public class TaxiInvoiceDto implements Serializable {
	
	private static final long serialVersionUID = 8031111477360417727L;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date applyTime;//开票时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date dealTime;//滴滴处理发票的时间
	private BigDecimal invoiceValue;//发票金额
	private Integer status;//发票状态0未知,13已寄出,21电子代开票,22电子已开票,30其他状态
	private Integer expressFeeType;//邮费类型,0未知,1默认(包邮),2其他
	private Integer invoicePrintType;//发票出票类型,0未知,1电子,2纸质,3其他
	private String recipientName;//接收人姓名
	private String recipientPhone;//接收人手机号
	private String provinceName;//接收人省份
	private String cityName;//接收人城市
	private String districtName;//接收人区县
	private String detailedAddress;//接收人详细地址
	private String recipientEmail;//接收人邮箱
	private String invoiceTitle;//发票抬头
	private String invoiceContent;//发票内容
	private String invoiceRemark;//发票备注
	private String registeredAddressPhone;//发票上面打出来的的地址电话 购买方地址电话
	private String depositBankAccount;//发票上面打出来的 购买方开户行及账号
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date earliestCreateTime;//发票包含的行程中，最早的行程时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date latestCreateTime;//发票包含的行程中，最晚的行程时间
	private BigDecimal expressFee;//快递费用
	private Integer invoiceCount;//发票数量
	private Integer orderCount;//发票包含的行程数量
	
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public BigDecimal getInvoiceValue() {
		return invoiceValue;
	}
	public void setInvoiceValue(BigDecimal invoiceValue) {
		this.invoiceValue = invoiceValue;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getExpressFeeType() {
		return expressFeeType;
	}
	public void setExpressFeeType(Integer expressFeeType) {
		this.expressFeeType = expressFeeType;
	}
	public Integer getInvoicePrintType() {
		return invoicePrintType;
	}
	public void setInvoicePrintType(Integer invoicePrintType) {
		this.invoicePrintType = invoicePrintType;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getRecipientPhone() {
		return recipientPhone;
	}
	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getDetailedAddress() {
		return detailedAddress;
	}
	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	public String getRecipientEmail() {
		return recipientEmail;
	}
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getInvoiceContent() {
		return invoiceContent;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public String getInvoiceRemark() {
		return invoiceRemark;
	}
	public void setInvoiceRemark(String invoiceRemark) {
		this.invoiceRemark = invoiceRemark;
	}
	public String getRegisteredAddressPhone() {
		return registeredAddressPhone;
	}
	public void setRegisteredAddressPhone(String registeredAddressPhone) {
		this.registeredAddressPhone = registeredAddressPhone;
	}
	public String getDepositBankAccount() {
		return depositBankAccount;
	}
	public void setDepositBankAccount(String depositBankAccount) {
		this.depositBankAccount = depositBankAccount;
	}
	public Date getEarliestCreateTime() {
		return earliestCreateTime;
	}
	public void setEarliestCreateTime(Date earliestCreateTime) {
		this.earliestCreateTime = earliestCreateTime;
	}
	public Date getLatestCreateTime() {
		return latestCreateTime;
	}
	public void setLatestCreateTime(Date latestCreateTime) {
		this.latestCreateTime = latestCreateTime;
	}
	public BigDecimal getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}
	public Integer getInvoiceCount() {
		return invoiceCount;
	}
	public void setInvoiceCount(Integer invoiceCount) {
		this.invoiceCount = invoiceCount;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
}
