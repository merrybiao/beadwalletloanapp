package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.Table;

/**
 * 产品关联表（维护每期服务费）
 *
 *
 * Module:
 *
 * BwProductTerm.java
 *
 * @author 毛汇源
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "bw_product_term")
public class BwProductTerm implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    private long id;// 主键
    private long pId;// 产品id
    private Integer termNum;// 期数
    private Double rate;// 每期服务费费率

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    public Integer getTermNum() {
        return termNum;
    }

    public void setTermNum(Integer termNum) {
        this.termNum = termNum;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}
