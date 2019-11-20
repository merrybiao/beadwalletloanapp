package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_identity_liveness_record")
public class BwIdentityLivenessRecord implements Serializable {

	private static final long serialVersionUID = -6485948787968219006L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//主键ID
	private String productNo;//产品编号(给请求方分配的产品编号)
	private String livenessSource;//活体识别来源（1:face++ 2:商汤）
	private String idcardNumber;//身份证号
	private String name;//姓名
	private String livenessUrl;//活体资源Url
	private String livenessData;//活体文件base64（不保存）
	private String livenessResult;//活体识别结果json
	private Date createTime;//创建时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getLivenessSource() {
		return livenessSource;
	}
	public void setLivenessSource(String livenessSource) {
		this.livenessSource = livenessSource;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLivenessUrl() {
		return livenessUrl;
	}
	public void setLivenessUrl(String livenessUrl) {
		this.livenessUrl = livenessUrl;
	}
	public String getLivenessData() {
		return livenessData;
	}
	public void setLivenessData(String livenessData) {
		this.livenessData = livenessData;
	}
	public String getLivenessResult() {
		return livenessResult;
	}
	public void setLivenessResult(String livenessResult) {
		this.livenessResult = livenessResult;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
