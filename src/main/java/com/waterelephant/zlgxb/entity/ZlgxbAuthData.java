package com.waterelephant.zlgxb.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ZlgxbAuthData implements Serializable {
    /**
     * 主键自增
     */
    private Long id;

    /**
     * 请求授权唯一性标志
     */
    private String sequenceNo;

    /**
     * 授权令牌
     */
    private String token;

    /**
     * 商户Id
     */
    private String appId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机
     */
    private String phone;

    /**
     * 身份证
     */
    private String idcard;

    /**
     * 授权项
     */
    private String authItem;

    /**
     * 请求时间
     */
    private String timestamp;

    /**
     * 芝麻分
     */
    private Integer score;

    /**
     * 授权状态1/2/3
     */
    private Boolean authStatus;

    /**
     * 授权时间
     */
    private Date authTime;

    /**
     * 授权返回地址
     */
    private String returnUrl;

    /**
     * 数据推送通知接口
     */
    private String notifyUrl;

    /**
     * 创建记录时间
     */
    private Date createTime;

    /**
     * 授权重定向地址
     */
    private String redirectUrl;

    /**
     * 授权项
     */
    private String authitem;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAuthItem() {
        return authItem;
    }

    public void setAuthItem(String authItem) {
        this.authItem = authItem;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Boolean authStatus) {
        this.authStatus = authStatus;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getAuthitem() {
        return authitem;
    }

    public void setAuthitem(String authitem) {
        this.authitem = authitem;
    }

}