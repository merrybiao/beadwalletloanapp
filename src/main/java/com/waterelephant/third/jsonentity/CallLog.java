package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通话记录
 * Created by dengyan on 2017/7/20.
 */
public class CallLog {
    @JSONField(name = "type")
    private String type; // 运营商 1：移动 2：联通 3：电信

    @JSONField(name = "date")
    private String date; // 通话日期：13位时间戳

    @JSONField(name = "phone")
    private String phone; // 被叫手机号

    @JSONField(name = "duration")
    private String duration; // 通话时长单位/秒

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
