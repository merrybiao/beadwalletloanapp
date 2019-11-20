package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通讯录
 * Created by dengyan on 2017/7/20.
 */
public class PhoneList {

    @JSONField(name = "phone") // 手机号
    private String phone;

    @JSONField(name = "name") // 姓名
    private String name;
    
    @JSONField(name = "create_time") // 创建时间，13位时间戳
    private String createTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    
}
