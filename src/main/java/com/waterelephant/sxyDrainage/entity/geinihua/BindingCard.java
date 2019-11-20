//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//
//import org.hibernate.validator.constraints.NotBlank;
//
///**
// * 绑卡信息
// *
// * @author xanthuim
// */
//
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
//public class BindingCard {
//    /**
//     * 订单唯一标示	string	否	改订单号为北斗平台生成，机构方需要保存。
//     */
//    @NotBlank(message = "orderNo不能为空")
//    private String orderNo;
//    /**
//     * 用户姓名	string	否	绑卡用户姓名
//     */
//    @NotBlank(message = "userName不能为空")
//    private String userName;
//    /**
//     * 用户手机号	string	否	绑卡用户手机号
//     */
//    @NotBlank(message = "mobile不能为空")
//    private String mobile;
//    /**
//     * 用户身份证号码	string	否	绑卡用户身份证号码
//     */
//    @NotBlank(message = "idCard不能为空")
//    private String idCard;
//    /**
//     * 银行代码	string	否	例如：ABC，详细请参见底部银行代码对照表
//     */
//    @NotBlank(message = "bankCode不能为空")
//    private String bankCode;
//    /**
//     * 卡号	string	否	用户卡号
//     */
//    @NotBlank(message = "bankCard不能为空")
//    private String bankCard;
//    /**
//     * ip地址	string	否	用户下单使用的ip地址
//     */
//    private String ip;
//    /**
//     * 区分新卡老卡	int	否	默认为1，1新卡 2已绑定卡
//     */
//    private Integer cardType;
//    /**
//     * 自定义卡信息	string	是	合作机构自定义卡信息字段，用于合作机构确定已绑定的卡
//     */
//    private String cardInfo;
//    /**
//     * 绑卡类型	int	是	0储蓄卡，1信用卡
//     */
//    private Integer creditCard;
//    /**
//     * 绑卡完成后需要跳转的页面	string	否	合作机构根据此参数，完成绑卡后进行页面跳转
//     * 注意：该页面需要合作机构传递银行接口的处理结果，以result参数的形式，
//     * 如：[http:\北斗回调接口?result=1"，1表示成功，0表示失败])
//     */
//    private String backForwardUrl;
//
//    /**
//     * 短信验证码接口	string	否	短信验证码
//     */
//    private String smsCode;
//
//    /**
//     * int 否 是否还款卡，1:还款卡，0:收款卡
//     */
//    private Integer repayCard;
//    /**
//     * int 否 是否当前默认卡，1：是，0：否
//     */
//    private Integer isDefault;
//
//    public String getOrderNo() {
//        return orderNo;
//    }
//
//    public void setOrderNo(String orderNo) {
//        this.orderNo = orderNo;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getIdCard() {
//        return idCard;
//    }
//
//    public void setIdCard(String idCard) {
//        this.idCard = idCard;
//    }
//
//    public String getBankCode() {
//        return bankCode;
//    }
//
//    public void setBankCode(String bankCode) {
//        this.bankCode = bankCode;
//    }
//
//    public String getBankCard() {
//        return bankCard;
//    }
//
//    public void setBankCard(String bankCard) {
//        this.bankCard = bankCard;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public Integer getCardType() {
//        return cardType;
//    }
//
//    public void setCardType(Integer cardType) {
//        this.cardType = cardType;
//    }
//
//    public String getCardInfo() {
//        return cardInfo;
//    }
//
//    public void setCardInfo(String cardInfo) {
//        this.cardInfo = cardInfo;
//    }
//
//    public Integer getCreditCard() {
//        return creditCard;
//    }
//
//    public void setCreditCard(Integer creditCard) {
//        this.creditCard = creditCard;
//    }
//
//    public String getBackForwardUrl() {
//        return backForwardUrl;
//    }
//
//    public void setBackForwardUrl(String backForwardUrl) {
//        this.backForwardUrl = backForwardUrl;
//    }
//
//    public String getSmsCode() {
//        return smsCode;
//    }
//
//    public void setSmsCode(String smsCode) {
//        this.smsCode = smsCode;
//    }
//
//    public Integer getRepayCard() {
//        return repayCard;
//    }
//
//    public void setRepayCard(Integer repayCard) {
//        this.repayCard = repayCard;
//    }
//
//    public Integer getIsDefault() {
//        return isDefault;
//    }
//
//    public void setIsDefault(Integer isDefault) {
//        this.isDefault = isDefault;
//    }
//}
