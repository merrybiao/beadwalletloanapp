package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_credit_dhb_binding_idcards")
public class BwCreditDhbBindingIdcards implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastAppearIdcard;// 最近此身份证出现时间
    private Integer otherNamesCnt;// 此号码绑定其他姓名个数
    private Date createTime;
    private String idcardCity;// 份证户籍城市
    private String idcardRegion;// 身份证户籍地区
    private String idcardProvince;// 身份证户籍省份
    private Integer idcardAge;// 年龄
    private String otherIdcard;// 绑定其他身份证号码
    private Integer idcardValidate;// 身份证是否是有效身份证
    private Long infoId;
    private String idcardGender;// 性别
    private Integer searchOrgsCnt;// 查询此身份证的机构数

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastAppearIdcard() {
        return this.lastAppearIdcard;
    }

    public void setLastAppearIdcard(String lastAppearIdcard) {
        this.lastAppearIdcard = lastAppearIdcard;
    }

    public Integer getOtherNamesCnt() {
        return this.otherNamesCnt;
    }

    public void setOtherNamesCnt(Integer otherNamesCnt) {
        this.otherNamesCnt = otherNamesCnt;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIdcardCity() {
        return this.idcardCity;
    }

    public void setIdcardCity(String idcardCity) {
        this.idcardCity = idcardCity;
    }

    public String getIdcardRegion() {
        return this.idcardRegion;
    }

    public void setIdcardRegion(String idcardRegion) {
        this.idcardRegion = idcardRegion;
    }

    public String getIdcardProvince() {
        return this.idcardProvince;
    }

    public void setIdcardProvince(String idcardProvince) {
        this.idcardProvince = idcardProvince;
    }

    public Integer getIdcardAge() {
        return this.idcardAge;
    }

    public void setIdcardAge(Integer idcardAge) {
        this.idcardAge = idcardAge;
    }

    public String getOtherIdcard() {
        return this.otherIdcard;
    }

    public void setOtherIdcard(String otherIdcard) {
        this.otherIdcard = otherIdcard;
    }

    public Integer getIdcardValidate() {
        return this.idcardValidate;
    }

    public void setIdcardValidate(Integer idcardValidate) {
        this.idcardValidate = idcardValidate;
    }

    public Long getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getIdcardGender() {
        return this.idcardGender;
    }

    public void setIdcardGender(String idcardGender) {
        this.idcardGender = idcardGender;
    }

    public Integer getSearchOrgsCnt() {
        return this.searchOrgsCnt;
    }

    public void setSearchOrgsCnt(Integer searchOrgsCnt) {
        this.searchOrgsCnt = searchOrgsCnt;
    }

}
