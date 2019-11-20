package com.waterelephant.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name= "bw_credit_verify_source")
public class BwCreditVerifySource implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private Long borrowerId;//用户id
	private Long adjunctId;//附件表id
	private Long creditId;//授信id
	private Integer verifySource;//认证来源：0、人工，  1、face++， 2、商汤
	private LocalDateTime createTime;//入库时间

}
