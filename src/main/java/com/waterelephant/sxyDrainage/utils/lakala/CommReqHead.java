//package com.waterelephant.sxyDrainage.utils.lakala;
//
//import org.hibernate.validator.constraints.NotBlank;
//
//import java.io.Serializable;
//
///**
// * 请求报文体head
// * 
// * @ClassName RequestHead
// * @Description TODO(这里用一句话描述这个类的作用)
// * @author liujingjing
// * @Date 2018年3月28日 下午3:20:42
// * @version 1.0.0
// */
//public class CommReqHead implements Serializable {
//
//    /**
//     * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
//     */
//    private static final long serialVersionUID = 1L;
//
//    /*
//     * 交易流水号 String(32) M
//     */
//    @NotBlank(message = "NotBlank")
//    private String serviceSn;
//
//    /*
//     * 交易服务码 String(5) M
//     */
//    @NotBlank(message = "NotBlank")
//    private String serviceId;
//
//    /*
//     * 技术渠道编号 String(16) M
//     */
//    @NotBlank(message = "NotBlank")
//    private String channelId;
//
//    /*
//     * 交易来源 String(4) M
//     */
//    @NotBlank(message = "NotBlank")
//    private String inputSource;
//
//    /*
//     * 操作员id String(8) N
//     */
//    private String opId;
//
//    /*
//     * 交易时间 String(14) M YYYYMMDDHHMMSS
//     */
//    @NotBlank(message = "NotBlank")
//    private String requestTime;
//
//    /*
//     * 版本号 String(6) M 1.0.0
//     */
//    @NotBlank(message = "NotBlank")
//    private String versionId;
//
//    /*
//     * 报文验证码 String(30) N
//     */
//    private String mac;
//
//    /*
//     * 业务渠道 String(48) M
//     */
//    @NotBlank(message = "NotBlank")
//    private String businessChannel;
//
//    /**
//     * 全局请求流水
//     */
//    private String sid;
//
//    /*
//     * 签名 String (200)
//     */
//    @NotBlank(message = "NotBlank")
//    private String signData;
//
//    public String getServiceSn() {
//        return serviceSn;
//    }
//
//    public void setServiceSn(String serviceSn) {
//        this.serviceSn = serviceSn;
//    }
//
//    public String getServiceId() {
//        return serviceId;
//    }
//
//    public void setServiceId(String serviceId) {
//        this.serviceId = serviceId;
//    }
//
//    public String getChannelId() {
//        return channelId;
//    }
//
//    public void setChannelId(String channelId) {
//        this.channelId = channelId;
//    }
//
//    public String getInputSource() {
//        return inputSource;
//    }
//
//    public void setInputSource(String inputSource) {
//        this.inputSource = inputSource;
//    }
//
//    public String getOpId() {
//        return opId;
//    }
//
//    public void setOpId(String opId) {
//        this.opId = opId;
//    }
//
//    public String getRequestTime() {
//        return requestTime;
//    }
//
//    public void setRequestTime(String requestTime) {
//        this.requestTime = requestTime;
//    }
//
//    public String getVersionId() {
//        return versionId;
//    }
//
//    public void setVersionId(String versionId) {
//        this.versionId = versionId;
//    }
//
//    public String getMac() {
//        return mac;
//    }
//
//    public void setMac(String mac) {
//        this.mac = mac;
//    }
//
//    public String getBusinessChannel() {
//        return businessChannel;
//    }
//
//    public void setBusinessChannel(String businessChannel) {
//        this.businessChannel = businessChannel;
//    }
//
//    public String getSignData() {
//        return signData;
//    }
//
//    public void setSignData(String signData) {
//        this.signData = signData;
//    }
//
//    public String getSid() {
//        return sid;
//    }
//
//    public void setSid(String sid) {
//        this.sid = sid;
//    }
//}
