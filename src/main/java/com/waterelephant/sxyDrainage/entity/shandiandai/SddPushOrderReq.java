//package com.waterelephant.sxyDrainage.entity.shandiandai;
//
//import com.waterelephant.sxyDrainage.entity.shandiandai.pushorder.*;
//
///**
// * 借款申请实体类
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/1
// * @since JDK 1.8
// */
//public class SddPushOrderReq {
//
//    /** 订单编号 */
//    private String loanId;
//    /** 用户 IP */
//    private String ip;
//    /** 用户基本信息，json格式 */
//    private SddPersonalInfo personalInfo;
//    /** 手机信息，json格式 */
//    private SddmobileData mobileData;
//    /** 贷款需求额度 */
//    private String loanAmount;
//    /** 贷款需求期限；单位月 */
//    private String loanMaturity;
//    /** 芝麻认证,json 格式 */
//    private SddZhiMa zhima;
//    /** 认证信息，json格式 */
//    private SddOcr ocr;
//    /** 信用卡认证，json格式 */
//    private SddCreditCard creditCard;
//    /** 人脸识别，json格式 */
//    private SddFace face;
//    /** 运营商报告，json格式 */
//    private SddCarrierInfo carrierInfo;
//
//    public String getLoanId() {
//        return loanId;
//    }
//
//    public void setLoanId(String loanId) {
//        this.loanId = loanId;
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
//    public SddPersonalInfo getPersonalInfo() {
//        return personalInfo;
//    }
//
//    public void setPersonalInfo(SddPersonalInfo personalInfo) {
//        this.personalInfo = personalInfo;
//    }
//
//    public SddmobileData getMobileData() {
//        return mobileData;
//    }
//
//    public void setMobileData(SddmobileData mobileData) {
//        this.mobileData = mobileData;
//    }
//
//    public String getLoanAmount() {
//        return loanAmount;
//    }
//
//    public void setLoanAmount(String loanAmount) {
//        this.loanAmount = loanAmount;
//    }
//
//    public String getLoanMaturity() {
//        return loanMaturity;
//    }
//
//    public void setLoanMaturity(String loanMaturity) {
//        this.loanMaturity = loanMaturity;
//    }
//
//    public SddZhiMa getZhima() {
//        return zhima;
//    }
//
//    public void setZhima(SddZhiMa zhima) {
//        this.zhima = zhima;
//    }
//
//    public SddOcr getOcr() {
//        return ocr;
//    }
//
//    public void setOcr(SddOcr ocr) {
//        this.ocr = ocr;
//    }
//
//    public SddCreditCard getCreditCard() {
//        return creditCard;
//    }
//
//    public void setCreditCard(SddCreditCard creditCard) {
//        this.creditCard = creditCard;
//    }
//
//    public SddFace getFace() {
//        return face;
//    }
//
//    public void setFace(SddFace face) {
//        this.face = face;
//    }
//
//    public SddCarrierInfo getCarrierInfo() {
//        return carrierInfo;
//    }
//
//    public void setCarrierInfo(SddCarrierInfo carrierInfo) {
//        this.carrierInfo = carrierInfo;
//    }
//}
