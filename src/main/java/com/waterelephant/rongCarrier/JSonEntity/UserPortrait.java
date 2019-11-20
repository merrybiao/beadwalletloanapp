package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 用户画像
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 11:01
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserPortrait {

    /**
     * night_activity_ratio : 7.56
     * both_call_cnt : 20
     * special_call_info : 1
     * night_msg_ratio : 6.5
     * contact_distribution : 1
     * active_days : 1
     */

    private double night_activity_ratio;
    private int both_call_cnt;
    private List<UserPortraitSpecialCallInfo> special_call_info;
    private double night_msg_ratio;
    private UserPortraitContactDistribution contact_distribution;
    private UserPortraitActiveDays active_days;

    public double getNight_activity_ratio() {
        return night_activity_ratio;
    }

    public void setNight_activity_ratio(double night_activity_ratio) {
        this.night_activity_ratio = night_activity_ratio;
    }

    public int getBoth_call_cnt() {
        return both_call_cnt;
    }

    public void setBoth_call_cnt(int both_call_cnt) {
        this.both_call_cnt = both_call_cnt;
    }

    public List<UserPortraitSpecialCallInfo> getSpecial_call_info() {
        return special_call_info;
    }

    public void setSpecial_call_info(List<UserPortraitSpecialCallInfo> special_call_info) {
        this.special_call_info = special_call_info;
    }

    public double getNight_msg_ratio() {
        return night_msg_ratio;
    }

    public void setNight_msg_ratio(double night_msg_ratio) {
        this.night_msg_ratio = night_msg_ratio;
    }

    public UserPortraitContactDistribution getContact_distribution() {
        return contact_distribution;
    }

    public void setContact_distribution(UserPortraitContactDistribution contact_distribution) {
        this.contact_distribution = contact_distribution;
    }

    public UserPortraitActiveDays getActive_days() {
        return active_days;
    }

    public void setActive_days(UserPortraitActiveDays active_days) {
        this.active_days = active_days;
    }
}
