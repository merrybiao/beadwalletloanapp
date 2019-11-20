package com.waterelephant.drainage.entity.fqgj;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/20 11:06
 */
public class FqgjReq {
    private String app_id;
    private String biz_data;
    private String format;
    private String sign;
    private String sign_type;
    private String timestamp;
    private String version;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getBiz_data() {
        return biz_data;
    }

    public void setBiz_data(String biz_data) {
        this.biz_data = biz_data;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "FqgjReq{" +
                "app_id='" + app_id + '\'' +
                ", biz_data='" + biz_data + '\'' +
                ", format='" + format + '\'' +
                ", sign='" + sign + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
