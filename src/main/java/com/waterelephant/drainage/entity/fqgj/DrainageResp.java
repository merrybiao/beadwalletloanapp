package com.waterelephant.drainage.entity.fqgj;

/**
 * 引流返回实体类
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/26 11:01
 */
public class DrainageResp {

    /**
     * 服务器响应编号
     */
    private String code;
    /**
     * 服务器响应消息
     */
    private String msg;
    /**
     * 服务器响应结果对象
     */
    private Object data;

    private String reason;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
