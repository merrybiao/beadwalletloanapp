package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 */
@Table(name = "bw_dssj_pushdata")
public class BwDssjPushdata implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号
     */
    private String orderno;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 签名
     */
    private String sign;

    /**
     * 是否压缩
     */
    private String compressstatus;

    /**
     * 兼容字段，意义同res里transId
     */
    private String tino;

    /**
     * 身份证
     */
    private String idcard;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 姓名
     */
    private String name;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 交易号是客户端生成的唯一性号码，调用接口后服务器返回给客户端，客户端根据该参数判断数据的一致性
     */
    private String transid;

    /**
     * 信用分
     */
    private String score;

    /**
     * 推送接口类型
     */
    private String buestype;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCompressstatus() {
        return compressstatus;
    }

    public void setCompressstatus(String compressstatus) {
        this.compressstatus = compressstatus;
    }

    public String getTino() {
        return tino;
    }

    public void setTino(String tino) {
        this.tino = tino;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBuestype() {
        return buestype;
    }

    public void setBuestype(String buestype) {
        this.buestype = buestype;
    }

}
