package com.waterelephant.drainage.jsonentity.fqgj;

import java.io.Serializable;

/**
 * Created by fq_qiguo on 2017/5/23.
 */
@SuppressWarnings("serial")
public class ContactVO implements Serializable{

    private String name;

    private String phone;

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
