package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 朋友圈分布
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 11:30
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserPortraitContactDistribution {

    /**
     * ratio : 93.61
     * location : 湖北
     */

    private double ratio;
    private String location;

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
