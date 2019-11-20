package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 活跃信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 11:09
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserPortraitActiveDays {

    /**
     * start_day : 2016-12-01
     * stop_3_days_detail : ["2017-01-10 - 2017-01-12","2017-02-07 - 2017-02-09","2017-03-05 - 2017-03-07"]
     * stop_days_detail : ["2016-12-05","2016-12-06","2016-12-11","2016-12-14","2016-12-20","2016-12-30","2017-01-03","2017-01-10","2017-01-11","2017-01-12","2017-02-07","2017-02-08","2017-02-09","2017-02-16","2017-02-24","2017-03-02","2017-03-03","2017-03-05","2017-03-06","2017-03-07","2017-03-21","2017-03-26","2017-03-28","2017-04-03","2017-04-06","2017-04-29","2017-05-07","2017-05-08"]
     * total_days : 160
     * end_day : 2017-05-09
     * stop_days : 28
     * stop_3_days : 3
     */

    private String start_day;
    private Integer total_days;
    private String end_day;
    private Integer stop_days;
    private Integer stop_3_days;
    private String stop_3_days_detail;
    private String stop_days_detail;

    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public Integer getTotal_days() {
        return total_days;
    }

    public void setTotal_days(Integer total_days) {
        this.total_days = total_days;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public Integer getStop_days() {
        return stop_days;
    }

    public void setStop_days(Integer stop_days) {
        this.stop_days = stop_days;
    }

    public Integer getStop_3_days() {
        return stop_3_days;
    }

    public void setStop_3_days(Integer stop_3_days) {
        this.stop_3_days = stop_3_days;
    }

    public String getStop_3_days_detail() {
        return stop_3_days_detail;
    }

    public void setStop_3_days_detail(String stop_3_days_detail) {
        this.stop_3_days_detail = stop_3_days_detail;
    }

    public String getStop_days_detail() {
        return stop_days_detail;
    }

    public void setStop_days_detail(String stop_days_detail) {
        this.stop_days_detail = stop_days_detail;
    }
}
