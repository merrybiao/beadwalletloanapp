package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 联系人所在地区
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 11:54
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class AreaAnalysisDetail {

    /**
     * called_cnt : 4
     * talk_seconds : 1441
     * talk_cnt : 13
     * msg_cnt : 0
     * called_seconds : 457
     * receive_cnt : 0
     * month : 2017-03
     * call_cnt : 9
     * unknown_cnt : 0
     * contact_phone_cnt : 2
     * send_cnt : 0
     * call_seconds : 984
     */

    private String called_cnt;
    private String talk_seconds;
    private String talk_cnt;
    private String msg_cnt;
    private String called_seconds;
    private String receive_cnt;
    private String month;
    private String call_cnt;
    private String unknown_cnt;
    private String contact_phone_cnt;
    private String send_cnt;
    private String call_seconds;

    public String getCalled_cnt() {
        return called_cnt;
    }

    public void setCalled_cnt(String called_cnt) {
        this.called_cnt = called_cnt;
    }

    public String getTalk_seconds() {
        return talk_seconds;
    }

    public void setTalk_seconds(String talk_seconds) {
        this.talk_seconds = talk_seconds;
    }

    public String getTalk_cnt() {
        return talk_cnt;
    }

    public void setTalk_cnt(String talk_cnt) {
        this.talk_cnt = talk_cnt;
    }

    public String getMsg_cnt() {
        return msg_cnt;
    }

    public void setMsg_cnt(String msg_cnt) {
        this.msg_cnt = msg_cnt;
    }

    public String getCalled_seconds() {
        return called_seconds;
    }

    public void setCalled_seconds(String called_seconds) {
        this.called_seconds = called_seconds;
    }

    public String getReceive_cnt() {
        return receive_cnt;
    }

    public void setReceive_cnt(String receive_cnt) {
        this.receive_cnt = receive_cnt;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCall_cnt() {
        return call_cnt;
    }

    public void setCall_cnt(String call_cnt) {
        this.call_cnt = call_cnt;
    }

    public String getUnknown_cnt() {
        return unknown_cnt;
    }

    public void setUnknown_cnt(String unknown_cnt) {
        this.unknown_cnt = unknown_cnt;
    }

    public String getContact_phone_cnt() {
        return contact_phone_cnt;
    }

    public void setContact_phone_cnt(String contact_phone_cnt) {
        this.contact_phone_cnt = contact_phone_cnt;
    }

    public String getSend_cnt() {
        return send_cnt;
    }

    public void setSend_cnt(String send_cnt) {
        this.send_cnt = send_cnt;
    }

    public String getCall_seconds() {
        return call_seconds;
    }

    public void setCall_seconds(String call_seconds) {
        this.call_seconds = call_seconds;
    }
}
