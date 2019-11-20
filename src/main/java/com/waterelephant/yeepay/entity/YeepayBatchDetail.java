package com.waterelephant.yeepay.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**
 * 实体类
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-22 10:21:09
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name="bw_yeepay_batch_detail")
public class YeepayBatchDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id; //
    
    private String merchantBatchNo; //商户批次号
    
    private Long borrowerId; //借款人id

    public Integer getYeepay_type() {
        return yeepay_type;
    }

    public void setYeepay_type(Integer yeepay_type) {
        this.yeepay_type = yeepay_type;
    }

    private Long orderId; //订单id

    private Integer yeepay_type; //易宝扣款类型，1.批量扣款，2单笔扣款

    private String requestNo; // 商户请求号
   
    private String bathprotocolnNo; // 协议号
    
    private String amount; // 金额
   
    private String productName; // 商品名称
   
    private String avaliableTime; // 有效期
    
    private String cardNo; // 卡号
   
    private String userName; // 用户姓名
  
    private String phone; // 手机号
    
    private String idCardNo; // 身份证号
   
    private String free1; // 自由字段1
   
    private String free2; // 自由字段2
    
    private String free3; // 自由字段3
    
    private String bankCode; //银行编码
    
    private String status; //订单状态
    
    private String errorCode; //错误代码
    
    private String errorMsg; //错误信息
    
    private String ybOrderId; //易宝该笔扣款的流水号，调用扣款查询接口返回的数据
    
    private Integer payType;//用来表示还款还是办理展期：1，还款 2,展期
    
    private Integer terminalType;//终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
    
    private String bankSuccessDate;//扣款完成时间

    public String getBankSuccessDate() {
		return bankSuccessDate;
	}
	public void setBankSuccessDate(String bankSuccessDate) {
		this.bankSuccessDate = bankSuccessDate;
	}
	public String getYbOrderId() {
		return ybOrderId;
	}
	public void setYbOrderId(String ybOrderId) {
		this.ybOrderId = ybOrderId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }

    public String getMerchantBatchNo() {
        return merchantBatchNo;
    }

    public void setMerchantBatchNo(String merchantBatchNo) {
        this.merchantBatchNo = merchantBatchNo;
    }

    public void setBorrowerId(Long borrowerId){
        this.borrowerId=borrowerId;
    }
    public Long getBorrowerId(){
        return this.borrowerId;
    }

    public void setOrderId(Long orderId){
        this.orderId=orderId;
    }
    public Long getOrderId(){
        return this.orderId;
    }

    public void setRequestNo(String requestNo){
        this.requestNo=requestNo;
    }
    public String getRequestNo(){
        return this.requestNo;
    }

    public void setBathprotocolnNo(String bathprotocolnNo){
        this.bathprotocolnNo=bathprotocolnNo;
    }
    public String getBathprotocolnNo(){
        return this.bathprotocolnNo;
    }

    public void setAmount(String amount){
        this.amount=amount;
    }
    public String getAmount(){
        return this.amount;
    }

    public void setProductName(String productName){
        this.productName=productName;
    }
    public String getProductName(){
        return this.productName;
    }

    public void setAvaliableTime(String avaliableTime){
        this.avaliableTime=avaliableTime;
    }
    public String getAvaliableTime(){
        return this.avaliableTime;
    }

    public void setCardNo(String cardNo){
        this.cardNo=cardNo;
    }
    public String getCardNo(){
        return this.cardNo;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getUserName(){
        return this.userName;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return this.phone;
    }

    public void setIdCardNo(String idCardNo){
        this.idCardNo=idCardNo;
    }
    public String getIdCardNo(){
        return this.idCardNo;
    }

    public void setFree1(String free1){
        this.free1=free1;
    }
    public String getFree1(){
        return this.free1;
    }

    public void setFree2(String free2){
        this.free2=free2;
    }
    public String getFree2(){
        return this.free2;
    }

    public void setFree3(String free3){
        this.free3=free3;
    }
    public String getFree3(){
        return this.free3;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("YeepayBatchDetail[");
        sb.append("id=");
        sb.append(id);
        sb.append(",borrowerId=");
        sb.append(borrowerId);
        sb.append(",orderId=");
        sb.append(orderId);
        sb.append(",requestNo=");
        sb.append(requestNo);
        sb.append(",bathprotocolnNo=");
        sb.append(bathprotocolnNo);
        sb.append(",amount=");
        sb.append(amount);
        sb.append(",productName=");
        sb.append(productName);
        sb.append(",avaliableTime=");
        sb.append(avaliableTime);
        sb.append(",cardNo=");
        sb.append(cardNo);
        sb.append(",userName=");
        sb.append(userName);
        sb.append(",phone=");
        sb.append(phone);
        sb.append(",idCardNo=");
        sb.append(idCardNo);
        sb.append(",free1=");
        sb.append(free1);
        sb.append(",free2=");
        sb.append(free2);
        sb.append(",free3=");
        sb.append(free3);
        sb.append("]");
        return sb.toString();
    }
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
