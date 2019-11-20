package com.waterelephant.rongtaobao.service;

import com.waterelephant.rongtaobao.entity.TaoBaoReturn;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/8 9:46
 */
public interface RongTaoBaoService {

    void saveTaoBaoData(TaoBaoReturn taoBaoReturn,Long borrowerId, Long orderId, String authChannel) throws Exception;


    void saveOrderAuther(String orderId);
}
