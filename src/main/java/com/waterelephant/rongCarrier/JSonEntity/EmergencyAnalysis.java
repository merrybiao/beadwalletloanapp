package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 紧急联系人
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 11:44
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class EmergencyAnalysis {
    private String phone;
    private String name;
    private String first_contact_date;
    private String last_contact_date;
    private Integer talk_seconds;
    private Integer talk_cnt;
    private Integer call_seconds;
    private Integer call_cnt;
    private Integer called_seconds;
    private Integer called_cnt;
    private Integer msg_cnt;
    private Integer send_cnt;
    private Integer receive_cnt;
    private Integer unknown_cnt;

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

    public String getFirst_contact_date() {
        return first_contact_date;
    }

    public void setFirst_contact_date(String first_contact_date) {
        this.first_contact_date = first_contact_date;
    }

    public String getLast_contact_date() {
        return last_contact_date;
    }

    public void setLast_contact_date(String last_contact_date) {
        this.last_contact_date = last_contact_date;
    }

    public Integer getTalk_seconds() {
        return talk_seconds;
    }

    public void setTalk_seconds(Integer talk_seconds) {
        this.talk_seconds = talk_seconds;
    }

    public Integer getTalk_cnt() {
        return talk_cnt;
    }

    public void setTalk_cnt(Integer talk_cnt) {
        this.talk_cnt = talk_cnt;
    }

    public Integer getCall_seconds() {
        return call_seconds;
    }

    public void setCall_seconds(Integer call_seconds) {
        this.call_seconds = call_seconds;
    }

    public Integer getCall_cnt() {
        return call_cnt;
    }

    public void setCall_cnt(Integer call_cnt) {
        this.call_cnt = call_cnt;
    }

    public Integer getCalled_seconds() {
        return called_seconds;
    }

    public void setCalled_seconds(Integer called_seconds) {
        this.called_seconds = called_seconds;
    }

    public Integer getCalled_cnt() {
        return called_cnt;
    }

    public void setCalled_cnt(Integer called_cnt) {
        this.called_cnt = called_cnt;
    }

    public Integer getMsg_cnt() {
        return msg_cnt;
    }

    public void setMsg_cnt(Integer msg_cnt) {
        this.msg_cnt = msg_cnt;
    }

    public Integer getSend_cnt() {
        return send_cnt;
    }

    public void setSend_cnt(Integer send_cnt) {
        this.send_cnt = send_cnt;
    }

    public Integer getReceive_cnt() {
        return receive_cnt;
    }

    public void setReceive_cnt(Integer receive_cnt) {
        this.receive_cnt = receive_cnt;
    }

    public Integer getUnknown_cnt() {
        return unknown_cnt;
    }

    public void setUnknown_cnt(Integer unknown_cnt) {
        this.unknown_cnt = unknown_cnt;
    }
}