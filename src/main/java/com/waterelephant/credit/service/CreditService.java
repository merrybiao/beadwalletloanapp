package com.waterelephant.credit.service;

/**
 * 用户额度计算业务类
 * <p>
 * Module:
 * <p>
 * CreditService.java
 *
 * @author 张博
 * @version 1.0
 * @description: <描述>
 * @since JDK 1.8
 */
public interface CreditService {

    /**
     * 根据身份证号查询用户已使用额度
     *
     * @param idCard 工单号
     * @return
     */
    public Double calculateUseCreditMoney(String idCard);

}
