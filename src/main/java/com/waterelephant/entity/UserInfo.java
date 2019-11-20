/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.waterelephant.entity;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author HWang
 */
public class UserInfo implements Serializable {
    /**
     * 唯一标识
     */
    private String openid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private String sex;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
    /**
     * 头像
     */
    private String headimgurl;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
}
