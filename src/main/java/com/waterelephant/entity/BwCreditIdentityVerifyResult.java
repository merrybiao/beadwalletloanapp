package com.waterelephant.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * <p>身份认证结果
 * @author dinglinhao
 */
@Data
@Table(name = "bw_credit_identity_verify_result")
public class BwCreditIdentityVerifyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 关联id */
    private Long relationId;
    /**借款人ID 申请人ID**/
    private Long borrowerId;
    /**关联类型(1:授信)**/
    private Integer relationType;
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

    
}
