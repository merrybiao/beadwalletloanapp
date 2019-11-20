package com.waterelephant.drainage.jsonentity.fqgj;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@SuppressWarnings("serial")
public class CallLogVO implements Serializable {

    private String type;

    private String date;

    private String duration;

    private String phone;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
