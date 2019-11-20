package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 报文头
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 15:14
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadInfo {

    /**
     * report_time : 2017-05-09 15:16:46
     * search_id : 14943140751141785819
     * user_type : 1
     */

    private String report_time;
    private String search_id;
    private int user_type;

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public String getSearch_id() {
        return search_id;
    }

    public void setSearch_id(String search_id) {
        this.search_id = search_id;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
}
