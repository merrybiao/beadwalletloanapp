package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.NotNull;

/**
 * 用户基本信息
 * Created by dengyan on 2017/7/20.
 */
public class UserBasicInfo {

    @NotNull(message = "用户姓名为空")
    @JSONField(name = "name")
    private String name;

    @NotNull(message = "用户身份证号为空")
    @JSONField(name = "id_card")
    private String idCard;

    @NotNull(message = "用户手机号为空")
    @JSONField(name = "mobile")
    private String mobile;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
