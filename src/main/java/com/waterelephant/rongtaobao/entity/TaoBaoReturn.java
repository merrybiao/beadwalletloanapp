package com.waterelephant.rongtaobao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/8 9:49
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TaoBaoReturn {
    private List<TaoBaoEntity> data_list;

    public List<TaoBaoEntity> getData_list() {
        return data_list;
    }

    public void setData_list(List<TaoBaoEntity> data_list) {
        this.data_list = data_list;
    }
}
