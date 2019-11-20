package com.waterelephant.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 身份认证结果
 *
 * @author 王亚楠
 * @version 1.0
 * @date 2018/9/21
 * @since JDK 1.8
 */
@Table(name = "bw_identity_verify_result")
public class BwIdentityVerifyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 工单id */
    private Long orderId;
    /** 1:face++ 2:商汤 */
    private Integer verifySource;
    /** 1:正面照和活体照片对比 2:三方预留照片和活体照片对比 */
    private Integer type;
    /** 1:对比通过 2:对比通过,但分数过低, 3:对比失败 */
    private Integer result;
    /** 对比结果返回分数 */
    private String score;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getVerifySource() {
        return verifySource;
    }

    public void setVerifySource(Integer verifySource) {
        this.verifySource = verifySource;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
