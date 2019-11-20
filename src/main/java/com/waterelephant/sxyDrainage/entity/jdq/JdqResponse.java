///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.jdq;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import java.util.Date;
//
///**
// * Module:
// * <p>
// * JdqResponse.java
// *
// * @author 王飞
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public class JdqResponse {
//    /**
//     * 0：借点钱获取并处理成功其他为失败
//     */
//    private Integer code;
//    /**
//     * 备注信息
//     */
//    private String desc;
//    /**
//     * 数据
//     */
//    private Object data;
//
//    /**
//     * 开始时间
//     */
//    @JsonIgnore
//    private Date startTime;
//
//    /**
//     * 渠道
//     */
//    @JsonIgnore
//    private String channelId;
//    /**
//     * 关键字
//     */
//    @JsonIgnore
//    private String key;
//    /**
//     * 接口调用成功
//     */
//    public static final int CODE_SUCCESS = 0;
//    /**
//     * 一步绑卡成功，无需发验证码，不用再调绑卡接口
//     */
//    public static final int CODE_BIND_CARD_SUCCESS = 1;
//    /**
//     * 进件失败 数据格式不对 或绑卡失败
//     */
//    public static final int CODE_FAIL = -1;
//    /**
//     * 获取请求成功但因为处理失败，机构系统异常或服务间异常
//     */
//    public static final int CODE_FAIL_Exception = -2;
//    /**
//     * 未确认Unconfirmed
//     */
//    public static final int CODE_UNCONFIRMED = -3;
//    /**
//     * 重复调用
//     */
//    public static final int CODE_DUPLICATECALL = 101;
//
//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
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
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
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
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//
//}
