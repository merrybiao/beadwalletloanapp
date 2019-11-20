package com.waterelephant.entity;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="bw_credit_auth")
public class BwCreditAuth {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//主键
	private Long relationId;//关联id(入关联类型为授信,则关联授信表id)
	private Integer relationType;//关联类型(1:授信)
	private Integer authType;//认证类型(1：运营商 2：个人信息 3：身份认证 4：芝麻信用 5：社保 6：公积金 7：邮箱 8：淘宝 9：京东  10:单位信息  11：用款确认 12.支付宝 13.滴滴 14.美团 15.学生',)
	private Integer isStudent;//是否是学生
	private Long authChannel;//认证渠道
	private LocalDateTime createTime;//创建时间
	private LocalDateTime updateTime;//修改时间


}
