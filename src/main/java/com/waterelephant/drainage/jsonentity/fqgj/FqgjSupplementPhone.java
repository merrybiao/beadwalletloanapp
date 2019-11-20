package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/28 9:49
 */
public class FqgjSupplementPhone {
	
	@JSONField(name = "name")
    private String name;
	
	@JSONField(name = "phone")
    private String phone;
	
	@JSONField(name = "createtime")
    private String createtime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
