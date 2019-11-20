package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 身份证信息表
 *
 * @author dengyan
 *
 */
@Table(name = "bw_external_identity_card")
public class BwExternalIdentityCard implements Serializable {
	private static final long serialVersionUID = -4889702405226642766L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键id
	private String appid;//应用id 产品id
    private String name; // 姓名
    private String gender; // 性别
    private String idCardNumber; // 身份证号码
    private String address; // 地址
    private String birthday; // 生日
    private String issuedBy; // 签发机关
    private String validDate; // 身份证有效期
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private String race; // 民族
    private Integer verifySource;//校验来源 faceId :1 商汤 2
    private String ext1;
    private String ext2;
    private String ext3;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Integer getVerifySource() {
		return verifySource;
	}

	public void setVerifySource(Integer verifySource) {
		this.verifySource = verifySource;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
    
}
