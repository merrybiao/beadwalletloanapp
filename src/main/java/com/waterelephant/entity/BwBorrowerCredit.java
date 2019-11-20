package com.waterelephant.entity;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
/**
 * 用户授信额度表(授信单申请人额度表)
 * 注意 LocalDateTime 为java8新特性
 * @author dinglinhao
 *
 */
@Data
@Table(name="bw_borrower_credit")
public class BwBorrowerCredit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//主键id
	private String creditNo;//授信单号
	private Long borrowerId;//申请人ID
	private Long productId;//产品id
	private Double creditAmount;//授信额度
	private Double usedAmount;//已使用额度
	private Integer status;//状态（授信状态1，草稿；2，初审；3，终审；4，已通过；5，过期；6，结束；7，拒绝；8，撤回
	private Integer channel;//来源渠道 1:安卓 2:ios 3:后台
	private LocalDateTime expiryDate;//有效期限(到期截止时间)
	private LocalDateTime submitTime;//提交审核时间
	private LocalDateTime createTime;//创建时间
	private LocalDateTime updateTime;//更新时间

}