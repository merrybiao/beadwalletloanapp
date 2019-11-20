package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 联系人所在地区
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 11:54
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class AreaAnalysis {

    /**
     * called_cnt : 4
     * talk_seconds : 1441
     * talk_cnt : 13
     * area : 上海
     * receive_cnt : 0
     * called_seconds : 457
     * msg_cnt : 0
     * detail : 1
     * call_cnt : 9
     * unknown_cnt : 0
     * contact_phone_cnt : 2
     * send_cnt : 0
     * call_seconds : 984
     */

    private int called_cnt;
    private int talk_seconds;
    private int talk_cnt;
    private String area;
    private int receive_cnt;
    private int called_seconds;
    private int msg_cnt;
    private List<AreaAnalysisDetail> detail;
    private int call_cnt;
    private int unknown_cnt;
    private int contact_phone_cnt;
    private int send_cnt;
    private int call_seconds;

    public int getCalled_cnt() {
        return called_cnt;
    }

    public void setCalled_cnt(int called_cnt) {
        this.called_cnt = called_cnt;
    }

    public int getTalk_seconds() {
        return talk_seconds;
    }

    public void setTalk_seconds(int talk_seconds) {
        this.talk_seconds = talk_seconds;
    }

    public int getTalk_cnt() {
        return talk_cnt;
    }

    public void setTalk_cnt(int talk_cnt) {
        this.talk_cnt = talk_cnt;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getReceive_cnt() {
        return receive_cnt;
    }

    public void setReceive_cnt(int receive_cnt) {
        this.receive_cnt = receive_cnt;
    }

    public int getCalled_seconds() {
        return called_seconds;
    }

    public void setCalled_seconds(int called_seconds) {
        this.called_seconds = called_seconds;
    }

    public int getMsg_cnt() {
        return msg_cnt;
    }

    public void setMsg_cnt(int msg_cnt) {
        this.msg_cnt = msg_cnt;
    }

    public List<AreaAnalysisDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<AreaAnalysisDetail> detail) {
        this.detail = detail;
    }

    public int getCall_cnt() {
        return call_cnt;
    }

    public void setCall_cnt(int call_cnt) {
        this.call_cnt = call_cnt;
    }

    public int getUnknown_cnt() {
        return unknown_cnt;
    }

    public void setUnknown_cnt(int unknown_cnt) {
        this.unknown_cnt = unknown_cnt;
    }

    public int getContact_phone_cnt() {
        return contact_phone_cnt;
    }

    public void setContact_phone_cnt(int contact_phone_cnt) {
        this.contact_phone_cnt = contact_phone_cnt;
    }

    public int getSend_cnt() {
        return send_cnt;
    }

    public void setSend_cnt(int send_cnt) {
        this.send_cnt = send_cnt;
    }

    public int getCall_seconds() {
        return call_seconds;
    }

    public void setCall_seconds(int call_seconds) {
        this.call_seconds = call_seconds;
    }
}
