package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 外地呼叫分析
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 12:02
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripAnalysis {

    /**
     * called_cnt : 224
     * talk_seconds : 28048
     * talk_cnt : 355
     * msg_cnt : 0
     * called_seconds : 17916
     * receive_cnt : 0
     * date_distribution : ["2017-05","2017-04","2017-03","2017-02","2017-01","2016-12"]
     * detail : 1
     * call_cnt : 131
     * unknown_cnt : 0
     * location : 湖北武汉
     * send_cnt : 0
     * call_seconds : 10132
     */

    private int called_cnt;
    private int talk_seconds;
    private int talk_cnt;
    private int msg_cnt;
    private int called_seconds;
    private int receive_cnt;
    private List<Detail> detail;
    private int call_cnt;
    private int unknown_cnt;
    private String location;
    private int send_cnt;
    private int call_seconds;
    private String date_distribution;

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

    public int getMsg_cnt() {
        return msg_cnt;
    }

    public void setMsg_cnt(int msg_cnt) {
        this.msg_cnt = msg_cnt;
    }

    public int getCalled_seconds() {
        return called_seconds;
    }

    public void setCalled_seconds(int called_seconds) {
        this.called_seconds = called_seconds;
    }

    public int getReceive_cnt() {
        return receive_cnt;
    }

    public void setReceive_cnt(int receive_cnt) {
        this.receive_cnt = receive_cnt;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getDate_distribution() {
        return date_distribution;
    }

    public void setDate_distribution(String date_distribution) {
        this.date_distribution = date_distribution;
    }
}
