package com.waterelephant.dssj.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * bw_dssj_authdata
 * @author 
 */
public class DssjAuthpulldata implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 外键
     */
    private Long uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 订单号
     */
    private String orderno;

    /**
     * 授权操作结果，success成功，其他都是失败
     */
    private String status;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 是否压缩
     */
    private String compressstatus;

    /**
     * 签名
     */
    private String sign;

    /**
     * 唯一识别号
     */
    private String tino;
    
    /**
     * 授权链接
     */
    private String url;

    /**
     * 重定向页面地址
     */
    private String reditUrl;

    /**
     * 推送数据地址
     */
    private String notifyUrl;

    /**
     * 信用分
     */
    private Integer score;

    /**
     * 授权时间
     */
    private Date insertTime;

    /**
     * 查询分数时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCompressstatus() {
        return compressstatus;
    }

    public void setCompressstatus(String compressstatus) {
        this.compressstatus = compressstatus;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTino() {
        return tino;
    }

    public void setTino(String tino) {
        this.tino = tino;
    }

    public String getReditUrl() {
        return reditUrl;
    }

    public void setReditUrl(String reditUrl) {
        this.reditUrl = reditUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    
}