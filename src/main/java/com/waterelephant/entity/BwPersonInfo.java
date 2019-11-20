package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwPersonInfo entity. @author MyEclipse Persistence Tools
 */
@Table(name = "bw_person_info")
public class BwPersonInfo implements java.io.Serializable {

    private static final long serialVersionUID = 7096985547686908515L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cityName;
    private String address;
    private String relationName;
    private String relationPhone;
    private String unrelationName;
    private String unrelationPhone;
    private Integer marryStatus;
    private Integer houseStatus;
    private Integer carStatus;
    private Long orderId;
    private Date createTime;
    private Date updateTime;
    private String email;
    private String colleagueName;
    private String colleaguePhone;
    private String friend1Name;
    private String friend1Phone;
    private String friend2Name;
    private String friend2Phone;
    private String qqchat;
    private String wechat;

    /**
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "BwPersonInfo [id=" + id + ", cityName=" + cityName + ", address=" + address + ", relationName=" + relationName + ", relationPhone=" + relationPhone + ", unrelationName="
                + unrelationName + ", unrelationPhone=" + unrelationPhone + ", marryStatus=" + marryStatus + ", houseStatus=" + houseStatus + ", carStatus=" + carStatus + ", orderId=" + orderId
                + ", createTime=" + createTime + ", updateTime=" + updateTime + ", email=" + email + "]";
    }

    public String getColleagueName() {
        return colleagueName;
    }

    public void setColleagueName(String colleagueName) {
        this.colleagueName = colleagueName;
    }

    public String getColleaguePhone() {
        return colleaguePhone;
    }

    public void setColleaguePhone(String colleaguePhone) {
        this.colleaguePhone = colleaguePhone;
    }

    public String getFriend1Name() {
        return friend1Name;
    }

    public void setFriend1Name(String friend1Name) {
        this.friend1Name = friend1Name;
    }

    public String getFriend1Phone() {
        return friend1Phone;
    }

    public void setFriend1Phone(String friend1Phone) {
        this.friend1Phone = friend1Phone;
    }

    public String getFriend2Name() {
        return friend2Name;
    }

    public void setFriend2Name(String friend2Name) {
        this.friend2Name = friend2Name;
    }

    public String getFriend2Phone() {
        return friend2Phone;
    }

    public void setFriend2Phone(String friend2Phone) {
        this.friend2Phone = friend2Phone;
    }

    public String getQqchat() {
        return qqchat;
    }

    public void setQqchat(String qqchat) {
        this.qqchat = qqchat;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * @return 获取 id属性值
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 设置 id 属性值为参数值 id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 获取 cityName属性值
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName 设置 cityName 属性值为参数值 cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return 获取 address属性值
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address 设置 address 属性值为参数值 address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return 获取 relationName属性值
     */
    public String getRelationName() {
        return relationName;
    }

    /**
     * @param relationName 设置 relationName 属性值为参数值 relationName
     */
    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    /**
     * @return 获取 relationPhone属性值
     */
    public String getRelationPhone() {
        return relationPhone;
    }

    /**
     * @param relationPhone 设置 relationPhone 属性值为参数值 relationPhone
     */
    public void setRelationPhone(String relationPhone) {
        this.relationPhone = relationPhone;
    }

    /**
     * @return 获取 unrelationName属性值
     */
    public String getUnrelationName() {
        return unrelationName;
    }

    /**
     * @param unrelationName 设置 unrelationName 属性值为参数值 unrelationName
     */
    public void setUnrelationName(String unrelationName) {
        this.unrelationName = unrelationName;
    }

    /**
     * @return 获取 unrelationPhone属性值
     */
    public String getUnrelationPhone() {
        return unrelationPhone;
    }

    /**
     * @param unrelationPhone 设置 unrelationPhone 属性值为参数值 unrelationPhone
     */
    public void setUnrelationPhone(String unrelationPhone) {
        this.unrelationPhone = unrelationPhone;
    }

    /**
     * @return 获取 marryStatus属性值
     */
    public Integer getMarryStatus() {
        return marryStatus;
    }

    /**
     * @param marryStatus 设置 marryStatus 属性值为参数值 marryStatus
     */
    public void setMarryStatus(Integer marryStatus) {
        this.marryStatus = marryStatus;
    }

    /**
     * @return 获取 houseStatus属性值
     */
    public Integer getHouseStatus() {
        return houseStatus;
    }

    /**
     * @param houseStatus 设置 houseStatus 属性值为参数值 houseStatus
     */
    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    /**
     * @return 获取 carStatus属性值
     */
    public Integer getCarStatus() {
        return carStatus;
    }

    /**
     * @param carStatus 设置 carStatus 属性值为参数值 carStatus
     */
    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    /**
     * @return 获取 orderId属性值
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId 设置 orderId 属性值为参数值 orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return 获取 createTime属性值
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 设置 createTime 属性值为参数值 createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 获取 updateTime属性值
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 设置 updateTime 属性值为参数值 updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 获取 email属性值
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 设置 email 属性值为参数值 email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
