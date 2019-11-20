//package com.waterelephant.sxyDrainage.utils.lakala;
//
//import java.io.Serializable;
//
///**
// * 公共返回参数报文头
// * 
// * @ClassName ResponseHead
// * @Description TODO(这里用一句话描述这个类的作用)
// * @author king
// * @Date 2018年3月29日 下午3:49:54
// * @version 1.0.0
// */
//public class CommResHead implements Serializable {
//
//    /**
//     * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
//     */
//    private static final long serialVersionUID = 1L;
//
//    /*
//     * 响应码 String(6)
//     */
//    private String code;
//
//    /*
//     * 响应码描述 String(200)
//     */
//    private String desc;
//
//    /*
//     * 技术渠道编号 String(16)
//     */
//    private String channelId;
//
//    /*
//     * 响应流水号 String (32)
//     */
//    private String serviceSn;
//
//    /*
//     * 响应时间 String (14)
//     */
//    private String serviceTime;
//
//    /*
//     * 全局请求流水 String (64)
//     */
//    private String sid;
//
//    /*
//     * 业务渠道 String (48)
//     */
//    private String businessChannel;
//
//    /*
//     * 业务渠道 String (200)
//     */
//    private String signData;
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
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
//    public String getServiceSn() {
//        return serviceSn;
//    }
//
//    public void setServiceSn(String serviceSn) {
//        this.serviceSn = serviceSn;
//    }
//
//    public String getServiceTime() {
//        return serviceTime;
//    }
//
//    public void setServiceTime(String serviceTime) {
//        this.serviceTime = serviceTime;
//    }
//
//    public String getSid() {
//        return sid;
//    }
//
//    public void setSid(String sid) {
//        this.sid = sid;
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
//}
