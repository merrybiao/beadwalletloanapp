package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "bw_credit_record")
public class BwCreditRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//主键
	private Long borrowerId;//借款人id
	private Long relationId;//关联id(入关联类型为授信,则关联授信表id)
	private Integer relationType;//关联类型(1:授信)
	private Integer creditType;//认证类型---1：运营商 2：个人信息 3：身份认证 4：芝麻信用 5：社保 6：公积金 7：邮箱 8：淘宝 9：京东  10:单位信息  11：用款确认 12.支付宝 13.滴滴 14.美团 15.学生
	private Integer creditThird;//认证三方---1:商汤  2:腾讯云 3:融360  4:魔方  5:公信宝	
	private String queryId;//三方数据查询id(例如融360：searchId,魔方：taskId)
	private String dataType;//数据类型(比如运营商的task,report)
	private String ossFileUrl;//oss存储数据文件地址
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
}
